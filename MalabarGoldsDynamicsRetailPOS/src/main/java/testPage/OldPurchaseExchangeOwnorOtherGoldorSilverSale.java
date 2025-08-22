
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

import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import objectRepository.OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj;
import testData.CommonData;
import testData.OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.AppUtilities.SKUResult;
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
		base.setZoom(driver, 80);

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
		Double ActualRateOg22k = Double.parseDouble(TextItemRateBilling.replace("₹", "").replace(",", "").trim());

		asrt.assertEquals(OgCvalue22k, ActualRateOg22k,1.0, "Mismatch in total amount for 22K OG, expected "+OgCvalue22k+" but got "+ActualRateOg22k+" in Billing screen");

		String TextItmRateBilling= ItemRateBilling.get(2).getText().replace("-", "").trim();
		Double ActualRateOg18k = Double.parseDouble(TextItmRateBilling.replace("₹", "").replace(",", "").trim());

		asrt.assertEquals(OgCvalue18k, ActualRateOg18k,1.0,"Mismatch in total amount for 18K OG,expected "+OgCvalue18k+" but got "+ActualRateOg18k+" in Billing screen");

		for (int n=0; n<ItemQtyBilling.size();n++) 
		{
			TextItemQtyBilling.add(ItemQtyBilling.get(n).getText().trim());
		}

		String ExpectedGrossWt = ExpectedGrossValue.replace("-", "").trim();
		Double ExpectedQty = Double.parseDouble(ExpectedGrossWt);
		String BillingQtySku = ItemQtyBilling.get(0).getText().replace("-", "").trim();
		Double ActualGrossWtSku = Double.parseDouble(BillingQtySku.replace("₹", "").replace(",", "").trim());
		asrt.assertEquals(ExpectedQty, ActualGrossWtSku,1.0,"Mismatch in Qty value,expected "+ExpectedQty+" but got "+ActualGrossWtSku+" in the Billing screen");		

		String BillingQtyOg22k = ItemQtyBilling.get(1).getText().replace("-", "").trim();
		Double ActualQtyOg22k =Double.parseDouble(BillingQtyOg22k.replace("₹", "").replace(",", "").trim());
		asrt.assertEquals(OgQty22k,ActualQtyOg22k,1,"Mismatch in Qty value for 22k OG ,expected "+OgQty22k+" but got "+ActualQtyOg22k+" in the Billing screen");

		String BillingQtyOg18k = ItemQtyBilling.get(2).getText().replace("-", "").trim();
		Double ActualQtyOg18k =Double.parseDouble(BillingQtyOg18k.replace("₹", "").replace(",", "").trim());
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
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_InvoiceNo);
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
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_InvoiceNo);

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

		String ActualTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("₹", "").replace(",", "").trim();
		asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalWithoutTaxInBillingPge,"Total without tax mismatch: "+ExpectedTotalAmountInItemDetailsPge+" but found "+ActualTotalWithoutTaxInBillingPge+" in billing page");

		String ExpectedSubTotalInBillingPge = ActualTotalWithoutTaxInBillingPge;
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC41_OGTax;
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");

		String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
		String ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");

		// Step 15: Click on the Amount
		String ExpectedAmountDueFromBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("₹", "").replace(",", "").trim();
		String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("₹", "").replace(",", "").trim();
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
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));	

		// Step 6: Select configuration(purity 22K)		
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_Purity);

		// Step 7: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
		//Note: click on purchase button
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));

		// Step 8: Select QC Person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_QcAndSmithPerson);

		// Step 9: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_QcAndSmithPerson); 

		//Step 10. Enter piece value
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_GrossPieces);

		//Step 11. Enter gross weight
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"),OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_GrossWeight);

		// Step 12 : click on calculate button
		//Expected: Check Purchase rate(Board rate) and Check calculation
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(1000);
		double ActualOGPurchaseRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		double ExpectedOGPurchaseRateInItemDetailsPge = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_OGRate("column4 textRight","gridcell")));		
		asrt.assertEquals(ExpectedOGPurchaseRateInItemDetailsPge, ActualOGPurchaseRateInItemDetailsPge,"OG Purchase Rate mismatch: Expected value is "+ExpectedOGPurchaseRateInItemDetailsPge+" but got "+ActualOGPurchaseRateInItemDetailsPge+" in item details page");

		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
		double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
		double ExpectedAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualOGPurchaseRateInItemDetailsPge * 100.0) / 100.0;
		double ActualAmountInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(ExpectedAmountInItemDetailsPge, ActualAmountInItemDetailsPge,"Cvalue mismatch: "+ExpectedAmountInItemDetailsPge+" but got "+ActualAmountInItemDetailsPge+" in item details page");		
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

		String ActualTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("₹", "").replace(",", "").trim();
		String ActualTotalWithoutTax = String.valueOf(Double.parseDouble(ActualTotalWithoutTaxInBillingPge));		
		asrt.assertEquals(ExpectedTotalAmount, ActualTotalWithoutTax,"Total without tax mismatch: "+ExpectedTotalAmount+" but found "+ActualTotalWithoutTax+" in Billing page");

		String ExpectedSubTotalInBillingPge = ActualTotalWithoutTax;
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
		String ActualSubTotal = String.valueOf(Double.parseDouble(ActualSubTotalInBillingPge));	
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotal,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotal+" in Billing page");

		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC47_OGTax;
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in Billing page");

		String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;	
		String ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
		String ActualTotalAmount = String.valueOf(Double.parseDouble(ActualTotalAmountInBillingPge));	
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmount,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmount+" in Billing page");

		// Step 14: Click on the Amount
		String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("₹", "").replace(",", "").trim();
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
		erpUtils.RTGSPaymentDetails(ActualCustomerName,ReceiveAmtFromInBillingPge);
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
		base.setZoom(driver, 70);

		// Step 2: Click on Transaction button
		Thread.sleep(5000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

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
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), OldPurchaseExchangeOwnorOtherGoldorSilverSaleTestData.TC42_InvoiceNumber);

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
				String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replace("-", "").replace("₹", "").replace(",", "").trim();
				SubTotalSum = SubTotalSum+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
				String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(TotalAmount.get(i)));
				asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge,"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
			}
			String ExpectedSubTotalInBillingPge = String.format("%.2f",SubTotalSum);
			String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
			asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");


			String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
			ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
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
}