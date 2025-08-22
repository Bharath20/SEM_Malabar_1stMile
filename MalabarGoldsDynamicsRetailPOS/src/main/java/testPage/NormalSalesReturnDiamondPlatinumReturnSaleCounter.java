package testPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import testData.CommonData;
import testData.NormalSalesReturnGoldSilverReturnSaleCounter_TestData;
import testData.NormalSalesReturnDiamondPlatinumReturnSaleCounter_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.BasePge;
import utilPack.ErpUtilities;
import utilPack.PdfUtilities;
import utilPack.AppUtilities.ReturnDetails;


public class NormalSalesReturnDiamondPlatinumReturnSaleCounter extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	PdfUtilities pdfUtils;
	ErpUtilities erpUtils;

	Login login = new Login(driver); 
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	NormalSalesReturnGoldSilverReturnSaleCounter_TestData NormalSalesReturnGoldSilverReturnSaleCounterTestData = new NormalSalesReturnGoldSilverReturnSaleCounter_TestData();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj = new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();
	NormalSalesReturnDiamondPlatinumReturnSaleCounter_TestData NormalSalesReturndiamondPlatinumReturnSaleCounterTestData= new NormalSalesReturnDiamondPlatinumReturnSaleCounter_TestData();

	public NormalSalesReturnDiamondPlatinumReturnSaleCounter(WebDriver driver) {
		super(driver);
		base     = new BasePge(driver);
		appUtils = new AppUtilities(base);
		pdfUtils = new PdfUtilities(base);
		erpUtils = new ErpUtilities(base);
	}

	// <summary>
	// Test Case Title : Normal Sale Return Multiple Diamond
	// Automation ID : TC23 
	// Author : Christy Reji
	// </summary>

	public void TC23_NormalSalesReturnDiamondPlatinumReturnSaleCounter() throws Exception 
	{
		//PreRequisite : Take the bill number (receipt number) that has previously been sold
		//Step 1 :Click on Transaction button
		String InvoiceNo          = appUtils.GetInvoiceNoAfterNormalSale(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC23_CustomerNo, NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC23_ItemList);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		//Step 3 : Select the customer 
		//Step 4 : Click on add to sale button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC23_CustomerNo,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product
		//Step 9 : Click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		//Expected Result : Check calculation Note:Billling Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC23_ReturnProducts);

		//Validation for product meant to be returned should be previously bought by the same customer.
		String ExpectedCustomerName = NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC23_CustomerName;
		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName);

		//Validation Steps : Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax)
		List<String> BillScrnProdNames      = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> BillScrnProdQtys       = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> BillScrnProdTotal      = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));

		List<String> ReturnProdNames        = Details.productNames;
		List<String> ReturnProdQtyList      = Details.productQty;
		List<String> ReturnProdTotalList    = Details.productPrice;

		for (int ProdList = 0; ProdList < BillScrnProdNames.size(); ProdList++) 
		{
			String ExpectedReturnName       = ReturnProdNames.get(ProdList).trim();
			double ExpectedReturnQty        = Double.parseDouble(ReturnProdQtyList.get(ProdList).trim().replace("-", "").replaceAll("[^\\d.]", ""));
			double ExpectedReturnTotalWithoutTax = Double.parseDouble(ReturnProdTotalList.get(ProdList).trim().replaceAll("[^\\d.]", ""));

			String ActualName               = BillScrnProdNames.get(ProdList).trim();
			double ActualQty                = Double.parseDouble(BillScrnProdQtys.get(ProdList).replace("-", "").replaceAll("[^\\d.]", ""));
			double ActualTotalWithoutTax    = Double.parseDouble(BillScrnProdTotal.get(ProdList).replace("-", "").replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ActualName,ExpectedReturnName ,"In Transaction Line Page,The product name does not match the item previously purchased by the customer. " +"Expected: " + ExpectedReturnName + ", but found: " + ActualName);
			asrt.assertEquals(ActualQty,ExpectedReturnQty, 0.1,"In Transaction Line Page, The quantity does not match the quantity previously purchased by the customer. " +"Expected: " + ExpectedReturnQty + ", but found: " + ActualQty);
			asrt.assertEquals(ActualTotalWithoutTax,ExpectedReturnTotalWithoutTax, 0.1,"In Transaction Line Page, The TOTAL (without Tax) does not match the TOTAL (without Tax) previously paid by the customer. " +"Expected: " + ExpectedReturnTotalWithoutTax + ", but found: " + ActualTotalWithoutTax);
		}

		//Validation Steps : No of Product lines, Displayed SubTotal, TAX, Total amount
		double ReturnSumSubTotal = 0.0;
		for (String TotalAmnt : BillScrnProdTotal) {
			String ReturnTotalAmnt = TotalAmnt.replaceAll("[^\\d.]", "").trim();
			if (!ReturnTotalAmnt.isEmpty()) {
				ReturnSumSubTotal += Double.parseDouble(ReturnTotalAmnt);
			}
		}

		double ReturnTaxAmount         = ReturnSumSubTotal*3/100;
		double ReturnTotalAmount       = ReturnSumSubTotal+ReturnTaxAmount;
		int ActualNoOfProductLines     = BillScrnProdNames.size();     		
		String TransSubTotal           = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("SubtotalField","row pad12 padBottom0","h4"));
		String TransTax                = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TaxField","row pad12 padBottom0","h4"));
		String TransTotalAmnt          = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TotalAmountField","row pad12 padBottom0","h4"));
		String TransNoOfLines          = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("LinesField","row pad12 padBottom0","h4"));
		double TransactionSubTotal     = Double.parseDouble(TransSubTotal.replaceAll("[^\\d.]", "").trim());
		double TransactionTax          = Double.parseDouble(TransTax.replaceAll("[^\\d.]", "").trim());
		double TransactionTotalAmount  = Double.parseDouble(TransTotalAmnt.replaceAll("[^\\d.]", "").trim());
		int TransactionNoOfLines       = Integer.parseInt(TransNoOfLines.replaceAll("[^\\d]", "").trim());

		asrt.assertEquals(TransactionNoOfLines, ActualNoOfProductLines, "In Transaction Line Page, Mismatch in number of Actual product lines  "+TransactionNoOfLines+" and Expected "+ActualNoOfProductLines+" product lines in Billing screen");
		asrt.assertEquals(TransactionSubTotal, ReturnSumSubTotal,0.10,  "In Transaction Line Page, Mismatch in Actual subtotal "+TransactionSubTotal+" and Expected "+ReturnSumSubTotal+" sub total in Billing screen");
		asrt.assertEquals(TransactionTax, ReturnTaxAmount,0.10, "In Transaction Line Page, Mismatch in Actual Tax "+TransactionTax+" and Expected "+ReturnTaxAmount+" Tax in Billing screen");
		asrt.assertEquals(TransactionTotalAmount,ReturnTotalAmount,0.10,  "In Transaction Line Page, Mismatch in Actual total amount "+TransactionTotalAmount+" and Expected "+ReturnTotalAmount+" total amount in Billing screen");

		//Step 10  : Click on the Amount
		//Step 11  : Select a Payment method as convert advance
		//Step 12  : Select transaction type as normal
		//Step 13  : Select sales person
		//Step 14  : Select the due date
		//Step 15  : Enter remarks
		//Step 16  : click on nominee details
		//Step 16  : Enter nominee name
		//Step 16  : Select nominee relation
		//Step 16  : Click on check box(Same as customer address)
		//Step 16  : Click on OK button
		//Step 17  : Click on Deposit button
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

		asrt.assertEquals(PdfAdvanceReceived,TransactionTotalAmount,0.10, "Mismatch in total amount in billing screen actual "+PdfAdvanceReceived+" and Expected "+TransactionTotalAmount+"in Normal Advance PDF");
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
	// Test Case Title : Sales return of multiple diamond and platinum items
	// Automation ID : TC25
	// Author : Nivya Ramesan
	// </summary>

	public void TC25_NormalSalesReturnDiamondPlatinumReturnSaleCounter() throws Exception
	{

		// Step 1: Login to POS
		// Step 2: Click on Transaction button

		//ArrayList<String> SkuList = new ArrayList<>();
		//		SkuList.addAll(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.SkuValuePlatinum);
		//		SkuList.addAll(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.SkuValueDiamond);			
		String InvoiceNo = appUtils.GetInvoiceNoAfterNormalSale(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC25_CustomerNo, NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.SkuList);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		//Step 3 : Select the customer 
		//Step 4 : Click on add to sale button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC25_CustomerNo,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product
		//Step 9 : Click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		//Expected Result : Check calculation Note:Billing Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.ReturnProducts);

		//Validation for product meant to be returned should be previously bought by the same customer.
		String ExpectedCustomerName = NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC25_CustomerName;
		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName);

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

			asrt.assertEquals(ActualName,ExpectedReturnName ,"In Transaction Line Page,The product name does not match the item previously purchased by the customer. " +"Expected: " + ExpectedReturnName + ", but found: " + ActualName);
			asrt.assertEquals(ActualQty,ExpectedReturnQty, 0.1,"In Transaction Line Page, The quantity does not match the quantity previously purchased by the customer. " +"Expected: " + ExpectedReturnQty + ", but found: " + ActualQty);
			asrt.assertEquals(ActualTotalNoTax,ExpectedReturnTotalWithoutTax, 0.1,"In Transaction Line Page, The TOTAL (without Tax) does not match the TOTAL (without Tax) previously paid by the customer. " +"Expected: " + ExpectedReturnTotalWithoutTax + ", but found: " + ActualTotalNoTax);
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

		asrt.assertEquals(TransNoOfLinesInt, ActualNoOfProductLines, "In Transaction Line Page, Mismatch in number of Actual product lines  "+TransNoOfLinesInt+" and Expected "+ActualNoOfProductLines+" product lines in Billing screen");
		asrt.assertEquals(TransactionSubTotal, ReturnSumSubTotal,0.10,  "In Transaction Line Page, Mismatch in Actual subtotal "+TransactionSubTotal+" and Expected "+ReturnSumSubTotal+" sub total in Billing screen");
		asrt.assertEquals(TransactionTax, ReturnTaxAmount,0.10, "In Transaction Line Page, Mismatch in Actual Tax "+TransactionTax+" and Expected "+ReturnTaxAmount+" Tax in Billing screen");
		asrt.assertEquals(TransactionTotalAmount,ReturnTotalAmount,0.10,  "In Transaction Line Page, Mismatch in Actual total amount "+TransactionTotalAmount+" and Expected "+ReturnTotalAmount+" total amount in Billing screen");

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

		asrt.assertEquals(PdfAdvanceReceived,TransactionTotalAmount,0.10, "Mismatch in total amount in billing screen actual "+PdfAdvanceReceived+" and Expected "+TransactionTotalAmount+"in Normal Advance PDF");
		asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,0.10, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
		asrt.assertEquals(UtilityTestData.DepositType, DepositType, "Mismatch in Deposit Type Actual "+UtilityTestData.DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

		Double WeightValue          = PdfAdvanceReceived/PdfMaxGoldRate;
		BigDecimal CalcWeightValue  = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
		double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),0.10, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");

		Thread.sleep(5000);
		//		appUtils.CounterFlow("Diamond","Gold", NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.SkuValueDiamond , UtilityTestData.Terminal);
		//		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		//		Thread.sleep(2000);
		//		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		//		appUtils.CounterFlow("Platinum","Platinum", NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.SkuValuePlatinum , UtilityTestData.Terminal);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Normal Sale Return Multiple Platinum
	// Automation ID : TC24
	// Author : Gokul.P
	// </summary>

	public void TC24_NormalSalesReturnDiamondPlatinumReturnSaleCounter() throws Exception 
	{
		//PreRequisite : Take the bill number (receipt number) that has previously been sold
		//Step 1 :Click on Transaction button
		String InvoiceNo          = appUtils.GetInvoiceNoAfterNormalSale(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC24_CustomerNo, NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC24_SkuValue);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		//Step 3 : Select the customer 
		//Step 4 : Click on add to sale button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC24_CustomerNo,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product
		//Step 9 : Click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		//Expected Result : Check calculation Note:Billing Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC24_ReturnProducts);

		//Validation for product meant to be returned should be previously bought by the same customer.
		String ExpectedCustomerName = NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC24_CustomerName;
		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return in the billing screen. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName);

		//Validation Steps : Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax)
		List<String> BillScrnProdNames      = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> BillScrnProdQtys       = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> BillScrnProdTotal      = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));

		List<String> ReturnProdNames        = Details.productNames;
		List<String> ReturnProdQtyList      = Details.productQty;
		List<String> ReturnProdTotalList    = Details.productPrice;

		for (int ProdList = 0; ProdList < BillScrnProdNames.size(); ProdList++) 
		{
			String ExpectedReturnName       = ReturnProdNames.get(ProdList).trim();
			double ExpectedReturnQty        = Double.parseDouble(ReturnProdQtyList.get(ProdList).trim().replace("-", "").replaceAll("[^\\d.]", ""));
			double ExpectedReturnTotalWithoutTax = Double.parseDouble(ReturnProdTotalList.get(ProdList).trim().replaceAll("[^\\d.]", ""));

			String ActualName               = BillScrnProdNames.get(ProdList).trim();
			double ActualQty                = Double.parseDouble(BillScrnProdQtys.get(ProdList).replace("-", "").replaceAll("[^\\d.]", ""));
			double ActualTotalWithoutTax    = Double.parseDouble(BillScrnProdTotal.get(ProdList).replace("-", "").replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ActualName,ExpectedReturnName ,"In Transaction Line Page,The product name does not match the item previously purchased by the customer. " +"Expected: " + ExpectedReturnName + ", but found: " + ActualName);
			asrt.assertEquals(ActualQty,ExpectedReturnQty, 1,"In Transaction Line Page, The quantity does not match the quantity previously purchased by the customer. " +"Expected: " + ExpectedReturnQty + ", but found: " + ActualQty);
			asrt.assertEquals(ActualTotalWithoutTax,ExpectedReturnTotalWithoutTax, 1,"In Transaction Line Page, The TOTAL (without Tax) does not match the TOTAL (without Tax) previously paid by the customer. " +"Expected: " + ExpectedReturnTotalWithoutTax + ", but found: " + ActualTotalWithoutTax);
		}

		//Validation Steps : No of Product lines, Displayed SubTotal, TAX, Total amount
		double ReturnSumSubTotal = 0.0;
		for (String TotalAmntBillingPge : BillScrnProdTotal) {
			String ReturnTotalAmntBillingPge = TotalAmntBillingPge.replaceAll("[^\\d.]", "").trim();
			if (!ReturnTotalAmntBillingPge.isEmpty()) {
				ReturnSumSubTotal += Double.parseDouble(ReturnTotalAmntBillingPge);
			}
		}

		double ReturnTaxAmount         = ReturnSumSubTotal*3/100;
		double ReturnTotalAmount       = ReturnSumSubTotal+ReturnTaxAmount;
		int ActualNoOfProductLines     = BillScrnProdNames.size();     		
		String TransSubTotal           = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("SubtotalField","row pad12 padBottom0","h4"));
		String TransTax                = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TaxField","row pad12 padBottom0","h4"));
		String TransTotalAmnt          = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TotalAmountField","row pad12 padBottom0","h4"));
		String TransNoOfLines          = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("LinesField","row pad12 padBottom0","h4"));
		double TransactionSubTotal     = Double.parseDouble(TransSubTotal.replaceAll("[^\\d.]", "").trim());
		double TransactionTax          = Double.parseDouble(TransTax.replaceAll("[^\\d.]", "").trim());
		double TransactionTotalAmount  = Double.parseDouble(TransTotalAmnt.replaceAll("[^\\d.]", "").trim());
		int TransactionNoOfLines       = Integer.parseInt(TransNoOfLines.replaceAll("[^\\d]", "").trim());

		asrt.assertEquals(TransactionNoOfLines, ActualNoOfProductLines, "In Transaction Line Page, Mismatch in number of Actual product lines  "+TransactionNoOfLines+" and Expected "+ActualNoOfProductLines+" product lines in Billing screen");
		asrt.assertEquals(TransactionSubTotal, ReturnSumSubTotal,1,  "In Transaction Line Page, Mismatch in Actual subtotal "+TransactionSubTotal+" and Expected "+ReturnSumSubTotal+" sub total in Billing screen");
		asrt.assertEquals(TransactionTax, ReturnTaxAmount,1, "In Transaction Line Page, Mismatch in Actual Tax "+TransactionTax+" and Expected "+ReturnTaxAmount+" Tax in Billing screen");
		asrt.assertEquals(TransactionTotalAmount,ReturnTotalAmount,1,  "In Transaction Line Page, Mismatch in Actual total amount "+TransactionTotalAmount+" and Expected "+ReturnTotalAmount+" total amount in Billing screen");

		//Step 10  : Click on the Amount
		//Step 11  : Select a Payment method as convert advance
		//Step 12  : Select transaction type as normal
		//Step 13  : Select sales person
		//Step 14  : Select the due date
		//Step 15  : Enter remarks
		//Step 16  : click on nominee details
		//Step 16  : Enter nominee name
		//Step 16  : Select nominee relation
		//Step 16  : Click on check box(Same as customer address)
		//Step 16  : Click on OK button
		//Step 17  : Click on Deposit button
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

		asrt.assertEquals(PdfAdvanceReceived,TransactionTotalAmount,1, "Mismatch in total amount in billing screen actual "+PdfAdvanceReceived+" and Expected "+TransactionTotalAmount+"in Normal Advance PDF");
		asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,1, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
		asrt.assertEquals(UtilityTestData.DepositType, DepositType, "Mismatch in Deposit Type Actual "+UtilityTestData.DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

		Double WeightValue          = PdfAdvanceReceived/PdfMaxGoldRate;
		BigDecimal CalcWeightValue  = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
		double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),1, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");
		Thread.sleep(5000);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	// <summary>
	// Test Case Title : Sales return of multiple diamond with multiple category sale items
	//=============Challenge in Wastage amount calculation Step:9,10. 2-3 Tolerance variation showing========
	// Automation ID : TC26
	// Author : Jhoncy Joseph
	// </summary>
	public void TC26_NormalSalesReturnDiamondPlatinumReturnSaleCounter() throws Exception {

		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSale(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_CustomerNo, NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_ItemList);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		String InvoiceNo            = (String) InvoiceDetails.get("InvoiceNo");
		String TotalNetWeightReturn = (String) InvoiceDetails.get("TotalNetWeight");
		String SkuPurityReturn      = (String) InvoiceDetails.get("FirstSkuPurity");
		String SkuGoldRateReturn    = (String) InvoiceDetails.get("FirstSkuGoldRate");
		String TotalRgWeightReturn  = (String) InvoiceDetails.get("TotalRgWeight");

		// Extract SKU-wise map
		Map<String, String> SkuWiseData = (Map<String, String>) InvoiceDetails.get("SKUWiseDetails");
		Map<String, String> ScannedSKUDataMapSale = (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");

		String ErpGoldRateFor24K= erpUtils.SetMetalRateInERP(SkuGoldRateReturn, SkuPurityReturn);

		Thread.sleep(120000); //Wait needed for ERP Gold Rate to Change

		//Step 3 : Select the customer 
		//Step 4 : Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_CustomerNo,UtilityTestData.MenuBarOptions[5]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product (multiple diamond) one by one and click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_ReturnProducts);
		String ExpectedCustomerName = NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_CustomerName;

		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName+" in transaction page");

		//Step 9  :  Scan multiple SKU (Diamond,Gold,Uncut,Precia etc)
		//Step 10 :  Click on add to cart button
		//Expected Result: Check calculationNote:Billling Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		// Diamond SKUs
		List<String> DiamondSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_SkuGoldCount,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_TransferType,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_FromCounter4,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_MetalType);
		SkuList.addAll(DiamondSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); 

		// Gold SKUs
		List<String> GoldSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_SkuGoldCount,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_TransferType,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_FromCounter,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_MetalType);
		SkuList.addAll(GoldSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); // go to Transaction

		// Precia SKUs
		List<String> PreciaSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_SkuGoldCount,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_TransferType,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_FromCounter3,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_MetalType);
		SkuList.addAll(PreciaSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); 

		// Uncut SKUs
		List<String> UncutSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_SkuGoldCount,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_TransferType,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_FromCounter2,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_MetalType);
		SkuList.addAll(UncutSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); 

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
		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC26_ProformaInvoice");
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

		Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+"in recall estimation page"); 

		// Step 14: Select the Transaction Type as Sales Return
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_TransationType);

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

		erpUtils.ErpValidateTransactionLineCalculation(TransactionRecallDataLineReturn, ScannedSKUDataMapSale);

		// Step 17:  Click on the Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		// Step 18: post sales return bill
		// Expected Result: Check Return Invoice details
		pdfUtils.DownloadAndRenameLatestPDF("TC26SaleReturnInvoice");

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
		double ActualTaxableValue 		= Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedTaxableValue  	= Double.parseDouble(ExpTaxableValueReturnSale);

		double ActualGrossAmount 		= Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedGrossAmount 		= Double.parseDouble(ExpGrossAmountReturnSale);

		double ActualTotalGrossWeight   = Math.abs(Double.parseDouble(((String) InvoiceDetails.get("TotalGrossWeight")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedTotalGrossWeight = Double.parseDouble(ExpTotalGrossWtReturnSale);

		double ActualNetWeight			= Double.parseDouble(((String) InvoiceDetails.get("TotalNetWeight")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		double ExpectedNetWeight 		= Double.parseDouble(ExpTotalNetWtReturnSale);

		double ActualNetTotal 			= Double.parseDouble(((String) InvoiceDetails.get("NetTotal")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		double ExpectedNetTotal 		= Double.parseDouble(ExpTotalNetValueReturnSale);

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

		// Step 20: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_TransationType2);

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
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_ApprovalCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC26_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 25:  post sales return bill
		// Expected Result: Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice   		     = pdfUtils.DownloadAndRenameLatestPDF("TC26SaleInvoice");
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

	// <summary>
	// Test Case Title : Sales Return Of Dual Tone Platinum With Any Other Sales Items
	// Automation ID : TC27
	// Author : Robin T Abraham
	// </summary>
	//=============Challenge in Wastage amount calculation Step:9,10. 2-3 Tolerance variation showing===========================
	public void TC27_NormalSalesReturnDiamondPlatinumReturnSaleCounter() throws Exception {

		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSaleDualTonePlatinum(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_CustomerNo);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		String InvoiceNo            = (String) InvoiceDetails.get("InvoiceNo");
		String TotalNetWeightReturn = (String) InvoiceDetails.get("TotalNetWeight");
		String SkuPurityReturn      = (String) InvoiceDetails.get("FirstSkuPurity");
		String SkuGoldRateReturn    = (String) InvoiceDetails.get("FirstSkuGoldRate");
		String TotalRgWeightReturn  = (String) InvoiceDetails.get("TotalRgWeight");
		String PlatinumPurityReturn = (String) InvoiceDetails.get("Purity");	

		// Extract SKU-wise map
		Map<String, String> SkuWiseData = (Map<String, String>) InvoiceDetails.get("SKUWiseDetails");

		Map<String, String> ScannedSkuDataMapSale = (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");

		String ErpGoldRateFor24K= erpUtils.SetMetalRateInERP(SkuGoldRateReturn, PlatinumPurityReturn );

		Thread.sleep(5000); //Wait needed for ERP Gold Rate to Change

		//Step 3 : Select the customer 
		//Step 4 : Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_CustomerNo,UtilityTestData.MenuBarOptions[5]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product dual tone platinum and click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		List<String> ToReturnProducts = Arrays.asList("Platinum");
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, ToReturnProducts);
		String ExpectedCustomerName = NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_CustomerName;

		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName +"in transaction page");

		//Step 9  :  Scan SKU (Gold)
		//Step 10 :  Click on add to cart button
		//Expected Result: Check calculationNote:Billing Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		// Gold SKUs
		List<String> GoldSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_SkuCount,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_TransferType,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_FromCounter,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_MetalType);
		SkuList.addAll(GoldSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); // go to Transaction

		String ErpGoldRate24k = ErpGoldRateFor24K;
		erpUtils.ResetRemainingReturnWeight();		
		for (String Sku : SkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> currentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			erpUtils.ErpSkuIngredientItemCalculation(currentSkuData, SkuCounter,TotalRgWeightReturn,PlatinumPurityReturn,SkuGoldRateReturn,ErpGoldRate24k);
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
		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC27_ProformaInvoice");
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
				/*Double.parseDouble(TotalGrossWeight),
				TotalNetWeight*/);

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

		Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+"in estimation page"); 

		// Step 14: Select the Transaction Type as Sales Return
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_TransationType);

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
		pdfUtils.DownloadAndRenameLatestPDF("TC27SaleReturnInvoice");

		Map<String, String> result = pdfUtils.SalesReturnPDFCredit();
		String ExpSerialNoReturnSale         = result.get("SerialNo");
		String ExpTotalProductsReturnSale    = result.get("TotalProducts");
		String ExpTotalQtyReturnSale         = result.get("TotalQty");
		String ExpGrossAmountReturnSale      = result.get("GrossAmount");
		String ExpTotalNetWtReturnSale       = result.get("TotalNetWt");
		String ExpTotalGrossWtReturnSale     = result.get("TotalGrossWt");
		String ExpTotalNetValueReturnSale    = result.get("TotalNetValue");
		String ExpTaxableValueReturnSale     = result.get("TaxableValue");
		String ExpAdjustmentReturnSale       = result.get("PaymentDetails");
		String ExpTotalValueReturnSale       = result.get("TotalValue");
		int TotalProductsSale = Integer.parseInt(result.get("TotalProducts"));

		// Loop through each SKU/Product
		for (int i = 1; i <= TotalProductsSale; i++) {
			String ExpectedGrossWt   		= result.get("Product" + i + ".GrossWt");
			String ExpectedStoneWt		    = result.get("Product" + i + ".StoneWt");
			String ExpectedNetWt 			= result.get("Product" + i + ".NetWt");
			String ExpectedAmount 			= result.get("Product" + i + ".Amount");
			String ActualGrossWt 			= SkuWiseData.getOrDefault("SKU_" + i + "_GrossWeight", "N/A");
			String ActualStoneWt 			= SkuWiseData.getOrDefault("SKU_" + i + "_StoneWeight", "N/A");
			String ActualNetWt 				= SkuWiseData.getOrDefault("SKU_" + i + "_NetWeight", "N/A");
			String ActualAmount 			= SkuWiseData.getOrDefault("SKU_" + i + "_Total", "N/A");

			Assert.assertEquals(ActualGrossWt, ExpectedGrossWt, "Mismatch in Gross Weight for SKU_" + i +"in PDF Sale Return Invoice details");
			Assert.assertEquals(ActualStoneWt, ExpectedStoneWt, "Mismatch in Stone Weight for SKU_" + i+"in PDF Sale Return Invoice details");
			Assert.assertEquals(ActualNetWt, ExpectedNetWt, "Mismatch in Net Weight for SKU_" + i+"in PDF Sale Return Invoice details");
			Assert.assertEquals(ActualAmount, ExpectedAmount, "Mismatch in Total Amount for SKU_" + i+"in PDF Sale Return Invoice details");
		}
		double ActualTaxableValue 		= Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedTaxableValue  	= Double.parseDouble(ExpTaxableValueReturnSale);

		double ActualGrossAmount 		= Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedGrossAmount 		= Double.parseDouble(ExpGrossAmountReturnSale);

		double ActualTotalGrossWeight   = Math.abs(Double.parseDouble(((String) InvoiceDetails.get("TotalGrossWeight")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedTotalGrossWeight = Double.parseDouble(ExpTotalGrossWtReturnSale);

		double ActualNetWeight			= Double.parseDouble(((String) InvoiceDetails.get("TotalNetWeight")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		double ExpectedNetWeight 		= Double.parseDouble(ExpTotalNetWtReturnSale);

		double ActualNetTotal 			= Double.parseDouble(((String) InvoiceDetails.get("NetTotal")).replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		double ExpectedNetTotal 		= Double.parseDouble(ExpTotalNetValueReturnSale);

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
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_TransationType2);

		// Step 21: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 22: Select any Sales Representative
		base.setZoom(driver, 60);
		Thread.sleep(3000);
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

		// Step 23:  Click on the Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));

		// Step 24:  Select a Payment method(Cash or Card)
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentHDFCCard = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_ApprovalCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC27_ApprovalCodeAgain);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 25:  post sales return bill
		// Expected Result: Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice   		     = pdfUtils.DownloadAndRenameLatestPDF("TC27SaleInvoice");
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
