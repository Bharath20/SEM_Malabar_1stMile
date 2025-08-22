package utilPack;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj;
import objectRepository.GPPSettlementAverageRateSingleSkuMultipleSku_Obj;
import objectRepository.GppOpeningAndCollectionMultiplePlans_Obj;
import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleDiamondandPlatinum_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import objectRepository.OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj;
import testData.CommonData;
import testData.Utility_TestData;
import testPage.Login;



public class AppUtilities  {

	//	protected WebDriver driver;
	//	Assert asrt;
	//	JavascriptExecutor js;
	protected WebDriver driver;
	protected BasePge base;
	protected PdfUtilities PdfUtilities;
	protected Login login;
	Assert asrt;
	JavascriptExecutor js;

	LoginPage_Obj LoginPageObj= new LoginPage_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj=new NormalSalesReturnGoldSilverReturnSaleCounter_Obj(); 
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	NormalSaleDiamondandPlatinum_Obj NormalSaleDiamondandPlatinumObj =new NormalSaleDiamondandPlatinum_Obj();
	OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj = new OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj();
	AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj = new AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj();
	GPPSettlementAverageRateSingleSkuMultipleSku_Obj GPPSettlementAverageRateSingleSkuMultipleSkuObj = new GPPSettlementAverageRateSingleSkuMultipleSku_Obj();
	GppOpeningAndCollectionMultiplePlans_Obj GppOpeningAndCollectionMultiplePlansObj = new GppOpeningAndCollectionMultiplePlans_Obj();

	public AppUtilities(BasePge base) {
		this.base   = base;
		this.driver = base.driver;
		this.login  = new Login(this.driver);
		this.PdfUtilities = new PdfUtilities(this.base);
	}

