
package testPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.CustomerCreation_Obj;
import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import objectRepository.OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj;
import testData.CommonData;
import testData.OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.AppUtilities.SKUResult;
import utilPack.AppUtilities.SKUTypeResult;
import utilPack.BasePge;
import utilPack.ErpUtilities;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;

//import basePackage.BasePge;

public class OldPurchaseExchangeOwnorOtherGoldorSilverSale extends BasePge {

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
	OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData = new OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData();
	CustomerCreation_Obj CustomerCreationObj = new CustomerCreation_Obj();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj =  new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();

	public OldPurchaseExchangeOwnorOtherGoldorSilverSale(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		pdfUtils=new PdfUtilities(base);
		mathUtils = new MathUtilities(base);
		erpUtils = new ErpUtilities(base);
	}

	// <summary>
	// Test Case Title : Own OG multiple purity(22k,18k) in exchange by manual entry
	// Intermittent Failure: Step 7: An 'Error' popup is displayed on the Old Gold Details page during the execution of PurchaseOldGold method.
	// Intermittent Failure: Step 14: Sales Representative selection alert intermittently appears after adding a 22K Old Gold item.
	// Intermittent Failure: Step 14:'Cart has changed' alert intermittently appears after adding the second 18K Old Gold item
	// Automation ID : TC40_OgOwnMultiplePurityInExchangeByManualEntry
	// Author: Hasna E K
	// </summary>
	public void TC40_OldPurchaseExchangeOwnorOtherGoldorSilverSale() throws Exception { 	

		//Step1 :Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2: Navigate to the Transaction page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		//Step 3:Select customer and click on add to estimation button
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_CustomerId, "AddCustomerForSalesEstimationCommand");

		//Step 4:Scan a SKU and click add to cart button
		//Expected Result: Check calculation
		SKUResult SkuOGSale = appUtils.GetOGSaleSku(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_TransferType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_FromCounter,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_MetalType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SaleQty,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SkuCount);
		List<String> SkuList = SkuOGSale.PcsRateSKU;
		System.out.println("SkuList"+SkuList);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String,String> ScannedSkuDataMap = new HashMap();
		////			appUtils.SKUIngridentTableValues(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_Sku.get(0),
		//					OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SkuCount,ScannedSkuDataMap);
		appUtils.SKUIngridentTableValues(SkuList.get(0),
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SkuCount,ScannedSkuDataMap);
		String ExpectedGrossValue = ScannedSkuDataMap.get("SKU_" + OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SkuCount + "_GrossWeight");
		String ExpectedItemName = ScannedSkuDataMap.get("SKU_" + OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SkuCount + "_ItemName");
		String TotalForEachItem= ScannedSkuDataMap.get("SKU_" + OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SkuCount + "_Total");
		String RateKey = "SKU" + OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SkuCount  + "_ITEM" + OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SkuCount  + "_RATE";
		String BoardRateFromSkuTble = ScannedSkuDataMap.get(RateKey);
		Double SkuBoardRate = Double.parseDouble(BoardRateFromSkuTble);
		String Purity = ScannedSkuDataMap.get("SKU_" + OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SkuCount + "_Purity");
		double BoardRate22k=0;
		if (Purity.equalsIgnoreCase("18k"))
		{
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 18;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		}else if(Purity.equalsIgnoreCase("14k")){
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 14;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		}else if(Purity.equalsIgnoreCase("22k")){
			BoardRate22k = SkuBoardRate;

		}
		//		System.out.println("BoardRate22k "+BoardRate22kFrmSkuTble);
		//		BoardRate22k = Double.parseDouble(BoardRate22kFrmSkuTble);

