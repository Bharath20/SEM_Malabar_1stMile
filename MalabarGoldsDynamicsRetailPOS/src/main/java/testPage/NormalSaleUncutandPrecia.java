package testPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import testData.CommonData;
import testData.NormalSaleGoldandSilver_TestData;
import testData.NormalSaleUncutandPrecia_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.BasePge;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;

//import basePackage.BasePge;

public class NormalSaleUncutandPrecia extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	MathUtilities mathUtils;
	PdfUtilities pdfUtils;

	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();
	NormalSaleGoldandSilver_TestData NormalSaleGoldAndSilverTestData = new NormalSaleGoldandSilver_TestData();
	NormalSaleUncutandPrecia_TestData NormalSaleUncutAndPreciaTestData = new NormalSaleUncutandPrecia_TestData();
	Utility_TestData UtilityTestData = new Utility_TestData();
	Login login = new Login(driver);
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();

	public NormalSaleUncutandPrecia(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		mathUtils = new MathUtilities(base);
		pdfUtils=new PdfUtilities(base);
	}
	
	/// <summary>
	/// Test Case Title : Normal sale multiple Precia
	/// Automation ID : TC33
	/// Author : Jhoncy
	/// </summary>
	public void TC33_NormalSaleUncutandPrecia() throws Exception {

		//Step 1 : Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 80);

		//Step 2 : Click on Transaction button
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		//Step 3 : Select the customer 
		//Step 4 : Click on add to estimation button
		appUtils.SearchByCustomerID(NormalSaleUncutAndPreciaTestData.TC33_CustomerID, UtilityTestData.MenuBarOptions[5]);

		// Step 5 : Scan Multiple precia SKU's and click on Add to cart button
		// Step 6 : Select any one of the sales agent
		// Expected Result : Check Calculations
		// Check calculation : Billing Screen, No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,
		// TAX , Total Amount
		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> ExpectedItemNames = new ArrayList<>();
		List<String> TotalForEachItems = new ArrayList<>();
		List<String> ItemNamesFromBillingPge =new ArrayList<>();
		List<String> ItemQuantity=new ArrayList<>();
		int i=0;
		double ActualSubTotal = 0.0;
		double TotalWithoutTax = 0.0;

//		List<String> SkuList = new ArrayList<>();
//		SkuList.addAll(NormalSaleUncutAndPreciaTestData.TC33_SkuList);
		List<String> SkuList = appUtils.SelectMultipleSKUs(
				NormalSaleUncutAndPreciaTestData.TC33_SKUCount,
				NormalSaleUncutAndPreciaTestData.TC33_TransferType,
				NormalSaleUncutAndPreciaTestData.TC33_FromCounter,
				NormalSaleUncutAndPreciaTestData.TC33_MetalType
				);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
		for (i = 0; i < SkuList.size(); i++) 
		{    		
			appUtils.SKUIngridentTableValues(SkuList.get(i), i + 1,SkuDetails);
			ExpectedItemNames.add(SkuDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItems.add(SkuDetails.get("SKU_" + (i+1) + "_Total"));
			ItemQuantity.add(SkuDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		Map<String, String> Results = appUtils.TransactionSKUsLinesVerify(SkuList, SkuDetails);		
		String SubTotal = Results.get("Subtotal");
		String Discount = Results.get("Discount");
		String TotalGrossWeight = Results.get("GrossWeight");
		String TotalNetWeight = Results.get("NetWeight");
		String Tax = Results.get("Tax");
		String TotalAmount = Results.get("TotalAmount");
		String NetTotal = Results.get("NetTotal");
		String LinesCount = Results.get("LinesCount");

		//Displayed Item Name
		List<WebElement> ItemNamesInBilling = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		for (int n=0; n<ItemNamesInBilling.size();n++) 
		{
			ItemNamesFromBillingPge.add(ItemNamesInBilling.get(n).getText().trim());
		}
		i=0;
		for (String ItemName : ExpectedItemNames) 
		{
			String ExpectedItemName = ItemName.substring(ItemName.indexOf("-") + 2);
			String ItemNameFromBilling = ItemNamesInBilling.get(i).getText().trim();  

			asrt.assertEquals(ExpectedItemName, ItemNameFromBilling,"Item Name mismatch : Expected  is " + ExpectedItemName + " but got " + ItemNameFromBilling + " in Billing Page");
			i++; 
		}

		//No of Product lines
		int ExpectedLinesCount = Integer.parseInt(LinesCount);
		int ActualProductCount = ItemNamesInBilling.size();    

		asrt.assertEquals(ExpectedLinesCount, ActualProductCount, "Number of Product Lines mismatch : Expected value is " + ExpectedLinesCount + " but got " + ActualProductCount + " in Billing page");

		//Displayed Quantity
		List<WebElement> BillingScreenItemQty = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		for (i = 0; i < BillingScreenItemQty.size(); i++)
		{
			String DisplayedItemQty = BillingScreenItemQty.get(i).getText().trim().replaceAll("[^\\d.]", "");
			double DisplayedQty = Double.parseDouble(DisplayedItemQty);
			String ExpectedItemQty = ItemQuantity.get(i).trim().replaceAll("[^\\d.]", "");			
			double ExpectedQty = Double.parseDouble(ExpectedItemQty);

			asrt.assertEquals(DisplayedQty,ExpectedQty,"Quantity mismatch : Expected value is " + ExpectedQty + " but got " + DisplayedQty + " in Billing page");
		}

		//Displayed TOTAL (without Tax)
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		for (int j =0; j<TotalsWithoutTax.size();j++) 
		{
			double ActualTotal = Double.parseDouble(TotalForEachItems.get(j));
			double ExpectedTotal = Double.parseDouble(TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpectedTotal, ActualTotal,"Total(Without Tax) mismatch : Expected value is " + ExpectedTotal + "but got " + ActualTotal + " in Billing screen");
		}

		//Displayed Subtotal
		for (WebElement SubTotalElement : TotalsWithoutTax)
		{
			String SubTotalValue = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
			if (!SubTotalValue.isEmpty())
			{
				ActualSubTotal=ActualSubTotal + Double.parseDouble(SubTotalValue);
			}
		}	
		TotalWithoutTax = Math.round(ActualSubTotal * 100.0) / 100.0;
		double ExpectedSubTotal = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));

		asrt.assertEquals(ExpectedSubTotal, TotalWithoutTax, "SubTotal mismatch : Expected value is " + ExpectedSubTotal + " but got " + TotalWithoutTax + " in Billing page");

		//Tax amount Validation
		double TaxAmount=ActualSubTotal*3/100;
		double ExpectedTax = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));	

		asrt.assertEquals(ExpectedTax,TaxAmount , 1.0, "Tax Amount mismatch : Expected value is " + ExpectedTax + "but got " + TaxAmount + " in Billing page");

		//Total Amount
		double ActualTotalAmount=ActualSubTotal+TaxAmount;
		double ExpectedTotalAmount = Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", ""));

		asrt.assertEquals(ExpectedTotalAmount, ActualTotalAmount, 1.0,"Total Amount mismatch : Expected value is "+ ExpectedTotalAmount + " but got " + ActualTotalAmount + " in Billing page");

		//Step 7 : In Billing, Click on "Save to Estimate" button	
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));

		//Step 8 : Click on Save estimation
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		Thread.sleep(3000);
		String NewPdfName = pdfUtils.DownloadAndRenameLatestPDF("TC33_ProformaInvoice");
		Thread.sleep(3000);
		String PdfPath =System.getProperty("user.dir") + "\\Invoices\\"+NewPdfName+"";
		String Gst = Tax;
		String InvoiceTotal = TotalAmount;
		String TaxableValue=SubTotal;
		pdfUtils.ProFormaInvoicePDFVerifyNormal(
				PdfPath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				LinesCount,
				Double.parseDouble(TotalGrossWeight),
				Double.parseDouble(TotalNetWeight));

		// Step 9 :Click on Cash and then click on Recall estimate
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

		asrt.assertEquals(ExpectedLinesCount, ActualLinesCountInRecall,"Number of Product Lines mismatch : Expected value is "+ExpectedLinesCount+" but got "+ActualLinesCountInRecall+" in Recall page");

		//To validate Item name
		for (int k =0; k < ItemNamesFromRecall.size();k++) 
		{			
			String ExpectedNameRecall = ItemNamesFromRecall.get(k).getText().trim();
			String ItemNameFromBilling = ItemNamesFromBillingPge.get(k);

			asrt.assertEquals(ExpectedNameRecall,ItemNameFromBilling,"Item Name mismatch : Expected value is "+ExpectedNameRecall+" but got "+ItemNameFromBilling+" in Recall page");
		}

		//To validate quantity
		List<WebElement> QuantityFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
		for(int l =0; l<QuantityFromRecall.size();l++) 
		{
			String DisplayedQtyRecall = QuantityFromRecall.get(l).getText().trim().replaceAll("[^\\d.]", "");
			double DisplayedRecallQty = Double.parseDouble(DisplayedQtyRecall);
			String ExpectedQty = ItemQuantity.get(l).trim().replaceAll("[^\\d.]", "");			
			double ExpectedRecallQty = Double.parseDouble(ExpectedQty);

			asrt.assertEquals(ExpectedRecallQty,DisplayedRecallQty,"Quantity mismatch : Expected value is " + ExpectedRecallQty + " but got " + DisplayedRecallQty+ " in Billing page");
		}

		//To validate Total without tax
		double ActualTotalWithoutTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax")));

		asrt.assertEquals(ExpectedSubTotal,ActualTotalWithoutTax,"Total(Without Tax) mismatch : value is "+ExpectedSubTotal+" but got "+ActualTotalWithoutTax+" in Recall page");

		//To validate tax
		double ActualTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));

		asrt.assertEquals(ExpectedTax,ActualTax,"Tax Value mismatch : Expected value is "+ExpectedTax+" but got "+ActualTax+" in Recall page");

		//To validate sub total
		double ActSubtotalFromRecall = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")));
		double ExpSubTotal = Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", ""));

		asrt.assertEquals(ExpSubTotal,ActSubtotalFromRecall,"SubTotal mismatch : Expected value is "+ExpSubTotal+" but got "+ActSubtotalFromRecall+" in Recall page");

		//Step 11: Select the Transaction Type as Sales 
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"),NormalSaleUncutAndPreciaTestData.TC30_TransactionType);

		//Step 12 : Click on Process Estimation 
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		//Step 13: .Choose a sales representative
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();
		
		//Step 14: Click on the Amount
		//Step 15 : Select a Payment method(Cash or Card)
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentAmountHDFC=base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleUncutAndPreciaTestData.TC33_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleUncutAndPreciaTestData.TC33_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		//Step 16 : Post the Invoice
		// Expected Result: User should able to Post the Invoice
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String SaleInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC33_ProformaInvoice"); 
		String SalesInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+SaleInvoice+"";
		String TotalDiscount = Discount;
		String TotalQtyPcs = LinesCount;
		String GrossAmount = SubTotal;
		String TotalValue = TotalAmount;
		String NetValue = NetTotal;

		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
				SalesInvoicePath,
				GrossAmount,
				TotalDiscount,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				SubTotal,
				NetValue,
				PaymentAmountHDFC,
				SkuList,
				SkuDetails
				);
