package testPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import testData.CommonData;
import testData.GPPSettlementAverageRateSingleSkuMultipleSku_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.AppUtilities.ReturnDetails;
import utilPack.BasePge;
import utilPack.ErpUtilities;
import utilPack.ErpUtilities.GPPAccountDetails;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;

//import basePackage.BasePge;

public class GPPSettlementAverageRateSingleSkuMultipleSku extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	ErpUtilities erpUtils;

	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj = new AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj = new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();
	GPPSettlementAverageRateSingleSkuMultipleSku_Obj GPPSettlementAverageRateSingleSkuMultipleSkuObj = new GPPSettlementAverageRateSingleSkuMultipleSku_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	GPPSettlementAverageRateSingleSkuMultipleSku_TestData GPPSettlementAverageRateSingleSkuMultipleSkuTestData = new GPPSettlementAverageRateSingleSkuMultipleSku_TestData();
	Login login = new Login(driver);
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();

	public GPPSettlementAverageRateSingleSkuMultipleSku(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		erpUtils = new ErpUtilities(base);
	}

	/// <summary>
	/// Test Case Title : Mature a GG scheme
	//==========Challenge (scroll of gpp account opening table) to find customer from ERP ========
	/// Automation ID : TC110
	/// Author : Jhoncy
	/// </summary>
	public void TC110_GPPSettlementAverageRateSingleSkuMultipleSku() throws Exception {
		
		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Prerequisite Customer must be having a ongoing GG scheme
		GPPAccountDetails Details = erpUtils.ToGetOnGoingGGScheme();
		String CustomerAccount = Details.CustomerAccount;
		String GPPAccountNumber = Details.GppAccount;

		//Step 2:Click on Transaction button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		//Step 3:Click on GPP button
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));

		//Step 4:Click on GPP Maturity button
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP Maturity"));
		Thread.sleep(7000);
		
		//Step 5: Select customer 
		//Step 6: Click on add to GPP Maturity button
		appUtils.SearchByCustomerIDForGPP(CustomerAccount, UtilityTestData.GPPMaturityButton);
		
		//Step 7:Select GPP account
		base.buttonClick(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("Select gpp account..."));
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("Select gpp account..."),GPPAccountNumber);
		
		//Step 8:Click on Mature button
		//Expected Result:Verify validation *Successfuly matured scheme*ERP- verify matured column enabled
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions("SearchView_AppBarView_appBarCommandCommand"));
		base.buttonClick(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Ele_GPPMature("primaryButton","YES"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Matured successfully. account no. - "+GPPAccountNumber)),"Validation saying 'Matured successfully.' is not displayed ");
		base.buttonClick(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Ele_GPPMature("primaryButton","OK"));
		
		Thread.sleep(5000);
		erpUtils.ToGetMaturedGGScheme(GPPAccountNumber);	
	}
	
	/// <summary>
		/// Test Case Title : Check the discount details shown in the maturity screen
		//==========Challenge (scroll of gpp account opening table) to find customer from ERP ========
		/// Automation ID : TC111
		/// Author : Neethu
		/// </summary>

		public void TC111_GPPSettlementAverageRateSingleSkuMultipleSku() throws Exception {

			// Step 1: Login to POS
			login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
			base.setZoom(driver, 60);

			//Prerequisite Customer must be having a ongoing GG scheme
			GPPAccountDetails Details = erpUtils.ToGetOnGoingGGScheme();
			String CustomerAccount = Details.CustomerAccount;
			String GPPAccountNumber = Details.GppAccount;

			//Step 2:Click on Transaction button
			appUtils.HamBurgerButtonClick("iconShop");

			//Step 3:Click on GPP button
			base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
			Thread.sleep(2000);
			
			//Step 4:Click on GPP Maturity button
			base.excuteJsClick(LoginPageObj.Edt_AlertText("GPP Maturity"));
			Thread.sleep(7000);

			//Step 5: Select customer 
			//Step 6: Click on add to GPP Maturity button
			appUtils.SearchByCustomerIDForGPP(CustomerAccount, UtilityTestData.GPPMaturityButton);

			//Step 7:Select GPP account
			base.buttonClick(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("Select gpp account..."));
			base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("Select gpp account..."),GPPAccountNumber);

			//Step 8: Click on discount details button
			//Expected Result:Verify discount details table
			base.buttonClick(LoginPageObj.Btn_SingnIn("SearchView_AppBarView_appBarCommandDiscountCommand"));
		
			WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(8));
			if (base.isElementPresent(driver, NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SpecifyPrice("Error"))) {		
				try {
					WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(LoginPageObj.Ele_ErrorMessage("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20")));
					OkButton.click();
					base.buttonClick(LoginPageObj.Btn_SingnIn("SearchView_AppBarView_appBarCommandDiscountCommand"));

				} 
				catch (TimeoutException e) {
				} 
			}
			
				
			String[] Option = {GPPSettlementAverageRateSingleSkuMultipleSkuTestData.TC111_GLDDiscountDetails,GPPSettlementAverageRateSingleSkuMultipleSkuTestData.TC111_PRCDiscountDetails,GPPSettlementAverageRateSingleSkuMultipleSkuTestData.TC111_UNCDiscountDetails,GPPSettlementAverageRateSingleSkuMultipleSkuTestData.TC111_GBCDiscountDetails};
			for(String DiscountDetails : Option)
			{
				asrt.assertTrue(base.isExists(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_TotalAmount(DiscountDetails,"win-item")), "Mismatch in discount details " + DiscountDetails + "in GPP maturity page.");
			}
			

			
		}

}