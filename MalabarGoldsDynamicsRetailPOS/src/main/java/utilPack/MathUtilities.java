package utilPack;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import testData.POC_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities.ReturnDetails;

public class MathUtilities {

	protected WebDriver driver;
	Assert asrt;
	JavascriptExecutor js;

	BasePge base;
	NormalSaleGoldandSilver_Obj NormalSaleGoldandSilverObj=new NormalSaleGoldandSilver_Obj();
	LoginPage_Obj LoginPageObj = new LoginPage_Obj();
	public MathUtilities (BasePge base) {
		this.base = base;
		this.driver = base.driver; 
	}

	/// <summary>
	/// Method to calculate cvalue in Ingredient table on item details page
	/// Author : Sangeetha M S
	/// </summary>	
	public void ValidateCvalue(Map<String, String> DataMap,int SkuNumber)
	{		
		for (String key : DataMap.keySet()) 
		{
			if (key.endsWith("_QTY")) 
			{
				String baseKey = key.replace("_QTY", "");
				double IngredientTbleQty = Double.parseDouble(DataMap.get(baseKey + "_QTY"));
				double IngredientTbleRate = Double.parseDouble(DataMap.get(baseKey + "_RATE"));
				double ExpectedCvalue = Double.parseDouble(DataMap.get(baseKey + "_CVALUE"));
				double CalculatedCvalue = Math.round(IngredientTbleQty * IngredientTbleRate * 100.0) / 100.0;

				asrt.assertEquals(CalculatedCvalue, ExpectedCvalue,1, "Mismatch at " + baseKey + ": Expected = " + ExpectedCvalue + ", Found = " + CalculatedCvalue+" in Ingredient table in Item Details Page");
			}
		}
	}
	/// <summary>
	/// Method to Validate cvalue and rate textfields in item details page
	/// Author : Sangeetha M S
	/// </summary>
	public void ValidateCvalueAndRateTextFields(Map<String, String> DataMap,int SkuNumber)
	{
		double CvalueSum = 0.0;
		for (String key : DataMap.keySet()) 
		{
			if (key.startsWith("SKU" + SkuNumber + "_ITEM") && key.endsWith("_CVALUE")) 
			{
				String valueTrimmed = DataMap.get(key).trim().replace(",", "");
				if (!valueTrimmed.isEmpty())
				{
					double value = Double.parseDouble(valueTrimmed);
					CvalueSum += value;
				}
			}
		}
		String ActualCvalueSum = String.format("%.2f", CvalueSum);
		String CvalueTextFieldKey = "SKU_" + SkuNumber + "_CvalueTextField";
		String ActualTextFieldCvalue = DataMap.get(CvalueTextFieldKey).trim();
		String RateTextFieldKey = "SKU_" + SkuNumber + "_RateTextField";
		String ActualTextFieldRate = DataMap.get(RateTextFieldKey).trim();
		if(CvalueSum==0) 
		{
			asrt.assertEquals(ActualTextFieldCvalue,ActualTextFieldRate,"Value Mismatch in CValue text box and Rate text box in Item Details page");
		}
		else
		{
			asrt.assertEquals(ActualTextFieldCvalue, ActualCvalueSum,"Value mismatch: Expected "+ActualTextFieldCvalue+" in CValue text box but got sum as"+ActualCvalueSum+" in Item Details page");
			asrt.assertEquals(ActualTextFieldRate, ActualCvalueSum,"Value mismatch: Expected "+ActualTextFieldRate+"in Rate text box but got sum as"+ActualCvalueSum+" in Item Details page");
		}
	}

	/// <summary>
	/// Method to Validate Total Amount in item details page
	/// Author : Chandana Babu
	/// </summary>
	public void ValidateTotalAmount(Map<String, String> DataMap, int index) {

		String Number = String.valueOf(index);

		double TextTotalCvalue = Double.parseDouble(DataMap.get("SKU_"+Number+"_TotalCValue"));
		double TextMakingValue = Double.parseDouble(DataMap.get("SKU_"+Number+"_MakingValue"));
		double TextWastageAmount = Double.parseDouble(DataMap.get("SKU_"+Number+"_WastageAmount"));
		double TextExpectedTotalAmount = Double.parseDouble(DataMap.get("SKU_"+Number+"_Total"));		
		double ActualTotalAmount;
		if (TextMakingValue == 0.00) {
			ActualTotalAmount = Math.round((TextTotalCvalue + TextWastageAmount)*100.0)/100.0;
		} else {
			ActualTotalAmount = Math.round((TextTotalCvalue + TextMakingValue)*100.0)/100.0;
		}
		asrt.assertEquals(ActualTotalAmount, TextExpectedTotalAmount,1,
				"Value mismatch: expected total is " + TextExpectedTotalAmount + " but got " + ActualTotalAmount+" in item details page");
	}

