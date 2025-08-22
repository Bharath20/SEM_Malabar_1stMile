package testPage;

import utilPack.ErpUtilities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.random.RandomDataGenerator;
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

import objectRepository.CustomerCreation_Obj;
import objectRepository.GPPSettlementAverageRateSingleSkuMultipleSku_Obj;
import objectRepository.GppOpeningAndCollectionMultiplePlans_Obj;
import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import objectRepository.OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj;
import testData.CommonData;
import testData.CustomerCreation_TestData;
import testData.GppOpeningAndCollectionMultiplePlans_TestData;
import testData.POC_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.BasePge;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;
import utilPack.AppUtilities.SKUResult;


public class GppOpeningAndCollectionMultiplePlans extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	MathUtilities mathUtils;
	PdfUtilities pdfUtils;
	ErpUtilities erpUtils;


	Login login = new Login(driver); 
	POC_TestData poctestdata = new POC_TestData();
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	CustomerCreation_Obj CustomerCreationObj=new CustomerCreation_Obj();
	CustomerCreation_TestData CustomerCreationTestData = new CustomerCreation_TestData();
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	GppOpeningAndCollectionMultiplePlans_TestData GppOpeningAndCollectionMultiplePlansTestData= new GppOpeningAndCollectionMultiplePlans_TestData();
	GppOpeningAndCollectionMultiplePlans_Obj GppOpeningAndCollectionMultiplePlansObj = new GppOpeningAndCollectionMultiplePlans_Obj();
	GPPSettlementAverageRateSingleSkuMultipleSku_Obj  GPPSettlementAverageRateSingleSkuMultipleSkuObj = new GPPSettlementAverageRateSingleSkuMultipleSku_Obj();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj =  new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();
	OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj = new OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj();

	public GppOpeningAndCollectionMultiplePlans(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		mathUtils = new MathUtilities(base);
		pdfUtils = new PdfUtilities(base);
		erpUtils = new ErpUtilities(base);
	}


	//<summary>
	// Test Case Title : Collection against an existing GB scheme
	// Automation ID : TC106
	// Author : Aiswarya
	// </summary>
	public void TC106_GppOpeningAndCollectionMultiplePlans() throws Exception {
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2: Click on Transaction button
		appUtils.HamBurgerButtonClick("iconShop");

		//Precondition
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
		Thread.sleep(2000);
		WebDriverWait Wait =new WebDriverWait(driver, Duration.ofSeconds(50));
		Wait.until(ExpectedConditions.presenceOfElementLocated(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account")));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account"));
		//Search customer and click on add to customer order
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlans_TestData.TC106_CustomerID,UtilityTestData.MenuBarOptions[6]);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"), "GB::Golden Bloom Purchase Advance Plan");
		Thread.sleep(3000);
		double GBInstallmentAmount = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput")));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SalesPersonInput"),GppOpeningAndCollectionMultiplePlans_TestData.TC106_SalesPerson);
		base.buttonClick(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("BankProof"));
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("BankProof"),GppOpeningAndCollectionMultiplePlans_TestData.TC106_BankProof);
		base.buttonClick(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"));
		base.setData(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"),GppOpeningAndCollectionMultiplePlans_TestData.TC106_NomineeName);
		base.buttonClick(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"));
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"),GppOpeningAndCollectionMultiplePlans_TestData.TC106_NomineeRelation);
		base.buttonClick(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("IsSameAsAbove"));
		Thread.sleep(1000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("View more"));
		Thread.sleep(1000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("SearchView_AppBarView_appBarCommandCommand"));
		//getting ApplicationID from pdf
		String ApplicationIDGBPdf=pdfUtils.ExtractApplicationIdFromPdf();
		//Taking first collection
		appUtils.HamBurgerButtonClick("iconShop");
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlans_TestData.TC106_CustomerID,UtilityTestData.MenuBarOptions[0]);
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("GPP Collection Entry"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),GppOpeningAndCollectionMultiplePlans_TestData.TC106_DepositType);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), ApplicationIDGBPdf);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"),GppOpeningAndCollectionMultiplePlans_TestData.TC106_SalesPerson.replaceAll("[^A-Za-z]", "").trim());		 
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(2000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),UtilityTestData.ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),UtilityTestData.CardNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 3: Select customer
		//Step 4: Click on add to sale button
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlans_TestData.TC106_CustomerID,UtilityTestData.MenuBarOptions[0]);

		//Step 5: Click on gpp--> gpp collection entry
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("GPP Collection Entry"));

		//Step 6: Choose deposit type
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),GppOpeningAndCollectionMultiplePlans_TestData.TC106_DepositType);

		//Step 7: Enter transaction number,deposit amount and sales person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), ApplicationIDGBPdf);
		Thread.sleep(2000);
		double GBDepositAmount = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount")));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson("4"),GppOpeningAndCollectionMultiplePlans_TestData.TC106_SalesPerson.replaceAll("[^A-Za-z]", "").trim());		 

		// Step 8: Click on deposit
		// Expected: Verify Total amount displayed on billing screen
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(2000);
		double TotalAmountBilling = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Btn_Amnt("h1")).replaceAll("[^\\d.]", ""));
		Thread.sleep(2000);

		// Assertion
		Assert.assertEquals(TotalAmountBilling,GBDepositAmount,"Mismatch in deposit amount. Expected GB Deposit Amount: " + GBDepositAmount + ", but got Billing Amount: " + TotalAmountBilling + " in the Billing Screen");
		//Step 9: Click on the Amount
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));

		//Step 10: Select a Payment method(Cash or Card)
		String PaymentModeBilling = base.GetText(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),UtilityTestData.ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),UtilityTestData.CardNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 11: Post the Invoice
		//11.Verify invoice Amt(Rs.), Cum. Amt(Rs.), Payment Mode
		Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();

		double GBDepositAmountPdf = Double.parseDouble(PaymentDetails.get("Amt"));
		double CumulativeAmountPdf = Double.parseDouble(PaymentDetails.get("CumAmt"));
		String PaymentModePdf = PaymentDetails.get("PaymentMode");

		Assert.assertEquals(GBDepositAmountPdf,GBDepositAmount,"Mismatch in GB deposit amount: expected " + GBDepositAmount + ", but found " + GBDepositAmountPdf + "in the Gpp Reciept PDF");
		Assert.assertEquals(PaymentModePdf,PaymentModeBilling,"Mismatch in payment mode: expected " + PaymentModeBilling + ", but found " + PaymentModePdf+ "in the Gpp Reciept PDF");
		Assert.assertTrue(CumulativeAmountPdf > GBInstallmentAmount, "Cumulative amount is not greater than the created GB deposit amount. Expected greater than " + GBInstallmentAmount + ", but found " + CumulativeAmountPdf+ "in the Gpp Reciept PDF");
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title :Scheme Validations
	//  1.Collection against adjusted scheme
	//  2.collection against matured scheme
	//  3.multiple collection against 10 installments completed scheme/installment completed unmatured scheme
	//  4.collect amount more than scheme amount.
	// Automation ID : TC107_GppOpeningAndCollection
	// Author: Christy Reji
	// </summary>
	public void TC107_GppOpeningAndCollectionMultiplePlans() throws Exception { 	

		//SCENARIO_1 : Collection against adjusted scheme
		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 2: Click on Transaction button
		Thread.sleep(5000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		// Step 3:Select customer 
		//Step 4:click on add to sale button
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario1Customer,UtilityTestData.MenuBarOptions[0]);

		// Step 5:Click on GPP->GPP collection entry
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CustomerAdjustment("GPP Collection Entry"));

		//Step 6.Choose deposit Type(GPP)
		Thread.sleep(4000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC107_Deposit), GppOpeningAndCollectionMultiplePlansTestData.TC107_DepositType);

		//Step 7.Select transaction number
		// Expected scenario 1.Should not display adjusted scheme
		base.VerifyOptionNotPresent(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC107_Transaction), GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario1TransNo);
		Assert.assertTrue(true, "Verified: The transaction number"+GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario1TransNo+" is not present in the dropdown.");

		//Void transaction
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Close"));
		base.buttonClick(GppOpeningAndCollectionMultiplePlansObj.Btn_VoidTransaction("ButtonGrid4Control", "positionRelative", "Void Transaction"));
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Yes"));
		System.out.println("==SCENARIO_1 : Collection against adjusted scheme completed==");

		//==============SCENARIO_2 : Collection against matured scheme=============
		//Adjusted no, matured yes customer from erp

		// Step 3:Select customer 
		//Step 4:click on add to sale button
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario2Customer,UtilityTestData.MenuBarOptions[0]);

		// Step 5:Click on GPP->GPP collection entry
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CustomerAdjustment("GPP Collection Entry"));

		//Step 6.Choose deposit Type(GPP)
		Thread.sleep(4000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC107_Deposit), GppOpeningAndCollectionMultiplePlansTestData.TC107_DepositType);

		//Step 7.Select transaction number
		//Expected scenario 2: Should not display adjusted scheme
		base.VerifyOptionNotPresent(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC107_Transaction), GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario2TransNo);
		Assert.assertTrue(true, "Verified: The transaction number"+GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario2TransNo+" is not present in the dropdown.");

		//Void transaction
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Close"));
		base.buttonClick(GppOpeningAndCollectionMultiplePlansObj.Btn_VoidTransaction("ButtonGrid4Control", "positionRelative", "Void Transaction"));
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Yes"));
		System.out.println("==SCENARIO_2 : Collection against matured scheme completed==");

		//======================SCENARIO_3:Multiple collection against 10 installments completed scheme/installment completed unmatured scheme========
		// Step 3:Select customer 
		//Step 4:click on add to sale button
		Thread.sleep(3000);
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario3Customer,UtilityTestData.MenuBarOptions[0]);

		// Step 5:Click on GPP->GPP collection entry
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_CustomerAdjustment("GPP Collection Entry"));

		//Step 6.Choose deposit Type(GPP)
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC107_Deposit), GppOpeningAndCollectionMultiplePlansTestData.TC107_DepositType);

		//Step 7.Select transaction number
		Thread.sleep(2000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC107_Transaction), GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario3TransNo);

		//Step 8.Enter deposit amount(multiple)
		base.ClickClearEnter(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario3DepositAmt);

		//Step 9.Select sales person And Click on deposit
		//Expected Scenario: 3. Verify validation after 12 installment
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC107_SalesPersonNo), UtilityTestData.SalePersonName);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Validation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Warning!"));
		Thread.sleep(2000);
		String ValidationMsgScenario3 = base.GetText(CustomerCreationObj.Ele_CustomerIDLabel("text: content")).trim();
		Assert.assertTrue(ValidationMsgScenario3.equalsIgnoreCase(GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario3Validation.trim()), "Validation message mismatch. Expected: " + GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario3Validation +", but found: " + ValidationMsgScenario3);

		//Void transaction
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Close"));
		base.buttonClick(GppOpeningAndCollectionMultiplePlansObj.Btn_VoidTransaction("ButtonGrid4Control", "positionRelative", "Void Transaction"));
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Yes"));
		System.out.println("==SCENARIO_3 : Multiple collection against 10 installments completed scheme/installment completed unmatured scheme completed==");

		//Scenario 4
		//======================SCENARIO_4 :Collect amount more than scheme amount.=============

		// Step 3:Select customer 
		//Step 4:click on add to sale button
		Thread.sleep(3000);
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario4Customer,UtilityTestData.MenuBarOptions[0]);

		//Fetching value from GPP maturity for validation
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_CustomerAdjustment("GPP Maturity"));
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario4Customer,UtilityTestData.GPPMaturityButton);
		Thread.sleep(4000);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("Select gpp account"), GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario4TransNo);
		Thread.sleep(2000);
		String AmountPaid= base.GetAttribte(GppOpeningAndCollectionMultiplePlansObj.Ele_AmountPaid("TotalAmountPaid"), "value");
		System.out.println("AmountPaid : " + AmountPaid);
		Thread.sleep(5000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		//Step 5:Click on GPP->GPP Collection entry	
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_CustomerAdjustment("GPP Collection Entry"));

		//Step 6.Choose deposit Type(GPP)
		Thread.sleep(4000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC107_Deposit), GppOpeningAndCollectionMultiplePlansTestData.TC107_DepositType);

		//Step 7.Select transaction number
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC107_Transaction), GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario4TransNo);

		//Step 8.Enter deposit amount(multiple)
		base.ClickClearEnter(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),GppOpeningAndCollectionMultiplePlansTestData.TC107_Scenario4DepositAmt);

		//Step 9.Select sales person And Click on deposit
		//Expected Scenario: 4Verify validation when collect more than scheme amount
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC107_SalesPersonNo), UtilityTestData.SalePersonName);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(2000);

		//Validation
		base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Warning!"));
		Thread.sleep(2000);
		String ValidationMsgScenario4 = base.GetText(CustomerCreationObj.Ele_CustomerIDLabel("text: content"));
		System.out.println("ValidationMsgScenario3: "+ValidationMsgScenario4);
		String ActualAmountPaid       = ValidationMsgScenario4.replaceAll("[^0-9]", "");
		System.out.println("EMI Amount Displayed : " + ActualAmountPaid);

		//Calculation
		double AmountPaidValue        = Double.parseDouble(AmountPaid);
		double ExpectedAmountPaid     = 11000 - AmountPaidValue;
		System.out.println("EMI Pending after Calculation: " + ExpectedAmountPaid);

		Assert.assertEquals(Double.parseDouble(ActualAmountPaid), ExpectedAmountPaid, "SCENARIO_4: The pending EMI amount after calculation : "+ExpectedAmountPaid+"mismatch with actual displayed EMI amount : " +Double.parseDouble(ActualAmountPaid)+ "in the validation page");
		System.out.println("==SCENARIO_4 : Collect amount more than scheme amount completed==");
	}

	// <summary>
	// Test Case Title : Open GG scheme and take collection through converted to advance(SR/OG)
	// Automation ID : TC102
	// Author: Sangeetha M S
	// </summary>
	public void TC102_GppOpeningAndCollectionMultiplePlans() throws Exception 
	{
		//Step   1.Login to POS
		Thread.sleep(5000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);

		//Precondition -To take the current Board rate 
		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");

		Map<String, String> SkuDetails = new LinkedHashMap<>();	
		List<String> PurityOfItems=new ArrayList();
		List<Double> AllRates = new ArrayList<>();
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(GppOpeningAndCollectionMultiplePlans_TestData.TC102_SaleProductCount,
				GppOpeningAndCollectionMultiplePlans_TestData.TC102_TransferTypeSKU,
				GppOpeningAndCollectionMultiplePlans_TestData.TC102_FromCounterSKU,
				GppOpeningAndCollectionMultiplePlans_TestData.TC102_MetalTypeSKU
				);
		appUtils.HamBurgerButtonClick("iconShop");

		for (int i = 0;i < SkuListGold.size();i++)
		{
			String CurrentGoldSku = SkuListGold.get(i);
			appUtils.SKUIngridentTableValues(CurrentGoldSku.toString(), (i+1), SkuDetails);
			PurityOfItems.add(SkuDetails.get("SKU_" + (i+1) + "_Purity"));
			for (int ItemIndex = 1; ; ItemIndex++)  //Change
			{
				String Rate = "SKU" + (i+1)+ "_ITEM" + ItemIndex + "_RATE";
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
			SkuDetails.put("SKU_" + (i+1) + "_AllRates", AllRates.toString());
		}

		//For conversion of Rate
		String ItemPurity = PurityOfItems.get(0);
		double ExchangeRate =  AllRates.get(0);
		double ExpectedGoldRate22k=0;
		long InstallmentAmount=0;
		if (ItemPurity.equalsIgnoreCase("18k"))
		{
			double ConvertedGoldRate = (ExchangeRate * 22) / 18;
			ExpectedGoldRate22k = Math.round(ConvertedGoldRate);
		}
		else 
		{
			ExpectedGoldRate22k = Math.round(ExchangeRate);
		}
		appUtils.VoidTransaction();

		//Calculated Installment amount based on Rate
		InstallmentAmount = Math.round(ExpectedGoldRate22k / 500.0) * 500;
		String InstallmentAmountValue = String.valueOf(InstallmentAmount);

		//Step 2.Click on Transaction button and Go to GPP
		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));

		//Step 3.Select New GPP Account
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account"));

		//Step 4.Search customer phone number in search tab
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC102_PhoneNo, GppOpeningAndCollectionMultiplePlansTestData.TC102_Option);

		//Step 5.Click on "Add to GPP ac open"
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconBuy"));

		//Step 6.Select scheme code as "Golden glow purchase plan"
		//Expected : Verify that selecting Scheme Code "GG" sets the installment amount to the default minimum of 1000 and is also editable
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"), GppOpeningAndCollectionMultiplePlansTestData.TC102_Scheme);
		Thread.sleep(2000);
		String DefaultInstallmentAmountValue=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		String DefaultInstallmentAmount=GppOpeningAndCollectionMultiplePlans_TestData.TC102_DefaultInstallmentAmount;
		asrt.assertEquals(DefaultInstallmentAmountValue,DefaultInstallmentAmount,"Mismatch in default installment amount when selecting Scheme Code GG,Got"+DefaultInstallmentAmountValue+" and Expected "+DefaultInstallmentAmount+" in GPP account opening page"); 
		Thread.sleep(3000);

		//Step 7.Enter Installment amount
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"),InstallmentAmountValue); 
		double FirstInstallmentAmount= Double.parseDouble(InstallmentAmountValue);

		//Step 8.Select Sales person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SalesPersonInput"), GppOpeningAndCollectionMultiplePlansTestData.TC102_SalesPerson);

		//Step 9.Select Id proof type 1       test data pass
		base.selectorByVisibleText(GppOpeningAndCollectionMultiplePlansObj.Sel_IdType1("Id proof type 1"),GppOpeningAndCollectionMultiplePlansTestData.TC102_IdProofType1);

		//Step 10.Enter Id proof no 1
		base.setData(GppOpeningAndCollectionMultiplePlansObj.Ele_IdProofNo1("Idproofno1", "Id proof no 1"), GppOpeningAndCollectionMultiplePlansTestData.TC102_IdProofNo1);

		//Step 11.Select Bank proof type as "Passbook"
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("BankProof"),GppOpeningAndCollectionMultiplePlansTestData.TC102_BankProofType);

		//Step 12.Enter Nominee name and Select Nominee relation
		base.buttonClick(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"));
		base.setData(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"),GppOpeningAndCollectionMultiplePlansTestData.TC102_NomineeName);
		base.buttonClick(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"));
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"),GppOpeningAndCollectionMultiplePlansTestData.TC102_NomineeRelation);

		//Step 13.Click on check box(Same as above)
		base.buttonClick(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("IsSameAsAbove"));
		Thread.sleep(1000);

		//Step 14.Click on save
		//Expected :Verify Application ID, Scheme Details, Customer Details, Nominee Details, Bank Details		
		String CustomerName=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("CustNmInput"));
		String CustomerPhone=GppOpeningAndCollectionMultiplePlansTestData.TC102_PhoneNo;
		String SchemeDetails=base.getTheSelectedTextInDropDown(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"));
		String NomineeName=GppOpeningAndCollectionMultiplePlansTestData.TC102_NomineeName;
		String BankAccountNo=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkacno"));
		String BankIFSC=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkcode"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("View more"));
		Thread.sleep(1000);	
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_GpsacopnView_AppBarView_appBarCommandCommand"));

		Map<String, String> pdfDetails = pdfUtils.ExtractDetailsFromGPPPdf(GppOpeningAndCollectionMultiplePlansTestData.TC102_SchemeCode);
		String ApplicationIDPdf   = pdfDetails.get("ApplicationID");
		String SchemePdf          = pdfDetails.get("Plan");
		String CustomerNamePdf    = pdfDetails.get("CustomerName");
		String PhonePdf           = pdfDetails.get("Phone");
		String NomineeNamePdf     = pdfDetails.get("NomineeName");
		String BankAccountNoPdf   = pdfDetails.get("BankAccountNo");
		String IFSCCodePdf        = pdfDetails.get("IFSCCode");
		asrt.assertNotNull(ApplicationIDPdf,"Application ID " + ApplicationIDPdf + " not found in Golden Glow Purchase Plan PDF content.");
		asrt.assertEquals(SchemePdf, SchemeDetails,"Scheme name mismatch: "+SchemeDetails+" but got "+SchemePdf+" in Golden Glow Purchase Plan Pdf");		
		asrt.assertEquals(CustomerNamePdf, CustomerName,"Customer name Mismatch: "+CustomerName+" but got "+CustomerNamePdf+" in  Golden Glow Purchase Plan Pdf");		
		asrt.assertEquals(PhonePdf, CustomerPhone,"Customer phone number mismatch: "+CustomerPhone+" but got "+PhonePdf+" in  Golden Glow Purchase Plan Pdf");		
		asrt.assertEquals(NomineeNamePdf, NomineeName,"Nominee name mismatch: "+NomineeName+" but got "+NomineeNamePdf+" in  Golden Glow Purchase Plan Pdf");		
		asrt.assertEquals(BankAccountNoPdf, BankAccountNo,"Bank Account number mismatch: "+BankAccountNo+" but got "+BankAccountNoPdf+" in  Golden Glow Purchase Plan Pdf");		
		asrt.assertEquals(IFSCCodePdf, BankIFSC,"Bank IFSC code mismatch: "+BankIFSC+" but got "+IFSCCodePdf+" in  Golden Glow Purchase Plan Pdf");		

		//Step 15.Search the customer		
		//Step 16.Click on add to sale button
		appUtils.HamBurgerButtonClick("iconShop");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC102_CustomerNo,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 17.Select OG
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),"OG");
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Products"));
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));	

		//Step 18.Click on OG own
		//Step 19.Select configuration
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),GppOpeningAndCollectionMultiplePlansTestData.TC102_Purity);

		//Step 20.Click on Add item button
		//Step 21.Select Exchange
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		//Step 22.Select QC person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),GppOpeningAndCollectionMultiplePlansTestData.TC102_QCPerson);

		//Step 23.Select Smith person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),GppOpeningAndCollectionMultiplePlansTestData.TC102_SmithPerson);

		//Step 24.Enter piece value
		//Step 25.Enter gross weight
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"), GppOpeningAndCollectionMultiplePlansTestData.TC102_Pieces);
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"), GppOpeningAndCollectionMultiplePlansTestData.TC102_GrossWeight);
		String OGTotalWeight=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));

		//Step 26.Click on calculate button
		//Expected : Check Exchange rate(Board rate)
		//check calculation Note:Cashier Screen after recall
		//*No of Product lines,*Displayed Item Name,*Displayed Quantity,*Displayed TOTAL (without Tax),*Displayed Subtotal,*TAX,*Total Amount
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(4000);
		String OGItemName = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = OGItemName.substring(OGItemName.indexOf("-") + 2);
		String OGTotalWeightInItemDetailPage=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt"));
		double OGTotalAmount = Double.parseDouble(TotalAmountInItemDetailsPge);

		//Exchange rate(Board rate)
		double ActualGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));
		asrt.assertEquals(ExpectedGoldRate22k, Math.round(ActualGoldRateInItemDetailsPge),1,"Exchange rate (Board rate)22k mismatch: Expected exchange rate is " +ExpectedGoldRate22k + " but got " + Math.round(ActualGoldRateInItemDetailsPge) +" in OG item details page");

		//Amount
		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetwt"));		
		double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
		double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
		String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetamt"));
		double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);
		asrt.assertEquals(ExpectedGoldAmountInItemDetailsPge, ActualGoldAmountInItemDetailsPge,1.0,"GoldAmount mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in OG item details page");		

		//Step	 27.Click on Add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("bottomLeft grow pad12", "1"));

		//*No of Product lines,*Displayed Item Name,*Displayed Quantity,*Displayed TOTAL (without Tax),*Displayed Subtotal,*TAX,*Total Amount
		List<WebElement> ListOfItemNamesInBillingPge = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));		
		int ExpectedLinesCountInBillingPge = ListOfItemNamesInBillingPge.size();

		int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("LinesField", "h4")));
		asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");

		String ActualItemNameInBillingPge = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		asrt.assertEquals(ExpectedItemNameInItemDetailsPge, ActualItemNameInBillingPge,"Item name mismatch: "+ExpectedItemNameInItemDetailsPge+" but found "+ActualItemNameInBillingPge+" in Billing page");

		String ActualGrossWeightInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5")).replace("-", "");
		double ActualGrossWeight = Double.parseDouble(ActualGrossWeightInBillingPge);//String.valueOf(Double.parseDouble(ActualGrossWeightInBillingPge));
		double ExpectedQty = Double.parseDouble(OGTotalWeightInItemDetailPage);
		asrt.assertEquals(ExpectedQty, ActualGrossWeight,"Quantity mismatch: "+ExpectedQty+" but found "+ActualGrossWeight+" in Billing page");

		String ActualTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		double ActualTotalWithoutTax = Double.parseDouble(ActualTotalWithoutTaxInBillingPge);		
		asrt.assertEquals(OGTotalAmount, ActualTotalWithoutTax,"Total without tax mismatch: "+OGTotalAmount+" but found "+ActualTotalWithoutTax+" in Billing page");

		String ExpectedSubTotalInBillingPge = String.valueOf(ActualTotalWithoutTax);
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualSubTotal = String.valueOf(Double.parseDouble(ActualSubTotalInBillingPge));	
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotal,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotal+" in Billing page");

		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = GppOpeningAndCollectionMultiplePlansTestData.TC102_OGTax;
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"OG Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in Billing page");

		String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;	
		String ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualTotalAmount = String.valueOf(Double.parseDouble(ActualTotalAmountInBillingPge));	
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmount,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmount+" in Billing page");

		//Step	 28.Click on the Amount
		String ReceiveAmtInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 29.Select convert to advance
		//Step 30.1.Select type as GPP
		//Step 30.2.Select scheme id(GG)    
		//Step 30.3.Select sales agent
		//Step 30.4.Click on deposit button
		String PaymentModeBilling = base.GetText(LoginPageObj.Edt_AlertText(GppOpeningAndCollectionMultiplePlansTestData.TC102_PaymentMode));
		base.buttonClick(LoginPageObj.Edt_AlertText(GppOpeningAndCollectionMultiplePlansTestData.TC102_PaymentMode));	
		Thread.sleep(4000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("Ext col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("Ext col grow"),GppOpeningAndCollectionMultiplePlansTestData.TC102_SchemeType);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"),ApplicationIDPdf);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC102_SalePersonNumber), GppOpeningAndCollectionMultiplePlansTestData.TC102_SalePersonName);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Step 31.Post the Invoice
		//Verify  *Application ID,*Being advance against ,*Customer Details,*Board rate,Purity, Amount,*Payment Mode		
		//Step 32.Click on balance amount
		//Step 33.Click on payment method as cash
		//Step 34.Post invoice
		String BalanceAmtInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		String TrimBalance = BalanceAmtInBillingPge.replace("-", "").trim();
		double BalanceAmount = Double.parseDouble(TrimBalance);
		String ItemPurityValue=GppOpeningAndCollectionMultiplePlansTestData.TC102_Purity;
		if(BalanceAmount>0)
		{
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.buttonClick(LoginPageObj.Edt_AlertText(GppOpeningAndCollectionMultiplePlansTestData.TC102_PaymentMode2));	
			Thread.sleep(4000);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("cancelButton primaryButton"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "GPP ADV. INVOICE"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("btnblue"));

			Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
			double CumulativeAmountGppPdf = Double.parseDouble(PaymentDetails.get("CumAmt"));			
			String ApplicationIDGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();
			String AdvanceAgainstGppPdf = PaymentDetails.getOrDefault("AdvanceAgainst", "").trim();
			String CustomerNameGppPdf = PaymentDetails.getOrDefault("CustomerName", "").trim();
			String PhoneGppPdf = PaymentDetails.getOrDefault("Phone", "").trim();
			String BoardRateGppPdf = PaymentDetails.getOrDefault("BoardRate", "").trim();
			String PurityGppPdf = PaymentDetails.getOrDefault("Purity", "").trim();
			String PaymentModeGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
			String CustomerPhoneGppPdf=PaymentDetails.getOrDefault("Phone", "").trim();
			String SchemeName = SchemeDetails.contains("::") ? SchemeDetails.split("::", 2)[1].trim() : SchemeDetails;

			asrt.assertEquals(CustomerPhoneGppPdf,CustomerPhone,"Mismatch in Customer Mobile Number: expected " + CustomerPhone + ", but found " + CustomerPhoneGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(ApplicationIDPdf,ApplicationIDGppPdf,"Mismatch in Application Id: expected " + ApplicationIDPdf + ", but found " + ApplicationIDGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(PaymentModeGppPdf,PaymentModeBilling,"Mismatch in payment mode: expected " + PaymentModeBilling + ", but found " + PaymentModeGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals(CumulativeAmountGppPdf,FirstInstallmentAmount, "Mismatch in Cummilative Amount: expected " + FirstInstallmentAmount + ", but found " + CumulativeAmountGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals(AdvanceAgainstGppPdf,SchemeName,"Being Advance against value Mismatch: expected " + SchemeName + ", but found " + AdvanceAgainstGppPdf + "in the Gpp Reciept PDF");		
			asrt.assertEquals(CustomerNameGppPdf,CustomerName,"Mismatch in customer name: expected " + CustomerName + ", but found " + CustomerNameGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals( Double.parseDouble(BoardRateGppPdf), ExpectedGoldRate22k,"Mismatch in Board rate: expected " + ExpectedGoldRate22k + ", but found " + BoardRateGppPdf+ "in the Gpp Reciept PDF"); 
			asrt.assertEquals(PurityGppPdf,ItemPurityValue,"Mismatch in Purity : expected " + ItemPurityValue + ", but found " + PurityGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(PaymentModeGppPdf,PaymentModeBilling,"Mismatch in payment mode: expected " + PaymentModeBilling + ", but found " + PaymentModeGppPdf+ "in the Gpp Reciept PDF");	
		}
		else if(BalanceAmount == 0.00 )
		{
			Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
			double CumulativeAmountGppPdf = Double.parseDouble(PaymentDetails.get("CumAmt"));			
			String ApplicationIDGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();
			String AdvanceAgainstGppPdf = PaymentDetails.getOrDefault("AdvanceAgainst", "").trim();
			String CustomerNameGppPdf = PaymentDetails.getOrDefault("CustomerName", "").trim();
			String PhoneGppPdf = PaymentDetails.getOrDefault("Phone", "").trim();
			String BoardRateGppPdf = PaymentDetails.getOrDefault("BoardRate", "").trim();
			String PurityGppPdf = PaymentDetails.getOrDefault("Purity", "").trim();
			String PaymentModeGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
			String CustomerPhoneGppPdf=PaymentDetails.getOrDefault("Phone", "").trim();
			String SchemeName = SchemeDetails.contains("::") ? SchemeDetails.split("::", 2)[1].trim() : SchemeDetails;

			asrt.assertEquals(CustomerPhoneGppPdf,CustomerPhone,"Mismatch in Customer Mobile Number: expected " + CustomerPhone + ", but found " + CustomerPhoneGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(ApplicationIDPdf,ApplicationIDGppPdf,"Mismatch in Application Id: expected " + ApplicationIDPdf + ", but found " + ApplicationIDGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(PaymentModeGppPdf,PaymentModeBilling,"Mismatch in payment mode: expected " + PaymentModeBilling + ", but found " + PaymentModeGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals(CumulativeAmountGppPdf,FirstInstallmentAmount, "Mismatch in Cummilative Amount: expected " + FirstInstallmentAmount + ", but found " + CumulativeAmountGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals(AdvanceAgainstGppPdf,SchemeName,"Being Advance against value Mismatch: expected " + SchemeName + ", but found " + AdvanceAgainstGppPdf + "in the Gpp Reciept PDF");		
			asrt.assertEquals(CustomerNameGppPdf,CustomerName,"Mismatch in customer name: expected " + CustomerName + ", but found " + CustomerNameGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals( Double.parseDouble(BoardRateGppPdf), ExpectedGoldRate22k,"Mismatch in Board rate: expected " + ExpectedGoldRate22k + ", but found " + BoardRateGppPdf+ "in the Gpp Reciept PDF"); 
			asrt.assertEquals(PurityGppPdf,ItemPurityValue,"Mismatch in Purity : expected " + ItemPurityValue + ", but found " + PurityGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(PaymentModeGppPdf,PaymentModeBilling,"Mismatch in payment mode: expected " + PaymentModeBilling + ", but found " + PaymentModeGppPdf+ "in the Gpp Reciept PDF");	
		}
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
	}

	// <summary>
	// Test Case Title : Open GB scheme and take collection through converted to advance(SR/OG)
	// Automation ID : TC103
	// Author: Robin T. Abraham
	// </summary>
	public void TC103_GppOpeningAndCollectionMultiplePlans() throws Exception 
	{
		//Step   1.Login to POS
		Thread.sleep(5000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);

		//Precondition -To take the current Board rate

		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");

		Map<String, String> SkuDetails = new LinkedHashMap<>();	
		List<String> PurityOfItems=new ArrayList();
		List<Double> AllRates = new ArrayList<>();
		Thread.sleep(3000);
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(GppOpeningAndCollectionMultiplePlans_TestData.TC103_SaleProductCount,
				GppOpeningAndCollectionMultiplePlans_TestData.TC103_TransferTypeSKU,
				GppOpeningAndCollectionMultiplePlans_TestData.TC103_FromCounterSKU,
				GppOpeningAndCollectionMultiplePlans_TestData.TC103_MetalTypeSKU
				);
		appUtils.HamBurgerButtonClick("iconShop");

		for (int i = 0;i < SkuListGold.size();i++)
		{
			String CurrentGoldSku = SkuListGold.get(i);
			appUtils.SKUIngridentTableValues(CurrentGoldSku.toString(), (i+1), SkuDetails);
			PurityOfItems.add(SkuDetails.get("SKU_" + (i+1) + "_Purity"));
			for (int ItemIndex = 1; ; ItemIndex++)  //Change
			{
				String Rate = "SKU" + (i+1)+ "_ITEM" + ItemIndex + "_RATE";
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
			SkuDetails.put("SKU_" + (i+1) + "_AllRates", AllRates.toString());
		}

		//For conversion of Rate
		String ItemPurity = PurityOfItems.get(0);
		double PurchaseRate =  AllRates.get(0);
		double ExpectedGoldRate22k=0;
		long InstallmentAmount=0;
		if (ItemPurity.equalsIgnoreCase("18k"))
		{
			double ConvertedGoldRate = (PurchaseRate * 22) / 18;
			ExpectedGoldRate22k = Math.round(ConvertedGoldRate);
		}
		else 
		{
			ExpectedGoldRate22k = Math.round(PurchaseRate);
		}
		appUtils.VoidTransaction();

		//Calculated Installment amount based on Rate
		InstallmentAmount = Math.round(ExpectedGoldRate22k / 500.0) * 500;
		String InstallmentAmountValue = String.valueOf(InstallmentAmount);

		//Step 2.Click on Transaction button and Go to GPP
		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));

		//Step 3.Select New GPP Account
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account"));

		//Step 4.Search customer phone number in search tab
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC103_PhoneNo, GppOpeningAndCollectionMultiplePlansTestData.TC103_Option);

		//Step 5.Click on "Add to GPP ac open"
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconBuy"));

		//Step 6.Select scheme code as "Golden Bloom purchase Advance plan"
		//Expected : Verify that selecting Scheme Code "GB" sets the installment amount to the default minimum of 1000 and is also editable
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"), GppOpeningAndCollectionMultiplePlansTestData.TC103_Scheme);
		Thread.sleep(2000);
		String DefaultInstallmentAmountValue=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		String DefaultInstallmentAmount=GppOpeningAndCollectionMultiplePlans_TestData.TC103_DefaultInstallmentAmount;
		asrt.assertEquals(DefaultInstallmentAmountValue,DefaultInstallmentAmount,"Mismatch in default installment amount when selecting Scheme Code GB,Got"+DefaultInstallmentAmountValue+" and Expected "+DefaultInstallmentAmount+" in GPP account opening page"); 
		Thread.sleep(3000);

		//Step 7.Enter Installment amount
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"),InstallmentAmountValue); 
		double FirstInstallmentAmount= Double.parseDouble(InstallmentAmountValue);

		//Step 8.Select Sales person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SalesPersonInput"), GppOpeningAndCollectionMultiplePlansTestData.TC103_SalesPerson);

		//Step 9.Select Id proof type 1
		base.selectorByVisibleText(GppOpeningAndCollectionMultiplePlansObj.Sel_IdType1("Id proof type 1"),GppOpeningAndCollectionMultiplePlansTestData.TC103_IdProofType1);

		//Step 10.Enter Id proof no 1
		base.setData(GppOpeningAndCollectionMultiplePlansObj.Ele_IdProofNo1("Idproofno1", "Id proof no 1"), GppOpeningAndCollectionMultiplePlansTestData.TC103_IdProofNo1);

		//Step 11.Select Bank proof type as "Passbook"
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("BankProof"),GppOpeningAndCollectionMultiplePlansTestData.TC103_BankProofType);

		//Step 12.Enter Nominee name and Select Nominee relation
		base.buttonClick(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"));
		base.setData(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"),GppOpeningAndCollectionMultiplePlansTestData.TC103_NomineeName);
		base.buttonClick(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"));
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"),GppOpeningAndCollectionMultiplePlansTestData.TC103_NomineeRelation);

		//Step 13.Click on check box(Same as above)
		base.buttonClick(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("IsSameAsAbove"));
		Thread.sleep(1000);

		//Step 14.Click on save
		//Expected :Verify Application ID, Scheme Details, Customer Details, Nominee Details, Bank Details		
		String CustomerName=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("CustNmInput"));
		String CustomerPhone=GppOpeningAndCollectionMultiplePlansTestData.TC103_PhoneNo;
		String SchemeDetails=base.getTheSelectedTextInDropDown(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"));
		String NomineeName=GppOpeningAndCollectionMultiplePlansTestData.TC103_NomineeName;
		String BankAccountNo=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkacno"));
		String BankIFSC=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkcode"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("View more"));
		Thread.sleep(1000);	
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_GpsacopnView_AppBarView_appBarCommandCommand"));

		Map<String, String> pdfDetails = pdfUtils.ExtractDetailsFromGPPPdf(GppOpeningAndCollectionMultiplePlansTestData.TC103_SchemeCode);
		String ApplicationIDPdf   = pdfDetails.get("ApplicationID");
		String SchemePdf          = pdfDetails.get("Plan");
		String CustomerNamePdf    = pdfDetails.get("CustomerName");
		String PhonePdf           = pdfDetails.get("Phone");
		String NomineeNamePdf     = pdfDetails.get("NomineeName");
		String BankAccountNoPdf   = pdfDetails.get("BankAccountNo");
		String IFSCCodePdf        = pdfDetails.get("IFSCCode");
		asrt.assertNotNull(ApplicationIDPdf,"Application ID " + ApplicationIDPdf + " not found in Golden Bloom purchase Plan PDF content.");
		asrt.assertEquals(SchemePdf, SchemeDetails,"Scheme name mismatch: "+SchemeDetails+" but got "+SchemePdf+" in Golden Bloom Purchase Plan Pdf");		
		asrt.assertEquals(CustomerNamePdf, CustomerName,"Customer name Mismatch: "+CustomerName+" but got "+CustomerNamePdf+" in  Golden Bloom Purchase Plan Pdf");		
		asrt.assertEquals(PhonePdf, CustomerPhone,"Customer phone number mismatch: "+CustomerPhone+" but got "+PhonePdf+" in  Golden Bloom Purchase Plan Pdf");		
		asrt.assertEquals(NomineeNamePdf, NomineeName,"Nominee name mismatch: "+NomineeName+" but got "+NomineeNamePdf+" in  Golden Bloom Purchase Plan Pdf");		
		asrt.assertEquals(BankAccountNoPdf, BankAccountNo,"Bank Account number mismatch: "+BankAccountNo+" but got "+BankAccountNoPdf+" in  Golden Bloom Purchase Plan Pdf");		
		asrt.assertEquals(IFSCCodePdf, BankIFSC,"Bank IFSC code mismatch: "+BankIFSC+" but got "+IFSCCodePdf+" in  Golden Bloom Purchase Plan Pdf");		

		//Step 15.Search the customer		
		//Step 16.Click on add to sale button
		appUtils.HamBurgerButtonClick("iconShop");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC103_CustomerNo,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 17.Select OG
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),"OG");
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Products"));
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));	

		//Step 18.Click on OG own
		//Step 19.Select configuration
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),GppOpeningAndCollectionMultiplePlansTestData.TC103_Purity);

		//Step 20.Click on Add item button		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		//Step 21.Select Purchase 
		Thread.sleep(2000);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("NPurchase"));
		base.ClickCondition(NormalSaleGoldAndSilverObj.Ele_Name("NPurchase"));

		//Step 22.Select QC person
		//Step 23.Select Smith person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),GppOpeningAndCollectionMultiplePlansTestData.TC103_QCPerson);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),GppOpeningAndCollectionMultiplePlansTestData.TC103_SmithPerson);

		//Step 24.Enter piece value
		//Step 25.Enter gross weight
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"), GppOpeningAndCollectionMultiplePlansTestData.TC103_Pieces);
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"), GppOpeningAndCollectionMultiplePlansTestData.TC103_GrossWeight);
		String OGTotalWeight=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));

		//Step 26.Click on calculate button
		//Expected : Check Purchase rate(Board rate)
		//check calculation Note:Cashier Screen after recall
		//*No of Product lines,*Displayed Item Name,*Displayed Quantity,*Displayed TOTAL (without Tax),*Displayed Subtotal,*TAX,*Total Amount
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(4000);
		String OGItemName = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = OGItemName.substring(OGItemName.indexOf("-") + 2);
		String OGTotalWeightInItemDetailPage=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt"));
		double OGTotalAmount = Double.parseDouble(TotalAmountInItemDetailsPge);

		//Purchase rate(Board rate)
		double ActualGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetrate")));
		double ExpectedGoldRateInItemDetailsPge = Math.round(ExpectedGoldRate22k * 0.98);
		asrt.assertEquals(Math.round(ActualGoldRateInItemDetailsPge),ExpectedGoldRateInItemDetailsPge,1,"Purchase rate (Board rate)22k mismatch: Expected Purchase rate is " +ExpectedGoldRate22k + " but got " + Math.round(ActualGoldRateInItemDetailsPge) +" in OG item details page");

		//Amount
		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetwt"));		
		double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
		double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
		String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogpnetamt"));
		double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);
		asrt.assertEquals(ExpectedGoldAmountInItemDetailsPge, ActualGoldAmountInItemDetailsPge,1.0,"GoldAmount mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in OG item details page");		

		//Step 27.Click on Add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("bottomLeft grow pad12", "1"));

		//*No of Product lines,*Displayed Item Name,*Displayed Quantity,*Displayed TOTAL (without Tax),*Displayed Subtotal,*TAX,*Total Amount
		List<WebElement> ListOfItemNamesInBillingPge = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));		
		int ExpectedLinesCountInBillingPge = ListOfItemNamesInBillingPge.size();

		int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("LinesField", "h4")));
		asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Recall page");

		String ActualItemNameInBillingPge = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		asrt.assertEquals(ExpectedItemNameInItemDetailsPge, ActualItemNameInBillingPge,"Item name mismatch: "+ExpectedItemNameInItemDetailsPge+" but found "+ActualItemNameInBillingPge+" in Recall page");

		String ActualGrossWeightInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5")).replace("-", "");
		double ActualGrossWeight = Double.parseDouble(ActualGrossWeightInBillingPge);
		double ExpectedQty = Double.parseDouble(OGTotalWeightInItemDetailPage);
		asrt.assertEquals(ExpectedQty, ActualGrossWeight,"Quantity mismatch: "+ExpectedQty+" but found "+ActualGrossWeight+" in Recall page");

		String ActualTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		double ActualTotalWithoutTax = Double.parseDouble(ActualTotalWithoutTaxInBillingPge);		
		asrt.assertEquals(OGTotalAmount, ActualTotalWithoutTax,"Total without tax mismatch: "+OGTotalAmount+" but found "+ActualTotalWithoutTax+" in Recall page");

		String ExpectedSubTotalInBillingPge = String.valueOf(ActualTotalWithoutTax);
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualSubTotal = String.valueOf(Double.parseDouble(ActualSubTotalInBillingPge));	
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotal,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotal+" in Recall page");

		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = GppOpeningAndCollectionMultiplePlansTestData.TC103_OGTax;
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"OG Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in Recall page");

		String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;	
		String ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualTotalAmount = String.valueOf(Double.parseDouble(ActualTotalAmountInBillingPge));	
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmount,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmount+" in Recall page");

		//Step	 28.Click on the Amount
		String ReceiveAmtInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 29.Select convert to advance
		//Step 30.1.Select type as GPP
		//Step 30.2.Select scheme id(GB)    
		//Step 30.3.Select sales agent
		//Step 30.4.Click on deposit button
		String PaymentModeBilling = base.GetText(LoginPageObj.Edt_AlertText(GppOpeningAndCollectionMultiplePlansTestData.TC103_PaymentMode));
		base.buttonClick(LoginPageObj.Edt_AlertText(GppOpeningAndCollectionMultiplePlansTestData.TC103_PaymentMode));	
		Thread.sleep(4000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("Ext col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("Ext col grow"),GppOpeningAndCollectionMultiplePlansTestData.TC103_SchemeType);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"),ApplicationIDPdf);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(GppOpeningAndCollectionMultiplePlansTestData.TC103_SalePersonNumber), GppOpeningAndCollectionMultiplePlansTestData.TC103_SalePersonName);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Step 31.Post the Invoice
		//Verify  *Application ID,*Being advance against ,*Customer Details,*Board rate,Purity, Amount,*Payment Mode		
		//Click on balance amount
		//Click on payment method as cash
		String BalanceAmtInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		String TrimBalance = BalanceAmtInBillingPge.replace("-", "").trim();
		double BalanceAmount = Double.parseDouble(TrimBalance);
		String ItemPurityValue=GppOpeningAndCollectionMultiplePlansTestData.TC103_Purity;
		if(BalanceAmount>0)
		{
			Thread.sleep(2000);
			//base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.buttonClick(LoginPageObj.Edt_AlertText(GppOpeningAndCollectionMultiplePlansTestData.TC103_PaymentMode2));	
			Thread.sleep(4000);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("cancelButton primaryButton"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "GPP ADV. INVOICE"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("btnblue"));

			Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
			double CumulativeAmountGppPdf = Double.parseDouble(PaymentDetails.get("CumAmt"));			
			String ApplicationIDGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();
			String AdvanceAgainstGppPdf = PaymentDetails.getOrDefault("AdvanceAgainst", "").trim();
			String CustomerNameGppPdf = PaymentDetails.getOrDefault("CustomerName", "").trim();
			String PhoneGppPdf = PaymentDetails.getOrDefault("Phone", "").trim();
			String BoardRateGppPdf = PaymentDetails.getOrDefault("BoardRate", "").trim();
			String PurityGppPdf = PaymentDetails.getOrDefault("Purity", "").trim();
			String PaymentModeGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
			String CustomerPhoneGppPdf=PaymentDetails.getOrDefault("Phone", "").trim();
			String SchemeName = (SchemeDetails.contains("::") ? SchemeDetails.split("::", 2)[1].trim() : SchemeDetails).replace("Plan"," ").trim();

			asrt.assertEquals(CustomerPhoneGppPdf,CustomerPhone,"Mismatch in Customer Mobile Number: expected " + CustomerPhone + ", but found " + CustomerPhoneGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(ApplicationIDPdf,ApplicationIDGppPdf,"Mismatch in Application Id: expected " + ApplicationIDPdf + ", but found " + ApplicationIDGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(CumulativeAmountGppPdf,FirstInstallmentAmount, "Mismatch in Cummilative Amount: expected " + FirstInstallmentAmount + ", but found " + CumulativeAmountGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals(AdvanceAgainstGppPdf,SchemeName,"Being Advance against value Mismatch: expected " + SchemeName + ", but found " + AdvanceAgainstGppPdf + "in the Gpp Reciept PDF");		
			asrt.assertEquals(CustomerNameGppPdf,CustomerName,"Mismatch in customer name: expected " + CustomerName + ", but found " + CustomerNameGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals( Double.parseDouble(BoardRateGppPdf), ExpectedGoldRate22k,"Mismatch in Board rate: expected " + ExpectedGoldRate22k + ", but found " + BoardRateGppPdf+ "in the Gpp Reciept PDF"); 
			asrt.assertEquals(PurityGppPdf,ItemPurityValue,"Mismatch in Purity : expected " + ItemPurityValue + ", but found " + PurityGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(PaymentModeGppPdf,PaymentModeBilling,"Mismatch in payment mode: expected " + PaymentModeBilling + ", but found " + PaymentModeGppPdf+ "in the Gpp Reciept PDF");	
		}
		else if(BalanceAmount == 0.00 )
		{
			Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
			double CumulativeAmountGppPdf = Double.parseDouble(PaymentDetails.get("CumAmt"));			
			String ApplicationIDGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();
			String AdvanceAgainstGppPdf = PaymentDetails.getOrDefault("AdvanceAgainst", "").trim();
			String CustomerNameGppPdf = PaymentDetails.getOrDefault("CustomerName", "").trim();
			String PhoneGppPdf = PaymentDetails.getOrDefault("Phone", "").trim();
			String BoardRateGppPdf = PaymentDetails.getOrDefault("BoardRate", "").trim();
			String PurityGppPdf = PaymentDetails.getOrDefault("Purity", "").trim();
			String PaymentModeGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
			String CustomerPhoneGppPdf=PaymentDetails.getOrDefault("Phone", "").trim();
			String SchemeName = SchemeDetails.contains("::") ? SchemeDetails.split("::", 2)[1].trim() : SchemeDetails;

			asrt.assertEquals(CustomerPhoneGppPdf,CustomerPhone,"Mismatch in Customer Mobile Number: expected " + CustomerPhone + ", but found " + CustomerPhoneGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(ApplicationIDPdf,ApplicationIDGppPdf,"Mismatch in Application Id: expected " + ApplicationIDPdf + ", but found " + ApplicationIDGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(PaymentModeGppPdf,PaymentModeBilling,"Mismatch in payment mode: expected " + PaymentModeBilling + ", but found " + PaymentModeGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals(CumulativeAmountGppPdf,FirstInstallmentAmount, "Mismatch in Cummilative Amount: expected " + FirstInstallmentAmount + ", but found " + CumulativeAmountGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals(AdvanceAgainstGppPdf,SchemeName,"Being Advance against value Mismatch: expected " + SchemeName + ", but found " + AdvanceAgainstGppPdf + "in the Gpp Reciept PDF");		
			asrt.assertEquals(CustomerNameGppPdf,CustomerName,"Mismatch in customer name: expected " + CustomerName + ", but found " + CustomerNameGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals( Double.parseDouble(BoardRateGppPdf), ExpectedGoldRate22k,"Mismatch in Board rate: expected " + ExpectedGoldRate22k + ", but found " + BoardRateGppPdf+ "in the Gpp Reciept PDF"); 
			asrt.assertEquals(PurityGppPdf,ItemPurityValue,"Mismatch in Purity : expected " + ItemPurityValue + ", but found " + PurityGppPdf + "in the Gpp Reciept PDF");	
		}
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
	}
	// <summary>
	// Test Case Title : Open GS scheme and take collection
	// Automation ID : TC101
	// Author: Chandana Babu
	// </summary>
	public void TC101_GppOpeningAndCollectionMultiplePlans() throws Exception {

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");

		//Step 2: Go to GPP
		base.ClickCondition(LoginPageObj.Edt_AlertText("GPP"));

		//Step 3: Select New GPP Account Opening
		WebDriverWait Wait =new WebDriverWait(driver, Duration.ofSeconds(50));
		Wait.until(ExpectedConditions.presenceOfElementLocated(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account")));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account"));

		//Step 4: Search customer phone number
		//Step 5: Add to GPP ac open
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC101_CustomerNo,UtilityTestData.MenuBarOptions[6]);

		//Step 6: Select scheme code(GS)
		//Expected: Verify that selecting Scheme Code "GS" sets the installment amount to the default minimum of "1" and is also editable 
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"),GppOpeningAndCollectionMultiplePlansTestData.TC101_Scheme);
		Thread.sleep(1000);
		String DefaultGSInstallmentAmount = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		asrt.assertEquals(DefaultGSInstallmentAmount, GppOpeningAndCollectionMultiplePlansTestData.TC101_DefaultInstallmentAmount,"Default GS Installment Amount mismatch: Expected is "+GppOpeningAndCollectionMultiplePlansTestData.TC101_DefaultInstallmentAmount+" but got "+DefaultGSInstallmentAmount+" in GPP account opening page");

		//Step 7: Enter installment amount		
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"), GppOpeningAndCollectionMultiplePlansTestData.TC101_InstallmentAmount);
		Thread.sleep(1000);
		String ActualInstallmentAmount = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		asrt.assertEquals(ActualInstallmentAmount, GppOpeningAndCollectionMultiplePlansTestData.TC101_InstallmentAmount,"Installment Amount mismatch: Expected is "+GppOpeningAndCollectionMultiplePlansTestData.TC101_InstallmentAmount+" but got "+ActualInstallmentAmount+" in GPP account opening page");

		//Step 8: Select sales person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SalesPersonInput"),GppOpeningAndCollectionMultiplePlansTestData.TC101_SalesPerson);

		//Step 9: Select ID Proof 1
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("IdTypeOptions"), GppOpeningAndCollectionMultiplePlansTestData.TC101_IdProofType);

		//Step 10: Enter ID Proof number
		//Note: If customer doesn't have bank account add bank details
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Idproofno1"), GppOpeningAndCollectionMultiplePlansTestData.TC101_IdProofNo);

		//Step 11: Select bank proof type
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("BankProof"),GppOpeningAndCollectionMultiplePlansTestData.TC101_BankProof);

		//Step 12: Enter nominee name
		base.setData(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"),UtilityTestData.NomineeName);

		//Step 13: Select nominee relation
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"),UtilityTestData.NomineeRelation);

		//Step 14: Click on check box( same as above)
		base.ClickCondition(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("IsSameAsAbove"));

		//Step 15: Click on save button
		//Expected: Verify Application ID, Scheme Details, Customer Details, Nominee Details, Bank Details
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("View more"));
		Thread.sleep(1000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("SearchView_AppBarView_appBarCommandCommand"));
		String CustomerName=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("CustNmInput"));
		String CustomerPhone=GppOpeningAndCollectionMultiplePlansTestData.TC101_PhoneNo;
		String SchemeDetails=base.getTheSelectedTextInDropDown(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"));
		String NomineeName=UtilityTestData.NomineeName;
		String BankAccountNo=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkacno"));
		String BankIFSC=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkcode"));		
		Map<String, String> PdfDetails = pdfUtils.ExtractDetailsFromGPPPdf(GppOpeningAndCollectionMultiplePlansTestData.TC101_SchemeCode);
		String ApplicationIdPdf   = PdfDetails.get("ApplicationID");
		String SchemePdf          = PdfDetails.get("Plan");
		String CustomerNamePdf    = PdfDetails.get("CustomerName");
		String PhonePdf           = PdfDetails.get("Phone");
		String NomineeNamePdf     = PdfDetails.get("NomineeName");
		String BankAccountNoPdf   = PdfDetails.get("BankAccountNo");
		String IFSCCodePdf        = PdfDetails.get("IFSCCode");
		asrt.assertNotNull(ApplicationIdPdf,"Application ID " + ApplicationIdPdf + " not found in Golden Bliss Wedding Advance Plan PDF content.");
		asrt.assertEquals(SchemePdf, SchemeDetails,"Scheme name mismatch: Expected is "+SchemeDetails+" but got "+SchemePdf+" in Golden Bliss Wedding Advance Plan Pdf");		
		asrt.assertEquals(CustomerNamePdf, CustomerName,"Customer name Mismatch: Expected is "+CustomerName+" but got "+CustomerNamePdf+" in  Golden Bliss Wedding Advance Plan Pdf");		
		asrt.assertEquals(PhonePdf, CustomerPhone,"Customer phone number mismatch: Expected is "+CustomerPhone+" but got "+PhonePdf+" in  Golden Bliss Wedding Advance Plan Pdf");		
		asrt.assertEquals(NomineeNamePdf, NomineeName,"Nominee name mismatch: Expected is "+NomineeName+" but got "+NomineeNamePdf+" in  Golden Bliss Wedding Advance Plan Pdf");		
		asrt.assertEquals(BankAccountNoPdf, BankAccountNo,"Bank Account number mismatch: Expected is"+BankAccountNo+" but got "+BankAccountNoPdf+" in  Golden Bliss Wedding Advance Plan Pdf");		
		asrt.assertEquals(IFSCCodePdf, BankIFSC,"Bank IFSC code mismatch: Expected is "+BankIFSC+" but got "+IFSCCodePdf+" in  Golden Bliss Wedding Advance Plan Pdf");		

		//Step 16: Select customer
		//Step 17: Click on Add to sale button
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlans_TestData.TC101_CustomerNo,UtilityTestData.MenuBarOptions[0]);

		//Step 18: Click on GPP
		base.ClickCondition(LoginPageObj.Edt_AlertText("GPP"));

		//Step 19: GPP collection entry
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_return("GPP Collection Entry"));

		//Step 20: Choose deposit type
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),GppOpeningAndCollectionMultiplePlansTestData.TC101_DepositType);

		//Step 21: Enter transaction number
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), ApplicationIdPdf);
		String ExpectedDepositAmount = String.format("%.2f",Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"))));

		//Step 22: Select sales person
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"),UtilityTestData.SalePersonName);		 

		//Step 23: Click on deposit
		//Note: Billing screen: Total Amount
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(1000);
		String ActualDepositAmount = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replaceAll("[^\\d.]", "").trim();
		asrt.assertEquals(ActualDepositAmount, ExpectedDepositAmount,"Deposit amount mismatch: Expected is "+ExpectedDepositAmount+" but got "+ActualDepositAmount+" in billing page");

		//Step 24: Click on the Amount
		base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 25: Select a Payment method(Cash or Card)
		base.ClickCondition(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 26: Post the Invoice
		//Expected: Verify invoice Amt(Rs.), Cum. Amt(Rs.) and Payment Mode
		Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
		String ActualAmountInGppPdf = PaymentDetails.get("Amt");
		String ActualCumulativeAmountInGppPdf = PaymentDetails.get("CumAmt");			
		String ActualPaymentModeInGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
		String ActualApplicationIdInGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();

		String ExpectedAmountInGppPdf = ActualDepositAmount;
		String ExpectedApplicationIdInGppPdf= ApplicationIdPdf;

		asrt.assertEquals(ActualAmountInGppPdf,ExpectedAmountInGppPdf,"Amount mismatch: Expected is "+ExpectedAmountInGppPdf+" but got "+ActualAmountInGppPdf+" in Gpp Reciept PDF");
		asrt.assertEquals(ActualCumulativeAmountInGppPdf,ExpectedAmountInGppPdf, "Cummilative Amount mismatch: Expected is " + ExpectedAmountInGppPdf + " but found " + ActualCumulativeAmountInGppPdf+ " in Gpp Reciept PDF");
		asrt.assertEquals(ActualPaymentModeInGppPdf,GppOpeningAndCollectionMultiplePlansTestData.TC101_PaymentMode,"Payment mode mismatch: Expected " + GppOpeningAndCollectionMultiplePlansTestData.TC101_PaymentMode + ", but found " + ActualPaymentModeInGppPdf+ " in Gpp Reciept PDF");
		asrt.assertEquals(ActualApplicationIdInGppPdf,ExpectedApplicationIdInGppPdf,"Application Id mismatch: Expected " + ExpectedApplicationIdInGppPdf + ", but found " + ActualApplicationIdInGppPdf +" in Gpp Reciept PDF");
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}


	// <summary>
	// Test Case Title : Open GB scheme and take collection
	// Automation ID : TC100
	// Author: Hasna E K
	// </summary>
	public void TC100_GppOpeningAndCollectionMultiplePlans() throws Exception 
	{
		//Step   1.Login to POS
		Thread.sleep(5000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);

		//Step 2.Go to GPP
		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));

		//Step 3.Select New GPP Account
		WebDriverWait Wait =new WebDriverWait(driver, Duration.ofSeconds(20));
		Wait.until(ExpectedConditions.presenceOfElementLocated(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account")));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account"));

		//Step 4.Search customer phone number 
		//Step 5.Click on "Add to GPP ac open"
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC100_PhoneNo, UtilityTestData.MenuBarOptions[6]);

		//Step 6.Select scheme code(GB)
		//Expected : selecting Scheme Code "GB" sets the installment amount to the default minimum of 1000 and is also editable
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"),GppOpeningAndCollectionMultiplePlansTestData.TC100_Scheme);
		Thread.sleep(1000);
		String DefaultGBInstallmentAmount = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		String ExpectedDefaultInstallmentAmount = GppOpeningAndCollectionMultiplePlansTestData.TC100_DefaultInstallmentAmount;
		asrt.assertEquals(DefaultGBInstallmentAmount, ExpectedDefaultInstallmentAmount,"Default GS Installment Amount mismatch: Expected is "+ExpectedDefaultInstallmentAmount+" but got "+DefaultGBInstallmentAmount+" in GPP account opening page");

		//Step 7.Enter Installment amount
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"), GppOpeningAndCollectionMultiplePlansTestData.TC100_InstallmentAmount);
		Thread.sleep(1000);

		//Step 8.Select Sales person
		Thread.sleep(2000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SalesPersonInput"), GppOpeningAndCollectionMultiplePlansTestData.TC100_SalesPerson);

		//Step 9.Select Id proof type 1
		base.selectorByVisibleText(GppOpeningAndCollectionMultiplePlansObj.Sel_IdType1("Id proof type 1"),GppOpeningAndCollectionMultiplePlansTestData.TC100_IdProofType1);

		//Step 10: Enter ID Proof number
		//Note: If customer doesn't have bank account add bank details
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Idproofno1"), GppOpeningAndCollectionMultiplePlansTestData.TC100_IdProofNo1);

		String BankAccountNo = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkacno"));
		if (BankAccountNo == null) {
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Bnkacno"), GppOpeningAndCollectionMultiplePlansTestData.TC100_BankAccountNo);
			Thread.sleep(1000);
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Bnkname"), GppOpeningAndCollectionMultiplePlansTestData.TC100_BankName);
			Thread.sleep(1000);
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Bnkcode"), GppOpeningAndCollectionMultiplePlansTestData.TC100_BankIfscCode);
			Thread.sleep(1000);
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("CustBranch"), GppOpeningAndCollectionMultiplePlansTestData.TC100_BankBranch);	
			Thread.sleep(1000);
		}

		//Step 11: Select bank proof type
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("BankProof"),GppOpeningAndCollectionMultiplePlansTestData.TC100_BankProof);

		//Step 12: Enter nominee name
		base.setData(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"),UtilityTestData.NomineeName);

		//Step 13: Select nominee relation
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"),UtilityTestData.NomineeRelation);

		//Step 14: Click on check box( same as above)
		base.ClickCondition(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("IsSameAsAbove"));

		//Step 15: Click on save button
		//Expected: Verify Application ID, Scheme Details, Customer Details, Nominee Details, Bank Details
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("View more"));
		Thread.sleep(1000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("SearchView_AppBarView_appBarCommandCommand"));
		String CustomerName=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("CustNmInput"));
		String CustomerPhone=GppOpeningAndCollectionMultiplePlansTestData.TC100_PhoneNo;
		String SchemeDetails=base.getTheSelectedTextInDropDown(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"));
		String NomineeName=UtilityTestData.NomineeName;
		String BankIfscCode=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkcode"));		
		Map<String, String> PdfDetails = pdfUtils.ExtractDetailsFromGPPPdf(GppOpeningAndCollectionMultiplePlansTestData.TC100_SchemeCode);
		String ApplicationIdPdf   = PdfDetails.get("ApplicationID");
		String SchemePdf          = PdfDetails.get("Plan");
		String CustomerNamePdf    = PdfDetails.get("CustomerName");
		String PhonePdf           = PdfDetails.get("Phone");
		String NomineeNamePdf     = PdfDetails.get("NomineeName");
		String BankAccountNoPdf   = PdfDetails.get("BankAccountNo");
		String IfscCodePdf        = PdfDetails.get("IFSCCode");
		asrt.assertNotNull(ApplicationIdPdf,"Application ID " + ApplicationIdPdf + " not found in Golden Bloom Purchase Advance Plan PDF content.");
		asrt.assertEquals(SchemePdf, SchemeDetails,"Scheme name mismatch: Expected "+SchemeDetails+" but got "+SchemePdf+" in Golden Bloom Purchase Advance Plan Pdf");		
		asrt.assertEquals(CustomerNamePdf, CustomerName,"Customer name Mismatch: Expected "+CustomerName+" but got "+CustomerNamePdf+" in Golden Bloom Purchase Advance Plan Pdf");		
		asrt.assertEquals(PhonePdf, CustomerPhone,"Customer phone number mismatch: Expected "+CustomerPhone+" but got "+PhonePdf+" in Golden Bloom Purchase Advance Plan Pdf");		
		asrt.assertEquals(NomineeNamePdf, NomineeName,"Nominee name mismatch: Expected "+NomineeName+" but got "+NomineeNamePdf+" in Golden Bloom Purchase Advance Plan Pdf");		
		asrt.assertEquals(BankAccountNoPdf, BankAccountNo,"Bank Account number mismatch: Expected "+BankAccountNo+" but got "+BankAccountNoPdf+" in Golden Bloom Purchase Advance Plan Pdf");		
		asrt.assertEquals(IfscCodePdf, BankIfscCode,"Bank IFSC code mismatch: Expected "+BankIfscCode+" but got "+IfscCodePdf+" in Golden Bloom Purchase Advance Plan Pdf");		

		//Step 16: Select customer
		//Step 17: Click on Add to sale button
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlans_TestData.TC100_CustomerId,UtilityTestData.MenuBarOptions[0]);

		//Step 18: Click on GPP
		base.ClickCondition(LoginPageObj.Edt_AlertText("GPP"));

		//Step 19: GPP collection entry
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_return("GPP Collection Entry"));

		//Step 20: Choose deposit type
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),GppOpeningAndCollectionMultiplePlansTestData.TC100_DepositType);

		//Step 21: Enter transaction number
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), ApplicationIdPdf);
		String ExpectedDepositAmount = String.format("%.2f",Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"))));

		//Step 22: Select sales person
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"),UtilityTestData.SalePersonName);		 

		//Step 23: Click on deposit
		//Note: Billing screen: Total Amount
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(1000);
		String ActualDepositAmount = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replaceAll("[^\\d.]", "").trim();
		asrt.assertEquals(ActualDepositAmount, ExpectedDepositAmount,"Deposit amount mismatch: Expected is "+ExpectedDepositAmount+" but got "+ActualDepositAmount+" in billing page");

		//Step 24: Click on the Amount
		base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 25: Select a Payment method(Cash or Card)
		base.ClickCondition(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 26: Post the Invoice
		//Expected: Verify invoice Amt(Rs.), Cum. Amt(Rs.) and Payment Mode
		Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
		String ActualAmountInGppPdf = PaymentDetails.get("Amt");
		String ActualCumulativeAmountInGppPdf = PaymentDetails.get("CumAmt");			
		String ActualPaymentModeInGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
		String ActualApplicationIdInGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();

		String ExpectedAmountInGppPdf = ActualDepositAmount;
		String ExpectedApplicationIdInGppPdf= ApplicationIdPdf;

		asrt.assertEquals(ActualAmountInGppPdf,ExpectedAmountInGppPdf,"Amount mismatch: Expected is "+ExpectedAmountInGppPdf+" but got "+ActualAmountInGppPdf+" in Gpp Reciept PDF");
		asrt.assertEquals(ActualCumulativeAmountInGppPdf,ExpectedAmountInGppPdf, "Cummilative Amount mismatch: Expected is " + ExpectedAmountInGppPdf + " but found " + ActualCumulativeAmountInGppPdf+ " in Gpp Reciept PDF");
		asrt.assertEquals(ActualPaymentModeInGppPdf,GppOpeningAndCollectionMultiplePlansTestData.TC100_PaymentMode,"Payment mode mismatch: Expected " + GppOpeningAndCollectionMultiplePlansTestData.TC100_PaymentMode + ", but found " + ActualPaymentModeInGppPdf+ " in Gpp Reciept PDF");
		asrt.assertEquals(ActualApplicationIdInGppPdf,ExpectedApplicationIdInGppPdf,"Application Id mismatch: Expected " + ExpectedApplicationIdInGppPdf + ", but found " + ActualApplicationIdInGppPdf +" in Gpp Reciept PDF");
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Open GG scheme and take collection
	// Automation ID : TC99
	// Author: Robin T Abraham
	// </summary>
	public void TC99_GppOpeningAndCollectionMultiplePlans() throws Exception {

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");

		//Step 2: Go to GPP
		base.ClickCondition(LoginPageObj.Edt_AlertText("GPP"));

		//Step 3: Select New GPP Account Opening
		WebDriverWait Wait =new WebDriverWait(driver, Duration.ofSeconds(50));
		Wait.until(ExpectedConditions.presenceOfElementLocated(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account")));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account"));

		//Step 4: Search customer phone number
		//Step 5: Add to GPP ac open
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC99_CustomerNo,UtilityTestData.MenuBarOptions[6]);

		//Step 6: Select scheme code(GG)
		//Expected: Verify that selecting Scheme Code as "GG" sets the installment amount to the default minimum of 1000 and is also editable 
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"),GppOpeningAndCollectionMultiplePlansTestData.TC99_Scheme);
		Thread.sleep(1000);
		String DefaultGGInstallmentAmount = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		asrt.assertEquals(DefaultGGInstallmentAmount, GppOpeningAndCollectionMultiplePlansTestData.TC99_DefaultInstallmentAmount,"Default GG Installment Amount mismatch: Expected is "+GppOpeningAndCollectionMultiplePlansTestData.TC99_DefaultInstallmentAmount+" but got "+DefaultGGInstallmentAmount+" in GPP account opening page");

		//Step 7: Enter installment amount		
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"), GppOpeningAndCollectionMultiplePlansTestData.TC99_InstallmentAmount);
		Thread.sleep(1000);
		String ActualInstallmentAmount = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		asrt.assertEquals(ActualInstallmentAmount, GppOpeningAndCollectionMultiplePlansTestData.TC99_InstallmentAmount,"Installment Amount mismatch: Expected is "+GppOpeningAndCollectionMultiplePlansTestData.TC99_InstallmentAmount+" but got "+ActualInstallmentAmount+" in GPP account opening page");

		//Step 8: Select sales person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SalesPersonInput"),GppOpeningAndCollectionMultiplePlansTestData.TC99_SalesPerson);

		//Step 9: Select ID Proof 1
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("IdTypeOptions"), GppOpeningAndCollectionMultiplePlansTestData.TC99_IdProofType);

		//Step 10: Enter ID Proof number
		//Note: If customer doesn't have bank account add bank details
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Idproofno1"), GppOpeningAndCollectionMultiplePlansTestData.TC99_IdProofNo);

		//Step 11: Select bank proof type
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("BankProof"),GppOpeningAndCollectionMultiplePlansTestData.TC99_BankProof);

		//Step 12: Enter nominee name
		base.setData(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"),UtilityTestData.NomineeName);

		//Step 13: Select nominee relation
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"),UtilityTestData.NomineeRelation);

		//Step 14: Click on check box( same as above)
		base.ClickCondition(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("IsSameAsAbove"));

		//Step 15: Click on save button
		//Expected: Verify Application ID, Scheme Details, Customer Details, Nominee Details, Bank Details
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("View more"));
		Thread.sleep(1000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("SearchView_AppBarView_appBarCommandCommand"));
		String CustomerName=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("CustNmInput"));
		String CustomerPhone=GppOpeningAndCollectionMultiplePlansTestData.TC99_PhoneNo;
		String SchemeDetails=base.getTheSelectedTextInDropDown(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"));
		String NomineeName=UtilityTestData.NomineeName;
		String BankAccountNo=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkacno"));
		String BankIFSC=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkcode"));		
		Map<String, String> PdfDetails = pdfUtils.ExtractDetailsFromGPPPdf(GppOpeningAndCollectionMultiplePlansTestData.TC99_SchemeCode);
		String ApplicationIdPdf   = PdfDetails.get("ApplicationID");
		String SchemePdf          = PdfDetails.get("Plan");
		String CustomerNamePdf    = PdfDetails.get("CustomerName");
		String PhonePdf           = PdfDetails.get("Phone");
		String NomineeNamePdf     = PdfDetails.get("NomineeName");
		String BankAccountNoPdf   = PdfDetails.get("BankAccountNo");
		String IFSCCodePdf        = PdfDetails.get("IFSCCode");
		asrt.assertNotNull(ApplicationIdPdf,"Application ID " + ApplicationIdPdf + " not found in Golden Glow Wedding Advance Plan PDF content.");
		asrt.assertEquals(SchemePdf, SchemeDetails,"Scheme name mismatch: Expected is "+SchemeDetails+" but got "+SchemePdf+" in Golden Glow Wedding Advance Plan Pdf");		
		asrt.assertEquals(CustomerNamePdf, CustomerName,"Customer name Mismatch: Expected is "+CustomerName+" but got "+CustomerNamePdf+" in  Golden Glow Wedding Advance Plan Pdf");		
		asrt.assertEquals(PhonePdf, CustomerPhone,"Customer phone number mismatch: Expected is "+CustomerPhone+" but got "+PhonePdf+" in  Golden Glow Wedding Advance Plan Pdf");		
		asrt.assertEquals(NomineeNamePdf, NomineeName,"Nominee name mismatch: Expected is "+NomineeName+" but got "+NomineeNamePdf+" in  Golden Glow Wedding Advance Plan Pdf");		
		asrt.assertEquals(BankAccountNoPdf, BankAccountNo,"Bank Account number mismatch: Expected is"+BankAccountNo+" but got "+BankAccountNoPdf+" in  Golden Glow Wedding Advance Plan Pdf");		
		asrt.assertEquals(IFSCCodePdf, BankIFSC,"Bank IFSC code mismatch: Expected is "+BankIFSC+" but got "+IFSCCodePdf+" in  Golden Glow Wedding Advance Plan Pdf");		

		//Step 16: Select customer
		//Step 17: Click on Add to sale button
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlans_TestData.TC99_CustomerNo,UtilityTestData.MenuBarOptions[0]);

		//Step 18: Click on GPP
		base.ClickCondition(LoginPageObj.Edt_AlertText("GPP"));

		//Step 19: GPP collection entry
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_return("GPP Collection Entry"));

		//Step 20: Choose deposit type
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),GppOpeningAndCollectionMultiplePlansTestData.TC99_DepositType);

		//Step 21: Enter transaction number
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), ApplicationIdPdf);
		String ExpectedDepositAmount = String.format("%.2f",Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"))));

		//Step 22: Select sales person
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"),UtilityTestData.SalePersonName);		 

		//Step 23: Click on deposit
		//Note: Billing screen: Total Amount
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(1000);
		String ActualDepositAmount = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replaceAll("[^\\d.]", "").trim();
		asrt.assertEquals(ActualDepositAmount, ExpectedDepositAmount,"Deposit amount mismatch: Expected is "+ExpectedDepositAmount+" but got "+ActualDepositAmount+" in billing page");

		//Step 24: Click on the Amount
		base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 25: Select a Payment method(Cash or Card)
		base.ClickCondition(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 26: Post the Invoice
		//Expected: Verify invoice Amt(Rs.), Cum. Amt(Rs.) and Payment Mode
		Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
		String ActualAmountInGppPdf = PaymentDetails.get("Amt");
		String ActualCumulativeAmountInGppPdf = PaymentDetails.get("CumAmt");			
		String ActualPaymentModeInGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
		String ActualApplicationIdInGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();

		String ExpectedAmountInGppPdf = ActualDepositAmount;
		String ExpectedApplicationIdInGppPdf= ApplicationIdPdf;

		asrt.assertEquals(ActualAmountInGppPdf,ExpectedAmountInGppPdf,"Amount mismatch: Expected is "+ExpectedAmountInGppPdf+" but got "+ActualAmountInGppPdf+" in Gpp Reciept PDF");
		asrt.assertEquals(ActualCumulativeAmountInGppPdf,ExpectedAmountInGppPdf, "Cummilative Amount mismatch: Expected is " + ExpectedAmountInGppPdf + " but found " + ActualCumulativeAmountInGppPdf+ " in Gpp Reciept PDF");
		asrt.assertEquals(ActualPaymentModeInGppPdf,GppOpeningAndCollectionMultiplePlansTestData.TC99_PaymentMode,"Payment mode mismatch: Expected " + GppOpeningAndCollectionMultiplePlansTestData.TC99_PaymentMode + ", but found " + ActualPaymentModeInGppPdf+ " in Gpp Reciept PDF");
		asrt.assertEquals(ActualApplicationIdInGppPdf,ExpectedApplicationIdInGppPdf,"Application Id mismatch: Expected " + ExpectedApplicationIdInGppPdf + ", but found " + ActualApplicationIdInGppPdf +" in Gpp Reciept PDF");
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

	}	
	// <summary>
	// Test Case Title : Collection against an existing GG scheme
	// Automation ID : TC105
	// Author: Chandana Babu
	// </summary>
	public void TC105_GppOpeningAndCollectionMultiplePlans() throws Exception {

		//Step 1: Login to POS
		//Step 2: Click on Transaction button
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");
		
		//Pre-requsite
		String ApplicationIdPdf = appUtils.OpenGppAndTakeCollection(
				GppOpeningAndCollectionMultiplePlansTestData.TC105_CustomerNo,
				GppOpeningAndCollectionMultiplePlansTestData.TC105_Scheme,
				GppOpeningAndCollectionMultiplePlansTestData.TC105_InstallmentAmount,
				GppOpeningAndCollectionMultiplePlansTestData.TC105_SchemeCode,
				"","");

		//Step 3: Select customer
		//Step 4: Click on add to sale button
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlans_TestData.TC105_CustomerNo,UtilityTestData.MenuBarOptions[0]);

		//Step 5: Click on gpp--> gpp collection entry
		base.ClickCondition(LoginPageObj.Edt_AlertText("GPP"));
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_return("GPP Collection Entry"));

		//Step 6:Choose deposit type
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),GppOpeningAndCollectionMultiplePlansTestData.TC105_DepositType);

		//Step 7: Enter transaction number,deposit amount and sales person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), ApplicationIdPdf);
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),GppOpeningAndCollectionMultiplePlansTestData.TC105_InstallmentAmount);
		Thread.sleep(2000);
		String ExpectedDepositAmount = String.format("%.2f",Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"))));
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"),UtilityTestData.SalePersonName);

		//Step 8: Click on deposit
		//Note: Billing screen Total Amount
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(1000);
		String ActualDepositAmount = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replaceAll("[^\\d.]", "").trim();
		asrt.assertEquals(ActualDepositAmount, ExpectedDepositAmount,"Deposit amount mismatch: Expected is "+ExpectedDepositAmount+" but got "+ActualDepositAmount+" in billing page");

		//Step 9: Click on the Amount
		base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 10: Select a Payment method(Cash or Card)
		base.ClickCondition(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 11: Post the Invoice
		//Expected: Verify invoice Amt(Rs.), Cum. Amt(Rs.) , Payment Mode
		Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
		String ActualAmountInGppPdf = PaymentDetails.get("Amt");
		String ActualCumulativeAmountInGppPdf = PaymentDetails.get("CumAmt");			
		String ActualPaymentModeInGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
		String ActualApplicationIdInGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();

		String ExpectedAmountInGppPdf = ActualDepositAmount;
		String ExpectedCumulativeAmountInGppPdf = String.format("%.2f",(Double.parseDouble(ActualDepositAmount)+Double.parseDouble(GppOpeningAndCollectionMultiplePlansTestData.TC105_InstallmentAmount)));
		String ExpectedApplicationIdInGppPdf= ApplicationIdPdf;

		asrt.assertEquals(ActualAmountInGppPdf,ExpectedAmountInGppPdf,"Amount mismatch: Expected is "+ExpectedAmountInGppPdf+" but got "+ActualAmountInGppPdf+" in Gpp Reciept PDF");
		asrt.assertEquals(ActualCumulativeAmountInGppPdf,ExpectedCumulativeAmountInGppPdf, "Cummilative Amount mismatch: Expected is " + ExpectedCumulativeAmountInGppPdf + " but found " + ActualCumulativeAmountInGppPdf+ " in Gpp Reciept PDF");
		asrt.assertEquals(ActualPaymentModeInGppPdf,GppOpeningAndCollectionMultiplePlansTestData.TC105_PaymentMode,"Payment mode mismatch: Expected is " + GppOpeningAndCollectionMultiplePlansTestData.TC105_PaymentMode + ", but found " + ActualPaymentModeInGppPdf+ " in Gpp Reciept PDF");
		asrt.assertEquals(ActualApplicationIdInGppPdf,ExpectedApplicationIdInGppPdf,"Application Id mismatch: Expected is " + ExpectedApplicationIdInGppPdf + " but found " + ActualApplicationIdInGppPdf +" in Gpp Reciept PDF");
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	
	// <summary>
		// Test Case Title : Take single collection for multiple installment of GG scheme
		// Automation ID : TC108
		// Author: Gokul.P
		// </summary>
		public void TC108_GppOpeningAndCollectionMultiplePlans() throws Exception
		{		
			//Step 1: Login to POS
			//Step 2: Click on Transaction button
			login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
			base.setZoom(driver, 60);
			appUtils.HamBurgerButtonClick("iconShop");
			
			//Pre-requsite
			String ApplicationIdPdf = appUtils.OpenGppAndTakeCollection(
					GppOpeningAndCollectionMultiplePlansTestData.TC108_CustomerNo,
					GppOpeningAndCollectionMultiplePlansTestData.TC108_Scheme,
					GppOpeningAndCollectionMultiplePlansTestData.TC108_InstallmentAmount,
					GppOpeningAndCollectionMultiplePlansTestData.TC108_SchemeCode,
					"","");
		
			//Step 3: Select customer
			//Step 4: Click on add to sale button
			appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlans_TestData.TC108_CustomerNo,UtilityTestData.MenuBarOptions[0]);
			
			//Step 5: Click on gpp--> gpp collection entry
			base.ClickCondition(LoginPageObj.Edt_AlertText("GPP"));
			base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_return("GPP Collection Entry"));
			
			//Step 6:Choose deposit type
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),GppOpeningAndCollectionMultiplePlansTestData.TC108_DepositType);
			
			//Step 7: Enter transaction number,deposit amount and sales person
			//Note: deposit amount is 1000 then should enter multiple of 1000
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), ApplicationIdPdf);
			String DepositAmount = Integer.toString((Integer.parseInt(GppOpeningAndCollectionMultiplePlansTestData.TC108_InstallmentAmount)*GppOpeningAndCollectionMultiplePlansTestData.TC108_Multiple));
			base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),DepositAmount);
			String ExpectedDepositAmount = String.format("%.2f",Double.parseDouble(base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"))));
			base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"),UtilityTestData.SalePersonName);
		
			//Step 8: Click on deposit
			//Step 9: Click on the Amount
			//Note: Billing screen Total Amount
			base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
			Thread.sleep(1000);
			String ActualDepositAmount = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replaceAll("[^\\d.]", "").trim();
			asrt.assertEquals(ActualDepositAmount, ExpectedDepositAmount,"Deposit amount mismatch: Expected is "+ExpectedDepositAmount+" but got "+ActualDepositAmount+" in billing page");
			base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			
			//Step 10: Select a Payment method(Cash or Card)
			base.ClickCondition(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
			String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
			base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
			base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
			base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
			base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
			
			//Step 11: Post the Invoice
			//Expected: Verify invoice Amt(Rs.), Cum. Amt(Rs.) , Payment Mode
			Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
			String ActualAmountInGppPdf = PaymentDetails.get("Amt");
			String ActualCumulativeAmountInGppPdf = PaymentDetails.get("CumAmt");			
			String ActualPaymentModeInGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
			String ActualApplicationIdInGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();
	 
			String ExpectedAmountInGppPdf = ActualDepositAmount;
			String ExpectedCumulativeAmountInGppPdf = String.format("%.2f",(Double.parseDouble(ActualDepositAmount)+Double.parseDouble(GppOpeningAndCollectionMultiplePlansTestData.TC108_InstallmentAmount)));
			String ExpectedApplicationIdInGppPdf= ApplicationIdPdf;
	 
			asrt.assertEquals(ActualAmountInGppPdf,ExpectedAmountInGppPdf,"Amount mismatch: Expected is "+ExpectedAmountInGppPdf+" but got "+ActualAmountInGppPdf+" in Gpp Reciept PDF");
			asrt.assertEquals(ActualCumulativeAmountInGppPdf,ExpectedCumulativeAmountInGppPdf, "Cummilative Amount mismatch: Expected is " + ExpectedCumulativeAmountInGppPdf + " but found " + ActualCumulativeAmountInGppPdf+ " in Gpp Reciept PDF");
			asrt.assertEquals(ActualPaymentModeInGppPdf,GppOpeningAndCollectionMultiplePlansTestData.TC108_PaymentMode,"Payment mode mismatch: Expected is " + GppOpeningAndCollectionMultiplePlansTestData.TC108_PaymentMode + ", but found " + ActualPaymentModeInGppPdf+ " in Gpp Reciept PDF");
			asrt.assertEquals(ActualApplicationIdInGppPdf,ExpectedApplicationIdInGppPdf,"Application Id mismatch: Expected is " + ExpectedApplicationIdInGppPdf + " but found " + ActualApplicationIdInGppPdf +" in Gpp Reciept PDF");
			pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
		}

	// <summary>
	// Test Case Title : Open GS scheme and take collection through converted to advance(SR/OG)
	// Automation ID : TC104
	// Author: Hasna E K
	// </summary>
	public void TC104_GppOpeningAndCollectionMultiplePlans() throws Exception 
	{
		//Step   1.Login to POS
		Thread.sleep(5000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);

		//Step  2.Click on Transaction button and Go to GPP
		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));

		//Step 3.Select New GPP Account
		WebDriverWait Wait =new WebDriverWait(driver, Duration.ofSeconds(20));
		Wait.until(ExpectedConditions.presenceOfElementLocated(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account")));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account"));

		//Step 4.Search customer phone number in search tab
		//Step 5.Click on "Add to GPP ac open"
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC104_PhoneNo, UtilityTestData.MenuBarOptions[6]);

		//Step  6.Select scheme code as "Golden Bliss Wedding Advance plan"
		//Expected : Verify that selecting Scheme Code "GS" sets the installment amount to the default minimum of 1 and is also editable
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"),GppOpeningAndCollectionMultiplePlansTestData.TC104_Scheme);
		Thread.sleep(1000);
		String DefaultGBInstallmentAmount = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		String ExpectedDefaultInstallmentAmount = GppOpeningAndCollectionMultiplePlansTestData.TC104_DefaultInstallmentAmount;
		asrt.assertEquals(DefaultGBInstallmentAmount, ExpectedDefaultInstallmentAmount,"Default GS Installment Amount mismatch: Expected is "+ExpectedDefaultInstallmentAmount+" but got "+DefaultGBInstallmentAmount+" in GPP account opening page");

		//Step 7.Enter Installment amount
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"), GppOpeningAndCollectionMultiplePlansTestData.TC104_InstallmentAmount);
		Thread.sleep(1000);
		double FirstInstallmentAmount= Double.parseDouble(GppOpeningAndCollectionMultiplePlansTestData.TC104_InstallmentAmount);

		//Step 8.Select Sales person
		Thread.sleep(2000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SalesPersonInput"), GppOpeningAndCollectionMultiplePlansTestData.TC104_SalesPerson);

		//Step 9.Select Id proof type 1
		base.selectorByVisibleText(GppOpeningAndCollectionMultiplePlansObj.Sel_IdType1("Id proof type 1"),GppOpeningAndCollectionMultiplePlansTestData.TC104_IdProofType1);

		//Step 10: Enter ID Proof number
		//Note: If customer doesn't have bank account add bank details
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Idproofno1"), GppOpeningAndCollectionMultiplePlansTestData.TC104_IdProofNo1);

		String BankAccountNo = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkacno"));
		if (BankAccountNo == null) {
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Bnkacno"), GppOpeningAndCollectionMultiplePlansTestData.TC104_BankAccountNo);
			Thread.sleep(1000);
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Bnkname"), GppOpeningAndCollectionMultiplePlansTestData.TC104_BankName);
			Thread.sleep(1000);
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Bnkcode"), GppOpeningAndCollectionMultiplePlansTestData.TC100_BankIfscCode);
			Thread.sleep(1000);
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("CustBranch"), GppOpeningAndCollectionMultiplePlansTestData.TC100_BankBranch);	
			Thread.sleep(1000);
		}

		//Step  11.Select Bank proof type as "Passbook"
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("BankProof"),GppOpeningAndCollectionMultiplePlansTestData.TC104_BankProof);

		//Step 12. Enter Nominee name and Select Nominee relation
		base.setData(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"),UtilityTestData.NomineeName);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"),UtilityTestData.NomineeRelation);

		//Step 13. Click on check box(Same as above)
		base.ClickCondition(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("IsSameAsAbove"));

		//Step 14.Click on save
		//Expected Result: Verify Application ID, Scheme Details, Customer Details, Nominee Details, Bank Details
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("View more"));
		Thread.sleep(1000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("SearchView_AppBarView_appBarCommandCommand"));
		String CustomerName=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("CustNmInput"));
		String CustomerPhone=GppOpeningAndCollectionMultiplePlansTestData.TC104_PhoneNo;
		String SchemeDetails=base.getTheSelectedTextInDropDown(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"));		
		String NomineeName=UtilityTestData.NomineeName;
		String BankIfscCode=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Bnkcode"));
		Thread.sleep(3000);
		Map<String, String> PdfDetails = pdfUtils.ExtractDetailsFromGPPPdf(GppOpeningAndCollectionMultiplePlansTestData.TC104_SchemeCode);
		String ApplicationIdPdf   = PdfDetails.get("ApplicationID");
		String SchemePdf          = PdfDetails.get("Plan");
		String CustomerNamePdf    = PdfDetails.get("CustomerName");
		String PhonePdf           = PdfDetails.get("Phone");
		String NomineeNamePdf     = PdfDetails.get("NomineeName");
		String BankAccountNoPdf   = PdfDetails.get("BankAccountNo");
		String IfscCodePdf        = PdfDetails.get("IFSCCode");
		asrt.assertNotNull(ApplicationIdPdf,"Application ID " + ApplicationIdPdf + " not found in Golden Bloom Purchase Advance Plan PDF content.");
		asrt.assertEquals(SchemePdf, SchemeDetails,"Scheme name mismatch: Expected "+SchemeDetails+" but got "+SchemePdf+" in Golden Bloom Purchase Advance Plan Pdf");		
		asrt.assertEquals(CustomerNamePdf, CustomerName,"Customer name Mismatch: Expected "+CustomerName+" but got "+CustomerNamePdf+" in Golden Bloom Purchase Advance Plan Pdf");		
		asrt.assertEquals(PhonePdf, CustomerPhone,"Customer phone number mismatch: Expected "+CustomerPhone+" but got "+PhonePdf+" in Golden Bloom Purchase Advance Plan Pdf");		
		asrt.assertEquals(NomineeNamePdf, NomineeName,"Nominee name mismatch: Expected "+NomineeName+" but got "+NomineeNamePdf+" in Golden Bloom Purchase Advance Plan Pdf");		
		asrt.assertEquals(BankAccountNoPdf, BankAccountNo,"Bank Account number mismatch: Expected "+BankAccountNo+" but got "+BankAccountNoPdf+" in Golden Bloom Purchase Advance Plan Pdf");		
		asrt.assertEquals(IfscCodePdf, BankIfscCode,"Bank IFSC code mismatch: Expected "+BankIfscCode+" but got "+IfscCodePdf+" in Golden Bloom Purchase Advance Plan Pdf");		

		//Step 15.Search the customer
		//Step 16.Click on add to sale button
		appUtils.SearchByCustomerID(GppOpeningAndCollectionMultiplePlansTestData.TC104_CustomerId,UtilityTestData.MenuBarOptions[0]);

		//Fetch current board rate
		List<String> GoldSkus = appUtils.SelectMultipleSKUs
				(GppOpeningAndCollectionMultiplePlansTestData.TC104_SkuCount,
						GppOpeningAndCollectionMultiplePlansTestData.TC104_TransferType,
						GppOpeningAndCollectionMultiplePlansTestData.TC104_FromCounter,
						GppOpeningAndCollectionMultiplePlansTestData.TC104_MetalType);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		appUtils.HamBurgerButtonClick("iconShop");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));		
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Thread.sleep(3000);
		base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), GoldSkus.get(0));
		wait.until(ExpectedConditions.elementToBeClickable(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"))).click();
		base.setZoom(driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.dataListLine")));

		String Purity=  base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("Purity"), "value");
		String BoardRateFromSkuTble = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column4 textRight", "h4 ellipsis cell"));
		Double SkuBoardRate = Double.parseDouble(BoardRateFromSkuTble);
		Thread.sleep(2000);

		appUtils.HamBurgerButtonClick("iconShop");

		double BoardRate22k=0;
		if(Purity.equalsIgnoreCase("22k")){
			BoardRate22k = SkuBoardRate;
		}else if (Purity.equalsIgnoreCase("18k")){
			double ConvertedBoardRate22k = (SkuBoardRate * 22) / 18;
			BoardRate22k = Math.round(ConvertedBoardRate22k);
		}

		//Step 17.Select OG
		//Step 18.Click on OG own
		//Step 19.Select configuration
		//Step 20.Click on Add item button
		//Step 21.Select Exchange
		//Step 22.Select QC person
		//Step 23.Select Smith person
		//Step 24.Enter piece value
		//Step 25.Enter gross weight
		//Step 26.Click on calculate button
		//Expected Result: Check Exchange rate(Board rate),*check calculation
		appUtils.PurchaseOldGold(GppOpeningAndCollectionMultiplePlansTestData.TC104_Configuration, 
				GppOpeningAndCollectionMultiplePlansTestData.TC104_PurchaseOrExchange,
				GppOpeningAndCollectionMultiplePlansTestData.TC104_QCPerson,
				GppOpeningAndCollectionMultiplePlansTestData.TC104_SmithPerson,
				GppOpeningAndCollectionMultiplePlansTestData.TC104_GrossPieces,
				GppOpeningAndCollectionMultiplePlansTestData.TC104_GrossWeight);

		Map<String, Double> Og22kData = mathUtils.ValidateOldGoldItemDetailsData(BoardRate22k, GppOpeningAndCollectionMultiplePlansTestData.TC104_Configuration);
		Double ExpectedOgQty = Og22kData.get("OgQty");
		Double ExpectedOgCvalue = Og22kData.get("OgCvalue");
		String OGItemName = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String OgNameInItemDetailsPge = OGItemName.substring(OGItemName.indexOf("-") + 2);
		String OGTotalWeightInItemDetailPage=base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"));
		String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt"));
		double OGTotalAmount = Double.parseDouble(TotalAmountInItemDetailsPge);

		//Step 27.Click on Add to cart button
		Thread.sleep(1000);
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("bottomLeft grow pad12", "1"));

		// Note:Cashier Screen- *No of Product lines, *Displayed Item Name,*Displayed Quantity,*Displayed TOTAL (without Tax),*Displayed Subtotal,*TAX,*Total Amount
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));

		//Lines Count Validation
		String LinesCountBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("LinesField", "h4"));
		int ExpectedLinesCount = Integer.parseInt(LinesCountBillingPge.replaceAll("[^\\d]", ""));

		Assert.assertEquals(AllProductRows.size(), ExpectedLinesCount,
				"Mismatch in SKU Linescount. Actual:" + AllProductRows.size() + ", Expected:" + ExpectedLinesCount+" in the billing screen");

		//Item Name Validation
		String OgItemNameInBillingPge = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		asrt.assertEquals(OgItemNameInBillingPge,OgNameInItemDetailsPge, "Item name mismatch: Expected "+OgNameInItemDetailsPge+" but found "+OgItemNameInBillingPge+" in billing screen");

		//GrossWeight Validation
		String OgGrossWeightInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5")).replaceAll("[^\\d.]", "").trim();
		double ActualGrossWeight = Double.parseDouble(OgGrossWeightInBillingPge);
		asrt.assertEquals(ActualGrossWeight,ExpectedOgQty,1,"Quantity mismatch:Expected "+ExpectedOgQty+" but found "+ActualGrossWeight+" in billing screen");

		//TotalWithoutTax Validation
		String ActualTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replaceAll("[^\\d.]", "").trim();
		double ActualTotalWithoutTax = Double.parseDouble(ActualTotalWithoutTaxInBillingPge);		
		asrt.assertEquals(OGTotalAmount, ActualTotalWithoutTax,"Total without tax mismatch: Expected "+OGTotalAmount+" but found "+ActualTotalWithoutTax+" in billing screen");

		//SubTotal Validation
		String ExpectedSubTotalInBillingPge = String.valueOf(ActualTotalWithoutTax);
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4")).replaceAll("[^\\d.]", "").trim();
		String ActualSubTotal = String.valueOf(Double.parseDouble(ActualSubTotalInBillingPge));	
		asrt.assertEquals(ActualSubTotal,ExpectedSubTotalInBillingPge, "Subtotal mismatch: Expected "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotal+" in billing screen");

		//Tax Validation
		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4")).replaceAll("[^\\d.]", "").trim();
		String ExpectedTaxInBillingPge = GppOpeningAndCollectionMultiplePlansTestData.TC104_OGTax;
		asrt.assertEquals(ActualTaxInBillingPge,ExpectedTaxInBillingPge, "OG Tax amount mismatch: Expected "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing screen");

		//Total Amount Validation
		String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;	
		String ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4")).replaceAll("[^\\d.]", "").trim();
		String ActualTotalAmount = String.valueOf(Double.parseDouble(ActualTotalAmountInBillingPge));	
		asrt.assertEquals(ActualTotalAmount,ExpectedTotalAmountInBillingPge, "Total Amount mismatch: Expected "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmount+" in billing screen");

		//Step	 28.Click on the Amount
		String ReceiveAmtInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replaceAll("[^\\d.]", "").trim();
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 29.Select convert to advance
		//Step 30.1.Select type as GPP
		//Step 30.2.Select scheme id(GS)    
		//Step 30.3.Select sales agent
		//Step 30.4.Click on deposit button
		String PaymentModeBilling = base.GetText(LoginPageObj.Edt_AlertText(GppOpeningAndCollectionMultiplePlansTestData.TC103_PaymentMode));
		base.buttonClick(LoginPageObj.Edt_AlertText(GppOpeningAndCollectionMultiplePlansTestData.TC104_PaymentMode));	
		Thread.sleep(4000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("Ext col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("Ext col grow"),GppOpeningAndCollectionMultiplePlansTestData.TC103_SchemeType);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"),ApplicationIdPdf);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"),UtilityTestData.SalePersonName);		 
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Step 31.Post the Invoice
		//Expected Result: Verify *Application ID,*Being advance against ,*Customer Details,*Board rate,Purity, Amount,*Payment Mode		
		//Click on balance amount
		//Click on payment method as cash
		String BalanceAmtInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replaceAll("[^\\d.-]", "").trim();
		String BalanceAmountValue = BalanceAmtInBillingPge.replace("-", "").trim();
		double BalanceAmount = Double.parseDouble(BalanceAmountValue);
		String ItemPurityValue=GppOpeningAndCollectionMultiplePlansTestData.TC104_Configuration;
		if(BalanceAmount>0)
		{
			Thread.sleep(2000);
			//base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.buttonClick(LoginPageObj.Edt_AlertText(GppOpeningAndCollectionMultiplePlansTestData.TC104_PaymentMode2));	
			Thread.sleep(4000);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("cancelButton primaryButton"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "GPP ADV. INVOICE"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("btnblue"));

			Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
			double CumulativeAmountGppPdf = Double.parseDouble(PaymentDetails.get("CumAmt"));			
			String ApplicationIDGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();
			String AdvanceAgainstGppPdf = PaymentDetails.getOrDefault("AdvanceAgainst", "").trim();			
			String CustomerNameGppPdf = PaymentDetails.getOrDefault("CustomerName", "").trim();
			String PhoneGppPdf = PaymentDetails.getOrDefault("Phone", "").trim();
			String BoardRateGppPdf = PaymentDetails.getOrDefault("BoardRate", "").trim();
			String PurityGppPdf = PaymentDetails.getOrDefault("Purity", "").trim();
			String PaymentModeGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
			String CustomerPhoneGppPdf=PaymentDetails.getOrDefault("Phone", "").trim();
			String SchemeName = SchemeDetails.contains("::") ? SchemeDetails.split("::", 2)[1].trim() : SchemeDetails;

			asrt.assertEquals(CustomerPhoneGppPdf,CustomerPhone,"Mismatch in Customer Mobile Number: expected " + CustomerPhone + ", but found " + CustomerPhoneGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(ApplicationIdPdf,ApplicationIDGppPdf,"Mismatch in Application Id: expected " + ApplicationIdPdf + ", but found " + ApplicationIDGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(CumulativeAmountGppPdf,FirstInstallmentAmount, "Mismatch in Cummilative Amount: expected " + FirstInstallmentAmount + ", but found " + CumulativeAmountGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals(AdvanceAgainstGppPdf,SchemeName,"Being Advance against value Mismatch: expected " + SchemeName + ", but found " + AdvanceAgainstGppPdf + "in the Gpp Reciept PDF");		
			asrt.assertEquals(CustomerNameGppPdf,CustomerName,"Mismatch in customer name: expected " + CustomerName + ", but found " + CustomerNameGppPdf+ "in the Gpp Reciept PDF");	
			asrt.assertEquals( Double.parseDouble(BoardRateGppPdf), BoardRate22k,"Mismatch in Board rate: expected " + BoardRate22k + ", but found " + BoardRateGppPdf+ "in the Gpp Reciept PDF"); 
			asrt.assertEquals(PurityGppPdf,ItemPurityValue,"Mismatch in Purity : expected " + ItemPurityValue + ", but found " + PurityGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(PaymentModeGppPdf,PaymentModeBilling,"Mismatch in payment mode: expected " + PaymentModeBilling + ", but found " + PaymentModeGppPdf+ "in the Gpp Reciept PDF");	
		}
		else if(BalanceAmount == 0.00 )
		{
			Map<String, String> PaymentDetails = pdfUtils.ExtractPaymentDetailsFromPdf();
			double CumulativeAmountGppPdf = Double.parseDouble(PaymentDetails.get("CumAmt"));			
			String ApplicationIDGppPdf = PaymentDetails.getOrDefault("ApplicationID", "").trim();
			String AdvanceAgainstGppPdf = PaymentDetails.getOrDefault("AdvanceAgainst", "").trim();
			String CustomerNameGppPdf = PaymentDetails.getOrDefault("CustomerName", "").trim();
			String PhoneGppPdf = PaymentDetails.getOrDefault("Phone", "").trim();
			String BoardRateGppPdf = PaymentDetails.getOrDefault("BoardRate", "").trim();
			String PurityGppPdf = PaymentDetails.getOrDefault("Purity", "").trim();
			String PaymentModeGppPdf = PaymentDetails.getOrDefault("PaymentMode", "").trim();
			String CustomerPhoneGppPdf=PaymentDetails.getOrDefault("Phone", "").trim();
			String SchemeName = SchemeDetails.contains("::") ? SchemeDetails.split("::", 2)[1].trim() : SchemeDetails;

			asrt.assertEquals(CustomerPhoneGppPdf,CustomerPhone,"Mismatch in Customer Mobile Number: expected " + CustomerPhone + ", but found " + CustomerPhoneGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(ApplicationIdPdf,ApplicationIDGppPdf,"Mismatch in Application Id: expected " + ApplicationIdPdf + ", but found " + ApplicationIDGppPdf + "in the Gpp Reciept PDF");
			asrt.assertEquals(PaymentModeGppPdf,PaymentModeBilling,"Mismatch in payment mode: expected " + PaymentModeBilling + ", but found " + PaymentModeGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals(CumulativeAmountGppPdf,FirstInstallmentAmount, "Mismatch in Cummilative Amount: expected " + FirstInstallmentAmount + ", but found " + CumulativeAmountGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals(AdvanceAgainstGppPdf,SchemeName,"Being Advance against value Mismatch: expected " + SchemeName + ", but found " + AdvanceAgainstGppPdf + "in the Gpp Reciept PDF");		
			asrt.assertEquals(CustomerNameGppPdf,CustomerName,"Mismatch in customer name: expected " + CustomerName + ", but found " + CustomerNameGppPdf+ "in the Gpp Reciept PDF");
			asrt.assertEquals( Double.parseDouble(BoardRateGppPdf), BoardRate22k,"Mismatch in Board rate: expected " + BoardRate22k + ", but found " + BoardRateGppPdf+ "in the Gpp Reciept PDF"); 
			asrt.assertEquals(PurityGppPdf,ItemPurityValue,"Mismatch in Purity : expected " + ItemPurityValue + ", but found " + PurityGppPdf + "in the Gpp Reciept PDF");	
		}
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
}