//		appUtils.SalesReturnFlow(NormalSaleUncutAndPreciaTestData.TC33_CustomerID, PdfInvoiceNo, NormalSaleUncutAndPreciaTestData.TC33_ReturnProducts);
//		appUtils.CounterFlow(NormalSaleUncutAndPreciaTestData.TC33_FromCounter,NormalSaleUncutAndPreciaTestData.TC33_MetalType,NormalSaleUncutAndPreciaTestData.TC33_SkuList ,UtilityTestData.Terminal);
		Thread.sleep(2000);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();		
	}


	// <summary>
		// Test Case Title : Normal sale multiple Uncut and Precia item
		// Automation ID : TC30
		// Author : Sangeetha M S
		// </summary>
		public void TC30_NormalSaleUncutandPrecia() throws Exception
		{
			//Step 1 : Login to POS
			login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
			base.setZoom(driver, 80);

			//Step 2 : Click on Transaction button
			Thread.sleep(3000);
			appUtils.HamBurgerButtonClick("iconShop");

			//Step 3 : Select the customer 
			//Step 4 : Click on add to estimation button
			appUtils.SearchByCustomerID(NormalSaleUncutAndPreciaTestData.TC30_CustomerId, NormalSaleUncutAndPreciaTestData.TC30_Option);

			// Step 5 : Scan Multiple uncut and precia SKU's and click on Add to cart button
			// Step 6 : Select any one of the sales agent
			// Expected Result : Check Calculations
			// Check calculation : Billing Screen, No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,
			// TAX , Total Amount
			Map<String, String> SkuDetails = new LinkedHashMap<>();
			List<String> ExpectedItemNames = new ArrayList<>();
			List<String> TotalForEachItems = new ArrayList<>();
			List<String> ItemNamesFromBillingPge =new ArrayList<>();
			List<String> ItemQuantity=new ArrayList<>();
			int i=0;
			double ActualSubTotal = 0.0;
			double TotalWithoutTax = 0.0;

			/*For Sku Passing as List
			List<String> SkuList = new ArrayList<>();
			SkuList.addAll(NormalSaleUncutAndPreciaTestData.TC30_SkuUncutList);
			SkuList.addAll(NormalSaleUncutAndPreciaTestData.TC30_SkuPreciaList);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
			for (i = 0; i < SkuList.size(); i++) 
			{    		
				appUtils.SKUIngridentTableValues(SkuList.get(i), i + 1,SkuDetails);
				ExpectedItemNames.add(SkuDetails.get("SKU_" + (i+1) + "_HeaderName"));
				TotalForEachItems.add(SkuDetails.get("SKU_" + (i+1) + "_Total"));
				ItemQuantity.add(SkuDetails.get("SKU_" + (i+1) + "_GrossWeight"));
				Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, (i+1));
				mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
			}*/
			List<String> SkuListUncut = appUtils.SelectMultipleSKUs(NormalSaleUncutAndPreciaTestData.TC30_SkuUncutCount,NormalSaleUncutAndPreciaTestData.TC30_TransferType,NormalSaleUncutAndPreciaTestData.TC30_UncutFromCounter,NormalSaleUncutAndPreciaTestData.TC30_MetalType);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
			for (i = 0; i < SkuListUncut.size(); i++) 
			{    		
				appUtils.SKUIngridentTableValues(SkuListUncut.get(i), i + 1, SkuDetails);
				ExpectedItemNames.add(SkuDetails.get("SKU_" + (i+1) + "_HeaderName"));
				TotalForEachItems.add(SkuDetails.get("SKU_" + (i+1) + "_Total"));
				ItemQuantity.add(SkuDetails.get("SKU_" + (i+1) + "_GrossWeight"));
				Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, (i+1));
				mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
			}

			List<String> SkuListsPrecia = appUtils.SelectMultipleSKUs(NormalSaleUncutAndPreciaTestData.TC30_SkuPreciaCount,NormalSaleUncutAndPreciaTestData.TC30_TransferType,NormalSaleUncutAndPreciaTestData.TC30_PreciaFromCounter,NormalSaleUncutAndPreciaTestData.TC30_MetalType);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
			for (i = 0; i < SkuListsPrecia.size(); i++) 
			{    		
				int Index = SkuListUncut.size() + i + 1;
				appUtils.SKUIngridentTableValues(SkuListsPrecia.get(i), Index, SkuDetails);
				ExpectedItemNames.add(SkuDetails.get("SKU_" + Index  + "_HeaderName"));
				TotalForEachItems.add(SkuDetails.get("SKU_" + Index + "_Total"));
				ItemQuantity.add(SkuDetails.get("SKU_" + Index + "_GrossWeight"));
				Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, (i+1));
				mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
			}

			List<String> AllSkuList = new ArrayList<>();
			AllSkuList.addAll(SkuListUncut);
			AllSkuList.addAll(SkuListsPrecia);

			Map<String, String> Results = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);		
			String SubTotal = Results.get("Subtotal");
			String Discount = Results.get("Discount");
			String TotalGrossWeight = Results.get("GrossWeight");
			String TotalNetWeight = Results.get("NetWeight");
			String Tax = Results.get("Tax");
			String TotalAmount = Results.get("TotalAmount");
			String NetTotal = Results.get("NetTotal");
			String LinesCount = Results.get("LinesCount");

			//Displayed Item Name
			List<WebElement> ItemNamesInBilling = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
			for (int n=0; n<ItemNamesInBilling.size();n++) 
			{
				ItemNamesFromBillingPge.add(ItemNamesInBilling.get(n).getText().trim());
			}
			i=0;
			for (String ItemName : ExpectedItemNames) 
			{
				String ExpectedItemName = ItemName.substring(ItemName.indexOf("-") + 2);
				String ItemNameFromBilling = ItemNamesInBilling.get(i).getText().trim();  

				asrt.assertEquals(ExpectedItemName, ItemNameFromBilling,"Item Name mismatch : Expected  is " + ExpectedItemName + " but got " + ItemNameFromBilling + " in Billing Page");
				i++; 
			}

			//No of Product lines
			int ExpectedLinesCount = Integer.parseInt(LinesCount);
			int ActualProductCount = ItemNamesInBilling.size();    

			asrt.assertEquals(ExpectedLinesCount, ActualProductCount, "Number of Product Lines mismatch : Expected value is "+ExpectedLinesCount+" but got "+ActualProductCount+" in Billing page");

			//Displayed Quantity
			List<WebElement> BillingScreenItemQty = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
			for (i = 0; i < BillingScreenItemQty.size(); i++)
			{
				String DisplayedItemQty = BillingScreenItemQty.get(i).getText().trim().replaceAll("[^\\d.]", "");
				double DisplayedQty = Double.parseDouble(DisplayedItemQty);
				String ExpectedItemQty = ItemQuantity.get(i).trim().replaceAll("[^\\d.]", "");			
				double ExpectedQty = Double.parseDouble(ExpectedItemQty);

				asrt.assertEquals(DisplayedQty,ExpectedQty,"Quantity mismatch : Expected value is " + ExpectedQty + " but got " + DisplayedQty+ " in Billing page");
			}

			//Displayed TOTAL (without Tax)
			List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldAndSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
			for (int j =0; j<TotalsWithoutTax.size();j++) 
			{
				double ActualTotal = Double.parseDouble(TotalForEachItems.get(j));
				double ExpectedTotal = Double.parseDouble(TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""));

				asrt.assertEquals(ExpectedTotal, ActualTotal,"Total(Without Tax) mismatch : Expected value is "+ExpectedTotal+"but got "+ActualTotal+ " in Billing screen");
			}

			//Displayed Subtotal
			for (WebElement SubTotalElement : TotalsWithoutTax)
			{
				String SubTotalValue = SubTotalElement.getText().trim().replaceAll("[^\\d.]", "");
				if (!SubTotalValue.isEmpty())
				{
					ActualSubTotal=ActualSubTotal + Double.parseDouble(SubTotalValue);
				}
			}	
			TotalWithoutTax = Math.round(ActualSubTotal * 100.0) / 100.0;
			double ExpectedSubTotal = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpectedSubTotal, TotalWithoutTax, "SubTotal mismatch : Expected value is "+ExpectedSubTotal+" but got "+TotalWithoutTax+" in Billing page");

			//Tax amount Validation
			double TaxAmount=ActualSubTotal*3/100;
			double ExpectedTax = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));	

			asrt.assertEquals(ExpectedTax,TaxAmount ,1, "Tax Amount mismatch : Expected value is "+ExpectedTax+"but got "+TaxAmount+" in Billing page");

			//Total Amount
			double ActualTotalAmount=ActualSubTotal+TaxAmount;
			double ExpectedTotalAmount = Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpectedTotalAmount, ActualTotalAmount, 1,"Total Amount mismatch : Expected value is "+ExpectedTotalAmount+" but got "+ActualTotalAmount+" in Billing page");

			//Step 7 : In Billing, Click on "Save to Estimate" button	
			base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));

			//Step 8 : Click on Save estimation
			base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
			String EstmnNumber=base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

			String NewPdfName = pdfUtils.DownloadAndRenameLatestPDF("TC30_ProformaInvoice");
			String PdfPath =System.getProperty("user.dir") + "\\Invoices\\"+NewPdfName+"";
			String Gst = Tax;
			String InvoiceTotal = TotalAmount;
			String TaxableValue=SubTotal;
			pdfUtils.ProFormaInvoicePDFVerifyNormal(
					PdfPath,
					TaxableValue,
					Gst,
					InvoiceTotal,
					NetTotal,
					LinesCount,
					Double.parseDouble(TotalGrossWeight),
					Double.parseDouble(TotalNetWeight));

			// Step 9 :Click on Cash and then click on Recall estimate
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

			asrt.assertEquals(ExpectedLinesCount, ActualLinesCountInRecall,"Number of Product Lines mismatch : Expected value is "+ExpectedLinesCount+" but got "+ActualLinesCountInRecall+" in Recall page");

			//To validate Item name
			for (int k =0; k < ItemNamesFromRecall.size();k++) 
			{			
				String ExpectedNameRecall = ItemNamesFromRecall.get(k).getText().trim();
				String ItemNameFromBilling = ItemNamesFromBillingPge.get(k);

				asrt.assertEquals(ExpectedNameRecall,ItemNameFromBilling,"Item Name mismatch : Expected value is "+ExpectedNameRecall+" but got "+ItemNameFromBilling+" in Recall page");
			}

			//To validate quantity
			List<WebElement> QuantityFromRecall = base.GetElement(NormalSaleGoldAndSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
			for(int l =0; l<QuantityFromRecall.size();l++) 
			{
				String DisplayedQtyRecall = QuantityFromRecall.get(l).getText().trim().replaceAll("[^\\d.]", "");
				double DisplayedRecallQty = Double.parseDouble(DisplayedQtyRecall);
				String ExpectedQty = ItemQuantity.get(l).trim().replaceAll("[^\\d.]", "");			
				double ExpectedRecallQty = Double.parseDouble(ExpectedQty);

				asrt.assertEquals(ExpectedRecallQty,DisplayedRecallQty,"Quantity mismatch : Expected value is " + ExpectedRecallQty + " but got " + DisplayedRecallQty+ " in Billing page");
			}

			//To validate Total without tax
			double ActualTotalWithoutTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax")));

			asrt.assertEquals(ExpectedSubTotal,ActualTotalWithoutTax,"Total(Without Tax) mismatch : value is "+ExpectedSubTotal+" but got "+ActualTotalWithoutTax+" in Recall page");

			//To validate tax
			double ActualTax = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.TotalTax")));

			asrt.assertEquals(ExpectedTax,ActualTax,"Tax Value mismatch : Expected value is "+ExpectedTax+" but got "+ActualTax+" in Recall page");

			//To validate sub total
			double ActSubtotalFromRecall = Double.parseDouble(base.GetText(NormalSaleGoldAndSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")));
			double ExpSubTotal = Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpSubTotal,ActSubtotalFromRecall,"SubTotal mismatch : Expected value is "+ExpSubTotal+" but got "+ActSubtotalFromRecall+" in Recall page");

			//Step 11: Select the Transaction Type as Sales 
			base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"),NormalSaleUncutAndPreciaTestData.TC30_TransactionType);

			//Step 12 : Click on Process Estimation 
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

			//Step 13: .Choose a sales representative
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
			appUtils.HandleSpecifyRateModalInRecall();
			
			//Step 14: Click on the Amount
			//Step 15 : Select a Payment method(Cash or Card)
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
			base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
			String PaymentAmountHDFC=base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
			base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleUncutAndPreciaTestData.TC30_ApprCode);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
			base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleUncutAndPreciaTestData.TC30_CardCode);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

			//Step 16 : Post the Invoice
			// Expected Result: User should able to Post the Invoice
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
			String SaleInvoice= pdfUtils.DownloadAndRenameLatestPDF("TC30_ProformaInvoice"); 
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));	
			String SalesInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+SaleInvoice+"";
			String TotalDiscount = Discount;
			String TotalQtyPcs = LinesCount;
			String GrossAmount=SubTotal;
			String TotalValue=TotalAmount;
			String NetValue=NetTotal;

			String PdfInvoiceNo = pdfUtils.SaleInvoicePdfValidation(
					SalesInvoicePath,
					GrossAmount,
					TotalDiscount,
					TotalQtyPcs,
					TotalNetWeight,
					TotalGrossWeight,
					SubTotal,
					NetValue,
					PaymentAmountHDFC,
					AllSkuList,
					SkuDetails
					);
			pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
			/*SKU Return and Counter Transfer
		 	appUtils.SalesReturnFlow(NormalSaleUncutAndPreciaTestData.TC30_CustomerId, PdfInvoiceNo, NormalSaleUncutAndPreciaTestData.TC30_ReturnProducts);
			appUtils.CounterFlow(NormalSaleUncutAndPreciaTestData.TC30_UncutFromCounter,NormalSaleUncutAndPreciaTestData.TC30_MetalType,NormalSaleUncutAndPreciaTestData.TC30_SkuUncutList ,UtilityTestData.Terminal);
			Thread.sleep(2000);
			base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
			appUtils.CounterFlow(NormalSaleUncutAndPreciaTestData.TC30_PreciaFromCounter,NormalSaleUncutAndPreciaTestData.TC30_MetalType,NormalSaleUncutAndPreciaTestData.TC30_SkuPreciaList,UtilityTestData.Terminal);
			 */		
		}



	/// <summary>
	/// Test Case Title : Normal Sale Multiple Uncut
	/// Automation ID : TC31
	/// Author : Robin
	/// </summary>
	public void TC31_NormalSaleUncutandPrecia() throws Exception {
		// Step 1: Login to POS
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Click on Transaction button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer 
		// Step 4: Click on add to estimation
		appUtils.SearchByCustomerID(NormalSaleUncutAndPreciaTestData.TC31_CustomerID, UtilityTestData.MenuBarOptions[5]);

		// Step 5: Scan Multiple Uncut SKU's and click on Add to cart button
		// Step 6: Select anyone of the sales agent
		// Expected Result : Validating No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal, TAX, Total amount
		Map<String, String> ScannedSkuData = new LinkedHashMap<>();    
		List<String> UncutSkuList = appUtils.SelectMultipleSKUs(
				NormalSaleUncutAndPreciaTestData.TC31_SKUCount,
				NormalSaleUncutAndPreciaTestData.TC31_TransferType,
				NormalSaleUncutAndPreciaTestData.TC31_FromCounter,
				NormalSaleUncutAndPreciaTestData.TC31_MetalType
				);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
 
 
		for (int i = 0;i < UncutSkuList.size(); i++)
		{
			String CurrentUncutSku = UncutSkuList.get(i);
			int UncutSkuCount = i + 1;
 
			appUtils.SKUIngridentTableValues(CurrentUncutSku.toString(), UncutSkuCount, ScannedSkuData);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuData, UncutSkuCount);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, UncutSkuCount);
		}
 
		Map<String, String> SkuDetails = appUtils.TransactionSKUsLinesVerify(UncutSkuList, ScannedSkuData);
		String SubTotal = SkuDetails.get("Subtotal");
		String TotalGrossWeight = SkuDetails.get("GrossWeight");
		String TotalNetWeight = SkuDetails.get("NetWeight");
		String Gst = SkuDetails.get("Tax");
		String InvoiceTotal = SkuDetails.get("TotalAmount");
		String NetTotal = SkuDetails.get("NetTotal");
		String LinesCount = SkuDetails.get("LinesCount");
		String TotalDiscount = SkuDetails.get("Discount");
 
		mathUtils.ValidateTransactionLineCalculation(SkuDetails, ScannedSkuData);

		// Step 7: In Billing, Click on "Save to Estimate" button
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));

		// Step 8: Click on Save estimation
		// Expected Result: Validating invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		Thread.sleep(3000);
		String ProformaInvoicePdfName = pdfUtils.DownloadAndRenameLatestPDF("TC31_ProFormaInvoice");
		Thread.sleep(3000);
		String PdfPath =System.getProperty("user.dir") + "\\Invoices\\"+ProformaInvoicePdfName+"";
		pdfUtils.ProFormaInvoicePDFVerifyNormal(
				PdfPath,
				SubTotal,
				Gst,
				InvoiceTotal,
				NetTotal,
				LinesCount,
				Double.parseDouble(TotalGrossWeight),
				Double.parseDouble(TotalNetWeight)
				);

		// Step 9: Click on Cash and then click on Recall estimate
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Filter"), EstmnNumber);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));

		// Step 10:Select the estimate and click on recall estimation
		// Expected Result: No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal, TAX, Total amount in recall estimate page
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		Thread.sleep(3000);
		// Step 11:Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSaleUncutAndPreciaTestData.TC31_TransactionType);

		// Step 12:Click on Process Estimation
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 13:Choose a sales representative
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();
		
		Map<String, String> TransactionRecallDataLine = appUtils.TransactionSKUsLinesVerify(UncutSkuList, ScannedSkuData);	
		mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLine, ScannedSkuData);

		// Step 14:Click on the Amount
		// Step 15:Select a Payment method(Cash or Card)
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentHDFCCard = base.GetAttribte(NormalSaleGoldAndSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleUncutAndPreciaTestData.TC31_ApprCode);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldAndSilverObj.Edt_ApprCode("textInputArea"),NormalSaleUncutAndPreciaTestData.TC31_CardNo);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("Close"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		// Step 16:  Post the Invoice
		// Expected Result : Validating final invoice details
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String TaxInvoicePdfName = pdfUtils.DownloadAndRenameLatestPDF("TC31_TaxInvoice");	
		String InvoicePdfPath =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoicePdfName+"";
		String GrossAmount = SubTotal;
		String TaxableValue = SubTotal;
		String TotalQtyPcs = LinesCount;
		String NetValue = String.format("%.2f", Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", "")));
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
				UncutSkuList,
				ScannedSkuData
				);

//		appUtils.SalesReturnFlow(NormalSaleUncutAndPreciaTestData.TC31_CustomerID, PdfInvoiceNo, NormalSaleUncutAndPreciaTestData.TC31_ReturnProducts);
//		appUtils.CounterFlow(NormalSaleUncutAndPreciaTestData.TC31_FromCounter,NormalSaleUncutAndPreciaTestData.TC31_MetalType,NormalSaleUncutAndPreciaTestData.TC31_UncutSkuList ,UtilityTestData.Terminal);

		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	// <summary>
	// Test Case Title : Normal sale uncut with diamond ingredient(VIRAZ)
	// Step 5: Handled item-specific 'warning' message on the Item Details page after clicking the 'Add to Cart' button.
	// Automation ID : TC32
	// Author : Hasna E K
	// </summary>
	public void TC32_NormalSaleUncutandPrecia() throws Exception
	{
		//Step 1 : Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2 : Click on Transaction button
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		//Step 3 : Select the customer 
		//Step 4 : Click on add to estimation button
		appUtils.SearchByCustomerID(NormalSaleUncutAndPreciaTestData.TC32_CustomerID, NormalSaleUncutAndPreciaTestData.TC32_Option);

		//Step 5: Scan Uncut-Viraz SKU and click on Add to cart button
		//Step 6. Select any one of the sales agent
		//Expected Result: Check calculation, Note:Billing Screen- *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		
		List<String> SkuList = appUtils.GetVirazSkus(Utility_TestData.UncutVirazSkus, 1);
		for (int i = 0; i < SkuList.size(); i++) {
			int SkuNumber = i + 1;
			String Sku =SkuList.get(i);
			appUtils.SKUIngridentTableValues(Sku, SkuNumber, ScannedSkuDataMap);
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuNumber);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuNumber);
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		//Billing Screen- *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal = TransactionDataLine.get("Subtotal");
		String Discount = TransactionDataLine.get("Discount");
		String TotalGrossWeight = TransactionDataLine.get("GrossWeight");
		String TotalNetWeight = TransactionDataLine.get("NetWeight");
		String Tax = TransactionDataLine.get("Tax");
		String TotalAmount = TransactionDataLine.get("TotalAmount");
		String NetTotal = TransactionDataLine.get("NetTotal");
		String LinesCount = TransactionDataLine.get("LinesCount");

		List<String> ItemNameBillngScrn = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5"));
		List<String> QtyBillingScrn = base.GetElementTexts(NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5"));
		mathUtils.ValidateTransactionLineCalculation(TransactionDataLine, ScannedSkuDataMap);

		//Step 7: In Billing,Click on Save to estimation button
		//Step 8: save estimate
		//Expected Result: Verify the invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldAndSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC32_ProformaInvoice");
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

		//Step 9:Click on Cash and then click on Recall estimate
		//Step 10: Select the estimate and click on Recall estimation
		//Expected Result: Verify whether the recalled item is the same as the item in the cart. 
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

		appUtils.ValidateBillingAndRecallScreenData(EstmnNumber,LinesCount,ItemNameBillngScrn,QtyBillingScrn, SubTotal, Tax, NetTotal);

		// Step 11: Select the Transaction Type as Sales
		base.buttonClick(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_BankProofType("Transaction type"), NormalSaleUncutAndPreciaTestData.TC32_TransactionType);

		// Step 12: Click on Process Estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		// Step 13: Choose a Sales Representative
		// Expected Result:Cashier Screen after recall*No of Product lines*Displayed Item Name*Displayed Quantity*Displayed TOTAL (without Tax)*Displayed Subtotal*TAX*Total Amount
		base.setZoom(driver, 60);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		Map<String, String> TransactionRecallDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		mathUtils.ValidateTransactionLineCalculation(TransactionRecallDataLine, ScannedSkuDataMap);

		// Step 14:  Click on the Amount
		// Step 15:  Select a Payment method(Cash or Card)
		String PaymentHDFCCard =base.GetText(NormalSaleGoldAndSilverObj.Sel_AmountDue("Amount due"));
		appUtils.PaymentAfterRecallEstimate(NormalSaleUncutAndPreciaTestData.TC32_PaymentMethod);

		// Step 16:  Post the Invoice
		// Expected Result:  Check final invoice details * save receipt id for future reference
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String SalesInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC32_SalesInvoice");
		Thread.sleep(2000);
		String SaleInvoicePdfPath = System.getProperty("user.dir") + "\\Invoices\\" + SalesInvoice;
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
		appUtils.SalesReturnFlow(NormalSaleUncutAndPreciaTestData.TC32_CustomerID, PdfInvoiceNo,NormalSaleUncutAndPreciaTestData.TC32_ReturnProducts);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		appUtils.CounterFlow(NormalSaleUncutAndPreciaTestData.TC32_ToCounter,
				NormalSaleUncutAndPreciaTestData.TC32_MetalType,
				SkuList,
				Utility_TestData.Terminal);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

	}

}