	/// <summary>
	/// CValue Validation- Recalculates each ingredient’s cost (Qty × Rate) and asserts it matches the expected value.
	/// Gross Weight Check- Sums all ingredient quantities (_QTY) and ensures it matches the SKU’s displayed gross weight.
	/// Making Value Check-Computes making cost using NetWeight × (MakingRate ÷ 100) and verifies it matches the Exp value.
	/// Wastage Amount Check-Strips non-numeric chars from WastageQty, computes (SumOfCValues × WastageQty ÷ 100), and verifies it with the Exp.
	/// Total Amount Check-Adds either the making value or wastage amount to the total Cvalue and verifies the final total.
	/// Author: VISHNU MANOJ K
	/// <summary>
	public void SkuIngredientItemCalculation(Map<String, String> SKUIngredientData, int SkuNumber) {
		System.out.println("====== Validating SKU_" + SkuNumber + " ======");

		double SumOfCValues = 0.0;
		for (String Key : SKUIngredientData.keySet()) {
			if (Key.endsWith("_QTY")) {
				String BaseKey = Key.replace("_QTY", "");
				double Qty = Double.parseDouble(SKUIngredientData.get(BaseKey + "_QTY"));
				double Rate = Double.parseDouble(SKUIngredientData.get(BaseKey + "_RATE"));
				double ExpectedCValue = Double.parseDouble(SKUIngredientData.get(BaseKey + "_CVALUE"));
				double CalculatedCValue = Math.round(Qty * Rate * 100.0) / 100.0;

				System.out.println("CValue for " + BaseKey +
						": Qty=" + Qty + ", Rate=" + Rate +
						", CalcValue=" + CalculatedCValue + ", ExpValue=" + ExpectedCValue);

				asrt.assertEquals(CalculatedCValue, ExpectedCValue,1,"After Calculation mismatch in CValue Actual:"+CalculatedCValue+" and Expected:"+ExpectedCValue+" for " + BaseKey);

				String ItemId = SKUIngredientData.getOrDefault(BaseKey + "_ITEMID", "")
						.trim()
						.toUpperCase();
				if (ItemId.equals("RG")) {
					SumOfCValues += CalculatedCValue;
				}
			}
		}

		double CalculatedGrossWeight = 0.0;
		String QtyPattern = "^SKU" + SkuNumber + "_ITEM(\\d+)_QTY$";
		for (String Key : SKUIngredientData.keySet()) {
			Matcher Matcher = Pattern.compile(QtyPattern).matcher(Key);
			if (Matcher.find()) {
				int ItemIndex = Integer.parseInt(Matcher.group(1)); // Extract item index
				double Qty = Double.parseDouble(SKUIngredientData.get(Key).replaceAll("[^\\d.]", ""));
				String ItemKeyPrefix = "SKU" + SkuNumber + "_ITEM" + ItemIndex;
				String Unit = SKUIngredientData.getOrDefault(ItemKeyPrefix + "_UNIT", "").trim().toLowerCase();
				double QtyInGrams = Unit.contains("ct") ? Qty * 0.2 : Qty;
				System.out.println("[DEBUG] " + Key + " = " + Qty + " (" + Unit + "), converted to grams = " + QtyInGrams);
				CalculatedGrossWeight += QtyInGrams;
			}
		}

		double ActualGrossWeight = Math.round(CalculatedGrossWeight * 1000.0) / 1000.0;
		double ExpectedGrossWeight = Double.parseDouble(SKUIngredientData.get("SKU_" + SkuNumber + "_GrossWeight"));

		System.out.println("Gross Weight: Calc=" + ActualGrossWeight + ", Exp=" + ExpectedGrossWeight);
		asrt.assertEquals(
				ActualGrossWeight, ExpectedGrossWeight, 0.100,
				"After Calculation mismatch in Gross Weight Actual: " + ActualGrossWeight +
				" and Expected: " + ExpectedGrossWeight + " for SKU_" + SkuNumber
				);

		String MakingRateStr = SKUIngredientData.get("SKU_" + SkuNumber + "_MakingRate");
		double MakingRate = 0.0;
		try {
			MakingRate = Double.parseDouble(MakingRateStr);
		} catch (Exception Ex) {
			System.out.println("Invalid MakingRate [" + MakingRateStr + "], defaulting to 0.0");
		}

		double NetWeight = Double.parseDouble(SKUIngredientData.get("SKU_" + SkuNumber + "_NetWeight"));

		double CalculatedMakingValue = Math.round(NetWeight * MakingRate * 100.0 ) / 100.0;

		double FetchedMakingValue = Double.parseDouble(SKUIngredientData.get("SKU_" + SkuNumber + "_MakingValue"));

		System.out.println("MakingValue: NetWeight=" + NetWeight +
				", MakingRate%=" + MakingRate +
				", CalcValue=" + CalculatedMakingValue +
				", ExpValue=" + FetchedMakingValue);
		//String TextMakingType = base.getTheSelectedTextInDropDown(NormalSaleGoldandSilverObj.Sel_DepositType("makingTypeOptions"));
		String TextMakingType = SKUIngredientData.get("SKU_" + SkuNumber + "_MakingType");

		if (TextMakingType.equals("Total")||TextMakingType.equals("Pcs") ) 
		{
			asrt.assertEquals(MakingRate, FetchedMakingValue,1," Mismatch in Making value and Making rate, Making value: "+FetchedMakingValue+" and Making rate : "+FetchedMakingValue+" for SKU_" + SkuNumber);
		}
		else
		{
			asrt.assertEquals(CalculatedMakingValue, FetchedMakingValue,1,"After Calculation mismatch in Making Value Actual:"+CalculatedMakingValue+" and Expected"+FetchedMakingValue+" for SKU_" + SkuNumber);
		}

		String WastageQtyRaw = SKUIngredientData.get("SKU_" + SkuNumber + "_WastageQty").trim();
		String WastageQtyClean = WastageQtyRaw.replaceAll("[^\\d.]", "");
		System.out.println("Raw WastageQty: [" + WastageQtyRaw + "], Cleaned: [" + WastageQtyClean + "]");
		double WastageQty = WastageQtyClean.isEmpty() ? 0.0 : Double.parseDouble(WastageQtyClean);

		double CalculatedWastageAmount = Math.round(SumOfCValues * (WastageQty / 100.0) * 100.0) / 100.0;
		double FetchedWastageAmount = Double.parseDouble(
				SKUIngredientData.get("SKU_" + SkuNumber + "_WastageAmount"));

		System.out.println("WastageAmount: SumCValues=" + SumOfCValues +
				", WastageQty%=" + WastageQty +
				", CalcValue=" + CalculatedWastageAmount +
				", ExpValue=" + FetchedWastageAmount);
		asrt.assertEquals(CalculatedWastageAmount, FetchedWastageAmount,1,"After Calculation mismatch in Wastage Amount Actual:"+CalculatedWastageAmount+" and Expected:"+FetchedWastageAmount+" for SKU_" + SkuNumber);

		double TotalCValue = Double.parseDouble(SKUIngredientData.get("SKU_" + SkuNumber + "_TotalCValue"));
		double ExpectedTotal = Double.parseDouble(SKUIngredientData.get("SKU_" + SkuNumber + "_Total"));
		double ActualTotal = Math.round((TotalCValue +
				(FetchedMakingValue == 0.00 ? CalculatedWastageAmount : FetchedMakingValue)) * 100.0) / 100.0;

		System.out.println("TotalAmount: CValue=" + TotalCValue +
				", Mode=" + (FetchedMakingValue == 0.00 ? "Wastage" : "Making") +
				", UsedValue=" + (FetchedMakingValue == 0.00 ? CalculatedWastageAmount : FetchedMakingValue) +
				", CalcValue=" + ActualTotal +
				", ExpValue=" + ExpectedTotal);
		asrt.assertEquals(ActualTotal, ExpectedTotal,1,"After Calculation mismatch in Total Amount Actual:"+ActualTotal+" and Expected:"+ExpectedTotal+" for SKU_" + SkuNumber);

		System.out.println("====== Validation Completed for SKU_" + SkuNumber + " ======\n");
	}

