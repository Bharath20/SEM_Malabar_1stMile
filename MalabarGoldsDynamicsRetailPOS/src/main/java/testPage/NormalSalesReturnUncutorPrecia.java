

package testPage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import objectRepository.OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj;
import testData.CommonData;
import testData.NormalSaleGoldandSilver_TestData;
import testData.NormalSalesReturnGoldSilverReturnSaleCounter_TestData;
import testData.NormalSalesReturnUncutorPrecia_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.AppUtilities.ReturnDetails;
import utilPack.BasePge;
import utilPack.ErpUtilities;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;


public class NormalSalesReturnUncutorPrecia extends BasePge {

	BasePge base;
	Assert asrt;
	PdfUtilities pdfUtils;
	ErpUtilities erpUtils;
	AppUtilities appUtils;
	MathUtilities mathUtils;

	Login Login = new Login(driver); 
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();
	Utility_TestData UtilityTestData=new Utility_TestData();
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj=new OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj = new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();	
	NormalSalesReturnUncutorPrecia_TestData NormalSalesReturnUncutorPreciaTestData=new NormalSalesReturnUncutorPrecia_TestData();

	public NormalSalesReturnUncutorPrecia(WebDriver driver) {
		super(driver);
		base     = new BasePge(driver);
		appUtils = new AppUtilities(base);
		mathUtils=new MathUtilities(base);
		erpUtils = new ErpUtilities(base);
		pdfUtils =new PdfUtilities(base);
	}

	// <summary>
	// Test Case Title : Sales Return of Multiple Uncut and Precia Item
	// Automation ID : TC36
	// Bug ID: Bug03_MalabarGoldDynamicPOS | Step : 9 -There is a mismatch in the quantity value displayed on the Transaction page(Particular SKUs)
	// Author : Vishnu Manoj K
	// </summary>
	public void TC36_NormalSalesReturnUncutorPrecia() throws Exception {

		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		List<String> ItemList = Arrays.asList("2", "Uncut", "Gold", "2", "Precia", "Gold");
		String InvoiceNo = appUtils.GetInvoiceNoAfterNormalSale(NormalSalesReturnUncutorPreciaTestData.TC36_CustomerNo, ItemList);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		//Step 3 : Select the customer
		//Step 4 : Click on add to sale button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturnUncutorPreciaTestData.TC36_CustomerNo,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product
		//Step 9 : Click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		//Expected Result : Check calculation Note:Billling Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> ToReturnProducts = Arrays.asList("Uncut","Uncut","Precia","Precia");
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, ToReturnProducts);

