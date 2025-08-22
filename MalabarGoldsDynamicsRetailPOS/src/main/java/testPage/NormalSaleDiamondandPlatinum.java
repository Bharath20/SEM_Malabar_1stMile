package testPage;

import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleDiamondandPlatinum_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import testData.CommonData;
import testData.NormalSaleDiamondandPlatinum_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.AppUtilities.SKUTypeResult;
import utilPack.BasePge;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;

public class NormalSaleDiamondandPlatinum extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	MathUtilities mathUtils;
	PdfUtilities pdfUtils;

	Login login = new Login(driver);
	LoginPage_Obj LoginPageObj= new LoginPage_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	NormalSaleDiamondandPlatinum_Obj NormalSaleDiamondAndPlatinumObj = new NormalSaleDiamondandPlatinum_Obj();
	NormalSaleDiamondandPlatinum_TestData NormalSaleDiamondandPlatinumTestData = new NormalSaleDiamondandPlatinum_TestData();

	public NormalSaleDiamondandPlatinum(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		mathUtils = new MathUtilities(base);
		pdfUtils = new PdfUtilities(base);
	}

	// <summary>
	// Test Case Title : Normal sale multiple diamond and platinum item
	// Automation ID : TC07_NormalSaleDiamondandPlatinum
	//Author: Neethu 
	// </summary>

	public void TC07_NormalSaleDiamondandPlatinum() throws Exception {

		// Step 1: Login to POS
		// Expected Result: User should be Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 80);

		// Step 2: Navigate to the Transaction page 

		Thread.sleep(3000);	
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3 : Select customer and click in add to estimate button
		// Step 4 : Click on add to estimate button
		appUtils.SearchByCustomerID(NormalSaleDiamondandPlatinumTestData.TC07_CustomerID, NormalSaleDiamondandPlatinumTestData.TC07_Option);

		// Step 5 : Scan Multiple Diamond and Platinum SKU's and click on Add to cart button
		// Step 6 : Select any one of the sales agent
		// Expected Result : Check Calculations
		// Check calculation : Billing Screen, No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,
		// TAX , Total Amount
		Map<String, String> ScannedSKUDataMap = new LinkedHashMap<>();
		List<String> ExpectedItemNames = new ArrayList<>();
		List<String> TotalForEachItem = new ArrayList<>();
		List<String> ItemNamesFromBillingPge =new ArrayList<>();
		List<String> ItemQuantity=new ArrayList<>();

		int i=0;
		double ActualSubTotal = 0.0;
		double TotalWithoutTax = 0.0;


		List<String> MultipleDiamondSkus      = appUtils.SelectMultipleSKUs( NormalSaleDiamondandPlatinumTestData.TC07_SKUCount,NormalSaleDiamondandPlatinumTestData.TC07_TransferType,
				NormalSaleDiamondandPlatinumTestData.TC07_ToCounterDiamond,NormalSaleDiamondandPlatinumTestData.TC07_MetalTypeDiamond);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		List<String> MultiplePlatinumSkus = appUtils.SelectMultipleSKUs( NormalSaleDiamondandPlatinumTestData.TC07_SKUCount,NormalSaleDiamondandPlatinumTestData.TC07_TransferType,
				NormalSaleDiamondandPlatinumTestData.TC07_ToCounterPlatinum,NormalSaleDiamondandPlatinumTestData.TC07_MetalTypePlatinum);

		Thread.sleep(1000);

		List<String> SkuList = new ArrayList<>();
		SkuList.addAll(MultipleDiamondSkus);
		SkuList.addAll(MultiplePlatinumSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
		for (i = 0; i < SkuList.size(); i++) 
		{    		
			appUtils.SKUIngridentTableValues(SkuList.get(i), i + 1,ScannedSKUDataMap);
			ExpectedItemNames.add(ScannedSKUDataMap.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItem.add(ScannedSKUDataMap.get("SKU_" + (i+1) + "_Total"));
			ItemQuantity.add(ScannedSKUDataMap.get("SKU_" + (i+1) + "_GrossWeight"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSKUDataMap, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		Map<String, String> TransactionFieldValues = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSKUDataMap);		
		String SubTotal = TransactionFieldValues.get("Subtotal");
		String Discount = TransactionFieldValues.get("Discount");
		String TotalGrossWeight = TransactionFieldValues.get("GrossWeight");
		String TotalNetWeight = TransactionFieldValues.get("NetWeight");
		String Tax = TransactionFieldValues.get("Tax");
		String TotalAmount = TransactionFieldValues.get("TotalAmount");
		String NetTotal = TransactionFieldValues.get("NetTotal");
		String LinesCount = TransactionFieldValues.get("LinesCount");

		//Displayed Item Name
		String ExpectedTrimmed="";
		List<WebElement> ItemNamesInBilling = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		for (int n=0; n<ItemNamesInBilling.size();n++) 
		{
			ItemNamesFromBillingPge.add(ItemNamesInBilling.get(n).getText().trim());
		}

		i=0;
		for (String Name : ExpectedItemNames)
		{
			String ExpectedName = Name.substring(Name.indexOf("-") + 2);
			String ItemNameFromBilling = ItemNamesInBilling.get(i).getText().trim();
			asrt.assertEquals(ExpectedName,ItemNameFromBilling, "Displayed Item name mismatch occured expected value is "+ExpectedName+"but got "+ItemNameFromBilling+ "in billing screen");
			i++;
		}

		//No of Product lines
		int ExpectedLinesCount = Integer.parseInt(LinesCount);
		int ActualProductCount = ItemNamesInBilling.size();    
		asrt.assertEquals(ExpectedLinesCount, ActualProductCount, "Mismatch in number of product lines in Billing screen");

		// display quantity	
		List<WebElement> BillScrnProdQty = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		for (i = 0; i < BillScrnProdQty.size(); i++)
		{
			String DisplayedItemQty = BillScrnProdQty.get(i).getText().trim().replaceAll("[^\\d.]", "");
			double DisplayedQty = Double.parseDouble(DisplayedItemQty);
			String ExpectedItemQty = ItemQuantity.get(i).trim().replaceAll("[^\\d.]", "");			
			double ExpectedQty = Double.parseDouble(ExpectedItemQty);

			asrt.assertEquals(DisplayedQty,ExpectedQty,"Value mismatch expected Qty value is " + ExpectedQty + " but Displayed Qty value got as " + DisplayedQty+ " in Billing page");
		}


		//Displayed TOTAL (without Tax)
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		for (int j =0; j<TotalsWithoutTax.size();j++) 
		{
			double ActualTotal = Double.parseDouble(TotalForEachItem.get(j));
			double ExpectedTotal = Double.parseDouble(TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""));
			asrt.assertEquals(ExpectedTotal, ActualTotal,"Value mismatch in Displayed TOTAL (without Tax) expected value is "+ExpectedTotal+"but got "+ActualTotal+ "Billing screen");
		}

		//Displayed TOTAL (without Tax)
		for (WebElement SubTotalElement : TotalsWithoutTax)
		{
			String SubTotalValue = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!SubTotalValue.isEmpty())
			{
				ActualSubTotal=ActualSubTotal + Double.parseDouble(SubTotalValue);
			}
		}
		double TaxAmount=ActualSubTotal*3/100;
		double ActualTotalAmount =ActualSubTotal+TaxAmount;

		//Displayed SubTotal
		double SumSubTotal = 0.0;
		SumSubTotal = Math.round(ActualSubTotal * 100.0) / 100.0;
		double ExpectedSubtotal = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(SumSubTotal, ExpectedSubtotal, "Mismatch in Displayed subtotal and calculated subtotal in Billing screen");
		System.out.println("ESumSubtotalr: "+SumSubTotal);

		//Tax amount Validation
		double ExpectedTax = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));	
		asrt.assertEquals(TaxAmount, ExpectedTax, 1.0, "Actual tax amount is not same as calculated tax amount ");

		//Total Amount
		double AmountTotalParsed = Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));
		double AmountTotal = Math.round(AmountTotalParsed * 100.0) / 100.0;
		asrt.assertEquals(AmountTotal, ActualTotalAmount, 1.0,"Mismatch in Total Amount and Calculated Total Amount in Billing screen");

		//Step 7 : In Billing, Click on "Save to Estimate" button	
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));

		//Step 8 : Click on Save estimation
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		System.out.println("Estimation Number: "+EstmnNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		Thread.sleep(7000);

		String NewPdfName  = pdfUtils.DownloadAndRenameLatestPDF("TC07_ProformaInvoice");
		String PdfPath  =System.getProperty("user.dir") + "\\Invoices\\"+NewPdfName+"";
		String Gst = Tax;
		String InvoiceTotal = TotalAmount;
		pdfUtils.ProFormaInvoicePDFVerifyNormal(
				PdfPath,
				SubTotal,
				Gst,
				InvoiceTotal,
				NetTotal,
				LinesCount,
				Double.parseDouble(TotalGrossWeight),
				Double.parseDouble(TotalNetWeight));

		// Step 9 :Click on Cash and then click on Recall estimate
		// Verfiy Invoice details
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);

		//Step 10:  Select the estimate and click on Recall estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//To validate lines count
		List<WebElement> ItemNamesFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		int ActualLinesCountInRecall = ItemNamesFromRecall.size();
		asrt.assertEquals(ExpectedLinesCount, ActualLinesCountInRecall,"Mismatch in Lines of count in Recall Estimation page");

		//To validate Item name
		for (int k =0; k<ItemNamesFromRecall.size();k++) 
		{			
			String ExpectedNameRecall = ItemNamesFromRecall.get(k).getText().trim();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			String ItemNameFromBilling = ItemNamesFromBillingPge.get(k);
			asrt.assertEquals(ExpectedNameRecall,ItemNameFromBilling,"Item name mismatch in Recall estimation page,expected value is "+ExpectedNameRecall+"but got "+ItemNameFromBilling);
		}

		//To validate quantity
		List<WebElement> QuantityFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
		for (i = 0; i < QuantityFromRecall.size(); i++)
		{
			String DisplayedQtyStr = QuantityFromRecall.get(i).getText().trim().replaceAll("[^\\d.]", "");
			double DisplayedQty = Double.parseDouble(DisplayedQtyStr);
			String ExpectedQtyStr = ItemQuantity.get(i).trim().replaceAll("[^\\d.]", "");			
			double ExpectedQty = Double.parseDouble(ExpectedQtyStr);

			asrt.assertEquals(DisplayedQty,ExpectedQty,"Qty Value mismatch in expected value as " + ExpectedQty + " but displayed value as " + DisplayedQty+ " in Recall Estimation page");
		}

		//To validate Total without tax
		double ActualTotalWithoutTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax")));
		asrt.assertEquals(ExpectedSubtotal,ActualTotalWithoutTax,"Total Amount without tax mismatch in Recall Estimation page");

		//To validate tax
		double ActualTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));
		asrt.assertEquals(ExpectedTax,ActualTax,"Tax amount mismatch inside Recall Estimation page");

		//To validate sub total
		double ActualSubTotalFromRecall = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")));
		double ExpectedSubTotal = Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(ExpectedSubTotal,ActualSubTotalFromRecall,"Sub total mismatch inside Recall estimation page");

		//Step 11: Select the Transaction Type as Sales 
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"),NormalSaleDiamondandPlatinumTestData.TC07_TransactionType );

		//Step 12 : Click on Process Estimation 
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

		//Step 13: Choose a sales representative
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();
		//Step 14: Click on the Amount
		//Step 15 : Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText(NormalSaleDiamondandPlatinumTestData.TC07_PaymentType));
		String PaymentAmountHDFC=base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleDiamondandPlatinumTestData.TC07_AprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleDiamondandPlatinumTestData.TC07_CardNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		//Step 16 : Post the Invoice
		//Expected Result: User should able to Post the Invoice
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		String NewPdfName1 = pdfUtils.DownloadAndRenameLatestPDF("TC07_ProformaInvoice"); 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		String PdfPath2 =System.getProperty("user.dir") + "\\Invoices\\"+NewPdfName1+"";
		String GrossAmount=SubTotal;
		String TotalValue=TotalAmount;
		String NetValue=NetTotal;
		String TotalDiscount = Discount;
		String TotalQtyPcs = LinesCount;

		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
				PdfPath2,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				SubTotal,
				NetValue,
				PaymentAmountHDFC,
				SkuList,
				ScannedSKUDataMap
				);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20"));
		Thread.sleep(5000);


		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Normal sale multiple diamond and platinum piece rate items
	// Automation ID : TC09
	// Author: Chandana Babu
	// </summary>
	public void TC09_NormalSaleDiamondandPlatinum() throws Exception {

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer
		// Step 4: Click on add to estimation
		appUtils.SearchByCustomerID(NormalSaleDiamondandPlatinumTestData.TC09_CustomerNo, UtilityTestData.MenuBarOptions[5]);

		// Step 5: Scan multiple diamond and platinum piece rate SKU and click on Add to cart button
		// Step 6: Select any one of the sales agent
		List <String> RequiredPieceRateSku = appUtils.FetchPieceRateItem(UtilityTestData.DiamondPieceRateSkus, 2);
		List<String> ItemNames = new ArrayList<>();
		List<String> TotalForEachItem = new ArrayList<>();
		List<String> Quantity = new ArrayList<>();
		Map<String, String> ItemDetails = new HashMap<>();
		for (int i = 0; i < RequiredPieceRateSku.size(); i++) {
			appUtils.SKUIngridentTableValues(RequiredPieceRateSku.get(i), i + 1, ItemDetails);
			ItemNames.add(ItemDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItem.add(ItemDetails.get("SKU_" + (i+1) + "_Total"));
			Quantity.add(ItemDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			Map<String , String >CurrentSkuData = appUtils.ExtractDataForSku(ItemDetails, i+1);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, i+1);

		}
		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(RequiredPieceRateSku, ItemDetails);
		String SubTotal = TransactionPageItems.get("Subtotal");
		String Discount = TransactionPageItems.get("Discount");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight = TransactionPageItems.get("NetWeight");
		String Gst = TransactionPageItems.get("Tax");
		String InvoiceTotal = TransactionPageItems.get("TotalAmount");
		String NetTotal = TransactionPageItems.get("NetTotal");
		String LinesCount = TransactionPageItems.get("LinesCount");

		int ExpectedLinesCount = Integer.parseInt(LinesCount);
		double ExpectedGrossWeight = Double.parseDouble(TotalGrossWeight);
		double ExpectedSubTotal = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTax = Double.parseDouble(Gst.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTotalAmount = Double.parseDouble(InvoiceTotal.trim().replaceAll("[^\\d.]", ""));
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ActualLinesCount = AllProductRows.size();
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		double ActualSubTotal = 0.00;
		for (WebElement SubTotalElement : TotalsWithoutTax)
		{
			String Text = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!Text.isEmpty())
			{
				ActualSubTotal=ActualSubTotal + Double.parseDouble(Text);
			}
		}
		double ActualTaxAmount = Math.round((ActualSubTotal*3/100)*100.0)/100.0;
		double ActualTotalAmount = ActualSubTotal+ActualTaxAmount;

		//To validate Line count, Item name, Quantity, Sub total, tax, Total amount in Billing page
		asrt.assertEquals(ExpectedLinesCount, ActualLinesCount,"Line count value mismatch: Expected is "+ExpectedLinesCount+"but got "+ActualLinesCount+" in Billing page");

		List<WebElement> ItemNamesFromBilling = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> TextItemNamesFromBilling =new ArrayList<>();
		for (int n=0; n<ItemNamesFromBilling.size();n++) {
			TextItemNamesFromBilling.add(ItemNamesFromBilling.get(n).getText().trim());
		}
		int i=0;
		for (String Name : ItemNames) {
			String ExpectedName = Name.substring(Name.indexOf("-") + 2);
			String ItemNameFromBilling = ItemNamesFromBilling.get(i).getText().trim();

			asrt.assertEquals(ExpectedName,ItemNameFromBilling, "Item name mismatch: Expected value is "+ExpectedName+"but got "+ItemNameFromBilling+" in Billing page");
			i++;
		}

		List<WebElement> AllSKUElements = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5"));
		List<String> QuantityFromBilling = new ArrayList<>();
		int n=0;
		for (WebElement Qty : AllSKUElements) {
			double ActualGrossWeightFromBilling = Double.parseDouble(Qty.getText());
			QuantityFromBilling.add(Qty.getText());
			double ExpectedGrossWeightFromItemDetails = Double.parseDouble(Quantity.get(n));
			asrt.assertEquals(ActualGrossWeightFromBilling,ExpectedGrossWeightFromItemDetails,"GrossWeight mismatch: Expected value is "+ActualGrossWeightFromBilling+"but got "+ExpectedGrossWeightFromItemDetails+" in Billing page");
			n++;
		}

		for (int j =0; j<TotalsWithoutTax.size();j++) {
			double ActualTotal = Double.parseDouble(TotalForEachItem.get(j));
			double ExpectedTotal = Double.parseDouble(TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpectedTotal, ActualTotal,1.0,"Total value mismatch: Expected value is "+ExpectedTotal+"but got "+ActualTotal+" in Billing page");
		}

		asrt.assertEquals(ExpectedSubTotal,ActualSubTotal,1.0,"SubTotal mismatch: Expected value is "+ExpectedSubTotal+"but got "+ActualSubTotal+" in Billing page");
		asrt.assertEquals(ExpectedTax,ActualTaxAmount,1.0,"TaxAmount mismatch: Expected value is "+ExpectedTax+"but got "+ActualTaxAmount+" in Billing page");
		asrt.assertEquals(ExpectedTotalAmount,ActualTotalAmount,1.0,"TotalAmount mismatch: Expected value is "+ExpectedTotalAmount+"but got "+ActualTotalAmount+" in Billing page");

		// Step 7: In Billing, Click on "Save to Estimate" button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));

		// Step 8: Click on Save estimation 
		// Expected Result: Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC09_ProFormaInvoice");
		String ProFormaInvoice =System.getProperty("user.dir") + "\\Invoices\\"+ProFormaInvoiceName+"";
		pdfUtils.ProFormaInvoicePDFVerifyNormal(
				ProFormaInvoice,
				SubTotal,
				Gst,
				InvoiceTotal,
				NetTotal,
				LinesCount,
				Double.parseDouble(TotalGrossWeight),
				Double.parseDouble(TotalNetWeight)
				);

		// Step 9: Recall estimate from cashier screen
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);

		// Step 10:Select estimate and click on recall estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//To validate lines count, Item name, Quantity, Tax,  Sub Total in Recall page
		List<WebElement> ItemNamesFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		int ActualLinesCountInRecall = ItemNamesFromRecall.size();

		asrt.assertEquals(ExpectedLinesCount, ActualLinesCountInRecall,"Lines Count mismatch: Expected value is "+ExpectedLinesCount+"but got "+ActualLinesCountInRecall+" in Recall page");

		for (int k =0; k<ItemNamesFromRecall.size();k++) {
			String ExpectedNameInRecall = ItemNamesFromRecall.get(k).getText().trim();
			String ActualItemNameFromBilling = TextItemNamesFromBilling.get(k);

			asrt.assertEquals(ExpectedNameInRecall,ActualItemNameFromBilling,"Item name mismatch: Expected value is "+ExpectedNameInRecall+"but got "+ActualItemNameFromBilling+" in Recall page");
		}

		List<WebElement> QuantityFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
		for(int l =0; l<QuantityFromRecall.size();l++) {
			double ActualQty = Double.parseDouble(QuantityFromRecall.get(l).getText().trim());
			double ExpectedQty = Double.parseDouble(QuantityFromBilling.get(l));
			asrt.assertEquals(ExpectedQty,ActualQty,1.0,"Quantity mismatch: Expected value is "+ExpectedQty+"but got "+ActualQty+" in Recall page");
		}

		double ActualTotalWithoutTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax")));
		asrt.assertEquals(ExpectedSubTotal,ActualTotalWithoutTax,1.0,"TotalWithoutTax mismatch: Expected value is "+ExpectedSubTotal+"but got "+ActualTotalWithoutTax+" in Recall page");
		double ActualTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));
		asrt.assertEquals(ExpectedTax,ActualTax,1.0,"Tax Value mismatch: Expected value is "+ExpectedTax+"but got "+ActualTax+" in Recall page");
		double ActualSubTotalFromRecall = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")));
		asrt.assertEquals(ExpectedTotalAmount,ActualSubTotalFromRecall,1.0,"Subtotal mismatch: Expected value is "+ExpectedTotalAmount+"but got "+ActualSubTotalFromRecall+" in Recall page");

		// Step 11:Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSaleDiamondandPlatinumTestData.TC09_TransactionType);

		// Step 12:Click on Process Estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 13:Choose a sales representative
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		// Step 14:Click on the Amount
		// Step 15:Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText(NormalSaleDiamondandPlatinumTestData.TC09_PaymentMethod));
		String PaymentAmountHDFC = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleDiamondandPlatinumTestData.TC09_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleDiamondandPlatinumTestData.TC09_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 16:  Post the Invoice
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		//Thread.sleep(5000);
		String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC09_TaxInvoice");
		//Thread.sleep(5000);		
		String TaxInvoice =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";	
		String GrossAmount=SubTotal;
		String TaxableValue = SubTotal;
		String TotalValue=InvoiceTotal;
		String TotalQtyPcs = LinesCount;
		String TotalDiscount = Discount;
		String NetValue=String.format("%.2f", Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", "")));
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
				TaxInvoice,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentAmountHDFC,
				RequiredPieceRateSku,
				ItemDetails
				);	
		//SalesReturn and Counter In
		appUtils.SalesReturnFlow(NormalSaleDiamondandPlatinumTestData.TC09_CustomerNo, PdfInvoiceNo, NormalSaleDiamondandPlatinumTestData.TC09_RequiredReturnPcsRateSKUs);
		appUtils.CounterFlow(NormalSaleDiamondandPlatinumTestData.TC09_ToCounter, NormalSaleDiamondandPlatinumTestData.TC09_MetalType, RequiredPieceRateSku, UtilityTestData.Terminal);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Normal sale with multiple piece rate items and making rate items-platinum and diamond
	// Automation ID : TC11
	// Author : Robin T Abraham
	// </summary>
	public void TC11_NormalSaleDiamondandPlatinum() throws Exception {

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer
		// Step 4: Click on add to estimation
		appUtils.SearchByCustomerID(NormalSaleDiamondandPlatinumTestData.TC11_CustomerNo, UtilityTestData.MenuBarOptions[5]);

		// Step 5: Scan multiple Diamond and Platinum making rate and piece rate SKUs, and click on the Add to Cart button
		// Step 6: Select any one of the sales agent
		List<String> AllSkuList = new ArrayList<>();

		List<String> RequiredPcsRateItems = appUtils.FetchPieceRateItem(UtilityTestData.DiamondPieceRateSkus, 1);
		/*List<String> RequiredMakingRateDiamondItems = appUtils.SelectMultipleSKUs(
				NormalSaleDiamondandPlatinumTestData.TC11_MakingRateDiamondSKUCount,
				NormalSaleDiamondandPlatinumTestData.TC11_MakingRateDiamondTransferType,
				NormalSaleDiamondandPlatinumTestData.TC11_MakingRateDiamondFromCounter,
				NormalSaleDiamondandPlatinumTestData.TC11_MakingRateDiamondMetalType
				);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));*/

		List<String> RequiredMakingRatePlatinumItems = appUtils.SelectMultipleSKUs(
				NormalSaleDiamondandPlatinumTestData.TC11_MakingRatePlatinumSKUCount,
				NormalSaleDiamondandPlatinumTestData.TC11_MakingRatePlatinumTransferType,
				NormalSaleDiamondandPlatinumTestData.TC11_MakingRatePlatinumFromCounter,
				NormalSaleDiamondandPlatinumTestData.TC11_MakingRatePlatinumMetalType
				);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		AllSkuList.addAll(RequiredPcsRateItems);
		//AllSkuList.addAll(RequiredMakingRateDiamondItems);
		AllSkuList.addAll(RequiredMakingRatePlatinumItems);

		List<String> ItemNames = new ArrayList<>();
		List<String> SkuQuantity = new ArrayList<>();
		List<String> TotalForEachItem = new ArrayList<>();
		Map<String, String> BillingDetailsMap = new HashMap<>();
		for (int i = 0; i < AllSkuList.size(); i++) {
			appUtils.SKUIngridentTableValues(AllSkuList.get(i), i + 1, BillingDetailsMap);
			ItemNames.add(BillingDetailsMap.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItem.add(BillingDetailsMap.get("SKU_" + (i+1) + "_Total"));
			SkuQuantity.add(BillingDetailsMap.get("SKU_" + (i+1) + "_GrossWeight"));

			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(BillingDetailsMap, i+1);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, i+1);
		}
		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(AllSkuList, BillingDetailsMap);
		String SubTotal = TransactionPageItems.get("Subtotal");
		String Discount = TransactionPageItems.get("Discount");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight = TransactionPageItems.get("NetWeight");
		String Gst = TransactionPageItems.get("Tax");
		String InvoiceTotal = TransactionPageItems.get("TotalAmount");
		String NetTotal = TransactionPageItems.get("NetTotal");
		String LinesCount = TransactionPageItems.get("LinesCount");


		int ExpectedLinesCount = Integer.parseInt(LinesCount);
		//double ExpectedGrossWeight = Double.parseDouble(TotalGrossWeight);
		double ExpectedSubTotal = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTax = Double.parseDouble(Gst.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTotalAmount = Double.parseDouble(InvoiceTotal.trim().replaceAll("[^\\d.]", ""));
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ActualLinesCount = AllProductRows.size();
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		double ActualSubTotal = 0.0;
		for (WebElement SubTotalElement : TotalsWithoutTax)
		{
			String Text = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!Text.isEmpty())
			{
				ActualSubTotal=ActualSubTotal + Double.parseDouble(Text);
			}
		}
		double ActualTaxAmount = ActualSubTotal*3/100;
		double ActualTotalAmount = ActualSubTotal+ActualTaxAmount;

		//double ActualGrossWeight = Double.parseDouble(base.GetText(LoginPageObj.Ele_ErrorMessage("height48 width48 center primaryFontColor h5")).trim());

		//To validate Line count, Item name, Quantity, Sub total, tax, Total amount in Billing page
		asrt.assertEquals(ExpectedLinesCount, ActualLinesCount,"Lines count mismatch: Expected value is "+ExpectedLinesCount+"but got "+ActualLinesCount+" in Billing page");

		List<WebElement> ItemNamesFromBillingPge = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> TextItemNamesFromBillingPge =new ArrayList<>();
		for (int n=0; n<ItemNamesFromBillingPge.size();n++) {
			TextItemNamesFromBillingPge.add(ItemNamesFromBillingPge.get(n).getText().trim());
		}
		int i=0;
		for (String ItemName : ItemNames) {
			String ExpectedItemName = ItemName.substring(ItemName.indexOf("-") + 2);
			String ItemNameFromBillingPage = ItemNamesFromBillingPge.get(i).getText().trim();

			asrt.assertEquals(ExpectedItemName,ItemNameFromBillingPage, "Item name mismatch: Expected value is "+ExpectedItemName+"but got "+ItemNameFromBillingPage+" in Billing page");
			i++;
		}

		List<WebElement> AllSkuElements = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5"));
		List<String> QuantityFromBillingPge = new ArrayList<>();
		int n=0;
		for (WebElement Qty : AllSkuElements) {
			double ActualGrossWeightFromBillingPge = Double.parseDouble(Qty.getText());
			QuantityFromBillingPge.add(Qty.getText());
			double ExpectedGrossWeightFromItemDetailsPge = Double.parseDouble(SkuQuantity.get(n));
			asrt.assertEquals(ActualGrossWeightFromBillingPge,ExpectedGrossWeightFromItemDetailsPge,"GrossWeight mismatch: Expected value is "+ActualGrossWeightFromBillingPge+"but got "+ExpectedGrossWeightFromItemDetailsPge+" in Billing page");
			n++;
		}

		for (int j =0; j<TotalsWithoutTax.size();j++) {
			double ActualTotal = Double.parseDouble(TotalForEachItem.get(j));
			double ExpectedTotal = Double.parseDouble(TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpectedTotal, ActualTotal,"Item Total value mismatch: Expected value is "+ExpectedTotal+"but got "+ActualTotal+" in Billing page");
		}

		asrt.assertEquals(ExpectedSubTotal,ActualSubTotal, 1.0,"Item SubTotal value mismatch: Expected value is "+ExpectedSubTotal+"but got "+ActualSubTotal+" in Billing page");
		asrt.assertEquals(ExpectedTax,ActualTaxAmount,1.0,"Item TaxAmount value mismatch: Expected value is "+ExpectedTax+"but got "+ActualTaxAmount+" in Billing page");
		asrt.assertEquals(ExpectedTotalAmount,ActualTotalAmount, 1.0,"Item TotalAmount mismatch: Expected value is "+ExpectedTotalAmount+"but got "+ActualTotalAmount+" in Billing page");

		// Step 7: In Billing, Click on "Save to Estimate" button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));

		// Step 8: Click on Save estimation
		// Expected: Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC11_TaxInvoice");
		String TaxableValue = SubTotal;
		String ProFormaInvoice =System.getProperty("user.dir") + "\\Invoices\\"+ProFormaInvoiceName+"";
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

		// Step 9: Recall estimate from cashier screen
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);

		// Step 10:Select estimate and click on recall estimation
		// Expected:Verify whether the recalled item is the same as the item in the cart
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//To validate lines count, Item name, Quantity, Tax,  Sub Total in Recall page
		List<WebElement> ItemNamesFromRecallPge = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		int ActualLinesCountInRecall = ItemNamesFromRecallPge.size();

		asrt.assertEquals(ExpectedLinesCount, ActualLinesCountInRecall,"Lines Count mismatch: Expected value is "+ExpectedLinesCount+"but got "+ActualLinesCountInRecall+" in Recall page");

		for (int k =0; k<ItemNamesFromRecallPge.size();k++) {
			String ExpectedItemNameInRecallPage = ItemNamesFromRecallPge.get(k).getText().trim();
			String ActualItemNameFromBilling = TextItemNamesFromBillingPge.get(k);

			asrt.assertEquals(ExpectedItemNameInRecallPage,ActualItemNameFromBilling,"Item Name mismatch: Expected value is "+ExpectedItemNameInRecallPage+"but got "+ActualItemNameFromBilling+" in Recall page");
		}

		List<WebElement> SkuQuantityFromRecallPge = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
		for(int l =0; l<SkuQuantityFromRecallPge.size();l++) {
			double ActualQty = Double.parseDouble(SkuQuantityFromRecallPge.get(l).getText().trim());
			double ExpectedQty = Double.parseDouble(QuantityFromBillingPge.get(l));
			asrt.assertEquals(ExpectedQty,ActualQty,1.0,"Quantity mismatch: Expected value is "+ExpectedQty+"but got "+ActualQty+" in Recall page");
		}

		double ActualTotalWithoutTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax")));
		asrt.assertEquals(ExpectedSubTotal,ActualTotalWithoutTax,"Total value WithoutTax mismatch: Expected value is "+ExpectedSubTotal+"but got "+ActualTotalWithoutTax+" in Recall page");
		double ActualTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));
		asrt.assertEquals(ExpectedTax,ActualTax,"Tax value mismatch: Expected value is "+ExpectedTax+"but got "+ActualTax+" in Recall page");
		double ActualSubTotalFromRecallPge = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")));
		asrt.assertEquals(ExpectedTotalAmount,ActualSubTotalFromRecallPge,"Subtotal mismatch: Expected value is "+ExpectedTotalAmount+"but got "+ActualSubTotalFromRecallPge+" in Recall page");

		// Step 11:Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"),NormalSaleDiamondandPlatinumTestData.TC11_TransactionType);

		// Step 12:Click on Process Estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 13:Choose a sales representative
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();
		
		// Step 14:Click on the Amount
		// Step 15:Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText(NormalSaleDiamondandPlatinumTestData.TC11_PaymentMethod));
		String PaymentAmountHDFC=base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleDiamondandPlatinumTestData.TC11_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleDiamondandPlatinumTestData.TC11_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 16:  Post the Invoice
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(5000);
		String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC11_TaxInvoice");
		String Adjustment="0";
		Thread.sleep(5000);	
		String TaxInvoice =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";
		String GrossAmount=SubTotal;
		String TotalDiscount = Discount;
		String TotalQtyPcs = LinesCount;
		String TotalValue = InvoiceTotal;
		String NetValue=String.format("%.2f", Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", "")));
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
				TaxInvoice,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentAmountHDFC,
				AllSkuList,
				BillingDetailsMap
				);

		appUtils.SalesReturnFlow(NormalSaleDiamondandPlatinumTestData.TC11_CustomerNo, PdfInvoiceNo, NormalSaleDiamondandPlatinumTestData.TC11_ReturnProducts);
		appUtils.CounterFlow(NormalSaleDiamondandPlatinumTestData.TC11_FromCounter,NormalSaleDiamondandPlatinumTestData.TC11_MetalType,RequiredPcsRateItems ,UtilityTestData.Terminal);

		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

	}

	// <summary>
	// Test Case Title : Normal sale diamond piece rate and per gram rate item
	// Automation ID : TC10
	// Author : Sangeetha M S
	// </summary>
	public void TC10_NormalSaleDiamondandPlatinum() throws Exception
	{
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 2: Click on Transaction button
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer
		// Step 4: Click on add to estimate button
		appUtils.SearchByCustomerID(NormalSaleDiamondandPlatinumTestData.TC10_CustomerId, UtilityTestData.MenuBarOptions[5]);

		//Step 5 : Scan diamond piece rate and per gram rate SKU and click on Add to cart button
		//Step 6 : Select any one of the sales agent
		//Expected Result : Check Calculations
		//Check calculation : Billing Screen, No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,
		//TAX , Total Amount
		Map<String, String> SkuDetails = new LinkedHashMap<>();	
		List <String> SkuValueDiamondPiece = appUtils.FetchPieceRateItem(UtilityTestData.DiamondPieceRateSkus, 1);
		List<String> SkuListDiamond = appUtils.SelectMultipleSKUs(NormalSaleDiamondandPlatinumTestData.TC10_SkuCount,NormalSaleDiamondandPlatinumTestData.TC10_TransferTypeDiamond,NormalSaleDiamondandPlatinumTestData.TC10_FromCounter,NormalSaleDiamondandPlatinumTestData.TC10_MetalType);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		List<String> SkuList = new ArrayList<>();
		SkuList.addAll(SkuValueDiamondPiece);
		SkuList.addAll(SkuListDiamond);

		for (int i = 0;i < SkuList.size();i++)
		{
			String CurrentDiamondSku = SkuList.get(i);
			int DiamondSkuCount = i + 1;
			appUtils.SKUIngridentTableValues(CurrentDiamondSku.toString(), DiamondSkuCount, SkuDetails);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, DiamondSkuCount);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, DiamondSkuCount);
		}

		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(SkuList, SkuDetails);
		String SubTotal = TransactionPageItems.get("Subtotal");
		String Discount = TransactionPageItems.get("Discount");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight = TransactionPageItems.get("NetWeight");
		String Tax = TransactionPageItems.get("Tax");
		String TotalAmount = TransactionPageItems.get("TotalAmount");
		String NetTotal = TransactionPageItems.get("NetTotal");
		String LinesCount = TransactionPageItems.get("LinesCount");
		mathUtils.ValidateTransactionLineCalculation(TransactionPageItems, SkuDetails);

		//Step 7 : Click on Save to estimation button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));

		//Step 8 : Save estimate
		//Expected Result : Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC10_ProformaInvoice");
		String ProFormaInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+ProFormaInvoiceName+"";
		String Gst= Tax;
		String TaxableValue=SubTotal;
		String InvoiceTotal=TotalAmount;
		pdfUtils.ProFormaInvoicePDFVerifyNormal(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				LinesCount,
				Double.parseDouble(TotalGrossWeight),
				Double.parseDouble(TotalNetWeight));

		//Step 9 : Recall estimate from cashier screen
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);

		//Step 10 : Select estimate and click on recall estimation
		//Expected Results : Verify whether the recalled item is the same as the item in the cart.
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//Step 11 : Select the Transaction Type as Sales 
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"),NormalSaleDiamondandPlatinumTestData.TC10_TransactionType);

		//Step 12 : Click on Process Estimation 
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		//Step 13 : Choose a sales representative
		// Expected Result: Validate Cashier Screen after recall *No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));	
		appUtils.HandleSpecifyRateModalInRecall();
		
		Map<String, String> TransactionRecallDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, SkuDetails);	
		mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLine, SkuDetails);

		//Step 14 : Click on the Amount
		//Step 15 : Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentAmountHDFC=base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleDiamondandPlatinumTestData.TC10_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleDiamondandPlatinumTestData.TC10_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		//Step 16 : Post the Invoice
		//Expected Result : Check final invoice details
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		String SaleInvoice= pdfUtils.DownloadAndRenameLatestPDF("TC10_TaxInvoice"); 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));	
		String SalesInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+SaleInvoice+"";
		String GrossAmount = SubTotal;
		String TotalDiscount=Discount;
		String TotalQtyPcs=LinesCount;
		String NetValue=NetTotal;

		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
				SalesInvoicePath,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentAmountHDFC,
				SkuList,
				SkuDetails);

		appUtils.SalesReturnFlow(NormalSaleDiamondandPlatinumTestData.TC10_CustomerId, PdfInvoiceNo, NormalSaleDiamondandPlatinumTestData.TC10_ReturnProducts);
		appUtils.CounterFlow(NormalSaleDiamondandPlatinumTestData.TC10_FromCounter,NormalSaleDiamondandPlatinumTestData.TC10_MetalType,SkuValueDiamondPiece,UtilityTestData.Terminal);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Normal sale normal platinum,dual tone platinum and platinum with diamond
	// Automation ID : TC08
	// </summary>
	public void TC08_NormalSaleDiamondandPlatinum() throws Exception {

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2 : Click on Transaction button
		appUtils.HamBurgerButtonClick("iconShop");

		//Step 3 : Select the customer 		
		// Step 4 : Click on add to estimate button
		appUtils.SearchByCustomerID(NormalSaleDiamondandPlatinumTestData.TC08_CustomerId,NormalSaleDiamondandPlatinumTestData.TC08_Option);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		//Step 5: Scan normal platinum,dual tone platinum and platinum with diamond and click on Add to cart button
		//Step 6. Select any one of the sales agent
		//Expected Result: Check calculation, Note:Billing Screen- *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		List<String>ScannedSkus = appUtils.ScanMultiplePlatinumSkus(NormalSaleDiamondandPlatinumTestData.TC08_TransferType,
																	NormalSaleDiamondandPlatinumTestData.TC08_FromCounter,
																	NormalSaleDiamondandPlatinumTestData.TC08_MetalType);
		Map<String, List<String>> PlatinumSkus = appUtils.FetchPlatinumSkuWithDiffIngredients(ScannedSkus,
																	NormalSaleDiamondandPlatinumTestData.TC08_skuCountPerCategory);
		System.out.println("PlatinumSkus "+PlatinumSkus);

		List<String> NormalPlatinum = PlatinumSkus.get("NORMAL");
		List<String> DualTonePlatinum = PlatinumSkus.get("DUAL");
		List<String> PlatinumWithDiamonds = PlatinumSkus.get("DIAMOND");
		
		List<String> SkuList = new ArrayList<>();
		SkuList.addAll(NormalPlatinum);
		SkuList.addAll(DualTonePlatinum);
		SkuList.addAll(PlatinumWithDiamonds);
		System.out.println("SkuList "+SkuList);
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		for (int i = 0; i < SkuList.size(); i++) {
			int SkuNumber = i + 1;
			String Sku = SkuList.get(i);
			appUtils.SKUIngridentTableValues(Sku, SkuNumber, ScannedSkuDataMap);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuNumber);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuNumber);
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = TransactionDataLine.get("Subtotal");
		String Discount = TransactionDataLine.get("Discount");
		String TotalGrossWeight = TransactionDataLine.get("GrossWeight");
		String TotalNetWeight = TransactionDataLine.get("NetWeight");
		String Tax = TransactionDataLine.get("Tax");
		String TotalAmount = TransactionDataLine.get("TotalAmount");
		String NetTotal = TransactionDataLine.get("NetTotal");
		String LinesCount = TransactionDataLine.get("LinesCount");
		List<String> ItemNamesBillngScrn = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5"));
		List<String> QtyBillingScrn = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5"));
		List<String> TotWithoutTaxForEachItem = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5"));
		mathUtils.ValidateTransactionLineCalculation(TransactionDataLine, ScannedSkuDataMap);

		//Step 7: Click on Save to estimation button
		//Step 8: save estimate
		//Expected Result: Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC08_ProformaInvoice");
		Thread.sleep(2000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst= Tax;
		String TaxableValue=SubTotal;
		String InvoiceTotal=TotalAmount;
		pdfUtils.ProFormaInvoicePDFVerifyNormal(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				LinesCount,
				Double.parseDouble(TotalGrossWeight),
				Double.parseDouble(TotalNetWeight));

		//Step 9:Recall estimate from cashier screen
		//Step 10: Select estimate and click on recall estimation
		//Expected Result: Verify whether the recalled item is the same as the item in the cart. Note:-Cashier Screen after recall- *No of Product lines,*Displayed Item Name
		//*Displayed Quantity,*Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		// Step 11: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSaleDiamondandPlatinumTestData.TC08_TransactionType);

		// Step 12: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 13: Select any Sales Representative
		// Note:-13.Cashier Screen after recall*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		base.setZoom(driver, 60);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		Map<String, String> TransactionRecallDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);

		mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLine, ScannedSkuDataMap);

		// Step 14:  Click on the Amount
		// Step 15:  Select a Payment method(Cash or Card)
		String PaymentHDFCCard =base.GetText(NormalSaleGoldAndSilverObj.Sel_AmountDue("Amount due"));
		appUtils.PaymentAfterRecallEstimate(NormalSaleDiamondandPlatinumTestData.TC08_PaymentMethod);

		// Step 16:  Post the Invoice
		// Expected Result:  Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC08_SaleInvoice");
		Thread.sleep(2000);
		String SaleInvoicePdfPath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice;
		String GrossAmount = SubTotal;
		String TotalDiscount=Discount;
		String TotalQtyPcs=LinesCount;
		String NetValue=NetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
				SaleInvoicePdfPath,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				SkuList,
				ScannedSkuDataMap);
		System.out.println("PDF INVOICE NO:-"+PdfInvoiceNo);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		//Sales return and counter in flow
		appUtils.SalesReturnFlow(NormalSaleDiamondandPlatinumTestData.TC08_CustomerId, PdfInvoiceNo,NormalSaleDiamondandPlatinumTestData.TC08_ReturnProducts);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		appUtils.CounterFlow(NormalSaleDiamondandPlatinumTestData.TC08_ToCounter,
				NormalSaleDiamondandPlatinumTestData.TC08_MetalType,
				SkuList ,
				Utility_TestData.Terminal);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}


}