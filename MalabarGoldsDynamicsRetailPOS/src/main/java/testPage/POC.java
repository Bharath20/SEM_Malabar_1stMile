package testPage;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import testData.CommonData;
import testData.POC_TestData;
import utilPack.AppUtilities;
import utilPack.BasePge;
import utilPack.PdfUtilities;

public class POC extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	PdfUtilities PdfUtils;
	public POC(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		PdfUtils=new PdfUtilities(base);
	}


	// <summary>
	// Test Case Title : Customer Creation / Customer Edit [Creating retail customer with all details]
	// Automation ID : TC01_POC
	// </summary>
	public void TC01_POC() throws InterruptedException {

		NormalSaleGoldandSilver_Obj normalsalegoldandsilverObj = new NormalSaleGoldandSilver_Obj();
		POC_TestData poctestdata = new POC_TestData();
		LoginPage_Obj LoginPageObj=new LoginPage_Obj();
		Login login = new Login(driver); 


		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 80);

		// Step 1: Navigate to the Transaction page
		// Expected Result: User should be navigated to the Transaction page
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 2: Click on add customer
		// Expected Result: User should be able to click on add customer
		base.buttonClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_Login("Create new customer")), "The user is not able to navigated to Add Customer page");
		System.out.println("addcustomer button is clicked");

		// Step 3: .Click on create new customer
		// Expected Result: User should be able to clickable on create new customer
		base.buttonClick(LoginPageObj.Btn_Login("Create new customer"));
		asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h5 clip")), "The user is not able to navigated to create new customer page");

		// Step 4: .Enter the First, Last Name, Phone Number
		// Expected Result: User should be able to enter the First and Last Name
		base.buttonClick(normalsalegoldandsilverObj.Ele_Name("customerFirstNameInput11111111"));
		base.setData(normalsalegoldandsilverObj.Ele_Name("customerFirstNameInput"),poctestdata.TC01_POCFirstName );
		base.buttonClick(normalsalegoldandsilverObj.Ele_Name("customerLastNameInput"));
		base.setData(normalsalegoldandsilverObj.Ele_Name("customerLastNameInput"),poctestdata.TC01_POCLastName );
		String Lastname = base.GetValue(normalsalegoldandsilverObj.Ele_Name("customerLastNameInput"));		
		base.scrollToElement(normalsalegoldandsilverObj.Ele_Name("customerPhoneNumberInput"));
		base.buttonClick(normalsalegoldandsilverObj.Ele_Name("customerPhoneNumberInput"));
		base.setData(normalsalegoldandsilverObj.Ele_Name("customerPhoneNumberInput"),poctestdata.TC01_POCPhoneNumber);

		// Step 5: Select the Preferred Language, Religion Code, Preference Language
		// Expected Result: User should be able to select the Preferred Language, Religion Code
		base.scrollToElement(normalsalegoldandsilverObj.Sel_DepositType("customerLanguageOptions"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("customerLanguageOptions"),"Arabic");
		base.scrollToElement(normalsalegoldandsilverObj.Sel_DepositType("RegionCode"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("RegionCode"),"Christian");
		base.scrollToElement(normalsalegoldandsilverObj.Sel_DepositType("PreferredLanguage"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("PreferredLanguage"),"Hindi");

		// Step 6: Click Add an address
		// Expected Result: User should be able to click Add an address
		base.scrollToElement(LoginPageObj.Edt_AlertText("Add an address"));
		base.excuteJsClick(LoginPageObj.Edt_AlertText("Add an address"));
		Thread.sleep(1000);
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Address("Address")), "The user is not able to view the Add an address");

		// Step 7: Enter Zip code
		// Expected Result: User should be able to enter Zip code
		base.buttonClick(normalsalegoldandsilverObj.Ele_Name("zipTabRead"));
		base.setData(normalsalegoldandsilverObj.Ele_Name("zipTabRead"),poctestdata.TC01_POCZipCode );
		driver.findElement(By.id("zipTabRead")).sendKeys(Keys.ENTER);

		// Step 8: Select the address and click Save button
		// Expected Result: User should be able to select the address and click Save button
		Thread.sleep(1000);
		String address = base.GetText(normalsalegoldandsilverObj.Ele_Address());
		System.out.println(address);
		base.buttonClick(normalsalegoldandsilverObj.Ele_Address());	

		// Step 9: click Save button
		// Expected Result: User should be able to click Save button
		Thread.sleep(1000);
		base.scrollToElement(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));

	}

	// <summary>
		// Test Case Title : Normal Sales Gold /Silver/Single sku/Multiple sku/Piece rate [Normal sale multiple gold and silver item]		    
		// ID : TC02_POC
		// </summary>
	public void TC02_POC() throws Exception {
		NormalSaleGoldandSilver_Obj normalsalegoldandsilverObj = new NormalSaleGoldandSilver_Obj();
		POC_TestData poctestdata = new POC_TestData();
		LoginPage_Obj LoginPageObj=new LoginPage_Obj();
		Login login = new Login(driver);
		
		
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);
		// Step 1: Navigate to the Transaction page
		// Expected Result: User should be navigated to the Transaction page
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 2: Select customer
		// Expected Result: User should be navigated to the customer page
		base.buttonClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),poctestdata.TC02_CustomerNo);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Customers"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Customers")));

		// Step 3: Click on add to estmation button
		// Expected Result: User should be add the cutomer to estimation
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconCompleted"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 4: Scan multiple SKU and click on Add to cart button
		// Expected Result: User should be add the cutomer to estimation
		// Step 4: Scan multiple SKUs
		int skuCountToAdd = 2;
		String transferType = "Inter";
		String fromCounter = "Gold";
		String metalType = "Gold";
		List<String> SkuList = appUtils.SelectMultipleSKUs(skuCountToAdd,transferType,fromCounter,metalType);

		// Step 5: Navigate to the Transaction
		// Expected Result: User should be navigated to the Transaction page
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "User is not navigated to Transaction page");

		// Step 6: Add SKUs to cart
		// Step 6: Scan multiple SKU and click on Add to cart button
		// Expected Result: User should able to add Multiple SKUs and can add to cart
		//SKUs Verifying Page Ingridents and Sales all
		// Step 1: Create a LinkedHashMap to store structured scanned SKU data
		// Format will be like: SKU1_ITEM1_TEAMID, SKU1_ITEM1_PCS, etc.
		Map<String, String> ScannedSKUDataMap = new LinkedHashMap<>();

		// Step 2: Loop through each SKU in the provided skuList
		for (int i = 0; i < SkuList.size(); i++) {    
			//String sku = skuList.get(i);
			// Step 3: Process the current SKU by calling the reusable method
			// Pass the SKU string, its sequence number (i + 1), and the map to store extracted data
			appUtils.SKUIngridentTableValues(SkuList.get(i), i + 1, ScannedSKUDataMap);
		}

		//TranscationPageVerifyLines
		Map<String, String> transactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSKUDataMap);
		String TaxableValue = transactionDataLine.get("Subtotal");
		String Discount = transactionDataLine.get("Discount");
		String TotalGrossWeight = transactionDataLine.get("GrossWeight");
		String TotalNetWeight = transactionDataLine.get("NetWeight");
		String GST = transactionDataLine.get("Tax");
		String InvoiceTotal = transactionDataLine.get("TotalAmount");
		String NetTotal = transactionDataLine.get("NetTotal");
		String LinesCount = transactionDataLine.get("LinesCount");

		// Step 7: Go to Billing
		// Expected Result: User should able to select Biling
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		Thread.sleep(3000);
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Save To Estimate")), "The user is able to view the Billing options");

		// Step 8: Click on Save To Estimate Click on Save to estimation button
		// Expected Result: User should able to select Save To Estimate
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Estimation")), "The user is not able to navigate to estimation page");
		String EstmnNumber=base.GetValue(normalsalegoldandsilverObj.Ele_EstmnNumber("Estimation no"));
		System.out.println("Estimation Number: "+EstmnNumber);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// PDF Verification Step
		// Step 9: Verify Estimation Number in the PDF
		// PDF Verification Step
		// Wait for the print dialog to appear
		// Step 1: Save original window handle
		Thread.sleep(2000); 
		// Step 9: Handle Print Preview (PDF download)
		Thread.sleep(3000);
		//String downloadDir = System.getProperty("user.dir") + "\\Invoices";
		//String newPdfName = appUtils.downloadAndRenamePDF(System.getProperty("user.dir") + "\\Invoices", "TC");
		String newPdfName = PdfUtils.DownloadAndRenameLatestPDF("TCPdf1"); // or "POC2", etc.

	Thread.sleep(5000);		
	//	String PdfPath = "C:\\Users\\vishn\\Downloads\\Dynamics Retail POS.pdf"; // Replace with actual
	String PdfPath =System.getProperty("user.dir") + "\\Invoices\\"+newPdfName+"";
	String PdfContent = PdfUtils.IsGetText(PdfPath);	
	System.out.println(PdfContent);

	PdfUtils.ProFormaInvoicePDFVerifyNormal(
			PdfPath,
			TaxableValue,
			GST,
			InvoiceTotal,
			NetTotal,
			LinesCount,
			Double.parseDouble(TotalGrossWeight),
			Double.parseDouble(TotalNetWeight)
			);
	//---------------------------------------------------------------------//

	// Step 9: Go to cash
	//		// Expected Result: User should able to select Biling
	base.buttonClick(normalsalegoldandsilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
	asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Recall Estimate")), "The user is not able to view to Cash tiles option");

	// Step 10: Click on Save To Estimate Click on Save to estimation button
	// Expected Result: User should able to select Save To Estimate
	base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

	base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

	base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	Thread.sleep(3000);
	base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);

	System.out.println("The Estimation is for sales return: " + EstmnNumber);
	Thread.sleep(3000);
	base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
	base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
	asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Estimation")), "The user is not able to navigate to estimation page");

	// Step 11: Select the Transaction Type as Sales 
	// Expected Result: User should able to Select the Transaction Type as Sales       
	base.buttonClick(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"));
	base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"), "Sales");

	// Step 12: Click on Process Estimation  
	// Expected Result: User should able to Click on Process Estimation 
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

	// Step 13: Select any Sales Representative
	// Expected Result: sale representative is selected
	base.setZoom(driver, 60);
	Thread.sleep(3000);
	base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
	asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

	// Step 14:  Click on the Amount
	// Expected Result: User should able to click the amount 
	base.buttonClick(normalsalegoldandsilverObj.Btn_Amnt("h1"));
	asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("HDFC (Card)")),"The user is not able to view the payment methods");

	// Step 15:  Select a Payment method(Cash or Card)
	// Expected Result: User should able to select the Payement Method 
	base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Card)"));
	String PaymentAmountHDFC=base.GetAttribte(normalsalegoldandsilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
	base.buttonClick(normalsalegoldandsilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
	base.setData(normalsalegoldandsilverObj.Edt_ApprCode("textInputArea"),"12345");
	base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("OK"));
	base.setData(normalsalegoldandsilverObj.Edt_ApprCode("textInputArea"),"12345");
	base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("OK"));
	base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Close"));
	base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

	// Step 16:  Post the Invoice
	// Expected Result: User should able to Post the Invoice 
	//String PdfPath1 =System.getProperty("user.dir") + "\\Invoices\\"+newPdfName+"";
	
	base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("PRINT"));
	Thread.sleep(5000);
	//String renamedPdf = appUtils.downloadAndRenamePDF(downloadDir, "TC02");
	String newPdfName1 = PdfUtils.DownloadAndRenameLatestPDF("TCPdf2"); // or "POC2", etc.
	
	String cleanedGST = GST.replaceAll("[₹,\\s]", "");
	double taxValue = Double.parseDouble(cleanedGST);
	double CGSTValue = taxValue / 2;
	String CGST = String.format("%.2f", CGSTValue);
	double SGSTValue = taxValue / 2;
	String SGST = String.format("%.2f", SGSTValue);

	
    String TotalDiamond="0";
    String Adjustment="0";
    String InvoiceNo="0";
	Thread.sleep(5000);		
	//	String PdfPath = "C:\\Users\\vishn\\Downloads\\Dynamics Retail POS.pdf"; // Replace with actual
	String PdfPath2 =System.getProperty("user.dir") + "\\Invoices\\"+newPdfName1+"";
	String PdfContent2 = PdfUtils.IsGetText(PdfPath2);	
	System.out.println(PdfContent2);
	String GrossAmount=TaxableValue;
	String TotalValue=InvoiceTotal;
	String RoundOff="0.06";
	String NetValue=NetTotal;
	System.out.println("InvoiceNo"+InvoiceNo+"GrossAmount"+GrossAmount+
			"Discount"+Discount+
		    "LinesCount"+LinesCount+
		    "TotalNetWeight"+TotalNetWeight+
		    "TotalGrossWeight"+TotalGrossWeight+
		    "TaxableValue"+TaxableValue+
		    "CGST"+CGST+
		    "SGST"+SGST+
		    "TotalValue"+TotalValue+
		    "RoundOff"+RoundOff+
		    "NetTotal"+NetTotal+
		    "PaymentAmountHDFC"+PaymentAmountHDFC+
		    "TotalDiamond"+TotalDiamond+
		    "Adjustment"+Adjustment
		    );
	String pdfInvoiceNo = PdfUtils.SaleInvoicePdfValidation(
			PdfPath2,
		    GrossAmount,
		    Discount,
		    LinesCount,
		    TotalNetWeight,
		    TotalGrossWeight,
		    TaxableValue,
//		    CGST,
//		    SGST,
//		    TotalValue,
//		    RoundOff,
		    NetTotal,
		    PaymentAmountHDFC,
		    SkuList,
		    ScannedSKUDataMap
		);
	System.out.println("PDF INVOICE NO:-"+pdfInvoiceNo);
	//PdfUtils.DeleteAllPDFFilesInInvoiceFolder();
}




	//<summary>
	// Test Case Title : Normal Sales Return Gold /Silver Only return/Return with sale/Counter in [Sales return with OG and a sale]
	// ID : TC03_POC
	// </summary>
	public void TC03_POC() throws Exception {
		NormalSaleGoldandSilver_Obj normalsalegoldandsilverObj = new NormalSaleGoldandSilver_Obj();
		POC_TestData poctestdata = new POC_TestData();
		LoginPage_Obj LoginPageObj=new LoginPage_Obj();
		Login login = new Login(driver); 

		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 1: Navigate to the Transaction page
		// Expected Result: User should be navigated to the Transaction page

		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 2: User should able to Search the Customers using Mobile Number
		// Expected Result: User should be navigated to the Customer page
		base.buttonClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),poctestdata.TC05_CustomerNo);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Customers"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Customers")),"The user is not able to navigate to the customer page");

		// Step 3: User should able to click the Customer
		// Expected Result: User should be navigated to the Customer summary page
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("win-itemsblock"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Address("Summary")), "The user not able to view the customer summary page");

		// Step 4: User should able to click the see all
		// Expected Result: User should be navigated to the Recent purchase page
		base.excuteJsClick(normalsalegoldandsilverObj.Ele_SeeAll("See all"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Purchase("recentPurchasesViewCommandAppBar")), "The user not able navigate to Recent Purchase page");

		// Step 5: User should able get the recent receipt no: and go to the transaction page
		// Expected Result: User should be fetch the recent receipt no: and should be navigate to the transaction page
		String receiptnumber = base.GetText(normalsalegoldandsilverObj.Ele_SKUNumber("column1", "h4 ellipsis cell", "1" ));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 6: User should able to Search the Customers using Mobile Number
		// Expected Result: User should be navigated to the Customer page
		base.buttonClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		Thread.sleep(3000);
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),poctestdata.TC05_CustomerNo);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Customers"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Customers")),"The user is not able to navigate to the customer page");

		// Step 7: Click on add to estmation button
		// Expected Result: User should be add the cutomer to estimation
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconCompleted"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 8: Click on add to Customer Adjustment button
		// Expected Result: User should be add the Customer Adjustment 
		base.excuteJsClick(normalsalegoldandsilverObj.Ele_CustomerAdjustment("Customer Adjustments"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_CustomerAdjustment("Return transaction")), "The user is not able to navigated to Customer Adjustment page");

		// Step 9: Click on add to return transaction button
		// Expected Result: User should be add the return transaction 
		base.excuteJsClick(normalsalegoldandsilverObj.Ele_CustomerAdjustment("Return transaction"));
		//	asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 10: Click on ABC button and enter the Receipt no:
		// Expected Result: User click on ABC button and should able to enter the receipt no:
		base.buttonClick(normalsalegoldandsilverObj.Btn_CartMoney("numpad-control-buttons flexGrow100 flexRow","h1 marginBottom0 button-content-abc primaryFontColor"));
		base.setDataWithoutClear(normalsalegoldandsilverObj.Ele_abc("numpad-control alphanumeric-mode flexCol","numpad-control-input"), receiptnumber );
		base.buttonClick(normalsalegoldandsilverObj.Btn_ReturnCartMoney("flexGrow100 accentBackground enter", "h1 marginBottom0 iconReturnKey button-content-return"));
		Thread.sleep(1000);

		// Expected Result: User select store name and register name 
		//	Thread.sleep(3000);
		//		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("multipleTransactionStoreNames"),"5124");
		//		Thread.sleep(3000);
		//		base.selectorByIndex(normalsalegoldandsilverObj.Sel_DepositType("multipleTransactionRegisterNumbers"), 1 );
		//		base.buttonClick(LoginPageObj.Ele_ErrorMessage("buttonContainer col no-shrink halfWidth leftmostDialogButton marginTop20"));
		//		//asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Return("Returnable products")), "The user is not able to view the returnable products");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		// Check if the dropdown contains the visible text "5124"
		if (base.isElementPresent(driver, normalsalegoldandsilverObj.Sel_DepositType("multipleTransactionStoreNames"))) {
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
				Thread.sleep(3000);
				// Select the dropdown value by visible text
				base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("multipleTransactionStoreNames"));
				base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("multipleTransactionStoreNames"), "5124");
				Thread.sleep(3000);
				base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("multipleTransactionRegisterNumbers"));
				base.selectorByIndex(normalsalegoldandsilverObj.Sel_DepositType("multipleTransactionRegisterNumbers"), 1 );
				base.buttonClick(LoginPageObj.Ele_ErrorMessage("buttonContainer col no-shrink halfWidth leftmostDialogButton marginTop20"));

				System.out.println("Dropdown found and value '5124' selected.");

			} catch (Exception e) {
				System.out.println("Failed to select dropdown value: " + e.getMessage());
			}
		} else {
			System.out.println("Dropdown not found, skipping the selection.");
		}

		// Step 12: select return product and click on return button 
		// Expected Result: User can able to select return product and click on return button 
		Thread.sleep(4000);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("win-item", "1"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("return"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 13: Scan SKU and click on Add to cart button
		// Expected Result: User should be Scan SKU and click on Add to cart button
		base.buttonClick(LoginPageObj.Edt_AlertText("Purchase"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Counter Transfer"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("SKU counter stock transfer")), "The user is not able to navigate the SKU Counter stock Transfer page");
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("TransferType"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("TransferType"), "Inter");
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("FromCounter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("FromCounter"), "Gold");
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("MetalType"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("MetalType"), "Gold");
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconSearch"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 padLeft12", "Product search"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Product search")),"The user is not able to navigate the Product Search page");
		String SKU_No1= base.GetText(normalsalegoldandsilverObj.Ele_SKUNumber("column1","h4 ellipsis cell","1"));
		System.out.println(SKU_No1);

		// Step 14: Navigate to the Transaction
		// Expected Result: User should be navigated to the Transaction page
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 15: Scan multiple SKU and click on Add to cart button
		// Expected Result: User should able to add Multiple SKUs and can add to cart
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
		Thread.sleep(3000);
		base.setDataWithoutClear(normalsalegoldandsilverObj.Edt_Name("Search or enter quantity"), SKU_No1);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));;
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_CustomerAdjustment("Item details")), "The user is not able to navifate to the Item details page");

		// Step 16: Click on add to cart button and Select any Sales Representative
		// Expected Result: User should able to click on add to cart button and able to Select any Sales Representative
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 17: User should able to Search the OG product and select the OG product
		// Expected Result: User should be navigated to the Product page and should able to select the OG product
		base.buttonClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),poctestdata.TC03_OGProduct);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Products"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Products")),"The user is not able to navigate to the customer page");
		Thread.sleep(3000);
		//		base.buttonClick(normalsalegoldandsilverObj.Btn_CartMoney("win-container win-selected","win-itembox win-selected"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_OP("dataListLine","Old Gold Own"));

		// Step 20: User select the Configuration and click Add Item
		// Expected Result: User should be able to select the Configuration of OG product and user should be click Add Item
		Thread.sleep(1000);
		base.buttonClick(normalsalegoldandsilverObj.Sel_Configuration("col grow"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_Configuration("col grow"),"22k"); 		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		//		base.selectorByVisibleText(normalsalegoldandsilverObj.Ele_DropDownConfig("Select a configuration"), "22k");
		//		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		// Step 21: Select QC person and Smith person
		// Expected Result: User should be able to Select QC person and Smith person
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("qcInput"), "Dinesh A H");
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("smInput"), "Baiju M V .");


		// Step 22: Enter piece value and gross weight
		// Expected Result: User should be able to enter piece value ans gross weight
		base.setDataWithoutClear(normalsalegoldandsilverObj.Ele_Name("piecesogp"), poctestdata.TC03_PieceValue);
		Thread.sleep(3000);
		base.setDataWithoutClear(normalsalegoldandsilverObj.Ele_Name("grossWeightogp"), poctestdata.TC03_Grossweight);
		Thread.sleep(3000);

		// Step 23: click on calculate button and Click on Add to cart button
		// Expected Result: User should be able to click on calculate button and should bee able to click on Add to cart button
		base.scrollToElement(normalsalegoldandsilverObj.Ele_Return("Calculate"));
		Thread.sleep(3000);
		base.buttonClick(normalsalegoldandsilverObj.Ele_Return("Calculate"));
		Thread.sleep(3000);
		base.buttonClick(LoginPageObj.Btn_SingnIn("SimpleProductDetailsView_AppBarView_appBarCommandCommand"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");
		//-----------------------needed one....----------------------------	

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_okButtonClick'][id='DefaultMessageDialogButton']"))) {
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

				base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_okButtonClick'][id='DefaultMessageDialogButton']"));
				System.out.println("Popup appeared and OK button was clicked.");
			} catch (Exception e) {
				System.out.println("Failed to click the OK button: " + e.getMessage());
			}
		} else {

			System.out.println("Popup not found, skipping the close button action.");
		}


		// Step 24: click on Billing and click save esimate
		// Expected Result: User should be able to click on Billing and click save esimate
		base.excuteJsClick(normalsalegoldandsilverObj.Ele_CustomerAdjustment("Billing"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(LoginPageObj.Btn_Logout("ButtonGrid1Control", "buttonGridButton accentBackground highContrastBorder pad0 margin0 positionAbsolute button6 backgroundPositionCenter"));
		base.buttonClick(LoginPageObj.Btn_Logout("ButtonGrid1Control", "buttonGridButton accentBackground highContrastBorder pad0 margin0 positionAbsolute button0 backgroundPositionCenter"));	
		//	WebElement element = driver.findElement(By.cssSelector("[data-bind='value: viewModel.EstId, valueUpdate: \\'afterkeydown\\'']"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String Estimation_No = base.GetValue(normalsalegoldandsilverObj.Ele_EstmnNumber("Estimation no"));
		//String Estimation_No = element.getAttribute("value");
		Thread.sleep(1000);
		System.out.println("The Estimation no: "+Estimation_No);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		//	asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step : Cancel Print
		// Expected Result: User should able to cancel the print
		Thread.sleep(8000);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);
		Thread.sleep(8000);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);
		//---------------------------------------------------------------------//


		// Step 25: click on Cash and click Recall estimate button
		// Expected Result: User should be able to click Cash and click Recall estimate button
		base.excuteJsClick(LoginPageObj.Edt_AlertText("Cash "));
		base.buttonClick(LoginPageObj.Btn_Logout("ButtonGrid2Control", "buttonGridButton accentBackground highContrastBorder pad0 margin0 positionAbsolute button1 backgroundPositionCenter"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Recall estimation")), "The user is not able to navigated to Recall Estimation page");

		// Step 26: select the estimation No: and click the Recall Estimation
		// Expected Result: User should be able to select the estimation No: and click the Recall Estimation
		//String Estimation_No = element.getAttribute("value");

		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), Estimation_No);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		Thread.sleep(3000);
		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), Estimation_No);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		System.out.println("The Estimation is for sales return: " + Estimation_No);
		Thread.sleep(3000);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 ellipsis cell",Estimation_No));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Estimation")), "The user is not able to navigate to estimation page");

		// Step 27: Select the Transaction Type as Sales Return
		// Expected Result: User should able to Select the Transaction Type as Sales Return     
		base.buttonClick(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"), "Sales Return");

		// Step 28: Click on Process Estimation  
		// Expected Result: User should able to Click on Process Estimation 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 29:  Click on the Amount and select the CAsh in Advance option
		// Expected Result: User should able to click the amount and select the Cash in Advance option
		String SalesReturnAmount =base.GetText(normalsalegoldandsilverObj.Btn_Amnt("h1"));
		System.out.println("The Amount is "+SalesReturnAmount);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Amnt("h1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		if (SalesReturnAmount != null && !SalesReturnAmount.trim().isEmpty())
		{
			SalesReturnAmount = SalesReturnAmount.replace("₹", "").replace("?", "").trim();
			if (!SalesReturnAmount.equals("0.00")) {
				System.out.println(SalesReturnAmount);

				base.buttonClick(normalsalegoldandsilverObj.Ele_ConvtAdv("win-item col grow", "Convert to Advance"));
				base.selectorByVisibleText(normalsalegoldandsilverObj.Ele_SalesPerson("3"), "Aazad");
				base.selectorByVisibleText(normalsalegoldandsilverObj.Ele_DrpdwnYear("win-datepicker-year win-dropdown win-order2 displayOrder3"), "2026");
				base.scrollToElement(normalsalegoldandsilverObj.Ele_Remarks("6"));
				base.setData(normalsalegoldandsilverObj.Ele_Remarks("6"), poctestdata.TC03_Remarks);
				base.scrollToElement(normalsalegoldandsilverObj.Btn_Deposit("Add Nominee Details"));
				base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Add Nominee Details"));
				Thread.sleep(1000);
				base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Nominee name"), poctestdata.TC03_Nomineename);
				base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Nominee relation"), "Father");
				base.excuteJsClick(normalsalegoldandsilverObj.Ele_Name("NomineeChkBox"));
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
				base.scrollToElement(normalsalegoldandsilverObj.Btn_Deposit("Deposit"));
				base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Deposit"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));	
				base.buttonClick(normalsalegoldandsilverObj.Btn_Amnt("h1"));
				Thread.sleep(3000);
			}
		}
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

		// Step : Cancel Print
		// Expected Result: User should able to cancel the print
		Thread.sleep(8000);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);
		Thread.sleep(8000);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);
		//---------------------------------------------------------------------//
		// Step 30: click on Cash and click Recall estimate button
		// Expected Result: User should be able to click Cash and click Recall estimate button
		base.excuteJsClick(LoginPageObj.Edt_AlertText("Cash "));
		base.buttonClick(LoginPageObj.Btn_Logout("ButtonGrid2Control", "buttonGridButton accentBackground highContrastBorder pad0 margin0 positionAbsolute button1 backgroundPositionCenter"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Recall estimation")), "The user is not able to navigated to Recall Estimation page");

		// Step 31: select the estimation No: and click the Recall Estimation
		// Expected Result: User should be able to select the estimation No: and click the Recall Estimation
		//String Estimation_No = element.getAttribute("value");

		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), Estimation_No);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));	
		Thread.sleep(3000);
		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), Estimation_No);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));	

		System.out.println("The Estimation is for Exchange: " + Estimation_No);
		Thread.sleep(3000);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 ellipsis cell",Estimation_No));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));			base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Estimation")), "The user is not able to navigate to estimation page");		


		// Step 32: Select the Transaction Type as Exchange 
		// Expected Result: User should able to Select the Transaction Type as Exchange     
		base.buttonClick(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"), "Exchange");

		// Step 33: Click on Process Estimation  
		// Expected Result: User should able to Click on Process Estimation 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));			//asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 34: Select any Sales Representative
		// Expected Result: sale representative is selected
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 35:  Click on the Amount 
		// Expected Result: User should able to click the amount 
		String ExchangeAmount =base.GetText(normalsalegoldandsilverObj.Btn_Amnt("h1"));
		System.out.println("The Amount is "+ExchangeAmount);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Amnt("h1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		if (ExchangeAmount != null && !ExchangeAmount.trim().isEmpty())
		{
			ExchangeAmount = ExchangeAmount.replace("₹", "").replace("?", "").trim();
			if (!ExchangeAmount.equals("0.00")) {
				System.out.println(ExchangeAmount);
				base.buttonClick(normalsalegoldandsilverObj.Ele_ConvtAdv("win-item col grow", "Convert to Advance"));
				base.selectorByVisibleText(normalsalegoldandsilverObj.Ele_SalesPerson("3"), "Aazad");
				base.selectorByVisibleText(normalsalegoldandsilverObj.Ele_DrpdwnYear("win-datepicker-year win-dropdown win-order2 displayOrder3"), "2026");
				base.scrollToElement(normalsalegoldandsilverObj.Ele_Remarks("6"));
				base.setData(normalsalegoldandsilverObj.Ele_Remarks("6"), poctestdata.TC03_Remarks);
				base.scrollToElement(normalsalegoldandsilverObj.Btn_Deposit("Add Nominee Details"));
				base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Add Nominee Details"));
				Thread.sleep(1000);
				base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Nominee name"), poctestdata.TC03_Nomineename);
				base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Nominee relation"), "Father");
				base.excuteJsClick(normalsalegoldandsilverObj.Ele_Name("NomineeChkBox"));
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
				base.scrollToElement(normalsalegoldandsilverObj.Btn_Deposit("Deposit"));
				base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Deposit"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));					base.buttonClick(normalsalegoldandsilverObj.Btn_Amnt("h1"));

			}
		}



		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("cancelButton primaryButton"));


		// Step : Cancel Print
		// Expected Result: User should able to cancel the print
		Thread.sleep(8000);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);
		//---------------------------------------------------------------------//

		// Step 36: click on Cash and click Recall estimate button
		// Expected Result: User should be able to click Cash and click Recall estimate button
		base.excuteJsClick(LoginPageObj.Edt_AlertText("Cash "));
		base.buttonClick(LoginPageObj.Btn_Logout("ButtonGrid2Control", "buttonGridButton accentBackground highContrastBorder pad0 margin0 positionAbsolute button1 backgroundPositionCenter"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Recall estimation")), "The user is not able to navigated to Recall Estimation page");

		// Step 37: select the estimation No: and click the Recall Estimation
		// Expected Result: User should be able to select the estimation No: and click the Recall Estimation
		//String Estimation_No = element.getAttribute("value");
		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), Estimation_No);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		Thread.sleep(3000);
		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), Estimation_No);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		System.out.println("The Estimation is for Exchange: " + Estimation_No);
		Thread.sleep(3000);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 ellipsis cell",Estimation_No));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Estimation")), "The user is not able to navigate to estimation page");		


		// Step 38: Select the Transaction Type as Sales 
		// Expected Result: User should able to Select the Transaction Type as Sales     
		base.buttonClick(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"), "Sales");

		// Step 39: Click on Process Estimation  
		// Expected Result: User should able to Click on Process Estimation 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		//	asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 40: Select any Sales Representative
		// Expected Result: sale representative is selected
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 41:  Click on the Amount 
		// Expected Result: User should able to click the amount 
		String Amount =base.GetText(normalsalegoldandsilverObj.Btn_Amnt("h1"));
		System.out.println("The Amount is "+Amount);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Amnt("h1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		if (Amount != null && !Amount.trim().isEmpty())
		{
			Amount = Amount.replace("₹", "").replace("?", "").trim();
			if (!Amount.equals("0.00")) {
				//asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("HDFC (Card)")),"The user is not able to view the payment methods");

				// Step 42:  Select a Payment method(Cash or Card)
				// Expected Result: User should able to select the Payement Method 
				base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Card)"));
				base.buttonClick(normalsalegoldandsilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
				base.setData(normalsalegoldandsilverObj.Edt_ApprCode("textInputArea"),"12345");
				base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("OK"));
				base.setData(normalsalegoldandsilverObj.Edt_ApprCode("textInputArea"),"12345");
				base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("OK"));

			}
		}
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Close"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 43:  Post the Invoice
		// Expected Result: User should able to Post the Invoice 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("PRINT"));
	}


	// <summary>
	// Test Case Title :Old Purchase/ Exchange (Invoice Fetch) Own Diamond Own Platinum Own Precia Own Uncut Store transactions/Old sku table/Manual entry [Multiple OU/OT/OP with multiple category item]
	// ID : TC04_POC
	// </summary>
	public void TC04_POC() throws Exception {
		NormalSaleGoldandSilver_Obj normalsalegoldandsilverObj = new NormalSaleGoldandSilver_Obj();

		POC_TestData poctestdata = new POC_TestData();
		LoginPage_Obj LoginPageObj=new LoginPage_Obj();
		Login login = new Login(driver); 

		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);
		// Step 1: Navigate to the Transaction page
		// Expected Result: User should be navigated to the Transaction page
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 2: Select customer
		// Expected Result: User should be navigated to the customer page
		base.buttonClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),poctestdata.TC04_CustomerNo);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Customers"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Customers")));

		// Step 3: Click on add to estmation button
		// Expected Result: User should be add the cutomer to estimation
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconCompleted"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 4: Select OP
		// Expected Result: User should be navigated to the customer page
		base.buttonClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),"OP");
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Products"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Products")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(normalsalegoldandsilverObj.Btn_OP("dataListLine",poctestdata.TC04_ProdSelection));
		base.buttonClick(normalsalegoldandsilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_Configuration("col grow"),poctestdata.TC04_Configuration);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
				System.out.println("Popup appeared and OK button was clicked.");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));			       
			} catch (Exception e) {
				System.out.println("Failed to click the OK button: " + e.getMessage());
			}
		} else {

			System.out.println("Popup not found, skipping the close button action.");
		}
		if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
				System.out.println("Popup appeared and OK button was clicked.");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));			       
			} catch (Exception e) {
				System.out.println("Failed to click the OK button: " + e.getMessage());
			}
		} else {

			System.out.println("Popup not found, skipping the close button action.");
		}
		//Select QC person
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(2000);
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("qcInput"),poctestdata.TC04_QCperson);
		//Select Smith person
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("smInput"),poctestdata.TC04_Smithperson);
		base.setData(normalsalegoldandsilverObj.Ele_Name("piecesogp"),poctestdata.TC04_SelectPiece);
		base.setData(normalsalegoldandsilverObj.Ele_Name("grossWeightogp"),poctestdata.TC04_SelectGrossWeight);
		//click on calculate button
		base.scrollToElementtoCenter(normalsalegoldandsilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Calculate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		Thread.sleep(5000);
		//Click on cart button
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandimage"));
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));//sales representative

		// Step 5: Select OT
		// Expected Result: User should be navigated to the customer page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		Thread.sleep(3000);
		base.excuteJsClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),"OT");
		Thread.sleep(3000);
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),"OT");
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Products"));	
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Products")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(normalsalegoldandsilverObj.Btn_OP("dataListLine",poctestdata.TC04_ProdSelection2));
		base.buttonClick(normalsalegoldandsilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_Configuration("col grow"),poctestdata.TC04_Configuration2);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));

		if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
				System.out.println("Popup appeared and OK button was clicked.");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));			       
			} catch (Exception e) {
				System.out.println("Failed to click the OK button: " + e.getMessage());
			}
		} else {

			System.out.println("Popup not found, skipping the close button action.");
		}

		if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
				System.out.println("Popup appeared and OK button was clicked.");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));			       
			} catch (Exception e) {
				System.out.println("Failed to click the OK button: " + e.getMessage());
			}
		} else {

			System.out.println("Popup not found, skipping the close button action.");
		}
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("qcInput"),poctestdata.TC04_QCperson);
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("smInput"),poctestdata.TC04_Smithperson);
		base.setData(normalsalegoldandsilverObj.Ele_Name("piecesogp"),poctestdata.TC04_SelectPiece);
		base.setData(normalsalegoldandsilverObj.Ele_Name("grossWeightogp"),poctestdata.TC04_SelectGrossWeight);
		base.scrollToElementtoCenter(normalsalegoldandsilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Calculate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		Thread.sleep(5000);
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandimage"));
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));//sales representat

		// Step 6: Select OU
		// Expected Result: User should be navigated to the customer page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		Thread.sleep(3000);
		base.buttonClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),"OU");
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Products"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Products")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(normalsalegoldandsilverObj.Btn_OP("dataListLine",poctestdata.TC04_ProdSelection3));//javascript
		base.buttonClick(normalsalegoldandsilverObj.Sel_Configuration("col grow"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_Configuration("col grow"),poctestdata.TC04_Configuration3); 		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
				System.out.println("Popup appeared and OK button was clicked.");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));			       
			} catch (Exception e) {
				System.out.println("Failed to click the OK button: " + e.getMessage());
			}
		} else {

			System.out.println("Popup not found, skipping the close button action.");
		}

		if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"))) {
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_TemplatedDialogButton1'].primaryButton"));
				System.out.println("Popup appeared and OK button was clicked.");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_addProductToSale","win-commandimage"));			       
			} catch (Exception e) {
				System.out.println("Failed to click the OK button: " + e.getMessage());
			}
		} else {

			System.out.println("Popup not found, skipping the close button action.");
		}
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("qcInput"),poctestdata.TC04_QCperson);
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("smInput"),poctestdata.TC04_Smithperson);
		base.setData(normalsalegoldandsilverObj.Ele_Name("piecesogp"),poctestdata.TC04_SelectPiece);
		base.setData(normalsalegoldandsilverObj.Ele_Name("grossWeightogp"),poctestdata.TC04_SelectGrossWeight);
		base.scrollToElementtoCenter(normalsalegoldandsilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Calculate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		Thread.sleep(5000);
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandimage"));
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));//sales representat

		//cart has changed 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		if (base.isElementPresent(driver, By.cssSelector("button[data-ax-bubble='messageDialog_okButtonClick'][id='DefaultMessageDialogButton']"))) {
			try {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				base.excuteJsClick(By.cssSelector("button[data-ax-bubble='messageDialog_okButtonClick'][id='DefaultMessageDialogButton']"));
				System.out.println("Popup appeared and OK button was clicked.");
			} catch (Exception e) {
				System.out.println("Failed to click the OK button: " + e.getMessage());
			}
		} else {

			System.out.println("Popup not found, skipping the close button action.");
		}


		// Step 4: Scan multiple SKU and click on Add to cart button
		// Expected Result: User should be add the cutomer to estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		Thread.sleep(3000);
		base.buttonClick(LoginPageObj.Edt_AlertText("Purchase"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Counter Transfer"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("SKU counter stock transfer")), "The user is not able to navigate the SKU Counter stock Transfer page");
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("TransferType"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("TransferType"), "Inter");
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("FromCounter"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("FromCounter"), "Gold");
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("MetalType"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("MetalType"), "Gold");
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconSearch"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 padLeft12", "Product search"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Product search")),"The user is not able to navigate the Product Search page");
		String SKU_No1= base.GetText(normalsalegoldandsilverObj.Ele_SKUNumber("column1","h4 ellipsis cell","1"));
		String SKU_No2= base.GetText(normalsalegoldandsilverObj.Ele_SKUNumber("column1","h4 ellipsis cell","2"));
		System.out.println(SKU_No1);
		System.out.println(SKU_No2);

		// Step 5: Navigate to the Transaction
		// Expected Result: User should be navigated to the Transaction page
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 6: Scan multiple SKU and click on Add to cart button
		// Expected Result: User should able to add Multiple SKUs and can add to cart
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
		Thread.sleep(3000);
		base.setDataWithoutClear(normalsalegoldandsilverObj.Edt_Name("Search or enter quantity"), SKU_No1);
		Thread.sleep(3000);
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//2ndskunumber
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
		Thread.sleep(3000);
		base.setDataWithoutClear(normalsalegoldandsilverObj.Edt_Name("Search or enter quantity"), SKU_No2);
		Thread.sleep(3000);
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// Step 7: Go to Billing
		// Expected Result: User should able to select Biling
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Save To Estimate")), "The user is able to view the Billing options");

		// Step 8: Click on Save To Estimate Click on Save to estimation button
		// Expected Result: User should able to select Save To Estimate
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Estimation")), "The user is not able to navigate to estimation page");
		String EstmnNumber=base.GetValue(normalsalegoldandsilverObj.Ele_EstmnNumber("Estimation no"));
		System.out.println("Estimation Number: "+EstmnNumber);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// Step : Cancel Print
		// Expected Result: User should able to cancel the print
		Thread.sleep(8000);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);
		//---------------------------------------------------------------------//
		// Step 9: Go to cash
		// Expected Result: User should able to select Biling	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Recall Estimate")), "The user is not able to view to Cash tiles option");

		// Step 10: Click on Save To Estimate Click on Save to estimation button
		// Expected Result: User should able to select Save To Estimate
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
		Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("h4 ellipsis cell","1"));

		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Estimation")), "The user is not able to navigate to estimation page");

		// Step 11: Select the Transaction Type as Exchange
		// Expected Result: User should able to Select the Transaction Type as Exchange       
		base.buttonClick(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"), "Exchange"); //

		// Step 12: Click on Process Estimation  
		// Expected Result: User should able to Click on Process Estimation 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		// Step 13: Select any Sales Representative
		// Expected Result: sale representative is selected
		Thread.sleep(3000);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 14:  Click on the Amount
		// Expected Result: User should able to click the amount 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Amnt("h1"));

		// Step 15:  Click Close
		// Expected Result: User should able to Close 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Close"));

		// Step 16:  Post the Invoice, and cancel the print
		// Expected Result: User should able to Post the Invoice and able to Click Cancel
		Thread.sleep(8000);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);

		// Step 17: Go to cash
		// Expected Result: User should able to select Biling
		base.buttonClick(normalsalegoldandsilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Recall Estimate")), "The user is not able to view to Cash tiles option");

		// Step 18: Click on Save To Estimate Click on Save to estimation button
		// Expected Result: User should able to select Save To Estimate
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		Thread.sleep(3000);
		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("h4 ellipsis cell","1"));	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Estimation")), "The user is not able to navigate to estimation page");

		// Step 19: Select the Transaction Type as Sales 
		// Expected Result: User should able to Select the Transaction Type as Sales       
		base.buttonClick(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"), "Sales");

		// Step 20: Click on Process Estimation  
		// Expected Result: User should able to Click on Process Estimation 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		// Step 21: Select any Sales Representative
		// Expected Result: sale representative is selected
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 22:  Click on the Amount
		// Expected Result: User should able to click the amount 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Amnt("h1"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("HDFC (Card)")),"The user is not able to view the payment methods");

		// Step 23:  Select a Payment method(Cash or Card)
		// Expected Result: User should able to select the Payement Method 
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Card)"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(normalsalegoldandsilverObj.Edt_ApprCode("textInputArea"),poctestdata.TC04_ApprovalCode);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("OK"));
		base.setData(normalsalegoldandsilverObj.Edt_ApprCode("textInputArea"),poctestdata.TC04_CardNumber);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("OK"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Close"));


		// Step 24:  Post the Invoice
		// Expected Result: User should able to Post the Invoice 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("PRINT"));


	}

	// <summary>
	// Test Case Title : Advance Collection Normal Advance Offer Advance Convert to Advance Add on advance Sku reserve [Collection against a sku reserved advance]
	// ID : TC05_POC
	// </summary>
	public void TC05_POC() throws InterruptedException {
		NormalSaleGoldandSilver_Obj normalsalegoldandsilverObj = new NormalSaleGoldandSilver_Obj();

		POC_TestData poctestdata = new POC_TestData();
		LoginPage_Obj LoginPageObj=new LoginPage_Obj();
		Login login = new Login(driver); 

		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 1: Navigate to the Transaction page
		// Expected Result: User should be navigated to the Transaction page
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 2: User should able to Search the Customers using Mobile Number
		// Expected Result: User should be navigated to the Customer page
		base.buttonClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),poctestdata.TC05_CustomerNo);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Customers"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Customers")),"The user is not able to navigate to the customer page");

		// Step 3: Select customer and click on add to sale button
		// Expected Result: User should able to add the customer to Sale
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// Step 4: Select advance 
		// Expected Result: User should able to select Advance
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Advance Collection")),"Thes user is not able to view the Advance tile options");

		// Step 5: Click on advance collection
		// Expected Result: User should able to select Advance collection
		base.buttonClick(LoginPageObj.Edt_AlertText("Advance Collection"));

		// Step 6:  Enter deposite amount
		// Expected Result: User should able to select Enter deposit in GPP
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("OrderTypeOptions"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("OrderTypeOptions"), "GPP");
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("TransTypeOptions"));
		Thread.sleep(1000);
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("TransTypeOptions"), poctestdata.TC05_TransactionNo);
		base.setData(normalsalegoldandsilverObj.Ele_Name("DepAmount"),poctestdata.TC05_DepositAmnt);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SalesPerson("4"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Ele_SalesPerson("4"),poctestdata.TC05_SalesPersonName);

		// Step 7:  Click on deposit button
		// Expected Result: User should able to deposit the amount
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Deposit"));

		// Step 8:  Click on the Amount
		// Expected Result: User should able to click the amount 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Amnt("h1"));
		Thread.sleep(1000);
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Payement("Payment method")), "The user is not able to view Payment method");

		// Step 9:  Select a Payment method(Cash or Card)
		// Expected Result: User should able to select the Payement Method 
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Card)"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(normalsalegoldandsilverObj.Edt_ApprCode("textInputArea"),"12345");
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("OK"));
		base.setData(normalsalegoldandsilverObj.Edt_ApprCode("textInputArea"),"12345");
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("OK"));

		// Step 10:  Post the Invoice
		// Expected Result: User should able to Post the Invoice 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Close"));

	}

	//<summary>
	// Test Case Title : Gpp Opening And Collection (Multiple Plans) [Open GS scheme and take collection through converted to advance(SR/OG)]
	// ID : TC07_POC
	// </summary>
	public void TC07_POC() throws InterruptedException {
		NormalSaleGoldandSilver_Obj normalsalegoldandsilverObj = new NormalSaleGoldandSilver_Obj();

		POC_TestData poctestdata = new POC_TestData();
		LoginPage_Obj LoginPageObj=new LoginPage_Obj();
		Login login = new Login(driver); 

		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 1: Navigate to the Transaction page
		// Expected Result: User should be navigated to the Transaction page
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 2: Go to GPP
		// Expected Result: User should able to select GPP
		base.buttonClick(LoginPageObj.Edt_AlertText("GPP"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("New GPP Account")), "The user is unable to view the GPP tiles options");

		// Step 3: Click on advance collection
		// Expected Result: User should able to select Advance collection
		Thread.sleep(5000);
		base.buttonClick(LoginPageObj.Edt_AlertText("New GPP Account"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("GPP account opening")), "The user is not able view the GPP account opening page");

		// Step 4: User should able to Search customer phone number
		// Expected Result: User should be navigated to the Customer page
		base.buttonClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),poctestdata.TC07_CustomerNo);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Customers"));

		// Step 5: Add to GPP ac open
		// Expected Result: User should able to Add to GPP ac open
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconBuy"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("GPP account opening")), "The user is not able view the GPP account opening page");

		// Step 6:  Enter all mandatory details
		// Expected Result: User should able to Enter all mandatory details
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("SchmTypeOptions"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("SchmTypeOptions"), "GG::Golden Glow Purchase Plan");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(normalsalegoldandsilverObj.Ele_Name("Idproofno1"),poctestdata.TC07_IdProofNo);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.excuteJsClick(normalsalegoldandsilverObj.Sel_BankProofType("Bank proof type"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Bank proof type"),"Cheque");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(normalsalegoldandsilverObj.Ele_Name("NmInput"),poctestdata.TC07_NomineeName);
		base.buttonClick(normalsalegoldandsilverObj.Sel_BankProofType("Nominee relation"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Nominee relation"),"Father");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(normalsalegoldandsilverObj.Ele_Name("NmInput22"),poctestdata.TC07_NomineeAddress);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		// Step 7:  Click on save
		// Expected Result: User should able to Save the GPP
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

	}

	//<summary>
	// Test Case Title : Normal Sales Return Gold /Silver Only return/Return with sale/Counter in [Sales return with OG and a sale]
	// ID : TC06_POC
	// </summary>
	public void TC06_POC() throws Exception {
		NormalSaleGoldandSilver_Obj normalsalegoldandsilverObj = new NormalSaleGoldandSilver_Obj();

		POC_TestData poctestdata = new POC_TestData();
		LoginPage_Obj LoginPageObj=new LoginPage_Obj();
		Login login = new Login(driver); 

		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 1: Navigate to the Transaction page
		// Expected Result: User should be navigated to the Transaction page
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 2: User should able to Search the Customers using Mobile Number
		// Expected Result: User should be navigated to the Customer page
		base.buttonClick(normalsalegoldandsilverObj.Edt_Name("Search"));
		base.setData(normalsalegoldandsilverObj.Edt_Name("Search"),poctestdata.TC05_CustomerNo);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SearchList("Customers"));
		asrt.assertTrue(base.isExists(LoginPageObj.Btn_SignInButton("Customers")),"The user is not able to navigate to the customer page");

		// Step 3: Click on add to estmation button
		// Expected Result: User should be add the cutomer to estimation
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconCompleted"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 4: Go to Billing
		// Expected Result: User should able to select Biling
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Save To Estimate")), "The user is able to view the Billing options");

		// Step 5: Click on add to Customer Adjustment button
		// Expected Result: User should be add the Customer Adjustment 
		base.excuteJsClick(normalsalegoldandsilverObj.Ele_CustomerAdjustment("Customer Adjustments"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_CustomerAdjustment("Return transaction")), "The user is not able to navigated to Customer Adjustment page");

		//click on normal advance settlement
		base.buttonClick(LoginPageObj.Edt_AlertText("Normal Advance Adjustment"));

		//Select advance and click on cart button
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("column1","h4 ellipsis cell","1"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart1("CartView_AppBarView_appBarCommandCommand","win-commandimage"));

		// Step 6: Scan multiple SKU and click on Add to cart button
		// Expected Result: User should be add the cutomer to estimation
		base.buttonClick(LoginPageObj.Edt_AlertText("Purchase"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Counter Transfer"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("SKU counter stock transfer")), "The user is not able to navigate the SKU Counter stock Transfer page");
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("TransferType"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("TransferType"), "Inter");
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("FromCounter"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("FromCounter"), "Gold");
		base.buttonClick(normalsalegoldandsilverObj.Sel_DepositType("MetalType"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_DepositType("MetalType"), "Gold");
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconSearch"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 padLeft12", "Product search"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Product search")),"The user is not able to navigate the Product Search page");
		String SKU_No1= base.GetText(normalsalegoldandsilverObj.Ele_SKUNumber("column1","h4 ellipsis cell","1"));
		String SKU_No2= base.GetText(normalsalegoldandsilverObj.Ele_SKUNumber("column1","h4 ellipsis cell","2"));
		System.out.println(SKU_No1);
		System.out.println(SKU_No2);

		// Step 7: Navigate to the Transaction
		// Expected Result: User should be navigated to the Transaction page
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));	
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 8: Scan multiple SKU and click on Add to cart button
		// Expected Result: User should able to add Multiple SKUs and can add to cart
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
		Thread.sleep(3000);
		base.setDataWithoutClear(normalsalegoldandsilverObj.Edt_Name("Search or enter quantity"), SKU_No1);
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//2ndskunumber
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart("h1 marginBottom0 button-content-abc primaryFontColor"));
		Thread.sleep(3000);
		base.setDataWithoutClear(normalsalegoldandsilverObj.Edt_Name("Search or enter quantity"), SKU_No2);
		base.buttonClick(normalsalegoldandsilverObj.Btn_AddtoCart("h1 marginBottom0 iconReturnKey button-content-return"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// Step 9: Go to Billing
		// Expected Result: User should able to select Biling
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_back("ButtonGrid1Control", "buttonGridButton accentBackground highContrastBorder pad0 margin0 positionAbsolute button6 backgroundPositionCenter"));		
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Save To Estimate")), "The user is able to view the Billing options");

		// Step 10: Click on Save To Estimate Click on Save to estimation button
		// Expected Result: User should able to select Save To Estimate
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Estimation")), "The user is not able to navigate to estimation page");
		String EstmnNumber=base.GetValue(normalsalegoldandsilverObj.Ele_EstmnNumber("Estimation no"));
		System.out.println("Estimation Number: "+EstmnNumber);
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// Step : Cancel Print
		// Expected Result: User should able to cancel the print
		Thread.sleep(8000);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);
		Thread.sleep(8000);
		base.robot_PressKey(KeyEvent.VK_ESCAPE);
		//---------------------------------------------------------------------//
		// Step 11: Go to cash
		// Expected Result: User should able to select Biling
		base.buttonClick(normalsalegoldandsilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Recall Estimate")), "The user is not able to view to Cash tiles option");

		// Step 12: Click on Save To Estimate Click on Save to estimation button
		// Expected Result: User should able to select Save To Estimate
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		base.excuteJsClick(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"));
		Thread.sleep(3000);
		base.setData(normalsalegoldandsilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("h4 ellipsis cell","1"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("Estimation")), "The user is not able to navigate to estimation page");

		// Step 13: Select the Transaction Type as Sales 
		// Expected Result: User should able to Select the Transaction Type as Sales       
		base.buttonClick(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(normalsalegoldandsilverObj.Sel_BankProofType("Transaction type"), "Sales");

		// Step 14: Click on Process Estimation  
		// Expected Result: User should able to Click on Process Estimation 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		// Step 15: Select any Sales Representative
		// Expected Result: sale representative is selected
		base.buttonClick(normalsalegoldandsilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		asrt.assertTrue(base.isExists(normalsalegoldandsilverObj.Ele_Transaction("Transaction")), "The user is not able to navigated to Transaction page");

		// Step 16:  Click on the Amount
		// Expected Result: User should able to click the amount 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Amnt("h1"));
		asrt.assertTrue(base.isExists(LoginPageObj.Edt_AlertText("HDFC (Card)")),"The user is not able to view the payment methods");

		// Step 17:  Select a Payment method(Cash or Card)
		// Expected Result: User should able to select the Payement Method 
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Card)"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(normalsalegoldandsilverObj.Edt_ApprCode("textInputArea"),"12345");
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("OK"));
		base.setData(normalsalegoldandsilverObj.Edt_ApprCode("textInputArea"),"12345");
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("OK"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("Close"));
		base.buttonClick(normalsalegoldandsilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 17:  Post the Invoice
		// Expected Result: User should able to Post the Invoice 
		base.buttonClick(normalsalegoldandsilverObj.Btn_Deposit("PRINT"));

	}	
}