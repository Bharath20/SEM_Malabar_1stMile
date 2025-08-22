package testPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import utilPack.MathUtilities;
import utilPack.PdfUtilities;
import utilPack.AppUtilities.ReturnDetails;


public class NormalSalesReturnDiamondPlatinumReturnSaleCounter extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	PdfUtilities pdfUtils;
	ErpUtilities erpUtils;
	MathUtilities mathUtils;

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
		mathUtils = new MathUtilities(base);
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
		Map<String, String> ScannedSkuDataMapSale = (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");
		Map<String, String> TransactionDataLineSale = (Map<String, String>) InvoiceDetails.get("TransactionDataLine");
		String LinesCountSale = TransactionDataLineSale.get("LinesCount");
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
		Map<String, String> AdjustedScannedSkuMap = erpUtils.MergeScannedSkuDataMaps(ScannedSkuDataMapSale, ScannedSkuDataMap);
		erpUtils.ErpValidateTransactionLineCalculation(TransactionDataLine, AdjustedScannedSkuMap,LinesCountSale);

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
		String TotalAmountReturn	    = TransactionRecallDataLineReturn.get("TotalAmount").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
		String NetTotalReturn 			= TransactionRecallDataLineReturn.get("NetTotal").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
		String LinesCountReturn 		= TransactionRecallDataLineReturn.get("LinesCount");
		mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLineReturn, ScannedSkuDataMapSale);

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
		String TotalAmountReturn	    = TransactionRecallDataLineReturn.get("TotalAmount").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
		String NetTotalReturn 			= TransactionRecallDataLineReturn.get("NetTotal").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
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

	// <summary>
	// Test Case Title : Sale a sales returned diamond item with OG
	// Automation ID : TC28
	// Author : Neethu
	// </summary>

	public void TC28_NormalSalesReturnDiamondPlatinumReturnSaleCounter() throws Exception 
	{

		//Prerequisite :1.Take the bill number (receipt number) that has previously been sold(Sale, Sales Return, Counter In)	
		List<String> FetchInvoiceAndSku = appUtils.GetInvoiceNoAfterNormalSaleFetch(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_CustomerNo, NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC_28ItemList);		 
		Thread.sleep(3000);
		String InvoiceNo            = FetchInvoiceAndSku.get(0);
		String ActualCustomerName   = pdfUtils.ExtractCustomerNameFromSaleInvoice();
		Thread.sleep(3000);
		List<String> FetchedDiamondSku = FetchInvoiceAndSku.subList(1, FetchInvoiceAndSku.size());	
		Thread.sleep(4000);
		appUtils.SalesReturnFlow(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_CustomerNo, InvoiceNo,NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_ReturnProducts);
		Thread.sleep(2000);
		appUtils.CounterFlow("Diamond","Gold",FetchedDiamondSku,UtilityTestData.Terminal);

		//Step 1 : Login to POS
		//Step 2 : Click on Transaction button
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		Thread.sleep(2000);

		// Step 3 : Select customer
		// Step 4 : Click on add to estimation button
		appUtils.SearchByCustomerID(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_CustomerNo,  UtilityTestData.MenuBarOptions[5]);

		// Step 5 : Scan SKU (sale return) 
		// Expected:Check calculation,  Note:Billing Screen,No of Product lines, Displayed Item Name, Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		// Step 6 : click on Add to cart button
		List<String> ItemNames           = new ArrayList<>();
		List<String> TotalForEachItem    = new ArrayList<>();
		List<String> Quantity            = new ArrayList<>();
		Map<String, String> ItemDetails  = new HashMap<>();
		for (int i = 0; i < FetchedDiamondSku.size(); i++) {
			appUtils.SKUIngridentTableValues(FetchedDiamondSku.get(i), i + 1, ItemDetails);
			ItemNames.add(ItemDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItem.add(ItemDetails.get("SKU_" + (i+1) + "_Total"));
			Quantity.add(ItemDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ItemDetails, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(FetchedDiamondSku, ItemDetails);
		String SubTotal         = TransactionPageItems.get("Subtotal");
		String Discount         = TransactionPageItems.get("Discount");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight   = TransactionPageItems.get("NetWeight");
		String Gst              = TransactionPageItems.get("Tax");
		String InvoiceTotal     = TransactionPageItems.get("TotalAmount");
		String NetTotal         = TransactionPageItems.get("NetTotal");
		String LinesCount       = TransactionPageItems.get("LinesCount");

		mathUtils.ValidateTransactionLineCalculation(TransactionPageItems, ItemDetails);
		List<String> SkuNameElementsBillingPge = base.GetElementTexts(
				NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5")
				);
		for (int i = 0; i < SkuNameElementsBillingPge.size(); i++) {
			String ActualSkuName = SkuNameElementsBillingPge.get(i).trim();
			String ExpectedSkuName = ItemDetails.get("SKU_" + (i + 1) + "_SKUName");
			Assert.assertNotNull(ExpectedSkuName, "Expected SKU name is null for SKU_" + (i + 1));

			String[] SplitName = ExpectedSkuName.trim().split(" - ", 2);
			String ExpectedProductNameOnly = (SplitName.length > 1) ? SplitName[1].trim() : ExpectedSkuName.trim();

			System.out.println("Asserting SKU_" + (i + 1) + " Name: Expected = " + ExpectedProductNameOnly + ", Actual = " + ActualSkuName);
			Assert.assertEquals(ActualSkuName, ExpectedProductNameOnly, "After Calculation Mismatch in SKU name Actual:"+ActualSkuName+" and SKU name Expected:"+ExpectedProductNameOnly+"  for SKU_" + (i + 1)+ "in Billing Page");
		}

		//Step 7: Search OG
		//Step 8:Click on OG own
		//Step 9: Select configuration
		//Step 10: Click on Add item button
		//Step 11:Select QC person
		//Step 12:Select Smith person
		//Step 13:Enter piece value
		//Step 14:Enter gross weight
		//Step 15:click on calculate button
		//Expected Result:  Check Exchange rate(Board rate ) ,* Check calculation Note:Billling Screen , *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		appUtils.PurchaseOldGold(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_Configuration, 
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_PurchaseOrExchange,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_QCPerson,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_SmithPerson,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_GrossPieces,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_GrossWeight);

		//* Check calculation on OG Item detail page
		Map<String, Double> OgDataMap = new LinkedHashMap<>();
		String OgQty = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column3 textRight", "h4 ellipsis cell"));
		String OgRate = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column4 textRight", "h4 ellipsis cell"));
		String OgCvalue = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column5 textRight", "h4 ellipsis cell"));

		Double OgQtyIngredientTable =  Double.parseDouble(OgQty);
		Double OgRateIngredientTable = Double.parseDouble(OgRate);
		Double OgCValueIngredientTable = Double.parseDouble(OgCvalue);

		Double OgCalculatedCvalue = OgQtyIngredientTable * OgRateIngredientTable;
		OgDataMap.put("OgQty", OgQtyIngredientTable);
		OgDataMap.put("OgCvalue",OgCValueIngredientTable);

		asrt.assertEquals(OgCalculatedCvalue, OgCValueIngredientTable,1, "Mismatch in Cvalue,  Expected " + OgCalculatedCvalue+" but got "+OgCValueIngredientTable+" in  Old Gold Item details page");
		Double OgNetRateValue = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));
		asrt.assertEquals(OgRateIngredientTable, OgNetRateValue,1,"Mismatch in net rate, Expected " + OgRateIngredientTable+" but got "+OgNetRateValue+" in  Old Gold Item details page");
		Double OgNetAmountValue = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(OgCalculatedCvalue, OgNetAmountValue,1," Mismatch in Net amount, Expected " + OgCalculatedCvalue+" but got "+OgNetAmountValue+" in  Old Gold Item details page");
		Double TotalAmountValue =Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt")));
		asrt.assertEquals(OgCalculatedCvalue, TotalAmountValue,1, "Mismatch in Total amount value , Expected " + OgCalculatedCvalue+" but got "+TotalAmountValue+" in 22k Old Gold Item details page");
		Thread.sleep(1000);

		//Step 16:Click on Add to cart button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		Thread.sleep(1000);

		By CartChangedAlert = NormalSaleGoldAndSilverObj.Ele_CustomerAdjustment("cart has changed");
		By OkBtn = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");

		if (base.isElementPresent(driver, CartChangedAlert)) {		
			try {
				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String>     TextOGItemNamesBilling =new ArrayList<>();
		List<String>     TextItemNamesBilling =new ArrayList<>();
		List<String>     TextItemQtyBilling =new ArrayList<>();

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(FetchedDiamondSku, ItemDetails);		
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

		String SalesReturnSkuItemRate = ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.\\-]", "");
		String PdfSalesReturnSkuItemRate = 	ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.-]", "");
		double FirstItemRate = Double.parseDouble(SalesReturnSkuItemRate);

		double ExpectedSubTotal = 0.0;
		ExpectedSubTotal = FirstItemRate-(OgCalculatedCvalue);

		double ExpectedTaxAmnt = FirstItemRate * 3 / 100;  //-653.08
		String StrEpectedTaxAmnt = String.valueOf(ExpectedTaxAmnt);
		double ExpectedTaxAmount = Double.parseDouble(StrEpectedTaxAmnt.trim().replaceAll("[^\\d.\\-]", ""));

		double ActualTotalAmountInBilling = ExpectedSubTotal + ExpectedTaxAmount;

		asrt.assertEquals(ExpectedTaxAmount,TaxBilling,1.0,"Mismatch in Tax value, expected "+ExpectedTaxAmount+" but got "+TaxBilling+" in the billing screen");

		for (int n=0; n<ItemNamesBilling.size();n++)
		{
			String ItemName=ItemNamesBilling.get(n).getText().trim();
			if(ItemName.contains("OG"))
			{TextOGItemNamesBilling.add(ItemName);}
			else {TextItemNamesBilling.add(ItemName);}
		}
		Thread.sleep(3000);
		//ITems Sales And SalesReturn
		for(int ItemName=0;ItemName<TextItemNamesBilling.size();ItemName++)
		{
			String ExpectedFull =TextItemNamesBilling.get(ItemName);
			String ExpectedTrimmed = ExpectedFull.contains("-") ?ExpectedFull.substring(ExpectedFull.indexOf("-") + 2) : ExpectedFull;
			String ActualItem = ItemNamesBilling.get(ItemName).getText().trim();
			asrt.assertEquals(ExpectedTrimmed,ActualItem, "Mismatch in item name, expected "+ExpectedTrimmed+"but got "+ActualItem+"  in the billing screen.");
		}


		for (int i=0; i<TextOGItemNamesBilling.size();i++)
		{
			String ActualItemOG = TextOGItemNamesBilling.get(i).trim();
			asrt.assertEquals(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_OgItemName, ActualItemOG,"Mismatch in OG item name, expected "+NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_OgItemName+" but got "+ActualItemOG+" in the billing screen.");

		}		

		for (int i = 0; i <ItemNamesBilling.size(); i++)
		{
			String ItemName=ItemNamesBilling.get(i).getText().trim();
			if(ItemName.contains("OG"))
				break;    
			else {	
				String ItemRate= ItemRateBilling.get(i).getText();
				double ExpectedTotal = Double.parseDouble(ItemRate.replaceAll("[^\\d.]", ""));
				String ActualTotalBilling = ItemRateBilling.get(i).getText().trim();
				double ActualTotal = Double.parseDouble(ActualTotalBilling.replaceAll("[^\\d.]", ""));
				asrt.assertEquals(ExpectedTotal, ActualTotal,1.0,"Mismatch in total amounts (without tax),expected "+ExpectedTotal+" but got "+ActualTotal+" in the Billing screen.");		}
		}

		String FirstItemRateBilling= ItemRateBilling.get(0).getText().replace("-", "").trim();

		String TextItemRateBilling= ItemRateBilling.get(1).getText().replace("-", "").trim();
		Double ActualRateOG22k = Double.parseDouble(TextItemRateBilling.replace("\u20B9", "").replace(",", "").trim());
		asrt.assertEquals(OgCalculatedCvalue, ActualRateOG22k,1.0, "Mismatch in total amount for 22K OG, expected "+OgCalculatedCvalue+" but got "+ActualRateOG22k+" in Billing screen");
		asrt.assertEquals(ExpectedSubTotal,SubTotalBilling,1.0,"Mismatch in Subtotal value, expected "+ExpectedSubTotal+" but got "+SubTotalBilling+"in the Billing screen");	
		asrt.assertEquals(TotalAmountBilling,ActualTotalAmountInBilling,1.0,"Mismatch in Total amount value, expected "+TotalAmountBilling+" but got "+ActualTotalAmountInBilling+" in the Billing screen");

		//Step 17: Click on Save to estimation button
		//Step 18: Save to estimation 
		//Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CustomerAdjustment("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(5000);	
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC28_ProformaInvoice");
		Thread.sleep(5000);		
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FirstInvoice+"";
		String TaxableValue = PdfSalesReturnSkuItemRate;
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

		//Step 19:Recall estimate from cashier
		//Expected Result:Verify whether the recalled item is the same as the item in the cart.
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));		

		//Validation Steps : Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax)
		String EstmnNumberRecallPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstmnNumberRecallPge,EstmnNumber,"Mismatch in Estimation No:, expected "+EstmnNumberRecallPge+" but got "+EstmnNumber+"in the  screen");	

		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_TransactionTypeExchnge);

		// Step 20: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 
		base.setZoom(driver, 60);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		if (base.isElementPresent(driver,NormalSaleGoldAndSilverObj.Btn_Estimation("cancelButton primaryButton"))) {
			try { 
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} catch (Exception e) {
			}
		}

		// Step 21:  Post exchange invoice
		// Expected Result: Check Exchange invoice details
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> PurchaseBillData =pdfUtils.OGPdfValidation(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_PaymentDetails);
		String ExchngeAdjustment =PurchaseBillData.get("PaymentDetails");

		//Step 22: Again recall estimate from cashier screen
		// Expected Result: User should able to recall the estimate for sales 
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		Thread.sleep(3000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Step 23: Select the Transaction Type as Sales        
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"),NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_TransactionTypeSales);

		// Step 24: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));		
		String DiscountBilling = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC= base.GetText(NormalSaleGoldAndSilverObj.Sel_AmountDue("Amount due"));
		double PaymentAmountHDFCTrimmedValue= Double.parseDouble(PaymentAmountHDFC.trim().replaceAll("[^\\d.]", ""));
		String PdfPaymentAmountHDFC = String.valueOf(PaymentAmountHDFCTrimmedValue);

		Map<String, String> SkuBillingScrnTableData = appUtils.TransactionSKUsLinesVerify(FetchedDiamondSku, ItemDetails);
		String SkuNetTotal = SkuBillingScrnTableData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTableData.get("LinesCount");
		String TotalNetWeightRecall 	 =   SkuBillingScrnTableData.get("NetWeight");

		double SkuNetTotalTrimmedValue= Double.parseDouble(SkuNetTotal.trim().replaceAll("[^\\d.]", ""));
		String PdfSkuNetTotal = String.valueOf(SkuNetTotalTrimmedValue);

		//Step 25: Click on amount
		//Step 26: Select payment method(cash/card)
		appUtils.PaymentAfterRecallEstimate(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC28_PaymentMethod);

		//Step 27: Post exchange invoice(sale)
		//Expected Result: Check final invoice details,* save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC28_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";
		String GrossAmount = PdfSalesReturnSkuItemRate;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String Adjustment = ExchngeAdjustment;
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
				FetchedDiamondSku,
				ItemDetails);

		System.out.println("PDF INVOICE NO:-"+PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();


	}
	// <summary>
	// Test Case Title : Sales return of platinum and diamond with od and a sale
	// Automation ID : TC29
	// Author : Vishnu Manoj K
	// </summary>
	public void TC29_NormalSalesReturndiamondPlatinumReturnSaleCounter() throws Exception {

		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		List<String> ItemList = NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_ItemList;
		Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSale(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_CustomerNo, ItemList);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		String InvoiceNo            = (String) InvoiceDetails.get("InvoiceNo");
		String TotalNetWeightReturn = (String) InvoiceDetails.get("TotalNetWeight");
		String SkuPurityReturn      = (String) InvoiceDetails.get("FirstSkuPurity");
		String SkuGoldRateReturn    = (String) InvoiceDetails.get("FirstSkuGoldRate");
		String TotalRgWeightReturn  = (String) InvoiceDetails.get("TotalRgWeight");
		// Extract SKU-wise map
		Map<String, String> SkuWiseData = (Map<String, String>) InvoiceDetails.get("SKUWiseDetails");

		//  Print only SKU_1_NetWeight and SKU_1_GrossWeight
		Map<String, String> ScannedSkuDataMapSale = (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");
		Map<String, String> TransactionDataLineSale = (Map<String, String>) InvoiceDetails.get("TransactionDataLine");
		String LinesCountSale = TransactionDataLineSale.get("LinesCount");

		String ErpGoldRateFor24K= erpUtils.SetMetalRateInERP(SkuGoldRateReturn, SkuPurityReturn);

		Thread.sleep(120000); //Wait needed for ERP Gold Rate to Change

		//Step 3 : Select the customer 
		//Step 4 : Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_CustomerNo,UtilityTestData.MenuBarOptions[5]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product (multiple gold) one by one and click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		List<String> ToReturnProducts = NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_ReturnProducts;
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, ToReturnProducts);
		String ExpectedCustomerName = NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_CustomerName;

		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName);

		//Step 9  :  Scan multiple SKU (Gold,Uncut, Precia etc)
		//Step 10 :  Click on add to cart button
		//Expected Result: Check calculationNote:Billling Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		// Gold SKUs
		List<String> GoldSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_SkuGoldCount,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_TransferType,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_FromCounter,
				NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_MetalType);
		SkuList.addAll(GoldSkus);

		WebDriverWait HamBurgerWait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			if (HamBurgerWait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"))) != null)
			{
				Thread.sleep(3000);
				base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
			}
		} catch (Exception e) {
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_abc(
					"height48 centerY","Show or hide navigation bar"));
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
		}
		String ErpGoldRate24k = ErpGoldRateFor24K;
		double LastPurity = 0.0;

		erpUtils.ResetRemainingReturnWeight();
		for (String Sku : SkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> currentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			double CurrentPurity=erpUtils.ErpSkuIngredientItemCalculation(currentSkuData, SkuCounter,TotalRgWeightReturn,SkuPurityReturn,SkuGoldRateReturn,ErpGoldRate24k);
			LastPurity = CurrentPurity; 
			SkuCounter++;
		}
		String FinalPurityWithK = Math.round(LastPurity) + "k";
		double CurrentRate = (Double.parseDouble(ErpGoldRateFor24K) / 24) * LastPurity;

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

		// Step 11: Search OD
		// Step 12: Click on OD own
		// Step 13: Select configuration
		// Step 14: Click on Add item button
		// Step 15: Select QC person
		// Step 16: Select Smith person
		// Step 17: Enter piece value
		// Step 18: Enter gross weight
		// Step 19: Click on calculate button
		// Step 20: Click on Add to cart button
		// Expected Result:  Check Exchange rate(Board rate ) ,* Check calculation Note:Billling Screen , *No of Product lines
		//*Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		String ActualTotalAmountInBillingPge ="";
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_Product);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Products"));
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		By locator = NormalSaleGoldAndSilverObj.Btn_OP("dataListLine", UtilityTestData.OldDiamondProduct);

		int attempts = 0;
		while (attempts < 3) {
			try {
				WebElement oldDiamondElement = Wait.until(ExpectedConditions.elementToBeClickable(locator));
				oldDiamondElement.click();
				break;
			} catch (StaleElementReferenceException e) {
				System.out.println("Retrying click due to stale element...");
			}
			attempts++;
		}

		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),FinalPurityWithK);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_QcAndSmithPerson);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_QcAndSmithPerson);

		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"), NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_PiecesByPurity);

		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"), NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_GrossWeightByPurity);

		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(4000);
		double ActualGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));//purchase
		double ExpectedGoldRateInItemDetailsPge = (double) Math.round(CurrentRate);
		asrt.assertEquals(ExpectedGoldRateInItemDetailsPge, ActualGoldRateInItemDetailsPge,1.0,"ODPurchaseRate mismatch: Expected value is "+ExpectedGoldRateInItemDetailsPge+" but got "+ActualGoldRateInItemDetailsPge+" in item details page");

		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetwt"));		
		double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
		double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
		String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetamt"));
		double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);
		asrt.assertEquals(ExpectedGoldAmountInItemDetailsPge, ActualGoldAmountInItemDetailsPge,1.0,"Gold Amount(Cvalue) mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in item details page");		

		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt"));
		double ActualTotalAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
		double ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPge;//+ActualDiamondAmountInItemDetailsPge
		asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

		String ItemNameForOD = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = ItemNameForOD.substring(ItemNameForOD.indexOf("-") + 2);

		Map<String, String> ScannedSkuDataMapOD = new LinkedHashMap<>();	
		appUtils.ItemDetailsTableValues(1,ScannedSkuDataMapOD);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandimage"));
		try {
			By dialogButtonLocator = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");
			// Wait until the element is visible
			Wait.until(ExpectedConditions.visibilityOfElementLocated(dialogButtonLocator));
			// Click the button
			base.buttonClick(dialogButtonLocator);
			System.out.println("DefaultMessageDialogButton clicked successfully.");

		} catch (TimeoutException e) {
			System.out.println("DefaultMessageDialogButton was not visible within the timeout.");
		} catch (Exception e) {
			System.out.println("Error while trying to click DefaultMessageDialogButton: " + e.getMessage());
		}

		Map<String, String> TransactionDataLineOD = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMapOD);
		String SubTotalOD		 		= TransactionDataLineOD.get("Subtotal");
		String DiscountOD 			    = TransactionDataLineOD.get("Discount");
		String TotalGrossWeightOD 	    = TransactionDataLineOD.get("GrossWeight");//wrong
		String TotalNewNetWeightOD 	    = TransactionDataLineOD.get("NetWeight");
		String TaxOD 					= TransactionDataLineOD.get("Tax");
		String TotalAmountOD 			= TransactionDataLineOD.get("TotalAmount");
		String NetTotalOD			    = TransactionDataLineOD.get("NetTotal");
		String LinesCountOD		        = TransactionDataLineOD.get("LinesCount");
		Map<String, String> AdjustedScannedSkuMapOD = erpUtils.MergeScannedSkuDataMaps(AdjustedScannedSkuMap, ScannedSkuDataMapOD);
		//erpUtils.ErpValidateTransactionLineCalculationSubtract(TransactionDataLineOD, AdjustedScannedSkuMapOD,LinesCountSale);
        erpUtils.ErpValidateTransactionLineCalculationSubtract(TransactionDataLineOD, AdjustedScannedSkuMapOD,LinesCountSale,"1",true);
		
        // Step 21: Save Esimate
		// Expected Result:Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_abc("buttonsContainer positionRelative","Back" ));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		Thread.sleep(500);
		String ActualEstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// PDF Verification Step
		Thread.sleep(7000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC29_ProformaInvoice");
		Thread.sleep(2000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst                 = TaxOD.replaceAll("[^\\d.]", "");
		String TaxableValue		   = SubTotal.replaceAll("[^\\d.]", "");
		String InvoiceTotal        = TotalAmountOD.replaceAll("[^\\d.]", "");
		String TotalAmnt           = SubTotalOD.replaceAll("[^\\d.]", "");
		NetTotalOD                 = NetTotalOD.replaceAll("[^\\d.]", "");
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotalOD,
				TotalAmnt);

		// Step 22: Recall estimate from cashier screen
		// Step 23: Select estimate and click on recall estimation
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

		//Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+""); 

		// Step 24: Select transaction type as "Sales return" and click on Process estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_TransationType);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 25: Select any Sales Representative
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

		//mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLineReturn, ScannedSkuDataMapSale);

		// Step 26:  Click on the Amount For printing Receipt
		// Expected Result: Check Return Invoice details
		appUtils.PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
				UtilityTestData.SalePersonNumber,
				UtilityTestData.SalePersonName,
				UtilityTestData.DueYear,
				UtilityTestData.NomineeName,
				UtilityTestData.NomineeRelation
				);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

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
		System.out.println("TotalAmountReturn "+TotalAmountReturn);
		System.out.println("ExpAdvanceReceived "+ExpectedAdvanceReceived);
		double TotalAmountSale = Double.parseDouble(TotalAmountReturn.replaceAll("[^\\d.]", ""));
		Assert.assertEquals(TotalAmountSale, ExpectedAdvanceReceived, 1.00, 
				"Mismatch of total amount in billing screen " + TotalAmount + " and " + ExpectedAdvanceReceived + " in Invoice PDF");

		System.out.println("ActMaxGoldRate "+ActualMaxGoldRate);
		System.out.println("ExpFixedGoldRate "+ExpectedFixedGoldRate);
		asrt.assertEquals(ActualMaxGoldRate, ExpectedFixedGoldRate,1, "Mismatch of Fixed Gold Rate"+ExpectedFixedGoldRate+" and "+ActualMaxGoldRate+" Max Gold Rate in Invoice PDF");
		asrt.assertEquals(UtilityTestData.DepositType, DepositType, "Mismatch of Deposit Type "+UtilityTestData.DepositType+" and "+DepositType+ " in Invoice PDF");

		if(ApproxWeight != "" || ApproxWeight != null)
		{
			Double ApproxWeightValue = ExpectedAdvanceReceived/ActualMaxGoldRate;
			System.out.println("ApproxWeightValue "+ApproxWeightValue);
			BigDecimal RoundedApproxWeightValue = new BigDecimal(ApproxWeightValue).setScale(3, RoundingMode.HALF_UP);
			System.out.println("RoundedApproxWeightValue "+RoundedApproxWeightValue);

			double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());
			System.out.println("ApproxWeightPDFValue "+ApproxWeightPdfValue);
			asrt.assertEquals(ApproxWeightPdfValue, RoundedApproxWeightValue.doubleValue(),1, "Mismatch of ApproxWeightValue "+ApproxWeightPdfValue+" and "+RoundedApproxWeightValue.doubleValue()+" ApproxWeightPDFValue");
		}

		// Step 27:  In cash, click on "Recall Estimate"
		// Step 28:  Select the estimate, Click on Recall Estimation
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

		// Step 29: Select transaction type as "Exchange" and click on Process estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_TransationType3);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 30: Select any Sales Representative
		base.setZoom(driver, 60);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		Map<String, String> TransactionRecallDataLineExchange = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMapOD);
		String SubTotalExchange			 =   TransactionRecallDataLineExchange.get("Subtotal");
		String DiscountExchange			 =   TransactionRecallDataLineExchange.get("Discount");
		String TotalGrossWeightExchange	 =   TransactionRecallDataLineExchange.get("GrossWeight");
		String TotalNetWeightExchange 	 =   TransactionRecallDataLineExchange.get("NetWeight");
		String TotalAmountExchange		 =   TransactionRecallDataLineExchange.get("TotalAmount");
		String NetTotalExchange			 =   TransactionRecallDataLineExchange.get("NetTotal");
		String LinesCountExchange		 =   TransactionRecallDataLineExchange.get("LinesCount");
		String AdjustmentExchange		 =   TransactionRecallDataLineExchange.get("Payments");
		//mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLineExchange, ScannedSkuDataMapOD);

		// Step 31:  Click on the Amount For printing Receipt
		// Expected Result: Check final invoice details * save receipt id for future reference
		appUtils.PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
				UtilityTestData.SalePersonNumber,
				UtilityTestData.SalePersonName,
				UtilityTestData.DueYear,
				UtilityTestData.NomineeName,
				UtilityTestData.NomineeRelation
				);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		//PDF validation ADVANCE RECEIPT VOUCHER details : Towards the advance against purchase of approximate value, Fixed Gold rate, Deposit Type, Advance Received
		Map<String, String> ResultExchange = pdfUtils.NormalAdvancePdfValidation();
		System.out.println("Receipt Id: " + ResultExchange.get("ReceiptId"));
		System.out.println("Max Gold Rate: " + ResultExchange.get("MaxGoldRate"));
		System.out.println("Advance Received: " + ResultExchange.get("AdvanceReceived"));
		System.out.println("Payment Amount: " + ResultExchange.get("PaymentAmount"));
		System.out.println("Approx Weight: " + ResultExchange.get("ApproxWeight"));
		System.out.println("Fixed Gold Rate: " + ResultExchange.get("FixedGoldRate"));
		System.out.println("Deposit Type: " + ResultExchange.get("DepositType"));
		System.out.println("Payment Mode: " + ResultExchange.get("PaymentMode"));
		System.out.println("Payment Amount: " + ResultExchange.get("PaymentAmount"));

		String AdvanceReceivedExchange = ResultExchange.get("AdvanceReceived");
		String FixedGoldRateExchange = ResultExchange.get("FixedGoldRate");
		String MaxGoldRateExchange = ResultExchange.get("MaxGoldRate");
		String ApproxWeightExchange = ResultExchange.get("ApproxWeight");
		String DepositTypeExchange = ResultExchange.get("DepositType");

		double ExpectedAdvanceReceivedExchange = Double.parseDouble(AdvanceReceivedExchange.replaceAll("[^\\d.]", "").trim());
		double ExpectedFixedGoldRateExchange = Double.parseDouble(FixedGoldRateExchange.replaceAll("[^\\d.]", "").trim());
		double ActualMaxGoldRateExchange = Double.parseDouble(MaxGoldRateExchange.replaceAll("[^\\d.]", "").trim());
		System.out.println("TotalAmountReturnExchange "+NetTotalExchange);
		System.out.println("ExpAdvanceReceived "+ExpectedAdvanceReceivedExchange);
		double NetTotalSaleExchange = Double.parseDouble(NetTotalExchange.replaceAll("[^\\d.]", ""));
		Assert.assertEquals(NetTotalSaleExchange, ExpectedAdvanceReceivedExchange, 1.00, 
				"Mismatch of total amount in billing screen " + NetTotalSaleExchange + " and " + ExpectedAdvanceReceivedExchange + " in Invoice PDF");

		System.out.println("ActMaxGoldRateExchange "+ActualMaxGoldRateExchange);
		System.out.println("ExpFixedGoldRateExchange "+ExpectedFixedGoldRateExchange);
		asrt.assertEquals(ActualMaxGoldRateExchange, ExpectedFixedGoldRateExchange,1, "Mismatch of Fixed Gold Rate"+ExpectedFixedGoldRateExchange+" and "+ActualMaxGoldRateExchange+" Max Gold Rate in Invoice PDF");
		asrt.assertEquals(UtilityTestData.DepositType, DepositType, "Mismatch of Deposit Type "+UtilityTestData.DepositType+" and "+DepositType+ " in Invoice PDF");

		if(ApproxWeightExchange != "" || ApproxWeightExchange != null)
		{
			Double ApproxWeightValueExchange = ExpectedAdvanceReceivedExchange/ActualMaxGoldRateExchange;
			System.out.println("ApproxWeightValueExchange "+ApproxWeightValueExchange);
			BigDecimal RoundedApproxWeightValueExchange = new BigDecimal(ApproxWeightValueExchange).setScale(3, RoundingMode.HALF_UP);
			System.out.println("RoundedApproxWeightValueExchange "+RoundedApproxWeightValueExchange);

			double ApproxWeightPdfValueExchange = Double.parseDouble(ApproxWeightExchange.replaceAll("[^\\d.]", "").trim());
			System.out.println("ApproxWeightPDFValueExchange "+ApproxWeightPdfValueExchange);
			asrt.assertEquals(ApproxWeightPdfValueExchange, RoundedApproxWeightValueExchange.doubleValue(),1, "Mismatch of ApproxWeightValue "+ApproxWeightPdfValueExchange+" and "+RoundedApproxWeightValueExchange.doubleValue()+" ApproxWeightPDFValue");
		}

		// Step 32:  In cash, click on "Recall Estimate"
		// Step 33:  Select the estimate, Click on Recall Estimation
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

		// Step 34: Select transaction type as "Sales" and click on Process estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturndiamondPlatinumReturnSaleCounterTestData.TC29_TransationType2);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 35: Select any Sales Representative
		base.setZoom(driver, 60);
		Thread.sleep(3000);
		Wait.until(ExpectedConditions.elementToBeClickable(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"))).click();

		Map<String, String> TransactionRecallDataLineRecall = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotalRecall			 =   TransactionRecallDataLineRecall.get("Subtotal");
		String DiscountRecall 			 =   TransactionRecallDataLineRecall.get("Discount");
		String TotalGrossWeightRecall	 =   TransactionRecallDataLineRecall.get("GrossWeight");
		String TotalNetWeightRecall 	 =   TransactionRecallDataLineRecall.get("NetWeight");
		String TotalAmountRecall		 =   TransactionRecallDataLineRecall.get("TotalAmount");
		String NetTotalRecall			 =   TransactionRecallDataLineRecall.get("NetTotal");
		String LinesCountRecall			 =   TransactionRecallDataLineRecall.get("LinesCount");
		String AdjustmentReturn			 =   TransactionRecallDataLineReturn.get("Payments");

		mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLineRecall, ScannedSkuDataMap);

		// Step 36:  Click on the Amount For printing Receipt
		// Expected Result: Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice   		     = pdfUtils.DownloadAndRenameLatestPDF("TC29SaleInvoice");
		Thread.sleep(2000);
		String SalesPdfPath  		     = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice;
		String GrossAmount    		     = SubTotalRecall.replaceAll("[^\\d.\\-]", "");
		String TotalDiscount  		     =DiscountRecall;
		String TotalQtyPcs    		     =LinesCountRecall;
		String TaxableValueSale		     =TotalAmountRecall.replaceAll("[^\\d.\\-]", "");
		String SanitizedNetTotalRecall   = NetTotalRecall.replaceAll("[^\\d.\\-]", "");
		String SanitizedAdjustmentReturn = AdjustmentReturn.replaceAll("[^\\d.\\-]", "");
		String NetValueSale              =TotalAmountRecall.replaceAll("[^\\d.\\-]", ""); //String.format("%.2f", (Math.abs(Double.parseDouble(SanitizedNetTotalRecall)) + Math.abs(Double.parseDouble(SanitizedAdjustmentReturn))));
		String TotalDiamond              ="";
		String PaymentAmountHDFC         ="";
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
