package testPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import objectRepository.AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj;
import objectRepository.GPPSettlementAverageRateSingleSkuMultipleSku_Obj;
import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleDiamondandPlatinum_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import objectRepository.OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj;
import testData.AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve_TestData;
import testData.CommonData;
import testData.OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.AppUtilities.ReturnDetails;
import utilPack.BasePge;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;

//import basePackage.BasePge;

public class AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	MathUtilities mathUtils;
	PdfUtilities pdfUtils;
	Login login = new Login(driver);
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj = new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();	
	AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve_TestData AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData = new AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve_TestData();
	OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj = new OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj();
	NormalSaleDiamondandPlatinum_Obj NormalSaleDiamondandPlatinumObj =new NormalSaleDiamondandPlatinum_Obj();
	AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj = new AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj();
	GPPSettlementAverageRateSingleSkuMultipleSku_Obj GPPSettlementAverageRateSingleSkuMultipleSkuObj = new GPPSettlementAverageRateSingleSkuMultipleSku_Obj();

	public AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		mathUtils = new MathUtilities(base);
		pdfUtils=new PdfUtilities(base);
	}

	/// <summary>
	/// Test Case Title : SR converted to normal advance
	/// Automation ID : TC67
	/// Author : Jobitha
	/// </summary>
	public void TC67_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception {
		// 1.Login to POS
		// 2.Click on Transaction button
		String InvoiceNo = appUtils.GetInvoiceNoAfterNormalSale(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC67_CustomerNo, AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.SkuList);
		String ActualCustomerName = PdfUtilities.ExtractCustomerNameFromSaleInvoice();

		// 3.Select customer
		// 4.Click on add to sale button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC67_CustomerNo,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// 5.Click on customer adjustment button
		// 6.Click on return transaction button at the billing screen
		// 7.Enter reciept number
		// 8.Select return product
		//Expected:The product meant to be returned should be previously bought by the same customer.
		//Check calculation
		// Note:Billling Screen
		// *No of Product lines
		// *Displayed Item Name
		// *Displayed Quantity
		// *Displayed TOTAL (without Tax)
		// *Displayed Subtotal
		// *TAX
		// *Total Amount
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.ReturnProducts);

		//Validation for product meant to be returned should be previously bought by the same customer.
		String ExpectedCustomerName = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC67_CustomerName;
		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName+"in Billling Screen");

		//Validation Steps : Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax)
		List<String> BillScrnProdNames    = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> BillScrnProdQtys     = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> BillScrnProdTotal    = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));

		List<String> ReturnProdNames      = Details.productNames;
		List<String> ReturnProdQtyList    = Details.productQty;
		List<String> ReturnProdTotalList  = Details.productPrice;

		for (int i = 0; i < BillScrnProdNames.size(); i++) {
			String ExpectedReturnName       = ReturnProdNames.get(i).trim();
			double ExpectedReturnQty        = Double.parseDouble(ReturnProdQtyList.get(i).trim().replace("-", "").replaceAll("[^\\d.]", ""));
			double ExpectedReturnTotalWithoutTax = Double.parseDouble(ReturnProdTotalList.get(i).trim().replaceAll("[^\\d.]", ""));

			String ActualName       = BillScrnProdNames.get(i).trim();
			double ActualQty        = Double.parseDouble(BillScrnProdQtys.get(i).replace("-", "").replaceAll("[^\\d.]", ""));
			double ActualTotalNoTax = Double.parseDouble(BillScrnProdTotal.get(i).replace("-", "").replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ActualName,ExpectedReturnName ,"In Transaction Line Page,The product name does not match the item previously purchased by the customer. " +"Expected: " + ExpectedReturnName + ", but found: " + ActualName+" in Billling Screen");
			asrt.assertEquals(ActualQty,ExpectedReturnQty, 1,"In Transaction Line Page, The quantity does not match the quantity previously purchased by the customer. " +"Expected: " + ExpectedReturnQty + ", but found: " + ActualQty+" in Billling Screen");
			asrt.assertEquals(ActualTotalNoTax,ExpectedReturnTotalWithoutTax, 1,"In Transaction Line Page, The TOTAL (without Tax) does not match the TOTAL (without Tax) previously paid by the customer. " +"Expected: " + ExpectedReturnTotalWithoutTax + ", but found: " + ActualTotalNoTax+" in Billling Screen");
		}
		//Validation Steps : No of Product lines, Displayed SubTotal, TAX, Total amount
		double ReturnSumSubTotal = 0.0;
		for (String TotalAmnt : BillScrnProdTotal) {
			String ReturnTotalAmnt = TotalAmnt.replaceAll("[^\\d.]", "").trim();
			if (!ReturnTotalAmnt.isEmpty()) {
				ReturnSumSubTotal += Double.parseDouble(ReturnTotalAmnt);
			}
		}

		double ReturnTaxAmount        = ReturnSumSubTotal*3/100;
		double ReturnTotalAmount      = ReturnSumSubTotal+ReturnTaxAmount;
		int ActualNoOfProductLines    = BillScrnProdNames.size();     		
		String TransSubTotal          = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("SubtotalField","row pad12 padBottom0","h4"));
		String TransTax               = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TaxField","row pad12 padBottom0","h4"));
		String TransTotalAmnt         = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TotalAmountField","row pad12 padBottom0","h4"));
		String TransNoOfLines         = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("LinesField","row pad12 padBottom0","h4"));
		double TransactionSubTotal    = Double.parseDouble(TransSubTotal.replaceAll("[^\\d.]", "").trim());
		double TransactionTax         = Double.parseDouble(TransTax.replaceAll("[^\\d.]", "").trim());
		double TransactionTotalAmount = Double.parseDouble(TransTotalAmnt.replaceAll("[^\\d.]", "").trim());
		int TransNoOfLinesInt         = Integer.parseInt(TransNoOfLines.replaceAll("[^\\d]", "").trim());

		asrt.assertEquals(TransNoOfLinesInt, ActualNoOfProductLines,"In Transaction Line Page, Mismatch in number of Actual product lines  "+TransNoOfLinesInt+" and Expected "+ActualNoOfProductLines+" product lines in Billing screen");
		asrt.assertEquals(TransactionSubTotal, ReturnSumSubTotal,1,"In Transaction Line Page, Mismatch in Actual subtotal "+TransactionSubTotal+" and Expected "+ReturnSumSubTotal+" sub total in Billing screen");
		asrt.assertEquals(TransactionTax, ReturnTaxAmount,1,"In Transaction Line Page, Mismatch in Actual Tax "+TransactionTax+" and Expected "+ReturnTaxAmount+" Tax in Billing screen");
		asrt.assertEquals(TransactionTotalAmount,ReturnTotalAmount,1,"In Transaction Line Page, Mismatch in Actual total amount "+TransactionTotalAmount+" and Expected "+ReturnTotalAmount+" total amount in Billing screen");

		// 9.Click on return button
		// 10.Click on the Amount
		// 11.Select payment method as convert advance
		// 12.select deposite type as normal
		// 13.Enter deposite amount
		// 14.Select sales person
		// 15. Select due date
		// 16. Enter remarks
		// 17. Add nominee details
		// 17.1 Enter nominee name
		// 17.2 Select nominee relation
		// 17.3 Click on check box(Same as customer address)
		// 17.4 Click on OK button
		// 17. Click on deposit button		
		// 18.	Post the Invoice
		//Expected: Check ADVANCE RECEIPT VOUCHER details
		// *Towards the advance against purchase of approximate value
		// *Fixed Gold rate
		// *Deposit Type
		// *Advance received
		// *Save receipt id for future reference

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

		asrt.assertEquals(PdfAdvanceReceived,TransactionTotalAmount,1, "Mismatch in total amount in billing screen actual "+PdfAdvanceReceived+" and Expected "+TransactionTotalAmount+"in Normal Advance PDF");
		asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,1, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
		asrt.assertEquals(Utility_TestData.DepositType, DepositType, "Mismatch in Deposit Type Actual "+UtilityTestData.DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

		Double WeightValue          = PdfAdvanceReceived/PdfMaxGoldRate;
		BigDecimal CalcWeightValue  = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
		double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),1, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");
		Thread.sleep(5000);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	/// <summary>
	/// Test Case Title : OG Purchase converted to normal advance
	/// Automation ID : TC65
	/// Author :Christy Reji
	/// </summary>
	public void TC65_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception {

		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 2: Click on Transaction button
		Thread.sleep(5000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		// Step 3:Select customer and click on add to sale button
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC65_CustomerNo,UtilityTestData.MenuBarOptions[0]);

		//Step 4:Select OG
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		Thread.sleep(1000);
		base.ClickClearEnter(NormalSaleGoldAndSilverObj.Edt_Name("Search"),UtilityTestData.OGProduct);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_CustomerSearch("Products"));

		//Step 5: Click on OG own and choose purchase
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));

		//Step 6:Select configuration
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC65_Config);

		//Step 7 : Click on add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
		//Note:OGPurchase
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("NPurchase"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("NPurchase"));

		//Step 8:Select QC Person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),UtilityTestData.QCAndSmithPerson);

		//Step 9: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),UtilityTestData.QCAndSmithPerson); 

		//Step 10: Enter piece value
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC65_GrossPieces);

		//Step 11: enter gross weight
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC65_GrossWeight);

		//Step 12: Click on Calculate button
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));

		//Fetching values from Itemdetails page
		String OgNameActual                           = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader")).split("-")[0].trim();;
		Thread.sleep(7000);
		double  TotalAmountInItemDetailsPge           = Double.parseDouble(base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt"),"value"));
		String ActualTotalAmount                      = String.format("%.2f",TotalAmountInItemDetailsPge);

		//Step 13:Click on Add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Step 14: Click on the amount
		//validation
		//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
		List<WebElement> AllProductRows          = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ExpectedLinesCountInBillingPge       = AllProductRows.size();
		int ActualLinesCountInBillingPge         = Integer.parseInt(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("LinesField", "h4")));
		String ExpectItemNameInBillingPge        = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		String ActualGrossWeightInBillingPge     = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5")).replace("-", ""); 
		Thread.sleep(3000);
		String ExpectTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedSubTotalInBillingPge      = ExpectTotalWithoutTaxInBillingPge;
		String ActualSubTotalInBillingPge        = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualTaxInBillingPge             = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge           = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC65_OGTax;
		String ExpectedTotalAmountInBillingPge   = ExpectedSubTotalInBillingPge;
		String ActualTotalAmountInBillingPge     = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedGrossWt                   = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC65_GrossWeight;
		String ExpectedAmountDueFromBillingPge   = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ReceiveAmtFromInBillingPge        = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();

		asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");
		asrt.assertEquals(OgNameActual, ExpectItemNameInBillingPge,"Item name mismatch: "+OgNameActual+" but found "+ExpectItemNameInBillingPge+" in billing page");
		asrt.assertEquals(ExpectedGrossWt, ActualGrossWeightInBillingPge,"Gross weight mismatch: "+ExpectedGrossWt+" but found "+ActualGrossWeightInBillingPge+" in billing page");
		asrt.assertEquals(ActualTotalAmount, ExpectTotalWithoutTaxInBillingPge,"Total without tax mismatch: "+ActualTotalAmount+" but found "+ExpectTotalWithoutTaxInBillingPge+" in billing page");
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");

		//Step 15 : Select payment method as convert advance
		//Step 16:Choose normal advance
		//Step 17: Select sales person
		//Step 18:Select the due date and Enter remarks
		//Step 19: Click on nominee details
		//Step 19,a,b,c,d: Enter nominee name, nominee relation, Click on checkbox(same as customer address)Click on ok button and deposit button
		//Step 20: Click on deposit button
		Thread.sleep(3000);
		appUtils.PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
				UtilityTestData.SalePersonNumber,
				UtilityTestData.SalePersonName,
				UtilityTestData.DueYear,
				UtilityTestData.NomineeName,
				UtilityTestData.NomineeRelation);

		//Warning handling if present
		try {
			WebDriverWait ShortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
			WebElement Warning = ShortWait.until( ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Ele_Payement("Warning")) );
			base.excuteJsClick(LoginPageObj.Btn_SignInButton("OK"));
		} catch (Exception e) {
			// Warning not found
		}

		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		//Step 21: Post the invoice
		// Check ADVANCE RECEIPT VOUCHER details,*Towards the advance against purchase of approximate value
		//  *Fixed Gold rate, Deposit Type ,Advance Received,save receipt id for future reference	
		Map<String, String> Result  = pdfUtils.NormalAdvancePdfValidation();	
		Thread.sleep(3000);
		String AdvanceReceived      = Result.get("AdvanceReceived");
		String FixedGoldRate        = Result.get("FixedGoldRate");
		String MaxGoldRate          = Result.get("MaxGoldRate");
		String ApproxWeight         = Result.get("ApproxWeight");
		String DepositType          = Result.get("DepositType");
		double PdfAdvanceReceived   = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
		double PdfFixedGoldRate     = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
		double PdfMaxGoldRate       = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(PdfAdvanceReceived,Double.parseDouble(ExpectedAmountDueFromBillingPge),1, "Mismatch in total amount in billing screen actual "+PdfAdvanceReceived+" and Expected "+Double.parseDouble(ExpectedAmountDueFromBillingPge)+"in Normal Advance PDF");
		asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,1, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
		asrt.assertEquals(Utility_TestData.DepositType, DepositType, "Mismatch in Deposit Type Actual "+UtilityTestData.DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

		Double WeightValue          = PdfAdvanceReceived/PdfMaxGoldRate;
		BigDecimal CalcWeightValue  = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
		double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),1, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");
		Thread.sleep(5000);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Normal collection for normal advance
	// Automation ID : TC66
	// Author: Vishnu RR
	/// </summary>

	public void TC66_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception {

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer 
		//Step 4.click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_CustomerNo, UtilityTestData.MenuBarOptions[0]);
		Thread.sleep(3000);

		// Step 5. Click on advance button at the transaction screen
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("text semilight primaryFontColor","Advance"));

		// Step 6. Click on advance collection button
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance Collection"));
		Thread.sleep(3000);

		//Step 7.select deposit type as normal
		//Step  8.Enter deposit amount
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_AdvanceAmt);

		// Step  9. Select sales person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson("4"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_SalesPerson);

		// Step  10. Select due date
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_DrpdwnYear("win-datepicker-year win-dropdown win-order2 displayOrder3"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_DueYear);

		// Step  11. Enter remarks
		base.setData(NormalSaleGoldAndSilverObj.Ele_Remarks("6"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_Remarks);

		// Step  12. Add nominee details
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
		Thread.sleep(1000);

		//12.1 Enter nominee name
		//12.2 Select nominee relation
		//12.3 Click on check box(Same as customer address)					
		// 12.4 Click on OK button
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Nominee name"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_Nomineename);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Nominee relation"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_NomineRelation);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		//Step  13. Click on deposit button
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(3000);

		// 14. Click on amount
		// 15. select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_PaymentMethod));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_Cardnumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//16.Post the Invoice	
		//Expected Result: Towards the advance against purchase of approximate value – Fixed Gold rate, Deposit Type, Advance received, Payment mode.
		Thread.sleep(50000);
		Map<String, String> Result = pdfUtils.NormalAdvancePdfValidation();	

		String AdvanceReceived     = Result.get("AdvanceReceived");
		String FixedGoldRate       = Result.get("FixedGoldRate");
		String MaxGoldRate         = Result.get("MaxGoldRate");
		String ApproxWeight        = Result.get("ApproxWeight");
		String DepositType         = Result.get("DepositType");
		double PdfAdvanceReceived  = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
		double PdfFixedGoldRate    = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
		double PdfMaxGoldRate      = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());
		double DepositedAdvanceAmt = Double.parseDouble(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC66_AdvanceAmt.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(PdfAdvanceReceived,DepositedAdvanceAmt,1, "Mismatch in total amount in billing screen actual "+PdfAdvanceReceived+" and Expected "+ DepositedAdvanceAmt +"in Normal Advance PDF");
		asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,1, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
		asrt.assertEquals(Utility_TestData.DepositType, DepositType, "Mismatch in Deposit Type Actual "+UtilityTestData.DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

		Double WeightValue          = PdfAdvanceReceived/PdfMaxGoldRate;
		BigDecimal CalcWeightValue  = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
		double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),1, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");
		Thread.sleep(5000);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	// <summary>
	// Test Case Title : Create customer offer advance with weight booked
	// Automation ID : TC69
	// Author: Chandana Babu
	// </summary>
	public void TC69_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception {	
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		//Step 2: Click on Transaction button
		//base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		//base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		appUtils.HamBurgerButtonClick("iconShop");

		//Step 3: Click on advance-->customer order
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Customer Order"));

		//Step 4: Search customer and click on add to customer order
		appUtils.SearchByCustomerIDForGPP(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_CustomerNo, UtilityTestData.AddToCustomerOrder);

		//Step 5: Search CST and click on the CST_Order
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_Product);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_CustomerSearch("Products"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("dataListLine"));

		//Step 6: Click on "Add to order line"
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to order line"));

		//Step 7: Select the 'Is offer order' checkbox
		base.buttonClick(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_CheckBox("checkbox", "IsofferChkBox"));

		//Step 8: Choose the company and invent location
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col", "viewModel.IsOrderEntry"), UtilityTestData.Company[0]);
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col stretch", "viewModel.IsOrderEntry"), UtilityTestData.Location[0]);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Sales person"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_SalesPerson);

		//Step 9: Select the order line
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		//Step 10: Select the ingredient line
		base.scrollToElement(LoginPageObj.Ele_ErrorMessage("win-itembox"));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		//Step 11: Click on edit ingredient line
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("SimpleProductDetailsView_EditSlDtlappBarCommandCommand"));

		//Step 12: Enter pieces and weight
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: Pieces"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_Pieces);
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: Weight"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_GrossWeight);

		//Step 13: Click ok button
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		double TotalAmount = Double.parseDouble(base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_TotalAmount("Total Amount", "h4 ellipsis cell")));

		//Step 14: Click on willing to pay button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("SimpleProductDetailsView_appWillToPayCommandCommand"));

		//Step 15: Enter the amount
		String WillingToPayAmount = Double.toString(TotalAmount*AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_Percentage);
		Thread.sleep(1000);
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Amount("value: WILLAMT", "number"), WillingToPayAmount);
		Thread.sleep(1000);
		String ExpectedOfferExpiryDate= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: SDATE"));
		String ExpectedValidityDays = base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: SDAYS")).replaceAll("\\D+", "");;

		//Step 16: Click on apply button
		base.buttonClick(LoginPageObj.Btn_SignInButton("APPLY"));

		//Step 17: Click on edit line
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Txt_Sku("win-structuralnodes win-selectionmode"));
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_EditSlappBarCommandCommand"));

		//Step 18: Enter the line delivery date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate ExpiryDate = LocalDate.parse(ExpectedOfferExpiryDate, formatter);
		LocalDate TargetDate = ExpiryDate.minusDays(1);
		String Day = Integer.toString(TargetDate.getDayOfMonth());
		String Month = Integer.toString(TargetDate.getMonthValue() - 1); 
		String Year = Integer.toString(TargetDate.getYear());		
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-date"), Day);
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-month"), Month);
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-year"), Year);

		//Step 19: Enter remarks
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Remarks("value: Remarks"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_Remarks);

		//Step 20: Click on OK button
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		//Step 21: Add nominee details, Select a sales person
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_NomineeDetailsAppBarCommandCommand"));
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeName"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_NomineeName);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: NomineeRelationArr"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_NomineeRelation);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));	
		String Address = base.GetText(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"));
		if(Address == "") {
			base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_NomineeAdresss);
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}
		else {
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}

		//Step 22: Click on save order button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_appBarCommandCommand"));
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("YES"));
		String Message = base.GetText(NormalSaleDiamondandPlatinumObj.Ele_GrossWeight("text: content"));
		String OrderId = Message.split(" - ")[1].split(" ")[0];
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		//Step 23: Go to transactions, Search customer, Click on Add to sale
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_CustomerNo ,UtilityTestData.MenuBarOptions[0]);

		//Step 24: Choose advance-->advance collection
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Advance Collection"));

		//Step 25: Choose type as order
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"), "Order");

		//Step 26: Select the transaction number from the drop down
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OrderId);

		//Step 27: Enter the deposit amount
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),WillingToPayAmount);

		//Step 28: Choose the sales person		
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_SalesPerson);

		//Step 29: Click on deposit button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Step 30: Click on the Amount
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 31: Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 32: Post the Invoice
		double ApproxWeight = Double.parseDouble(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_GrossWeight);
		String ExpectedApproxWeight = String.format("%.3f",ApproxWeight);
		double GoldRate = TotalAmount/ApproxWeight;
		String ExpectedFixedGoldRate = String.format("%.2f",GoldRate);

		Map<String, String> AdvanceReceiptPdf = pdfUtils.OfferAdvancePdfValidation();
		String ActualAdvanceReceived = AdvanceReceiptPdf.get("AdvanceReceived");
		String ActualApproxWeight = AdvanceReceiptPdf.get("ApproxWeight");
		String ActualFixedGoldRate = AdvanceReceiptPdf.get("FixedGoldRate");
		String ActualDepositType = AdvanceReceiptPdf.get("DepositType");
		String ActualValidityDays = AdvanceReceiptPdf.get("ValidityDays");
		String ActualOfferExpiryDate = AdvanceReceiptPdf.get("OfferExpiryDate");
		asrt.assertEquals(ActualAdvanceReceived,ExpectedAdvanceAmount,"Advance Received mismatch: Expected is "+ExpectedAdvanceAmount+" but found "+ActualAdvanceReceived+" in Advance Receipt");
		asrt.assertEquals(ActualApproxWeight,ExpectedApproxWeight,"ApproxWeight mismatch: Expected is "+ExpectedApproxWeight+" but found "+ActualApproxWeight+" in Advance Receipt");
		asrt.assertEquals(ActualFixedGoldRate,ExpectedFixedGoldRate,"Fixed Gold Rate mismatch: Expected is "+ExpectedFixedGoldRate+" but found "+ActualFixedGoldRate+" in Advance Receipt");
		asrt.assertEquals(ActualDepositType,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_DepositType,"Deposit Type mismatch: Expected is "+ AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_DepositType+" but found "+ActualDepositType+" in Advance Receipt");
		asrt.assertEquals(ActualValidityDays,ExpectedValidityDays,"Validity Days mismatch: Expected is "+ExpectedValidityDays+" but found "+ActualValidityDays+" in Advance Receipt");
		asrt.assertEquals(ActualOfferExpiryDate,ExpectedOfferExpiryDate,"Offer Expiry Date mismatch: Expected is "+ExpectedOfferExpiryDate+" but found "+ActualOfferExpiryDate+" in Advance Receipt");		
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	// <summary>
	// Test Case Title : Normal collection against an existing offer advance
	// Automation ID : TC75
	// Author: Jhoncy Joseph
	// </summary>
	public void TC75_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception {	
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2: Click on Transaction button
		Thread.sleep(5000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		//Prerequisite *customer must have an existing parent offer advance
		Map<String, String> OfferDetails = appUtils.CreateCustomerOfferAdvance(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_CustomerPhoneNo,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_Pieces,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_GrossWeight,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_DepositPercent,
				UtilityTestData.Company[0],
				UtilityTestData.Location[0]);
		String OrderId = OfferDetails.get("OrderId");	
		String TotalAmount = OfferDetails.get("AdvanceAmount");	
		double TotalAdvanceAmount = Double.parseDouble(TotalAmount);
		String ExpectedValidityDays = OfferDetails.get("ValidityDays");	
		String ExpectedOfferExpiryDate = OfferDetails.get("OfferExpiryDate");

		//Step 3: Select customer
		//Step 4: Click on add to sale button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_CustomerPhoneNo ,UtilityTestData.MenuBarOptions[0]);

		//Step 5:Click on advance button at the transaction screen
		//Step 6:Click on advance collection button
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Advance Collection"));

		//Step 7: Select deposit type as order
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_Deposit);

		//Step 8: Select the transaction number from the drop down
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OrderId);

		//Step 9: Enter the deposit amount
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"), String.valueOf(Double.parseDouble(TotalAmount) * AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_DepositPercent));

		//Step 10: Choose the sales person		
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_SalesPerson);

		//Step 11: Click on deposit button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Step 12: Click on the Amount
		Thread.sleep(2000);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 13: Select a Payment method(Cash or Card)
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 14: Post the Invoice
		//Expected Result:Check ADVANCE RECEIPT VOUCHER details *Towards the advance against purchase of approximate value*Fixed Gold rate *Deposit Type *Advance Received * Validity* Offer Expriry date
		double ApproxWeight = Double.parseDouble(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_GrossWeight);
		String ExpectedApproxWeight = String.format("%.3f",ApproxWeight);
		double GoldRate = TotalAdvanceAmount/ApproxWeight;
		String ExpectedFixedGoldRate = String.format("%.2f",GoldRate);

		Map<String, String> AdvanceReceiptPdf = pdfUtils.OfferAdvancePdfValidation();
		String ActualAdvanceReceived = AdvanceReceiptPdf.get("AdvanceReceived");
		String ActualApproxWeight = AdvanceReceiptPdf.get("ApproxWeight");
		String ActualFixedGoldRate = AdvanceReceiptPdf.get("FixedGoldRate");
		String ActualDepositType = AdvanceReceiptPdf.get("DepositType");
		String ActualValidityDays = AdvanceReceiptPdf.get("ValidityDays");
		String ActualOfferExpiryDate = AdvanceReceiptPdf.get("OfferExpiryDate");
		asrt.assertEquals(ActualAdvanceReceived,ExpectedAdvanceAmount,"Advance Received mismatch: Expected is "+ExpectedAdvanceAmount+" but found "+ActualAdvanceReceived+" in Advance Receipt");
		asrt.assertEquals(ActualApproxWeight,ExpectedApproxWeight,"ApproxWeight mismatch: Expected is "+ExpectedApproxWeight+" but found "+ActualApproxWeight+" in Advance Receipt");
		asrt.assertEquals(ActualFixedGoldRate,ExpectedFixedGoldRate,"Fixed Gold Rate mismatch: Expected is "+ExpectedFixedGoldRate+" but found "+ActualFixedGoldRate+" in Advance Receipt");
		asrt.assertEquals(ActualDepositType,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_DepositType,"Deposit Type mismatch: Expected is "+ AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_DepositType+" but found "+ActualDepositType+" in Advance Receipt");
		asrt.assertEquals(ActualValidityDays,ExpectedValidityDays,"Validity Days mismatch: Expected is "+ExpectedValidityDays+" but found "+ActualValidityDays+" in Advance Receipt");
		asrt.assertEquals(ActualOfferExpiryDate,ExpectedOfferExpiryDate,"Offer Expiry Date mismatch: Expected is "+ExpectedOfferExpiryDate+" but found "+ActualOfferExpiryDate+" in Advance Receipt");		
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}


	// <summary>
	// Test Case Title : OG purchase convert to existing offer advance
	// Automation ID : TC76
	// Author: Aiswarya
	// </summary>

	public void TC76_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception
	{

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Precondition:Customer must have an offer advance
		// Click on Transaction button
		appUtils.HamBurgerButtonClick("iconShop");

		Map<String, String> OfferDetails = appUtils.CreateCustomerOfferAdvance(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_CustomerID,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_PiecesByPurity,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_GrossWeight,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_WillingToPayPercentage,
				UtilityTestData.Company[0],
				UtilityTestData.Location[0]);

		String OfferAdvanceId              = OfferDetails.get("OrderId");
		String ExpectedOfferExpiryDate     = OfferDetails.get("OfferExpiryDate");
		String WillingToPayAmount          = OfferDetails.get("WillingToPayAmount");
		double ExpectedAdvanceAmount       = Double.parseDouble(OfferDetails.get("AdvanceAmount"));
		double ExpectedFixedGoldRate       = Double.parseDouble(OfferDetails.get("FixedGoldRate"));
		int    ExpectedInitialValidityDays = Integer.parseInt(OfferDetails.get("ValidityDays").toString());
		String ExpectedDepositType         = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_DepositType;
		double ExpectedGrossWeight         = Double.parseDouble(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_GrossWeight);

		//Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_CustomerID,UtilityTestData.MenuBarOptions[0]);

		//Step 4: Select OG
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),"OG");
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Products"));
		//Step 5: Click on OG Own
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));

		//Step 6: Select configuration(purity 22K)		
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_Purity);

		//Step 7: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_QCPerson);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_SmithPerson);
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_PiecesByPurity);
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_GrossWeightByPurity);
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(10000);

		double CurrentGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));
		// Choose Purchase
		base.ClickCondition(NormalSaleGoldAndSilverObj.Ele_Name("NPurchase"));
		// Step 8: Select QC person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_QCPerson);

		// Step 9: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_SmithPerson);

		// Step 10: Enter piece value
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_PiecesByPurity);

		// Step 11: Enter gross weight
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_GrossWeightByPurity);

		// Step 12: Click on calculate button
		//Expected:Check Purchase rate own Gold, Check calculation 
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(2000);
		double ActualOGPurchaseRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));
		double ExpectedOGPurchaseRateInItemDetailsPge = Math.round((CurrentGoldRateInItemDetailsPge * 0.98) * 100.0) / 100.0;

		asrt.assertEquals(ActualOGPurchaseRateInItemDetailsPge,ExpectedOGPurchaseRateInItemDetailsPge, 1.0,"OGPurchaseRate mismatch: Expected value is " + ExpectedOGPurchaseRateInItemDetailsPge + " but got " + ActualOGPurchaseRateInItemDetailsPge + " in item details page");

		double ExpectedNetWeightInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetwt")));
		double ExpectedAmountInItemDetailsPge    = Math.round(ExpectedNetWeightInItemDetailsPge * ActualOGPurchaseRateInItemDetailsPge * 100.0) / 100.0;
		double ActualAmountInItemDetailsPge      = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetamt")));

		asrt.assertEquals(ActualAmountInItemDetailsPge,ExpectedAmountInItemDetailsPge, 1.0,"Amount mismatch: Expected value is" + ExpectedAmountInItemDetailsPge + " but got " + ActualAmountInItemDetailsPge + " in item details page");

		double ExpectedGrossWeightInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp")));
		double ActualTotalAmountInItemDetailsPge =  Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt")));

		asrt.assertEquals(ActualTotalAmountInItemDetailsPge, ExpectedAmountInItemDetailsPge,1.0,"Total amount mismatch: " + ExpectedAmountInItemDetailsPge + " but got " + ActualTotalAmountInItemDetailsPge + " in item details page");

		String ItemName = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = ItemName.substring(ItemName.indexOf("-") + 2);

		// Step 13: Click on add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		//Validation for Billing Screen, No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal, TAX, Total Amount
		List<WebElement> AllProductRows    = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ExpectedLinesCountInBillingPge = AllProductRows.size();
		int ActualLinesCountInBillingPge   = Integer.parseInt(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("LinesField", "h4")));

		asrt.assertEquals(ActualLinesCountInBillingPge, ExpectedLinesCountInBillingPge,"Lines count mismatch: Expected is " + ExpectedLinesCountInBillingPge + " but got " + ActualLinesCountInBillingPge + " in Billing page");

		String ActualItemNameInBillingPge  = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));

		asrt.assertEquals(ActualItemNameInBillingPge, ExpectedItemNameInItemDetailsPge, "Item name mismatch: " + ExpectedItemNameInItemDetailsPge + " but found " + ActualItemNameInBillingPge + " in billing page");

		double ActualGrossWeightInBillingPge = Double.parseDouble( base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5")).replace("-", "").trim());

		asrt.assertEquals(ActualGrossWeightInBillingPge, ExpectedGrossWeightInItemDetailsPge,"Gross weight mismatch: " + ExpectedGrossWeightInItemDetailsPge + " but found " + ActualGrossWeightInBillingPge + " in billing page");

		double ActualTotalWithoutTaxInBillingPge = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5")).replace("-", "").replace("₹", "").replace(",", "").trim());

		asrt.assertEquals(ActualTotalWithoutTaxInBillingPge, ExpectedAmountInItemDetailsPge,"Total without tax mismatch: " + ExpectedAmountInItemDetailsPge + " but found " + ActualTotalWithoutTaxInBillingPge + " in billing page");

		double ExpectedSubTotalInBillingPge = ActualTotalWithoutTaxInBillingPge;
		double ActualSubTotalInBillingPge   = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim());

		asrt.assertEquals(ActualSubTotalInBillingPge, ExpectedSubTotalInBillingPge,"Subtotal mismatch: " + ExpectedSubTotalInBillingPge + " but found " + ActualSubTotalInBillingPge + " in billing page");

		String ActualTaxInBillingPge   = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_OGTax;

		asrt.assertEquals(ActualTaxInBillingPge,ExpectedTaxInBillingPge,"Tax amount mismatch: " + ExpectedTaxInBillingPge + " but found " + ActualTaxInBillingPge + " in billing page");

		double ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
		double ActualTotalAmountInBillingPge   = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim());

		asrt.assertEquals(ActualTotalAmountInBillingPge,ExpectedTotalAmountInBillingPge,"Total Amount mismatch: " + ExpectedTotalAmountInBillingPge + " but found " + ActualTotalAmountInBillingPge + " in billing page");

		//Step 14: Click on the Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));

		//Step 15: Select converted to advance button
		base.buttonClick(LoginPageObj.Edt_AlertText("Convert to Advance"));

		//Step 16: Choose deposit type as order 
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_DepositType);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OfferAdvanceId);
		Thread.sleep(3000); 

		//Step 17: Select sales agent
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson("3"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC76_SalesPerson);
		Thread.sleep(3000);
		//Step 18: Click on deposit button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		Thread.sleep(5000);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT ORDER ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		//Step 19: Post Invoice
		// Expected:  Check ADVANCE RECEIPT VOUCHER details Towards the advance against purchase of approximate value,Fixed Gold rate,Deposit Type,Advance Received,Validity,Offer Expriry date,save receipt id for future reference
		Map<String, String> AdvanceReceiptDetailsFromPdf   = pdfUtils.OfferAdvancePdfValidation();

		String ActualValidityInOfferAdvanceReceiptPDF          = AdvanceReceiptDetailsFromPdf.get("Validity");
		String ActualReceiptIDInOfferAdvanceReceiptPDF         = AdvanceReceiptDetailsFromPdf.get("ReceiptId");
		String ActualDepositTypeInOfferAdvanceReceiptPDF       = AdvanceReceiptDetailsFromPdf.get("DepositType");
		String ActualOfferExpiryDateInOfferAdvanceReceiptPDF   = AdvanceReceiptDetailsFromPdf.get("OfferExpiryDate");
		int    ActualValidityDaysInOfferAdvanceReceiptPDF      = Integer.parseInt(AdvanceReceiptDetailsFromPdf.get("ValidityDays"));
		double ActualApproxWeightInOfferAdvanceReceiptPDF      = Double.parseDouble(AdvanceReceiptDetailsFromPdf.get("ApproxWeight"));
		double ActualFixedGoldRateInOfferAdvanceReceiptPDF     = Double.parseDouble(AdvanceReceiptDetailsFromPdf.get("FixedGoldRate"));
		double ActualAdvanceReceivedInOfferAdvanceReceiptPDF   = Double.parseDouble(AdvanceReceiptDetailsFromPdf.get("AdvanceReceived"));

		String ActualDepositType                           = ActualDepositTypeInOfferAdvanceReceiptPDF.replaceAll("(?i)order\\s*advance", "Order");
		double PercentUsed                                 = (ActualAdvanceReceivedInOfferAdvanceReceiptPDF / ExpectedAdvanceAmount) * 100;
		int    ExpectedValidityDays;
		if (PercentUsed >= 3 && PercentUsed <= 4.99)        ExpectedValidityDays = 15;
		else if (PercentUsed >= 5 && PercentUsed <= 9.99)   ExpectedValidityDays = 30;
		else if (PercentUsed >= 10 && PercentUsed <= 19.99) ExpectedValidityDays = 60;
		else if (PercentUsed >= 20 && PercentUsed <= 34.99) ExpectedValidityDays = 90;
		else if (PercentUsed >= 35 && PercentUsed <= 49.99) ExpectedValidityDays = 120;
		else if (PercentUsed >= 50 && PercentUsed <= 74.99) ExpectedValidityDays = 180;
		else if (PercentUsed >= 75 && PercentUsed <= 99.99) ExpectedValidityDays = 330;
		else if (PercentUsed >= 100)                        ExpectedValidityDays = 345;
		else ExpectedValidityDays = 0;
		DateTimeFormatter DateFormatter   = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate InitialExpiryDate       = LocalDate.parse(ExpectedOfferExpiryDate, DateFormatter);
		LocalDate UpdatedExpiryDate       = InitialExpiryDate.plusDays(ExpectedValidityDays-ExpectedInitialValidityDays);
		String    ExpectedExpiryDate      = UpdatedExpiryDate.format(DateFormatter); 

		Assert.assertEquals(ActualDepositType,ExpectedDepositType, "Mismatch in Deposit Type: expected '" + ExpectedDepositType + "' but got '" + ActualDepositType + "' in the  ADVANCE RECEIPT PDF.");
		Assert.assertEquals(ActualValidityDaysInOfferAdvanceReceiptPDF,ExpectedValidityDays, "Mismatch in Validity Days: expected '" + ExpectedValidityDays + "' but got '" + ActualValidityDaysInOfferAdvanceReceiptPDF + "' in the  ADVANCE RECEIPT PDF.");
		Assert.assertEquals(ActualApproxWeightInOfferAdvanceReceiptPDF,ExpectedGrossWeight , "Mismatch in Approximate Weight: expected '" + ExpectedGrossWeight + "' but got '" + ActualApproxWeightInOfferAdvanceReceiptPDF + "' in the  ADVANCE RECEIPT PDF.");
		Assert.assertEquals(ActualFixedGoldRateInOfferAdvanceReceiptPDF,ExpectedFixedGoldRate, "Mismatch in Fixed Gold Rate: expected '" + ExpectedFixedGoldRate + "' but got '" + ActualFixedGoldRateInOfferAdvanceReceiptPDF + "' in the  ADVANCE RECEIPT PDF.");
		Assert.assertEquals(ActualOfferExpiryDateInOfferAdvanceReceiptPDF,ExpectedExpiryDate, "Mismatch in Offer Expiry Date: expected '" + ExpectedExpiryDate + "' but got '" + ActualOfferExpiryDateInOfferAdvanceReceiptPDF + "' in the  ADVANCE RECEIPT PDF.");
		Assert.assertEquals(ActualAdvanceReceivedInOfferAdvanceReceiptPDF,ActualTotalAmountInBillingPge, "Mismatch in Advance Received: expected '" + ActualTotalAmountInBillingPge + "' but got '" + ActualAdvanceReceivedInOfferAdvanceReceiptPDF + "' in the  ADVANCE RECEIPT PDF.");
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

	}

	// <summary>
	// Test Case Title : Normal collection against an add on advance
	// Automation ID : TC77
	// Author: Aiswarya
	// </summary>

	public void TC77_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception
	{

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2: Click on Transaction button
		appUtils.HamBurgerButtonClick("iconShop");

		//Precondition:Customer must have an existing parent offer advance
		Map<String, String> OfferDetails = appUtils.CreateCustomerOfferAdvance(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC77_CustomerID,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC77_Pieces,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC77_GrossWeight,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC77_DepositPercent,
				UtilityTestData.Company[0],
				UtilityTestData.Location[0]);

		String OfferAdvanceId              = OfferDetails.get("OrderId");
		String ExpectedOfferExpiryDate     = OfferDetails.get("OfferExpiryDate");
		double ExpectedTotalAdvanceAmount  = Double.parseDouble(OfferDetails.get("AdvanceAmount"));
		double ExpectedFixedGoldRate       = Double.parseDouble(OfferDetails.get("FixedGoldRate"));
		int    ExpectedInitialValidityDays = Integer.parseInt(OfferDetails.get("ValidityDays").toString());
		String ExpectedDepositType         = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC77_DepositType;
		double ExpectedApproxWeight        = Double.parseDouble(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC77_GrossWeight);

		Thread.sleep(1000);
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC77_CustomerID, UtilityTestData.MenuBarOptions[0]);
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Advance Collection"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_Deposit);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OfferAdvanceId);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"), String.valueOf(ExpectedTotalAdvanceAmount * AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_DepositPercent));	
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_SalesPerson);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(2000);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		Map<String, String> InitialAdvanceReceiptPdf = pdfUtils.OfferAdvancePdfValidation();
		double ActualInitialAdvanceReceived = Double.parseDouble(InitialAdvanceReceiptPdf.get("AdvanceReceived"));

		//Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC77_CustomerID,UtilityTestData.MenuBarOptions[0]);

		//Step 4: Select advance
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		//Step 5: Click on advance collection
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Advance Collection"));

		//Step 7: Select deposit type as order
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_Deposit);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OfferAdvanceId);

		//Step 8: Enter the deposit amount
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"), String.valueOf(ExpectedTotalAdvanceAmount * AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_DepositPercent));	
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_SalesPerson);

		//Step 9: Click on deposit button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Step 10: Click on the Amount
		Thread.sleep(2000);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 11: Select a Payment method(Cash or Card)
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		double ExpectedAdvanceAmount = Double.parseDouble(base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", ""));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 12: Post the Invoice
		//Expected Result:Check ADVANCE RECEIPT VOUCHER details *Towards the advance against purchase of approximate value*Fixed Gold rate *Deposit Type *Advance Received * Validity* Offer Expriry date
		Map<String, String> AdvanceReceiptPdf = pdfUtils.OfferAdvancePdfValidation();
		double ActualAdvanceReceived      = Double.parseDouble(AdvanceReceiptPdf.get("AdvanceReceived"));
		double ActualApproxWeight         = Double.parseDouble(AdvanceReceiptPdf.get("ApproxWeight"));
		double ActualFixedGoldRate        = Double.parseDouble(AdvanceReceiptPdf.get("FixedGoldRate"));
		String ActualDepositType          = AdvanceReceiptPdf.get("DepositType");
		int    ActualValidityDays         = Integer.parseInt(AdvanceReceiptPdf.get("ValidityDays").toString());
		String ActualOfferExpiryDate      = AdvanceReceiptPdf.get("OfferExpiryDate");
		double PercentUsed                = ((ActualAdvanceReceived + ActualInitialAdvanceReceived) / ExpectedTotalAdvanceAmount) * 100;
		int    ExpectedValidityDays;
		if (PercentUsed >= 3 && PercentUsed <= 4.99)        ExpectedValidityDays = 15;
		else if (PercentUsed >= 5 && PercentUsed <= 9.99)   ExpectedValidityDays = 30;
		else if (PercentUsed >= 10 && PercentUsed <= 19.99) ExpectedValidityDays = 60;
		else if (PercentUsed >= 20 && PercentUsed <= 34.99) ExpectedValidityDays = 90;
		else if (PercentUsed >= 35 && PercentUsed <= 49.99) ExpectedValidityDays = 120;
		else if (PercentUsed >= 50 && PercentUsed <= 74.99) ExpectedValidityDays = 180;
		else if (PercentUsed >= 75 && PercentUsed <= 99.99) ExpectedValidityDays = 330;
		else if (PercentUsed >= 100)                        ExpectedValidityDays = 345;
		else ExpectedValidityDays = 0;

		DateTimeFormatter DateFormatter   = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate InitialExpiryDate       = LocalDate.parse(ExpectedOfferExpiryDate, DateFormatter);
		LocalDate UpdatedExpiryDate       = InitialExpiryDate.plusDays(ExpectedValidityDays-ExpectedInitialValidityDays);
		String    ExpectedExpiryDate      = UpdatedExpiryDate.format(DateFormatter); 

		asrt.assertEquals(ActualAdvanceReceived,ExpectedAdvanceAmount,"Advance Received mismatch: Expected is "+ExpectedAdvanceAmount+" but found "+ActualAdvanceReceived+" in Advance Receipt");
		asrt.assertEquals(ActualApproxWeight,ExpectedApproxWeight,"ApproxWeight mismatch: Expected is "+ExpectedApproxWeight+" but found "+ActualApproxWeight+" in Advance Receipt");
		asrt.assertEquals(ActualFixedGoldRate,ExpectedFixedGoldRate,"Fixed Gold Rate mismatch: Expected is "+ExpectedFixedGoldRate+" but found "+ActualFixedGoldRate+" in Advance Receipt");
		asrt.assertEquals(ActualDepositType,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_DepositType,"Deposit Type mismatch: Expected is "+ AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC75_DepositType+" but found "+ActualDepositType+" in Advance Receipt");
		asrt.assertEquals(ActualValidityDays,ExpectedValidityDays,"Validity Days mismatch: Expected is "+ExpectedValidityDays+" but found "+ActualValidityDays+" in Advance Receipt");
		asrt.assertEquals(ActualOfferExpiryDate,ExpectedExpiryDate,"Offer Expiry Date mismatch: Expected is "+ExpectedOfferExpiryDate+" but found "+ExpectedExpiryDate+" in Advance Receipt");		
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Create customer offer advance with SKU reserve [Verify while nominee detail getting removed if we update any other details after updating the nominee details.]
	// Automation ID : TC68
	// Author: Jhoncy Joseph
	// </summary>
	public void TC68_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception {	

		//Step 1.Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2: Click on Transaction button
		Thread.sleep(5000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		//Select SKU Number
		List<String> GoldSku = appUtils.SelectMultipleSKUs(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_SKUCount,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_TranferType,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_FromCounterGold,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_MetalTypeGold);

		List<String> SkuList=new ArrayList<>();
		SkuList.addAll(GoldSku);
		String SkuNumber = SkuList.get(0);	
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
		base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), SkuNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
		base.setZoom(driver, 40);
		double ExpectedTotalCValueInItemDetailsPage = Double.parseDouble(base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("valueInput"), "value"));
		double ExpectedGrossWeightInItemDetailspage =  Double.parseDouble(base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightInput"), "value"));		
		double ExpectedTotalAmountInItemDetailPage = Double.parseDouble(base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("totalInput"), "value"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));	

		//Step 3: Click on advance
		//Step 4: Select customer order
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Customer Order"));

		//Step 5: Search customer and click on add to customer order
		Thread.sleep(3000);
		appUtils.SearchByCustomerIDForGPP(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_CustomerPhoneNo, UtilityTestData.AddToCustomerOrder);

		//Step 6: Enter the SKU number in the field and click enter
		//Expected Result: Verify the product details *Gross wt *CValue *Total amount
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_ScanSku("AddSKUAutomatic "), SkuNumber);
		base.pressKey(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_ScanSku("AddSKUAutomatic "), "ENTER");
		Thread.sleep(2000);
		double ActualGrossWeightInCustomerOrderPage = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column3", "h4 ellipsis cell")));
		double ActualCValueInCustomerOrderPage = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column4", "h4 ellipsis cell")));
		double TotalInCustomerOrderPage = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column7", "h4 ellipsis cell")));
		double ActualTotalAmountInCustomerOrderPage = Double.parseDouble(base.GetValue(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Ele_GPPCustAccnt("TotAmtInput")));
		asrt.assertEquals(ExpectedGrossWeightInItemDetailspage,ActualGrossWeightInCustomerOrderPage,"Gross weight mismatch: Expected is "+ExpectedGrossWeightInItemDetailspage+" but found "+ActualGrossWeightInCustomerOrderPage+" in customer order page");
		asrt.assertEquals(ExpectedTotalCValueInItemDetailsPage,ActualCValueInCustomerOrderPage,1.0,"CValue mismatch: Expected is "+ExpectedTotalCValueInItemDetailsPage+" but found "+ActualCValueInCustomerOrderPage+" in customer order page");
		asrt.assertEquals(ExpectedTotalAmountInItemDetailPage,ActualTotalAmountInCustomerOrderPage,1.0,"Gross weight mismatch: Expected is "+ExpectedTotalAmountInItemDetailPage+" but found "+ActualTotalAmountInCustomerOrderPage+" in customer order page");

		//Verify while nominee detail getting removed if we update any other details after updating the nominee details.
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_NomineeDetailsAppBarCommandCommand"));
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeName"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_NomineeName);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: NomineeRelationArr"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_NomineeRelation);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));	
		String Address = base.GetText(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"));
		if(Address == "") {
			base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_NomineeAdresss);
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}
		else {
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}			

		//Step 7: Select the 'Is offer order' checkbox
		base.buttonClick(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_CheckBox("checkbox", "IsofferChkBox"));

		//Add nominee details
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_NomineeDetailsAppBarCommandCommand"));
		Thread.sleep(1000);
		WebElement NomineeNameField = driver.findElement(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Ele_GPPCustAccnt("NmInput"));
		String NomineeName = NomineeNameField.getAttribute("value");
		asrt.assertTrue(NomineeName.isEmpty(),"Nominee details are not removed if we update any other details in customer order page after updating the nominee details.");	
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("secondaryButton"));

		//Step 8: Choose the company and invent location
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col", "viewModel.IsOrderEntry"), UtilityTestData.Company[0]);
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col stretch", "viewModel.IsOrderEntry"), UtilityTestData.Location[0]);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Sales person"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_SalesPerson);

		//Step 9: Select the order line
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		//Step 10: Select the ingredient line
		base.scrollToElement(LoginPageObj.Ele_ErrorMessage("win-itembox"));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		//Step 11: Click on willing to pay button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions("MGDRetail_MGDRetail_Extensions_OrderView_appWillToPayCommandCommand"));

		//Step 12: Enter the amount
		String WillingToPayAmount = Double.toString(ActualTotalAmountInCustomerOrderPage*AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_DepositPercent);
		Thread.sleep(1000);
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Amount("value: WILLAMT", "number"), WillingToPayAmount);
		Thread.sleep(1000);
		String ExpectedOfferExpiryDate= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: SDATE"));
		String ExpectedValidityDays = base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: SDAYS")).replaceAll("\\D+", "");;

		//Step 13: Click on apply button
		base.buttonClick(LoginPageObj.Btn_SignInButton("APPLY"));

		//Step 14: Click on edit line
		Thread.sleep(3000);
		base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Txt_Sku("win-structuralnodes win-selectionmode"));
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions("MGDRetail_MGDRetail_Extensions_OrderView_EditSlappBarCommandCommand"));

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
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Remarks("value: Remarks"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_Remarks);

		//Step 17: Click on OK button
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		//Step 18: Add nominee details, Select a sales person
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_NomineeDetailsAppBarCommandCommand"));
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeName"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_NomineeName);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: NomineeRelationArr"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_NomineeRelation);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));	
		String Address1 = base.GetText(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"));
		if(Address1 == "") {
			base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_NomineeAdresss);
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

		//Step 20: Go to transactions
		//Step 21: Search customer
		//Step 22: Click on add to sale button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_CustomerPhoneNo ,UtilityTestData.MenuBarOptions[0]);

		//Step 23: Choose advance-->advance collection
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Advance Collection"));

		//Step 24: Choose type as order
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"), "Order");

		//Step 25: Select the transaction number from the drop down
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OrderId);

		//Step 26: Enter the deposit amount
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),WillingToPayAmount);	
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_SalesPerson);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Step 27: Click on the Amount
		Thread.sleep(3000);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 28: Post the Invoice
		//Expected Result:Check ADVANCE RECEIPT VOUCHER details *Towards the advance against purchase of approximate value*Fixed Gold rate *Deposit Type *Advance Received * Validity* Offer Expriry date
		double ApproxWeight = ActualGrossWeightInCustomerOrderPage;
		String ExpectedApproxWeight = String.format("%.3f",ApproxWeight);
		double GoldRate = TotalInCustomerOrderPage/ApproxWeight;
		String ExpectedFixedGoldRate = String.format("%.2f",GoldRate);

		Map<String, String> AdvanceReceiptPdf = pdfUtils.OfferAdvancePdfValidation();
		String ActualAdvanceReceived = AdvanceReceiptPdf.get("AdvanceReceived");
		String ActualApproxWeight = AdvanceReceiptPdf.get("ApproxWeight");
		String ActualFixedGoldRate = AdvanceReceiptPdf.get("FixedGoldRate");
		String ActualDepositType = AdvanceReceiptPdf.get("DepositType");
		String ActualValidityDays = AdvanceReceiptPdf.get("ValidityDays");
		String ActualOfferExpiryDate = AdvanceReceiptPdf.get("OfferExpiryDate");
		asrt.assertEquals(ActualAdvanceReceived,ExpectedAdvanceAmount,"Advance Received mismatch: Expected is "+ExpectedAdvanceAmount+" but found "+ActualAdvanceReceived+" in Advance Receipt");
		asrt.assertEquals(ActualApproxWeight,ExpectedApproxWeight,"ApproxWeight mismatch: Expected is "+ExpectedApproxWeight+" but found "+ActualApproxWeight+" in Advance Receipt");
		asrt.assertEquals(ActualFixedGoldRate,ExpectedFixedGoldRate,"Fixed Gold Rate mismatch: Expected is "+ExpectedFixedGoldRate+" but found "+ActualFixedGoldRate+" in Advance Receipt");
		asrt.assertEquals(ActualDepositType,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_DepositType,"Deposit Type mismatch: Expected is "+ AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC68_DepositType+" but found "+ActualDepositType+" in Advance Receipt");
		asrt.assertEquals(ActualValidityDays,ExpectedValidityDays,"Validity Days mismatch: Expected is "+ExpectedValidityDays+" but found "+ActualValidityDays+" in Advance Receipt");
		asrt.assertEquals(ActualOfferExpiryDate,ExpectedOfferExpiryDate,"Offer Expiry Date mismatch: Expected is "+ExpectedOfferExpiryDate+" but found "+ActualOfferExpiryDate+" in Advance Receipt");		
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	public void TC72_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception{

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Precondition:Customer must have an offer advance
		// Click on Transaction button
		appUtils.HamBurgerButtonClick("iconShop");

		Map<String, String> OfferDetails = appUtils.CreateCustomerOfferAdvance(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_CustomerID,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_PiecesByPurity,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_GrossWeight,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_WillingToPayPercentage,
				UtilityTestData.Company[0],
				UtilityTestData.Location[0]);

		String OfferAdvanceId              = OfferDetails.get("OrderId");
		String ExpectedOfferExpiryDate     = OfferDetails.get("OfferExpiryDate");
		String WillingToPayAmount          = OfferDetails.get("WillingToPayAmount");
		double ExpectedAdvanceAmount       = Double.parseDouble(OfferDetails.get("AdvanceAmount"));
		double ExpectedFixedGoldRate       = Double.parseDouble(OfferDetails.get("FixedGoldRate"));
		int    ExpectedInitialValidityDays = Integer.parseInt(OfferDetails.get("ValidityDays").toString());
		String ExpectedDepositType         = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_Deposit;
		double ExpectedGrossWeight         = Double.parseDouble(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_GrossWeight);

		appUtils.HamBurgerButtonClick("iconShop");
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_CustomerNo ,UtilityTestData.MenuBarOptions[0]);

		//Click on advance button at the transaction screen
		//Click on advance collection button
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Advance Collection"));

		//Select deposit type as order
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_Deposit);

		//Select the transaction number from the drop down
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OfferAdvanceId);

		//Enter the deposit amount
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"), String.valueOf(ExpectedAdvanceAmount * AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_DepositPercent));

		//Choose the sales person		
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_SalesPerson);

		// Click on deposit button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Click on the Amount
		Thread.sleep(2000);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Select a Payment method(Cash or Card)
		//Note-payment step is not proceeded
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		//				String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		Map<String, String> IntialAdvanceReceiptPdf   = pdfUtils.OfferAdvancePdfValidation();
		double ActualIntialAdvanceReceivedPDF   = Double.parseDouble(IntialAdvanceReceiptPdf.get("AdvanceReceived"));

		//3. Select customer and click on add to sale button

		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_CustomerID,UtilityTestData.MenuBarOptions[0]);

		//Step 4: Select OG
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),"OG");
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Products"));

		//Step 5: Click on OG Own and  Choose Purchase
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));

		//Step 6: Select configuration(purity 22K)		
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_Purity);

		//Step 7: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_QCPerson);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_SmithPerson);
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_PiecesByPurity);
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_GrossWeightByPurity);
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(10000);

		double CurrentGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));

		// Choose Purchase
		base.ClickCondition(NormalSaleGoldAndSilverObj.Ele_Name("NPurchase"));
		// Step 8: Select QC person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_QCPerson);

		// Step 9: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_SmithPerson);

		// Step 10: Enter piece value
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_PiecesByPurity);

		// Step 11: Enter gross weight
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_GrossWeightByPurity);

		//Step 12:Enter Stone pieces, Weight and rate[Not Mandatory]

		// Step 13: Click on calculate button
		//Expected:Check Purchase rate own Gold, Check calculation 
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(2000);
		double ActualOGPurchaseRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));
		double ExpectedOGPurchaseRateInItemDetailsPge = Math.round((CurrentGoldRateInItemDetailsPge * 0.98) * 100.0) / 100.0;

		asrt.assertEquals(ActualOGPurchaseRateInItemDetailsPge,ExpectedOGPurchaseRateInItemDetailsPge, 1.0,"OGPurchaseRate mismatch: Expected value is " + ExpectedOGPurchaseRateInItemDetailsPge + " but got " + ActualOGPurchaseRateInItemDetailsPge + " in item details page");

		double ExpectedNetWeightInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetwt")));
		double ExpectedAmountInItemDetailsPge    = Math.round(ExpectedNetWeightInItemDetailsPge * ActualOGPurchaseRateInItemDetailsPge * 100.0) / 100.0;
		double ActualAmountInItemDetailsPge      = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetamt")));

		asrt.assertEquals(ActualAmountInItemDetailsPge,ExpectedAmountInItemDetailsPge, 1.0,"Amount mismatch: Expected value is" + ExpectedAmountInItemDetailsPge + " but got " + ActualAmountInItemDetailsPge + " in item details page");

		double ExpectedGrossWeightInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp")));
		double ActualTotalAmountInItemDetailsPge =  Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt")));

		asrt.assertEquals(ActualTotalAmountInItemDetailsPge, ExpectedAmountInItemDetailsPge,1.0,"Total amount mismatch: " + ExpectedAmountInItemDetailsPge + " but got " + ActualTotalAmountInItemDetailsPge + " in item details page");

		String ItemName = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = ItemName.substring(ItemName.indexOf("-") + 2);

		// Step 14: Click on add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Validation for Billing Screen, No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal, TAX, Total Amount
		List<WebElement> AllProductRows    = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ExpectedLinesCountInBillingPge = AllProductRows.size();
		int ActualLinesCountInBillingPge   = Integer.parseInt(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("LinesField", "h4")));

		asrt.assertEquals(ActualLinesCountInBillingPge, ExpectedLinesCountInBillingPge,"Lines count mismatch: Expected is " + ExpectedLinesCountInBillingPge + " but got " + ActualLinesCountInBillingPge + " in Billing page");

		String ActualItemNameInBillingPge  = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));

		asrt.assertEquals(ActualItemNameInBillingPge, ExpectedItemNameInItemDetailsPge, "Item name mismatch: " + ExpectedItemNameInItemDetailsPge + " but found " + ActualItemNameInBillingPge + " in billing page");

		double ActualGrossWeightInBillingPge = Double.parseDouble( base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5")).replace("-", "").trim());

		asrt.assertEquals(ActualGrossWeightInBillingPge, ExpectedGrossWeightInItemDetailsPge,"Gross weight mismatch: " + ExpectedGrossWeightInItemDetailsPge + " but found " + ActualGrossWeightInBillingPge + " in billing page");

		double ActualTotalWithoutTaxInBillingPge = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5")).replace("-", "").replace("₹", "").replace(",", "").trim());

		asrt.assertEquals(ActualTotalWithoutTaxInBillingPge, ExpectedAmountInItemDetailsPge,"Total without tax mismatch: " + ExpectedAmountInItemDetailsPge + " but found " + ActualTotalWithoutTaxInBillingPge + " in billing page");

		double ExpectedSubTotalInBillingPge = ActualTotalWithoutTaxInBillingPge;
		double ActualSubTotalInBillingPge   = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim());

		asrt.assertEquals(ActualSubTotalInBillingPge, ExpectedSubTotalInBillingPge,"Subtotal mismatch: " + ExpectedSubTotalInBillingPge + " but found " + ActualSubTotalInBillingPge + " in billing page");

		String ActualTaxInBillingPge   = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_OGTax;

		asrt.assertEquals(ActualTaxInBillingPge,ExpectedTaxInBillingPge,"Tax amount mismatch: " + ExpectedTaxInBillingPge + " but found " + ActualTaxInBillingPge + " in billing page");

		double ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
		double ActualTotalAmountInBillingPge   = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim());

		asrt.assertEquals(ActualTotalAmountInBillingPge,ExpectedTotalAmountInBillingPge,"Total Amount mismatch: " + ExpectedTotalAmountInBillingPge + " but found " + ActualTotalAmountInBillingPge + " in billing page");

		//Step 15: Click on the Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));

		//Step 16: Select converted to advance button
		base.buttonClick(LoginPageObj.Edt_AlertText("Convert to Advance"));

		//Step 17: Choose deposit type as order 
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_DepositType);

		//Step 18:Select the transaction number from the drop down
		//Step 19:Enter the deposit amount
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OfferAdvanceId);
		Thread.sleep(3000); 

		//Step 20: Select sales agent
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson("3"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC72_SalesPerson);
		Thread.sleep(3000);
		//Step 21: Click on deposit button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		//Step 22:Click on the Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		Thread.sleep(5000);

		//Step 23:Post the Invoice
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT ORDER ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));

		//PDF Validation
		Map<String, String> AdvanceReceiptDetailsFromPdf   = pdfUtils.OfferAdvancePdfValidation();

		String ActualValidityInOfferAdvanceReceiptPDF          = AdvanceReceiptDetailsFromPdf.get("Validity");
		String ActualReceiptIDInOfferAdvanceReceiptPDF         = AdvanceReceiptDetailsFromPdf.get("ReceiptId");
		String ActualDepositTypeInOfferAdvanceReceiptPDF       = AdvanceReceiptDetailsFromPdf.get("DepositType");
		String ActualOfferExpiryDateInOfferAdvanceReceiptPDF   = AdvanceReceiptDetailsFromPdf.get("OfferExpiryDate");
		int    ActualValidityDaysInOfferAdvanceReceiptPDF      = Integer.parseInt(AdvanceReceiptDetailsFromPdf.get("ValidityDays"));
		double ActualApproxWeightInOfferAdvanceReceiptPDF      = Double.parseDouble(AdvanceReceiptDetailsFromPdf.get("ApproxWeight"));
		double ActualFixedGoldRateInOfferAdvanceReceiptPDF     = Double.parseDouble(AdvanceReceiptDetailsFromPdf.get("FixedGoldRate"));
		double ActualAdvanceReceivedInOfferAdvanceReceiptPDF   = Double.parseDouble(AdvanceReceiptDetailsFromPdf.get("AdvanceReceived"));

		String ActualDepositType                           = ActualDepositTypeInOfferAdvanceReceiptPDF.replaceAll("(?i)order\\s*advance", "Order");
		double PercentUsed                                 = ((ActualAdvanceReceivedInOfferAdvanceReceiptPDF + ActualIntialAdvanceReceivedPDF)/ ExpectedAdvanceAmount) * 100;
		int    ExpectedValidityDays;
		if (PercentUsed >= 3 && PercentUsed <= 4.99)        ExpectedValidityDays = 15;
		else if (PercentUsed >= 5 && PercentUsed <= 9.99)   ExpectedValidityDays = 30;
		else if (PercentUsed >= 10 && PercentUsed <= 19.99) ExpectedValidityDays = 60;
		else if (PercentUsed >= 20 && PercentUsed <= 34.99) ExpectedValidityDays = 90;
		else if (PercentUsed >= 35 && PercentUsed <= 49.99) ExpectedValidityDays = 120;
		else if (PercentUsed >= 50 && PercentUsed <= 74.99) ExpectedValidityDays = 180;
		else if (PercentUsed >= 75 && PercentUsed <= 99.99) ExpectedValidityDays = 330;
		else if (PercentUsed >= 100)                        ExpectedValidityDays = 345;
		else ExpectedValidityDays = 0;
		DateTimeFormatter DateFormatter   = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate InitialExpiryDate       = LocalDate.parse(ExpectedOfferExpiryDate, DateFormatter);
		LocalDate UpdatedExpiryDate       = InitialExpiryDate.plusDays(ExpectedValidityDays-ExpectedInitialValidityDays);
		String    ExpectedExpiryDate      = UpdatedExpiryDate.format(DateFormatter); 

		Assert.assertEquals(ActualDepositType,ExpectedDepositType, "Mismatch in Deposit Type: expected '" + ExpectedDepositType + "' but got '" + ActualDepositType + "' in the  ADVANCE RECEIPT PDF.");
		Assert.assertEquals(ActualValidityDaysInOfferAdvanceReceiptPDF,ExpectedValidityDays, "Mismatch in Validity Days: expected '" + ExpectedValidityDays + "' but got '" + ActualValidityDaysInOfferAdvanceReceiptPDF + "' in the  ADVANCE RECEIPT PDF.");
		Assert.assertEquals(ActualApproxWeightInOfferAdvanceReceiptPDF,ExpectedGrossWeight , "Mismatch in Approximate Weight: expected '" + ExpectedGrossWeight + "' but got '" + ActualApproxWeightInOfferAdvanceReceiptPDF + "' in the  ADVANCE RECEIPT PDF.");
		Assert.assertEquals(ActualFixedGoldRateInOfferAdvanceReceiptPDF,ExpectedFixedGoldRate, "Mismatch in Fixed Gold Rate: expected '" + ExpectedFixedGoldRate + "' but got '" + ActualFixedGoldRateInOfferAdvanceReceiptPDF + "' in the  ADVANCE RECEIPT PDF.");
		Assert.assertEquals(ActualOfferExpiryDateInOfferAdvanceReceiptPDF,ExpectedExpiryDate, "Mismatch in Offer Expiry Date: expected '" + ExpectedExpiryDate + "' but got '" + ActualOfferExpiryDateInOfferAdvanceReceiptPDF + "' in the  ADVANCE RECEIPT PDF.");
		Assert.assertEquals(ActualAdvanceReceivedInOfferAdvanceReceiptPDF,ActualTotalAmountInBillingPge, "Mismatch in Advance Received: expected '" + ActualTotalAmountInBillingPge + "' but got '" + ActualAdvanceReceivedInOfferAdvanceReceiptPDF + "' in the  ADVANCE RECEIPT PDF.");
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

	}

	/// <summary>
	/// Test Case Title : OG exchange converted to normal advance
	/// Automation ID : TC64
	/// Author :Vishnu Manoj K
	/// </summary>
	public void TC64_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception {

		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 2: Click on Transaction button
		Thread.sleep(5000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		// Step 3:Select customer and click on add to sale button
		Thread.sleep(2000);
		Map<String, String> SkuDetails = new LinkedHashMap<>();
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC64_CustomerNo,UtilityTestData.MenuBarOptions[0]);

		//PreCondition
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC64_SkuGoldCount,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC64_TransferType,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC64_GoldFromCounter,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC64_MetalType);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
		for (int i = 0; i < SkuListGold.size(); i++) 
		{    		
			appUtils.BoardRateFromSkuRporRgorRs(SkuListGold.get(i), i + 1, SkuDetails);
		}
		double Purity = 0;
		String SkuPurityReturn=SkuDetails.get("SKU_1_Purity");
		String BoardRateAny=SkuDetails.get("SKU1_ITEM1_RATE");
		if (SkuPurityReturn.contains("24")) {
			Purity = 24;
		} else if (SkuPurityReturn.contains("22")) {
			Purity = 22;
		} else if (SkuPurityReturn.contains("18")) {
			Purity = 18;}
		System.out.println("Purity:----------"+Purity);
		double BoardRate;
		if (Purity == 22) {
			BoardRate = Double.parseDouble(BoardRateAny); // already 24K, no conversion needed
		} else {
			BoardRate = ((Double.parseDouble(BoardRateAny)) / Purity) * 24;
		}
		System.out.println("BoardRate:---------"+BoardRate);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		//Step 4:Select OG
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		Thread.sleep(1000);
		base.ClickClearEnter(NormalSaleGoldAndSilverObj.Edt_Name("Search"),UtilityTestData.OGProduct);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_CustomerSearch("Products"));

		//Step 5: Click on OG own and choose Exchange
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));

		//Step 6:Select configuration
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC64_Config);

		//Step 7 : Click on add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("NExchange"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("NExchange"));

		//Step 8:Select QC Person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),UtilityTestData.QCAndSmithPerson);

		//Step 9: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),UtilityTestData.QCAndSmithPerson); 

		//Step 10: Enter piece value
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC64_GrossPieces);

		//Step 11: enter gross weight
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC64_GrossWeight);
		//Step 12: Click on Calculate button
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));

		//Fetching values from Itemdetails page
		String OgNameActual                           = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader")).split("-")[0].trim();;
		Thread.sleep(7000);
		double  TotalAmountInItemDetailsPge           = Double.parseDouble(base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt"),"value"));
		String ActualTotalAmount                      = String.format("%.2f",TotalAmountInItemDetailsPge);

		Map<String, Double> OuSecondDataMap = new LinkedHashMap<>();
		String OgSecondQty = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUNumber("column3 textRight", "h4 ellipsis cell","1"));
		String OgSecondRate = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUNumber("column4 textRight", "h4 ellipsis cell","1"));
		String OgSecondCvalue = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUNumber("column5 textRight", "h4 ellipsis cell","1"));
		Double OgSecondQtyIngredientTable =  Double.parseDouble(OgSecondQty);
		Double OgSecondRateIngredientTable = Double.parseDouble(OgSecondRate);
		Double OgSecondCValueIngredientTable = Double.parseDouble(OgSecondCvalue);
		Double OgSecondCalculatedCvalue = OgSecondQtyIngredientTable * OgSecondRateIngredientTable;
		System.out.println("The OG caluculated value: "+OgSecondCalculatedCvalue);

		asrt.assertEquals(OgSecondCalculatedCvalue, OgSecondCValueIngredientTable,1, "Mismatch in Cvalue,  Expected " + OgSecondCalculatedCvalue+" but got "+OgSecondCValueIngredientTable+" in  Item details page");
		Double OgSecondNetRateValue = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));
		asrt.assertEquals(OgSecondNetRateValue, BoardRate,1,"Mismatch in net rate, Expected " + OgSecondNetRateValue+" but got "+BoardRate+" in Item details page");
		Double OgSecondNetAmountValue = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(OgSecondCalculatedCvalue, OgSecondNetAmountValue,1," Mismatch in Net amount, Expected " + OgSecondCalculatedCvalue+" but got "+OgSecondNetAmountValue+" in Item details page");
		Double OgSecondTotalAmountValue =Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt")));
		asrt.assertEquals(OgSecondTotalAmountValue, OgSecondCValueIngredientTable,1, "Mismatch in Total amount value , Expected " + OgSecondCValueIngredientTable+" but got "+OgSecondTotalAmountValue+" in Item details page");
		Thread.sleep(1000);

		//Step 13:Click on Add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		By CartChangedAlert = NormalSaleGoldAndSilverObj.Ele_CustomerAdjustment("input string");
		By OkBtn = LoginPageObj.Btn_SingnIn("Button1Close");

		if (base.isElementPresent(driver, CartChangedAlert)) {		
			try {
				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}
		WebDriverWait Wait1 = new WebDriverWait(driver, Duration.ofSeconds(8));
		Thread.sleep(1000);

		By CartChangedAlert1 = NormalSaleGoldAndSilverObj.Ele_CustomerAdjustment("cart has changed");
		By OkBtn1 = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");

		if (base.isElementPresent(driver, CartChangedAlert1)) {		
			try {
				WebElement OkButton = Wait1.until(ExpectedConditions.elementToBeClickable(OkBtn1));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}
		//Step 14: Click on the amount
		//validation
		//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
		List<WebElement> AllProductRows          = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ExpectedLinesCountInBillingPge       = AllProductRows.size();
		int ActualLinesCountInBillingPge         = Integer.parseInt(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("LinesField", "h4")));
		String ExpectItemNameInBillingPge        = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		String ActualGrossWeightInBillingPge     = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5")).replace("-", ""); 
		Thread.sleep(3000);
		String ExpectTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedSubTotalInBillingPge      = ExpectTotalWithoutTaxInBillingPge;
		String ActualSubTotalInBillingPge        = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualTaxInBillingPge             = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge           = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC64_OGTax;
		String ExpectedTotalAmountInBillingPge   = ExpectedSubTotalInBillingPge;
		String ActualTotalAmountInBillingPge     = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedGrossWt                   = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC64_GrossWeight;
		String ExpectedAmountDueFromBillingPge   = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ReceiveAmtFromInBillingPge        = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();

		asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");
		asrt.assertEquals(OgNameActual, ExpectItemNameInBillingPge,"Item name mismatch: "+OgNameActual+" but found "+ExpectItemNameInBillingPge+" in billing page");
		asrt.assertEquals(ExpectedGrossWt, ActualGrossWeightInBillingPge,"Gross weight mismatch: "+ExpectedGrossWt+" but found "+ActualGrossWeightInBillingPge+" in billing page");
		asrt.assertEquals(ActualTotalAmount, ExpectTotalWithoutTaxInBillingPge,"Total without tax mismatch: "+ActualTotalAmount+" but found "+ExpectTotalWithoutTaxInBillingPge+" in billing page");
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");

		//Step 15 : Select payment method as convert advance
		//Step 16:Choose normal advance
		//Step 17: Select sales person
		//Step 18:Select the due date and Enter remarks
		//Step 19: Click on nominee details
		//Step 19,a,b,c,d: Enter nominee name, nominee relation, Click on checkbox(same as customer address)Click on ok button and deposit button
		//Step 20: Click on deposit button
		Thread.sleep(3000);
		appUtils.PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
				UtilityTestData.SalePersonNumber,
				UtilityTestData.SalePersonName,
				UtilityTestData.DueYear,
				UtilityTestData.NomineeName,
				UtilityTestData.NomineeRelation);

		//Warning handling if present
		try {
			WebDriverWait ShortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
			WebElement Warning = ShortWait.until( ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Ele_Payement("Warning")) );
			base.excuteJsClick(LoginPageObj.Btn_SignInButton("OK"));
		} catch (Exception e) {
			// Warning not found
		}

		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		//Step 21: Post the invoice
		// Check ADVANCE RECEIPT VOUCHER details,*Towards the advance against purchase of approximate value
		//  *Fixed Gold rate, Deposit Type ,Advance Received,save receipt id for future reference	
		Map<String, String> Result  = pdfUtils.NormalAdvancePdfValidation();	
		Thread.sleep(3000);
		String AdvanceReceived      = Result.get("AdvanceReceived");
		String FixedGoldRate        = Result.get("FixedGoldRate");
		String MaxGoldRate          = Result.get("MaxGoldRate");
		String ApproxWeight         = Result.get("ApproxWeight");
		String DepositType          = Result.get("DepositType");
		double PdfAdvanceReceived   = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
		double PdfFixedGoldRate     = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
		double PdfMaxGoldRate       = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(PdfAdvanceReceived,Double.parseDouble(ExpectedAmountDueFromBillingPge),1, "Mismatch in total amount in billing screen actual "+PdfAdvanceReceived+" and Expected "+Double.parseDouble(ExpectedAmountDueFromBillingPge)+"in Normal Advance PDF");
		asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,1, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
		asrt.assertEquals(Utility_TestData.DepositType, DepositType, "Mismatch in Deposit Type Actual "+UtilityTestData.DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

		Double WeightValue          = PdfAdvanceReceived/PdfMaxGoldRate;
		BigDecimal CalcWeightValue  = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
		double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),1, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");
		Thread.sleep(5000);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title :Normal collection against offer advance
	// Automation ID : TC73
	// Author: Gokul.P
	// </summary>
	public void TC73_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception {	

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(25));
		//Pre-requsite
		Map<String, String> AdvanceDetails = appUtils.CreateWeightBookedOfferAdvance
				(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC73_CustomerId,
						AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC73_Pieces,
						AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC73_GrossWeight,
						UtilityTestData.Company[0],UtilityTestData.Location[0],
						AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC73_DepositPercent);   

		String OfferAdvanceId              = AdvanceDetails.get("OrderId");
		String WillingToPayAmount          = AdvanceDetails.get("WillingToPayAmount");
		String ExpectedOfferExpiryDate     = AdvanceDetails.get("OfferExpiryDate");
		double ExpectedTotalAdvanceAmount  = Double.parseDouble(AdvanceDetails.get("AdvanceAmount"));
		double ExpectedFixedGoldRate       = Double.parseDouble(AdvanceDetails.get("FixedGoldRate"));
		int    ExpectedInitialValidityDays = Integer.parseInt(AdvanceDetails.get("ValidityDays").toString());
		String ExpectedDepositType         = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC73_DepositType;
		double IntitalCollection           = Double.parseDouble(AdvanceDetails.get("AdvanceReceived").toString());

		//2.Click on Transaction button		
		appUtils.HamBurgerButtonClick("iconShop");

		// 3.Select customer
		// 4.Click on add to sale button
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC73_CustomerId ,UtilityTestData.MenuBarOptions[0]);

		// 5.Click on advance button at the transaction screen
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));

		// 6.Click on advance collection button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Advance Collection"));

		// 7.Choose type as order
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"), "Order");

		// 8.Select the transaction number from the drop down
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OfferAdvanceId);


		// 9.Enter the deposit amount
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),WillingToPayAmount);

		// 10.Choose the sales person		
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC69_SalesPerson);

		// 11.Click on deposit button
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		// 12.Click on the Amount
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		Thread.sleep(2000);
		base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		// 13.Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmountBillingPge = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		double ExpectedAdvanceAmount=Double.parseDouble(ExpectedAdvanceAmountBillingPge);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		// 14.Post the Invoice
		// Expected :Check ADVANCE RECEIPT VOUCHER details:Towards the advance against purchase of approximate value,Fixed Gold rate,Deposit Type,Advance Received
		// Validity,Offer Expriry date,save receipt id for future reference
		double ApproxWeight         = Double.parseDouble(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC73_GrossWeight);
		String ExpectedApproxWeight = String.format("%.3f",ApproxWeight);
		double GoldRate             = ExpectedTotalAdvanceAmount/ApproxWeight;
		String ExpectedFixedGldRate = String.format("%.2f",GoldRate);

		Map<String, String> AdvanceReceiptPdf = pdfUtils.OfferAdvancePdfValidation();
		double ActualAdvanceReceived =Double.parseDouble( AdvanceReceiptPdf.get("AdvanceReceived"));
		String ActualApproxWeight    = AdvanceReceiptPdf.get("ApproxWeight");
		String ActualFixedGoldRate   = AdvanceReceiptPdf.get("FixedGoldRate");
		String ActualDepositType     = AdvanceReceiptPdf.get("DepositType");
		int    ActualValidityDays    = Integer.parseInt(AdvanceReceiptPdf.get("ValidityDays"));
		String ActualOfferExpiryDate = AdvanceReceiptPdf.get("OfferExpiryDate");

		double PercentUsed           = ((ActualAdvanceReceived + IntitalCollection) / ExpectedTotalAdvanceAmount) * 100;
		int    ExpectedValidityDays;
		if (PercentUsed >= 3 && PercentUsed <= 4.99)        ExpectedValidityDays = 15;
		else if (PercentUsed >= 5 && PercentUsed <= 9.99)   ExpectedValidityDays = 30;
		else if (PercentUsed >= 10 && PercentUsed <= 19.99) ExpectedValidityDays = 60;
		else if (PercentUsed >= 20 && PercentUsed <= 34.99) ExpectedValidityDays = 90;
		else if (PercentUsed >= 35 && PercentUsed <= 49.99) ExpectedValidityDays = 120;
		else if (PercentUsed >= 50 && PercentUsed <= 74.99) ExpectedValidityDays = 180;
		else if (PercentUsed >= 75 && PercentUsed <= 99.99) ExpectedValidityDays = 330;
		else if (PercentUsed >= 100)                        ExpectedValidityDays = 345;
		else ExpectedValidityDays = 0;

		DateTimeFormatter DateFormatter   = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate InitialExpiryDate       = LocalDate.parse(ExpectedOfferExpiryDate, DateFormatter);
		LocalDate UpdatedExpiryDate       = InitialExpiryDate.plusDays(ExpectedValidityDays-ExpectedInitialValidityDays);
		String    ExpectedExpiryDate      = UpdatedExpiryDate.format(DateFormatter); 
		asrt.assertEquals(ActualAdvanceReceived,ExpectedAdvanceAmount,1,"Advance Received mismatch: Expected is "+ExpectedAdvanceAmount+" but found "+ActualAdvanceReceived+" in Advance Receipt");
		asrt.assertEquals(ActualApproxWeight,ExpectedApproxWeight,"ApproxWeight mismatch: Expected is "+ExpectedApproxWeight+" but found "+ActualApproxWeight+" in Advance Receipt");
		asrt.assertEquals(ActualFixedGoldRate,ExpectedFixedGldRate,"Fixed Gold Rate mismatch: Expected is "+ExpectedFixedGldRate+" but found "+ActualFixedGoldRate+" in Advance Receipt");
		asrt.assertEquals(ActualDepositType,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC73_DepositType,"Deposit Type mismatch: Expected is "+ AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC73_DepositType+" but found "+ActualDepositType+" in Advance Receipt");
		asrt.assertEquals(ActualValidityDays,ExpectedValidityDays,"Validity Days mismatch: Expected is "+ExpectedInitialValidityDays+" but found "+ActualValidityDays+" in Advance Receipt");
		asrt.assertEquals(ActualOfferExpiryDate,ExpectedExpiryDate,"Offer Expiry Date mismatch: Expected is "+ExpectedOfferExpiryDate+" but found "+ActualOfferExpiryDate+" in Advance Receipt");		
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	/// <summary>
	/// Test Case Title : OG exchange converted to offer advance
	/// Automation ID : TC71
	/// Author :Vishnu Manoj K
	/// </summary>
	public void TC71_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception {

		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 2: Click on Transaction button
		Thread.sleep(5000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		// Step 3:Select customer and click on add to Estimate
		Thread.sleep(2000);
		Map<String, String> SkuDetails = new LinkedHashMap<>();
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_CustomerNo,UtilityTestData.MenuBarOptions[5]);

		//PreCondition
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_SkuGoldCount,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_TransferType,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_GoldFromCounter,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_MetalType);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
		for (int i = 0; i < SkuListGold.size(); i++) 
		{    		
			appUtils.BoardRateFromSkuRporRgorRs(SkuListGold.get(i), i + 1, SkuDetails);
		}
		double Purity = 0;
		String SkuPurityReturn=SkuDetails.get("SKU_1_Purity");
		String BoardRateAny=SkuDetails.get("SKU1_ITEM1_RATE");
		if (SkuPurityReturn.contains("24")) {
			Purity = 24;
		} else if (SkuPurityReturn.contains("22")) {
			Purity = 22;
		} else if (SkuPurityReturn.contains("18")) {
			Purity = 18;}
		System.out.println("Purity:----------"+Purity);
		double BoardRate;
		if (Purity == 22) {
			BoardRate = Double.parseDouble(BoardRateAny); // already 24K, no conversion needed
		} else {
			BoardRate = ((Double.parseDouble(BoardRateAny)) / Purity) * 24;
		}
		System.out.println("BoardRate:---------"+BoardRate);

		//Step 4:Select OG
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		Thread.sleep(1000);
		base.ClickClearEnter(NormalSaleGoldAndSilverObj.Edt_Name("Search"),UtilityTestData.OGProduct);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_CustomerSearch("Products"));

		//Step 5: Click on OG own and choose Exchange
		WebDriverWait WaitProduct = new WebDriverWait(driver, Duration.ofSeconds(10));
		WaitProduct.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.Product)));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));

		//Step 6:Select configuration
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_Config);

		//Step 7 : Click on add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("NExchange"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("NExchange"));

		//Step 8:Select QC Person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),UtilityTestData.QCAndSmithPerson);

		//Step 9: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),UtilityTestData.QCAndSmithPerson); 

		//Step 10: Enter piece value
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_GrossPieces);

		//Step 11: enter gross weight
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_GrossWeight);

		//Step 12: Click on Calculate button
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));

		Thread.sleep(4000);
		Map<String, String> ScannedSkuDataMapOuSecond = new LinkedHashMap<>();	
		appUtils.ItemDetailsTableValues(1,ScannedSkuDataMapOuSecond);
		System.out.println(ScannedSkuDataMapOuSecond.keySet());
		String OuSecondRgRate=ScannedSkuDataMapOuSecond.get("SKU1_ITEM1_RATE");
		String OuSecondRgQty=ScannedSkuDataMapOuSecond.get("SKU1_ITEM1_QTY");
		String OuSecondRgCvalue=ScannedSkuDataMapOuSecond.get("SKU1_ITEM1_CVALUE");
		String OuSecondStoneRate=ScannedSkuDataMapOuSecond.get("SKU1_ITEM2_RATE");
		String OuSecondStoneQty=ScannedSkuDataMapOuSecond.get("SKU1_ITEM2_QTY");
		String OuSecondStoneCvalue=ScannedSkuDataMapOuSecond.get("SKU1_ITEM2_CVALUE");

		//Fetching values from Itemdetails page
		String OgNameActual                           = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader")).split("-")[0].trim();;
		Thread.sleep(7000);
		double  TotalAmountInItemDetailsPge           = Double.parseDouble(base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt"),"value"));
		String ActualTotalAmount                      = String.format("%.2f",TotalAmountInItemDetailsPge);

		Map<String, Double> OuSecondDataMap = new LinkedHashMap<>();
		String OgSecondQty = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUNumber("column3 textRight", "h4 ellipsis cell","1"));
		String OgSecondRate = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUNumber("column4 textRight", "h4 ellipsis cell","1"));
		String OgSecondCvalue = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUNumber("column5 textRight", "h4 ellipsis cell","1"));
		Double OgSecondQtyIngredientTable =  Double.parseDouble(OgSecondQty);
		Double OgSecondRateIngredientTable = Double.parseDouble(OgSecondRate);
		Double OgSecondCValueIngredientTable = Double.parseDouble(OgSecondCvalue);
		Double OgSecondCalculatedCvalue = OgSecondQtyIngredientTable * OgSecondRateIngredientTable;
		System.out.println("The OG caluculated value: "+OgSecondCalculatedCvalue);

		//			asrt.assertEquals(OgSecondCalculatedCvalue, OgSecondCValueIngredientTable,1, "Mismatch in Cvalue,  Expected " + OgSecondCValueIngredientTable+" but got "+OgSecondCalculatedCvalue+" in  Item details page");
		//			Double OgSecondNetRateValue = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));
		//			asrt.assertEquals(OgSecondNetRateValue, BoardRate,1,"Mismatch in net rate, Expected " + BoardRate+" but got "+OgSecondNetRateValue+" in Item details page");
		//			Double OgSecondNetAmountValue = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetamt")));
		//			asrt.assertEquals(OgSecondCalculatedCvalue, OgSecondNetAmountValue,1," Mismatch in Net amount, Expected " + OgSecondNetAmountValue+" but got "+OgSecondCalculatedCvalue+" in Item details page");
		//			Double OgSecondTotalAmountValue =Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt")));
		//			asrt.assertEquals(OgSecondTotalAmountValue, OgSecondCValueIngredientTable,1, "Mismatch in Total amount value , Expected " + OgSecondCValueIngredientTable+" but got "+OgSecondTotalAmountValue+" in Item details page");
		//			Thread.sleep(1000);

		//Step 13:Click on Add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		By CartChangedAlert = NormalSaleGoldAndSilverObj.Ele_CustomerAdjustment("input string");
		By OkBtn = LoginPageObj.Btn_SingnIn("Button1Close");

		if (base.isElementPresent(driver, CartChangedAlert)) {		
			try {
				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}
		WebDriverWait Wait1 = new WebDriverWait(driver, Duration.ofSeconds(8));
		Thread.sleep(1000);

		By CartChangedAlert1 = NormalSaleGoldAndSilverObj.Ele_CustomerAdjustment("cart has changed");
		By OkBtn1 = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");

		if (base.isElementPresent(driver, CartChangedAlert1)) {		
			try {
				WebElement OkButton = Wait1.until(ExpectedConditions.elementToBeClickable(OkBtn1));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}
		Map<String, String> RecallTransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuListGold, ScannedSkuDataMapOuSecond);
		String SubTotal		 		= RecallTransactionDataLine.get("Subtotal");
		String Discount			    = RecallTransactionDataLine.get("Discount");
		String TotalGrossWeight	    = RecallTransactionDataLine.get("GrossWeight");
		String TotalNewNetWeight    = RecallTransactionDataLine.get("NetWeight");//wrong
		String Tax				    = RecallTransactionDataLine.get("Tax");
		String TotalAmount			= RecallTransactionDataLine.get("TotalAmount");
		String NetTotal		        = RecallTransactionDataLine.get("NetTotal");
		String LinesCount	        = RecallTransactionDataLine.get("LinesCount");

		//Step  14. Save to estimate
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CustomerAdjustment("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(6000);	
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC71_ProformaInvoice");
		Thread.sleep(6000);	
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FirstInvoice+"";
		String InvoiceTotal =TotalAmount.replaceAll("[^\\d.]","");;
		String TaxableValue = "0.00";
		String GST = "!";//Tax.replaceAll("[^\\d.]","");
		String TotalAmnt =NetTotal.replaceAll("[^\\d.]","");
		String NetTotalPdf =NetTotal.replaceAll("[^\\d.]","");
		//			pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
		//					ProformaInvoicePath,
		//					TaxableValue,
		//					GST,
		//					InvoiceTotal,
		//					NetTotalPdf,
		//					TotalAmnt
		//					);

		//Step 15:Recall estimate from cashier
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));		

		String EstmnNumberRecallPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		//			asrt.assertEquals(EstmnNumberRecallPge,EstmnNumber,"Mismatch in Estimation No:, expected "+EstmnNumberRecallPge+" but got "+EstmnNumber+"in the Recall Page");	

		//Step 16: Select transaction type as "Exchange "
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_TransactionTypeExchnge);

		//Step 17: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 
		base.setZoom(driver, 60);
		Thread.sleep(3000);

		//Step 18: Choose a sales representative
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		Map<String, String> RecallTransactionDataLineOuSecond = appUtils.TransactionSKUsLinesVerify(SkuListGold, ScannedSkuDataMapOuSecond);
		String RecallSubTotalOuSecond		 		= RecallTransactionDataLineOuSecond.get("Subtotal");
		String RecallDiscountOuSecond 			    = RecallTransactionDataLineOuSecond.get("Discount");
		String RecallTotalGrossWeightOuSecond 	    = RecallTransactionDataLineOuSecond.get("GrossWeight");
		String RecallTotalNewNetWeightOuSecond 	    = RecallTransactionDataLineOuSecond.get("NetWeight");//wrong
		String RecallTaxOuSecond 					= RecallTransactionDataLineOuSecond.get("Tax");
		String RecallTotalAmountOuSecond 			= RecallTransactionDataLineOuSecond.get("TotalAmount");
		String RecallNetTotalOuSecond			    = RecallTransactionDataLineOuSecond.get("NetTotal");
		String RecallLinesCountOuSecond		        = RecallTransactionDataLineOuSecond.get("LinesCount");
		String AdjustmentOuSecond		            = RecallTransactionDataLineOuSecond.get("Payments");

		//Step 19: Click on the amount
		List<WebElement> AllProductRows          = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ExpectedLinesCountInBillingPge       = AllProductRows.size();
		int ActualLinesCountInBillingPge         = Integer.parseInt(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("LinesField", "h4")));
		String ExpectItemNameInBillingPge        = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		String ActualGrossWeightInBillingPge     = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5")).replace("-", ""); 
		Thread.sleep(3000);
		String ExpectTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedSubTotalInBillingPge      = ExpectTotalWithoutTaxInBillingPge;
		String ActualSubTotalInBillingPge        = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualTaxInBillingPge             = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge           = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_OGTax;
		String ExpectedTotalAmountInBillingPge   = ExpectedSubTotalInBillingPge;
		String ActualTotalAmountInBillingPge     = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedGrossWt                   = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC71_GrossWeight;
		String ExpectedAmountDueFromBillingPge   = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ReceiveAmtFromInBillingPge        = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();

		//			asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");
		//			asrt.assertEquals(OgNameActual, ExpectItemNameInBillingPge,"Item name mismatch: "+OgNameActual+" but found "+ExpectItemNameInBillingPge+" in billing page");
		//			asrt.assertEquals(ExpectedGrossWt, ActualGrossWeightInBillingPge,"Gross weight mismatch: "+ExpectedGrossWt+" but found "+ActualGrossWeightInBillingPge+" in billing page");
		//			asrt.assertEquals(ActualTotalAmount, ExpectTotalWithoutTaxInBillingPge,"Total without tax mismatch: "+ActualTotalAmount+" but found "+ExpectTotalWithoutTaxInBillingPge+" in billing page");
		//			asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");
		//			asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");
		//			asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");

		//Step 20 :  Select convert advance
		//Expected Result: Should not display "order "value in deposit type drop down
		String AmountDue = base.GetText(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		Thread.sleep(2000);
		//			asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Convert to Advance")),"The user is not able to view the payment methods");
		Thread.sleep(2000);
		base.buttonClick(LoginPageObj.Edt_AlertText(UtilityTestData.PaymentMode));
		List<String> DepositTypeOptions = base.GetElementTexts(
				NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"));
		boolean ContainsOrder= DepositTypeOptions.stream()
				.flatMap(option -> Stream.of(option.split("\\R")))
				.map(String::trim)
				.map(String::toLowerCase) 
				.anyMatch(opt -> opt.equals("order")); 

		asrt.assertFalse(ContainsOrder,"Order found in the Deposit Type dropdown, Dropdown options are: " + DepositTypeOptions);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : SR converted to offer advance
	// Automation ID : TC74
	// Author: Nivya Ramesan
	// </summary>
	public void TC74_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception {	

		//Step 1: Login to POS
		//Pre-requsite - Take the bill number (receipt number) that has previously been sold.
		String InvoiceNo = appUtils.GetInvoiceNoAfterNormalSale(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_CustomerNo, AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.SkuList);
		String ActualCustomerName = PdfUtilities.ExtractCustomerNameFromSaleInvoice();
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(25));

		//Pre-requsite - customer must have an existing parent offer advance
		Map<String, String> AdvanceDetails = appUtils.CreateWeightBookedOfferAdvance
				(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_CustomerNo,
						AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_Pieces,
						AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_GrossWeight,
						UtilityTestData.Company[0],UtilityTestData.Location[0],
						AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_DepositPercent);   

		String OfferAdvanceId              = AdvanceDetails.get("OrderId");
		String WillingToPayAmount          = AdvanceDetails.get("WillingToPayAmount");
		double ExpectedWillingToPayAmount  = Double.parseDouble(WillingToPayAmount);
		String ExpectedOfferExpiryDate     = AdvanceDetails.get("OfferExpiryDate");
		double ExpectedTotalAdvanceAmount  = Double.parseDouble(AdvanceDetails.get("AdvanceAmount"));
		double ExpectedFixedGoldRate       = Double.parseDouble(AdvanceDetails.get("FixedGoldRate"));
		int    ExpectedInitialValidityDays = Integer.parseInt(AdvanceDetails.get("ValidityDays").toString());
		String ExpectedDepositType         = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_DepositType;
		double IntitalCollection           = Double.parseDouble(AdvanceDetails.get("AdvanceReceived").toString());

		//Step 2.Click on Transaction button		
		appUtils.HamBurgerButtonClick("iconShop");

		//Step 3: Select customer
		//Step 4: Click on add to sale button
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_CustomerPhoneNo ,UtilityTestData.MenuBarOptions[0]);

		//Step 5.Click on customer adjustment button
		//Step 6.Click on return transaction button at the billing screen
		//Step 7.Enter reciept number
		//Step 8.Select return product
		//Step 9.Click on return button
		//Expected:The product meant to be returned should be previously bought by the same customer.
		//Check calculation
		// Note:Billling Screen
		// *No of Product lines
		// *Displayed Item Name
		// *Displayed Quantity
		// *Displayed TOTAL (without Tax)
		// *Displayed Subtotal
		// *TAX
		// *Total Amount
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.ReturnProducts);

		String ExpectedCustomerName = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_CustomerName;
		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName+"in Billling Screen");

		List<String> BillScrnProdNames    = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> BillScrnProdQtys     = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> BillScrnProdTotal    = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));

		List<String> ReturnProdNames      = Details.productNames;
		List<String> ReturnProdQtyList    = Details.productQty;
		List<String> ReturnProdTotalList  = Details.productPrice;

		for (int i = 0; i < BillScrnProdNames.size(); i++) {
			String ExpectedReturnName       = ReturnProdNames.get(i).trim();
			double ExpectedReturnQty        = Double.parseDouble(ReturnProdQtyList.get(i).trim().replace("-", "").replaceAll("[^\\d.]", ""));
			double ExpectedReturnTotalWithoutTax = Double.parseDouble(ReturnProdTotalList.get(i).trim().replaceAll("[^\\d.]", ""));

			String ActualName       = BillScrnProdNames.get(i).trim();
			double ActualQty        = Double.parseDouble(BillScrnProdQtys.get(i).replace("-", "").replaceAll("[^\\d.]", ""));
			double ActualTotalNoTax = Double.parseDouble(BillScrnProdTotal.get(i).replace("-", "").replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ActualName,ExpectedReturnName ,"In Transaction Line Page,The product name does not match the item previously purchased by the customer. " +"Expected: " + ExpectedReturnName + ", but found: " + ActualName+" in Billling Screen");
			asrt.assertEquals(ActualQty,ExpectedReturnQty, 1,"In Transaction Line Page, The quantity does not match the quantity previously purchased by the customer. " +"Expected: " + ExpectedReturnQty + ", but found: " + ActualQty+" in Billling Screen");
			asrt.assertEquals(ActualTotalNoTax,ExpectedReturnTotalWithoutTax, 1,"In Transaction Line Page, The TOTAL (without Tax) does not match the TOTAL (without Tax) previously paid by the customer. " +"Expected: " + ExpectedReturnTotalWithoutTax + ", but found: " + ActualTotalNoTax+" in Billling Screen");
		}

		double ReturnSumSubTotal = 0.0;
		for (String TotalAmnt : BillScrnProdTotal) {
			String ReturnTotalAmnt = TotalAmnt.replaceAll("[^\\d.]", "").trim();
			if (!ReturnTotalAmnt.isEmpty()) {
				ReturnSumSubTotal += Double.parseDouble(ReturnTotalAmnt);
			}
		}

		double ReturnTaxAmount        = ReturnSumSubTotal*3/100;
		double ReturnTotalAmount      = ReturnSumSubTotal+ReturnTaxAmount;
		int ActualNoOfProductLines    = BillScrnProdNames.size();     		
		String TransSubTotal          = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("SubtotalField","row pad12 padBottom0","h4"));
		String TransTax               = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TaxField","row pad12 padBottom0","h4"));
		String TransTotalAmnt         = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TotalAmountField","row pad12 padBottom0","h4"));
		String TransNoOfLines         = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("LinesField","row pad12 padBottom0","h4"));
		double TransactionSubTotal    = Double.parseDouble(TransSubTotal.replaceAll("[^\\d.]", "").trim());
		double TransactionTax         = Double.parseDouble(TransTax.replaceAll("[^\\d.]", "").trim());
		double TransactionTotalAmount = Double.parseDouble(TransTotalAmnt.replaceAll("[^\\d.]", "").trim());
		int TransNoOfLinesInt         = Integer.parseInt(TransNoOfLines.replaceAll("[^\\d]", "").trim());

		asrt.assertEquals(TransNoOfLinesInt, ActualNoOfProductLines,"In Transaction Line Page, Mismatch in number of Actual product lines  "+TransNoOfLinesInt+" and Expected "+ActualNoOfProductLines+" product lines in Billing screen");
		asrt.assertEquals(TransactionSubTotal, ReturnSumSubTotal,1,"In Transaction Line Page, Mismatch in Actual subtotal "+TransactionSubTotal+" and Expected "+ReturnSumSubTotal+" sub total in Billing screen");
		asrt.assertEquals(TransactionTax, ReturnTaxAmount,1,"In Transaction Line Page, Mismatch in Actual Tax "+TransactionTax+" and Expected "+ReturnTaxAmount+" Tax in Billing screen");
		asrt.assertEquals(TransactionTotalAmount,ReturnTotalAmount,1,"In Transaction Line Page, Mismatch in Actual total amount "+TransactionTotalAmount+" and Expected "+ReturnTotalAmount+" total amount in Billing screen");

		//Step 10: Click on the Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		//Step 11: Select converted to advance button
		base.buttonClick(LoginPageObj.Edt_AlertText("Convert to Advance"));
		//Step 12:  Choose offer advance
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_Deposit);
		Thread.sleep(2000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OfferAdvanceId);

		//Step 13: Choose the sales person		
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_SalesPerson);

		//Step 14: Click on deposit button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		Thread.sleep(5000);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT ORDER ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		Thread.sleep(2000);

		//Step 15: Post the Invoice
		//Expected Result:Check ADVANCE RECEIPT VOUCHER details *Towards the advance against purchase of approximate value*Fixed Gold rate *Deposit Type *Advance Received * Validity* Offer Expriry date

		Map<String, String> AdvanceReceiptPdf = pdfUtils.OfferAdvancePdfValidation();
		String ActualAdvanceReceived = AdvanceReceiptPdf.get("AdvanceReceived");
		double ActualAdvance=Double.parseDouble(ActualAdvanceReceived);
		String ActualApproxWeight = AdvanceReceiptPdf.get("ApproxWeight");
		String ActualFixedGoldRate = AdvanceReceiptPdf.get("FixedGoldRate");
		Double ActualFixedGldRate = Double.parseDouble(ActualFixedGoldRate);
		String ActualDepositType = AdvanceReceiptPdf.get("DepositType");
		int ActualValidityDays = Integer.parseInt(AdvanceReceiptPdf.get("ValidityDays"));		
		String ActualOfferExpiryDate = AdvanceReceiptPdf.get("OfferExpiryDate");

		double ApproxWeight = Double.parseDouble(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_GrossWeight);
		String ExpectedApproxWeight = String.format("%.3f",ApproxWeight);		
		double PercentUsed           = ((ExpectedWillingToPayAmount+ActualAdvance) / ExpectedTotalAdvanceAmount) * 100;
		int    ExpectedValidityDays;
		if (PercentUsed >= 3 && PercentUsed <= 4.99)        ExpectedValidityDays = 15;
		else if (PercentUsed >= 5 && PercentUsed <= 9.99)   ExpectedValidityDays = 30;
		else if (PercentUsed >= 10 && PercentUsed <= 19.99) ExpectedValidityDays = 60;
		else if (PercentUsed >= 20 && PercentUsed <= 34.99) ExpectedValidityDays = 90;
		else if (PercentUsed >= 35 && PercentUsed <= 49.99) ExpectedValidityDays = 120;
		else if (PercentUsed >= 50 && PercentUsed <= 74.99) ExpectedValidityDays = 180;
		else if (PercentUsed >= 75 && PercentUsed <= 99.99) ExpectedValidityDays = 330;
		else if (PercentUsed >= 100)                        ExpectedValidityDays = 345;
		else ExpectedValidityDays = 0;

		DateTimeFormatter DateFormatter   = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate InitialExpiryDate       = LocalDate.parse(ExpectedOfferExpiryDate, DateFormatter);
		LocalDate UpdatedExpiryDate       = InitialExpiryDate.plusDays(ExpectedValidityDays-ExpectedInitialValidityDays);
		String    ExpectedExpiryDate      = UpdatedExpiryDate.format(DateFormatter); 

		asrt.assertEquals(ActualAdvance,TransactionTotalAmount,1.0,"Advance Received mismatch: Expected is "+ExpectedTotalAdvanceAmount+" but found "+ActualAdvance+" in Advance Receipt");
		asrt.assertEquals(ActualApproxWeight,ExpectedApproxWeight,"ApproxWeight mismatch: Expected is "+ExpectedApproxWeight+" but found "+ActualApproxWeight+" in Advance Receipt");
		asrt.assertEquals(ActualFixedGldRate,ExpectedFixedGoldRate,1,"Fixed Gold Rate mismatch: Expected is "+ExpectedFixedGoldRate+" but found "+ActualFixedGoldRate+" in Advance Receipt");
		asrt.assertEquals(ActualDepositType,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_DepositType,"Deposit Type mismatch: Expected is "+ AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC74_DepositType+" but found "+ActualDepositType+" in Advance Receipt");
		asrt.assertEquals(ActualValidityDays,ExpectedValidityDays,"Validity Days mismatch: Expected is "+ExpectedValidityDays+" but found "+ActualValidityDays+" in Advance Receipt");
		asrt.assertEquals(ActualOfferExpiryDate,ExpectedExpiryDate,"Offer Expiry Date mismatch: Expected is "+ExpectedExpiryDate+" but found "+ActualOfferExpiryDate+" in Advance Receipt");		
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title :Reserve SKU against an existing offer advance
	// Automation ID : TC70
	//Challenge : While loading the Stock summary report or Stock details report then observed loading issue. So, its not able to view the reports. 
	// Author: Neethu
	// </summary>
	public void TC70_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception
	{		
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(25));

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Pre-requsite
		Map<String, String> AdvanceDetails = appUtils.CreateWeightBookedOfferAdvance
				(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_CustomerId,
						AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_Pieces,
						AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_GrossWeight,
						UtilityTestData.Company[0],UtilityTestData.Location[0],
						AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_DepositPercent);   

		String OfferAdvanceId              = AdvanceDetails.get("OrderId");
		String WillingToPayAmount          = AdvanceDetails.get("WillingToPayAmount");
		String ExpectedOfferExpiryDate     = AdvanceDetails.get("OfferExpiryDate");
		double ExpectedTotalAdvanceAmount  = Double.parseDouble(AdvanceDetails.get("AdvanceAmount"));
		double ExpectedFixedGoldRate       = Double.parseDouble(AdvanceDetails.get("FixedGoldRate"));
		int    ExpectedInitialValidityDays = Integer.parseInt(AdvanceDetails.get("ValidityDays").toString());
		String ExpectedDepositType         = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC73_DepositType;
		double IntitalCollection           = Double.parseDouble(AdvanceDetails.get("AdvanceReceived").toString());

		//Step 2: Click on Transaction button		
		appUtils.HamBurgerButtonClick("iconShop");

		//Step 3: Select customer and click on add to sale button
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_CustomerId ,UtilityTestData.MenuBarOptions[0]);

		//Step 4: Click on Advance-->Reserve SKU
		List<String> SKUDetails      = 	appUtils.SelectMultipleSKUs( AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_SKUCount,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_TransferType,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_FromCounter,AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_MetalType);
		appUtils.HamBurgerButtonClick("iconShop");

		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Reserve SKU"));

		//Step 5: Choose the advance ID	
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: availableTransTypeOptions"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_AdvanceType);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: availableTransTypeOptions"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_AdvanceTypeNormal);
		Thread.sleep(5000);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: availableTransTypeOptions"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC70_AdvanceType);
		Thread.sleep(5000);
		System.out.println("The offer no: "+OfferAdvanceId);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", OfferAdvanceId));
		Thread.sleep(5000);

		//Step 6: Enter SKU number
		base.buttonClick(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_ScanSku("value: SKUNUMBERAUTO"));
		Thread.sleep(3000);
		String SKUNumber = String.join(", ", SKUDetails);
		System.out.println("The Sku: "+SKUNumber); 
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_ScanSku("value: SKUNUMBERAUTO"), SKUNumber);
		Thread.sleep(5000);

		//Step 7: Click on add sku button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("btnblue"));

		//Step 8: Click on OK button
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("buttonContainer col no-shrink halfWidth leftmostDialogButton marginTop20"));

		//Step 9: Go to home-->reports-->stock Details reports-->check if booked SKU has been displayed or not.
		//Expected: Check if the SKU is present in stock details report.
		//Challenge : While loading the Stock summary report or Stock details report then observed loading issue. So, its not able to view the reports. 
		appUtils.HamBurgerButtonClick("iconHome");
		Thread.sleep(4000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_CustomerAdjustment("POS Reports"));
		base.buttonClick(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Ele_GPPMature("btnrpt","Stock details"));

	}
	// <summary>
	// Test Case Title : Collection against a sku reserved advance
	// Automation ID : TC78
	// Author: Sangeetha M S
	// </summary>
	public void TC78_AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserve() throws Exception
	{
		//Step 1.Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2: Click on Transaction button
		Thread.sleep(3000);
		appUtils.HamBurgerButtonClick("iconShop");

		//Prerequisite : Customer must have an existing parent offer advance
		Map<String, String> OfferAdvanceResult = appUtils.OfferAdvanceReserveSkuAndTakeCollection(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC78_CustomerPhoneNo,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC78_SKUCount,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC78_TranferType,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC78_FromCounterGold,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC78_MetalTypeGold,
				AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC78_DepositPercent);

		Thread.sleep(2000);
		String ActualGrossWeightInCustomerOrderPge=OfferAdvanceResult.get("ActualGrossWeightInCustomerOrderPge");
		String OfferAdvanceOrderId = OfferAdvanceResult.get("OrderId");
		String WillingToPayAmount= OfferAdvanceResult.get("WillingToPayAmount");
		double TotalInCustomerInOrderPage =Double.parseDouble(OfferAdvanceResult.get("TotalInCustomerOrderPage"));
		String ExpectedOfferExpiryDate=OfferAdvanceResult.get("ExpectedOfferExpiryDate");
		int ExpectedInitialValidityDay=Integer.parseInt(OfferAdvanceResult.get("ExpectedValidityDays"));		
		double ActualGrossWeightInCustomerOrderPage=Double.parseDouble(ActualGrossWeightInCustomerOrderPge);
		double FirstAdvanceReceivedAmount=Double.parseDouble(OfferAdvanceResult.get("FirstAdvanceReceived"));

		//Step 3: Select customer and click on add to sale button
		Thread.sleep(3000);
		appUtils.HamBurgerButtonClick("iconShop");
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC78_CustomerPhoneNo ,UtilityTestData.MenuBarOptions[0]);

		//Step 4: Select advance
		//Step 5: Click on advance collection
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Advance Collection"));
		Thread.sleep(5000);
		if(base.isExists(LoginPageObj.Btn_SingnIn("Button1Close")))
		{
		  base.ClickCondition(LoginPageObj.Btn_SingnIn("Button1Close"));	
		}

		//Step 6: Select customer advance		
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC78_OrderTypeOptions);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OfferAdvanceOrderId);

		//Step 7: Enter deposit amount
		//Step 8: Click on deposit button
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),WillingToPayAmount);	
		Thread.sleep(2000);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC78_SalesPerson);
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Step 9: Click on the Amount
		//Step 10: Select a Payment method(Cash or Card)
		//Step 11: Post the Invoice	
		//Expected :Check ADVANCE RECEIPT VOUCHER details 
		//*Towards the advance against purchase of approximate value,*Fixed Gold rate,*Deposit Type,*Advance Received,* Validity,* Offer Expriry date

		Thread.sleep(3000);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		double ApproxWeight = ActualGrossWeightInCustomerOrderPage;
		String ExpectedApproxWeight = String.format("%.3f",ApproxWeight);
		double GoldRate = TotalInCustomerInOrderPage/ApproxWeight;
		String ExpectedFixedGoldRate = String.format("%.2f",GoldRate);
		
		Thread.sleep(1000);
		Map<String, String> AdvanceReceiptPdf = pdfUtils.OfferAdvancePdfValidation();
		String ActualAdvanceReceived = AdvanceReceiptPdf.get("AdvanceReceived");
		String ActualApproxWeight = AdvanceReceiptPdf.get("ApproxWeight");
		String ActualFixedGoldRate = AdvanceReceiptPdf.get("FixedGoldRate");
		String ActualDepositType = AdvanceReceiptPdf.get("DepositType");
		String InitialValidityDays  = AdvanceReceiptPdf.get("ValidityDays");
		String ActualOfferExpiryDate = AdvanceReceiptPdf.get("OfferExpiryDate");

		double ActualValidityDays=Double.parseDouble(InitialValidityDays);
		double ActualAdvanceReceivedAmount=Double.parseDouble(ActualAdvanceReceived);	
		double PercentUsed = ((ActualAdvanceReceivedAmount + FirstAdvanceReceivedAmount) / TotalInCustomerInOrderPage) * 100;
		int FinalExpectedValidityDays;
		if (PercentUsed >= 3 && PercentUsed <= 4.99)        FinalExpectedValidityDays = 15;
		else if (PercentUsed >= 5 && PercentUsed <= 9.99)   FinalExpectedValidityDays = 30;
		else if (PercentUsed >= 10 && PercentUsed <= 19.99) FinalExpectedValidityDays = 60;
		else if (PercentUsed >= 20 && PercentUsed <= 34.99) FinalExpectedValidityDays = 90;
		else if (PercentUsed >= 35 && PercentUsed <= 49.99) FinalExpectedValidityDays = 120;
		else if (PercentUsed >= 50 && PercentUsed <= 74.99) FinalExpectedValidityDays = 180;
		else if (PercentUsed >= 75 && PercentUsed <= 99.99) FinalExpectedValidityDays = 330;
		else if (PercentUsed >= 100)                        FinalExpectedValidityDays = 345;
		else FinalExpectedValidityDays = 0;

		DateTimeFormatter DateFormatter= DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate InitialExpiryDate    = LocalDate.parse(ExpectedOfferExpiryDate, DateFormatter);
		LocalDate UpdatedExpiryDate    = InitialExpiryDate.plusDays(FinalExpectedValidityDays-ExpectedInitialValidityDay);
		String    ExpectedExpiryDate   = UpdatedExpiryDate.format(DateFormatter);
		String ExpectedDepositType     = AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceSkuReserveTestData.TC78_DepositType;

		asrt.assertEquals(ActualAdvanceReceived,ExpectedAdvanceAmount,"Advance Received mismatch: Expected is "+ExpectedAdvanceAmount+" but found "+ActualAdvanceReceived+" in Advance Receipt");
		asrt.assertEquals(ActualFixedGoldRate,ExpectedFixedGoldRate,"Fixed Gold Rate mismatch: Expected is "+ExpectedFixedGoldRate+" but found "+ActualFixedGoldRate+" in Advance Receipt");
		asrt.assertEquals(ActualDepositType,ExpectedDepositType,"Deposit Type mismatch: Expected is "+ ExpectedDepositType+" but found "+ActualDepositType+" in Advance Receipt");
		asrt.assertEquals(ActualApproxWeight,ExpectedApproxWeight,"ApproxWeight mismatch: Expected is "+ExpectedApproxWeight+" but found "+ActualApproxWeight+" in Advance Receipt");
		asrt.assertEquals(ActualValidityDays,FinalExpectedValidityDays,"Validity Days mismatch: Expected is "+FinalExpectedValidityDays+" but found "+ActualValidityDays+" in Advance Receipt");
		asrt.assertEquals(ActualOfferExpiryDate,ExpectedExpiryDate,"Offer Expiry Date mismatch: Expected is "+ExpectedExpiryDate+" but found "+ActualOfferExpiryDate+" in Advance Receipt");		
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

}