		mathUtils.SkuIngredientItemCalculation(ScannedSkuDataMap, OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SkuCount);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		//Step 5: Select OG
		//Step 6:Click on OG own
		//Step 7: Select configuration(purity 22K)
		//Step 8: Click on Add item button
		//Choose Exchange
		//Step 9:Select QC person
		//Step 10:Select Smith person
		//Step 11:Enter piece value
		//Step 12:Enter gross weight
		//Step 13:click on calculate button
		//Expected Result:  Check Exchange rate(Board rate ) ,* Check calculation Note:Billling Screen , *No of Product lines
		//*Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		appUtils.PurchaseOldGold(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_Configuration, 
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_PurchaseOrExchange,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_QCPerson,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SmithPerson,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_GrossPieces,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_GrossWeight);
		Map<String, Double> Og22kData = mathUtils.ValidateOldGoldItemDetailsData(BoardRate22k, OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_Configuration);
		Double OgQty22k = Og22kData.get("OgQty");
		Double OgCvalue22k = Og22kData.get("OgCvalue");

		//Step 14:Click on Add to cart button
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		By SalesRepSelectAlert =NormalSaleGoldandSilverObj.Ele_Name("salesRepresentativesButtonGrid");
		By SalesRep = NormalSaleGoldandSilverObj.Ele_SKUSalesRep("margin0","1");
		try {
			WebElement SalesRepAlertModal = Wait.until(ExpectedConditions.visibilityOfElementLocated(SalesRepSelectAlert));
			WebElement SelectSalesRep = Wait.until(ExpectedConditions.elementToBeClickable(SalesRep));
			SelectSalesRep.click();

		} catch (TimeoutException e) {
			WebDriverWait SalesRepWait = new WebDriverWait(driver, Duration.ofSeconds(2));
			SalesRepWait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Edt_AlertText(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_CustomerId)));
		}

		//Continue step 5-14 ( select the purity 18K)
		appUtils.PurchaseOldGold(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_Configuration18k,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_PurchaseOrExchange,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_QCPerson,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_SmithPerson,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_GrossPieces,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_GrossWeight);
		Map<String, Double> Og18kData = mathUtils.ValidateOldGoldItemDetailsData(BoardRate22k, OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_Configuration18k);
		Double OgQty18k = Og18kData.get("OgQty");
		Double OgCvalue18k = Og18kData.get("OgCvalue");
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		
		By CartChangedAlert = NormalSaleGoldandSilverObj.Ele_CustomerAdjustment("cart has changed");
		By OkBtn = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");

		if (base.isElementPresent(driver, CartChangedAlert)) {		
			try {
				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
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
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> TextItemNamesBilling =new ArrayList<>();
		List<String> TextItemQtyBilling =new ArrayList<>();

		int ExpectedLinesCount = AllProductRows.size();	
		asrt.assertEquals(ExpectedLinesCount,LinesCountBilling, "Mismatch in Lines count,expected "+ExpectedLinesCount+" but got "+LinesCountBilling+" in the billing screen");

		String SkuItemRate = ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.\\-]", "");
		double FirstItemRate = Double.parseDouble(SkuItemRate);
		double ExpectedSubTotal = 0.0;
		ExpectedSubTotal = FirstItemRate-(OgCvalue18k+OgCvalue22k);

		double ExpectedTaxAmnt = FirstItemRate * 3 / 100;
		double ActualTotalAmount = ExpectedSubTotal + ExpectedTaxAmnt;

		asrt.assertEquals(ExpectedTaxAmnt,TaxBilling,1.0,"Mismatch in Tax value, expected"+ExpectedTaxAmnt+" but got "+TaxBilling+" in the billing screen");

		for (int n=0; n<ItemNamesBilling.size();n++) 
		{
			TextItemNamesBilling.add(ItemNamesBilling.get(n).getText().trim());
		}
		Thread.sleep(3000);
		String ExpectedFull = ExpectedItemName.trim();
		String ExpectedTrimmed = ExpectedFull.contains("-") ?ExpectedFull.substring(ExpectedFull.indexOf("-") + 2) : ExpectedFull;
		String ActualItem = ItemNamesBilling.get(0).getText().trim();

		asrt.assertEquals(ExpectedTrimmed,ActualItem, "Mismatch in item name, expected "+ExpectedTrimmed+"but got "+ActualItem+"  in the billing screen.");

		for (int i=1; i<TextItemNamesBilling.size();i++) 
		{
			String ActualItemOg = TextItemNamesBilling.get(i).trim();
			asrt.assertEquals(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_OgItemName, ActualItemOg,"Mismatch in OG item name, expected "+OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_OgItemName+" but got "+ActualItemOg+" in the billing screen.");

		}		

		for (int i = 0; i <1; i++)
		{
			String ExpectedTotalStr = ScannedSkuDataMap.get("SKU_" + (i + 1) + "_Total");
			double ExpectedTotal = Double.parseDouble(ExpectedTotalStr.replaceAll("[^\\d.]", ""));
			String ActualTotalStr = ItemRateBilling.get(i).getText().trim();
			double ActualTotal = Double.parseDouble(ActualTotalStr.replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpectedTotal, ActualTotal,1.0,"Mismatch in total amounts (without tax),expected "+ExpectedTotal+" but got "+ActualTotal+" in the billing screen.");		
		}

		String TextItemRateBilling= ItemRateBilling.get(1).getText().replace("-", "").trim();
		Double ActualRateOg22k = Double.parseDouble(TextItemRateBilling.replace("\u20B9", "").replace(",", "").trim());

		asrt.assertEquals(OgCvalue22k, ActualRateOg22k,1.0, "Mismatch in total amount for 22K OG, expected "+OgCvalue22k+" but got "+ActualRateOg22k+" in Billing screen");

		String TextItmRateBilling= ItemRateBilling.get(2).getText().replace("-", "").trim();
		Double ActualRateOg18k = Double.parseDouble(TextItmRateBilling.replace("\u20B9", "").replace(",", "").trim());

		asrt.assertEquals(OgCvalue18k, ActualRateOg18k,1.0,"Mismatch in total amount for 18K OG,expected "+OgCvalue18k+" but got "+ActualRateOg18k+" in Billing screen");

		for (int n=0; n<ItemQtyBilling.size();n++) 
		{
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		String ExpectedGrossWt = ExpectedGrossValue.replace("-", "").trim();
		Double ExpectedQty = Double.parseDouble(ExpectedGrossWt);
		String BillingQtySku = ItemQtyBilling.get(0).getText().replace("-", "").trim();
		Double ActualGrossWtSku = Double.parseDouble(BillingQtySku.replace("\u20B9", "").replace(",", "").trim());
		asrt.assertEquals(ExpectedQty, ActualGrossWtSku,1.0,"Mismatch in Qty value,expected "+ExpectedQty+" but got "+ActualGrossWtSku+" in the Billing screen");		

		String BillingQtyOg22k = ItemQtyBilling.get(1).getText().replace("-", "").trim();
		Double ActualQtyOg22k =Double.parseDouble(BillingQtyOg22k.replace("\u20B9", "").replace(",", "").trim());
		asrt.assertEquals(OgQty22k,ActualQtyOg22k,1,"Mismatch in Qty value for 22k OG ,expected "+OgQty22k+" but got "+ActualQtyOg22k+" in the Billing screen");

		String BillingQtyOg18k = ItemQtyBilling.get(2).getText().replace("-", "").trim();
		Double ActualQtyOg18k =Double.parseDouble(BillingQtyOg18k.replace("\u20B9", "").replace(",", "").trim());
		asrt.assertEquals(OgQty18k,ActualQtyOg18k,1.0,"Mismatch in Qty value for 18k OG ,expected "+OgQty18k+" but got "+ActualQtyOg18k+" in the Billing screen");
		asrt.assertEquals(ExpectedSubTotal,SubTotalBilling,1.0,"Mismatch in Subtotal value, expected "+ExpectedSubTotal+" but got "+SubTotalBilling+"in the Billing screen");	
		asrt.assertEquals(TotalAmountBilling,ActualTotalAmount,1.0,"Mismatch in Total amount value, expected "+TotalAmountBilling+" but got "+ActualTotalAmount+" in the Billing screen");

		//Step 15: Save to estimate
		//Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(4000);	
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC40_ProformaInvoice");
		Thread.sleep(4000);		
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FirstInvoice+"";
		String TaxableValue = SkuItemRate;
		String InvoiceTotal = TotalAmount;
		String Gst = Tax;
		String TotalAmnt =SubTotal;
		//			String PdfPath = ProformaInvoicePath;

		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProformaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				TotalAmnt
				//Double.parseDouble(TotalGrossWeight),
				//Double.parseDouble(TotalNetWeight)
				);

		//Step 16:Recall estimate from cashier
		//Expected Result:Verify whether the recalled item is the same as the item in the cart.
		//Note:-12.Cashier Screen after recall, *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));			

		appUtils.ValidateBillingAndRecallScreenData(EstmnNumber,LinesCount,TextItemNamesBilling,TextItemQtyBilling, SubTotal, Tax, NetTotal);

		// Step 17: Select transaction type as Exchange
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_TransactionType1);

		// Step 18: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 

		// Step 19: Choose any Sales Representative
		base.setZoom(driver, 60);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		// Step 20:  Click on the Amount 0.00
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) {
			try { 
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} catch (Exception e) {
			}
		} 

		// Step 21:  Post exchange invoice
		// Expected Result: Check Exchange invoice details
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> PurchaseBillData =pdfUtils.OGPdfValidation(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_PaymentDetails);
		String ExchngeAdjustment =PurchaseBillData.get("PaymentDetails");

		//Step 22: Again recall estimate from cashier screen
		// Expected Result: User should able to recall the estimate for sales return
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Step 23: Select the Transaction Type as Sales        
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_TransactionType2);

		// Step 24: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		// Step 25: Choose a Sales Representative
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));	
		appUtils.HandleSpecifyRateModalInRecall();

		String Discount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC= base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		Map<String, String> SkuBillingScrnTbleData = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SkuNetTotal = SkuBillingScrnTbleData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTbleData.get("LinesCount");

		//Step 26: Click on amount
		//Step 27: Select payment method(cash/card)
		appUtils.PaymentAfterRecallEstimate(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_PaymentMethod);

		//Step 28: Post exchange invoice(sale)
		//Expected Result: Check final invoice details,* save receipt id for future reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC40_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";

		String GrossAmount=SkuItemRate;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String Adjustment=ExchngeAdjustment;
		String TotalDiamond= OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC40_TotalDiamond;
		String PaymentHDFCCard = PaymentAmountHDFC;
		String SalesPdfPath = SaleInvoicePath;
		TotalNetWeight =ExpectedGrossWt;
		TotalGrossWeight= ExpectedGrossWt;
		String NetValue = SkuNetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
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
				SkuList,
				ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:-"+PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}


	// <summary>
	// Test Case Title : own OG purchase by receipt number fetch -refund
	// Automation ID : TC41
	// Author: Chandana Babu
	// </summary>
	public void TC41_OldPurchaseExchangeOwnorOtherGoldorSilverSale() throws Exception {

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4: Select OG
		Thread.sleep(3000);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		//base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),"OG");
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));

		// Step 5: Click on OG own
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));	

		// Step 6: Select configuration(purity 22K)
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_Purity);

		// Step 7: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		//base.buttonClick(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), UtilityTestData.TC41_InvoiceNo);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_LineNo);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));
		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_QcAndSmithPerson);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_QcAndSmithPerson); 
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(10000);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		double CurrentGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));

		// Step 8: click on purchase button
		Thread.sleep(2000);
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));

		// Step 9: Enter receipt number
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), UtilityTestData.TC41_InvoiceNo);

		// Step 10: Enter line number
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_LineNo);

		// Step 11: click on fetch button and Select QC person
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));
		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_QcAndSmithPerson);

		// Step 12: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_QcAndSmithPerson); 

		// Step 13 : click on calculate button
		//Expected: Check Purchase rate(Board rate) and Check calculation
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(2000);
		double ActualOGPurchaseRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		double ExpectedOGPurchaseRateInItemDetailsPge = Math.round((CurrentGoldRateInItemDetailsPge*0.98)*100.0)/100.0;

		asrt.assertEquals(ExpectedOGPurchaseRateInItemDetailsPge, ActualOGPurchaseRateInItemDetailsPge,1.0,"OGPurchaseRate mismatch: Expected value is "+ExpectedOGPurchaseRateInItemDetailsPge+" but got "+ActualOGPurchaseRateInItemDetailsPge+" in item details page");

		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
		double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
		double ExpectedAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualOGPurchaseRateInItemDetailsPge * 100.0) / 100.0;
		double ActualAmountInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(ExpectedAmountInItemDetailsPge, ActualAmountInItemDetailsPge,1.0,"Amount mismatch: "+ExpectedAmountInItemDetailsPge+" but got "+ActualAmountInItemDetailsPge+" in item details page");				

		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		String ExpectedTotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
		String ActualTotalAmountInItemDetailsPge = Double.toString(ActualAmountInItemDetailsPge);

		asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalAmountInItemDetailsPge,"Total amount mismatch: "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

		String ItemName = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = ItemName.substring(ItemName.indexOf("-") + 2);

		// Step 14: Click on Add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ExpectedLinesCountInBillingPge = AllProductRows.size();
		int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));
		asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");

		String ActualItemNameInBillingPge = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		asrt.assertEquals(ExpectedItemNameInItemDetailsPge, ActualItemNameInBillingPge,"Item name mismatch: "+ExpectedItemNameInItemDetailsPge+" but found "+ActualItemNameInBillingPge+" in billing page");

		String ActualGrossWeightInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5")).replace("-", "");
		asrt.assertEquals(ExpectedGrossWeightInItemDetailsPge, ActualGrossWeightInBillingPge,"Gross weight mismatch: "+ExpectedGrossWeightInItemDetailsPge+" but found "+ActualGrossWeightInBillingPge+" in billing page");

		String ActualTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalWithoutTaxInBillingPge,"Total without tax mismatch: "+ExpectedTotalAmountInItemDetailsPge+" but found "+ActualTotalWithoutTaxInBillingPge+" in billing page");

		String ExpectedSubTotalInBillingPge = ActualTotalWithoutTaxInBillingPge;
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_OGTax;
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");

		String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
		String ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");

		// Step 15: Click on the Amount
		String ExpectedAmountDueFromBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		// Step 16: Click on RTGS/NEFT
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("RTGS/NEFT"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment","iconReturnKey"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		// Step 17: Post invoice
		// Expected: Check final invoice details
		String ExpectedGrossWeightFromBillingPge = ActualGrossWeightInBillingPge;
		String ExpectedOGPurchaseRateFromItemDetailsPge = String.format("%.2f",ActualOGPurchaseRateInItemDetailsPge);
		Map<String, String> OgPurchaseBill = pdfUtils.OGPdfValidation(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_PaymentDetails);
		String GrossWeightFromInvoice = OgPurchaseBill.get("GrossWt1");
		String NetWeightFromInvoice = OgPurchaseBill.get("NetWt1");
		String GoldRateFromInvoice = OgPurchaseBill.get("Rate1");
		String TotalAmountFromInvoice = OgPurchaseBill.get("TotalAmount1");
		String PaymentDetailsFromInvoice = OgPurchaseBill.get("PaymentDetails");
		asrt.assertEquals(ExpectedGrossWeightFromBillingPge, GrossWeightFromInvoice,"Gross Weight mismatch: "+ExpectedGrossWeightFromBillingPge+" but got "+GrossWeightFromInvoice+" in invoice");		
		asrt.assertEquals(ExpectedNetWeightInItemDetailsPge, NetWeightFromInvoice,"Net Weight mismatch: "+ExpectedNetWeightInItemDetailsPge+" but got "+NetWeightFromInvoice+" in invoice");	
		asrt.assertEquals(ExpectedOGPurchaseRateFromItemDetailsPge, GoldRateFromInvoice,"Gold rate mismatch: "+ExpectedOGPurchaseRateFromItemDetailsPge+" but got "+ GoldRateFromInvoice+" in invoice");	
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, TotalAmountFromInvoice,"Total amount mismatch: "+ExpectedTotalAmountInBillingPge+" but got "+TotalAmountFromInvoice+" in invoice");	
		asrt.assertEquals(ExpectedAmountDueFromBillingPge, PaymentDetailsFromInvoice,"Payment details mismatch: "+ExpectedTotalAmountInBillingPge+" but got "+PaymentDetailsFromInvoice+" in invoice");

		//Expected :In ERP, 1. search RTGS payment details and 2.verify RTGS entry line updated
		String CustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();
		erpUtils.RTGSPaymentDetails(CustomerName,ReceiveAmtFromInBillingPge);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : OG purchase converting to GPP/Advance/RTGS
	// Automation ID : TC47
	// Author: Jhoncy Joseph
	// </summary>
	public void TC47_OldPurchaseExchangeOwnorOtherGoldorSilverSale() throws Exception {

		//Step 1: Login to POS	
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4: Select OG
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.OGProduct);
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");

		// Step 5: Click on OG own
		Thread.sleep(7000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));	

		// Step 6: Select configuration(purity 22K)		
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_Purity);

		// Step 7: Click on Add item button
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));

		// To calculate Purchase rate
		//Step 8:Select QC person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_QcAndSmithPerson);

		//Step 9: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_QcAndSmithPerson); 

		//Step 10: Enter piece value
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_GrossPieces);

		//Step 11: Enter gross weight
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_GrossWeight);

		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(10000);
		double ExchangeBoardRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		System.out.println(ExchangeBoardRateInItemDetailsPge);
		//Note: click on purchase button
		Thread.sleep(3000);
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));

		// Step 12 : click on calculate button
		//Expected: Check Purchase rate(Board rate) and Check calculation
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(5000);
		double ActualOGPurchaseRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		double ExpectedOGPurchaseRateInItemDetailsPge = Math.round((ExchangeBoardRateInItemDetailsPge*0.98)*100.0)/100.0;

		asrt.assertEquals(ExpectedOGPurchaseRateInItemDetailsPge, ActualOGPurchaseRateInItemDetailsPge,1.0,"OG Purchase Rate mismatch: Expected value is "+ExpectedOGPurchaseRateInItemDetailsPge+" but got "+ActualOGPurchaseRateInItemDetailsPge+" in item details page");

		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
		double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
		double ExpectedAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualOGPurchaseRateInItemDetailsPge * 100.0) / 100.0;
		double ActualAmountInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(ExpectedAmountInItemDetailsPge, ActualAmountInItemDetailsPge,1.0,"Cvalue mismatch: "+ExpectedAmountInItemDetailsPge+" but got "+ActualAmountInItemDetailsPge+" in item details page");		
		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		String ExpectedTotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
		String ItemName = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = ItemName.substring(ItemName.indexOf("-") + 2);
		String ExpectedGrossWeight = String.valueOf(Double.parseDouble(ExpectedGrossWeightInItemDetailsPge));
		String ExpectedTotalAmount = String.valueOf(Double.parseDouble(ExpectedTotalAmountInItemDetailsPge));

		// Step 13: Click on Add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ExpectedLinesCountInBillingPge = AllProductRows.size();
		int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));
		asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");

		String ActualItemNameInBillingPge = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		asrt.assertEquals(ExpectedItemNameInItemDetailsPge, ActualItemNameInBillingPge,"Item name mismatch: "+ExpectedItemNameInItemDetailsPge+" but found "+ActualItemNameInBillingPge+" in Billing page");

		String ActualGrossWeightInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5")).replace("-", "");
		String ActualGrossWeight = String.valueOf(Double.parseDouble(ActualGrossWeightInBillingPge));
		asrt.assertEquals(ExpectedGrossWeight, ActualGrossWeight,"Gross weight mismatch: "+ExpectedGrossWeight+" but found "+ActualGrossWeight+" in Billing page");

		String ActualTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualTotalWithoutTax = String.valueOf(Double.parseDouble(ActualTotalWithoutTaxInBillingPge));		
		asrt.assertEquals(ExpectedTotalAmount, ActualTotalWithoutTax,"Total without tax mismatch: "+ExpectedTotalAmount+" but found "+ActualTotalWithoutTax+" in Billing page");

		String ExpectedSubTotalInBillingPge = ActualTotalWithoutTax;
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualSubTotal = String.valueOf(Double.parseDouble(ActualSubTotalInBillingPge));	
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotal,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotal+" in Billing page");

		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_OGTax;
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in Billing page");

		String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;	
		String ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualTotalAmount = String.valueOf(Double.parseDouble(ActualTotalAmountInBillingPge));	
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmount,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmount+" in Billing page");

		// Step 14: Click on the Amount
		String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		// Step 15: Click on RTGS/NEFT
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("RTGS/NEFT"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment","iconReturnKey"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		// Step 16: Post invoice
		// Expected: Check final invoice details
		String ExpectedGrossWeightFromBillingPge = ActualGrossWeight;
		String ExpectedOGPurchaseRateFromItemDetailsPge = String.format("%.2f",ActualOGPurchaseRateInItemDetailsPge);
		Map<String, String> OgPurchaseBill = pdfUtils.OGPdfValidation(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_PaymentDetails);
		String GrossWeightFromInvoice = OgPurchaseBill.get("GrossWt1");
		String GrossWeight = String.valueOf(Double.parseDouble(GrossWeightFromInvoice));	
		String NetWeightFromInvoice = OgPurchaseBill.get("NetWt1");
		String GoldRateFromInvoice = OgPurchaseBill.get("Rate1");
		String TotalAmountFromInvoice = OgPurchaseBill.get("TotalAmount1");
		String TotalAmount = String.valueOf(Double.parseDouble(TotalAmountFromInvoice));	
		String PaymentDetailsFromInvoice = OgPurchaseBill.get("PaymentDetails");
		String PaymentDetails = String.valueOf(Double.parseDouble(PaymentDetailsFromInvoice));
		String InvoiceNo = OgPurchaseBill.get("InvoiceNumber");
		asrt.assertEquals(ExpectedGrossWeightFromBillingPge, GrossWeight,"Gross Weight mismatch: "+ExpectedGrossWeightFromBillingPge+" but got "+GrossWeight+" in Invoice Pdf");		
		asrt.assertEquals(ExpectedNetWeightInItemDetailsPge, NetWeightFromInvoice,"Net Weight mismatch: "+ExpectedNetWeightInItemDetailsPge+" but got "+NetWeightFromInvoice+" in Invoice Pdf");	
		asrt.assertEquals(ExpectedOGPurchaseRateFromItemDetailsPge, GoldRateFromInvoice,"Gold rate mismatch: "+ExpectedOGPurchaseRateFromItemDetailsPge+" but got "+ GoldRateFromInvoice+" in Invoice Pdf");	
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, TotalAmount,"Total amount mismatch: "+ExpectedTotalAmountInBillingPge+" but got "+TotalAmount+" in Invoice Pdf");	
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, PaymentDetails,"Payment details mismatch: "+ExpectedTotalAmountInBillingPge+" but got "+PaymentDetails+" in Invoice Pdf");
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		//Expected :In ERP, check for the latest entry in RTGS Payment details
		erpUtils.RTGSPaymentDetails(ActualCustomerName,ReceiveAmtFromInBillingPge.replaceAll("[^\\d.-]", ""));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}


	// <summary>
	// Test Case Title : own OG exchange by receipt number fetch
	// Automation ID : TC42
	// Author : Sangeetha M S
	// </summary>
	public void TC42_OldPurchaseExchangeOwnorOtherGoldorSilverSale() throws Exception
	{
		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Click on Transaction button
		Thread.sleep(5000);
		appUtils.HamBurgerButtonClick("iconShop");

		// Step 3:Select customer and click on add to estimation button
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_CustomerId, OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_Option);

		//Step 4 : Scan a SKU and click on add to cart button
		//Expected Results : Check calculation
		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> ExpectedItemNames = new ArrayList<>();
		List<String> TotalForEachItems = new ArrayList<>();
		List<String> ItemQuantity=new ArrayList<>();
		List<String> PurityOfItems=new ArrayList();
		List<Double> AllRates = new ArrayList<>();

		String OgItemNameInBillingPge=null;
		int i=0;
		int k=0;
		double TotalWithoutTax = 0.0;

		SKUResult SkuGoldSale = appUtils.GetOGSaleSku(
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_TransferType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_GoldFromCounter,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_MetalType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_GoldSaleQuantity,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_SkuCount);
		List<String> SkuGoldList = SkuGoldSale.PcsRateSKU;
		System.out.println("Gold Selected SKUs: " + SkuGoldList);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));	

		SKUResult SkuDiamondSale = appUtils.GetOGSaleSku(
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_TransferType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_DiamondFromCounter,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_MetalType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_DiamondSaleQuantity,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_SkuCount);
		List<String> SkuDiamondList = SkuDiamondSale.PcsRateSKU;
		System.out.println("Diamond Selected SKUs: " + SkuDiamondList);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));		

		SKUResult SkuUncutSale = appUtils.GetOGSaleSku(
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_TransferType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_UncutFromCounter,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_MetalType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_SaleQuantity,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_SkuCount);
		List<String> SkuUncutList = SkuUncutSale.PcsRateSKU;
		System.out.println("Uncut Selected SKUs: " + SkuUncutList);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));		

		SKUResult SkuPreciaSale = appUtils.GetOGSaleSku(
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_TransferType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_PreciaFromCounter,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_MetalType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_SaleQuantity,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_SkuCount
				);
		List<String> SkuPreciaList = SkuPreciaSale.PcsRateSKU;
		System.out.println("Precia Selected SKUs: " + SkuPreciaList);

		List<String> AllSkuList  	  = new ArrayList<>();	
		AllSkuList.addAll(SkuGoldList);
		AllSkuList.addAll(SkuDiamondList);
		AllSkuList.addAll(SkuUncutList);
		AllSkuList.addAll(SkuPreciaList);

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));		

		for (i = 0; i < AllSkuList.size(); i++)
		{
			String  CurrentSkuList = AllSkuList.get(i);
			int SkuCount = i + 1;
			appUtils.SKUIngridentTableValues(CurrentSkuList.toString(),SkuCount, SkuDetails);
			ExpectedItemNames.add(SkuDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItems.add(SkuDetails.get("SKU_" + (i+1) + "_Total"));
			ItemQuantity.add(SkuDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			PurityOfItems.add(SkuDetails.get("SKU_" + (i+1) + "_Purity"));
			for (int ItemIndex = 1; ; ItemIndex++) 
			{
				String Rate = "SKU" + SkuCount + "_ITEM" + ItemIndex + "_RATE";
				if (SkuDetails.containsKey(Rate)) 
				{
					try 
					{
						double SkuRate = Double.parseDouble(SkuDetails.get(Rate));
						AllRates.add(SkuRate);
					}
					catch (NumberFormatException e) 
					{
						System.out.println("Rate not fetched");					
					}
				} 
				else
				{
					break; 
				}
			}
			SkuDetails.put("SKU_" + SkuCount + "_AllRates", AllRates.toString());
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, SkuCount);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuCount);
		}

		//For scanned Sku list
		Map<String, String> SkuTransactionPageItems = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);
		String SkuTotalGrossWeight = SkuTransactionPageItems.get("GrossWeight");
		String SkuSubTotalValue = SkuTransactionPageItems.get("Subtotal");
		String SkuLinesCount=SkuTransactionPageItems.get("LinesCount");
		String SkuInvoiceTotal = SkuTransactionPageItems.get("TotalAmount");
		String SkuNetTotal = SkuTransactionPageItems.get("NetTotal");
		mathUtils.ValidateTransactionLineCalculation(SkuTransactionPageItems, SkuDetails);

		// Step 5: Select OG
		//Click on OG own
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),"OG");
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));	

		// Step 6: Select configuration(purity 22K)		
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_Purity);

		// Step 7: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		//Step 8. Enter receipt number
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), UtilityTestData.InvoiceNumber);

		//Step 9. Enter line number
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_LineNumber );

		//Step 10. Click on fetch button
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));

		//Step 11. Select QC person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_QCPerson);

		//Step 12. Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_SmithPerson);

		//Step 13. click on calculate button
		//Check Exchange rate(Board rate)
		//Check calculation: Billling Screen,No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		Thread.sleep(3000);
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

		//Check Exchange rate(Board rate)
		Thread.sleep(3000);
		double ActualOGRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		String Purity = PurityOfItems.get(0);
		double ExchangeRate =  AllRates.get(0);
		long RoundedExpectedOgRate=0;
		if (Purity.equalsIgnoreCase("18k")) 
		{
			double ExpectedOgRate = (ExchangeRate * 22) / 18;
			RoundedExpectedOgRate = Math.round(ExpectedOgRate);
			asrt.assertEquals(RoundedExpectedOgRate, Math.round(ActualOGRateInItemDetailsPge),1,"Exchange rate (Board rate) mismatch: Expected exchange rate is " + RoundedExpectedOgRate + " but got " + Math.round(ActualOGRateInItemDetailsPge) +" in OG item details page");
		} 
		else 
		{
			RoundedExpectedOgRate = Math.round(ExchangeRate);
			asrt.assertEquals(RoundedExpectedOgRate, Math.round(ActualOGRateInItemDetailsPge),1,"Exchange rate (Board rate) mismatch: Expected exchange rate is " +RoundedExpectedOgRate + " but got " + Math.round(ActualOGRateInItemDetailsPge) +" in OG item details page");
		}

		//Check Amount
		double NetWeightInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt")));
		double ExpectedAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualOGRateInItemDetailsPge * 100.0) / 100.0;	
		double ActualAmountInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));			
		asrt.assertEquals(ExpectedAmountInItemDetailsPge, ActualAmountInItemDetailsPge,"Amount mismatch: Expected amount is "+ExpectedAmountInItemDetailsPge+" but got "+ActualAmountInItemDetailsPge+" in OG item details page");		

		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		String OgTotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));	
		String OgItemName = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInOgItemDetailsPge = OgItemName.substring(OgItemName.indexOf("-") + 2);

		//Step 14. Click on add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));

		List<String> CurrentSkuList = Optional.ofNullable(AllSkuList).orElseGet(ArrayList::new);
		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(CurrentSkuList, SkuDetails);
		String SubTotal = TransactionPageItems.get("Subtotal");
		String Discount = TransactionPageItems.get("Discount");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight = TransactionPageItems.get("NetWeight");
		String Gst = TransactionPageItems.get("Tax");
		String InvoiceTotal = TransactionPageItems.get("NetTotal");
		String NetTotal = TransactionPageItems.get("NetTotal");
		String LinesCount = TransactionPageItems.get("LinesCount");

		//OG Item name
		List<WebElement> ListOfItemNamesInBillingPge = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));		
		boolean OgItemFound = false;
		for (WebElement Item : ListOfItemNamesInBillingPge)
		{
			String ItemNameFromBillingPage = Item.getText().trim();	    
			if (ItemNameFromBillingPage.equalsIgnoreCase("OG")) 
			{
				OgItemFound = true;
				OgItemNameInBillingPge = ItemNameFromBillingPage;
				Assert.assertEquals(OgItemNameInBillingPge, ExpectedItemNameInOgItemDetailsPge,"OG Item Name mismatch: Expected '" + ExpectedItemNameInOgItemDetailsPge + "' but got '" + OgItemNameInBillingPge + "' in Billing Page");
				break;
			}
		}
		if (!OgItemFound)
		{
			System.out.println("OG item not found in the Billing Page item list.");
		}

		//OG Quantity
		List<WebElement> BillingScreenItemQty=base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		for (i = 0; i < ListOfItemNamesInBillingPge.size(); i++) 
		{
			String itemNameInBilling = ListOfItemNamesInBillingPge.get(i).getText().trim();
			if (itemNameInBilling.equalsIgnoreCase(ExpectedItemNameInOgItemDetailsPge))
			{
				String DisplayedItemQty = BillingScreenItemQty.get(i).getText().trim().replaceAll("[^\\d.]", "");
				double DisplayedQty = Double.parseDouble(DisplayedItemQty);
				asrt.assertEquals(DisplayedQty, NetWeightInItemDetailsPge,"OG Item Quantity mismatch: Expected value is " + NetWeightInItemDetailsPge +" but got " + DisplayedQty + " in Billing page");
				break; 
			}
		}

		//OG Total amount without tax
		double OgItemDetailsPageTotal=0.0;
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		for (int j = 0; j < TotalsWithoutTax.size(); j++)
		{
			String ItemNameInBilling = ListOfItemNamesInBillingPge.get(j).getText().trim();
			String DisplayedTotalStr = TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", "");
			double DisplayedTotal = Double.parseDouble(DisplayedTotalStr);
			OgItemDetailsPageTotal=Double.parseDouble(OgTotalAmountInItemDetailsPge);
			if (ItemNameInBilling.equalsIgnoreCase(ExpectedItemNameInOgItemDetailsPge))
			{
				asrt.assertEquals(DisplayedTotal, OgItemDetailsPageTotal, 1, "OG item Total(Without Tax) mismatch: Expected " + OgItemDetailsPageTotal + " but got " + DisplayedTotal + " in Billing screen");
			}
		}

		//Subtotal
		double TotalWithoutTaxSum = 0.0;
		double OgTotal = 0.0;
		double TaxableAmount=0.0;
		for (int j = 0; j < TotalsWithoutTax.size(); j++) 
		{
			String ItemNameInBilling = ListOfItemNamesInBillingPge.get(j).getText().trim(); 
			String SubTotalStr = TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""); 

			if (!SubTotalStr.isEmpty())
			{
				double CalcSubTotal = Double.parseDouble(SubTotalStr);
				if (!ItemNameInBilling.equalsIgnoreCase(ExpectedItemNameInOgItemDetailsPge)) 
				{
					TotalWithoutTaxSum =TotalWithoutTaxSum+ CalcSubTotal;
					TaxableAmount=TotalWithoutTaxSum;			
				} 
				else
				{				
					TotalWithoutTaxSum = TotalWithoutTaxSum-CalcSubTotal;
					OgTotal=CalcSubTotal;
				}
			}
		}
		TotalWithoutTax = Math.round(TotalWithoutTaxSum * 100.0) / 100.0;
		String SubTotalBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"));
		double ExpectedSubTotalBillingPge = Double.parseDouble(SubTotalBillingPge.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(TotalWithoutTax, ExpectedSubTotalBillingPge,"SubTotal mismatch : Expected value is " + ExpectedSubTotalBillingPge + " but got " + TotalWithoutTax + " in Billing page");

		//TAX
		double TaxAmountBillingPge=TaxableAmount*3/100;
		String TaxBillingPage = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4"));
		double ExpectedTaxBillingPge=Double.parseDouble(TaxBillingPage.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(ExpectedTaxBillingPge,TaxAmountBillingPge ,1, "Tax Amount mismatch : Expected value is "+ExpectedTaxBillingPge+"but got "+TaxAmountBillingPge+" in Billing page");

		//Total Amount
		String TotalAmount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4"));
		double ExpectedTotalAmount=Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));
		double CalcTotalAmount=ExpectedTaxBillingPge+ExpectedSubTotalBillingPge;
		asrt.assertEquals(ExpectedTotalAmount, CalcTotalAmount, 1,"Total Amount mismatch : Expected value is "+ExpectedTotalAmount+" but got "+CalcTotalAmount+" in Billing page");

		//Step 15. Save to estimate
		//Expected Results :Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		String TaxableValue = Double.toString(TaxableAmount);
		String TotalAmnt = SubTotal;
		Gst = TaxBillingPage;
		InvoiceTotal = TotalAmount;
		String ProformaPdfName = pdfUtils.DownloadAndRenameLatestPDF("TC42_ProformaInvoice");
		String ProFormaInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+ProformaPdfName+"";
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				TotalAmnt
				);

		//Step 16. Recall estimate from cashier
		//Step 17. Select transaction type as Exchange
		//Expected Results : Verify whether the recalled item is the same as the item in the cart.
		//Cashier Screen after recall : No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_TransactionTypeOG);

		List<WebElement> OGItemNamesFromRecallPage = base.GetElement(NormalSaleGoldandSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		for (k = 0; k < OGItemNamesFromRecallPage.size(); k++) 
		{
			String ExpectedNameRecallPage = OGItemNamesFromRecallPage.get(k).getText().trim();

			if (ExpectedNameRecallPage.equalsIgnoreCase(ExpectedItemNameInOgItemDetailsPge)) 
			{
				//To validate OG Item name
				asrt.assertEquals(ExpectedNameRecallPage,ExpectedItemNameInOgItemDetailsPge,"OG Item Name mismatch : Expected value is "+ExpectedNameRecallPage+" but got "+ExpectedItemNameInOgItemDetailsPge+" in Recall page");

				//To validate quantity
				List<WebElement> QuantityFromRecall = base.GetElement(NormalSaleGoldandSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
				for(int l =0; l<QuantityFromRecall.size();l++) 
				{
					String DisplayedQtyRecall = QuantityFromRecall.get(l).getText().trim().replaceAll("[^\\d.]", "");
					double DisplayedRecallQty = Double.parseDouble(DisplayedQtyRecall);								
					double ExpectedRecallQty = Double.parseDouble(ExpectedGrossWeightInItemDetailsPge.trim().replaceAll("[^\\d.]", ""));

					asrt.assertEquals(ExpectedRecallQty,DisplayedRecallQty,"OG Quantity mismatch : Expected value is " + ExpectedRecallQty + " but got " + DisplayedRecallQty+ " in Recall page");
				}

				//To validate OG Total without tax
				double ActualTotalWithoutTax = Math.abs(Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax"))));

				asrt.assertEquals(OgItemDetailsPageTotal,ActualTotalWithoutTax,"OG Total(Without Tax) mismatch : value is "+OgItemDetailsPageTotal+" but got "+ActualTotalWithoutTax+" in Recall page");

				//To validate sub total
				double ActSubtotalFromRecall = Math.abs(Double.parseDouble( base.GetText(NormalSaleGoldandSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")).trim().replaceAll("[^\\d.-]", "")));
				double ExpSubTotal =  Math.abs(OgTotal);

				asrt.assertEquals(ExpSubTotal,ActSubtotalFromRecall,"OG SubTotal mismatch : Expected value is "+ExpSubTotal+" but got "+ActSubtotalFromRecall+" in Recall page");		
			}
		}

		//Step 18. Click on process estimation button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		//Step 19. Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Step 20. Click on amount 0.00
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) 
		{
			try 
			{ 
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} 
			catch (Exception e) 
			{
			}
		} 

		// Step 21:  Post exchange invoice
		// Expected Result: Check Exchange invoice details
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> PurchaseBillData =pdfUtils.OGPdfValidation("Adjustment");
		String ExchngeAdjustment =PurchaseBillData.get("PaymentDetails");

		//Step 22. Again recall estimate from cashier screen
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		//Step 23. Select transaction type as sale
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));	
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_TransactionType);

		List<WebElement> ItemNamesFromRecall = base.GetElement(NormalSaleGoldandSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		for (k = 0; k < ItemNamesFromRecall.size(); k++) 
		{
			String ExpectedNameRecall = ItemNamesFromRecall.get(k).getText().trim();
			String ItemNameFromBilling = ExpectedItemNames.get(k).trim();
			if (ItemNameFromBilling.equalsIgnoreCase(ExpectedItemNameInOgItemDetailsPge)) 
			{
				//To validate Item name
				asrt.assertEquals(ExpectedNameRecall,ItemNameFromBilling,"Item Name mismatch : Expected value is "+ExpectedNameRecall+" but got "+ItemNameFromBilling+" in Recall page");

				//To validate quantity
				List<WebElement> QuantityFromRecall = base.GetElement(NormalSaleGoldandSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
				for(int l =0; l<QuantityFromRecall.size();l++) 
				{
					String DisplayedQtyRecall = QuantityFromRecall.get(l).getText().trim().replaceAll("[^\\d.]", "");
					double DisplayedRecallQty = Double.parseDouble(DisplayedQtyRecall);
					String ExpectedQtyRecall = ItemQuantity.get(l).trim().replaceAll("[^\\d.]", "");			
					double ExpectedRecallQty = Double.parseDouble(ExpectedQtyRecall);

					asrt.assertEquals(ExpectedRecallQty,DisplayedRecallQty,"Quantity mismatch : Expected value is " + ExpectedRecallQty + " but got " + DisplayedRecallQty+ " in Recall page");
				}

				//To validate Total without tax
				double ActualTotalWithoutTax = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax")));

				asrt.assertEquals(ExpectedSubTotalBillingPge,ActualTotalWithoutTax,"Total(Without Tax) mismatch : value is "+ExpectedSubTotalBillingPge+" but got "+ActualTotalWithoutTax+" in Recall page");

				//To validate tax
				double ActualTax = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));

				asrt.assertEquals(ExpectedTaxBillingPge,ActualTax,"Tax Value mismatch : Expected value is "+ExpectedTaxBillingPge+" but got "+ActualTax+" in Recall page");

				//To validate sub total
				double ActualSubTotalFromRecall = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")));
				double ExpectedSubTotal = Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", ""));

				asrt.assertEquals(ExpectedSubTotal,ActualSubTotalFromRecall,"SubTotal mismatch : Expected value is "+ExpectedSubTotal+" but got "+ActualSubTotalFromRecall+" in Recall page");		
			}
		}

		// Step 24: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		// Step 25: Choose a Sales Representative
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));		

		//Step 26. Click on amount
		//Step 27. Select payment method(cash/card)		
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentAmountHDFC=base.GetAttribte(NormalSaleGoldandSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldandSilverObj.Edt_ApprCode("textInputArea"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_ApprCode);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldandSilverObj.Edt_ApprCode("textInputArea"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_CardCode);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Close"));

		//Step 28. Post exchange invoice
		//Expected Results :  Check final invoice details
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC42_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";

		String GrossAmount=SkuSubTotalValue;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String Adjustment=ExchngeAdjustment;
		String TotalDiamond= OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_TotalDiamond;
		String PaymentHDFCCard = PaymentAmountHDFC;
		String SalesPdfPath = SaleInvoicePath;
		TotalNetWeight =SkuTotalGrossWeight;
		TotalGrossWeight= SkuTotalGrossWeight;
		String NetValue = SkuNetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
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
				AllSkuList,
				SkuDetails
				);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Own OG multiple purity in purchase by manual entry-converted advance
	// Automation ID : TC39
	// Author: Nivya Ramesan
	// </summary>
	public void TC39_OwnOgMultiplePurityinPurchaseByManualEntryConvertedAdvance() throws Exception { 

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC39_CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4: Select OG
		// Step 5: Click on OG own
		// Step 6: Select configuration(purity 22K)
		// Step 7: Click on Add item button
		// Note: Click on purchase button
		// Step 8: Select QC person
		// Step 9: Select Smith person
		// Step 10: Enter piece value
		// Step 11: Enter gross weight
		// Step 12: click on calculate button
		// Expected: Check Purchase rate(Board rate) and Check calculation
		// Step 13: Click on Add to cart button
		// Note: Continue step 4-13 ( select the purity 18K)
		List<String> Purities = Arrays.asList("22k","18k");
		List<String> ItemNamesInItemDetailsPge = new ArrayList<>();
		List<String> GrossWeight = new ArrayList<>();
		List<String> NetWeight = new ArrayList<>();
		List<String> GoldRate = new ArrayList<>();
		List<String> TotalAmount = new ArrayList<>();
		String ActualTotalAmountInBillingPge ="";

		for(String Purity : Purities) {


			Thread.sleep(3000);
			base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),"OG");
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));	
			base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),Purity);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
			Thread.sleep(1000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC39_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC39_QcAndSmithPerson);
			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC39_GrossPieces);
			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC39_GrossWeight);
			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(4000);

			double CurrentGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
			base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
			Thread.sleep(1000);
			base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
			Thread.sleep(1000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC39_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC39_QcAndSmithPerson);
			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC39_GrossPieces);
			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC39_GrossWeight);
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

			String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
			double ActualTotalAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
			double ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPge;
			asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

			String ItemNameForOG = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
			String ExpectedItemNameInItemDetailsPge = ItemNameForOG.substring(ItemNameForOG.indexOf("-") + 2);

			ItemNamesInItemDetailsPge.add(ExpectedItemNameInItemDetailsPge);
			GrossWeight.add(ExpectedGrossWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			NetWeight.add(ExpectedNetWeightInItemDetailsPge);
			GoldRate.add(Double.toString(ActualGoldRateInItemDetailsPge));
			TotalAmount.add(TotalAmountInItemDetailsPge);
			Thread.sleep(2000);
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
				String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replace("-", "").replace("\u20B9", "").replace(",", "").trim();
				SubTotalSum = SubTotalSum+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
				String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(TotalAmount.get(i)));
				asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge,"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
			}
			String ExpectedSubTotalInBillingPge = String.format("%.2f",SubTotalSum);
			String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
			asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");


			String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
			ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
			asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");
		}

		//Step 14  : Click on the Amount
		//Step 15  : Select a Payment method as convert advance
		//Step 16  : Select transaction type as normal
		//Step 17  : Select sales person
		//Step 18  : Select the due date
		//Step 19  : Enter remarks
		//Step 20  : click on nominee details
		//Step 20.1  : Enter nominee name
		//Step 20.2  : Select nominee relation
		//Step 20.3  : Click on check box(Same as customer address)
		//Step 20.4  : Click on OK button
		//Step 21    : Click on Deposit button
		//Expected Result : Check ADVANCE RECEIPT VOUCHER details*Towards the advance against purchase of approximate value*Fixed Gold rate*Deposit Type*Advance Received* save receipt id for future reference      
		appUtils.PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
				UtilityTestData.SalePersonNumber,
				UtilityTestData.SalePersonName,
				UtilityTestData.DueYear,
				UtilityTestData.NomineeName,
				UtilityTestData.NomineeRelation);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		Map<String, String> Result = pdfUtils.NormalAdvancePdfValidation();	
		String AdvanceReceived     = Result.get("AdvanceReceived");
		String FixedGoldRate       = Result.get("FixedGoldRate");
		String MaxGoldRate         = Result.get("MaxGoldRate");
		String ApproxWeight        = Result.get("ApproxWeight");
		String DepositType         = Result.get("DepositType");
		double PdfAdvanceReceived  = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
		double PdfFixedGoldRate    = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
		double PdfMaxGoldRate      = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,0.10, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
		asrt.assertEquals(UtilityTestData.DepositType, DepositType, "Mismatch in Deposit Type Actual "+UtilityTestData.DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

		Double WeightValue          = PdfAdvanceReceived/PdfMaxGoldRate;
		BigDecimal CalcWeightValue  = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
		double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),0.10, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");
		Thread.sleep(5000);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Other OG Exchange
	// Automation ID : TC44
	// Author : Christy Reji
	// </summary>
	public void TC44_OldPurchaseExchangeOwnorOtherGoldorSilverSale() throws Exception
	{
		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Click on Transaction button
		Thread.sleep(5000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		// Step 3:Select customer and click on add to estimation button
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_CustomerID, OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_Option);

		//Step 4: Scan a SKU and click add to cart button
		//Expected : 4. Check calculation
		SKUResult SkuGoldSale = appUtils.GetOGSaleSku(
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_TranferType,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_FromCounterGold,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_MetalTypeGold,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_SaleQuantity,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_SKUCount
				);
		List<String> SkuList=SkuGoldSale.PcsRateSKU;

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		//taking the purity
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		List<String> ExpectedItemNames        = new ArrayList<>();
		List<String> TotalForEachItems        = new ArrayList<>();
		List<String> ItemQuantity             = new ArrayList<>();
		List<String> PurityOfItems            =new ArrayList();
		List<Double> AllRates                 = new ArrayList<>();

		for (int i = 0; i < SkuList.size(); i++)
		{
			String  CurrentSkuList = SkuList.get(i);
			int SkuCount = i + 1;
			appUtils.SKUIngridentTableValues(CurrentSkuList.toString(),SkuCount, ScannedSkuDataMap);
			ExpectedItemNames.add(ScannedSkuDataMap.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItems.add(ScannedSkuDataMap.get("SKU_" + (i+1) + "_Total"));
			ItemQuantity.add(ScannedSkuDataMap.get("SKU_" + (i+1) + "_GrossWeight"));
			PurityOfItems.add(ScannedSkuDataMap.get("SKU_" + (i+1) + "_Purity"));

			for (int ItemIndex = 1; ; ItemIndex++) 
			{
				String Rate = "SKU" + SkuCount + "_ITEM" + ItemIndex + "_RATE";
				if (ScannedSkuDataMap.containsKey(Rate)) 
				{
					try 
					{
						double SkuRate = Double.parseDouble(ScannedSkuDataMap.get(Rate));
						AllRates.add(SkuRate);
					}
					catch (NumberFormatException e) 
					{
						System.out.println("Rate not fetched");					
					}
				} 
				else{break; }
			}
			ScannedSkuDataMap.put("SKU_" + SkuCount + "_AllRates", AllRates.toString());
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCount);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuCount);
		}

		//TranscationPageVerifyLines
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal         = TransactionDataLine.get("Subtotal");
		String Discount         = TransactionDataLine.get("Discount");
		String TotalGrossWeight = TransactionDataLine.get("GrossWeight");
		String TotalNetWeight   = TransactionDataLine.get("NetWeight");
		String Tax              = TransactionDataLine.get("Tax");
		String TotalAmount      = TransactionDataLine.get("TotalAmount");
		String NetTotal2         = TransactionDataLine.get("NetTotal");
		String LinesCount       = TransactionDataLine.get("LinesCount");

		mathUtils.ValidateTransactionLineCalculation(TransactionDataLine, ScannedSkuDataMap);

		// Step 5: Search OG
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.OGProduct);
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");

		// Step 6: Click on OGT
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_OgtProduct));	

		// Step 7: Select configuration(purity 22K)		
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_Purity);

		// Step 8: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
		//Note:OGExchange
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));

		// Step 9: Select QC Person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_QCAndSmithPerson);

		// Step 10: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_QCAndSmithPerson); 

		//Step 11. Enter piece value
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_GrossPieces);

		//Step 12. Enter gross weight
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_GrossWeight);

		//Step:13 Click on XRF button
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("XRF"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("XRF"));

		//Step:14 Enter XRF values
		//Check XRF Calculation
		base.isExists(NormalSaleGoldandSilverObj.Ele_Payement("XRF purity"));
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_Name("xrfval1"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_XRFPurity);
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_Name("xrfval2"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_XRFPurity);
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_Name("xrfval3"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_XRFPurity);

		double XrfAvg = mathUtils.XRFCalculation(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_XRFPurity,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_XRFPurity, OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_XRFPurity);  
		System.out.println("Average XRF Value after calculation: " + XrfAvg);

		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));	
		String XrfAvgExpected = base.GetAttribte(NormalSaleGoldandSilverObj.Ele_Name("ogpavgpurity"), "value");
		System.out.println("Average XRF value in exchange page :"+XrfAvgExpected);

		asrt.assertEquals(XrfAvg, Double.parseDouble(XrfAvgExpected),"The actual average value after calculation : "+XrfAvg+"is not equal to average value : "+XrfAvgExpected+" in OGT Exchange page.");

		//Step 15:click on calculate button
		// Check Exchange rate(Board rate) ,Check calculation, Note:Billing Screen, No of Product lines,Displayed Item Name,Displayed Quantity
		//Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

		//Check Exchange rate(Board rate)
		Thread.sleep(3000);
		double ActualOgtRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		System.out.println("OGT Rate : "+ActualOgtRateInItemDetailsPge);
		String Purity              = PurityOfItems.get(0);
		double ExchangeRate        = (AllRates.get(0));
		long RoundedExpectedOgtRate =0;
		if (Purity.equalsIgnoreCase("18k")) 
		{
			double ExpectedOgRate = (ExchangeRate * 22) / 18;

		}  	
		RoundedExpectedOgtRate                     = Math.round(ExchangeRate);
		double XrfPurity                          = XrfAvg * 1000;  // Converts 0.916 to 916
		int RoundedExpectedOgRateDecimal          = (int) (RoundedExpectedOgtRate / 916.0);
		double CalculationPurity                  = RoundedExpectedOgRateDecimal * XrfPurity;	
		double ExchangeGoldRate                   = ActualOgtRateInItemDetailsPge * 0.98;	
		double OgtExchangeRate                    = ActualOgtRateInItemDetailsPge*0.02;
		double ExpectedExchangePurityXrfBoardRate = CalculationPurity - OgtExchangeRate;	
		double CurrentRate                        = ExchangeGoldRate + OgtExchangeRate;

		asrt.assertEquals(CurrentRate, Math.round(ActualOgtRateInItemDetailsPge),1,"Exchange rate (Board rate) mismatch: Expected exchange rate is " +CurrentRate + " but got " + Math.round(ActualOgtRateInItemDetailsPge) +" in OG item details page");

		//Check calculation
		double NetWeightInItemDetailsPge              = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt")));
		double ExpectedAmountInItemDetailsPge         = Math.round(NetWeightInItemDetailsPge * ActualOgtRateInItemDetailsPge * 100.0) / 100.0;	
		double ActualAmountInItemDetailsPge           = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));			
		asrt.assertEquals(ExpectedAmountInItemDetailsPge, ActualAmountInItemDetailsPge, 0.1,"Amount mismatch: Expected amount is " + ExpectedAmountInItemDetailsPge + " but got " + ActualAmountInItemDetailsPge + " in OG item details page");


		String ExpectedGrossWeightInItemDetailsPge    = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		String OgtTotalAmountInItemDetailsPge         = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));	
		String OgtItemName                            = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInOgtItemDetailsPge    = OgtItemName.substring(OgtItemName.indexOf("-") + 2);

		//Step 16: Click on Add to Cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));

		// OGT Item Validation in Billing Page
		List<WebElement> ItemNames  = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemQtys   = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<WebElement> ItemTotals = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));

		boolean OgtItemFound        = false;
		double OgtQty               = 0.0, ogtTotal = 0.0;
		double LastNonOgtItemTotal  = 0.0;
		int TotalLinesBillingPage   = ItemNames.size();

		// Validate OGT Item Name, Quantity, and Total (Without Tax)
		for (int i = 0; i < ItemNames.size(); i++) {
			String ItemName        = ItemNames.get(i).getText().trim();
			String QtyStr          = ItemQtys.get(i).getText().trim().replaceAll("[^\\d.]", "");
			String TotalStr        = ItemTotals.get(i).getText().trim().replaceAll("[^\\d.]", "");
			double Qty             = QtyStr.isEmpty() ? 0.0 : Double.parseDouble(QtyStr);
			double ItemTotal       = TotalStr.isEmpty() ? 0.0 : Double.parseDouble(TotalStr);

			if (ItemName.equalsIgnoreCase(ExpectedItemNameInOgtItemDetailsPge)) {
				OgtItemFound       = true;
				OgtQty             = Qty;
				ogtTotal           = ItemTotal;

				System.out.println("OGT total transaction page: " + ogtTotal);
				Assert.assertEquals(ItemName, ExpectedItemNameInOgtItemDetailsPge, "OGT Item Name mismatch from OGT Exchange page :"+ItemName+"but found"+ExpectedItemNameInOgtItemDetailsPge+"in Billing page");
				asrt.assertEquals(OgtQty, NetWeightInItemDetailsPge, "OGT Item quantity mismatch from OGT Exchange page :"+OgtQty+"but found"+NetWeightInItemDetailsPge+"in Billing page");
				asrt.assertEquals(ogtTotal, Double.parseDouble(OgtTotalAmountInItemDetailsPge), 1,"OGT total without tax mismatch from OGT Exchange page: " + ogtTotal + " but found " + Double.parseDouble(OgtTotalAmountInItemDetailsPge) + " in Billing page");
			} else {
				LastNonOgtItemTotal = ItemTotal;  // capture last non-OGT item total
			}
		}

		if (!OgtItemFound) {
			System.out.println("OGT item not found in the Billing Page.");
		}

		//  Subtotal = OGT total - last non-OGT item total
		double CalculatedSubTotal =   LastNonOgtItemTotal-ogtTotal;
		System.out.println("Calculated Subtotal (OGT - Last Non-OGT Item): " + CalculatedSubTotal);

		// Take absolute value to remove negative sign
		CalculatedSubTotal = Math.abs(CalculatedSubTotal);

		// Round to 2 decimal places
		CalculatedSubTotal = Math.round(CalculatedSubTotal * 100.0) / 100.0;

		double ExpectedSubTotal = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replaceAll("[^\\d.]", ""));
		ExpectedSubTotal = Math.round(ExpectedSubTotal * 100.0) / 100.0;
		asrt.assertEquals(CalculatedSubTotal, ExpectedSubTotal, "Subtotal mismatch");

		// Tax Validation (3% of taxable amount)
		double ExpectedTax = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replaceAll("[^\\d.]", ""));
		double CalculatedTax = Math.round(LastNonOgtItemTotal * 3) / 100.0;
		asrt.assertEquals(ExpectedTax, CalculatedTax, 1, "The tax value mismatch from BillingPage UI Expected: "+ExpectedTax+ "but fount :" +CalculatedTax+" after calculation");

		// Total Amount Validation
		double ExpectedTotal   = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replaceAll("[^\\d.]", ""));
		double CalculatedTotal = ExpectedSubTotal + ExpectedTax;
		asrt.assertEquals(ExpectedTotal, CalculatedTotal, 1, "The total amount mismatch from BillingPage UI Expected: "+ExpectedTotal+ "but fount :" +CalculatedTotal+" after calculation");


		//Step  17. Save to estimate
		//Expected : Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		String TaxableValue        = Double.toString(LastNonOgtItemTotal);
		String TotalAmnt           = SubTotal;
		String Gst                 =  Double.toString(ExpectedTax);
		String InvoiceTotal        =  Double.toString(CalculatedTotal);
		String NetTotal            =  Double.toString(CalculatedTotal);
		String ProformaPdfName     = pdfUtils.DownloadAndRenameLatestPDF("TC44_ProformaInvoice");
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+ProformaPdfName+"";
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				TotalAmnt
				);

		//Step 18. Recall estimate from cashier
		//Expected : Verify whether the recalled item is the same as the item in the cart. Note:Cashier Screen after recall
		// *No of Product lines,*Displayed Item Name, *Displayed Quantity *Displayed TOTAL (without Tax)*Displayed Subtotal *TAX*Total Amount
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Validation
		appUtils.ValidateRecallPageOGItem(ExpectedItemNames,ItemQuantity,ExpectedItemNameInOgtItemDetailsPge,ExpectedGrossWeightInItemDetailsPge
				,ExpectedSubTotal,ExpectedTax,ExpectedTotal,TotalLinesBillingPage,"OGT");

		//Step 19: Select transaction type as Exchange
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_TransactionTypeOGT);

		//Step 20.Click on Process Estimation
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		//Step 21.Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Step 22:  Click on amount 0.00	
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) 
		{
			try 
			{ 
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} 
			catch (Exception e) {}
		} 

		//Step 23 :Post the exchange invoice
		//Expected :  Check Exchange invoice details
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		//validation
		Map<String, String> PurchaseBillData = pdfUtils.OGPdfValidation("Adjustment");
		String ExchngeAdjustment             = String.format("%.2f", Double.parseDouble(PurchaseBillData.get("PaymentDetails").trim()));
		String GrossWeightFromInvoice        = PurchaseBillData.get("GrossWt1").split("\\.")[0];
		String NetWeightFromInvoice          = PurchaseBillData.get("NetWt1").split("\\.")[0];;
		String GoldRateFromInvoice           = String.format("%.1f", Double.parseDouble(PurchaseBillData.get("Rate1").trim()));
		String TotalAmountFromInvoice        = String.format("%.1f", Double.parseDouble(PurchaseBillData.get("TotalAmount1").trim()));
		String PaymentDetailsFromInvoice     = String.format("%.1f", Double.parseDouble(PurchaseBillData.get("PaymentDetails").trim()));
		String OgtGrossWt                    = OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_GrossWeight;	

		asrt.assertEquals(Double.parseDouble(ExchngeAdjustment), ogtTotal, 0.1, "Mismatch in value OGPdf adjustment:"+Double.parseDouble(ExchngeAdjustment)+"but got" +ogtTotal+"rom billing page");
		asrt.assertEquals(GrossWeightFromInvoice, OgtGrossWt,"Mismatch in value OGPdf adjustment:"+GrossWeightFromInvoice+"but got" +OgtGrossWt+"rom billing page");
		asrt.assertEquals(NetWeightFromInvoice, OgtGrossWt,"Mismatch in value OGPdf adjustment:"+NetWeightFromInvoice+"but got" +OgtGrossWt+"rom billing page");	
		asrt.assertEquals(Double.parseDouble(GoldRateFromInvoice), ActualOgtRateInItemDetailsPge,0.1,"Mismatch in value OGPdf adjustment:"+Double.parseDouble(ExchngeAdjustment)+"but got" +ActualOgtRateInItemDetailsPge+"rom billing page");
		asrt.assertEquals(Double.parseDouble(TotalAmountFromInvoice), ogtTotal,0.1,"Mismatch in value OGPdf adjustment:"+Double.parseDouble(TotalAmountFromInvoice)+"but got" +ogtTotal+"rom billing page");
		asrt.assertEquals(Double.parseDouble(PaymentDetailsFromInvoice), ogtTotal,0.1,"Mismatch in value OGPdf adjustment:"+Double.parseDouble(PaymentDetailsFromInvoice)+"but got" +ogtTotal+"rom billing page");

		//Step 24. Again recall estimate from cashier screen
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		//Step 25. Select transaction type as sale
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));	
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC44_TransactionTypeSales);

		//Step 26: Click on process estimation button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 27:Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		// Step 28:Click on the Amount
		// Step 29:Select a Payment method(Cash or Card)
		String PaymentAmount = appUtils.PaymentAfterRecallEstimate("HDFC");
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		//Step 30 : Post the invoice
		//Check the final ivoice details
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice    = pdfUtils.DownloadAndRenameLatestPDF("TC44_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";

		String GrossAmount     = SubTotal;
		String TotalDiscount   = Discount;
		String TotalQtyPcs     = String.valueOf(TotalLinesBillingPage);
		String Adjustment      = ExchngeAdjustment;
		String PaymentHDFCCard = PaymentAmount;
		String SalesPdfPath    = SaleInvoicePath;
		TotalNetWeight         =  TotalGrossWeight;
		TotalGrossWeight       = TotalGrossWeight;
		String NetValue        = NetTotal2;
		String PdfInvoiceNo    = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				"",
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
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
	// Test Case Title : Multiple old(OG and OS) with multiple SKU
	// Automation ID : TC49
	// Author: Neethu
	// </summary>

	public void TC49_OldPurchaseExchangeOwnorOtherGoldorSilverSale() throws Exception {
		//Step 1: Login to POS	
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Click on Transaction page
		Thread.sleep(4000);
		appUtils.HamBurgerButtonClick("iconShop");
		Thread.sleep(2000);

		//Step 3:Select customer and click on add to estimation button
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_CustomerId, "AddCustomerForSalesEstimationCommand");

		//Step 4: Scan a SKU(Multiple SKU Gold & Uncut) and click add to cart button
		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> ExpectedItemNames = new ArrayList<>();
		List<String> TotalForEachItems = new ArrayList<>();
		List<String> ItemNamesFromBillingPge =new ArrayList<>();
		List<String> ItemQuantity=new ArrayList<>();
		int i=0;
		double ActualSubTotal = 0.0;
		double TotalWithoutTax = 0.0;

		List<String> SkuListGold = appUtils.SelectMultipleSKUs(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_SkuGoldCount,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_TransferType,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_GoldFromCounter,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_MetalType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
		for (i = 0; i < SkuListGold.size(); i++) 
		{    		
			appUtils.SKUIngridentTableValues(SkuListGold.get(i), i + 1, SkuDetails);
			ExpectedItemNames.add(SkuDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItems.add(SkuDetails.get("SKU_" + (i+1) + "_Total"));
			ItemQuantity.add(SkuDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		List<String> SkuListsUncut = appUtils.SelectMultipleSKUs(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_SkuUncutCount,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_TransferType,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_UncutFromCounter,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_MetalType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		for (i = 0; i < SkuListsUncut.size(); i++) 
		{    		
			int Index = SkuListGold.size() + i + 1;
			appUtils.SKUIngridentTableValues(SkuListsUncut.get(i), Index, SkuDetails);
			ExpectedItemNames.add(SkuDetails.get("SKU_" + Index  + "_HeaderName"));
			TotalForEachItems.add(SkuDetails.get("SKU_" + Index + "_Total"));
			ItemQuantity.add(SkuDetails.get("SKU_" + Index + "_GrossWeight"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		List<String> AllSkuList = new ArrayList<>();
		AllSkuList.addAll(SkuListGold);
		AllSkuList.addAll(SkuListsUncut);

		Map<String, String> Results = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);		
		String SubTotal = Results.get("Subtotal");
		String Discount = Results.get("Discount");
		String TotalGrossWeight = Results.get("GrossWeight");
		String TotalNetWeight = Results.get("NetWeight");
		String Tax = Results.get("Tax");
		String TotalAmount = Results.get("TotalAmount");
		String NetTotal = Results.get("NetTotal");
		String LinesCount = Results.get("LinesCount");

		//Displayed Item Name
		List<WebElement> ItemNamesInBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		for (int n=0; n<ItemNamesInBilling.size();n++) 
		{
			ItemNamesFromBillingPge.add(ItemNamesInBilling.get(n).getText().trim());
		}
		i=0;
		for (String ItemName : ExpectedItemNames) 
		{
			String ExpectedItemName = ItemName.substring(ItemName.indexOf("-") + 2);
			String ItemNameFromBilling = ItemNamesInBilling.get(i).getText().trim();  

			asrt.assertEquals(ExpectedItemName, ItemNameFromBilling,"Item Name mismatch : Expected  is " + ExpectedItemName + " but got " + ItemNameFromBilling + " in Billing Page");
			i++; 
		}

		//No of Product lines
		int ExpectedLinesCount = Integer.parseInt(LinesCount);
		int ActualProductCount = ItemNamesInBilling.size();    
		asrt.assertEquals(ExpectedLinesCount, ActualProductCount, "Number of Product Lines mismatch : Expected value is "+ExpectedLinesCount+" but got "+ActualProductCount+" in Billing page");

		//Displayed Quantity
		List<WebElement> BillingScreenItemQty = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		for (i = 0; i < BillingScreenItemQty.size(); i++)
		{
			String DisplayedItemQty = BillingScreenItemQty.get(i).getText().trim().replaceAll("[^\\d.]", "");
			double DisplayedQty = Double.parseDouble(DisplayedItemQty);
			String ExpectedItemQty = ItemQuantity.get(i).trim().replaceAll("[^\\d.]", "");			
			double ExpectedQty = Double.parseDouble(ExpectedItemQty);

			asrt.assertEquals(DisplayedQty,ExpectedQty,"Quantity mismatch : Expected value is " + ExpectedQty + " but got " + DisplayedQty+ " in Billing page");
		}

		//Displayed TOTAL (without Tax)
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		for (int j =0; j<TotalsWithoutTax.size();j++) 
		{
			double ActualTotal = Double.parseDouble(TotalForEachItems.get(j));
			double ExpectedTotal = Double.parseDouble(TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpectedTotal, ActualTotal,"Total(Without Tax) mismatch : Expected value is "+ExpectedTotal+"but got "+ActualTotal+ " in Billing screen");
		}

		//Displayed Subtotal
		for (WebElement SubTotalElement : TotalsWithoutTax)
		{
			String SubTotalValue = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!SubTotalValue.isEmpty())
			{
				ActualSubTotal=ActualSubTotal + Double.parseDouble(SubTotalValue);
			}
		}	
		TotalWithoutTax = Math.round(ActualSubTotal * 100.0) / 100.0;
		double ExpectedSubTotal = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(ExpectedSubTotal, TotalWithoutTax, "SubTotal mismatch : Expected value is "+ExpectedSubTotal+" but got "+TotalWithoutTax+" in Billing page");

		//Tax amount Validation
		double TaxAmount=ActualSubTotal*3/100;
		double ExpectedTax = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));	
		asrt.assertEquals(ExpectedTax,TaxAmount ,1, "Tax Amount mismatch : Expected value is "+ExpectedTax+"but got "+TaxAmount+" in Billing page");

		//Total Amount
		double ActualTotalAmount=ActualSubTotal+TaxAmount;
		double ExpectedTotalAmount = Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(ExpectedTotalAmount, ActualTotalAmount, 1,"Total Amount mismatch : Expected value is "+ExpectedTotalAmount+" but got "+ActualTotalAmount+" in Billing page");

		//Step 5: Search OG
		//Step 6:Click on OG own
		//Step 7: Select configuration(purity 22K)
		//Step 8: Click on Add item button
		//Choose Exchange
		//Step 9:Select QC person
		//Step 10:Select Smith person
		//Step 11:Enter piece value
		//Step 12:Enter gross weight
		//Step 13:click on calculate button
		//Expected Result:  Check Exchange rate(Board rate ) 
		appUtils.PurchaseOldGold(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_Configuration, 
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_PurchaseOrExchange,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_QCPerson,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_SmithPerson,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_GrossPieces,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_GrossWeight);

		Map<String, Double> OgDataMap = new LinkedHashMap<>();
		String OgQty = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column3 textRight", "h4 ellipsis cell"));
		String OgRate = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column4 textRight", "h4 ellipsis cell"));
		String OgCvalue = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column5 textRight", "h4 ellipsis cell"));

		Double OgQtyIngredientTable =  Double.parseDouble(OgQty);
		Double OgRateIngredientTable = Double.parseDouble(OgRate);
		Double OgCValueIngredientTable = Double.parseDouble(OgCvalue);

		
		Double OgCalculatedCvalue = OgQtyIngredientTable * OgRateIngredientTable;
		System.out.println("The OG caluculated value: "+OgCalculatedCvalue);

		OgDataMap.put("OgQty", OgQtyIngredientTable);
		OgDataMap.put("OgCvalue",OgCValueIngredientTable);

		asrt.assertEquals(OgCalculatedCvalue, OgCValueIngredientTable,1, "Mismatch in Cvalue,  Expected " + OgCalculatedCvalue+" but got "+OgCValueIngredientTable+" in Old Gold Item details page");
		Double OgNetRateValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		asrt.assertEquals(OgRateIngredientTable, OgNetRateValue,1,"Mismatch in net rate, Expected " + OgRateIngredientTable+" but got "+OgNetRateValue+" in Old Gold Item details page");
		Double OgNetAmountValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(OgCalculatedCvalue, OgNetAmountValue,1," Mismatch in Net amount, Expected " + OgCalculatedCvalue+" but got "+OgNetAmountValue+" in Old Gold Item details page");
		Double TotalAmountValue =Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt")));
		asrt.assertEquals(OgCalculatedCvalue, TotalAmountValue,1, "Mismatch in Total amount value , Expected " + OgCalculatedCvalue+" but got "+TotalAmountValue+" in Old Gold Item details page");
		Thread.sleep(1000);

		//Step 14: Click on Add to cart button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		Thread.sleep(1000);

		By CartChangedAlert = NormalSaleGoldandSilverObj.Ele_CustomerAdjustment("cart has changed");
		By OkBtn = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");

		if (base.isElementPresent(driver, CartChangedAlert)) {		
			try {
				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}

		// Step 15: Search OS
		// Step 16: Click on OS
		// Step 17: Select configuration(purity 925)
		// Step 18: Click on Add item button
		// Step 19: Select QC person
		// Step 20: Select Smith person
		// Step 21: Enter piece value
		// Step 22: Enter gross weight
		// Step 23: click on calculate button
		//Expected Result:  Check Exchange rate for Silver 
		appUtils.PurchaseOldSilver(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_SilverConfiguration, 
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_PurchaseOrExchange,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_QCPerson,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_SmithPerson,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_GrossPieces,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_GrossWeight);	

		Map<String, Double> OsDataMap = new LinkedHashMap<>();
		String OsQty = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column3 textRight", "h4 ellipsis cell"));
		String OsRate = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column4 textRight", "h4 ellipsis cell"));
		String OsCvalue = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column5 textRight", "h4 ellipsis cell"));

		Double OsQtyIngredientTable =  Double.parseDouble(OsQty);
		Double OsRateIngredientTable = Double.parseDouble(OsRate);
		Double OsCValueIngredientTable = Double.parseDouble(OsCvalue);

		Double OsCalculatedCvalue = OsQtyIngredientTable * OsRateIngredientTable;
		System.out.println("The OS caluculated value: "+OsCalculatedCvalue);
		OsDataMap.put("OsQty", OsQtyIngredientTable);
		OsDataMap.put("OsCvalue",OsCValueIngredientTable);

		asrt.assertEquals(OsCalculatedCvalue, OsCValueIngredientTable,1, "Mismatch in Cvalue,  Expected " + OsCalculatedCvalue+" but got "+OsCValueIngredientTable+" in Old Silver Item details page");
		Double OsNetRateValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		asrt.assertEquals(OsRateIngredientTable, OsNetRateValue,1,"Mismatch in net rate, Expected " + OsRateIngredientTable+" but got "+OsNetRateValue+" in Old Silver Item details page");
		Double OsNetAmountValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(OsCalculatedCvalue, OsNetAmountValue,1," Mismatch in Net amount, Expected " + OsCalculatedCvalue+" but got "+OsNetAmountValue+" in Old Silver Item details page");
		Double OSTotalAmountValue =Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt")));
		asrt.assertEquals(OsCalculatedCvalue, OSTotalAmountValue,1, "Mismatch in Total amount value , Expected " + OgCalculatedCvalue+" but got "+OSTotalAmountValue+" in Old Silver Item details page");
		Thread.sleep(1000);

		//Step 24: Click on Add to cart button
		// Expected result: Check calculation Note:Billling Screen , *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		
		WebDriverWait Wait1 = new WebDriverWait(driver, Duration.ofSeconds(8));
		Thread.sleep(1000);

		By CartChangedAlert1 = NormalSaleGoldandSilverObj.Ele_CustomerAdjustment("cart has changed");
		By OkBtn1 = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");

		if (base.isElementPresent(driver, CartChangedAlert1)) {		
			try {
				WebElement OkButton = Wait1.until(ExpectedConditions.elementToBeClickable(OkBtn1));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String>     TextOGItemNamesBilling =new ArrayList<>();
		List<String>     TextItemNamesBilling =new ArrayList<>();
		List<String>     TextItemQtyBilling =new ArrayList<>();
		List<String>     TextOSItemNamesBilling =new ArrayList<>();


		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);		
		String SubTotalAfterOG = BillingScrnTableData.get("Subtotal");
		String TotalGrossWeightAfterOG = BillingScrnTableData.get("GrossWeight");
		String TotalNetWeightAfterOG = BillingScrnTableData.get("NetWeight");
		String TaxAfterOG = BillingScrnTableData.get("Tax");
		String TotalAmountAfterOG = BillingScrnTableData.get("TotalAmount");
		String NetTotalAfterOG = BillingScrnTableData.get("NetTotal");
		String LinesCountAfterOG = BillingScrnTableData.get("LinesCount");

		double TotalAmountAfterOGBillingScrn = Double.parseDouble(TotalAmountAfterOG.trim().replaceAll("[^\\d.]", ""));
		String PdfTotalAmountAfterOG = String.valueOf(TotalAmountAfterOGBillingScrn);
		double TaxAfterOGBillingScrn = Double.parseDouble(TaxAfterOG.trim().replaceAll("[^\\d.]", ""));
		String PdfTaxAfterOG = String.valueOf(TaxAfterOGBillingScrn);
		double SubTotalAfterOGBillingScrn = Double.parseDouble(SubTotalAfterOG.trim().replaceAll("[^\\d.]", ""));
		String PdfSubTotalAfterOG = String.valueOf(SubTotalAfterOGBillingScrn);
		double NetTotalAfterOGBillingScrn = Double.parseDouble(NetTotalAfterOG.trim().replaceAll("[^\\d.]", ""));
		String PdfNetTotalAfterOG = String.valueOf(NetTotalAfterOGBillingScrn);
		double TotalGrossWeightAfterOGBillingScrn = Double.parseDouble(TotalGrossWeightAfterOG.trim().replaceAll("[^\\d.]", ""));
		String PdfTotalGrossWeightAfterOG = String.valueOf(TotalGrossWeightAfterOGBillingScrn);

		int LinesCountBilling = Integer.parseInt(LinesCountAfterOG);
		double SubTotalBilling = Double.parseDouble(SubTotalAfterOG.trim().replaceAll("[^\\d.\\-]", ""));
		double TaxBilling = Double.parseDouble(TaxAfterOG.trim().replaceAll("[^\\d.\\-]", ""));
		double TotalAmountBilling = Double.parseDouble(TotalAmountAfterOG.trim().replaceAll("[^\\d.\\-]", ""));

		int ExpectedLinesCountInBillingPge = AllProductRows.size();	
		asrt.assertEquals(ExpectedLinesCountInBillingPge,LinesCountBilling, "Mismatch in Lines count,expected "+ExpectedLinesCountInBillingPge+" but got "+LinesCountBilling+" in the billing screen");

		String FristSkuItemRate = ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.\\-]", "");
		String SecondSkuItemRate = ItemRateBilling.get(1).getText().trim().replaceAll("[^\\d.\\-]", "");

		String PdfFristSkuItemRate = 	ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.-]", "");
		String PdfSecondSkuItemRate = 	ItemRateBilling.get(1).getText().trim().replaceAll("[^\\d.-]", "");

		double FirstItemRate = Double.parseDouble(FristSkuItemRate);
		double SecondItemRate = Double.parseDouble(SecondSkuItemRate);
		double SumSkuItemRate = FirstItemRate+SecondItemRate;
		String PdfSumOfSkuItemRate =String.format("%.2f", SumSkuItemRate);
		System.out.println("Sum of two SKU: "+PdfSumOfSkuItemRate);	
		String PdfSumSkuItemRate = 	String.valueOf(PdfSumOfSkuItemRate);

		double ExpectedSubTotalBillingScrn = 0.0;
		ExpectedSubTotalBillingScrn = SumSkuItemRate-(OgCalculatedCvalue+OsCalculatedCvalue);
		System.out.println("Expected Subtotal : "+ExpectedSubTotalBillingScrn);	

		//tax cal
		double ExpectedTaxAmnt = SumSkuItemRate * 3 / 100;  //-653.08
		String StrEpectedTaxAmnt = String.valueOf(ExpectedTaxAmnt);
		double ExpectedTaxAmount = Double.parseDouble(StrEpectedTaxAmnt.trim().replaceAll("[^\\d.\\-]", ""));

		//total amount
		double ActualTotalAmountInBilling = ExpectedSubTotalBillingScrn + ExpectedTaxAmount;
		asrt.assertEquals(ExpectedTaxAmount,TaxBilling,1.0,"Mismatch in Tax value, expected "+ExpectedTaxAmount+" but got "+TaxBilling+" in the billing screen");


		for (int n=0; n<ItemNamesBilling.size();n++)
		{
			String ItemName=ItemNamesBilling.get(n).getText().trim();
			if(ItemName.contains("OG"))
			{TextOGItemNamesBilling.add(ItemName);}
			if(ItemName.contains("OS"))
			{TextOSItemNamesBilling.add(ItemName);}
			else {TextItemNamesBilling.add(ItemName);}
		}
		Thread.sleep(3000);

		//ITems Sales 
		for(int ItemName=0;ItemName<TextItemNamesBilling.size();ItemName++)
		{
			String ExpectedFull =TextItemNamesBilling.get(ItemName);
			//String ExpectedTrimmed = ExpectedFull.contains("-") ?ExpectedFull.substring(ExpectedFull.indexOf("-") + 2) : ExpectedFull;
			String ActualItem = ItemNamesBilling.get(ItemName).getText().trim();
			asrt.assertEquals(ExpectedFull,ActualItem, "Mismatch in item name, expected "+ExpectedFull+"but got "+ActualItem+"  in the billing screen.");
		}


		for (int j=0; i<TextOGItemNamesBilling.size();i++)
		{
			String ActualItemOG = TextOGItemNamesBilling.get(j).trim();
			asrt.assertEquals(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_OgItemName, ActualItemOG,"Mismatch in OG item name, expected "+OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_OgItemName+" but got "+ActualItemOG+" in the billing screen.");

		}	

		for (int k = 0; k <ItemNamesBilling.size(); k++)
		{
			String ItemName=ItemNamesBilling.get(k).getText().trim();
			if(ItemName.contains("OG") || ItemName.contains("OS") )
				break;    
			else {	
				String ItemRate= ItemRateBilling.get(k).getText();
				double ExpectedTotal = Double.parseDouble(ItemRate.replaceAll("[^\\d.]", ""));
				String ActualTotalBilling = ItemRateBilling.get(k).getText().trim();
				double ActualTotal = Double.parseDouble(ActualTotalBilling.replaceAll("[^\\d.]", ""));
				asrt.assertEquals(ExpectedTotal, ActualTotal,1.0,"Mismatch in total amounts (without tax),expected "+ExpectedTotal+" but got "+ActualTotal+" in the Billing screen.");		}
		}


		String FirstItemRateBilling= ItemRateBilling.get(0).getText().replace("-", "").trim();
		String SecondItemRateBilling= ItemRateBilling.get(1).getText().replace("-", "").trim();

		String OGItemRateBilling= ItemRateBilling.get(2).getText().replace("-", "").trim();
		String OSItemRateBilling= ItemRateBilling.get(3).getText().replace("-", "").trim();

		Double ActualRateOG22k = Double.parseDouble(OGItemRateBilling.replace("\u20B9", "").replace(",", "").trim());
		Double ActualRateOS22k = Double.parseDouble(OSItemRateBilling.replace("\u20B9", "").replace(",", "").trim());
		asrt.assertEquals(OgCalculatedCvalue, ActualRateOG22k,1.0, "Mismatch in total amount for 22K OG, expected "+OgCalculatedCvalue+" but got "+ActualRateOG22k+" in Billing screen");
		asrt.assertEquals(OsCalculatedCvalue, ActualRateOS22k,1.0, "Mismatch in total amount for 22K OG, expected "+OsCalculatedCvalue+" but got "+ActualRateOS22k+" in Billing screen");
		asrt.assertEquals(ExpectedSubTotalBillingScrn,SubTotalBilling,1.0,"Mismatch in Subtotal value, expected "+ExpectedSubTotalBillingScrn+" but got "+SubTotalBilling+"in the Billing screen");	
		asrt.assertEquals(TotalAmountBilling,ActualTotalAmountInBilling,1.0,"Mismatch in Total amount value, expected "+TotalAmountBilling+" but got "+ActualTotalAmountInBilling+" in the Billing screen");

		//Step 25: Click on Save to estimation button
		//Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(6000);	
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC49_ProformaInvoice");
		Thread.sleep(6000);	
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FirstInvoice+"";
		String TaxableValue = PdfSumSkuItemRate;
		String InvoiceTotalAmount = PdfTotalAmountAfterOG;
		String GST = PdfTaxAfterOG;
		String TotalAmnt =PdfSubTotalAfterOG;
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProformaInvoicePath,
				TaxableValue,
				GST,
				InvoiceTotalAmount,
				PdfNetTotalAfterOG,
				TotalAmnt
				);

		//Step 26:Recall estimate from cashier
		//Expected Result:Verify whether the recalled item is the same as the item in the cart.
		//Cashier Screen after recall *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));		

		//Validation Steps : Verify whether the recalled item is the same as the item in the cart.
		String EstmnNumberRecallPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstmnNumberRecallPge,EstmnNumber,"Mismatch in Estimation No:, expected "+EstmnNumberRecallPge+" but got "+EstmnNumber+"in the Recall Page");	

		//Step 27: Select transaction type as Exchange
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_TransactionTypeExchnge);

		// Step 28: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 
		base.setZoom(driver, 60);
		Thread.sleep(3000);

		// Step 29: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		Map<String, String> BillingScrnExcTableData = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);
		String ExchngeAdjustment 					= BillingScrnExcTableData.get("Payments");
		String SubTotalAfterOU 					    = BillingScrnExcTableData.get("Subtotal");
		String TotalGrossWeightAfterOGOS		 	    = BillingScrnExcTableData.get("GrossWeight");
		String TaxAfterOu						    = BillingScrnExcTableData.get("Tax");
		String TotalAmountAfterOGOS 				    = BillingScrnExcTableData.get("TotalAmount");
		String NetTotalAfterOu 					    = BillingScrnExcTableData.get("NetTotal");
		String LinesCountAfterOu 				    = BillingScrnExcTableData.get("LinesCount");

		
		// Step 30: Click on amount 0.00
		
		String AmountDueOg = base.GetText(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));

		AmountDueOg = AmountDueOg.replaceAll("[^\\d.-]", "").trim();
		double NumericAmountOg = Double.parseDouble(AmountDueOg);	
		double OgTotalAmountInBillingPge =Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replaceAll("[^\\d.]", "").trim());
		String OgTotalAmountValueInBillingPge = String.valueOf(OgTotalAmountInBillingPge);
		System.out.println("OgTotalAmountInBillingPge : "+OgTotalAmountInBillingPge);
		List<String> OgQuantity = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
		
	String ExpectedAmountForExchangeInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replaceAll("[^\\d.]", "").trim();

		double TotalGrossWtOg = 0.00;
		for(int j =0; j < OgQuantity.size() ;j++) {
			String OgQuantityInBillingPge = OgQuantity.get(j).replaceAll("[^\\d.]", "").trim();
			double ActualQtyInBillingPge = Double.parseDouble(OgQuantityInBillingPge);
			TotalGrossWtOg += ActualQtyInBillingPge;
		}
		
		String PaymentDetails = "";
		String PaymentOgFromInvoice = "";
		
		
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));

		// Step 31:  Post exchange invoice
		// Expected Result: Check Exchange invoice details		
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		PaymentDetails = OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_PaymentDetails;
		
		Map<String, String>PurchaseBillData = pdfUtils.PurchaseBillPdfValidation
				(PaymentDetails,
						OgTotalAmountInBillingPge, 
						TotalGrossWtOg,
						OgTotalAmountInBillingPge, 
						TotalGrossWtOg);
	
		//Step 32: Again recall estimate from cashier screen
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		Thread.sleep(3000);
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Step 33: Select the Transaction Type as Sales        
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_TransactionTypeSales);

		//Validation Steps : Verify whether the recalled item is the same as the item in the cart.
		String EstmnNumberRecallPgeSales = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstmnNumberRecallPgeSales,EstmnNumber,"Mismatch in Estimation No:, expected "+EstmnNumberRecallPge+" but got "+EstmnNumber+"in the Recall page");	

		// Step 34: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		// Step 35: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));		

		String DiscountBilling = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC= base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		double PaymentAmountHDFCTrimmedValue= Double.parseDouble(PaymentAmountHDFC.trim().replaceAll("[^\\d.]", ""));
		String PdfPaymentAmountHDFC = String.valueOf(PaymentAmountHDFCTrimmedValue);

		Map<String, String> SkuBillingScrnTableData = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);
		String SkuNetTotal = SkuBillingScrnTableData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTableData.get("LinesCount");
		String TotalNetWeightRecall 	 =   SkuBillingScrnTableData.get("NetWeight");

		double SkuNetTotalTrimmedValue= Double.parseDouble(SkuNetTotal.trim().replaceAll("[^\\d.]", ""));
		String PdfSkuNetTotal = String.valueOf(SkuNetTotalTrimmedValue);

		//Step 36: Click on amount
		//Step 37: Select payment method(cash/card)
		appUtils.PaymentAfterRecallEstimate(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC49_PaymentMethod);

		//Step 38: Post exchange invoice(sale)
		//Expected Result: Check final invoice details,* save receipt id for future reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC49_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";
		String GrossAmount = PdfSumSkuItemRate;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String Adjustment = OgTotalAmountValueInBillingPge;
		String PaymentHDFCCard = PdfPaymentAmountHDFC;
		String TotalDiamond ="";
		String SalesPdfPath = SaleInvoicePath;
		TotalGrossWeight= TotalGrossWeight;
		String NetValue = PdfSkuNetTotal;
		String TotalNetWt = TotalNetWeightRecall;	
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWt,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				AllSkuList,
				SkuDetails);

		System.out.println("PDF INVOICE NO:-"+PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}


	// <summary>
	// Test Case Title : Other OG purchase
	// Automation ID : TC43
	// Author: Vishnu Manoj K
	// </summary>
	public void TC43_OldPurchaseExchangeOwnorOtherGoldorSilverSale() throws Exception {

		//Step 1: Login to POS	
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		WebDriverWait HamBurgerWait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			if (HamBurgerWait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"))) != null)
			{
				Thread.sleep(3000);
				base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
			}
		} catch (Exception e) {
			Thread.sleep(3000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc(
					"height48 centerY","Show or hide navigation bar"));
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
		}

		// Step : Pre-Requisite for taking BoardRate
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.OGProduct);
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));		
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_Purity);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_QcAndSmithPerson);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_QcAndSmithPerson); 
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_GrossPieces);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_GrossWeight);
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(1000);
		double BoardRate = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		System.out.println("BoardRate22k"+BoardRate);

		// Step 2: Navigate to the Transaction page
		try {
			if (HamBurgerWait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"))) != null)
			{
				Thread.sleep(3000);
				base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
			}
		} catch (Exception e) {
			Thread.sleep(3000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc(
					"height48 centerY","Show or hide navigation bar"));
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
		}
		// Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4: Select OGT
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.OGTProduct);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");

		// Step 5: Click on OG other
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldGoldThirdPartyProduct));	

		// Step 6: Select configuration(purity 22K)		
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_Purity);

		// Step 7: Click on Add item button
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		// Step 8: Click on purchase button
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));

		// Step 9: Select QC Person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_QcAndSmithPerson);

		//Step 10: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_QcAndSmithPerson); 

		//Step 11: Enter piece value
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_GrossPieces);

		//Step 12: Enter gross weight
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_GrossWeight);

		//Step 13: Click on XRF button
		//Expected Result: Check XRF Calculation
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("XRF"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("XRF"));
		base.isExists(NormalSaleGoldandSilverObj.Ele_Payement("XRF purity"));
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_Name("xrfval1"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_XRFPurity);
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_Name("xrfval2"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_XRFPurity1);
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_Name("xrfval3"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_XRFPurity2);

		double XrfAvg = mathUtils.XRFCalculation(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_XRFPurity,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_XRFPurity1, OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_XRFPurity2);  
		System.out.println("Average XRF Value after calculation: " + XrfAvg);

		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));	
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		String XrfAvgExpected = base.GetAttribte(NormalSaleGoldandSilverObj.Ele_Name("ogpavgpurity"), "value");
		System.out.println("Average XRF value in purchase page :"+XrfAvgExpected);
		XrfAvg = new BigDecimal(XrfAvg)
				.setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		asrt.assertEquals(XrfAvg, Double.parseDouble(XrfAvgExpected),"The actual average value after calculation : "+XrfAvg+"is not equal to average value : "+XrfAvgExpected+" in OGT Exchange page.");

		//Step 15:click on calculate button
		//Expected: Check Purchase rate(Board rate) and Check calculation
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(5000);
		double BoardRate24K=(BoardRate * 24)/ 22 ;
		double FloorValue = Math.floor(BoardRate24K);
		double DecimalPart = BoardRate24K - FloorValue;
		if (DecimalPart > 0.5) {
			BoardRate24K = Math.ceil(BoardRate24K);        // Round up
		} else if (DecimalPart == 0.5) {
			BoardRate24K = FloorValue + 0.5;               // Keep 0.5
		} else {
			BoardRate24K = Math.floor(BoardRate24K);       // Round down
		}
		System.out.println("System.out.println(BoardRate24K);"+BoardRate24K);
		double Rate97Percentage=new BigDecimal((BoardRate24K * 97) / 100).setScale(2, RoundingMode.DOWN).doubleValue();
		System.out.println("Rate97Percentage "+Rate97Percentage);
		double ActualOGPurchaseRateInItemDetailsPge=new BigDecimal((Rate97Percentage * (XrfAvg * 100)) / 100).setScale(2, RoundingMode.DOWN).doubleValue();
		base.setZoom(driver, 50);
		Thread.sleep(2000);
		double ExpectedOGPurchaseRateInItemDetailsPge = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_OGRate("column4 textRight","gridcell")));		

		asrt.assertEquals(ExpectedOGPurchaseRateInItemDetailsPge, ActualOGPurchaseRateInItemDetailsPge,1,"OGT Purchase Rate mismatch: Expected value is "+ExpectedOGPurchaseRateInItemDetailsPge+" but got "+ActualOGPurchaseRateInItemDetailsPge+" in item details page");

		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
		double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
		double ExpectedAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ExpectedOGPurchaseRateInItemDetailsPge * 100.0) / 100.0;
		double ActualAmountInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));

		asrt.assertEquals(ActualAmountInItemDetailsPge,ExpectedAmountInItemDetailsPge,1,"Cvalue mismatch: "+ExpectedAmountInItemDetailsPge+" but got "+ActualAmountInItemDetailsPge+" in item details page");		

		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		String ExpectedTotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
		String ItemName = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = ItemName.substring(ItemName.indexOf("-") + 2);
		String ExpectedGrossWeight = String.valueOf(Double.parseDouble(ExpectedGrossWeightInItemDetailsPge));
		String ExpectedTotalAmount = String.valueOf(Double.parseDouble(ExpectedTotalAmountInItemDetailsPge));

		// Step 16: Click on Add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ExpectedLinesCountInBillingPge = AllProductRows.size();
		int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));

		asrt.assertEquals(ActualLinesCountInBillingPge, ExpectedLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");

		String ActualItemNameInBillingPge = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));

		asrt.assertEquals(ActualItemNameInBillingPge, ExpectedItemNameInItemDetailsPge,"Item name mismatch: "+ExpectedItemNameInItemDetailsPge+" but found "+ActualItemNameInBillingPge+" in Billing page");

		String ActualGrossWeightInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5")).replace("-", "");
		String ActualGrossWeight = String.valueOf(Double.parseDouble(ActualGrossWeightInBillingPge));

		asrt.assertEquals(ExpectedGrossWeight, ActualGrossWeight,"Gross weight mismatch: "+ExpectedGrossWeight+" but found "+ActualGrossWeight+" in Billing page");

		String ActualTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("Ã¢â€šÂ¹", "").replace(",", "").trim();
		String ActualTotalWithoutTax = String.valueOf(Double.parseDouble(ActualTotalWithoutTaxInBillingPge.replaceAll("[^\\d.]", "")));		

		asrt.assertEquals(ActualTotalWithoutTax,ExpectedTotalAmount, "Total without tax mismatch: "+ExpectedTotalAmount+" but found "+ActualTotalWithoutTax+" in Billing page");

		String ExpectedSubTotalInBillingPge = ActualTotalWithoutTax;
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("Ã¢â€šÂ¹", "").replace(",", "").trim();
		String ActualSubTotal = String.valueOf(Double.parseDouble(ActualSubTotalInBillingPge.replaceAll("[^\\d.]", "")));	

		asrt.assertEquals(ActualSubTotal,ExpectedSubTotalInBillingPge ,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotal+" in Billing page");

		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("Ã¢â€šÂ¹", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_OGTax;

		asrt.assertEquals(ActualTaxInBillingPge.replaceAll("[^\\d.]", ""),ExpectedTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in Billing page");

		String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;	
		String ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("Ã¢â€šÂ¹", "").replace(",", "").trim();
		String ActualTotalAmount = String.valueOf(Double.parseDouble(ActualTotalAmountInBillingPge.replaceAll("[^\\d.]", "")));	

		asrt.assertEquals(ActualTotalAmount,ExpectedTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmount+" in Billing page");

		// Step 17: Click on the Amount
		String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("Ã¢â€šÂ¹", "").replace(",", "").trim();
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		// Step 18: Click on RTGS/NEFT
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("RTGS/NEFT"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment","iconReturnKey"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		// Step 19: Post invoice
		// Expected: Check final invoice details
		String ExpectedGrossWeightFromBillingPge = ActualGrossWeight;
		String ExpectedOGPurchaseRateFromItemDetailsPge = String.format("%.2f",ExpectedOGPurchaseRateInItemDetailsPge);
		Map<String, String> OgPurchaseBill = pdfUtils.OGPdfValidation(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC43_PaymentDetails);
		String GrossWeightFromInvoice = OgPurchaseBill.get("GrossWt1");
		String GrossWeight = String.valueOf(Double.parseDouble(GrossWeightFromInvoice));	
		String NetWeightFromInvoice = OgPurchaseBill.get("NetWt1");
		String GoldRateFromInvoice = OgPurchaseBill.get("Rate1");
		String TotalAmountFromInvoice = OgPurchaseBill.get("TotalAmount1");
		String TotalAmount = String.valueOf(Double.parseDouble(TotalAmountFromInvoice));	
		String PaymentDetailsFromInvoice = OgPurchaseBill.get("PaymentDetails");
		String PaymentDetails = String.valueOf(Double.parseDouble(PaymentDetailsFromInvoice));
		String InvoiceNo = OgPurchaseBill.get("InvoiceNumber");

		asrt.assertEquals(GrossWeight,ExpectedGrossWeightFromBillingPge, "Gross Weight mismatch: "+ExpectedGrossWeightFromBillingPge+" but got "+GrossWeight+" in Invoice Pdf");		
		asrt.assertEquals(NetWeightFromInvoice,ExpectedNetWeightInItemDetailsPge,"Net Weight mismatch: "+ExpectedNetWeightInItemDetailsPge+" but got "+NetWeightFromInvoice+" in Invoice Pdf");	
		asrt.assertEquals(GoldRateFromInvoice,ExpectedOGPurchaseRateFromItemDetailsPge,"Gold rate mismatch: "+ExpectedOGPurchaseRateFromItemDetailsPge+" but got "+ GoldRateFromInvoice+" in Invoice Pdf");	
		asrt.assertEquals(TotalAmount,ExpectedTotalAmountInBillingPge,"Total amount mismatch: "+ExpectedTotalAmountInBillingPge+" but got "+TotalAmount+" in Invoice Pdf");	
		asrt.assertEquals(PaymentDetails,ExpectedTotalAmountInBillingPge,"Payment details mismatch: "+ExpectedTotalAmountInBillingPge+" but got "+PaymentDetails+" in Invoice Pdf");

		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		// Step 20: ERP 
		//Expected :In ERP, check for the latest entry in RTGS Payment details
		erpUtils.RTGSPaymentDetails(ActualCustomerName,ReceiveAmtFromInBillingPge.replaceAll("[^\\d.-]", ""));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : OG converting to cash,RTGS,AdvanceS
	// Automation ID : TC48
	// Author: Vishnu RR
	public void TC48_OldPurchaseExchangeOwnorOtherGoldorSilverSale() throws Exception {

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC48_CustomerId, UtilityTestData.MenuBarOptions[0]);
		String CustomerName = base.GetText(NormalSaleGoldandSilverObj.Ele_Purchase("customerName"));
		Thread.sleep(3000);
		// Step 4: Select OG
		// Step 5: Click on OG own
		// Step 6: Select configuration(purity 22K)
		// Step 7: Click on Add item button
		// 8. Select QC person
		// 9. Select Smith person
		// 10. Enter piece value
		// 11. Enter gross weight
		//12. click on calculate button
		appUtils.PurchaseOldGold(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC48_Configuration, 
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC48_PurchaseOrExchange,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC48_QCPerson,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC48_SmithPerson,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC48_GrossPieces,
				OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC48_GrossWeight);

		Thread.sleep(2000);
		//Checking the Calculation OG
		double NetWeight = Double.parseDouble(base.GetValue(CustomerCreationObj.Ele_Name("ogpnetwt")));
		double NetRate = Double.parseDouble(base.GetValue(CustomerCreationObj.Ele_Name("ogpnetrate")));
		double ExpectedTotalValue = Math.round(NetWeight * NetRate );
		double ActualTotalValue = Double.parseDouble(base.GetValue(CustomerCreationObj.Ele_Name("ogpnetamt")));
		double ActualGrossAmt = Double.parseDouble(base.GetValue(CustomerCreationObj.Ele_Name("ogpgrossamt"))); 
		double ActualTotalAmt = Double.parseDouble(base.GetValue(CustomerCreationObj.Ele_Name("ogptotalamt")));
		double ActualCValue = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_OGRate("column5 textRight","gridcell")));

		asrt.assertEquals(ExpectedTotalValue, ActualTotalValue," Amount mismatch: "+ExpectedTotalValue+" but found "+ActualTotalValue+" in Billing page");
		asrt.assertEquals(ExpectedTotalValue, ActualGrossAmt,"Gross Amount mismatch: "+ExpectedTotalValue+" but found "+ActualGrossAmt+" in Billing page");
		asrt.assertEquals(ExpectedTotalValue, ActualTotalAmt,"Total Amount mismatch: "+ExpectedTotalValue+" but found "+ActualTotalAmt+" in Billing page");
		asrt.assertEquals(ExpectedTotalValue, ActualCValue,"Actual C Value mismatch: "+ExpectedTotalValue+" but found "+ActualCValue+" in Billing page");

		Thread.sleep(2000);
		//Billing page Calculation OG
		//String LinesCount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4"));
		base.actionClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand", "win-commandimage"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Calculations in the billing page 

		List<WebElement> AllProduct = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ExpectedLinesCountInBillingPage = AllProduct.size();
		int ActualLinesCountInBillingPage = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));
		//Amount in the List 
		double ActualAmtInList = Double.parseDouble(
				base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"))
				.replace("-", "")
				.replace("\u20B9", "")
				.replace(",", "")
				.trim()
				);
		//Sub total Amt
		double ActualSubTotalAmt = Double.parseDouble(
				base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"))
				.replace("-", "")
				.replace("\u20B9", "")
				.replace(",", "")
				.trim()
				);
		//Total Amt
		double ActualTotalAmount = Double.parseDouble(
				base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4"))
				.replace("-", "")
				.replace("\u20B9", "")
				.replace(",", "")
				.trim()
				);
		asrt.assertEquals(ExpectedLinesCountInBillingPage, ActualLinesCountInBillingPage,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPage+" but got "+ActualLinesCountInBillingPage+" in Billing page");
		asrt.assertEquals(ExpectedTotalValue, ActualAmtInList,"Amount in the List  "+ExpectedTotalValue+" but got "+ActualAmtInList+" in Billing page");
		asrt.assertEquals(ExpectedTotalValue, ActualSubTotalAmt,"SubTotal Amount is "+ExpectedTotalValue+" but got "+ActualSubTotalAmt+" in Billing page");
		asrt.assertEquals(ExpectedTotalValue, ActualTotalAmount,"Total Amount is  "+ExpectedTotalValue+" but got "+ActualTotalAmount+" in Billing page");

		// 13.Click on amount 
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		Thread.sleep(1000);

		//			        14 Select convert advance
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_ConvtAdv("win-item col grow", "Convert to Advance"));

		//			        15. Choose normal advance
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("DepAmount"));

		//16. Enter advance amount
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("DepAmount"),OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData.TC48_AdvanceAmt);

		//			        17. Select sales person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Ele_SalesPerson("3"), OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData.TC48_SalesPerson);

		//			        18. Select the due date
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Ele_DrpdwnYear("win-datepicker-year win-dropdown win-order2 displayOrder3"), OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData.TC48_DueYear);
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Remarks("6"));

		//		            19. Enter remarks
		base.setData(NormalSaleGoldandSilverObj.Ele_Remarks("6"),OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData.TC48_Remark);

		//				   20. click on nominee details
		base.scrollToElement(NormalSaleGoldandSilverObj.Btn_Deposit("Add Nominee Details"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Add Nominee Details"));
		Thread.sleep(1000);

		//			        21.1. Enter nominee name
		//			        21.2 Select nominee relation
		//			        21.3 Click on check box(Same as customer address)
		//			        21.4 Click on OK button
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Nominee name"), OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData.TC48_Nomineename);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Nominee relation"), OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData.TC48_NomineRelation);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NomineeChkBox"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		//			        22. Click on Deposit button	        
		base.scrollToElement(NormalSaleGoldandSilverObj.Btn_Deposit("Deposit"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Deposit"));

		//			        23. again click amount
		//Expected Check ADVANCE RECEIPT VOUCHER details
		//					 *Towards the advance against purchase of approximate value
		//					 *Fixed Gold rate
		//					 *Deposit Type
		//					 *Advance Received
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		Thread.sleep(5000);   

		//					25. Select payment method as cash
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_ConvtAdv("win-item col grow", "Cash"));
		Thread.sleep(3000);

		//					 26. Enter partial amount 
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ClearButton("Clear"));
		base.actionSetData(CustomerCreationObj.Ele_ERPCustid("Payment amount"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC48_PartialAmount);

		//					 27. Click enter button   
		base.actionClick(NormalSaleGoldandSilverObj.Btn_Estimation("flexGrow100 accentBackground enter"));
		//					 28. Again click on amount
		String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		System.out.print(ReceiveAmtFromInBillingPge);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		Thread.sleep(1000);
		//			        29. Select payment method as RTGS/NEFT
		//			        30.Post the Invoice

		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("RTGS/NEFT"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment","iconReturnKey"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		//Validate  Fixed Gold rate, Deposit Type, Advance Received
		Thread.sleep(10000);
		Map<String, String> Result = pdfUtils.NormalAdvancePdfValidation();	
		String AdvanceReceived     = Result.get("AdvanceReceived");
		String FixedGoldRate       = Result.get("FixedGoldRate");
		String MaxGoldRate         = Result.get("MaxGoldRate");
		String ApproxWeight        = Result.get("ApproxWeight");
		String DepositType         = Result.get("DepositType");
		double PdfAdvanceReceived  = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
		double PdfFixedGoldRate    = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
		double PdfMaxGoldRate      = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,1.0, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
		asrt.assertEquals(UtilityTestData.DepositType, DepositType, "Mismatch in Deposit Type Actual "+UtilityTestData.DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

		Double WeightValue          = PdfAdvanceReceived/PdfMaxGoldRate;
		BigDecimal CalcWeightValue  = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
		double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),0.10, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");
		Thread.sleep(5000);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

		//Expected :In ERP, check for the latest entry in RTGS Payment details
		Thread.sleep(120000);
		erpUtils.RTGSPaymentDetails(CustomerName,ReceiveAmtFromInBillingPge);
	}

	// <summary>
	// Test Case Title : Own multiple purity silver exchange by manual entry
	// Automation ID : TC45
	// Author: Gokul. P
	// </summary>
	public void TC45_OldPurchaseExchangeOwnorOtherGoldorSilverSale() throws Exception
	{   
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		// 1. Login to POS 		
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 80);
		// 2. Click on Transaction button
		//		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		//		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		appUtils.HamBurgerButtonClick("iconShop");
		// 3. Select customer and click on add to estimation button
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_CustomerNo , UtilityTestData.MenuBarOptions[5]);

		// 4.Scan SKU
		List<String> SkuList = appUtils.SelectMultipleSKUs(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_SkuCountToAdd,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_TransferType,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_FromCounter,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_MetalType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		List<String> ItemNames        = new ArrayList<>();
		List<String> ItemQuantity 	  = new ArrayList<>();
		List<String> TotalForEachItem = new ArrayList<>();
		Map<String, String> ItemData  = new HashMap<>();
		for (int i = 0; i < SkuList.size(); i++)
		{  
			Thread.sleep(2000);
			appUtils.SKUIngridentTableValues(SkuList.get(i), i + 1, ItemData);
			ItemNames.add(ItemData.get("SKU_" + (i+1) + "_HeaderName"));
			ItemQuantity.add(ItemData.get("SKU_" + (i+1) + "_GrossWeight"));
			TotalForEachItem.add(ItemData.get("SKU_" + (i+1) + "_Total"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ItemData, i+1);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, i+1);
		}


		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(SkuList, ItemData);
		String SubTotal         = TransactionPageItems.get("Subtotal");
		String Discount         = TransactionPageItems.get("Discount");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight   = TransactionPageItems.get("NetWeight");
		String Gst              = TransactionPageItems.get("Tax");
		String InvoiceTotal     = TransactionPageItems.get("TotalAmount");
		String NetTotal         = TransactionPageItems.get("NetTotal");
		String LinesCount       = TransactionPageItems.get("LinesCount");

		//		 5. Select OS
		//		 6.Click on OS own
		//		 7. Select configuration(purity 925,800)
		//		 8. Click on Add item button
		//		 9. Select QC person
		//		 10. Select Smith person
		//		 11. Enter piece value
		//		 12. Enter gross weight
		//		 13. click on calculate button
		//		 14. Click on Add to cart button
		//       Expected: Check Exchange rate(Board rate)
		//       Check calculation:  Note:Billing Screen
		//		 No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax),Displayed Subtotal, TAX, Total Amount
		int PurityCount					  =0;
		double TotalCValueItemDetailsPage =0.0;
		double CValueFor925PurityOS       =0.0;
		double CValueFor800PurityOS       =0.0;
		double OsNetWtFor925OSPurity      =0.0;
		double OsNetWtFor800OSPurity      =0.0;
		for(String OsPurity:OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_OSPurity)
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
			base.ClickCondition(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.setDataWithoutClear(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_ExchangeProduct);	
			Thread.sleep(1000);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Ele_OSOwn("Old Silver Own"));

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
			base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			Thread.sleep(2000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OsPurity);

			//Step for Bug: 'Unexpected Error' Pop up displayed in the Old Gold Details page
			if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
				try {
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
					base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
					System.out.println("Popup appeared and OK button was clicked.");
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				} catch (Exception e) {System.out.println("Failed to click the OK button: " + e.getMessage());}
			} else {System.out.println("Popup not found, skipping the close button action.");}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			Thread.sleep(1000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_QcAndSmithPerson);
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_GrossPieces);
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_GrossWeight);
			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
			Thread.sleep(6000);
			String Rate  =base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate"));
			double OsRate=Double.parseDouble(Rate);
			if(OsPurity.equals("925"))
			{
				double ExchangeRateOsItemDetailsPage=87.8*0.925;
				Thread.sleep(2000);

				asrt.assertEquals(ExchangeRateOsItemDetailsPage, OsRate,1,"The rate of OS (Purity 925) is not displayed correctly in the Item Details Page: Expected: "+ExchangeRateOsItemDetailsPage+"but Actual:"+OsRate);

				String NetWt          =base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
				OsNetWtFor925OSPurity =Double.parseDouble(NetWt);
				String CValue         =base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
				CValueFor925PurityOS  =Double.parseDouble(CValue);
				double CValueOs       =OsNetWtFor925OSPurity*ExchangeRateOsItemDetailsPage;

				asrt.assertEquals(CValueFor925PurityOS, CValueOs,1,"The CValue for the OS purity "+OsPurity+" is not as expected in the Item Details Page: Expected: "+CValueFor925PurityOS+" Actual: "+CValueOs);

				TotalCValueItemDetailsPage+=CValueFor925PurityOS;
			}else {
				double ExchangeRateOsItemDetailsPage=76*0.8;

				asrt.assertEquals(ExchangeRateOsItemDetailsPage, OsRate,1,"The rate of OS (Purity 800) is not displayed correctly in the Item Details Page: Expected: "+ExchangeRateOsItemDetailsPage+"but Actual:"+OsRate);

				String NetWt          =base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
				OsNetWtFor800OSPurity      =Double.parseDouble(NetWt);
				String CValue        =base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
				CValueFor800PurityOS =Double.parseDouble(CValue);
				double CValueOs      =OsNetWtFor800OSPurity*ExchangeRateOsItemDetailsPage;

				asrt.assertEquals(CValueFor800PurityOS, CValueOs,1,"The CValue for the OS purity "+OsPurity+" is not as expected in the Item Details Page: Expected: "+CValueFor800PurityOS+" Actual: "+CValueOs);

				TotalCValueItemDetailsPage+=CValueFor800PurityOS;
			}
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Ele_MenuBar("Add to Cart"));
			Thread.sleep(2000);			
			PurityCount++;
		}
		List<WebElement> AllProductRows         = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling       = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling        = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling         = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String>     TextOSItemNamesBilling = new ArrayList<>();
		List<String>     TextItemNamesBilling   = new ArrayList<>();
		List<String>     TextItemQtyBilling     = new ArrayList<>();
		List<String>     ItemQuantityRecall     = new ArrayList<>();

		Map<String, String> BillingScrnTableData  = appUtils.TransactionSKUsLinesVerify(SkuList, ItemData);		
		String SubTotalAfterOS                    = BillingScrnTableData.get("Subtotal");
		String TotalGrossWeightAfterOS            = BillingScrnTableData.get("GrossWeight");
		String TotalNetWeightAfterOS              = BillingScrnTableData.get("NetWeight");
		String TaxAfterOS                         = BillingScrnTableData.get("Tax");
		String TotalAmountAfterOS                 = BillingScrnTableData.get("TotalAmount");
		String NetTotalAfterOS                    = BillingScrnTableData.get("NetTotal");
		String LinesCountAfterOS                  = BillingScrnTableData.get("LinesCount");

		for(int i=0;i<ItemQtyBilling.size();i++)
		{
			ItemQuantityRecall.add(ItemQtyBilling.get(i).getText());
		}

		double TotalAmountAfterOGBillingScrn      = Double.parseDouble(TotalAmountAfterOS.trim().replaceAll("[^\\d.]", ""));
		String PdfTotalAmountAfterOS              = String.valueOf(TotalAmountAfterOGBillingScrn);
		double TaxAfterOGBillingScrn              = Double.parseDouble(TaxAfterOS.trim().replaceAll("[^\\d.]", ""));
		String PdfTaxAfterOS                      = String.valueOf(TaxAfterOGBillingScrn);
		double SubTotalAfterOSBillingScrn         = Double.parseDouble(SubTotalAfterOS.trim().replaceAll("[^\\d.]", ""));
		String PdfSubTotalAfterOS                 = String.valueOf(SubTotalAfterOSBillingScrn);
		double NetTotalAfterOSBillingScrn         = Double.parseDouble(NetTotalAfterOS.trim().replaceAll("[^\\d.]", ""));
		String PdfNetTotalAfterOS                 = String.valueOf(NetTotalAfterOSBillingScrn);
		double TotalGrossWeightAfterOSBillingScrn = Double.parseDouble(TotalGrossWeightAfterOS.trim().replaceAll("[^\\d.]", ""));
		String PdfTotalGrossWeightAfterOS         = String.valueOf(TotalGrossWeightAfterOSBillingScrn);

		int LinesCountBilling     = Integer.parseInt(LinesCountAfterOS);
		double SubTotalBilling    = Double.parseDouble(SubTotalAfterOS.trim().replaceAll("[^\\d.\\-]", ""));
		double TaxBilling         = Double.parseDouble(TaxAfterOS.trim().replaceAll("[^\\d.\\-]", ""));
		double TotalAmountBilling = Double.parseDouble(TotalAmountAfterOS.trim().replaceAll("[^\\d.\\-]", ""));

		int ExpectedLinesCountInBillingPge = AllProductRows.size();	

		asrt.assertEquals(ExpectedLinesCountInBillingPge,LinesCountBilling, "Mismatch in Lines count,expected "+ExpectedLinesCountInBillingPge+" but got "+LinesCountBilling+" in the billing screen");

		String SalesReturnSkuItemRate    = ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.\\-]", "");
		String PdfSalesReturnSkuItemRate = 	ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.-]", "");
		double FirstItemRate             = Double.parseDouble(SalesReturnSkuItemRate);

		double ExpectedSubTotal              = 0.0;
		ExpectedSubTotal                     = FirstItemRate-(TotalCValueItemDetailsPage);
		double ExpectedTaxAmnt               = FirstItemRate * 3 / 100;  
		String ExpectedTaxAmountInBillingPge = String.valueOf(ExpectedTaxAmnt);
		double ExpectedTaxAmount             = Double.parseDouble(ExpectedTaxAmountInBillingPge.trim().replaceAll("[^\\d.\\-]", ""));
		double ActualTotalAmountInBilling    = ExpectedSubTotal + ExpectedTaxAmount;

		asrt.assertEquals(ExpectedTaxAmount,TaxBilling,1.0,"Mismatch in Tax value, expected "+ExpectedTaxAmount+" but got "+TaxBilling+" in the billing screen");

		for (int n=0; n<ItemNamesBilling.size();n++)
		{
			String ItemName=ItemNamesBilling.get(n).getText().trim();
			if(ItemName.contains("OS")||ItemName.contains("Old Silver"))
			{TextOSItemNamesBilling.add(ItemName);}
			else {TextItemNamesBilling.add(ItemName);}
		}
		Thread.sleep(3000);
		for(int ItemName=0;ItemName<TextItemNamesBilling.size();ItemName++)
		{
			String ExpectedItemName =ItemNames.get(ItemName);	
			String ExpectedTrimmed = ExpectedItemName.contains("-") ?ExpectedItemName.substring(ExpectedItemName.indexOf("-") + 2) : ExpectedItemName;

			String ActualItemName   = ItemNamesBilling.get(ItemName).getText().trim();
			asrt.assertEquals(ExpectedTrimmed,ActualItemName, "Mismatch in item name, expected "+ExpectedItemName+"but got "+ActualItemName+"  in the billing screen.");
		}
		for (int i=0; i<TextOSItemNamesBilling.size();i++)
		{
			String ActualOSItemName = TextOSItemNamesBilling.get(i).trim();

			asrt.assertEquals(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_OSProductName.get(i), ActualOSItemName,"Mismatch in OS item name, expected "+OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_OSProductName+" but got "+ActualOSItemName+" in the billing screen.");
		}		
		for (int i = 0; i <ItemNamesBilling.size(); i++)
		{
			String ItemName=ItemNamesBilling.get(i).getText().trim();
			if(ItemName.contains("OS")||ItemName.contains("Old Silver"))
			{
				String TextItemRateBilling = ItemRateBilling.get(i).getText().replace("-", "").trim();
				Double ActualOSRate        = Double.parseDouble(TextItemRateBilling.replace("\u20B9", "").replace(",", "").trim());
				if(ItemName.equals("OS"))
				{
					double ExpectedOSRate=CValueFor925PurityOS;
					asrt.assertEquals(ExpectedOSRate, ActualOSRate,1.0, "Mismatch in total amount for OS (Purity 925), expected "+ExpectedOSRate+" but got "+ActualOSRate+" in Billing screen");
				}else 
				{ double ExpectedOSRate=CValueFor800PurityOS;
				asrt.assertEquals(ExpectedOSRate, ActualOSRate,1.0, "Mismatch in total amount for  OS (Purity 800), expected "+ExpectedOSRate+" but got "+ActualOSRate+" in Billing screen");
				}								
			}    
			else {	
				String ItemRate           = ItemRateBilling.get(i).getText();
				double ExpectedTotal      = Double.parseDouble(ItemRate.replaceAll("[^\\d.]", ""));
				String ActualTotalBilling = ItemRateBilling.get(i).getText().trim();
				double ActualTotal        = Double.parseDouble(ActualTotalBilling.replaceAll("[^\\d.]", ""));
				asrt.assertEquals(ExpectedTotal, ActualTotal,1.0,"Mismatch in total amounts (without tax),expected "+ExpectedTotal+" but got "+ActualTotal+" in the Billing screen.");		}
		}

		asrt.assertEquals(ExpectedSubTotal,SubTotalBilling,1.0,"Mismatch in Subtotal value, expected "+ExpectedSubTotal+" but got "+SubTotalBilling+"in the Billing screen");	
		asrt.assertEquals(TotalAmountBilling,ActualTotalAmountInBilling,1.0,"Mismatch in Total amount value, expected "+TotalAmountBilling+" but got "+ActualTotalAmountInBilling+" in the Billing screen");

		// 15. Save Estimate
		// Expected: Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(5000);	
		String FirstInvoice        = pdfUtils.DownloadAndRenameLatestPDF("TC45_ProformaInvoice");
		Thread.sleep(5000);		
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FirstInvoice+"";
		String TaxableValue        = PdfSalesReturnSkuItemRate;
		String InvoiceTotalAmount  = PdfTotalAmountAfterOS;
		String GST                 = PdfTaxAfterOS;
		String TotalAmnt           = PdfSubTotalAfterOS;
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProformaInvoicePath,
				TaxableValue,
				GST,
				InvoiceTotalAmount,
				PdfNetTotalAfterOS,
				TotalAmnt
				);

		//	     16.Recall estimate from cashier screen
		//		 17. Select estimate and click on Recall estimation button
		//		 18.Select the Transaction Type as Sales & Exchange
		//		 19.Click on Process Estimation 
		//		 20.Choose a sales representative
		//		 21.Click on the Amount
		//		 22.Select a Payment method(Cash or Card)
		//		 23.Post the Invoice
		//  Expected Result: Cashier Screen after recall*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		//  Expected Result:Check Exchange invoice details
		//  Expected Result Check final invoice details		
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));		
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		String ExpectedEstmnNumber= base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));

		Assert.assertEquals(EstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+EstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+" in the Recall Estimation page"); 

		List<String> TextItemNameRecall=new ArrayList<>();

		TextItemNameRecall.addAll(TextItemNamesBilling);
		TextItemNameRecall.addAll(TextOSItemNamesBilling);	

		appUtils.ValidateBillingAndRecallScreenData(EstmnNumber,LinesCountAfterOS,TextItemNameRecall,ItemQuantityRecall, SubTotalAfterOS, TaxAfterOS, NetTotalAfterOS);

		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"), "Exchange");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) {
			try { 
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} catch (Exception e) {}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> PurchaseBillData =pdfUtils.OSPdfValidation(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_PaymentDetails);
		String ExchngeAdjustment             =PurchaseBillData.get("PaymentDetails");
		Double ActualExchangeAdjustment      = Double.parseDouble(ExchngeAdjustment);		
		String ExchangeNwt925                = PurchaseBillData.get("NetWt1");
		Double ExpectedNetWt925              = Double.parseDouble(ExchangeNwt925);
		String ExchangeNwt800                = PurchaseBillData.get("NetWt2");
		Double ExpectedNetWt800              = Double.parseDouble(ExchangeNwt800);
		Double ExpectedExchangeAdjustment    =CValueFor925PurityOS+CValueFor800PurityOS;		
		asrt.assertEquals(ActualExchangeAdjustment, ExpectedExchangeAdjustment,1,"The Adjustment value displayed in the pdf is not equal in the Purchase Bill: Expected: "+ExpectedExchangeAdjustment+"Actual"+ActualExchangeAdjustment);
		asrt.assertEquals(OsNetWtFor925OSPurity, ExpectedNetWt925,1,"The net weight for the purity 925 is not displayed correctly in the Purchase Bill: Expected:"+OsNetWtFor925OSPurity+" Actual:"+ExpectedNetWt925);
		asrt.assertEquals(OsNetWtFor800OSPurity, ExpectedNetWt800,1,"The net weight for the purity 800 is not displayed correctly in the Purchase Bill: Expected:"+OsNetWtFor800OSPurity+" Actual:"+ExpectedNetWt800);

		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));

		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),"Sales");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));		
		String DiscountBilling                = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC              = base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		double PaymentAmountHDFCTrimmedValue  = Double.parseDouble(PaymentAmountHDFC.trim().replaceAll("[^\\d.]", ""));
		String PdfPaymentAmountHDFC           = String.valueOf(PaymentAmountHDFCTrimmedValue);

		Map<String, String> SkuBillingScrnTableData  = appUtils.TransactionSKUsLinesVerify(SkuList, ItemData);
		String SkuNetTotal                           = SkuBillingScrnTableData.get("TotalAmount");
		String SkuLinesCount                         = SkuBillingScrnTableData.get("LinesCount");
		String TotalNetWeightRecall 	             =   SkuBillingScrnTableData.get("NetWeight");

		double SkuNetTotalTrimmedValue  = Double.parseDouble(SkuNetTotal.trim().replaceAll("[^\\d.]", ""));
		String PdfSkuNetTotal           = String.valueOf(SkuNetTotalTrimmedValue);
		appUtils.PaymentAfterRecallEstimate(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_PaymentMethods);	
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice    = pdfUtils.DownloadAndRenameLatestPDF("TC45_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";
		String GrossAmount     = PdfSalesReturnSkuItemRate;
		String TotalDiscount   = Discount;
		String TotalQtyPcs     = SkuLinesCount;
		String Adjustment      = ExchngeAdjustment;
		String PaymentHDFCCard = PdfPaymentAmountHDFC;
		String TotalDiamond    ="";
		String SalesPdfPath    = SaleInvoicePath;
		TotalGrossWeight       = TotalGrossWeight;
		String NetValue        = PdfSkuNetTotal;
		String TotalNetWt      = TotalNetWeightRecall;

		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWt,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				SkuList,
				ItemData);
		System.out.println("PDF INVOICE NO:-"+PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();	 
	}

	// <summary>
	// Test Case Title : Own Multiple Purity Silver Exchange By Fetching Receipt Number
	// Automation ID : TC46
	// Author: Nivya Ramesan
	// </summary>
	public void TC46_OldPurchaseExchangeOwnorOtherGoldorSilverSale() throws Exception
	{   
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

		//Step 1:Login to POS 		
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2:Click on Transaction button
		appUtils.HamBurgerButtonClick("iconShop");

		//Step 3:Select customer and click on add to estimation button
		appUtils.SearchByCustomerID(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_CustomerNo , UtilityTestData.MenuBarOptions[5]);

		//Step 4:Scan SKU
		List<String> SkuList = appUtils.SelectMultipleSKUs(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_SkuCountToAdd,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_TransferType,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_FromCounter,OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC45_MetalType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		List<String> ItemNames        = new ArrayList<>();
		List<String> ItemQuantity 	  = new ArrayList<>();
		List<String> TotalForEachItem = new ArrayList<>();
		Map<String, String> ItemData  = new HashMap<>();
		for (int i = 0; i < SkuList.size(); i++)
		{  
			Thread.sleep(2000);
			appUtils.SKUIngridentTableValues(SkuList.get(i), i + 1, ItemData);
			ItemNames.add(ItemData.get("SKU_" + (i+1) + "_HeaderName"));
			ItemQuantity.add(ItemData.get("SKU_" + (i+1) + "_GrossWeight"));
			TotalForEachItem.add(ItemData.get("SKU_" + (i+1) + "_Total"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ItemData, i+1);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, i+1);
		}

		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(SkuList, ItemData);
		String SubTotal         = TransactionPageItems.get("Subtotal");
		String Discount         = TransactionPageItems.get("Discount");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight   = TransactionPageItems.get("NetWeight");
		String Gst              = TransactionPageItems.get("Tax");
		String InvoiceTotal     = TransactionPageItems.get("TotalAmount");
		String NetTotal         = TransactionPageItems.get("NetTotal");
		String LinesCount       = TransactionPageItems.get("LinesCount");

		//Step 5: Select OS
		//Step 6: Click on OS own
		//Step 7: Select configuration(purity 925)
		//Step 8: Click on Add item button
		//Step 9: click on purchase button
		//Step 10: Enter receipt number
		//Step 11: enter line number
		//Step 12: Click on fetch button
		//Step 13: Select QC person
		//Step 14: Select Smith person
		//Step 15: Enter piece value
		//Step 16: Enter gross weight
		//Step 17: click on calculate button
		//Step 18: Click on Add to cart button
		//Step Continue step 5-18 for 800 purity
		//Expected: Check Exchange rate(Board rate)
		//Check calculation:  Note:Billing Screen
		//No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax),Displayed Subtotal, TAX, Total Amount

		int PurityCount					  =0;
		double TotalCValueItemDetailsPage =0.0;
		double CValueFor925PurityOS       =0.0;
		double CValueFor800PurityOS       =0.0;
		double NetWtFor925OSPurity        =0.0;
		double NetWtFor800OSPurity        =0.0;
		for(String OsPurity:OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_OSPurity)
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
			base.ClickCondition(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.setDataWithoutClear(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_ExchangeProduct);	
			Thread.sleep(1000);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Ele_OSOwn("Old Silver Own"));

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
			base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			Thread.sleep(2000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OsPurity);

			//Step for Bug: 'Unexpected Error' Pop up displayed in the Old Gold Details page
			if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
				try {
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
					base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
					System.out.println("Popup appeared and OK button was clicked.");
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				} catch (Exception e) {System.out.println("Failed to click the OK button: " + e.getMessage());}
			} else {System.out.println("Popup not found, skipping the close button action.");}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			Thread.sleep(1000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_InvoiceNumber);
			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"));
			if (OsPurity.equals("925")) {
				base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), 
						OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_LineNumber925);
			} else if (OsPurity.equals("800")) {
				base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), 
						OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_LineNumber800);
			}

			base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));
			Thread.sleep(1000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_QcAndSmithPerson);
			Thread.sleep(1000);
			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
			Thread.sleep(6000);
			String Rate  =base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate"));
			double OsRate=Double.parseDouble(Rate);

			//Exchange rate validation
			if(OsPurity.equals(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_OSPurity.get(0)))

			{
				double ExchangeRateOsItemDetailsPage = Math.floor(95 * 0.925 * 10.0) / 10.0;  
				Thread.sleep(2000);
				asrt.assertEquals(ExchangeRateOsItemDetailsPage, OsRate, 1,"The rate of OS (Purity 925) is not displayed correctly in the Item Details Page: Expected: "+ ExchangeRateOsItemDetailsPage + " but Actual: " + OsRate);

				String NetWt = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
				NetWtFor925OSPurity = Double.parseDouble(NetWt);
				String CValue = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
				CValueFor925PurityOS = Double.parseDouble(CValue);

				double CValueOs = NetWtFor925OSPurity * ExchangeRateOsItemDetailsPage;
				asrt.assertEquals(CValueFor925PurityOS, CValueOs, 1,"The CValue for the OS purity " + OsPurity + " is not as expected in the Item Details Page: Expected: "+ CValueFor925PurityOS + " Actual: " + CValueOs);

				TotalCValueItemDetailsPage+=CValueFor925PurityOS;

			}else {
				double ExchangeRateOsItemDetailsPage=95*0.8;

				asrt.assertEquals(ExchangeRateOsItemDetailsPage, OsRate,1,"The rate of OS (Purity 800) is not displayed correctly in the Item Details Page: Expected: "+ExchangeRateOsItemDetailsPage+"but Actual:"+OsRate);

				String NetWt          =base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
				NetWtFor800OSPurity      =Double.parseDouble(NetWt);
				String CValue        =base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
				CValueFor800PurityOS =Double.parseDouble(CValue);
				double CValueOs      =NetWtFor800OSPurity*ExchangeRateOsItemDetailsPage;

				asrt.assertEquals(CValueFor800PurityOS, CValueOs,1,"The CValue for the OS purity "+OsPurity+" is not as expected in the Item Details Page: Expected: "+CValueFor800PurityOS+" Actual: "+CValueOs);

				TotalCValueItemDetailsPage+=CValueFor800PurityOS;
			}
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Ele_MenuBar("Add to Cart"));
			Thread.sleep(2000);			
			PurityCount++;
		}
		List<WebElement> AllProductRows         = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling       = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling        = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling         = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String>     TextOSItemNamesBilling = new ArrayList<>();
		List<String>     TextItemNamesBilling   = new ArrayList<>();
		List<String>     TextItemQtyBilling     = new ArrayList<>();
		List<String>     ItemQuantityRecall     = new ArrayList<>();

		Map<String, String> BillingScrnTableData  = appUtils.TransactionSKUsLinesVerify(SkuList, ItemData);		
		String SubTotalAfterOS                    = BillingScrnTableData.get("Subtotal");
		String TotalGrossWeightAfterOS            = BillingScrnTableData.get("GrossWeight");
		String TotalNetWeightAfterOS              = BillingScrnTableData.get("NetWeight");
		String TaxAfterOS                         = BillingScrnTableData.get("Tax");
		String TotalAmountAfterOS                 = BillingScrnTableData.get("TotalAmount");
		String NetTotalAfterOS                    = BillingScrnTableData.get("NetTotal");
		String LinesCountAfterOS                  = BillingScrnTableData.get("LinesCount");

		for(int i=0;i<ItemQtyBilling.size();i++)
		{
			ItemQuantityRecall.add(ItemQtyBilling.get(i).getText());
		}

		double TotalAmountAfterOSBillingScrn      = Double.parseDouble(TotalAmountAfterOS.trim().replaceAll("[^\\d.]", ""));
		String PdfTotalAmountAfterOS              = String.valueOf(TotalAmountAfterOSBillingScrn);
		double TaxAfterOSBillingScrn              = Double.parseDouble(TaxAfterOS.trim().replaceAll("[^\\d.]", ""));
		String PdfTaxAfterOS                      = String.valueOf(TaxAfterOSBillingScrn);
		double SubTotalAfterOSBillingScrn         = Double.parseDouble(SubTotalAfterOS.trim().replaceAll("[^\\d.]", ""));
		String PdfSubTotalAfterOS                 = String.valueOf(SubTotalAfterOSBillingScrn);
		double NetTotalAfterOSBillingScrn         = Double.parseDouble(NetTotalAfterOS.trim().replaceAll("[^\\d.]", ""));
		String PdfNetTotalAfterOS                 = String.valueOf(NetTotalAfterOSBillingScrn);
		double TotalGrossWeightAfterOSBillingScrn = Double.parseDouble(TotalGrossWeightAfterOS.trim().replaceAll("[^\\d.]", ""));
		String PdfTotalGrossWeightAfterOS         = String.valueOf(TotalGrossWeightAfterOSBillingScrn);

		int LinesCountBilling     = Integer.parseInt(LinesCountAfterOS);
		double SubTotalBilling    = Double.parseDouble(SubTotalAfterOS.trim().replaceAll("[^\\d.\\-]", ""));
		double TaxBilling         = Double.parseDouble(TaxAfterOS.trim().replaceAll("[^\\d.\\-]", ""));
		double TotalAmountBilling = Double.parseDouble(TotalAmountAfterOS.trim().replaceAll("[^\\d.\\-]", ""));

		int ExpectedLinesCountInBillingPge = AllProductRows.size();	

		asrt.assertEquals(ExpectedLinesCountInBillingPge,LinesCountBilling, "Mismatch in Lines count,expected "+ExpectedLinesCountInBillingPge+" but got "+LinesCountBilling+" in the billing screen");

		String SalesReturnSkuItemRate    = ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.\\-]", "");
		String PdfSalesReturnSkuItemRate = 	ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.-]", "");
		double FirstItemRate             = Double.parseDouble(SalesReturnSkuItemRate);

		double ExpectedSubTotal              = 0.0;
		ExpectedSubTotal                     = FirstItemRate-(TotalCValueItemDetailsPage);
		double ExpectedTaxAmnt               = FirstItemRate * 3 / 100;  
		String ExpectedTaxAmountInBillingPge = String.valueOf(ExpectedTaxAmnt);
		double ExpectedTaxAmount             = Double.parseDouble(ExpectedTaxAmountInBillingPge.trim().replaceAll("[^\\d.\\-]", ""));
		double ActualTotalAmountInBilling    = ExpectedSubTotal + ExpectedTaxAmount;

		asrt.assertEquals(ExpectedTaxAmount,TaxBilling,1.0,"Mismatch in Tax value, expected "+ExpectedTaxAmount+" but got "+TaxBilling+" in the billing screen");

		for (int n=0; n<ItemNamesBilling.size();n++)
		{
			String ItemName=ItemNamesBilling.get(n).getText().trim();
			if(ItemName.contains("OS")||ItemName.contains("Old Silver"))
			{TextOSItemNamesBilling.add(ItemName);}
			else {TextItemNamesBilling.add(ItemName);}
		}
		Thread.sleep(3000);
		for(int ItemName=0;ItemName<TextItemNamesBilling.size();ItemName++)
		{
			String ExpectedItemName =ItemNames.get(ItemName);	
			String ExpectedTrimmedItemName = ExpectedItemName.contains("-") ?ExpectedItemName.substring(ExpectedItemName.indexOf("-") + 2) : ExpectedItemName;

			String ActualItemName   = ItemNamesBilling.get(ItemName).getText().trim();
			asrt.assertEquals(ExpectedTrimmedItemName,ActualItemName, "Mismatch in item name, expected "+ExpectedItemName+"but got "+ActualItemName+"  in the billing screen.");
		}
		for (int i=0; i<TextOSItemNamesBilling.size();i++)
		{
			String ActualOSItemName = TextOSItemNamesBilling.get(i).trim();

			asrt.assertEquals(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_OSProductName.get(i), ActualOSItemName,"Mismatch in OS item name, expected "+OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_OSProductName+" but got "+ActualOSItemName+" in the billing screen.");
		}		
		for (int i = 0; i <ItemNamesBilling.size(); i++)
		{
			String ItemName=ItemNamesBilling.get(i).getText().trim();
			if(ItemName.contains("OS")||ItemName.contains("Old Silver"))
			{
				String TextItemRateBilling = ItemRateBilling.get(i).getText().replace("-", "").trim();
				Double ActualOSRate        = Double.parseDouble(TextItemRateBilling.replace("\u20B9", "").replace(",", "").trim());
				if(ItemName.equals("OS"))
				{
					double ExpectedOSRate=CValueFor925PurityOS;
					asrt.assertEquals(ExpectedOSRate, ActualOSRate,1.0, "Mismatch in total amount for OS (Purity 925), expected "+ExpectedOSRate+" but got "+ActualOSRate+" in Billing screen");
				}else 
				{ double ExpectedOSRate=CValueFor800PurityOS;
				asrt.assertEquals(ExpectedOSRate, ActualOSRate,1.0, "Mismatch in total amount for  OS (Purity 800), expected "+ExpectedOSRate+" but got "+ActualOSRate+" in Billing screen");
				}								
			}    
			else {	
				String ItemRate           = ItemRateBilling.get(i).getText();
				double ExpectedTotal      = Double.parseDouble(ItemRate.replaceAll("[^\\d.]", ""));
				String ActualTotalBilling = ItemRateBilling.get(i).getText().trim();
				double ActualTotal        = Double.parseDouble(ActualTotalBilling.replaceAll("[^\\d.]", ""));
				asrt.assertEquals(ExpectedTotal, ActualTotal,1.0,"Mismatch in total amounts (without tax),expected "+ExpectedTotal+" but got "+ActualTotal+" in the Billing screen.");		}
		}

		asrt.assertEquals(ExpectedSubTotal,SubTotalBilling,1.0,"Mismatch in Subtotal value, expected "+ExpectedSubTotal+" but got "+SubTotalBilling+"in the Billing screen");	
		asrt.assertEquals(TotalAmountBilling,ActualTotalAmountInBilling,1.0,"Mismatch in Total amount value, expected "+TotalAmountBilling+" but got "+ActualTotalAmountInBilling+" in the Billing screen");

		//Step 19:Save Estimate
		//Expected: Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(5000);	
		String FirstInvoice        = pdfUtils.DownloadAndRenameLatestPDF("TC46_ProformaInvoice");
		Thread.sleep(5000);		
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FirstInvoice+"";
		String TaxableValue        = PdfSalesReturnSkuItemRate;
		String InvoiceTotalAmount  = PdfTotalAmountAfterOS;
		String GST                 = PdfTaxAfterOS;
		String TotalAmnt           = PdfSubTotalAfterOS;
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProformaInvoicePath,
				TaxableValue,
				GST,
				InvoiceTotalAmount,
				PdfNetTotalAfterOS,
				TotalAmnt
				);

		//Step 20:Recall estimate from cashier screen
		//Step 21:Select estimate and click on Recall estimation button
		//Step 22:Select the Transaction Type as Exchange
		//Step 23.Click on Process Estimation 
		//Step 24.Choose a sales representative
		//Step 25.Click on the Amount
		//Step 26.Post the exchange Invoice
		//Step 27. Again recall estimate from cashier screen
		//Step 28.Select the Transaction Type as sales
		//Step 29.Click on Process Estimation 
		//Step 30.Choose a sales representative
		//Step 31.Click on the Amount
		//Step 32.Select a Payment method(Cash or Card)
		//Step 33.Post the Invoice
		//Expected Result: Cashier Screen after recall*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		//Expected Result:Check Exchange invoice details
		//Expected Result Check final invoice details	

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));		
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		String ExpectedEstmnNumber= base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));

		Assert.assertEquals(EstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+EstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+" in the Recall Estimation page"); 

		List<String> TextItemNameRecall=new ArrayList<>();

		TextItemNameRecall.addAll(TextItemNamesBilling);
		TextItemNameRecall.addAll(TextOSItemNamesBilling);	

		appUtils.ValidateBillingAndRecallScreenData(EstmnNumber,LinesCountAfterOS,TextItemNameRecall,ItemQuantityRecall, SubTotalAfterOS, TaxAfterOS, NetTotalAfterOS);

		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_TransactionTypeExchnge);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) {
			try { 
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} catch (Exception e) {}
		}

		//Check Exchange invoice details(Validation)
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> PurchaseBillData =pdfUtils.OSPdfValidation(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_PaymentDetails);
		String ExchngeAdjustment             =PurchaseBillData.get("PaymentDetails");
		Double ActualExchangeAdjustment      = Double.parseDouble(ExchngeAdjustment);		
		String ExchangeNwt925                = PurchaseBillData.get("NetWt1");
		Double ExpectedNetWt925              = Double.parseDouble(ExchangeNwt925);
		String ExchangeNwt800                = PurchaseBillData.get("NetWt2");
		Double ExpectedNetWt800              = Double.parseDouble(ExchangeNwt800);
		Double ExpectedExchangeAdjustment    =CValueFor925PurityOS+CValueFor800PurityOS;		
		asrt.assertEquals(ActualExchangeAdjustment, ExpectedExchangeAdjustment,1,"The Adjustment value displayed in the pdf is not equal in the Purchase Bill: Expected: "+ExpectedExchangeAdjustment+"Actual"+ActualExchangeAdjustment);
		asrt.assertEquals(NetWtFor925OSPurity, ExpectedNetWt925,1,"The net weight for the purity 925 is not displayed correctly in the Purchase Bill: Expected:"+NetWtFor925OSPurity+" Actual:"+ExpectedNetWt925);
		asrt.assertEquals(NetWtFor800OSPurity, ExpectedNetWt800,1,"The net weight for the purity 800 is not displayed correctly in the Purchase Bill: Expected:"+NetWtFor800OSPurity+" Actual:"+ExpectedNetWt800);

		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));

		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_TransactionTypeSales);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));		
		String DiscountBilling                = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC              = base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		double PaymentAmountHDFCTrimmedValue  = Double.parseDouble(PaymentAmountHDFC.trim().replaceAll("[^\\d.]", ""));
		String PdfPaymentAmountHDFC           = String.valueOf(PaymentAmountHDFCTrimmedValue);

		Map<String, String> SkuBillingScrnTableData  = appUtils.TransactionSKUsLinesVerify(SkuList, ItemData);
		String SkuNetTotal                           = SkuBillingScrnTableData.get("TotalAmount");
		String SkuLinesCount                         = SkuBillingScrnTableData.get("LinesCount");
		String TotalNetWeightRecall 	             =   SkuBillingScrnTableData.get("NetWeight");

		double SkuNetTotalTrimmedValue  = Double.parseDouble(SkuNetTotal.trim().replaceAll("[^\\d.]", ""));
		String PdfSkuNetTotal           = String.valueOf(SkuNetTotalTrimmedValue);
		appUtils.PaymentAfterRecallEstimate(OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC46_PaymentMethods);	
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice    = pdfUtils.DownloadAndRenameLatestPDF("TC46_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";
		String GrossAmount     = PdfSalesReturnSkuItemRate;
		String TotalDiscount   = Discount;
		String TotalQtyPcs     = SkuLinesCount;
		String Adjustment      = ExchngeAdjustment;
		String PaymentHDFCCard = PdfPaymentAmountHDFC;
		String TotalDiamond    ="";
		String SalesPdfPath    = SaleInvoicePath;
		TotalGrossWeight       = TotalGrossWeight;
		String NetValue        = PdfSkuNetTotal;
		String TotalNetWt      = TotalNetWeightRecall;

		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWt,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				SkuList,
				ItemData);
		System.out.println("PDF INVOICE NO:-"+PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();	

	}


}