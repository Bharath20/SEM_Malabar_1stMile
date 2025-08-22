package testPage;

import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleDiamondandPlatinum_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.StatementPosting_Obj;
import testData.CommonData;
import testData.StatementPosting_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.AppUtilities.SKUTypeResult;
import utilPack.BasePge;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;

public class StatementPosting extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	MathUtilities mathUtils;
	PdfUtilities pdfUtils;

	Login login = new Login(driver);
	LoginPage_Obj LoginPageObj= new LoginPage_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	StatementPosting_Obj StatementPostingObj = new StatementPosting_Obj();
	StatementPosting_TestData StatementPostingTestData = new StatementPosting_TestData();
	public StatementPosting(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		mathUtils = new MathUtilities(base);
		pdfUtils = new PdfUtilities(base);
	}

	// <summary>
	// Test Case Title : StatementPosting
	// Automation ID : TC310
	// Challenge: At Step 9, after clicking the 'Calculate Statement' button and selecting 'Yes' in the confirmation popup, 
	///the automation fails to proceed further, likely due to a 409 Conflict error observed in the console.
	// Author: Chandana Babu
	// </summary>
	public void TC310_StatementPosting() throws Exception {

		//Step 1: Go to validate transaction
		//Step 2: Click on the add arrow mark
		//Step 3: Click ok button
		//Step 4: Go to store transactions
		//Step 5: Check if latest validation status is success or not
		String InvoiceNo = appUtils.GetInvoiceNoAfterNormalSale(StatementPostingTestData.TC310_CustomerNo, StatementPostingTestData.TC310_Products);
		base.newWindow(1);
		driver.get(UtilityTestData.ERPURL);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));		
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), StatementPostingTestData.TC310_StoreTransaction);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Ele_ErrorMessage("reactGrid fill-width fill-height")));

		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofMinutes(7))              
				.pollingEvery(Duration.ofSeconds(20))            
				.ignoring(NoSuchElementException.class);

		fluentWait.until(driver -> {
			try {
				driver.navigate().refresh();		
				base.buttonClick(StatementPostingObj.Btn_ReceiptNo("dyn-headerCell", "RBOTransactionTable_receiptId"));
				base.setData(NormalSaleGoldAndSilverObj.Ele_ERPCustAccntisExactly("FilterField_RBOTransactionTable"), InvoiceNo);
				base.buttonClick(NormalSaleGoldAndSilverObj.Ele_RunNowERP("__RBOTransactionTable_receiptId_ApplyFilters_label"));
				return base.isExists(NormalSaleGoldAndSilverObj.Ele_ERPInvoice(InvoiceNo)); 
			} catch (Exception e) {
				return false;  
			}
		});
		boolean isSuccessful = false;
		int maxAttempts = 5; 
		int attempt = 0;
		String Status = "";

		while (!isSuccessful && attempt < maxAttempts) {
			attempt++;
			System.out.println("Attempt: " + attempt);

			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
			WebElement SearchBox = driver.findElement(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"));
			SearchBox.sendKeys("Validate store transaction");
			SearchBox.sendKeys(Keys.ARROW_RIGHT);
			Thread.sleep(1000);
			SearchBox.sendKeys("s");
			SearchBox.sendKeys(Keys.BACK_SPACE);
			SearchBox.sendKeys("s");		    
			base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
			base.buttonClick(StatementPostingObj.Btn_Add("Add"));
			base.buttonClick(LoginPageObj.Edt_Alert("OK"));
			Thread.sleep(20000);

			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
			SearchBox.sendKeys("Store transaction");
			Thread.sleep(1000);
			SearchBox.sendKeys(Keys.ARROW_RIGHT);
			Thread.sleep(2000);
			SearchBox.sendKeys("s");
			SearchBox.sendKeys(Keys.BACK_SPACE);
			SearchBox.sendKeys("s");

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
			base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
			Thread.sleep(20000);

			wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Ele_ErrorMessage("reactGrid fill-width fill-height")));
			base.buttonClick(StatementPostingObj.Btn_ReceiptNo("dyn-headerCell", "RBOTransactionTable_receiptId"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_ERPCustAccntisExactly("FilterField_RBOTransactionTable"), InvoiceNo);
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_RunNowERP("__RBOTransactionTable_receiptId_ApplyFilters_label"));

			Thread.sleep(5000);
			Status = base.GetValue(NormalSaleGoldAndSilverObj.Ele_WarehouseName("Statement_ValidationStatus")).trim();
			System.out.println("Status found: " + Status);

			if (Status.equalsIgnoreCase(StatementPostingTestData.TC310_Status)) {
				isSuccessful = true;
			} else {
				Thread.sleep(5000); 
				driver.navigate().refresh();
			}
		}
		asrt.assertEquals(Status, StatementPostingTestData.TC310_Status,"Validation status mismatch: Expected is "+Status+" but found "+"Successful"+" in store transaction page");		
		String Time = base.GetValue(NormalSaleGoldAndSilverObj.Ele_WarehouseName("DetailsHeader_TransTime")).trim();
		String Date = base.GetValue(NormalSaleGoldAndSilverObj.Ele_ERPReceiveAmt("Date"));
		System.out.println("TimeStr: " + Time);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a",Locale.ENGLISH);
		LocalTime time = LocalTime.parse(Time, formatter);
		String OneMinuteEarlier = time.minusMinutes(1).format(formatter);
		System.out.println("OneMinuteEarlier:"+OneMinuteEarlier);
		String OneMinuteLater = time.plusMinutes(1).format(formatter);
		System.out.println("OneMinuteLater:"+OneMinuteLater);

		//Step 6: Go to Statements
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		WebElement SearchBox1 = driver.findElement(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"));
		Thread.sleep(1000);
		SearchBox1.sendKeys("Statement");
		SearchBox1.sendKeys(Keys.ARROW_RIGHT); 
		Thread.sleep(1000);

		SearchBox1.sendKeys("s");
		SearchBox1.sendKeys(Keys.BACK_SPACE);
		SearchBox1.sendKeys("s");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");

		//Step 7: Click on new --> enter start date --> enter to date
		wait.until(ExpectedConditions.visibilityOfElementLocated(StatementPostingObj.Btn_Add("SystemDefinedNewButton"))); 
		base.buttonClick(StatementPostingObj.Btn_Add("SystemDefinedNewButton"));
		base.setData(StatementPostingObj.Edt_StoreNo("grid"), UtilityTestData.WarehouseId);
		base.pressKey(StatementPostingObj.Edt_StoreNo("grid"), "ENTER");
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_WarehouseName("TransactionInterval_transFromDate"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("TransactionInterval_transFromDate"), Date);
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("TransactionInterval_transToDate"), Date);

		//Step 8: Enter start time --> enter end time
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("TransactionInterval_transFromTime"), OneMinuteEarlier);
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("TransactionInterval_transToTime"), OneMinuteLater);

		//Step 9: Click on calculate statement
		Thread.sleep(5000);
		base.excuteJsClick(StatementPostingObj.Btn_Calculate("RetailStatementCalculate_label", "RetailEodStatementTable"));
		Thread.sleep(5000);
		base.buttonClick(StatementPostingObj.Btn_Add("Yes"));

		//Step 10: Click on post statement
		//Expected : Check for any errors in my batch job
		Thread.sleep(5000);
		base.excuteJsClick(StatementPostingObj.Btn_Calculate("RetailStatementPost_label", "RetailEodStatementTable"));
		Thread.sleep(5000);
		base.buttonClick(StatementPostingObj.Btn_Add("Yes"));

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), StatementPostingTestData.TC310_BatchJob);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");

		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Transaction("public_fixedDataTableCell_cellContent", "Scheduled start date/time"));
		base.buttonClick(LoginPageObj.Edt_Alert("Sort newest to oldest"));
		String StatementStatus = base.GetValue(NormalSaleGoldAndSilverObj.Ele_StatusERP("2", "Ended"));
		asrt.assertEquals("Ended", StatementStatus, "Statment status is not ended");
	}
}