package testPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
import testData.NormalSalesReturnGoldSilverReturnSaleCounter_TestData;
import testData.POC_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.AppUtilities.ReturnDetails;
import utilPack.BasePge;
import utilPack.ErpUtilities;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;


public class NormalSalesReturnGoldSilverReturnSaleCounter extends BasePge {

	BasePge base;
	Assert asrt;
	MathUtilities mathUtils;
	AppUtilities appUtils;
	PdfUtilities pdfUtils;
	ErpUtilities erpUtils;

	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();
	Login login = new Login(driver); 
	NormalSalesReturnGoldSilverReturnSaleCounter_TestData NormalSalesReturnGoldSilverReturnSaleCounterTestData = new NormalSalesReturnGoldSilverReturnSaleCounter_TestData();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj = new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj = new OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj();


	public NormalSalesReturnGoldSilverReturnSaleCounter(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base);
		pdfUtils = new PdfUtilities(base);
		erpUtils = new ErpUtilities(base);
		mathUtils = new MathUtilities(base);

	}

	// <summary>
	// Test Case Title : Multiple sales return silver
	// Automation ID : TC13_NormalSalesReturnGoldSilverReturnSaleCounter
	// Author : Anagha
	// </summary>

	public void TC13_NormalSalesReturnGoldSilverReturnSaleCounter() throws Exception
	{
		//PreRequisite : Take the bill number (receipt number) that has previously been sold
		//Step 1 : Login to POS
		//Step 2 : Click on Transaction button
		String InvoiceNo          = appUtils.GetInvoiceNoAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_CustID, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_ItemList);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		//Step 3 : Select the customer
		//Step 4 : Click on add to sale button
		appUtils.SearchByCustomerID(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_CustID, "CustomerSearch_addSelectedCustomerToCartCommand");

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product
		//Step 9 : Click on return button
		//Expected Result : Check calculation : No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal, TAX, Total amount
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_ReturnProducts);

		//Validation for product meant to be returned should be previously bought by the same customer.
		String ExpectedCustomerName = NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_CustomerName;
		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName);

		//Validation Steps : Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax)
		List<WebElement> BillScrnProdName  = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> BillScrnProdQty = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<WebElement> BillScrnProdPrice = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));

		List<String> ReturnProdNames        = Details.productNames;
		List<String> ReturnProdQty      = Details.productQty;
		List<String> ReturnProdPrice    = Details.productPrice;

		for (int i = 0; i < BillScrnProdName.size(); i++)
		{
			String BillScreenProdName = BillScrnProdName.get(i).getText();
			String BillScreenProQty = BillScrnProdQty.get(i).getText().replace("-", "").trim();
			String BillScreenProdPrice = BillScrnProdPrice.get(i).getText().replace("-", "").trim();
			String ExpectedReturnProdNames       = ReturnProdNames.get(i).trim();

			double ExpectedQty        = Double.parseDouble(ReturnProdQty.get(i).trim().replace("-", "").replaceAll("[^\\d.]", ""));
			double ExpectedProdPrice = Double.parseDouble(ReturnProdPrice.get(i).trim().replace("-", "").replaceAll("[^\\d.]", ""));			
			double ActualQty = Double.parseDouble(BillScreenProQty);
			double ActualPrice = Double.parseDouble(BillScreenProdPrice.replace("Ã¢â€šÂ¹", "").replace("\u20B9", "").replace(",", "").trim());

			asrt.assertEquals(ExpectedReturnProdNames, BillScreenProdName,"Item Name does not match between the return and billing screen");
			asrt.assertEquals(ExpectedQty, ActualQty,1, "Quantity does not match between the return and billing screen");
			asrt.assertEquals(ExpectedProdPrice, ActualPrice,1 ,"Price does not match between the return and billing screen");
		}

		//Validation Steps : No of Product lines, Displayed SubTotal, TAX, Total amount
		List<WebElement> ProductCount = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		double TotalWithoutTax = 0.0;
		for (WebElement TotalsWithoutTaxElement : TotalsWithoutTax)
		{
			String TotalWithoutTaxText = TotalsWithoutTaxElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!TotalWithoutTaxText.isEmpty())
			{
				TotalWithoutTax = TotalWithoutTax + Double.parseDouble(TotalWithoutTaxText);
			}
		}
		double TaxAmount = TotalWithoutTax*3/100;
		int ActualNoOfLines = ProductCount.size();     		
		String SubTotalText = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("SubtotalField","row pad12 padBottom0","h4"));
		String TaxText = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TaxField","row pad12 padBottom0","h4"));
		String TotalAmountText = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TotalAmountField","row pad12 padBottom0","h4"));
		String NoOfLinesText = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("LinesField","row pad12 padBottom0","h4"));

		double SubTotal = Double.parseDouble(SubTotalText.replaceAll("[^\\d.]", "").trim());
		double Tax = Double.parseDouble(TaxText.replaceAll("[^\\d.]", "").trim());
		double ExpectedTotalAmount = SubTotal + Tax;
		double TotalAmount = Double.parseDouble(TotalAmountText.replaceAll("[^\\d.]", "").trim());
		int NoOfLines = Integer.parseInt(NoOfLinesText.replaceAll("[^\\d]", "").trim());

		asrt.assertEquals(NoOfLines, ActualNoOfLines, "Mismatch in number of product lines"+NoOfLines+" and "+ActualNoOfLines+" in Billing screen");
		asrt.assertEquals(SubTotal, TotalWithoutTax,1,  "Mismatch of subtotal"+SubTotal+" and "+TotalWithoutTax+ " in Billing screen");
		asrt.assertEquals(Tax, TaxAmount,1, "Mismatch of Tax "+Tax+" and "+TaxAmount+" in Billing screen");
		asrt.assertEquals(TotalAmount, ExpectedTotalAmount,1,  "Mismatch of TotalAmount "+TotalAmount+" and "+ExpectedTotalAmount+" in Billing screen");

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
		//Step 17 : Click on Deposit button
		//Expected Result : User should be able to do the Advance payment successfully       
		appUtils.PaymentModeForDiffTransactions(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_PaymentMode, "", "",
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_SalePersonNumber,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_SalePersonName,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_DueYear,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_NomineeName,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_NomineeRelation
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
		System.out.println("TotAmnt "+TotalAmount);
		System.out.println("ExpAdvanceReceived "+ExpectedAdvanceReceived);
		asrt.assertEquals(TotalAmount, ExpectedAdvanceReceived,1, "Mismatch of total amount in billing screen "+TotalAmount+" and "+ExpectedAdvanceReceived+"in Invoice PDF");

		System.out.println("ActMaxGoldRate "+ActualMaxGoldRate);
		System.out.println("ExpFixedGoldRate "+ExpectedFixedGoldRate);
		asrt.assertEquals(ActualMaxGoldRate, ExpectedFixedGoldRate,1, "Mismatch of Fixed Gold Rate"+ExpectedFixedGoldRate+" and "+ActualMaxGoldRate+" Max Gold Rate in Invoice PDF");
		asrt.assertEquals(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_DepositType, DepositType, "Mismatch of Deposit Type "+NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_DepositType+" and "+DepositType+ " in Invoice PDF");

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
		Thread.sleep(5000);
		//	appUtils.CounterFlow(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_ToCounter,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_MetalType,  NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_SkuValue , UtilityTestData.Terminal);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Sales return of multiple gold and silver items
	// Automation ID : TC14_SalesReturnMultipleGoldandSilver
	// Author : Neethu
	// </summary>

	public void TC14_NormalSalesReturnGoldSilverReturnSaleCounter() throws Exception 
	{
		//Pre-Condition
		String ReceiptNumber = appUtils.GetInvoiceNoAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_CustID,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_ItemList);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

		// Step 1 and Step 2 are done in Prerequisite condition
		//Step 3 : Select the customer 
		//Step 4 : Click on add to sale button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_CustID,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product(gold and silver)
		//Step 9 : Click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		//Expected Result : Check calculation Note:Billing Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(ReceiptNumber, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_ReturnProducts);


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
			double ExpectedPrice = Double.parseDouble(ProdPriceList.get(i).trim().replace("Ã¢â€šÂ¹", "").replace(",", "").replace("-", "").replaceAll("[^\\d.]", ""));

			String BillScreenProdName = BillScrnProdName.get(i).getText();
			String BillScreenProQty = BillScrnProdQty.get(i).getText().replace("-", "").trim();
			String BillScreenProdPrice = BillScrnProdPrice.get(i).getText().replace("-", "").trim();
			double ActualQty = Double.parseDouble(BillScreenProQty);
			double ActualPrice = Double.parseDouble(BillScreenProdPrice.replace("\u20B9", "").replace(",", "").replaceAll("[^\\d.]", "").trim());

			asrt.assertEquals(ExpectedName, BillScreenProdName,"Item Name mismatch: Expected" +ExpectedName+ "but got" +BillScreenProdName+ "in billing screen");
			asrt.assertEquals(ExpectedQty, ActualQty,1.0, "Quantity mismatch: Expected" +ExpectedQty+ "but got" +ActualQty+ "in billing screen");
			asrt.assertEquals(ExpectedPrice, ActualPrice,1.0,"Price mismatch: Expected" +ExpectedPrice+ "but got" +ActualPrice+ "in billing screen");
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
		String SubTotalTextFromReturn = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("SubtotalField","row pad12 padBottom0","h4"));
		String TaxTextFromReturn = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TaxField","row pad12 padBottom0","h4"));
		String TotalAmountTexFromReturn = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TotalAmountField","row pad12 padBottom0","h4"));
		String NoOfLinesTextFromReturn = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("LinesField","row pad12 padBottom0","h4"));

		double SubTotal = Double.parseDouble(SubTotalTextFromReturn.replaceAll("[^\\d.]", "").trim());
		double Tax = Double.parseDouble(TaxTextFromReturn.replaceAll("[^\\d.]", "").trim());
		double ExpectedTotalAmount = SubTotal + Tax;
		double TotalAmount = Double.parseDouble(TotalAmountTexFromReturn.replaceAll("[^\\d.]", "").trim());
		int NoOfLines = Integer.parseInt(NoOfLinesTextFromReturn.replaceAll("[^\\d]", "").trim());

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
		//Step 17 : Click on Deposit button
		//Expected Result : User should be able to do the Advance payment successfully       
		appUtils.PaymentModeForDiffTransactions(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_PaymentMode, "", "",
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_SalePersonNumber,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_SalePersonName,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_DueYear,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_NomineeName,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_NomineeRelation);
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
		asrt.assertEquals(ExpectedTotalAmount, ExpectedAdvanceReceived,1, "Total amount mismatch: Expected "+ExpectedTotalAmount+" but got "+ExpectedAdvanceReceived+"in the Normal Advance Pdf");

		System.out.println("ActMaxGoldRate "+ActualMaxGoldRate);
		System.out.println("ExpFixedGoldRate "+ExpectedFixedGoldRate);
		asrt.assertEquals(ActualMaxGoldRate, ExpectedFixedGoldRate,1, "Fixed Gold Rate mismatch: Expected "+ExpectedFixedGoldRate+" but got "+ActualMaxGoldRate+"in the Normal Advance Pdf");
		asrt.assertEquals(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC14_DepositType, DepositType, "Deposit Type mismatch: Expected "+NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_DepositType+" but got "+DepositType+ "in the Normal Advance Pdf");

		if(ApproxWeight != "" || ApproxWeight != null)
		{
			Double ApproxWeightValue = ExpectedAdvanceReceived/ActualMaxGoldRate;
			System.out.println("ApproxWeightValue "+ApproxWeightValue);
			BigDecimal RoundedApproxWeightValue = new BigDecimal(ApproxWeightValue).setScale(3, RoundingMode.HALF_UP);
			System.out.println("RoundedApproxWeightValue "+RoundedApproxWeightValue);

			double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());
			System.out.println("ApproxWeightPDFValue "+ApproxWeightPdfValue);
			asrt.assertEquals(ApproxWeightPdfValue, RoundedApproxWeightValue.doubleValue(),1, "Mismatch of ApproxWeightValue: Expected: "+ApproxWeightPdfValue+" but got "+RoundedApproxWeightValue.doubleValue()+"in the Normal Advance Pdf");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20"));
		Thread.sleep(5000);


		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Sales return through add to sale
	// Pre-Condition: Created a Sales Estimation manually and passing it to the test case as test data
	// Automation ID : TC20
	// Author : Hasna EK
	// </summary>

	public void TC20_NormalSalesReturnGoldSilverReturnSaleCounter() throws Exception{

		//Pre-Condition
		String ReceiptNumber = appUtils.GetInvoiceNoAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_CustomerId,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_ItemList);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

		//Step 2 : Click on Transaction button
		//base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		//Step 3 : Select the customer
		//Step 4 : Click on add to sale button
		appUtils.SearchByCustomerID(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_CustomerId, "CustomerToCartCommand");	

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product( multiple gold )
		//Step 9 : Click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		//Check calculation, Note:Billing Screen, *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX,*Total Amount
		ReturnDetails ReturnProdDetails = appUtils.ReturnMultipleProductsWithDetails(ReceiptNumber, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_ProductsToReturn);

		List<String> BillScrnProdName  = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> BillScrnProdQty = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> BillScrnProdPrice = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));

		for (int i = 0; i < BillScrnProdName.size(); i++)
		{
			String BillScreenProdName = BillScrnProdName.get(i);
			String BillScreenProdQty = BillScrnProdQty.get(i).replace("-", "").trim();
			String BillScreenProdPrice = BillScrnProdPrice.get(i).replace("-", "").trim();
			double ActualQty = Double.parseDouble(BillScreenProdQty);
			double ActualPrice = Double.parseDouble(BillScreenProdPrice.replace("\u20B9", "").replace(",", "").trim());

			String ReturnProdName = ReturnProdDetails.productNames.get(i);
			String ReturnProdQty = ReturnProdDetails.productQty.get(i);
			String ReturnProdPrice = ReturnProdDetails.productPrice.get(i);
			double ExpectedQty = Double.parseDouble(ReturnProdQty);
			double ExpectedProdPrice = Double.parseDouble(ReturnProdPrice.replace("\u20B9", "").replace(",", "").trim());

			asrt.assertEquals(ReturnProdName, BillScreenProdName,"Item Name mismatch: Expected "+ReturnProdName+" but got "+BillScreenProdName+" in the billing screen");
			asrt.assertEquals(ExpectedQty, ActualQty, 1, "Item quantity mismatch: Expected "+ExpectedQty+" but got "+ActualQty+" in the billing screen");
			asrt.assertEquals(ExpectedProdPrice, ActualPrice,1 ,"Item price mismatch: Expected "+ExpectedProdPrice+" but got "+ActualPrice+" in the billing screen");
		}

		List<String> TotalsWithoutTax = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));    
		double CalculatedSubTotal = 0.0;
		for (String Total : TotalsWithoutTax)
		{
			String SubTotalText = Total.trim().replaceAll("[^\\d.]", "");
			if (!SubTotalText.isEmpty())
			{
				CalculatedSubTotal+= Double.parseDouble(SubTotalText);
			}
		}
		double CalculatedTax = CalculatedSubTotal*3/100;
		double CalculatdTotalAmount = CalculatedSubTotal+CalculatedTax;

		int ExpectedLinesCount = BillScrnProdName.size();     		
		String SubTotalFrmBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("SubtotalField","row pad12 padBottom0","h4"));
		String TaxFrmBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TaxField","row pad12 padBottom0","h4"));
		String TotalAmntFrmBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("TotalAmountField","row pad12 padBottom0","h4"));
		String LinesCountFrmBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SubTot("LinesField","row pad12 padBottom0","h4"));

		double BillingScrnSubTotal = Double.parseDouble(SubTotalFrmBillingPge.replaceAll("[^\\d.]", "").trim());
		double BillingScrnTax = Double.parseDouble(TaxFrmBillingPge.replaceAll("[^\\d.]", "").trim());
		double BillingScrnTotalAmnt = Double.parseDouble(TotalAmntFrmBillingPge.replaceAll("[^\\d.]", "").trim());
		int BillingScrnLinesCount = Integer.parseInt(LinesCountFrmBillingPge.replaceAll("[^\\d]", "").trim());

		asrt.assertEquals(ExpectedLinesCount,BillingScrnLinesCount, "Lines count Mismatch:Expected "+ExpectedLinesCount+" but got "+BillingScrnLinesCount+" in the billing screen");
		asrt.assertEquals(CalculatedSubTotal,BillingScrnSubTotal,1,  "SubTotal mismatch:Expected "+CalculatedSubTotal+" but got "+BillingScrnSubTotal+" in the billing screen");
		asrt.assertEquals(CalculatedTax,BillingScrnTax,1, "Tax vlaue mismatch:Expected "+CalculatedTax+" but got "+BillingScrnTax+" in the billing screen");
		asrt.assertEquals(CalculatdTotalAmount, BillingScrnTotalAmnt,1,  "TotalAmount mismatch:Expected "+CalculatdTotalAmount+" but got "+BillingScrnTotalAmnt+" in the billing screen");

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
		//Step 17 : Click on Deposit button
		//Expected Result : Check ADVANCE RECEIPT VOUCHER details ,*Towards the advance against purchase of approximate value, *Fixed Gold rate,
		//					*Deposit Type, *Advance Received, * save receipt id for future reference
		appUtils.PaymentModeForDiffTransactions(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_PaymentMode, "", "",
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_SalePersonNumber,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_SalePersonName,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_DueYear,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_NomineeName,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_NomineeRelation);

		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		Map<String, String> PdfData= pdfUtils.NormalAdvancePdfValidation();
		System.out.println("Receipt Id: " + PdfData.get("ReceiptId"));
		System.out.println("Advance Received: " + PdfData.get("AdvanceReceived"));
		System.out.println("Approx Weight: " + PdfData.get("ApproxWeight"));
		System.out.println("Fixed Gold Rate: " + PdfData.get("FixedGoldRate"));
		System.out.println("Deposit Type: " + PdfData.get("DepositType"));

		String AdvanceReceived = PdfData.get("AdvanceReceived");
		String FixedGoldRate = PdfData.get("FixedGoldRate");
		String MaxGoldRate = PdfData.get("MaxGoldRate");
		String ApproxWeight = PdfData.get("ApproxWeight");
		String DepositType = PdfData.get("DepositType");

		double ExpectedAdvanceReceived = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
		double ExpectedFixedGoldRate = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
		double ActualMaxGoldRate = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(BillingScrnTotalAmnt, ExpectedAdvanceReceived,1, "Total amount mismatch: Expected "+BillingScrnTotalAmnt+" but got "+ExpectedAdvanceReceived+"in the Normal Advance Pdf");
		asrt.assertEquals(ActualMaxGoldRate, ExpectedFixedGoldRate,1, "Fixed Gold Rate mismatch: Expected "+ExpectedFixedGoldRate+" but got "+ActualMaxGoldRate+"in the Normal Advance Pdf");
		asrt.assertEquals(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_DepositType, DepositType, "Deposit Type mismatch: Expected "+NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC13_DepositType+" but got "+DepositType+ "in the Normal Advance Pdf");

		if(ApproxWeight != "" || ApproxWeight != null)
		{
			Double ApproxWeightValue = ExpectedAdvanceReceived/ActualMaxGoldRate;
			System.out.println("ApproxWeightValue "+ApproxWeightValue);
			BigDecimal RoundedApproxWeightValue = new BigDecimal(ApproxWeightValue).setScale(3, RoundingMode.HALF_UP);
			System.out.println("RoundedApproxWeightValue "+RoundedApproxWeightValue);

			double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());
			System.out.println("ApproxWeightPDFValue "+ApproxWeightPdfValue);
			asrt.assertEquals(ApproxWeightPdfValue, RoundedApproxWeightValue.doubleValue(),1, "Mismatch of ApproxWeightValue: Expected: "+ApproxWeightPdfValue+" but got "+RoundedApproxWeightValue.doubleValue()+"in the Normal Advance Pdf");
		}
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Sales return of multiple gold with multiple category sale items
	// Automation ID : TC15
	// Author : Vishnu Manoj K
	// </summary>
	public void TC15_NormalSalesReturnGoldSilverReturnSaleCounter() throws Exception {

		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		List<String> ItemList = Arrays.asList("2", "Gold", "Gold");
		Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_CustomerNo, ItemList);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		String InvoiceNo            = (String) InvoiceDetails.get("InvoiceNo");
		String TotalNetWeightReturn = (String) InvoiceDetails.get("TotalNetWeight");
		String SkuPurityReturn      = (String) InvoiceDetails.get("FirstSkuPurity");
		String SkuGoldRateReturn    = (String) InvoiceDetails.get("FirstSkuGoldRate");
		String TotalRgWeightReturn  =  (String) InvoiceDetails.get("TotalRgWeight");

		// Extract SKU-wise map
		Map<String, String> SkuWiseData = (Map<String, String>) InvoiceDetails.get("SKUWiseDetails");

		//  Print only SKU_1_NetWeight and SKU_1_GrossWeight
		String Sku1NetWeight   = SkuWiseData.getOrDefault("SKU_1_NetWeight", "N/A");
		String Sku1GrossWeight = SkuWiseData.getOrDefault("SKU_1_GrossWeight", "N/A");
		String Sku1StoneWeight = SkuWiseData.getOrDefault("SKU_1_StoneWeight", "N/A");
		String Sku1Total	   = SkuWiseData.getOrDefault("SKU_1_Total", "N/A");
		String Sku2NetWeight   = SkuWiseData.getOrDefault("SKU_2_NetWeight", "N/A");
		String Sku2GrossWeight = SkuWiseData.getOrDefault("SKU_2_GrossWeight", "N/A");
		String Sku2StoneWeight = SkuWiseData.getOrDefault("SKU_2_StoneWeight", "N/A");
		String Sku2Total       = SkuWiseData.getOrDefault("SKU_2_Total", "N/A");
		Map<String, String> ScannedSKUDataMapSale = (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");
		Map<String, String> TransactionDataLineSale = (Map<String, String>) InvoiceDetails.get("TransactionDataLine");
		String LinesCountSale = TransactionDataLineSale.get("LinesCount");
		String ErpGoldRateFor24K= erpUtils.SetMetalRateInERP(SkuGoldRateReturn, SkuPurityReturn);

		Thread.sleep(120000); //Wait needed for ERP Gold Rate to Change

		//Step 3 : Select the customer 
		//Step 4 : Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_CustomerNo,UtilityTestData.MenuBarOptions[5]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product (multiple gold) one by one and click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		List<String> ToReturnProducts = Arrays.asList("Gold","Gold");
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, ToReturnProducts);
		String ExpectedCustomerName = NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_CustomerName;

		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName);

		//Step 9  :  Scan multiple SKU (Gold,Uncut, Precia etc)
		//Step 10 :  Click on add to cart button
		//Expected Result: Check calculationNote:Billling Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		// Gold SKUs
		List<String> GoldSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_SkuGoldCount,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_TransferType,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_FromCounter,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_MetalType);
		SkuList.addAll(GoldSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); // go to Transaction

		// Uncut SKUs
		List<String> UncutSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_SkuGoldCount2,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_TransferType2,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_FromCounter2,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_MetalType2);
		SkuList.addAll(UncutSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); 

		// Diamond SKUs
		List<String> DiamondSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_SkuGoldCount3,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_TransferType3,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_FromCounter3,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_MetalType3);
		SkuList.addAll(DiamondSkus);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); 
		String ErpGoldRate24k = ErpGoldRateFor24K;
		erpUtils.ResetRemainingReturnWeight();
		for (String Sku : SkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> currentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			erpUtils.ErpSkuIngredientItemCalculation(currentSkuData, SkuCounter,TotalRgWeightReturn,SkuPurityReturn,SkuGoldRateReturn,ErpGoldRate24k);
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
		Map<String, String> AdjustedScannedSkuMap = erpUtils.MergeScannedSkuDataMaps(ScannedSKUDataMapSale, ScannedSkuDataMap);
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
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC15_ProformaInvoice");
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

		Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+""); 

		// Step 14: Select the Transaction Type as Sales Return
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_TransationType);

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

		mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLineReturn, ScannedSKUDataMapSale);

		// Step 17:  Click on the Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		// Step 18: post sales return bill
		// Expected Result: Check Return Invoice details
		pdfUtils.DownloadAndRenameLatestPDF("TC15SaleReturnInvoice");

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
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_TransationType2);

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
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_ApprovalCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC15_ApprovalCodeAgain);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 25:  post sales return bill
		// Expected Result: Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice   		     = pdfUtils.DownloadAndRenameLatestPDF("TC15SaleInvoice");
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
	}
	//<summary>
	// Test Case Title : Sales return of silver with any other sales items
	// Automation ID : TC16
	// Author : Christy Reji
	// </summary>

	public void TC16_NormalSalesReturnGoldSilverReturnSaleCounter() throws Exception{

		//PreRequisite : Take the bill number (receipt number) that has previously been sold
		//Step 1 :Click on Transaction button	
		String InvoiceNo =appUtils.GetInvoiceNoAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_CustomerNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_ItemList);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		// 2. Click on Transaction button
		// 3. Select customer
		// 4. Click on add to estimation button
		// 5. Click on customer adjustment button
		// 6. Click on return transaction button at the billing screen
		// 7. Enter reciept number
		// 8. Select return product(silver) and click on return button
		//Expected : Validation for product meant to be returned should be previously bought by the same customer.
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_CustomerNo,UtilityTestData.MenuBarOptions[5]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_ReturnProductSilver);
		//ExpectedValidation
		String ExpectedCustomerName = NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_CustomerName;
		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"ExpectedCustomerNameFrom MethodInvoicePdf: " + ActualCustomerName + ", but found: " + ExpectedCustomerName);

		//Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax)
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

		// No of Product lines, Displayed SubTotal, TAX, Total amount
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

		//Step 9: Scan multiple SKU
		//Step 10: Click on add to cart button
		//Assertion :Check calculation  Note:Billling Screen  *No of Product lines  *Displayed Item Name *Displayed Quantity
		// *Displayed TOTAL (without Tax) *Displayed Subtotal  *TAX  *Total Amount
		List<String> GoldSku     = appUtils.SelectMultipleSKUs( NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_SKUCount,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_TranferType,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_FromCounterGold,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_MetalTypeGold);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));


		List<String> DiamondSku     = appUtils.SelectMultipleSKUs( NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_SKUCount,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_TranferType,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_FromCounterDiamond,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_MetalTypeGold);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		List<String> UncutSku     = appUtils.SelectMultipleSKUs( NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_SKUCount,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_TranferType,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_FromCounterUncut,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_MetalTypeGold);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		List<String> PreciaSku     = appUtils.SelectMultipleSKUs( NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_SKUCount,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_TranferType,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_FromCounterPrecia,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC16_MetalTypeGold);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		List<String> SkuList=new ArrayList<>();
		SkuList.addAll(GoldSku);
		SkuList.addAll(DiamondSku);
		SkuList.addAll(UncutSku);
		SkuList.addAll(PreciaSku);	

		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		for (int i = 0; i < SkuList.size(); i++) {
			int SkuNumber = i + 1;
			String Sku = SkuList.get(i);
			// Step 1: Extract & store table values
			appUtils.SKUIngridentTableValues(Sku, SkuNumber, ScannedSkuDataMap);
			// Step 2: Filter for this specific SKU
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuNumber);
			// Step 3: Validate calculated values
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuNumber);
		}

		//TranscationPageVerifyLines
		List<String> TotalSku=new ArrayList<>();
		TotalSku.addAll(GoldSku);
		TotalSku.addAll(DiamondSku);
		TotalSku.addAll(UncutSku);
		TotalSku.addAll(PreciaSku);

		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(TotalSku, ScannedSkuDataMap);
		String SubTotal         = TransactionDataLine.get("Subtotal");
		String Discount         = TransactionDataLine.get("Discount");
		String TotalGrossWeight = TransactionDataLine.get("GrossWeight");
		String TotalNetWeight   = TransactionDataLine.get("NetWeight");
		String Tax              = TransactionDataLine.get("Tax");
		String TotalAmount      = TransactionDataLine.get("TotalAmount");
		String NetTotal         = TransactionDataLine.get("NetTotal");
		String LinesCount       = TransactionDataLine.get("LinesCount");
		List<String> ActualNames = mathUtils.ValidateTransactionLineCalculationsReturnProd(TransactionDataLine, ScannedSkuDataMap);

		List<String> CartedProductNames = new ArrayList<>();
		CartedProductNames.addAll(BillScrnProdNames);
		for (String ProdName : ActualNames) {
			CartedProductNames.add(ProdName);
		}

		String Prod1Name = CartedProductNames.get(0);
		String Prod2Name = CartedProductNames.get(1);
		String Prod3Name = CartedProductNames.get(2);
		String Prod4Name = CartedProductNames.get(3);

		// Step 11: Click on Save to estimation button
		//Step 12: Save Esimate
		// Expected Result:Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_BillingBack("Back"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		Thread.sleep(2000);
		String EstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// PDF Validation
		Thread.sleep(3000);
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC16_ProformaInvoice");
		Thread.sleep(3000);		
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FirstInvoice+"";
		String TaxableValue = SubTotal;
		String Gst          = Tax;
		String InvoiceTotal = TotalAmount;	
		String NetTotals    = NetTotal;	
		String TotalAmnt    = SubTotal;
		String PdfPath      = ProformaInvoicePath;
		pdfUtils.ProFormaInvoicePDFVerifyReturnSale(
				PdfPath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotals,
				TotalAmnt,
				Double.parseDouble(TotalGrossWeight),
				Double.parseDouble(TotalNetWeight)
				);

		// Step 13: Recall estimate from cashier screen
		//Expected: Verify whether the recalled item is the same as the item in the cart.
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//validation
		List<WebElement> ItemNamesFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		int ActualLinesCountInRecall         = ItemNamesFromRecall.size();

		for (int Prod =0; Prod<ItemNamesFromRecall.size();Prod++) {
			String ExpectedNameInRecall      = ItemNamesFromRecall.get(Prod).getText().trim();
			String ActualItemNameFromBilling = CartedProductNames.get(Prod);

			asrt.assertEquals(ExpectedNameInRecall,ActualItemNameFromBilling,"Item name mismatch: Expected value is "+ExpectedNameInRecall+"but got "+ActualItemNameFromBilling+" in Recall page");
		}

		// Step 14:Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), "Sales Return");

		// Step 15:Click on Process Estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 16:Choose a sales representative
		//base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		Map<String, String> TransactionRecallDataLineReturn = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotalReturn 			= TransactionRecallDataLineReturn.get("Subtotal");
		String DiscountReturn 			= TransactionRecallDataLineReturn.get("Discount");
		String TotalGrossWeightReturn   = TransactionRecallDataLineReturn.get("GrossWeight");
		String TotalNewNetWeightReturn  = TransactionRecallDataLineReturn.get("NetWeight");
		String TaxReturn 				= TransactionRecallDataLineReturn.get("Tax");
		String TotalAmountReturn	    = TransactionRecallDataLineReturn.get("TotalAmount").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
		String NetTotalReturn 			= TransactionRecallDataLineReturn.get("NetTotal").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
		String LinesCountReturn 		= TransactionRecallDataLineReturn.get("LinesCount");

		// Step 17:Click on the Amount
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_AmountDue("Change due"));
		Thread.sleep(2000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		//Step 19. Again recall estimate from cashier screen
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//Step 20.Select the Transaction Type as "sale "
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), "Sales");

		//Step 21.Click on Process Estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		//Step 22.Choose a sales representative
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


		//Step 23.Click on the Amount
		// Step 24.Select a Payment method(Cash or Card)
		String PaymentAmounts = appUtils.PaymentAfterRecallEstimate("HDFC");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		//Step 25: Post the Invoice
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
		String PaymentAmountHDFC         = PaymentAmounts;
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
	// Test Case Title : Sale a sales returned SKU
	// Automation ID : TC18
	// Author : Nivya
	// </summary>
	public void TC18_NormalSalesReturnGoldSilverReturnSaleCounterTest() throws Exception {

		//Prerequisite :Take the sales returned SKU(Sale, Sales Return, Counter In)	
		List<String> FetchInvoiceAndSku = appUtils.GetInvoiceNoAfterNormalSaleFetch(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC18_CustomerId, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC18_SkuItemList);		 
		String InvoiceNo            = FetchInvoiceAndSku.get(0);
		String ActualCustomerName   = pdfUtils.ExtractCustomerNameFromSaleInvoice();
		List<String> FetchedGoldSku = FetchInvoiceAndSku.subList(1, FetchInvoiceAndSku.size());			
		Thread.sleep(2000);
		appUtils.SalesReturnFlow(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC18_CustomerId, InvoiceNo,NormalSalesReturnGoldSilverReturnSaleCounterTestData.ReturnProducts);
		Thread.sleep(2000);
		appUtils.CounterFlow("Gold","Gold",FetchedGoldSku,UtilityTestData.Terminal);

		//Step 1 : Login to POS
		//Step 2 : Click on Transaction button
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		Thread.sleep(2000);

		// Step 3 : Select customer
		// Step 4 : Click on add to estimation button
		appUtils.SearchByCustomerID(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC18_CustomerId,  UtilityTestData.MenuBarOptions[5]);

		// Step 5 : Scan SKU (sale return)and click on Add to cart button
		// Expected:Check calculation,  Note:Billing Screen,No of Product lines, Displayed Item Name,
		// Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		// Step 6 : Select any one of the sales agent
		List<String> ItemNames           = new ArrayList<>();
		List<String> TotalForEachItem    = new ArrayList<>();
		List<String> Quantity            = new ArrayList<>();
		Map<String, String> ItemDetails  = new HashMap<>();
		for (int i = 0; i < FetchedGoldSku.size(); i++) {
			appUtils.SKUIngridentTableValues(FetchedGoldSku.get(i), i + 1, ItemDetails);
			ItemNames.add(ItemDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItem.add(ItemDetails.get("SKU_" + (i+1) + "_Total"));
			Quantity.add(ItemDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ItemDetails, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}
		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(FetchedGoldSku, ItemDetails);
		String SubTotal         = TransactionPageItems.get("Subtotal");
		String Discount         = TransactionPageItems.get("Discount");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight   = TransactionPageItems.get("NetWeight");
		String Gst              = TransactionPageItems.get("Tax");
		String InvoiceTotal     = TransactionPageItems.get("TotalAmount");
		String NetTotal         = TransactionPageItems.get("NetTotal");
		String LinesCount       = TransactionPageItems.get("LinesCount");

		mathUtils.ValidateTransactionLineCalculation(TransactionPageItems, ItemDetails);
		List<String> SkuNameElements = base.GetElementTexts(
				NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5")
				);
		for (int i = 0; i < SkuNameElements.size(); i++) {
			String ActualSkuName = SkuNameElements.get(i).trim();
			String ExpectedSkuName = ItemDetails.get("SKU_" + (i + 1) + "_SKUName");
			Assert.assertNotNull(ExpectedSkuName, "Expected SKU name is null for SKU_" + (i + 1));

			String[] SplitName = ExpectedSkuName.trim().split(" - ", 2);
			String ExpectedProductNameOnly = (SplitName.length > 1) ? SplitName[1].trim() : ExpectedSkuName.trim();

			System.out.println("Asserting SKU_" + (i + 1) + " Name: Expected = " + ExpectedProductNameOnly + ", Actual = " + ActualSkuName);
			Assert.assertEquals(ActualSkuName, ExpectedProductNameOnly, "After Calculation Mismatch in SKU name Actual:"+ActualSkuName+" and SKU name Expected:"+ExpectedProductNameOnly+"  for SKU_" + (i + 1));
		}

		// Step 7: Click on Save to estimation button
		// Step 8: Save Esimate
		// Expected Result:Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		String TaxableValue        = SubTotal;
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC04_ProFormaInvoice");
		String ProFormaInvoice     = System.getProperty("user.dir") + "\\Invoices\\"+ProFormaInvoiceName+"";
		pdfUtils.ProFormaInvoicePDFVerifyNormal(
				ProFormaInvoice,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				LinesCount,
				Double.parseDouble(TotalGrossWeight),
				Double.parseDouble(TotalNetWeight)
				);

		//Step 9 : Recall estimate from cashier screen
		//Step 10 : Select estimate and click on Recall estimation button
		//Expected : Verify whether the recalled item is the same as the item in the cart.
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//Validation
		List<WebElement> ItemNamesFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		int actualLinesCountInRecall = ItemNamesFromRecall.size();

		for (int i = 0; i < actualLinesCountInRecall; i++) {
			String ExpectedNameInRecall = ItemNamesFromRecall.get(i).getText().trim();
			String ActualNameFromBilling = SkuNameElements.get(i).trim(); 

			asrt.assertEquals(
					ActualNameFromBilling,
					ExpectedNameInRecall,
					"Item name mismatch at index " + i + ": Expected [" + ExpectedNameInRecall + "] but found [" + ActualNameFromBilling + "] in Recall page"
					);
		}

		// Step 11 : Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC18_TransactionType);

		// Step 12 : Click on Process Estimation
		// Step 13 : Select any Sales Representative
		// Expected Result: Note:-12.Cashier Screen after recall*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		base.setZoom(driver, 60);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		Map<String, String> TransactionRecallDataLine = appUtils.TransactionSKUsLinesVerify(FetchedGoldSku, ItemDetails);
		mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLine, ItemDetails);

		// Step 14 :  Click on the Amount
		// Step 15 :  Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC18_PaymentMethod));
		String PaymentAmountHDFC = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC18_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC18_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 16:  Post the Invoice
		// Expected Result: Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		String TaxInvoiceName    = pdfUtils.DownloadAndRenameLatestPDF("TC04_TaxInvoice");		
		String TaxInvoicePath    = System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";	
		String GrossAmount       = SubTotal;
		String TotalValue        = InvoiceTotal;
		String TotalQtyPcs       = LinesCount;
		String TotalDiscount     = Discount;
		String NetValue          = String.format("%.2f", Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", "")));
		String PdfInvoiceNo      = pdfUtils.SaleInvoicePdfValidation(
				TaxInvoicePath,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentAmountHDFC,
				FetchedGoldSku,
				ItemDetails
				);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	///<Summary>
	/// TestCase:Counter in the sales returned SKU and verify stock summary and stock details reports
	/// Automation ID: TC17
	/// Pre-Condition: NormalSale and SalesReturn
	/// Author: Gokul.P
	///</Summary>

	public void TC17_NormalSalesReturnGoldSilverReturnSaleCounter() throws Exception
	{
		//Pre-requisite
		WebDriverWait wait                               = new WebDriverWait(driver, Duration.ofSeconds(50));
		WebDriverWait HamBurgerWait                      = new WebDriverWait(driver, Duration.ofSeconds(5));
		Map<String,Object> InvoiceSkuPurityAndProduct    = appUtils.GetInvoiceNoSkuPurityAndProductAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC17_CustomerID,NormalSalesReturnGoldSilverReturnSaleCounterTestData.CounterTransfer);	
		String InvoiceNo                                 =(String) InvoiceSkuPurityAndProduct.get("Invoice");
		List<String> TC17_SkuList                        =(List<String>) InvoiceSkuPurityAndProduct.get("SKU");
		String TC17_Purity                               =(String) InvoiceSkuPurityAndProduct.get("Purity");
		String TC17_Product                              =(String) InvoiceSkuPurityAndProduct.get("ProductCode");

		appUtils.SalesReturnFlow(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC17_CustomerID, InvoiceNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC17_ReturnProducts);

		Thread.sleep(5000);
		
		appUtils.HamBurgerButtonClick("iconHome");

		base.ClickCondition(LoginPageObj.Edt_AlertText("POS Reports"));
		Thread.sleep(2000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Stock Summary"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Cb_MetalType("Gold"));
		Thread.sleep(2000);		
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions("saveBarCommandCommand"));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		String StockSummaryAfterSalesReturnPdfName = PdfUtilities.DownloadAndRenameLatestPDF("Stock Summary");
		String PdfPathAfterSalesReturn = System.getProperty("user.dir") + "\\Invoices\\" + StockSummaryAfterSalesReturnPdfName;
		String OnHandSkuTotalPiecesInStockSummary =pdfUtils.StockSummary(PdfPathAfterSalesReturn,"On-hand SKU","Gold",TC17_Product,TC17_Purity);      
		int OnHandSkuPiecesCount= Integer.parseInt(OnHandSkuTotalPiecesInStockSummary);
		String SaleReturnSkuTotalPiecesInStockSummary=pdfUtils.StockSummary(PdfPathAfterSalesReturn,"Sales Return SKU","Sales Return",TC17_Product,TC17_Purity);
		int SaleReturnSkuTotalPiece=Integer.parseInt(SaleReturnSkuTotalPiecesInStockSummary);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		Thread.sleep(2000);

		// 2. Click on Transaction button
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// 3. Click on Purchase button
		base.buttonClick(LoginPageObj.Edt_AlertText("Purchase"));

		//4. Click on counter transfer button
		base.buttonClick(LoginPageObj.Edt_AlertText("Counter Transfer"));

		for(int SkuCount =0; SkuCount < TC17_SkuList.size();SkuCount++)
		{
			//Click in Transfer Type,FromCounter,ToCounter,MetalType,Ship,InterReceiptSearch,Receive
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"));
			Thread.sleep(2000);

			//5. Select transfer type as Inter
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"), "Inter");

			//6. Select from counter as sales return
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"), "Sales Return");	

			//7. Select to counter and metal type
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("ToCounter"));		
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("ToCounter"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC17_ToCounter);
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC17_MetalType);

			//8. Search product and click on enter
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Product"), TC17_SkuList.get(SkuCount));
			base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("Product"), "ENTER");
			Thread.sleep(2000);

			//9. click on ship button
			base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
			base.buttonClick(LoginPageObj.Edt_Alert("Ship"));
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			Thread.sleep(2000);

			// 10. Again Select transfer type as Inter
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"), "Inter");

			//11.Click on search button
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconSearch"));
			Thread.sleep(2000);

			//12.Click on "Inter receipt Search button
			base.buttonClick(LoginPageObj.Edt_AlertText("Inter receipt search"));

			//13.  Select device id
			//Expected: Verify already shipped sku can only be received
			List<WebElement> AllElements = base.GetElement(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_RecipetNumber());	
			for (WebElement Receipt : AllElements)
			{  
				String ReceiptNumber = Receipt.getText();
				if (ReceiptNumber.contains(UtilityTestData.Terminal)) {
					Receipt.click();
					Thread.sleep(2000);
					String SkuText=base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Txt_Sku("wrapTextWithoutHyphen"));
					if(SkuText.contains(TC17_SkuList.get(SkuCount)))
					{
						String[] lines = SkuText.split("\\r?\\n");
						for (int i = 0; i < lines.length; i++)
						{
							if (lines[i].contains("SKU details") && i + 1 < lines.length) {
								String ExpectedSku = lines[i + 1].trim();
								String AcutalSKUInCounterTransferPage=TC17_SkuList.get(SkuCount);
								asrt.assertEquals(ExpectedSku, AcutalSKUInCounterTransferPage,"The already shipped SKU in the Counter Transfer page is not shipped is not correctly: Expected: "+ExpectedSku+" Actual "+ AcutalSKUInCounterTransferPage+"");
								break;
							}						
						}						
						base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));
						break;
					}
					else{base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_ReciptClose());}
				}
			}

			//14.Click on receive button
			base.buttonClick(LoginPageObj.Btn_SingnIn("Button1"));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Receive("Receive"));
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			Thread.sleep(2000);		
		}

		//15. Click on home button		
		appUtils.HamBurgerButtonClick("iconHome");
				
		//16. Click on POS report
		Thread.sleep(3000);
		base.ClickCondition(LoginPageObj.Edt_AlertText("POS Reports"));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));

		// 17 . Click on stock summary
		base.buttonClick(LoginPageObj.Btn_SignInButton("Stock Summary"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//18 Click on Metal Type
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Cb_MetalType("Gold"));
		Thread.sleep(2000);

		//19. Click on View Report
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions("saveBarCommandCommand"));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));

		//20.Verify the counter
		// Sales return, Counter in
		//Expected:Verify the counter
		// Sales return, Counter in	22		
		int TotalCounterIn= TC17_SkuList.size();
		int OnHandSKUPiecesAfterCounterIn       	= OnHandSkuPiecesCount+TotalCounterIn;
		int	SaleReturnSKUTotalPieceAfterCounterIn   = SaleReturnSkuTotalPiece-TotalCounterIn;
		String OnHandSkuPieces    			        = String.valueOf(OnHandSKUPiecesAfterCounterIn);
		String SaleReturnSkuPieces	                = String.valueOf(SaleReturnSKUTotalPieceAfterCounterIn);
		String PdfNameAfterCounterIn                = PdfUtilities.DownloadAndRenameLatestPDF("Stock Summary_After_CounterIn");
		String PdfPathAfterCounterIn                = System.getProperty("user.dir") + "\\Invoices\\" + PdfNameAfterCounterIn;
		String OnHandSKUTotalPieceAfterCounterIn    = pdfUtils.StockSummary(PdfPathAfterCounterIn,"On-hand SKU","Gold",TC17_Product,TC17_Purity);  
		asrt.assertEquals(OnHandSkuPieces, OnHandSKUTotalPieceAfterCounterIn,"The total pieces after counter in the stock summary is not equal as expected:  Expected: "+OnHandSkuPieces+" but Actual: "+OnHandSKUTotalPieceAfterCounterIn+"");
		String SaleReturnSKUTotalPiecesAfterCounterIn=pdfUtils.StockSummary(PdfPathAfterCounterIn,"Sales Return SKU","Sales Return",TC17_Product,TC17_Purity);
		asrt.assertEquals(SaleReturnSkuPieces, SaleReturnSKUTotalPiecesAfterCounterIn,"The Sales Return Pieces after Stock Summary is not equal as expected: Expected: "+SaleReturnSkuPieces+"but Actual:" +SaleReturnSKUTotalPiecesAfterCounterIn+"");	
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	
	// <summary>
	// Test Case Title : Sales Return Through Add to Estimate
	// Automation ID : TC19
	// Author : Robin T. Abraham
	// </summary>
	public void TC19_NormalSalesReturnGoldSilverReturnSaleCounter() throws Exception {

		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_CustomerNo,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_SkuList);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		String InvoiceNo            = (String) InvoiceDetails.get("InvoiceNo");
		String TotalNetWeightReturn = (String) InvoiceDetails.get("TotalNetWeight");
		String SkuPurityReturn      = (String) InvoiceDetails.get("FirstSkuPurity");
		String SkuGoldRateReturn    = (String) InvoiceDetails.get("FirstSkuGoldRate");
		String TotalRgWeightReturn  =  (String) InvoiceDetails.get("TotalRgWeight");

		// Extract SKU-wise map
		Map<String, String> SkuWiseData = (Map<String, String>) InvoiceDetails.get("SKUWiseDetails");

		//  Print only SKU_1_NetWeight and SKU_1_GrossWeight
		String Sku1NetWeight   = SkuWiseData.getOrDefault("SKU_1_NetWeight", "N/A");
		String Sku1GrossWeight = SkuWiseData.getOrDefault("SKU_1_GrossWeight", "N/A");
		String Sku1StoneWeight = SkuWiseData.getOrDefault("SKU_1_StoneWeight", "N/A");
		String Sku1Total	   = SkuWiseData.getOrDefault("SKU_1_Total", "N/A");
		Map<String, String> ScannedSKUDataMapSale = (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");

		String ErpGoldRateFor24K= erpUtils.SetMetalRateInERP(SkuGoldRateReturn, SkuPurityReturn);

		Thread.sleep(5000); //Wait needed for ERP Gold Rate to Change

		//Step 3 : Select the customer 
		//Step 4 : Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_CustomerNo,UtilityTestData.MenuBarOptions[5]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product and click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_ReturnProducts);
		String ExpectedCustomerName = NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_CustomerName;

		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName+ " in customer search page" );

		//Step 9  :  Scan multiple SKU (Gold)
		//Step 10 :  Click on add to cart button
		//Expected Result: Check calculationNote:Billling Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		// Gold SKUs
		List<String> GoldSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_SkuGoldCount,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_TransferType,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_FromCounter,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_MetalType);
		SkuList.addAll(GoldSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); // go to Transaction

		String ErpGoldRate24k = ErpGoldRateFor24K;
		erpUtils.ResetRemainingReturnWeight();

		for (String Sku : SkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> currentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			erpUtils.ErpSkuIngredientItemCalculation(currentSkuData, SkuCounter,TotalRgWeightReturn,SkuPurityReturn,SkuGoldRateReturn,ErpGoldRate24k);
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
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC19_ProformaInvoice");
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

		Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+" in estimation page"); 

		// Step 14: Select the Transaction Type as Sales Return
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_TransationType);

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

		erpUtils.ErpValidateTransactionLineCalculation(TransactionRecallDataLineReturn, ScannedSKUDataMapSale);

		// Step 17:  Click on the Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		// Step 18: post sales return bill
		// Expected Result: Check Return Invoice details
		pdfUtils.DownloadAndRenameLatestPDF("TC19SaleReturnInvoice");

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
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_TransationType2);

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
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_ApprovalCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC19_ApprovalCodeAgain);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 25:  post sales return bill
		// Expected Result: Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice   		     = pdfUtils.DownloadAndRenameLatestPDF("TC19SaleInvoice");
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
	// Test Case Title : Multiple sales return gold with multiple item
	// Automation ID : TC12
	// Author : Jhoncy Joseph
	// </summary>
	public void TC12_NormalSalesReturnGoldSilverReturnSaleCounter() throws Exception {

		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_CustomerNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_ItemList);
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
		appUtils.SearchByCustomerID(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_CustomerNo,UtilityTestData.MenuBarOptions[5]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product (gold) one by one and click on return button
		//Expected Result : The product meant to be returned should be previously bought by the same customer.
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_ReturnProducts);
		String ExpectedCustomerName = NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_CustomerName;

		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return. " +"Expected: " + ActualCustomerName + ", but found: " + ExpectedCustomerName+" in transaction page");

		//Step 9  :  Scan multiple SKU (Gold)
		//Step 10 :  Click on add to cart button
		//Expected Result: Check calculationNote:Billling Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		// Gold SKUs
		List<String> GoldSkuList = appUtils.SelectMultipleSKUs(
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_SkuGoldCount,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_TransferType,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_FromCounter,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_MetalType);
		SkuList.addAll(GoldSkuList);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction")); // go to Transaction

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
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC12_ProformaInvoice");
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
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_TransationType);

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
		pdfUtils.DownloadAndRenameLatestPDF("TC12SaleReturnInvoice");

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
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_TransationType2);

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
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_ApprovalCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC12_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 25:  post sales return bill
		// Expected Result: Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice   		     = pdfUtils.DownloadAndRenameLatestPDF("TC12SaleInvoice");
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
	// Test Case Title : Sales return with OG and a sale
	// Automation ID : TC21
	// Author: Chandana Babu
	// </summary>
	public void TC21_NormalSalesReturnGoldSilverReturnSaleCounter() throws Exception {
		//Step 1: Login to POS
		//Step 2: Click on Transaction button
		Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_CustomerNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_Products);
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
		String Sku2NetWeight   = SkuWiseData.getOrDefault("SKU_2_NetWeight", "N/A");
		String Sku2GrossWeight = SkuWiseData.getOrDefault("SKU_2_GrossWeight", "N/A");
		String Sku2StoneWeight = SkuWiseData.getOrDefault("SKU_2_StoneWeight", "N/A");
		String Sku2Total       = SkuWiseData.getOrDefault("SKU_2_Total", "N/A");
		Map<String, String> ScannedSkuDataMapSale = (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");

		String ErpGoldRateFor24K= erpUtils.SetMetalRateInERP(SkuGoldRateReturn, SkuPurityReturn);

		Thread.sleep(120000); //Wait needed for ERP Gold Rate to Change

		//Step 3: Select customer
		//Step 4: Click on add to estimation button
		appUtils.SearchByCustomerID(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_CustomerNo, UtilityTestData.MenuBarOptions[5]);

		//Step 5: Click on customer adjustment button
		//Step 6: Click on return transaction button at the billing screen
		//Step 7: Enter reciept number
		//Step 8: Select return product ( multiple gold )and click on return button
		//Expected: The product meant to be returned should be previously bought by the same customer.
		ReturnDetails Details = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_ProductsToReturn);
		asrt.assertEquals(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_CustomerName,ActualCustomerName,"Customer name for sale is not the same as the customer name from return. " +"ExpectedCustomerNameFrom MethodInvoicePdf: " + ActualCustomerName + ", but found: " + NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_CustomerName);

		List<String> ReturnProductNames        = Details.productNames;
		List<String> ReturnProductQtyList      = Details.productQty;
		List<String> ReturnProductTotalList    = Details.productPrice;

		//Step 9: Scan sku
		//Note: Check calculation and No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX and Total Amount
		//Step 10: Click on add to cart button
		List<String> SaleProductNames = new ArrayList<>();
		List<String> SaleProductQtyList = new ArrayList<>();
		List<String> SaleProductTotalList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;

		List<String> SkuList = appUtils.SelectMultipleSKUs(
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_SaleProductCount,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_TransferType,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_FromCounter,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_MetalType);
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
		String SubTotal		 		= TransactionDataLine.get("Subtotal");
		String Discount 			= TransactionDataLine.get("Discount");
		String TotalGrossWeight 	= TransactionDataLine.get("GrossWeight");
		String TotalNetWeight   	= TransactionDataLine.get("NetWeight");
		String Tax 					= TransactionDataLine.get("Tax");
		String TotalAmount 			= TransactionDataLine.get("TotalAmount");
		String ActualLinesCount		= TransactionDataLine.get("LinesCount");
		List<String> ProductNames = new ArrayList<>();
		List<String> ProductQtyList = new ArrayList<>();
		List<String> ProductTotalList = new ArrayList<>();
		ProductNames.addAll(ReturnProductNames);
		ProductNames.addAll(SaleProductNames);
		ProductQtyList.addAll(ReturnProductQtyList);
		ProductQtyList.addAll(SaleProductQtyList);
		ProductTotalList.addAll(ReturnProductTotalList); 
		ProductTotalList.addAll(SaleProductTotalList);

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		String ExpectedLinesCount = Integer.toString(AllProductRows.size());
		asrt.assertEquals(ExpectedLinesCount, ActualLinesCount,"Lines count mismatch: Expected is "+ExpectedLinesCount+" but found "+ActualLinesCount+" in billing page");		
		double SubTotalSum = 0.00;
		for(int i =0; i < AllProductRows.size() ;i++) {
			List<WebElement> ItemName = base.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
			String ActualItemNameInBillingPge = ItemName.get(i).getText();
			if (i==2) {
				String ExpectedItemNameInBillingPge = ProductNames.get(i).substring(ProductNames.get(i).indexOf("-") + 1).trim();
				asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");			
			}
			else {
				String ExpectedItemNameInBillingPge = ProductNames.get(i).trim();
				asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");
			}
			List<WebElement> Quantity = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
			String ActualQuantityInBillingPge = Quantity.get(i).getText().replace("-", "");
			String ExpectedQuantityInBillingPge = ProductQtyList.get(i);
			double ActualQtyInBillingPge = Double.parseDouble(ActualQuantityInBillingPge);
			double ExpectedQtyInBillingPge = Double.parseDouble(ExpectedQuantityInBillingPge);
			asrt.assertEquals(ExpectedQtyInBillingPge, ActualQtyInBillingPge,1.000,"Quantity mismatch: Expected is "+ExpectedQtyInBillingPge+" but got "+ActualQtyInBillingPge+" in billing page");

			List<WebElement> TotalWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));
			String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replace("\u20B9", "").replace(",", "").trim();
			SubTotalSum = SubTotalSum+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
			String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(ProductTotalList.get(i).replace("\u20B9", "").replace(",", "").trim()));
			asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge.replace("-", ""),"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
		}
		String ExpectedSubTotalInBillingPge = String.format("%.2f",SubTotalSum);
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replace("\u20B9", "").replace(",", "").trim();
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

		String ActualTaxInBillingPge = Tax.replace("\u20B9", "").replace(",", "").trim();
		double ActualTax = Double.parseDouble(ActualTaxInBillingPge);
		double ExpectedTax = SubTotalSum*0.03;
		String ExpectedTaxInBillingPge = String.format("%.2f",ExpectedTax);
		asrt.assertEquals(ExpectedTax, ActualTax,1.0,"Tax amount mismatch: "+ExpectedTax+" but found "+ActualTax+" in billing page");

		double ExpectedTotalAmountInBillingPge = Double.parseDouble(ActualSubTotalInBillingPge)+Double.parseDouble(ActualTaxInBillingPge);
		double ActualTotalAmountInBillingPge = Double.parseDouble(TotalAmount.replace("\u20B9", "").replace(",", "").trim());
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,1.0,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");

		//Step 11: Search OG in search box
		base.buttonClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_OldProduct);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Products"));
		Thread.sleep(2000);

		//Step 12:Click on OG own and Choose Exchange
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));	

		//Step 13: Select configuration
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_Configuration);

		//Step 14: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		//Step 15: Select QC person
		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_QcAndSmithPerson);

		//Step 16: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_QcAndSmithPerson);

		//Step 17: Enter piece value
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_Pieces);

		//Step 18: Enter gross weight
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_GrossWeight);

		//Step 19 click on calculate button
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(4000);
		double ActualCurrentGoldRate22kInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));
		double CurrentERPGoldRate24k = Double.parseDouble(ErpGoldRateFor24K);
		double ExpectedCurrentGoldRate22kFromERP = (CurrentERPGoldRate24k*22)/24;
		asrt.assertEquals(ExpectedCurrentGoldRate22kFromERP, ActualCurrentGoldRate22kInItemDetailsPge,1.0,"Gold Rate mismatch: Expected is "+ExpectedCurrentGoldRate22kFromERP+" but found "+ActualCurrentGoldRate22kInItemDetailsPge+"in item details page");

		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetwt"));		
		double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
		double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualCurrentGoldRate22kInItemDetailsPge * 100.0) / 100.0;
		String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetamt"));
		double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);
		asrt.assertEquals(ExpectedGoldAmountInItemDetailsPge, ActualGoldAmountInItemDetailsPge,1.0,"GoldAmount mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in item details page");

		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt"));
		double ActualTotalAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
		double ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPge;
		asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

		//Step 20: Click on Add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		if(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Cart has changed"))) {
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}

		//Step 21: save estimate
		//Expected: Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_abc("buttonsContainer positionRelative","Back" ));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String ActualEstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		Thread.sleep(3000);
		double SubTotalInBillingPge = Double.parseDouble(ActualSubTotalInBillingPge)-ActualTotalAmountInItemDetailsPge;
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC21_ProformaInvoice");
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
		// Expected Result: Verify whether the recalled item is the same as the item in the cart.(Estimation No Check)
		//Step 23: Select estimate and click on Recall estimation button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		String ExpectedEstmnNumber= base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+""); 

		//Step 24:Select the Transaction Type as sale return/exchange/Sales(one by one)
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_TransactionTypeReturn);

		//Step 25:Click on Process Estimation 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		//Step 26:Choose a sales representative
		//Expected: Cashier Screen after recall Estimate No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX and Total Amount
		//base.setZoom(driver, 60);
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

		//Step 27: Post sale return invoice
		String AmountDueForSaleReturnFromBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		double AmountDueForSaleReturnInFromBillingPge = Double.parseDouble(AmountDueForSaleReturnFromBillingPge);
		String ExpectedAmountForSaleReturnInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		double TaxForSaleReturnProducts = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4")).replace("\u20B9", "").replace(",", "").replace("-", ""));
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
			String ActualAdvanceReceived = AdvanceReceiptVoucherDetails.get("AdvanceReceived");
			String ActualNetReceived = AdvanceReceiptVoucherDetails.get("NetAdvance");
			String ActualPaymentMode = AdvanceReceiptVoucherDetails.get("PaymentMode");
			String PaymentAmount = AdvanceReceiptVoucherDetails.get("PaymentAmount");
			String ApproxWeight = AdvanceReceiptVoucherDetails.get("ApproxWeight");
			String ActualFixedGoldRate = AdvanceReceiptVoucherDetails.get("FixedGoldRate");
			String ActualDepositType = AdvanceReceiptVoucherDetails.get("DepositType");
			asrt.assertEquals(ExpectedAmountForSaleReturnInBillingPge, ActualAdvanceReceived,"Advance value mismatch: Expected is "+ExpectedAmountForSaleReturnInBillingPge+" but found "+ActualAdvanceReceived+" in Advance receipt");
			asrt.assertEquals(ExpectedAmountForSaleReturnInBillingPge, ActualNetReceived,"Net value mismatch: Expected is "+ExpectedAmountForSaleReturnInBillingPge+" but found "+ActualNetReceived+" in Advance receipt");
			asrt.assertEquals(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_PaymentMode, ActualPaymentMode,"Payment mode mismatch: Expected is "+NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_PaymentMode+" but found "+ActualPaymentMode+" in Advance receipt");
			asrt.assertEquals(ExpectedAmountForSaleReturnInBillingPge, PaymentAmount,"Payment Amount mismatch: Expected is "+ExpectedAmountForSaleReturnInBillingPge+" but found "+PaymentAmount+" in Advance receipt");
			base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		}
		else {
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
			Map<String, String> CreditNoteDetails = pdfUtils.SalesReturnPDFCredit();
			String ActualGrossWt1 = CreditNoteDetails.get("Product1.GrossWt");
			String ActualStoneWt1 = CreditNoteDetails.get("Product1.StoneWt");
			String ActualNetWt1 = CreditNoteDetails.get("Product1.NetWt");
			String ActualAmount1 = CreditNoteDetails.get("Product1.Amount");
			String ActualGrossWt2 = CreditNoteDetails.get("Product2.GrossWt");
			String ActualStoneWt2 = CreditNoteDetails.get("Product2.StoneWt");
			String ActualNetWt2 = CreditNoteDetails.get("Product2.NetWt");
			String ActualAmount2 = CreditNoteDetails.get("Product2.Amount");
			String ActualTaxableValue   = CreditNoteDetails.get("TaxableValue");
			String ActualTotalNetValue  = CreditNoteDetails.get("TotalNetValue");
			String ActualPaymentDetails = CreditNoteDetails.get("PaymentDetails");
			double ExpectedTaxableValue = Double.parseDouble(Sku1Total)+Double.parseDouble(Sku2Total);
			double TotalAmountInBillingPge = ExpectedTaxableValue+TaxForSaleReturnProducts;

			asrt.assertEquals(Sku1GrossWeight, ActualGrossWt1,"Gross Weight mismatch: Expected is "+Sku1GrossWeight+" but found "+ActualGrossWt1+" in credit note");
			asrt.assertEquals(Sku1StoneWeight, ActualStoneWt1,"StoneWt mismatch: Expected is"+Sku1StoneWeight+" but found "+ActualStoneWt1+" in credit note");
			asrt.assertEquals(Sku1NetWeight, ActualNetWt1,"NetWt mismatch: Expected is"+Sku1NetWeight+" but found "+ActualNetWt1+" in credit note");
			asrt.assertEquals(Double.parseDouble(Sku1Total), Double.parseDouble(ActualAmount1),1.0,"Amount mismatch: Expected is"+Sku1Total+" but found "+ActualAmount1+" in credit note");

			asrt.assertEquals(Sku2GrossWeight, ActualGrossWt2,"Gross Weight mismatch: Expected is "+Sku2GrossWeight+" but found "+ActualGrossWt2+" in credit note");
			asrt.assertEquals(Sku2StoneWeight, ActualStoneWt2,"StoneWt mismatch: Expected is"+Sku2StoneWeight+" but found "+ActualStoneWt2+" in credit note");
			asrt.assertEquals(Sku2NetWeight, ActualNetWt2,"NetWt mismatch: Expected is"+Sku2NetWeight+" but found "+ActualNetWt2+" in credit note");
			asrt.assertEquals(Double.parseDouble(Sku2Total), Double.parseDouble(ActualAmount2),1.0,"Amount mismatch: Expected is"+Sku2Total+" but found "+ActualAmount2+" in credit note");

			asrt.assertEquals(ExpectedTaxableValue, Double.parseDouble(ActualTaxableValue),1.0,"Taxable Value mismatch: Expected is"+ExpectedTaxableValue+" but found "+ActualTaxableValue+" in credit note");
			asrt.assertEquals(TotalAmountInBillingPge, Double.parseDouble(ActualTotalNetValue),1.0,"Total Net Value mismatch: Expected is"+TotalAmountInBillingPge+" but found "+ActualTotalNetValue+" in credit note");
			asrt.assertEquals(TotalAmountInBillingPge, Double.parseDouble(ActualPaymentDetails),1.0,"Payment Detailsmismatch: Expected is"+TotalAmountInBillingPge+" but found "+ActualPaymentDetails+" in credit note");
		}	

		//Step 28: Again recall estimate and select transaction type as exchange	
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_TransactionTypeExchange);

		//Step 29: Click on process estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		//Step 30:Post exchange invoice
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		String AmountDueForExchangeFromBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		double AmountDueForExchangeInFromBillingPge = Double.parseDouble(AmountDueForExchangeFromBillingPge);			
		String ExpectedAmountForExchangeInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
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
			String ActualNetReceived = AdvanceReceiptVoucherDetails.get("NetAdvance");
			String ActualPaymentMode = AdvanceReceiptVoucherDetails.get("PaymentMode");
			String PaymentAmount = AdvanceReceiptVoucherDetails.get("PaymentAmount");
			String ApproxWeight = AdvanceReceiptVoucherDetails.get("ApproxWeight");
			String ActualFixedGoldRate = AdvanceReceiptVoucherDetails.get("FixedGoldRate");
			String ActualDepositType = AdvanceReceiptVoucherDetails.get("DepositType");
			asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, ActualAdvanceReceived,"Advance value mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+ActualAdvanceReceived+" in Advance receipt");
			asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, ActualNetReceived,"Net value mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+ActualNetReceived+" in Advance receipt");
			asrt.assertEquals(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_PaymentMode, ActualPaymentMode,"Payment Mode mismatch: Expected is "+NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_PaymentMode+" but found "+ActualPaymentMode+" in Advance receipt");
			asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, PaymentAmount,"Payment Amount mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+PaymentAmount+" in Advance receipt");
			base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		}
		else {
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
			double GrossWeight = Double.parseDouble(ExpectedGrossWeightInItemDetailsPge);
			String ExpectedGrossWeightFromBillingPge = String.format("%.3f", GrossWeight);  
			String ExpectedOGPurchaseRateFromItemDetailsPge = String.format("%.2f",ActualCurrentGoldRate22kInItemDetailsPge);
			double TotalAmountForOG = Double.parseDouble(TotalAmountInItemDetailsPge);
			String ExpectedTotalAmountForOG = String.format("%.2f", TotalAmountForOG);  			
			Map<String, String> OgPurchaseBill = pdfUtils.OGPdfValidation("Adjustment");
			String GrossWeightFromInvoice = OgPurchaseBill.get("GrossWt1");
			String NetWeightFromInvoice = OgPurchaseBill.get("NetWt1");
			String GoldRateFromInvoice = OgPurchaseBill.get("Rate1");
			String TotalAmountFromInvoice = OgPurchaseBill.get("TotalAmount1");
			String PaymentDetailsFromInvoice = OgPurchaseBill.get("PaymentDetails");
			asrt.assertEquals(ExpectedGrossWeightFromBillingPge, GrossWeightFromInvoice,"Gross Weight mismatch: "+ExpectedGrossWeightFromBillingPge+" but got "+GrossWeightFromInvoice+" in purchase bill");		
			asrt.assertEquals(ExpectedNetWeightInItemDetailsPge, NetWeightFromInvoice,"Net Weight mismatch: "+ExpectedNetWeightInItemDetailsPge+" but got "+NetWeightFromInvoice+" in purchase bill");	
			asrt.assertEquals(ExpectedOGPurchaseRateFromItemDetailsPge, GoldRateFromInvoice,"Gold rate mismatch: "+ExpectedOGPurchaseRateFromItemDetailsPge+" but got "+ GoldRateFromInvoice+" in purchase bill");	
			asrt.assertEquals(ExpectedTotalAmountForOG, TotalAmountFromInvoice,"Total amount mismatch: "+ExpectedTotalAmountInBillingPge+" but got "+TotalAmountFromInvoice+" in purchase bill");	
			asrt.assertEquals(ExpectedTotalAmountForOG, PaymentDetailsFromInvoice,"Payment details mismatch: "+ExpectedTotalAmountInBillingPge+" but got "+PaymentDetailsFromInvoice+" in purchase bill");
		}

		//Step 31: Again recall estimate and select transaction type as sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC21_TransactionTypeSales);

		//Step 32: Click on process estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		//Step 33:Click on the Amount
		//Step 34:Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		String GrossAmountForSale= base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4"));
		String TaxableValueForSale = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4"));
		String TotalValueForSale=base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4"));
		String TotalNetValueForSale=base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4"));
		String TotalQtyPcsForSale = String.valueOf(SkuList.size());
		String TotalDiscountForSale = Discount;
		String TotalNetWeightForSale = TotalNetWeight;
		String TotalGrossWeightForSale= SaleProductQtyList.get(0);
		String AdjustmentForSale = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("PaymentsField", "h4"));;

		String AmountDueForSaleFromBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		double AmountDueForSaleInFromBillingPge = Double.parseDouble(AmountDueForSaleFromBillingPge);
		String PaymentAmountForSale ="";
		if (AmountDueForSaleInFromBillingPge==0.00) {
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
			PaymentAmountForSale = "";
		}
		else {
			PaymentAmountForSale = appUtils.PaymentAfterRecallEstimate("HDFC");
		}

		//Step 35:Post the Invoice
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC21_TaxInvoice");		
		String TaxInvoice =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";
		String TotalDiamond = "";
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
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

	// Test Case Title : Multiple Sr and Multiple OG with Sale
	// Automation ID : TC22
	// Author : Hasna EK
	// </summary>

	public void TC22_NormalSalesReturnGoldSilverReturnSaleCounter() throws Exception{

		//Pre-Requisite:1. Take the bill number (receipt number) that has previously been sold.
		// Step 1: Login to POS
		// Step 2: Click on Transaction button
		Map<String, Object> InvoiceDetails = erpUtils.GetInvoiceDetailsAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_CustomerId,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_SkuList);
		String ActualCustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();

		String InvoiceNo            = (String) InvoiceDetails.get("InvoiceNo");
		String TotalNetWeightReturn = (String) InvoiceDetails.get("TotalNetWeight");
		String SkuPurityReturn      = (String) InvoiceDetails.get("FirstSkuPurity");
		String SkuGoldRateReturn    = (String) InvoiceDetails.get("FirstSkuGoldRate");
		String TotalRgWeightReturn  =  (String) InvoiceDetails.get("TotalRgWeight");
		List<String> AllSkuListReturn  = (List<String>) InvoiceDetails.get("AllSkuList");
		Map<String, String> ScannedSkuDataMapReturn= (Map<String, String>) InvoiceDetails.get("ScannedSKUDataMap");
		Map<String, String> SkuWiseData = (Map<String, String>) InvoiceDetails.get("SKUWiseDetails");
		
		//Pre-Requisite: 2. Change metal rate.
		String ErpGoldRateFor24K= erpUtils.SetMetalRateInERP(SkuGoldRateReturn, SkuPurityReturn);
		Thread.sleep(10000); //Wait needed for ERP Gold Rate to Change

		//Step 3 : Select the customer 
		//Step 4 : Click on add to estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_CustomerId,UtilityTestData.MenuBarOptions[5]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 5 : Click on customer adjustment button
		//Step 6 : Click on return transaction button at the billing screen
		//Step 7 : Enter the Receipt Number
		//Step 8 : Select return product ( multiple gold )and click on return button
		//Expected Result : 8.The product meant to be returned should be previously bought by the same customer.
		ReturnDetails ReturnProdDetails = appUtils.ReturnMultipleProductsWithDetails(InvoiceNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_ReturnProducts);
		String ExpectedCustomerName = NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_CustomerName;

		asrt.assertEquals(ActualCustomerName,ExpectedCustomerName ," Customer name for sale is not the same as the customer name from return.Expected: " + ActualCustomerName + ", but got: " + ExpectedCustomerName+ " in customer search page" );

		//Step 9  :  Scan SKU
		//Step 10 :  Click on add to cart button
		// Note	: Check average rate calculation
		//Expected Result: Check calculationNote:Billing Screen*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();

		// Gold SKUs
		List<String> SelectedSkus = appUtils.SelectMultipleSKUs(
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_SKUCount ,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_TranferType,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_FromCounter,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_MetalType);
		SkuList.addAll(SelectedSkus);

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
		int SkuCounter = 1;
		erpUtils.ResetRemainingReturnWeight();
		Thread.sleep(2000);
		for (String Sku : SkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			erpUtils.ErpSkuIngredientItemCalculation(CurrentSkuData, SkuCounter,TotalRgWeightReturn,SkuPurityReturn,SkuGoldRateReturn,ErpGoldRate24k);
			SkuCounter++;
		}

		//Step 11: Select OG
		//Step 12:Click on OG own
		//Step 13: Select configuration
		//Step 14: Click on Add item button
		//Step 15:Select QC person
		//Step 16:Select Smith person
		//Step 17:Enter piece value
		//Step 18:Enter gross weight
		//Step 19:Click on calculate button
		//Step 20:Click on Add to cart button	
		// Expected Result: 19. Check Old Gold Calculation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.PurchaseOldGold(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_Configuration, 
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_PurchaseOrExchange,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_QCPerson,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_SmithPerson,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_GrossPieces,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_GrossWeight);
		double CalculatedBoardRate22k = (Double.parseDouble(ErpGoldRate24k))*22/24;
		double BoardRate22k = Math.round(CalculatedBoardRate22k);	

		Map<String, Double> FirstOgData = mathUtils.ValidateOldGoldItemDetailsData(BoardRate22k,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_Configuration);
		Double FirstOgQty = FirstOgData.get("OgQty");
		Double FirstOgCvalue = FirstOgData.get("OgCvalue");
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		

		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		if (base.isElementPresent(driver, NormalSaleGoldAndSilverObj.Ele_CustomerAdjustment("cart has changed"))) {		
			try {
				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton")));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}

		//Note: continue step 11- 20
		appUtils.PurchaseOldGold(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_Configuration, 
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_PurchaseOrExchange,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_QCPerson,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_SmithPerson,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_GrossPieces,
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_GrossWeight);
		Map<String, Double> SecondOgData = mathUtils.ValidateOldGoldItemDetailsData(BoardRate22k,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_Configuration);
		Double SecondOgQty = SecondOgData.get("OgQty");
		Double SecondOgCvalue = SecondOgData.get("OgCvalue");
		List<Double> OgQtyData = Arrays.asList(FirstOgQty,SecondOgQty);
		List<Double> OgCvalueData = Arrays.asList(FirstOgCvalue,SecondOgCvalue);
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));			
		try {
			if (Wait.until(ExpectedConditions.presenceOfElementLocated(NormalSaleGoldAndSilverObj.Ele_CustomerAdjustment("cart has changed"))) != null) {

				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton")));
				OkButton.click();
			} 
		} catch (TimeoutException e) {
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

		int LinesCountBilling = Integer.parseInt(LinesCount);
		double SubTotalBilling = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double TaxBilling = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));
		double TotalAmountBilling = Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));
		//			ReturnDetails ReturnProdDetails = appUtils.ReturnMultipleProductsWithDetails(ReceiptNumber, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC20_ProductsToReturn);
		List<String> BillScreenProdName  = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> BillScreenProdQty = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String> BillScreenProdPrice = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));

		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

		Map<String, Double> DataMap  = mathUtils.ValidateBillingScreenCalculationForSalesReturnSaleAndOg(TransactionDataLine,ScannedSkuDataMap,ReturnProdDetails,OgQtyData,OgCvalueData,	
				NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_OgItemName,NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_SKUCount);
		double CalculatedTaxableValue = DataMap.get("TaxableValue");
		String TaxableValue = String.valueOf(CalculatedTaxableValue);	

		// Step 21: Save Estimate
		// Expected Result:Verify the invoice details

		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_abc("buttonsContainer positionRelative","Back" ));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstimationNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		// PDF Verification Step
		Thread.sleep(4000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC22_ProformaInvoice");
		Thread.sleep(5000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst                 = Tax;
		String InvoiceTotal        = TotalAmount;
		String TotalAmnt           = SubTotal;
		double TotalNetWeight      = Double.parseDouble(TotalNewNetWeight) - Double.parseDouble(TotalNetWeightReturn);
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				TotalAmnt);

		Thread.sleep(2000);

		//Step 22:Recall estimate from cashier
		//Step 23: Select estimate and click on Recall estimation button
		//Step 24:Select the Transaction Type as sale return/exchange/Sales(one by one)
		//Step 25:Click on Process Estimation 
		//Expected Result: 22. Verify whether the recalled item is the same as the item in the cart.
		appUtils.RecallEstimateProcess(EstimationNumber, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_TransactionType1);

		// Step 26: Select any Sales Representative
		//		try {
		//			base.setZoom(driver, 60);
		//			Thread.sleep(1000);
		//			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		//			Thread.sleep(1000);
		//			appUtils.HandleSpecifyRateModalInRecall();
		//		}catch(Exception e) {
		//			System.out.println("Sales man popup Not appeared for salereturn");
		//		}

		Map<String, String> TransactionRecallDataLineReturn = appUtils.TransactionSKUsLinesVerify(AllSkuListReturn, ScannedSkuDataMapReturn);
		String TotalAmountReturn   = TransactionRecallDataLineReturn.get("TotalAmount").replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim();
		double TotalAmntReturn     = Double.parseDouble(TotalAmountReturn.replaceAll("[^\\d.]", "").trim());
		String LinesCountReturn    = TransactionRecallDataLineReturn.get("LinesCount");

		// Step 27: Post sale return invoice
		// Expected Result: Check Return Invoice details
		String AmountDueReturn = base.GetText(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		AmountDueReturn = AmountDueReturn.replaceAll("[^0-9.-]", "").trim();
		double NetAmountReturn = Double.parseDouble(AmountDueReturn);
		double ExpectedAdvanceReceived = 0.00;
		WebDriverWait Alertwait = new WebDriverWait(driver, Duration.ofSeconds(7));

		if (NetAmountReturn < 0.0){
			appUtils.PaymentModeForDiffTransactions(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_PaymentMode, "", "",
					UtilityTestData.SalePersonNumber,
					UtilityTestData.SalePersonName,
					UtilityTestData.DueYear,
					UtilityTestData.NomineeName,
					UtilityTestData.NomineeRelation);
			base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));

			base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
			base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			AmountDueReturn = AmountDueReturn.replaceAll("[-]", "").trim();
			ExpectedAdvanceReceived = Double.parseDouble(AmountDueReturn);

			pdfUtils.VerifyFieldsInNormalAdvancePdf(ExpectedAdvanceReceived, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_DepositType);
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		}
		else {
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
			base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
			try {
				if (Wait.until(ExpectedConditions.presenceOfElementLocated(NormalSaleGoldAndSilverObj.Ele_CustomerAdjustment("cart has changed"))) != null) {

					WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton")));
					OkButton.click();
				} 
			} catch (TimeoutException e) {
			}
			ExpectedAdvanceReceived = TotalAmntReturn;
			pdfUtils.VerifyFieldsInCreditNotePdf(SkuWiseData, InvoiceDetails, LinesCountReturn, ExpectedAdvanceReceived);
		}

		//Step 28: Again recall estimate and select transaction type as exchange
		//Step 29: Click on process estimation button
		Thread.sleep(1000);
		appUtils.RecallEstimateProcess(EstimationNumber, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_TransactionType2);
		base.setZoom(driver, 60);
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		// Click on the Amount 0.00 
		// Step 30:  Post exchange invoice
		// Expected Result: Check Exchange invoice details
		String AmountDueOg = base.GetText(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));

		AmountDueOg = AmountDueOg.replaceAll("[^\\d.-]", "").trim();
		double NumericAmountOg = Double.parseDouble(AmountDueOg);	
		double OgTotalAmountInBillingPge =Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4")).replaceAll("[^\\d.]", "").trim());
		List<String> OgQuantity = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
		String ExpectedAmountForExchangeInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replaceAll("[^\\d.]", "").trim();


		double TotalGrossWtOg = 0.00;
		for(int i =0; i < OgQuantity.size() ;i++) {
			String OgQuantityInBillingPge = OgQuantity.get(i).replaceAll("[^\\d.]", "").trim();
			double ActualQtyInBillingPge = Double.parseDouble(OgQuantityInBillingPge);
			TotalGrossWtOg += ActualQtyInBillingPge;
		}
		String PaymentDetails = "";
		String PaymentOgFromInvoice = "";
		if (NumericAmountOg<0.00) {
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
			String ActualNetReceived = AdvanceReceiptVoucherDetails.get("NetAdvance");
			String ActualPaymentMode = AdvanceReceiptVoucherDetails.get("PaymentMode");
			String PaymentAmount = AdvanceReceiptVoucherDetails.get("PaymentAmount");
			String ApproxWeight = AdvanceReceiptVoucherDetails.get("ApproxWeight");
			String ActualFixedGoldRate = AdvanceReceiptVoucherDetails.get("FixedGoldRate");
			String ActualDepositType = AdvanceReceiptVoucherDetails.get("DepositType");
			asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, ActualAdvanceReceived,"Advance value mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+ActualAdvanceReceived+" in Advance receipt");
			asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, ActualNetReceived,"Net value mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+ActualNetReceived+" in Advance receipt");
			asrt.assertEquals(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_PaymentMode, ActualPaymentMode,"Payment Mode mismatch: Expected is "+NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_PaymentMode+" but found "+ActualPaymentMode+" in Advance receipt");
			asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, PaymentAmount,"Payment Amount mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+PaymentAmount+" in Advance receipt");
			base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		}
		else {
			Thread.sleep(2000);
			base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			PaymentDetails = NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_PaymentDetails;
			Map<String, String>PurchaseBillData = pdfUtils.PurchaseBillPdfValidation
					(PaymentDetails,
							OgTotalAmountInBillingPge, 
							TotalGrossWtOg,
							OgTotalAmountInBillingPge, 
							TotalGrossWtOg);
			PaymentOgFromInvoice =PurchaseBillData.get("PaymentDetails");

		}
		//Step 31: Again recall estimate and select transaction type as sales
		//Step 32: Click on process estimation button
		// Expected Result: 31.Cashier Screen after recall*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		appUtils.RecallEstimateProcess(EstimationNumber, NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_TransactionType3);
		base.setZoom(driver, 60);
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		appUtils.HandleSpecifyRateModalInRecall();

		Map<String, String> TransactionRecallDataLineSale = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotalSale			= TransactionRecallDataLineSale.get("Subtotal");
		String DiscountSale			= TransactionRecallDataLineSale.get("Discount");
		String TotalGrossWeightSale = TransactionRecallDataLineSale.get("GrossWeight");
		String TotalNetWeightSale   = TransactionRecallDataLineSale.get("NetWeight");
		String TotalAmountSale	    = TransactionRecallDataLineSale.get("TotalAmount").replaceAll("[^\\d.]", "").trim();
		String NetTotalSale 	    = TransactionRecallDataLineSale.get("NetTotal").replaceAll("[^\\d.]", "").trim();
		String LinesCountSale 		= TransactionRecallDataLineSale.get("LinesCount");
		String PaymentsSale 		= TransactionRecallDataLineSale.get("Payments");

		erpUtils.ErpValidateTransactionLineCalculation(TransactionRecallDataLineSale, ScannedSkuDataMap);

		String AmountDueSale = base.GetText(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		AmountDueSale = AmountDueSale.replaceAll("[^\\d.-]", "").trim();

		//Step 33: Click on amount
		//Step 34: Select payment method(cash/card)
		double FinalAmountDue = Double.parseDouble(AmountDueSale);
		String AdjustmentSale;
		String PaymentHdfcCard;
		if (FinalAmountDue > 0.00){

			double AdjustmentOg = Double.parseDouble(PaymentOgFromInvoice);					
			double AdjustmentValueSale = AdjustmentOg +TotalAmntReturn;
			AdjustmentSale = String.valueOf(AdjustmentValueSale);			
			PaymentHdfcCard = AmountDueSale;

			appUtils.PaymentModeForDiffTransactions(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_PaymentMethod,
					NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_ApprCode,
					NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_CardNo,
					UtilityTestData.SalePersonNumber,
					UtilityTestData.SalePersonName,
					UtilityTestData.DueYear,
					UtilityTestData.NomineeName,
					UtilityTestData.NomineeRelation); 
			base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			Thread.sleep(1000);	
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		}
		else {
			AdjustmentSale = TotalAmountSale;
			Thread.sleep(2000);
			try {
				base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
				base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				Thread.sleep(1000);	
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
			}catch(Exception e) {}

			PaymentHdfcCard = null;
		}

		//Step 35: Post exchange invoice(sale)
		//Expected Result: Check final invoice details,* save receipt id for future reference
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC22_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";

		String GrossAmount     =SubTotalSale;	
		String TotalDiscount   = Discount;
		String TotalQtyPcs     = LinesCountSale;
		String Adjustment      = AdjustmentSale;
		String TotalDiamond    = NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC22_TotalDiamond;
		String PaymentHDFCCard = PaymentHdfcCard;
		String SalesPdfPath    = SaleInvoicePath;
		String NetValue        = TotalAmountSale;
		String TaxableValueSale = SubTotalSale;

		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWeightSale,
				TotalGrossWeightSale,
				TaxableValueSale,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				SkuList,
				ScannedSkuDataMap);
		System.out.println("FINAL PDF INVOICE NO:-"+PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

	}
}