	/// <summary>
	/// Validate SKU Count- Compares the number of SKUs shown on the transaction page with the LinesCount from TransactionDataLine.
	/// Validate SKU Names- Ensures each displayed SKU name matches the expected name from ScannedSkuDataMap, ignoring prefix codes.
	/// Validate Gross Weight (Qty)-Confirms the displayed quantity (gross weight) for each SKU matches the expected gross weight.
	/// Validate Total Without Tax (Per SKU)-Asserts that each SKU's displayed total (without tax) matches the expected total value.
	/// Validate Subtotal (Sum of All SKU Totals)-Adds all displayed SKU totals and compares the sum to the Subtotal value from TransactionDataLine.
	/// Validate Tax (3% of Subtotal) -Calculates 3% of the subtotal and verifies it matches the displayed Tax value.
	/// Validate Total Amount (Subtotal + Tax)-Adds subtotal and tax and confirms it equals the displayed TotalAmount.
	/// Author: VISHNU MANOJ K
	/// <summary>
	public void ValidateTransactionLineCalculation(Map<String, String> TransactionDataLine,Map<String, String> ScannedSkuDataMap)
	{
		System.out.println("====== Validation Started for Transaction Page Line ======  ");

		// === Step 1: Assert that number of SKUs matches LinesCount ===
		List<String> SkuNameElements = base.GetElementTexts(
				NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5")
				);

		int ExpectedLinesCount = Integer.parseInt(TransactionDataLine.get("LinesCount").replaceAll("[^\\d]", ""));
		Assert.assertEquals(SkuNameElements.size(), ExpectedLinesCount, "After Calculation Value Mismatch in number of SKUs Actual:"+SkuNameElements.size()+" and number of SKUs Expected:"+ExpectedLinesCount+" LinesCount in Transaction Line Page");

		// === Step 2: Assert each SKU Name ===
		for (int i = 0; i < SkuNameElements.size(); i++) {
			String ActualSkuName = SkuNameElements.get(i).trim();
			String ExpectedSkuName = ScannedSkuDataMap.get("SKU_" + (i + 1) + "_SKUName");
			Assert.assertNotNull(ExpectedSkuName, "Expected SKU name is null for SKU_" + (i + 1));

			String[] Split = ExpectedSkuName.trim().split(" - ", 2);
			String ExpectedProductNameOnly = (Split.length > 1) ? Split[1].trim() : ExpectedSkuName.trim();

			System.out.println("Asserting SKU_" + (i + 1) + " Name: Expected = " + ExpectedProductNameOnly + ", Actual = " + ActualSkuName);
			Assert.assertEquals(ActualSkuName, ExpectedProductNameOnly, "After Calculation Mismatch in SKU name Actual:"+ActualSkuName+" and SKU name Expected:"+ExpectedProductNameOnly+"  for SKU_" + (i + 1));
		}

		// === Step 3: Assert GrossWeight (Qty) ===
		List<String> SkuQuantityElements = base.GetElementTexts(
				NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5")
				);

		for (int i = 0; i < SkuQuantityElements.size(); i++) {
			String ActualQtyStr = SkuQuantityElements.get(i).trim().replaceAll("[^\\d.]", "");
			String ExpectedGrossWeightStr = ScannedSkuDataMap.get("SKU_" + (i + 1) + "_GrossWeight");

			Assert.assertNotNull(ExpectedGrossWeightStr, "Expected GrossWeight is null for SKU_" + (i + 1));

			double ActualQty = Double.parseDouble(ActualQtyStr);
			double ExpectedGrossWeight = Double.parseDouble(ExpectedGrossWeightStr.trim().replaceAll("[^\\d.]", ""));

			System.out.println("Asserting GrossWeight for SKU_" + (i + 1) + ": Expected = " + ExpectedGrossWeight + ", Actual = " + ActualQty);
			Assert.assertEquals(ActualQty, ExpectedGrossWeight,1, "After Calculation Mismatch in GrossWeight Actual:"+ActualQty+" and Expected:"+ExpectedGrossWeight+" for SKU_" + (i + 1));
		}

		// === Step 4 & 5: Assert Total Without Tax and Subtotal ===
		List<String> TotalWithoutTaxElements = base.GetElementTexts(
				NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5")
				);

		double TotalWithoutTaxSum = 0.0;

		for (int i = 0; i < TotalWithoutTaxElements.size(); i++) {
			int SkuNumber = i + 1;
			String ActualTotalStr = TotalWithoutTaxElements.get(i).trim().replaceAll("[^\\d.]", "");
			String ExpectedTotalStr = ScannedSkuDataMap.get("SKU_" + SkuNumber + "_Total");

			double ActualTotal = Double.parseDouble(ActualTotalStr);
			double ExpectedTotal = Double.parseDouble(ExpectedTotalStr.trim().replaceAll("[^\\d.]", ""));

			System.out.println("Asserting TOTAL (Without Tax) for SKU_" + SkuNumber + ": Expected = " + ExpectedTotal + ", Actual = " + ActualTotal);
			Assert.assertEquals(ActualTotal, ExpectedTotal,1, "After Calculation Mismatch in TOTAL (Without Tax) Actual:"+ActualTotal+" and TOTAL (Without Tax) Expected:"+ExpectedTotal+" for SKU_" + SkuNumber);

			TotalWithoutTaxSum += ActualTotal;
		}

		double ExpectedSubtotal = Double.parseDouble(TransactionDataLine.get("Subtotal").replaceAll("[^\\d.]", ""));
		System.out.println("Asserting TOTAL (Without Tax) SUM vs Subtotal:");
		System.out.println("Expected Subtotal = " + ExpectedSubtotal + ", Actual Sum = " + TotalWithoutTaxSum);
		Assert.assertEquals(TotalWithoutTaxSum, ExpectedSubtotal,1, "After Calculation Mismatch in total sum Actual:"+TotalWithoutTaxSum+" and total sum Expected:"+ExpectedSubtotal+" ");

		// === Step 6: TAX = 3% of Subtotal ===
		double CalculatedTax = Math.round((ExpectedSubtotal * 0.03) * 100.0) / 100.0;
		double ExpectedTax = Double.parseDouble(TransactionDataLine.get("Tax").replaceAll("[^\\d.]", ""));

		System.out.println("Asserting calculated 3% TAX vs displayed Tax:");
		System.out.println("Expected Tax = " + ExpectedTax + ", Calculated Tax = " + CalculatedTax);
		Assert.assertEquals(CalculatedTax, ExpectedTax,1, "After Calculation Mismatch in calculated Tax "+CalculatedTax+" and Expected Tax value is"+ExpectedTax+"");

		// === Step 7: TotalAmount = Subtotal + Tax ===
		double CalculatedTotalAmount = Math.round((ExpectedSubtotal + ExpectedTax) * 100.0) / 100.0;
		double ExpectedTotalAmount = Double.parseDouble(TransactionDataLine.get("TotalAmount").replaceAll("[^\\d.]", ""));

		System.out.println("Asserting Total Amount (Subtotal + Tax) with displayed TotalAmount:");
		System.out.println("Expected TotalAmount = " + ExpectedTotalAmount + ", Calculated TotalAmount = " + CalculatedTotalAmount);
		Assert.assertEquals(CalculatedTotalAmount, ExpectedTotalAmount,1, "After Calculation Mismatch in Total Amount Calculated "+CalculatedTotalAmount+" and Expected Total Amount "+ExpectedTotalAmount+"");

		System.out.println("====== Validation Completed for Transaction Page Line ======  ");

	}
	///<summary>
	/// Method to validate the data in Old Gold Item details screen
	///Author : Hasna EK
	/// </summary>
	public Map<String, Double> ValidateOldGoldItemDetailsData(double BoardRate22k, String Configuration)throws Exception {

		Map<String, Double> OgDataMap = new LinkedHashMap<>();
		String OgQty = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column3 textRight", "h4 ellipsis cell"));
		String OgRate = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column4 textRight", "h4 ellipsis cell"));
		String OgCvalue = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column5 textRight", "h4 ellipsis cell"));

		Double OgQtyIngredientTable =  Double.parseDouble(OgQty);
		Double OgRateIngredientTable = Double.parseDouble(OgRate);
		Double OgCValueIngredientTable = Double.parseDouble(OgCvalue);
		//		Double BoardRate22k = Double.parseDouble(BoardRate22kFrmSkuTble);
		Double OgCalculatedCvalue = OgQtyIngredientTable * OgRateIngredientTable;
		OgDataMap.put("OgQty", OgQtyIngredientTable);
		OgDataMap.put("OgCvalue",OgCValueIngredientTable);

		if (Configuration.equalsIgnoreCase("18k")){
			Double ExpectedOgRate18k = (BoardRate22k*18)/22;
			long Rounded18kRate = Math.round(ExpectedOgRate18k);
			asrt.assertEquals(Rounded18kRate, OgRateIngredientTable,1, "Mismatch in Board rate, Expected " + Rounded18kRate+" but got "+OgRateIngredientTable+" in  Old Gold Item details page");
		}else {
			asrt.assertEquals(BoardRate22k, OgRateIngredientTable,1, "Mismatch in Board rate, Expected " + BoardRate22k+" but got "+OgRateIngredientTable+" in  Old Gold Item details page");
		}

		asrt.assertEquals(OgCalculatedCvalue, OgCValueIngredientTable,1, "Mismatch in Cvalue,  Expected " + OgCalculatedCvalue+" but got "+OgCValueIngredientTable+" in  Old Gold Item details page");
		Double OgNetRateValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		asrt.assertEquals(OgRateIngredientTable, OgNetRateValue,1,"Mismatch in net rate, Expected " + OgRateIngredientTable+" but got "+OgNetRateValue+" in  Old Gold Item details page");
		Double OgNetAmountValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(OgCalculatedCvalue, OgNetAmountValue,1," Mismatch in Net amount, Expected " + OgCalculatedCvalue+" but got "+OgNetAmountValue+" in  Old Gold Item details page");
		String OgPurityValue =base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetpurity"));
		asrt.assertTrue(OgPurityValue.equalsIgnoreCase(Configuration),"Mismatch in Purity value, Expected " + OgPurityValue+" but got "+Configuration+" in  Old Gold Item details page");
		Double TotalAmountValue =Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt")));
		asrt.assertEquals(OgCalculatedCvalue, TotalAmountValue,1, "Mismatch in Total amount value , Expected " + OgCalculatedCvalue+" but got "+TotalAmountValue+" in 22k Old Gold Item details page");

		return OgDataMap;
	}

