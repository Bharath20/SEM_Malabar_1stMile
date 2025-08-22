

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
			double ExpectedPrice = Double.parseDouble(ProdPriceList.get(i).trim().replace("â‚¹", "").replace(",", "").replace("-", "").replaceAll("[^\\d.]", ""));

			String BillScreenProdName = BillScrnProdName.get(i).getText();
			String BillScreenProQty = BillScrnProdQty.get(i).getText().replace("-", "").trim();
			String BillScreenProdPrice = BillScrnProdPrice.get(i).getText().replace("-", "").trim();
			double ActualQty = Double.parseDouble(BillScreenProQty);
			double ActualPrice = Double.parseDouble(BillScreenProdPrice.replace("₹", "").replace(",", "").replaceAll("[^\\d.]", "").trim());

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
		String TotalAmountReturn	    = TransactionRecallDataLineReturn.get("TotalAmount").replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim();
		String NetTotalReturn 			= TransactionRecallDataLineReturn.get("NetTotal").replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim();
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
		double ActualTaxableValue 		   = Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedTaxableValue  	   = Double.parseDouble(ExpTaxableValueReturnSale);

		double ActualGrossAmount 		   = Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedGrossAmount 		   = Double.parseDouble(ExpGrossAmountReturnSale);

		double ActualTotalGrossWeight      = Math.abs(Double.parseDouble(((String) InvoiceDetails.get("TotalGrossWeight")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedTotalGrossWeight    = Double.parseDouble(ExpTotalGrossWtReturnSale);

		double ActualNetWeight			   = Double.parseDouble(((String) InvoiceDetails.get("TotalNetWeight")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		double ExpectedNetWeight 		   = Double.parseDouble(ExpTotalNetWtReturnSale);

		double ActualNetTotal 			   = Double.parseDouble(((String) InvoiceDetails.get("NetTotal")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
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
}