		//Validation for product meant to be returned should be previously bought by the same customer.
		String ExpectedCustomerName = NormalSalesReturnUncutorPreciaTestData.TC36_CustomerName;
		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName);

		//Validation Steps : Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax)
		List<String> BillScrnProdNames  = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> BillScrnProdQtys = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> BillScrnProdTotal = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));

		List<String> ReturnProdNames = Details.productNames;
		List<String> ReturnProdQtyList = Details.productQty;
		List<String> ReturnProdTotalList = Details.productPrice;

		for (int i = 0; i < BillScrnProdNames.size(); i++) {
			String ExpectedReturnName = ReturnProdNames.get(i).trim();
			double ExpectedReturnQty = Double.parseDouble(ReturnProdQtyList.get(i).trim().replace("-", "").replaceAll("[^\\d.]", ""));
			double ExpectedReturnTotalNoTax = Double.parseDouble(ReturnProdTotalList.get(i).trim().replaceAll("[^\\d.]", ""));

			String ActualName = BillScrnProdNames.get(i).trim();
			double ActualQty = Double.parseDouble(BillScrnProdQtys.get(i).replace("-", "").replaceAll("[^\\d.]", ""));
			double ActualTotalNoTax = Double.parseDouble(BillScrnProdTotal.get(i).replace("-", "").replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ActualName,ExpectedReturnName ,
					"In Transaction Line Page,The product name does not match the item previously purchased by the customer. " +
							"Expected: " + ExpectedReturnName + ", but found: " + ActualName);

			asrt.assertEquals(ActualQty,ExpectedReturnQty, 0.1,
					"In Transaction Line Page, The quantity does not match the quantity previously purchased by the customer. " +
							"Expected: " + ExpectedReturnQty + ", but found: " + ActualQty);

			asrt.assertEquals(ActualTotalNoTax,ExpectedReturnTotalNoTax, 0.1,
					"In Transaction Line Page, The TOTAL (without Tax) does not match the TOTAL (without Tax) previously paid by the customer. " +
							"Expected: " + ExpectedReturnTotalNoTax + ", but found: " + ActualTotalNoTax);
		}

		//Validation Steps : No of Product lines, Displayed SubTotal, TAX, Total amount
		double ReturnSumSubTotal = 0.0;
		for (String TotalAmnt : BillScrnProdTotal) {
			String ReturnTotalAmnt = TotalAmnt.replaceAll("[^\\d.]", "").trim();
			if (!ReturnTotalAmnt.isEmpty()) {
				ReturnSumSubTotal += Double.parseDouble(ReturnTotalAmnt);
			}
		}

		double ReturnTaxAmount = ReturnSumSubTotal*3/100;
		double ReturnTotalAmount = ReturnSumSubTotal+ReturnTaxAmount;
		int ActualNoOfProductLines = BillScrnProdNames.size();     		
		String TransSubTotal = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("SubtotalField","row pad12 padBottom0","h4"));
		String TransTax = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TaxField","row pad12 padBottom0","h4"));
		String TransTotalAmnt = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TotalAmountField","row pad12 padBottom0","h4"));
		String TransNoOfLines = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("LinesField","row pad12 padBottom0","h4"));
		double TransSubTotalDb = Double.parseDouble(TransSubTotal.replaceAll("[^\\d.]", "").trim());
		double TransTaxDb = Double.parseDouble(TransTax.replaceAll("[^\\d.]", "").trim());
		double TransTotalAmount = Double.parseDouble(TransTotalAmnt.replaceAll("[^\\d.]", "").trim());
		int TransNoOfLinesInt = Integer.parseInt(TransNoOfLines.replaceAll("[^\\d]", "").trim());

		asrt.assertEquals(TransNoOfLinesInt, ActualNoOfProductLines, "In Transaction Line Page, Mismatch in number of Actual product lines  "+TransNoOfLinesInt+" and Expected "+ActualNoOfProductLines+" product lines in Billing screen");
		asrt.assertEquals(TransSubTotalDb, ReturnSumSubTotal,0.10,  "In Transaction Line Page, Mismatch in Actual subtotal "+TransSubTotalDb+" and Expected "+ReturnSumSubTotal+" sub total in Billing screen");
		asrt.assertEquals(TransTaxDb, ReturnTaxAmount,0.10, "In Transaction Line Page, Mismatch in Actual Tax "+TransTaxDb+" and Expected "+ReturnTaxAmount+" Tax in Billing screen");
		asrt.assertEquals(TransTotalAmount,ReturnTotalAmount,0.10,  "In Transaction Line Page, Mismatch in Actual total amount "+TransTotalAmount+" and Expected "+ReturnTotalAmount+" total amount in Billing screen");


		//Step 10  : Click on the Amount
		//Step 11  : Select a Payment method as convert advance
		//Step 12  : Select transaction type as normal
		//Step 13  : Select sales person
		//Step 14  : Select the due date
		//Step 15  : Enter remarks
		//Step 16  : click on nominee details
		//Step 16.1 : Enter nominee name
		//Step 16.2 : Select nominee relation
		//Step 16.3 : Click on check box(Same as customer address)
		//Step 16.4 : Click on OK button
		//Step 17  : Click on Deposit button
		//Expected Result : Check ADVANCE RECEIPT VOUCHER details*Towards the advance against purchase of approximate value*Fixed Gold rate*Deposit Type*Advance Received* save receipt id for future reference      
		appUtils.PaymentModeForDiffTransactions(NormalSalesReturnUncutorPreciaTestData.TC36_PaymentMode, "", "",
				NormalSalesReturnUncutorPreciaTestData.TC36_SalePersonNumber,
				NormalSalesReturnUncutorPreciaTestData.TC36_SalePersonName,
				NormalSalesReturnUncutorPreciaTestData.TC36_DueYear,
				NormalSalesReturnUncutorPreciaTestData.TC36_NomineeName,
				NormalSalesReturnUncutorPreciaTestData.TC36_NomineeRelation);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		Map<String, String> Result = pdfUtils.NormalAdvancePdfValidation();	

		String AdvanceReceived = Result.get("AdvanceReceived");
		String FixedGoldRate = Result.get("FixedGoldRate");
		String MaxGoldRate = Result.get("MaxGoldRate");
		String ApproxWeight = Result.get("ApproxWeight");
		String DepositType = Result.get("DepositType");
		double PdfAdvanceReceived = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
		double PdfFixedGoldRate = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
		double PdfMaxGoldRate = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(PdfAdvanceReceived,TransTotalAmount,0.10, "Mismatch in total amount in billing screen actual "+PdfAdvanceReceived+" and Expected "+TransTotalAmount+"in Normal Advance PDF");
		asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,0.10, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
		asrt.assertEquals(NormalSalesReturnUncutorPreciaTestData.TC36_DepositType, DepositType, "Mismatch in Deposit Type Actual "+NormalSalesReturnUncutorPreciaTestData.TC36_DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

		Double WeightValue = PdfAdvanceReceived/PdfMaxGoldRate;
		BigDecimal CalcWeightValue = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
		double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),0.10, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");

		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

	}

	// <summary>
	// Test Case Title : Sales return multiple precia converted to advance
	// Automation ID : TC35
	// Author : Neethu
	// </summary>

	public void TC35_NormalSalesReturnUncutorPrecia() throws Exception 
	{

		//Pre-Condition
		String ReceiptNumber = appUtils.GetInvoiceNoAfterNormalSale(NormalSalesReturnUncutorPreciaTestData.TC35_CustID,
				NormalSalesReturnUncutorPreciaTestData.TC_35ItemList);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

		// Step 1 and Step 2 are done in Prerequisite condition
		//Step 3 : Select the customer 
		//Step 4 : Click on add to sale button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturnUncutorPreciaTestData.TC35_CustID,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product(multiple precia)
		//Step 9 : Click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		//Expected Result : Check calculation Note:Billling Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount	
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(ReceiptNumber, NormalSalesReturnUncutorPreciaTestData.TC35_ReturnProducts);

		//Validation Steps : Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax)
		List<WebElement> BillScrnProdName  = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> BillScrnProdQty = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<WebElement> BillScrnProdPrice = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));

		List<String> ProdNames = Details.productNames;
		List<String> ProdQtyList = Details.productQty;
		List<String> ProdPriceList = Details.productPrice;

		for (int i = 0; i < BillScrnProdName.size(); i++)
		{		    
			String ExpectedName = ProdNames.get(i).trim();
			double ExpectedQty = Double.parseDouble(ProdQtyList.get(i).trim().replace("-", "").replaceAll("[^\\d.]", ""));
			double ExpectedPrice = Double.parseDouble(ProdPriceList.get(i).trim().replace("\u20B9", "").replace(",", "").replace("-", "").replaceAll("[^\\d.]", ""));

			String BillScreenProdName = BillScrnProdName.get(i).getText();
			String BillScreenProQty = BillScrnProdQty.get(i).getText().replace("-", "").trim();
			String BillScreenProdPrice = BillScrnProdPrice.get(i).getText().replace("-", "").trim();
			double ActualQty = Double.parseDouble(BillScreenProQty);
			double ActualPrice = Double.parseDouble(BillScreenProdPrice.replace("\u20B9", "").replace(",", "").replaceAll("[^\\d.]", "").trim());

			asrt.assertEquals(ExpectedName, BillScreenProdName,"Item Name mismatch: Expected" +ExpectedName+ "but got" +BillScreenProdName+ "in billing screen");
			asrt.assertEquals(ExpectedQty, ActualQty, 1.0, "Quantity mismatch: Expected" +ExpectedQty+ "but got" +ActualQty+ "in billing screen");
			asrt.assertEquals(ExpectedPrice, ActualPrice,1.0 ,"Price mismatch: Expected" +ExpectedPrice+ "but got" +ActualPrice+ "in billing screen");
		}

		//Validation Steps : No of Product lines, Displayed SubTotal, TAX, Total amount
		double TotalWithoutTax = 0.0;
		for (WebElement SubTotalElement : BillScrnProdPrice)
		{
			String SubTotalText = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!SubTotalText.isEmpty())
			{
				TotalWithoutTax=TotalWithoutTax + Double.parseDouble(SubTotalText);
			}
		}


		double TaxAmount = TotalWithoutTax*3/100;
		double ActualTotalAmount = TotalWithoutTax+TaxAmount;
		int ActualNoOfProductLines = BillScrnProdName.size();     		
		String SubTotalFromReturnPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("SubtotalField","row pad12 padBottom0","h4"));
		String TaxFromReturnPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TaxField","row pad12 padBottom0","h4"));
		String TotalAmountFromReturnPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TotalAmountField","row pad12 padBottom0","h4"));
		String NoOfLinesFromReturnPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("LinesField","row pad12 padBottom0","h4"));

		double SubTotal = Double.parseDouble(SubTotalFromReturnPge.replaceAll("[^\\d.]", "").trim());
		double Tax = Double.parseDouble(TaxFromReturnPge.replaceAll("[^\\d.]", "").trim());
		double ExpectedTotalAmount = SubTotal + Tax;
		double TotalAmount = Double.parseDouble(TotalAmountFromReturnPge.replaceAll("[^\\d.]", "").trim());
		int NoOfLines = Integer.parseInt(NoOfLinesFromReturnPge.replaceAll("[^\\d]", "").trim());

		asrt.assertEquals(NoOfLines, ActualNoOfProductLines, "No: of Lines mismatch: Expected " +NoOfLines+ "but got" +ActualNoOfProductLines+ " in Billing screen");
		asrt.assertEquals(SubTotal, TotalWithoutTax,1.0,  "SubTotal mismatch: Expected " +SubTotal+ "but got"+TotalWithoutTax+ " in Billing screen");
		asrt.assertEquals(Tax, TaxAmount,1.0, "Tax mismatch: Expected "+Tax+" but got "+TaxAmount+" in Billing screen");
		asrt.assertEquals(TotalAmount, ExpectedTotalAmount,1.0,  "TotalAmount mismatch: Expected "+TotalAmount+" but got "+ExpectedTotalAmount+" in Billing screen");

		//Step 10 : Click on the Amount
		//Step 11 : Select a Payment method as convert advance
		//Step 12 : Select transaction type as normal
		//Step 13 : Select sales person
		//Step 14 : Select the due date
		//Step 15 : Enter remarks
		//Step 16 : click on nominee details
		//Step 6.1 : Enter nominee name
		//Step 6.2 : Select nominee relation
		//Step 6.3 : Click on check box(Same as customer address)
		//Step 6.4 : Click on OK button
		appUtils.PaymentModeForDiffTransactions(NormalSalesReturnUncutorPreciaTestData.TC35_PaymentMode, "", "",
				NormalSalesReturnUncutorPreciaTestData.TC35_SalePersonNumber,
				NormalSalesReturnUncutorPreciaTestData.TC35_SalePersonName,
				NormalSalesReturnUncutorPreciaTestData.TC35_DueYear,
				NormalSalesReturnUncutorPreciaTestData.TC35_NomineeName,
				NormalSalesReturnUncutorPreciaTestData.TC35_NomineeRelation);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));

		//PDF validation ADVANCE RECEIPT VOUCHER details : Towards the advance against purchase of approximate value, Fixed Gold rate, Deposit Type, Advance Received
		Map<String, String> Result = pdfUtils.NormalAdvancePdfValidation();
		System.out.println("Receipt Id: " + Result.get("ReceiptId"));
		System.out.println("Max Gold Rate: " + Result.get("MaxGoldRate"));
		System.out.println("Advance Received: " + Result.get("AdvanceReceived"));
		System.out.println("Payment Amount: " + Result.get("PaymentAmount"));
		System.out.println("Approx Weight: " + Result.get("ApproxWeight"));
		System.out.println("Fixed Gold Rate: " + Result.get("FixedGoldRate"));
		System.out.println("Deposit Type: " + Result.get("DepositType"));
		System.out.println("Payment Mode: " + Result.get("PaymentMode"));
		System.out.println("Payment Amount: " + Result.get("PaymentAmount"));

		String AdvanceReceived = Result.get("AdvanceReceived");
		String FixedGoldRate = Result.get("FixedGoldRate");
		String MaxGoldRate = Result.get("MaxGoldRate");
		String ApproxWeight = Result.get("ApproxWeight");
		String DepositType = Result.get("DepositType");

		double ExpectedAdvanceReceived = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
		double ExpectedFixedGoldRate = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
		double ActualMaxGoldRate = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());
		System.out.println("TotAmnt "+ExpectedTotalAmount);
		System.out.println("ExpAdvanceReceived "+ExpectedAdvanceReceived);
		asrt.assertEquals(ExpectedTotalAmount, ExpectedAdvanceReceived,1.0, "Total amount mismatch: Expected "+ExpectedTotalAmount+" but got "+ExpectedAdvanceReceived+"in the Normal Advance Pdf");

		System.out.println("ActMaxGoldRate "+ActualMaxGoldRate);
		System.out.println("ExpFixedGoldRate "+ExpectedFixedGoldRate);
		asrt.assertEquals(ActualMaxGoldRate, ExpectedFixedGoldRate,1.0, "Fixed Gold Rate mismatch: Expected "+ExpectedFixedGoldRate+" but got "+ActualMaxGoldRate+"in the Normal Advance Pdf");
		asrt.assertEquals(NormalSalesReturnUncutorPreciaTestData.TC35_DepositType, DepositType, "Deposit Type mismatch: Expected "+NormalSalesReturnUncutorPreciaTestData.TC35_DepositType+" but got "+DepositType+ "in the Normal Advance Pdf");

		if(ApproxWeight != "" || ApproxWeight != null)
		{
			Double ApproxWeightValue = ExpectedAdvanceReceived/ActualMaxGoldRate;
			System.out.println("ApproxWeightValue "+ApproxWeightValue);
			BigDecimal RoundedApproxWeightValue = new BigDecimal(ApproxWeightValue).setScale(3, RoundingMode.HALF_UP);
			System.out.println("RoundedApproxWeightValue "+RoundedApproxWeightValue);

			double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());
			System.out.println("ApproxWeightPDFValue "+ApproxWeightPdfValue);
			asrt.assertEquals(ApproxWeightPdfValue, RoundedApproxWeightValue.doubleValue(),1.0, "Mismatch of ApproxWeightValue: Expected: "+ApproxWeightPdfValue+" but got "+RoundedApproxWeightValue.doubleValue()+"in the Normal Advance Pdf");
		}



		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20"));
		Thread.sleep(3000);

		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	//=============Challenge in RG Gold Rate Step:9,10===========================
	// Test Case Title : Sales return of uncut precia with multiple category item
	// Automation ID : TC37
	// Author : Christy Reji
	// </summary>

	public void TC37_NormalSalesReturnUncutorPrecia() throws Exception {

		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSale(NormalSalesReturnUncutorPreciaTestData.TC37_CustomerNo, NormalSalesReturnUncutorPreciaTestData.TC37_SkuList);
		String ActualCustomerName          = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		String InvoiceNo            = (String) InvoiceDetails.get("InvoiceNo");
		String TotalNetWeightReturn = (String) InvoiceDetails.get("TotalNetWeight");
		String SkuPurityReturn      = (String) InvoiceDetails.get("FirstSkuPurity");
		String SkuGoldRateReturn    = (String) InvoiceDetails.get("FirstSkuGoldRate");
		String TotalRgWeightReturn  = (String) InvoiceDetails.get("TotalRgWeight");
		// Extract SKU-wise map
		Map<String, String> SkuWiseData = (Map<String, String>) InvoiceDetails.get("SKUWiseDetails");

		String Sku1NetWeight        = SkuWiseData.getOrDefault("SKU_1_NetWeight", "N/A");
		String Sku1GrossWeight      = SkuWiseData.getOrDefault("SKU_1_GrossWeight", "N/A");
		String Sku1StoneWeight      = SkuWiseData.getOrDefault("SKU_1_StoneWeight", "N/A");
		String Sku1Total	        = SkuWiseData.getOrDefault("SKU_1_Total", "N/A");
		String Sku2NetWeight        = SkuWiseData.getOrDefault("SKU_2_NetWeight", "N/A");
		String Sku2GrossWeight      = SkuWiseData.getOrDefault("SKU_2_GrossWeight", "N/A");
		String Sku2StoneWeight      = SkuWiseData.getOrDefault("SKU_2_StoneWeight", "N/A");
		String Sku2Total            = SkuWiseData.getOrDefault("SKU_2_Total", "N/A");

		Map<String, String> ScannedSkuDataMapSale = (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");

		String ErpGoldRateFor24K    = erpUtils.SetMetalRateInERP(SkuGoldRateReturn, SkuPurityReturn);
		Thread.sleep(12000); //Wait needed for ERP Gold Rate to Change

		//Step 3 : Select the customer 
		//Step 4 : Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturnUncutorPreciaTestData.TC37_CustomerNo,UtilityTestData.MenuBarOptions[5]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product (uncut & precia) one by one and click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		ReturnDetails Details       = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturnUncutorPreciaTestData.TC37_ReturnProducts);
		String ExpectedCustomerName = NormalSalesReturnUncutorPreciaTestData.TC37_CustomerName;

		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"ExpectedCustomerNameFrom MethodInvoicePdf: " + ActualCustomerName + ", but found: " + ExpectedCustomerName);

		//Step 9  :  Scan multiple SKU (Diamond,Uncut, Precia)
		//Step 10 :  Click on add to cart button
		//Expected Result: Check calculationNote:Billling Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		// Uncut SKUs
		List<String> UncutSkus = appUtils.SelectMultipleSKUs(NormalSalesReturnUncutorPreciaTestData.TC37_SkuCount,NormalSalesReturnUncutorPreciaTestData.TC37_TransferType,
				NormalSalesReturnUncutorPreciaTestData.TC37_FromCounterUncut,NormalSalesReturnUncutorPreciaTestData.TC37_MetalType);
		SkuList.addAll(UncutSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); 

		//Precia SKUs
		List<String> PreciaSku = appUtils.SelectMultipleSKUs(NormalSalesReturnUncutorPreciaTestData.TC37_SkuCount,NormalSalesReturnUncutorPreciaTestData.TC37_TransferType,
				NormalSalesReturnUncutorPreciaTestData.TC37_FromCounterPrecia,NormalSalesReturnUncutorPreciaTestData.TC37_MetalType);
		SkuList.addAll(PreciaSku);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); 

		//Diamond
		List<String> DiamondSku = appUtils.SelectMultipleSKUs(NormalSalesReturnUncutorPreciaTestData.TC37_SkuCount,NormalSalesReturnUncutorPreciaTestData.TC37_TransferType,
				NormalSalesReturnUncutorPreciaTestData.TC37_FromCounterDiamond,NormalSalesReturnUncutorPreciaTestData.TC37_MetalType);
		SkuList.addAll(DiamondSku);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); // go to Transaction

		String ErpGoldRate24k = ErpGoldRateFor24K;
		erpUtils.ResetRemainingReturnWeight();
		for (String Sku : SkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			erpUtils.ErpSkuIngredientItemCalculation(CurrentSkuData, SkuCounter,TotalRgWeightReturn,SkuPurityReturn,SkuGoldRateReturn,ErpGoldRate24k);
			SkuCounter++;
		}

		//TranscationPageVerifyLines
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal		 		= TransactionDataLine.get("Subtotal");
		String Discount 			= TransactionDataLine.get("Discount");
		String TotalGrossWeight 	= TransactionDataLine.get("GrossWeight");
		String TotalNewNetWeight 	= TransactionDataLine.get("NetWeight");
		String Tax 					= TransactionDataLine.get("Tax");
		String TotalAmount 			= TransactionDataLine.get("TotalAmount");
		String NetTotal			    = TransactionDataLine.get("NetTotal");
		String LinesCount		    = TransactionDataLine.get("LinesCount");

		// Step 11: Click on Save to estimation button
		// Step 12: Save Esimate
		// Expected Result:Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_abc("buttonsContainer positionRelative","Back" ));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String ActualEstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// PDF Verification Step
		Thread.sleep(4000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC37ProformaInvoice");
		Thread.sleep(2000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst                 = Tax;
		String TaxableValue		   = SubTotal;
		String InvoiceTotal        = TotalAmount;
		String TotalAmnt           = SubTotal;
		double TotalNetWeight      = Double.parseDouble(TotalNewNetWeight) - Double.parseDouble(TotalNetWeightReturn);
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				TotalAmnt
				//Double.parseDouble(TotalGrossWeight),
				//TotalNetWeight
				);

		// Step 13: Recall estimate from cashier screen
		// Expected Result: Verify whether the recalled item is the same as the item in the cart.(Estimation No Check)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		String ExpectedEstmnNumber= base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));

		Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+" in Recall Estimation page"); 

		// Step 14: Select the Transaction Type as Sales Return
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnUncutorPreciaTestData.TC37_TransationType);

		// Step 15: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 16: Select any Sales Representative
		// Expected Result: Cashier Screen after recall*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		base.setZoom(driver, 60);
		Thread.sleep(3000);
		Map<String, String> TransactionRecallDataLineReturn = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotalReturn 			= TransactionRecallDataLineReturn.get("Subtotal");
		String DiscountReturn 			= TransactionRecallDataLineReturn.get("Discount");
		String TotalGrossWeightReturn   = TransactionRecallDataLineReturn.get("GrossWeight");
		String TotalNewNetWeightReturn  = TransactionRecallDataLineReturn.get("NetWeight");
		String TaxReturn 				= TransactionRecallDataLineReturn.get("Tax");
		String TotalAmountReturn	    = TransactionRecallDataLineReturn.get("TotalAmount").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
		String NetTotalReturn 			= TransactionRecallDataLineReturn.get("NetTotal").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
		String LinesCountReturn 		= TransactionRecallDataLineReturn.get("LinesCount");

		erpUtils.ErpValidateTransactionLineCalculation(TransactionRecallDataLineReturn, ScannedSkuDataMapSale);

		// Step 17:  Click on the Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		// Step 18: post sales return bill
		// Expected Result: Check Return Invoice details
		pdfUtils.DownloadAndRenameLatestPDF("TC37_SaleReturnInvoice");

		Map<String, String> Result = pdfUtils.SalesReturnPDFCredit();
		String ExpSerialNoReturnSale         = Result.get("SerialNo");
		String ExpTotalProductsReturnSale    = Result.get("TotalProducts");
		String ExpTotalQtyReturnSale         = Result.get("TotalQty");
		String ExpGrossAmountReturnSale      = Result.get("GrossAmount");
		String ExpTotalNetWtReturnSale       = Result.get("TotalNetWt");
		String ExpTotalGrossWtReturnSale     = Result.get("TotalGrossWt");
		String ExpTotalNetValueReturnSale    = Result.get("TotalNetValue");
		String ExpTaxableValueReturnSale     = Result.get("TaxableValue");
		String ExpAdjustmentReturnSale       = Result.get("PaymentDetails");
		String ExpTotalValueReturnSale       = Result.get("TotalValue");
		int TotalProductsSale                = Integer.parseInt(Result.get("TotalProducts"));

		// Loop through each SKU/Product
		for (int i = 1; i <= TotalProductsSale; i++) {
			String ExpectedGrossWt   		= Result.get("Product" + i + ".GrossWt");
			String ExpectedStoneWt		    = Result.get("Product" + i + ".StoneWt");
			String ExpectedNetWt 			= Result.get("Product" + i + ".NetWt");
			String ExpectedAmount 			= Result.get("Product" + i + ".Amount");
			String ActualGrossWt 			= SkuWiseData.getOrDefault("SKU_" + i + "_GrossWeight", "N/A");
			String ActualStoneWt 			= SkuWiseData.getOrDefault("SKU_" + i + "_StoneWeight", "N/A");
			String ActualNetWt 				= SkuWiseData.getOrDefault("SKU_" + i + "_NetWeight", "N/A");
			String ActualAmount 			= SkuWiseData.getOrDefault("SKU_" + i + "_Total", "N/A");

			Assert.assertEquals(ActualGrossWt, ExpectedGrossWt, "Mismatch in Gross Weight for SKU_" + i +"in PDF Sale Return Invoice details");
			Assert.assertEquals(ActualStoneWt, ExpectedStoneWt, "Mismatch in Stone Weight for SKU_" + i+"in PDF Sale Return Invoice details");
			Assert.assertEquals(ActualNetWt, ExpectedNetWt, "Mismatch in Net Weight for SKU_" + i+"in PDF Sale Return Invoice details");
			Assert.assertEquals(ActualAmount, ExpectedAmount, "Mismatch in Total Amount for SKU_" + i+"in PDF Sale Return Invoice details");
		}
		double ActualTaxableValue 		   = Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedTaxableValue  	   = Double.parseDouble(ExpTaxableValueReturnSale);

		double ActualGrossAmount 		   = Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedGrossAmount 		   = Double.parseDouble(ExpGrossAmountReturnSale);

		double ActualTotalGrossWeight      = Math.abs(Double.parseDouble(((String) InvoiceDetails.get("TotalGrossWeight")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedTotalGrossWeight    = Double.parseDouble(ExpTotalGrossWtReturnSale);

		double ActualNetWeight			   = Double.parseDouble(((String) InvoiceDetails.get("TotalNetWeight")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		double ExpectedNetWeight 		   = Double.parseDouble(ExpTotalNetWtReturnSale);

		double ActualNetTotal 			   = Double.parseDouble(((String) InvoiceDetails.get("NetTotal")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		double ExpectedNetTotal 		   = Double.parseDouble(ExpTotalNetValueReturnSale);

		Assert.assertEquals(ActualTaxableValue, ExpectedTaxableValue,1, "Mismatch in Taxable Value Actual "+ActualTaxableValue+" and Expected Taxable Value "+ExpectedTaxableValue+"in PDF Sale Return Invoice details");
		Assert.assertEquals(ActualGrossAmount, ExpectedGrossAmount,1, "Mismatch in Gross Amount Actual "+ActualGrossAmount+" and Expected Gross Amount "+ExpectedGrossAmount+"in PDF Sale Return Invoice details");
		Assert.assertEquals(LinesCountReturn, ExpTotalQtyReturnSale, "Mismatch in No of Product Lines Actual "+LinesCountReturn+" and Expected No of Product Lines "+ExpTotalProductsReturnSale+"in PDF Sale Return Invoice details");
		Assert.assertEquals(ActualNetWeight, ExpectedNetWeight,1, "Mismatch in Total Net Weight Actual "+ActualNetWeight+" and Expected Net Weight "+ExpectedNetWeight+"in PDF Sale Return Invoice details");
		Assert.assertEquals(ActualTotalGrossWeight, ExpectedTotalGrossWeight,1, "Mismatch in Total Gross Weight Actual "+ActualTotalGrossWeight+" and Expected Total Gross Weight"+ExpectedTotalGrossWeight+"in PDF Sale Return Invoice details");
		Assert.assertEquals(ActualNetTotal, ExpectedNetTotal,1, "Mismatch in Net Total Actual "+ActualNetTotal+" and Expected Net Total "+ExpectedNetTotal+"in PDF Sale Return Invoice details");

		Thread.sleep(3000);
		// Step 19:  Again recall estimate from cashier screen
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Step 20: Select the Transaction Type as Sales Return
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnUncutorPreciaTestData.TC37_TransationType2);

		// Step 21: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 22: Select any Sales Representative
		base.setZoom(driver, 60);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		Map<String, String> TransactionRecallDataLineRecall = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotalRecall			 =   TransactionRecallDataLineRecall.get("Subtotal");
		String DiscountRecall 			 =   TransactionRecallDataLineRecall.get("Discount");
		String TotalGrossWeightRecall	 =   TransactionRecallDataLineRecall.get("GrossWeight");
		String TotalNetWeightRecall 	 =   TransactionRecallDataLineRecall.get("NetWeight");
		String TotalAmountRecall		 =   TransactionRecallDataLineRecall.get("TotalAmount");
		String NetTotalRecall			 =   TransactionRecallDataLineRecall.get("NetTotal");
		String LinesCountRecall			 =   TransactionRecallDataLineRecall.get("LinesCount");
		String AdjustmentReturn			 =   TransactionRecallDataLineReturn.get("Payments");

		// Step 23:  Click on the Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));

		// Step 24:  Select a Payment method(Cash or Card)
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentHDFCCard = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnUncutorPreciaTestData.TC37_ApprovalCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnUncutorPreciaTestData.TC37_ApprovalCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 25:  post sales return bill
		// Expected Result: Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice   		     = pdfUtils.DownloadAndRenameLatestPDF("TC37_SaleInvoice");
		Thread.sleep(2000);
		String SalesPdfPath  		     = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice;
		String GrossAmount    		     = SubTotalRecall;
		String TotalDiscount  		     = DiscountRecall;
		String TotalQtyPcs    		     = LinesCountRecall;
		String TaxableValueSale		     = TotalAmountRecall;
		String SanitizedNetTotalRecall   = NetTotalRecall.replaceAll("[^\\d.\\-]", "");
		String SanitizedAdjustmentReturn = AdjustmentReturn.replaceAll("[^\\d.\\-]", "");
		String NetValueSale              = String.format("%.2f", (Math.abs(Double.parseDouble(SanitizedNetTotalRecall)) + Math.abs(Double.parseDouble(SanitizedAdjustmentReturn))));
		String TotalDiamond              = "";
		String PaymentAmountHDFC         = PaymentHDFCCard;
		double TotalNewNetWeightDouble   = Double.parseDouble(TotalNewNetWeight);
		String TotalNetWt			     = TotalNetWeightRecall;
		String TotalGrossWt			     = TotalGrossWeightRecall;
		String Adjustment 			     = String.valueOf(Math.abs(Double.parseDouble(SanitizedAdjustmentReturn)));
		String PdfInvoiceNo              = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWt,
				TotalGrossWt,
				TaxableValueSale,
				NetValueSale,
				PaymentAmountHDFC,
				Adjustment,
				SkuList,
				ScannedSkuDataMap);

		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	
	// <summary>
	// Test Case Title : Sales return multiple Uncut with multiple sale
	// Automation ID : TC34
	// Author : Jhoncy Joseph
	// </summary>
	public void TC34_NormalSalesReturnUncutorPrecia() throws Exception {

		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSale(NormalSalesReturnUncutorPreciaTestData.TC34_CustomerNo, NormalSalesReturnUncutorPreciaTestData.TC34_ItemList);
		String ActualCustomerName          = pdfUtils.ExtractCustomerNameFromSaleInvoice();
		String InvoiceNo            = (String) InvoiceDetails.get("InvoiceNo");
		String TotalNetWeightReturn = (String) InvoiceDetails.get("TotalNetWeight");
		String SkuPurityReturn      = (String) InvoiceDetails.get("FirstSkuPurity");
		String SkuGoldRateReturn    = (String) InvoiceDetails.get("FirstSkuGoldRate");
		String TotalRgWeightReturn  = (String) InvoiceDetails.get("TotalRgWeight");

		// Extract SKU-wise map
		Map<String, String> SkuWiseData = (Map<String, String>) InvoiceDetails.get("SKUWiseDetails");
		Map<String, String> ScannedSkuDataMapSale = (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");
		Map<String, String> TransactionDataLineSale = (Map<String, String>) InvoiceDetails.get("TransactionDataLine");
		String LinesCountSale = TransactionDataLineSale.get("LinesCount");

		String ErpGoldRateFor24K= erpUtils.SetMetalRateInERP(SkuGoldRateReturn, SkuPurityReturn);

		Thread.sleep(120000); //Wait needed for ERP Gold Rate to Change

		//Step 3 : Select customer 
		//Step 4 : Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturnUncutorPreciaTestData.TC34_CustomerNo,UtilityTestData.MenuBarOptions[5]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product (multiple uncut) one by one and click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		ReturnDetails Details       = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturnUncutorPreciaTestData.TC34_ReturnProducts);
		String ExpectedCustomerName = NormalSalesReturnUncutorPreciaTestData.TC34_CustomerName;

		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"ExpectedCustomerNameFrom MethodInvoicePdf: " + ActualCustomerName + ", but found: " + ExpectedCustomerName);

		//Step 9  :  Scan multiple SKU (Gold)
		//Step 10 :  Click on add to cart button
		//Step 11: Choose a sales representative
		//Expected Result: Check calculationNote:Billing Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		// Gold SKUs
		List<String> GoldSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturnUncutorPreciaTestData.TC34_SkuCount,
				NormalSalesReturnUncutorPreciaTestData.TC34_TransferType,
				NormalSalesReturnUncutorPreciaTestData.TC34_FromCounter,
				NormalSalesReturnUncutorPreciaTestData.TC34_MetalType);
		SkuList.addAll(GoldSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); 

		erpUtils.ResetRemainingReturnWeight();
		for (String Sku : SkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			erpUtils.ErpSkuIngredientItemCalculation(CurrentSkuData, SkuCounter,TotalRgWeightReturn,SkuPurityReturn,SkuGoldRateReturn,ErpGoldRateFor24K);
			SkuCounter++;
		}	
		//TranscationPageVerifyLines
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal		 		= TransactionDataLine.get("Subtotal");
		String Discount 			= TransactionDataLine.get("Discount");
		String TotalGrossWeight 	= TransactionDataLine.get("GrossWeight");
		String TotalNewNetWeight 	= TransactionDataLine.get("NetWeight");
		String Tax 					= TransactionDataLine.get("Tax");
		String TotalAmount 			= TransactionDataLine.get("TotalAmount");
		String NetTotal			    = TransactionDataLine.get("NetTotal");
		String LinesCount		    = TransactionDataLine.get("LinesCount");
		Map<String, String> AdjustedScannedSkuMap = erpUtils.MergeScannedSkuDataMaps(ScannedSkuDataMapSale, ScannedSkuDataMap);
		erpUtils.ErpValidateTransactionLineCalculation(TransactionDataLine, AdjustedScannedSkuMap,LinesCountSale);

		String AmountDue = base.GetText(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		if (AmountDue != null && !AmountDue.trim().isEmpty()) {
			AmountDue = AmountDue.replaceAll("[^0-9.-]", "").trim();
			double NumericAmount = Double.parseDouble(AmountDue);
			System.out.println("The Amount: "+NumericAmount);
			if (NumericAmount < 0.00) {

				// Step 12: Click on Save to estimation button in billing screen
				// Step 13: Save Estimate
				// Expected Result:Verify the invoice details
				base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_abc("buttonsContainer positionRelative","Back" ));
				base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
				String ActualEstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

				// PDF Verification Step
				Thread.sleep(3000);
				String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC34_ProformaInvoice");
				Thread.sleep(2000);
				String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
				String Gst                 = Tax;
				String TaxableValue		   = SubTotal;
				String InvoiceTotal        = TotalAmount;
				String TotalAmnt           = SubTotal;
				double TotalNetWeight      = Double.parseDouble(TotalNewNetWeight) - Double.parseDouble(TotalNetWeightReturn);
				pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
						ProFormaInvoicePath,
						TaxableValue,
						Gst,
						InvoiceTotal,
						NetTotal,
						TotalAmnt
						);

				// Step 14: Recall estimate from cashier screen
				// Expected Result: Verify whether the recalled item is the same as the item in the cart.(Estimation No Check)
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
				base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				Thread.sleep(2000);
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
				Thread.sleep(2000);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
				String ExpectedEstmnNumberSalesReturn= base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));

				Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumberSalesReturn,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumberSalesReturn+" in Recall Estimation page"); 

				// Step 15: Select the Transaction Type as Sales Return
				base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
				base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnUncutorPreciaTestData.TC34_TransactionType1);

				// Step 16: Click on Process Estimation
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

				// Expected Result: Cashier Screen after recall*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
				base.setZoom(driver, 60);
				Thread.sleep(3000);
				Map<String, String> TransactionRecallDataLineReturn = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
				String SubTotalReturn 			= TransactionRecallDataLineReturn.get("Subtotal");
				String DiscountReturn 			= TransactionRecallDataLineReturn.get("Discount");
				String TotalGrossWeightReturn   = TransactionRecallDataLineReturn.get("GrossWeight");
				String TotalNewNetWeightReturn  = TransactionRecallDataLineReturn.get("NetWeight");
				String TaxReturn 				= TransactionRecallDataLineReturn.get("Tax");
				String TotalAmountReturn	    = TransactionRecallDataLineReturn.get("TotalAmount").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
				String NetTotalReturn 			= TransactionRecallDataLineReturn.get("NetTotal").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
				String LinesCountReturn 		= TransactionRecallDataLineReturn.get("LinesCount");

				mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLineReturn, ScannedSkuDataMapSale);

				String TransAmountDue = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_AmountDue("h1","cartView_amountDueLink"));
				double TransTotalAmount = Double.parseDouble(TransAmountDue.replaceAll("[^\\d.]", "").trim());

				//Step 17.Click on the amount
				//Step 18.Click on Convert to Advance
				//Step 18.1.Select Deposit type as ""Normal""
				//Step 18.2.Select Sales person
				//Step 18.3.Select Due date
				//Step 18.4.Enter Remarks
				//Step 18.5.Click on Add Nominee details
				//Step 18.6.Enter Nominee name
				//Step 18.7.Select Nominee relation
				//Step 18.8.Click on check box(Same as customer address)
				//Step 18.9.Click on OK button
				//Step 19.Click on Deposit button
				//Step 20.Click on the Amount for Receipt
				//Expected:Check Return Invoice details
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
				Thread.sleep(2000);
				base.buttonClick(LoginPageObj.Edt_AlertText(NormalSalesReturnUncutorPreciaTestData.TC34_PaymentMode2));	
				Thread.sleep(4000);
				base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(NormalSalesReturnUncutorPreciaTestData.TC34_SalePersonNumber), NormalSalesReturnUncutorPreciaTestData.TC34_SalePersonName);
				base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_DrpdwnYear("win-datepicker-year win-dropdown win-order2 displayOrder3"), NormalSalesReturnUncutorPreciaTestData.TC34_DueYear);
				base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Remarks("6"));
				base.setData(NormalSaleGoldAndSilverObj.Ele_Remarks("6"), UtilityTestData.Remarks);
				base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
				Thread.sleep(1000);
				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Nominee name"),NormalSalesReturnUncutorPreciaTestData.TC34_NomineeName);
				base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Nominee relation"), NormalSalesReturnUncutorPreciaTestData.TC34_NomineeRelation);
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
				base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));	
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
				Thread.sleep(3000);	

				base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
				base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
				Thread.sleep(8000);
				base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
				base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
				Map<String, String> Result = pdfUtils.NormalAdvancePdfValidation();	

				String AdvanceReceived = Result.get("AdvanceReceived");
				String FixedGoldRate = Result.get("FixedGoldRate");
				String MaxGoldRate = Result.get("MaxGoldRate");
				String ApproxWeight = Result.get("ApproxWeight");
				String DepositType = Result.get("DepositType");
				double PdfAdvanceReceived = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
				double PdfFixedGoldRate = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
				double PdfMaxGoldRate = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());

				asrt.assertEquals(PdfAdvanceReceived,TransTotalAmount,1.0, "Mismatch in total amount in billing screen actual "+PdfAdvanceReceived+" and Expected "+TransTotalAmount+"in Normal Advance PDF");
				asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,1.0, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
				asrt.assertEquals(NormalSalesReturnUncutorPreciaTestData.TC34_DepositType, DepositType, "Mismatch in Deposit Type Actual "+NormalSalesReturnUncutorPreciaTestData.TC34_DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

				Double WeightValue = PdfAdvanceReceived/PdfMaxGoldRate;
				BigDecimal CalcWeightValue = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
				double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

				asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),1.0, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");

				pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
				base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));

				//Step 21.In cash, click on ""Recall Estimate""
				//Step 22.Select the estimate, Click on Recall Estimation
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
				base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				Thread.sleep(2000);
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
				Thread.sleep(2000);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
				String ExpectedEstmnNumberSales= base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));

				Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumberSales,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumberSales+" in Recall Estimation page"); 

				// Step 23:Select transaction type as ""Sales"" and click on Process estimation
				base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
				base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnUncutorPreciaTestData.TC34_TransactionType2);

				//Step 24: Choose a sales representative
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

				appUtils.HandleSpecifyRateModalInRecall();

				Map<String, String> TransactionRecallDataLineRecall = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
				String SubTotalRecall			 =   TransactionRecallDataLineRecall.get("Subtotal");
				String DiscountRecall 			 =   TransactionRecallDataLineRecall.get("Discount");
				String TotalGrossWeightRecall	 =   TransactionRecallDataLineRecall.get("GrossWeight");
				String TotalNetWeightRecall 	 =   TransactionRecallDataLineRecall.get("NetWeight");
				String TotalAmountRecall		 =   TransactionRecallDataLineRecall.get("TotalAmount");
				String NetTotalRecall			 =   TransactionRecallDataLineRecall.get("NetTotal");
				String LinesCountRecall			 =   TransactionRecallDataLineRecall.get("LinesCount");
				String AdjustmentReturn			 =   TransactionRecallDataLineReturn.get("Payments");

				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

				// Step 25:  post sales return bill
				// Expected Result: Check final invoice details * save receipt id for future reference
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
				Thread.sleep(3000);
				String FinalInvoice   		     = pdfUtils.DownloadAndRenameLatestPDF("TC34SaleInvoice");
				Thread.sleep(2000);
				String SalesPdfPath  		     = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice;
				String GrossAmount    		     = SubTotalRecall;
				String TotalDiscount  		     =DiscountRecall;
				String TotalQtyPcs    		     =LinesCountRecall;
				String TaxableValueSale		     =TotalAmountRecall;
				String SanitizedNetTotalRecall   = NetTotalRecall.replaceAll("[^\\d.\\-]", "");
				String SanitizedAdjustmentReturn = AdjustmentReturn.replaceAll("[^\\d.\\-]", "");
				String NetValueSale              = String.format("%.2f", (Math.abs(Double.parseDouble(SanitizedNetTotalRecall)) + Math.abs(Double.parseDouble(SanitizedAdjustmentReturn))));
				String TotalDiamond              ="";
				String PaymentHDFCCard           ="";
				double TotalNewNetWeightDouble   = Double.parseDouble(TotalNewNetWeight);
				String TotalNetWt			     =TotalNetWeightRecall;
				String TotalGrossWt			     =TotalGrossWeightRecall;
				String Adjustment 			     = String.valueOf(Math.abs(Double.parseDouble(SanitizedAdjustmentReturn)));
				String PdfInvoiceNo              = pdfUtils.SaleInvoicePdfAdjustmentValidation(
						SalesPdfPath,
						GrossAmount,
						TotalDiscount,
						TotalDiamond,
						TotalQtyPcs,
						TotalNetWt,
						TotalGrossWt,
						TaxableValueSale,
						NetValueSale,
						PaymentHDFCCard,
						Adjustment,
						SkuList,
						ScannedSkuDataMap);
				pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
			}
			else if (NumericAmount > 0.00) {

				// Step 12: Click on Save to estimation button in billing screen
				// Step 13: Save Estimate
				// Expected Result:Verify the invoice details
				base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_abc("buttonsContainer positionRelative","Back" ));
				base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
				String ActualEstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
				// PDF Verification Step
				Thread.sleep(3000);
				String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC34_ProformaInvoice");
				Thread.sleep(2000);
				String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
				String Gst                 = Tax;
				String TaxableValue		   = SubTotal;
				String InvoiceTotal        = TotalAmount;
				String TotalAmnt           = SubTotal;
				double TotalNetWeight      = Double.parseDouble(TotalNewNetWeight) - Double.parseDouble(TotalNetWeightReturn);
				pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
						ProFormaInvoicePath,
						TaxableValue,
						Gst,
						InvoiceTotal,
						NetTotal,
						TotalAmnt
						);

				// Step 14: Recall estimate from cashier screen
				// Expected Result: Verify whether the recalled item is the same as the item in the cart.(Estimation No Check)
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
				base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				Thread.sleep(2000);
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
				Thread.sleep(2000);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
				String ExpectedEstmnNumberSalesReturn= base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));

				Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumberSalesReturn,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumberSalesReturn+" in Recall Estimation page"); 

				// Step 15: Select the Transaction Type as Sales Return
				base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
				base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnUncutorPreciaTestData.TC34_TransactionType1);

				// Step 16: Click on Process Estimation
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

				// Step 17: Select any Sales Representative
				// Expected Result: Cashier Screen after recall*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
				base.setZoom(driver, 60);
				Thread.sleep(3000);
				Map<String, String> TransactionRecallDataLineReturn = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
				String SubTotalReturn 			= TransactionRecallDataLineReturn.get("Subtotal");
				String DiscountReturn 			= TransactionRecallDataLineReturn.get("Discount");
				String TotalGrossWeightReturn   = TransactionRecallDataLineReturn.get("GrossWeight");
				String TotalNewNetWeightReturn  = TransactionRecallDataLineReturn.get("NetWeight");
				String TaxReturn 				= TransactionRecallDataLineReturn.get("Tax");
				String TotalAmountReturn	    = TransactionRecallDataLineReturn.get("TotalAmount").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
				String NetTotalReturn 			= TransactionRecallDataLineReturn.get("NetTotal").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
				String LinesCountReturn 		= TransactionRecallDataLineReturn.get("LinesCount");

				mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLineReturn, ScannedSkuDataMapSale);

				// Step 18:  Click on the Amount
				// Expected Result: Check Return Invoice details
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
				pdfUtils.DownloadAndRenameLatestPDF("TC34SaleReturnInvoice");

				Map<String, String> Result = pdfUtils.SalesReturnPDFCredit();
				String ExpSerialNoReturnSale         = Result.get("SerialNo");
				String ExpTotalProductsReturnSale    = Result.get("TotalProducts");
				String ExpTotalQtyReturnSale         = Result.get("TotalQty");
				String ExpGrossAmountReturnSale      = Result.get("GrossAmount");
				String ExpTotalNetWtReturnSale       = Result.get("TotalNetWt");
				String ExpTotalGrossWtReturnSale     = Result.get("TotalGrossWt");
				String ExpTotalNetValueReturnSale    = Result.get("TotalNetValue");
				String ExpTaxableValueReturnSale     = Result.get("TaxableValue");
				String ExpAdjustmentReturnSale       = Result.get("PaymentDetails");
				String ExpTotalValueReturnSale       = Result.get("TotalValue");
				int TotalProductsSale = Integer.parseInt(Result.get("TotalProducts"));

				// Loop through each SKU/Product
				for (int i = 1; i <= TotalProductsSale; i++) {
					String ExpectedGrossWt   		= Result.get("Product" + i + ".GrossWt");
					String ExpectedStoneWt		    = Result.get("Product" + i + ".StoneWt");
					String ExpectedNetWt 			= Result.get("Product" + i + ".NetWt");
					String ExpectedAmount 			= Result.get("Product" + i + ".Amount");
					String ActualGrossWt 			= SkuWiseData.getOrDefault("SKU_" + i + "_GrossWeight", "N/A");
					String ActualStoneWt 			= SkuWiseData.getOrDefault("SKU_" + i + "_StoneWeight", "N/A");
					String ActualNetWt 				= SkuWiseData.getOrDefault("SKU_" + i + "_NetWeight", "N/A");
					String ActualAmount 			= SkuWiseData.getOrDefault("SKU_" + i + "_Total", "N/A");

					Assert.assertEquals(ActualGrossWt, ExpectedGrossWt, "Mismatch in Gross Weight for SKU_" + i +"in PDF Sale Return Invoice details");
					Assert.assertEquals(ActualStoneWt, ExpectedStoneWt, "Mismatch in Stone Weight for SKU_" + i+"in PDF Sale Return Invoice details");
					Assert.assertEquals(ActualNetWt, ExpectedNetWt, "Mismatch in Net Weight for SKU_" + i+"in PDF Sale Return Invoice details");
					Assert.assertEquals(ActualAmount, ExpectedAmount, "Mismatch in Total Amount for SKU_" + i+"in PDF Sale Return Invoice details");
				}
				double ActualTaxableValue 		= Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim()));
				double ExpectedTaxableValue  	= Double.parseDouble(ExpTaxableValueReturnSale);

				double ActualGrossAmount 		= Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim()));
				double ExpectedGrossAmount 		= Double.parseDouble(ExpGrossAmountReturnSale);

				double ActualTotalGrossWeight   = Math.abs(Double.parseDouble(((String) InvoiceDetails.get("TotalGrossWeight")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim()));
				double ExpectedTotalGrossWeight = Double.parseDouble(ExpTotalGrossWtReturnSale);

				double ActualNetWeight			= Double.parseDouble(((String) InvoiceDetails.get("TotalNetWeight")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
				double ExpectedNetWeight 		= Double.parseDouble(ExpTotalNetWtReturnSale);

				double ActualNetTotal 			= Double.parseDouble(((String) InvoiceDetails.get("NetTotal")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
				double ExpectedNetTotal 		= Double.parseDouble(ExpTotalNetValueReturnSale);

				Assert.assertEquals(ActualTaxableValue, ExpectedTaxableValue,1, "Mismatch in Taxable Value Actual "+ActualTaxableValue+" and Expected Taxable Value "+ExpectedTaxableValue+"in PDF Sale Return Invoice details");
				Assert.assertEquals(ActualGrossAmount, ExpectedGrossAmount,1, "Mismatch in Gross Amount Actual "+ActualGrossAmount+" and Expected Gross Amount "+ExpectedGrossAmount+"in PDF Sale Return Invoice details");
				Assert.assertEquals(LinesCountReturn, ExpTotalQtyReturnSale, "Mismatch in No of Product Lines Actual "+LinesCountReturn+" and Expected No of Product Lines "+ExpTotalProductsReturnSale+"in PDF Sale Return Invoice details");
				Assert.assertEquals(ActualNetWeight, ExpectedNetWeight,1, "Mismatch in Total Net Weight Actual "+ActualNetWeight+" and Expected Net Weight "+ExpectedNetWeight+"in PDF Sale Return Invoice details");
				Assert.assertEquals(ActualTotalGrossWeight, ExpectedTotalGrossWeight,1, "Mismatch in Total Gross Weight Actual "+ActualTotalGrossWeight+" and Expected Total Gross Weight"+ExpectedTotalGrossWeight+"in PDF Sale Return Invoice details");
				Assert.assertEquals(ActualNetTotal, ExpectedNetTotal,1, "Mismatch in Net Total Actual "+ActualNetTotal+" and Expected Net Total "+ExpectedNetTotal+"in PDF Sale Return Invoice details");

				//Step 21.In cash, click on "Recall Estimate"
				//Step 22.Select the estimate, Click on Recall Estimation
				//Expected Result: Verify whether the recalled item is the same as the item in the cart.(Estimation No Check)
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
				base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				Thread.sleep(2000);
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
				Thread.sleep(2000);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
				String ExpectedEstmnNumberSales= base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));

				Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumberSales,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumberSales+" in Recall Estimation page"); 

				// Step 23:Select transaction type as ""Sales"" and click on Process estimation
				base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
				base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), "Sales");

				//Step 24: Choose a sales representative
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
				appUtils.HandleSpecifyRateModalInRecall();

				Map<String, String> TransactionRecallDataLineRecall = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
				String SubTotalRecall			 =   TransactionRecallDataLineRecall.get("Subtotal");
				String DiscountRecall 			 =   TransactionRecallDataLineRecall.get("Discount");
				String TotalGrossWeightRecall	 =   TransactionRecallDataLineRecall.get("GrossWeight");
				String TotalNetWeightRecall 	 =   TransactionRecallDataLineRecall.get("NetWeight");
				String TotalAmountRecall		 =   TransactionRecallDataLineRecall.get("TotalAmount");
				String NetTotalRecall			 =   TransactionRecallDataLineRecall.get("NetTotal");
				String LinesCountRecall			 =   TransactionRecallDataLineRecall.get("LinesCount");
				String AdjustmentReturn			 =   TransactionRecallDataLineReturn.get("Payments");

				//Step 25:Click on the Amount For printing Receipt
				//Expected Result: Check final invoice details * save receipt id for future reference
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));

				base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
				String PaymentHDFCCard = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
				base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnUncutorPreciaTestData.TC37_ApprovalCode);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
				base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnUncutorPreciaTestData.TC37_ApprovalCode);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
				Thread.sleep(3000);
				String FinalInvoice   		     = pdfUtils.DownloadAndRenameLatestPDF("TC34SaleInvoice");
				Thread.sleep(2000);
				String SalesPdfPath  		     = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice;
				String GrossAmount    		     = SubTotalRecall;
				String TotalDiscount  		     =DiscountRecall;
				String TotalQtyPcs    		     =LinesCountRecall;
				String TaxableValueSale		     =TotalAmountRecall;
				String SanitizedNetTotalRecall   = NetTotalRecall.replaceAll("[^\\d.\\-]", "");
				String SanitizedAdjustmentReturn = AdjustmentReturn.replaceAll("[^\\d.\\-]", "");
				String NetValueSale              = String.format("%.2f", (Math.abs(Double.parseDouble(SanitizedNetTotalRecall)) + Math.abs(Double.parseDouble(SanitizedAdjustmentReturn))));
				String TotalDiamond              ="";
				String PaymentAmountHDFC         =PaymentHDFCCard;
				double TotalNewNetWeightDouble   = Double.parseDouble(TotalNewNetWeight);
				String TotalNetWt			     =TotalNetWeightRecall;
				String TotalGrossWt			     =TotalGrossWeightRecall;
				String Adjustment 			     = String.valueOf(Math.abs(Double.parseDouble(SanitizedAdjustmentReturn)));
				String PdfInvoiceNo              = pdfUtils.SaleInvoicePdfAdjustmentValidation(
						SalesPdfPath,
						GrossAmount,
						TotalDiscount,
						TotalDiamond,
						TotalQtyPcs,
						TotalNetWt,
						TotalGrossWt,
						TaxableValueSale,
						NetValueSale,
						PaymentAmountHDFC,
						Adjustment,
						SkuList,
						ScannedSkuDataMap);
				pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
			}
		}
	}
	
	// <summary>
		// Test Case Title : Sales return with OU and a sale
		// Automation ID : TC38
		// Author: Gokul.P
		// </summary>
		public void TC38_NormalSalesReturnUncutorPrecia() throws Exception {
			//Step 1: Login to POS
			//Step 2: Click on Transaction button
			Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSale(NormalSalesReturnUncutorPreciaTestData.TC38_CustomerNo, NormalSalesReturnUncutorPreciaTestData.TC38_Products);
			String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

			String InvoiceNo            = (String) InvoiceDetails.get("InvoiceNo");
			String TotalNetWeightReturn = (String) InvoiceDetails.get("TotalNetWeight");
			String SkuPurityReturn      = (String) InvoiceDetails.get("FirstSkuPurity");
			String SkuGoldRateReturn    = (String) InvoiceDetails.get("FirstSkuGoldRate");
			String TotalRgWeightReturn  = (String) InvoiceDetails.get("TotalRgWeight");

			// Extract SKU-wise map
			Map<String, String> SkuWiseData = (Map<String, String>) InvoiceDetails.get("SKUWiseDetails");

			//  Print only SKU_1_NetWeight and SKU_1_GrossWeight
			String Sku1NetWeight   = SkuWiseData.getOrDefault("SKU_1_NetWeight", "N/A");
			String Sku1GrossWeight = SkuWiseData.getOrDefault("SKU_1_GrossWeight", "N/A");
			String Sku1StoneWeight = SkuWiseData.getOrDefault("SKU_1_StoneWeight", "N/A");
			String Sku1Total	   = SkuWiseData.getOrDefault("SKU_1_Total", "N/A");
			Map<String, String> ScannedSkuDataMapSale = (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");

			String ErpGoldRateFor24K= erpUtils.SetMetalRateInERP(SkuGoldRateReturn, SkuPurityReturn);
			Thread.sleep(120000); //Wait needed for ERP Gold Rate to Change

			//Step 3: Select customer
			//Step 4: Click on add to estimation button
			appUtils.SearchByCustomerID(NormalSalesReturnUncutorPreciaTestData.TC38_CustomerNo, UtilityTestData.MenuBarOptions[5]);

			//Step 5: Click on customer adjustment button
			//Step 6: Click on return transaction button at the billing screen
			//Step 7: Enter receipt number
			//Step 8: Select return product and click on return button
			//Expected: The product meant to be returned should be previously bought by the same customer.
			ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturnUncutorPreciaTestData.TC38_ProductsToReturn);
			asrt.assertEquals(NormalSalesReturnUncutorPreciaTestData.TC38_CustomerName,ActualCustomerName,"Customer name for sale is not the same as the customer name from return. " +"ExpectedCustomerNameFrom MethodInvoicePdf: " + ActualCustomerName + ", but found: " + NormalSalesReturnUncutorPreciaTestData.TC38_CustomerName);

			List<String> ReturnProductNames        = Details.productNames;
			List<String> ReturnProductQtyList      = Details.productQty;
			List<String> ReturnProductTotalList    = Details.productPrice;

			//Step 9: Scan sku		
			//Step 10: Click on add to cart button
			// 11. Choose a sales representative
			//Expected: Check calculation and No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX and Total Amount
			List<String> SaleProductNames         = new ArrayList<>();
			List<String> SaleProductQtyList       = new ArrayList<>();
			List<String> SaleProductTotalList     = new ArrayList<>();
			Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
			int SkuCounter = 1;

			List<String> SkuList = appUtils.SelectMultipleSKUs(
					NormalSalesReturnUncutorPreciaTestData.TC38_SaleProductCount,
					NormalSalesReturnUncutorPreciaTestData.TC38_TransferType,
					NormalSalesReturnUncutorPreciaTestData.TC38_FromCounter,
					NormalSalesReturnUncutorPreciaTestData.TC38_MetalType);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); // go to Transaction

			String ErpGoldRate24k = ErpGoldRateFor24K;
			erpUtils.ResetRemainingReturnWeight();
			for (String Sku : SkuList) {
				appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
				Map<String, String> currentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
				SaleProductNames.add(ScannedSkuDataMap.get("SKU_" + SkuCounter + "_HeaderName"));
				SaleProductQtyList.add(ScannedSkuDataMap.get("SKU_" + SkuCounter + "_GrossWeight"));
				SaleProductTotalList.add(ScannedSkuDataMap.get("SKU_" + SkuCounter + "_Total"));
				erpUtils.ErpSkuIngredientItemCalculation(currentSkuData, SkuCounter,TotalRgWeightReturn,SkuPurityReturn,SkuGoldRateReturn,ErpGoldRate24k);
				SkuCounter++;
			}
			//TranscationPageVerifyLines
			Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
			String SubTotal		 		  = TransactionDataLine.get("Subtotal");
			String Discount 			  = TransactionDataLine.get("Discount");
			String TotalGrossWeight 	  = TransactionDataLine.get("GrossWeight");
			String TotalNetWeight   	  = TransactionDataLine.get("NetWeight");
			String Tax 					  = TransactionDataLine.get("Tax");
			String TotalAmount 			  = TransactionDataLine.get("TotalAmount");
			String ActualLinesCount		  = TransactionDataLine.get("LinesCount");
			List<String> ProductNames     = new ArrayList<>();
			List<String> ProductQtyList   = new ArrayList<>();
			List<String> ProductTotalList = new ArrayList<>();
			ProductNames.addAll(ReturnProductNames);
			ProductNames.addAll(SaleProductNames);
			ProductQtyList.addAll(ReturnProductQtyList);
			ProductQtyList.addAll(SaleProductQtyList);
			ProductTotalList.addAll(ReturnProductTotalList); 
			ProductTotalList.addAll(SaleProductTotalList);

			List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
			String ExpectedLinesCount       = Integer.toString(AllProductRows.size());

			asrt.assertEquals(ExpectedLinesCount, ActualLinesCount,"Lines count mismatch: Expected is "+ExpectedLinesCount+" but found "+ActualLinesCount+" in billing page");		

			double SubTotalSum = 0.00;
			for(int i =0; i < AllProductRows.size() ;i++) {
				List<WebElement> ItemName         = base.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
				String ActualItemNameInBillingPge = ItemName.get(i).getText();
				if (i==1) {
					String ExpectedItemNameInBillingPge = ProductNames.get(i).substring(ProductNames.get(i).indexOf("-") + 1).trim();
					asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");			
				}
				else {
					String ExpectedItemNameInBillingPge = ProductNames.get(i).trim();
					asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");
				}
				List<WebElement> Quantity           = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
				String ActualQuantityInBillingPge   = Quantity.get(i).getText().replace("-", "");
				String ExpectedQuantityInBillingPge = ProductQtyList.get(i);
				double ActualQtyInBillingPge        = Double.parseDouble(ActualQuantityInBillingPge);
				double ExpectedQtyInBillingPge      = Double.parseDouble(ExpectedQuantityInBillingPge);
				asrt.assertEquals(ExpectedQtyInBillingPge, ActualQtyInBillingPge,1.0,"Quantity mismatch: Expected is "+ExpectedQtyInBillingPge+" but got "+ActualQtyInBillingPge+" in billing page");

				List<WebElement> TotalWithoutTax           = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));
				String ActualTotalWithoutTaxInBillingPge   = TotalWithoutTax.get(i).getText().replace("₹", "").replace(",", "").trim();
				SubTotalSum                                = SubTotalSum+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
				String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(ProductTotalList.get(i).replace("₹", "").replace(",", "").trim()));
				asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge.replace("-", ""),"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
			}
			String ExpectedSubTotalInBillingPge = String.format("%.2f",SubTotalSum);
			String ActualSubTotalInBillingPge   = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replace("₹", "").replace(",", "").trim();
			asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

			String ActualTaxInBillingPge   = Tax.replace("₹", "").replace(",", "").trim();
			double ActualTax               = Double.parseDouble(ActualTaxInBillingPge);
			double ExpectedTax             = SubTotalSum*0.03;
			String ExpectedTaxInBillingPge = String.format("%.2f",ExpectedTax);
			asrt.assertEquals(ExpectedTax, ActualTax,1.0,"Tax amount mismatch: "+ExpectedTax+" but found "+ActualTax+" in billing page");

			double ExpectedTotalAmountInBillingPge = Double.parseDouble(ActualSubTotalInBillingPge)+Double.parseDouble(ActualTaxInBillingPge);
			double ActualTotalAmountInBillingPge   = Double.parseDouble(TotalAmount.replace("₹", "").replace(",", "").trim());
			asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,1.0,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");


			// 12.Search "OU"
			base.buttonClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
			base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),NormalSalesReturnUncutorPreciaTestData.TC38_OldProduct);
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);

			// 13.Select "Old Uncut Own"
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.OldUncutOwnProduct));	

			//14.Select Configuration as "22k"
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),NormalSalesReturnUncutorPreciaTestData.TC38_Configuration);

			//16.Select QC person and Smith person
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));		
			Thread.sleep(1000);
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),NormalSalesReturnUncutorPreciaTestData.TC38_QcAndSmithPerson);		
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),NormalSalesReturnUncutorPreciaTestData.TC38_QcAndSmithPerson);

			//17.Enter Pieces and Gross weight 
			base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"), NormalSalesReturnUncutorPreciaTestData.TC38_Pieces);	
			base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"), NormalSalesReturnUncutorPreciaTestData.TC38_GrossWeight);

			//18. Enter stone pieces, weight & rate
			//19.Click on Calculate	
			// [Check the displayed values]
			//Expected:Check Exchange rate(Board rate)
			//Check calculation: Note:Billing Screen,No of Product lines,Displayed Item Name,Displayed Quantity,
			//Displayed TOTAL (without Tax),Displayed Subtotal,TAX,	Total Amount
			base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("ogpstonepcs"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("ogpstonepcs"),NormalSalesReturnUncutorPreciaTestData.TC38_StonePieces);
			// Step for Bug: 'Unexpected Error' Pop up displayed in the Old Gold Details
			// page
			if (base.isElementPresent(driver,By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
				try {
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
					base.excuteJsClick(	By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
					System.out.println("Popup appeared and OK button was clicked.");
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				} catch (Exception e) {System.out.println("Failed to click the OK button: " + e.getMessage());}
			} else {System.out.println("Popup not found, skipping the close button action.");}

			base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("ogpstonewt"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("ogpstonewt"),NormalSalesReturnUncutorPreciaTestData.TC38_StoneWeight);
			base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("ogpstonerate"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("ogpstonerate"),NormalSalesReturnUncutorPreciaTestData.TC38_StoneRate);
			base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(4000);
			double ActualCurrentGoldRate22kInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));
			double CurrentERPGoldRate24k                    = Double.parseDouble(ErpGoldRateFor24K);
			double ExpectedCurrentGoldRate22kFromERP        = (CurrentERPGoldRate24k*22)/24;

			asrt.assertEquals(ActualCurrentGoldRate22kInItemDetailsPge, ExpectedCurrentGoldRate22kFromERP,1.0,"Gold Rate mismatch: Expected is "+ExpectedCurrentGoldRate22kFromERP+" but found "+ActualCurrentGoldRate22kInItemDetailsPge+"in item details page");

			String ExpectedNetWeightInItemDetailsPge  = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetwt"));		
			double NetWeightInItemDetailsPge          = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
			double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualCurrentGoldRate22kInItemDetailsPge * 100.0) / 100.0;
			String GoldAmountInItemDetailsPge         = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetamt"));
			double ActualGoldAmountInItemDetailsPge   = Double.parseDouble(GoldAmountInItemDetailsPge);
			asrt.assertEquals(ActualGoldAmountInItemDetailsPge, ExpectedGoldAmountInItemDetailsPge,1.0,"GoldAmount mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in item details page");

			double AmountStone                         = Double.parseDouble(NormalSalesReturnUncutorPreciaTestData.TC38_StoneWeight)*Double.parseDouble(NormalSalesReturnUncutorPreciaTestData.TC38_StoneRate);
			String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
			String TotalAmountInItemDetailsPge         = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt"));
			double ActualTotalAmountInItemDetailsPge   = Double.parseDouble(TotalAmountInItemDetailsPge);
			double ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPge+AmountStone;
			asrt.assertEquals(ActualTotalAmountInItemDetailsPge,ExpectedTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

			List<String> ExpectedNetWtOU             = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("column3 textRight", "h4 ellipsis cell"));
			String ExpectedGrossWtOU                 = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
			List<String> ActualOURateInItemDetailPge = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("column4 textRight", "h4 ellipsis cell"));

			//Step 20: Click on Add to cart
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
			base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
			if(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Cart has changed"))) {
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			}

			//Step 21:Click on Save to Estimate button in billing screen and click on Save estimation
			//Expected: Verify the estimate invoice

			Map<String, String> TransactionDataLineRecall = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
			String SubTotalAfterOU		 		  = TransactionDataLineRecall.get("Subtotal");
			String DiscountAfterOU 			      = TransactionDataLineRecall.get("Discount");
			String TotalGrossWeightAfterOU 	      = TransactionDataLineRecall.get("GrossWeight");
			String TotalNetWeightAfterOU   	      = TransactionDataLineRecall.get("NetWeight");
			String TaxAfterOU 					  = TransactionDataLineRecall.get("Tax");
			String TotalAmountAfterOU 			  = TransactionDataLineRecall.get("TotalAmount");
			String ActualLinesCountAfterOU		  = TransactionDataLineRecall.get("LinesCount");
			ProductNames.add(NormalSalesReturnUncutorPreciaTestData.TC38_OldProduct);			
			ProductQtyList.add(ExpectedGrossWtOU);			
			ProductTotalList.add(TotalAmountInItemDetailsPge);

			List<WebElement> AllProductRowsAfterOU = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
			String ExpectedLinesCountAfterOu       = Integer.toString(AllProductRowsAfterOU.size());

			asrt.assertEquals(ActualLinesCountAfterOU,ExpectedLinesCountAfterOu,"Lines count mismatch: Expected is "+ExpectedLinesCount+" but found "+ActualLinesCount+" in billing page");	


			double SubTotalSumAfterOu = 0.00;
			for(int i =0; i < AllProductRowsAfterOU.size() ;i++) {
				List<WebElement> ItemName         = base.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
				String ActualItemNameInBillingPge = ItemName.get(i).getText();
				if (i==1) {
					String ExpectedItemNameInBillingPge = ProductNames.get(i).substring(ProductNames.get(i).indexOf("-") + 1).trim();
					asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");			
				}
				else {
					String ExpectedItemNameInBillingPge = ProductNames.get(i).trim();
					asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");
				}


				List<WebElement> Quantity           = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
				String ActualQuantityInBillingPge   = Quantity.get(i).getText().replace("-", "");
				String ExpectedQuantityInBillingPge = ProductQtyList.get(i);
				double ActualQtyInBillingPge        = Double.parseDouble(ActualQuantityInBillingPge);
				double ExpectedQtyInBillingPge      = Double.parseDouble(ExpectedQuantityInBillingPge);
				asrt.assertEquals(ExpectedQtyInBillingPge, ActualQtyInBillingPge,1.0,"Quantity mismatch: Expected is "+ExpectedQtyInBillingPge+" but got "+ActualQtyInBillingPge+" in billing page");
				List<WebElement> TotalWithoutTax           = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));
				String ActualTotalWithoutTaxInBillingPge   = TotalWithoutTax.get(i).getText().replace("₹", "").replace(",", "").trim();
				SubTotalSumAfterOu                                = SubTotalSumAfterOu+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
				String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(ProductTotalList.get(i).replace("₹", "").replace(",", "").trim()));
				asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge.replace("-", ""),"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
			}

			String ExpectedSubTotalAFterOUInBillingPge = String.format("%.2f",SubTotalSumAfterOu);
			String ActualSubTotalAfterOUInBillingPge   = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replace("₹", "").replace(",", "").trim();
			asrt.assertEquals(ActualSubTotalAfterOUInBillingPge, ExpectedSubTotalAFterOUInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

			String ActualTaxAfterOuInBillingPge   = Tax.replace("₹", "").replace(",", "").trim();
			double ActualTaxAfterOu               = Double.parseDouble(ActualTaxAfterOuInBillingPge);


			double ExpectedTaxAfterOu             = ExpectedTax;

			asrt.assertEquals(ActualTaxAfterOu,ExpectedTaxAfterOu,1.0,"Tax amount mismatch: "+ExpectedTaxAfterOu+" but found "+ActualTaxAfterOu+" in billing page");

			double ExpectedTotalAmountAfterOUInBillingPge = Double.parseDouble(ActualSubTotalAfterOUInBillingPge)+Double.parseDouble(ActualTaxAfterOuInBillingPge);
			double ActualTotalAmountAfterOuInBillingPge   = Double.parseDouble(TotalAmountAfterOU.replace("₹", "").replace(",", "").trim());
			asrt.assertEquals(ActualTotalAmountAfterOuInBillingPge, ExpectedTotalAmountAfterOUInBillingPge,1.0,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");

			base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_abc("buttonsContainer positionRelative","Back" ));
			base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
			String ActualEstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
			Thread.sleep(3000);
			double SubTotalInBillingPge = Double.parseDouble(ActualSubTotalInBillingPge)-ActualTotalAmountInItemDetailsPge;
			String ProFormaInvoiceName  = pdfUtils.DownloadAndRenameLatestPDF("TC38_ProformaInvoice");
			Thread.sleep(2000);
			String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
			String Gst                 = ActualTaxInBillingPge.replace("-", "");
			String TaxableValue		   = ActualSubTotalInBillingPge.replace("-", "");
			String InvoiceTotal        = String.format("%.2f",SubTotalInBillingPge+ActualTax).replace("-", "");
			String TotalAmnt           = String.format("%.2f",SubTotalInBillingPge+ActualTax).replace("-", "");
			String NetTotal            = String.format("%.2f",SubTotalInBillingPge+ActualTax).replace("-", "");
			pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
					ProFormaInvoicePath,
					TaxableValue,
					Gst,
					InvoiceTotal,
					NetTotal,
					TotalAmnt
					);

			//Step 22:Recall estimate from cashier screen
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
			base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));

			//Step 23: Select estimate and click on Recall estimation button
			base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);			
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
			String ExpectedEstmnNumber= base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
			Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+" in Recall Estimate Page"); 

			//Step 24:Select the Transaction Type as Sales return
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnUncutorPreciaTestData.TC38_TransactionTypeReturn);

			//Step 25:Click on Process Estimation 
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

			//Step 26:Click on the amount and post sale return 
			//Expected: Check sales return invoice details
			Thread.sleep(3000);
			Map<String, String> TransactionRecallDataLineReturn = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
			String SubTotalReturn 			= TransactionRecallDataLineReturn.get("Subtotal");
			String DiscountReturn 			= TransactionRecallDataLineReturn.get("Discount");
			String TotalGrossWeightReturn   = TransactionRecallDataLineReturn.get("GrossWeight");
			String TotalNewNetWeightReturn  = TransactionRecallDataLineReturn.get("NetWeight");
			String TaxReturn 				= TransactionRecallDataLineReturn.get("Tax");
			String TotalAmountReturn	    = TransactionRecallDataLineReturn.get("TotalAmount").replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim();
			String NetTotalReturn 			= TransactionRecallDataLineReturn.get("NetTotal").replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim();
			String LinesCountReturn 		= TransactionRecallDataLineReturn.get("LinesCount");


			String AmountDueForSaleReturnFromBillingPge    = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("₹", "").replace(",", "").trim();
			double AmountDueForSaleReturnInFromBillingPge  = Double.parseDouble(AmountDueForSaleReturnFromBillingPge);
			String ExpectedAmountForSaleReturnInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("₹", "").replace(",", "").trim();
			double TaxForSaleReturnProducts                = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4")).replace("₹", "").replace(",", "").replace("-", ""));
			if (AmountDueForSaleReturnInFromBillingPge<0.00) {
				appUtils.PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
						UtilityTestData.SalePersonNumber,
						UtilityTestData.SalePersonName,
						UtilityTestData.DueYear,
						UtilityTestData.NomineeName,
						UtilityTestData.NomineeRelation);
				base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
				base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "NORMAL ADV. INVOICE"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
				Map<String, String> AdvanceReceiptVoucherDetails = pdfUtils.NormalAdvancePdfValidation();
				String ActualAdvanceReceived   = AdvanceReceiptVoucherDetails.get("AdvanceReceived");
				String ActualNetReceived       = AdvanceReceiptVoucherDetails.get("NetAdvance");
				String ActualPaymentMode       = AdvanceReceiptVoucherDetails.get("PaymentMode");
				String PaymentAmount           = AdvanceReceiptVoucherDetails.get("PaymentAmount");
				String ApproxWeight            = AdvanceReceiptVoucherDetails.get("ApproxWeight");
				String ActualFixedGoldRate     = AdvanceReceiptVoucherDetails.get("FixedGoldRate");
				String ActualDepositType       = AdvanceReceiptVoucherDetails.get("DepositType");
				asrt.assertEquals(ExpectedAmountForSaleReturnInBillingPge, ActualAdvanceReceived,"Advance value mismatch: Expected is "+ExpectedAmountForSaleReturnInBillingPge+" but found "+ActualAdvanceReceived+" in Advance receipt");
				asrt.assertEquals(ExpectedAmountForSaleReturnInBillingPge, ActualNetReceived,"Net value mismatch: Expected is "+ExpectedAmountForSaleReturnInBillingPge+" but found "+ActualNetReceived+" in Advance receipt");
				asrt.assertEquals(NormalSalesReturnUncutorPreciaTestData.TC38_PaymentMode, ActualPaymentMode,"Payment mode mismatch: Expected is "+NormalSalesReturnUncutorPreciaTestData.TC38_PaymentMode+" but found "+ActualPaymentMode+" in Advance receipt");
				asrt.assertEquals(ExpectedAmountForSaleReturnInBillingPge, PaymentAmount,"Payment Amount mismatch: Expected is "+ExpectedAmountForSaleReturnInBillingPge+" but found "+PaymentAmount+" in Advance receipt");
				base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
			}
			else {
				base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
				base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
				Map<String, String> CreditNoteDetails = pdfUtils.SalesReturnPDFCredit();
				String ActualGrossWt1                 = CreditNoteDetails.get("Product1.GrossWt");
				String ActualStoneWt1                 = CreditNoteDetails.get("Product1.StoneWt");
				String ActualNetWt1                   = CreditNoteDetails.get("Product1.NetWt");
				String ActualAmount1                  = CreditNoteDetails.get("Product1.Amount");
				String ActualTaxableValue             = CreditNoteDetails.get("TaxableValue");
				String ActualTotalNetValue            = CreditNoteDetails.get("TotalNetValue");
				String ActualPaymentDetails           = CreditNoteDetails.get("PaymentDetails");
				asrt.assertEquals(Sku1GrossWeight, ActualGrossWt1,"Gross Weight mismatch: Expected is "+Sku1GrossWeight+" but found "+ActualGrossWt1+" in credit note");
				asrt.assertEquals(Sku1StoneWeight, ActualStoneWt1,"StoneWt mismatch: Expected is"+Sku1StoneWeight+" but found "+ActualStoneWt1+" in credit note");
				asrt.assertEquals(Sku1NetWeight, ActualNetWt1,"NetWt mismatch: Expected is"+Sku1NetWeight+" but found "+ActualNetWt1+" in credit note");
				asrt.assertEquals(Double.parseDouble(Sku1Total), Double.parseDouble(ActualAmount1),1.0,"Amount mismatch: Expected is"+Sku1Total+" but found "+ActualAmount1+" in credit note");
			}	

			//27.In cash, click on "Recall Estimate"
			//28.Select the estimate, Click on Recall Estimation
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
			base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
			base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
			base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));

			// 29.Select transaction type as "Exchange" and click on Process estimation
			// 30.Choose a sales representative
			// 31.Click on the Amount For printing Receipt
			//Expected:Check Exchange invoice details		
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnUncutorPreciaTestData.TC38_TransactionTypeExchange);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
			String AmountDueForExchangeFromBillingPge    = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("₹", "").replace(",", "").trim();
			double AmountDueForExchangeInFromBillingPge  = Double.parseDouble(AmountDueForExchangeFromBillingPge);			
			String ExpectedAmountForExchangeInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("₹", "").replace(",", "").trim();
			if (AmountDueForExchangeInFromBillingPge<0.00) {
				appUtils.PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
						UtilityTestData.SalePersonNumber,
						UtilityTestData.SalePersonName,
						UtilityTestData.DueYear,
						UtilityTestData.NomineeName,
						UtilityTestData.NomineeRelation);
				base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
				base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "NORMAL ADV. INVOICE"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
				Map<String, String> AdvanceReceiptVoucherDetails = pdfUtils.NormalAdvancePdfValidation();
				String ActualAdvanceReceived = AdvanceReceiptVoucherDetails.get("AdvanceReceived");
				String ActualNetReceived     = AdvanceReceiptVoucherDetails.get("NetAdvance");
				String ActualPaymentMode     = AdvanceReceiptVoucherDetails.get("PaymentMode");
				String PaymentAmount         = AdvanceReceiptVoucherDetails.get("PaymentAmount");
				String ApproxWeight          = AdvanceReceiptVoucherDetails.get("ApproxWeight");
				String ActualFixedGoldRate   = AdvanceReceiptVoucherDetails.get("FixedGoldRate");
				String ActualDepositType     = AdvanceReceiptVoucherDetails.get("DepositType");
				asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, ActualAdvanceReceived,"Advance value mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+ActualAdvanceReceived+" in Advance receipt");
				asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, ActualNetReceived,"Net value mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+ActualNetReceived+" in Advance receipt");
				asrt.assertEquals(NormalSalesReturnUncutorPreciaTestData.TC38_PaymentMode, ActualPaymentMode,"Payment Mode mismatch: Expected is "+NormalSalesReturnUncutorPreciaTestData.TC38_PaymentMode+" but found "+ActualPaymentMode+" in Advance receipt");
				asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, PaymentAmount,"Payment Amount mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+PaymentAmount+" in Advance receipt");
				base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
			}
			else
			{
				base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
				base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
				double  GrossWeight = Double.parseDouble(ExpectedGrossWeightInItemDetailsPge);
				String  ExpectedGrossWeightFromBillingPge         = String.format("%.3f", GrossWeight);  
				String  ExpectedOUPurchaseRateFromItemDetailsPge  = String.format("%.2f",ActualCurrentGoldRate22kInItemDetailsPge);
				double  TotalAmountForOU                          = Double.parseDouble(TotalAmountInItemDetailsPge);
				String  ExpectedTotalAmountForOU                  = String.format("%.2f", TotalAmountForOU);  
				String	ExpectedTotal    =TotalAmountInItemDetailsPge;
				String	ExpectedItemToal =TotalAmountInItemDetailsPge;
				String	ExpectedPyament  =TotalAmountInItemDetailsPge;
				pdfUtils.ValidateOUPdf(NormalSalesReturnUncutorPreciaTestData.TC38_PaymentMode, NormalSalesReturnUncutorPreciaTestData.TC38_CustomerName, ExpectedGrossWtOU, ExpectedNetWtOU, ActualOURateInItemDetailPge, ExpectedTotal, ExpectedItemToal, ExpectedPyament);
			}

			// 32.In cash, click on "Recall Estimate"
			//33.Select the estimate, Click on Recall Estimation
			//34.Select transaction type as "Sales" and click on Process estimation
			// 35.Choose a sales representative
			//36.Click on the Amount For printing Receipt
			//Expected: Cashier Screen after recall:No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
			//Expected:Check final invoice details, save receipt id for future reference
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
			base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
			base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
			base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnUncutorPreciaTestData.TC38_TransactionTypeSales);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

			Thread.sleep(3000);
			Map<String, String> TransactionRecallDataLineSales = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
			String SubTotalReturnSales			 = TransactionRecallDataLineSales.get("Subtotal");
			String DiscountReturnSales 			 = TransactionRecallDataLineSales.get("Discount");
			String TotalGrossWeightReturnSales   = TransactionRecallDataLineSales.get("GrossWeight");
			String TotalNewNetWeightReturnSales  = TransactionRecallDataLineSales.get("NetWeight");
			String TaxReturnSales 				 = TransactionRecallDataLineSales.get("Tax");
			String TotalAmountReturnSales	     = TransactionRecallDataLineSales.get("TotalAmount").replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim();
			String NetTotalReturnSales 			 = TransactionRecallDataLineSales.get("NetTotal").replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim();
			String LinesCountReturnSales 		 = TransactionRecallDataLineSales.get("LinesCount");

			erpUtils.ErpValidateTransactionLineCalculation(TransactionRecallDataLineSales, ScannedSkuDataMap);	

			String GrossAmountForSale               = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4"));
			String TaxableValueForSale              = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4"));
			String TotalValueForSale                = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4"));
			String TotalNetValueForSale             = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4"));
			String TotalQtyPcsForSale               = String.valueOf(SkuList.size());
			String TotalDiscountForSale             = Discount;
			String TotalNetWeightForSale            = TotalNetWeight;
			String TotalGrossWeightForSale          = SaleProductQtyList.get(0);
			String AdjustmentForSale                = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("PaymentsField", "h4"));
			String AmountDueForSaleFromBillingPge   = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("₹", "").replace(",", "").trim();
			double AmountDueForSaleInFromBillingPge = Double.parseDouble(AmountDueForSaleFromBillingPge);
			String PaymentAmountForSale ="";
			if (AmountDueForSaleInFromBillingPge==0.00) {
				base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
				base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
				PaymentAmountForSale = "";
			}
			else {PaymentAmountForSale = appUtils.PaymentAfterRecallEstimate("HDFC");}
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
			String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC38_TaxInvoice");		
			String TaxInvoice     =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";
			String TotalDiamond   = "";
			String PdfInvoiceNo   = pdfUtils.SaleInvoicePdfAdjustmentValidation(
					TaxInvoice,
					GrossAmountForSale,
					TotalDiscountForSale,
					TotalDiamond,
					TotalQtyPcsForSale,
					TotalNetWeightForSale,
					TotalGrossWeightForSale,
					TaxableValueForSale,
					TotalNetValueForSale,
					PaymentAmountForSale,
					AdjustmentForSale,
					SkuList,
					ScannedSkuDataMap
					);
			pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
		}
}