	/// <summary>
	/// Validate SKU Count- Compares the number of SKUs shown on the transaction page with the LinesCount from TransactionDataLine.
	/// Validate SKU Names- Ensures each displayed SKU name matches the expected name from ScannedSkuDataMap, ignoring prefix codes.
	/// Validate Gross Weight (Qty)-Confirms the displayed quantity (gross weight) for each SKU matches the expected gross weight.
	/// Validate Total Without Tax (Per SKU)-Asserts that each SKU's displayed total (without tax) matches the expected total value.
	/// Validate Subtotal (Sum of All SKU Totals)-Adds all displayed SKU totals and compares the sum to the Subtotal value from TransactionDataLine.
	/// Validate Tax (3% of Subtotal) -Calculates 3% of the subtotal and verifies it matches the displayed Tax value.
	/// Validate Total Amount (Subtotal + Tax)-Adds subtotal and tax and confirms it equals the displayed TotalAmount.
	///Return the ProductNames
	/// Author: Christy Reji
	/// <summary>
	public List<String> ValidateTransactionLineCalculationsReturnProd(Map<String, String> TransactionDataLine, Map<String, String> ScannedSkuDataMap) {
		System.out.println("====== Validation Started for Transaction Page Line ======");

		List<String> ActualSkuNamesList = new ArrayList<>();

		// === Step 1: Assert that number of SKUs matches LinesCount ===
		List<String> SkuNameElements = base.GetElementTexts(
				NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5"));

		int ExpectedLinesCount = Integer.parseInt(TransactionDataLine.get("LinesCount").replaceAll("[^\\d]", ""));
		Assert.assertEquals(SkuNameElements.size(), ExpectedLinesCount,
				"Mismatch in SKU count. Actual:" + SkuNameElements.size() + ", Expected:" + ExpectedLinesCount);

		// === Step 2: Assert each SKU Name (Starting from second index) ===
		for (int i = 1; i < SkuNameElements.size(); i++) {
			String ActualSkuName = SkuNameElements.get(i).trim();
			ActualSkuNamesList.add(ActualSkuName); // collect actual SKU name

			String ExpectedSkuName = ScannedSkuDataMap.get("SKU_" + i + "_SKUName");
			Assert.assertNotNull(ExpectedSkuName, "Expected SKU name is null for SKU_" + i);

			String[] Split = ExpectedSkuName.trim().split(" - ", 2);
			String ExpectedProductNameOnly = (Split.length > 1) ? Split[1].trim() : ExpectedSkuName.trim();

			System.out.println("Asserting SKU_" + i + " Name: Expected = " + ExpectedProductNameOnly + ", Actual = " + ActualSkuName);
			Assert.assertEquals(ActualSkuName, ExpectedProductNameOnly,
					"SKU name mismatch for SKU_" + i + ". Actual:" + ActualSkuName + ", Expected:" + ExpectedProductNameOnly);
		}

		// === Step 3: Assert GrossWeight (Qty) ===
		List<String> SkuQuantityElements = base.GetElementTexts(
				NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5"));

		for (int i = 1; i < SkuQuantityElements.size(); i++) {
			String ActualQtyStr = SkuQuantityElements.get(i).trim().replaceAll("[^\\d.]", "");
			String ExpectedGrossWeightStr = ScannedSkuDataMap.get("SKU_" + i + "_GrossWeight");

			Assert.assertNotNull(ExpectedGrossWeightStr, "Expected GrossWeight is null for SKU_" + i);

			double ActualQty = Double.parseDouble(ActualQtyStr);
			double ExpectedGrossWeight = Double.parseDouble(ExpectedGrossWeightStr.trim().replaceAll("[^\\d.]", ""));

			System.out.println("Asserting GrossWeight for SKU_" + i + ": Expected = " + ExpectedGrossWeight + ", Actual = " + ActualQty);
			Assert.assertEquals(ActualQty, ExpectedGrossWeight, 1,
					"Mismatch in GrossWeight for SKU_" + i + ". Actual:" + ActualQty + ", Expected:" + ExpectedGrossWeight);
		}

		// === Step 4 & 5: Assert Total Without Tax and Subtotal ===
		List<String> TotalWithoutTaxElements = base.GetElementTexts(
				NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5"));

		double TotalWithoutTaxSum = 0.0;

		for (int i = 1; i < TotalWithoutTaxElements.size(); i++) {
			String ActualTotalStr = TotalWithoutTaxElements.get(i).trim().replaceAll("[^\\d.]", "");
			String ExpectedTotalStr = ScannedSkuDataMap.get("SKU_" + i + "_Total");

			double ActualTotal = Double.parseDouble(ActualTotalStr);
			double ExpectedTotal = Double.parseDouble(ExpectedTotalStr.trim().replaceAll("[^\\d.]", ""));

			System.out.println("Asserting TOTAL (Without Tax) for SKU_" + i + ": Expected = " + ExpectedTotal + ", Actual = " + ActualTotal);
			Assert.assertEquals(ActualTotal, ExpectedTotal, 1,
					"Mismatch in TOTAL (Without Tax) for SKU_" + i + ". Actual:" + ActualTotal + ", Expected:" + ExpectedTotal);

			TotalWithoutTaxSum += ActualTotal;
		}

		double ExpectedSubtotal = Double.parseDouble(TransactionDataLine.get("Subtotal").replaceAll("[^\\d.]", ""));

		// === Step 6: TAX = 3% of Subtotal ===
		double CalculatedTax = Math.round((ExpectedSubtotal * 0.03) * 100.0) / 100.0;
		double ExpectedTax = Double.parseDouble(TransactionDataLine.get("Tax").replaceAll("[^\\d.]", ""));

		System.out.println("Asserting calculated 3% TAX vs displayed Tax:");
		System.out.println("Expected Tax = " + ExpectedTax + ", Calculated Tax = " + CalculatedTax);

		// === Step 7: TotalAmount = Subtotal + Tax ===
		double CalculatedTotalAmount = Math.round((ExpectedSubtotal + ExpectedTax) * 100.0) / 100.0;
		double ExpectedTotalAmount = Double.parseDouble(TransactionDataLine.get("TotalAmount").replaceAll("[^\\d.]", ""));

		System.out.println("Asserting Total Amount (Subtotal + Tax) with displayed TotalAmount:");
		System.out.println("Expected TotalAmount = " + ExpectedTotalAmount + ", Calculated TotalAmount = " + CalculatedTotalAmount);

		System.out.println("====== Validation Completed for Transaction Page Line ======");

		return ActualSkuNamesList;
	}

	/// <summary>
	///Method to calculate XRF value
	/////Author-Christy Reji
	/// <summary>
	public double XRFCalculation(String Purity1, String Purity2, String Purity3) throws InterruptedException {
		double XRFPurity1 = Double.parseDouble(Purity1);
		double XRFPurity2 = Double.parseDouble(Purity2);
		double XRFPurity3 = Double.parseDouble(Purity3);

		double AverageXRFValue = (XRFPurity1 + XRFPurity2 + XRFPurity3) / 3;
		return AverageXRFValue;
	}


	/// <summary>
	/// Validate SKU Count- Compares the number of SKUs shown on the transaction page with the LinesCount from TransactionDataLine.
	/// Validate SKU Names- Ensures each displayed SKU name matches the expected name from ScannedSkuDataMap, ignoring prefix codes.
	/// Validate Gross Weight (Qty)-Confirms the displayed quantity (gross weight) for each SKU matches the expected gross weight.
	/// Validate Total Without Tax (Per SKU)-Asserts that each SKU's displayed total (without tax) matches the expected total value.
	/// Validate Subtotal (Sum of All SKU Totals)-Adds all displayed SKU totals and compares the sum to the Subtotal value from TransactionDataLine.
	/// Validate Tax (3% of Subtotal) -Calculates 3% of the subtotal and verifies it matches the displayed Tax value.
	/// Validate Total Amount (Subtotal + Tax)-Adds subtotal and tax and confirms it equals the displayed TotalAmount.
	/// Author: Hasna EK
	/// <summary>
	public Map<String, Double> ValidateBillingScreenCalculationForSalesReturnSaleAndOg(Map<String, String> TransactionDataLine,Map<String, String> ScannedSkuDataMap,ReturnDetails ReturnProdDetails,List<Double> OgQtyData,List<Double> OgCvalueData,String OgItemName,int SkuCount)
	{
		System.out.println("====== Validation Started for Transaction Page Line ======  ");
		Map<String, Double> ResultMap = new LinkedHashMap<>();
		List<String> BillScreenProdName  = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> BillScreenProdQty = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> BillScreenProdPrice = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));

		//Lines Count Validation
		int ExpectedLinesCount = Integer.parseInt(TransactionDataLine.get("LinesCount").replaceAll("[^\\d]", ""));
		Assert.assertEquals(AllProductRows.size(), ExpectedLinesCount,
				"Mismatch in SKU Linescount. Actual:" + AllProductRows.size() + ", Expected:" + ExpectedLinesCount+" in the billing screen");

		//Sales return products validation in billing screen
		double ReturnSkuTotalWithoutTaxSum = 0.0;			
		for (int i = 0; i < ReturnProdDetails.productNames.size(); i++)
		{
			String BillScreenItemName = BillScreenProdName.get(i);
			String BillScreenItemQty = BillScreenProdQty.get(i).replace("-", "").trim();
			String BillScreenItemPrice = BillScreenProdPrice.get(i).replace("-", "").trim();
			double ActualQty = Double.parseDouble(BillScreenItemQty);
			double ActualPrice = Double.parseDouble(BillScreenItemPrice.replace("\u20B9", "").replace(",", "").trim());

			String ReturnProdName = ReturnProdDetails.productNames.get(i);
			String ReturnProdQty = ReturnProdDetails.productQty.get(i);
			String ReturnProdPrice = ReturnProdDetails.productPrice.get(i);
			double ExpectedQty = Double.parseDouble(ReturnProdQty);
			double ExpectedProdPrice = Double.parseDouble(ReturnProdPrice.replace("\u20B9", "").replace(",", "").trim());
			ReturnSkuTotalWithoutTaxSum += ActualPrice;

			System.out.println("ReturnSkuTotalWithoutTaxSum"+ReturnSkuTotalWithoutTaxSum);

			asrt.assertEquals(ReturnProdName, BillScreenItemName,"Item Name mismatch: Expected "+ReturnProdName+" but got "+BillScreenItemName+" in the billing screen");
			asrt.assertEquals(ExpectedQty, ActualQty, 1, "Item quantity mismatch: Expected "+ExpectedQty+" but got "+ActualQty+" in the billing screen");
			asrt.assertEquals(ExpectedProdPrice, ActualPrice,1 ,"Item price mismatch: Expected "+ExpectedProdPrice+" but got "+ActualPrice+" in the billing screen");
		}

		// Quantity,Total Without Tax and Item Name Validation for normal SKU
		double SkuTotalWithoutTaxSum = 0.0;
		int i = ReturnProdDetails.productNames.size();
		int k = 1;
		System.out.println("ReturnProdDetails.productNames.size() = " + i);

		// Loop until matching OG item name is found or list ends
		while (i < BillScreenProdQty.size() && k<=SkuCount) {
			String CurrentSkuName = BillScreenProdName.get(i).trim();
			if (CurrentSkuName.equals(OgItemName)) {
				break; // Stop once matching OG item is found
			}

			String ExpectedSkuName = ScannedSkuDataMap.get("SKU_" + k + "_SKUName");
			Assert.assertNotNull(ExpectedSkuName, "Expected SKU name is null for SKU_" + i);

			String[] Split = ExpectedSkuName.trim().split(" - ", 2);
			String ExpectedProductName = (Split.length > 1) ? Split[1].trim() : ExpectedSkuName.trim();

			System.out.println("Asserting SKU_" + i + " Name: Expected = " + ExpectedProductName + ", Actual = " + CurrentSkuName);
			Assert.assertEquals(CurrentSkuName, ExpectedProductName,
					"SKU name mismatch for SKU_" + i + ". Actual: " + CurrentSkuName + ", Expected: " + ExpectedProductName);

			String ActualQtyStr = BillScreenProdQty.get(i).trim().replaceAll("[^\\d.]", "");
			String ExpectedGrossWeightStr = ScannedSkuDataMap.get("SKU_" + k + "_GrossWeight");
			Assert.assertNotNull(ExpectedGrossWeightStr, "Expected GrossWeight is null for SKU_" + k);

			double ActualQty = Double.parseDouble(ActualQtyStr);
			double ExpectedGrossWeight = Double.parseDouble(ExpectedGrossWeightStr.trim().replaceAll("[^\\d.]", ""));

			System.out.println("Asserting GrossWeight for SKU_" + k + ": Expected = " + ExpectedGrossWeight + ", Actual = " + ActualQty);
			Assert.assertEquals(ActualQty, ExpectedGrossWeight, 1, "Mismatch in GrossWeight for SKU_" + k);

			String ActualTotalStr = BillScreenProdPrice.get(i).trim().replaceAll("[^\\d.]", "");
			String ExpectedTotalStr = ScannedSkuDataMap.get("SKU_" + k + "_Total");

			double ActualTotal = Double.parseDouble(ActualTotalStr);
			double ExpectedTotal = Double.parseDouble(ExpectedTotalStr.trim().replaceAll("[^\\d.]", ""));

			System.out.println("Asserting TOTAL (Without Tax) for SKU_" + k + ": Expected = " + ExpectedTotal + ", Actual = " + ActualTotal);
			Assert.assertEquals(ActualTotal, ExpectedTotal, 1, "Mismatch in TOTAL (Without Tax) for SKU_" + k);

			SkuTotalWithoutTaxSum += ActualTotal;
			i++; // Move to next item
			k++;
		}

		// Quantity,Total Without Tax and Item Name Validation for OG
		int OgIndex = AllProductRows.size()-2;
		double OgTotalAmount = 0.00;
		double OgTotalQty = 0.00;
		int ExpectedIndex = 0;
		for (int j=OgIndex ; j<AllProductRows.size() && ExpectedIndex < OgQtyData.size();j++) {
			String BillScreenOgName = BillScreenProdName.get(j);
			String BillScreenOgQty = BillScreenProdQty.get(j).replace("-", "").trim();
			String BillScreenOgPrice = BillScreenProdPrice.get(j).replace("-", "").trim();
			double ActualQty = Double.parseDouble(BillScreenOgQty);
			double ActualPrice = Double.parseDouble(BillScreenOgPrice.replace("\u20B9", "").replace(",", "").trim());

			System.out.println("ActualPrice :"+ActualPrice);
			System.out.println("ActualQty :"+ActualQty);

			double ExpectedOgQty = OgQtyData.get(ExpectedIndex);
			double ExpectedOgPrice = OgCvalueData.get(ExpectedIndex);				
			OgTotalAmount += ActualPrice;
			OgTotalQty +=ActualQty;

			asrt.assertEquals(OgItemName, BillScreenOgName,"Item Name mismatch: Expected "+OgItemName+" but got "+BillScreenOgName+" in the billing screen");
			asrt.assertEquals(ExpectedOgQty, ActualQty, 1, "Item quantity mismatch: Expected "+ExpectedOgQty+" but got "+ActualQty+" in the billing screen");
			asrt.assertEquals(ExpectedOgPrice, ActualPrice,1 ,"Item price mismatch: Expected "+ExpectedOgPrice+" but got "+ActualPrice+" in the billing screen");
			ExpectedIndex++;
		}

		//Validate SubTotal value
		double TaxableValue = SkuTotalWithoutTaxSum-ReturnSkuTotalWithoutTaxSum;			
		double CalculatedSubTotal = Math.round((TaxableValue -OgTotalAmount)* 100.0) / 100.0;		
		double SubTotalBillingScreen = Double.parseDouble(TransactionDataLine.get("Subtotal").replaceAll("[^\\d.-]", ""));

		Assert.assertEquals(CalculatedSubTotal, SubTotalBillingScreen,1, "After Calculation Mismatch in total sum Actual:"+CalculatedSubTotal+" and total sum Expected:"+SubTotalBillingScreen+" in the billing screen ");

		// Validate Tax
		double CalculatedTax = Math.round((TaxableValue * 0.03) * 100.0) / 100.0;			
		double ActualTax = Double.parseDouble(TransactionDataLine.get("Tax").replaceAll("[^\\d.-]", ""));

		Assert.assertEquals(CalculatedTax, ActualTax,1, "After Calculation Mismatch in calculated Tax "+CalculatedTax+" and Expected Tax value is"+ActualTax+" in the billing screen");

		// Validate TotalAmount = SubTotal + Tax 
		double CalculatedTotalAmount = Math.round((SubTotalBillingScreen + CalculatedTax) * 100.0) / 100.0;
		double ExpectedTotalAmount = Double.parseDouble(TransactionDataLine.get("TotalAmount").replaceAll("[^\\d.-]", ""));

		Assert.assertEquals(CalculatedTotalAmount, ExpectedTotalAmount,1, "After Calculation Mismatch in Total Amount Calculated "+CalculatedTotalAmount+" and Expected Total Amount "+ExpectedTotalAmount+" in the billing screen");		

		System.out.println("Validation Summary:");
		System.out.println("TaxableValue: " + TaxableValue);
		System.out.println("CalculatedSubTotal: " + CalculatedSubTotal);
		System.out.println("Displayed SubTotal: " + SubTotalBillingScreen);
		System.out.println("CalculatedTax: " + CalculatedTax);
		System.out.println("Displayed Tax: " + ActualTax);
		System.out.println("CalculatedTotalAmount: " + CalculatedTotalAmount);
		System.out.println("Displayed TotalAmount: " + ExpectedTotalAmount);

		ResultMap.put("TaxableValue", TaxableValue);
		ResultMap.put("OgTotalQty", OgTotalQty);
		ResultMap.put("ReturnSkuTotalWithoutTaxSum", ReturnSkuTotalWithoutTaxSum);
		ResultMap.put("OgTotalAmount", OgTotalAmount);

		return ResultMap;
	}
	/// <summary>
	/// Validate SKU Count,SKU Names, Gross Weight (Qty), Total Without Tax (Per SKU),Subtotal (Sum of All SKU Totals),Tax, Total Amount (Subtotal + Tax)
	/// Author: Hasna EK
	/// <summary>
	public Map<String, String> ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(Map<String, String> ScannedSkuDataMap, String CstProdQty, String AdjustmentAmount,String ExpectedAdvanceName)
	{
		Map<String, String> ResultMap = new LinkedHashMap<>();

		List<String> BillScreenProdName  = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> BillScreenProdQty = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> BillScreenProdPrice = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));

