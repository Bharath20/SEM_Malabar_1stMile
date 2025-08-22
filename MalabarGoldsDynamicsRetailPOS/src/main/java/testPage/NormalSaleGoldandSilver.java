
package testPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import testData.CommonData;
import testData.NormalSaleGoldandSilver_TestData;
import testData.NormalSalesReturnGoldSilverReturnSaleCounter_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.AppUtilities.SKUTypeResult;
import utilPack.BasePge;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;


public class NormalSaleGoldandSilver extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	MathUtilities mathUtils;
	PdfUtilities pdfUtils;

	Login Login = new Login(driver); 
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();
	Utility_TestData UtilityTestData=new Utility_TestData();
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	NormalSaleGoldandSilver_TestData NormalSaleGoldAndSilverTestData =new NormalSaleGoldandSilver_TestData();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj = new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();	
	NormalSalesReturnGoldSilverReturnSaleCounter_TestData NormalSalesReturnGoldSilverReturnSaleCounterTestData = new NormalSalesReturnGoldSilverReturnSaleCounter_TestData();

	public NormalSaleGoldandSilver(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base);
		mathUtils=new MathUtilities(base);
		pdfUtils=new PdfUtilities(base);
	}

	// <summary>
	// Test Case Title : Normal sale multiple SLA and Gold item
	// Automation ID : TC03
	// Author: Gokul.P 
	// </summary>
	public void TC03_NormalSaleGoldandSilver() throws Exception
	{
		//Step 1: Login to POS
		Login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		Thread.sleep(5000);

		// Step 2: Navigate to the Transaction page
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer
		// Step 4: Click on add to estimation
		appUtils.SearchByCustomerID(NormalSaleGoldAndSilverTestData.TC03_CustomerID, UtilityTestData.MenuBarOptions[5]);

		// Step 5: Scan multiple diamond and platinum piece rate SKU and click on Add to cart button
		// Expected: Check calculation Note:Billing Screen:No of Product lines,Displayed Item Name
		// Displayed Quantity ,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		// Step 6: Select any one of the sales agent
		List<String> SkuList = appUtils.SelectMultipleSKUs(NormalSaleGoldAndSilverTestData.SkuCountToAdd,NormalSaleGoldAndSilverTestData.TransferType,NormalSaleGoldAndSilverTestData.FromCounter1,NormalSaleGoldAndSilverTestData.MetalType1);
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));		
		SKUTypeResult SkuSilverArticle = appUtils.GetMakingAndPcsRateItems(NormalSaleGoldAndSilverTestData.TransferType, NormalSaleGoldAndSilverTestData.FromCounter2, NormalSaleGoldAndSilverTestData.MetalType2,NormalSaleGoldAndSilverTestData.FromCounter2, NormalSaleGoldAndSilverTestData.Product);
		List<String> SilverArticleList = SkuSilverArticle.MakingRateSKU;
		List<String> RequiredSilverArticleList = appUtils.GetRequiredMakingRateItems(2, SilverArticleList);

		Thread.sleep(3000);

		SkuList.addAll(RequiredSilverArticleList);
		//			SkuList.addAll(NormalSaleGoldAndSilverTestData.TC03_SkuGold);		
		//			SkuList.addAll(NormalSaleGoldAndSilverTestData.TC03_SkuSilver);
		//		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		//		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));		
		List<String> ItemNames = new ArrayList<>();
		List<String> ItemQuantity = new ArrayList<>();
		List<String> TotalForEachItem = new ArrayList<>();
		Map<String, String> ItemData = new HashMap<>();
		for (int i = 0; i < SkuList.size(); i++)
		{  
			Thread.sleep(2000);
			appUtils.SKUIngridentTableValues(SkuList.get(i), i + 1, ItemData);
			ItemNames.add(ItemData.get("SKU_" + (i+1) + "_HeaderName"));
			ItemQuantity.add(ItemData.get("SKU_" + (i+1) + "_GrossWeight"));
			TotalForEachItem.add(ItemData.get("SKU_" + (i+1) + "_Total"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ItemData, i+1);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, i+1);
		}

		Map<String, String> Results = appUtils.TransactionSKUsLinesVerify(SkuList, ItemData);
		String SubTotal = Results.get("Subtotal");
		String Discount = Results.get("Discount");
		String TotalGrossWeight = Results.get("GrossWeight");
		String TotalNetWeight = Results.get("NetWeight");
		String Gst = Results.get("Tax");
		String InvoiceTotal = Results.get("TotalAmount");
		String NetTotal = Results.get("NetTotal");
		String LinesCount = Results.get("LinesCount");
		int ExpectedLinesCount = Integer.parseInt(LinesCount);
		//	double ExpectedGrossWeight = Double.parseDouble(TotalGrossWeight);
		double ExpectedSubTotal = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTax = Double.parseDouble(Gst.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTotalAmount = Double.parseDouble(InvoiceTotal.trim().replaceAll("[^\\d.]", ""));
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ActualLinesCount = AllProductRows.size();
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		double ActualSubTotal = 0.0;
		for (WebElement SubTotalElement : TotalsWithoutTax)
		{
			String SubTotalText = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!SubTotalText.isEmpty())
			{
				ActualSubTotal=ActualSubTotal + Double.parseDouble(SubTotalText);
			}
		}
		double ActualTaxAmount = Math.round((ActualSubTotal*3/100)*100.0)/100.0;
		double ActualTotalAmount = ActualSubTotal+ActualTaxAmount;

		//To validate Line count, Item name, Quantity, Sub total, tax, Total amount in Billing page
		asrt.assertEquals(ExpectedLinesCount, ActualLinesCount,"Line Count mismatch: Expected is  "+ExpectedLinesCount+"but got "+ActualLinesCount+" in Billing page");

		List<WebElement> ItemNamesFromBillingPge = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> TextItemNamesFromBillingPge =new ArrayList<>();
		for (int n=0; n<ItemNamesFromBillingPge.size();n++) {
			TextItemNamesFromBillingPge.add(ItemNamesFromBillingPge.get(n).getText().trim());
		}
		int i=0;
		for (String ItemName : ItemNames) {
			String ExpectedItemName = ItemName.substring(ItemName.indexOf("-") + 2);
			String ItemNameFromBillingPage = ItemNamesFromBillingPge.get(i).getText().trim();

			asrt.assertEquals(ExpectedItemName,ItemNameFromBillingPage, "Item Name mismatch: Expected: "+ExpectedItemName+"but got "+ItemNameFromBillingPage+" in Billing page");
			i++;
		}

		List<WebElement> BillScreenPageProductQty = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		for(int quantity = 0; quantity < BillScreenPageProductQty.size(); quantity++) {

			String BillingScrnQuantity = BillScreenPageProductQty.get(quantity).getText();
			String ItemDetailsQuantity = ItemQuantity.get(quantity);		   
			BigDecimal ExpectedQuantity = new BigDecimal(ItemDetailsQuantity).setScale(2, RoundingMode.HALF_UP);
			BigDecimal ActualQuantity = new BigDecimal(BillingScrnQuantity).setScale(2, RoundingMode.HALF_UP);

			asrt.assertEquals(ActualQuantity, ExpectedQuantity, "Quantity Mismatch: Expected " + ExpectedQuantity + " but got " + ActualQuantity + " in Billing page");
		}
		for (int j =0; j<TotalsWithoutTax.size();j++) {
			double ActualTotal = Double.parseDouble(TotalForEachItem.get(j));
			double ExpectedTotal = Double.parseDouble(TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""));
			asrt.assertEquals(ExpectedTotal, ActualTotal,"Total without tax mismatch: Expected:  "+ExpectedTotal+"but got "+ActualTotal+" in Billing page");
		}
		asrt.assertEquals(ExpectedSubTotal,ActualSubTotal, 1.0,"Subtotal mismatch : Expected "+ExpectedSubTotal+"but got "+ActualSubTotal+" in Billing page");
		asrt.assertEquals(ExpectedTax,ActualTaxAmount,1.0,"Gst amount mismatch: Expected "+ExpectedTax+"but got "+ActualTaxAmount+" in Billing page");
		asrt.assertEquals(ExpectedTotalAmount,ActualTotalAmount, 1.0,"Total Amount mismatch: Expected "+ExpectedTotalAmount+"but got "+ActualTotalAmount+" in Billing page");

		// 7.Click on Save to estimation button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));

		// Step 8:Save estimate
		// Expected: Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		String TaxableValue=SubTotal;
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC03Pdf");
		String ProFormaInvoice =System.getProperty("user.dir") + "\\Invoices\\"+ProFormaInvoiceName+"";
		String PdfContent = pdfUtils.IsGetText(ProFormaInvoice);
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
		// Expected:Verify whether the recalled item is the same as the item in the cart.
		// Cashier Screen after recall,No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,Tax, Total Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//To validate lines count, Item name, Quantity, Tax,  Sub Total in Recall page
		List<WebElement> ItemNamesFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		int ActualLinesCountInRecall = ItemNamesFromRecall.size();

		asrt.assertEquals(ExpectedLinesCount, ActualLinesCountInRecall,"Lines Count mismatch: Expected value is "+ExpectedLinesCount+"but got "+ActualLinesCountInRecall+" in Recall page");

		for (int k =0; k<ItemNamesFromRecall.size();k++) {
			String ExpectedItemNameInRecall = ItemNamesFromRecall.get(k).getText().trim();
			String ActualItemNameFromBillingPage = TextItemNamesFromBillingPge.get(k);

			asrt.assertEquals(ExpectedItemNameInRecall,ActualItemNameFromBillingPage,"Item Name mismatch: Expected value is "+ExpectedItemNameInRecall+"but got "+ActualItemNameFromBillingPage+" in Recall page");
		}
		List<WebElement> QuantityFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
		for(int l =0; l<QuantityFromRecall.size();l++) {
			double ActualQty = Double.parseDouble(QuantityFromRecall.get(l).getText().trim());
			double ExpectedQty=Double.parseDouble(ItemQuantity.get(l));
			asrt.assertEquals(ExpectedQty,ActualQty,1.0,"Quantity mismatch: Expected value is "+ExpectedQty+"but got "+ActualQty+" in Recall page");
		}
		double ActualTotalWithoutTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax")));
		asrt.assertEquals(ExpectedSubTotal,ActualTotalWithoutTax,1.0,"TotalWithoutTax mismatch: Expected value is"+ExpectedSubTotal+"but got "+ActualTotalWithoutTax+" in Recall page");
		double ActualTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));
		asrt.assertEquals(ExpectedTax,ActualTax,1.0,"GST amount mismatch : Expected: "+ExpectedTax+"but got "+ActualTax+" in Recall page");
		double ActualSubtotalFromRecall = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")));
		asrt.assertEquals(ExpectedTotalAmount,ActualSubtotalFromRecall,1.0,"Subtotal value mismatch: Expected: "+ExpectedTotalAmount+"but got "+ActualSubtotalFromRecall+" in Recall page");

		// Step 10:Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), "Sales");

		// Step 11:Click on Process Estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 12:Choose a sales representative
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();
		// Step 13:Click on the Amount
		// Step 14:Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText(NormalSaleGoldAndSilverTestData.TC03_PaymentMethod));
		String PaymentAmountHDFC=base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleGoldAndSilverTestData.TC03_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleGoldAndSilverTestData.TC03_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 15:  Post the Invoice
		// Expected: Check final invoice details
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC03_TaxInvoice");				
		String TaxInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";	
		String GrossAmount=SubTotal;		
		String TotalQtyPcs = LinesCount;
		String TotalDiscount = Discount;
		String NetValue=String.format("%.2f", Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", "")));
		System.out.println(NetValue);
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
				TaxInvoicePath,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentAmountHDFC,
				SkuList,
				ItemData
				);
		//			appUtils.SalesReturnFlow(NormalSaleGoldAndSilverTestData.TC03_CustomerID, PdfInvoiceNo,NormalSaleGoldAndSilverTestData.TC03_ReturnProduct);
		//			appUtils.CounterFlow(NormalSaleGoldAndSilverTestData.ToCounter1, NormalSaleGoldAndSilverTestData.MetalType1, NormalSaleGoldAndSilverTestData.TC03_SkuGold,UtilityTestData.Terminal);
		//			base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		//			base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));
		//			appUtils.CounterFlow(NormalSaleGoldAndSilverTestData.ToCounter2, NormalSaleGoldAndSilverTestData.MetalType2, NormalSaleGoldAndSilverTestData.TC03_SkuSilver,UtilityTestData.Terminal);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Normal sale multiple gold piece rate items
	// Automation ID : TC04
	// Author: Nivya Ramesan
	// </summary>
	public void TC04_NormalSaleGoldandSilver() throws Exception {

		//Step 1: Login to POS
		Login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 2: Navigate to the Transaction page
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer
		// Step 4: Click on add to estimation
		appUtils.SearchByCustomerID(NormalSaleGoldAndSilverTestData.TC04_CustomerNo, UtilityTestData.MenuBarOptions[5]);

		// Step 5: Scan multiple gold piece rate SKU and click on Add to cart button
		// Expected:Check calculation Billing Screen, No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		// Step 6: Select any one of the sales agent	
		List <String> SkuValueGoldPiece = appUtils.FetchPieceRateItem(UtilityTestData.GoldPieceRateSkus, 2);
		List<String> ItemNames           = new ArrayList<>();
		List<String> TotalForEachItem    = new ArrayList<>();
		List<String> Quantity            = new ArrayList<>();
		Map<String, String> ItemDetails  = new HashMap<>();
		for (int i = 0; i < SkuValueGoldPiece.size(); i++) {
			appUtils.SKUIngridentTableValues(SkuValueGoldPiece.get(i), i + 1, ItemDetails);
			ItemNames.add(ItemDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItem.add(ItemDetails.get("SKU_" + (i+1) + "_Total"));
			Quantity.add(ItemDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			//			mathUtils.ValidateCvalue(ItemDetails, i+1);
			//			mathUtils.ValidateTotalAmount(ItemDetails, i+1);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ItemDetails, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}
		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(SkuValueGoldPiece, ItemDetails);
		String SubTotal         = TransactionPageItems.get("Subtotal");
		String Discount         = TransactionPageItems.get("Discount");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight   = TransactionPageItems.get("NetWeight");
		String Gst              = TransactionPageItems.get("Tax");
		String InvoiceTotal     = TransactionPageItems.get("TotalAmount");
		String NetTotal         = TransactionPageItems.get("NetTotal");
		String LinesCount       = TransactionPageItems.get("LinesCount");

		int ExpectedLinesCount     = Integer.parseInt(LinesCount);
		double ExpectedSubTotal    = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTax         = Double.parseDouble(Gst.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTotalAmount = Double.parseDouble(InvoiceTotal.trim().replaceAll("[^\\d.]", ""));
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ActualLinesCount = AllProductRows.size();
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		double ActualSubTotal = 0.00;
		for (WebElement SubTotalElement : TotalsWithoutTax)
		{
			String SubTotalText = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!SubTotalText.isEmpty())
			{
				ActualSubTotal=ActualSubTotal + Double.parseDouble(SubTotalText);
			}
		}
		double ActualTaxAmount = Math.round((ActualSubTotal*3/100)*100.0)/100.0;
		double ActualTotalAmount = ActualSubTotal+ActualTaxAmount;

		//To validate Line count, Item name, Quantity, Sub total, tax, Total amount in Billing page
		asrt.assertEquals(ExpectedLinesCount, ActualLinesCount,"Line count mismatch: Expected is "+ExpectedLinesCount+"but got "+ActualLinesCount+" in Billing page");

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
		List<String> QuantityFromBilling = new ArrayList<>();
		int n=0;
		for (WebElement Qty : AllSkuElements) {
			double ActualGrossWeightFromBilling = Double.parseDouble(Qty.getText());
			QuantityFromBilling.add(Qty.getText());
			double ExpectedGrossWeightFromItemDetails = Double.parseDouble(Quantity.get(n));
			asrt.assertEquals(ActualGrossWeightFromBilling,ExpectedGrossWeightFromItemDetails,"GrossWeight mismatch: Expected value is "+ActualGrossWeightFromBilling+"but got "+ExpectedGrossWeightFromItemDetails+" in Billing page");
			n++;
		}

		for (int j =0; j<TotalsWithoutTax.size();j++) {
			double ActualTotal = Double.parseDouble(TotalForEachItem.get(j));
			double ExpectedTotal = Double.parseDouble(TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpectedTotal, ActualTotal,1.0,"Total value without tax mismatch: Expected value is "+ExpectedTotal+"but got "+ActualTotal+" in Billing page");
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

		String TaxableValue = SubTotal;
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC04_ProFormaInvoice");
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
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Step 10:Select the Transaction Type as Sales
		// Expected : Verify whether the recalled item is the same as the item in the cart.Cashier Screen after recall,
		// No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSaleGoldAndSilverTestData.TC04_TransactionType);

		//To validate lines count, Item name, Quantity, Tax,  Sub Total in Recall page
		List<WebElement> ItemNamesFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		int ActualLinesCountInRecall = ItemNamesFromRecall.size();

		asrt.assertEquals(ExpectedLinesCount, ActualLinesCountInRecall,"Lines Count mismatch: Expected value is "+ExpectedLinesCount+"but got "+ActualLinesCountInRecall+" in Recall page");

		for (int k =0; k<ItemNamesFromRecall.size();k++) {
			String ExpectedItemNameInRecall = ItemNamesFromRecall.get(k).getText().trim();
			String ActualItemNameFromBillingPge = TextItemNamesFromBillingPge.get(k);

			asrt.assertEquals(ExpectedItemNameInRecall,ActualItemNameFromBillingPge,"Item name mismatch: Expected value is "+ExpectedItemNameInRecall+"but got "+ActualItemNameFromBillingPge+" in Recall page");
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

		// Step 11:Click on Process Estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 12:Choose a sales representative
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		// Step 13:Click on the Amount
		// Step 14:Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText(NormalSaleGoldAndSilverTestData.TC04_PaymentMethod));
		String PaymentAmountHDFC = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleGoldAndSilverTestData.TC04_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleGoldAndSilverTestData.TC04_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 15:Post the Invoice
		// Expected:Check final invoice details
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC04_TaxInvoice");		
		String TaxInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";	
		String GrossAmount=SubTotal;
		String TotalValue=InvoiceTotal;
		String TotalQtyPcs = LinesCount;
		String TotalDiscount = Discount;
		String NetValue=String.format("%.2f", Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", "")));
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
				TaxInvoicePath,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentAmountHDFC,
				SkuValueGoldPiece,
				ItemDetails
				);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
		appUtils.SalesReturnFlow(NormalSaleGoldAndSilverTestData.TC04_CustomerNo, PdfInvoiceNo,NormalSaleGoldAndSilverTestData.ReturnProducts);
		Thread.sleep(2000);
		appUtils.CounterFlow("Gold", "Gold", SkuValueGoldPiece, UtilityTestData.Terminal);
	}

	/// <summary>
	/// Test Case Title : Normal sale multiple silver piece rate
	/// Automation ID : TC05
	/// Author : Jhoncy
	/// </summary>
	public void TC05_NormalSaleGoldandSilver() throws Exception
	{
		//Step 1: Login to POS
		Login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 2: Click on Transaction button
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer
		// Step 4: Click on add to estimate button
		appUtils.SearchByCustomerID(NormalSaleGoldAndSilverTestData.TC05_CustomerID, UtilityTestData.MenuBarOptions[5]);

		//Step 5 : Scan silver piece rate SKU and click on Add to cart button
		//Step 6 : Select any one of the sales agent
		//Expected Result : Check Calculations
		//Check calculation : Billing Screen, No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,
		//TAX , Total Amount
		Map<String, String> SkuDetails = new LinkedHashMap<>();    
		List <String> RequiredPieceRateSku = appUtils.FetchPieceRateItem(UtilityTestData.SilverPieceRateSkus, 2);

		//base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		//base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		//Map<String, String> SkuDetails = new LinkedHashMap<>();		
		for (int i = 0;i < RequiredPieceRateSku.size(); i++)
		{
			String CurrentSku = RequiredPieceRateSku.get(i);
			int SkuCount = i + 1;
			appUtils.SKUIngridentTableValues(CurrentSku.toString(), SkuCount, SkuDetails);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, SkuCount);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuCount);
		}

		List<String> SkuList = RequiredPieceRateSku;
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

		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC05_ProformaInvoice");
		Thread.sleep(3000);
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
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"),NormalSaleGoldAndSilverTestData.TC05_TransactionType);

		//Step 12 : Click on Process Estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		//Step 13 : Choose a sales representative
		// Expected Result: Validate Cashier Screen after recall *No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));	
		Map<String, String> TransactionRecallDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, SkuDetails);	
		mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLine, SkuDetails);

		//Step 14 : Click on the Amount
		//Step 15 : Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentAmountHDFC=base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleGoldAndSilverTestData.TC05_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleGoldAndSilverTestData.TC05_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		//Step 16 : Post the Invoice
		//Expected Result : Check final invoice details
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(2000);
		String SaleInvoice= pdfUtils.DownloadAndRenameLatestPDF("TC05_TaxInvoice");
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

		appUtils.SalesReturnFlow(NormalSaleGoldAndSilverTestData.TC05_CustomerID, PdfInvoiceNo, NormalSaleGoldAndSilverTestData.TC05_ReturnProducts);
		appUtils.CounterFlow(NormalSaleGoldAndSilverTestData.TC05_FromCounter,NormalSaleGoldAndSilverTestData.TC05_MetalType,RequiredPieceRateSku ,UtilityTestData.Terminal);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Normal Sale Multiple Gold
	// Automation ID : TC01
	// <Author : Vishnu Manoj K
	// </summary>
	public String TC01_NormalSaleGoldandSilver() throws Exception {

		// Step 1: Login to POS
		Login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Click on Transaction button
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer
		// Step 4: Click on add to estimation button
		appUtils.SearchByCustomerID(NormalSaleGoldAndSilverTestData.TC01_CustomerNo,"AddCustomerForSalesEstimationCommand");

		// Step 5: Scan gold SKU and click on Add to cart button
		// Step 6: Scan another gold SKU and click on Add to cart button
		// Step 7: Select any one of the sales agent
		// Expected Result: Check calculation Note:Billling Screen *No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		List<String> SkuList = appUtils.SelectMultipleSKUs(NormalSaleGoldAndSilverTestData.TC01_SkuGoldCount,NormalSaleGoldAndSilverTestData.TC01_TransferType,NormalSaleGoldAndSilverTestData.TC01_FromCounter,NormalSaleGoldAndSilverTestData.TC01_MetalType);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
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
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = TransactionDataLine.get("Subtotal");
		String Discount = TransactionDataLine.get("Discount");
		String TotalGrossWeight = TransactionDataLine.get("GrossWeight");
		String TotalNetWeight = TransactionDataLine.get("NetWeight");
		String Tax = TransactionDataLine.get("Tax");
		String TotalAmount = TransactionDataLine.get("TotalAmount");
		String NetTotal = TransactionDataLine.get("NetTotal");
		String LinesCount = TransactionDataLine.get("LinesCount");

		mathUtils.ValidateTransactionLineCalculation(TransactionDataLine, ScannedSkuDataMap);

		// Step 8: Save Esimate
		// Step 9: Click on Save to estimation button
		// Expected Result:Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		// PDF Verification Step
		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC01_ProformaInvoice");
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

		// Step 10: Recall estimate from cashier screen
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

		// Step 11: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSaleGoldAndSilverTestData.TC01_TransationType);

		// Step 12: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 13: Select any Sales Representative
		// Expected Result: Verify whether the recalled item is the same as the item in the cart.Note:-13.Cashier Screen after recall*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		base.setZoom(driver, 60);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		Map<String, String> TransactionRecallDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLine, ScannedSkuDataMap);

		// Step 14:  Click on the Amount
		// Expected Result: User should able to click the amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));

		// Step 15:  Select a Payment method(Cash or Card)
		// Expected Result: User should able to select the Payement Method
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentHDFCCard = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleGoldAndSilverTestData.TC01_ApprovalCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleGoldAndSilverTestData.TC01_ApprovalCodeAgain);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 16:  Post the Invoice
		// Expected Result:  16. Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC01SaleInvoice");
		Thread.sleep(2000);
		String InvoicePdfPath = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice;
		String GrossAmount = SubTotal;
		String TotalDiscount=Discount;
		String TotalQtyPcs=LinesCount;
		String NetValue=NetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
				InvoicePdfPath,
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

		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
		return PdfInvoiceNo;
	}
	// <summary>
	// Test Case Title : Normal sale multiple gold and silver item
	// Automation ID : TC02
	// Author: Christy reji
	// </summary>
	public void TC02_NormalSaleGoldandSilver() throws Exception {

		// Step 1: Login to POS
		// Expected Result: User should be Login to POS
		Login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 80);

		// Step 2: Navigate to the Transaction page 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));


		// Step 3 : Select customer and click in add to estimate button
		// Step 4 : Click on add to estimate button	
		appUtils.SearchByCustomerID(NormalSaleGoldAndSilverTestData.TC02_CustomerID,  UtilityTestData.MenuBarOptions[5]);

		// Step 5  : Scan multiple gold and silver one by one and click on Add to cart button
		// Expected:Check calculation,  Note:Billing Screen,No of Product lines, Displayed Item Name,
		// Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		// Step 6. Select any one of the sales agent
		List<String> MultipleGoldSkus      = appUtils.SelectMultipleSKUs( NormalSaleGoldAndSilverTestData.TC02_SKUCount,NormalSaleGoldAndSilverTestData.TC02_TranferType,
				NormalSaleGoldAndSilverTestData.TC02_FromCounterGold,NormalSaleGoldAndSilverTestData.TC02_MetalTypeGold);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		List<String> MultipleSilverSkus = appUtils.SelectMultipleSKUs( NormalSaleGoldAndSilverTestData.TC02_SKUCount,NormalSaleGoldAndSilverTestData.TC02_TranferType,
				NormalSaleGoldAndSilverTestData.TC02_FromCounterSilver,NormalSaleGoldAndSilverTestData.TC02_MetalTypeSilver);

		Thread.sleep(1000);
		List<String> RequiredSkus  	  = new ArrayList<>();
		RequiredSkus.addAll(MultipleGoldSkus);
		RequiredSkus.addAll(MultipleSilverSkus);		
		List<String> ItemNames 		  = new ArrayList<>();
		List<String> ItemQuantity 	  = new ArrayList<>();
		List<String> TotalForEachItem = new ArrayList<>();
		Map<String, String> ItemData  = new HashMap<>();

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		for (int i = 0; i < RequiredSkus.size(); i++)
		{   Thread.sleep(2000);
		appUtils.SKUIngridentTableValues(RequiredSkus.get(i), i + 1, ItemData);
		ItemNames.add(ItemData.get("SKU_" + (i+1) + "_HeaderName"));
		ItemQuantity.add(ItemData.get("SKU_" + (i+1) + "_GrossWeight"));
		TotalForEachItem.add(ItemData.get("SKU_" + (i+1) + "_Total"));
		Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ItemData, (i+1));
		mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		Map<String, String> Results       = appUtils.TransactionSKUsLinesVerify(RequiredSkus, ItemData);
		String SubTotal                   = Results.get("Subtotal");
		String Discount                   = Results.get("Discount");
		String TotalGrossWeight           = Results.get("GrossWeight");
		String TotalNetWeight             = Results.get("NetWeight");
		String Gst                        = Results.get("Tax");
		String InvoiceTotal               = Results.get("TotalAmount");
		String NetTotal                   = Results.get("NetTotal");
		String LinesCount                 = Results.get("LinesCount");
		int ExpectedLinesCount            = Integer.parseInt(LinesCount);
		double ExpectedSubTotal           = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTax                = Double.parseDouble(Gst.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTotalAmount        = Double.parseDouble(InvoiceTotal.trim().replaceAll("[^\\d.]", ""));
		List<WebElement> AllProductRows   = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ActualLinesCount              = AllProductRows.size();
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		double ActualSubTotal             = 0.0;
		for (WebElement SubTotalElement : TotalsWithoutTax)
		{
			String SubTotalText           = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!SubTotalText.isEmpty())
			{
				ActualSubTotal=ActualSubTotal + Double.parseDouble(SubTotalText);
			}
		}
		double ActualTaxAmount            = Math.round((ActualSubTotal*3/100)*100.0)/100.0;
		double ActualTotalAmount          = ActualSubTotal+ActualTaxAmount;

		// To validate Line count, Item name, Quantity, Sub total, tax, Total amount in Billing page
		asrt.assertEquals(ExpectedLinesCount, ActualLinesCount,"Line Count mismatch: Expected is  "+ExpectedLinesCount+"but got "+ActualLinesCount+" in Billing page");

		List<WebElement> ItemNamesFromBillingPge = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<String> TextItemNamesFromBillingPge = new ArrayList<>();
		for (int n=0; n<ItemNamesFromBillingPge.size();n++) {
			TextItemNamesFromBillingPge.add(ItemNamesFromBillingPge.get(n).getText().trim());
		}
		int i=0;
		for (String ItemName : ItemNames) {
			String ExpectedItemName        = ItemName.substring(ItemName.indexOf("-") + 2);
			String ItemNameFromBillingPage = ItemNamesFromBillingPge.get(i).getText().trim();

			asrt.assertEquals(ExpectedItemName,ItemNameFromBillingPage, "Item Name mismatch: Expected: "+ExpectedItemName+"but got "+ItemNameFromBillingPage+" in Billing page");
			i++;
		}

		List<WebElement> BillScreenPageProductQty = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		for(int quantity = 0; quantity < BillScreenPageProductQty.size(); quantity++) {

			String BillingScrnQuantity    = BillScreenPageProductQty.get(quantity).getText();
			String ItemDetailsQuantity    = ItemQuantity.get(quantity);		   
			BigDecimal ExpectedQuantity   = new BigDecimal(ItemDetailsQuantity).setScale(2, RoundingMode.HALF_UP);
			BigDecimal ActualQuantity     = new BigDecimal(BillingScrnQuantity).setScale(2, RoundingMode.HALF_UP);

			asrt.assertEquals(ActualQuantity, ExpectedQuantity, "Quantity Mismatch: Expected " + ExpectedQuantity + " but got " + ActualQuantity + " in Billing page");
		}
		for (int j =0; j<TotalsWithoutTax.size();j++) {
			double ActualTotal   = Double.parseDouble(TotalForEachItem.get(j));
			double ExpectedTotal = Double.parseDouble(TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""));
			System.out.println("ActualTotal: "+ActualTotal);
			asrt.assertEquals(ExpectedTotal, ActualTotal,"Total without tax mismatch: Expected:  "+ExpectedTotal+"but got "+ActualTotal+" in Billing page");
		}
		asrt.assertEquals(ExpectedSubTotal,ActualSubTotal, 1.0,"Subtotal mismatch : Expected "+ExpectedSubTotal+"but got "+ActualSubTotal+" in Billing page");
		asrt.assertEquals(ExpectedTax,ActualTaxAmount,1.0,"Gst amount mismatch: Expected "+ExpectedTax+"but got "+ActualTaxAmount+" in Billing page");
		asrt.assertEquals(ExpectedTotalAmount,ActualTotalAmount, 1.0,"Total Amount mismatch: Expected "+ExpectedTotalAmount+"but got "+ActualTotalAmount+" in Billing page");

		// 7.Click on Save to estimation button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));

		// Step 8:Save esimate
		// Expected : Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber         = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC02Pdf1");
		String ProFormaInvoice     = System.getProperty("user.dir") + "\\Invoices\\"+ProFormaInvoiceName+"";
		String PdfContent          = pdfUtils.IsGetText(ProFormaInvoice);
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
		// Expected : Verify whether the recalled item is the same as the item in the cart.
		// Cashier Screen after recall,No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax)
		// Displayed Subtotal,Total Amount,TAX
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//To validate lines count, Item name, Quantity, Tax,  Sub Total in Recall page
		List<WebElement> ItemNamesFromRecall     = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		int ActualLinesCountInRecall             = ItemNamesFromRecall.size();

		asrt.assertEquals(ExpectedLinesCount, ActualLinesCountInRecall,"Lines Count mismatch: Expected value is "+ExpectedLinesCount+"but got "+ActualLinesCountInRecall+" in Recall page");

		for (int k =0; k<ItemNamesFromRecall.size();k++) {
			String ExpectedItemNameInRecall          = ItemNamesFromRecall.get(k).getText().trim();
			String ActualItemNameFromBillingPage = TextItemNamesFromBillingPge.get(k);

			asrt.assertEquals(ExpectedItemNameInRecall,ActualItemNameFromBillingPage,"Item Name mismatch: Expected value is "+ExpectedItemNameInRecall+"but got "+ActualItemNameFromBillingPage+" in Recall page");
		}
		List<WebElement> QuantityFromRecall      = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
		for(int l =0; l<QuantityFromRecall.size();l++) {
			double ActualQty   = Double.parseDouble(QuantityFromRecall.get(l).getText().trim());
			double ExpectedQty = Double.parseDouble(ItemQuantity.get(l));
			asrt.assertEquals(ExpectedQty,ActualQty,1.0,"Quantity mismatch: Expected value is "+ExpectedQty+"but got "+ActualQty+" in Recall page");
		}
		double ActualTotalWithoutTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax")));
		asrt.assertEquals(ExpectedSubTotal,ActualTotalWithoutTax,1.0,"TotalWithoutTax mismatch: Expected value is"+ExpectedSubTotal+"but got "+ActualTotalWithoutTax+" in Recall page");
		double ActualTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));
		asrt.assertEquals(ExpectedTax,ActualTax,1.0,"GST amount mismatch : Expected: "+ExpectedTax+"but got "+ActualTax+" in Recall page");
		double ActualSubtotalFromRecall = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")));
		asrt.assertEquals(ExpectedTotalAmount,ActualSubtotalFromRecall,1.0,"Subtotal value mismatch: Expected: "+ExpectedTotalAmount+"but got "+ActualSubtotalFromRecall+" in Recall page");

		// Step 10:Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), "Sales");

		// Step 11:Click on Process Estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 12:Choose a sales representative
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();
		// Step 13:Click on the Amount
		// Step 14:Select a Payment method(Cash or Card)
		String PaymentAmount = appUtils.PaymentAfterRecallEstimate("HDFC");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 15:  Post the Invoice
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC02_TaxInvoice");	
		String TaxInvoice     = System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";	
		String GrossAmount    = SubTotal;
		String TaxableValue   = SubTotal;
		String TotalQtyPcs    = LinesCount;
		String TotalDiscount  = Discount;
		String NetValue       = String.format("%.2f", Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", "")));
		String PdfInvoiceNo   = pdfUtils.SaleInvoicePdfValidation(
				TaxInvoice,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentAmount,
				RequiredSkus,
				ItemData
				);				
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}


	// <summary>
	// Test Case Title : Normal sale with multiple piece rate items and making rate items
	// Automation ID : TC06
	// Author: Anagha 
	// </summary>
	public void TC06_NormalSaleGoldandSilver() throws Exception
	{
		// Step 1: Login to POS
		Login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 2: Click on Transaction button
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		// Step 3: Select customer
		// Step 4: Click on add to estimation
		appUtils.SearchByCustomerID(NormalSaleGoldAndSilverTestData.TC06_CustId, "SearchView_AddCustomerForSalesEstimationCommand");

		// Step 5: Scan multiple SKU( silver making & Piece rate) and click on Add to cart button
		// Step 6: Select anyone of the sales agent
		Map<String, String> ItemDetails = new LinkedHashMap<>();	
		List<String> ItemNames = new ArrayList<>();
		List<String> TotalForEachItem = new ArrayList<>();
		List<String> Quantity = new ArrayList<>();

		List <String> RequiredPieceRateSku = appUtils.FetchPieceRateItem(UtilityTestData.SilverPieceRateSkus, 1);	
        //base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		//base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		base.actionClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
		

		List<String> MultipleSilverSkus = appUtils.SelectMultipleSKUs( NormalSaleGoldAndSilverTestData.TC06_SkuCount,NormalSaleGoldAndSilverTestData.TC06_TransferType,
				NormalSaleGoldAndSilverTestData.TC06_ToCounter,NormalSaleGoldAndSilverTestData.TC06_MetalType);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		Thread.sleep(1000);
		List<String> AllSkuList  	  = new ArrayList<>();
		AllSkuList.addAll(RequiredPieceRateSku);
		AllSkuList.addAll(MultipleSilverSkus);

		for (int i = 0;i < AllSkuList.size();i++)
		{			
			String CurrentSilverSku = AllSkuList.get(i);
			appUtils.SKUIngridentTableValues(CurrentSilverSku.toString(), (i+1), ItemDetails);
			ItemNames.add(ItemDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItem.add(ItemDetails.get("SKU_" + (i+1) + "_Total"));
			Quantity.add(ItemDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ItemDetails, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		//Validation Steps : No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal, TAX, Total amount		
		Map<String, String> SkuDetails = appUtils.TransactionSKUsLinesVerify(AllSkuList, ItemDetails);
		String SubTotal = SkuDetails.get("Subtotal");
		String TotalGrossWeight = SkuDetails.get("GrossWeight");
		String TotalNetWeight = SkuDetails.get("NetWeight");
		String Gst = SkuDetails.get("Tax");
		String InvoiceTotal = SkuDetails.get("TotalAmount");
		String NetTotal = SkuDetails.get("NetTotal");
		String LinesCount = SkuDetails.get("LinesCount");
		String Discount = SkuDetails.get("Discount");

		int ExpectedLinesCount = Integer.parseInt(LinesCount);
		double ExpectedSubTotal = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTax = Double.parseDouble(Gst.trim().replaceAll("[^\\d.]", ""));
		double ExpectedTotalAmount = Double.parseDouble(InvoiceTotal.trim().replaceAll("[^\\d.]", ""));
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		int ActualLinesCount = AllProductRows.size();
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		double ActualSubTotal = 0.0;
		for (WebElement SubTotalElement : TotalsWithoutTax)
		{
			String SubTotalText = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!SubTotalText.isEmpty())
			{
				ActualSubTotal=ActualSubTotal + Double.parseDouble(SubTotalText);
			}
		}
		double ActualTaxAmount = Math.round((ActualSubTotal*3/100)*100.0)/100.0;
		double ActTotalAmount = ActualSubTotal + ActualTaxAmount;

		//base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));

		asrt.assertEquals(ExpectedLinesCount, ActualLinesCount,"Lines count mismatch: Expected " + ExpectedLinesCount + " but got " + ActualLinesCount +" in transaction page");

		List<WebElement> SkuListAndReturnListProdName  = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> SkuListAndReturnListProdQty = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<WebElement> SkuListAndReturnListProdPrice = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<String> ItemNamesBillScreen =new ArrayList<>();
		for (int n=0; n < SkuListAndReturnListProdName.size(); n++) {
			ItemNamesBillScreen.add(SkuListAndReturnListProdName.get(n).getText().trim());
		}
		int BillingSize = ItemNamesBillScreen.size();
		int ExpectedSize = ItemNames.size();
		for (int i = 0; i < ExpectedSize; i++) {
			String ExpectedName = ItemNames.get(i).substring(ItemNames.get(i).indexOf("-") + 2).trim();
			String ActualName = ItemNamesBillScreen.get(BillingSize - ExpectedSize + i).trim();

			asrt.assertEquals(ActualName,ExpectedName,"Item names mismatch: Expected '"+ ExpectedName + "' but found '" + ActualName + "' in Billing page");
		}

		List<String> QtyBillScreen = new ArrayList<>();
		for (int n=0; n < SkuListAndReturnListProdQty.size(); n++) 
		{
			QtyBillScreen.add(SkuListAndReturnListProdQty.get(n).getText().trim());
		}
		int BillingSizeQty = QtyBillScreen.size();
		int ExpectedSizeQty = Quantity.size();
		for (int i = 0; i < ExpectedSizeQty; i++)
		{
			String ExpectedQtyText = Quantity.get(i).trim().replaceAll("[^\\d.]", "");
			String ActualQtyText = QtyBillScreen.get(BillingSizeQty - ExpectedSizeQty + i).trim().replaceAll("[^\\d.]", "");

			double ExpectedQty = Double.parseDouble(ExpectedQtyText);
			double ActualQty = Double.parseDouble(ActualQtyText);

			asrt.assertEquals(ActualQty, ExpectedQty, 1,"Quantity mismatch: Expected '" + ExpectedQty + "' but found '" + ActualQty + "' in Billing page");
		}

		List<String> TotalWithoutTaxBillScreen = new ArrayList<>();
		for (int n=0; n < SkuListAndReturnListProdPrice.size(); n++) 
		{
			TotalWithoutTaxBillScreen.add(SkuListAndReturnListProdPrice.get(n).getText().trim());
		}
		int BillingSizePrice = TotalWithoutTaxBillScreen.size();
		int ExpectedSizePrice = TotalForEachItem.size();
		for (int i = 0; i < ExpectedSizePrice; i++) {
			String ExpectedTotalWithoutTaxStr = TotalForEachItem.get(i).trim().replaceAll("[^\\d.]", "");
			String ActualTotalWithoutTaxStr = TotalWithoutTaxBillScreen.get(BillingSizePrice - ExpectedSizePrice + i).trim().replaceAll("[^\\d.]", "");

			double ExpectedTotalWithoutTax = Double.parseDouble(ExpectedTotalWithoutTaxStr);
			double ActualTotalWithoutTax = Double.parseDouble(ActualTotalWithoutTaxStr);

			asrt.assertEquals(ActualTotalWithoutTax, ExpectedTotalWithoutTax, 1,"Total without tax mismatch: Expected '" + ExpectedTotalWithoutTax + "' but found '" + ActualTotalWithoutTax + "' in Billing page");
		}

		asrt.assertEquals(ExpectedSubTotal,ActualSubTotal, 1,"SubTotal mismatch: Expected " + ExpectedSubTotal + " but found " + ActualSubTotal + " in transaction page");
		asrt.assertEquals(ExpectedTax,ActualTaxAmount,1,"Tax amount mismatch: Expected " + ExpectedTax+" but found " + ActualTaxAmount + " in transaction page");
		asrt.assertEquals(ExpectedTotalAmount,ActTotalAmount, 1,"Total Amount mismatch: Expected" + ExpectedTotalAmount + " but found " + ActTotalAmount + " in transaction page");

		// Step 7: In Billing, Click on "Save to Estimate" button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));

		// Step 8: Click on Save estimation
		// Expected Result: Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		String TaxableValue = SubTotal;
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC06_ProFormaInvoice");
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
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Step 10:Select the Transaction Type as Sales
		// Expected : Verify whether the recalled item is the same as the item in the cart.Cashier Screen after recall,
		// No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSaleGoldAndSilverTestData.TC06_TransactionType);

		//To validate lines count, Item name, Quantity, Tax,  Sub Total in Recall page
		List<WebElement> ItemNamesFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		int ActualLinesCountInRecall = ItemNamesFromRecall.size();

		asrt.assertEquals(ExpectedLinesCount, ActualLinesCountInRecall,"Lines Count mismatch: Expected value is "+ExpectedLinesCount+"but got "+ActualLinesCountInRecall+" in Recall page");

		for (int k =0; k<ItemNamesFromRecall.size();k++) {
			String ExpectedItemNameInRecall = ItemNamesFromRecall.get(k).getText().trim();
			String ActualItemNameFromBillingPge = ItemNamesBillScreen.get(k);

			asrt.assertEquals(ExpectedItemNameInRecall,ActualItemNameFromBillingPge,"Item name mismatch: Expected value is "+ExpectedItemNameInRecall+"but got "+ActualItemNameFromBillingPge+" in Recall page");
		}

		List<WebElement> QuantityFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
		for(int l =0; l<QuantityFromRecall.size();l++) 
		{
			double ActualQty = Double.parseDouble(QuantityFromRecall.get(l).getText().trim());
			double ExpectedQty = Double.parseDouble(QtyBillScreen.get(l));
			asrt.assertEquals(ExpectedQty,ActualQty,1,"Quantity mismatch: Expected value is "+ExpectedQty+"but got "+ActualQty+" in Recall page");
		}

		double ActualTotalWithoutTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax")));
		asrt.assertEquals(ExpectedSubTotal,ActualTotalWithoutTax,1,"TotalWithoutTax mismatch: Expected value is "+ExpectedSubTotal+"but got "+ActualTotalWithoutTax+" in Recall page");
		double ActualTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));
		asrt.assertEquals(ExpectedTax,ActualTax,1,"Tax Value mismatch: Expected value is "+ExpectedTax+"but got "+ActualTax+" in Recall page");
		double ActualSubTotalFromRecall = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")));
		asrt.assertEquals(ExpectedTotalAmount,ActualSubTotalFromRecall,1,"Subtotal mismatch: Expected value is "+ExpectedTotalAmount+"but got "+ActualSubTotalFromRecall+" in Recall page");

		// Step 11:Click on Process Estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 12:Choose a sales representative
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		// Step 13:Click on the Amount
		// Step 14:Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentAmountHDFC = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleGoldAndSilverTestData.TC06_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleGoldAndSilverTestData.TC06_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 15:Post the Invoice
		// Expected:Check final invoice details
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC06_TaxInvoice");		
		String TaxInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";	
		String GrossAmount=SubTotal;
		String TotalQtyPcs = LinesCount;
		String TotalDiscount = Discount;
		String NetValue = NetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
				TaxInvoicePath,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				SubTotal,
				NetValue,
				PaymentAmountHDFC,
				AllSkuList,
				ItemDetails
				);

		appUtils.SalesReturnFlow(NormalSaleGoldAndSilverTestData.TC06_CustId, PdfInvoiceNo, NormalSaleGoldAndSilverTestData.TC06_ReturnProducts);
		appUtils.CounterFlow(NormalSaleGoldAndSilverTestData.TC06_ToCounter,NormalSaleGoldAndSilverTestData.TC06_MetalType,RequiredPieceRateSku,UtilityTestData.Terminal);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder(); 
	}
}