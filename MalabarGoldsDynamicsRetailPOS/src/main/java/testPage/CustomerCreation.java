
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
		
		
}