		//Lines Count Validation
		String LinesCountBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4"));

		int ExpectedLinesCount = Integer.parseInt(LinesCountBillingPge.replaceAll("[^\\d]", ""));
		Assert.assertEquals(AllProductRows.size(), ExpectedLinesCount,
				"Mismatch in SKU Linescount. Actual:" + AllProductRows.size() + ", Expected:" + ExpectedLinesCount+" in the billing screen");


		double SkuTotalWithoutTaxSum = 0.00;
		double TotalGrossWt = 0.00;
		for (int i = 1;i<AllProductRows.size();i++) {
			String ExpectedSkuName = ScannedSkuDataMap.get("SKU_" + i + "_SKUName");
			Assert.assertNotNull(ExpectedSkuName, "Expected SKU name is null for SKU_" + i);

			String[] Split = ExpectedSkuName.trim().split(" - ", 2);
			String ExpectedProductName = (Split.length > 1) ? Split[1].trim() : ExpectedSkuName.trim();
			String CurrentSkuName = BillScreenProdName.get(i).trim();

			Assert.assertEquals(CurrentSkuName, ExpectedProductName,
					"Mismatch in SKU name, Actual: " + CurrentSkuName + ", Expected: " + ExpectedProductName+" in the billing screen.");

			String ActualGrossWt = BillScreenProdQty.get(i).trim().replaceAll("[^\\d.]", "");
			String ExpectedGrossWeightFromDataMap = ScannedSkuDataMap.get("SKU_" + i + "_GrossWeight");
			Assert.assertNotNull(ExpectedGrossWeightFromDataMap, "Expected GrossWeight is null for SKU_" + i);

			double ActualGrossWtBillingPge = Double.parseDouble(ActualGrossWt);
			double ExpectedGrossWtBillingPge = Double.parseDouble(ExpectedGrossWeightFromDataMap.trim().replaceAll("[^\\d.]", ""));

			Assert.assertEquals(ActualGrossWtBillingPge, ExpectedGrossWtBillingPge, 1, "Mismatch in GrossWeight, Expected "+ExpectedGrossWtBillingPge+" but got "+ActualGrossWtBillingPge+" in the billing screen");

			String ActualTotalPrice = BillScreenProdPrice.get(i).trim().replaceAll("[^\\d.]", "");
			String ExpectedTotalPrice = ScannedSkuDataMap.get("SKU_" + i + "_Total");

			double ActualTotal = Double.parseDouble(ActualTotalPrice);
			double ExpectedTotal = Double.parseDouble(ExpectedTotalPrice.trim().replaceAll("[^\\d.]", ""));

			Assert.assertEquals(ActualTotal, ExpectedTotal, 1, "Mismatch in TOTAL (Without Tax), Expected "+ExpectedTotal+" but got "+ActualTotal+" in the billing screen");
			TotalGrossWt +=ExpectedGrossWtBillingPge;
			SkuTotalWithoutTaxSum += ActualTotal;
		}
		TotalGrossWt = Math.round(TotalGrossWt* 1000.0) / 1000.0;
		String TotalGrossWtSku = String.valueOf(TotalGrossWt);
		
