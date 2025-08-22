package testPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import objectRepository.OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj;
import testData.CommonData;
import testData.OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_TestData;
import testData.OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.BasePge;
import utilPack.ErpUtilities;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;

//import basePackage.BasePge;

public class OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	MathUtilities mathUtils;
	PdfUtilities pdfUtils;
	ErpUtilities erpUtils;

	Login login = new Login(driver); 
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();
	NormalSaleGoldandSilver_Obj NormalSaleGoldandSilverObj = new NormalSaleGoldandSilver_Obj();
	OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj = new OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj();
	OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_TestData OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData = new OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_TestData();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj = new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();

	public OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		pdfUtils=new PdfUtilities(base);
		mathUtils = new MathUtilities(base);
		erpUtils = new ErpUtilities(base);
	}

	// <summary>
	// Test Case Title : Own Uncut Exchange From Store Transactions
	// Automation ID : TC59
	// Author: Robin T Abraham
	// </summary>
	public void TC59_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 2: Click on Transaction button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer and click on add to estimation button
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_CustomerID, UtilityTestData.MenuBarOptions[5]);

		// Step 4: Scan multiple SKU
		Map<String, String> ScannedSkuData = new LinkedHashMap<>();

		List<String> AllSkuList = new ArrayList<>();

		List<String> GoldSkuList = appUtils.SelectMultipleSKUs(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_GoldSkuCount,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_GoldTransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_GoldFromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_GoldMetalType
				);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		List<String> DiamondSkuList = appUtils.SelectMultipleSKUs(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_DiamondSkuCount,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_DiamondTransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_DiamondFromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_DiamondMetalType
				);

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		List<String> UncutSkuList = appUtils.SelectMultipleSKUs(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_UncutSkuCount,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_UncutTransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_UncutFromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_UncutMetalType
				);

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		List<String> PreciaSkuList = appUtils.SelectMultipleSKUs(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_PreciaSkuCount,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_PreciaTransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_PreciaFromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_PreciaMetalType
				);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		AllSkuList.addAll(GoldSkuList);
		AllSkuList.addAll(DiamondSkuList);
		AllSkuList.addAll(UncutSkuList);
		AllSkuList.addAll(PreciaSkuList);

		//Step 5 : Select OU
		//Step 6 : Click on OU own
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),"OU");
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldUncutOwnProduct));	

		//Step 7. Select configuration(purity 22k)
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_Purity);

		//Step 8. Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"));
		Thread.sleep(3000);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_ReceiptNumber);

		//Step 9. Enter line number
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_LineNumber );
		//Step 10. Click on fetch button
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));

		//Step 11. Select QC person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_QCPerson);

		//Step 12. Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_SmithPerson);

		//Step 13. click on calculate button
		/*Note: Check displayed values  NEED TO DO CODING
				Check Exchange rate(Board rate)
		 * Check calculation
				 Note:Billling Screen
		 *No of Product lines
		 *Displayed Item Name
		 *Displayed Quantity
		 *Displayed TOTAL (without Tax)
		 *Displayed Subtotal
		 *TAX
		 *Total Amount*/
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

		//Step 14. Click on add to cart button
		base.buttonClick(LoginPageObj.Btn_SingnIn("SimpleProductDetailsView_AppBarView_appBarCommandCommand"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Step 15. Save to estimate
		//Expected Results :Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		/*String EstmnNumber=base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));*/
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

	}
	//<summary>
	// Test Case Title : own diamond manual purchase
	// Automation ID : TC61
	// Author: Chandana Babu
	// </summary>
	public void TC61_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4: Select OD
		// Step 5: Click on OD own
		// Step 6: Select configuration(purity 22K)
		// Step 7: Click on Add item button and click on purchase button
		// Step 8: Select QC person
		// Step 9: Select Smith person
		// Step 10: Enter piece value
		// Step 11: Enter gross weight
		// Step 12: Enter Diamond pieces,Weight and rate
		// Expected: Check Purchase rate(Board rate) and Check calculation
		// Step 13: click on calculate button
		// Step 14: Click on Add to cart button
		// Step 15: Choose a Sales Representative
		// Note: Continue step 5-13 ( select the purity 18K)
		List<String> Purities = Arrays.asList("22k","18k");
		List<String> ItemNamesInItemDetailsPge = new ArrayList<>();
		List<String> GrossWeight = new ArrayList<>();
		List<String> NetWeight = new ArrayList<>();
		List<String> GoldRate = new ArrayList<>();
		List<String> DiamondWeight = new ArrayList<>();
		List<String> DiamondRate = new ArrayList<>();
		List<String> TotalAmount = new ArrayList<>();
		String ActualTotalAmountInBillingPge ="";

		for(String Purity : Purities) {
			Thread.sleep(3000);
			base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_Product);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldDiamondProduct));	

			base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),Purity);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

			Thread.sleep(1000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_QcAndSmithPerson);

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_PiecesByPurity.get(Purity));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_GrossWeightByPurity.get(Purity));

			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(4000);
			double CurrentGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));

			base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
			Thread.sleep(1000);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));

			Thread.sleep(1000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_QcAndSmithPerson);

			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_QcAndSmithPerson); 

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_PiecesByPurity.get(Purity));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdpcs"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdpcs"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_DiamondPiecesByPurity.get(Purity));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_GrossWeightByPurity.get(Purity));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_DiamondWeightByPurity.get(Purity));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_DiamondRateByPurity.get(Purity));

			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(3000);
			double ActualGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
			double ExpectedGoldRateInItemDetailsPge = (double) Math.round(CurrentGoldRateInItemDetailsPge*0.98);
			asrt.assertEquals(ExpectedGoldRateInItemDetailsPge, ActualGoldRateInItemDetailsPge,1.0,"ODPurchaseRate mismatch: Expected value is "+ExpectedGoldRateInItemDetailsPge+" but got "+ActualGoldRateInItemDetailsPge+" in item details page");

			String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));		
			double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
			double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
			String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
			double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);
			asrt.assertEquals(ExpectedGoldAmountInItemDetailsPge, ActualGoldAmountInItemDetailsPge,1.0,"GoldAmount mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in item details page");		

			String ExpectedDiamondWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"));
			String ExpectedDiamondRateInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"));
			double ExpectedDiamondAmountInItemDetailsPge = Math.round(Double.parseDouble(ExpectedDiamondWeightInItemDetailsPge)*Double.parseDouble(ExpectedDiamondRateInItemDetailsPge)*100.0)/100.0;
			String DiamondAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdamt"));
			double ActualDiamondAmountInItemDetailsPge = Double.parseDouble(DiamondAmountInItemDetailsPge);
			asrt.assertEquals(ExpectedDiamondAmountInItemDetailsPge, ActualDiamondAmountInItemDetailsPge,1.0, "Diamond amount mismatch: Expected is "+ExpectedDiamondAmountInItemDetailsPge+" but got "+ActualDiamondAmountInItemDetailsPge+" in item details page");

			String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
			double ActualTotalAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
			double ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPge+ActualDiamondAmountInItemDetailsPge;
			asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

			String ItemNameForOD = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
			String ExpectedItemNameInItemDetailsPge = ItemNameForOD.substring(ItemNameForOD.indexOf("-") + 2);

			ItemNamesInItemDetailsPge.add(ExpectedItemNameInItemDetailsPge);
			GrossWeight.add(ExpectedGrossWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			NetWeight.add(ExpectedNetWeightInItemDetailsPge);
			GoldRate.add(Double.toString(ActualGoldRateInItemDetailsPge));
			DiamondWeight.add(ExpectedDiamondWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			DiamondRate.add(ExpectedDiamondRateInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			TotalAmount.add(TotalAmountInItemDetailsPge);

			if (base.isExists(NormalSaleGoldandSilverObj.Ele_Payement("Error"))) {
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			};
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
			base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
			
			double SubTotalSum = 0.00;
			List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
			for(int i =0; i < AllProductRows.size() ;i++) {

				//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
				int ExpectedLinesCountInBillingPge = AllProductRows.size();
				int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));
				asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");

				List<WebElement> ItemName = base.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
				String ActualItemNameInBillingPge = ItemName.get(i).getText();
				String ExpectedItemNameInBillingPge = ItemNamesInItemDetailsPge.get(i);
				asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");

				List<WebElement> Quantity = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
				String ActualQuantityInBillingPge = Quantity.get(i).getText().replace("-", "");
				String ExpectedQuantityInBillingPge = GrossWeight.get(i);
				asrt.assertEquals(ExpectedQuantityInBillingPge, ActualQuantityInBillingPge,"Quantity mismatch: Expected is "+ExpectedQuantityInBillingPge+" but got "+ActualQuantityInBillingPge+" in billing page");

				List<WebElement> TotalWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));
				String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replace("-", "").replace("₹", "").replace(",", "").trim();
				SubTotalSum = SubTotalSum+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
				String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(TotalAmount.get(i)));
				asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge,"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
			}
			String ExpectedSubTotalInBillingPge = String.format("%.2f",SubTotalSum);
			String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
			asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

			String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
			String ExpectedTaxInBillingPge = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_ODTax;
			asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");

			String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
			ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
			asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");
		}

		// Step 16: Click on the Amount
		String ActualAmountDueInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("₹", "").replace(",", "").trim();
		String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("₹", "").replace(",", "").trim();
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		// Step 17: Click on RTGS/NEFT
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("RTGS/NEFT"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment","iconReturnKey"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		// Step 18: Post invoice
		// Expected: Check final invoice details
		String PaymentDetails = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_PaymentDetails;
		Map<String, String> PdfDetails = pdfUtils.ValidateODPdf(
				PaymentDetails, 
				GrossWeight, 
				NetWeight, 
				GoldRate, 
				DiamondWeight, 
				DiamondRate, 
				TotalAmount, 
				ActualTotalAmountInBillingPge, 
				ActualAmountDueInBillingPge);
		String InvoiceNumber = PdfDetails.get("InvoiceNumber");
		String CustomerName = PdfDetails.get("CustomerName");
		
		//Expected :In ERP, 1. search RTGS payment details and 2.verify RTGS entry line updated
		erpUtils.RTGSPaymentDetails(CustomerName,ReceiveAmtFromInBillingPge);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
}