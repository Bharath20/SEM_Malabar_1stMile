package testPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj;
import objectRepository.AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_Obj;
import objectRepository.CustomerCreation_Obj;
import objectRepository.GPPSettlementAverageRateSingleSkuMultipleSku_Obj;
import objectRepository.GppOpeningAndCollectionMultiplePlans_Obj;
import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleDiamondandPlatinum_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import objectRepository.OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj;
import testData.AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData;
import testData.CommonData;
import testData.OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.AppUtilities.SKUResult;
import utilPack.BasePge;
import utilPack.ErpUtilities;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;

public class AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	MathUtilities mathUtils;
	PdfUtilities pdfUtils;
	ErpUtilities erpUtils;

	Login login = new Login(driver);
	LoginPage_Obj LoginPageObj = new LoginPage_Obj();
	NormalSaleGoldandSilver_Obj NormalSaleGoldandSilverObj = new NormalSaleGoldandSilver_Obj();
	OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj = new OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj();
	AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_Obj AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj = new AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_Obj();
	OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData = new OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData();
	AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData = new AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj = new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	CustomerCreation_Obj CustomerCreationObj = new CustomerCreation_Obj();
	AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj = new AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj();
	GppOpeningAndCollectionMultiplePlans_Obj GppOpeningAndCollectionMultiplePlansObj = new GppOpeningAndCollectionMultiplePlans_Obj();
	GPPSettlementAverageRateSingleSkuMultipleSku_Obj GPPSettlementAverageRateSingleSkuMultipleSkuObj = new GPPSettlementAverageRateSingleSkuMultipleSku_Obj();
	NormalSaleDiamondandPlatinum_Obj NormalSaleDiamondandPlatinumObj = new NormalSaleDiamondandPlatinum_Obj();

	public AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos(WebDriver driver) {

		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base);
		pdfUtils = new PdfUtilities(base);
		mathUtils = new MathUtilities(base);
		erpUtils = new ErpUtilities(base);

	}

	// <summary>
	// Test Case Title : OG with partial advance settlement
	// Automation ID : TC97
	// Author : Nandagopan
	// </summary>

	public void TC97_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		// Step 2. Click on Transaction button
		appUtils.HamBurgerButtonClick("iconShop");

		// Step 3. Select customer
		// Step 4. Click on add to estimation button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_CustomerId,
				"AddCustomerForSalesEstimationCommand");
		// Note-For Checking Average Rate,Calculations are done.For Getting Board
		// rate,scanning sku

		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> PurityOfItems = new ArrayList();
		List<Double> AllRates = new ArrayList<>();
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SaleProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_TransferTypeSKU,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_FromCounterSKU,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_MetalTypeSKU);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation(
				"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); // go to
		// Transaction
		for (int i = 0; i < SkuListGold.size(); i++)

		{
			String CurrentGoldSku = SkuListGold.get(i);

			appUtils.SKUIngridentTableValues(CurrentGoldSku.toString(), (i + 1), SkuDetails);

			PurityOfItems.add(SkuDetails.get("SKU_" + (i + 1) + "_Purity"));

			for (int ItemIndex = 1;; ItemIndex++) {
				String Rate = "SKU" + (i + 1) + "_ITEM" + ItemIndex + "_RATE";

				if (SkuDetails.containsKey(Rate))

				{
					try

					{
						double SkuRate = Double.parseDouble(SkuDetails.get(Rate));

						AllRates.add(SkuRate);
					} catch (NumberFormatException e) {
						System.out.println("Rate not fetched");
					}
				} else {
					break;
				}
			}

			SkuDetails.put("SKU_" + (i + 1) + "_AllRates", AllRates.toString());
		}
		// For conversion of Rate

		String ItemPurity = PurityOfItems.get(0);
		double ExchangeRate = AllRates.get(0);
		double ExpectedGoldRate22k = 0;
		double ExpectedGoldRate18k = 0;
		if (ItemPurity.equalsIgnoreCase("18k")) {
			double ConvertedGoldRate = (ExchangeRate * 22) / 18;

			ExpectedGoldRate22k = Math.round(ConvertedGoldRate);

			ExpectedGoldRate18k = ExchangeRate;
		} else {
			ExpectedGoldRate22k = Math.round(ExchangeRate);
			ExpectedGoldRate18k = (ExchangeRate * 18) / 22;
		}
		System.out.print(ExpectedGoldRate22k);
		appUtils.VoidTransaction();

		// Seraching Customer with ID
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_CustomerId,
				"AddCustomerForSalesEstimationCommand");
		// Step 5. Click on customer adjustment button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// Step 6. click on normal advance adjustment button
		Wait.until(ExpectedConditions
				.elementToBeClickable(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Normal Advance Adjustment")))
		.click();

		double AdvanceBalance = 0;
		String AdvanceBalanceAmount = "";
		String MetalRateFetch = "";

		// Step 7. Select advance and Enter partial amount
		List<WebElement> TransactionNo = base
				.GetElement(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_AdvanceAdjustTrnsNo("h4 ellipsis cell", "Transaction"));
		for (int i = 0; i <= TransactionNo.size(); i++) {
			TransactionNo.get(i).click();
			AdvanceBalance = Double.parseDouble(
					base.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
							.Ele_BalanceAmountInput("Balance amount")));
			if (AdvanceBalance > 100) {
				AdvanceBalanceAmount = base
						.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
								.Ele_AdvanceAdjustBalanceAmt("option", "Balance amount", "gridcell", i));
				MetalRateFetch = base
						.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
								.Ele_AdvanceAdjustMetalRate("option", "Metal Rate", "gridcell", i));
				break;
			}
		}

		String BalanceAmtInAdvanceAdj = base
				.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Balance amount"));
		String DefaultAdjAmt = base
				.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Adjustment amount"));
		double Adjustment = AdvanceBalance / 4;
		double Quantity22k = Adjustment / Double.parseDouble(MetalRateFetch);

		double MetalRate18k = (Double.parseDouble(MetalRateFetch) * 18) / 22;
		double Quantity18k = Adjustment / MetalRate18k;

		System.out.println(Adjustment);
		System.out.println(AdvanceBalanceAmount);
		base.clearData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAmountInput("Adjustment amount"));
		base.setData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAmountInput("Adjustment amount"), Double.toString(Adjustment));

		// Step 8. Click on cart button
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		asrt.assertEquals(BalanceAmtInAdvanceAdj, DefaultAdjAmt,
				"Mismatch in balance amount and adjusted amount displayed in advance adjustment page");

		// Step 9:Scan a SKU and click add to cart button
		// Note:Check Average Rate
		// Expected Result: Check calculation
		SKUResult SkuOGSale = appUtils.GetOGSaleSku(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_MetalType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SaleQty,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SkuCount);

		List<String> SkuList = SkuOGSale.PcsRateSKU;
		System.out.println("SkuList" + SkuList);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> ScannedSkuDataMap = new HashMap();
		appUtils.SKUIngridentTableValues(SkuList.get(0),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SkuCount,
				ScannedSkuDataMap);

		String ExpectedGrossValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SkuCount
				+ "_GrossWeight");
		String ExpectedNetValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SkuCount
				+ "_TotalRGWeight");
		String ExpectedItemName = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SkuCount
				+ "_ItemName");
		String TotalForEachItem = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SkuCount
				+ "_Total");
		String RateKey = "SKU"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SkuCount
				+ "_ITEM"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SkuCount
				+ "_RATE";
		String BoardRateFromSkuTble = ScannedSkuDataMap.get(RateKey);
		Double SkuBoardRate = Double.parseDouble(BoardRateFromSkuTble);
		String Purity = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SkuCount
				+ "_Purity");
		double BoardRate22k = 0;

		if (Purity.equalsIgnoreCase("22k")) {
			if (Double.parseDouble(ExpectedNetValue) > Quantity22k) {
				double Remainingwgt = (Double.parseDouble(ExpectedNetValue) - Quantity22k);
				double TotalAmount = (Quantity22k * Double.parseDouble(MetalRateFetch))
						+ (Remainingwgt * ExpectedGoldRate22k);
				double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0) / 100.0;
				asrt.assertEquals(SkuBoardRate, AverageRate, 1.0, "Mismatch  In Average Rate,Expected" + AverageRate
						+ "but got" + SkuBoardRate + "in return page");

				System.out.print("Average rate of the SKU is:" + AverageRate);
			} else {
				asrt.assertEquals(SkuBoardRate, Double.parseDouble(MetalRateFetch), 1.0,
						"Mismatch  In Average Rate,Expected" + MetalRateFetch + "but got" + SkuBoardRate
						+ "in return page");
			}
		}

		if (Purity.equalsIgnoreCase("18k")) {
			if (Double.parseDouble(ExpectedNetValue) > Quantity18k) {
				double Remainingwgt = (Double.parseDouble(ExpectedNetValue) - Quantity18k);
				double TotalAmount = (Quantity18k * MetalRate18k) + (Remainingwgt * ExpectedGoldRate18k);
				double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0) / 100.0;
				asrt.assertEquals(SkuBoardRate, AverageRate, 1.0, "Mismatch  In Average Rate,Expected" + AverageRate
						+ "but got" + SkuBoardRate + "in return page");

				System.out.print("Average rate of the SKU is:" + AverageRate);
			} else {
				asrt.assertEquals(SkuBoardRate, MetalRate18k, 1.0, "Mismatch  In Average Rate,Expected" + MetalRate18k
						+ "but got" + SkuBoardRate + "in return page");
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// Step 10: Select OG
		// Step 11:Click on OG own
		// Step 12: Select configuration(purity 22K)
		// Step 13: Click on Add item button

		// Step 14:Select QC person
		// Step 15:Select Smith person
		// Step 16:Enter piece value
		// Step 17:Enter gross weight
		// Step 18:click on calculate button
		// Step 19:Click on Add to cart button

		appUtils.PurchaseOldGold(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_Configuration,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_PurchaseOrExchange,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_QCPerson,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_SmithPerson,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_GrossPieces,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_GrossWeight);
		Map<String, Double> Og22kData = mathUtils.ValidateOldGoldItemDetailsData(ExpectedGoldRate22k,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_Configuration);
		Double OgQty22k = Og22kData.get("OgQty");
		Double OgCvalue22k = Og22kData.get("OgCvalue");
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldandSilverObj
				.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand", "win-commandicon"));
		WebDriverWait Wait1 = new WebDriverWait(driver, Duration.ofSeconds(8));
		By SalesRepSelectAlert = NormalSaleGoldandSilverObj.Ele_Name("salesRepresentativesButtonGrid");
		By SalesRep = NormalSaleGoldandSilverObj.Ele_SKUSalesRep("margin0", "1");
		try {
			WebElement SalesRepAlertModal = Wait1
					.until(ExpectedConditions.visibilityOfElementLocated(SalesRepSelectAlert));
			WebElement SelectSalesRep = Wait1.until(ExpectedConditions.elementToBeClickable(SalesRep));
			SelectSalesRep.click();

		} catch (TimeoutException e) {
			WebDriverWait SalesRepWait = new WebDriverWait(driver, Duration.ofSeconds(2));
			SalesRepWait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Edt_AlertText(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_CustomerId)));
		}

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = BillingScrnTableData.get("Subtotal");
		String TotalGrossWeight = BillingScrnTableData.get("GrossWeight");
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Tax = BillingScrnTableData.get("Tax");
		String TotalAmount = BillingScrnTableData.get("TotalAmount");
		String NetTotal = BillingScrnTableData.get("NetTotal");
		String LinesCount = BillingScrnTableData.get("LinesCount");

		int LinesCountBilling = Integer.parseInt(LinesCount);
		double SubTotalBilling = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double TaxBilling = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));
		double TotalAmountBilling = Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> TextItemNamesBilling = new ArrayList<>();
		List<String> TextItemQtyBilling = new ArrayList<>();

		int ExpectedLinesCount = AllProductRows.size();
		asrt.assertEquals(ExpectedLinesCount, LinesCountBilling, "Mismatch in Lines count,expected "
				+ ExpectedLinesCount + " but got " + LinesCountBilling + " in the billing screen");

		String SkuItemRate = ItemRateBilling.get(1).getText().trim().replaceAll("[^\\d.\\-]", "");
		double FirstItemRate = Double.parseDouble(SkuItemRate);
		double ExpectedSubTotal = 0.0;
		ExpectedSubTotal = FirstItemRate - (OgCvalue22k + Adjustment);

		double ExpectedTaxAmnt = FirstItemRate * 3 / 100;
		double ActualTotalAmount = ExpectedSubTotal + ExpectedTaxAmnt;

		asrt.assertEquals(ExpectedTaxAmnt, TaxBilling, 1.0, "Mismatch in Tax value, expected" + ExpectedTaxAmnt
				+ " but got " + TaxBilling + " in the billing screen");

		for (int n = 0; n < ItemNamesBilling.size(); n++) {
			TextItemNamesBilling.add(ItemNamesBilling.get(n).getText().trim());
		}
		Thread.sleep(3000);
		String ExpectedFullItemName = ExpectedItemName.trim();
		String ExpectedTrimmedItemName = ExpectedFullItemName.contains("-")
				? ExpectedFullItemName.substring(ExpectedFullItemName.indexOf("-") + 2)
						: ExpectedFullItemName;
		String ActualItemName = ItemNamesBilling.get(1).getText().trim();

		asrt.assertEquals(ExpectedTrimmedItemName, ActualItemName, "Mismatch in item name, expected "
				+ ExpectedTrimmedItemName + "but got " + ActualItemName + "  in the billing screen.");

		for (int i = 1; i < TextItemNamesBilling.size(); i++) {
			String ActualItemOg = TextItemNamesBilling.get(2).trim();
			asrt.assertEquals(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_OgItemName,
					ActualItemOg,
					"Mismatch in OG item name, expected "
							+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_OgItemName
							+ " but got " + ActualItemOg + " in the billing screen.");

		}

		for (int i = 1; i < 1; i++) {
			String ExpectedTotalStr = ScannedSkuDataMap.get("SKU_" + (i + 1) + "_Total");
			double ExpectedTotal = Double.parseDouble(ExpectedTotalStr.replaceAll("[^\\d.]", ""));
			String ActualTotalStr = ItemRateBilling.get(i).getText().trim();
			double ActualTotal = Double.parseDouble(ActualTotalStr.replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpectedTotal, ActualTotal, 1.0, "Mismatch in total amounts (without tax),expected "
					+ ExpectedTotal + " but got " + ActualTotal + " in the billing screen.");
		}

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		String TextItemRateBilling = ItemRateBilling.get(2).getText().replace("-", "").trim();
		Double ActualRateOg22k = Double.parseDouble(TextItemRateBilling.replace("₹", "").replace(",", "").trim());

		asrt.assertEquals(OgCvalue22k, ActualRateOg22k, 1.0, "Mismatch in total amount for 22K OG, expected "
				+ OgCvalue22k + " but got " + ActualRateOg22k + " in Billing screen");

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		String ExpectedGrossWt = ExpectedGrossValue.replace("-", "").trim();
		Double ExpectedQty = Double.parseDouble(ExpectedGrossWt);
		String BillingQtySku = ItemQtyBilling.get(1).getText().replace("-", "").trim();
		Double ActualGrossWtSku = Double.parseDouble(BillingQtySku.replace("₹", "").replace(",", "").trim());
		asrt.assertEquals(ExpectedQty, ActualGrossWtSku, 1.0, "Mismatch in Qty value,expected " + ExpectedQty
				+ " but got " + ActualGrossWtSku + " in the Billing screen");

		String BillingQtyOg22k = ItemQtyBilling.get(2).getText().replace("-", "").trim();
		Double ActualQtyOg22k = Double.parseDouble(BillingQtyOg22k.replace("₹", "").replace(",", "").trim());
		asrt.assertEquals(OgQty22k, ActualQtyOg22k, 1, "Mismatch in Qty value for 22k OG ,expected " + OgQty22k
				+ " but got " + ActualQtyOg22k + " in the Billing screen");
		asrt.assertEquals(ExpectedSubTotal, SubTotalBilling, 1.0, "Mismatch in Subtotal value, expected "
				+ ExpectedSubTotal + " but got " + SubTotalBilling + "in the Billing screen");
		asrt.assertEquals(TotalAmountBilling, ActualTotalAmount, 1.0, "Mismatch in Total amount value, expected "
				+ TotalAmountBilling + " but got " + ActualTotalAmount + " in the Billing screen");
		// Step 20: Save to estimate
		// Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Back"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));

		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(4000);
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC97_ProformaInvoice");
		Thread.sleep(4000);
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FirstInvoice + "";
		String TaxableValue = SkuItemRate;
		String InvoiceTotal = Double.toString(Double.parseDouble(TaxableValue.replaceAll("[^\\d.]", "").trim())
				+ Double.parseDouble(Tax.replaceAll("[^\\d.]", "").trim()) - (OgCvalue22k));

		String TotalAdjustment = Double.toString(Adjustment);
		String Gst = Tax;
		String TotalAmnt = "";
		Thread.sleep(3000);
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProformaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, TotalAdjustment, NetTotal, TotalAmnt);

		// Step 21:Recall estimate from cashier
		// Expected Result:Verify whether the recalled item is the same as the item in
		// the cart.
		// Note:Cashier Screen after recall, *No of Product lines, *Displayed Item Name,
		// *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal,
		// *TAX, *Total Amount

		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2", "text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		// Step 22:Select estimate and click on Recall estimation button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		appUtils.ValidateBillingAndRecallScreenData(EstmnNumber, LinesCount, TextItemNamesBilling, TextItemQtyBilling,
				SubTotal, Tax, NetTotal);

		// Step 23: Select transaction type as Exchange
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_TransactionType1);

		// Step 24: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 25: Choose any Sales Representative
		base.setZoom(driver, 60);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		// Step 26: Click on the Amount 0.00
		// Expected Result: Check Exchange invoice details
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		if (base.isElementPresent(driver, NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) {
			try {
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} catch (Exception e) {
			}
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> PurchaseBillData = pdfUtils.OGPdfValidation(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_PaymentDetails);
		String ExchngeAdjustment = PurchaseBillData.get("PaymentDetails");

		// Step 27: Again recall estimate from cashier screen
		// Step 28:Select estimate and click on Recall estimation button

		base.excuteJsClick(
				NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2", "text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Step 29: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_TransactionType2);

		// Step 30: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 31: Choose a Sales Representative
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC = base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		Map<String, String> SkuBillingScrnTbleData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SkuNetTotal = SkuBillingScrnTbleData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTbleData.get("LinesCount");

		// Step 32: Click on amount
		// Select payment method(cash/card)
		appUtils.PaymentAfterRecallEstimate(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_PaymentMethod);

		// Step 33: Post exchange invoice(sale)
		// Expected Result: Check final invoice details,* save receipt id for future
		// reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC97_SaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";

		String GrossAmount = SkuItemRate;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String OGAdjustment = ExchngeAdjustment;
		String TotalDiamond = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_TotalDiamond;
		String PaymentHDFCCard = PaymentAmountHDFC;
		String SalesPdfPath = SaleInvoicePath;
		String AdvanceAmountFinalInvoice = Double.toString(Adjustment);
		TotalNetWeight = ExpectedGrossWt;
		TotalGrossWeight = ExpectedGrossWt;
		String NetValue = SkuNetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(SalesPdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, OGAdjustment, AdvanceAmountFinalInvoice, SkuList, ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:-" + PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	// <summary>
	// Test Case Title : Normal advance partial settlement
	// Automation ID : TC95
	// Author : Nivya Ramesan
	// </summary>

	public void TC95_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		// Step 2. Click on Transaction button
		appUtils.HamBurgerButtonClick("iconShop");

		// Step 3. Select customer
		// Step 4. Click on add to estimation button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_CustomerId,
				"AddCustomerForSalesEstimationCommand");
		// Note - Checking Average Rate,Calculations are done.For Getting Board
		// rate,scanning sku

		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> PurityOfItems = new ArrayList();
		List<Double> AllRates = new ArrayList<>();
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SaleProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_TransferTypeSKU,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_FromCounterSKU,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_MetalTypeSKU);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation(
				"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); // go to
		// Transaction
		for (int i = 0; i < SkuListGold.size(); i++)

		{
			String CurrentGoldSku = SkuListGold.get(i);

			appUtils.SKUIngridentTableValues(CurrentGoldSku.toString(), (i + 1), SkuDetails);

			PurityOfItems.add(SkuDetails.get("SKU_" + (i + 1) + "_Purity"));

			for (int ItemIndex = 1;; ItemIndex++) {
				String Rate = "SKU" + (i + 1) + "_ITEM" + ItemIndex + "_RATE";

				if (SkuDetails.containsKey(Rate))

				{
					try

					{
						double SkuRate = Double.parseDouble(SkuDetails.get(Rate));

						AllRates.add(SkuRate);
					} catch (NumberFormatException e) {
						System.out.println("Rate not fetched");
					}
				} else {
					break;
				}
			}

			SkuDetails.put("SKU_" + (i + 1) + "_AllRates", AllRates.toString());
		}
		// For conversion of Rate

		String ItemPurity = PurityOfItems.get(0);
		double ExchangeRate = AllRates.get(0);
		double ExpectedGoldRate22k = 0;
		double ExpectedGoldRate18k = 0;
		if (ItemPurity.equalsIgnoreCase("18k")) {
			double ConvertedGoldRate = (ExchangeRate * 22) / 18;

			ExpectedGoldRate22k = Math.round(ConvertedGoldRate);

			ExpectedGoldRate18k = ExchangeRate;
		} else {
			ExpectedGoldRate22k = Math.round(ExchangeRate);
			ExpectedGoldRate18k = (ExchangeRate * 18) / 22;
		}
		System.out.print(ExpectedGoldRate22k);
		appUtils.VoidTransaction();

		// Seraching Customer with ID
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_CustomerId,
				"AddCustomerForSalesEstimationCommand");
		// Step 5. Click on customer adjustment button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// Step 6. click on normal advance adjustment button
		Wait.until(ExpectedConditions
				.elementToBeClickable(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Normal Advance Adjustment")))
		.click();

		double AdvanceBalance = 0;
		String AdvanceBalanceAmount = "";
		String MetalRateFetch = "";

		// Step 7. Select advance and Enter partial amount
		List<WebElement> TransactionNo = base
				.GetElement(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_AdvanceAdjustTrnsNo("h4 ellipsis cell", "Transaction"));
		for (int i = 0; i <= TransactionNo.size(); i++) {
			TransactionNo.get(i).click();
			AdvanceBalance = Double.parseDouble(
					base.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
							.Ele_BalanceAmountInput("Balance amount")));
			if (AdvanceBalance > 100) {
				AdvanceBalanceAmount = base
						.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
								.Ele_AdvanceAdjustBalanceAmt("option", "Balance amount", "gridcell", i));
				MetalRateFetch = base
						.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
								.Ele_AdvanceAdjustMetalRate("option", "Metal Rate", "gridcell", i));
				break;
			}
		}

		String BalanceAmtInAdvanceAdj = base
				.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Balance amount"));
		String DefaultAdjAmt = base
				.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Adjustment amount"));
		double Adjustment = AdvanceBalance * 0.1;
		double Quantity = Adjustment / Double.parseDouble(MetalRateFetch);

		System.out.println(Adjustment);
		System.out.println(AdvanceBalanceAmount);
		base.clearData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAmountInput("Adjustment amount"));
		base.setData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAmountInput("Adjustment amount"), Double.toString(Adjustment));

		// Step 8. Click on cart button
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		asrt.assertEquals(BalanceAmtInAdvanceAdj, DefaultAdjAmt,
				"Mismatch in balance amount and adjusted amount displayed in advance adjustment page");

		// Step 9:Scan a SKU and click add to cart button
		// Note:Check Average Rate
		// Expected Result: Check calculation
		SKUResult SkuOGSale = appUtils.GetOGSaleSku(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_MetalType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SaleQty,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount);

		List<String> SkuList = SkuOGSale.PcsRateSKU;
		System.out.println("SkuList" + SkuList);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> ScannedSkuDataMap = new HashMap();
		appUtils.SKUIngridentTableValues(SkuList.get(0),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount,
				ScannedSkuDataMap);

		String ExpectedGrossValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_GrossWeight");
		String ExpectedNetValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_TotalRGWeight");
		String ExpectedItemName = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_ItemName");
		String TotalForEachItem = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_Total");
		String RateKey = "SKU"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_ITEM"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_RATE";
		String BoardRateFromSkuTble = ScannedSkuDataMap.get(RateKey);
		Double SkuBoardRate = Double.parseDouble(BoardRateFromSkuTble);
		String Purity = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_Purity");
		double BoardRate22k = 0;
		if (Purity.equalsIgnoreCase("18k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 18;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("14k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 14;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("22k")) {
			BoardRate22k = SkuBoardRate;

		}
		if (Double.parseDouble(ExpectedNetValue) > Quantity) {
			double Remainingwgt = (Double.parseDouble(ExpectedNetValue) - Quantity);
			double TotalAmount = (Quantity * Double.parseDouble(MetalRateFetch)) + (Remainingwgt * ExpectedGoldRate22k);
			double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0) / 100.0;
			asrt.assertEquals(SkuBoardRate, AverageRate, 1.0,
					"Mismatch  In Average Rate,Expected" + AverageRate + "but got" + SkuBoardRate + "in return page");

			System.out.print("Average rate of the SKU is:" + AverageRate);

		} else {
			asrt.assertEquals(SkuBoardRate, Double.parseDouble(MetalRateFetch), 1.0,
					"Mismatch  In Average Rate,Expected" + MetalRateFetch + "but got" + SkuBoardRate
					+ "in return page");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = BillingScrnTableData.get("Subtotal");
		String TotalGrossWeight = BillingScrnTableData.get("GrossWeight");
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Tax = BillingScrnTableData.get("Tax");
		String TotalAmount = BillingScrnTableData.get("TotalAmount");
		String NetTotal = BillingScrnTableData.get("NetTotal");
		String LinesCount = BillingScrnTableData.get("LinesCount");

		int LinesCountBilling = Integer.parseInt(LinesCount);
		double SubTotalBilling = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double TaxBilling = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));
		double TotalAmountBilling = Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> TextItemNamesBilling = new ArrayList<>();
		List<String> TextItemQtyBilling = new ArrayList<>();

		int ExpectedLinesCount = AllProductRows.size();
		asrt.assertEquals(ExpectedLinesCount, LinesCountBilling, "Mismatch in Lines count,expected "
				+ ExpectedLinesCount + " but got " + LinesCountBilling + " in the billing screen");

		String SkuItemRate = ItemRateBilling.get(1).getText().trim().replaceAll("[^\\d.\\-]", "");
		double FirstItemRate = Double.parseDouble(SkuItemRate);
		double ExpectedSubTotal = 0.0;
		ExpectedSubTotal = FirstItemRate - (Adjustment);

		double ExpectedTaxAmnt = FirstItemRate * 3 / 100;
		double ActualTotalAmount = ExpectedSubTotal + ExpectedTaxAmnt;

		asrt.assertEquals(ExpectedTaxAmnt, TaxBilling, 1.0, "Mismatch in Tax value, expected" + ExpectedTaxAmnt
				+ " but got " + TaxBilling + " in the billing screen");

		for (int n = 0; n < ItemNamesBilling.size(); n++) {
			TextItemNamesBilling.add(ItemNamesBilling.get(n).getText().trim());
		}
		Thread.sleep(3000);
		String ExpectedFullItemName = ExpectedItemName.trim();
		String ExpectedTrimmedItemName = ExpectedFullItemName.contains("-")
				? ExpectedFullItemName.substring(ExpectedFullItemName.indexOf("-") + 2)
						: ExpectedFullItemName;
		String ActualItemName = ItemNamesBilling.get(1).getText().trim();

		asrt.assertEquals(ExpectedTrimmedItemName, ActualItemName, "Mismatch in item name, expected "
				+ ExpectedTrimmedItemName + "but got " + ActualItemName + "  in the billing screen.");

		String ExpectedTotalStr = ScannedSkuDataMap.get("SKU_" + (1) + "_Total");
		double ExpectedTotal = Double.parseDouble(TotalForEachItem.replaceAll("[^\\d.]", ""));
		String ActualTotalStr = ItemRateBilling.get(1).getText().trim();
		double ActualTotal = Double.parseDouble(ActualTotalStr.replaceAll("[^\\d.]", ""));
		System.out.println("ExpectedTotal" + TotalForEachItem);
		System.out.println("ActualTotal" + ActualTotal);

		asrt.assertEquals(ExpectedTotal, ActualTotal, 1.0, "Mismatch in total amounts (without tax),expected "
				+ ExpectedTotal + " but got " + ActualTotal + " in the billing screen.");

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		String ExpectedGrossWt = ExpectedGrossValue.replace("-", "").trim();
		Double ExpectedQty = Double.parseDouble(ExpectedGrossWt);
		String BillingQtySku = ItemQtyBilling.get(1).getText().replace("-", "").trim();
		Double ActualGrossWtSku = Double.parseDouble(BillingQtySku.replace("₹", "").replace(",", "").trim());
		asrt.assertEquals(ExpectedQty, ActualGrossWtSku, 1.0, "Mismatch in Qty value,expected " + ExpectedQty
				+ " but got " + ActualGrossWtSku + " in the Billing screen");
		asrt.assertEquals(ExpectedSubTotal, SubTotalBilling, 1.0, "Mismatch in Subtotal value, expected "
				+ ExpectedSubTotal + " but got " + SubTotalBilling + "in the Billing screen");
		asrt.assertEquals(TotalAmountBilling, ActualTotalAmount, 1.0, "Mismatch in Total amount value, expected "
				+ TotalAmountBilling + " but got " + ActualTotalAmount + " in the Billing screen");

		// Step 10: Save to estimate
		// Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Back"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));

		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(4000);
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC95_ProformaInvoice");
		Thread.sleep(4000);
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FirstInvoice + "";
		String TaxableValue = SkuItemRate;
		String InvoiceTotal = Double.toString(Double.parseDouble(TaxableValue.replaceAll("[^\\d.]", "").trim())
				+ Double.parseDouble(Tax.replaceAll("[^\\d.]", "").trim()));

		String TotalAdjustment = Double.toString(Adjustment);
		String Gst = Tax;
		String TotalAmnt = "";
		Thread.sleep(3000);
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProformaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, TotalAdjustment, NetTotal, TotalAmnt);

		// Step 11:Recall estimate from cashier
		// Expected Result:Verify whether the recalled item is the same as the item in
		// the cart.
		// Note:Cashier Screen after recall, *No of Product lines, *Displayed Item Name,
		// *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal,
		// *TAX, *Total Amount

		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2", "text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		// Step 12:Select estimate and click on Recall estimation button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		appUtils.ValidateBillingAndRecallScreenData(EstmnNumber, LinesCount, TextItemNamesBilling, TextItemQtyBilling,
				SubTotal, Tax, NetTotal);

		// Step 13: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_TransactionType2);

		// Step 14: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 15: Choose a Sales Representative
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC = base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		Map<String, String> SkuBillingScrnTbleData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SkuNetTotal = SkuBillingScrnTbleData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTbleData.get("LinesCount");

		// Step 16: Click on amount
		// Select payment method(cash/card)
		appUtils.PaymentAfterRecallEstimate(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_PaymentMethod);

		// Step 17:Select a Payment method(Cash or Card)
		// Step 18: Post exchange invoice(sale)
		// Expected Result: Check final invoice details,* save receipt id for future
		// reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC95_SaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";

		String GrossAmount = SkuItemRate;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String OGAdjustment = null;
		String TotalDiamond = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_TotalDiamond;
		String PaymentHDFCCard = PaymentAmountHDFC;
		String SalesPdfPath = SaleInvoicePath;
		String AdvanceAmountFinalInvoice = Double.toString(Adjustment);
		TotalNetWeight = ExpectedGrossWt;
		TotalGrossWeight = ExpectedGrossWt;
		String NetValue = SkuNetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(SalesPdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, OGAdjustment, AdvanceAmountFinalInvoice, SkuList, ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:-" + PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Normal advance settlement with board rate less than fixed
	// rate
	// Automation ID : TC81
	// Author: Chandana Babu
	// </summary>
	public void TC81_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Click on Transaction button
		appUtils.HamBurgerButtonClick("iconShop");
		String AdvanceAmount = appUtils.CreateNormalAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_CustomerNo,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_AdvanceAmount,
				UtilityTestData.SalePersonName, UtilityTestData.DueYear);

		// Step 3: Select customer
		// Step 4: Click on add to estimation button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_CustomerNo,
				UtilityTestData.MenuBarOptions[5]);

		// Step 5: Click on customer adjustment button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));

		// Step 6: click on normal advance adjustment button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Normal Advance Adjustment"));

		// Step 7: Select advance and click on cart button
		// Expected: The balance amount and adjustment amount for the selected advances
		// should be shown correctly
		Thread.sleep(4000);
		base.actionClick(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"));
		base.actionSetData(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"),
				UtilityTestData.Terminal);
		base.pressKey(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"), "ENTER");
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));
		String BalanceAmount = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_TotalAmount("Balance amount", "h4 ellipsis cell"));
		String AdvanceRate = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_TotalAmount("Metal Rate", "h4 ellipsis cell"));
		String AdjustmentAmount = base
				.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.InputAmount"));
		Thread.sleep(1000);
		asrt.assertEquals(BalanceAmount, AdjustmentAmount, "Amount mismatch: Balance amount is " + BalanceAmount
				+ " and adjustment amount is " + AdjustmentAmount + " in advance adjustment page");
		base.buttonClick(NormalSaleGoldandSilverObj
				.Btn_Estimation("win-commandingsurface-overflowbutton win-appbar-overflowbutton"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_return("Add to cart"));

		String ErpGoldRateFor24K = erpUtils.ToUpdateMetalRateWithLowerValue(AdvanceRate, "22k");
		Thread.sleep(120000); // Wait needed for ERP Gold Rate to Change

		// Step 8: Enter OTP
		// Step 9: Scan Gold SKU
		// Note: In case of gold rate which ever is less that is considered
		// Expected : Check calculation
		List<String> SkuList = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_ProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_MetalType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation(
				"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		List<String> ProductNames = new ArrayList<>();
		List<String> ProductQtyList = new ArrayList<>();
		List<String> ProductTotalList = new ArrayList<>();
		ProductNames.add(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_DepositAdjustment);
		ProductQtyList.add(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_DepositAdjustmentQty);
		ProductTotalList.add(AdjustmentAmount);
		for (int i = 0; i < SkuList.size(); i++) {
			int SkuNumber = i + 1;
			String Sku = SkuList.get(i);
			appUtils.SKUIngridentTableValues(Sku, SkuNumber, ScannedSkuDataMap);
			ProductNames.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_HeaderName"));
			ProductQtyList.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_GrossWeight"));
			ProductTotalList.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_Total"));
			double ActualRgRateInItemDetailsPge = Double
					.parseDouble(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_RateRG"));
			String Purity = ScannedSkuDataMap.get("SKU_" + SkuNumber + "_Purity");
			if (Purity.contains("22")) {
				double ExpectedCurrentRateFor22K = (Double.parseDouble(ErpGoldRateFor24K) * 22) / 24;
				asrt.assertEquals(ActualRgRateInItemDetailsPge, ExpectedCurrentRateFor22K, 1.0,
						"RG rate mismatch: Expected is " + ExpectedCurrentRateFor22K + " but got "
								+ ActualRgRateInItemDetailsPge + " in item details page");
			}
			if (Purity.contains("18")) {
				double ExpectedCurrentRateFor18K = (Double.parseDouble(ErpGoldRateFor24K) * 18) / 24;
				asrt.assertEquals(ActualRgRateInItemDetailsPge, ExpectedCurrentRateFor18K, 1.0,
						"RG rate mismatch: Expected is " + ExpectedCurrentRateFor18K + " but got "
								+ ActualRgRateInItemDetailsPge + " in item details page");
			}
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuNumber);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuNumber);
		}

		// Note:Billling Screen No of Product lines, Displayed Item Name, Displayed
		// Quantity, Displayed TOTAL (without Tax), Displayed Subtotal, TAX and Total
		// Amount
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = TransactionDataLine.get("Subtotal");
		String NetTotal = TransactionDataLine.get("NetTotal");
		String Discount = TransactionDataLine.get("Discount");
		String TotalNetWeight = TransactionDataLine.get("NetWeight");
		String Tax = TransactionDataLine.get("Tax");
		String TotalAmount = TransactionDataLine.get("TotalAmount");
		String ActualLinesCount = TransactionDataLine.get("LinesCount");
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		String ExpectedLinesCount = Integer.toString(AllProductRows.size());
		asrt.assertEquals(ActualLinesCount, ExpectedLinesCount, "Lines count mismatch: Expected is "
				+ ExpectedLinesCount + " but found " + ActualLinesCount + " in billing page");

		double SubTotalSum = 0.00;
		for (int i = 0; i < AllProductRows.size(); i++) {
			List<WebElement> ItemName = base
					.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
			String ActualItemNameInBillingPge = ItemName.get(i).getText();
			if (i == 1) {
				String ExpectedItemNameInBillingPge = ProductNames.get(i)
						.substring(ProductNames.get(i).indexOf("-") + 1).trim();
				asrt.assertEquals(ActualItemNameInBillingPge, ExpectedItemNameInBillingPge,
						"Item name mismatch: Expected is " + ExpectedItemNameInBillingPge + " but got "
								+ ActualItemNameInBillingPge + " in billing page");
			} else {
				String ExpectedItemNameInBillingPge = ProductNames.get(i).trim();
				asrt.assertEquals(ActualItemNameInBillingPge, ExpectedItemNameInBillingPge,
						"Item name mismatch: Expected is " + ExpectedItemNameInBillingPge + " but got "
								+ ActualItemNameInBillingPge + " in billing page");
			}
			List<WebElement> Quantity = base
					.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5"));
			String ActualQuantityInBillingPge = Quantity.get(i).getText().replace("-", "");
			String ExpectedQuantityInBillingPge = ProductQtyList.get(i);
			double ActualQtyInBillingPge = Double.parseDouble(ActualQuantityInBillingPge);
			double ExpectedQtyInBillingPge = Double.parseDouble(ExpectedQuantityInBillingPge);
			asrt.assertEquals(ActualQtyInBillingPge, ExpectedQtyInBillingPge, 1.000, "Quantity mismatch: Expected is "
					+ ExpectedQtyInBillingPge + " but got " + ActualQtyInBillingPge + " in billing page");

			List<WebElement> TotalWithoutTax = base.GetElement(
					NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5"));
			String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replaceAll("[^\\d.-]", "")
					.trim();
			SubTotalSum = SubTotalSum + Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
			String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f",
					Double.parseDouble(ProductTotalList.get(i).replaceAll("[^\\d.-]", "").trim()));
			asrt.assertEquals(Double.parseDouble(ActualTotalWithoutTaxInBillingPge.replace("-", "")),
					Double.parseDouble(ExpectedTotalWithoutTaxInBillingPge), 1.0,
					"TotalWithoutTax mismatch: Expected is " + ExpectedTotalWithoutTaxInBillingPge + " but got "
							+ ActualTotalWithoutTaxInBillingPge + " in billing page");
		}
		String ExpectedSubTotalInBillingPge = String.format("%.2f", SubTotalSum).replaceAll("[^\\d.]", "").trim();
		;
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"))
				.replaceAll("[^\\d.]", "").trim();
		asrt.assertEquals(Double.parseDouble(ActualSubTotalInBillingPge),
				Double.parseDouble(ExpectedSubTotalInBillingPge), 1.0,
				"Subtotal mismatch: " + ExpectedSubTotalInBillingPge + " but found " + ActualSubTotalInBillingPge
				+ " in billing page");

		String ActualTaxInBillingPge = Tax.replaceAll("[^\\d.]", "").trim();
		double ActualTax = Double.parseDouble(ActualTaxInBillingPge);
		double ExpectedTax = Double.parseDouble(ProductTotalList.get(1)) * 0.03;
		String ExpectedTaxInBillingPge = String.format("%.2f", ExpectedTax);
		asrt.assertEquals(Double.parseDouble(ActualTaxInBillingPge), Double.parseDouble(ExpectedTaxInBillingPge), 1.0,
				"Tax amount mismatch: " + ExpectedTaxInBillingPge + " but found " + ActualTaxInBillingPge
				+ " in billing page");

		double ExpectedTotalAmountInBillingPge = Double.parseDouble(ActualSubTotalInBillingPge)
				+ Double.parseDouble(ActualTaxInBillingPge);
		double ActualTotalAmountInBillingPge = Double.parseDouble(TotalAmount.replaceAll("[^\\d.-]", "").trim());
		asrt.assertEquals(ActualTotalAmountInBillingPge, ExpectedTotalAmountInBillingPge, 1.0, "Total Amount mismatch: "
				+ ExpectedTotalAmountInBillingPge + " but found " + ActualTotalAmountInBillingPge + " in billing page");

		// Step 10: save esimate
		// Expected: Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc("buttonsContainer positionRelative", "Back"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String ActualEstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC81_ProformaInvoice");
		Thread.sleep(2000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst = Tax;
		String TaxableValue = ProductTotalList.get(1);
		String InvoiceTotal = Double.toString(Double.parseDouble(ProductTotalList.get(1)) + ActualTax);
		String TotalAdjustment = AdjustmentAmount;
		String TotalAmt = "";
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProFormaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, TotalAdjustment, NetTotal, TotalAmt);

		// Step 11: Recall estimate from cashier screen
		// Step 12: Select estimate and click on Recall estimation button
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2", "text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		String ExpectedEstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,
				"Mismatch in Estimation number Actual is " + ActualEstmnNumber + " and Expected Estimation number"
						+ ExpectedEstmnNumber + " in recall estimation page");

		// Step 13: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_TransactionType);

		// Step 14: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 15: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		// Step 16: Click on the Amount
		// Expected: Cashier Screen after recall No of Product lines, Displayed Item
		// Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,
		// TAX and Total Amount
		// Step 17: Select a Payment method(Cash or Card)
		Map<String, String> TransactionDataLineInCashierScreen = appUtils.TransactionSKUsLinesVerify(SkuList,
				ScannedSkuDataMap);
		String TaxInCashierScreen = TransactionDataLineInCashierScreen.get("Tax");
		String TotalAmountInCashierScreen = TransactionDataLineInCashierScreen.get("TotalAmount");
		String ActualLinesCountInCashierScreen = TransactionDataLineInCashierScreen.get("LinesCount");

		List<WebElement> AllProductRowsInCashierScreen = base
				.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		String ExpectedLinesCountInCashierScreen = Integer.toString(AllProductRows.size());
		asrt.assertEquals(ExpectedLinesCountInCashierScreen, ActualLinesCountInCashierScreen,
				"Lines count mismatch: Expected is " + ExpectedLinesCountInCashierScreen + " but found "
						+ ActualLinesCountInCashierScreen + " in Cashier Screen");

		double SubTotalSumInCashierScreen = 0.00;
		for (int i = 0; i < AllProductRows.size(); i++) {
			List<WebElement> ItemName = base
					.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
			String ActualItemNameInCashierScreen = ItemName.get(i).getText();
			if (i == 1) {
				String ExpectedItemNameInCashierScreen = ProductNames.get(i)
						.substring(ProductNames.get(i).indexOf("-") + 1).trim();
				asrt.assertEquals(ExpectedItemNameInCashierScreen, ActualItemNameInCashierScreen,
						"Item name mismatch: Expected is " + ExpectedItemNameInCashierScreen + " but got "
								+ ActualItemNameInCashierScreen + " in Cashier Screen");
			} else {
				String ExpectedItemNameInCashierScreen = ProductNames.get(i).trim();
				asrt.assertEquals(ExpectedItemNameInCashierScreen, ActualItemNameInCashierScreen,
						"Item name mismatch: Expected is " + ExpectedItemNameInCashierScreen + " but got "
								+ ActualItemNameInCashierScreen + " in Cashier Screen");
			}
			List<WebElement> Quantity = base
					.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5"));
			String ActualQuantityInCashierScreen = Quantity.get(i).getText().replace("-", "");
			String ExpectedQuantityInCashierScreen = ProductQtyList.get(i);
			double ActualQtyInCashierScreen = Double.parseDouble(ActualQuantityInCashierScreen);
			double ExpectedQtyInCashierScreen = Double.parseDouble(ExpectedQuantityInCashierScreen);
			asrt.assertEquals(ExpectedQtyInCashierScreen, ActualQtyInCashierScreen, 1.000,
					"Quantity mismatch: Expected is " + ExpectedQtyInCashierScreen + " but got "
							+ ActualQtyInCashierScreen + " in Cashier Screen");

			List<WebElement> TotalWithoutTax = base.GetElement(
					NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5"));
			String ActualTotalWithoutTaxInCashierScreen = TotalWithoutTax.get(i).getText().replaceAll("[^\\d.-]", "")
					.trim();
			SubTotalSumInCashierScreen = SubTotalSumInCashierScreen
					+ Double.parseDouble(ActualTotalWithoutTaxInCashierScreen);
			String ExpectedTotalWithoutTaxInCashierScreen = String.format("%.2f",
					Double.parseDouble(ProductTotalList.get(i).replaceAll("[^\\d.-]", "").trim()));
			asrt.assertEquals(Double.parseDouble(ExpectedTotalWithoutTaxInCashierScreen),
					Double.parseDouble(ActualTotalWithoutTaxInCashierScreen.replace("-", "")), 1.0,
					"TotalWithoutTax mismatch: Expected is " + ExpectedTotalWithoutTaxInCashierScreen + " but got "
							+ ActualTotalWithoutTaxInCashierScreen + " in Cashier Screen");
		}
		String ExpectedSubTotalInCashierScreen = String.format("%.2f", SubTotalSumInCashierScreen)
				.replaceAll("[^\\d.]", "").trim();
		;
		String ActualSubTotalInCashierScreen = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"))
				.replaceAll("[^\\d.]", "").trim();
		asrt.assertEquals(Double.parseDouble(ExpectedSubTotalInCashierScreen),
				Double.parseDouble(ActualSubTotalInCashierScreen), 1.0,
				"Subtotal mismatch: " + ExpectedSubTotalInCashierScreen + " but found " + ActualSubTotalInCashierScreen
				+ " in Cashier Screen");

		String ActualTaxInCashierScreen = TaxInCashierScreen.replaceAll("[^\\d.]", "").trim();
		double ExpectedTaxCalculated = Double.parseDouble(ProductTotalList.get(1)) * 0.03;
		String ExpectedTaxInCashierScreen = String.format("%.2f", ExpectedTaxCalculated);
		asrt.assertEquals(Double.parseDouble(ActualTaxInCashierScreen), Double.parseDouble(ExpectedTaxInCashierScreen),
				1.0, "Tax amount mismatch: " + ExpectedTaxInCashierScreen + " but found " + ActualTaxInCashierScreen
				+ " in Cashier Screen");

		double ExpectedTotalAmountInCashierScreen = Double.parseDouble(ActualSubTotalInCashierScreen)
				+ Double.parseDouble(ActualTaxInCashierScreen);
		double ActualTotalAmountInCashierScreen = Double
				.parseDouble(TotalAmountInCashierScreen.replaceAll("[^\\d.-]", "").trim());
		asrt.assertEquals(ExpectedTotalAmountInCashierScreen, ActualTotalAmountInCashierScreen, 1.0,
				"Total Amount mismatch: " + ExpectedTotalAmountInCashierScreen + " but found "
						+ ActualTotalAmountInCashierScreen + " in Cashier Screen");

		String PaymentAmountForSale = appUtils.PaymentAfterRecallEstimate("HDFC");

		// Step 18: Post the Invoice
		// Expected: Check final invoice details
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC81TaxInvoice");
		Thread.sleep(2000);
		String InvoicePdfPath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice;
		String GrossAmount = ProductTotalList.get(1);
		String TotalDiscount = Discount;
		String TotalQtyPcs = Integer.toString(SkuList.size());
		String TotalDiamond = "";
		String NetValue = NetTotal;
		String PaymentHDFCCard = PaymentAmountForSale;
		String Adjustment = "";
		String TotalGrossWeight = ProductQtyList.get(1);
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(InvoicePdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, Adjustment, AdvanceAmount, SkuList, ScannedSkuDataMap);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Weight booked Offer advance settlement with multiple SKU
	// Automation ID : TC84
	// Author: Hasna EK
	// </summary>
	public void TC84_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {

		// Prerequisite: 1. Customer must be having weight booked offer advance
		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		Map<String, String> AdvanceDetails = appUtils.CreateWeightBookedOfferAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_CustomerID,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_Pieces,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_GrossWeight,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_Company,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_Location,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_DepositPercent);

		String OrderId = AdvanceDetails.get("OrderId");
		String BoardRate = AdvanceDetails.get("FixedGoldRate");
		String WillingToPayAmount = AdvanceDetails.get("WillingToPayAmount");
		String DepositAmnt = WillingToPayAmount;

		// Pre-Requisite: 2. Change metal rate.
		String SkuPurity = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_Purity;
		String ErpGoldRateFor24K = erpUtils.SetMetalRateInERP(BoardRate, SkuPurity);
		System.out.println("ErpGoldRateFor24K " + ErpGoldRateFor24K);
		Thread.sleep(10000);

		// Step 3: Select customer
		// Step 4: Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_CustomerID,
				UtilityTestData.MenuBarOptions[5]);

		// Step 5: Click on customer adjustments-->order advance adjustment
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));
		wait.until(ExpectedConditions.presenceOfElementLocated(
				NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Order Advance Adjustment"))).click();

		// Step 6: choose advance from dropdown-->enter the adjustment amount
		// Expected Result : *Verify whether the offer advances for the selected
		// customer are displayed correctly in the 'Customer Order No' dropdown.
		// *The balance amount and adjustment amount for the selected advances should be
		// shown correctly
		wait.until(ExpectedConditions.presenceOfElementLocated(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Sel_OrderNumber("availableTranscodeOptions")));
		try {
			base.selectorByVisibleText(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
					.Sel_OrderNumber("availableTranscodeOptions"),
					OrderId);
			String selectedValue = base.getTheSelectedTextInDropDown(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
					.Sel_OrderNumber("availableTranscodeOptions"));
			Assert.assertEquals(selectedValue, OrderId, "Order ID selection mismatch");
		} catch (Exception e) {
			Assert.fail("Order ID is not present: " + OrderId);
		}

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", OrderId));
		String BalanceAmount = base
				.GetAttribte(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAndAdjustmentAmnt("BalanceAmount"), "value");
		String FirstAdjustmentAmount = base
				.GetAttribte(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAndAdjustmentAmnt("InputAmount"), "value");

		asrt.assertEquals(Double.parseDouble(BalanceAmount), Double.parseDouble(DepositAmnt),
				"Mismatch in Balance amount, Expected " + DepositAmnt + " but got " + BalanceAmount
				+ " in the Order Adjustment page");
		asrt.assertEquals(Double.parseDouble(FirstAdjustmentAmount), Double.parseDouble(BalanceAmount),
				"Mismatch in Balance amount, Expected" + BalanceAmount + " but got" + FirstAdjustmentAmount
				+ " in the Order Adjustment page");

		base.setData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAndAdjustmentAmnt("InputAmount"), BalanceAmount);
		String AdjustmentAmount = base
				.GetAttribte(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAndAdjustmentAmnt("InputAmount"), "value");

		// Step 7: Click on add to cart
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// Step 8: Scan SKU and click add to cart button
		// Expected Result: Check calculationNote:Billing Screen*No of Product
		// lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without
		// Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SelectedGoldSkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;
		// Gold SKUs
		List<String> GoldSkuList = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_SkuGoldCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_MetalType);
		SelectedGoldSkuList.addAll(GoldSkuList);

		appUtils.HamBurgerButtonClick("iconShop");

		String CstGrossWt = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_GrossWeight;

		erpUtils.ResetRemainingReturnWeight();
		for (String Sku : SelectedGoldSkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			erpUtils.ErpSkuIngredientItemCalculation(CurrentSkuData, SkuCounter, CstGrossWt, SkuPurity, BoardRate,
					ErpGoldRateFor24K);
			SkuCounter++;
		}

		String CstProdQty = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_GrossPieces;
		String ExpectedAdvanceName = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_AdvanceName;
		Map<String, String> DataMap = mathUtils.ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(
				ScannedSkuDataMap, CstProdQty, AdjustmentAmount, ExpectedAdvanceName);
		String TaxBillingPge = DataMap.get("Tax");
		String InvoiceTotal = DataMap.get("InvoiceTotal");
		String TaxableValue = DataMap.get("TaxableValue");
		String AmountDue = DataMap.get("AmountDue");
		String LinesCount = DataMap.get("LinesCount");
		String TotalGrossWt = DataMap.get("TotalGrossWtSku");

		// Step 9: Click on save to estimate-->Recall estimate from cashier screen
		// Expected Result: Verify the estimate details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc("buttonsContainer positionRelative", "Back"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstimationNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		// PDF Verification Step
		Thread.sleep(4000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC84_ProformaInvoice");
		Thread.sleep(5000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst = TaxBillingPge;
		String TotalAmnt = TaxableValue;
		String NetTotal = AmountDue;
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProFormaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, AdjustmentAmount, NetTotal, TotalAmnt);
		Thread.sleep(2000);

		// Step 10: Select estimate and click on recall estimation
		// Step 11: Select the Transaction Type as Sales
		// Step 12: Click on Process Estimation
		// Step 13: Choose a sales representative
		// Expected Result: Verify whether the recalled item is the same as the item in
		// the cart.
		// Expected Result : *Verify whether the offer advances for the selected
		// customer are displayed correctly in the 'Customer Order No' dropdown.
		// *The balance amount and adjustment amount for the selected advances should be
		// shown correctly
		appUtils.RecallEstimateProcess(EstimationNumber,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_TransactionType);
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SelectedGoldSkuList,
				ScannedSkuDataMap);
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		Map<String, String> DataMapAfterRecall = mathUtils.ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(
				ScannedSkuDataMap, CstProdQty, AdjustmentAmount, ExpectedAdvanceName);

		// Step 14.Click on the Amount
		// Step 15.Select a Payment method(Cash or Card)
		appUtils.PaymentAfterRecallEstimate(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC84_PaymentMethod);

		// Step 16: Post exchange invoice(sale)
		// Expected Result: Check final invoice details,* save receipt id for future
		// reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC84_SaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";
		String GrossAmount = TaxableValue;
		String TotalDiscount = Discount;
		String TotalQtyPcs = String.valueOf(Integer.parseInt(LinesCount) - 1);
		String Adjustment = AdjustmentAmount;
		String TotalDiamond = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_TotalDiamond;
		String PaymentHDFCCard = AmountDue;
		String SalesPdfPath = SaleInvoicePath;
		String AdvanceAmountFinalInvoice = DepositAmnt;
		String TotalGrossWeight = TotalGrossWt;
		String NetValue = AmountDue;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(SalesPdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, Adjustment, AdvanceAmountFinalInvoice, SelectedGoldSkuList, ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:- " + PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : multiple OG converted normal advance settlement
	// Automation ID : TC89
	// Author : Jhoncy
	// </summary>
	public void TC89_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> PurityOfItems = new ArrayList();
		List<Double> AllRates = new ArrayList<>();
		appUtils.HamBurgerButtonClick("iconShop");
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SkuCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_MetalType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation(
				"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		for (int i = 0; i < SkuListGold.size(); i++)

		{
			String CurrentGoldSku = SkuListGold.get(i);

			appUtils.SKUIngridentTableValues(CurrentGoldSku.toString(), (i + 1), SkuDetails);

			PurityOfItems.add(SkuDetails.get("SKU_" + (i + 1) + "_Purity"));

			for (int ItemIndex = 1;; ItemIndex++) {
				String Rate = "SKU" + (i + 1) + "_ITEM" + ItemIndex + "_RATE";

				if (SkuDetails.containsKey(Rate))

				{
					try

					{
						double SkuRate = Double.parseDouble(SkuDetails.get(Rate));

						AllRates.add(SkuRate);
					} catch (NumberFormatException e) {
						System.out.println("Rate not fetched");
					}
				} else {
					break;
				}
			}

			SkuDetails.put("SKU_" + (i + 1) + "_AllRates", AllRates.toString());
		}
		// For conversion of Rate

		String ItemPurity = PurityOfItems.get(0);
		double ExchangeRate = AllRates.get(0);
		double ExpectedGoldRate22k = 0;
		double ExpectedGoldRate18k = 0;
		if (ItemPurity.equalsIgnoreCase("18k")) {
			double ConvertedGoldRate = (ExchangeRate * 22) / 18;

			ExpectedGoldRate22k = Math.round(ConvertedGoldRate);

			ExpectedGoldRate18k = ExchangeRate;
		} else {
			ExpectedGoldRate22k = Math.round(ExchangeRate);
			ExpectedGoldRate18k = (ExchangeRate * 18) / 22;
		}
		System.out.println(ExpectedGoldRate22k);
		appUtils.VoidTransaction();

		// Step 2. Click on Transaction button
		// Prerequisite :Customer must have a OG converted normal advance
		String OGPaymentAmount1 = appUtils.MultipleOGConvertedToNormalAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_CustomerPhoneNo);

		String OGPaymentAmount2 = appUtils.MultipleOGConvertedToNormalAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_CustomerPhoneNo);

		// Step 3: Select customer
		// Step 4: Click on add to estimation button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_CustomerPhoneNo,
				UtilityTestData.MenuBarOptions[5]);

		// Step 5: Click on customer adjustment button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));

		// Step 6: click on normal advance adjustment button
		// Step 7: Select advance and click on cart button
		// Expected: The balance amount and adjustment amount for the selected advances
		// should be shown correctly
		List<String> OGPaymentAmount = Arrays.asList(OGPaymentAmount1, OGPaymentAmount2);
		double Quantity = 0.0;
		String MetalRateFetch = "";
		double TotalBalance = 0.0;
		double Adjustment = 0.0;
		for (String OGAmount : OGPaymentAmount) {

			base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Normal Advance Adjustment"));
			Thread.sleep(4000);
			base.actionClick(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"));
			base.actionSetData(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"), OGAmount);
			Thread.sleep(3000);
			// base.pressKey(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value:
			// viewModel.SearchTerm"), "ENTER");
			// Thread.sleep(4000);
			base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));
			String BalanceAmount = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
					.Ele_TotalAmount("Balance amount", "h4 ellipsis cell"));
			String AdjustmentAmount = base
					.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.InputAmount"));
			Thread.sleep(1000);
			MetalRateFetch = base
					.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
							.Ele_AdvanceAdjustMetalRate("option", "Metal Rate", "gridcell", 0));
			Quantity = Quantity + Double.parseDouble(AdjustmentAmount) / Double.parseDouble(MetalRateFetch);
			TotalBalance = TotalBalance + Double.parseDouble(BalanceAmount);
			Adjustment = Adjustment + Double.parseDouble(AdjustmentAmount);
			asrt.assertEquals(BalanceAmount, AdjustmentAmount, "Amount mismatch: Balance amount is " + BalanceAmount
					+ " and adjustment amount is " + AdjustmentAmount + " in advance adjustment page");
			base.buttonClick(NormalSaleGoldandSilverObj
					.Btn_Estimation("win-commandingsurface-overflowbutton win-appbar-overflowbutton"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_return("Add to cart"));
		}
		// Step 8:Enter OTP(disabled)
		// Step 9:Scan a SKU and click add to cart button
		// Note:Check Average Rate
		// Expected Result: 9.Check calculation Note:Billling Screen*No of Product
		// lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without
		// Tax)*Displayed Subtotal*TAX *Total Amount
		SKUResult SkuOGSale = appUtils.GetOGSaleSku(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_MetalType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SaleQty,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SkuCount);

		List<String> SkuList = SkuOGSale.PcsRateSKU;
		System.out.println("SkuList" + SkuList);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> ScannedSkuDataMap = new HashMap();
		appUtils.SKUIngridentTableValues(SkuList.get(0),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SkuCount,
				ScannedSkuDataMap);

		String ExpectedGrossValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SkuCount
				+ "_GrossWeight");
		String ExpectedNetValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SkuCount
				+ "_TotalRGWeight");
		String ExpectedItemName = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SkuCount
				+ "_ItemName");
		String TotalForEachItem = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SkuCount
				+ "_Total");
		String RateKey = "SKU"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SkuCount
				+ "_ITEM"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SkuCount
				+ "_RATE";
		String BoardRateFromSkuTble = ScannedSkuDataMap.get(RateKey);
		Double SkuBoardRate = Double.parseDouble(BoardRateFromSkuTble);
		String Purity = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_SkuCount
				+ "_Purity");
		double BoardRate22k = 0;
		if (Purity.equalsIgnoreCase("18k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 18;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("14k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 14;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("22k")) {
			BoardRate22k = SkuBoardRate;

		}
		if (Double.parseDouble(ExpectedNetValue) > Quantity) {
			double Remainingwgt = (Double.parseDouble(ExpectedNetValue) - Quantity);
			double TotalAmount = (Quantity * Double.parseDouble(MetalRateFetch)) + (Remainingwgt * ExpectedGoldRate22k);
			double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0) / 100.0;
			asrt.assertEquals(SkuBoardRate, AverageRate, 1.0,
					"Mismatch  In Average Rate,Expected" + AverageRate + "but got" + SkuBoardRate + "in return page");

			System.out.print("Average rate of the SKU is:" + AverageRate);

		} else {
			asrt.assertEquals(SkuBoardRate, Double.parseDouble(MetalRateFetch), 1.0,
					"Mismatch  In Average Rate,Expected" + MetalRateFetch + "but got" + SkuBoardRate
					+ "in return page");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = BillingScrnTableData.get("Subtotal");
		String TotalGrossWeight = BillingScrnTableData.get("GrossWeight");
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Tax = BillingScrnTableData.get("Tax");
		String TotalAmount = BillingScrnTableData.get("TotalAmount");
		String NetTotal = BillingScrnTableData.get("NetTotal");
		String LinesCount = BillingScrnTableData.get("LinesCount");

		int LinesCountBilling = Integer.parseInt(LinesCount);
		double ActualSubTotalBilling = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double TaxBilling = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTotalAmount = Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> TextItemNamesBilling = new ArrayList<>();
		List<String> TextItemQtyBilling = new ArrayList<>();

		int ExpectedLinesCount = AllProductRows.size();
		asrt.assertEquals(ExpectedLinesCount, LinesCountBilling, "Mismatch in Lines count,expected "
				+ ExpectedLinesCount + " but got " + LinesCountBilling + " in the billing screen");

		String SkuItemRate = ItemRateBilling.get(2).getText().trim().replaceAll("[^\\d.\\-]", "");
		double GoldItemRate = Double.parseDouble(SkuItemRate);
		double ExpectedSubTotal = 0.0;
		ExpectedSubTotal = GoldItemRate - (TotalBalance);

		double ExpectedTaxAmnt = GoldItemRate * 3 / 100;
		double ActualTotalAmount = ExpectedSubTotal + ExpectedTaxAmnt;

		asrt.assertEquals(ExpectedTaxAmnt, TaxBilling, 1.0, "Mismatch in Tax value, expected" + ExpectedTaxAmnt
				+ " but got " + TaxBilling + " in the billing screen");

		for (int n = 0; n < ItemNamesBilling.size(); n++) {
			TextItemNamesBilling.add(ItemNamesBilling.get(n).getText().trim());
		}
		Thread.sleep(3000);
		String ExpectedFullItemName = ExpectedItemName.trim();
		String ExpectedItemName1 = ExpectedFullItemName.contains("-")
				? ExpectedFullItemName.substring(ExpectedFullItemName.indexOf("-") + 2)
						: ExpectedFullItemName;
		String ActualItemName = ItemNamesBilling.get(2).getText().trim();

		asrt.assertEquals(ActualItemName, ExpectedItemName1, "Mismatch in item name, expected " + ActualItemName
				+ "but got " + ExpectedItemName1 + "  in the billing screen.");

		String ExpectedTotal = ScannedSkuDataMap.get("SKU_" + (1) + "_Total");
		double ExpectTotal = Double.parseDouble(TotalForEachItem.replaceAll("[^\\d.]", ""));
		String ActualTotalStr = ItemRateBilling.get(2).getText().trim();
		double ActualTotal = Double.parseDouble(ActualTotalStr.replaceAll("[^\\d.]", ""));
		System.out.println("ExpectedTotal" + TotalForEachItem);
		System.out.println("ActualTotal" + ActualTotal);

		asrt.assertEquals(ActualTotal, ExpectTotal, 1.0, "Mismatch in total amounts (without tax),expected "
				+ ActualTotal + " but got " + ExpectTotal + " in the billing screen.");

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		String ExpectedGrossWt = ExpectedGrossValue.replace("-", "").trim();
		Double ExpectedQty = Double.parseDouble(ExpectedGrossWt);
		String BillingQtySku = ItemQtyBilling.get(2).getText().replace("-", "").trim();
		Double ActualGrossWtSku = Double.parseDouble(BillingQtySku.replace("₹", "").replace(",", "").trim());
		asrt.assertEquals(ActualGrossWtSku, ExpectedQty, 1.0, "Mismatch in Qty value,expected " + ActualGrossWtSku
				+ " but got " + ExpectedQty + " in the Billing screen");
		asrt.assertEquals(ActualSubTotalBilling, ExpectedSubTotal, 1.0, "Mismatch in Subtotal value, expected "
				+ ActualSubTotalBilling + " but got " + ExpectedSubTotal + "in the Billing screen");
		asrt.assertEquals(ActualTotalAmount, ExpectedTotalAmount, 1.0, "Mismatch in Total amount value, expected "
				+ ActualTotalAmount + " but got " + ExpectedTotalAmount + " in the Billing screen");

		// Step 10: Save to estimate
		// Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Back"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));

		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(4000);
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC89_ProformaInvoice");
		Thread.sleep(4000);
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FirstInvoice + "";
		String TaxableValue = SkuItemRate;
		String InvoiceTotal = Double.toString(Double.parseDouble(TaxableValue.replaceAll("[^\\d.]", "").trim())
				+ Double.parseDouble(Tax.replaceAll("[^\\d.]", "").trim()));

		String TotalAdjustment = Double.toString(Adjustment);
		String Gst = Tax;
		String TotalAmnt = "";
		Thread.sleep(3000);
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProformaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, TotalAdjustment, NetTotal, TotalAmnt);

		// Step 11:Recall estimate from cashier
		// Expected Result:Verify whether the recalled item is the same as the item in
		// the cart.
		// Note:Cashier Screen after recall, *No of Product lines, *Displayed Item Name,
		// *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal,
		// *TAX, *Total Amount
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2", "text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		// Step 12:Select estimate and click on Recall estimation button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		appUtils.ValidateBillingAndRecallScreenData(EstmnNumber, LinesCount, TextItemNamesBilling, TextItemQtyBilling,
				SubTotal, Tax, NetTotal);

		// Step 13: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_TransactionType2);

		// Step 14: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 15: Choose a Sales Representative
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC = base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		Map<String, String> SkuBillingScrnTbleData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SkuNetTotal = SkuBillingScrnTbleData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTbleData.get("LinesCount");

		// Step 16: Click on amount
		// Select payment method(cash/card)
		appUtils.PaymentAfterRecallEstimate(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_PaymentMethod);

		// Step 17:Select a Payment method(Cash or Card)
		// Step 18: Post exchange invoice(sale)
		// Expected Result: Check final invoice details,* save receipt id for future
		// reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC89_SaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";

		String GrossAmount = SkuItemRate;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String OGAdjustment = null;
		String TotalDiamond = null;
		String PaymentHDFCCard = PaymentAmountHDFC;
		String SalesPdfPath = SaleInvoicePath;
		String AdvanceAmountFinalInvoice = TotalAdjustment;
		TotalNetWeight = ExpectedGrossWt;
		TotalGrossWeight = ExpectedGrossWt;
		String NetValue = SkuNetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(SalesPdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, OGAdjustment, AdvanceAmountFinalInvoice, SkuList, ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:-" + PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Normal advance settlement with multiple category items
	// Automation ID : TC80
	// Author: Jhoncy
	// </summary>
	public void TC80_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		appUtils.HamBurgerButtonClick("iconShop");
		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> PurityOfItems = new ArrayList();
		List<Double> AllRates = new ArrayList<>();
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_ProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_MetalType);
		appUtils.HamBurgerButtonClick("iconShop");
		for (int i = 0; i < SkuListGold.size(); i++)

		{
			String CurrentGoldSku = SkuListGold.get(i);

			appUtils.SKUIngridentTableValues(CurrentGoldSku.toString(), (i + 1), SkuDetails);

			PurityOfItems.add(SkuDetails.get("SKU_" + (i + 1) + "_Purity"));

			for (int ItemIndex = 1;; ItemIndex++) {
				String Rate = "SKU" + (i + 1) + "_ITEM" + ItemIndex + "_RATE";

				if (SkuDetails.containsKey(Rate))

				{
					try

					{
						double SkuRate = Double.parseDouble(SkuDetails.get(Rate));

						AllRates.add(SkuRate);
					} catch (NumberFormatException e) {
						System.out.println("Rate not fetched");
					}
				} else {
					break;
				}
			}

			SkuDetails.put("SKU_" + (i + 1) + "_AllRates", AllRates.toString());
		}
		// For conversion of Rate

		String ItemPurity = PurityOfItems.get(0);
		double ExchangeRate = AllRates.get(0);
		double ExpectedGoldRate22k = 0;
		double ExpectedGoldRate18k = 0;
		if (ItemPurity.equalsIgnoreCase("18k")) {
			double ConvertedGoldRate = (ExchangeRate * 22) / 18;

			ExpectedGoldRate22k = Math.round(ConvertedGoldRate);

			ExpectedGoldRate18k = ExchangeRate;
		} else {
			ExpectedGoldRate22k = Math.round(ExchangeRate);
			ExpectedGoldRate18k = (ExchangeRate * 18) / 22;
		}
		System.out.print(ExpectedGoldRate22k);
		appUtils.VoidTransaction();

		// Step 2: Click on Transaction button
		// Prerequisite:Customer must be having a normal advance
		appUtils.HamBurgerButtonClick("iconShop");
		String AdvanceAmount = appUtils.CreateNormalAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_CustomerNo,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_AdvanceAmount,
				UtilityTestData.SalePersonName, UtilityTestData.DueYear);

		// Step 3: Select customer
		// Step 4: Click on add to estimation button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_CustomerNo,
				UtilityTestData.MenuBarOptions[5]);

		// Step 5: Click on customer adjustment button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));

		// Step 6: click on normal advance adjustment button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Normal Advance Adjustment"));

		// Step 7: Select advance and click on cart button
		// Expected: The balance amount and adjustment amount for the selected advances
		// should be shown correctly
		Thread.sleep(4000);
		base.actionClick(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"));
		base.actionSetData(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"),
				UtilityTestData.Terminal);
		// base.pressKey(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value:
		// viewModel.SearchTerm"), "ENTER");
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));
		String BalanceAmount = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_TotalAmount("Balance amount", "h4 ellipsis cell"));
		String AdvanceRate = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_TotalAmount("Metal Rate", "h4 ellipsis cell"));
		String AdjustmentAmount = base
				.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.InputAmount"));
		Thread.sleep(1000);
		String MetalRateFetch = base
				.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_AdvanceAdjustMetalRate("option", "Metal Rate", "gridcell", 0));
		double Quantity = Double.parseDouble(AdjustmentAmount) / Double.parseDouble(MetalRateFetch);
		double TotalBalance = Double.parseDouble(BalanceAmount);
		double Adjustment = Double.parseDouble(AdjustmentAmount);

		asrt.assertEquals(BalanceAmount, AdjustmentAmount, "Amount mismatch: Balance amount is " + BalanceAmount
				+ " and adjustment amount is " + AdjustmentAmount + " in advance adjustment page");

		base.buttonClick(NormalSaleGoldandSilverObj
				.Btn_Estimation("win-commandingsurface-overflowbutton win-appbar-overflowbutton"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_return("Add to cart"));

		// Step 8: Enter OTP(disabled)
		// Step 9: Scan multiple category SKU(Gold,Precia,Uncut)and click add to cart
		// button
		// Note:Check Average Rate
		// Expected Result:Check calculation Note:Billling Screen*No of Product
		// lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without
		// Tax)*Displayed Subtotal*TAX *Total Amount
		List<String> SelectedSkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		List<String> GoldSkuList = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_ProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_MetalType);
		SelectedSkuList.addAll(GoldSkuList);
		appUtils.HamBurgerButtonClick("iconShop");

		List<String> PreciaSkuList = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_ProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_FromCounterPrecia,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_MetalType);
		SelectedSkuList.addAll(PreciaSkuList);
		appUtils.HamBurgerButtonClick("iconShop");

		List<String> UncutSkuList = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_ProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_FromCounterDmd,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_MetalType);
		SelectedSkuList.addAll(UncutSkuList);
		appUtils.HamBurgerButtonClick("iconShop");
		for (String Sku : SelectedSkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuCounter);
			SkuCounter++;
		}
		String CstProdQty = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_GrossPieces;
		String ExpectedAdvanceName = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_AdvanceName;
		Map<String, String> DataMap = mathUtils.ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(
				ScannedSkuDataMap, CstProdQty, AdjustmentAmount, ExpectedAdvanceName);
		String TaxBillingPge = DataMap.get("Tax");
		String InvoiceTotal = DataMap.get("InvoiceTotal");
		String TaxableValue = DataMap.get("TaxableValue");
		String AmountDue = DataMap.get("AmountDue");
		String LinesCount = DataMap.get("LinesCount");
		String TotalGrossWt = DataMap.get("TotalGrossWtSku");
		String ExpectedNetValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_ProductCount
				+ "_TotalRGWeight");
		String RateKey = "SKU"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_ProductCount
				+ "_ITEM"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_ProductCount
				+ "_RATE";

		String BoardRateFromSkuTble = ScannedSkuDataMap.get(RateKey);
		Double SkuBoardRate = Double.parseDouble(BoardRateFromSkuTble);
		String Purity = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_ProductCount
				+ "_Purity");
		double BoardRate22k = 0;
		if (Purity.equalsIgnoreCase("18k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 18;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("14k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 14;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("22k")) {
			BoardRate22k = SkuBoardRate;

		}
		if (Double.parseDouble(ExpectedNetValue) > Quantity) {
			double Remainingwgt = (Double.parseDouble(ExpectedNetValue) - Quantity);
			double TotalAmount = (Quantity * Double.parseDouble(MetalRateFetch)) + (Remainingwgt * ExpectedGoldRate22k);
			double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0) / 100.0;

			asrt.assertEquals(SkuBoardRate, AverageRate, 1.0,
					"Mismatch  In Average Rate,Expected" + AverageRate + "but got" + SkuBoardRate + "in return page");

			System.out.print("Average rate of the SKU is:" + AverageRate);

		} else {
			asrt.assertEquals(SkuBoardRate, Double.parseDouble(MetalRateFetch), 1.0,
					"Mismatch  In Average Rate,Expected" + MetalRateFetch + "but got" + SkuBoardRate
					+ "in return page");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// Step 10: save estimate
		// Expected Result: Verify the estimate details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc("buttonsContainer positionRelative", "Back"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstimationNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		// PDF Verification Step
		Thread.sleep(4000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC80_ProformaInvoice");
		Thread.sleep(5000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst = TaxBillingPge;
		String TotalAmnt = TaxableValue;
		String NetTotal = AmountDue;
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProFormaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, AdjustmentAmount, NetTotal, TotalAmnt);
		Thread.sleep(2000);

		// Step 11:Recall estimate from cashier screen
		// Step 12:Select estimate and click on Recall estimation button
		// Step 13:Select the Transaction Type as Sales
		// Step 14:Click on Process Estimation
		// Step 15:Choose a sales representative
		// Expected Result: Verify whether the recalled item is the same as the item in
		// the cart.
		// Note:Cashier Screen after recall*No of Product lines*Displayed Item
		// Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed
		// Subtotal*TAX*Total Amount
		appUtils.RecallEstimateProcess(EstimationNumber,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_TransactionType);

		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SelectedSkuList,
				ScannedSkuDataMap);
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		Map<String, String> DataMapAfterRecall = mathUtils.ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(
				ScannedSkuDataMap, CstProdQty, AdjustmentAmount, ExpectedAdvanceName);

		// Step 16.Click on the Amount
		// Step 17.Select a Payment method(Cash or Card)
		appUtils.PaymentAfterRecallEstimate(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC80_PaymentMethod);

		// Step 18: Post exchange invoice(sale)
		// Expected Result: Check final invoice details,* save receipt id for future
		// reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC80_SaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";
		String GrossAmount = TaxableValue;
		String TotalDiscount = Discount;
		String TotalQtyPcs = String.valueOf(Integer.parseInt(LinesCount) - 1);
		String TotalDiamond = null;
		String PaymentHDFCCard = AmountDue;
		String SalesPdfPath = SaleInvoicePath;
		String TotalGrossWeight = TotalGrossWt;
		String NetValue = AmountDue;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(SalesPdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, AdjustmentAmount, AdjustmentAmount, SelectedSkuList, ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:- " + PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Offer advance partial settlement
	// Automation ID : TC96
	// Author: Vishnu RR
	// </summary>
	public void TC96_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {

		// Step:1.Login to POS
		// URL: https://scunmv1je3k79005544-cpos.su.retail.dynamics.com/

		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		// appUtils.HamBurgerButtonClick("iconShop");

		// Prerequisite : Customer Must have an offer Advance
		Map<String, String> Details = appUtils.CreateWeightBookedOfferAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC96_CustomerID,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC96_PiecesByPurity,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC96_GrossWeight,
				UtilityTestData.Company[0], UtilityTestData.Location[0],
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC96_DepositPercent);

		// Step 3. Select customer
		// Step 4. Click on add to estimation button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC96_CustomerID,
				"AddCustomerForSalesEstimationCommand");
		// Note - Checking Average Rate,Calculations are done.For Getting Board
		// rate,scanning sku

		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> PurityOfItems = new ArrayList();
		List<Double> AllRates = new ArrayList<>();
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC96_SaleProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC96_TransferTypeSKU,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_FromCounterSKU,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_MetalTypeSKU);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation(
				"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); // go to
		// Transaction
		for (int i = 0; i < SkuListGold.size(); i++)

		{
			String CurrentGoldSku = SkuListGold.get(i);

			appUtils.SKUIngridentTableValues(CurrentGoldSku.toString(), (i + 1), SkuDetails);

			PurityOfItems.add(SkuDetails.get("SKU_" + (i + 1) + "_Purity"));

			for (int ItemIndex = 1;; ItemIndex++) {
				String Rate = "SKU" + (i + 1) + "_ITEM" + ItemIndex + "_RATE";

				if (SkuDetails.containsKey(Rate))

				{
					try

					{
						double SkuRate = Double.parseDouble(SkuDetails.get(Rate));

						AllRates.add(SkuRate);
					} catch (NumberFormatException e) {
						System.out.println("Rate not fetched");
					}
				} else {
					break;
				}
			}

			SkuDetails.put("SKU_" + (i + 1) + "_AllRates", AllRates.toString());
		}
		// For conversion of Rate

		String ItemPurity = PurityOfItems.get(0);
		double ExchangeRate = AllRates.get(0);
		double ExpectedGoldRate22k = 0;
		double ExpectedGoldRate18k = 0;
		if (ItemPurity.equalsIgnoreCase("18k")) {
			double ConvertedGoldRate = (ExchangeRate * 22) / 18;

			ExpectedGoldRate22k = Math.round(ConvertedGoldRate);

			ExpectedGoldRate18k = ExchangeRate;
		} else {
			ExpectedGoldRate22k = Math.round(ExchangeRate);
			ExpectedGoldRate18k = (ExchangeRate * 18) / 22;
		}
		System.out.print(ExpectedGoldRate22k);
		appUtils.VoidTransaction();

		// step4 .Click on add to estimation button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC96_CustomerID,
				"AddCustomerForSalesEstimationCommand");

		// Step 5. Click on customer adjustment button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Step 6. click on Order Advancement Adjustment
		Wait.until(ExpectedConditions
				.elementToBeClickable(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Order Advance Adjustment")))
		.click();

		// Step 7. Select the Order Number from the list
		base.selectorByVisibleText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Sel_OrderNumber("availableTranscodeOptions"), Details.get("OrderId"));

		// step: 8. Select advance and Enter partial amount in "Adjustment amount"
		base.ClickCondition(NormalSaleGoldandSilverObj.Ele_ItemRecallEst("option", Details.get("OrderId")));

		// To Get the details from the orderID
		String ExpectedOrderID = base.getTheSelectedTextInDropDown(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Sel_OrderNumber("availableTranscodeOptions"));

		// Expected Result Verify whether the offer advances for the selected customer
		// are displayed correctly in the 'Customer Order No' dropdown

		asrt.assertEquals(Details.get("OrderId"), ExpectedOrderID, "Mismatch in Order ID");

		double AdvanceBalance = 0;
		String AdvanceBalanceAmount = "";
		String MetalRateFetch = "";

		// Balance Amount - in the list
		Thread.sleep(2000);
		AdvanceBalanceAmount = base
				.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_OrderBalanceAmt("dataListLine", "Balance amount"));
		System.out.println("Advance Amt = " + AdvanceBalanceAmount);
		// Balance Amt in the box
		Thread.sleep(2000);
		AdvanceBalance = Double.parseDouble(
				base.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Balance amount")));
		System.out.println("Balance Amount from the box " + AdvanceBalance);

		// To fetch the metal rate
		// MetalRateFetch =
		// base.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj.Ele_AdvanceAdjustTrnsNo("'h4
		// ellipsis cell", "Metal Rate"));

		MetalRateFetch = base.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_MetalRate("column3", "h4 ellipsis cell", "Metal Rate"));
		System.out.println("Metal Rate = " + MetalRateFetch);
		// Selecting the list
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("column1", "h4 ellipsis cell", "1"));

		String BalanceAmtInAdvanceAdj = base
				.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Balance amount"));
		String DefaultAdjAmt = base
				.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Adjustment amount"));
		double Adjustment = AdvanceBalance * 0.1;
		double Quantity22k = Adjustment / Double.parseDouble(MetalRateFetch);

		double MetalRate18k = (Double.parseDouble(MetalRateFetch) * 18) / 22;
		double Quantity18k = Adjustment / MetalRate18k;

		System.out.println(Adjustment);
		System.out.println(AdvanceBalanceAmount);
		base.clearData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAmountInput("Adjustment amount"));
		base.setData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAmountInput("Adjustment amount"), Double.toString(Adjustment));

		// Step 8. Click on cart button
		// Expected Result:The balance amount and adjustment amount for the selected
		// advances should be shown correctly
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		asrt.assertEquals(BalanceAmtInAdvanceAdj, DefaultAdjAmt,
				"Mismatch in balance amount and adjusted amount displayed in advance adjustment page");

		// Step 9:Scan a SKU and click add to cart button
		// Note:Check Average Rate
		// Expected Result: Check calculation
		SKUResult SkuOGSale = appUtils.GetOGSaleSku(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_MetalType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SaleQty,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount);

		List<String> SkuList = SkuOGSale.PcsRateSKU;
		System.out.println("SkuList" + SkuList);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> ScannedSkuDataMap = new HashMap();
		appUtils.SKUIngridentTableValues(SkuList.get(0),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount,
				ScannedSkuDataMap);

		String ExpectedGrossValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_GrossWeight");
		String ExpectedNetValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_TotalRGWeight");
		String ExpectedItemName = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_ItemName");
		String TotalForEachItem = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_Total");
		String RateKey = "SKU"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_ITEM"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_RATE";
		String BoardRateFromSkuTble = ScannedSkuDataMap.get(RateKey);
		Double SkuBoardRate = Double.parseDouble(BoardRateFromSkuTble);
		String Purity = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_Purity");
		double BoardRate22k = 0;
		// if (Purity.equalsIgnoreCase("18k"))
		// {
		// double ConvertedBoardRate22k = (SkuBoardRate * 22) / 18;
		// BoardRate22k = Math.round(ConvertedBoardRate22k);
		// }else if(Purity.equalsIgnoreCase("14k")){
		// double ConvertedBoardRate22k = (SkuBoardRate * 22) / 14;
		// BoardRate22k = Math.round(ConvertedBoardRate22k);
		// }else if(Purity.equalsIgnoreCase("22k")){
		// BoardRate22k = SkuBoardRate;
		//
		// }
		if (Purity.equalsIgnoreCase("22k")) {
			if (Double.parseDouble(ExpectedNetValue) > Quantity22k) {
				double Remainingwgt = (Double.parseDouble(ExpectedNetValue) - Quantity22k);
				double TotalAmount = (Quantity22k * Double.parseDouble(MetalRateFetch))
						+ (Remainingwgt * ExpectedGoldRate22k);
				double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0) / 100.0;
				asrt.assertEquals(SkuBoardRate, AverageRate, 1.0, "Mismatch  In Average Rate,Expected" + AverageRate
						+ "but got" + SkuBoardRate + "in return page");

				System.out.print("Average rate of the SKU is:" + AverageRate);
			} else {
				asrt.assertEquals(SkuBoardRate, Double.parseDouble(MetalRateFetch), 1.0,
						"Mismatch  In Average Rate,Expected" + MetalRateFetch + "but got" + SkuBoardRate
						+ "in return page");
			}
		}

		if (Purity.equalsIgnoreCase("18k")) {
			if (Double.parseDouble(ExpectedNetValue) > Quantity18k) {
				double Remainingwgt = (Double.parseDouble(ExpectedNetValue) - Quantity18k);
				double TotalAmount = (Quantity18k * MetalRate18k) + (Remainingwgt * ExpectedGoldRate18k);
				double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0) / 100.0;
				asrt.assertEquals(SkuBoardRate, AverageRate, 1.0, "Mismatch  In Average Rate,Expected" + AverageRate
						+ "but got" + SkuBoardRate + "in return page");

				System.out.print("Average rate of the SKU is:" + AverageRate);
			} else {
				asrt.assertEquals(SkuBoardRate, MetalRate18k, 1.0, "Mismatch  In Average Rate,Expected" + MetalRate18k
						+ "but got" + SkuBoardRate + "in return page");
			}
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = BillingScrnTableData.get("Subtotal");
		String TotalGrossWeight = BillingScrnTableData.get("GrossWeight");
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Tax = BillingScrnTableData.get("Tax");
		String TotalAmount = BillingScrnTableData.get("TotalAmount");
		String NetTotal = BillingScrnTableData.get("NetTotal");
		String LinesCount = BillingScrnTableData.get("LinesCount");

		int LinesCountBilling = Integer.parseInt(LinesCount);
		double SubTotalBilling = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double TaxBilling = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));
		double TotalAmountBilling = Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> TextItemNamesBilling = new ArrayList<>();
		List<String> TextItemQtyBilling = new ArrayList<>();

		int ExpectedLinesCount = AllProductRows.size();
		asrt.assertEquals(ExpectedLinesCount, LinesCountBilling, "Mismatch in Lines count,expected "
				+ ExpectedLinesCount + " but got " + LinesCountBilling + " in the billing screen");

		String SkuItemRate = ItemRateBilling.get(1).getText().trim().replaceAll("[^\\d.\\-]", "");
		double FirstItemRate = Double.parseDouble(SkuItemRate);
		double ExpectedSubTotal = 0.0;
		ExpectedSubTotal = FirstItemRate - (Adjustment);

		double ExpectedTaxAmnt = FirstItemRate * 3 / 100;
		double ActualTotalAmount = ExpectedSubTotal + ExpectedTaxAmnt;

		asrt.assertEquals(ExpectedTaxAmnt, TaxBilling, 1.0, "Mismatch in Tax value, expected" + ExpectedTaxAmnt
				+ " but got " + TaxBilling + " in the billing screen");

		for (int n = 0; n < ItemNamesBilling.size(); n++) {
			TextItemNamesBilling.add(ItemNamesBilling.get(n).getText().trim());
		}
		Thread.sleep(3000);
		String ExpectedFullItemName = ExpectedItemName.trim();
		String ExpectedTrimmedItemName = ExpectedFullItemName.contains("-")
				? ExpectedFullItemName.substring(ExpectedFullItemName.indexOf("-") + 2)
						: ExpectedFullItemName;
		String ActualItemName = ItemNamesBilling.get(1).getText().trim();

		asrt.assertEquals(ExpectedTrimmedItemName, ActualItemName, "Mismatch in item name, expected "
				+ ExpectedTrimmedItemName + "but got " + ActualItemName + "  in the billing screen.");

		String ExpectedTotalStr = ScannedSkuDataMap.get("SKU_" + (1) + "_Total");
		double ExpectedTotal = Double.parseDouble(TotalForEachItem.replaceAll("[^\\d.]", ""));
		String ActualTotalStr = ItemRateBilling.get(1).getText().trim();
		double ActualTotal = Double.parseDouble(ActualTotalStr.replaceAll("[^\\d.]", ""));
		System.out.println("ExpectedTotal" + TotalForEachItem);
		System.out.println("ActualTotal" + ActualTotal);

		asrt.assertEquals(ExpectedTotal, ActualTotal, 1.0, "Mismatch in total amounts (without tax),expected "
				+ ExpectedTotal + " but got " + ActualTotal + " in the billing screen.");

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		String ExpectedGrossWt = ExpectedGrossValue.replace("-", "").trim();
		Double ExpectedQty = Double.parseDouble(ExpectedGrossWt);
		String BillingQtySku = ItemQtyBilling.get(1).getText().replace("-", "").trim();
		Double ActualGrossWtSku = Double.parseDouble(BillingQtySku.replace("₹", "").replace(",", "").trim());
		asrt.assertEquals(ExpectedQty, ActualGrossWtSku, 1.0, "Mismatch in Qty value,expected " + ExpectedQty
				+ " but got " + ActualGrossWtSku + " in the Billing screen");
		asrt.assertEquals(ExpectedSubTotal, SubTotalBilling, 1.0, "Mismatch in Subtotal value, expected "
				+ ExpectedSubTotal + " but got " + SubTotalBilling + "in the Billing screen");
		asrt.assertEquals(TotalAmountBilling, ActualTotalAmount, 1.0, "Mismatch in Total amount value, expected "
				+ TotalAmountBilling + " but got " + ActualTotalAmount + " in the Billing screen");

		// Step 10: Save to estimate
		// Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Back"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));

		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(4000);
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC95_ProformaInvoice");
		Thread.sleep(4000);
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FirstInvoice + "";
		String TaxableValue = SkuItemRate;
		String InvoiceTotal = Double.toString(Double.parseDouble(TaxableValue.replaceAll("[^\\d.]", "").trim())
				+ Double.parseDouble(Tax.replaceAll("[^\\d.]", "").trim()));

		String TotalAdjustment = Double.toString(Adjustment);
		String Gst = Tax;
		String TotalAmnt = "";
		Thread.sleep(3000);
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProformaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, TotalAdjustment, NetTotal, TotalAmnt);

		// Step 11:Recall estimate from cashier
		// Expected Result:Verify whether the recalled item is the same as the item in
		// the cart.
		// Note:Cashier Screen after recall, *No of Product lines, *Displayed Item Name,
		// *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal,
		// *TAX, *Total Amount

		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2", "text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		// Step 12:Select estimate and click on Recall estimation button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		appUtils.ValidateBillingAndRecallScreenData(EstmnNumber, LinesCount, TextItemNamesBilling, TextItemQtyBilling,
				SubTotal, Tax, NetTotal);

		// Step 13: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_TransactionType2);

		// Step 14: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 15: Choose a Sales Representative
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC = base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		Map<String, String> SkuBillingScrnTbleData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SkuNetTotal = SkuBillingScrnTbleData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTbleData.get("LinesCount");

		// Step 16: Click on amount
		// Select payment method(cash/card)
		appUtils.PaymentAfterRecallEstimate(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC97_PaymentMethod);

		// Step 17:Select a Payment method(Cash or Card)
		// Step 18: Post exchange invoice(sale)
		// Expected Result: Check final invoice details,* save receipt id for future
		// reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC95_SaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";

		String GrossAmount = SkuItemRate;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String OGAdjustment = null;
		String TotalDiamond = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_TotalDiamond;
		String PaymentHDFCCard = PaymentAmountHDFC;
		String SalesPdfPath = SaleInvoicePath;
		String AdvanceAmountFinalInvoice = Double.toString(Adjustment);
		TotalNetWeight = ExpectedGrossWt;
		TotalGrossWeight = ExpectedGrossWt;
		String NetValue = SkuNetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(SalesPdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, OGAdjustment, AdvanceAmountFinalInvoice, SkuList, ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:-" + PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Offer advance settlement with board rate less than fixed
	// rate
	// Automation ID : TC86
	// Author: Christy Reji
	// </summary>
	public void TC86_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {

		// Prerequisite: . Customer must be having offer advance
		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		Map<String, String> AdvanceDetails = appUtils.CreateWeightBookedOfferAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_CustomerID,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_Pieces,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_GrossWeight,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_Company,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_Location,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_DepositPercent);

		String OrderId = AdvanceDetails.get("OrderId");
		String BoardRate = AdvanceDetails.get("FixedGoldRate");
		String WillingToPayAmount = AdvanceDetails.get("WillingToPayAmount");
		String DepositAmnt = WillingToPayAmount;

		// Step 3: Select customer
		// Step 4: Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_CustomerID,
				UtilityTestData.MenuBarOptions[5]);

		// Step 5: Click on customer adjustments-->order advance adjustment
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));
		wait.until(ExpectedConditions.presenceOfElementLocated(
				NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Order Advance Adjustment"))).click();

		// Step 6: Choose advance from "Customer order no" dropdown->enter the
		// adjustment amount
		// Step 7:Select the required advance
		// Expected :The balance amount and adjustment amount for the selected advances
		// should be shown correctly
		wait.until(ExpectedConditions.presenceOfElementLocated(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Sel_OrderNumber("availableTranscodeOptions")));
		try {
			base.selectorByVisibleText(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
					.Sel_OrderNumber("availableTranscodeOptions"),
					OrderId);
			String SelectedValue = base.getTheSelectedTextInDropDown(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
					.Sel_OrderNumber("availableTranscodeOptions"));
			Assert.assertEquals(SelectedValue, OrderId, "Order ID selection mismatch");
		} catch (Exception e) {
			Assert.fail("Order ID is not present: " + OrderId);
		}

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", OrderId));
		String BalanceAmount = base
				.GetAttribte(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAndAdjustmentAmnt("BalanceAmount"), "value");
		String FirstAdjustmentAmount = base
				.GetAttribte(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAndAdjustmentAmnt("InputAmount"), "value");

		asrt.assertEquals(Double.parseDouble(BalanceAmount), Double.parseDouble(DepositAmnt),
				"Mismatch in Balance amount, Expected " + DepositAmnt + " but got " + BalanceAmount
				+ " in the Order Adjustment page");
		asrt.assertEquals(Double.parseDouble(FirstAdjustmentAmount), Double.parseDouble(BalanceAmount),
				"Mismatch in Balance amount, Expected" + BalanceAmount + " but got" + FirstAdjustmentAmount
				+ " in the Order Adjustment page");

		base.setData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAndAdjustmentAmnt("InputAmount"), BalanceAmount);
		String AdjustmentAmount = base
				.GetAttribte(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAndAdjustmentAmnt("InputAmount"), "value");

		// Step 8: Click on add to cart
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		List<String> AdjustedName = base
				.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5"))
				.stream().map(text -> text.replaceAll("[\u20B9,=:\\-]", "").replaceAll("\\s{2,}", " ").trim())
				.collect(Collectors.toList());
		List<String> AdjustedQty = base
				.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5"))
				.stream().map(text -> text.replaceAll("[\u20B9,=:\\-]", "").replaceAll("\\s{2,}", " ").trim())
				.collect(Collectors.toList());
		List<String> SKUGrossAmount = base
				.GetElementTexts(
						NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5"))
				.stream().map(text -> text.replaceAll("[\u20B9,=:\\-]", "").replaceAll("\\s+", "").trim())
				.collect(Collectors.toList());
		String ExpectedAdjustedName = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_DepositAdjustment;
		String ExpectedAdjustedQty = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_DepositAdjustmentQty;

		Assert.assertEquals(AdjustedName.get(0), ExpectedAdjustedName,
				"The Adjusted name from billing page :" + AdjustedName.get(0)
				+ " mismatch with expected adjusted name : " + ExpectedAdjustedName + " from offer advance");
		Assert.assertEquals(AdjustedQty.get(0), ExpectedAdjustedQty,
				"The Adjusted Qty from billing page :" + AdjustedQty.get(0) + " mismatch with expected adjusted Qty : "
						+ ExpectedAdjustedQty + " from offer advance");

		// Less than fixed rate-ERP
		String ErpGoldRateFor24K = erpUtils.ToUpdateMetalRateWithLowerValue(BoardRate, "22k");
		Thread.sleep(120000);
		Thread.sleep(120000);
		// Step 9: Scan any SKU and click on Add to cart button
		// Step 10: Select any one of the sales agent

		List<String> SkuList = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_ProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_MetalType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation(
				"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		List<String> ProductNames = new ArrayList<>();
		List<String> ProductQtyList = new ArrayList<>();
		List<String> ProductTotalList = new ArrayList<>();
		ProductNames.add(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_DepositAdjustment);
		ProductQtyList.add(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC86_DepositAdjustmentQty);
		ProductTotalList.add(AdjustmentAmount);
		for (int i = 0; i < SkuList.size(); i++) {
			int SkuNumber = i + 1;
			String Sku = SkuList.get(i);
			appUtils.SKUIngridentTableValues(Sku, SkuNumber, ScannedSkuDataMap);
			ProductNames.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_HeaderName"));
			ProductQtyList.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_GrossWeight"));
			ProductTotalList.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_Total"));
			double ActualRgRateInItemDetailsPge = Double
					.parseDouble(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_RateRG"));
			String Purity = ScannedSkuDataMap.get("SKU_" + SkuNumber + "_Purity");
			if (Purity.contains("22")) {
				double ExpectedCurrentRateFor22K = (Double.parseDouble(ErpGoldRateFor24K) * 22) / 24;
				asrt.assertEquals(ActualRgRateInItemDetailsPge, ExpectedCurrentRateFor22K, 1.0,
						"RG rate mismatch: Expected is " + ExpectedCurrentRateFor22K + " but got "
								+ ActualRgRateInItemDetailsPge + " in item details page");
			}
			if (Purity.contains("18")) {
				double ExpectedCurrentRateFor18K = (Double.parseDouble(ErpGoldRateFor24K) * 18) / 24;
				asrt.assertEquals(ActualRgRateInItemDetailsPge, ExpectedCurrentRateFor18K, 1.0,
						"RG rate mismatch: Expected is " + ExpectedCurrentRateFor18K + " but got "
								+ ActualRgRateInItemDetailsPge + " in item details page");
			}
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuNumber);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuNumber);
		}
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = TransactionDataLine.get("Subtotal").replaceAll("[^\\d.]", "").trim();
		String Discount = TransactionDataLine.get("Discount");
		String TotalGrossWeight = TransactionDataLine.get("GrossWeight");
		String TotalNetWeight = TransactionDataLine.get("NetWeight");
		String Tax = TransactionDataLine.get("Tax").replaceAll("[^\\d.]", "").trim();
		String TotalAmount = TransactionDataLine.get("TotalAmount").replaceAll("[^\\d.]", "").trim();
		String NetTotal = TransactionDataLine.get("NetTotal").replaceAll("[^\\d.]", "").trim();
		String LinesCount = TransactionDataLine.get("LinesCount");
		List<String> ActualNames = mathUtils.ValidateTransactionLineCalculationsReturnProd(TransactionDataLine,
				ScannedSkuDataMap);
		String SKUName = ActualNames.toString().replace("[", "").replace("]", "").trim();

		// Step 11:In Billing, Click on "Save to Estimate" button
		// Step 12:Click on Save esimation
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc("buttonsContainer positionRelative", "Back"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String ActualEstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// Validation
		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC86_ProformaInvoice");
		Thread.sleep(2000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst = Tax;
		String TaxableValue = ProductTotalList.get(1);
		double CalculatedInvoice = Double.parseDouble(Gst) + Double.parseDouble(TaxableValue);
		String InvoiceTotal = String.valueOf(CalculatedInvoice);
		String TotalAdjustment = String.format("%.2f", Double.parseDouble(AdjustmentAmount.trim()));
		String TotalAmt = "";
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProFormaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, TotalAdjustment, NetTotal, TotalAmt);

		// Step 13:Click on Cash and then click on Recall estimate
		// Step 14: Select estimate and click on Recall estimation button
		// Expected : Verify whether the recalled item is the same as the item in the
		// cart.
		// Note:-17.Cashier Screen after recall,*No of Product lines,*Displayed Item
		// Name, *Displayed Quantity*Displayed TOTAL (without Tax)
		// *Displayed Subtotal *TAX *Total Amount

		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2", "text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		String ExpectedEstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));

		Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,
				"Mismatch in Estimation number Actual is " + ActualEstmnNumber + " and Expected Estimation number"
						+ ExpectedEstmnNumber + " in recall estimation page");

		// Validation Recall page
		List<String> ExpectedItemNames = Arrays.asList(ExpectedAdjustedName, SKUName).stream().map(Object::toString)
				.collect(Collectors.toList());
		List<String> ExpectedGrossWeights = ProductQtyList;

		appUtils.RecallEstimateItemValidation(ExpectedItemNames, ProductQtyList, ExpectedItemNames,
				ExpectedGrossWeights, Double.parseDouble(SubTotal), Double.parseDouble(Tax),
				Double.parseDouble(TotalAmount), Integer.parseInt(LinesCount), "Deposit Adjustment");

		// Step 15: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_TransactionType);

		// Step 16: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 17: Choose a sales representative
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		// Step 18 : click on amount
		// Step 19: Select payment method method (cash/card)
		String PaymentAmount = appUtils.PaymentAfterRecallEstimate("HDFC");
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 20 Post invoice
		// Expected : Check final invoice details
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC86_FinalSaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";
		String GrossAmount = String.format("%.2f", Double.parseDouble(SKUGrossAmount.get(0).trim()));
		;
		String TotalDiscount = Discount;
		String TotalQtyPcs = String.valueOf(ActualNames.size());
		String Adjustment = TotalAdjustment;
		String PaymentHDFCCard = PaymentAmount;
		String SalesPdfPath = SaleInvoicePath;
		TotalNetWeight = TotalNetWeight;
		double totalGrossWeightVal = Double.parseDouble(TotalGrossWeight);
		double adjustedQtyVal = Double.parseDouble(ExpectedAdjustedQty);
		String PDFGrossWt = String.format("%.3f", totalGrossWeightVal + adjustedQtyVal);
		String NetValue = NetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(SalesPdfPath, GrossAmount, TotalDiscount, "",
				TotalQtyPcs, TotalNetWeight, PDFGrossWt, TaxableValue, NetValue, PaymentHDFCCard, Adjustment, SkuList,
				ScannedSkuDataMap);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Offer advance settlement with reserved SKU and other one or
	// two SKU
	// Automation ID : TC85
	// Author: Vishnu Manoj K
	// </summary>
	public void TC85_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
		// Step 1.Login to POS
		// Step 2: Click on Transaction button
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// PreRequesite
		// Step 1.Login to POS
		// Step 2: Click on Transaction button
		Thread.sleep(5000);
		appUtils.HamBurgerButtonClick("iconShop");
		// base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon
		// center iconShop"));

		// Select SKU Number
		List<String> GoldSku = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_SKUCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_TransferTypeGold,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_FromCounterGold,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_MetalTypeGold);
		System.out.println("GoldSku" + GoldSku);
		List<String> SkuList = new ArrayList<>();
		SkuList.addAll(GoldSku);
		String SkuNumber = SkuList.get(0);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation(
				"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
		Thread.sleep(3000);
		base.setDataWithoutClear(NormalSaleGoldandSilverObj.Edt_Name("Search or enter quantity"), SkuNumber);
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
		base.setZoom(driver, 40);
		double ExpectedTotalCValueInItemDetailsPage = Double
				.parseDouble(base.GetAttribte(NormalSaleGoldandSilverObj.Ele_Name("valueInput"), "value"));
		double ExpectedGrossWeightInItemDetailspage = Double
				.parseDouble(base.GetAttribte(NormalSaleGoldandSilverObj.Ele_Name("grossWeightInput"), "value"));
		double ExpectedTotalAmountInItemDetailPage = Double
				.parseDouble(base.GetAttribte(NormalSaleGoldandSilverObj.Ele_Name("totalInput"), "value"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation(
				"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));

		// Step 3: Click on advance
		// Step 4: Select customer order
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_return("Customer Order"));

		// Step 5: Search customer and click on add to customer order
		appUtils.SearchByCustomerIDForGPP(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_CustomerPhoneNo,
				UtilityTestData.AddToCustomerOrder);

		// Step 6: Enter the SKU number in the field and click enter
		// Expected Result: Verify the product details *Gross wt *CValue *Total amount
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_ScanSku("AddSKUAutomatic "),
				SkuNumber);
		base.pressKey(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_ScanSku("AddSKUAutomatic "),
				"ENTER");
		Thread.sleep(2000);
		double ActualGrossWeightInCustomerOrderPage = Double
				.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column3", "h4 ellipsis cell")));
		double ActualCValueInCustomerOrderPage = Double
				.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column4", "h4 ellipsis cell")));
		double TotalInCustomerOrderPage = Double
				.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column7", "h4 ellipsis cell")));
		double ActualTotalAmountInCustomerOrderPage = Double.parseDouble(
				base.GetValue(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Ele_GPPCustAccnt("TotAmtInput")));

		// Step 7: Select the 'Is offer order' checkbox
		base.buttonClick(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_CheckBox("checkbox",
				"IsofferChkBox"));

		// Step 8: Choose the company and invent location
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col",
				"viewModel.IsOrderEntry"), UtilityTestData.Company[0]);
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_IsOrder("col stretch", "viewModel.IsOrderEntry"), UtilityTestData.Location[0]);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Sales person"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_SalesPerson);

		// Step 9: Select the order line
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		// Step 10: Select the ingredient line
		base.scrollToElement(LoginPageObj.Ele_ErrorMessage("win-itembox"));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		// Step 11: Click on willing to pay button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj
				.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(NormalSaleGoldandSilverObj
				.Btn_MenuBarOptions("MGDRetail_MGDRetail_Extensions_OrderView_appWillToPayCommandCommand"));

		// Step 12: Enter the amount
		String WillingToPayAmountStr = Double.toString(ActualTotalAmountInCustomerOrderPage
				* AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_DepositPercent);
		Thread.sleep(1000);
		double WillingToPayAmountValue = Double.parseDouble(WillingToPayAmountStr);
		double FloorValue = Math.floor(WillingToPayAmountValue);
		double DecimalPart = WillingToPayAmountValue - FloorValue;

		System.out.println("FloorValue: " + FloorValue);
		System.out.println("DecimalPart: " + DecimalPart);
		if (DecimalPart > 0.5) {
			WillingToPayAmountValue = Math.ceil(WillingToPayAmountValue); // Round up
			System.out.println("AdjustedGoldRate if DecimalPart>0.5 " + WillingToPayAmountValue);
		} else if (DecimalPart == 0.5) {
			WillingToPayAmountValue = FloorValue + 0.5; // Keep 0.5
			System.out.println("AdjustedGoldRate if DecimalPart==0.5 " + WillingToPayAmountValue);
		} else {
			WillingToPayAmountValue = Math.floor(WillingToPayAmountValue); // Round down
			System.out.println("AdjustedGoldRate if DecimalPart<0.5 " + WillingToPayAmountValue);
		}
		String WillingToPayAmount = String.valueOf(WillingToPayAmountValue);
		base.setData(
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Amount("value: WILLAMT", "number"),
				WillingToPayAmount);
		Thread.sleep(1000);
		String ExpectedOfferExpiryDate = base.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: SDATE"));
		String ExpectedValidityDays = base.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: SDAYS"))
				.replaceAll("\\D+", "");
		;

		// Step 13: Click on apply button
		base.buttonClick(LoginPageObj.Btn_SignInButton("APPLY"));

		// Step 14: Click on edit line
		Thread.sleep(3000);
		base.ClickCondition(
				NormalSalesReturnGoldSilverReturnSaleCounterObj.Txt_Sku("win-structuralnodes win-selectionmode"));
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj
				.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(NormalSaleGoldandSilverObj
				.Btn_MenuBarOptions("MGDRetail_MGDRetail_Extensions_OrderView_EditSlappBarCommandCommand"));

		// Step 15: Enter the line delivery date
		DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate ExpiryDate = LocalDate.parse(ExpectedOfferExpiryDate, Formatter);
		LocalDate TargetDate = ExpiryDate.minusDays(1);
		String Day = Integer.toString(TargetDate.getDayOfMonth());
		String Month = Integer.toString(TargetDate.getMonthValue() - 1);
		String Year = Integer.toString(TargetDate.getYear());
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-date"), Day);
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-month"), Month);
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-year"), Year);

		// Step 16: Enter remarks
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Remarks("value: Remarks"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_Remarks);

		// Step 17: Click on OK button
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		// Step 18: Add nominee details, Select a sales person
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj
				.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj
				.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_NomineeDetailsAppBarCommandCommand"));
		Thread.sleep(1000);
		base.setData(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: NomineeName"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_NomineeName);
		base.selectorByVisibleText(
				GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: NomineeRelationArr"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_NomineeRelation);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_Name("NomineeChkBox"));
		String Address1 = base.GetText(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: NomineeAddress"));
		if (Address1 == "") {
			base.setData(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: NomineeAddress"),
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_NomineeAdresss);
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		} else {
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}

		// Step 19: Click on save button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj
				.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_appBarCommandCommand"));
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("YES"));
		String Message = base.GetText(NormalSaleDiamondandPlatinumObj.Ele_GrossWeight("text: content"));
		String OrderId = Message.split(" - ")[1].split(" ")[0];
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		System.out.println("OrderId" + OrderId);

		// Step 20: Go to transactions
		// Step 21: Search customer
		// Step 22: Click on add to sale button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation(
				"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_CustomerPhoneNo,
				UtilityTestData.MenuBarOptions[0]);

		// Step 23: Choose advance-->advance collection
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader")));
		wait.until(ExpectedConditions.presenceOfElementLocated(LoginPageObj.Edt_AlertText("Advance"))).click();
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_return("Advance Collection"));

		// Step 24: Choose type as order
		// base.buttonClick(NormalSaleGoldandSilverObj.Sel_DepositType("OrderTypeOptions"));
		Thread.sleep(5000);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("OrderTypeOptions"), "Order");

		// Step 25: Select the transaction number from the drop down
		// base.buttonClick(NormalSaleGoldandSilverObj.Sel_DepositType("TransTypeOptions"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("TransTypeOptions"), OrderId);
		System.out.println("WillingToPayAmount" + WillingToPayAmount);

		// Step 26: Enter the deposit amount
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("DepAmount"), WillingToPayAmount);
		base.selectorByVisibleText(
				GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_SalesPerson);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Deposit"));

		// Step 27: Click on the Amount
		Thread.sleep(3000);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base
				.GetAttribte(NormalSaleGoldandSilverObj
						.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"), "value")
				.replace(",", "");
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Close"));

		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
		// Step 3: Select customer
		// Step 4: Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_CustomerId,
				UtilityTestData.MenuBarOptions[5]);

		// Step 5: Click on customer adjustments-->order advance adjustment
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));
		wait.until(ExpectedConditions.presenceOfElementLocated(
				NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Order Advance Adjustment"))).click();

		// Step 6: choose advance from dropdown-->enter the adjustment amount
		// Expected Result :*The balance amount and adjustment amount for the selected
		// advances should be shown correctly
		wait.until(ExpectedConditions.presenceOfElementLocated(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Sel_OrderNumber("availableTranscodeOptions")));
		try {
			base.selectorByVisibleText(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
					.Sel_OrderNumber("availableTranscodeOptions"),
					OrderId);// OrderId);
			String selectedValue = base.getTheSelectedTextInDropDown(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
					.Sel_OrderNumber("availableTranscodeOptions"));
			Assert.assertEquals(selectedValue, OrderId, "Order ID selection mismatch");
		} catch (Exception e) {
			Assert.fail("Order ID is not present: " + OrderId);
		}
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", OrderId));
		String BalanceAmount = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_TotalAmount("Balance amount", "h4 ellipsis cell"));
		String AdvanceRate = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_TotalAmount("Metal Rate", "h4 ellipsis cell"));
		String AdjustmentAmount = base
				.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.InputAmount"));
		Thread.sleep(1000);

		asrt.assertEquals(BalanceAmount, AdjustmentAmount, "Amount mismatch: Balance amount is " + BalanceAmount
				+ " and adjustment amount is " + AdjustmentAmount + " in advance adjustment page");

		// Step 7: Click on add to cart
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// Step 8: Scan reserved SKU as well as another SKU and click add to cart button
		// Note : unlock reserve SKU before scanning
		// Expected Result: Check calculationNote:Billing Screen*No of Product
		// lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without
		// Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SelectedGoldSkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;
		wait.until(ExpectedConditions.presenceOfElementLocated(LoginPageObj.Edt_AlertText("Purchase"))).click();
		base.ClickCondition(LoginPageObj.Edt_AlertText("Reserved SKU Release"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("OrderTypeOptions"), "Order");
		base.setData(NormalSaleGoldandSilverObj.Edt_TextField("stretch", "text"), GoldSku.get(0));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("dataListLine"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("reason"), "Settlement");
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_searchCommandCommand"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		appUtils.HamBurgerButtonClick("iconShop");

		SelectedGoldSkuList.addAll(GoldSku);

		// Gold SKUs
		List<String> GoldSkuList = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_SKUCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_MetalType);

		SelectedGoldSkuList.addAll(GoldSkuList);
		appUtils.HamBurgerButtonClick("iconShop");
		String CstGrossWt = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_GrossWeight;

		erpUtils.ResetRemainingReturnWeight();
		for (String Sku : SelectedGoldSkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuCounter);
			SkuCounter++;
		}

		String CstProdQty = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_GrossPieces;
		String ExpectedAdvanceName = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_AdvanceName;
		Map<String, String> DataMap = mathUtils.ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(
				ScannedSkuDataMap, CstProdQty, AdjustmentAmount, ExpectedAdvanceName);
		String TaxBillingPge = DataMap.get("Tax");
		String InvoiceTotal = DataMap.get("InvoiceTotal");
		String TaxableValue = DataMap.get("TaxableValue");
		String AmountDue = DataMap.get("AmountDue");
		String LinesCount = DataMap.get("LinesCount");
		String TotalGrossWt = DataMap.get("TotalGrossWtSku");

		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);

		// Step 9: Click on save to estimate-->Recall estimate from cashier screen
		// Expected Result: Verify the estimate details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc("buttonsContainer positionRelative", "Back"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstimationNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		// PDF Verification Step
		Thread.sleep(4000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC85_ProformaInvoice");
		Thread.sleep(5000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst = TaxBillingPge;
		String TotalAmnt = TaxableValue;
		String NetTotal = AmountDue;
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProFormaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, AdjustmentAmount, NetTotal, TotalAmnt);
		Thread.sleep(2000);

		// Step 10: Select estimate and click on recall estimation
		// Step 11: Select the Transaction Type as Sales
		// Step 12: Click on Process Estimation
		// Step 13: Choose a sales representative
		// Expected Result: Verify whether the recalled item is the same as the item in
		// the cart.
		// Note:-Cashier Screen after recall*No of Product lines *Displayed Item
		// Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed
		// Subtotal*TAX*Total Amount
		appUtils.RecallEstimateProcess(EstimationNumber,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_TransactionType);
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SelectedGoldSkuList,
				ScannedSkuDataMap);
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		Map<String, String> DataMapAfterRecall = mathUtils.ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(
				ScannedSkuDataMap, CstProdQty, AdjustmentAmount, ExpectedAdvanceName);
		Map<String, String> TransactionDataLineRecall = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		System.out.println("Values in TransactionDataLineRecall: " + new HashSet<>(TransactionDataLineRecall.values()));
		System.out.println("Values in TransactionDataLine: " + new HashSet<>(TransactionDataLine.values()));

		asrt.assertEquals(new HashSet<>(TransactionDataLineRecall.values()),
				new HashSet<>(TransactionDataLine.values()),
				"Mismatch detected: Recalled item and cart item are not the same.");

		// Step 14.Click on the Amount
		// Step 15.Select a Payment method(Cash or Card)
		appUtils.PaymentAfterRecallEstimate(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC85_PaymentMethod);

		// Step 16: Post exchange invoice(sale)
		// Expected Result: Check final invoice details,* save receipt id for future
		// reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC85_SaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";
		String GrossAmount = TaxableValue;
		String TotalDiscount = Discount;
		String TotalQtyPcs = String.valueOf(Integer.parseInt(LinesCount) - 1);
		String Adjustment = AdjustmentAmount;
		String TotalDiamond = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_TotalDiamond;
		String PaymentHDFCCard = AmountDue;
		String SalesPdfPath = SaleInvoicePath;
		String AdvanceAmountFinalInvoice = WillingToPayAmount;
		String TotalGrossWeight = TotalGrossWt;
		String NetValue = AmountDue;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(SalesPdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, Adjustment, AdvanceAmountFinalInvoice, SelectedGoldSkuList, ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:- " + PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Normal advance settlement with board rate greater than
	// fixed rate
	// Automation ID : TC82
	// Author: Sangeetha M S
	// </summary>
	public void TC82_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {
		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Click on Transaction button
		appUtils.HamBurgerButtonClick("iconShop");

		// Prerequisite 1:Customer must have a normal advance
		String AdvanceAmount = appUtils.CreateNormalAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC82_CustomerNo,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC82_AdvanceAmount,
				UtilityTestData.SalePersonName, UtilityTestData.DueYear);

		// Step 3: Select customer
		// Step 4: Click on add to estimation button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC82_CustomerNo,
				UtilityTestData.MenuBarOptions[5]);

		// Step 5: Click on customer adjustments button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));

		// Step 6: Click on normal Advance settlement
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Normal Advance Adjustment"));

		// Step 7: Select advance and click on cart button
		// Expected: The balance amount and adjustment amount for the selected advances
		// should be shown correctly
		Thread.sleep(4000);
		base.actionClick(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"));
		base.actionSetData(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"),
				UtilityTestData.Terminal);
		base.pressKey(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"), "ENTER");
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));
		String BalanceAmount = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_TotalAmount("Balance amount", "h4 ellipsis cell"));
		String AdvanceRate = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_TotalAmount("Metal Rate", "h4 ellipsis cell"));
		String AdjustmentAmount = base
				.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.InputAmount"));
		Thread.sleep(1000);

		asrt.assertEquals(BalanceAmount, AdjustmentAmount, "Amount mismatch: Balance amount is " + BalanceAmount
				+ " and adjustment amount is " + AdjustmentAmount + " in advance adjustment page");

		base.buttonClick(NormalSaleGoldandSilverObj
				.Btn_Estimation("win-commandingsurface-overflowbutton win-appbar-overflowbutton"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_return("Add to cart"));

		double Adjustment = Double.parseDouble(AdjustmentAmount);
		double Quantity22k = Adjustment / Double.parseDouble(AdvanceRate);

		double MetalRate18k = (Double.parseDouble(AdvanceRate) * 18) / 22;
		double Quantity18k = Adjustment / MetalRate18k;

		// Prerequisite 2.Normal Advance Board Rate should be greater than Fixed Board
		// rate
		String ErpGoldRateFor24K = erpUtils.SetMetalRateInERP(AdvanceRate, "22k");
		Thread.sleep(120000); // Wait needed for ERP Gold Rate to Change

		double Current22kRate = (Double.parseDouble(ErpGoldRateFor24K) * 22) / 24;
		double Current18kRate = (Double.parseDouble(ErpGoldRateFor24K) * 18) / 24;

		// Step 8: Enter OTP
		// Step 9: Scan Gold SKU and click on Add to cart button
		// Expected : Check calculation
		// Step 10: Select any one of the sales agent
		List<String> SkuList = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC82_ProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC82_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC82_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC82_MetalType);
		appUtils.HamBurgerButtonClick("iconShop");
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		List<String> ProductNames = new ArrayList<>();
		List<String> ProductQtyList = new ArrayList<>();
		List<String> ProductTotalList = new ArrayList<>();
		ProductNames.add(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC82_DepositAdjustment);
		ProductQtyList.add(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC82_DepositAdjustmentQty);
		ProductTotalList.add(AdjustmentAmount);
		for (int i = 0; i < SkuList.size(); i++) {
			int SkuNumber = i + 1;
			String Sku = SkuList.get(i);
			appUtils.SKUIngridentTableValues(Sku, SkuNumber, ScannedSkuDataMap);
			ProductNames.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_HeaderName"));
			ProductQtyList.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_GrossWeight"));
			ProductTotalList.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_Total"));
			String ExpectedNetValue = ScannedSkuDataMap.get("SKU_" + SkuNumber + "_TotalRGWeight");
			double ActualRgRateInItemDetailsPge = Double
					.parseDouble(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_RateRG"));
			String Purity = ScannedSkuDataMap.get("SKU_" + SkuNumber + "_Purity");
			if (Purity.equalsIgnoreCase("22k")) {
				if (Double.parseDouble(ExpectedNetValue) > Quantity22k) {
					double Remainingwgt = (Double.parseDouble(ExpectedNetValue) - Quantity22k);
					double TotalAmount = (Quantity22k * Double.parseDouble(AdvanceRate))
							+ (Remainingwgt * Current22kRate);
					double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0)
							/ 100.0;

					asrt.assertEquals(ActualRgRateInItemDetailsPge, AverageRate, 1.0,
							"Mismatch  In Average Rate,Expected" + AverageRate + "but got"
									+ ActualRgRateInItemDetailsPge + "in return page");
					System.out.print("Average rate of the SKU is:" + AverageRate);
				} else {
					asrt.assertEquals(ActualRgRateInItemDetailsPge, Current22kRate, 1.0,
							"Mismatch  In Average Rate,Expected" + Current22kRate + "but got"
									+ ActualRgRateInItemDetailsPge + "in return page");
				}
			}

			if (Purity.equalsIgnoreCase("18k")) {
				if (Double.parseDouble(ExpectedNetValue) > Quantity18k) {
					double Remainingwgt = (Double.parseDouble(ExpectedNetValue) - Quantity18k);
					double TotalAmount = (Quantity18k * MetalRate18k) + (Remainingwgt * Current18kRate);
					double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0)
							/ 100.0;

					asrt.assertEquals(ActualRgRateInItemDetailsPge, AverageRate, 1.0,
							"Mismatch  In Average Rate,Expected" + AverageRate + "but got"
									+ ActualRgRateInItemDetailsPge + "in return page");
					System.out.print("Average rate of the SKU is:" + AverageRate);
				} else {
					asrt.assertEquals(ActualRgRateInItemDetailsPge, Current18kRate, 1.0,
							"Mismatch  In Average Rate,Expected" + Current18kRate + "but got"
									+ ActualRgRateInItemDetailsPge + "in return page");
				}
			}
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuNumber);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuNumber);
		}

		// Note:Billling Screen No of Product lines, Displayed Item Name, Displayed
		// Quantity, Displayed TOTAL (without Tax), Displayed Subtotal, TAX and Total
		// Amount
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = TransactionDataLine.get("Subtotal");
		String NetTotal = TransactionDataLine.get("NetTotal");
		String Discount = TransactionDataLine.get("Discount");
		String TotalNetWeight = TransactionDataLine.get("NetWeight");
		String Tax = TransactionDataLine.get("Tax");
		String TotalAmount = TransactionDataLine.get("TotalAmount");
		String ActualLinesCount = TransactionDataLine.get("LinesCount");
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		String ExpectedLinesCount = Integer.toString(AllProductRows.size());
		asrt.assertEquals(ActualLinesCount, ExpectedLinesCount, "Lines count mismatch: Expected is "
				+ ExpectedLinesCount + " but found " + ActualLinesCount + " in billing page");

		double SubTotalSum = 0.00;
		for (int i = 0; i < AllProductRows.size(); i++) {
			List<WebElement> ItemName = base
					.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
			String ActualItemNameInBillingPge = ItemName.get(i).getText();
			if (i == 1) {
				String ExpectedItemNameInBillingPge = ProductNames.get(i)
						.substring(ProductNames.get(i).indexOf("-") + 1).trim();
				asrt.assertEquals(ActualItemNameInBillingPge, ExpectedItemNameInBillingPge,
						"Item name mismatch: Expected is " + ExpectedItemNameInBillingPge + " but got "
								+ ActualItemNameInBillingPge + " in billing page");
			} else {
				String ExpectedItemNameInBillingPge = ProductNames.get(i).trim();
				asrt.assertEquals(ActualItemNameInBillingPge, ExpectedItemNameInBillingPge,
						"Item name mismatch: Expected is " + ExpectedItemNameInBillingPge + " but got "
								+ ActualItemNameInBillingPge + " in billing page");
			}
			List<WebElement> Quantity = base
					.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5"));
			String ActualQuantityInBillingPge = Quantity.get(i).getText().replace("-", "");
			String ExpectedQuantityInBillingPge = ProductQtyList.get(i);
			double ActualQtyInBillingPge = Double.parseDouble(ActualQuantityInBillingPge);
			double ExpectedQtyInBillingPge = Double.parseDouble(ExpectedQuantityInBillingPge);
			asrt.assertEquals(ActualQtyInBillingPge, ExpectedQtyInBillingPge, 1.000, "Quantity mismatch: Expected is "
					+ ExpectedQtyInBillingPge + " but got " + ActualQtyInBillingPge + " in billing page");

			List<WebElement> TotalWithoutTax = base.GetElement(
					NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5"));
			String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replaceAll("[^\\d.-]", "")
					.trim();
			SubTotalSum = SubTotalSum + Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
			String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f",
					Double.parseDouble(ProductTotalList.get(i).replaceAll("[^\\d.-]", "").trim()));
			asrt.assertEquals(Double.parseDouble(ActualTotalWithoutTaxInBillingPge.replace("-", "")),
					Double.parseDouble(ExpectedTotalWithoutTaxInBillingPge), 1.0,
					"TotalWithoutTax mismatch: Expected is " + ExpectedTotalWithoutTaxInBillingPge + " but got "
							+ ActualTotalWithoutTaxInBillingPge + " in billing page");
		}
		String ExpectedSubTotalInBillingPge = String.format("%.2f", SubTotalSum).replaceAll("[^\\d.]", "").trim();
		;
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"))
				.replaceAll("[^\\d.]", "").trim();
		asrt.assertEquals(Double.parseDouble(ActualSubTotalInBillingPge),
				Double.parseDouble(ExpectedSubTotalInBillingPge), 1.0,
				"Subtotal mismatch: " + ExpectedSubTotalInBillingPge + " but found " + ActualSubTotalInBillingPge
				+ " in billing page");

		String ActualTaxInBillingPge = Tax.replaceAll("[^\\d.]", "").trim();
		double ActualTax = Double.parseDouble(ActualTaxInBillingPge);
		double ExpectedTax = Double.parseDouble(ProductTotalList.get(1)) * 0.03;
		String ExpectedTaxInBillingPge = String.format("%.2f", ExpectedTax);
		asrt.assertEquals(Double.parseDouble(ActualTaxInBillingPge), Double.parseDouble(ExpectedTaxInBillingPge), 1.0,
				"Tax amount mismatch: " + ExpectedTaxInBillingPge + " but found " + ActualTaxInBillingPge
				+ " in billing page");

		double ExpectedTotalAmountInBillingPge = Double.parseDouble(ActualSubTotalInBillingPge)
				+ Double.parseDouble(ActualTaxInBillingPge);
		double ActualTotalAmountInBillingPge = Double.parseDouble(TotalAmount.replaceAll("[^\\d.-]", "").trim());
		asrt.assertEquals(ActualTotalAmountInBillingPge, ExpectedTotalAmountInBillingPge, 1.0, "Total Amount mismatch: "
				+ ExpectedTotalAmountInBillingPge + " but found " + ActualTotalAmountInBillingPge + " in billing page");

		// Step 11:In Billing, Click on "Save to Estimate" button
		// Step 12 :Click on Save estimation
		// Expected: Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc("buttonsContainer positionRelative", "Back"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String ActualEstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC82_ProformaInvoice");
		Thread.sleep(2000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst = Tax;
		String TaxableValue = ProductTotalList.get(1);
		String InvoiceTotal = Double.toString(Double.parseDouble(ProductTotalList.get(1)) + ActualTax);
		String TotalAdjustment = AdjustmentAmount;
		String TotalAmt = "";
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProFormaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, TotalAdjustment, NetTotal, TotalAmt);

		// Step 13.Click on Cash and then click on Recall estimate
		// Step 14.Select the estimate and click on Recall estimation
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2", "text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		String ExpectedEstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));

		asrt.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,
				"Mismatch in Estimation number Actual is " + ActualEstmnNumber + " and Expected Estimation number"
						+ ExpectedEstmnNumber + " in recall estimation page");

		// Step 15: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC82_TransactionType);

		// Step 16: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 17: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		// Step 18: Click on the Amount
		// Expected: Cashier Screen after recall No of Product lines, Displayed Item
		// Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,
		// TAX and Total Amount
		// Step 19: Select a Payment method(Cash or Card)
		Map<String, String> TransactionDataLineInCashierScreen = appUtils.TransactionSKUsLinesVerify(SkuList,
				ScannedSkuDataMap);
		String TaxInCashierScreen = TransactionDataLineInCashierScreen.get("Tax");
		String TotalAmountInCashierScreen = TransactionDataLineInCashierScreen.get("TotalAmount");
		String ActualLinesCountInCashierScreen = TransactionDataLineInCashierScreen.get("LinesCount");

		List<WebElement> AllProductRowsInCashierScreen = base
				.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		String ExpectedLinesCountInCashierScreen = Integer.toString(AllProductRows.size());

		asrt.assertEquals(ExpectedLinesCountInCashierScreen, ActualLinesCountInCashierScreen,
				"Lines count mismatch: Expected is " + ExpectedLinesCountInCashierScreen + " but found "
						+ ActualLinesCountInCashierScreen + " in Cashier Screen");

		double SubTotalSumInCashierScreen = 0.00;
		for (int i = 0; i < AllProductRows.size(); i++) {
			List<WebElement> ItemName = base
					.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
			String ActualItemNameInCashierScreen = ItemName.get(i).getText();
			if (i == 1) {
				String ExpectedItemNameInCashierScreen = ProductNames.get(i)
						.substring(ProductNames.get(i).indexOf("-") + 1).trim();
				asrt.assertEquals(ExpectedItemNameInCashierScreen, ActualItemNameInCashierScreen,
						"Item name mismatch: Expected is " + ExpectedItemNameInCashierScreen + " but got "
								+ ActualItemNameInCashierScreen + " in Cashier Screen");
			} else {
				String ExpectedItemNameInCashierScreen = ProductNames.get(i).trim();
				asrt.assertEquals(ExpectedItemNameInCashierScreen, ActualItemNameInCashierScreen,
						"Item name mismatch: Expected is " + ExpectedItemNameInCashierScreen + " but got "
								+ ActualItemNameInCashierScreen + " in Cashier Screen");
			}
			List<WebElement> Quantity = base
					.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5"));
			String ActualQuantityInCashierScreen = Quantity.get(i).getText().replace("-", "");
			String ExpectedQuantityInCashierScreen = ProductQtyList.get(i);
			double ActualQtyInCashierScreen = Double.parseDouble(ActualQuantityInCashierScreen);
			double ExpectedQtyInCashierScreen = Double.parseDouble(ExpectedQuantityInCashierScreen);

			asrt.assertEquals(ExpectedQtyInCashierScreen, ActualQtyInCashierScreen, 1.0,
					"Quantity mismatch: Expected is " + ExpectedQtyInCashierScreen + " but got "
							+ ActualQtyInCashierScreen + " in Cashier Screen");

			List<WebElement> TotalWithoutTax = base.GetElement(
					NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5"));
			String ActualTotalWithoutTaxInCashierScreen = TotalWithoutTax.get(i).getText().replaceAll("[^\\d.-]", "")
					.trim();
			SubTotalSumInCashierScreen = SubTotalSumInCashierScreen
					+ Double.parseDouble(ActualTotalWithoutTaxInCashierScreen);
			String ExpectedTotalWithoutTaxInCashierScreen = String.format("%.2f",
					Double.parseDouble(ProductTotalList.get(i).replaceAll("[^\\d.-]", "").trim()));
			asrt.assertEquals(Double.parseDouble(ExpectedTotalWithoutTaxInCashierScreen),
					Double.parseDouble(ActualTotalWithoutTaxInCashierScreen.replace("-", "")), 1.0,
					"TotalWithoutTax mismatch: Expected is " + ExpectedTotalWithoutTaxInCashierScreen + " but got "
							+ ActualTotalWithoutTaxInCashierScreen + " in Cashier Screen");
		}
		String ExpectedSubTotalInCashierScreen = String.format("%.2f", SubTotalSumInCashierScreen)
				.replaceAll("[^\\d.]", "").trim();
		;
		String ActualSubTotalInCashierScreen = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"))
				.replaceAll("[^\\d.]", "").trim();
		asrt.assertEquals(Double.parseDouble(ExpectedSubTotalInCashierScreen),
				Double.parseDouble(ActualSubTotalInCashierScreen), 1.0,
				"Subtotal mismatch: " + ExpectedSubTotalInCashierScreen + " but found " + ActualSubTotalInCashierScreen
				+ " in Cashier Screen");

		String ActualTaxInCashierScreen = TaxInCashierScreen.replaceAll("[^\\d.]", "").trim();
		double ExpectedTaxCalculated = Double.parseDouble(ProductTotalList.get(1)) * 0.03;
		String ExpectedTaxInCashierScreen = String.format("%.2f", ExpectedTaxCalculated);
		asrt.assertEquals(Double.parseDouble(ActualTaxInCashierScreen), Double.parseDouble(ExpectedTaxInCashierScreen),
				1.0, "Tax amount mismatch: " + ExpectedTaxInCashierScreen + " but found " + ActualTaxInCashierScreen
				+ " in Cashier Screen");

		double ExpectedTotalAmountInCashierScreen = Double.parseDouble(ActualSubTotalInCashierScreen)
				+ Double.parseDouble(ActualTaxInCashierScreen);
		double ActualTotalAmountInCashierScreen = Double
				.parseDouble(TotalAmountInCashierScreen.replaceAll("[^\\d.-]", "").trim());
		asrt.assertEquals(ExpectedTotalAmountInCashierScreen, ActualTotalAmountInCashierScreen, 1.0,
				"Total Amount mismatch: " + ExpectedTotalAmountInCashierScreen + " but found "
						+ ActualTotalAmountInCashierScreen + " in Cashier Screen");

		String PaymentAmountForSale = appUtils.PaymentAfterRecallEstimate("HDFC");

		// Step 20: Post the Invoice
		// Expected: Check final invoice details
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC82TaxInvoice");
		Thread.sleep(2000);
		String InvoicePdfPath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice;
		String GrossAmount = ProductTotalList.get(1);
		String TotalDiscount = Discount;
		String TotalQtyPcs = Integer.toString(SkuList.size());
		String TotalDiamond = "";
		String NetValue = NetTotal;
		String PaymentHDFCCard = PaymentAmountForSale;
		String AdjustmentInInvoice = "";
		String TotalGrossWeight = ProductQtyList.get(1);
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(InvoicePdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, AdjustmentInInvoice, AdvanceAmount, SkuList, ScannedSkuDataMap);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

		Thread.sleep(1000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("Button1"));
	}

	// <summary>
	// Test Case Title: Normal advance settlement with multiple gold item
	// Automation ID: TC79
	// Author: Robin T. Abraham
	// </summary>

	public void TC79_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {
		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		appUtils.HamBurgerButtonClick("iconShop");

		// Valid SKU should be fetched and kept aside
		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> PurityOfItems = new ArrayList();
		List<Double> AllRates = new ArrayList<>();
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_ProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_MetalType);
		appUtils.HamBurgerButtonClick("iconShop");
		for (int i = 0; i < SkuListGold.size(); i++)

		{
			String CurrentGoldSku = SkuListGold.get(i);

			appUtils.SKUIngridentTableValues(CurrentGoldSku.toString(), (i + 1), SkuDetails);

			PurityOfItems.add(SkuDetails.get("SKU_" + (i + 1) + "_Purity"));

			for (int ItemIndex = 1;; ItemIndex++) {
				String Rate = "SKU" + (i + 1) + "_ITEM" + ItemIndex + "_RATE";

				if (SkuDetails.containsKey(Rate))

				{
					try

					{
						double SkuRate = Double.parseDouble(SkuDetails.get(Rate));

						AllRates.add(SkuRate);
					} catch (NumberFormatException e) {
						System.out.println("Rate not fetched");
					}
				} else {
					break;
				}
			}

			SkuDetails.put("SKU_" + (i + 1) + "_AllRates", AllRates.toString());
		}

		// For conversion of Rate
		String ItemPurity = PurityOfItems.get(0);
		double ExchangeRate = AllRates.get(0);
		double ExpectedGoldRate22k = 0;
		double ExpectedGoldRate18k = 0;
		if (ItemPurity.equalsIgnoreCase("18k")) {
			double ConvertedGoldRate = (ExchangeRate * 22) / 18;

			ExpectedGoldRate22k = Math.round(ConvertedGoldRate);

			ExpectedGoldRate18k = ExchangeRate;
		} else {
			ExpectedGoldRate22k = Math.round(ExchangeRate);
			ExpectedGoldRate18k = (ExchangeRate * 18) / 22;
		}
		System.out.print(ExpectedGoldRate22k);
		appUtils.VoidTransaction();

		// Step 2: Click on Transaction button
		// Prerequisite:Customer must be having a normal advance
		appUtils.HamBurgerButtonClick("iconShop");
		String AdvanceAmount = appUtils.CreateNormalAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_CustomerNo,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_AdvanceAmount,
				UtilityTestData.SalePersonName, UtilityTestData.DueYear);

		// Step 3: Select customer
		// Step 4: Click on Add to estimate button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_CustomerNo,
				UtilityTestData.MenuBarOptions[5]);

		// Step 5: Click on customer adjustments button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));

		// Step 6: click on normal advance adjustment button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Normal Advance Adjustment"));

		// Step 7: Select advance and click on cart button
		// Expected: The balance amount and adjustment amount for the selected advances
		// should be shown correctly
		Thread.sleep(4000);
		base.actionClick(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"));
		base.actionSetData(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"),
				UtilityTestData.Terminal);
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));
		String BalanceAmount = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_TotalAmount("Balance amount", "h4 ellipsis cell"));
		String AdvanceRate = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
				.Ele_TotalAmount("Metal Rate", "h4 ellipsis cell"));
		String AdjustmentAmount = base
				.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.InputAmount"));
		Thread.sleep(1000);
		String MetalRateFetch = base
				.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_AdvanceAdjustMetalRate("option", "Metal Rate", "gridcell", 0));
		double Quantity = Double.parseDouble(AdjustmentAmount) / Double.parseDouble(MetalRateFetch);
		double TotalBalance = Double.parseDouble(BalanceAmount);
		double Adjustment = Double.parseDouble(AdjustmentAmount);

		asrt.assertEquals(BalanceAmount, AdjustmentAmount, "Amount mismatch: Balance amount is " + BalanceAmount
				+ " and adjustment amount is " + AdjustmentAmount + " in advance adjustment page");

		base.buttonClick(NormalSaleGoldandSilverObj
				.Btn_Estimation("win-commandingsurface-overflowbutton win-appbar-overflowbutton"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_return("Add to cart"));

		// Step 8: Enter OTP(disabled)
		// Step 9: Scan Multiple Gold SKU's and click on Add to cart button
		// Note:Check Average Rate
		// Expected Result:Check calculation Note:Billling Screen*No of Product
		// lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without
		// Tax)*Displayed Subtotal*TAX *Total Amount
		// Step 10: Select any one of the sales agent
		// Step 11: In Billing, Click on ""Save to Estimate"" button
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		SKUResult SkuOGSale = appUtils.GetOGSaleSku(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_MetalType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_SaleQty,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_SkuCount);

		List<String> SkuList = SkuOGSale.PcsRateSKU;

		appUtils.HamBurgerButtonClick("iconShop");

		for (String Sku : SkuList) {

			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);

			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);

			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuCounter);
			SkuCounter++;
		}

		String CstProdQty = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_GrossPieces;
		String ExpectedAdvanceName = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_AdvanceName;
		Map<String, String> DataMap = mathUtils.ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(
				ScannedSkuDataMap, CstProdQty, AdjustmentAmount, ExpectedAdvanceName);
		String TaxBillingPge = DataMap.get("Tax");
		String InvoiceTotal = DataMap.get("InvoiceTotal");
		String TaxableValue = DataMap.get("TaxableValue");
		String AmountDue = DataMap.get("AmountDue");
		String LinesCount = DataMap.get("LinesCount");
		String TotalGrossWt = DataMap.get("TotalGrossWtSku");
		String ExpectedNetValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_ProductCount
				+ "_TotalRGWeight");
		String RateKey = "SKU"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_ProductCount
				+ "_ITEM"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_ProductCount
				+ "_RATE";

		String BoardRateFromSkuTble = ScannedSkuDataMap.get(RateKey);
		Double SkuBoardRate = Double.parseDouble(BoardRateFromSkuTble);
		String Purity = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_ProductCount
				+ "_Purity");
		double BoardRate22k = 0;
		if (Purity.equalsIgnoreCase("18k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 18;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("14k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 14;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("22k")) {
			BoardRate22k = SkuBoardRate;

		}
		if (Double.parseDouble(ExpectedNetValue) > Quantity) {
			double RemainingWgt = (Double.parseDouble(ExpectedNetValue) - Quantity);
			double TotalAmount = (Quantity * Double.parseDouble(MetalRateFetch)) + (RemainingWgt * ExpectedGoldRate22k);
			double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0) / 100.0;

			asrt.assertEquals(SkuBoardRate, AverageRate, 1.0,
					"Mismatch  In Average Rate,Expected" + AverageRate + "but got" + SkuBoardRate + "in return page");

			System.out.print("Average rate of the SKU is:" + AverageRate);

		} else {
			asrt.assertEquals(SkuBoardRate, Double.parseDouble(MetalRateFetch), 1.0,
					"Mismatch  In Average Rate,Expected" + MetalRateFetch + "but got" + SkuBoardRate
					+ "in return page");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// Step 12: Click on Save esimation
		// Expected Result: Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc("buttonsContainer positionRelative", "Back"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstimationNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		// PDF Verification Step
		Thread.sleep(4000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC79_ProformaInvoice");
		Thread.sleep(5000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst = TaxBillingPge;
		String TotalAmnt = TaxableValue;
		String NetTotal = AmountDue;
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProFormaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, AdjustmentAmount, NetTotal, TotalAmnt);
		Thread.sleep(2000);

		// Step 13: Click on Cash and then click on Recall estimate
		// Step 14: Select the estimate and click on Recall estimation
		// Step 15: Select the Transaction Type as Sales
		// Step 16: Click on Process Estimation
		// Step 17: Choose a sales representative

		// Expected Result: Verify whether the recalled item is the same as the item in
		// the cart.
		// Note:Cashier Screen after recall*No of Product lines*Displayed Item
		// Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed
		// Subtotal*TAX*Total Amount
		appUtils.RecallEstimateProcess(EstimationNumber,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_TransactionType);

		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		Map<String, String> DataMapAfterRecall = mathUtils.ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(
				ScannedSkuDataMap, CstProdQty, AdjustmentAmount, ExpectedAdvanceName);

		// Step 18: Click on the Amount
		// Step 19: Select a Payment method(Cash or Card)
		appUtils.PaymentAfterRecallEstimate(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC79_PaymentMethod);

		// Step 20: Post the invoice
		// Expected Result: Check final invoice details,* save receipt id for future
		// reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC79_SaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";
		String GrossAmount = TaxableValue;
		String TotalDiscount = Discount;
		String TotalQtyPcs = String.valueOf(Integer.parseInt(LinesCount) - 1);
		String TotalDiamond = null;
		String PaymentHDFCCard = AmountDue;
		String SalesPdfPath = SaleInvoicePath;
		String TotalGrossWeight = TotalGrossWt;
		String NetValue = AmountDue;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(SalesPdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, AdjustmentAmount, AdjustmentAmount, SkuList, ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:- " + PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : multiple SR converted normal advance settlement
	// Automation ID : TC88
	// Author : Nivya
	// </summary>
	public void TC88_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> PurityOfItems = new ArrayList();
		List<Double> AllRates = new ArrayList<>();
		appUtils.HamBurgerButtonClick("iconShop");
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_MetalType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation(
				"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		for (int i = 0; i < SkuListGold.size(); i++)

		{
			String CurrentGoldSku = SkuListGold.get(i);

			appUtils.SKUIngridentTableValues(CurrentGoldSku.toString(), (i + 1), SkuDetails);

			PurityOfItems.add(SkuDetails.get("SKU_" + (i + 1) + "_Purity"));

			for (int ItemIndex = 1;; ItemIndex++) {
				String Rate = "SKU" + (i + 1) + "_ITEM" + ItemIndex + "_RATE";

				if (SkuDetails.containsKey(Rate))

				{
					try

					{
						double SkuRate = Double.parseDouble(SkuDetails.get(Rate));

						AllRates.add(SkuRate);
					} catch (NumberFormatException e) {
						System.out.println("Rate not fetched");
					}
				} else {
					break;
				}
			}

			SkuDetails.put("SKU_" + (i + 1) + "_AllRates", AllRates.toString());
		}
		// For conversion of Rate

		String ItemPurity = PurityOfItems.get(0);
		double ExchangeRate = AllRates.get(0);
		double ExpectedGoldRate22k = 0;
		double ExpectedGoldRate18k = 0;
		if (ItemPurity.equalsIgnoreCase("18k")) {
			double ConvertedGoldRate = (ExchangeRate * 22) / 18;

			ExpectedGoldRate22k = Math.round(ConvertedGoldRate);

			ExpectedGoldRate18k = ExchangeRate;
		} else {
			ExpectedGoldRate22k = Math.round(ExchangeRate);
			ExpectedGoldRate18k = (ExchangeRate * 18) / 22;
		}
		System.out.println(ExpectedGoldRate22k);
		appUtils.VoidTransaction();

		// Step 2.Click on Transaction button
		// Prerequisite :Customer must have a Sales Return converted normal advance
		Map<String, String> SRPayment1 = appUtils.MultipleSRConvertedToNormalAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_CustomerId,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuList,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_ReturnProducts);

		String InvoiceNo1 = SRPayment1.get("InvoiceNo");
		String paymentAmountSR1 = SRPayment1.get("PaymentAmountSR");

		Map<String, String> SRPayment2 = appUtils.MultipleSRConvertedToNormalAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_CustomerId,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuList,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_ReturnProducts);

		String InvoiceNo2 = SRPayment2.get("InvoiceNo");
		String paymentAmountSR2 = SRPayment2.get("PaymentAmountSR");

		paymentAmountSR1 = paymentAmountSR1.replace("-", "").split("\\.")[0];
		paymentAmountSR2 = paymentAmountSR2.replace("-", "").split("\\.")[0];

		// Step 3: Select customer
		// Step 4: Click on add to estimation button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_CustomerId,
				UtilityTestData.MenuBarOptions[5]);

		// Step 5: Click on customer adjustment button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));

		// Step 6: click on normal advance adjustment button
		// Step 7: Select advance and click on cart button
		// Expected: The balance amount and adjustment amount for the selected advances
		// should be shown correctly
		List<String> SRPaymentAmounts = Arrays.asList(paymentAmountSR1, paymentAmountSR2);
		double Quantity = 0.0;
		String MetalRateFetch = "";
		double TotalBalance = 0.0;
		double Adjustment = 0.0;
		for (String SRAmount : SRPaymentAmounts) {

			base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Normal Advance Adjustment"));
			Thread.sleep(4000);
			base.actionClick(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"));
			base.actionSetData(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.SearchTerm"), SRAmount);
			Thread.sleep(3000);
			// base.pressKey(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value:
			// viewModel.SearchTerm"), "ENTER");
			// Thread.sleep(4000);
			base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));
			String BalanceAmount = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj
					.Ele_TotalAmount("Balance amount", "h4 ellipsis cell"));
			String AdjustmentAmount = base
					.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.InputAmount"));
			Thread.sleep(1000);
			MetalRateFetch = base
					.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
							.Ele_AdvanceAdjustMetalRate("option", "Metal Rate", "gridcell", 0));
			Quantity = Quantity + Double.parseDouble(AdjustmentAmount) / Double.parseDouble(MetalRateFetch);
			TotalBalance = TotalBalance + Double.parseDouble(BalanceAmount);
			Adjustment = Adjustment + Double.parseDouble(AdjustmentAmount);

			asrt.assertEquals(BalanceAmount, AdjustmentAmount, "Amount mismatch: Balance amount is " + BalanceAmount
					+ " and adjustment amount is " + AdjustmentAmount + " in advance adjustment page");

			base.buttonClick(NormalSaleGoldandSilverObj
					.Btn_Estimation("win-commandingsurface-overflowbutton win-appbar-overflowbutton"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_return("Add to cart"));
		}

		// Step 8:Enter OTP(disabled)
		// Step 9:Scan a SKU and click add to cart button
		// Note:Check Average Rate
		// Expected Result: 9.Check calculation Note:Billling Screen*No of Product
		// lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without
		// Tax)*Displayed Subtotal*TAX *Total Amount
		SKUResult SkuSRSale = appUtils.GetOGSaleSku(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_MetalType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SaleQty,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuCount);

		List<String> SkuList = SkuSRSale.PcsRateSKU;
		System.out.println("SkuList" + SkuList);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> ScannedSkuDataMap = new HashMap();
		appUtils.SKUIngridentTableValues(SkuList.get(0),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuCount,
				ScannedSkuDataMap);

		String ExpectedGrossValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuCount
				+ "_GrossWeight");
		String ExpectedNetValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuCount
				+ "_TotalRGWeight");
		String ExpectedItemName = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuCount
				+ "_ItemName");
		String TotalForEachItem = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuCount
				+ "_Total");
		String RateKey = "SKU"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuCount
				+ "_ITEM"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuCount
				+ "_RATE";
		String BoardRateFromSkuTble = ScannedSkuDataMap.get(RateKey);
		Double SkuBoardRate = Double.parseDouble(BoardRateFromSkuTble);
		String Purity = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_SkuCount
				+ "_Purity");
		double BoardRate22k = 0;
		if (Purity.equalsIgnoreCase("18k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 18;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("14k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 14;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("22k")) {
			BoardRate22k = SkuBoardRate;

		}
		if (Double.parseDouble(ExpectedNetValue) > Quantity) {
			double Remainingwgt = (Double.parseDouble(ExpectedNetValue) - Quantity);
			double TotalAmount = (Quantity * Double.parseDouble(MetalRateFetch)) + (Remainingwgt * ExpectedGoldRate22k);
			double AverageRate = Math.round((TotalAmount / Double.parseDouble(ExpectedNetValue)) * 100.0) / 100.0;

			asrt.assertEquals(SkuBoardRate, AverageRate, 1.0,
					"Mismatch  In Average Rate,Expected" + AverageRate + "but got" + SkuBoardRate + "in return page");

			System.out.print("Average rate of the SKU is:" + AverageRate);

		} else {
			asrt.assertEquals(SkuBoardRate, Double.parseDouble(MetalRateFetch), 1.0,
					"Mismatch  In Average Rate,Expected" + MetalRateFetch + "but got" + SkuBoardRate
					+ "in return page");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// Expected Result: Check calculation
		mathUtils.SkuIngredientItemCalculation(ScannedSkuDataMap,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC91_SkuCount);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = BillingScrnTableData.get("Subtotal");
		String TotalGrossWeight = BillingScrnTableData.get("GrossWeight");
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Tax = BillingScrnTableData.get("Tax");
		String TotalAmount = BillingScrnTableData.get("TotalAmount");
		String NetTotal = BillingScrnTableData.get("NetTotal");
		String LinesCount = BillingScrnTableData.get("LinesCount");

		int LinesCountBilling = Integer.parseInt(LinesCount);
		double ActualSubTotalBilling = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double TaxBilling = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTotalAmount = Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> TextItemNamesBilling = new ArrayList<>();
		List<String> TextItemQtyBilling = new ArrayList<>();

		int ExpectedLinesCount = AllProductRows.size();
		asrt.assertEquals(ExpectedLinesCount, LinesCountBilling, "Mismatch in Lines count,expected "
				+ ExpectedLinesCount + " but got " + LinesCountBilling + " in the billing screen");

		String SkuItemRate = ItemRateBilling.get(2).getText().trim().replaceAll("[^\\d.\\-]", "");
		double GoldItemRate = Double.parseDouble(SkuItemRate);
		double ExpectedSubTotal = 0.0;
		ExpectedSubTotal = GoldItemRate - (TotalBalance);

		double ExpectedTaxAmnt = GoldItemRate * 3 / 100;
		double ActualTotalAmount = ExpectedSubTotal + ExpectedTaxAmnt;

		asrt.assertEquals(ExpectedTaxAmnt, TaxBilling, 1.0, "Mismatch in Tax value, expected" + ExpectedTaxAmnt
				+ " but got " + TaxBilling + " in the billing screen");

		for (int n = 0; n < ItemNamesBilling.size(); n++) {
			TextItemNamesBilling.add(ItemNamesBilling.get(n).getText().trim());
		}
		Thread.sleep(3000);
		String ExpectedFullItemName = ExpectedItemName.trim();
		String ExpectedItemName1 = ExpectedFullItemName.contains("-")
				? ExpectedFullItemName.substring(ExpectedFullItemName.indexOf("-") + 2)
						: ExpectedFullItemName;
		String ActualItemName = ItemNamesBilling.get(2).getText().trim();

		asrt.assertEquals(ActualItemName, ExpectedItemName1, "Mismatch in item name, expected " + ActualItemName
				+ "but got " + ExpectedItemName1 + "  in the billing screen.");

		String ExpectedTotal = ScannedSkuDataMap.get("SKU_" + (1) + "_Total");
		double ExpectTotal = Double.parseDouble(TotalForEachItem.replaceAll("[^\\d.]", ""));
		String ActualTotalStr = ItemRateBilling.get(2).getText().trim();
		double ActualTotal = Double.parseDouble(ActualTotalStr.replaceAll("[^\\d.]", ""));
		System.out.println("ExpectedTotal" + TotalForEachItem);
		System.out.println("ActualTotal" + ActualTotal);

		asrt.assertEquals(ActualTotal, ExpectTotal, 1.0, "Mismatch in total amounts (without tax),expected "
				+ ActualTotal + " but got " + ExpectTotal + " in the billing screen.");

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		String ExpectedGrossWt = ExpectedGrossValue.replace("-", "").trim();
		Double ExpectedQty = Double.parseDouble(ExpectedGrossWt);
		String BillingQtySku = ItemQtyBilling.get(2).getText().replace("-", "").trim();
		Double ActualGrossWtSku = Double.parseDouble(BillingQtySku.replace("₹", "").replace(",", "").trim());

		asrt.assertEquals(ActualGrossWtSku, ExpectedQty, 1.0, "Mismatch in Qty value,expected " + ActualGrossWtSku
				+ " but got " + ExpectedQty + " in the Billing screen");
		asrt.assertEquals(ActualSubTotalBilling, ExpectedSubTotal, 1.0, "Mismatch in Subtotal value, expected "
				+ ActualSubTotalBilling + " but got " + ExpectedSubTotal + "in the Billing screen");
		asrt.assertEquals(ActualTotalAmount, ExpectedTotalAmount, 1.0, "Mismatch in Total amount value, expected "
				+ ActualTotalAmount + " but got " + ExpectedTotalAmount + " in the Billing screen");

		// Step 10: Save to estimate
		// Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Back"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));

		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(4000);
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC89_ProformaInvoice");
		Thread.sleep(4000);
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FirstInvoice + "";
		String TaxableValue = SkuItemRate;
		String InvoiceTotal = Double.toString(Double.parseDouble(TaxableValue.replaceAll("[^\\d.]", "").trim())
				+ Double.parseDouble(Tax.replaceAll("[^\\d.]", "").trim()));

		String TotalAdjustment = Double.toString(Adjustment);
		String Gst = Tax;
		String TotalAmnt = "";
		Thread.sleep(3000);
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProformaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, TotalAdjustment, NetTotal, TotalAmnt);

		// Step 11:Recall estimate from cashier
		// Expected Result:Verify whether the recalled item is the same as the item in
		// the cart.
		// Note:Cashier Screen after recall, *No of Product lines, *Displayed Item Name,
		// *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal,
		// *TAX, *Total Amount
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2", "text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		// Step 12:Select estimate and click on Recall estimation button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		appUtils.ValidateBillingAndRecallScreenData(EstmnNumber, LinesCount, TextItemNamesBilling, TextItemQtyBilling,
				SubTotal, Tax, NetTotal);

		// Step 13: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC89_TransactionType2);

		// Step 14: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 15: Choose a Sales Representative
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC = base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		Map<String, String> SkuBillingScrnTbleData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SkuNetTotal = SkuBillingScrnTbleData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTbleData.get("LinesCount");

		// Step 16: Click on amount
		// Select payment method(cash/card)
		// Step 17:Select a Payment method(Cash or Card)
		appUtils.PaymentAfterRecallEstimate(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC88_PaymentMethod);

		// Step 18: Post exchange invoice(sale)
		// Expected Result: Check final invoice details,* save receipt id for future
		// reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC88_SaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";

		String GrossAmount = SkuItemRate;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String OGAdjustment = null;
		String TotalDiamond = null;
		String PaymentHDFCCard = PaymentAmountHDFC;
		String SalesPdfPath = SaleInvoicePath;
		String AdvanceAmountFinalInvoice = TotalAdjustment;
		TotalNetWeight = ExpectedGrossWt;
		TotalGrossWeight = ExpectedGrossWt;
		String NetValue = SkuNetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(SalesPdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, OGAdjustment, AdvanceAmountFinalInvoice, SkuList, ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:-" + PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Multiple Add on offer Advance Settlement
	// Automation ID : TC91
	// Author: Vishnu RR
	// </summary>
	public void TC91_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos()
			throws Exception {

		// Step:1 Login to POS

		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step:2 Click on Transaction button
		// Prerequisite : Customer Must have an offer Advance 1
		Map<String, String> FirstAdvance = appUtils.CreateWeightBookedOfferAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC91_CustomerID,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC91_PiecesByPurity,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC91_GrossWeight,
				UtilityTestData.Company[0], UtilityTestData.Location[0],
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC91_DepositPercent);

		System.out.println("First Order ID: " + FirstAdvance.get("OrderId"));

		// Prerequisite : Customer Must have an offer Advance 2
		Map<String, String> SecondAdvance = appUtils.CreateWeightBookedOfferAdvance(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC91_CustomerID,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC91_PiecesByPurity,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC91_GrossWeight,
				UtilityTestData.Company[0], UtilityTestData.Location[0],
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC91_DepositPercent);

		System.out.println("Second Order ID: " + SecondAdvance.get("OrderId"));

		// Step 3. Select customer
		// Step 4. Click on add to estimation button
		appUtils.SearchByCustomerID(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_CustomerID,
				UtilityTestData.MenuBarOptions[5]);

		// Step 5.Click on customer adjustments-->order advance adjustment
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Wait.until(ExpectedConditions
				.elementToBeClickable(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Order Advance Adjustment")))
		.click();

		// Step 6. choose advance from dropdown-->enter the adjustment amount
		base.selectorByVisibleText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Sel_OrderNumber("availableTranscodeOptions"), FirstAdvance.get("OrderId"));
		base.ClickCondition(NormalSaleGoldandSilverObj.Ele_ItemRecallEst("option", FirstAdvance.get("OrderId")));

		double AdvanceBalance1 = 0;
		String AdvanceBalanceAmount1 = "";
		String MetalRateFetch = "";

		// Balance Amount - in the list
		Thread.sleep(2000);
		AdvanceBalanceAmount1 = base
				.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_OrderBalanceAmt("dataListLine", "Balance amount"));
		System.out.println("Advance Amt = " + AdvanceBalanceAmount1);
		// Balance Amt in the box
		Thread.sleep(2000);
		AdvanceBalance1 = Double.parseDouble(
				base.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Balance amount")));
		System.out.println("Balance Amount from the box " + AdvanceBalance1);

		MetalRateFetch = base.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_MetalRate("column3", "h4 ellipsis cell", "Metal Rate"));
		System.out.println("Metal Rate = " + MetalRateFetch);
		// Selecting the list
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("column1", "h4 ellipsis cell", "1"));

		String BalanceAmtInAdvanceAdj1 = base
				.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Balance amount"));
		String DefaultAdjAmt1 = base
				.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Adjustment amount"));
		double Adjustment1 = AdvanceBalance1 * 0.1;

		base.clearData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAmountInput("Adjustment amount"));
		base.setData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAmountInput("Adjustment amount"), Double.toString(Adjustment1));

		// Step 7. Click on cart button
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));

		// Repeating step 6 and 7.
		WebDriverWait Waits = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Step 5.Click on customer adjustments-->order advance adjustment
		Wait.until(ExpectedConditions
				.elementToBeClickable(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Order Advance Adjustment")))
		.click();

		// Step 6. choose advance from dropdown-->enter the adjustment amount
		base.selectorByVisibleText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Sel_OrderNumber("availableTranscodeOptions"), SecondAdvance.get("OrderId"));
		base.ClickCondition(NormalSaleGoldandSilverObj.Ele_ItemRecallEst("option", SecondAdvance.get("OrderId")));

		double AdvanceBalance2 = 0;
		String AdvanceBalanceAmount2 = "";

		// Balance Amount - in the list
		Thread.sleep(2000);
		AdvanceBalanceAmount2 = base
				.GetText(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_OrderBalanceAmt("dataListLine", "Balance amount"));
		System.out.println("Advance Amt = " + AdvanceBalanceAmount2);
		// Balance Amount in the box
		Thread.sleep(2000);
		AdvanceBalance2 = Double.parseDouble(
				base.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Balance amount")));
		System.out.println("Balance Amount from the box " + AdvanceBalance2);

		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("column1", "h4 ellipsis cell", "1"));

		String BalanceAmtInAdvanceAdj2 = base
				.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Balance amount"));
		String DefaultAdjAmt2 = base
				.GetValue(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
						.Ele_BalanceAmountInput("Adjustment amount"));
		double Adjustment2 = AdvanceBalance1 * 0.1;

		double Quantity22k = Adjustment1 / Double.parseDouble(MetalRateFetch);
		double MetalRate18k = (Double.parseDouble(MetalRateFetch) * 18) / 22;
		double Quantity18k = Adjustment1 / MetalRate18k;

		base.clearData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAmountInput("Adjustment amount"));
		base.setData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
				.Ele_BalanceAmountInput("Adjustment amount"), Double.toString(Adjustment2));

		// Step 7. Click on cart button
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));

		// Expected Result:The balance amount and adjustment amount for the selected
		// advances should be shown correctly
		asrt.assertEquals(BalanceAmtInAdvanceAdj1, DefaultAdjAmt1, "Mismatch in balance amount Expexted: "
				+ DefaultAdjAmt1 + "but got " + BalanceAmtInAdvanceAdj1 + " in advance adjustment page");
		asrt.assertEquals(BalanceAmtInAdvanceAdj2, DefaultAdjAmt2, "Mismatch in balance amount Expexted: "
				+ DefaultAdjAmt2 + "but got " + BalanceAmtInAdvanceAdj2 + " in advance adjustment page");

		// Step 8:Scan a SKU and click add to cart button
		SKUResult SkuOGSale = appUtils.GetOGSaleSku(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_MetalType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_SaleQty,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_SkuCount);

		List<String> SkuList = SkuOGSale.PcsRateSKU;
		System.out.println("SkuList" + SkuList);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> ScannedSkuDataMap = new HashMap();
		appUtils.SKUIngridentTableValues(SkuList.get(0),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_SkuCount,
				ScannedSkuDataMap);

		String ExpectedGrossValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_SkuCount
				+ "_GrossWeight");
		String ExpectedNetValue = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_SkuCount
				+ "_TotalRGWeight");
		String ExpectedItemName = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_SkuCount
				+ "_ItemName");
		String TotalForEachItem = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_SkuCount
				+ "_Total");
		String RateKey = "SKU"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_SkuCount
				+ "_ITEM"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_SkuCount
				+ "_RATE";
		String BoardRateFromSkuTble = ScannedSkuDataMap.get(RateKey);
		Double SkuBoardRate = Double.parseDouble(BoardRateFromSkuTble);
		String Purity = ScannedSkuDataMap.get("SKU_"
				+ AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_SkuCount
				+ "_Purity");
		double BoardRate22k = 0;
		/// Checking the calculation for the selected sku
		if (Purity.equalsIgnoreCase("18k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 18;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("14k")) {
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 14;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		} else if (Purity.equalsIgnoreCase("22k")) {
			BoardRate22k = SkuBoardRate;
		}
		// Expected Result: Check calculation
		mathUtils.SkuIngredientItemCalculation(ScannedSkuDataMap,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_TestData.TC91_SkuCount);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = BillingScrnTableData.get("Subtotal");
		String TotalGrossWeight = BillingScrnTableData.get("GrossWeight");
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Tax = BillingScrnTableData.get("Tax");
		String TotalAmount = BillingScrnTableData.get("TotalAmount");
		String NetTotal = BillingScrnTableData.get("NetTotal");
		String LinesCount = BillingScrnTableData.get("LinesCount");

		int LinesCountBilling = Integer.parseInt(LinesCount);
		double SubTotalBilling = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double TaxBilling = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));
		double TotalAmountBilling = Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldandSilverObj
				.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> TextItemNamesBilling = new ArrayList<>();
		List<String> TextItemQtyBilling = new ArrayList<>();

		int ExpectedLinesCount = AllProductRows.size();
		asrt.assertEquals(ExpectedLinesCount, LinesCountBilling, "Mismatch in Lines count,expected "
				+ ExpectedLinesCount + " but got " + LinesCountBilling + " in the billing screen");

		String SkuItemRate = ItemRateBilling.get(2).getText().trim().replaceAll("[^\\d.\\-]", "");
		double FirstItemRate = Double.parseDouble(SkuItemRate);
		double ExpectedSubTotal = 0.0;
		ExpectedSubTotal = FirstItemRate - (Adjustment1 + Adjustment2);

		double ExpectedTaxAmnt = FirstItemRate * 3 / 100;
		double ActualTotalAmount = ExpectedSubTotal + ExpectedTaxAmnt;

		asrt.assertEquals(ExpectedTaxAmnt, TaxBilling, 1.0, "Mismatch in Tax value, expected" + ExpectedTaxAmnt
				+ " but got " + TaxBilling + " in the billing screen");

		for (int n = 0; n < ItemNamesBilling.size(); n++) {
			TextItemNamesBilling.add(ItemNamesBilling.get(n).getText().trim());
		}
		Thread.sleep(3000);
		String ExpectedFullItemName = ExpectedItemName.trim();
		String ExpectedTrimmedItemName = ExpectedFullItemName.contains("-")
				? ExpectedFullItemName.substring(ExpectedFullItemName.indexOf("-") + 2)
						: ExpectedFullItemName;
		String ActualItemName = ItemNamesBilling.get(2).getText().trim();

		asrt.assertEquals(ExpectedTrimmedItemName, ActualItemName, "Mismatch in item name, expected "
				+ ExpectedTrimmedItemName + "but got " + ActualItemName + "  in the billing screen.");
		String ExpectedTotalStr = ScannedSkuDataMap.get("SKU_" + (2) + "_Total");
		double ExpectedTotal = Double.parseDouble(TotalForEachItem.replaceAll("[^\\d.]", ""));
		String ActualTotalStr = ItemRateBilling.get(2).getText().trim();
		double ActualTotal = Double.parseDouble(ActualTotalStr.replaceAll("[^\\d.]", ""));
		System.out.println("ExpectedTotal" + TotalForEachItem);
		System.out.println("ActualTotal" + ActualTotal);

		asrt.assertEquals(ExpectedTotal, ActualTotal, 1.0, "Mismatch in total amounts (without tax),expected "
				+ ExpectedTotal + " but got " + ActualTotal + " in the billing screen.");

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		for (int n = 0; n < ItemQtyBilling.size(); n++) {
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		String ExpectedGrossWt = ExpectedGrossValue.replace("-", "").trim();
		Double ExpectedQty = Double.parseDouble(ExpectedGrossWt);
		String BillingQtySku = ItemQtyBilling.get(2).getText().replace("-", "").trim();
		Double ActualGrossWtSku = Double.parseDouble(BillingQtySku.replace("₹", "").replace(",", "").trim());
		asrt.assertEquals(ExpectedQty, ActualGrossWtSku, 1.0, "Mismatch in Qty value,expected " + ExpectedQty
				+ " but got " + ActualGrossWtSku + " in the Billing screen");
		asrt.assertEquals(ExpectedSubTotal, SubTotalBilling, 1.0, "Mismatch in Subtotal value, expected "
				+ ExpectedSubTotal + " but got " + SubTotalBilling + "in the Billing screen");
		asrt.assertEquals(TotalAmountBilling, ActualTotalAmount, 1.0, "Mismatch in Total amount value, expected "
				+ TotalAmountBilling + " but got " + ActualTotalAmount + " in the Billing screen");

		// Step 9: Save to estimate
		// Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Back"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));

		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(4000);
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC95_ProformaInvoice");
		Thread.sleep(4000);
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FirstInvoice + "";
		String TaxableValue = SkuItemRate;
		String InvoiceTotal = Double.toString(Double.parseDouble(TaxableValue.replaceAll("[^\\d.]", "").trim())
				+ Double.parseDouble(Tax.replaceAll("[^\\d.]", "").trim()));

		String TotalAdjustment = Double.toString(Adjustment1 + Adjustment2);
		String Gst = Tax;
		String TotalAmnt = "";
		Thread.sleep(3000);
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(ProformaInvoicePath, TaxableValue, Gst,
				InvoiceTotal, TotalAdjustment, NetTotal, TotalAmnt);

		// Step 10:Recall estimate from cashier
		// Expected Result:Verify whether the recalled item is the same as the item in
		// the cart.
		// Note:Cashier Screen after recall, *No of Product lines, *Displayed Item Name,
		// *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal,
		// *TAX, *Total Amount

		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2", "text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		// Step 12:Select estimate and click on Recall estimation button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		String ExpectedEstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));

		Assert.assertEquals(EstmnNumber, ExpectedEstmnNumber, "Mismatch in Estimation number Actual is " + EstmnNumber
				+ " and Expected Estimation number" + ExpectedEstmnNumber + "");

		// Step 11: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_TransactionType2);

		// Step 12: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(
				NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		appUtils.ValidateBillingAndRecallScreenData(EstmnNumber, LinesCount, TextItemNamesBilling, TextItemQtyBilling,
				SubTotal, Tax, NetTotal);

		// Step 13: Choose a Sales Representative
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC = base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		Map<String, String> SkuBillingScrnTbleData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SkuNetTotal = SkuBillingScrnTbleData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTbleData.get("LinesCount");

		// Step 14: Click on amount
		// Select payment method(cash/card)
		// Step 15:Select a Payment method(Cash or Card)
		appUtils.PaymentAfterRecallEstimate(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC91_PaymentMethod);

		// Step 16: Post exchange invoice(sale)
		// Expected Result: Check final invoice details,* save receipt id for future
		// reference
		Thread.sleep(2000);
		base.ClickCondition(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.ClickCondition(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC95_SaleInvoice");
		Thread.sleep(3000);
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice + "";
		String GrossAmount = SkuItemRate;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String OGAdjustment = null;
		String TotalDiamond = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC95_TotalDiamond;
		String PaymentHDFCCard = PaymentAmountHDFC;
		String SalesPdfPath = SaleInvoicePath;
		String AdvanceAmountFinalInvoice = Double.toString(Adjustment1 + Adjustment2);
		TotalNetWeight = ExpectedGrossWt;
		TotalGrossWeight = ExpectedGrossWt;
		String NetValue = SkuNetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(SalesPdfPath, GrossAmount,
				TotalDiscount, TotalDiamond, TotalQtyPcs, TotalNetWeight, TotalGrossWeight, TaxableValue, NetValue,
				PaymentHDFCCard, OGAdjustment, AdvanceAmountFinalInvoice, SkuList, ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:-" + PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton", "Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Offer advance settlement with board rate greater than fixed rate
	// Automation ID : TC87
	// Author: Gokul.P
	// </summary>
	public void TC87_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos() throws Exception {

		//Prerequisite: 1. Customer must be having offer advance
		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		Map<String, String> AdvanceDetails = appUtils.CreateWeightBookedOfferAdvance
				(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_CustomerID,
						AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_Pieces, 
						AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_GrossWeight, 
						AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_Company, 
						AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_Location,
						AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_DepositPercent);

		String OrderId            = AdvanceDetails.get("OrderId");
		String BoardRate          = AdvanceDetails.get("FixedGoldRate");
		String WillingToPayAmount = AdvanceDetails.get("WillingToPayAmount");
		String DepositAmnt        = WillingToPayAmount;

		// Step 3: Select customer 
		// Step 4: Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_CustomerID, UtilityTestData.MenuBarOptions[5]);

		//Step 5: Click on customer adjustments-->order advance adjustment
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));
		wait.until(ExpectedConditions.presenceOfElementLocated(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Order Advance Adjustment"))).click();

		//Step 6: Choose advance from "Customer order no" dropdown->enter the adjustment amount
		//Step 7:Select the required advance
		//Expected :The balance amount and adjustment amount for the selected advances should be shown correctly
		wait.until(ExpectedConditions.presenceOfElementLocated(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj.Sel_OrderNumber("availableTranscodeOptions")));
		try {
			base.selectorByVisibleText(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj.Sel_OrderNumber("availableTranscodeOptions"), OrderId);
			String SelectedValue = base.getTheSelectedTextInDropDown(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj.Sel_OrderNumber("availableTranscodeOptions"));
			Assert.assertEquals(SelectedValue, OrderId, "Order ID selection mismatch");			
		} catch (Exception e) {
			Assert.fail("Order ID is not present: " + OrderId);
		}

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",OrderId));
		String BalanceAmount         = base.GetAttribte(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj.Ele_BalanceAndAdjustmentAmnt("BalanceAmount"),"value");
		String AdvanceRate           = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_TotalAmount("Metal Rate", "h4 ellipsis cell"));
		String FirstAdjustmentAmount = base.GetAttribte(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj.Ele_BalanceAndAdjustmentAmnt("InputAmount"),"value");

		asrt.assertEquals(Double.parseDouble(BalanceAmount),Double.parseDouble(DepositAmnt), "Mismatch in Balance amount, Expected "+DepositAmnt+" but got "+BalanceAmount+" in the Order Adjustment page");
		asrt.assertEquals(Double.parseDouble(FirstAdjustmentAmount),Double.parseDouble(BalanceAmount), "Mismatch in Balance amount, Expected"+BalanceAmount+" but got"+FirstAdjustmentAmount+" in the Order Adjustment page");

		base.setData(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj.Ele_BalanceAndAdjustmentAmnt("InputAmount"),BalanceAmount);
		String AdjustmentAmount      =base.GetAttribte(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj.Ele_BalanceAndAdjustmentAmnt("InputAmount"),"value");

		double Adjustment22K = Double.parseDouble(AdjustmentAmount);
		double Quantity22k = Adjustment22K/Double.parseDouble(AdvanceRate);

		double MetalRate18k = (Double.parseDouble(AdvanceRate)*18)/22;
		double Quantity18k = Adjustment22K/MetalRate18k;

		//Prerequisite 2.Normal Advance Board Rate should be greater than Fixed Board rate
		String ErpGoldRateFor24K= erpUtils.SetMetalRateInERP(AdvanceRate, "22k");
		Thread.sleep(120000); //Wait needed for ERP Gold Rate to Change

		double Current22kRate = (Double.parseDouble(ErpGoldRateFor24K)*22)/24;
		double Current18kRate = (Double.parseDouble(ErpGoldRateFor24K)*18)/24;


		//Step 8: Click on add to cart
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		List<String> AdjustedName   = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5")).stream().map(text -> text.replaceAll("[\u20B9,=:\\-]", "") .replaceAll("\\s{2,}", " ").trim()).collect(Collectors.toList());
		List<String> AdjustedQty    = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5")).stream().map(text -> text.replaceAll("[\u20B9,=:\\-]", "").replaceAll("\\s{2,}", " ").trim()).collect(Collectors.toList());
		List<String> SKUGrossAmount = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5")).stream().map(text -> text.replaceAll("[\u20B9,=:\\-]", "").replaceAll("\\s+", "").trim()).collect(Collectors.toList());
		String ExpectedAdjustedName = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_DepositAdjustment;
		String ExpectedAdjustedQty  = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_DepositAdjustmentQty;

		Assert.assertEquals(AdjustedName.get(0), ExpectedAdjustedName,"The Adjusted name from billing page :" +AdjustedName.get(0)+" mismatch with expected adjusted name : "+ExpectedAdjustedName+ " from offer advance");
		Assert.assertEquals(AdjustedQty.get(0), ExpectedAdjustedQty,"The Adjusted Qty from billing page :" +AdjustedQty.get(0)+" mismatch with expected adjusted Qty : "+ExpectedAdjustedQty+ " from offer advance");

		//Step 9: Scan any SKU and click on Add to cart button
		//Step 10: Select any one of the sales agent
		//Expected:  9.Check calculation
		//Note:Billing Screen:No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		List<String> SkuList = appUtils.SelectMultipleSKUs(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_ProductCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_TransferType,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_FromCounter,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_MetalType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		erpUtils.ResetRemainingReturnWeight();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		List<String> ProductNames             = new ArrayList<>();
		List<String> ProductQtyList           = new ArrayList<>();
		List<String> ProductTotalList         = new ArrayList<>();
		ProductNames.add(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_DepositAdjustment);
		ProductQtyList.add(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC87_DepositAdjustmentQty);
		ProductTotalList.add(AdjustmentAmount);
		for (int i = 0; i < SkuList.size(); i++) {
			int SkuNumber = i + 1;
			String Sku = SkuList.get(i);
			appUtils.SKUIngridentTableValues(Sku, SkuNumber, ScannedSkuDataMap);
			ProductNames.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_HeaderName"));
			ProductQtyList.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_GrossWeight"));
			ProductTotalList.add(ScannedSkuDataMap.get("SKU_" + SkuNumber + "_Total"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuNumber);
			//erpUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuNumber);
			erpUtils.ErpSkuIngredientItemCalculation(CurrentSkuData, SkuNumber, String.valueOf(Quantity22k), "22k",AdvanceRate , ErpGoldRateFor24K);
		}

		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal                         = TransactionDataLine.get("Subtotal").replaceAll("[^\\d.]", "").trim();
		String Discount                         = TransactionDataLine.get("Discount");
		String TotalGrossWeight                 = TransactionDataLine.get("GrossWeight");
		String TotalNetWeight                   = TransactionDataLine.get("NetWeight");
		String Tax                              = TransactionDataLine.get("Tax").replaceAll("[^\\d.]", "").trim();
		String TotalAmount                      = TransactionDataLine.get("TotalAmount").replaceAll("[^\\d.]", "").trim();
		String NetTotal                         = TransactionDataLine.get("NetTotal").replaceAll("[^\\d.]", "").trim();
		String LinesCount                       = TransactionDataLine.get("LinesCount");
		List<String> ActualNames                = mathUtils.ValidateTransactionLineCalculationsReturnProd(TransactionDataLine, ScannedSkuDataMap);
		String SKUName                          = ActualNames.toString().replace("[", "").replace("]", "").trim();

		//Step 11:In Billing, Click on "Save to Estimate" button
		//Step 12:Click on Save estimation
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc("buttonsContainer positionRelative","Back" ));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String ActualEstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		//Validation
		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC87_ProformaInvoice");
		Thread.sleep(2000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst                 = Tax;
		String TaxableValue        = ProductTotalList.get(1);
		double CalculatedInvoice   = Double.parseDouble(Gst) + Double.parseDouble(TaxableValue);
		String InvoiceTotal        = String.valueOf(CalculatedInvoice);
		String TotalAdjustment     = String.format("%.2f", Double.parseDouble(AdjustmentAmount.trim()));
		String TotalAmt            ="";
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				TotalAdjustment,
				NetTotal,
				TotalAmt);

		//Step 13:Click on Cash and then click on Recall estimate
		//Step 14: Select estimate and click on Recall estimation button
		//Expected : Verify whether the recalled item is the same as the item in the cart.
		// Note:-17.Cashier Screen after recall,*No of Product lines,*Displayed Item Name,  *Displayed Quantity*Displayed TOTAL (without Tax)
		//*Displayed Subtotal	 *TAX *Total Amount
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		String ExpectedEstmnNumber= base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));

		Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+" in recall estimation page"); 

		//Validation Recall page
		List<String> ExpectedItemNames = Arrays.asList(ExpectedAdjustedName,SKUName).stream().map(Object::toString).collect(Collectors.toList());
		List<String> ExpectedGrossWeights = ProductQtyList;

		appUtils.RecallEstimateItemValidation(
				ExpectedItemNames,        
				ProductQtyList,             
				ExpectedItemNames,      
				ExpectedGrossWeights,   
				Double.parseDouble(SubTotal),
				Double.parseDouble(Tax),
				Double.parseDouble(TotalAmount),
				Integer.parseInt(LinesCount),
				"Deposit Adjustment"                      
				);

		//Step 15: Select the Transaction Type as Sales 
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"), AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC81_TransactionType);

		//Step 16: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		//Step 17: Choose a sales representative
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Step 18 : click on amount
		//Step 19: Select payment method method (cash/card)
		String PaymentAmount = appUtils.PaymentAfterRecallEstimate("HDFC");
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		//Step 20 Post invoice
		//Expected : Check final invoice details
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice        = pdfUtils.DownloadAndRenameLatestPDF("TC87_FinalSaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath     = System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";
		String GrossAmount         = String.format("%.2f", Double.parseDouble(SKUGrossAmount.get(0).trim()));;
		String TotalDiscount       = Discount;
		String TotalQtyPcs         = String.valueOf(ActualNames.size());
		String Adjustment          = TotalAdjustment;
		String PaymentHDFCCard     = PaymentAmount;
		String SalesPdfPath        = SaleInvoicePath;
		TotalNetWeight             = TotalNetWeight;
		double totalGrossWeightVal = Double.parseDouble(TotalGrossWeight);
		double adjustedQtyVal      = Double.parseDouble(ExpectedAdjustedQty);
		String PDFGrossWt          = String.format("%.3f", totalGrossWeightVal + adjustedQtyVal);
		String NetValue            = NetTotal;
		String PdfInvoiceNo        = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				"",
				TotalQtyPcs,
				TotalNetWeight,
				PDFGrossWt,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				SkuList,
				ScannedSkuDataMap
				);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	
	// <summary>
	// Test Case Title : Offer advance settlement with reserve SKU
	// Automation ID : TC83
	// Author: Robin T. Abraham
	// </summary>
	public void TC83_AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos() throws Exception {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(25));
		//Step 1.Login to POS
		//Step 2: Click on Transaction button
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//PreRequesite
		//Customer must be having offer advance with resrved SKU
		//Step 1.Login to POS
		//Step 2: Click on Transaction button
		Thread.sleep(5000);
		appUtils.HamBurgerButtonClick("iconShop");
		//base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		//Select SKU Number
		List<String> GoldSku = appUtils.SelectMultipleSKUs(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_SKUCount,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_TransferTypeGold,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_FromCounterGold,
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_MetalTypeGold);
		System.out.println("GoldSku"+GoldSku);
		List<String> SkuList=new ArrayList<>();
		SkuList.addAll(GoldSku);
		String SkuNumber = SkuList.get(0);	
		appUtils.HamBurgerButtonClick("iconShop");
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
		Thread.sleep(3000);
		base.setDataWithoutClear(NormalSaleGoldandSilverObj.Edt_Name("Search or enter quantity"), SkuNumber);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
		base.setZoom(driver, 40);
		double ExpectedTotalCValueInItemDetailsPage = Double.parseDouble(base.GetAttribte(NormalSaleGoldandSilverObj.Ele_Name("valueInput"), "value"));
		double ExpectedGrossWeightInItemDetailspage =  Double.parseDouble(base.GetAttribte(NormalSaleGoldandSilverObj.Ele_Name("grossWeightInput"), "value"));		
		double ExpectedTotalAmountInItemDetailPage = Double.parseDouble(base.GetAttribte(NormalSaleGoldandSilverObj.Ele_Name("totalInput"), "value"));
		appUtils.HamBurgerButtonClick("iconShop");

		//Step 3: Click on advance
		//Step 4: Select customer order
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_return("Customer Order"));

		//Step 5: Search customer and click on add to customer order
		appUtils.SearchByCustomerIDForGPP(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_CustomerPhoneNo, UtilityTestData.AddToCustomerOrder);

		//Step 6: Enter the SKU number in the field and click enter
		//Expected Result: Verify the product details *Gross wt *CValue *Total amount
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_ScanSku("AddSKUAutomatic "), SkuNumber);
		base.pressKey(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_ScanSku("AddSKUAutomatic "), "ENTER");
		Thread.sleep(2000);
		double ActualGrossWeightInCustomerOrderPage = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column3", "h4 ellipsis cell")));
		double ActualCValueInCustomerOrderPage = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column4", "h4 ellipsis cell")));
		double TotalInCustomerOrderPage = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column7", "h4 ellipsis cell")));
		double ActualTotalAmountInCustomerOrderPage = Double.parseDouble(base.GetValue(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Ele_GPPCustAccnt("TotAmtInput")));

		//Step 7: Select the 'Is offer order' checkbox
		base.buttonClick(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_CheckBox("checkbox", "IsofferChkBox"));

		//Step 8: Choose the company and invent location
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col", "viewModel.IsOrderEntry"), UtilityTestData.Company[0]);
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col stretch", "viewModel.IsOrderEntry"), UtilityTestData.Location[0]);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Sales person"), AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_SalesPerson);

		//Step 9: Select the order line
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		//Step 10: Select the ingredient line
		base.scrollToElement(LoginPageObj.Ele_ErrorMessage("win-itembox"));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		//Step 11: Click on willing to pay button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_MenuBarOptions("MGDRetail_MGDRetail_Extensions_OrderView_appWillToPayCommandCommand"));

		//Step 12: Enter the amount
		String WillingToPayAmountStr = Double.toString(ActualTotalAmountInCustomerOrderPage*AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_DepositPercent);
		Thread.sleep(1000);
		double WillingToPayAmountValue=Double.parseDouble(WillingToPayAmountStr);
		double FloorValue = Math.floor(WillingToPayAmountValue);
		double DecimalPart = WillingToPayAmountValue - FloorValue;

		System.out.println("FloorValue: " + FloorValue);
		System.out.println("DecimalPart: " + DecimalPart);
		if (DecimalPart > 0.5) {
			WillingToPayAmountValue = Math.ceil(WillingToPayAmountValue);        // Round up
			System.out.println("AdjustedGoldRate if DecimalPart>0.5 " + WillingToPayAmountValue);
		} else if (DecimalPart == 0.5) {
			WillingToPayAmountValue = FloorValue + 0.5;                   // Keep 0.5
			System.out.println("AdjustedGoldRate if DecimalPart==0.5 " + WillingToPayAmountValue);
		} else {
			WillingToPayAmountValue = Math.floor(WillingToPayAmountValue);       // Round down
			System.out.println("AdjustedGoldRate if DecimalPart<0.5 " + WillingToPayAmountValue);
		}
		String WillingToPayAmount=String.valueOf(WillingToPayAmountValue);
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Amount("value: WILLAMT", "number"), WillingToPayAmount);
		Thread.sleep(1000);
		String ExpectedOfferExpiryDate= base.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: SDATE"));
		String ExpectedValidityDays = base.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: SDAYS")).replaceAll("\\D+", "");;

		//Step 13: Click on apply button
		base.buttonClick(LoginPageObj.Btn_SignInButton("APPLY"));

		//Step 14: Click on edit line
		Thread.sleep(3000);
		base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Txt_Sku("win-structuralnodes win-selectionmode"));
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_MenuBarOptions("MGDRetail_MGDRetail_Extensions_OrderView_EditSlappBarCommandCommand"));

		//Step 15: Enter the line delivery date
		DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate ExpiryDate = LocalDate.parse(ExpectedOfferExpiryDate, Formatter);
		LocalDate TargetDate = ExpiryDate.minusDays(1);
		String Day = Integer.toString(TargetDate.getDayOfMonth());
		String Month = Integer.toString(TargetDate.getMonthValue() - 1); 
		String Year = Integer.toString(TargetDate.getYear());		
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-date"), Day);
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-month"), Month);
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-year"), Year);

		//Step 16: Enter remarks
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Remarks("value: Remarks"), AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_Remarks);

		//Step 17: Click on OK button
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		//Step 18: Add nominee details, Select a sales person
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_NomineeDetailsAppBarCommandCommand"));
		Thread.sleep(1000);
		base.setData(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: NomineeName"),AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_NomineeName);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: NomineeRelationArr"), AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_NomineeRelation);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_Name("NomineeChkBox"));	
		String Address1 = base.GetText(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: NomineeAddress"));
		if(Address1 == "") {
			base.setData(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: NomineeAddress"),AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_NomineeAdresss);
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}
		else {
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}

		//Step 19: Click on save button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_appBarCommandCommand"));
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("YES"));
		String Message = base.GetText(NormalSaleDiamondandPlatinumObj.Ele_GrossWeight("text: content"));
		String OrderId = Message.split(" - ")[1].split(" ")[0];
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		System.out.println("OrderId"+OrderId);

		//Step 20: Go to transactions
		//Step 21: Search customer
		//Step 22: Click on add to sale button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		appUtils.SearchByCustomerID(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_CustomerPhoneNo ,UtilityTestData.MenuBarOptions[0]);

		//Step 23: Choose advance-->advance collection
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader")));
		wait.until(ExpectedConditions.presenceOfElementLocated(LoginPageObj.Edt_AlertText("Advance"))).click();
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_return("Advance Collection"));

		//Step 24: Choose type as order
		//base.buttonClick(NormalSaleGoldandSilverObj.Sel_DepositType("OrderTypeOptions"));
		Thread.sleep(5000);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("OrderTypeOptions"), "Order");

		//Step 25: Select the transaction number from the drop down
		//base.buttonClick(NormalSaleGoldandSilverObj.Sel_DepositType("TransTypeOptions"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("TransTypeOptions"), OrderId);
		System.out.println("WillingToPayAmount"+WillingToPayAmount);

		//Step 26: Enter the deposit amount
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("DepAmount"),WillingToPayAmount);	
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_SalesPerson);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Deposit"));

		//Step 27: Click on the Amount
		Thread.sleep(3000);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldandSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Close"));

		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
		// Step 3: Select customer 
		// Step 4: Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_CustomerId, UtilityTestData.MenuBarOptions[5]);

		//Step 5: Click on customer adjustments-->order advance adjustment
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));
		wait.until(ExpectedConditions.presenceOfElementLocated(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Order Advance Adjustment"))).click();

		//Step 6: choose advance from dropdown-->enter the adjustment amount
		//Expected Result : The balance amount and adjustment amount for the selected advances should be shown correctly
		wait.until(ExpectedConditions.presenceOfElementLocated(
				AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj.Sel_OrderNumber("availableTranscodeOptions")));
		try {
			base.selectorByVisibleText(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj.Sel_OrderNumber("availableTranscodeOptions"),OrderId);// OrderId);
			String selectedValue = base.getTheSelectedTextInDropDown(
					AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosObj
					.Sel_OrderNumber("availableTranscodeOptions")
					);
			Assert.assertEquals(selectedValue, OrderId, "Order ID selection mismatch");			
		} catch (Exception e) {
			Assert.fail("Order ID is not present: " + OrderId);
		}
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",OrderId));
		String BalanceAmount = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_TotalAmount("Balance amount", "h4 ellipsis cell"));
		String AdvanceRate = base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_TotalAmount("Metal Rate", "h4 ellipsis cell"));
		String AdjustmentAmount = base.GetValue(NormalSaleGoldandSilverObj.Ele_SameAsAbove("value: viewModel.InputAmount"));
		Thread.sleep(1000);

		asrt.assertEquals(BalanceAmount, AdjustmentAmount,"Amount mismatch: Balance amount is "+BalanceAmount+" and adjustment amount is "+AdjustmentAmount+" in advance adjustment page");

		//Step 7: Click on add to cart
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		//Step 8: Scan reserved SKU and click add to cart button
		// Note : unlock reserve SKU before scanning
		//Expected Result: Check calculation
		// Note: Billing Screen *No of Product lines *Displayed Item Name *Displayed Quantity *Displayed TOTAL (without Tax) *Displayed Subtotal *TAX *Total Amount
		List<String> SelectedGoldSkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		wait.until(ExpectedConditions.presenceOfElementLocated(LoginPageObj.Edt_AlertText("Purchase"))).click();
		base.ClickCondition(LoginPageObj.Edt_AlertText("Reserved SKU Release"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("OrderTypeOptions"), "Order");

		// Scan Reserved SKU
		base.setData(NormalSaleGoldandSilverObj.Edt_TextField("stretch","text"), GoldSku.get(0));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("dataListLine"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("reason"), "Settlement");
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_searchCommandCommand"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		appUtils.HamBurgerButtonClick("iconShop");

		SelectedGoldSkuList.addAll(GoldSku); //Reserved SKU

		String CstGrossWt = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_GrossWeight;

		erpUtils.ResetRemainingReturnWeight();
		for (String Sku : SelectedGoldSkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuCounter);
			SkuCounter++;
		}

		String CstProdQty = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_GrossPieces;
		String ExpectedAdvanceName = AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_AdvanceName;

		Map<String, String> DataMap = mathUtils.ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(
				ScannedSkuDataMap, 
				CstProdQty, 
				AdjustmentAmount, 
				ExpectedAdvanceName
				);

		String TaxBillingPge  = DataMap.get("Tax");
		String InvoiceTotal   = DataMap.get("InvoiceTotal");
		String TaxableValue   = DataMap.get("TaxableValue");
		String AmountDue      = DataMap.get("AmountDue");
		String LinesCount     = DataMap.get("LinesCount");
		String TotalGrossWt   = DataMap.get("TotalGrossWtSku");

		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(
				SkuList, 
				ScannedSkuDataMap
				);


		//Step 9: Click on save to estimate-->Recall estimate from cashier screen
		//Expected Result: Verify the estimate details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc("buttonsContainer positionRelative","Back" ));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstimationNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		// PDF Verification Step
		Thread.sleep(4000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC83_ProformaInvoice");
		Thread.sleep(5000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst                 = TaxBillingPge;
		String TotalAmnt           = TaxableValue;
		String NetTotal 		   = AmountDue;
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				AdjustmentAmount,
				NetTotal,
				TotalAmnt);
		Thread.sleep(2000);

		//Step 10: Select estimate and click on recall estimation
		//Step 11: Select the Transaction Type as Sales 
		//Step 12: Click on Process Estimation 
		//Step 13: Choose a sales representative
		//Expected Result: Verify whether the recalled item is the same as the item in the cart.
		//Note:-Cashier Screen after recall*No of Product lines *Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		appUtils.RecallEstimateProcess(EstimationNumber, AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_TransactionType);
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));	
		appUtils.HandleSpecifyRateModalInRecall();

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(SelectedGoldSkuList, ScannedSkuDataMap);		
		String TotalNetWeight = BillingScrnTableData.get("NetWeight");
		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		Map<String, String> DataMapAfterRecall = mathUtils.ValidateBillingScreenDataWithOfferAdvanceAndMultipleSkus(ScannedSkuDataMap,
				CstProdQty, 
				AdjustmentAmount,
				ExpectedAdvanceName);
		Map<String, String> TransactionDataLineRecall = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		System.out.println("Values in TransactionDataLineRecall: " + new HashSet<>(TransactionDataLineRecall.values()));
		System.out.println("Values in TransactionDataLine: " + new HashSet<>(TransactionDataLine.values()));

		asrt.assertEquals(
				new HashSet<>(TransactionDataLineRecall.values()),
				new HashSet<>(TransactionDataLine.values()),"Mismatch detected: Recalled item and cart item are not the same.");

		//Step 14.Click on the Amount
		//Step 15.Select a Payment method(Cash or Card)
		appUtils.PaymentAfterRecallEstimate(AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_PaymentMethod);

		//Step 16: Post exchange invoice(sale)
		//Expected Result: Check final invoice details,* save receipt id for future reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC83_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";
		String GrossAmount=TaxableValue;
		String TotalDiscount = Discount;
		String TotalQtyPcs = String.valueOf(Integer.parseInt(LinesCount) - 1);
		String Adjustment= AdjustmentAmount;
		String TotalDiamond= AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPosTestData.TC83_TotalDiamond;
		String PaymentHDFCCard = AmountDue;
		String SalesPdfPath = SaleInvoicePath;
		String AdvanceAmountFinalInvoice = WillingToPayAmount;	
		String TotalGrossWeight = TotalGrossWt;
		String NetValue = AmountDue;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidationAdvance(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				AdvanceAmountFinalInvoice,
				SelectedGoldSkuList,
				ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:- "+PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

}