		// Quantity,Total Without Tax and Item Name Validation for Offer advance
		String BillScreenDepositName = BillScreenProdName.get(0);
		String BillScreenDepositQty = BillScreenProdQty.get(0).replace("-", "").trim();
		String BillScreenDepositPrice = BillScreenProdPrice.get(0).replace("-", "").trim();
		double DepositQtyBillingPge = Double.parseDouble(BillScreenDepositQty);
		double DepositPriceBillingPge = Double.parseDouble(BillScreenDepositPrice.replaceAll("[^\\d.]", ""));

		System.out.println("ActualPrice of advance :"+DepositPriceBillingPge);
		System.out.println("ActualQty of advance :"+DepositQtyBillingPge);

		double ExpectedDepositQty = 	 Double.parseDouble(CstProdQty);
		double ExpectedDepositPrice = Double.parseDouble(AdjustmentAmount);				

		
		asrt.assertEquals(BillScreenDepositName, ExpectedAdvanceName,"Item Name mismatch: Expected "+ExpectedAdvanceName+" but got "+BillScreenDepositName+" in the billing screen");
		asrt.assertEquals(DepositQtyBillingPge, ExpectedDepositQty, 1, "Item quantity mismatch: Expected "+ExpectedDepositQty+" but got "+DepositQtyBillingPge+" in the billing screen");
		asrt.assertEquals(DepositPriceBillingPge, ExpectedDepositPrice,1 ,"Item price mismatch: Expected "+ExpectedDepositPrice+" but got "+DepositPriceBillingPge+" in the billing screen");

