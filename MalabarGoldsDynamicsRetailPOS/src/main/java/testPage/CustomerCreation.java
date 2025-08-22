
package testPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.CustomerCreation_Obj;
import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import testData.CommonData;
import testData.CustomerCreation_TestData;
import testData.POC_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.BasePge;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;

public class CustomerCreation extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	MathUtilities mathUtils;
	PdfUtilities pdfUtils;


	Login login = new Login(driver); 
	POC_TestData poctestdata = new POC_TestData();
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	CustomerCreation_Obj CustomerCreationObj=new CustomerCreation_Obj();
	CustomerCreation_TestData CustomerCreationTestData = new CustomerCreation_TestData();
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();

	public CustomerCreation(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		mathUtils = new MathUtilities(base);
		pdfUtils = new PdfUtilities(base);
	}


	//<summary>
	// Test Case Title : Creating retail customer with existing mobile number
	// Automation ID : TC129
	// Author : Aiswarya
	// </summary>
	public void TC129_CustomerCreation() throws Exception
	{
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		Wait.until(ExpectedConditions.elementToBeClickable(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable")));		
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		//Step 2: Click on the Transaction Button
		//Step 3: Click on add customer
		//Step 4:Click on create new customer
		Thread.sleep(3000);
		base.buttonClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));
		Thread.sleep(3000);
		base.buttonClick(LoginPageObj.Btn_Login("Create new customer"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"),CustomerCreation_TestData.TC129_FirstName.toUpperCase());
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"),CustomerCreation_TestData.TC129_LastName.toUpperCase());
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));

		//Step 5: Enter Exsiting customer Mobile Number
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),UtilityTestData.TC129_PhoneNumber); // Mobile Number:The phone number being added is already associated with an existing Retail customer
		System.out.print("TC129_PhoneNumber");
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"),CustomerCreation_TestData.TC129_CustomerLanguageOptions);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreation_TestData.TC129_RegionCode);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreation_TestData.TC129_PreferredLanguage);
		Thread.sleep(3000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));

		//Step 6: Click save button and check if validation appears saying mobile number already exists.
		//Expected: Should not be able to create new customer
		asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks")),"Validation saying 'Mobile number already exists' is not displayed ");

	}



	// <summary>
	// Test Case Title : Check the Validations (Validating the error messages for the following scenarios when the field is Empty: First Name, Last Name, Religion Code, Language Code, Preferred Language and Mobile Number )
	// Automation ID : TC_131
	//Author: Vishnu RR 
	// </summary>
	public void TC131_CustomerCreation() throws Exception
	{
		// Step 1: Login to POS
		// Expected Result: User should be Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		// Step 2: Click on Transaction button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		//step 3 - Click on add customer
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));

		//step 4 - Click on create customer
		Thread.sleep(3000);
		base.excuteJsClick(LoginPageObj.Btn_Login("Create new customer"));

		// Step 5.Add all customer details and check for the validation for each field.
		//Expected Results :Verify validations
		//5.1 First name:Empty First name, not allowed special characters
		//5.2 Last name: Empty Last name, not allowed special characters
		//5.3 Empty relegion
		//5.4 Empty language
		//5.5 Empty preffered language
		//5.6 Empty phone number
		//6.Click save button
		//Verifying the  validations on the missing fields

		//Validating the error message displayed When the FirstName Field is Empty.
		//LastName
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"),CustomerCreation_TestData.TC131_CustomerLastName );
		//Language
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"),CustomerCreation_TestData.TC131_CustomerLanguage);
		//Mobile No
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),CustomerCreation_TestData.TC131_CustomerContact );
		//Religion Code
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreation_TestData.TC131_ReligionCode);
		//PreferredLanguage
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreation_TestData.TC131_PreferredLanguage);		
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		Thread.sleep(3000);

		String ActualErrorMessageForMissingFirstName  = base.GetText(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks"));
		System.out.println(ActualErrorMessageForMissingFirstName);
		asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks")),"Validation saying 'First Name and Last Name are Mandatory' is not displayed correctly: " +ActualErrorMessageForMissingFirstName +"in the Customer Creation Page");

		Thread.sleep(2000);
		base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));

		//Validating the error message displayed for the FirstName with Special Character
		//First Name With Special Character
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"),CustomerCreation_TestData.TC131_CustomerFirstName+appUtils.getRandomProducts(CustomerCreationTestData.TC131_SpecialCharacters, 1).toString().replaceAll("[\\[\\]]", ""));
		System.out.print(CustomerCreation_TestData.TC131_CustomerFirstName+appUtils.getRandomProducts(CustomerCreationTestData.TC131_SpecialCharacters, 1).toString());
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		Thread.sleep(3000);
		String ActualErrorMessageForSplCharacterFirstName  = base.GetText(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks"));
		System.out.println(ActualErrorMessageForSplCharacterFirstName);
		asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks")),"Special characters are not allowed in First Name' is not displayed correctly: " +ActualErrorMessageForSplCharacterFirstName +"in the Customer Creation Page");

		base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));		


		//Validating the Error Message displayed when the LastName is Empty
		//LastName Clearing
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"));
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));
		//FirstName
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"),CustomerCreation_TestData.TC131_CustomerFirstName );
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		Thread.sleep(3000);
		String ActualErrorMessageForMissingLastName  = base.GetText(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks"));
		System.out.println(ActualErrorMessageForMissingLastName);
		asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks")),"Special characters are not allowed in Last Name' is not displayed correctly: " +ActualErrorMessageForMissingLastName +"in the Customer Creation Page");

		base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));

		//Last Name With Special Character
		//LastName
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"),CustomerCreation_TestData.TC131_CustomerLastName+appUtils.getRandomProducts(CustomerCreationTestData.TC131_SpecialCharacters, 1).toString().replaceAll("[\\[\\]]", ""));
		System.out.print(CustomerCreation_TestData.TC131_CustomerLastName+appUtils.getRandomProducts(CustomerCreationTestData.TC131_SpecialCharacters, 1).toString());
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		Thread.sleep(3000);
		String ActualErrorMessageForSplCharacterLastName  = base.GetText(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks"));
		System.out.println(ActualErrorMessageForSplCharacterLastName);
		asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks")),"Special characters are not allowed in Last Name' is not displayed correctly: " +ActualErrorMessageForSplCharacterLastName +"in the Customer Creation Page");

		base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));

		//Validating the Error Message displayed when the Religion code is Empty
		//Clearing the First&Last Name
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"));
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"),CustomerCreation_TestData.TC131_CustomerFirstName);//FirstName & LastName
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"),CustomerCreation_TestData.TC131_CustomerLastName);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreation_TestData.TC131_Selectoption);		//Religion Code	
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		Thread.sleep(3000);
		String ActualErrorMessageForBlankReligionCode  = base.GetText(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks"));
		System.out.println(ActualErrorMessageForBlankReligionCode);
		asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks")),"Invalid religion code' is not displayed correctly: " +ActualErrorMessageForBlankReligionCode +"in the Customer Creation Page");

		base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));


		//Validating the Error Message displayed when the Preferred Language is Empty
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreation_TestData.TC131_ReligionCode); //Religion Code
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreation_TestData.TC131_Selectoption); //PreferredLanguage
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		Thread.sleep(3000);
		String ActualErrorMessageForBlankPreferredLanguage  = base.GetText(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks"));
		System.out.println(ActualErrorMessageForBlankPreferredLanguage);
		asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks")),"Invalid language code' is not displayed correctly: " +ActualErrorMessageForBlankPreferredLanguage +"in the Customer Creation Page");

		base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));

		//Validating the Error Message displayed when the Mobile Number is Empty
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreation_TestData.TC131_PreferredLanguage);
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		Thread.sleep(3000);
		String ActualErrorMessageForBlankMobileNumber  = base.GetText(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks"));
		System.out.println(ActualErrorMessageForBlankMobileNumber);
		asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks")),"This mobile number already exists with another customer!' is not displayed correctly: " +ActualErrorMessageForBlankMobileNumber +"in the Customer Creation Page");

		base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));

		//Validating the Save Button is enabled when not selecting the Language option
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),CustomerCreation_TestData.TC131_CustomerContact );
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"),CustomerCreation_TestData.TC131_SelectLanguageOption); //Language
		Thread.sleep(5000);
		Assert.assertFalse(base.isEnabledBy(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails")), "The save button is enabled in the customer creation page");

	}


	// <summary>
	// Test Case Title : Creating organization customer with existing mobile number
	// Automation ID : TC138
	//Author: Nandagopan
	// </summary>

	public void TC138_CustomerCreation() throws Exception{


		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		Thread.sleep(5000);

		//Step 2: Click on the Transaction Button
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		//Step 3: Click on add customer
		base.buttonClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));
		Thread.sleep(3000);

		//Step 4:Click on create new customer
		base.buttonClick(LoginPageObj.Btn_Login("Create new customer"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput")));
		Thread.sleep(2000);

		//Step 5:Click on Customer Type as Organisation
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput"),CustomerCreationTestData.TC138_CustomerType);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("companyNameInput"),CustomerCreationTestData.TC138_CustomerCompanyNameOG);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),UtilityTestData.TC138_PhoneNumber); // The phone number being added is already associated with an existing Organization customer.
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"),CustomerCreationTestData.TC138_CustomerReceiptlanguage);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreationTestData.TC138_CustomerReligion);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreationTestData.TC138_CustomerPrefLanguage);

		//Click on Save button
		base.scrollToElement(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));

		//Step 6: Click save button and check if validation appears saying mobile number already exists.
		//Expected: Should not be able to create new customer
		asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks")),"Validation saying 'Mobile number already exists' is not displayed ");
		base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));


	}

	//<summary>
	// Test Case Title : Edit address of an existing retail customer
	// Automation ID : TC132
	// Author : Aiswarya
	// </summary>
	public void TC132_CustomerCreation() throws Exception
	{
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(50));

		//Precondition:Customer Creation
		Thread.sleep(3000);
		base.buttonClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));
		Thread.sleep(3000);
		base.buttonClick(LoginPageObj.Btn_Login("Create new customer"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"),CustomerCreation_TestData.TC132_FirstName.toUpperCase());
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"),CustomerCreation_TestData.TC132_LastName.toUpperCase() );
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),CustomerCreation_TestData.TC132_PhoneNumber);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"),CustomerCreation_TestData.TC132_CustomerLanguageOptions);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreation_TestData.TC132_RegionCode);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreation_TestData.TC132_PreferredLanguage);
		base.scrollToElement(LoginPageObj.Edt_AlertText("Add an address"));
		base.excuteJsClick(LoginPageObj.Edt_AlertText("Add an address"));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"));
		List<String> CustomerZipCode = appUtils.getRandomProducts(CustomerCreation_TestData.TC132_ZipCode,1);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"),CustomerZipCode.get(0));
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"), "Enter");
		if(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Address")))
		{
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("win-item col grow", "1"));
		}
		else {
			base.ClickCondition(LoginPageObj.Btn_SingnIn("cmdSaveCustomerAddress"));
		}
		Thread.sleep(3000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		String CustomerFullName = (CustomerCreation_TestData.TC132_FirstName + " " + CustomerCreation_TestData.TC132_LastName).toUpperCase();
		Thread.sleep(3000);
		String CustomerAddress= base.GetText(LoginPageObj.Ele_ErrorMessage("primaryFontColor"));

		//Get newly created customer ID
		Wait.until(ExpectedConditions.visibilityOfElementLocated(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount")));
		base.scrollToElement(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount"));
		Thread.sleep(3000); 
		String CustomerId = base.GetText(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount")).trim();    
		System.out.println("Customer ID: " + CustomerId);

		//Step 2:Click on Transaction button
		Thread.sleep(2000);
		appUtils.HamBurgerButtonClick("iconShop");

		// Step 3:Select customer
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"), CustomerId);
		Thread.sleep(3000); 
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Customers"));
		base.Hover(CustomerCreationObj.Ele_Customer(CustomerId,"dataListLine"));
		base.buttonClick(CustomerCreationObj.Ele_Customer(CustomerId,"dataListLine"));

		// Step 4: Click on the "Edit" button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions(Utility_TestData.MenuBarOptionsCustomerProfile[3]));
		Thread.sleep(3000); 

		// Step 5: Edit the Existing address
		base.scrollToElement(CustomerCreationObj.Ele_PrimaryCustomAddr(CustomerFullName));
		base.excuteJsClick(CustomerCreationObj.Ele_PrimaryCustomAddr(CustomerFullName));
		Thread.sleep(3000);
		base.excuteJsClick(LoginPageObj.Btn_SignInButton("Yes"));
		base.setData(CustomerCreationObj.Ele_StreetName("streetNameTabRead"), BasePge.CreateRandomWithoutNumbers(10));
		Thread.sleep(1000);
		List<String> ExpectedZipCode = appUtils.getRandomProducts(CustomerCreation_TestData.TC132_ZipCode,1);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"),ExpectedZipCode.get(0));
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"), "Enter");
		Thread.sleep(1000);
		if(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Address")))
		{
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("win-item col grow", "1"));
			Thread.sleep(1000);
			try{base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerAddress"));}catch(Exception e){System.out.println();}
		}
		else {
			Thread.sleep(1000);	
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerAddress"));
		}
		//Step 6:Save Edited Address
		Thread.sleep(2000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		Thread.sleep(2000);
		String UpdatedAddress= base.GetText(LoginPageObj.Ele_ErrorMessage("primaryFontColor"));
		//validation
		asrt.assertNotEquals(CustomerAddress, UpdatedAddress,"The address of the existing customer : "+CustomerAddress+" is equal to edited customer address Address "+UpdatedAddress+"");

		//Step 6.1:Search customer id
		//Expected:Verify Details
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		Thread.sleep(1000); 
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"), CustomerId);
		Thread.sleep(3000); 
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Customers"));
		base.Hover(CustomerCreationObj.Ele_Customer(CustomerId,"dataListLine"));
		base.buttonClick(CustomerCreationObj.Ele_Customer(CustomerId,"dataListLine"));
		//Validation
		String ActualAddress= base.GetText(LoginPageObj.Ele_ErrorMessage("primaryFontColor"));
		asrt.assertEquals(UpdatedAddress, ActualAddress," Customer address Mismatch Expected : "+UpdatedAddress+" but got :"+ActualAddress+"in POS Customer Summary page");

		//Step 6.2:Search All Customers in ERP
		base.newWindow(1);
		driver.get(UtilityTestData.ERPURL);
		Thread.sleep(2000);	
		driver.navigate().refresh();
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), CustomerCreation_TestData.CustomerSearch);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"));
		Thread.sleep(6000);	

		//Step 6.3:Filter Customerid 
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), CustomerId);
		Thread.sleep(10000);
		base.buttonClick(CustomerCreationObj.Ele_ERPAccField("custtablelistpage_2_QuickFilterControl_listbox", "Account"));
		Thread.sleep(5000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), "ENTER");
		Thread.sleep(3000);

		//Step 6.4:Open Account
		//Expected:Verify Edited Customer Details
		base.actionClick(CustomerCreationObj.Ele_ERPCustid("Account"));
		base.setZoom(driver, 50);
		base.scrollToElement(CustomerCreationObj.Ele_ERPCustomerAgeGroupCode("Preferred language"));
		String ERPAddress=base.GetText(CustomerCreationObj.Ele_ERPaddressField("Address"));

		asrt.assertEquals(ActualAddress, ERPAddress,"The address from POS Customer Summary page "+ActualAddress+" is not equal the address from ERP Customer Details page: "+ERPAddress+"");
	}


	// <summary>
	// Test Case Title : Customer Creation
	// Automation ID : TC130
	//Author: Vishnu RR 
	// </summary>

	public void TC130_CustomerCreation() throws Exception

	{
		// Step 1: Login to POS
		// Expected Result: User should be Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);

		// Step 2: Click on Transaction button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		//step 3 - Click on add customer
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));

		//step 4 - Click on create customer
		Thread.sleep(3000);
		base.excuteJsClick(LoginPageObj.Btn_Login("Create new customer"));

		//5.Add all customer details
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput"),CustomerCreation_TestData.TC130_CustomerType);
		String CustomerActualType = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTitleOptions"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTitleOptions"),CustomerCreation_TestData.TC130_customerTitleOptions);
		String CustomerActualTitle =  base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTitleOptions"));

		// Customer - FirstName - Last - Contact details 
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"),CustomerCreation_TestData.TC130_CustomerFirstName );
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"),CustomerCreation_TestData.TC130_CustomerLastName );
		String CustomerActualFirstname = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"));
		String CustomerActualLastname = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));
		String ActualCustomerFullName = ( CustomerActualFirstname + " " +  CustomerActualLastname );

		//Email Address 
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerContactEmailInput"),CustomerCreation_TestData.TC130_Email );
		String CustomerActualEmail = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerContactEmailInput"));

		//Contact Number 
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),CustomerCreation_TestData.TC130_CustomerContact);
		String ContactActualNo = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));

		//Preference
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerReceiptPreferenceOptions"),CustomerCreation_TestData.TC130_customerReceiptPreferenceOptions);
		String ActualReceiptPreference = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerReceiptPreferenceOptions"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerCurrencyOptions"),CustomerCreation_TestData.TC130_customerCurrencyOptions);
		String ActualCurrencyoption = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerReceiptPreferenceOptions"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"),CustomerCreation_TestData.customerLanguageOptions);
		String ActualCustomerLanguage = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		String ActualCustomerLanguageText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));  

		// Preferred Language, Religion Code, Preference Language 
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("CustomerAgeGroupCode"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("CustomerAgeGroupCode"),CustomerCreation_TestData.TC130_CustomerAgeGroupCode);
		String ActualActCustomerAge = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("CustomerAgeGroupCode"));
		String ActualCustomerageText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("CustomerAgeGroupCode"));  

		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("CasteCode"),CustomerCreation_TestData.TC130_CasteCode);
		String ActualActCasteCode = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("CasteCode"));
		String ActualCustomerCasteCodeText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("CasteCode"));  

		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("ProfessionCode"),CustomerCreation_TestData.TC130_ProfessionCode);
		String AddActProfessionCode = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("ProfessionCode"));
		String ActualCustomerProfessionCode = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("ProfessionCode"));  

		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreation_TestData.TC130_RegionCode);
		String AddActRegionCode = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"));
		String ActualCustomerReligionCodeText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"));  

		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage")); 
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreation_TestData.TC130_PreferredLanguage);
		String AddActPreferredLanguage = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"));
		String ActualCustomerPreferredLanguageText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage")); 

		// DOB
		base.scrollToElement(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-date win-dropdown win-order0 displayOrder1"));
		base.selectorByVisibleText(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-date win-dropdown win-order0 displayOrder1"),CustomerCreationTestData.TC130_DobDate);
		String CustomerActDateofbirth = base.GetValue(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-date win-dropdown win-order0 displayOrder1"));

		base.selectorByVisibleText(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-month win-dropdown win-order1 displayOrder2"),CustomerCreationTestData.TC130_DobMonth);
		int CustomerActMonthofbirth = Integer.parseInt(base.GetValue(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-month win-dropdown win-order1 displayOrder2")));
		String CustomerActMonthofbirthText = base.getTheSelectedTextInDropDown(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-month win-dropdown win-order1 displayOrder2"));

		base.selectorByVisibleText(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-year win-dropdown win-order2 displayOrder3"),CustomerCreationTestData.TC130_DobYear);
		String CustomerActyearofbirth = base.GetValue(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-year win-dropdown win-order2 displayOrder3"));

		String FullDOB = CustomerActMonthofbirth + 1 +  "/" + CustomerActDateofbirth + "/" + CustomerActyearofbirth;


		// Date of Anniversary 
		base.selectorByVisibleText(CustomerCreationObj.Ele_Dob("Date of anniversary", "win-datepicker-date win-dropdown win-order0 displayOrder1"),CustomerCreationTestData.TC130_AnDate);
		String CustomerActDateOfAnniversary = base.GetValue(CustomerCreationObj.Ele_Dob("Date of anniversary", "win-datepicker-date win-dropdown win-order0 displayOrder1"));

		base.selectorByVisibleText(CustomerCreationObj.Ele_Dob("Date of anniversary", "win-datepicker-month win-dropdown win-order1 displayOrder2"),CustomerCreationTestData.TC130_AnMonth);
		int CustomerActMonthOfAnniversary = Integer.parseInt(base.GetValue(CustomerCreationObj.Ele_Dob("Date of anniversary", "win-datepicker-month win-dropdown win-order1 displayOrder2")));
		String CustomerActMonthofanniversaryText = base.getTheSelectedTextInDropDown(CustomerCreationObj.Ele_Dob("Date of anniversary", "win-datepicker-month win-dropdown win-order1 displayOrder2"));

		base.selectorByVisibleText(CustomerCreationObj.Ele_Dob("Date of anniversary", "win-datepicker-year win-dropdown win-order2 displayOrder3"),CustomerCreationTestData.TC130_AnYear);
		String CustomerActYearOfAnniversary = base.GetValue(CustomerCreationObj.Ele_Dob("Date of anniversary", "win-datepicker-year win-dropdown win-order2 displayOrder3"));

		String FullAnniversaryDate = CustomerActMonthOfAnniversary + 1 + "/" + CustomerActDateOfAnniversary + "/" + CustomerActYearOfAnniversary;


		//Pan Details
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("IDProofType"),CustomerCreation_TestData.TC130_IDProofType );
		String CustomerActIDProofType = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("IDProofType"));
		String CustomerActIDProofTypeText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("IDProofType")); 
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("IDProofNumber"),CustomerCreation_TestData.TC130_PanNumber);
		String CustomerActIDProofNumber= base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("IDProofNumber"));

		//PREFERRED MOBILE NUMBER 
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("ReferredBy"),CustomerCreation_TestData.TC130_ReferredBy );
		String CustomerActReferredText= base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("ReferredBy")); 

		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("MobileNumberOfReferredBy"),CustomerCreation_TestData.TC130_Preferrednumber );
		String CustomerActMobileNumberOfReferredBy= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("MobileNumberOfReferredBy"));

		//Actual AddressProof Type - 
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("AddressProofType"),CustomerCreation_TestData.TC130_AddressProofType );
		String ActAddressProof= base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("AddressProofType"));
		String ActAddressProofTypeText= base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("AddressProofType")); 

		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("AddressProofNumber"),CustomerCreation_TestData.TC130_AadharNo );
		String ActAddressProofNumber= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("AddressProofNumber"));

		//Bank Details 
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("BankCode"));
		Thread.sleep(2000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("BankCode"),CustomerCreation_TestData.TC130_BankName1);
		String CustomerActBankCodeText= base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("BankCode"));

		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("BankAccNo"),CustomerCreation_TestData.TC130_BankAccNo );
		String CustomerActNobankAct= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("BankAccNo"));

		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("IFSCCode"),CustomerCreation_TestData.TC130_IFSCCode );
		String CustomerActIFSCCode= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("IFSCCode"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("Branch"),CustomerCreation_TestData.TC130_BranchName );
		String CustomerActBranchName= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("Branch"));

		// Click Add an address
		base.scrollToElement(LoginPageObj.Edt_AlertText("Add an address"));
		base.excuteJsClick(LoginPageObj.Edt_AlertText("Add an address"));
		Thread.sleep(1000);

		// Enter Zip code
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"),CustomerCreation_TestData.TC130_ZipCode );
		driver.findElement(By.id("zipTabRead")).sendKeys(Keys.ENTER);

		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Address());	
		String AddCustomerActCountryText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("countryRegionTabRead"));
		String AddCustomerActStreet = base.GetValue(CustomerCreationObj.Ele_Field("streetNameTabRead"));
		String AddCustomerActCity = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("cityTabRead"));
		String AddCustomerActDistrict = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("districtTabRead"));
		String AddCustomerActState = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("stateTabRead"));
		String AddCustomerActCode = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"));

		Thread.sleep(1000);
		base.scrollToElement(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		String FullAddressCustomer = base.GetText(CustomerCreationObj.Btn_AddtoCart("primaryFontColor")); //Full Address

		//6. Click the Save Button 
		// Expected Condition : Verify whether the details given is reflected in ERP & POS

		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));

		//Verifying the Details on the Edited Page 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement CustomerIdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount")));
		base.scrollToElement(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount"));
		Thread.sleep(3000);
		String NewCustomerId = CustomerIdElement.getText().trim();    
		System.out.println("Customer ID: " + NewCustomerId);
		//Click on Transaction button
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		//Select customer
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"), NewCustomerId);
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Customers"));
		base.Hover(CustomerCreationObj.Ele_Customer(NewCustomerId));
		base.buttonClick(CustomerCreationObj.Ele_Customer(NewCustomerId));
		// Click on the "Edit" button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions(Utility_TestData.MenuBarOptionsCustomerProfile[3]));
		Thread.sleep(3000);
		// Verifying the details in POS
		String AddCustomerExpType = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput")); // type
		String AddCustomerExpTitle = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTitleOptions")); // Title
		String CustomerExpFirstname = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput")); // FirstName
		String CustomerExpLastname = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));   // LastName 

		// Email 
		String CustomeEmailExp = base.GetText(CustomerCreationObj.Ele_PrimaryEmail("Primary email"));
		// Contact Number 
		String CustomeContactExp = base.GetText(CustomerCreationObj.Ele_PrimaryEmail("Primary phone number"));
		String AddExpCustomerAge = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("CustomerAgeGroupCode"));  // Customer Age 
		String AddExpCasteCode = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("CasteCode"));  // Cast Code 
		String AddExpProfessionCode = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("ProfessionCode")); // ProfessionCode
		String AddExpRegionCode = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode")); // RegionCode
		String AddExpPreferredLanguage = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage")); //PreferredLanguage
		String ExpectedCustomerLanguageText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));  
		String CustomerExpDateofbirth = base.GetValue(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-date win-dropdown win-order0 displayOrder1"));//Date of Birth 
		String CustomerExpMonthofbirth = base.getSelectedDropdownValue(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-month win-dropdown win-order1 displayOrder2")); //Month of Birth 
		String CustomerExpyearofbirth = base.GetValue(CustomerCreationObj.Ele_Dob("Date of birth", "win-datepicker-year win-dropdown win-order2 displayOrder3")); //Year of Birth 
		String CustomerExpDateofanniversary = base.GetValue(CustomerCreationObj.Ele_Dob("Date of anniversary", "win-datepicker-date win-dropdown win-order0 displayOrder1")); //Date of anniversary 
		String CustomerExpMonthofanniversary = base.getSelectedDropdownValue(CustomerCreationObj.Ele_Dob("Date of anniversary", "win-datepicker-month win-dropdown win-order1 displayOrder2")); //Month of anniversary
		String CustomerExpYearofanniversary = base.GetValue(CustomerCreationObj.Ele_Dob("Date of anniversary", "win-datepicker-year win-dropdown win-order2 displayOrder3")); //year of anniversary 
		String CustomerExpIDProofType = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("IDProofType")); // pan IDProofType
		String CustomerExpIDProofNumber= base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("IDProofNumber")); // pan IDProofNumber
		String CustomerExpReferredBy= base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("ReferredBy")); //Referred by Mobile Type
		String CustomerExpMobileNumberOfReferredBy= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("MobileNumberOfReferredBy")); // Referred Mobile No  
		String ExpAddressProof= base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("AddressProofType")); // AddressProofType
		String ExpAddressProofNumber= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("AddressProofNumber")); // AddressProofNumber
		String CustomerExpbankselection= base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("BankCode"));; //Bank Name 
		String CustomerActNobankExp= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("BankAccNo")); // Bank Account 
		String CustomerExpIFSCCode= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("IFSCCode"));// Bank IFSC code 
		Thread.sleep(1000);
		String CustomerExpBranchName= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("Branch")); // Bank Branch Name 


		Assert.assertEquals(CustomerActualType, AddCustomerExpType,"Mismatch in Customer Type: Expected '" + CustomerActualType + "', but found '" + AddCustomerExpType + "' in Edit Customer page."); // type
		Assert.assertEquals(CustomerActualTitle, AddCustomerExpTitle,"Mismatch in Customer Title: Expected '" + CustomerActualTitle + "', but found '" + AddCustomerExpTitle + "' in Edit Customer page."); // Title
		Assert.assertEquals(CustomerActualFirstname, CustomerExpFirstname,"Mismatch in Customer FirstName: Expected '" + CustomerActualFirstname + "', but found '" + CustomerExpFirstname + "' in Edit Customer page."); // FirstName
		Assert.assertEquals(CustomerActualLastname, CustomerExpLastname,"Mismatch in Customer LastName: Expected '" + CustomerActualLastname + "', but found '" + CustomerExpLastname + "' in Edit Customer page.");  // LastName
		Assert.assertEquals(CustomerActualEmail, CustomeEmailExp,"Mismatch in Customer Email ID : Expected '" + CustomerActualEmail + "', but found '" + CustomeEmailExp + "' in Edit Customer page.");  // Email 
		Assert.assertEquals(ContactActualNo, CustomeContactExp,"Mismatch in Customer ContactNo: Expected '" + ContactActualNo + "', but found '" + CustomeContactExp + "' in Edit Customer page."); // Contact Number 
		Assert.assertEquals(ActualCustomerLanguageText, ExpectedCustomerLanguageText,"Mismatch in Customer Email ID : Expected '" + ActualCustomerLanguageText + "', but found '" + ExpectedCustomerLanguageText + "' in Edit Customer page.");  // Language
		Assert.assertEquals(ActualCustomerageText, AddExpCustomerAge,"Mismatch in Customer Age: Expected '" + ActualCustomerageText + "', but found '" + AddExpCustomerAge + "' in Edit Customer page.");  // Language option 
		Assert.assertEquals(ActualCustomerCasteCodeText, AddExpCasteCode,"Mismatch in Customer Caste Code: Expected '" + ActualCustomerCasteCodeText + "', but found '" + AddExpCasteCode + "' in Edit Customer page.");  // Cast Code 
		Assert.assertEquals(ActualCustomerProfessionCode, AddExpProfessionCode,"Mismatch in Customer Profession Code: Expected '" + ActualCustomerProfessionCode + "', but found '" + AddExpProfessionCode + "' in Edit Customer page.");  // ProfessionCode
		Assert.assertEquals(ActualCustomerReligionCodeText, AddExpRegionCode,"Mismatch in Customer Profession Code: Expected '" + ActualCustomerReligionCodeText + "', but found '" + AddExpRegionCode + "' in Edit Customer page.");   //RegionCode
		Assert.assertEquals(ActualCustomerPreferredLanguageText, AddExpPreferredLanguage,"Mismatch in Customer PreferredLanguage Code: Expected '" + ActualCustomerPreferredLanguageText + "', but found '" + AddExpPreferredLanguage + "' in Edit Customer page.");   //PreferredLanguage
		Assert.assertEquals(CustomerActDateofbirth, CustomerExpDateofbirth,"Mismatch in Customer Date Of Birth: Expected '" + CustomerActDateofbirth + "', but found '" + CustomerExpDateofbirth + "' in Edit Customer page.");   //Date of Birth 
		Assert.assertEquals(CustomerActMonthofbirthText, CustomerExpMonthofbirth,"Mismatch in Customer Date Of Month: Expected '" + CustomerActMonthofbirthText + "', but found '" + CustomerExpMonthofbirth + "' in Edit Customer page");   //Month of Birth 
		Assert.assertEquals(CustomerActyearofbirth, CustomerExpyearofbirth,"Mismatch in Customer Date Of Month: Expected '" + CustomerActyearofbirth + "', but found '" + CustomerExpyearofbirth + "' in Edit Customer page");   //Year of Birth 
		Assert.assertEquals(CustomerActDateOfAnniversary, CustomerExpDateofanniversary,"Mismatch in Customer Date Of Anniversary: Expected '" + CustomerActDateOfAnniversary + "', but found '" + CustomerExpDateofanniversary + "' in Edit Customer page");  //Date of anniversary
		Assert.assertEquals(CustomerActMonthofanniversaryText, CustomerExpMonthofanniversary,"Mismatch in Customer Month Of Anniversary: Expected '" + CustomerActMonthofanniversaryText + "', but found '\" + CustomerExpMonthofanniversary + \"' in Edit Customer page");   //Month of anniversary 
		Assert.assertEquals(CustomerActYearOfAnniversary, CustomerExpYearofanniversary,"Mismatch in Customer Year Of Anniversary: Expected '" + CustomerActYearOfAnniversary + "', but found '" + CustomerExpYearofanniversary + "' in Edit Customer page");   //Year of anniversary 
		Assert.assertEquals(CustomerActIDProofTypeText, CustomerExpIDProofType,"Mismatch in Customer ID Proof Type: Expected '" + CustomerActIDProofTypeText + "', but found '" + CustomerExpIDProofType + "' in Edit Customer page");   //IDProofType - PAN
		Assert.assertEquals(CustomerActIDProofNumber.toUpperCase(), CustomerExpIDProofNumber.toUpperCase(), "Mismatch in Customer ID Proof Type: Expected '" + CustomerActIDProofNumber + "', but found '\" + CustomerExpIDProofNumber + \"' in Edit Customer page");
		Assert.assertEquals(CustomerActReferredText, CustomerExpReferredBy,"Mismatch in Customer ID Proof Type: Expected '" + CustomerActReferredText + "', but found '" + CustomerExpReferredBy + "' in Edit Customer page");   //Referred by Mobile Type
		Assert.assertEquals(CustomerActMobileNumberOfReferredBy, CustomerExpMobileNumberOfReferredBy,"Mismatch in Customer ID Proof (PAN) Number: Expected '" + CustomerActMobileNumberOfReferredBy + "', but found '" + CustomerExpMobileNumberOfReferredBy + "' in Edit Customer page");  // Referred Mobile No
		Assert.assertEquals(ActAddressProofTypeText, ExpAddressProof,"Mismatch in Customers Address Proof Type: Expected '" + ActAddressProofTypeText + "', but found '" + ExpAddressProof + "' in Edit Customer page");   // AddressProofType
		Assert.assertEquals(ActAddressProofNumber, ExpAddressProofNumber,"Mismatch in Customers Address Proof Number: Expected '" + ActAddressProofNumber + "', but found '" + ExpAddressProofNumber + "' in Edit Customer page");  // AddressProofNumber 
		Assert.assertEquals(CustomerActBankCodeText, CustomerExpbankselection,"Mismatch in Customers bank Name : Expected '" + CustomerActBankCodeText + "', but found '" + CustomerExpbankselection + "' in Edit Customer page");  // Bank selection  
		Assert.assertEquals(CustomerActNobankAct.trim(), CustomerActNobankExp.trim(),"Mismatch in Customers bank Account Number : Expected '" + CustomerActNobankAct + "', but found '" + CustomerActNobankExp + "' in Edit Customer page");  // bank Account 
		Assert.assertEquals(CustomerActIFSCCode, CustomerExpIFSCCode,"Mismatch in Customers bank Account IFSC Code : Expected '" + CustomerActIFSCCode + "', but found '" + CustomerExpIFSCCode + "' in Edit Customer page");  // Bank IFSC code  
		Assert.assertEquals(CustomerActBranchName, CustomerExpBranchName,"Mismatch in Customers bank Branch Name : Expected '" + CustomerActBranchName + "', but found '" + CustomerExpBranchName + "' in Edit Customer page");  // Bank branch name  

		base.scrollToElement(CustomerCreationObj.Ele_CustomerAddress("customerAddresses", "h4 ellipsis accent"));
		base.excuteJsClick(CustomerCreationObj.Ele_CustomerAddress("customerAddresses", "h4 ellipsis accent"));
		Thread.sleep(3000);
		base.excuteJsClick(LoginPageObj.Btn_SignInButton("Yes"));

		String AddCustomerExpName = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("nameTab"));
		String AddCustomerExpEmail = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("emailTab"));
		String AddCustomerExpCountryText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("countryRegionTabRead"));
		String AddCustomerExpStreet = base.GetValue(CustomerCreationObj.Ele_Field("streetNameTabRead"));
		String AddCustomerExpCity = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("cityTabRead"));
		String AddCustomerExpDistrict = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("districtTabRead"));
		String AddCustomerExpState = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("stateTabRead"));
		String AddCustomerExpCode = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"));

		Assert.assertEquals(ActualCustomerFullName, AddCustomerExpName,"Mismatch in Customers Full Name in the Address Edit Section : Expected '" + ActualCustomerFullName + "', but found '" + AddCustomerExpName + "' in Edit Customer page");
		Assert.assertEquals(AddCustomerActCountryText, AddCustomerExpCountryText,"Mismatch in Customers Country Name in the Address Edit Section : Expected '" + AddCustomerActCountryText + "', but found '" + AddCustomerExpCountryText + "' in Edit Customer page");
		Assert.assertEquals(AddCustomerActStreet, AddCustomerExpStreet,"Mismatch in Customers Street Address in the Address Edit Section : Expected '" + AddCustomerActStreet + "', but found '" + AddCustomerExpStreet + "' in Edit Customer page");
		Assert.assertEquals(AddCustomerActDistrict, AddCustomerExpDistrict,"Mismatch in Customers District  in the Address Edit Section : Expected '" + AddCustomerActDistrict + "', but found '" + AddCustomerActDistrict + "' in Edit Customer page");
		Assert.assertEquals(AddCustomerActState, AddCustomerExpState,"Mismatch in Customers State  in the Address Edit Section : Expected '" + AddCustomerActState + "', but found '" + AddCustomerExpState + "' in Edit Customer page");
		Assert.assertEquals(AddCustomerActCode, AddCustomerExpCode,"Mismatch in Customers  PIN Code in the Address Edit Section : Expected '" + AddCustomerActCode + "', but found '" + AddCustomerExpCode + "' in Edit Customer page");
		System.out.println(" Verification in POS completed ");

		Thread.sleep(3000);	
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerAddress"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));

		//Verifying in ERP Application   
		driver.get(UtilityTestData.ERPURL);
		//.Search All Customers
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), CustomerCreationTestData.TC130_CustomerSearch);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"));
		Thread.sleep(10000);	
		//Filter Customerid
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), NewCustomerId);
		Thread.sleep(10000);
		base.buttonClick(CustomerCreationObj.Ele_ERPAccField("custtablelistpage_2_QuickFilterControl_listbox", "Account"));
		Thread.sleep(5000);
		driver.findElement(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input")).sendKeys(Keys.ENTER);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), "ENTER");
		Thread.sleep(3000);
		//Open Account
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(CustomerCreationObj.Ele_ERPCustid("Account"))).click().build().perform();

		base.setZoom(driver, 50);
		Thread.sleep(2000);


		String CustomerTitleErp = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_Type_input"));
		String CustomerFirstNameErp = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_Name_FirstName_input"));
		String CustomerLastNameErp = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_Name_LastName_input"));
		String CustomerLanguageErp = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_Person_LanguageId_input"));
		String CustomerAgeErp = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_CustTable_MGDCustAgeGrpCode_input"));
		String CustomerReglionCodeErp = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_CustTable_MGDReligionCode_input"));
		String CustomerCasteCodeErp = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_CustTable_MGDCasteCode_input"));
		String CustomerProfessionErp = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_CustTable_MGDProfessionCode_input"));
		String CustomerPreferredlanguageErp = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_CustTable_MGDPreferredlanguage_input"));
		String CustomerIdProofTypeErp = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_CustTable_MGDIdProof_input"));
		String CustomerProofNoTypeErp = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_CustTable_MGDIDProofNum_input"));
		String MobileNoErp = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_CustTable_MGDRefMobileNo_input"));
		String ProofAadharErp = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_CustTable_MGDAddressProof_input"));
		String AadharNoErp = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_CustTable_MGDAddressProofNum_input"));
		String DOBErp = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_CustTable_MGDDateOfBirth_input"));
		String AnniversyFullErp = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_CustTable_MGDDateOfAnniversary_input"));	

		// Verification on ERP
		Assert.assertEquals(CustomerActualType, CustomerTitleErp,"Mismatch in Customer Type : Expected '" + CustomerActualType + "', but found '" + CustomerTitleErp + "' in ERP Customer Details Page");
		Assert.assertEquals(CustomerActualFirstname, CustomerFirstNameErp,"Mismatch in Customer FirstName : Expected '" + CustomerActualFirstname + "', but found '" + CustomerFirstNameErp + "' in ERP Customer Details Page");
		Assert.assertEquals(CustomerActualLastname, CustomerLastNameErp,"Mismatch in Customer LastName : Expected '" + CustomerActualLastname + "', but found '" + CustomerLastNameErp + "' in ERP Customer Details Page");
		Assert.assertEquals(ActualCustomerLanguage, CustomerLanguageErp,"Mismatch in Customer Language : Expected '" + ActualCustomerLanguage + "', but found '" + CustomerLanguageErp + "' in ERP Customer Details Page");
		Assert.assertEquals(ActualActCustomerAge, CustomerAgeErp,"Mismatch in Customer Age Group Code : Expected '" + ActualActCustomerAge + "', but found '" + CustomerAgeErp + "' in ERP Customer Details Page");
		Assert.assertEquals(AddActRegionCode, CustomerReglionCodeErp,"Mismatch in Customer Religion Code : Expected '" + AddActRegionCode + "', but found '" + CustomerReglionCodeErp + "' in ERP Customer Details Page");
		Assert.assertEquals(ActualActCasteCode, CustomerCasteCodeErp,"Mismatch in Customer Caste Code : Expected '" + ActualActCasteCode + "', but found '" + CustomerCasteCodeErp + "' in ERP Customer Details Page");
		Assert.assertEquals(AddActProfessionCode, CustomerProfessionErp,"Mismatch in Customer Profession Code  : Expected '" + AddActProfessionCode + "', but found '" + CustomerProfessionErp + "' in ERP Customer Details Page");
		Assert.assertEquals(AddActPreferredLanguage, CustomerPreferredlanguageErp,"Mismatch in Customer Preferred Language : Expected '" + AddActPreferredLanguage + "', but found '" + CustomerPreferredlanguageErp + "' in ERP Customer Details Page");
		Assert.assertEquals(CustomerActIDProofTypeText, CustomerIdProofTypeErp,"Mismatch in Customer ID Proof Type : Expected '" + CustomerActIDProofType + "', but found '" + CustomerIdProofTypeErp + "' in ERP Customer Details Page");
		Assert.assertEquals(CustomerActIDProofNumber.toUpperCase(), CustomerProofNoTypeErp,"Mismatch in Customer ID Proof Number : Expected '" + CustomerActIDProofNumber + "', but found '" + CustomerProofNoTypeErp + "' in ERP Customer Details Page");
		Assert.assertEquals(CustomerActMobileNumberOfReferredBy, MobileNoErp,"Mismatch in Customer Reffered Number : Expected '" + CustomerActMobileNumberOfReferredBy + "', but found '" + MobileNoErp + "' in ERP Customer Details Page");
		Assert.assertEquals(ActAddressProofTypeText, ProofAadharErp,"Mismatch in Customer Address Proof Type  : Expected '" + ActAddressProofTypeText + "', but found '" + ProofAadharErp + "' in ERP Customer Details Page");
		Assert.assertEquals(FullDOB, DOBErp,"Mismatch in Customer Date of birth  : Expected '" + FullDOB + "', but found '" + DOBErp + "' in ERP Customer Details Page");
		Assert.assertEquals(FullAnniversaryDate, AnniversyFullErp,"Mismatch in Customer Anniversary : Expected '" + FullAnniversaryDate + "', but found '" + AnniversyFullErp + "' in ERP Customer Details Page");

		base.scrollToElement(CustomerCreationObj.Ele_ERPCustomerAgeGroupCode("Preferred language"));
		Thread.sleep(2000);
		String ERPAddress=base.GetText(CustomerCreationObj.Ele_ERPaddressField("Address")); 
		Assert.assertEquals(FullAddressCustomer, ERPAddress,"Mismatch in Customer Address : Expected '" + FullAddressCustomer + "', but found '" + ERPAddress + "' in ERP Customer Details Page");
		base.scrollToElement(By.xpath("//button[@aria-label='Contact information']"));
		Thread.sleep(2000);

		//Strings for email and phone 
		String ErpEmailAddress=base.GetValue(CustomerCreationObj.Ele_ErpEmailContact("Email address")); 
		String ErpContactNo=base.GetValue(CustomerCreationObj.Ele_ErpEmailContact("Phone")); 


		Assert.assertEquals(CustomerActualEmail, ErpEmailAddress,"Mismatch in Customer Email Address : Expected '" + CustomerActualEmail + "', but found '" + ErpEmailAddress + "' in ERP Customer Details Page");
		Assert.assertEquals(ContactActualNo, ErpContactNo,"Mismatch in Customer Contact No : Expected '" + ContactActualNo + "', but found '" + ErpContactNo + "' in ERP Customer Details Page");
		System.out.println(" ERP Validation  is completed");
	}

	// <summary>
	// Test Case Title : Creating retail customer with mandatory fields
	// Automation ID : TC128
	//Author: Nandagopan 
	// </summary>

	public void TC128_CustomerCreation() throws Exception
	{
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);

		Thread.sleep(5000);

		//Step 2: Navigate to the Transaction page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));


		//Step 3: Click on add customer
		base.buttonClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));
		Thread.sleep(3000);

		// Step 4:Click on create new customer
		base.buttonClick(LoginPageObj.Btn_Login("Create new customer"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"),CustomerCreationTestData.TC128_FirstName );
		String CustomerFirstName = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"), CustomerCreationTestData.TC128_LastName );
		String CustomerLastName =  base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),CustomerCreationTestData.TC128_PhoneNumber);
		String CustomerPhoneNumber =  base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));

		//Select the Preferred Language, Religion Code, Receipt language Preference
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"),CustomerCreationTestData.TC128_CustomerReceiptlanguage);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreationTestData.TC128_CustomerReligion);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreationTestData.TC128_CustomerPrefLanguage);

		// Step 6: Click Save button
		//Expected Condition:Verify whether the details given is reflected in ERP & POS
		base.scrollToElement(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails")); 
		List<String> CustomerData= Arrays.asList(CustomerCreationTestData.TC128_FirstName,CustomerCreationTestData.TC128_LastName,CustomerCreationTestData.TC128_PhoneNumber,CustomerCreationTestData.TC128_CustomerReceiptlanguage,CustomerCreationTestData.TC128_CustomerReligion,CustomerCreationTestData.TC128_CustomerPrefLanguage);
		System.out.println(CustomerData);

		// Wait until the element is present AND visible
		WebElement customerIdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
				CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount")));
		base.scrollToElement(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount"));
		Thread.sleep(3000);
		String NewCustomerId = customerIdElement.getText().trim();
		System.out.println("Customer ID: " + NewCustomerId);

		//6.1 Check whether if Reflected in POS.
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),NewCustomerId);
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Customers"));
		base.Hover(CustomerCreationObj.Ele_Customer(NewCustomerId));
		base.buttonClick(CustomerCreationObj.Ele_Customer(NewCustomerId));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions(Utility_TestData.MenuBarOptionsCustomerProfile[3]));
		Thread.sleep(3000);
		String ExpectedCustomerFirstName    = base.GetValue(CustomerCreationObj.Ele_Name("customerFirstNameInput"));
		String ExpectedCustomerLastName     = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));
		String ExpectedCustomerPhoneNumber  = base.GetText(CustomerCreationObj.Ele_Phone());
		String ExpectedCustomerReceiptLang  = base.GetText(CustomerCreationObj.Ele_RecieptLang("customerLanguageOptions","en-IN"));
		String ExpectedCustomerReligion     = base.GetText(CustomerCreationObj.Ele_RecieptLang("RegionCode","HND"));
		String ExpectedCustomerPrefLang     = base.GetText(CustomerCreationObj.Ele_RecieptLang("PreferredLanguage","109"));

		//for Getting validating in ERP
		String ERPactualReceiptLangCode = base.GetValue(CustomerCreationObj.Ele_RecieptLang("customerLanguageOptions","en-IN"));
		String ERPactualReligionCode    = base.GetValue(CustomerCreationObj.Ele_RecieptLang("RegionCode","HND"));
		String ERPactualPrefLangCode    = base.GetValue(CustomerCreationObj.Ele_RecieptLang("PreferredLanguage","109"));

		Assert.assertEquals(CustomerFirstName,ExpectedCustomerFirstName, "First Name on Customer creation page Of POS "+CustomerFirstName+"is mismatch with first name "+ExpectedCustomerFirstName+" in customer Edit page of POS");
		Assert.assertEquals(CustomerLastName,ExpectedCustomerLastName, "Last Name on Customer Creation page of POS"+CustomerLastName+"is mismatch with Last Name in Customer Edit page of POS"+ExpectedCustomerLastName+"");
		Assert.assertEquals(CustomerPhoneNumber,ExpectedCustomerPhoneNumber,"Phone Number on Customer Creation page of POS"+CustomerPhoneNumber+"is mismatch with Phone Number in Customer Edit page"+ExpectedCustomerPhoneNumber+"");
		Assert.assertEquals(CustomerCreationTestData.TC128_CustomerReceiptlanguage,ExpectedCustomerReceiptLang,"Receipt Language on customer creation page");
		Assert.assertEquals(CustomerCreationTestData.TC128_CustomerReligion,ExpectedCustomerReligion,"Religion mismatch!");
		Assert.assertEquals(CustomerCreationTestData.TC128_CustomerPrefLanguage,ExpectedCustomerPrefLang,"Preferred Language mismatch!");
		System.out.println(" Customer data successfully validated in POS");

		//6.2:Check if it is Reflected in ERP.
		base.newWindow(1);
		driver.get(UtilityTestData.ERPURL);

		//Search All Customers
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), CustomerCreationTestData.TC128_CustomerSearch);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"));
		Thread.sleep(6000);	

		//Filter Customerid
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), NewCustomerId);
		Thread.sleep(10000);
		base.buttonClick(CustomerCreationObj.Ele_ERPAccField("custtablelistpage_2_QuickFilterControl_listbox", "Account"));
		Thread.sleep(10000);
		driver.findElement(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);

		//Open Account
		base.actionClick(CustomerCreationObj.Ele_ERPCustid("Account"));
		base.setZoom(driver, 50);
		Thread.sleep(2000);

		//Getting Required Data from ERP Customer Page
		String ERPFirstName   = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_Name_FirstName_input"));
		String ERPLastName    = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_Name_LastName_input"));
		String ERPReceiptLang = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_Person_LanguageId_input"));
		String ERPReligion    = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_CustTable_MGDReligionCode_input"));
		String ERPPrefLang    = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_CustTable_MGDPreferredlanguage_input"));
		Thread.sleep(5000);

		//Getting Phone number to Contact Information
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_return("Contact information"));
		Thread.sleep(3000);
		String ERPphoneNumber = base.GetAttribte(CustomerCreationObj.Ele_ERPCustid("Contact number/address"), "value");
		Thread.sleep(5000);

		Assert.assertEquals(ExpectedCustomerFirstName,ERPFirstName, "First Name from POS Customer Page "+ExpectedCustomerFirstName+" is not match with First name in ERP "+ERPFirstName+"");
		Assert.assertEquals(ExpectedCustomerLastName,ERPLastName, "Last Name from POS Customer Page"+ExpectedCustomerLastName+"is not match with Last name in ERP page"+ERPLastName+"");
		Assert.assertEquals(ERPactualReceiptLangCode,ERPReceiptLang,"Receipt language from POS Customer Page"+ERPactualReceiptLangCode+"is not match  with the Receipt language in ERP Customer Page"+ERPReceiptLang+"");
		Assert.assertEquals(ERPactualReligionCode,ERPReligion,"Religion Code in POS Customer page"+ERPactualReligionCode+"is not match with Religion code in ERP Customer Page"+ERPReligion+"");
		Assert.assertEquals(ERPactualPrefLangCode,ERPPrefLang,"Preferd Language in POS Customer Page"+ERPactualPrefLangCode+"is not match with the Prefered Langauge in ERP Customer Page"+ERPPrefLang+"");
		Assert.assertEquals(ExpectedCustomerPhoneNumber,ERPphoneNumber,"Phone Number in POS Customer Page"+ExpectedCustomerPhoneNumber+"is not match with Phone number in ERP Customer Page"+ERPphoneNumber+"");

		System.out.println(" Customer data successfully validated in ERP ");

	}

	// <summary>
	// Test Case Title : Creating organization customer with mandatory fields
	// Automation ID : TC137
	//Author: Aiswarya
	// </summary>

	public void TC137_CustomerCreation() throws Exception
	{
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2: Navigate to the Transaction page
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		//Step 3: Click on add customer
		Thread.sleep(3000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));
		Thread.sleep(3000);

		// Step 4: Click on create new customer
		base.buttonClick(LoginPageObj.Btn_Login("Create new customer"));

		//Step 5: Select Customer Type as "Organisation" 
		Wait.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput")));
		Thread.sleep(5000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput"),CustomerCreationTestData.TC137_CustomerType);
		String CustomerType = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput"));

		//Step 6: Enter the "Company Name"
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("companyNameInput"),CustomerCreationTestData.TC137_companyName );
		String CompanyName = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("companyNameInput"));

		//Step 7: Enter the "Phone number"
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),CustomerCreation_TestData.TC137_PhoneNumber);
		String CustomerPhoneNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));

		//Step 8: Select Customer Language 
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		List<String> CustomerLanguage = appUtils.getRandomProducts(CustomerCreation_TestData.TC137_CustomerLanguageOptions, 1);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"), CustomerLanguage.get(0));
		String CustomerLang = base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		String CustomerLangText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		Thread.sleep(2000);

		//Step 9: Click on Save
		//Expected: Verify whether the details given is reflected in ERP & POS  
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount")));
		base.scrollToElement(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount"));
		Thread.sleep(3000); 
		String CustomerId = base.GetText(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount")).trim();    

		//Step 10: Check whether it is getting updated on POS and ERP
		//Click on Transaction button
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		//Select customer
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"), CustomerId);
		Thread.sleep(3000); 
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Customers"));
		base.Hover(CustomerCreationObj.Ele_Customer(CustomerId,"dataListLine"));
		base.buttonClick(CustomerCreationObj.Ele_Customer(CustomerId,"dataListLine"));
		//Click on the "Edit" button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions(Utility_TestData.MenuBarOptionsCustomerProfile[3]));
		Thread.sleep(3000); 

		String ExpectedCustomerType = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput"));
		String ExpectedCompanyName = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("companyNameInput"));
		String ExpectedCustomerPhoneNumber= base.GetText(CustomerCreationObj.Ele_PhoneNumber("h4 accent","h5"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		String ExpectedCustomerLang =base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));

		Assert.assertEquals(CustomerType, ExpectedCustomerType, "Mismatch in Customer Type: expected " + CustomerType + " but found '" + ExpectedCustomerType + "' in Edit Customer page.");
		Assert.assertEquals(CompanyName, ExpectedCompanyName, "Mismatch in Company Name: expected " + CompanyName + " but found '" + ExpectedCompanyName + "' in Edit Customer page.");
		Assert.assertEquals(CustomerLangText, ExpectedCustomerLang, "Mismatch in Customer Language: expected " + CustomerLangText + " but found '" + ExpectedCustomerLang + "' in Edit Customer page.");
		Assert.assertEquals(CustomerPhoneNumber, ExpectedCustomerPhoneNumber, "Mismatch in Customer Phone Number: expected " + CustomerPhoneNumber + " but found '" + ExpectedCustomerPhoneNumber + "' in Edit Customer page.");

		//Verifying whether it is getting updated on ERP
		base.newWindow(1);
		driver.get(Utility_TestData.ERPURL);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), CustomerCreation_TestData.CustomerSearch);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"));
		Thread.sleep(6000);	

		//Filter CustomerId 
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), CustomerId);
		Thread.sleep(10000);
		base.buttonClick(CustomerCreationObj.Ele_ERPAccField("custtablelistpage_2_QuickFilterControl_listbox", "Account"));
		Thread.sleep(10000);
		driver.findElement(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);

		//Open Account
		base.actionClick(CustomerCreationObj.Ele_ERPCustid("Account"));
		base.setZoom(driver, 50);
		String ERPCustomerType = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_Type_input"));
		String ERPCompanyName= base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_Org_NameAlias_input"));
		String ERPCustomerLanguage = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_Org_LanguageId_input"));
		base.scrollToElement(CustomerCreationObj.Ele_ERPCustomerAgeGroupCode("Preferred language"));
		Thread.sleep(2000);
		String ERPPhoneNumber=base.GetValue(CustomerCreationObj.Ele_ErpEmailContact("Phone")); 

		Assert.assertEquals(CompanyName, ERPCompanyName, "Mismatch in Company Name: expected '" + CompanyName + "' but found '" + ERPCompanyName + "' in ERP Customer Details Page");
		Assert.assertEquals(CustomerLang, ERPCustomerLanguage, "Mismatch in Customer Language: expected '" + CustomerLang + "' but found '" + ERPCustomerLanguage + "' in ERP Customer Details Page");
		Assert.assertEquals(CustomerPhoneNumber, ERPPhoneNumber, "Mismatch in Customer Phone Number: expected '" + CustomerPhoneNumber + "' but found '" + ERPPhoneNumber + "' in ERP Customer Details Page");
		Boolean CustomerTypeOrganisation= CustomerType.equals(CustomerCreation_TestData.TC137_CustomerType)||CustomerType.equals(CustomerCreation_TestData.TC137_CustomerType);
		Boolean ERPCustomerTypeOrganisation = ERPCustomerType.equals(CustomerCreation_TestData.TC137_ERPCustomerType)||ERPCustomerType.equals(CustomerCreation_TestData.TC137_ERPCustomerType);
		Assert.assertEquals(CustomerTypeOrganisation, ERPCustomerTypeOrganisation,"Mismatch in Customer Type: expected '" + CustomerType + "' but found '" + ERPCustomerType + "' in ERP Customer Details Page");

	}


	// <summary>
	// Test Case Title : Edit Address Of An Existing Organization Customer
	// Automation ID : TC140
	//Author: Vishnu RR 
	// </summary>

	public void TC140_CustomerCreation() throws Exception

	{

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		//Precondition - Creating a customer with Customer Type as Organization.

		//Click on Transaction button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		//Click on add customer
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));
		//Click on create customer
		Thread.sleep(3000);
		base.excuteJsClick(LoginPageObj.Btn_Login("Create new customer"));
		//Select the Customer Type as Organization 
		Thread.sleep(2000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput"),CustomerCreationTestData.TC140_CustomerType);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("companyNameInput"),CustomerCreationTestData.TC140_CompanyName);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),CustomerCreationTestData.TC140_PhoneNumber);
		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"),CustomerCreationTestData.TC140_CustomerReceiptlanguage);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreationTestData.TC140_CustomerReligion);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreationTestData.TC140_CustomerPrefLanguage);
		//Click Add an address
		base.scrollToElement(LoginPageObj.Edt_AlertText("Add an address"));
		base.excuteJsClick(LoginPageObj.Edt_AlertText("Add an address")); // Clicking the add address button 
		Thread.sleep(1000);
		//Enter Zip code
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"),CustomerCreation_TestData.TC140_ZipCode ); //Adding the zip code 
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"), "Enter");
		if(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Address")))
		{

			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("win-item col grow", "1"));
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		}
		else {
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerAddress"));
		}
		String ActualFullAddressCustomer = base.GetText(CustomerCreationObj.Btn_AddtoCart("primaryFontColor")); //Actual Full Address of the customer  	

		//Step:2 Click on Transaction button
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		//Step:3 Search the phone number of existing organization customer
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"), CustomerCreationTestData.TC140_PhoneNumber);
		Thread.sleep(3000);
		//Step:4 Select the customer
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Customers"));
		base.Hover(CustomerCreationObj.Ele_Customer(CustomerCreationTestData.TC140_PhoneNumber));
		base.buttonClick(CustomerCreationObj.Ele_Customer(CustomerCreationTestData.TC140_PhoneNumber));
		// Step:4.Click on the organization name
		// Step:5.Make changes, Click on save
		// Click on the "Edit" button
		base.buttonClick(LoginPageObj.Btn_LoginButton("h4 ellipsis accent"));	
		Thread.sleep(3000);
		base.clearData(CustomerCreationObj.Ele_Field("streetNameTabRead"));
		base.setData(CustomerCreationObj.Ele_Field("streetNameTabRead"),CustomerCreation_TestData.TC140_Editedstreetdetails );
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"),CustomerCreation_TestData.TC140_EditedZipCode ); //Editing the zip code 
		//base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"), "Enter");
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"));
		if(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Address")))
		{

			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("win-item col grow", "1"));
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		}
		else {
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerAddress"));
		}
		Thread.sleep(2000);
		//Step 6 Check whether it is getting updated on POS and ERP
		// Expected Results: Verifying whether the details updated is reflected in ERP & POS 
		// Validating the address in POS 
		String ExpectedFullAddressCustomer = base.GetText(CustomerCreationObj.Btn_AddtoCart("primaryFontColor")); //Edited Customer Full address
		System.out.print("Edited Address: " + ExpectedFullAddressCustomer);
		Assert.assertNotEquals(ActualFullAddressCustomer, ExpectedFullAddressCustomer,"Match in Customer Address in POS : Expected '" + ActualFullAddressCustomer + "', and found '" + ExpectedFullAddressCustomer + "' in the customer details page");

		//Verifying in ERP Application   
		driver.get(UtilityTestData.ERPURL);
		//.Search All Customers
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), CustomerCreationTestData.TC140_CustomerSearch);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"));
		Thread.sleep(10000);	
		//Filter Customerid
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), CustomerCreationTestData.TC140_PhoneNumber);
		Thread.sleep(10000);
		base.buttonClick(CustomerCreationObj.Ele_ERPAccField("custtablelistpage_2_QuickFilterControl_listbox", "Telephone"));
		Thread.sleep(5000);
		driver.findElement(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input")).sendKeys(Keys.ENTER);
		//base.pressKey(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), "ENTER");
		Thread.sleep(3000);
		//Open Account
		base.actionClick(CustomerCreationObj.Ele_ERPCustid("Account"));		 
		base.setZoom(driver, 50);
		Thread.sleep(2000);
		base.scrollToElement(CustomerCreationObj.Ele_ERPCustomerAgeGroupCode("Preferred language"));
		Thread.sleep(2000);
		String ERPAddressField=base.GetText(CustomerCreationObj.Ele_ERPaddressField("Address")); 
		Assert.assertEquals(ExpectedFullAddressCustomer, ERPAddressField,"MisMatch in Customer Address : Expected '" + ExpectedFullAddressCustomer + "', but found '" + ERPAddressField + "' in ERP Customer Details Page");

	}

	// <summary>
	// Test Case Title : Creating organization customer with all details
	// Automation ID : TC139
	//Author: Aiswarya
	// </summary>

	public void TC139_CustomerCreation() throws Exception
	{
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		setZoom(driver, 60);
		Thread.sleep(8000);

		//Step 2: Navigate to the Transaction page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));


		//Step 3: Click on add customer
		base.buttonClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));
		Thread.sleep(3000);

		// Step 4:Click on create new customer
		base.buttonClick(LoginPageObj.Btn_Login("Create new customer"));

		//Step 5: Select Customer Type as "Organisation" 
		Wait.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput")));
		Thread.sleep(5000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput"),CustomerCreationTestData.TC139_CustomerType);
		String CustomerType = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput"));

		//Step 6: Enter the "Company Name"
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("companyNameInput"),CustomerCreationTestData.TC139_CompanyName );
		String CompanyName = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("companyNameInput"));

		//Step 7: Enter the Primary Email
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerContactEmailInput"),CustomerCreation_TestData.TC139_Email );
		String CompanyEmail = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerContactEmailInput"));

		//Step 8: Enter the "Phone number"
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),CustomerCreation_TestData.TC139_PhoneNumber);
		String CustomerPhoneNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));

		//Step 9: Select Customer Language 
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		List<String> CustomerLanguage = appUtils.getRandomProducts(CustomerCreation_TestData.TC139_CustomerLanguageOptions, 1);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"), CustomerLanguage.get(0));
		String CustomerLangValue= base.GetValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		String CustomerLangText = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		Thread.sleep(2000);

		//Step 10: Add Bank details
		//Step 10.1: Select Bank name 
		base.scrollToElement(CustomerCreationObj.Ele_ERPCustomerAgeGroupCode("Preferred language"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("BankCode"));
		Thread.sleep(2000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("BankCode"),CustomerCreation_TestData.TC139_BankName);
		String CompanyBankCode= base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("BankCode"));

		//Step 10.2:Account number
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("BankAccNo"),CustomerCreation_TestData.TC139_BankAccNo );
		String CompanyBankAccNo= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("BankAccNo"));

		//Step 10.3:IFSC code
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("IFSCCode"),CustomerCreation_TestData.TC139_IFSCCode );
		String CompanyIfscCode= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("IFSCCode"));

		//Step 10.4: Branch name
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("Branch"),CustomerCreation_TestData.TC139_BranchName );
		String CompanyBranchName= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("Branch"));

		//Step 11: Click on "Add an address"
		base.scrollToElement(LoginPageObj.Edt_AlertText("Add an address"));
		base.excuteJsClick(LoginPageObj.Edt_AlertText("Add an address"));
		Thread.sleep(2000);

		//Step 12: Enter the ZIP/postal code
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"));
		List<String> CompanyZipCode = appUtils.getRandomProducts(CustomerCreation_TestData.TC132_ZipCode,1);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"),CompanyZipCode.get(0));
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("zipTabRead"), "Enter");
		if(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Address")))
		{
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("win-item col grow", "1"));
		}
		else {
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerAddress"));
		}
		Thread.sleep(3000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		String CompanyAddress= base.GetText(LoginPageObj.Ele_ErrorMessage("primaryFontColor"));


		//Step 13: Click on Save
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount")));
		base.scrollToElement(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount"));
		Thread.sleep(3000); 
		String CustomerId = base.GetText(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount")).trim();
		System.out.println("CustomerID: " + CustomerId);

		//Step 14: Check whether it is getting updated on POS and ERP
		//Expected: Verify whether the details given is reflected in ERP & POS
		//Click on Transaction button
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		//Select customer
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"), CustomerId);
		Thread.sleep(3000); 
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Customers"));
		base.Hover(CustomerCreationObj.Ele_Customer(CustomerId,"dataListLine"));
		base.buttonClick(CustomerCreationObj.Ele_Customer(CustomerId,"dataListLine"));
		//Click on the "Edit" button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions(Utility_TestData.MenuBarOptionsCustomerProfile[3]));
		Thread.sleep(3000); 

		String ExpectedCustomerType = base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerTypeInput"));
		String ExpectedCompanyName = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("companyNameInput"));
		String ExpectedEmail = base.GetText(CustomerCreationObj.Ele_PrimaryEmail("Primary email"));
		String ExpectedCustomerPhoneNumber= base.GetText(CustomerCreationObj.Ele_CompanyPhoneNumber("CustomerPhoneTypeFormatter","h5"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		String ExpectedCustomerLang =base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		base.scrollToElement(CustomerCreationObj.Ele_ERPCustomerAgeGroupCode("Preferred language"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("BankCode"));
		String ExpectedCompanyBankCode= base.getSelectedDropdownValue(NormalSaleGoldAndSilverObj.Sel_DepositType("BankCode"));
		String ExpectedCustomerBankAccNo= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("BankAccNo"));
		String ExpectedCompanyIfscCode= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("IFSCCode"));
		String ExpectedCompanyBranchName= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("Branch"));			
		Thread.sleep(3000);
		String ExpectedCompanyAddress= base.GetText(LoginPageObj.Ele_ErrorMessage("primaryFontColor"));

		Assert.assertEquals(CustomerType, ExpectedCustomerType, "Mismatch in Customer Type: expected " + CustomerType + " but found '" + ExpectedCustomerType + "' in Edit Customer page.");
		Assert.assertEquals(CompanyName, ExpectedCompanyName, "Mismatch in Company Name: expected " + CompanyName + " but found '" + ExpectedCompanyName + "' in Edit Customer page.");
		Assert.assertEquals(CompanyEmail, ExpectedEmail, "Mismatch in Primary Email: expected " + CompanyEmail + " but found '" + ExpectedEmail + "' in Edit Customer page.");
		Assert.assertEquals(CustomerPhoneNumber, ExpectedCustomerPhoneNumber, "Mismatch in Customer Phone Number: expected " + CustomerPhoneNumber + " but found '" + ExpectedCustomerPhoneNumber + "' in Edit Customer page.");
		Assert.assertEquals(CustomerLangText, ExpectedCustomerLang, "Mismatch in Customer Language: expected " + CustomerLangText + " but found '" + ExpectedCustomerLang + "' in Edit Customer page.");
		Assert.assertEquals(CompanyBankCode, ExpectedCompanyBankCode, "Mismatch in Bank Name: expected " + CompanyBankCode + " but found '" + ExpectedCompanyBankCode + "' in Edit Customer page.");
		Assert.assertEquals(CompanyBankAccNo, ExpectedCustomerBankAccNo, "Mismatch in Account Number: expected " + CompanyBankAccNo + " but found '" + ExpectedCustomerBankAccNo + "' in Edit Customer page.");
		Assert.assertEquals(CompanyIfscCode, ExpectedCompanyIfscCode, "Mismatch in IFSC Code: expected " + CompanyIfscCode + " but found '" + ExpectedCompanyIfscCode + "' in Edit Customer page.");
		Assert.assertEquals(CompanyBranchName, ExpectedCompanyBranchName, "Mismatch in Branch Name: expected " + CompanyBranchName + " but found '" + ExpectedCompanyBranchName + "' in Edit Customer page.");
		Assert.assertEquals(CompanyAddress, ExpectedCompanyAddress, "Mismatch in Address: expected " + CompanyAddress + " but found '" + ExpectedCompanyAddress + "' in Edit Customer page.");

		//Verify whether the details given is reflected in ERP
		base.newWindow(1);
		driver.get(Utility_TestData.ERPURL);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), CustomerCreation_TestData.CustomerSearch);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"));
		Thread.sleep(6000);	

		//Filter CustomerId 
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), CustomerId);
		Thread.sleep(10000);
		base.buttonClick(CustomerCreationObj.Ele_ERPAccField("custtablelistpage_2_QuickFilterControl_listbox", "Account"));
		Thread.sleep(10000);
		driver.findElement(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);

		//Open Account
		base.actionClick(CustomerCreationObj.Ele_ERPCustid("Account"));
		base.setZoom(driver, 50);
		String ERPCustomerType = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_Type_input"));
		String ERPCompanyName= base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("custtablelistpage_2_Org_NameAlias_input"));
		String ERPCustomerLanguage = base.GetText(NormalSaleGoldAndSilverObj.Ele_Purchase("custtablelistpage_2_Org_LanguageId_input"));
		base.setZoom(driver, 50);
		base.scrollToElement(CustomerCreationObj.Ele_ERPCustomerAgeGroupCode("Preferred language"));
		String ERPAddress=base.GetText(CustomerCreationObj.Ele_ERPaddressField("Address"));
		base.scrollToElement(By.xpath("//button[@aria-label='Contact information']"));
		Thread.sleep(2000);
		String ERPEmail=base.GetValue(CustomerCreationObj.Ele_ErpEmailContact("Email address")); 
		String ERPPhoneNumber=base.GetValue(CustomerCreationObj.Ele_ErpEmailContact("Phone")); 
		base.buttonClick(CustomerCreationObj.Ele_BankAccounts("mibCustBankAccounts_label"));
		Thread.sleep(2000);
		String ERPBankName = base.GetValue(NormalSaleGoldAndSilverObj.Ele_WarehouseName("Identification_Name"));
		String ERPBankAccNo = base.GetValue(NormalSaleGoldAndSilverObj.Ele_WarehouseName("Identification_AccountNum"));
		String ERPBranchName = base.GetValue(NormalSaleGoldAndSilverObj.Ele_WarehouseName("CustBankAccount_MGDBranchName"));
		String ERPIfscCode= base.GetValue(NormalSaleGoldAndSilverObj.Ele_WarehouseName("CustBankAccount_MGDIFSCCode"));


		Boolean CustomerTypeOrganisation= CustomerType.equals(CustomerCreation_TestData.TC139_CustomerType)||CustomerType.equals(CustomerCreation_TestData.TC139_CustomerType);
		Boolean ERPCustomerTypeOrganisation = ERPCustomerType.equals(CustomerCreation_TestData.TC139_ERPCustomerType)||ERPCustomerType.equals(CustomerCreation_TestData.TC139_ERPCustomerType);
		Assert.assertEquals(CustomerTypeOrganisation, ERPCustomerTypeOrganisation,"Mismatch in Customer Type: expected '" + CustomerType + "' but found '" + ERPCustomerType + "' in ERP Customer Details Page");
		Assert.assertEquals(CompanyName, ERPCompanyName,"Mismatch in Company Name: expected '" + CompanyName + "' but found '" + ERPCompanyName + "' in ERP Customer Details Page");
		Assert.assertEquals(CustomerLangValue, ERPCustomerLanguage,"Mismatch in Customer Language: expected '" + CustomerLangValue + "' but found '" + ERPCustomerLanguage + "' in ERP Customer Details Page");
		Assert.assertEquals(CustomerPhoneNumber, ERPPhoneNumber, "Mismatch in Customer Phone Number: expected '" + CustomerPhoneNumber + "' but found '" + ERPPhoneNumber + "' in ERP Customer Details Page");
		Assert.assertEquals(CompanyAddress, ERPAddress,"Mismatch in Address: expected '" + CompanyAddress + "' but found '" + ERPAddress + "' in ERP Customer Details Page");
		Assert.assertEquals(CompanyBankCode, ERPBankName,"Mismatch in Bank Name: expected '" + CompanyBankCode + "' but found '" + ERPBankName + "' in ERP Customer Details Page");
		Assert.assertEquals(CompanyBankAccNo, ERPBankAccNo,"Mismatch in Bank Account Number: expected '" + CompanyBankAccNo + "' but found '" + ERPBankAccNo + "' in ERP Customer Details Page");
		Assert.assertEquals(CompanyBranchName, ERPBranchName,"Mismatch in Branch Name: expected '" + CompanyBranchName + "' but found '" + ERPBranchName + "' in ERP Customer Details Page");
		Assert.assertEquals(CompanyIfscCode, ERPIfscCode,"Mismatch in IFSC Code: expected '" + CompanyIfscCode + "' but found '" + ERPIfscCode + "' in ERP Customer Details Page");
	}
}