	/// <summary>
	/// To Get Multiple random SKU Numbers and Excluding the Piece Skus
	/// Author : Neethu & Vishnu
	/// </summary>
	public List<String> SelectMultipleSKUs(int SkuCount, String TransferType, String FromCounter, String MetalType) throws Exception {

		List<String> SelectedSkus = new ArrayList<>();

		// Navigate to SKU counter transfer page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		// Wait until loader disappears
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));

		// Now click the element
		wait.until(ExpectedConditions.elementToBeClickable(LoginPageObj.Edt_AlertText("Purchase"))).click();

		//base.buttonClick(LoginPageObj.Edt_AlertText("Purchase"));
		//base.buttonClick(LoginPageObj.Edt_AlertText("Counter Transfer"));
		base.ClickCondition(LoginPageObj.Edt_AlertText("Counter Transfer"));

		// Select transfer criteria
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"), TransferType);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"), FromCounter);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"), MetalType);

		// Navigate to Product Search
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconSearch"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 padLeft12", "Product search"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		// Combine all SKUs to exclude
		Set<String> ExcludedSkus = new HashSet<>();
		ExcludedSkus.addAll(Utility_TestData.GoldPieceRateSkus);
		ExcludedSkus.addAll(Utility_TestData.DiamondPieceRateSkus);
		ExcludedSkus.addAll(Utility_TestData.SilverPieceRateSkus);
		ExcludedSkus.addAll(Utility_TestData.PlatinumPieceRateSkus);
		ExcludedSkus.addAll(Utility_TestData.RestrictedSkus);
		ExcludedSkus.addAll(Utility_TestData.UncutVirazSkus);

		// Loop over available SKUs on UI and skip excluded or non-numeric ones
		int Index = 1;
		while (SelectedSkus.size() < SkuCount) {
			try {
				String Sku = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUNumber("column1", "h4 ellipsis cell", String.valueOf(Index)));
				if (Sku == null || Sku.trim().isEmpty()) break;

				if (!ExcludedSkus.contains(Sku) && Sku.matches("\\d+")) {
					System.out.println("SKU " + Index + ": " + Sku + " == Selected");
					SelectedSkus.add(Sku);
				} else {
					System.out.println("SKU " + Index + ": " + Sku + " == Skipped (either Piece rate or not numeric)");
				}
			} catch (Exception Ex) {
				System.out.println("No more SKUs found at index " + Index + ". Breaking.");
				break;
			}
			Index++;
		}

		if (SelectedSkus.size() < SkuCount) {
			throw new Exception("Only " + SelectedSkus.size() + " valid SKUs found. Required: " + SkuCount);
		}

		return SelectedSkus;
	}

	/// <summary>
	/// Method to search by customer ID and selecting option from menu bar
	///Author-Christy & Gokul
	/// </summary>	
	public void SearchByCustomerID(String CustomerID,String Option) throws InterruptedException 
	{
		// Wait for loader or overlay to go away
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader"))); 

		// Wait for field to be clickable, then click via JS (if necessary)
		By SearchBox = NormalSaleGoldAndSilverObj.Edt_Name("Search");

		Wait.until(ExpectedConditions.elementToBeClickable(SearchBox));
		base.excuteJsClick(SearchBox); // JS click to focus the field

		// Wait again for it to be interactable
		WebElement element = Wait.until(ExpectedConditions.elementToBeClickable(SearchBox));
		element.clear();  
		element.sendKeys(CustomerID);

		Thread.sleep(3000);

		//	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		//base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Customers"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_CustomerSearch("Customers"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Customers")),"The user is not able to navigate to the customer page");

		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
		for (String id : Utility_TestData.MenuBarOptions) 
		{
			asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions(id)), "The "+id+"  option is not exist in the menu bar");
		}
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions(Option));

		//asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_OGProduct("Main content")), "The corresponding "+Option+" is not displayed to the user on clicking options from the menubar");
	}

	/// <summary>
	/// Method to validate the selected payment after recalling an estimate.Method includes validations for approval code, card number, and the 'Change Due' subpage.
	/////Author-Christy Reji
	/// </summary>
	public String PaymentAfterRecallEstimate(String PaymentMethod) throws InterruptedException {
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_AmountDue("Amount due"));
		Thread.sleep(5000);
		asrt.assertTrue(driver.findElement(NormalSaleGoldAndSilverObj.Ele_Payement("Payment method")).isDisplayed(), "'Payment method' page is not displayed to user on clicking the amount due in line page after recall estimation");
		asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Payment method")), "'Payment method' page is not displayed to user on clicking the amount due in line page after recall estimation");

		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod(PaymentMethod));
		Thread.sleep(3000);

		asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Btn_AddtoCart("paymentView fragment")), "The 'Amount Due' and 'Payment Amount' fields are not visible to the user on the selected payment page.");

		String PaymentAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		Thread.sleep(3000);

		if (PaymentMethod.equals("HDFC") || PaymentMethod.equals("ICICI")) {
			// ApprCode-validation
			asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Appr.Code")),"'Appr.Code' subpage is not displayed to user on clicking return key button from user selected payment method page");
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

			// CardNumber-validation
			asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Card No")), "'Card Number' subpage is not displayed to user after entering appr code");
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			ChangeDueSubpageValidation();
		} else if (PaymentMethod.equals("Cash")) {
			ChangeDueSubpageValidation();
		} else {
			asrt.fail("User is unable to select the payment method after recalling an estimate.");
		}

		return PaymentAmount;
	}
	/// <summary>
	/// Method to validate change due subpage after succesfull Approval code and Card Number validation in recall estimation
	/// Author-Christy Reji
	/// </summary>
	public void ChangeDueSubpageValidation()throws InterruptedException
	{	
		Thread.sleep(2000);
		asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Change due")),"'Change due' subpage is not displayed to user after enter card number details");
		asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_ChangeDueAmount("0.00")),"'Change due' amount is not changed to zero even after appr, card number validations");
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Receipts")),"'Receipt' subpage is not displayed to user after clicking close button from 'Change due' subpage");
	}


	/// <summary>
	/// Method to select the Payment Mode(Sales, Sales Return, Exchange, Purchase) based on the AmountDue.
	/// This Method include sub scenario:
	/// 1. If AmountDue is greater than Zero(like Sales transaction) User can select payment method and can create Receipt.
	/// 2. If AmountDue is less than Zero(like Sales Return transaction) User can select payment method such as Convert to Advance and can create Receipt.
	/// Author : Neethu & Nivya
	/// </summary>
	public void PaymentModeForDiffTransactions(String PaymentMethod,String ApprCode,String Cardnumber, String SalesPersonNo, String SalesPersonName, String DueDate, String NomineeName, String NomineeRelation) throws Exception
	{	
		String AmountDue = base.GetText(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		if (AmountDue != null && !AmountDue.trim().isEmpty()) {
			AmountDue = AmountDue.replaceAll("[^0-9.-]", "").trim();
			double numericAmount = Double.parseDouble(AmountDue);
			System.out.println("The Amount: "+numericAmount);

			if (numericAmount > 0.00) {

				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));

				asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("HDFC (Credit Card)")),"The user is not able to view the payment methods");

				base.buttonClick(LoginPageObj.Edt_AlertText(PaymentMethod));
				Thread.sleep(2000);
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
				Thread.sleep(2000);
				base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),ApprCode);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
				base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),Cardnumber);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

				asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Receipts")),"'Receipt' subpage is not displayed to user after clicking close button from 'Change due' subpage");
			}

			else if (numericAmount < 0.00){

				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
				Thread.sleep(2000);

				asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Convert to Advance")),"The user is not able to view the payment methods");

				Thread.sleep(2000);
				base.buttonClick(LoginPageObj.Edt_AlertText(PaymentMethod));	
				Thread.sleep(4000);
				base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(SalesPersonNo), SalesPersonName);
				base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_DrpdwnYear("win-datepicker-year win-dropdown win-order2 displayOrder3"), DueDate);
				base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Remarks("6"));
				base.setData(NormalSaleGoldAndSilverObj.Ele_Remarks("6"), UtilityTestData.Remarks);
				base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
				Thread.sleep(1000);
				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Nominee name"),NomineeName);
				base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Nominee relation"), NomineeRelation);
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));
				//				base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Nominee address"),NomineeAddress);
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
				base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));	
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
				Thread.sleep(3000);	

				//asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_Payement("Change due")),"'Change due' subpage is not displayed to user after enter card number details");

			}

		}

		else {
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
			Thread.sleep(3000);
			//change due close button after clicking the amount
			if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_closeButtonClick'][class='cancelButton primaryButton']"))) {
				try {
					base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_closeButtonClick'][class='cancelButton primaryButton']"));
					System.out.println("Close button clicked successfully.");
				} catch (Exception e) {
					System.out.println("Failed to click the Close button: " + e.getMessage());
				}
			} else {
				System.out.println("Close button not found, skipping the action.");
			}

			//close button(from Reciept) after the click close button on change due
			if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_ExtensionTemplatedDialogButton1'][class='primaryButton']"))) {
				try {
					base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_ExtensionTemplatedDialogButton1'][class='primaryButton']"));
					System.out.println("Close button clicked successfully in reciept");
				} catch (Exception e) {
					System.out.println("Failed to click the Close button: " + e.getMessage());
				}
			} else {
				System.out.println("Close button not found, skipping the action.");
			}

		}	

	}


	/// <summary>
	/// Method to Void transaction from Cash
	/// </summary>
	///@Author:Gokul.P
	public void VoidTransaction() throws InterruptedException
	{
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_VoidMethod("ButtonGrid2","Void"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_VoidMethod("ButtonGrid2","Void transaction"));
		String ValidationMessage=base.GetText(LoginPageObj.Ele_ErrorMessage("messageText h5"));
		asrt.assertEquals(base.GetText(LoginPageObj.Ele_ErrorMessage("messageText h5")),ValidationMessage,"The warning message "+ValidationMessage+" is not displayed to the user");
		base.buttonClick(LoginPageObj.Btn_SignInButton("Yes"));
		Thread.sleep(1000);
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SingnIn("addCustomerButton")),"The customer is not removed from the transaction page");
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SingnIn("addProductButton")),"The product listed in the cart in the transaction page is not removed");
	}	

	/// <summary>
	/// Method to Void Product in the Cart
	/// </summary>
	///@Author:Gokul.P
	public void VoidProduct(List Option) throws InterruptedException
	{
		int count=0;
		List<WebElement> products= driver.findElements(NormalSaleGoldAndSilverObj.Txt_ProductValidation());	 
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_VoidMethod("ButtonGrid2","Void"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		WebElement hiddenElement=driver.findElement(NormalSaleGoldAndSilverObj.Txt_Quantity("1"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String getSKU = (String) js.executeScript("return arguments[0].textContent;", hiddenElement);      
		for(int j=0;j<Option.size();j++)
		{			
			if(getSKU.equals(Option.get(j)))
			{
				String quantity=products.get(j).getText();
				base.Hover(LoginPageObj.Edt_AlertText(quantity));
				base.buttonClick(NormalSaleGoldAndSilverObj.Fld_Product(quantity));
				base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_VoidMethod("ButtonGrid2","Void product"));			    	
				break;
			}
		}
		Thread.sleep(2000);
		for(int i=0;i<products.size();i++)
		{
			if(products.get(i).isDisplayed())
			{
				count++;					
			}	
		}				
		String totalProduct= base.GetText(NormalSaleGoldAndSilverObj.Txt_TotalProduct());
		asrt.assertEquals(Integer.parseInt(totalProduct), count,"The total number of products displayed in the cart is not equal to the lines"); 		
	}	

	/// <summary>
	/// Extracts structured data (rows and columns) from a dynamic HTML table on the SKU Ingredient page
	/// </summary>	
	public List<Map<String, String>> ExtractTableData(WebDriver driver){
		// Step 1: Create a list to hold each row's data (each row is a Map of columnName -> cellValue)
		List<Map<String, String>> TableData = new ArrayList<>();
		// Step 2: Find all column header elements (assumed to be inside divs with class containing 'column')
		List<WebElement> Headers = driver.findElements(By.cssSelector("div.dataListSubheader > div[class*='column']"));
		// Step 3: Extract and clean the text of each header
		List<String> HeaderNames = Headers.stream()
				.map(h -> h.findElement(By.cssSelector("div.h6.ellipsis")).getText().trim())  // Get trimmed text from nested div
				.collect(Collectors.toList());  // Collect header names into a list
		// Step 4: Find all table row elements (each with class 'dataListLine')
		List<WebElement> Rows = driver.findElements(By.cssSelector("div.dataListLine"));
		// Step 5: Loop through each row element to extract cell values
		for (WebElement Row : Rows) {
			// Step 5a: Get all cell elements inside the current row
			List<WebElement> Cells = Row.findElements(By.cssSelector("div[class*='column'] > div.cell"));
			// Step 5b: Create a map to store columnName -> cellValue for this row
			Map<String, String> RowData = new LinkedHashMap<>();
			// Step 5c: For each cell, map it to its corresponding header (up to the size of the smallest list)
			for (int i = 0; i < Math.min(HeaderNames.size(), Cells.size()); i++) {
				RowData.put(HeaderNames.get(i), Cells.get(i).getText().trim());  // Add entry: HeaderName -> CellText
			}
			// Step 5d: Add this row's data to the main table data list
			TableData.add(RowData);
		}

		// Step 6: Return the fully parsed table data
		return TableData;
	}

	/// <summary>
	/// Fetch and store all values in SKU Ingredient Page (Optimized version with reduced wait time)
	/// </summary>
	public void SKUIngridentTableValues(String Sku, int SkuNumber, Map<String, String> DataMap) throws Exception {

		// Step 1: Click "Add to Cart" to activate the SKU input field
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		Thread.sleep(3000);
		// Step 2: Wait and enter the SKU string into the input field (do not clear existing data)
		//wait.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity")));
		base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), Sku);

		// Step 3: Press return key button to trigger SKU search
		wait.until(ExpectedConditions.elementToBeClickable(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"))).click();

		// Step 4: Adjust zoom level to 40%
		base.setZoom(driver, 40);

		// Step 5: Wait until the data table with SKU details appears on the screen
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.dataListLine")));

		// Step 6: Fetch and store item-level details in DataMap
		DataMap.put("SKU_" + SkuNumber + "_GrossWeight", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightInput"), "value"));
		DataMap.put("SKU_" + SkuNumber + "_Purity", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("Purity"), "value"));
		DataMap.put("SKU_" + SkuNumber + "_TotalCValue", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("valueInput"), "value"));
		DataMap.put("SKU_" + SkuNumber + "_MakingRate", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("makingRateInput"), "value"));
		DataMap.put("SKU_" + SkuNumber + "_MakingValue", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("makingValueInput"), "value"));
		DataMap.put("SKU_" + SkuNumber + "_MakingType", base.getTheSelectedTextInDropDown(NormalSaleGoldAndSilverObj.Sel_DepositType("makingTypeOptions")));
		DataMap.put("SKU_" + SkuNumber + "_WastageQty", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Wastage Qty"), "value"));
		DataMap.put("SKU_" + SkuNumber + "_WastageAmount", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Wastage amount"), "value"));
		DataMap.put("SKU_" + SkuNumber + "_ItemName", base.GetText(By.xpath("//h3[@data-bind='text: SectionHeader']")));
		DataMap.put("SKU_" + SkuNumber + "_Total", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("totalInput"), "value"));
		DataMap.put("SKU_" + SkuNumber + "_HeaderName", base.GetText(By.xpath("//h3[@data-bind='text: SectionHeader']")));
		DataMap.put("SKU_" + SkuNumber + "_CvalueTextField", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("valueInput"), "value"));
		DataMap.put("SKU_" + SkuNumber + "_RateTextField", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("rateInput"), "value"));

		// Step 7: Adjust zoom level to 30% for better visibility
		base.setZoom(driver, 30);

		// Step 8: Extract and flatten table data into DataMap
		List<Map<String, String>> SkuData = ExtractTableData(driver);
		for (int ItemIndex = 0; ItemIndex < SkuData.size(); ItemIndex++) {
			Map<String, String> Row = SkuData.get(ItemIndex);
			for (Map.Entry<String, String> Entry : Row.entrySet()) {
				String Key = "SKU" + SkuNumber + "_ITEM" + (ItemIndex + 1) + "_" + Entry.getKey().toUpperCase().replaceAll("\\s+", "");
				DataMap.put(Key, Entry.getValue());
			}
		}

		// Step 9: Click the "Estimation" button to add the SKU estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// Step 10: Calculate and store weights (StoneWeight, NetWeight, RGWeight)
		double StoneWeight = 0.0, NetWeight = 0.0, TotalRGWeight = 0.0;
		String Rate_RP = "";
		String Rate_RG = "";
		for (int ItemIndex = 0; ItemIndex < SkuData.size(); ItemIndex++) {
			String prefix = "SKU" + SkuNumber + "_ITEM" + (ItemIndex + 1);
			String ItemId = DataMap.getOrDefault(prefix + "_ITEMID", "").trim().toUpperCase();
			String QtyStr = DataMap.getOrDefault(prefix + "_QTY", "0").replaceAll("[^\\d.]", "");
			String Unit = DataMap.getOrDefault(prefix + "_UNIT", "").trim().toLowerCase();
			String Rate = DataMap.getOrDefault(prefix + "_RATE", "0").replaceAll("[^\\d.]", "");

			if (!Rate.isEmpty()) {			
				if (ItemId.equals("RP")) {
					Rate_RP = Rate;
				}
				if (ItemId.equals("RG")) {
					Rate_RG = Rate;
				}
			}
			if (!QtyStr.isEmpty()) {
				double Qty = Math.round(Double.parseDouble(QtyStr) * 1000.0) / 1000.0;

				if (ItemId.equals("RG") || ItemId.equals("RP") || ItemId.equals("RS")) {
					NetWeight += Qty;
					if (ItemId.equals("RG")) {
						double QtyInGrams = Unit.contains("ct") ? Qty * 0.2 : Qty;
						TotalRGWeight += QtyInGrams;
					}
				} else {
					StoneWeight += Unit.contains("ct") ? Qty * 0.2 : Qty;
				}
			}
		}

		// Step 11: Round and store the final computed weight values
		DataMap.put("SKU_" + SkuNumber + "_StoneWeight", String.format("%.3f", StoneWeight));
		DataMap.put("SKU_" + SkuNumber + "_NetWeight", String.format("%.3f", NetWeight));
		DataMap.put("SKU_" + SkuNumber + "_TotalRGWeight", String.format("%.3f", TotalRGWeight));

		DataMap.put("SKU_" + SkuNumber + "_RateRP", Rate_RP);
		DataMap.put("SKU_" + SkuNumber + "_RateRG", Rate_RG);

		// Step 12: Store the SKU header name
		DataMap.put("SKU_" + SkuNumber + "_SKUName", base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader")));

		// Step 13: Click the "Estimation" button again (final confirmation)
		wait.until(ExpectedConditions.elementToBeClickable(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"))).click();

		// Step 14: If first SKU, reset zoom and focus on first SKU box
		if (SkuNumber == 1) {
			base.setZoom(driver, 40);
			wait.until(ExpectedConditions.elementToBeClickable(
					NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220","1"))).click();
		}

		// Extra: Handle "Specify price" modal if it appears
		WebDriverWait AlertWait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//		Thread.sleep(1000);
		try {
			if(AlertWait.until(ExpectedConditions.presenceOfElementLocated(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SpecifyPrice("Specify price")))!= null){
				base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_KeyOne("numpad-control-buttons flexGrow100 flexRow", "1", "1"));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_abc("flexGrow50 flexRow row-enter", "Enter"));
				Thread.sleep(500); // Short delay for modal to close
			}
		} catch (Exception e) {
		}

	}
	/// <summary>
	/// Fetch and store all values in Transaction Lines Page
	/// </summary>	
	public Map<String, String> TransactionSKUsLinesVerify(List<String> SkuList, Map<String, String> ScannedSKUDataMap) {
		Map<String, String> Results = new HashMap<>();

		// 1. Get Lines Count
		String LinesCount = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("LinesField", "h4"));
		//System.out.println("Total LinesCountQty: " + LinesCount);
		Results.put("LinesCount", LinesCount);

		// 2. Gross Weight (Sum of all visible weights)
		List<WebElement> AllSKUElements = driver.findElements(
				NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5")  );

		int SkuCount = AllSKUElements.size();
		double TotalGrossWeight = 0.0;

		for (int i = 1; i <= SkuCount; i++) {
			By SkuLocator = NormalSaleGoldAndSilverObj.Ele_SKUNumber("tillLayout-QuantityField textRight", "h5", String.valueOf(i));
			String ValueStr = base.GetText(SkuLocator);
			ValueStr = ValueStr.replaceAll("[^\\d.-]", "");
			double Grosswt = Double.parseDouble(ValueStr);
			Results.put("GrossWeight" + i, String.format("%.3f", Grosswt));
			if (ValueStr != null && !ValueStr.isEmpty()) {

				try {

					TotalGrossWeight += Double.parseDouble(ValueStr);

				} catch (NumberFormatException e) {
					System.out.println("Invalid number at index " + i + ": " + ValueStr);
				}
			}
		}

		//System.out.println("Total Gross Weight: " + TotalGrossWeight);
		Results.put("GrossWeight", String.format("%.3f", TotalGrossWeight));

		// 3. Net Weight (Only for RG/RP items)
		double TotalNetWeight = 0.0;
		for (int SkuIndex = 1; SkuIndex <= SkuList.size(); SkuIndex++) {
			int ItemNumber = 1;
			while (true) {
				String BaseKey = "SKU" + SkuIndex + "_ITEM" + ItemNumber;
				String ItemId = ScannedSKUDataMap.get(BaseKey + "_ITEMID");

				if (ItemId == null) break;

				if (ItemId.equals("RG") || ItemId.equals("RP") || ItemId.equals("RS")) {
					String QtyStr = ScannedSKUDataMap.getOrDefault(BaseKey + "_QTY", "0");
					QtyStr = QtyStr.replaceAll("[^\\d.]", "");

					try {
						TotalNetWeight += Double.parseDouble(QtyStr);
					} catch (NumberFormatException e) {
						//System.out.println("Invalid QTY at " + BaseKey + ": " + QtyStr);
					}
				}
				ItemNumber++;
			}
		}

		//System.out.println("Total Net Weight (N.Wt. for RG/RP): " + String.format("%.3f", TotalNetWeight));
		Results.put("NetWeight", String.format("%.3f", TotalNetWeight));

		// 4. Subtotal
		String Subtotal = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("SubtotalField", "h4"));
		//System.out.println("Total Subtotal: " + Subtotal);
		Results.put("Subtotal", Subtotal);

		// 5. Tax
		String Tax = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TaxField", "h4"));
		//System.out.println("Total Tax: " + Tax);
		Results.put("Tax", Tax);

		// 6. TotalAmount and NetTotal
		String TotalAmount = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("TotalAmountField", "h4"));
		//System.out.println("Total TotalAmount: " + TotalAmount);
		Results.put("TotalAmount", TotalAmount);

		// 7. Net Total = Total Amount= AmountDue
		String AmountDue = base.GetText(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		Results.put("NetTotal", AmountDue);

		//8. Payment
		String Payments = base.GetText(NormalSaleGoldAndSilverObj.Ele_Lines("PaymentsField", "h4"));
		Results.put("Payments", Payments);

		return Results;
	}

	/// <summary>
	/// Method to Purchase Old Gold. 
	/// This method describes the user can Purchase or Exchange the Old Gold products and can add those products to the cart.
	/// Step for Bug: 'Unexpected Error' Pop up displayed in the Old Gold Details page
	/// Author : Neethu
	/// </summary>	
	public void PurchaseOldGold(String SelectConfiguration, String PurchaseORExchange, String QCPerson, String SmithPerson, String GrossPieces, String GrossWeight) throws Exception
	{
		asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_Transaction("Transaction")), "The user is not able to navigate to Transaction page");

		base.buttonClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),"OG");
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Products"));

		Thread.sleep(2000);
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Products")),"The User is not able to view OG Product in Product Page");
		Thread.sleep(2000);
		driver.findElement(LoginPageObj.Edt_AlertText(UtilityTestData.Product)).click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),SelectConfiguration);

		//Step for Bug: 'Unexpected Error' Pop up displayed in the Old Gold Details page
		WebDriverWait Alertwait = new WebDriverWait(driver, Duration.ofSeconds(7));
		try {
			//			if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
			if (Alertwait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) != null) {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
				System.out.println("Popup appeared and OK button was clicked.");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			} 
		}catch (Exception e) { 
			System.out.println("Error Popup not found, skipping the close button action.");
		}

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(1000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name(PurchaseORExchange));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),QCPerson);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),SmithPerson);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"),GrossPieces);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"),GrossWeight);
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));

	}

	/// <summary>
	/// Creates a GPP account and extracts the Application ID from the generated PDF.
	/// Confirms successful account creation and retrieves the ID for further use.
	/// Author : Nivya
	/// </summary>
	public void GppAccountOpening(String CustomerPhNo, String SchemaCode, String BankProofType, String NomineeName, String NomineeRelation) throws Exception
	{
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
		base.buttonClick(LoginPageObj.Edt_AlertText("New GPP Account"));

		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("GPP account opening")),"The user is not able to navigate the GPP account opening page");

		base.buttonClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),CustomerPhNo);
		Thread.sleep(2000);

		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Customers"));

		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Customers")),"Customers list is not visible to the user");

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconBuy"));

		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("GPP account opening")), "The user is not able to navigate the GPP account opening page");

		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"), SchemaCode);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Bank proof type"), BankProofType);
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Nominee name"),NomineeName);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Nominee relation"), NomineeRelation);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("IsSameAsAbove"));	

		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SingnIn("SearchView_AppBarView_appBarCommandCommand")),"The User is not able to view Ok button to add Gpp Account for customer");

		base.buttonClick(LoginPageObj.Btn_SingnIn("SearchView_AppBarView_appBarCommandCommand"));
		PdfUtilities.GPPpdfValidation();
	}

	/// <summary>
	/// Method to return single or multiple product with details
	/// Author : Anagha
	/// </summary>
	public ReturnDetails ReturnMultipleProductsWithDetails(String ReceiptNo, List<String> RequiredReturnProductList) throws InterruptedException {
		List<String> returnedProductNames = new ArrayList<>();
		List<String> returnedProductQty = new ArrayList<>();
		List<String> returnedProductPrice = new ArrayList<>();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CustomerAdjustment("Customer Adjustments"));

		for (String ItemCategory : RequiredReturnProductList) {
			if (ItemCategory.equalsIgnoreCase("Gold") ||
					ItemCategory.equalsIgnoreCase("Silver") ||
					ItemCategory.equalsIgnoreCase("Diamond") ||
					ItemCategory.equalsIgnoreCase("Uncut") ||
					ItemCategory.equalsIgnoreCase("Platinum") ||
					ItemCategory.equalsIgnoreCase("Precia")) {

				wait.until(ExpectedConditions.elementToBeClickable(NormalSaleGoldAndSilverObj.Btn_CustomerAdjustment("Return transaction"))).click();
				wait.until(ExpectedConditions.elementToBeClickable(NormalSaleGoldAndSilverObj.Btn_abc("numpad-control alphanumeric-mode flexCol", "Enable text editing"))).click();
				base.setData(NormalSaleGoldAndSilverObj.Edt_TextField("numpad-control alphanumeric-mode flexCol", "text"), ReceiptNo);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("numpad-control-buttons flexGrow100 flexRow", "content-return"));

				if (base.isElementPresent(driver, NormalSaleGoldAndSilverObj.Sel_DepositType("multipleTransactionStoreNames"))) {
					try {
						WebElement storeDropdown = wait.until(ExpectedConditions.elementToBeClickable(NormalSaleGoldAndSilverObj.Sel_DepositType("multipleTransactionStoreNames")));
						storeDropdown.click();
						base.selectorByIndex(NormalSaleGoldAndSilverObj.Sel_DepositType("multipleTransactionStoreNames"), 1);

						WebElement regDropdown = wait.until(ExpectedConditions.elementToBeClickable(NormalSaleGoldAndSilverObj.Sel_DepositType("multipleTransactionRegisterNumbers")));
						regDropdown.click();
						base.selectorByIndex(NormalSaleGoldAndSilverObj.Sel_DepositType("multipleTransactionRegisterNumbers"), 1);

						wait.until(ExpectedConditions.elementToBeClickable(LoginPageObj.Ele_ErrorMessage("buttonContainer col no-shrink halfWidth leftmostDialogButton marginTop20"))).click();
					} catch (Exception e) {
						// Log or ignore
					}
				}

				List<WebElement> ReturnableProductList = base.GetElement(NormalSaleGoldAndSilverObj.Ele_Lines("salesOrderLinesView", "enhancedGridDynamicRowTemplate ratio5"));
				List<WebElement> ReturnableProductQty = base.GetElement(NormalSaleGoldAndSilverObj.Ele_Lines("salesOrderLinesView", "enhancedGridDynamicRowTemplate ratio1 textRight"));
				List<WebElement> ReturnableProductPrice = base.GetElement(NormalSaleGoldAndSilverObj.Ele_Lines("salesOrderLinesView", "enhancedGridDynamicRowTemplate ratio2 textRight"));

				for (int i = 0; i < ReturnableProductList.size(); i++) {
					String ProdName = ReturnableProductList.get(i).getText();
					String ProQty = ReturnableProductQty.get(i).getText();
					String ProdPrice = ReturnableProductPrice.get(i).getText();

					if ((ItemCategory.equalsIgnoreCase("Gold") && ProdName.startsWith("G")) ||
							(ItemCategory.equalsIgnoreCase("Silver") && ProdName.startsWith("S")) ||
							(ItemCategory.equalsIgnoreCase("Diamond") && ProdName.startsWith("D")) ||
							(ItemCategory.equalsIgnoreCase("Uncut") && ProdName.startsWith("U")) ||
							(ItemCategory.equalsIgnoreCase("Platinum") && (ProdName.startsWith("PL") || ProdName.startsWith("Pl") || ProdName.startsWith("pl") || ProdName.startsWith("PG"))) ||
							(ItemCategory.equalsIgnoreCase("Precia") && ProdName.toLowerCase().startsWith("pr"))) {

						ReturnableProductList.get(i).click();
						base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Return"));
						returnedProductNames.add(ProdName);
						returnedProductQty.add(ProQty);
						returnedProductPrice.add(ProdPrice);
						break;
					}
				}
			}
		}

		return new ReturnDetails(returnedProductNames, returnedProductQty, returnedProductPrice);
	}
	public class ReturnDetails {
		public List<String> productNames;
		public List<String> productQty;
		public List<String> productPrice;

		public ReturnDetails(List<String> productNames, List<String> productQty, List<String> productPrice) {
			this.productNames = productNames;
			this.productQty = productQty;
			this.productPrice = productPrice;
		}
	}

	///<summary>
	/// Method to get Making Rate and Piece rate items from product search page
	///Author : Anagha, Chandana Babu
	/// </summary>	
	public SKUTypeResult GetMakingAndPcsRateItems(String TransferType, String FromCounter, String MetalType, String Item, String Product) throws InterruptedException 
	{
		base.buttonClick(LoginPageObj.Edt_AlertText("Purchase"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Counter Transfer"));

		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("SKU counter stock transfer")),
				"The user is not able to navigate to the SKU Counter stock Transfer page");

		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"), TransferType);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"), FromCounter);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"), MetalType);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconSearch"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 padLeft12", "Product search"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Product search")),
				"The user is not able to navigate to the Product Search page");


		List<String> SeenSKUs = new  ArrayList<>();
		List<String> AllProductNames =new ArrayList<>();
		List<String> Quantities =new ArrayList<>();

		List<WebElement> AllSKUs = new ArrayList<>();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int SameCount = 0;
		int ScrollPauseTime = 400;

		int PreviousTotalCount = 0;

		while (true) {
			List<WebElement> CurrentRows = driver.findElements(By.xpath("//div[@class='win-container']"));
			int NewItems = 0;

			for (WebElement Row : CurrentRows) {
				String RowText = Row.getText().trim();
				if (RowText.isEmpty()) continue;

				String[] Parts = RowText.split("\\n");
				if (Parts.length < 6) continue;

				String SKU = Parts[0].trim();
				String ProductName = Parts[4].trim();
				String Quantity = Parts[6].trim();

				if (!SeenSKUs.contains(SKU)) {
					SeenSKUs.add(SKU);
					AllProductNames.add(ProductName);
					Quantities.add(Quantity);
					AllSKUs.add(Row);
					NewItems++;
				}
			}

			if (NewItems == 0) {
				SameCount++;
			} else {
				SameCount = 0;
			}

			if (SameCount >= 5) {
				break;
			}

			js.executeScript("document.querySelector('.win-viewport').scrollBy(0, 2500);");
			Thread.sleep(ScrollPauseTime);
		}
		List<String> PcsRateSKUs = new ArrayList<>();
		List<String> MakingRateSKUs = new ArrayList<>();
		for (int i = 0; i < SeenSKUs.size(); i++) {
			if (Item.equals("Gold")) {
				String Qty = Quantities.get(i);
				double QtyNum = Double.parseDouble(Qty);
				String ProductName = AllProductNames.get(i);
				if (QtyNum <= 0.5 && ProductName.equals(Product)) {
					PcsRateSKUs.add(SeenSKUs.get(i));
				}
				if (QtyNum > 0.5 && ProductName.equals(Product)) {
					MakingRateSKUs.add(SeenSKUs.get(i));
				}
			}
			if (Item.equals("Silver")) {
				String Qty = Quantities.get(i);
				double QtyNum = Double.parseDouble(Qty);
				String ProductName = AllProductNames.get(i);
				if (QtyNum <= 0.5 && ProductName.equals(Product)) {
					PcsRateSKUs.add(SeenSKUs.get(i));
				}
				if (QtyNum > 0.5 && ProductName.equals(Product)) {
					MakingRateSKUs.add(SeenSKUs.get(i));
				}
			}
			if (Item.equals("Diamond")) {
				String Qty = Quantities.get(i);
				double QtyNum = Double.parseDouble(Qty);
				String ProductName = AllProductNames.get(i);
				if (QtyNum <= 0.5 && ProductName.equals(Product)) {
					PcsRateSKUs.add(SeenSKUs.get(i));
				}
				if (QtyNum > 0.5 && ProductName.equals(Product)) {
					MakingRateSKUs.add(SeenSKUs.get(i));
				}
			}
			if (Item.equals("Platinum")) {
				String Qty = Quantities.get(i);
				double QtyNum = Double.parseDouble(Qty);
				if (QtyNum <= 1.5 && Product.equals("Null")) {
					PcsRateSKUs.add(SeenSKUs.get(i));
				}
				if (QtyNum > 1.5 && Product.equals("Null")) {
					MakingRateSKUs.add(SeenSKUs.get(i));
				}
			}
		}
		return new SKUTypeResult(PcsRateSKUs, MakingRateSKUs);
	}

	public class SKUTypeResult 
	{
		public List<String> PcsRateSKU;
		public List<String> MakingRateSKU;

		public SKUTypeResult(List<String> pcsRateSKUs, List<String> makingRateSKUs) 
		{
			this.PcsRateSKU = pcsRateSKUs;
			this.MakingRateSKU = makingRateSKUs;
		}
	}

	///<summary>
	/// Method to get required pcs rate items from list
	///Author : Chandana Babu
	/// </summary>
	public List<String> GetRequiredPcsRateItems(int count,List<String> SKUs ) throws InterruptedException
	{
		List<String> RequiredPcsRateSKUs = new ArrayList<>();
		if (SKUs.isEmpty()) {
			System.out.println("SKU List is empty - exiting test early");
		} else {

			for (int i = 0; i < SKUs.size(); i++) {

				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
				Thread.sleep(1000);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));

				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
				Thread.sleep(3000);
				base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), SKUs.get(i));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
				String TextMakingRate = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("makingRateInput"));
				double MakingRate = Double.parseDouble(TextMakingRate);
				String TextMakingValue = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("makingValueInput"));
				double MakingValue = Double.parseDouble(TextMakingValue);
				String TextMakingType = base.getTheSelectedTextInDropDown(NormalSaleGoldAndSilverObj.Sel_DepositType("makingTypeOptions"));
				if (TextMakingType.equals("Total")||TextMakingType.equals("Pcs") && MakingRate==MakingValue) {
					RequiredPcsRateSKUs.add(SKUs.get(i));
				}
				if (RequiredPcsRateSKUs.size()== count) {
					base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
					Thread.sleep(1000);
					base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
					break;
				}
			}
		}
		return RequiredPcsRateSKUs;
	}

	///<summary>
	/// Method to get required Making Rate Items from list
	///Author : Anagha
	/// </summary>
	public List<String> GetRequiredMakingRateItems(int count,List<String> SKUs ) throws InterruptedException
	{
		List<String> RequiredMakingRateSKUs = new ArrayList<>();
		if (SKUs.isEmpty()) {
			System.out.println("SKU List is empty - exiting test early");
		} else {

			for (int i = 0; i < SKUs.size(); i++) {

				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
				Thread.sleep(1000);
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));

				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
				Thread.sleep(3000);
				base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), SKUs.get(i));
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
				String TextMakingType = base.getTheSelectedTextInDropDown(NormalSaleGoldAndSilverObj.Sel_DepositType("makingTypeOptions"));
				if (TextMakingType.equals("Total")||TextMakingType.equals("Net")){
					RequiredMakingRateSKUs.add(SKUs.get(i));
				}
				if (RequiredMakingRateSKUs.size()== count) {
					base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
					Thread.sleep(1000);
					base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
					break;
				}
			}
		}
		return RequiredMakingRateSKUs;
	}	

	/// <summary>
	/// Filters out only the entries belonging to a specific SKU (skuNumber) from the big Data map.
	/// Author: VISHNU MANOJ K
	/// <summary>
	public Map<String, String> ExtractDataForSku(Map<String, String> FullMap, int SkuNumber) {
		Map<String, String> SingleSkuMap = new LinkedHashMap<>();
		String SkuPrefix = "SKU_" + SkuNumber + "_";
		String ItemPattern = "^SKU" + SkuNumber + "_ITEM\\d+_.*";

		for (Map.Entry<String, String> Entry : FullMap.entrySet()) {
			String Key = Entry.getKey();
			if (Key.startsWith(SkuPrefix) || Key.matches(ItemPattern)) {
				SingleSkuMap.put(Key, Entry.getValue());
			}
		}
		return SingleSkuMap;
	}

	///<summary>
	/// Method to get invoice no after normal sale
	///Author : Chandana
	/// </summary>
	public String GetInvoiceNoAfterNormalSale(String CustomerNo, List<String> CounterTransfer) throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3 : Select customer and click in add to sale button
		this.SearchByCustomerID(CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4 : Scan Multiple SKU's, click on Add to cart button and Select any one of the sales agent
		List<String> AllSkuList = new ArrayList<>();
		for(int j = 0; j + 2 < CounterTransfer.size(); j += 3) {
			List<String> RequiredSkuList = this.SelectMultipleSKUs(Integer.parseInt(CounterTransfer.get(j)),"Inter",CounterTransfer.get(j+1),CounterTransfer.get(j+2));
			AllSkuList.addAll(RequiredSkuList);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		}

		Map<String, String> ScannedSKUDataMap = new LinkedHashMap<>();
		for (int i = 0; i < AllSkuList.size(); i++)
		{    		
			this.SKUIngridentTableValues(AllSkuList.get(i), i + 1, ScannedSKUDataMap);
		}	

		//Map<String, String> TransactionFieldValues = this.TransactionSKUsLinesVerify(AllSkuList, ScannedSKUDataMap);		

		//Step 5: Click on the Amount and Select a Payment method(Cash or Card)
		String PaymentAmount = this.PaymentAfterRecallEstimate("HDFC");		

		//Step 6 : Post the Invoice
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		String TaxInvoiceName = PdfUtilities.DownloadAndRenameLatestPDF("Method_TaxInvoice");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		String TaxInvoice =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";
		String InvoiceNo = PdfUtilities.ExtractReceiptNoFrmSaleInvoice(TaxInvoice);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		return InvoiceNo;
		//How to call in your class
		//  String InvoiceNo = appUtils.GetInvoiceNoAfterNormalSale(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC17_CustomerID, NormalSalesReturnGoldSilverReturnSaleCounterTestData.SkuValue);
	}

	///<summary>
	/// Method for Sales Return flow
	///Author : Gokul,Christy,Nivya
	/// </summary>
	public void  SalesReturnFlow(String CustomerID,String InvoiceNo,List<String>Product) throws Exception
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		SearchByCustomerID(CustomerID,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Click on customer adjustment,return transaction,Receipt Number,Return Product,Return Button
		ReturnDetails Details = ReturnMultipleProductsWithDetails(InvoiceNo, Product); //Product taken from Your own testdata

		//Click on the Amount,Payment method,transaction type,due date, Nominee details,Deposit button	
		PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
				UtilityTestData.SalePersonNumber,
				UtilityTestData.SalePersonName,
				UtilityTestData.DueYear,
				UtilityTestData.NomineeName,
				UtilityTestData.NomineeRelation);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		Thread.sleep(1000);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		Thread.sleep(5000);	
		//How to call in your class
		//	appUtils.SalesReturnFlow(NormalSalesReturnGoldSilverReturnSaleCounterTestData.TC17_CustomerID, InvoiceNo, NormalSalesReturnGoldSilverReturnSaleCounterTestData.ReturnProducts);
	}


	//	  ///<summary>
	//
	//	/// Method for Counter In Flow
	//	///Author :Chandana,Christy,Nivya,Gokul
	//	/// </summary>
	public void CounterFlow(String ToCounter, String MetalType,List<String> SKUs, String Terminal) throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(LoginPageObj.Edt_AlertText("Purchase"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Counter Transfer"));
		for(int SkuCount =0; SkuCount < SKUs.size();SkuCount++)
		{
			//Click in Transfer Type,FromCounter,ToCounter,MetalType,Ship,InterReceiptSearch,Receive
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"), "Inter");// Hard Code
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"), "Sales Return");// Hard Code
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("ToCounter"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("ToCounter"), ToCounter);
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"), MetalType);
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Product"), SKUs.get(SkuCount));
			base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("Product"), "ENTER");
			Thread.sleep(2000);
			base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
			base.buttonClick(LoginPageObj.Edt_Alert("Ship"));
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			Thread.sleep(2000);
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"), "Inter");
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconSearch"));
			Thread.sleep(2000);
			base.buttonClick(LoginPageObj.Edt_AlertText("Inter receipt search"));

			// Fetch and Store the Recepit list based on the users terminal
			//			List<WebElement> AllElements = new ArrayList<>();			
			//			AllElements.addAll(base.GetElement(LoginPageObj.Ele_ErrorMessage("win-container win-container-even")));
			//
			//			List<WebElement> oddElements = base.GetElement(LoginPageObj.Ele_ErrorMessage("win-container win-container-odd"));
			//			if (oddElements != null) {
			//				AllElements.addAll(oddElements);
			//			}

			List<WebElement> AllElements = base.GetElement(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_RecipetNumber());
			// Get the SKU number that the user is using.
			for (WebElement Key : AllElements)
			{  
				String ReceiptNumber = Key.getText();
				if (ReceiptNumber.contains(Terminal)) {
					Key.click();
					Thread.sleep(2000);
					String SkuText=base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Txt_Sku("wrapTextWithoutHyphen"));
					if(SkuText.contains(SKUs.get(SkuCount)))
					{
						base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));
						break;
					}
					else{base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_ReciptClose());}
				}
			}

			//Receiving the SKU
			base.buttonClick(LoginPageObj.Btn_SingnIn("Button1"));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Receive("Receive"));
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			Thread.sleep(2000);
			//How to call in your class
			//appUtils.CounterFlow("Gold","Gold",NormalSalesReturnGoldSilverReturnSaleCounterTestData.SkuValue , "5011T02");
		}
	}

	///<summary>
	/// Method to fetch the the current rate of 22k gold
	///Author : Hasna EK
	/// </summary>
	public String Fetch22kCurrentRate(String SkuTestData)throws Exception {
		//Step 1: Click "Add to Cart" to activate the SKU input field
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));

		// Step 2: Set up WebDriverWait for dynamic waits (e.g., table loading)
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Step 3: Enter the SKU string into the input field (do not clear existing data)
		Thread.sleep(3000);
		base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), SkuTestData);

		// Step 4: Press return key button to trigger SKU search
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));

		base.setZoom(driver, 40);
		Thread.sleep(5000);
		// Step 5: Wait until the data table with SKU details appears on the screen
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.dataListLine")));

		// Step 6: Adjust zoom for better visibility if needed
		base.setZoom(driver, 30);
		List<Map<String, String>> SkuData = this.ExtractTableData(driver);
		//
		//		 	String Rate = null;
		//		 	if (!skuData.isEmpty()) {
		//		 	    Map<String, String> firstRow = skuData.get(0); // Only the first row
		//		 	    Rate = firstRow.get("Rate"); // Column header must match exactly

		Map<String, String> Row = SkuData.get(0);
		System.out.println("Extracted Table Row: " + Row);
		System.out.println("Available Keys: " + Row.keySet());
		String RateValue = Row.get("RATE"); 

		System.out.println(" 22k Purity Rate: " + RateValue);
		return RateValue;


	}
	///<summary>
	/// Method to validate the data between Billing screen and Recall Estimate screen
	///Author : Hasna EK
	/// </summary>
	public void ValidateBillingAndRecallScreenData(String EstimationNo, String LinesCount,List<String> ItemNamesBillngScrn,List<String>QtyBillingScrn,String SubTotal,String Tax,String NetTotal)throws Exception {

		System.out.println("====== Validation between Billing screen and Recall estimate screen has started ======  ");
		//To validate Estimation Number
		String EstmnNumberRecallPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstimationNo,EstmnNumberRecallPge,"Mismatch in Estimation number, expected: "+EstimationNo+" but got "+EstmnNumberRecallPge+" in Recall Estimation page ");
		//To validate lines count
		int LinesCountInBilling = Integer.parseInt(LinesCount);
		List<String> ItemNamesFromRecall = base.GetElementTexts(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		int LinesCountInRecall = ItemNamesFromRecall.size();
		asrt.assertEquals(LinesCountInBilling, LinesCountInRecall,"Mismatch in Lines count, expected: "+LinesCountInBilling+" but got "+LinesCountInRecall+" in Recall Estimation page");

		//To validate Item name
		for (int i =0; i<ItemNamesFromRecall.size();i++) 
		{			
			String ItemNameRecallPge = ItemNamesFromRecall.get(i).trim();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
			String ItemNameFromBillingPge = ItemNamesBillngScrn.get(i);
			asrt.assertEquals(ItemNameRecallPge,ItemNameFromBillingPge,"Mismatch in Item name, expected: "+ItemNameFromBillingPge+" but got "+ItemNameRecallPge+" in Recall estimation page");
		}

		List<String> QuantityFromRecall = base.GetElementTexts(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
		for (int i = 0; i < QuantityFromRecall.size(); i++)
		{
			String DisplayedRecallQty = QuantityFromRecall.get(i).trim().replaceAll("[^\\d.]", "");
			double QtyFromRecallPge = Double.parseDouble(DisplayedRecallQty);
			String DisplayedQtyBilling = QtyBillingScrn.get(i).trim().replaceAll("[^\\d.]", "");			
			double QtyFromBillingPge = Double.parseDouble(DisplayedQtyBilling);

			asrt.assertEquals(QtyFromRecallPge,QtyFromBillingPge,"Mismatch in Quantity Value, expected " + QtyFromBillingPge + " but got " + QtyFromRecallPge+ " in Recall Estimation page");
		}

		//To validate Total without tax
		double SubTotalFromBillingPge = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double TotalWithoutTaxRecallPge = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax")));
		asrt.assertEquals(SubTotalFromBillingPge,TotalWithoutTaxRecallPge,"Mismatch in Total Amount value, expected: "+SubTotalFromBillingPge+" but got "+TotalWithoutTaxRecallPge+" in Recall estimation page");

		//To validate tax
		double TaxFromBillingPge = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));
		double TaxFromRecallPge = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));
		asrt.assertEquals(TaxFromBillingPge,TaxFromRecallPge,"Mismatch in Tax amount, expected " + TaxFromBillingPge+" but got "+TaxFromRecallPge+" in Recall Estimation page");

		//To validate sub total
		double TotalAmntBillingPge = Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", ""));
		double SubTotalFromRecallPge = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")));
		asrt.assertEquals(TotalAmntBillingPge,SubTotalFromRecallPge,"Mismatch in Subtotal,expected " +TotalAmntBillingPge+" but got "+SubTotalFromRecallPge+" in Recall estimation page");

		System.out.println("====== Validation between the Billing screen and the Recall Estimate screen is completed ======  ");

	}

	///<summary>
	/// Method to Fetch the Uncut Viraz Sku
	/// Author: Hasna EK
	///</summary>
	public List<String> GetVirazSkus(List<String> productList, int count) throws InterruptedException {

		List<String> RequiredPiece = new ArrayList<>();
		List<String> AvailableProducts = new ArrayList<>(productList);

		while (RequiredPiece.size() < count && !AvailableProducts.isEmpty()) {
			List<String> Piece = getRandomProducts(AvailableProducts, 1);

			String sku = Piece.get(0);

			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Thread.sleep(3000);
			base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), sku);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));

			base.setZoom(driver, 60);
			Thread.sleep(3000);

			if (base.isExists(LoginPageObj.Edt_AlertText("Item details"))) {
				if (!RequiredPiece.contains(sku)) {	
					Thread.sleep(3000);
					//To handle item-specific 'warning' messages displayed on the Item Details page when the "Add to Cart" button is clicked.
					base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
					if (base.isExists( NormalSaleGoldAndSilverObj.Ele_CustomerAdjustment("Sale below cost price is not valid"))) {
						Thread.sleep(3000);
						base.buttonClick(LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton"));
						AvailableProducts.remove(sku);
					}
					else if(base.isElementPresent(driver,NormalSaleGoldAndSilverObj.Ele_SKUSalesRep("margin0","1"))){
						base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("cancelButton secondaryButton"));
						base.buttonClick(LoginPageObj.Btn_SingnIn("Button1Close"));
						RequiredPiece.add(sku);
						AvailableProducts.remove(sku);
					}
					base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
					Thread.sleep(1000);
					base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
				}
			} else {
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
				AvailableProducts.remove(sku);
			}
		}
		System.out.println("RequiredPiece "+RequiredPiece);
		return RequiredPiece;
	}

	///<summary>
	/// Method to Fetch Random Item from list
	///Author : Chandana,Nivya
	/// </summary>
	public  List<String> getRandomProducts(List<String> productList, int count) {
		if (count > productList.size()) {
			throw new IllegalArgumentException("Requested more items than available in the list.");
		}

		Random random = new Random();
		Set<Integer> pickedIndices = new HashSet<>();

		while (pickedIndices.size() < count) {
			int randomIndex = random.nextInt(productList.size());
			pickedIndices.add(randomIndex);
		}

		List<String> result = new ArrayList<>();
		for (int index : pickedIndices) {
			result.add(productList.get(index));
		}

		return result;
	}
	//<summary>
	/// Method to Fetch the Piece Rate Item
	/// Author: Chandana, Nivya
	///</summary>
	public List<String> FetchPieceRateItem(List<String> productList, int count) throws InterruptedException {

		List<String> RequiredPiece = new ArrayList<>();
		List<String> AvailableProducts = new ArrayList<>(productList);

		while (RequiredPiece.size() < count && !AvailableProducts.isEmpty()) {
			List<String> Piece = getRandomProducts(AvailableProducts, 1);

			String sku = Piece.get(0);

			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Thread.sleep(3000);
			base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), sku);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));

			base.setZoom(driver, 40);
			Thread.sleep(3000);

			if (base.isExists(LoginPageObj.Edt_AlertText("Item details"))) {
				if (!RequiredPiece.contains(sku)) {
					RequiredPiece.add(sku);
					AvailableProducts.remove(sku);

					base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
					Thread.sleep(1000);
					base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
				}
			} else {
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
				AvailableProducts.remove(sku);
			}
		}

		return RequiredPiece;
	}

	/// <summary>
	/// This method can be used to fetch skus which have gross weight greater than weight of OG during exchange
	/// Author : Sangeetha
	/// </summary>

	public SKUResult GetOGSaleSku(String TransferType, String FromCounter, String MetalType, double QtySale, int skuCount) throws InterruptedException {		 
		// Navigate to SKU counter transfer page
		base.buttonClick(LoginPageObj.Edt_AlertText("Purchase")); 
		base.buttonClick(LoginPageObj.Edt_AlertText("Counter Transfer"));	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		// Select Transfer Type
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType")));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"), TransferType);

		// Select From Counter 
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter")));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"), FromCounter);

		// Select Metal Type
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType")));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"), MetalType);

		// Navigate to Product Search
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconSearch")); 
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 padLeft12", "Product search")); 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		// Init data collections 
		List<String> SeenSKUs = new ArrayList<>(); 
		List<String> Quantities = new ArrayList<>();
		List<WebElement> AllSKUs = new ArrayList<>();
		List<String> PcsRateSKUs = new ArrayList<>();	
		JavascriptExecutor js = (JavascriptExecutor) driver;	
		int scrollPauseTime = 400;
		int maxTriesWithoutNew = 3;
		int triesWithoutNew = 0;

		while (PcsRateSKUs.size() < skuCount && triesWithoutNew < maxTriesWithoutNew) {
			List<WebElement> CurrentRows = driver.findElements(NormalSaleGoldAndSilverObj.Btn_AddtoCart("win-container"));
			int NewItems = 0;	
			for (WebElement Row : CurrentRows) {
				String RowText = Row.getText().trim();
				if (RowText.isEmpty()) continue;	
				String[] Parts = RowText.split("\\n"); 
				if (Parts.length < 7) continue;

				String SKU = Parts[0].trim(); 
				String Quantity = Parts[6].trim();	
				if (!SeenSKUs.contains(SKU)) { 
					SeenSKUs.add(SKU); 
					Quantities.add(Quantity); 
					AllSKUs.add(Row);	
					double QtyNum = 0;
					try { 
						QtyNum = Double.parseDouble(Quantity);
					} catch (NumberFormatException e) {
						continue;
					}

					if (QtyNum >= QtySale) {
						PcsRateSKUs.add(SKU);
						if (PcsRateSKUs.size() >= skuCount) break;
					}	
					NewItems++;
				} 
			}

			if (NewItems == 0) triesWithoutNew++;
			else triesWithoutNew = 0;
			if (PcsRateSKUs.size() >= skuCount) break;	
			js.executeScript("document.querySelector('.win-viewport').scrollBy(0, 2500);"); 
			Thread.sleep(scrollPauseTime);
		}	
		return new SKUResult(PcsRateSKUs);
	}

	public static class SKUResult {
		public List<String> PcsRateSKU;	
		public SKUResult(List<String> pcsRateSKUs) {
			this.PcsRateSKU = pcsRateSKUs;
		}
	}
	///<summary>
	/// Method to Fetch Sku and Invoice Number from Normal Sale
	/// Author: Nivya
	///</summary>
	public List<String> GetInvoiceNoAfterNormalSaleFetch(String CustomerNo, List<String> CounterTransfer) throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 80);

		// Step 2: Navigate to the Transaction page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));

		// Step 3 : Select customer
		this.SearchByCustomerID(CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4 : Scan Multiple SKUs
		List<String> AllSkuList = new ArrayList<>();
		for (int j = 0; j + 2 < CounterTransfer.size(); j += 3) {
			List<String> RequiredSkuList = this.SelectMultipleSKUs(
					Integer.parseInt(CounterTransfer.get(j)),
					"Inter",
					CounterTransfer.get(j + 1),
					CounterTransfer.get(j + 2)
					);
			AllSkuList.addAll(RequiredSkuList);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		}
		Map<String, String> ItemData  = new HashMap<>();
		for (int i = 0; i < AllSkuList.size(); i++)
		{   Thread.sleep(2000);
		this.SKUIngridentTableValues(AllSkuList.get(i), i + 1,ItemData);

		}

		// Step 5: Payment
		String PaymentAmount = this.PaymentAfterRecallEstimate("HDFC");

		// Step 6 : Post the Invoice
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		String TaxInvoiceName = PdfUtilities.DownloadAndRenameLatestPDF("Method_TaxInvoice");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		String TaxInvoice = System.getProperty("user.dir") + "\\Invoices\\" + TaxInvoiceName;
		String InvoiceNo = PdfUtilities.ExtractReceiptNoFrmSaleInvoice(TaxInvoice);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		// Combine invoice number and SKU list to return
		List<String> result = new ArrayList<>();
		result.add(InvoiceNo);
		result.addAll(AllSkuList);

		return result;
	}

	/// <summary>
	/// This method can be used to fetch Platinum skus which have Item Ids as specified in the code
	/// Author : Hasna EK
	/// </summary>
	public  List<String> ScanMultiplePlatinumSkus(String TransferType, String FromCounter, String MetalType) throws InterruptedException {
		// Navigate to SKU counter transfer page
		base.buttonClick(LoginPageObj.Edt_AlertText("Purchase")); 
		base.buttonClick(LoginPageObj.Edt_AlertText("Counter Transfer"));    
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		// Select Transfer Type
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='TransferType']")));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransferType"), TransferType);

		// Select From Counter 
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='FromCounter']")));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("FromCounter"), FromCounter);

		// Select Metal Type
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='MetalType']")));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("MetalType"), MetalType);

		// Navigate to Product Search
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconSearch")); 
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 padLeft12", "Product search")); 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		// Init data collections 
		List<String> SeenSkus = new ArrayList<>(); 
		List<String> MatchingSkus = new ArrayList<>();
		JavascriptExecutor js = (JavascriptExecutor) driver;    
		int scrollPauseTime = 400;
		int maxTriesWithoutNew = 3;
		int triesWithoutNew = 0;

		while (triesWithoutNew < maxTriesWithoutNew) {
			List<WebElement> CurrentRows = driver.findElements(By.xpath("//div[@class='win-container']"));
			int NewItems = 0;    
			for (WebElement Row : CurrentRows) {
				String RowText = Row.getText().trim();
				if (RowText.isEmpty()) continue;    
				String[] Parts = RowText.split("\\n"); 
				if (Parts.length < 7) continue;

				String SKU = Parts[0].trim(); 
				String ItemName = Parts[2].trim(); 

				if (!SeenSkus.contains(SKU)) { 
					SeenSkus.add(SKU);
					String cleanedItemName = ItemName.trim();
					//					if (cleanedItemId.startsWith("KDGEN") || cleanedItemId.startsWith("FRGEN") 
					//							||cleanedItemId.startsWith("CHGEN")||cleanedItemId.startsWith("FRSLT")) {
					if (cleanedItemName.startsWith("Platinum")) {
						MatchingSkus.add(SKU);
						NewItems++;
					}
				} 
			}

			if (NewItems == 0) triesWithoutNew++;
			else triesWithoutNew = 0;
			js.executeScript("document.querySelector('.win-viewport').scrollBy(0, 2500);"); 
			Thread.sleep(scrollPauseTime);
		}
		return MatchingSkus;
	}

	/// <summary>
	/// This method can be used to fetch Platinum skus based on ingredients(Normal Platinum, Dual Tone Platinum and Platinum with diamond)
	/// Author : Hasna EK
	/// </summary>
	public Map<String, List<String>> FetchPlatinumSkuWithDiffIngredients(List<String> MatchingSkus, int skuCountPerCategory) throws InterruptedException {

		List<String> AvailableProducts = new ArrayList<>(MatchingSkus);
		List<String> DiamondWithPlatinumSku = new ArrayList<>();
		List<String> DualTonePlatinumSku = new ArrayList<>();
		List<String> NormalPlatinumSku = new ArrayList<>();

		while (!AvailableProducts.isEmpty()) {
			List<String> Piece = getRandomProducts(AvailableProducts, 1);
			String Sku = Piece.get(0);
			boolean SkuCategorized = false;

			System.out.println("Selected sku " + Sku);

			this.HamBurgerButtonClick("iconShop");

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			Thread.sleep(3000);
			base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), Sku);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
			base.setZoom(driver, 60);
			Thread.sleep(3000);

			if (base.isExists(LoginPageObj.Edt_AlertText("Item details"))) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.dataListLine")));
				base.setZoom(driver, 60);
				List<Map<String, String>> SkuData = this.ExtractTableData(driver);
				List<String> Ingredients = new ArrayList<>();
				for (Map<String, String> row : SkuData) {
					String ingredient = row.get("ITEM ID");

					if (ingredient == null || ingredient.trim().isEmpty()) continue;
					Ingredients.add(ingredient.trim().toUpperCase());
				}

				boolean IncludesRp = Ingredients.contains("RP");
				boolean IncludesRg = Ingredients.contains("RG");
				boolean IncludesDiamond = Ingredients.contains("DIAMOND");
				boolean IncludesOtherStones = Ingredients.contains("C-OTHERSTONES");

				if (IncludesRp && IncludesRg && IncludesDiamond && DiamondWithPlatinumSku.size() < skuCountPerCategory) {
					DiamondWithPlatinumSku.add(Sku);
					SkuCategorized = true;
				} else if (IncludesRp && IncludesRg && !IncludesDiamond && !IncludesOtherStones && DualTonePlatinumSku.size() < skuCountPerCategory) {
					DualTonePlatinumSku.add(Sku);
					SkuCategorized = true;
				} else if (IncludesRp && !IncludesRg && !IncludesDiamond && !IncludesOtherStones && NormalPlatinumSku.size() < skuCountPerCategory) {
					NormalPlatinumSku.add(Sku);
					SkuCategorized = true;
				}

			} else {
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			}

			AvailableProducts.remove(Sku);

			if (!SkuCategorized) {
				System.out.println("SKU not matched with any category or limit reached: " + Sku);
			}

			this.HamBurgerButtonClick("iconShop");

			if (DiamondWithPlatinumSku.size() >= skuCountPerCategory &&
					DualTonePlatinumSku.size() >= skuCountPerCategory &&
					NormalPlatinumSku.size() >= skuCountPerCategory) {
				System.out.println("All " + skuCountPerCategory + " SKUs per type collected.");
				break;
			}


		}
		// Final categorized map
		Map<String, List<String>> ResultMap = new LinkedHashMap<>();
		ResultMap.put("NORMAL", NormalPlatinumSku);
		ResultMap.put("DUAL", DualTonePlatinumSku);
		ResultMap.put("DIAMOND", DiamondWithPlatinumSku);

		System.out.println("ResultMap "+ResultMap);
		return ResultMap;
	}
	/// <summary>
	/// This method handles the "Specify Price" modal in Recall flow until Transaction page is reached
	/// Author : Hasna EK
	/// </summary>
	public void HandleSpecifyRateModalInRecall() throws InterruptedException {

		int maxAttempts = 10;  // Optional safety limit to avoid infinite loop
		int attempt = 0;

		do {
			try {
				attempt++;
				System.out.println("Attempt " + attempt + ": Checking if on Transaction page...");
				if (base.isExists(By.xpath("//div[@class='h5 marginLeft08 padRight28']//div[text()='Transaction']"))) {
					System.out.println("Transaction page reached.");
					break;
				}
				// Check for "Specify Price" modal
				if (base.isElementPresent(driver, NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_SpecifyPrice("Specify price"))) {
					// Click "1"
					base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_KeyOne(
							"numpad-control-buttons flexGrow100 flexRow", "1", "1"));
					// Click "Enter"
					base.buttonClick(NormalSaleGoldAndSilverObj.Btn_abc("flexGrow50 flexRow row-enter", "Enter"));
					Thread.sleep(500); 
				} else {
					Thread.sleep(500); // Brief wait before re-checking
				}

			} catch (Exception e) {
			}
		} while (attempt < maxAttempts);
	}


	///<summary>
	/// Method to get invoice no and Sku Purity and Product after normal sale
	///Author : Gokul.P
	/// </summary>
	public Map<String, Object> GetInvoiceNoSkuPurityAndProductAfterNormalSale(String CustomerNo, List<String> CounterTransfer) throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3 : Select customer and click in add to sale button
		this.SearchByCustomerID(CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4 : Scan Multiple SKU's, click on Add to cart button and Select any one of the sales agent
		List<String> AllSkuList = new ArrayList<>();
		for(int j = 0; j + 2 < CounterTransfer.size(); j += 3) {
			Thread.sleep(3000);
			List<String> RequiredSkuList = this.SelectMultipleSKUs(Integer.parseInt(CounterTransfer.get(j)),"Inter",CounterTransfer.get(j+1),CounterTransfer.get(j+2));
			AllSkuList.addAll(RequiredSkuList);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		}

		String Purity=null;
		String ProductName=null;
		String ProductCode=null;

		Map<String, String> ScannedSKUDataMap = new LinkedHashMap<>();
		for (int i = 0; i < AllSkuList.size(); i++)
		{    	Thread.sleep(3000);	
		this.SKUIngridentTableValues(AllSkuList.get(i), i + 1, ScannedSKUDataMap);
		Purity=ScannedSKUDataMap.get("SKU_" + (i+1) + "_Purity");
		ProductName=ScannedSKUDataMap.get("SKU_" +(i+1)+ "_SKUName");
		}	

		if (ProductName != null && ProductName.contains(" - ")) {
			String skuCode = ProductName.split(" - ")[0];
			ProductCode = skuCode.substring(0, 2);	     
		}  

		//Step 5: Click on the Amount and Select a Payment method(Cash or Card)
		String PaymentAmount = this.PaymentAfterRecallEstimate("HDFC");		

		//Step 6 : Post the Invoice
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		String TaxInvoiceName = PdfUtilities.DownloadAndRenameLatestPDF("Method_TaxInvoice");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		String TaxInvoice =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";
		String InvoiceNo = PdfUtilities.ExtractReceiptNoFrmSaleInvoice(TaxInvoice);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		Map<String,Object> InvoiceaSkuPurityAndProduct=new HashMap<>();
		InvoiceaSkuPurityAndProduct.put("SKU",AllSkuList);
		InvoiceaSkuPurityAndProduct.put("Invoice",InvoiceNo);		
		InvoiceaSkuPurityAndProduct.put("Purity",Purity);
		InvoiceaSkuPurityAndProduct.put("ProductCode",ProductCode);
		return InvoiceaSkuPurityAndProduct;
	}

	/// <summary>
	/// Fetches Dual Tone Platinum SKUs from the given list.
	/// Adds SKUs to POS, checks their ingredients, and selects those containing both RP and RG,
	/// excluding Diamond and Other Stones. Stops when the required count is reached.
	/// Author : Robin T Abraham
	/// </summary>
	public List<String> FetchDualTonePlatinumSku(List<String> MatchingSkus, int skuCountPerCategory) throws InterruptedException {

		List<String> AvailableProducts = new ArrayList<>(MatchingSkus);
		List<String> DualTonePlatinumSku = new ArrayList<>();
		WebDriverWait HamBurgerWait = new WebDriverWait(driver, Duration.ofSeconds(5));

		while (!AvailableProducts.isEmpty()) {
			List<String> Piece = getRandomProducts(AvailableProducts, 1);
			String Sku = Piece.get(0);
			boolean SkuCategorized = false;

			System.out.println("Selected sku " + Sku);

			base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			Thread.sleep(3000);
			base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), Sku);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
			base.setZoom(driver, 60);
			Thread.sleep(3000);

			if (base.isExists(LoginPageObj.Edt_AlertText("Item details"))) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.dataListLine")));
				base.setZoom(driver, 60);
				List<Map<String, String>> SkuData = this.ExtractTableData(driver);
				List<String> Ingredients = new ArrayList<>();
				for (Map<String, String> row : SkuData) {
					String ingredient = row.get("ITEM ID");

					if (ingredient == null || ingredient.trim().isEmpty()) continue;
					Ingredients.add(ingredient.trim().toUpperCase());
				}

				boolean IncludesRp          = Ingredients.contains("RP");
				boolean IncludesRg          = Ingredients.contains("RG");
				boolean IncludesDiamond     = Ingredients.contains("DIAMOND");
				boolean IncludesOtherStones = Ingredients.contains("C-OTHERSTONES");

				if (IncludesRp && IncludesRg && !IncludesDiamond /*&& !IncludesOtherStones */&& DualTonePlatinumSku.size() < skuCountPerCategory) {
					DualTonePlatinumSku.add(Sku);
					SkuCategorized = true;
				}

			} else {
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			}

			AvailableProducts.remove(Sku);

			if (!SkuCategorized) {
				System.out.println("SKU not matched with any category or limit reached: " + Sku);
			}

			if (DualTonePlatinumSku.size() >= skuCountPerCategory ) {
				System.out.println("All " + skuCountPerCategory + " SKUs per type collected.");
				break;
			}

			try {
				if (HamBurgerWait.until(ExpectedConditions.visibilityOfElementLocated(
						LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"))) != null) {
					Thread.sleep(3000);
					base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconHome"));
				}
			} catch (Exception e) {

				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation(
						"headerSplitViewToggleButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
				Thread.sleep(3000);
				base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
			}
		}

		return DualTonePlatinumSku;
	}


	///Summary
	///Method to validate the items in Recall page having OG, OD,OGT-before performing exchange,sales
	///Author-Christy Reji
	///Summary
	public void ValidateRecallPageOGItem(List<String> ExpectedItemNames, List<String> ItemQuantity, 
			String ExpectedItemNameInOgItemDetailsPge, String ExpectedGrossWeightInItemDetailsPge,
			double ExpectedSubtotal, double ExpectedTax, double ExpectedTotal, int TotalLinesBillingPage,String OGName) {

		List<WebElement> ItemNamesFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		List<WebElement> QuantityFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
		int TotalLinesRecallPage = ItemNamesFromRecall.size();

		for (int k = 0; k < ItemNamesFromRecall.size(); k++) {
			String ActualItemRaw = ItemNamesFromRecall.get(k).getText().trim();

			if (ActualItemRaw.equalsIgnoreCase(ExpectedItemNameInOgItemDetailsPge)) {
				// OGT Item Validation
				asrt.assertEquals(ActualItemRaw, ExpectedItemNameInOgItemDetailsPge,"OG Item Name mismatch: Expected " + ExpectedItemNameInOgItemDetailsPge + " but got " + ActualItemRaw + " in Recall page");

				String QtyStr       = QuantityFromRecall.get(k).getText().trim().replaceAll("[^\\d.]", "");
				double ActualQty    = Double.parseDouble(QtyStr);
				double ExpectedQty  = Double.parseDouble(ExpectedGrossWeightInItemDetailsPge.trim().replaceAll("[^\\d.]", ""));
				asrt.assertEquals(ExpectedQty, ActualQty,"OG Quantity mismatch: Expected " + ExpectedQty + " but got " + ActualQty + " in Recall page");

				System.out.println("The"+OGName+" Item Validation successful from recall page");
			} else {
				String ExpectedItemRaw = ExpectedItemNames.get(k).trim();
				String ActualItemName = ActualItemRaw.contains("-")
						? ActualItemRaw.substring(ActualItemRaw.lastIndexOf("-") + 1).trim()
								: ActualItemRaw.trim();

				String ExpectedItemName = ExpectedItemRaw.contains("-")
						? ExpectedItemRaw.substring(ExpectedItemRaw.lastIndexOf("-") + 1).trim()
								: ExpectedItemRaw.trim();

				System.out.println("The names: " + ExpectedItemNames);
				System.out.println("Expected: " + ExpectedItemRaw + "\nActual: " + ActualItemRaw);

				asrt.assertEquals(ActualItemName, ExpectedItemName,
						"Item Name mismatch: Expected " + ExpectedItemName + " but got " + ActualItemName + " in Recall page");


				String QtyStr           = QuantityFromRecall.get(k).getText().trim().replaceAll("[^\\d.]", "");
				double ActualQty        = Double.parseDouble(QtyStr);
				String ExpectedQtyStr   = ItemQuantity.get(k).trim().replaceAll("[^\\d.]", "");
				double ExpectedQty      = Double.parseDouble(ExpectedQtyStr);
				asrt.assertEquals(ExpectedQty, ActualQty,"Quantity mismatch: Expected " + ExpectedQty + " but got " + ActualQty + " in Recall page");
				System.out.println("The Non"+OGName+" Item Validation successful from recall page");
			}
		}

		double ActualTotalWithoutTax = Math.abs(Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax"))));
		asrt.assertEquals(ExpectedSubtotal, ActualTotalWithoutTax,"Total (Without Tax) mismatch: Expected " + ExpectedSubtotal + " but got " + ActualTotalWithoutTax + " in Recall page");

		double ActualTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));
		asrt.assertEquals(ExpectedTax, ActualTax,"Tax Value mismatch: Expected " + ExpectedTax + " but got " + ActualTax + " in Recall page");

		double ActualSubTotal = Math.abs(Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal"))));
		asrt.assertEquals(ExpectedTotal, ActualSubTotal,"SubTotal mismatch: Expected " + ExpectedTotal + " but got " + ActualSubTotal + " in Recall page");
		asrt.assertEquals(TotalLinesBillingPage, TotalLinesRecallPage,"Total line count mismatch: Expected " + TotalLinesBillingPage + " but got " + TotalLinesRecallPage + " in Recall page");
		System.out.println("======== Validation completed from recall page ==================");
	}


	/// <summary>
	/// Method to Purchase or Exchange Old Silver. 
	/// This method describes the user can Purchase or Exchange the Old Silver products and can add those products to the cart.
	/// Step for Bug: 'Unexpected Error' Pop up displayed in the Old Silver Details page
	/// Author : Neethu
	/// </summary>	
	public void PurchaseOldSilver(String SelectConfiguration, String PurchaseORExchange, String QCPerson, String SmithPerson, String GrossPieces, String GrossWeight) throws Exception
	{
		asrt.assertTrue(base.isExists(NormalSaleGoldAndSilverObj.Ele_Transaction("Transaction")), "The user is not able to navigate to Transaction page");

		base.buttonClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),"OS");
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Products"));

		Thread.sleep(2000);
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Products")),"The User is not able to view OG Product in Product Page");
		Thread.sleep(2000);
		driver.findElement(LoginPageObj.Edt_AlertText(UtilityTestData.SilverProduct)).click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),SelectConfiguration);

		//Step for Bug: 'Unexpected Error' Pop up displayed in the Old Silver Details page
		if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
				System.out.println("Popup appeared and OK button was clicked.");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} catch (Exception e) {
				System.out.println("Failed to click the OK button: " + e.getMessage());
			}
		} else {

			System.out.println("Popup not found, skipping the close button action.");
		}
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name(PurchaseORExchange));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),QCPerson);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),SmithPerson);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"),GrossPieces);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"),GrossWeight);
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));

	}
	/// <summary>
	/// Fetch and store all values in SKU Ingredient Page(For OD/OG/OS) (Optimized version with reduced wait time)
	/// </summary>
	public void ItemDetailsTableValues(int SkuNumber, Map<String, String> DataMap) throws Exception {
		String ItemNameForOD = base.GetText(NormalSaleGoldAndSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = ItemNameForOD.substring(ItemNameForOD.indexOf("-") + 2);
		System.out.println("ExpectedItemNameInItemDetailsPge========="+ExpectedItemNameInItemDetailsPge);
		DataMap.put("SKU_" + SkuNumber + "_SKUName", ExpectedItemNameInItemDetailsPge);
		DataMap.put("SKU_" + SkuNumber + "_GrossWeight", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"), "value"));
		DataMap.put("SKU_" + SkuNumber + "_Total", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("ogptotalamt"), "value"));
		// Step 8: Extract and flatten table data into DataMap
		base.setZoom(driver, 20);
		List<Map<String, String>> SkuData = ExtractTableData(driver);
		for (int ItemIndex = 0; ItemIndex < SkuData.size(); ItemIndex++) {
			Map<String, String> Row = SkuData.get(ItemIndex);
			for (Map.Entry<String, String> Entry : Row.entrySet()) {
				String Key = "SKU" + SkuNumber + "_ITEM" + (ItemIndex + 1) + "_" + Entry.getKey().toUpperCase().replaceAll("\\s+", "");
				DataMap.put(Key, Entry.getValue());
				System.out.println(Key + " = " + Entry.getValue());
			}
		}

		// Step 10: Calculate and store weights (StoneWeight, NetWeight, RGWeight)
		double StoneWeight = 0.0, NetWeight = 0.0, TotalRGWeight = 0.0;
		for (int ItemIndex = 0; ItemIndex < SkuData.size(); ItemIndex++) {
			String prefix = "SKU" + SkuNumber + "_ITEM" + (ItemIndex + 1);
			String ItemId = DataMap.getOrDefault(prefix + "_ITEMID", "").trim().toUpperCase();
			String QtyStr = DataMap.getOrDefault(prefix + "_QTY", "0").replaceAll("[^\\d.]", "");
			String Unit = DataMap.getOrDefault(prefix + "_UNIT", "").trim().toLowerCase();

			if (!QtyStr.isEmpty()) {
				double Qty = Math.round(Double.parseDouble(QtyStr) * 1000.0) / 1000.0;

				if (ItemId.equals("RG") || ItemId.equals("RP") || ItemId.equals("RS")) {
					NetWeight += Qty;
					if (ItemId.equals("RG")) {
						double QtyInGrams = Unit.contains("ct") ? Qty * 0.2 : Qty;
						TotalRGWeight += QtyInGrams;
					}
				} else {
					StoneWeight += Unit.contains("ct") ? Qty * 0.2 : Qty;
				}
			}
		}

		// Step 11: Round and store the final computed weight values
		DataMap.put("SKU_" + SkuNumber + "_StoneWeight", String.format("%.3f", StoneWeight));
		DataMap.put("SKU_" + SkuNumber + "_NetWeight", String.format("%.3f", NetWeight));
		DataMap.put("SKU_" + SkuNumber + "_TotalRGWeight", String.format("%.3f", TotalRGWeight));
	}

	///<summary>
	/// Method to execute the Recall Estimate and process estimation steps
	///Author : Hasna EK
	/// </summary>
	public void RecallEstimateProcess(String EstimationNo, String TransactionType) throws Exception {

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"),EstimationNo);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstimationNo);
		Thread.sleep(1000);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstimationNo));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		//Verify whether the recalled item is the same as the item in the cart.
		String EstmnNumberRecallPge = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstimationNo,EstmnNumberRecallPge,"Mismatch in Estimation number, expected: "+EstimationNo+" but got "+EstmnNumberRecallPge+" in Recall Estimation page ");

		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), TransactionType);

		// Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 
	}

	///<Summary>
	///Method to click on the Hamburger Icon
	///@Author: Gokul.P
	///</Summary>
	public void HamBurgerButtonClick(String NavigationIcons) throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));

		try {
			Thread.sleep(3000);		
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Txt_Sku(NavigationIcons));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		}catch(Exception e) 
		{
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			Thread.sleep(3000);
			base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Txt_Sku(NavigationIcons));
		}		
	}
	/// <summary>
	/// Method to search by customer ID and selecting option from menu bar for GPP Customer
	///Author-Jhoncy
	/// </summary>	
	public void SearchByCustomerIDForGPP(String CustomerID,String Option) throws InterruptedException
	{
		// Wait for loader or overlay to go away
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader")));

		// Wait for field to be clickable, then click via JS (if necessary)
		By SearchBox = NormalSaleGoldAndSilverObj.Edt_Name("Search");

		Wait.until(ExpectedConditions.elementToBeClickable(SearchBox));
		base.excuteJsClick(SearchBox); // JS click to focus the field

		// Wait again for it to be interactable
		WebElement element = Wait.until(ExpectedConditions.elementToBeClickable(SearchBox));
		element.clear();  
		element.sendKeys(CustomerID);

		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_CustomerSearch("Customers"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Customers")),"The user is not able to navigate to the customer page");

		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions(Option));
	}
	/// <summary>
	/// Method to Create customer offer advance
	/// Author: Chandana Babu
	/// </summary>	
	public Map<String, String> CreateCustomerOfferAdvance(String CustomerID,String Pieces, String GrossWeight, double DepositPercent,String Company, String Location) throws InterruptedException
	{
		Map<String, String> Details = new HashMap<>();
		//Click on advance-->customer order
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Customer Order"));

		//Search customer and click on add to customer order
		this.SearchByCustomerIDForGPP(CustomerID, UtilityTestData.AddToCustomerOrder);

		//Search CST and click on the CST_Order
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),UtilityTestData.ProductOfferAdvance);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_CustomerSearch("Products"));
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("dataListLine"));

		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Btn_AddtoCart("dataListLine")));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("dataListLine"));
			System.out.println("Clicked again on CST orderprod");
		}
		catch(TimeoutException e) {
			System.out.println("Not again Clicked on CST orderprod");
		}

		try {
			if (Wait.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Ele_Payement("Error")))!=null) {
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			}
		}catch(TimeoutException e) {
			System.out.println("Error Message not found");
		}

		//Click on "Add to order line"
		Thread.sleep(1000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("win-commandingsurface-ellipsis win-appbar-ellipsis"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to order line"));

		//Select the 'Is offer order' checkbox
		base.buttonClick(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_CheckBox("checkbox", "IsofferChkBox"));

		//Choose the company and invent location
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col", "viewModel.IsOrderEntry"), Company);
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col stretch", "viewModel.IsOrderEntry"), Location);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Sales person"), UtilityTestData.SalePersonName);

		//Select the order line
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		//Select the ingredient line
		base.scrollToElement(LoginPageObj.Ele_ErrorMessage("win-itembox"));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		//Click on edit ingredient line
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("SimpleProductDetailsView_EditSlDtlappBarCommandCommand"));

		//Enter pieces and weight
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: Pieces"),Pieces);
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: Weight"),GrossWeight);

		//Click ok button
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		double TotalAmount = Double.parseDouble(base.GetText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_TotalAmount("Total Amount", "h4 ellipsis cell")));
		String AdvanceAmount = Double.toString(TotalAmount);
		double GoldRate = TotalAmount/Double.parseDouble(GrossWeight);
		String FixedGoldRate = String.format("%.2f",GoldRate);

		//Click on willing to pay button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("SimpleProductDetailsView_appWillToPayCommandCommand"));

		//Enter the amount
		String WillingToPayAmount = Double.toString(TotalAmount*DepositPercent);

		System.out.println("WillingToPayAmount  " + WillingToPayAmount);

		Thread.sleep(1000);
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Amount("value: WILLAMT", "number"), WillingToPayAmount);
		Thread.sleep(1000);
		String OfferExpiryDate= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: SDATE"));
		String ValidityDays = base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: SDAYS")).replaceAll("\\D+", "");;

		//Click on apply button
		base.buttonClick(LoginPageObj.Btn_SignInButton("APPLY"));

		//Step 17: Click on edit line
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Txt_Sku("win-structuralnodes win-selectionmode"));
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_EditSlappBarCommandCommand"));

		//Enter the line delivery date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate ExpiryDate = LocalDate.parse(OfferExpiryDate, formatter);
		LocalDate targetDate = ExpiryDate.minusDays(1);
		String Day = Integer.toString(targetDate.getDayOfMonth());
		String Month = Integer.toString(targetDate.getMonthValue() - 1);
		String Year = Integer.toString(targetDate.getYear());		
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-date"), Day);
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-month"), Month);
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-year"), Year);

		//Enter remarks
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Remarks("value: Remarks"), UtilityTestData.Remarks);

		//Click on OK button
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		//Add nominee details, Select a sales person
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_NomineeDetailsAppBarCommandCommand"));
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeName"),UtilityTestData.NomineeName);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: NomineeRelationArr"), UtilityTestData.NomineeRelation);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));	
		String Address = base.GetText(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"));
		if(Address == "") {
			base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"),UtilityTestData.NomineeAddress);
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}
		else {
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}

		//Click on save order button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_appBarCommandCommand"));
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("YES"));
		String Message = base.GetText(NormalSaleDiamondandPlatinumObj.Ele_GrossWeight("text: content"));
		String OrderId = Message.split(" - ")[1].split(" ")[0];
		//System.out.println(OrderId);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		Details.put("OrderId",OrderId);
		Details.put("AdvanceAmount",AdvanceAmount);
		Details.put("WillingToPayAmount",WillingToPayAmount);
		Details.put("ValidityDays", ValidityDays);
		Details.put("OfferExpiryDate", OfferExpiryDate);
		Details.put("FixedGoldRate", FixedGoldRate);

		return Details;
	}
	/// <summary>
	/// Method to Create Normal Advance 
	/// Author: Chandana Babu
	/// </summary>
	public String CreateNormalAdvance(String CustomerNo, String AdvanceAmount, String SalesPerson, String DueYear) throws Exception {
		//Select customer and  click on add to sale button
		Thread.sleep(1000);
		this.SearchByCustomerID(CustomerNo, UtilityTestData.MenuBarOptions[0]);
		Thread.sleep(3000);
		//Click on advance button at the transaction screen
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("text semilight primaryFontColor","Advance"));
		//Click on advance collection button
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance Collection"));
		Thread.sleep(3000);
		//select deposit type as normal
		//Enter deposit amount
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),AdvanceAmount);
		//Select sales person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson("4"),SalesPerson);
		//Select due date
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_DrpdwnYear("win-datepicker-year win-dropdown win-order2 displayOrder3"), DueYear);
		//Enter remarks
		base.setData(NormalSaleGoldAndSilverObj.Ele_Remarks("6"),UtilityTestData.Remarks);
		//Add nominee details
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
		Thread.sleep(1000);
		//Enter nominee name
		//Select nominee relation
		//Click on check box(Same as customer address)					
		//Click on OK button
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Nominee name"), UtilityTestData.NomineeName);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Nominee relation"), UtilityTestData.NomineeRelation);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		//Click on deposit button
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		Thread.sleep(3000);
		//Click on amount
		//select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String AdvanceAmountPaid = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),UtilityTestData.ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),UtilityTestData.CardNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		return AdvanceAmountPaid;
	}
	/// <summary>
	/// Find Board Rate Using Skus RG/ RP /RS
	/// Author: Vishnu Manoj K
	/// </summary>
	public void BoardRateFromSkuRporRgorRs(String Sku, int SkuNumber, Map<String, String> DataMap) throws Exception {

		// Step 1: Click "Add to Cart" to activate the SKU input field
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		Thread.sleep(3000);
		// Step 2: Wait and enter the SKU string into the input field (do not clear existing data)
		//wait.until(ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity")));
		base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), Sku);

		// Step 3: Press return key button to trigger SKU search
		wait.until(ExpectedConditions.elementToBeClickable(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"))).click();

		// Step 4: Adjust zoom level to 40%
		base.setZoom(driver, 40);

		// Step 5: Wait until the data table with SKU details appears on the screen
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.dataListLine")));

		// Step 6: Fetch and store item-level details in DataMap
		DataMap.put("SKU_" + SkuNumber + "_Purity", base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("Purity"), "value"));

		// Step 7: Adjust zoom level to 30% for better visibility
		base.setZoom(driver, 30);

		// Step 8: Extract and flatten table data into DataMap
		List<Map<String, String>> SkuData = ExtractTableData(driver);
		for (int ItemIndex = 0; ItemIndex < SkuData.size(); ItemIndex++) {
			Map<String, String> Row = SkuData.get(ItemIndex);
			for (Map.Entry<String, String> Entry : Row.entrySet()) {
				String Key = "SKU" + SkuNumber + "_ITEM" + (ItemIndex + 1) + "_" + Entry.getKey().toUpperCase().replaceAll("\\s+", "");
				DataMap.put(Key, Entry.getValue());
			}
		}

		// Step 9: Calculate RG/RS/RP
		double StoneWeight = 0.0, NetWeight = 0.0, TotalRGWeight = 0.0;
		String Rate_RP = "";
		String Rate_RG = "";
		String Rate_RS = "";
		for (int ItemIndex = 0; ItemIndex < SkuData.size(); ItemIndex++) {
			String prefix = "SKU" + SkuNumber + "_ITEM" + (ItemIndex + 1);
			String ItemId = DataMap.getOrDefault(prefix + "_ITEMID", "").trim().toUpperCase();
			String QtyStr = DataMap.getOrDefault(prefix + "_QTY", "0").replaceAll("[^\\d.]", "");
			String Unit = DataMap.getOrDefault(prefix + "_UNIT", "").trim().toLowerCase();
			String Rate = DataMap.getOrDefault(prefix + "_RATE", "0").replaceAll("[^\\d.]", "");

			if (!Rate.isEmpty()) {			
				if (ItemId.equals("RP")) {
					Rate_RP = Rate;
				}
				if (ItemId.equals("RG")) {
					Rate_RG = Rate;
				}
				if (ItemId.equals("RS")) {
					Rate_RS = Rate;
				}
			}
			if (!QtyStr.isEmpty()) {
				double Qty = Math.round(Double.parseDouble(QtyStr) * 1000.0) / 1000.0;

				if (ItemId.equals("RG") || ItemId.equals("RP") || ItemId.equals("RS")) {
					NetWeight += Qty;
					if (ItemId.equals("RG")) {
						double QtyInGrams = Unit.contains("ct") ? Qty * 0.2 : Qty;
						TotalRGWeight += QtyInGrams;
					}
				} else {
					StoneWeight += Unit.contains("ct") ? Qty * 0.2 : Qty;
				}
			}
		}

		DataMap.put("SKU_" + SkuNumber + "_RateRP", Rate_RP);
		DataMap.put("SKU_" + SkuNumber + "_RateRG", Rate_RG);
		DataMap.put("SKU_" + SkuNumber + "_RateRS", Rate_RS);
	}



	/// <summary>			/// Method to Add random SKU from the Test Data  list 
	/// Author: Vishnu RR
	/// </summary>	


	private static List<String> unusedSKUs = new ArrayList<>();

	public static String getRandomSKU(List<String> skuList) {
		if (unusedSKUs.isEmpty()) {
			unusedSKUs = new ArrayList<>(skuList); // Reset when all are used
		}
		Random rand = new Random();
		String sku = unusedSKUs.remove(rand.nextInt(unusedSKUs.size())); // Pick & remove from this round
		return sku;
	}

	/// <summary>
	///	Method to Create customer offer advance with weight booked
	/// Method to Create customer offer advance
	/// Author: Hasna EK
	/// </summary>
	public Map<String, String> CreateWeightBookedOfferAdvance(String CustomerId,String Pieces, String GrossWeight, String Company, String Location, double DepositePercentage) throws Exception  {	
		Map<String, String> Details = new HashMap<>();

		//Step 1: Login to POS
		//		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		//		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		this.HamBurgerButtonClick("iconShop");

		Details = this.CreateCustomerOfferAdvance(CustomerId,Pieces, GrossWeight,DepositePercentage, Company, Location);

		//Step 23: Go to transactions, Search customer, Click on Add to sale
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		this.SearchByCustomerID(CustomerId ,UtilityTestData.MenuBarOptions[0]);

		//Step 24: Choose advance-->advance collection
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Advance Collection"));

		//Step 25: Choose type as order
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"), "Order");

		//Step 26: Select the transaction number from the drop down
		String OrderId = Details.get("OrderId");
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OrderId);

		//Step 27: Enter the deposit amount
		String AdvanceAmount = Details.get("AdvanceAmount");
		String WillingToPayAmount = Details.get("WillingToPayAmount");
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"), WillingToPayAmount);

		System.out.println(WillingToPayAmount +" WillingToPayAmount");
		System.out.println(AdvanceAmount +" AdvanceAmount");

		//Step 28: Choose the sales person		
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"), UtilityTestData.SalePersonName);

		//Step 29: Click on deposit button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Step 30: Click on the Amount
		Thread.sleep(3000);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 31: Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Step 32: Post the Invoice
		Thread.sleep(5000);
		String NewPdfName = PdfUtilities.DownloadAndRenameLatestPDF("OfferAdvance");
		Map<String, String> IntialAdvanceReceiptPdf   = PdfUtilities.OfferAdvancePdfValidation();
		Details.put("AdvanceReceived",IntialAdvanceReceiptPdf.get("AdvanceReceived"));
		return Details;
	}	
	/// <summary>
	/// Method for multiple OG Converted to Normal Advance
	/// Author-Jhoncy
	/// </summary>	
	public String MultipleOGConvertedToNormalAdvance(String CustomerID) throws Exception
	{
		this.HamBurgerButtonClick("iconShop");
		//Select customer and click on add to sale button
		Thread.sleep(2000);
		this.SearchByCustomerID(CustomerID,UtilityTestData.MenuBarOptions[0]);

		//Select OG
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),UtilityTestData.OGProduct);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_CustomerSearch("Products"));

		//Click on OG own and choose purchase
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_OP("dataListLine",UtilityTestData.Product));

		//Select configuration
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_Configuration("col grow"),UtilityTestData.Purity22);

		//Click on add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		//To Handle the Unexpected Error
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		By CartChangedAlert = LoginPageObj.Btn_SignInButton(" Please try again later.");
		By OkBtn = LoginPageObj.Btn_SignInButton("OK");

		if (base.isElementPresent(driver, CartChangedAlert)) {		
			try {
				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
				OkButton.click();
			}
			catch (TimeoutException e) {
			}
		}

		//Note:OGPurchase
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("NPurchase"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("NPurchase"));

		//Select QC Person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("qcInput"),UtilityTestData.QCAndSmithPerson);

		//Select Smith person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("smInput"),UtilityTestData.QCAndSmithPerson); 

		//Enter piece value
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("piecesogp"),UtilityTestData.GrossPieces);

		//Enter gross weight
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightogp"),UtilityTestData.GrossWeight);

		//Click on Calculate button
		base.scrollToElementtoCenter(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Calculate"));

		//Click on Add to cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		Thread.sleep(1000);
		base.excuteJsClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Select payment method as convert advance
		//Choose normal advance
		//Select sales person
		//Select the due date and Enter remarks
		//Click on nominee details
		//Enter nominee name, nominee relation, Click on checkbox(same as customer address)Click on ok button and deposit button
		//Click on deposit button
		Thread.sleep(3000);
		String PaymentAmount = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		String OGAmountDue = PaymentAmount.replaceAll("[^\\d.]", "");
		System.out.println(OGAmountDue);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		Thread.sleep(2000);

		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Convert to Advance")),"The user is not able to view the payment methods");

		Thread.sleep(2000);
		base.buttonClick(LoginPageObj.Edt_AlertText(UtilityTestData.PaymentMode));	
		Thread.sleep(4000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(UtilityTestData.SalePersonNumber), UtilityTestData.SalePersonName);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_DrpdwnYear("win-datepicker-year win-dropdown win-order2 displayOrder3"), UtilityTestData.DueYear);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Remarks("6"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Remarks("6"), UtilityTestData.Remarks);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Nominee name"),UtilityTestData.NomineeName);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Nominee relation"), UtilityTestData.NomineeRelation);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));	
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		Thread.sleep(3000);	

		//Warning handling if present
		try {
			WebDriverWait ShortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
			WebElement Warning = ShortWait.until( ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Ele_Payement("Warning")) );
			base.excuteJsClick(LoginPageObj.Btn_SignInButton("OK"));
		} catch (Exception e) {
			// Warning not found
		}
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		return OGAmountDue;
	}

	/// <summary>
	/// Method to validate the items in Recall page having OG, OD, OGT - before performing exchange or sales
	/// Author - Christy Reji
	/// Modified - Added support for multiple OG items with correct weight mapping
	/// </summary>
	public void RecallEstimateItemValidation(
			List<String> ExpectedItemNames,          // All expected item names (Billing page)
			List<String> ItemQuantity,               // All expected item quantities (Billing page)
			List<String> ExpectedOGItemNames,        // OG item names to validate (in same order as their weights)
			List<String> ExpectedGrossWeights,       // OG item weights to validate (same order as OG names)
			double ExpectedSubtotal,
			double ExpectedTax,
			double ExpectedTotal,
			int TotalLinesBillingPage,
			String OGName) {

		List<WebElement> ItemNamesFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		List<WebElement> QuantityFromRecall  = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));

		System.out.println("ItemNamesFromRecall: " + ItemNamesFromRecall);
		System.out.println("QuantityFromRecall: " + QuantityFromRecall);

		int TotalLinesRecallPage = ItemNamesFromRecall.size();
		int OgIndex = 0; // Track which OG item we're validating

		for (int k = 0; k < ItemNamesFromRecall.size(); k++) {
			String ActualItemRaw = ItemNamesFromRecall.get(k).getText().trim();

			// CASE 1: OG item validation
			if (ExpectedOGItemNames.contains(ActualItemRaw)) {
				String ExpectedOGName      = ExpectedOGItemNames.get(OgIndex);
				String ExpectedOGWeightStr = ExpectedGrossWeights.get(OgIndex);

				// Validate OG item name
				asrt.assertEquals(ActualItemRaw,ExpectedOGName,"The "+OGName+ "Item Name mismatch: Expected " + ExpectedOGName + " but got " + ActualItemRaw + " in Recall page");

				// Validate OG quantity
				String QtyStr = QuantityFromRecall.get(k).getText().trim().replaceAll("[^\\d.]", "");
				double ActualQty = Double.parseDouble(QtyStr);
				double ExpectedQty = Double.parseDouble(ExpectedOGWeightStr);

				asrt.assertEquals(ActualQty, ExpectedQty,"OG Quantity mismatch: Expected " + ExpectedQty + " but got " + ActualQty + " in Recall page");
				System.out.println("The " + OGName + " Item Validation successful from recall page for item: " + ExpectedOGName);

				OgIndex++; // Move to the next OG name & weight
			}
			// CASE 2: Non-OG item validation
			else {
				String ExpectedItemRaw = ExpectedItemNames.get(k).trim();

				String ActualItemName = ActualItemRaw.contains("-")
						? ActualItemRaw.substring(ActualItemRaw.lastIndexOf("-") + 1).trim()
								: ActualItemRaw.trim();

				String ExpectedItemName = ExpectedItemRaw.contains("-")
						? ExpectedItemRaw.substring(ExpectedItemRaw.lastIndexOf("-") + 1).trim()
								: ExpectedItemRaw.trim();

				System.out.println("The names: " + ExpectedItemNames);
				System.out.println("Expected: " + ExpectedItemRaw + "\nActual: " + ActualItemRaw);

				asrt.assertEquals(ActualItemName,ExpectedItemName,"Item Name mismatch: Expected " + ExpectedItemName + " but got " + ActualItemName + " in Recall page");

				String QtyStr         = QuantityFromRecall.get(k).getText().trim().replaceAll("[^\\d.]", "");
				double ActualQty      = Double.parseDouble(QtyStr);
				String ExpectedQtyStr = ItemQuantity.get(k).trim().replaceAll("[^\\d.]", "");
				double ExpectedQty    = Double.parseDouble(ExpectedQtyStr);

				asrt.assertEquals( ExpectedQty, ActualQty, "Quantity mismatch: Expected " + ExpectedQty + " but got " + ActualQty + " in Recall page");
				System.out.println("The Non-" + OGName + " Item Validation successful from recall page");
			}
		}

		// Totals validation
		double actualTotalWithoutTax = Math.abs(Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax"))));
		asrt.assertEquals(ExpectedSubtotal, actualTotalWithoutTax, "Total (Without Tax) mismatch: Expected " + ExpectedSubtotal + " but got " + actualTotalWithoutTax + " in Recall page");

		double actualTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));
		asrt.assertEquals(ExpectedTax, actualTax, "Tax Value mismatch: Expected " + ExpectedTax + " but got " + actualTax + " in Recall page");

		double actualSubTotal = Math.abs(Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal"))));
		asrt.assertEquals(ExpectedTotal, actualSubTotal, "SubTotal mismatch: Expected " + ExpectedTotal + " but got " + actualSubTotal + " in Recall page");
		asrt.assertEquals(TotalLinesBillingPage, TotalLinesRecallPage, "Total line count mismatch: Expected " + TotalLinesBillingPage + " but got " + TotalLinesRecallPage + " in Recall page");

		System.out.println("======== Validation completed from recall page ==================");
	}
	// <summary>
	// Method to Open any GPP scheme and take first collection
	// Author: Chandana Babu
	// </summary>
	public String OpenGppAndTakeCollection(String CustomerNo, String Scheme, String InstallmentAmount,String SchemeCode,String IdProof, String IdProofNo) throws Exception {
		//Go to GPP
		base.ClickCondition(LoginPageObj.Edt_AlertText("GPP"));
		//Select New GPP Account Opening
		WebDriverWait Wait =new WebDriverWait(driver, Duration.ofSeconds(50));
		Wait.until(ExpectedConditions.presenceOfElementLocated(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account")));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("New GPP Account"));
		//Search customer phone number
		//Add to GPP ac open
		this.SearchByCustomerID(CustomerNo,UtilityTestData.MenuBarOptions[6]);
		//Select scheme code
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SchmTypeOptions"),Scheme);
		//Enter installment amount
		Thread.sleep(2000);
		base.clearData(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("InstlamtInput"), InstallmentAmount);
		//Select sales person
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("SalesPersonInput"),UtilityTestData.SalePersonNameWithNo);
		String IdProofNumber= base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("Idproofno1"));
		if (IdProofNumber == "") {
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("IdTypeOptions"), IdProof);
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("Idproofno1"), IdProofNo);
		}
		//Select bank proof type
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("BankProof"),UtilityTestData.BankProof);
		//Enter nominee name
		base.setData(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("NomineeName"),UtilityTestData.NomineeName);
		//Select nominee relation
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("NomineeRelation"),UtilityTestData.NomineeRelation);
		//Click on check box( same as above)
		base.ClickCondition(GppOpeningAndCollectionMultiplePlansObj.Sel_GPPNomineeName("IsSameAsAbove"));
		//Click on save button
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_return("View more"));
		Thread.sleep(2000);
		base.excuteJsClick(LoginPageObj.Btn_SingnIn("SearchView_AppBarView_appBarCommandCommand"));	
		Map<String, String> PdfDetails = PdfUtilities.ExtractDetailsFromGPPPdf(SchemeCode);
		String ApplicationIdPdf   = PdfDetails.get("ApplicationID");
		//Select customer
		//Click on Add to sale button
		this.SearchByCustomerID(CustomerNo,UtilityTestData.MenuBarOptions[0]);
		//Click on GPP
		base.ClickCondition(LoginPageObj.Edt_AlertText("GPP"));
		//GPP collection entry
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_return("GPP Collection Entry"));
		//Choose deposit type
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"),UtilityTestData.GPPDepositType);
		//Enter transaction number
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), ApplicationIdPdf);
		//Select sales person
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"),UtilityTestData.SalePersonName);		 
		//Click on deposit
		Thread.sleep(2000);
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		//Click on the Amount
		base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		//Select a Payment method(Cash or Card)
		base.ClickCondition(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		String ExpectedAdvanceAmount = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value").replace(",", "");
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		base.ClickCondition(LoginPageObj.Btn_SignInButton("OK"));
		base.ClickCondition(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		return ApplicationIdPdf;
	}

	/// <summary>
	/// Method for multiple SR Converted to Normal Advance
	/// Author - nivya
	/// </summary>
	public Map<String, String> MultipleSRConvertedToNormalAdvance(
			String CustomerID,
			List<String> CounterTransfer,
			List<String> ReturnProducts) throws Exception {

		Map<String, String> Result = new HashMap<>();

		this.HamBurgerButtonClick("iconShop");

		// Step 1: Select customer and click on add to sale button
		this.SearchByCustomerID(CustomerID, UtilityTestData.MenuBarOptions[0]);

		// Step 2: Scan Multiple SKU's, click on Add to cart button and Select any one of the sales agent
		List<String> AllSkuList = new ArrayList<>();
		for (int j = 0; j + 2 < CounterTransfer.size(); j += 3) {
			List<String> RequiredSkuList = this.SelectMultipleSKUs(
					Integer.parseInt(CounterTransfer.get(j)),
					"Inter",
					CounterTransfer.get(j + 1),
					CounterTransfer.get(j + 2)
					);
			AllSkuList.addAll(RequiredSkuList);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation(
					"headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		}

		Map<String, String> ScannedSKUDataMap = new LinkedHashMap<>();
		for (int i = 0; i < AllSkuList.size(); i++) {
			this.SKUIngridentTableValues(AllSkuList.get(i), i + 1, ScannedSKUDataMap);
		}

		// Step 3: Click on the Amount and Select a Payment method (Cash or Card)
		String PaymentAmount = this.PaymentAfterRecallEstimate("HDFC");

		// Step 4: Post the Invoice
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		String TaxInvoiceName = PdfUtilities.DownloadAndRenameLatestPDF("Method_TaxInvoice");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		String TaxInvoice = System.getProperty("user.dir") + "\\Invoices\\" + TaxInvoiceName + "";
		String InvoiceNo = PdfUtilities.ExtractReceiptNoFrmSaleInvoice(TaxInvoice);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		System.out.println("Generated Invoice No: " + InvoiceNo);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		SearchByCustomerID(CustomerID, UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Click on customer adjustment, return transaction, Receipt Number, Return Product, Return Button
		ReturnDetails Details = ReturnMultipleProductsWithDetails(InvoiceNo, ReturnProducts);

		// Click on the Amount, Payment method, transaction type, due date, Nominee details, Deposit button
		String PaymentAmountSR = base.GetText(
				NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")
				).replace("\u20B9", "").replace(",", "").trim();

		String SRAmountDue = PaymentAmountSR.replaceAll("[^\\d.]", "");
		System.out.println(SRAmountDue);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		Thread.sleep(2000);

		asrt.assertTrue(
				base.isExists(LoginPageObj.Edt_AlertText("Convert to Advance")),
				"The user is not able to view the payment methods"
				);

		Thread.sleep(2000);
		base.buttonClick(LoginPageObj.Edt_AlertText(UtilityTestData.PaymentMode));
		Thread.sleep(4000);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_SalesPerson(UtilityTestData.SalePersonNumber), UtilityTestData.SalePersonName);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Ele_DrpdwnYear("win-datepicker-year win-dropdown win-order2 displayOrder3"), UtilityTestData.DueYear);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Remarks("6"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Remarks("6"), UtilityTestData.Remarks);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Add Nominee Details"));
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Nominee name"), UtilityTestData.NomineeName);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Nominee relation"), UtilityTestData.NomineeRelation);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		Thread.sleep(3000);

		// Warning handling if present
		try {
			WebDriverWait ShortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
			WebElement Warning = ShortWait.until(
					ExpectedConditions.visibilityOfElementLocated(NormalSaleGoldAndSilverObj.Ele_Payement("Warning"))
					);
			base.excuteJsClick(LoginPageObj.Btn_SignInButton("OK"));
		} catch (Exception e) {
			// Warning not found
		}

		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton", "Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox", "win-item", "DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue", "PRINT"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		// Store both values in result map
		Result.put("InvoiceNo", InvoiceNo);
		Result.put("PaymentAmountSR", PaymentAmountSR);

		return Result;
	}

	// <summary>
	// Method to Create offer advance with Reserve Sku and take first collection
	// Author: Sangeetha M S
	// </summary>
	public Map<String, String> OfferAdvanceReserveSkuAndTakeCollection(String CustomerPhoneNo, int SkuCount, String TransferType,String FromCounterGold,String MetalTypeGold, double DepositPercent) throws Exception 
	{
		Map<String, String> OfferAdvanceDataMap = new HashMap<>();

		//Select SKU Number
		List<String> GoldSku =SelectMultipleSKUs(SkuCount,TransferType,FromCounterGold,MetalTypeGold);
		List<String> SkuList=new ArrayList<>();
		SkuList.addAll(GoldSku);
		String SkuNumber = SkuList.get(0);		
		Thread.sleep(3000);
		HamBurgerButtonClick("iconShop");	
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
		base.setDataWithoutClear(NormalSaleGoldAndSilverObj.Edt_Name("Search or enter quantity"), SkuNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
		base.setZoom(driver, 40);
		double ExpectedTotalCValueInItemDetailsPage = Double.parseDouble(base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("valueInput"), "value"));
		double ExpectedGrossWeightInItemDetailspage =  Double.parseDouble(base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("grossWeightInput"), "value"));		
		double ExpectedTotalAmountInItemDetailPage = Double.parseDouble(base.GetAttribte(NormalSaleGoldAndSilverObj.Ele_Name("totalInput"), "value"));
		Thread.sleep(3000);
		HamBurgerButtonClick("iconShop");	

		//Click on advance
		//Select customer order
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Customer Order"));

		//Search customer and click on add to customer order
		Thread.sleep(3000);
		SearchByCustomerIDForGPP(CustomerPhoneNo, UtilityTestData.MenuBarOptions[7]);

		//Enter the SKU number in the field and click enter
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_ScanSku("AddSKUAutomatic "), SkuNumber);
		base.pressKey(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_ScanSku("AddSKUAutomatic "), "ENTER");
		Thread.sleep(2000);
		String ActualGrossWeightInCustomerOrderPge=base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column3", "h4 ellipsis cell"));
		double ActualGrossWeightInCustomerOrderPage = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column3", "h4 ellipsis cell")));
		double ActualCValueInCustomerOrderPage = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column4", "h4 ellipsis cell")));
		String TotalInCustomerOrderPage=base.GetText(NormalSaleGoldAndSilverObj.Btn_CartMoney("column7", "h4 ellipsis cell"));
		double ActualTotalAmountInCustomerOrderPage = Double.parseDouble(base.GetValue(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Ele_GPPCustAccnt("TotAmtInput")));
		OfferAdvanceDataMap.put("TotalInCustomerOrderPage", TotalInCustomerOrderPage);
		OfferAdvanceDataMap.put("ActualGrossWeightInCustomerOrderPge", ActualGrossWeightInCustomerOrderPge);

		//Verify while nominee detail getting removed if we update any other details after updating the nominee details.
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_NomineeDetailsAppBarCommandCommand"));
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeName"),UtilityTestData.NomineeName);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: NomineeRelationArr"),UtilityTestData.NomineeRelation );
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));	
		String Address = base.GetText(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"));
		if(Address == "") 
		{
			base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"),UtilityTestData.NomineeAddress);
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}
		else
		{
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}			

		//Select the 'Is offer order' checkbox
		base.buttonClick(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_CheckBox("checkbox", "IsofferChkBox"));

		//Add nominee details
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_NomineeDetailsAppBarCommandCommand"));
		Thread.sleep(1000);
		WebElement NomineeNameField = driver.findElement(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Ele_GPPCustAccnt("NmInput"));
		String NomineeName = NomineeNameField.getAttribute("value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("secondaryButton"));

		//Choose the company and invent location
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col", "viewModel.IsOrderEntry"), UtilityTestData.Company[0]);
		base.selectorByVisibleText(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_IsOrder("col stretch", "viewModel.IsOrderEntry"), UtilityTestData.Location[0]);
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Sales person"), UtilityTestData.SalePersonName);

		//Select the order line
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		//Select the ingredient line
		base.scrollToElement(LoginPageObj.Ele_ErrorMessage("win-itembox"));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itembox"));

		//Click on willing to pay button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions("MGDRetail_MGDRetail_Extensions_OrderView_appWillToPayCommandCommand"));

		//Enter the amount
		String WillingToPayAmount = Double.toString(ActualTotalAmountInCustomerOrderPage*DepositPercent);
		OfferAdvanceDataMap.put("WillingToPayAmount", WillingToPayAmount);
		Thread.sleep(1000);
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Amount("value: WILLAMT", "number"), WillingToPayAmount);
		Thread.sleep(1000);
		String ExpectedOfferExpiryDate= base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: SDATE"));
		String ExpectedValidityDays = base.GetValue(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: SDAYS")).replaceAll("\\D+", "");;
		OfferAdvanceDataMap.put("ExpectedOfferExpiryDate", ExpectedOfferExpiryDate);
		OfferAdvanceDataMap.put("ExpectedValidityDays", ExpectedValidityDays);

		//Click on apply button
		base.buttonClick(LoginPageObj.Btn_SignInButton("APPLY"));

		//Click on edit line
		Thread.sleep(3000);
		base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Txt_Sku("win-structuralnodes win-selectionmode"));
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions("MGDRetail_MGDRetail_Extensions_OrderView_EditSlappBarCommandCommand"));

		//Enter the line delivery date
		DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate ExpiryDate = LocalDate.parse(ExpectedOfferExpiryDate, Formatter);
		LocalDate TargetDate = ExpiryDate.minusDays(1);
		String Day = Integer.toString(TargetDate.getDayOfMonth());
		String Month = Integer.toString(TargetDate.getMonthValue() - 1); 
		String Year = Integer.toString(TargetDate.getYear());	
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-date"), Day);
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-month"), Month);
		base.selectorByValue(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Date("content ExtensionTemplateDialog", "win-datepicker-year"), Year);

		//Enter remarks
		base.setData(AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvanceObj.Ele_Remarks("value: Remarks"),UtilityTestData.Remarks);

		//Click on OK button
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		//Add nominee details, Select a sales person
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_NomineeDetailsAppBarCommandCommand"));
		Thread.sleep(1000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeName"),UtilityTestData.NomineeName);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: NomineeRelationArr"), UtilityTestData.NomineeRelation);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("NomineeChkBox"));	
		String Address1 = base.GetText(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"));
		if(Address1 == "")
		{
			base.setData(NormalSaleGoldAndSilverObj.Ele_SameAsAbove("value: NomineeAddress"),UtilityTestData.NomineeAddress);
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}
		else 
		{
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		}

		//Click on save button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("MGDRetail_MGDRetail_Extensions_OrderView_appBarCommandCommand"));
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("YES"));
		String Remark = base.GetText(NormalSaleDiamondandPlatinumObj.Ele_GrossWeight("text: content"));
		String OrderId = Remark.split(" - ")[1].split(" ")[0];
		OfferAdvanceDataMap.put("OrderId", OrderId);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));

		//Go to transactions
		//Step Search customer
		//Step Click on add to sale button
		Thread.sleep(3000);
		HamBurgerButtonClick("iconShop");
		Thread.sleep(1000);
		SearchByCustomerID(CustomerPhoneNo ,UtilityTestData.MenuBarOptions[0]);

		//Choose advance-->advance collection
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_return("Advance Collection"));
		Thread.sleep(5000);
		if(base.isExists(LoginPageObj.Btn_SingnIn("Button1Close")))
		{
		  base.ClickCondition(LoginPageObj.Btn_SingnIn("Button1Close"));	
		}

		//Choose type as order
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("OrderTypeOptions"), "Order");

		//Select the transaction number from the drop down
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("TransTypeOptions"), OrderId);

		//Enter the deposit amount
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("DepAmount"),WillingToPayAmount);	
		Thread.sleep(2000);
		base.selectorByVisibleText(GPPSettlementAverageRateSingleSkuMultipleSkuObj.Sel_GPPAccountNo("options: ArrSALESMAN"),UtilityTestData.SalePersonName);
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Deposit"));

		//Click on the Amount
		Thread.sleep(3000);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_PaymentMethod("HDFC"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_IconReturnKey("paymentView fragment", "iconReturnKey"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.ApprCode);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("textInputDialogContent"), UtilityTestData.CardNumber);
		Thread.sleep(2000);
		base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));

		//Post the Invoice			
		Map<String, String> FirstAdvanceReceiptPdf = PdfUtilities.OfferAdvancePdfValidation();
		String FirstAdvanceReceived = FirstAdvanceReceiptPdf.get("AdvanceReceived");
		OfferAdvanceDataMap.put("FirstAdvanceReceived", FirstAdvanceReceived);

		return OfferAdvanceDataMap;
	}

}