		//Validate SubTotal value
		double TaxableValue = Math.round(SkuTotalWithoutTaxSum* 100.0) / 100.0;
		String CalculatedTaxableValue = String.valueOf(TaxableValue);

		double CalculatedSubTotal = Math.round((TaxableValue - DepositPriceBillingPge)* 100.0) / 100.0;
		String SubTotalBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"));
		double SubTotalBillingScreen = Double.parseDouble(SubTotalBillingPge.replaceAll("[^\\d.-]", ""));

		Assert.assertEquals(SubTotalBillingScreen, CalculatedSubTotal,1, "Mismatch in Subtotal value, Expected:"+CalculatedSubTotal+" but got :"+SubTotalBillingScreen+" in the billing screen ");

		// Validate Tax
		double CalculatedTax = Math.round((TaxableValue * 0.03) * 100.0) / 100.0;	
		String TaxBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4"));
		double ActualTax = Double.parseDouble(TaxBillingPge.replaceAll("[^\\d.-]", ""));

		Assert.assertEquals(ActualTax,CalculatedTax ,1, "After Calculation Mismatch in calculated Tax, Expected"+CalculatedTax+" but got "+ActualTax+" in the billing screen");

		// Validate TotalAmount = SubTotal + Tax 
		double CalculatedTotalAmount = Math.round((SubTotalBillingScreen + CalculatedTax) * 100.0) / 100.0;
		String TotalAmountBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4"));
		double ActualTotalAmount = Double.parseDouble(TotalAmountBillingPge.replaceAll("[^\\d.-]", ""));

		Assert.assertEquals(ActualTotalAmount,CalculatedTotalAmount ,1, "After Calculation Mismatch in Total Amount, Expected "+CalculatedTotalAmount+" but got "+ActualTotalAmount+" in the billing screen");		

		String AmountDue = base.GetText(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		double ActualAmountDue = Double.parseDouble(AmountDue.replaceAll("[^\\d.-]", ""));
		Assert.assertEquals(ActualAmountDue,CalculatedTotalAmount ,1, "After Calculation Mismatch in AmountDue, Expected "+CalculatedTotalAmount+" but got "+ActualAmountDue+" in the billing screen");		

		double CalculatedInvoiceTotal        = TaxableValue + ActualTax ;
		String InvoiceTotalBillingPge = String.valueOf(CalculatedInvoiceTotal);
		
		ResultMap.put("TaxableValue", CalculatedTaxableValue);
		ResultMap.put("AmountDue", AmountDue);
		ResultMap.put("Tax", TaxBillingPge);
		ResultMap.put("InvoiceTotal", InvoiceTotalBillingPge);
		ResultMap.put("LinesCount", LinesCountBillingPge);
		ResultMap.put("TotalGrossWtSku", TotalGrossWtSku);
		return ResultMap;

	}
}

