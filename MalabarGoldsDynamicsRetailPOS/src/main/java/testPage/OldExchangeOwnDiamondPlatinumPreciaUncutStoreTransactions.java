package testPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import objectRepository.NormalSalesReturnGoldSilverReturnSaleCounter_Obj;
import objectRepository.OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj;
import objectRepository.OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_Obj;
import testData.CommonData;
import testData.OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_TestData;
import testData.OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.BasePge;
import utilPack.ErpUtilities;
import utilPack.MathUtilities;
import utilPack.PdfUtilities;
import utilPack.AppUtilities.SKUResult;
import java.util.Optional;

//import basePackage.BasePge;

public class OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;
	MathUtilities mathUtils;
	PdfUtilities pdfUtils;
	ErpUtilities erpUtils;

	Login login = new Login(driver); 
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();
	NormalSaleGoldandSilver_Obj NormalSaleGoldandSilverObj = new NormalSaleGoldandSilver_Obj();
	OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj = new OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj();
	OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_TestData OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData = new OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_TestData();
	NormalSalesReturnGoldSilverReturnSaleCounter_Obj NormalSalesReturnGoldSilverReturnSaleCounterObj = new NormalSalesReturnGoldSilverReturnSaleCounter_Obj();
	OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_Obj OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsObj=new OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();

	public OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 
		pdfUtils=new PdfUtilities(base);
		mathUtils = new MathUtilities(base);
		erpUtils = new ErpUtilities(base);
	}

	// <summary>
	// Test Case Title : Own Uncut Exchange From Store Transactions
	// Automation ID : TC59
	// Author: Robin T Abraham
	// </summary>
	public void TC59_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 70);

		// Step 2: Click on Transaction button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer and click on add to estimation button
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_CustomerID, UtilityTestData.MenuBarOptions[5]);

		//Step 4: Scan multiple SKU
		//Step 5 : Select OU
		//Step 6 : Click on OU own
		Map<String, String> ScannedSkuData = new LinkedHashMap<>();

		List<String> AllSkuList = new ArrayList<>();
		List<String> PurityOfItems = new ArrayList();
		List<Double> AllRates = new ArrayList<>();
		String OuItemNameInBillingPge = null;
		int i=0;
		int k=0;
		double TotalWithoutTax = 0.0;
		String CurrentExchangeRate = null;

		List<String> GoldSkuList = appUtils.SelectMultipleSKUs(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_GoldSkuCount,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_GoldTransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_GoldFromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_GoldMetalType
				);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		List<String> PreciaSkuList = appUtils.SelectMultipleSKUs(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_PreciaSkuCount,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_PreciaTransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_PreciaFromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_PreciaMetalType
				);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		AllSkuList.addAll(GoldSkuList);
		AllSkuList.addAll(PreciaSkuList);

		Map<String, String> BillingDetailsMap = new HashMap<>();

		for ( i = 0; i < AllSkuList.size(); i++) {
			int SkuCount = i + 1;
			String CurrentSku = AllSkuList.get(i);

			// Fetch all ingredient values for current SKU
			appUtils.SKUIngridentTableValues(CurrentSku, SkuCount, BillingDetailsMap);

			PurityOfItems.add(BillingDetailsMap.get("SKU_" + (i+1) + "_Purity"));

			// Loop through each item under the current SKU to fetch rates
			for (int ItemIndex = 1; ; ItemIndex++) {
				String RateKey = "SKU" + SkuCount + "_ITEM" + ItemIndex + "_RATE";

				if (BillingDetailsMap.containsKey(RateKey)) {
					try {
						double SkuRate = Double.parseDouble(BillingDetailsMap.get(RateKey));
						AllRates.add(SkuRate);
					} catch (NumberFormatException e) {
						System.out.println("Rate not fetched or invalid for key: " + RateKey);
					}
				} else {
					break;
				}
			}

			BillingDetailsMap.put("SKU_" + SkuCount + "_AllRates", AllRates.toString());

			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(BillingDetailsMap, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		Map<String, String> SkuTransactionPageItems = appUtils.TransactionSKUsLinesVerify(AllSkuList, BillingDetailsMap);
		String SkuTotalGrossWeight  = SkuTransactionPageItems.get("GrossWeight");
		String SkuTotalNewNetWeight = SkuTransactionPageItems.get("NetWeight");
		String SkuSubTotalValue 	= SkuTransactionPageItems.get("Subtotal");
		String SkuLinesCount        = SkuTransactionPageItems.get("LinesCount");
		String SkuInvoiceTotal      = SkuTransactionPageItems.get("TotalAmount");
		String SkuNetTotal          = SkuTransactionPageItems.get("NetTotal");

		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),"OU");
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldUncutOwnProduct));	

		//Step 7. Select configuration(purity 22k) and Click on Add item button
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_Purity);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		//Step 8. Enter receipt number
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"));
		Thread.sleep(3000);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"),UtilityTestData.InvoiceNumber);

		//Step 9. Enter line number and Click on fetch button
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_LineNumber );
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));

		//Step 10. Select QC person
		//Step 11. Select Smith person
		//Step 12. click on calculate button
		//Check Exchange rate own Uncut
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_QCPerson);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_SmithPerson);

		Thread.sleep(3000);
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

		Thread.sleep(5000);

		double ActualOURateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		String Purity = PurityOfItems.get(0);
		double ExchangeRate =  AllRates.get(0);
		long RoundedExpectedOuRate=0;
		if (Purity.equalsIgnoreCase("18k")) 
		{
			double ExpectedOuRate = (ExchangeRate * 22) / 18;
			RoundedExpectedOuRate = Math.round(ExpectedOuRate);
			asrt.assertEquals(RoundedExpectedOuRate, Math.round(ActualOURateInItemDetailsPge),1,"Exchange rate (Board rate) mismatch: Expected exchange rate is " + RoundedExpectedOuRate + " but got " + Math.round(ActualOURateInItemDetailsPge) +" in OU item details page");
		} 
		else 
		{
			RoundedExpectedOuRate = Math.round(ExchangeRate);
			asrt.assertEquals(RoundedExpectedOuRate, Math.round(ActualOURateInItemDetailsPge),1,"Exchange rate (Board rate) mismatch: Expected exchange rate is " +RoundedExpectedOuRate + " but got " + Math.round(ActualOURateInItemDetailsPge) +" in OU item details page");
		}	

		List<String> ExpectedNetWtOU = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("column3 textRight", "h4 ellipsis cell"));
		String ExpectedGrossWtOU = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		List<String> ActualOURateInItemDetailPge = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("column4 textRight", "h4 ellipsis cell"));

		List<String> StoneLinesCount = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("column1", "h4 ellipsis cell"));

		List<Map<String, String>> ExpectedStoneListOU = new ArrayList<>();
		for (String Line : StoneLinesCount) {
			String[] Parts = Line.trim().split("\\s+");
			if (Parts.length >= 4) {
				Map<String, String> Stone = new HashMap<>();
				Stone.put("StoneName", Parts[0]);
				Stone.put("StoneWt", Parts[1]);
				Stone.put("Rate", Parts[2]);
				Stone.put("Amount", Parts[3]);
				ExpectedStoneListOU.add(Stone);
			}
		}

		String ExpectedTotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));

		String ItemName = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = ItemName.substring(ItemName.indexOf("-") + 2);

		//Step 13. Click on add to cart button
		//Expected :Check calculation
		//No of Product lines
		//Displayed Item Name
		//Displayed Quantity
		//Displayed TOTAL (without Tax)
		//Displayed Subtotal
		//Total Amount
		Thread.sleep(3000);
		base.buttonClick(LoginPageObj.Btn_SingnIn("SimpleProductDetailsView_AppBarView_appBarCommandCommand"));

		List<String> CurrentSkuList = Optional.ofNullable(AllSkuList).orElseGet(ArrayList::new);
		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(CurrentSkuList, BillingDetailsMap);
		String SubTotal = TransactionPageItems.get("Subtotal");
		String Discount = TransactionPageItems.get("Discount");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight = TransactionPageItems.get("NetWeight");
		String Gst = TransactionPageItems.get("Tax");
		String InvoiceTotal = TransactionPageItems.get("NetTotal");
		String NetTotal = TransactionPageItems.get("NetTotal");
		String LinesCount = TransactionPageItems.get("LinesCount");

		//OU Item name
		List<WebElement> ListOfItemNamesInBillingPge = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));		
		boolean OuItemFound = false;
		for (WebElement Item : ListOfItemNamesInBillingPge)
		{
			String ItemNameFromBillingPage = Item.getText().trim();	    
			if (ItemNameFromBillingPage.equalsIgnoreCase("OU")) 
			{
				OuItemFound = true;
				OuItemNameInBillingPge = ItemNameFromBillingPage;
				Assert.assertEquals(OuItemNameInBillingPge, ExpectedItemNameInItemDetailsPge,"OU Item Name mismatch: Expected '" + ExpectedItemNameInItemDetailsPge + "' but got '" + OuItemNameInBillingPge + "' in Billing Page");
				break;
			}
		}
		if (!OuItemFound)
		{
		}

		//OU Quantity
		List<WebElement> BillingScreenItemQty=base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		for (i = 0; i < ListOfItemNamesInBillingPge.size(); i++) 
		{
			String itemNameInBilling = ListOfItemNamesInBillingPge.get(i).getText().trim();
			if (itemNameInBilling.equalsIgnoreCase(ExpectedItemNameInItemDetailsPge))
			{
				String DisplayedItemQty = BillingScreenItemQty.get(i).getText().trim().replaceAll("[^\\d.]", "");
				double DisplayedQty = Double.parseDouble(DisplayedItemQty);
				asrt.assertEquals(String.format("%.3f",DisplayedQty),ExpectedGrossWtOU,"OU Item Quantity mismatch: Expected value is " + ExpectedGrossWtOU +" but got " + DisplayedQty + " in Billing page");
				break; 
			}
		}

		//OU Total amount without tax
		double OuItemDetailsPageTotal=0.0;
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		for (int j = 0; j < TotalsWithoutTax.size(); j++)
		{
			String ItemNameInBilling = ListOfItemNamesInBillingPge.get(j).getText().trim();
			String DisplayedTotalStr = TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", "");
			double DisplayedTotal = Double.parseDouble(DisplayedTotalStr);
			OuItemDetailsPageTotal=Double.parseDouble(ExpectedTotalAmountInItemDetailsPge);
			if (ItemNameInBilling.equalsIgnoreCase(ExpectedItemNameInItemDetailsPge))
			{
				asrt.assertEquals(DisplayedTotal, OuItemDetailsPageTotal, 1, "OU item Total(Without Tax) mismatch: Expected " + OuItemDetailsPageTotal + " but got " + DisplayedTotal + " in Billing screen");
			}
		}

		//Subtotal
		double TotalWithoutTaxSum = 0.0;
		double OuTotal = 0.0;
		double TaxableAmount=0.0;
		for (int j = 0; j < TotalsWithoutTax.size(); j++) 
		{
			String ItemNameInBilling = ListOfItemNamesInBillingPge.get(j).getText().trim(); 
			String SubTotalStr = TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""); 

			if (!SubTotalStr.isEmpty())
			{
				double CalcSubTotal = Double.parseDouble(SubTotalStr);
				if (!ItemNameInBilling.equalsIgnoreCase(ExpectedItemNameInItemDetailsPge)) 
				{
					TotalWithoutTaxSum =TotalWithoutTaxSum+ CalcSubTotal;
					TaxableAmount=TotalWithoutTaxSum;			
				} 
				else
				{				
					TotalWithoutTaxSum = TotalWithoutTaxSum-CalcSubTotal;
					OuTotal=CalcSubTotal;
				}
			}
		}

		TotalWithoutTax = Math.round(TotalWithoutTaxSum * 100.0) / 100.0;
		String SubTotalBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"));
		double ExpectedSubTotalBillingPge = Double.parseDouble(SubTotalBillingPge.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(TotalWithoutTax, ExpectedSubTotalBillingPge,"SubTotal mismatch : Expected value is " + ExpectedSubTotalBillingPge + " but got " + TotalWithoutTax + " in Billing page");

		//TAX
		double TaxAmountBillingPge=TaxableAmount*3/100;
		String TaxBillingPage = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4"));
		double ExpectedTaxBillingPge=Double.parseDouble(TaxBillingPage.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(ExpectedTaxBillingPge,TaxAmountBillingPge ,1, "Tax Amount mismatch : Expected value is "+ExpectedTaxBillingPge+"but got "+TaxAmountBillingPge+" in Billing page");

		//Total Amount
		String TotalAmount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4"));
		double ExpectedTotalAmount=Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));
		double CalcTotalAmount=ExpectedTaxBillingPge+ExpectedSubTotalBillingPge;
		asrt.assertEquals(ExpectedTotalAmount, CalcTotalAmount, 1,"Total Amount mismatch : Expected value is "+ExpectedTotalAmount+" but got "+CalcTotalAmount+" in Billing page");


		//Step 14. Save to estimate
		//Expected Results :Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));

		String TaxableValue = String.format("%.2f",TaxableAmount);
		String TotalAmnt = SubTotal;
		Gst = TaxBillingPage;
		InvoiceTotal = TotalAmount.trim().replaceAll("[^\\d.]", "");
		String ProformaPdfName = pdfUtils.DownloadAndRenameLatestPDF("TC59_ProformaInvoice");
		String ProFormaInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+ProformaPdfName+"";
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				TotalAmnt
				);

		//Step 15. Recall estimate from cashier
		//Step 16. Select estimate and click on save to estimate button
		//Step 17. Select the estimate and click on Recall estimation
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		//Validation Steps : Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax)
		String EstmnNumberRecallPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstmnNumberRecallPge,EstmnNumber,"Mismatch in Estimation No:, expected "+EstmnNumberRecallPge+" but got "+EstmnNumber+"in the  screen");	

		//Step 18.Select transaction type as "Exchange" and click on Process estimation
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_TransactionTypeOU);

		List<WebElement> OGItemNamesFromRecallPage = base.GetElement(NormalSaleGoldandSilverObj.Ele_ItemRecallEst("gridcell", "ITEM NAME"));
		for (k = 0; k < OGItemNamesFromRecallPage.size(); k++) 
		{
			String ExpectedNameRecallPage = OGItemNamesFromRecallPage.get(k).getText().trim();

			if (ExpectedNameRecallPage.equalsIgnoreCase(ExpectedItemNameInItemDetailsPge)) 
			{
				//To validate OG Item name
				asrt.assertEquals(ExpectedNameRecallPage,ExpectedItemNameInItemDetailsPge,"OG Item Name mismatch : Expected value is "+ExpectedNameRecallPage+" but got "+ExpectedItemNameInItemDetailsPge+" in Recall page");

				//To validate quantity
				List<WebElement> QuantityFromRecall = base.GetElement(NormalSaleGoldandSilverObj.Ele_ItemRecallEst("gridcell", "QUANTITY"));
				for(int l =0; l<QuantityFromRecall.size();l++) 
				{
					String DisplayedQtyRecall = QuantityFromRecall.get(l).getText().trim().replaceAll("[^\\d.]", "");
					double DisplayedRecallQty = Double.parseDouble(DisplayedQtyRecall);								
					double ExpectedRecallQty = Double.parseDouble(ExpectedGrossWtOU.trim().replaceAll("[^\\d.]", ""));

					asrt.assertEquals(ExpectedRecallQty,DisplayedRecallQty,"OU Quantity mismatch : Expected value is " + ExpectedRecallQty + " but got " + DisplayedRecallQty+ " in Recall page");
				}

				//To validate OG Total without tax
				double ActualTotalWithoutTax = Math.abs(Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_TotWithoutTax("text: viewModel.TotalWithoutTax"))));

				asrt.assertEquals(OuItemDetailsPageTotal,ActualTotalWithoutTax,"OU Total(Without Tax) mismatch : value is "+OuItemDetailsPageTotal+" but got "+ActualTotalWithoutTax+" in Recall page");

				//To validate sub total
				double ActSubtotalFromRecall = Math.abs(Double.parseDouble( base.GetText(NormalSaleGoldandSilverObj.Ele_TotWithoutTax("text: viewModel.SubTotal")).trim().replaceAll("[^\\d.-]", "")));
				double ExpSubTotal =  Math.abs(OuTotal);

				asrt.assertEquals(ExpSubTotal,ActSubtotalFromRecall,"OU SubTotal mismatch : Expected value is "+ExpSubTotal+" but got "+ActSubtotalFromRecall+" in Recall page");		
			}
		}

		//Step 19. Choose a sales representative
		//Step 20. Click on the Amount For printing Receipt
		//Expected Check Exchange invoice details
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) 
		{
			try 
			{ 
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} 
			catch (Exception e) 
			{
			}
		} 

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		// Prepare data for validation
		List<String> ExpectedGrossWt = new ArrayList<>();
		List<String> ExpectedNetWt = new ArrayList<>();
		List<String> ExpectedGoldRate = new ArrayList<>();
		List<Map<String, String>> ExpectedStones = new ArrayList<>();

		for (i = 1; i <= AllSkuList.size(); i++) {
			ExpectedGrossWt.add(BillingDetailsMap.get("SKU_" + i + "_GrossWeight"));
			ExpectedNetWt.add(BillingDetailsMap.get("SKU_" + i + "_NetWeight"));
			ExpectedGoldRate.add(BillingDetailsMap.get("SKU_" + i + "_Rate"));
		}

		// Dynamically extract stone data from BillingDetailsMap
		int StoneIndex = 1;
		while (BillingDetailsMap.containsKey("Stone_" + StoneIndex + "_Name")) {
			Map<String, String> Stone = new HashMap<>();
			Stone.put("StoneName", BillingDetailsMap.get("Stone_" + StoneIndex + "_Name"));
			Stone.put("Rate", BillingDetailsMap.get("Stone_" + StoneIndex + "_Rate"));
			Stone.put("Weight", BillingDetailsMap.get("Stone_" + StoneIndex + "_Weight"));
			Stone.put("Amount", BillingDetailsMap.get("Stone_" + StoneIndex + "_Amount"));
			ExpectedStones.add(Stone);
			StoneIndex++;
		}

		// Call ValidateOUPdf with dynamic inputs
		String ExpectedItemTotal=ExpectedTotalAmountInItemDetailsPge;
		String ExpectedTotal=ExpectedTotalAmountInItemDetailsPge;
		String ExpectedPaymentDetails=ExpectedTotalAmountInItemDetailsPge;
		Map<String, Object> PurchaseBillData = pdfUtils.ValidateOUPdf(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_PaymentDetails,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_BilledTo,
				ExpectedGrossWtOU,         
				ExpectedNetWtOU,           
				ActualOURateInItemDetailPge,
				ExpectedItemTotal,
				ExpectedTotal,
				ExpectedPaymentDetails
				);

		//Step 21. In cash, click on "Recall Estimate"
		//Step 22. Select the estimate, Click on Recall Estimation
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//Step 23. Select transaction type as "Sales" and click on Process estimation
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_TransactionType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 

		//Step 24. Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Step 25. Click on the Amount For printing Receipt
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentAmountHDFC=base.GetAttribte(NormalSaleGoldandSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldandSilverObj.Edt_ApprCode("textInputArea"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_ApprCode);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldandSilverObj.Edt_ApprCode("textInputArea"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC59_CardNo);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Close"));

		//Step 26. Post exchange invoice
		//Expected Results :  Check final invoice details
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(3000);	
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC59_TaxInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";

		String GrossAmount     = SkuSubTotalValue.replaceAll("[^\\d.]", "");
		String TotalDiscount   = Discount;
		String TotalQtyPcs     = SkuLinesCount;
		String Adjustment      = ExpectedTotalAmountInItemDetailsPge;
		String TotalDiamond    = null;
		String PaymentHDFCCard = PaymentAmountHDFC;
		String SalesPdfPath    = SaleInvoicePath;
		String NetValue        = SkuNetTotal.replaceAll("[^\\d.]", "");
		String PdfInvoiceNo    = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				SkuTotalNewNetWeight,
				SkuTotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				AllSkuList,
				BillingDetailsMap
				);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));

		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	//<summary>
	// Test Case Title : own diamond manual purchase
	// Automation ID : TC61
	// Author: Chandana Babu
	// </summary>
	public void TC61_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4: Select OD
		// Step 5: Click on OD own
		// Step 6: Select configuration(purity 22K)
		// Step 7: Click on Add item button and click on purchase button
		// Step 8: Select QC person
		// Step 9: Select Smith person
		// Step 10: Enter piece value
		// Step 11: Enter gross weight
		// Step 12: Enter Diamond pieces,Weight and rate
		// Expected: Check Purchase rate(Board rate) and Check calculation
		// Step 13: click on calculate button
		// Step 14: Click on Add to cart button
		// Step 15: Choose a Sales Representative
		// Note: Continue step 5-13 ( select the purity 18K)
		List<String> Purities = Arrays.asList("22k","18k");
		List<String> ItemNamesInItemDetailsPge = new ArrayList<>();
		List<String> GrossWeight = new ArrayList<>();
		List<String> NetWeight = new ArrayList<>();
		List<String> GoldRate = new ArrayList<>();
		List<String> DiamondWeight = new ArrayList<>();
		List<String> DiamondRate = new ArrayList<>();
		List<String> TotalAmount = new ArrayList<>();
		String ActualTotalAmountInBillingPge ="";

		for(String Purity : Purities) {
			Thread.sleep(3000);
			base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_Product);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldDiamondProduct));	

			base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),Purity);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

			Thread.sleep(1000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_QcAndSmithPerson);

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_PiecesByPurity.get(Purity));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_GrossWeightByPurity.get(Purity));

			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(4000);
			double CurrentGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));

			base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
			Thread.sleep(1000);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));

			Thread.sleep(1000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_QcAndSmithPerson);

			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_QcAndSmithPerson); 

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_PiecesByPurity.get(Purity));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdpcs"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdpcs"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_DiamondPiecesByPurity.get(Purity));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_GrossWeightByPurity.get(Purity));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_DiamondWeightByPurity.get(Purity));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_DiamondRateByPurity.get(Purity));

			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(3000);
			double ActualGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
			double ExpectedGoldRateInItemDetailsPge = (double) Math.round(CurrentGoldRateInItemDetailsPge*0.98);
			asrt.assertEquals(ExpectedGoldRateInItemDetailsPge, ActualGoldRateInItemDetailsPge,1.0,"ODPurchaseRate mismatch: Expected value is "+ExpectedGoldRateInItemDetailsPge+" but got "+ActualGoldRateInItemDetailsPge+" in item details page");

			String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));		
			double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
			double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
			String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
			double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);
			asrt.assertEquals(ExpectedGoldAmountInItemDetailsPge, ActualGoldAmountInItemDetailsPge,1.0,"GoldAmount mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in item details page");		

			String ExpectedDiamondWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"));
			String ExpectedDiamondRateInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"));
			double ExpectedDiamondAmountInItemDetailsPge = Math.round(Double.parseDouble(ExpectedDiamondWeightInItemDetailsPge)*Double.parseDouble(ExpectedDiamondRateInItemDetailsPge)*100.0)/100.0;
			String DiamondAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdamt"));
			double ActualDiamondAmountInItemDetailsPge = Double.parseDouble(DiamondAmountInItemDetailsPge);
			asrt.assertEquals(ExpectedDiamondAmountInItemDetailsPge, ActualDiamondAmountInItemDetailsPge,1.0, "Diamond amount mismatch: Expected is "+ExpectedDiamondAmountInItemDetailsPge+" but got "+ActualDiamondAmountInItemDetailsPge+" in item details page");

			String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
			double ActualTotalAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
			double ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPge+ActualDiamondAmountInItemDetailsPge;
			asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

			String ItemNameForOD = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
			String ExpectedItemNameInItemDetailsPge = ItemNameForOD.substring(ItemNameForOD.indexOf("-") + 2);

			ItemNamesInItemDetailsPge.add(ExpectedItemNameInItemDetailsPge);
			GrossWeight.add(ExpectedGrossWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			NetWeight.add(ExpectedNetWeightInItemDetailsPge);
			GoldRate.add(Double.toString(ActualGoldRateInItemDetailsPge));
			DiamondWeight.add(ExpectedDiamondWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			DiamondRate.add(ExpectedDiamondRateInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			TotalAmount.add(TotalAmountInItemDetailsPge);

			if (base.isExists(NormalSaleGoldandSilverObj.Ele_Payement("Error"))) {
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			};
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
			base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

			double SubTotalSum = 0.00;
			List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
			for(int i =0; i < AllProductRows.size() ;i++) {

				//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
				int ExpectedLinesCountInBillingPge = AllProductRows.size();
				int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));
				asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");

				List<WebElement> ItemName = base.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
				String ActualItemNameInBillingPge = ItemName.get(i).getText();
				String ExpectedItemNameInBillingPge = ItemNamesInItemDetailsPge.get(i);
				asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");

				List<WebElement> Quantity = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
				String ActualQuantityInBillingPge = Quantity.get(i).getText().replace("-", "");
				String ExpectedQuantityInBillingPge = GrossWeight.get(i);
				asrt.assertEquals(ExpectedQuantityInBillingPge, ActualQuantityInBillingPge,"Quantity mismatch: Expected is "+ExpectedQuantityInBillingPge+" but got "+ActualQuantityInBillingPge+" in billing page");

				List<WebElement> TotalWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));
				String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replace("-", "").replace("\u20B9", "").replace(",", "").trim();
				SubTotalSum = SubTotalSum+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
				String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(TotalAmount.get(i)));
				asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge,"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
			}
			String ExpectedSubTotalInBillingPge = String.format("%.2f",SubTotalSum);
			String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
			asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

			String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
			String ExpectedTaxInBillingPge = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_ODTax;
			asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");

			String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
			ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
			asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");
		}

		// Step 16: Click on the Amount
		String ActualAmountDueInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		// Step 17: Click on RTGS/NEFT
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("RTGS/NEFT"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment","iconReturnKey"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		// Step 18: Post invoice
		// Expected: Check final invoice details
		String PaymentDetails = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_PaymentDetails;
		Map<String, String> PdfDetails = pdfUtils.ValidateODPdf(
				PaymentDetails, 
				GrossWeight, 
				NetWeight, 
				GoldRate, 
				DiamondWeight, 
				DiamondRate, 
				TotalAmount, 
				ActualTotalAmountInBillingPge, 
				ActualAmountDueInBillingPge);
		String InvoiceNumber = PdfDetails.get("InvoiceNumber");
		String CustomerName = PdfDetails.get("CustomerName");

		//Expected :In ERP, 1. search RTGS payment details and 2.verify RTGS entry line updated
		erpUtils.RTGSPaymentDetails(CustomerName,ReceiveAmtFromInBillingPge);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Own diamond purchase from store transactions
	// Automation ID : TC54
	// Author: Jhoncy Joseph
	// </summary>
	public void TC54_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

		//Step 1: Login to POS	
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 50);

		// Step 2: Navigate to the Transaction page
		Thread.sleep(8000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		// Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC54_CustomerID, UtilityTestData.MenuBarOptions[0]);

		// Step 4: Select OD
		Thread.sleep(5000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.ODProduct);
		Thread.sleep(2000);			
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");

		// Step 5: Click on OD own
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldDiamondProduct));	

		// Step 6: Select configuration(purity 22K)		
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC54_Purity);

		// Step 7: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), UtilityTestData.TC54_ReceiptNo);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC54_LineNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));
		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC54_QcAndSmithPerson);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC54_QcAndSmithPerson); 
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(20000);
		double CurrentGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));

		//  click on purchase button
		Thread.sleep(2000);
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));

		// Step 8: Enter receipt number&click on fetch button
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), UtilityTestData.TC54_ReceiptNo);

		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC54_LineNumber);

		// Step 9: Select QC person
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));
		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC54_QcAndSmithPerson);

		// Step 10: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC54_QcAndSmithPerson); 

		// Step 11 : click on calculate button
		//Step 12.Click on cart button
		//Expected: Check Purchase rate own diamond and Check calculation
		//No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount 
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		List<String> ItemNamesInItemDetailsPge = new ArrayList<>();
		List<String> GrossWeightInItemDetailPge = new ArrayList<>();
		List<String> NetWeightInItemDetailPge = new ArrayList<>();
		List<String> GoldRateInItemDetailPge = new ArrayList<>();
		List<String> DiamondWeightInItemDetailPge = new ArrayList<>();
		List<String> DiamondRateInItemDetailPge = new ArrayList<>();
		List<String> TotalAmountInItemDetailPge = new ArrayList<>();
		String ActualTotalAmountInBillingPge ="";
		Thread.sleep(5000);

		double ActualGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		double ExpectedGoldRateInItemDetailsPge = (double) Math.round(CurrentGoldRateInItemDetailsPge*0.98);
		asrt.assertEquals(ExpectedGoldRateInItemDetailsPge, ActualGoldRateInItemDetailsPge,1.0,"OD Purchase rate mismatch: Expected value is "+ExpectedGoldRateInItemDetailsPge+" but got "+ActualGoldRateInItemDetailsPge+" in item details page");

		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));		
		double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
		double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
		String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
		double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);
		asrt.assertEquals(ExpectedGoldAmountInItemDetailsPge, ActualGoldAmountInItemDetailsPge,1.0,"Gold amount mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in item details page");		

		String ExpectedDiamondWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"));
		String ExpectedDiamondRateInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"));
		double ExpectedDiamondAmountInItemDetailsPge = Math.round(Double.parseDouble(ExpectedDiamondWeightInItemDetailsPge)*Double.parseDouble(ExpectedDiamondRateInItemDetailsPge)*100.0)/100.0;
		String DiamondAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdamt"));
		double ActualDiamondAmountInItemDetailsPge = Double.parseDouble(DiamondAmountInItemDetailsPge);
		asrt.assertEquals(ExpectedDiamondAmountInItemDetailsPge, ActualDiamondAmountInItemDetailsPge,1.0, "Diamond amount mismatch: Expected is "+ExpectedDiamondAmountInItemDetailsPge+" but got "+ActualDiamondAmountInItemDetailsPge+" in item details page");

		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
		double ActualTotalAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
		double ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPge+ActualDiamondAmountInItemDetailsPge;
		asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

		String ItemNameForOD = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = ItemNameForOD.substring(ItemNameForOD.indexOf("-") + 2);

		ItemNamesInItemDetailsPge.add(ExpectedItemNameInItemDetailsPge);
		GrossWeightInItemDetailPge.add(ExpectedGrossWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
		NetWeightInItemDetailPge.add(ExpectedNetWeightInItemDetailsPge);
		GoldRateInItemDetailPge.add(Double.toString(ActualGoldRateInItemDetailsPge));
		DiamondWeightInItemDetailPge.add(ExpectedDiamondWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
		DiamondRateInItemDetailPge.add(ExpectedDiamondRateInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
		TotalAmountInItemDetailPge.add(TotalAmountInItemDetailsPge);

		if (base.isExists(NormalSaleGoldandSilverObj.Ele_Payement("Error"))) {
			base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
		};
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		double SubTotalSumInBillingPge = 0.00;
		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		for(int i =0; i < AllProductRows.size() ;i++) {

			//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
			int ExpectedLinesCountInBillingPge = AllProductRows.size();
			int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));
			asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in billing page");

			List<WebElement> ItemName = base.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
			String ActualItemNameInBillingPge = ItemName.get(i).getText();
			String ExpectedItemNameInBillingPge = ItemNamesInItemDetailsPge.get(i);
			asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");

			List<WebElement> Quantity = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
			DecimalFormat Df = new DecimalFormat("0.00");
			String ActualQuantityInBillingPge = Quantity.get(i).getText().replace("-", "");
			String ExpectedQuantityInBillingPge = GrossWeightInItemDetailPge.get(i);
			String FormattedExpected = Df.format(Double.parseDouble(ExpectedQuantityInBillingPge));
			String FormattedActual = Df.format(Double.parseDouble(ActualQuantityInBillingPge));
			asrt.assertEquals(FormattedExpected, FormattedActual,"Quantity mismatch: Expected is "+FormattedExpected+" but got "+FormattedActual+" in billing page");

			List<WebElement> TotalWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));
			String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replace("-", "").replace("\u20B9", "").replace(",", "").trim();
			SubTotalSumInBillingPge = SubTotalSumInBillingPge+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
			String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(TotalAmountInItemDetailPge.get(i)));
			asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge,"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
		}
		String ExpectedSubTotalInBillingPge = String.format("%.2f",SubTotalSumInBillingPge);
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_ODTax;
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");

		String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
		ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");

		//Step 13  : Click on the Amount
		//Step 14  : Select a Payment method as convert advance
		//Step 15  : Select deposit type as normal
		//Step 16  : Enter deposit amount
		//Step 17  : Select sales person
		//Step 18  : Select the due date
		//Step 19  : Enter remarks
		//Step 20  : Click on nominee details
		//Step 20.1  : Enter nominee name
		//Step 20.2  : Select nominee relation
		//Step 20.3  : Click on check box(Same as customer address)
		//Step 20.4  : Click on OK button
		//Step 21    : Click on Deposit button
		//Step 22    : Post the Invoice
		//Expected Result : Check ADVANCE RECEIPT VOUCHER details*Towards the advance against purchase of approximate value*Fixed Gold rate*Deposit Type*Advance Received* save receipt id for future reference      
		appUtils.PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
				UtilityTestData.SalePersonNumber,
				UtilityTestData.SalePersonName,
				UtilityTestData.DueYear,
				UtilityTestData.NomineeName,
				UtilityTestData.NomineeRelation);
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_NormalAdvInvoice("win-itembox","win-item","DOCUMENT NORMAL ADV. INVOICE"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("btnblue","PRINT"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		Map<String, String> Result = pdfUtils.NormalAdvancePdfValidation();	
		String AdvanceReceived     = Result.get("AdvanceReceived");
		String FixedGoldRate       = Result.get("FixedGoldRate");
		String MaxGoldRate         = Result.get("MaxGoldRate");
		String ApproxWeight        = Result.get("ApproxWeight");
		String DepositType         = Result.get("DepositType");
		double PdfAdvanceReceived  = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
		double PdfFixedGoldRate    = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
		double PdfMaxGoldRate      = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(PdfMaxGoldRate, PdfFixedGoldRate,1.0, "Mismatch in Fixed Gold Rate actual "+PdfMaxGoldRate+" and Expected "+PdfFixedGoldRate+" Max Gold Rate in Normal Advance PDF");
		asrt.assertEquals(UtilityTestData.DepositType, DepositType, "Mismatch in Deposit Type Actual "+UtilityTestData.DepositType+" and Expected "+DepositType+ " in Normal Advance PDF");

		Double WeightValue          = PdfAdvanceReceived/PdfMaxGoldRate;
		BigDecimal CalcWeightValue  = new BigDecimal(WeightValue).setScale(3, RoundingMode.HALF_UP);
		double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());

		asrt.assertEquals(ApproxWeightPdfValue, CalcWeightValue.doubleValue(),1.0, "Mismatch in Approx Weight Value Actual "+ApproxWeightPdfValue+" and Expected "+CalcWeightValue.doubleValue()+" Approx Weight in Normal Advance PDF");
		Thread.sleep(5000);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	//<summary>
	// Test Case Title : Multiple OD in exchange with multiple category item
	// Automation ID : TC62
	// Author: Sangeetha M S
	// </summary>
	public void TC62_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception 
	{
		//Step 1.Login to POS
		Thread.sleep(5000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2. Click on Transaction button
		Thread.sleep(3000);
		appUtils.HamBurgerButtonClick("iconShop");

		//Step 3. Select customer and click on add to estimation button
		Thread.sleep(2000);
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_CustomerNo, OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_Option);

		//Step 4. Scan a SKU (Gold,diamond,precia)and click add to cart button		
		Map<String, String> SkuDetails = new LinkedHashMap<>();	
		List<String> ExpectedItemNames = new ArrayList<>();
		List<String> TotalForEachItems = new ArrayList<>();
		List<String> ItemQuantity=new ArrayList<>();
		List<String> PurityOfItems=new ArrayList();
		List<Double> AllRates = new ArrayList<>();

		SKUResult SkuGoldSaleItem = appUtils.GetOGSaleSku(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_GoldTransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_GoldFromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_GoldMetalType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_GoldSaleQuantity,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_SkuCount);
		List<String> GoldSkuList = SkuGoldSaleItem.PcsRateSKU;
		System.out.println("Gold Selected SKU: " + GoldSkuList);

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		for (int i = 0;i < GoldSkuList.size();i++)
		{
			String CurrentGoldSku = GoldSkuList.get(i);
			appUtils.SKUIngridentTableValues(CurrentGoldSku.toString(), (i+1), SkuDetails);
			ExpectedItemNames.add(SkuDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItems.add(SkuDetails.get("SKU_" + (i+1) + "_Total"));
			ItemQuantity.add(SkuDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			PurityOfItems.add(SkuDetails.get("SKU_" + (i+1) + "_Purity"));
			for (int ItemIndex = 1; ; ItemIndex++) 
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
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		//For conversion of Rate
		String ItemPurity = PurityOfItems.get(0);
		double ExchangeRate =  AllRates.get(0);
		double ExpectedGoldRate22k=0;
		double ExpectedGoldRate18k=0;
		if (ItemPurity.equalsIgnoreCase("18k")) 
		{
			double ConvertedGoldRate = (ExchangeRate * 22) / 18;
			ExpectedGoldRate22k = Math.round(ConvertedGoldRate);
			ExpectedGoldRate18k=ExchangeRate;
		} 
		else 
		{
			ExpectedGoldRate22k = Math.round(ExchangeRate);
			ExpectedGoldRate18k= (ExchangeRate * 18) / 22;
		}

		Map<String, String> SkuTransactionPageItems = appUtils.TransactionSKUsLinesVerify(GoldSkuList, SkuDetails);
		String SkuTotalGrossWeight = SkuTransactionPageItems.get("GrossWeight");
		String SkuSubTotalValue = SkuTransactionPageItems.get("Subtotal");
		String SkuLinesCount=SkuTransactionPageItems.get("LinesCount");
		String SkuInvoiceTotal = SkuTransactionPageItems.get("TotalAmount");
		String SkuNetTotal = SkuTransactionPageItems.get("NetTotal");
		mathUtils.ValidateTransactionLineCalculation(SkuTransactionPageItems, SkuDetails);

		//Step 5.Search OD
		//Step 6.Click on OD own
		//Step 7.Select configuration(purity 22K)
		//Step 8.Click on Add item button
		// Note: Click on Exchange button
		//Step 9.Select QC person
		//Step 10.Select Smith person
		//Step 11.Enter piece value
		//Step 12.Enter gross weight
		//Step 13.Enter Diamond pieces,Weight and rate
		//Step 14.Enter Stone pieces, Weight and rate.
		//Step 15.Click on calculate button
		//Step 16.Click on Add to cart button		
		//Note: Continue steps 5-16 for Purity 18K
		List<String> Purities = Arrays.asList("22k","18k");
		List<String> ItemNamesInItemDetailsPge = new ArrayList<>();
		List<String> GrossWeight = new ArrayList<>();
		List<String> NetWeight = new ArrayList<>();
		List<String> GoldRate = new ArrayList<>();
		List<String> DiamondWeight = new ArrayList<>();
		List<String> DiamondRate = new ArrayList<>();
		List<String> StoneWeight = new ArrayList<>();
		List<String> StoneRate = new ArrayList<>();
		List<String> AmountTotal = new ArrayList<>();
		List<Double> ODItemTotalList = new ArrayList<>();
		List<String> ExpectedSumOfODTotal=new ArrayList<>();

		String ActualTotalAmountInBillingPge ="";
		String ExpectedItemNameInItemDetailsPge="";
		String ExpectedNetWeightInItemDetailsPge="";
		String TotalAmountInItemDetailsPge="";
		String ExpectedGrossWeightInItemDetailsPge="";
		String ODTotalWeight="";
		String NetTotal="";
		String SubTotal="";
		String Discount="";
		String TotalGrossWeight="";
		String TotalNetWeight="";
		String Gst="";
		String InvoiceTotal="";
		String LinesCount="";

		double ExpectedTotalAmountInItemDetailsPge=0.00;
		double TotalWithoutTaxSum = 0.0;
		double ODTotal = 0.0;
		double TaxableAmount=0.0;
		double TotalWithoutTax = 0.0;		
		double  SumOfODTotal=0.00;
		double ActualGoldRateInItemDetailsPge =0.00;

		for(String Purity : Purities) 
		{
			Thread.sleep(3000);
			base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_Product);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldDiamondProduct));	

			base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),Purity);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

			Thread.sleep(1000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_QcAndSmithPerson);

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_Pieces);
			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_GrossWeight);
			ODTotalWeight=base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdpcs"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdpcs"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_DiamondPieces);
			List<WebElement> ErrorMessage = driver.findElements(NormalSaleGoldandSilverObj.Btn_AddtoCart("messageText h5"));
			if (!ErrorMessage.isEmpty()) 
			{
				base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20"));
			}	

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_DiamondGrossWeight);			
			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_DiamondRate);

			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonepcs"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonepcs"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_StonePieces);
			List<WebElement> StoneErrorMessage = driver.findElements(NormalSaleGoldandSilverObj.Btn_AddtoCart("messageText h5"));
			if (!StoneErrorMessage.isEmpty()) 
			{
				base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20"));
			}	
			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonewt"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonewt"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_StoneWeight);
			base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonerate"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonerate"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_StoneRate);

			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(4000);

			ActualGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
			if (Purity.equalsIgnoreCase("18k")) 
			{
				asrt.assertEquals(ExpectedGoldRate18k, Math.round(ActualGoldRateInItemDetailsPge),1,"Exchange rate (Board rate)18k mismatch: Expected exchange rate is " + ExpectedGoldRate18k + " but got " + Math.round(ActualGoldRateInItemDetailsPge) +" in OD item details page");
			} 
			else 
			{
				asrt.assertEquals(ExpectedGoldRate22k, Math.round(ActualGoldRateInItemDetailsPge),1,"Exchange rate (Board rate)22k mismatch: Expected exchange rate is " +ExpectedGoldRate22k + " but got " + Math.round(ActualGoldRateInItemDetailsPge) +" in OD item details page");
			}

			ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));		
			double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
			double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
			String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
			double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);
			asrt.assertEquals(ExpectedGoldAmountInItemDetailsPge, ActualGoldAmountInItemDetailsPge,1.0,"GoldAmount mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in OD item details page");		

			String ExpectedDiamondWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"));
			String ExpectedDiamondRateInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"));
			double ExpectedDiamondAmountInItemDetailsPge = Math.round(Double.parseDouble(ExpectedDiamondWeightInItemDetailsPge)*Double.parseDouble(ExpectedDiamondRateInItemDetailsPge)*100.0)/100.0;
			String DiamondAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdamt"));
			double ActualDiamondAmountInItemDetailsPge = Double.parseDouble(DiamondAmountInItemDetailsPge);

			asrt.assertEquals(ExpectedDiamondAmountInItemDetailsPge, ActualDiamondAmountInItemDetailsPge,1.0, "Diamond amount mismatch: Expected is "+ExpectedDiamondAmountInItemDetailsPge+" but got "+ActualDiamondAmountInItemDetailsPge+" in OD item details page");

			String ExpectedStoneWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpstonewt"));
			String ExpectedStoneRateInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpstonerate"));
			double ExpectedStoneAmountInItemDetailsPge = Math.round(Double.parseDouble(ExpectedStoneWeightInItemDetailsPge)*Double.parseDouble(ExpectedStoneRateInItemDetailsPge)*100.0)/100.0;
			String StoneAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpstoneamt"));
			double ActualStoneAmountInItemDetailsPge = Double.parseDouble(StoneAmountInItemDetailsPge);

			asrt.assertEquals(ExpectedStoneAmountInItemDetailsPge, ActualStoneAmountInItemDetailsPge,1.0, "Stone amount mismatch: Expected is "+ExpectedStoneAmountInItemDetailsPge+" but got "+ActualStoneAmountInItemDetailsPge+" in OD item details page");

			ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
			double ActualTotalAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
			ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPge+ActualDiamondAmountInItemDetailsPge+ActualStoneAmountInItemDetailsPge;

			asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in OD item details page");

			String ODItemName = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
			ExpectedItemNameInItemDetailsPge = ODItemName.substring(ODItemName.indexOf("-") + 2);
			double ODTotalAmount = Double.parseDouble(TotalAmountInItemDetailsPge);
			ODItemTotalList.add(ODTotalAmount); 

			ItemNamesInItemDetailsPge.add(ExpectedItemNameInItemDetailsPge);
			GrossWeight.add(ExpectedGrossWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			NetWeight.add(ExpectedNetWeightInItemDetailsPge);
			GoldRate.add(Double.toString(ActualGoldRateInItemDetailsPge));
			DiamondWeight.add(ExpectedDiamondWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			DiamondRate.add(ExpectedDiamondRateInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			StoneWeight.add(ExpectedStoneWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			StoneRate.add(ExpectedStoneRateInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
			AmountTotal.add(TotalAmountInItemDetailsPge);

			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
			base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));

			List<String> CurrentSkuList = Optional.ofNullable(GoldSkuList).orElseGet(ArrayList::new);
			Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(CurrentSkuList, SkuDetails);
			SubTotal = TransactionPageItems.get("Subtotal");
			Discount = TransactionPageItems.get("Discount");
			TotalGrossWeight = TransactionPageItems.get("GrossWeight");
			TotalNetWeight = TransactionPageItems.get("NetWeight");
			Gst = TransactionPageItems.get("Tax");
			InvoiceTotal = TransactionPageItems.get("NetTotal");
			NetTotal = TransactionPageItems.get("NetTotal");
			LinesCount = TransactionPageItems.get("LinesCount");

			//OD Item name
			String ODItemNameInBillingPge=null;
			List<WebElement> ListOfItemNamesInBillingPge = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));		
			boolean ODItemFound = false;
			for (WebElement Item : ListOfItemNamesInBillingPge)
			{
				String ItemNameFromBillingPage = Item.getText().trim();	    
				if (ItemNameFromBillingPage.equalsIgnoreCase("OD")) 
				{
					ODItemFound = true;
					ODItemNameInBillingPge = ItemNameFromBillingPage;

					asrt.assertEquals(ODItemNameInBillingPge, ExpectedItemNameInItemDetailsPge,"OD Item Name mismatch: Expected '" + ExpectedItemNameInItemDetailsPge + "' but got '" + ODItemNameInBillingPge + "' in Billing Page");
					break;
				}
			}
			if (!ODItemFound)
			{
				System.out.println("OD item not found in the Billing Page item list.");
			}

			//OD Quantity
			List<WebElement> BillingScreenItemQty=base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
			for (int i = 0; i < ListOfItemNamesInBillingPge.size(); i++) 
			{
				String ItemNameInBilling = ListOfItemNamesInBillingPge.get(i).getText().trim();
				if (ItemNameInBilling.equalsIgnoreCase(ExpectedItemNameInItemDetailsPge))
				{
					String DisplayedItemQty = BillingScreenItemQty.get(i).getText().trim().replaceAll("[^\\d.]", "");
					double DisplayedQty = Double.parseDouble(DisplayedItemQty);
					double ExpectedQty = Double.parseDouble(ODTotalWeight);

					asrt.assertEquals(DisplayedQty, ExpectedQty,"OD Item Quantity mismatch: Expected value is " + ExpectedQty +" but got " + DisplayedQty + " in Billing page");
					break; 
				}
			}

			//OD Total amount without tax
			double ODItemDetailsPageTotal = 0.0;
			int ODIndex = 0;
			List<WebElement> TotalsWithoutTax = base.GetElement( NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
			for (int j = 0; j < TotalsWithoutTax.size(); j++) 
			{
				String ItemNameInBillingPage = ListOfItemNamesInBillingPge.get(j).getText().trim();
				String DisplayedTotalStr = TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", "");
				if (ItemNameInBillingPage.contains("OD")) 
				{
					double DisplayedTotal = Double.parseDouble(DisplayedTotalStr);
					if (ODIndex < ODItemTotalList.size()) 
					{
						double ExpectedTotal = ODItemTotalList.get(ODIndex);
						SumOfODTotal+=ExpectedTotal;
						ExpectedSumOfODTotal.add(String.valueOf(SumOfODTotal)); 
						asrt.assertEquals( DisplayedTotal, ExpectedTotal,1.0, "OD item Total(Without Tax) mismatch at index " + ODIndex +": Expected = " + ExpectedTotal + ", Actual = " + DisplayedTotal + "in Billing page" );
					} 
					else 
					{
						System.out.println("More OD items found in Billing than in OD Page. Index: " + ODIndex);
					}
					ODIndex++;
				}
			}	
		}

		//Subtotal
		List<WebElement> ItemTotalsWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemNamesInBillingPge = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));		
		for (int j = 0; j < ItemNamesInBillingPge.size(); j++) 
		{
			String ItemNameInBilling = ItemNamesInBillingPge.get(j).getText().trim(); 
			String SubTotalInBillingPage = ItemTotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""); 
			if (!SubTotalInBillingPage.isEmpty())
			{
				double CalcSubTotal = Double.parseDouble(SubTotalInBillingPage);
				if (ItemNameInBilling.contains("OD") )
				{
					ODTotal += CalcSubTotal;
					TotalWithoutTaxSum -= CalcSubTotal;
				} 
				else
				{					
					TotalWithoutTaxSum += CalcSubTotal;
					TaxableAmount=CalcSubTotal;
				}
			}
		}
		TotalWithoutTaxSum=((int)(Math.abs(TotalWithoutTaxSum) * 100)) / 100.0;
		TotalWithoutTax =  Math.round(Math.abs(TotalWithoutTaxSum) * 100.0) / 100.0;
		String SubTotalBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"));
		double ExpectedSubTotalBillingPge = Double.parseDouble(SubTotalBillingPge.trim().replaceAll("[^\\d.]", ""));

		asrt.assertEquals(TotalWithoutTax, ExpectedSubTotalBillingPge,1,"SubTotal mismatch : Expected value is " + ExpectedSubTotalBillingPge + " but got " + TotalWithoutTax + " in Billing page");

		//TAX
		double TaxAmountBillingPge=TaxableAmount*3/100;
		String TaxInBillingPage = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4"));
		double ExpectedTaxBillingPge=Double.parseDouble(TaxInBillingPage.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(ExpectedTaxBillingPge,TaxAmountBillingPge ,1, "Tax Amount mismatch : Expected value is "+ExpectedTaxBillingPge+"but got "+TaxAmountBillingPge+" in Billing page");

		//Total Amount
		String TotalAmountInBillingPage = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4"));
		double ExpectedTotalAmount=Double.parseDouble(TotalAmountInBillingPage.trim().replaceAll("[^\\d.]", ""));		
		double CalcTotalAmount=ExpectedSubTotalBillingPge+ExpectedTaxBillingPge;
		asrt.assertEquals(ExpectedTotalAmount, CalcTotalAmount, 1,"Total Amount mismatch : Expected value is "+ExpectedTotalAmount+" but got "+CalcTotalAmount+" in Billing page");		

		//Step 17. Save to estimate
		//Expected : Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		String AmountInBillingPage = TotalAmountInBillingPage.replaceAll("[^\\d.-]", "");
		double ODAmount = Double.parseDouble(AmountInBillingPage);
		InvoiceTotal = String.format("%.2f", Math.abs(ODAmount));
		System.out.println("InvoiceTotal"+InvoiceTotal);

		String TaxableValue = Double.toString(TaxableAmount);
		String TotalAmnt = SubTotal;
		Gst = TaxInBillingPage;
		NetTotal=String.format("%.2f", Math.abs(ODAmount));
		String ProformaPdfName = pdfUtils.DownloadAndRenameLatestPDF("TC62_ProformaInvoice");
		String ProFormaInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+ProformaPdfName+"";
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				TotalAmnt
				);

		//Step 18. Recall estimate from cashier		
		//Expected : Verify whether the recalled item is the same as the item in the cart. Note:Cashier Screen after recall
		// *No of Product lines,*Displayed Item Name, *Displayed Quantity *Displayed TOTAL (without Tax)*Displayed Subtotal *TAX*Total Amount
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Validation
		int ItemCount = 0;
		if (LinesCount != null && !LinesCount.trim().isEmpty()) 
		{
			ItemCount = Integer.parseInt(LinesCount.trim());
		}
		String BillingPageSubTotal=  SubTotalBillingPge.replace("\u20B9", "").replace(",", "").trim();
		double SubTotalAmount=Double.parseDouble(BillingPageSubTotal);
		appUtils.ValidateRecallPageOGItem(ExpectedItemNames,ItemQuantity,ExpectedItemNameInItemDetailsPge,ExpectedGrossWeightInItemDetailsPge,SubTotalAmount,ExpectedTaxBillingPge,ExpectedTotalAmount,ItemCount,"OGT");

		//Step 19. Select transaction type as Exchange
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_TransactionTypeOD);

		//Step 20. Click on process estimation button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		//Step 21. Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Step 22. Click on amount 0.00
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) 
		{
			try 
			{ 
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} 
			catch (Exception e) 
			{
			}
		} 

		//Step 23. Post exchange invoice
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String PaymentDetails = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_PaymentDetails;
		String ODItemTotal = String.format("%.2f", ODTotal);
		Map<String, String> PdfDetails = pdfUtils.ValidateODPdf(
				PaymentDetails, 
				GrossWeight, 
				NetWeight, 
				GoldRate, 
				DiamondWeight, 
				DiamondRate, 
				AmountTotal,
				ODItemTotal, 
				ODItemTotal);
		String ExchngeAdjustment =PdfDetails.get("PaymentDetails");
		String InvoiceNumber = PdfDetails.get("InvoiceNumber");
		String CustomerName = PdfDetails.get("CustomerName");

		//Step 24. Again recall estimate from cashier screen
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		//Step 25. Select transaction type as sales
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));	
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_TransactionType);

		//Step 26. Click on process estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  

		//Step 27. Choose a sales representative
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));		

		//Step 28. Click on amount
		//Step 29. Select payment method(cash/card)
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		base.buttonClick(LoginPageObj.Edt_AlertText("HDFC (Credit Card)"));
		String PaymentAmountHDFC=base.GetAttribte(NormalSaleGoldandSilverObj.Edt_ApprCode("numpad-control-input primaryFontColor numpad-control-input-readonly"),"value");
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CartMoney("flexGrow50 flexRow row-enter","h1 marginBottom0 iconReturnKey button-content-return"));
		base.setData(NormalSaleGoldandSilverObj.Edt_ApprCode("textInputArea"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_ApprCode);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("OK"));
		base.setData(NormalSaleGoldandSilverObj.Edt_ApprCode("textInputArea"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_CardNo);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("OK"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Close"));

		//Step 30. Post exchange invoice
		//Expected Results :  Check final invoice details
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC62_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";

		String GrossAmount=SkuSubTotalValue;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String Adjustment=ExchngeAdjustment;
		String TotalDiamond= "";
		String PaymentHDFCCard = PaymentAmountHDFC;
		String SalesPdfPath = SaleInvoicePath;
		TotalNetWeight =SkuTotalGrossWeight;
		TotalGrossWeight= SkuTotalGrossWeight;
		String NetValue = SkuNetTotal;
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWeight,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				GoldSkuList,
				SkuDetails);				
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Own diamond exchange from store transactions
	// Automation ID : TC53
	// Author: Nivya Ramesan
	// </summary>
	public void TC53_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Click on Transaction button
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		// Step 3: Select customer and click on add to estimation button
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_CustomerID, UtilityTestData.MenuBarOptions[5]);

		// Step 4: Scan multiple SKU
		Map<String, String> ScannedSkuData  = new LinkedHashMap<>();
		List<String> AllSkuList             = new ArrayList<>();
		Map<String, String> SkuDetails      = new LinkedHashMap<>();
		List<String> ExpectedItemNames      = new ArrayList<>();
		List<String> TotalForEachItems      = new ArrayList<>();
		List<String> ItemQuantity           = new ArrayList<>();
		List<String> PurityOfItems          = new ArrayList();
		List<Double> AllRates               = new ArrayList<>();

		String ODItemNameInBillingPge=null;
		int i=0;
		int k=0;
		double TotalWithoutTax = 0.0;
		List<String> GoldSkuList = appUtils.SelectMultipleSKUs(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_GoldSkuCount,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_GoldTransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_GoldFromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_GoldMetalType
				);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		/*List<String> DiamondSkuList = appUtils.SelectMultipleSKUs(
							OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_DiamondSkuCount,
							OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_DiamondTransferType,
							OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_DiamondFromCounter,
							OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_DiamondMetalType
							);

			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));*/		

		List<String> UncutSkuList = appUtils.SelectMultipleSKUs(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_UncutSkuCount,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_UncutTransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_UncutFromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_UncutMetalType
				);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		List<String> PreciaSkuList = appUtils.SelectMultipleSKUs(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_PreciaSkuCount,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_PreciaTransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_PreciaFromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_PreciaMetalType
				);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		

		AllSkuList.addAll(GoldSkuList);
		/*AllSkuList.addAll(DiamondSkuList);*/
		AllSkuList.addAll(UncutSkuList);
		AllSkuList.addAll(PreciaSkuList);

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));		

		for (i = 0; i < AllSkuList.size(); i++)
		{
			String  CurrentSkuList = AllSkuList.get(i);
			int SkuCount = i + 1;
			appUtils.SKUIngridentTableValues(CurrentSkuList.toString(),SkuCount, SkuDetails);
			ExpectedItemNames.add(SkuDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItems.add(SkuDetails.get("SKU_" + (i+1) + "_Total"));
			ItemQuantity.add(SkuDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			PurityOfItems.add(SkuDetails.get("SKU_" + (i+1) + "_Purity"));
			for (int ItemIndex = 1; ; ItemIndex++) 
			{
				String Rate = "SKU" + SkuCount + "_ITEM" + ItemIndex + "_RATE";
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
			SkuDetails.put("SKU_" + SkuCount + "_AllRates", AllRates.toString());
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, SkuCount);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuCount);
		}

		//For scanned Sku list
		Map<String, String> SkuTransactionPageItems = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);
		String SkuNetTotal = SkuTransactionPageItems.get("NetTotal");
		mathUtils.ValidateTransactionLineCalculation(SkuTransactionPageItems, SkuDetails);

		//Step 5 : Select OD
		//Step 6 : Click on OD own
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),"OD");
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldDiamondProduct));	

		//Step 7: Select configuration(purity 22k)
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_Purity);

		//Step 8: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		//Step 9.Select exchange then Enter receipt number 
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"));
		Thread.sleep(3000);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_ReceiptNumber);
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_LineNumber );

		//Step 10. Click on fetch button
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));

		//Step 11. Select QC person
		//Check Exchange rate own diamond
		//Step 12. Select Smith person
		//Step 13. click on calculate button
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_QCPerson);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_SmithPerson);
		Thread.sleep(3000);
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

		//Check Exchange rate own diamond(Validation)
		Thread.sleep(3000);
		double ActualODRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		String Purity = PurityOfItems.get(0);
		double ExchangeRate =  AllRates.get(0);
		long RoundedExpectedODRate=0;
		if (Purity.equalsIgnoreCase("18k")) 
		{
			double ExpectedODRate = (ExchangeRate * 22) / 18;
			RoundedExpectedODRate = Math.round(ExpectedODRate);
			asrt.assertEquals(RoundedExpectedODRate, Math.round(ActualODRateInItemDetailsPge),1,"Exchange rate (Board rate) mismatch: Expected exchange rate is " + RoundedExpectedODRate + " but got " + Math.round(ActualODRateInItemDetailsPge) +" in OD item details page");
		} 
		else 
		{
			RoundedExpectedODRate = Math.round(ExchangeRate);
			asrt.assertEquals(RoundedExpectedODRate, Math.round(ActualODRateInItemDetailsPge),1,"Exchange rate (Board rate) mismatch: Expected exchange rate is " +RoundedExpectedODRate + " but got " + Math.round(ActualODRateInItemDetailsPge) +" in OD item details page");
		}
		//Check Displayed Values
		double NetWeightInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt")));
		double ExpectedAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualODRateInItemDetailsPge * 100.0) / 100.0;	
		double ActualAmountInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));			
		asrt.assertEquals(ExpectedAmountInItemDetailsPge, ActualAmountInItemDetailsPge,"Amount mismatch: Expected amount is "+ExpectedAmountInItemDetailsPge+" but got "+ActualAmountInItemDetailsPge+" in OD item details page");		

		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		double ExpectedGrossWeightItemDetailsPage=Double.parseDouble(ExpectedGrossWeightInItemDetailsPge);
		String ODTotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));	
		String ODItemName = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInOgItemDetailsPge = ODItemName.substring(ODItemName.indexOf("-") + 2);

		List<String> GrossWeight     = new ArrayList<>();
		List<String> NetWeight       = new ArrayList<>();
		List<String> GoldRate        = new ArrayList<>();
		List<String> DiamondWeight   = new ArrayList<>();
		List<String> DiamondRate     = new ArrayList<>();
		List<String> AmountTotal     = new ArrayList<>();
		List<Double> ODItemTotalList = new ArrayList<>();
		List<String>  ExpectedSumOfODTotal=new ArrayList<>();

		//Step 14. Click on add to cart button
		//Expected result: Check calculation Note:Billling Screen , *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount	
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));

		List<String> CurrentSkuList = Optional.ofNullable(AllSkuList).orElseGet(ArrayList::new);
		Map<String, String> TransactionPageItems = appUtils.TransactionSKUsLinesVerify(CurrentSkuList, SkuDetails);
		String SubTotal = TransactionPageItems.get("Subtotal");
		String TotalGrossWeight = TransactionPageItems.get("GrossWeight");
		String TotalNetWeight = TransactionPageItems.get("NetWeight");
		String Gst = TransactionPageItems.get("Tax");
		String InvoiceTotal = TransactionPageItems.get("NetTotal");
		String NetTotal = TransactionPageItems.get("NetTotal");
		String LinesCount = TransactionPageItems.get("LinesCount");

		//OD Item name
		List<WebElement> ListOfItemNamesInBillingPge = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));		
		boolean ODItemFound = false;
		for (WebElement Item : ListOfItemNamesInBillingPge)
		{
			String ItemNameFromBillingPage = Item.getText().trim();	    
			if (ItemNameFromBillingPage.equalsIgnoreCase("OD")) 
			{
				ODItemFound = true;
				ODItemNameInBillingPge = ItemNameFromBillingPage;
				Assert.assertEquals(ODItemNameInBillingPge, ExpectedItemNameInOgItemDetailsPge,"OD Item Name mismatch: Expected '" + ExpectedItemNameInOgItemDetailsPge + "' but got '" + ODItemNameInBillingPge + "' in Billing Page");
				break;
			}
		}
		if (!ODItemFound)
		{
			System.out.println("OD item not found in the Billing Page item list.");
		}

		//OD Quantity
		List<WebElement> BillingScreenItemQty=base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		int TotalLinesBillingPge = ListOfItemNamesInBillingPge.size();
		for (i = 0; i < ListOfItemNamesInBillingPge.size(); i++) 
		{
			String itemNameInBilling = ListOfItemNamesInBillingPge.get(i).getText().trim();
			if (itemNameInBilling.equalsIgnoreCase(ExpectedItemNameInOgItemDetailsPge))
			{
				String DisplayedItemQty = BillingScreenItemQty.get(i).getText().trim().replaceAll("[^\\d.]", "");
				double DisplayedQty = Double.parseDouble(DisplayedItemQty);
				System.out.println(DisplayedQty);
				asrt.assertEquals(DisplayedQty, ExpectedGrossWeightItemDetailsPage,1,"OD Item Quantity mismatch: Expected value is " + ExpectedGrossWeightInItemDetailsPge +" but got " + DisplayedQty + " in Billing page");
				break; 
			}
		}

		//OD Total amount without tax
		double ODItemDetailsPageTotal=0.0;
		double DisplayedTotal =0.0;
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		for (int j = 0; j < TotalsWithoutTax.size(); j++)
		{
			String ItemNameInBilling = ListOfItemNamesInBillingPge.get(j).getText().trim();
			String DisplayedTotalStr = TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", "");
			DisplayedTotal = Double.parseDouble(DisplayedTotalStr);
			ODItemDetailsPageTotal=Double.parseDouble(ODTotalAmountInItemDetailsPge);
			if (ItemNameInBilling.equalsIgnoreCase(ExpectedItemNameInOgItemDetailsPge))
			{
				asrt.assertEquals(DisplayedTotal, ODItemDetailsPageTotal, 1, "OD item Total(Without Tax) mismatch: Expected " + ODItemDetailsPageTotal + " but got " + DisplayedTotal + " in Billing screen");
			}
		}

		//Subtotal
		double TotalWithoutTaxSum = 0.0;
		double ODTotal = 0.0;
		double TaxableAmount=0.0;
		for (int j = 0; j < TotalsWithoutTax.size(); j++) 
		{
			String ItemNameInBilling = ListOfItemNamesInBillingPge.get(j).getText().trim(); 
			String SubTotalStr = TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""); 

			if (!SubTotalStr.isEmpty())
			{
				double CalcSubTotal = Double.parseDouble(SubTotalStr);
				if (!ItemNameInBilling.equalsIgnoreCase(ExpectedItemNameInOgItemDetailsPge)) 
				{
					TotalWithoutTaxSum =TotalWithoutTaxSum+ CalcSubTotal;
					TaxableAmount=TotalWithoutTaxSum;			
				} 
				else
				{				
					TotalWithoutTaxSum = TotalWithoutTaxSum-CalcSubTotal;
					ODTotal=CalcSubTotal;
				}
			}
		}

		TotalWithoutTax = Math.round(TotalWithoutTaxSum * 100.0) / 100.0;
		String SubTotalBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"));
		double ExpectedSubTotalBillingPge = Double.parseDouble(SubTotalBillingPge.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(TotalWithoutTax, ExpectedSubTotalBillingPge,"SubTotal mismatch : Expected value is " + ExpectedSubTotalBillingPge + " but got " + TotalWithoutTax + " in Billing page");

		//TAX
		double TaxAmountBillingPge=TaxableAmount*3/100;
		String TaxBillingPage = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4"));
		double ExpectedTaxBillingPge=Double.parseDouble(TaxBillingPage.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(ExpectedTaxBillingPge,TaxAmountBillingPge ,1, "Tax Amount mismatch : Expected value is "+ExpectedTaxBillingPge+"but got "+TaxAmountBillingPge+" in Billing page");

		//Total Amount
		String TotalAmount = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4"));
		double ExpectedTotalAmount=Double.parseDouble(TotalAmount.trim().replaceAll("[^\\d.]", ""));
		double CalcTotalAmount=ExpectedTaxBillingPge+ExpectedSubTotalBillingPge;
		asrt.assertEquals(ExpectedTotalAmount, CalcTotalAmount, 1,"Total Amount mismatch : Expected value is "+ExpectedTotalAmount+" but got "+CalcTotalAmount+" in Billing page");

		//Step 15.In Billing, Click on "Save to Estimate" button
		//Step 16.Click on Save esimation
		//Expected Results :Verify the Invoice details	
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String EstmnNumber=base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));
		Thread.sleep(5000);

		String TaxableValue = Double.toString(TaxableAmount);
		String TotalAmnt = SubTotal;
		String CleanedAmount = TotalAmount.replaceAll("[^\\d.-]", "");
		double parsedAmount = Double.parseDouble(CleanedAmount);
		InvoiceTotal = String.valueOf(parsedAmount);
		Gst = TaxBillingPage;
		String ProformaPdfName = pdfUtils.DownloadAndRenameLatestPDF("TC53_ProformaInvoice");
		String ProFormaInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+ProformaPdfName+"";
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				TotalAmnt
				);

		//Step 17.Click on Cash and then click on Recall estimate
		//Step 18. Select the estimate and click on Recall estimation
		//Expected Results : Verify whether the recalled item is the same as the item in the cart.
		//No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Validation
		appUtils.ValidateRecallPageOGItem(ExpectedItemNames,ItemQuantity,ExpectedItemNameInOgItemDetailsPge,ExpectedGrossWeightInItemDetailsPge
				,ExpectedSubTotalBillingPge,ExpectedTaxBillingPge,ExpectedTotalAmount,TotalLinesBillingPge,"OD");

		//Step 19.Select transaction type as "Exchange" and click on Process estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_TransactionTypeOG);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		//Step 20.Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Step 21.Click on the Amount For printing Receipt
		//Expected :  Check Exchange invoice details
		String AmountDueForExchangeFromBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		double AmountDueForExchangeInFromBillingPge = Double.parseDouble(AmountDueForExchangeFromBillingPge);			
		String ExpectedAmountForExchangeInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();

		if (AmountDueForExchangeInFromBillingPge<0.00) {
			appUtils.PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
					UtilityTestData.SalePersonNumber,
					UtilityTestData.SalePersonName,
					UtilityTestData.DueYear,
					UtilityTestData.NomineeName,
					UtilityTestData.NomineeRelation);
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "NORMAL ADV. INVOICE"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
			Map<String, String> AdvanceReceiptVoucherDetails = pdfUtils.NormalAdvancePdfValidation();
			String ActualAdvanceReceived           = AdvanceReceiptVoucherDetails.get("AdvanceReceived");
			String ActualNetReceived               = AdvanceReceiptVoucherDetails.get("NetAdvance");
			String ActualPaymentMode               = AdvanceReceiptVoucherDetails.get("PaymentMode");
			String PaymentAmount                   = AdvanceReceiptVoucherDetails.get("PaymentAmount");
			String ApproxWeight                    = AdvanceReceiptVoucherDetails.get("ApproxWeight");
			String ActualFixedGoldRate             = AdvanceReceiptVoucherDetails.get("FixedGoldRate");
			String ActualDepositType               = AdvanceReceiptVoucherDetails.get("DepositType");

			asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, ActualAdvanceReceived,"Advance value mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+ActualAdvanceReceived+" in Advance receipt");
			asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, ActualNetReceived,"Net value mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+ActualNetReceived+" in Advance receipt");
			asrt.assertEquals(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_PaymentMode, ActualPaymentMode,"Payment Mode mismatch: Expected is "+OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_PaymentMode+" but found "+ActualPaymentMode+" in Advance receipt");
			asrt.assertEquals(ExpectedAmountForExchangeInBillingPge, PaymentAmount,"Payment Amount mismatch: Expected is "+ExpectedAmountForExchangeInBillingPge+" but found "+PaymentAmount+" in Advance receipt");
			base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		}
		else {

			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
			if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) 
			{
				try 
				{ 
					base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				} 
				catch (Exception e) 
				{
				}
			} 	

			//validation
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			String PaymentDetails = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_PaymentDetails;
			String ODItemTotal = String.valueOf(ODTotal);
			Map<String, String> PdfDetails = pdfUtils.ValidateODPdf(
					PaymentDetails,
					GrossWeight,
					NetWeight,
					GoldRate,
					DiamondWeight,
					DiamondRate,
					AmountTotal,
					ODItemTotal,
					ODItemTotal);

			String ExchngeAdjustment =PdfDetails.get("PaymentDetails");
			String InvoiceNumber = PdfDetails.get("InvoiceNumber");
			String CustomerName = PdfDetails.get("CustomerName");
		}

		//Step 22.In cash, click on "Recall Estimate"
		//Step 23.Select the estimate, Click on Recall Estimation
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		//Step 24.Select transaction type as "Sales" and click on Process estimation
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));	
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC53_TransactionType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		//Step 25.Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		appUtils.HandleSpecifyRateModalInRecall();

		Map<String, String> TransactionRecallDataLineRecall = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);
		String SubTotalRecall			 =   TransactionRecallDataLineRecall.get("Subtotal");
		String DiscountRecall 			 =   TransactionRecallDataLineRecall.get("Discount");
		String TotalGrossWeightRecall	 =   TransactionRecallDataLineRecall.get("GrossWeight");
		String TotalNetWeightRecall 	 =   TransactionRecallDataLineRecall.get("NetWeight");
		String TotalAmountRecall		 =   TransactionRecallDataLineRecall.get("TotalAmount");
		String NetTotalRecall			 =   TransactionRecallDataLineRecall.get("NetTotal");
		String LinesCountRecall			 =   TransactionRecallDataLineRecall.get("LinesCount");
		String AdjustmentReturn			 =   SkuTransactionPageItems.get("Payments");

		//Step 26.Click on the Amount For printing Receipt
		String Adjustment= base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("PaymentsField", "h4"));;;
		String PaymentAmount = appUtils.PaymentAfterRecallEstimate("HDFC");
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

		//Step 27.Post Invoice
		//Check final invoice details
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		Thread.sleep(3000);
		String FinalInvoice   		     = pdfUtils.DownloadAndRenameLatestPDF("TC53_SaleInvoice");
		Thread.sleep(2000);
		String SalesPdfPath  		     = System.getProperty("user.dir") + "\\Invoices\\" + FinalInvoice;
		String GrossAmount    		     = SubTotalRecall;
		String TotalDiscount  		     = DiscountRecall;
		String TotalQtyPcs    		     = LinesCountRecall;
		String TaxableValueSale		     = TotalAmountRecall;
		String NetValue                  = SkuNetTotal;
		String TotalDiamond              = "";
		String PaymentAmountHDFC         = PaymentAmount;
		String TotalNetWt			     = TotalNetWeightRecall;
		String TotalGrossWt			     = TotalGrossWeightRecall;
		String PdfInvoiceNo              = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWt,
				TotalGrossWt,
				TaxableValueSale,
				NetValue,
				PaymentAmountHDFC,
				Adjustment,
				AllSkuList,
				SkuDetails);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}

	// <summary>
	// Test Case Title : Own precia purchase from store transactions
	// Automation ID : TC55
	// Author: Hasna EK
	// </summary>
	public void TC55_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		WebDriverWait HamBurgerWait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			if (HamBurgerWait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"))) != null)
			{
				Thread.sleep(3000);
				base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
			}
		} catch (Exception e) {
			Thread.sleep(3000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc(
					"height48 centerY","Show or hide navigation bar"));
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
		}

		// Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_CustomerID, UtilityTestData.MenuBarOptions[0]);

		// Step 4: Select OP
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);			

		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_OPProduct);
		Thread.sleep(1000);			
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");

		// Step 5: Click on OP own
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_OldPreciaOwnProduct));	

		// Step 6: Select configuration(purity 22K)		
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_Purity);

		// Step 7: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		//To get current board rate
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), UtilityTestData.InvoiceNumber);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_LineNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));
		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_QcAndSmithPerson);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_QcAndSmithPerson); 
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(15000);
		double BoardRate22k = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));

		//Step 8:Click on purchase
		//Step 9:Enter receipt number 
		//Step 10:Enter Line number 
		//Step 11: Click on fetch button
		Thread.sleep(2000);
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));

		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), UtilityTestData.InvoiceNumber);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_LineNumber);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));

		// Step 12: Select QC person
		Thread.sleep(2000);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_QcAndSmithPerson);

		// Step 13: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_QcAndSmithPerson); 

		// Step 14 : click on calculate button
		// Note: Check displayed values
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

		List<String> ItemNamesInItemDetailsPge = new ArrayList<>();
		List<String> GrossWeightInItemDetailPge = new ArrayList<>();
		List<String> NetWeightInItemDetailPge = new ArrayList<>();
		List<String> GoldRateInItemDetailPge = new ArrayList<>();
		List<String> PreciaWeightInItemDetailPge = new ArrayList<>();
		List<String> PreciaRateInItemDetailPge = new ArrayList<>();
		List<String> TotalAmountInItemDetailPge = new ArrayList<>();
		Thread.sleep(5000); 

		double ActualGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		double ExpectedGoldRateInItemDetailsPge = (double) Math.round(BoardRate22k*0.98);
		asrt.assertEquals(ExpectedGoldRateInItemDetailsPge, ActualGoldRateInItemDetailsPge,1.0,"OD Purchase rate mismatch: Expected value is "+ExpectedGoldRateInItemDetailsPge+" but got "+ActualGoldRateInItemDetailsPge+" in item details page");

		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));		
		double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
		double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
		String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
		double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);
		asrt.assertEquals(ExpectedGoldAmountInItemDetailsPge, ActualGoldAmountInItemDetailsPge,1.0,"Gold amount mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in item details page");		

		String ExpectedPreciaWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstonewt"));		
		String ExpectedPreciaRateInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstonerate"));
		double ExpectedPreciaAmountInItemDetailsPge = Math.round(Double.parseDouble(ExpectedPreciaWeightInItemDetailsPge)*Double.parseDouble(ExpectedPreciaRateInItemDetailsPge)*100.0)/100.0;
		String PreciaAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstoneamt"));		
		double ActualPreciaAmountInItemDetailsPge = Double.parseDouble(PreciaAmountInItemDetailsPge);
		asrt.assertEquals(ExpectedPreciaAmountInItemDetailsPge, ActualPreciaAmountInItemDetailsPge,1.0, "Diamond amount mismatch: Expected is "+ExpectedPreciaAmountInItemDetailsPge+" but got "+ActualPreciaAmountInItemDetailsPge+" in item details page");

		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
		double ActualTotalAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
		double ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPge+ActualPreciaAmountInItemDetailsPge;
		asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

		String ItemNameForOp = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = ItemNameForOp.substring(ItemNameForOp.indexOf("-") + 2);

		ItemNamesInItemDetailsPge.add(ExpectedItemNameInItemDetailsPge);
		GrossWeightInItemDetailPge.add(ExpectedGrossWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
		NetWeightInItemDetailPge.add(ExpectedNetWeightInItemDetailsPge);
		GoldRateInItemDetailPge.add(Double.toString(ActualGoldRateInItemDetailsPge));
		PreciaWeightInItemDetailPge.add(ExpectedPreciaWeightInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
		PreciaRateInItemDetailPge.add(ExpectedPreciaRateInItemDetailsPge.replaceFirst("^0+(?!$)", ""));
		TotalAmountInItemDetailPge.add(TotalAmountInItemDetailsPge);

		//Step 15: Click on cart button
		//Expected Result: Check Purchase rate own Precia and Check calculation
		// Note:Billing Screen- No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount 
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));		
		Thread.sleep(3000);
		double CalculatedSubTotalSum = 0.00;
		double TotalGrossWt = 0.00;
		double TotalNetWt = 0.00;
		List<String> AllProductRows = base.GetElementTexts(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<String> ItemName = base.GetElementTexts(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		List<String> Quantity = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
		List<String> TotalWithoutTax = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));

		for(int i =0; i < AllProductRows.size() ;i++) {

			//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
			int ExpectedLinesCountInBillingPge = AllProductRows.size();
			int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));
			asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in billing page");

			String ActualItemNameInBillingPge = ItemName.get(i);
			String ExpectedItemNameInBillingPge = ItemNamesInItemDetailsPge.get(i);
			asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");

			String ActualQuantityInBillingPge = Quantity.get(i).replace("-", "");
			String ExpectedQuantityInBillingPge = GrossWeightInItemDetailPge.get(i);
			String ExpectedQty = String.format("%.2f", Double.parseDouble(ExpectedQuantityInBillingPge));
			String ActualQty = String.format("%.2f", Double.parseDouble(ActualQuantityInBillingPge));
			asrt.assertEquals(ExpectedQty, ActualQty,"Quantity mismatch: Expected is "+ExpectedQty+" but got "+ActualQty+" in billing page");

			double ActualQtyInBillingPge = Double.parseDouble(ActualQty);
			TotalGrossWt += ActualQtyInBillingPge;
			String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).replaceAll("[^\\d.]", "").trim();

			CalculatedSubTotalSum = CalculatedSubTotalSum + Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
			String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(TotalAmountInItemDetailPge.get(i).replaceAll("[^\\d.]", "").trim()));
			asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge,"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");

			String ExpectedNetWtInBillingPge = NetWeightInItemDetailPge.get(i).replaceAll("[^\\d.]", "").trim();
			double ExpectedNetWt = Math.round(Double.parseDouble(ExpectedNetWtInBillingPge) * 100.0) / 100.0;
			TotalNetWt += ExpectedNetWt;	
		}

		double ExpectedSubTotalInBillingPge = Math.round(CalculatedSubTotalSum * 100.0) / 100.0;	
		double ActualSubTotalInBillingPge = Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replaceAll("[^\\d.]", "").trim());
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,1,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replaceAll("[^\\d.]", "").trim();
		String ExpectedTaxInBillingPge = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_OpTax;
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");

		double ExpectedTotalAmountInBillingPge =ExpectedSubTotalInBillingPge;			 
		double ActualTotalAmountInBillingPge =Double.parseDouble(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replaceAll("[^\\d.]", "").trim());
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,1,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");

		//Step 16: Click on amount
		double ActualAmountDueInBillingPge = Double.parseDouble(base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replaceAll("[^\\d.]", "").trim());
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 17: Select payment method as RTGS/NEFT
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("RTGS/NEFT"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment","iconReturnKey"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		//Step 18: Post the invoice
		//Expected Result:  Check final invoice details, * save receipt id for future reference
		String PaymentKeyword = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC55_PaymentDetails;
		Map<String, String> PdfDetails = pdfUtils.PurchaseBillPdfValidation
				(PaymentKeyword,
						ActualTotalAmountInBillingPge,
						TotalGrossWt,
						ActualAmountDueInBillingPge,
						TotalNetWt);

		String InvoiceNumber = PdfDetails.get("InvoiceNumber");
		System.out.println("InvoiceNumber "+InvoiceNumber);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

	}
	// <summary>
	// Test Case Title : own platinum exchange from store transactions
	// Automation ID : TC57
	// Author: Chandana Babu
	// </summary>
	public void TC57_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		//Step 2: Click on Transaction button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY","Transaction"));

		//Step 3: Select customer and click on add to estimation button
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_CustomerNo, UtilityTestData.MenuBarOptions[5]);

		//Step 4: Scan a SKU and click add to cart button
		//Expected: Check calculations	
		SKUResult SkuGoldSaleItem = appUtils.GetOGSaleSku(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_TransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_FromCounterGold,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_MetalTypeGold,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_Weight,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_SaleProductCount);
		List<String> SkuList = SkuGoldSaleItem.PcsRateSKU;
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));

		List<String> SkuListPlatinum = appUtils.SelectMultipleSKUs(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_SaleProductCount,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_TransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_FromCounterPlatinum,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_MetalTypePlatinum);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));

		SkuList.addAll(SkuListPlatinum);
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		List<String> ProductNames = new ArrayList<>();
		List<String> ProductQtyList = new ArrayList<>();
		List<String> ProductTotalList = new ArrayList<>();
		int SkuCounter = 1;	
		String RpRateInSkutemDetailsPge ="";
		String TotalAmountInSkuItemDetailsPge ="";
		for (String Sku : SkuList) {
			appUtils.SKUIngridentTableValues(Sku, SkuCounter, ScannedSkuDataMap);
			ProductNames.add(ScannedSkuDataMap.get("SKU_" + SkuCounter + "_HeaderName"));
			ProductQtyList.add(ScannedSkuDataMap.get("SKU_" + SkuCounter + "_GrossWeight"));
			ProductTotalList.add(ScannedSkuDataMap.get("SKU_" + SkuCounter + "_Total"));
			RpRateInSkutemDetailsPge  = ScannedSkuDataMap.get("SKU_" + SkuCounter + "_RateRP");
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(ScannedSkuDataMap, SkuCounter);
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, SkuCounter);
			SkuCounter++;
		}

		//Step 5: Search OT
		base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_OldPatinum);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));

		//Step 6: Click on Old platinum own
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_OldPatinumOwn));

		//Step 7: Select configuration
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_Purity);

		//Step 8: Click on Add item button and Choose Exchange
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		//Step 9: Enter receipt number
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefreceiptno"), UtilityTestData.InvoiceNumber);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_LineNo);

		//Step 10: Click on fetch button
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));

		//Step 11: Select QC person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_QcAndSmithPerson);

		//Step 12: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_QcAndSmithPerson); 

		//Step 13: Click on calculate button
		//Expected : Check Exchange rate own Platinium, Check calculation
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(1000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(3000);
		double ActualOTPurchaseRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		double ExpectedOTPurchaseRateInItemDetailsPge = Double.parseDouble(RpRateInSkutemDetailsPge);
		asrt.assertEquals(ActualOTPurchaseRateInItemDetailsPge, ExpectedOTPurchaseRateInItemDetailsPge,1.0,"OTPurchaseRate mismatch: Expected value is "+ExpectedOTPurchaseRateInItemDetailsPge+" but got "+ActualOTPurchaseRateInItemDetailsPge+" in item details page");

		List<String> QuantitiesInItemDetailsPge = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("column3 textRight", "h4 ellipsis cell"));	
		List<String> RatesInItemDetailsPge = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("column4 textRight", "h4 ellipsis cell"));
		List<String> CvaluesInItemDetailsPge = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("column5 textRight", "h4 ellipsis cell"));
		Double TotalAmountInItemDetailsPge = 0.00;
		for (int i =0; i<RatesInItemDetailsPge.size();i++) {			
			double ExpectedCvalueInItemDetailsPge = Double.parseDouble(QuantitiesInItemDetailsPge.get(i))*Double.parseDouble(RatesInItemDetailsPge.get(i));
			double ActualCvalueInItemDetailsPge = Double.parseDouble(CvaluesInItemDetailsPge.get(i));
			TotalAmountInItemDetailsPge = TotalAmountInItemDetailsPge+ActualCvalueInItemDetailsPge;
			asrt.assertEquals(ActualCvalueInItemDetailsPge, ExpectedCvalueInItemDetailsPge,1.0,"Cvalue mismatch: Expected is "+ExpectedCvalueInItemDetailsPge+" but got "+ActualCvalueInItemDetailsPge+" in item details page");	    
		}		
		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		String ActualTotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
		String ExpectedTotalAmountInItemDetailsPge = Double.toString(TotalAmountInItemDetailsPge);
		asrt.assertEquals(ActualTotalAmountInItemDetailsPge, ExpectedTotalAmountInItemDetailsPge,"Total amount mismatch: "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

		String ItemNameOT = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedOTItemNameInItemDetailsPge = ItemNameOT.substring(ItemNameOT.indexOf("-") + 2);
		ProductNames.add(ExpectedOTItemNameInItemDetailsPge);
		ProductQtyList.add(ExpectedGrossWeightInItemDetailsPge);
		ProductTotalList.add(ActualTotalAmountInItemDetailsPge);

		//Step 14: Click on Add to cart button
		//Note: Billling Screen, No of Product lines, Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal, TAX and Total Amount 
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));

		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal		 		= TransactionDataLine.get("Subtotal");
		String Discount 			= TransactionDataLine.get("Discount");
		String TotalGrossWeight 	= TransactionDataLine.get("GrossWeight");
		String TotalNetWeight   	= TransactionDataLine.get("NetWeight");
		String Tax 					= TransactionDataLine.get("Tax");
		String TotalAmount 			= TransactionDataLine.get("TotalAmount");
		String ActualLinesCount		= TransactionDataLine.get("LinesCount");

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		String ExpectedLinesCount = Integer.toString(AllProductRows.size());
		asrt.assertEquals(ExpectedLinesCount, ActualLinesCount,"Lines count mismatch: Expected is "+ExpectedLinesCount+" but found "+ActualLinesCount+" in billing page");		
		double SubTotalSum = 0.00;
		for(int i =0; i < AllProductRows.size() ;i++) {
			List<WebElement> ItemName = base.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
			String ActualItemNameInBillingPge = ItemName.get(i).getText();
			if (i<=1) {
				String ExpectedItemNameInBillingPge = ProductNames.get(i).substring(ProductNames.get(i).indexOf("-") + 1).trim();
				asrt.assertEquals(ActualItemNameInBillingPge,ExpectedItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");			
			}
			else {
				String ExpectedItemNameInBillingPge = ProductNames.get(i).trim();
				asrt.assertEquals(ActualItemNameInBillingPge,ExpectedItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");
			}
			List<WebElement> Quantity = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
			String ActualQuantityInBillingPge = Quantity.get(i).getText().replace("-", "");
			String ExpectedQuantityInBillingPge = ProductQtyList.get(i);
			double ActualQtyInBillingPge = Double.parseDouble(ActualQuantityInBillingPge);
			double ExpectedQtyInBillingPge = Double.parseDouble(ExpectedQuantityInBillingPge);
			asrt.assertEquals(ActualQtyInBillingPge,ExpectedQtyInBillingPge,1.0,"Quantity mismatch: Expected is "+ExpectedQtyInBillingPge+" but got "+ActualQtyInBillingPge+" in billing page");

			List<WebElement> TotalWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));
			String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replaceAll("[^\\d.\\-]", "").trim();
			SubTotalSum = SubTotalSum+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
			String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(ProductTotalList.get(i).replaceAll("[^\\d.\\-]", "").trim()));
			asrt.assertEquals(ActualTotalWithoutTaxInBillingPge.replace("-", ""),ExpectedTotalWithoutTaxInBillingPge,"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
		}
		String ExpectedSubTotalInBillingPge = String.format("%.2f",SubTotalSum);
		String ActualSubTotalInBillingPge = SubTotal.replaceAll("[^\\d.\\-]", "").trim();
		asrt.assertEquals(ActualSubTotalInBillingPge,ExpectedSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

		String ActualTaxInBillingPge = Tax.replaceAll("[^\\d.\\-]", "").trim();
		double ActualTax = Double.parseDouble(ActualTaxInBillingPge);
		double ExpectedTax = (Double.parseDouble(ProductTotalList.get(0))+Double.parseDouble(ProductTotalList.get(1)))*0.03;
		String ExpectedTaxInBillingPge = String.format("%.2f",ExpectedTax);
		asrt.assertEquals(ActualTax,ExpectedTax,1.0,"Tax amount mismatch: "+ExpectedTax+" but found "+ActualTax+" in billing page");

		double ExpectedTotalAmountInBillingPge = Double.parseDouble(ActualSubTotalInBillingPge)+ActualTax;
		double ActualTotalAmountInBillingPge = Double.parseDouble(TotalAmount.replaceAll("[^\\d.\\-]", "").trim());
		asrt.assertEquals(ActualTotalAmountInBillingPge,ExpectedTotalAmountInBillingPge,1.0,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");

		//Step 15: Save to estimate
		//Expected: Verify the Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		Thread.sleep(1000);
		base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
		String ActualEstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

		Thread.sleep(3000);
		String ProFormaInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC57_ProformaInvoice");
		Thread.sleep(2000);
		String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + ProFormaInvoiceName;
		String Gst                 = Tax.replaceAll("[^\\d.\\-]", "").trim();
		String TaxableValue		   = TotalAmountInSkuItemDetailsPge;
		String InvoiceTotal        = String.format("%.2f",Double.parseDouble(SubTotal.replaceAll("[^\\d.\\-]", ""))+Double.parseDouble(Tax.replaceAll("[^\\d.\\-]", "")));
		String TotalAmnt           = TotalAmount.replaceAll("[^\\d.\\-]", "").trim();
		String NetTotal            = String.format("%.2f",Double.parseDouble(SubTotal.replaceAll("[^\\d.\\-]", ""))+Double.parseDouble(Tax.replaceAll("[^\\d.\\-]", "")));
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProFormaInvoicePath,
				TaxableValue,
				Gst,
				InvoiceTotal,
				NetTotal,
				TotalAmnt
				);	

		//Step 16: Recall estimate from cashier
		//Expected: Verify whether the recalled item is the same as the item in the cart.
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		String ExpectedEstmnNumber= base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		Assert.assertEquals(ActualEstmnNumber, ExpectedEstmnNumber,"Mismatch in Estimation number Actual is "+ActualEstmnNumber+" and Expected Estimation number"+ExpectedEstmnNumber+""); 

		//Step 17: Select transaction type as Exchange
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_TransactionTypeExchange);

		//Step 18: Click on process estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		//Step 19: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		//Note:Cashier Screen after recall, No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX and Total Amount
		Map<String, String> TransactionDataLineForOT = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotalForOT		 = TransactionDataLineForOT.get("Subtotal");
		String TaxForOT				 = TransactionDataLineForOT.get("Tax");
		String TotalAmountForOT 	 = TransactionDataLineForOT.get("TotalAmount");
		String ActualLinesCountForOT = TransactionDataLineForOT.get("LinesCount");

		List<WebElement> AllProductRowsInCashierScreen = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		String ExpectedLinesCountInCashierScreen = Integer.toString(AllProductRowsInCashierScreen.size());
		asrt.assertEquals(ExpectedLinesCountInCashierScreen, ActualLinesCountForOT,"Lines count mismatch: Expected is "+ExpectedLinesCount+" but found "+ActualLinesCount+" in billing page");		

		List<WebElement> ItemName = base.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		String ActualItemNameInBillingPge = ItemName.get(0).getText();
		String ExpectedItemNameInBillingPge = ProductNames.get(2).trim();
		asrt.assertEquals(ActualItemNameInBillingPge,ExpectedItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");

		List<WebElement> Quantity = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
		String ActualQuantityInCashierScreen = Quantity.get(0).getText().replace("-", "");
		String ExpectedQuantityInCashierScreen = ProductQtyList.get(2);
		double ActualQtyInCashierScreen = Double.parseDouble(ActualQuantityInCashierScreen);
		double ExpectedQtyInCashierScreen = Double.parseDouble(ExpectedQuantityInCashierScreen);
		asrt.assertEquals(ActualQtyInCashierScreen,ExpectedQtyInCashierScreen,1.000,"Quantity mismatch: Expected is "+ExpectedQtyInCashierScreen+" but got "+ActualQtyInCashierScreen+" in billing page");

		List<WebElement> TotalWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));
		String ActualTotalWithoutTaxInCashierScreen = TotalWithoutTax.get(0).getText().replaceAll("[^\\d.\\-]", "").trim();
		double SubTotalSumForOT = Double.parseDouble(ActualTotalWithoutTaxInCashierScreen);
		String ExpectedTotalWithoutTaxInCashierScreen = String.format("%.2f", Double.parseDouble(ProductTotalList.get(2).replaceAll("[^\\d.\\-]", "").trim()));
		asrt.assertEquals(ActualTotalWithoutTaxInCashierScreen.replace("-", ""),ExpectedTotalWithoutTaxInCashierScreen,"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInCashierScreen+" but got "+ActualTotalWithoutTaxInCashierScreen+" in billing page");

		String ExpectedSubTotalInCashierScreen = String.format("%.2f",SubTotalSumForOT);
		String ActualSubTotalInCashierScreen = SubTotalForOT.replaceAll("[^\\d.\\-]", "").trim();
		asrt.assertEquals(ActualSubTotalInCashierScreen,ExpectedSubTotalInCashierScreen,"Subtotal mismatch: "+ExpectedSubTotalInCashierScreen+" but found "+ActualSubTotalInCashierScreen+" in billing page");

		String ActualTaxInCashierScreen = TaxForOT.replaceAll("[^\\d.\\-]", "").trim();
		double ActualTaxForOTInCashierScreen = Double.parseDouble(ActualTaxInCashierScreen);
		double ExpectedTaxForOTInCashierScreen = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_TaxAmountForOT;
		asrt.assertEquals(ActualTaxForOTInCashierScreen,ExpectedTaxForOTInCashierScreen,1.0,"Tax amount mismatch: "+ExpectedTaxForOTInCashierScreen+" but found "+ActualTaxForOTInCashierScreen+" in billing page");

		double ExpectedTotalAmountInCashierScreen = Double.parseDouble(ActualSubTotalInCashierScreen)+ActualTaxForOTInCashierScreen;
		double ActualTotalAmountInCashierScreen = Double.parseDouble(TotalAmountForOT.replaceAll("[^\\d.\\-]", "").trim());
		asrt.assertEquals(ActualTotalAmountInCashierScreen,ExpectedTotalAmountInCashierScreen,1.0,"Total Amount mismatch: "+ExpectedTotalAmountInCashierScreen+" but found "+ActualTotalAmountInCashierScreen+" in billing page");

		//Step 20: Click on amount 0.00
		//Expected: Check Exchange invoice details
		//Step 21: Post exchange invoice
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		String GrossWeightForOT =ExpectedGrossWeightInItemDetailsPge;
		String ItemTotalForOT =ActualTotalAmountInItemDetailsPge;
		String TotalForOT =ActualTotalAmountInItemDetailsPge;
		String PayementDetailsForOT =ActualTotalAmountInItemDetailsPge;		
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		Map<String, String> OTPurchaseBill = pdfUtils.ValidateOTPdf(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_PaymentDetails, 
				GrossWeightForOT,
				QuantitiesInItemDetailsPge,
				RatesInItemDetailsPge,
				ItemTotalForOT,
				TotalForOT,
				PayementDetailsForOT);

		//Step 22: Again recall estimate from cashier screen
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"), ActualEstmnNumber);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",ActualEstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//Step 23: Select transaction type as sales
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC57_TransactionTypeSale);

		//Step 24: Click on process estimation button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

		//Step 25: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

		//Step 26: Click on amount
		//Step 27: Select payment method(cash/card)
		String PaymentAmountForSale = appUtils.PaymentAfterRecallEstimate("HDFC");

		//Step 28: Post exchange invoice
		//Expected: Check final invoice details
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("TC57_TaxInvoice");		
		String TaxInvoice =System.getProperty("user.dir") + "\\Invoices\\"+TaxInvoiceName+"";
		String TotalDiamond = "";
		String GrossAmountForSale= base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"));
		String TaxableValueForSale = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4"));
		String TotalValueForSale=base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4"));
		String TotalNetValueForSale=base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4"));
		String TotalQtyPcsForSale = String.valueOf(SkuList.size());
		String TotalDiscountForSale = Discount;
		String TotalNetWeightForSale = TotalNetWeight;
		String TotalGrossWeightForSale = String.format("%.3f", Double.parseDouble(ProductQtyList.get(0))+Double.parseDouble(ProductQtyList.get(1)));
		String AdjustmentForSale = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("PaymentsField", "h4"));
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				TaxInvoice,
				GrossAmountForSale,
				TotalDiscountForSale,
				TotalDiamond,
				TotalQtyPcsForSale,
				TotalNetWeightForSale,
				TotalGrossWeightForSale,
				TaxableValueForSale,
				TotalNetValueForSale,
				PaymentAmountForSale,
				AdjustmentForSale,
				SkuList,
				ScannedSkuDataMap
				);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}


	//<summary>
	// Test Case Title : own precia exchange old SKU table
	// Automation ID : TC56
	// Author: Neethu
	// </summary>

	public void TC56_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

		//Step 1: Login to POS	
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 50);

		// Step 2: Click on Transaction button
		Thread.sleep(8000);
		base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));

		//Prerequisite:  In ERP
		// 1. Search old SKU table
		// 2. Filter design code as OP
		// 3.select sku
		base.newWindow(1);
		driver.get(UtilityTestData.ERPURL);

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.SearchOldSKUtable);
		Thread.sleep(5000);
		base.pressKey(NormalSaleGoldandSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
		Thread.sleep(3000);
		base.buttonClick(LoginPageObj.Edt_AlertText("Design Code"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_Name("__FilterField_MGDOldSkuTable_ItemId_ItemId_Input_0_0_input"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("__FilterField_MGDOldSkuTable_ItemId_ItemId_Input_0_0_input"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.OPDetails);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_RunNowERP("__MGDOldSkuTable_ItemId_ApplyFilters_label"));
		Thread.sleep(3000);

		base.buttonClick(LoginPageObj.Edt_AlertText("SKU number"));
		Thread.sleep(3000);
		base.buttonClick(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsObj.Sel_SKUNuber("__FilterField_MGDOldSkuTable_SKUNumber_SKUNumber_0","button-label button-label-dropDown"));
		base.buttonClick(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsObj.Sel_BeginSKU("sysPopup flyoutButton-flyOut layout-root-scope","begins with","2"));
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("__FilterField_MGDOldSkuTable_SKUNumber_SKUNumber_Input_0_0_input"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("__FilterField_MGDOldSkuTable_SKUNumber_SKUNumber_Input_0_0_input"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.SKUNumber);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_RunNowERP("__MGDOldSkuTable_SKUNumber_ApplyFilters_label"));
		Thread.sleep(3000);

		String SKUNumber = base.GetValue(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsObj.Sel_SKU("SKU number", "3"));
		System.out.println("The SKU No: "+SKUNumber);
		String TrimmedSKUNumber = SKUNumber.trim();
		Thread.sleep(10000);

		// Scroll right
		WebElement scrollbar = driver.findElement(By.cssSelector(".ScrollbarLayout_faceHorizontal"));
		Actions actions = new Actions(driver);
		actions.clickAndHold(scrollbar)
		.moveByOffset(1000, 0)
		.release()
		.perform();
		Thread.sleep(1000);

		String Purity = base.GetValue(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsObj.Sel_SKU("Purity", "3"));
		String lowerCasePurity = Purity.toLowerCase();
		System.out.println("The lowercase Purity: "+lowerCasePurity);
		String SkuPurity = lowerCasePurity.trim();
		Thread.sleep(2000);
		base.newWindow(0);
		Thread.sleep(5000);

		//Step 3:Select customer and click on add to estimation button
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_CustomerID, "AddCustomerForSalesEstimationCommand");

		//Step 4: Scan a SKU(SKU Gold) and click add to cart button
		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> ExpectedItemNames = new ArrayList<>();
		List<String> TotalForEachItems = new ArrayList<>();
		List<String> ItemNamesFromBillingPge =new ArrayList<>();
		List<String> ItemQuantity=new ArrayList<>();
		int i=0;
		double ActualSubTotal = 0.0;
		double TotalWithoutTax = 0.0;

		List<String> SkuListGold = appUtils.SelectMultipleSKUs(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_SkuGoldCount,OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_TransferType,OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_GoldFromCounter,OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_MetalType);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
		for (i = 0; i < SkuListGold.size(); i++) 
		{    		
			appUtils.SKUIngridentTableValues(SkuListGold.get(i), i + 1, SkuDetails);
			ExpectedItemNames.add(SkuDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItems.add(SkuDetails.get("SKU_" + (i+1) + "_Total"));
			ItemQuantity.add(SkuDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		List<String> AllSkuList = new ArrayList<>();
		AllSkuList.addAll(SkuListGold);

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
		List<WebElement> ItemNamesInBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
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
		List<WebElement> BillingScreenItemQty = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		for (i = 0; i < BillingScreenItemQty.size(); i++)
		{
			String DisplayedItemQty = BillingScreenItemQty.get(i).getText().trim().replaceAll("[^\\d.]", "");
			double DisplayedQty = Double.parseDouble(DisplayedItemQty);
			String ExpectedItemQty = ItemQuantity.get(i).trim().replaceAll("[^\\d.]", "");			
			double ExpectedQty = Double.parseDouble(ExpectedItemQty);

			asrt.assertEquals(DisplayedQty,ExpectedQty,"Quantity mismatch : Expected value is " + ExpectedQty + " but got " + DisplayedQty+ " in Billing page");
		}

		//Displayed TOTAL (without Tax)
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
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
		double ExpectedSubTotal = Double.parseDouble(SubTotal.trim().replaceAll("[^\\d.-]", ""));
		asrt.assertEquals(ExpectedSubTotal, TotalWithoutTax, "SubTotal mismatch : Expected value is "+ExpectedSubTotal+" but got "+TotalWithoutTax+" in Billing page");

		//Tax amount Validation
		double TaxAmount=ActualSubTotal*3/100;
		double ExpectedTax = Double.parseDouble(Tax.trim().replaceAll("[^\\d.]", ""));	
		asrt.assertEquals(ExpectedTax,TaxAmount ,1, "Tax Amount mismatch : Expected value is "+ExpectedTax+"but got "+TaxAmount+" in Billing page");

		//Total Amount
		double ActualTotalAmount=ActualSubTotal+TaxAmount;
		double ExpectedTotalAmount = Double.parseDouble(NetTotal.trim().replaceAll("[^\\d.]", ""));
		asrt.assertEquals(ExpectedTotalAmount, ActualTotalAmount, 1,"Total Amount mismatch : Expected value is "+ExpectedTotalAmount+" but got "+ActualTotalAmount+" in Billing page");

		// Step 5: Select OP
		Thread.sleep(5000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_OPProdcut);
		Thread.sleep(2000);			
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");

		// Step 6: Click on OP own
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_OldPreciaProduct));	

		// Step 7: Select configuration(purity)		
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),SkuPurity);

		// Step 8: Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		// Step 9: Enter SKU and click on fetch button
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"));	
		Thread.sleep(2000);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"),TrimmedSKUNumber);

		base.buttonClick(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"));	
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_LineNo);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));	
		Thread.sleep(1000);

		// Step 10: Select QC person		
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_QcAndSmithPerson);

		// Step 11: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_QcAndSmithPerson); 

		//Step 12: click on calculate button
		//Expected Result:  Check Exchange rate(Board rate ) 
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(3000);
		Map<String, Double> OPDataMap = new LinkedHashMap<>();
		String OPQty = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column3 textRight", "h4 ellipsis cell"));
		String OPRate = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column4 textRight", "h4 ellipsis cell"));
		String OPCvalue = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("column5 textRight", "h4 ellipsis cell"));

		Double OPQtyIngredientTable =  Double.parseDouble(OPQty);
		Double OPRateIngredientTable = Double.parseDouble(OPRate);
		Double OPCValueIngredientTable = Double.parseDouble(OPCvalue);

		Double OPCalculatedCvalue = OPQtyIngredientTable * OPRateIngredientTable;
		System.out.println("The OG caluculated value: "+OPCalculatedCvalue);

		OPDataMap.put("OPQty", OPQtyIngredientTable);
		OPDataMap.put("OPCvalue",OPCValueIngredientTable);

		asrt.assertEquals(OPCalculatedCvalue, OPCValueIngredientTable,1, "Mismatch in Cvalue,  Expected " + OPCalculatedCvalue+" but got "+OPCValueIngredientTable+" in Old Precia Item details page");
		Double OPNetRateValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		asrt.assertEquals(OPRateIngredientTable, OPNetRateValue,1,"Mismatch in net rate, Expected " + OPRateIngredientTable+" but got "+OPNetRateValue+" in Old Precia Item details page");
		Double OPNetAmountValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(OPCalculatedCvalue, OPNetAmountValue,1," Mismatch in Net amount, Expected " + OPCalculatedCvalue+" but got "+OPNetAmountValue+" in Old Precia Item details page");
		Double TotalAmountValue =Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt")));
		Double GrossAmountValue =Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpgrossamt")));
		asrt.assertEquals(GrossAmountValue, TotalAmountValue,1, "Mismatch in Total amount value , Expected " + GrossAmountValue+" but got "+TotalAmountValue+" in Old Precia Item details page");
		Thread.sleep(2000);

		//Step 13: Click on cart button
		// Expected result: Check calculation Note:Billling Screen , *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		WebDriverWait Wait1 = new WebDriverWait(driver, Duration.ofSeconds(8));

		By CartChangedAlert1 = NormalSaleGoldandSilverObj.Ele_CustomerAdjustment("cart has changed");
		By OkBtn1 = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");

		if (base.isElementPresent(driver, CartChangedAlert1)) {		
			try {
				WebElement OkButton = Wait1.until(ExpectedConditions.elementToBeClickable(OkBtn1));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}


		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String>     TextOPItemNamesBilling =new ArrayList<>();
		List<String>     TextItemNamesBilling =new ArrayList<>();
		List<String>     TextItemQtyBilling =new ArrayList<>();

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);		
		String SubTotalAfterOP = BillingScrnTableData.get("Subtotal");
		String TotalGrossWeightAfterOP = BillingScrnTableData.get("GrossWeight");
		String TotalNetWeightAfterOP = BillingScrnTableData.get("NetWeight");
		String TaxAfterOP = BillingScrnTableData.get("Tax");
		String TotalAmountAfterOP = BillingScrnTableData.get("TotalAmount");
		String NetTotalAfterOP = BillingScrnTableData.get("NetTotal");
		String LinesCountAfterOP = BillingScrnTableData.get("LinesCount");

		double TotalAmountAfterOPBillingScrn = Double.parseDouble(TotalAmountAfterOP.trim().replaceAll("[^\\d.]", ""));
		String PdfTotalAmountAfterOP = String.valueOf(TotalAmountAfterOPBillingScrn);
		double TaxAfterOPBillingScrn = Double.parseDouble(TaxAfterOP.trim().replaceAll("[^\\d.]", ""));
		String PdfTaxAfterOP = String.valueOf(TaxAfterOPBillingScrn);
		double SubTotalAfterOPBillingScrn = Double.parseDouble(SubTotalAfterOP.trim().replaceAll("[^\\d.]", ""));
		String PdfSubTotalAfterOP = String.valueOf(SubTotalAfterOPBillingScrn);
		double NetTotalAfterOPBillingScrn = Double.parseDouble(NetTotalAfterOP.trim().replaceAll("[^\\d.]", ""));
		String PdfNetTotalAfterOP = String.valueOf(NetTotalAfterOPBillingScrn);
		double TotalGrossWeightAfterOPBillingScrn = Double.parseDouble(TotalGrossWeightAfterOP.trim().replaceAll("[^\\d.]", ""));
		String PdfTotalGrossWeightAfterOP = String.valueOf(TotalGrossWeightAfterOPBillingScrn);

		int LinesCountBilling = Integer.parseInt(LinesCountAfterOP);
		double SubTotalBilling = Double.parseDouble(SubTotalAfterOP.trim().replaceAll("[^\\d.\\-]", ""));
		double TaxBilling = Double.parseDouble(TaxAfterOP.trim().replaceAll("[^\\d.\\-]", ""));
		double TotalAmountBilling = Double.parseDouble(TotalAmountAfterOP.trim().replaceAll("[^\\d.\\-]", ""));

		int ExpectedLinesCountInBillingPge = AllProductRows.size();	
		asrt.assertEquals(ExpectedLinesCountInBillingPge,LinesCountBilling, "Mismatch in Lines count,expected "+ExpectedLinesCountInBillingPge+" but got "+LinesCountBilling+" in the billing screen");

		String SalesReturnSkuItemRate = ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.\\-]", "");
		String PdfSalesReturnSkuItemRate = 	ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.-]", "");
		double FirstItemRate = Double.parseDouble(SalesReturnSkuItemRate);

		double ExpectedSubTotalBillingScrn = 0.0;
		ExpectedSubTotalBillingScrn = FirstItemRate-(GrossAmountValue);

		double ExpectedTaxAmnt = FirstItemRate * 3 / 100;  
		String ExpectedTaxAmntInBilliingScrn = String.valueOf(ExpectedTaxAmnt);
		double ExpectedTaxAmount = Double.parseDouble(ExpectedTaxAmntInBilliingScrn.trim().replaceAll("[^\\d.\\-]", ""));

		double ActualTotalAmountInBilling = ExpectedSubTotalBillingScrn + ExpectedTaxAmount;

		asrt.assertEquals(ExpectedTaxAmount,TaxBilling,1.0,"Mismatch in Tax value, expected "+ExpectedTaxAmount+" but got "+TaxBilling+" in the billing screen");

		for (int n=0; n<ItemNamesBilling.size();n++)
		{
			String ItemName=ItemNamesBilling.get(n).getText().trim();
			if(ItemName.contains("OP"))
			{TextOPItemNamesBilling.add(ItemName);}
			else {TextItemNamesBilling.add(ItemName);}
		}
		Thread.sleep(3000);
		//ITems Sales 
		for(int ItemName=0;ItemName<TextItemNamesBilling.size();ItemName++)
		{
			String ExpectedFull =TextItemNamesBilling.get(ItemName);
			//	String ExpectedTrimmed = ExpectedFull.contains("-") ?ExpectedFull.substring(ExpectedFull.indexOf("-") + 2) : ExpectedFull;
			String ActualItem = ItemNamesBilling.get(ItemName).getText().trim();
			asrt.assertEquals(ExpectedFull,ActualItem, "Mismatch in item name, expected "+ExpectedFull+"but got "+ActualItem+"  in the billing screen.");
		}


		for (int j=0; j<TextOPItemNamesBilling.size();j++)
		{
			String ActualItemOP = TextOPItemNamesBilling.get(j).trim();
			asrt.assertEquals(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_OPItemName, ActualItemOP,"Mismatch in OP item name, expected "+OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_OPItemName+" but got "+ActualItemOP+" in the billing screen.");

		}		

		for (int k = 0; k <ItemNamesBilling.size(); k++)
		{
			String ItemName=ItemNamesBilling.get(k).getText().trim();
			if(ItemName.contains("OP"))
				break;    
			else {	
				String ItemRate= ItemRateBilling.get(k).getText();
				double ExpectedTotal = Double.parseDouble(ItemRate.replaceAll("[^\\d.]", ""));
				String ActualTotalBilling = ItemRateBilling.get(k).getText().trim();
				double ActualTotal = Double.parseDouble(ActualTotalBilling.replaceAll("[^\\d.]", ""));
				asrt.assertEquals(ExpectedTotal, ActualTotal,1.0,"Mismatch in total amounts (without tax),expected "+ExpectedTotal+" but got "+ActualTotal+" in the Billing screen.");		}
		}

		String FirstItemRateBilling= ItemRateBilling.get(0).getText().replace("-", "").trim();

		String TextItemRateBilling= ItemRateBilling.get(1).getText().replace("-", "").trim();
		Double ActualRateOP22k = Double.parseDouble(TextItemRateBilling.replace("₹", "").replace(",", "").trim());
		asrt.assertEquals(GrossAmountValue, ActualRateOP22k,1.0, "Mismatch in total amount for OP, expected "+GrossAmountValue+" but got "+ActualRateOP22k+" in Billing screen");
		asrt.assertEquals(ExpectedSubTotalBillingScrn,SubTotalBilling,1.0,"Mismatch in Subtotal value, expected "+ExpectedSubTotal+" but got "+SubTotalBilling+"in the Billing screen");	
		asrt.assertEquals(TotalAmountBilling,ActualTotalAmountInBilling,1.0,"Mismatch in Total amount value, expected "+TotalAmountBilling+" but got "+ActualTotalAmountInBilling+" in the Billing screen");


		//Step 14: Save to estimation 
		//Expected Result: Verify Invoice details	
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(5000);	
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC56_ProformaInvoice");
		Thread.sleep(5000);		
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FirstInvoice+"";
		String TaxableValue = PdfSalesReturnSkuItemRate;
		String InvoiceTotalAmount = PdfTotalAmountAfterOP;
		String GST = PdfTaxAfterOP;
		String TotalAmnt =PdfSubTotalAfterOP;
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProformaInvoicePath,
				TaxableValue,
				GST,
				InvoiceTotalAmount,
				PdfNetTotalAfterOP,
				TotalAmnt
				);


		//Step 15:Recall estimate from cashier screen
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		//Step 16: Select estimate and click on save to estimate button
		//Step 17: Select the estimate and click on Recall estimation
		//Expected Result: Verify whether the recalled item is the same as the item in the cart.
		//Displayed Item Name, Displayed Quantity, Displayed TOTAL (without Tax)
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));		

		String EstmnNumberRecallPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstmnNumberRecallPge,EstmnNumber,"Mismatch in Estimation No:, expected "+EstmnNumberRecallPge+" but got "+EstmnNumber+"in the Recall page");	

		//Step 18: Select transaction type as "Exchange" and click on Process estimation
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_TransactionTypeExchnge);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 
		base.setZoom(driver, 60);
		Thread.sleep(3000);

		//Step 19: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		String DiscountBilling = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));

		String PaymentAmountHDFC= base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Change due"));
		double PaymentAmountHDFCTrimmedValue= Double.parseDouble(PaymentAmountHDFC.trim().replaceAll("[^\\d.]", ""));
		String PdfPaymentAmountHDFC = String.valueOf(PaymentAmountHDFCTrimmedValue);

		Map<String, String> SkuBillingScrnTableData = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);
		String SkuNetTotal = SkuBillingScrnTableData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTableData.get("LinesCount");
		String TotalNetWeightRecall 	 =   SkuBillingScrnTableData.get("NetWeight");

		double SkuNetTotalTrimmedValue= Double.parseDouble(SkuNetTotal.trim().replaceAll("[^\\d.]", ""));
		String PdfSkuNetTotal = String.valueOf(SkuNetTotalTrimmedValue);

		String AmountDueForExchangeFromBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("₹", "").replace(",", "").trim();
		double AmountDueForExchangeInFromBillingPge = Double.parseDouble(AmountDueForExchangeFromBillingPge);			
		String ExpectedAmountForExchangeInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("₹", "").replace(",", "").trim();

		if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) {
			try { 
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} catch (Exception e) {
			}
		}


		//Step 20: Click on the Amount For printing Receipt
		//Expected Result: Check Exchange invoice details
		if (AmountDueForExchangeInFromBillingPge<0.00) {
			appUtils.PaymentModeForDiffTransactions(UtilityTestData.PaymentMode, "", "",
					UtilityTestData.SalePersonNumber,
					UtilityTestData.SalePersonName,
					UtilityTestData.DueYear,
					UtilityTestData.NomineeName,
					UtilityTestData.NomineeRelation);

		}

		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "NORMAL ADV. INVOICE"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));

		Map<String, String> AdvanceReceiptVoucherDetails = pdfUtils.NormalAdvancePdfValidation();
		System.out.println("Receipt Id: " + AdvanceReceiptVoucherDetails.get("ReceiptId"));
		System.out.println("Max Gold Rate: " + AdvanceReceiptVoucherDetails.get("MaxGoldRate"));
		System.out.println("Advance Received: " + AdvanceReceiptVoucherDetails.get("AdvanceReceived"));
		System.out.println("Payment Amount: " + AdvanceReceiptVoucherDetails.get("PaymentAmount"));
		System.out.println("Approx Weight: " + AdvanceReceiptVoucherDetails.get("ApproxWeight"));
		System.out.println("Fixed Gold Rate: " + AdvanceReceiptVoucherDetails.get("FixedGoldRate"));
		System.out.println("Deposit Type: " + AdvanceReceiptVoucherDetails.get("DepositType"));
		System.out.println("Payment Mode: " + AdvanceReceiptVoucherDetails.get("PaymentMode"));
		System.out.println("Payment Amount: " + AdvanceReceiptVoucherDetails.get("PaymentAmount"));

		String AdvanceReceived = AdvanceReceiptVoucherDetails.get("AdvanceReceived");
		String FixedGoldRate = AdvanceReceiptVoucherDetails.get("FixedGoldRate");
		String MaxGoldRate = AdvanceReceiptVoucherDetails.get("MaxGoldRate");
		String ApproxWeight = AdvanceReceiptVoucherDetails.get("ApproxWeight");
		String DepositType = AdvanceReceiptVoucherDetails.get("DepositType");

		double ExpectedAdvanceReceived = Double.parseDouble(AdvanceReceived.replaceAll("[^\\d.]", "").trim());
		double ExpectedFixedGoldRate = Double.parseDouble(FixedGoldRate.replaceAll("[^\\d.]", "").trim());
		double ActualMaxGoldRate = Double.parseDouble(MaxGoldRate.replaceAll("[^\\d.]", "").trim());
		double ActualTotalAmountOP= Double.parseDouble(PdfTotalAmountAfterOP.replaceAll("[^\\d.]", "").trim());

		System.out.println("TotAmnt "+ActualTotalAmountOP);
		System.out.println("ExpAdvanceReceived "+ExpectedAdvanceReceived);
		asrt.assertEquals(ActualTotalAmountOP, ExpectedAdvanceReceived,1, "Total amount mismatch: Expected "+ExpectedTotalAmount+" but got "+ExpectedAdvanceReceived+"in the Normal Advance Pdf");

		System.out.println("ActMaxGoldRate "+ActualMaxGoldRate);
		System.out.println("ExpFixedGoldRate "+ExpectedFixedGoldRate);
		asrt.assertEquals(ActualMaxGoldRate, ExpectedFixedGoldRate,1, "Fixed Gold Rate mismatch: Expected "+ExpectedFixedGoldRate+" but got "+ActualMaxGoldRate+"in the Normal Advance Pdf");
		asrt.assertEquals(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_DepositType, DepositType, "Deposit Type mismatch: Expected "+OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_DepositType+" but got "+DepositType+ "in the Normal Advance Pdf");

		if(ApproxWeight != "" || ApproxWeight != null)
		{
			Double ApproxWeightValue = ExpectedAdvanceReceived/ActualMaxGoldRate;
			System.out.println("ApproxWeightValue "+ApproxWeightValue);
			BigDecimal RoundedApproxWeightValue = new BigDecimal(ApproxWeightValue).setScale(3, RoundingMode.HALF_UP);
			System.out.println("RoundedApproxWeightValue "+RoundedApproxWeightValue);

			double ApproxWeightPdfValue = Double.parseDouble(ApproxWeight.replaceAll("[^\\d.]", "").trim());
			System.out.println("ApproxWeightPDFValue "+ApproxWeightPdfValue);
			asrt.assertEquals(ApproxWeightPdfValue, RoundedApproxWeightValue.doubleValue(),1, "Mismatch of ApproxWeightValue: Expected: "+ApproxWeightPdfValue+" but got "+RoundedApproxWeightValue.doubleValue()+"in the Normal Advance Pdf");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20"));
		Thread.sleep(5000);

		//Step 21: In cash, click on "Recall Estimate"
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		//Step 22.Select the estimate, Click on Recall Estimation
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		Thread.sleep(3000);
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		//Step 23: Select transaction type as "Sales" and click on Process estimation
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC56_TransactionTypeSales);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		//Step 24: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));		
		String DiscountBillingScrn = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFCBillingScrn = base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Change due"));
		double PaymentAmountHDFCTrimmedValueBillingScrn= Double.parseDouble(PaymentAmountHDFCBillingScrn.trim().replaceAll("[^\\d.]", ""));
		String PdfPaymentAmountHDFCBillingScrn = String.valueOf(PaymentAmountHDFCTrimmedValueBillingScrn);		

		Map<String, String> SkuBillingScrnTableDataValue = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);
		String SkuNetTotalBillingScrn = SkuBillingScrnTableDataValue.get("TotalAmount");
		String SkuLinesCountBillingScrn = SkuBillingScrnTableDataValue.get("LinesCount");
		String TotalNetWeightRecallBillingScrn 	 =   SkuBillingScrnTableDataValue.get("NetWeight");

		double SkuNetTotalTrimmedValueBillingScrn= Double.parseDouble(SkuNetTotalBillingScrn.trim().replaceAll("[^\\d.]", ""));
		String PdfSkuNetTotalBillingScrn = String.valueOf(SkuNetTotalTrimmedValueBillingScrn);

		//Step 25: Click on amount For printing Receipt
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
		base.excuteJsClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("cancelButton primaryButton","Close"));

		//Step 26: Post invoice
		//Expected Result: Check final invoice details,* save receipt id for future reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC56_SaleInvoice");
		Thread.sleep(5000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";
		String GrossAmount = PdfSalesReturnSkuItemRate;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCountBillingScrn;
		String Adjustment = PdfSkuNetTotalBillingScrn;
		String PaymentHDFCCard = "";
		String TotalDiamond ="";
		String SalesPdfPath = SaleInvoicePath;
		TotalGrossWeight= TotalGrossWeight;
		String NetValue = PdfSkuNetTotalBillingScrn;
		String TotalNetWt = TotalNetWeightRecallBillingScrn;

		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWt,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				AllSkuList,
				SkuDetails);

		System.out.println("PDF INVOICE NO:-"+PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();


	}

	// <summary>
	// Test Case Title : Multiple Od/OT/OU with multiple SKU
	// Automation ID : TC50
	// Author: Vishnu Manoj K
	// </summary>
	public void TC50_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

		//Step 1: Login to POS	
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Click on Transaction page
		WebDriverWait HamBurgerWait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			if (HamBurgerWait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"))) != null)
			{
				Thread.sleep(3000);
				base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
			}
		} catch (Exception e) {
			Thread.sleep(3000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc(
					"height48 centerY","Show or hide navigation bar"));
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
		}

		//Step 3:Select customer and click on add to estimation button
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_CustomerId, "AddCustomerForSalesEstimationCommand");

		//Step 4: Scan Multiple SKU(Multiple SKU Gold & Uncut) and click add to cart button
		List<String> SkuList = new ArrayList<>();
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		int SkuCounter = 1;
		List<String> SkuListGold = appUtils.SelectMultipleSKUs(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_SkuGoldCount,OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_TransferType,OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_GoldFromCounter,OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_MetalType);
		SkuList.addAll(SkuListGold);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
		List<String> SkuListsUncut = appUtils.SelectMultipleSKUs(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_SkuUncutCount,OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_TransferType,OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_UncutFromCounter,OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_MetalType);
		SkuList.addAll(SkuListsUncut);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
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
		double Purity = 0;
		String SkuPurityReturn=ScannedSkuDataMap.get("SKU_1_Purity");
		String BoardRateAny=ScannedSkuDataMap.get("SKU1_ITEM1_RATE");
		if (SkuPurityReturn.contains("24")) {
			Purity = 24;
		} else if (SkuPurityReturn.contains("22")) {
			Purity = 22;
		} else if (SkuPurityReturn.contains("18")) {
			Purity = 18;}
		System.out.println("Purity:----------"+Purity);
		double BoardRate;
		if (Purity == 22) {
			BoardRate = Double.parseDouble(BoardRateAny); // already 24K, no conversion needed
		} else {
			BoardRate = ((Double.parseDouble(BoardRateAny)) / Purity) * 24;
		}
		System.out.println("BoardRate:---------"+BoardRate);

		//TranscationPageVerifyLines
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String SubTotal		 		= TransactionDataLine.get("Subtotal");
		String Discount 			= TransactionDataLine.get("Discount");
		String TotalGrossWeight 	= TransactionDataLine.get("GrossWeight");
		String TotalNewNetWeight 	= TransactionDataLine.get("NetWeight");
		String Tax 					= TransactionDataLine.get("Tax");
		String TotalAmount 			= TransactionDataLine.get("TotalAmount");
		String NetTotal			    = TransactionDataLine.get("NetTotal");
		String LinesCount		    = TransactionDataLine.get("LinesCount");
		mathUtils.ValidateTransactionLineCalculation(TransactionDataLine, ScannedSkuDataMap);

		//Step 5: Search OU
		//Step 6:Click on OU own
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.OUProduct);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldUncutOwnProduct));	

		// Step 6: Select configuration(purity 22K)		
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_Purity);

		// Step 7: Click on Add item button
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		// Step 8: Click on Exchange button
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));

		// Step 9: Select QC Person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_QcAndSmithPerson);

		//Step 10: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_QcAndSmithPerson); 

		//Step 11: Enter piece value
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_GrossPieces1);

		//Step 12: Enter gross weight
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_GrossWeight1);

		//Step 13:click on calculate button
		//Expected Result:  Check Exchange rate(Board rate ) 
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonepcs"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonepcs"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_StonePieces1);
		List<WebElement> StoneErrorMessage = driver.findElements(NormalSaleGoldandSilverObj.Btn_AddtoCart("messageText h5"));
		if (!StoneErrorMessage.isEmpty()) 
		{
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20"));
		}	
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonewt"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonewt"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_StoneWeight1);
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonerate"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonerate"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_StoneRate1);

		//Step 15:click on calculate button
		//Expected: Check Purchase rate(Board rate) and Check calculation
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

		Thread.sleep(4000);
		Map<String, String> ScannedSkuDataMapOu = new LinkedHashMap<>();	
		appUtils.ItemDetailsTableValues(3,ScannedSkuDataMapOu);
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		System.out.println(ScannedSkuDataMapOu.keySet());

		String OuRgRate=ScannedSkuDataMapOu.get("SKU3_ITEM1_RATE");
		String OuRgQty=ScannedSkuDataMapOu.get("SKU3_ITEM1_QTY");
		String OuRgCvalue=ScannedSkuDataMapOu.get("SKU3_ITEM1_CVALUE");
		String OuStoneRate=ScannedSkuDataMapOu.get("SKU3_ITEM2_RATE");
		String OuStoneQty=ScannedSkuDataMapOu.get("SKU3_ITEM2_QTY");
		String OuStoneCvalue=ScannedSkuDataMapOu.get("SKU3_ITEM2_CVALUE");

		double ActualGoldRateInItemDetailsPge = Double.parseDouble(OuRgRate);
		double ExpectedGoldRateInItemDetailsPge = (double) Math.round(BoardRate);
		asrt.assertEquals(ActualGoldRateInItemDetailsPge, ExpectedGoldRateInItemDetailsPge ,1.0,"OU Rate mismatch: Expected value is "+ExpectedGoldRateInItemDetailsPge+" but got "+ActualGoldRateInItemDetailsPge+" in item details page");

		double NetWeightInItemDetailsPge = Double.parseDouble(OuRgQty);
		double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
		String GoldAmountInItemDetailsPge =OuRgCvalue;
		double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);
		asrt.assertEquals(ActualGoldAmountInItemDetailsPge,ExpectedGoldAmountInItemDetailsPge,1.0,"Gold Amount(Cvalue) mismatch: Expected"+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in item details page");		

		//Stonevalidation
		double ActualGoldRateInItemDetailsPgeStone = Double.parseDouble(OuStoneRate);
		double ExpectedGoldRateInItemDetailsPgeStone = Double.parseDouble(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_StoneRate1);
		asrt.assertEquals(ActualGoldRateInItemDetailsPgeStone,ExpectedGoldRateInItemDetailsPgeStone,1.0,"OU Rate mismatch: Expected value is "+ExpectedGoldRateInItemDetailsPgeStone+" but got "+ActualGoldRateInItemDetailsPgeStone+" in item details page");

		double NetWeightInItemDetailsPgeStone = Double.parseDouble(OuStoneQty);
		double ExpectedGoldAmountInItemDetailsPgeStone = Math.round(NetWeightInItemDetailsPgeStone * ActualGoldRateInItemDetailsPgeStone * 100.0) / 100.0;
		String GoldAmountInItemDetailsPgeStone =OuStoneCvalue;
		double ActualGoldAmountInItemDetailsPgeStone = Double.parseDouble(GoldAmountInItemDetailsPgeStone);
		asrt.assertEquals(ActualGoldAmountInItemDetailsPgeStone,ExpectedGoldAmountInItemDetailsPgeStone,1.0,"Gold Amount(Cvalue) mismatch: "+ExpectedGoldAmountInItemDetailsPgeStone+" but got "+ActualGoldAmountInItemDetailsPgeStone+" in item details page");		

		String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
		double ActualTotalAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
		double ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPgeStone +ActualGoldAmountInItemDetailsPge;
		asrt.assertEquals(ActualTotalAmountInItemDetailsPge,ExpectedTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

		try {
			By dialogButtonLocator = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");
			// Wait until the element is visible
			Wait.until(ExpectedConditions.visibilityOfElementLocated(dialogButtonLocator));
			// Click the button
			base.buttonClick(dialogButtonLocator);
			System.out.println("DefaultMessageDialogButton clicked successfully.");

		} catch (TimeoutException e) {
			System.out.println("DefaultMessageDialogButton was not visible within the timeout.");
		} catch (Exception e) {
			System.out.println("Error while trying to click DefaultMessageDialogButton: " + e.getMessage());
		}


		//Step 14: Click on Add to cart button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		
		Thread.sleep(3000);
		By CartChangedAlert = NormalSaleGoldandSilverObj.Ele_CustomerAdjustment("input string");
		By OkBtn = LoginPageObj.Btn_SingnIn("Button1Close");
		if (base.isElementPresent(driver, CartChangedAlert)) {		
			try {
				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}

		Map<String, String> TransactionDataLineOu = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMapOu);
		String SubTotalOu		 		= TransactionDataLineOu.get("Subtotal");
		String DiscountOu 			    = TransactionDataLineOu.get("Discount");
		String TotalGrossWeightOu 	    = TransactionDataLineOu.get("GrossWeight");//wrong
		String TotalNewNetWeightOu 	    = TransactionDataLineOu.get("NetWeight");
		String TaxOu 					= TransactionDataLineOu.get("Tax");
		String TotalAmountOu 			= TransactionDataLineOu.get("TotalAmount");
		String NetTotalOu			    = TransactionDataLineOu.get("NetTotal");
		String LinesCountOu	        = TransactionDataLineOu.get("LinesCount");
		Map<String, String> AdjustedScannedSkuMapOu = erpUtils.MergeScannedSkuDataMaps(ScannedSkuDataMap, ScannedSkuDataMapOu);
		erpUtils.ErpValidateTransactionLineCalculationSubtract(TransactionDataLineOu, AdjustedScannedSkuMapOu,"2","1",true);

		// Step 15: Again Search OU
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.OUProduct);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");
		Thread.sleep(3000);

		// Step 16: Click on Old Uncut
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldUncutOwnProduct));	

		// Step 17: Select configuration(22k)	
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_Purity);

		// Step 18: Click on Add item button
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

		// Step 8: Click on Exchange button
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));

		// Step 19: Select QC person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_QcAndSmithPerson);

		//Step 20: Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_QcAndSmithPerson); 

		//Step 21: Enter piece value
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_GrossPieces2);

		//Step 22: Enter gross weight
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_GrossWeight2);

		//Step 23:click on calculate button
		//Expected Result:  Check Exchange rate for OU  * Check calculation
		// Note:Billling Screen
		// *No of Product lines
		// *Displayed Item Name
		// *Displayed Quantity
		// *Displayed TOTAL (without Tax)
		// *Displayed Subtotal
		// *TAX
		// *Total Amount
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonepcs"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonepcs"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_StonePieces2);
		List<WebElement> StoneErrorMessageSecond = driver.findElements(NormalSaleGoldandSilverObj.Btn_AddtoCart("messageText h5"));
		if (!StoneErrorMessageSecond.isEmpty()) 
		{
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20"));
		}	
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonewt"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonewt"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_StoneWeight2);
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonerate"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonerate"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_StoneRate2);

		//Step 15:click on calculate button
		//Expected: Check Purchase rate(Board rate) and Check calculation
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

		Thread.sleep(4000);
		Map<String, String> ScannedSkuDataMapOuSecond = new LinkedHashMap<>();	
		appUtils.ItemDetailsTableValues(4,ScannedSkuDataMapOuSecond);
		System.out.println(ScannedSkuDataMapOu.keySet());
		String OuSecondRgRate=ScannedSkuDataMapOuSecond.get("SKU4_ITEM1_RATE");
		String OuSecondRgQty=ScannedSkuDataMapOuSecond.get("SKU4_ITEM1_QTY");
		String OuSecondRgCvalue=ScannedSkuDataMapOuSecond.get("SKU4_ITEM1_CVALUE");
		String OuSecondStoneRate=ScannedSkuDataMapOuSecond.get("SKU4_ITEM2_RATE");
		String OuSecondStoneQty=ScannedSkuDataMapOuSecond.get("SKU4_ITEM2_QTY");
		String OuSecondStoneCvalue=ScannedSkuDataMapOuSecond.get("SKU4_ITEM2_CVALUE");

		double ActualGoldRateInItemDetailsPgeOuSecond = Double.parseDouble(OuSecondRgRate);
		double ExpectedGoldRateInItemDetailsPgeOuSecond = (double) Math.round(BoardRate);
		asrt.assertEquals(ActualGoldRateInItemDetailsPgeOuSecond,ExpectedGoldRateInItemDetailsPgeOuSecond,1.0,"OU Rate mismatch: Expected value is "+ExpectedGoldRateInItemDetailsPgeOuSecond+" but got "+ActualGoldRateInItemDetailsPgeOuSecond+" in item details page");

		double NetWeightInItemDetailsPgeOuSecond = Double.parseDouble(OuSecondRgQty);
		double ExpectedGoldAmountInItemDetailsPgeOuSecond = Math.round(NetWeightInItemDetailsPgeOuSecond * ActualGoldRateInItemDetailsPgeOuSecond * 100.0) / 100.0;
		String GoldAmountInItemDetailsPgeOuSecond =OuSecondRgCvalue;
		double ActualGoldAmountInItemDetailsPgeOuSecond = Double.parseDouble(GoldAmountInItemDetailsPgeOuSecond);
		asrt.assertEquals(ActualGoldAmountInItemDetailsPgeOuSecond,ExpectedGoldAmountInItemDetailsPgeOuSecond,1.0,"Gold Amount(Cvalue) mismatch: "+ExpectedGoldAmountInItemDetailsPgeOuSecond+" but got "+ActualGoldAmountInItemDetailsPgeOuSecond+" in item details page");		

		//Stonevalidation
		double ActualGoldRateInItemDetailsPgeStoneOuSecond = Double.parseDouble(OuSecondStoneRate);
		double ExpectedGoldRateInItemDetailsPgeStoneOuSecond = Double.parseDouble(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_StoneRate2);
		asrt.assertEquals(ActualGoldRateInItemDetailsPgeStoneOuSecond,ExpectedGoldRateInItemDetailsPgeStoneOuSecond,1.0,"OU Rate mismatch: Expected value is "+ExpectedGoldRateInItemDetailsPgeStoneOuSecond+" but got "+ActualGoldRateInItemDetailsPgeStoneOuSecond+" in item details page");

		double NetWeightInItemDetailsPgeStoneOuSecond = Double.parseDouble(OuSecondStoneQty);
		double ExpectedGoldAmountInItemDetailsPgeStoneOuSecond = Math.round(NetWeightInItemDetailsPgeStoneOuSecond * ActualGoldRateInItemDetailsPgeStoneOuSecond * 100.0) / 100.0;
		String GoldAmountInItemDetailsPgeStoneOuSecond =OuSecondStoneCvalue;
		double ActualGoldAmountInItemDetailsPgeStoneOuSecond = Double.parseDouble(GoldAmountInItemDetailsPgeStoneOuSecond);
		asrt.assertEquals(ActualGoldAmountInItemDetailsPgeStoneOuSecond,ExpectedGoldAmountInItemDetailsPgeStoneOuSecond,1.0,"Gold Amount(Cvalue) mismatch: "+ExpectedGoldAmountInItemDetailsPgeStoneOuSecond+" but got "+ActualGoldAmountInItemDetailsPgeStoneOuSecond+" in item details page");		

		String TotalAmountInItemDetailsPgeOuSecond = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
		double ActualTotalAmountInItemDetailsPgeOuSecond = Double.parseDouble(TotalAmountInItemDetailsPgeOuSecond);
		double ExpectedTotalAmountInItemDetailsPgeOuSecond = ActualGoldAmountInItemDetailsPgeStoneOuSecond +ActualGoldAmountInItemDetailsPgeOuSecond;
		asrt.assertEquals(ActualTotalAmountInItemDetailsPgeOuSecond,ExpectedTotalAmountInItemDetailsPgeOuSecond,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPgeOuSecond+" but got "+ActualTotalAmountInItemDetailsPgeOuSecond+" in item details page");

		try {
			By dialogButtonLocator = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");
			// Wait until the element is visible
			Wait.until(ExpectedConditions.visibilityOfElementLocated(dialogButtonLocator));
			// Click the button
			base.buttonClick(dialogButtonLocator);
			System.out.println("DefaultMessageDialogButton clicked successfully.");

		} catch (TimeoutException e) {
			System.out.println("DefaultMessageDialogButton was not visible within the timeout.");
		} catch (Exception e) {
			System.out.println("Error while trying to click DefaultMessageDialogButton: " + e.getMessage());
		}

		//Step 14: Click on Add to cart button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		
		Thread.sleep(3000);
		//		By CartChangedAlert = NormalSaleGoldandSilverObj.Ele_CustomerAdjustment("input string");
		//		By OkBtn = LoginPageObj.Btn_SingnIn("Button1Close");
		//		if (base.isElementPresent(driver, CartChangedAlert)) {		
		//			try {
		//				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
		//				OkButton.click();
		//			} 
		//			catch (TimeoutException e) {
		//			} 
		//		}

		Map<String, String> TransactionDataLineOuSecond = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMapOuSecond);
		String SubTotalOuSecond		 		= TransactionDataLineOuSecond.get("Subtotal");
		String DiscountOuSecond 			    = TransactionDataLineOuSecond.get("Discount");
		String TotalGrossWeightOuSecond 	    = TransactionDataLineOuSecond.get("GrossWeight");//wrong
		String TotalNewNetWeightOuSecond 	    = TransactionDataLineOuSecond.get("NetWeight");
		String TaxOuSecond 					= TransactionDataLineOuSecond.get("Tax");
		String TotalAmountOuSecond 			= TransactionDataLineOuSecond.get("TotalAmount");
		String NetTotalOuSecond			    = TransactionDataLineOuSecond.get("NetTotal");
		String LinesCountOuSecond		        = TransactionDataLineOuSecond.get("LinesCount");
		Map<String, String> AdjustedScannedSkuMapOuSecond =erpUtils.MergeScannedSkuDataMaps(AdjustedScannedSkuMapOu, ScannedSkuDataMapOuSecond);		
		erpUtils.ErpValidateTransactionLineCalculationSubtract(TransactionDataLineOuSecond, AdjustedScannedSkuMapOuSecond,"2","2",true);

		//Step 25: Click on Save to estimation button
		//Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(6000);	
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC50_ProformaInvoice");
		Thread.sleep(6000);	
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FirstInvoice+"";
		String InvoiceTotal =TotalAmountOuSecond.replaceAll("[^\\d.]","");;
		String TaxableValue = SubTotal.replaceAll("[^\\d.]","");
		String GST = TaxOuSecond.replaceAll("[^\\d.]","");
		String TotalAmnt =NetTotalOuSecond.replaceAll("[^\\d.]","");
		String NetTotalPdf =NetTotalOuSecond.replaceAll("[^\\d.]","");
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProformaInvoicePath,
				TaxableValue,
				GST,
				InvoiceTotal,
				NetTotalPdf,
				TotalAmnt
				);

		//Step 26:Recall estimate from cashier
		//Expected Result:Verify whether the recalled item is the same as the item in the cart.
		//Cashier Screen after recall *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));		

		//Validation Steps : Verify whether the recalled item is the same as the item in the cart.
		String EstmnNumberRecallPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstmnNumberRecallPge,EstmnNumber,"Mismatch in Estimation No:, expected "+EstmnNumberRecallPge+" but got "+EstmnNumber+"in the Recall Page");	

		//Step 27: Select transaction type as Exchange
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_TransactionTypeExchnge);

		// Step 28: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 
		base.setZoom(driver, 60);
		Thread.sleep(3000);

		// Step 29: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		Map<String, String> RecallTransactionDataLineOuSecond = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMapOuSecond);
		String RecallSubTotalOuSecond		 		= RecallTransactionDataLineOuSecond.get("Subtotal");
		String RecallDiscountOuSecond 			    = RecallTransactionDataLineOuSecond.get("Discount");
		String RecallTotalGrossWeightOuSecond 	    = RecallTransactionDataLineOuSecond.get("GrossWeight");
		String RecallTotalNewNetWeightOuSecond 	    = RecallTransactionDataLineOuSecond.get("NetWeight");//wrong
		String RecallTaxOuSecond 					= RecallTransactionDataLineOuSecond.get("Tax");
		String RecallTotalAmountOuSecond 			= RecallTransactionDataLineOuSecond.get("TotalAmount");
		String RecallNetTotalOuSecond			    = RecallTransactionDataLineOuSecond.get("NetTotal");
		String RecallLinesCountOuSecond		        = RecallTransactionDataLineOuSecond.get("LinesCount");
		String AdjustmentOuSecond		            = RecallTransactionDataLineOuSecond.get("Payments");
		Map<String, String> RecallAdjustedScannedSkuMapOuSecond =erpUtils.MergeScannedSkuDataMaps(ScannedSkuDataMapOuSecond, ScannedSkuDataMapOuSecond);

		System.out.println("====== Validation Completed for Cashier Screen======");

		// === Step 1: Assert number of SKUs matches LinesCount ===
		List<String> SkuNameElements = base.GetElementTexts(
				NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5")
				);
		int ExpectedLinesCount = Integer.parseInt(RecallTransactionDataLineOuSecond.get("LinesCount").replaceAll("[^\\d]", ""));
		Assert.assertEquals(SkuNameElements.size(), ExpectedLinesCount, "After Calculation Value Mismatch in number of SKUs Actual:"+SkuNameElements.size()+" and number of SKUs Expected:"+ExpectedLinesCount+" LinesCount in Transaction Line Page");

		// === Step 2: SKU Name ===
		for (int i = 0; i < SkuNameElements.size(); i++) {
			String ActualSkuName = SkuNameElements.get(i).trim();
			String ExpectedSkuName = RecallAdjustedScannedSkuMapOuSecond.get("SKU_" + (i + 1) + "_SKUName");
			Assert.assertNotNull(ExpectedSkuName, "Expected SKU name is null for SKU_" + (i + 1));

			String[] Split = ExpectedSkuName.trim().split(" - ", 2);
			String ExpectedProductNameOnly = (Split.length > 1) ? Split[1].trim() : ExpectedSkuName.trim();

			System.out.println("Asserting SKU_" + (i + 1) + " Name: Expected = " + ExpectedProductNameOnly + ", Actual = " + ActualSkuName);
			Assert.assertEquals(ActualSkuName, ExpectedProductNameOnly, "After Calculation Mismatch in SKU name Actual:"+ActualSkuName+" and SKU name Expected:"+ExpectedProductNameOnly+"  for SKU_" + (i + 1));
		}

		// === Step 3: GrossWeight ===
		List<String> SkuQuantityElements = base.GetElementTexts(
				NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5")
				);
		for (int i = 0; i < SkuQuantityElements.size(); i++) {
			String ActualQtyStr = SkuQuantityElements.get(i).trim().replaceAll("[^\\d.]", "");
			String ExpectedGrossWeightStr = RecallAdjustedScannedSkuMapOuSecond.get("SKU_" + (i + 1) + "_GrossWeight");
			Assert.assertNotNull(ExpectedGrossWeightStr, "Expected GrossWeight is null for SKU_" + (i + 1));

			double ActualQty = Double.parseDouble(ActualQtyStr);
			double ExpectedGrossWeight = Double.parseDouble(ExpectedGrossWeightStr.trim().replaceAll("[^\\d.]", ""));

			System.out.println("Asserting GrossWeight for SKU_" + (i + 1) + ": Expected = " + ExpectedGrossWeight + ", Actual = " + ActualQty);
			Assert.assertEquals(ActualQty, ExpectedGrossWeight,1, "After Calculation Mismatch in GrossWeight Actual:"+ActualQty+" and Expected:"+ExpectedGrossWeight+" for SKU_" + (i + 1));
		}

		// === Step 4 & 5: Total Without Tax and Subtotal ===
		List<String> TotalWithoutTaxElements = base.GetElementTexts(
				NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5")
				);
		double TotalWithoutTaxSum = 0.0;
		for (int i = 0; i < TotalWithoutTaxElements.size(); i++) {
			int SkuNumber = i + 1;
			String ActualTotalStr = TotalWithoutTaxElements.get(i).trim().replaceAll("[^\\d.]", "");
			String ExpectedTotalStr = RecallAdjustedScannedSkuMapOuSecond.get("SKU_" + SkuNumber + "_Total");

			double ActualTotal = Double.parseDouble(ActualTotalStr);
			double ExpectedTotal = Double.parseDouble(ExpectedTotalStr.trim().replaceAll("[^\\d.]", ""));

			System.out.println("Asserting TOTAL (Without Tax) for SKU_" + SkuNumber + ": Expected = " + ExpectedTotal + ", Actual = " + ActualTotal);
			Assert.assertEquals(ActualTotal, ExpectedTotal,1, "After Calculation Mismatch in TOTAL (Without Tax) Actual:"+ActualTotal+" and TOTAL (Without Tax) Expected:"+ExpectedTotal+" for SKU_" + SkuNumber);

			TotalWithoutTaxSum += ActualTotal;
		}
		double ExpectedSubtotal = Double.parseDouble(RecallTransactionDataLineOuSecond.get("Subtotal").replaceAll("[^\\d.]", ""));
		System.out.println("Asserting TOTAL (Without Tax) SUM vs Subtotal:");
		Assert.assertEquals(TotalWithoutTaxSum, ExpectedSubtotal,1, "After Calculation Mismatch in total sum Actual:"+TotalWithoutTaxSum+" and total sum Expected:"+ExpectedSubtotal+" ");

		// === Step 6: TAX — Force to 0.00 ===
		double CalculatedTax = 0.00;
		double ExpectedTax = Double.parseDouble(RecallTransactionDataLineOuSecond.get("Tax").replaceAll("[^\\d.]", ""));
		System.out.println("Asserting calculated TAX (forced 0.00) vs displayed Tax:");
		Assert.assertEquals(CalculatedTax, ExpectedTax,1, "After Calculation Mismatch in calculated Tax "+CalculatedTax+" and Expected Tax value is"+ExpectedTax+"");

		// === Step 7: TotalAmount ===
		double CalculatedTotalAmount = Math.round((ExpectedSubtotal + ExpectedTax) * 100.0) / 100.0;
		double ExpectedTotalAmount = Double.parseDouble(RecallTransactionDataLineOuSecond.get("TotalAmount").replaceAll("[^\\d.]", ""));
		System.out.println("Asserting Total Amount (Subtotal + Tax) with displayed TotalAmount:");
		Assert.assertEquals(CalculatedTotalAmount, ExpectedTotalAmount,1, "After Calculation Mismatch in Total Amount Calculated "+CalculatedTotalAmount+" and Expected Total Amount "+ExpectedTotalAmount+"");

		System.out.println("====== Validation Completed for Cashier Screen======");


		// Step 30: Click on amount 0.00
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));	
		if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) {
			try { 
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} catch (Exception e) {
			}
		}

		// Step 31:  Post exchange invoice
		// Expected Result: Check Exchange invoice details
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> PurchaseBillData =pdfUtils.OldSkuBillPdfValidation(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_PaymentDetails);

		String GrossWt1 = PurchaseBillData.get("GrossWt1");
		double GrossWtDouble1 = Double.parseDouble(GrossWt1);
		String NetWt1 = PurchaseBillData.get("NetWt1");
		double NetWtDouble1 = Double.parseDouble(NetWt1);
		String Rate1 = PurchaseBillData.get("Rate1");
		String ItemTotal1 = PurchaseBillData.get("ItemTotal1");
		Map<String, String> StoneData = new HashMap<>();
		List<String> StoneNamesListFirst = new ArrayList<>();
		for (Map.Entry<String, String> entry : PurchaseBillData.entrySet()) {
			String Key = entry.getKey();

			if (Key.endsWith("Wt1") && !Key.equals("GrossWt1") && !Key.equals("NetWt1")) {
				String StoneName = Key.replace("Wt1", "");
				String StoneWeight = entry.getValue();
				String RateKey = StoneName + "Rate1";
				String StoneRate = PurchaseBillData.getOrDefault(RateKey, "");
				// Store in map
				StoneData.put(StoneName + "_Wt", StoneWeight);
				StoneData.put(StoneName + "_Rate", StoneRate);
				StoneNamesListFirst.add(StoneName);
			}
		}
		for (String Stone : StoneNamesListFirst) {
			String StoneWeight1 = StoneData.get(Stone + "_Wt");
			String StoneRate1 = StoneData.get(Stone + "_Rate");
			System.out.println("Stone: " + Stone + ", Weight: " + StoneWeight1 + ", Rate: " + StoneRate1);

			asrt.assertEquals(Double.parseDouble(StoneWeight1),NetWeightInItemDetailsPgeStone,1.0,"Mismatch in Stone Weight , expected "+NetWeightInItemDetailsPgeStone+" but got "+Double.parseDouble(StoneWeight1)+" in the Purchase Bill Pdf");
			asrt.assertEquals(Double.parseDouble(StoneRate1),ActualGoldRateInItemDetailsPgeStone,1.0,"Mismatch in Stone Rate value, expected "+ActualGoldRateInItemDetailsPgeStone+" but got "+Double.parseDouble(StoneRate1)+" in the Purchase Bill Pdf");
		}
		Double NetWeightFirstOu=NetWeightInItemDetailsPge-NetWeightInItemDetailsPgeStone;
		String InvoiceNumberPdf = PurchaseBillData.get("InvoiceNumber");
		System.out.println("Invoice Number  " + InvoiceNumberPdf);

		asrt.assertEquals(GrossWtDouble1, Double.parseDouble(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_GrossWeight1),1.0, "Mismatch in Gross Weight of first Product, expected "+Double.parseDouble(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_GrossWeight1)+" but got "+GrossWtDouble1+" in the Purchase Bill Pdf");
		asrt.assertEquals(NetWtDouble1, NetWeightFirstOu,1.0, "Mismatch in Net Weight of first Product, expected "+NetWeightFirstOu+" but got "+NetWtDouble1+" in the Purchase Bill Pdf");
		asrt.assertEquals(Double.parseDouble(Rate1), ActualGoldRateInItemDetailsPge,1.0,"Mismatch in Rate of first Product, expected "+ActualGoldRateInItemDetailsPge+" but got "+Double.parseDouble(Rate1)+"in the Purchase Bill Pdf");	
		asrt.assertEquals(Double.parseDouble(ItemTotal1),ActualTotalAmountInItemDetailsPge,1.0,"Mismatch in Item Total of first Product, expected "+ActualTotalAmountInItemDetailsPge+" but got "+Double.parseDouble(ItemTotal1)+"in the Purchase Bill Pdf");	

		String GrossWt2 = PurchaseBillData.get("GrossWt2");
		String NetWt2 = PurchaseBillData.get("NetWt2");
		String Rate2 = PurchaseBillData.get("Rate2");
		String ItemTotal2 = PurchaseBillData.get("ItemTotal2");
		Map<String, String> StoneData2 = new HashMap<>();
		List<String> StoneNamesList = new ArrayList<>();
		for (Map.Entry<String, String> Entry : PurchaseBillData.entrySet()) {
			String Key = Entry.getKey();

			if (Key.endsWith("Wt1") && !Key.equals("GrossWt1") && !Key.equals("NetWt1")) {
				String StoneName = Key.replace("Wt1", "");
				String StoneWeight = Entry.getValue();
				String RateKey = StoneName + "Rate1";
				String StoneRate = PurchaseBillData.getOrDefault(RateKey, "");
				StoneData2.put(StoneName + "_Wt", StoneWeight);
				StoneData2.put(StoneName + "_Rate", StoneRate);
				StoneNamesList.add(StoneName);
			}
		}

		for (String Stone : StoneNamesList) {
			String StoneWeight2 = StoneData2.get(Stone + "_Wt");
			String StoneRate2 = StoneData2.get(Stone + "_Rate");
			System.out.println("Stone: " + Stone + ", Weight: " + StoneWeight2 + ", Rate: " + StoneRate2);
			asrt.assertEquals(Double.parseDouble(StoneWeight2),NetWeightInItemDetailsPgeStoneOuSecond,1.0,"Mismatch in Stone Weight , expected "+NetWeightInItemDetailsPgeStoneOuSecond+" but got "+Double.parseDouble(StoneWeight2)+" in the Purchase Bill Pdf");
			asrt.assertEquals(Double.parseDouble(StoneRate2),ActualGoldRateInItemDetailsPgeStone,1.0,"Mismatch in Stone Rate value, expected "+ActualGoldRateInItemDetailsPgeStone+" but got "+Double.parseDouble(StoneRate2)+" in the Purchase Bill Pdf");
		}
		Double NetWeightFirstOuSecond=NetWeightInItemDetailsPgeOuSecond-NetWeightInItemDetailsPgeStoneOuSecond;
		System.out.println("Invoice Number  " + InvoiceNumberPdf);

		asrt.assertEquals(Double.parseDouble(GrossWt2), Double.parseDouble(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_GrossWeight2),1.0, "Mismatch in Gross Weight of first Product, expected "+Double.parseDouble(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_GrossWeight2)+" but got "+Double.parseDouble(GrossWt2)+" in the Purchase Bill Pdf");
		asrt.assertEquals(Double.parseDouble(NetWt2), NetWeightFirstOuSecond,1.0, "Mismatch in Net Weight of first Product, expected "+NetWeightFirstOuSecond+" but got "+Double.parseDouble(NetWt2)+" in the Purchase Bill Pdf");
		asrt.assertEquals(Double.parseDouble(Rate2), ActualGoldRateInItemDetailsPgeOuSecond,1.0,"Mismatch in Rate of first Product, expected "+ActualGoldRateInItemDetailsPgeOuSecond+" but got "+Double.parseDouble(Rate2)+"in the Purchase Bill Pdf");	
		asrt.assertEquals(Double.parseDouble(ItemTotal2),ActualTotalAmountInItemDetailsPgeOuSecond,1.0,"Mismatch in Item Total of first Product, expected "+ActualTotalAmountInItemDetailsPgeOuSecond+" but got "+Double.parseDouble(ItemTotal2)+"in the Purchase Bill Pdf");	

		String TotalAmountPdf = PurchaseBillData.get("TotalAmount");
		String PaymentDetailsPdf = PurchaseBillData.get("PaymentDetails");
		asrt.assertEquals(Double.parseDouble(TotalAmountPdf),Double.parseDouble(RecallTotalAmountOuSecond.replaceAll("[^\\d.]", "")),1.0,"Mismatch in Subtotal value, expected "+Double.parseDouble(RecallTotalAmountOuSecond.replaceAll("[^\\d.]", ""))+" but got "+Double.parseDouble(TotalAmountPdf)+"in the Purchase Bill Pdf");	
		asrt.assertEquals(Double.parseDouble(PaymentDetailsPdf),Double.parseDouble(AdjustmentOuSecond.replaceAll("[^\\d.]", "")),1.0,"Mismatch in Total amount value, expected "+Double.parseDouble(AdjustmentOuSecond.replaceAll("[^\\d.]", ""))+" but got "+Double.parseDouble(PaymentDetailsPdf)+" in the Purchase Bill Pdf");


		//Step 32: Again recall estimate from cashier screen
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		Thread.sleep(3000);
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Step 33: Select the Transaction Type as Sales        
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_TransactionTypeSales);

		//Validation Steps : Verify whether the recalled item is the same as the item in the cart.
		String EstmnNumberRecallPgeSales = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstmnNumberRecallPgeSales,EstmnNumber,"Mismatch in Estimation No:, expected "+EstmnNumber+" but got "+EstmnNumberRecallPgeSales+"in the Recall page");	

		// Step 34: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		// Step 35: Choose a sales representative
		base.setZoom(driver, 40);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));		

		String DiscountBilling = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC= base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		double PaymentAmountHDFCTrimmedValue= Double.parseDouble(PaymentAmountHDFC.trim().replaceAll("[^\\d.]", ""));
		String PdfPaymentAmountHDFC = String.valueOf(PaymentAmountHDFCTrimmedValue);

		Map<String, String> RecallSaleTransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
		String RecallSaleSubTotal		 		= RecallSaleTransactionDataLine.get("Subtotal");
		String RecallSaleDiscount 			= RecallSaleTransactionDataLine.get("Discount");
		String RecallSaleTotalGrossWeight 	= RecallSaleTransactionDataLine.get("GrossWeight");
		String RecallSaleTotalNewNetWeight 	= RecallSaleTransactionDataLine.get("NetWeight");
		String RecallSaleTax 					= RecallSaleTransactionDataLine.get("Tax");
		String RecallSaleTotalAmount 			= RecallSaleTransactionDataLine.get("TotalAmount");
		String RecallSaleNetTotal			    = RecallSaleTransactionDataLine.get("NetTotal");
		String RecallSaleLinesCount		    = RecallSaleTransactionDataLine.get("LinesCount");
		mathUtils.ValidateTransactionLineCalculation(RecallSaleTransactionDataLine, ScannedSkuDataMap);

		double SkuNetTotalTrimmedValue= Double.parseDouble(RecallSaleTotalAmount.trim().replaceAll("[^\\d.]", ""));
		String PdfSkuNetTotal = String.valueOf(SkuNetTotalTrimmedValue);

		//Step 36: Click on amount
		//Step 37: Select payment method(cash/card)
		appUtils.PaymentAfterRecallEstimate(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC50_PaymentMethod);

		//Step 38: Post exchange invoice(sale)
		//Expected Result: Check final invoice details,* save receipt id for future reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC50_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";
		String GrossAmount = RecallSaleSubTotal.replaceAll("[^\\d.]", "");
		String TotalDiscount = RecallSaleDiscount;
		String TotalQtyPcs = RecallSaleLinesCount;
		String Adjustment = AdjustmentOuSecond.replaceAll("[^\\d.]", "");
		String PaymentHDFCCard = PdfPaymentAmountHDFC;
		String TotalDiamond ="";
		String SalesPdfPath = SaleInvoicePath;
		String TotalGrossWeightSale= RecallSaleTotalGrossWeight;
		String NetValue = PdfSkuNetTotal;
		String TotalNetWt = RecallSaleTotalNewNetWeight;	
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWt,
				TotalGrossWeightSale,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				SkuList,
				ScannedSkuDataMap);

		System.out.println("PDF INVOICE NO:-"+PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}



	// <summary>
	// Test Case Title : Multiple OU/OT/OP with multiple category item
	// Automation ID : TC63
	// Author: Robin T. Abraham
	// </summary>

	public void TC63_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

		//Step 1: Login to POS	
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Click on Transaction page
		appUtils.HamBurgerButtonClick("iconShop");

		// Step 3. Select customer and click on Add to Estimation button
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_CustomerID, UtilityTestData.MenuBarOptions[5]);

		// 4. Scan a SKU (Gold, Diamond, or Uncut) and click on "Add to Cart" button
		Map<String, String> SkuDetails = new LinkedHashMap<>();
		List<String> ExpectedItemNames = new ArrayList<>();
		List<String> TotalForEachItems = new ArrayList<>();
		List<String> ItemNamesFromBillingPge =new ArrayList<>();
		List<String> ItemQuantity=new ArrayList<>();
		int i=0;
		double ActualSubTotal = 0.0;
		double TotalWithoutTax = 0.0;

		SKUResult SkuGoldSaleItem = appUtils.GetOGSaleSku(
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_TransferType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_FromCounter,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_MetalType,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_SaleQuantity,
				OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_SkuGoldCount);
		List<String> SkuListGold = SkuGoldSaleItem.PcsRateSKU;
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));		
		for (i = 0; i < SkuListGold.size(); i++) 
		{    		
			appUtils.SKUIngridentTableValues(SkuListGold.get(i), i + 1, SkuDetails);
			ExpectedItemNames.add(SkuDetails.get("SKU_" + (i+1) + "_HeaderName"));
			TotalForEachItems.add(SkuDetails.get("SKU_" + (i+1) + "_Total"));
			ItemQuantity.add(SkuDetails.get("SKU_" + (i+1) + "_GrossWeight"));
			Map<String, String> CurrentSkuData = appUtils.ExtractDataForSku(SkuDetails, (i+1));
			mathUtils.SkuIngredientItemCalculation(CurrentSkuData, (i+1));
		}

		List<String> AllSkuList = new ArrayList<>();
		AllSkuList.addAll(SkuListGold);

		Map<String, String> Results = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);		
		String SubTotal    			= Results.get("Subtotal");
		String Discount 			= Results.get("Discount");
		String TotalGrossWeight 	= Results.get("GrossWeight");
		String TotalNetWeight		= Results.get("NetWeight");
		String Tax 					= Results.get("Tax");
		String TotalAmount 			= Results.get("TotalAmount");
		String NetTotal 			= Results.get("NetTotal");
		String LinesCount 			= Results.get("LinesCount");

		//Displayed Item Name
		List<WebElement> ItemNamesInBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
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
		asrt.assertEquals(ActualProductCount, ExpectedLinesCount, "Number of Product Lines mismatch : Expected value is "+ExpectedLinesCount+" but got "+ActualProductCount+" in Billing page");

		//Displayed Quantity
		List<WebElement> BillingScreenItemQty = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		for (i = 0; i < BillingScreenItemQty.size(); i++)
		{
			String DisplayedItemQty = BillingScreenItemQty.get(i).getText().trim().replaceAll("[^\\d.]", "");
			double DisplayedQty = Double.parseDouble(DisplayedItemQty);
			String ExpectedItemQty = ItemQuantity.get(i).trim().replaceAll("[^\\d.]", "");			
			double ExpectedQty = Double.parseDouble(ExpectedItemQty);

			asrt.assertEquals(DisplayedQty,ExpectedQty,"Quantity mismatch : Expected value is " + ExpectedQty + " but got " + DisplayedQty+ " in Billing page");
		}

		//Displayed TOTAL (without Tax)
		List<WebElement> TotalsWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		for (int j =0; j<TotalsWithoutTax.size();j++) 
		{
			double ActualTotal = Double.parseDouble(TotalForEachItems.get(j));
			double ExpectedTotal = Double.parseDouble(TotalsWithoutTax.get(j).getText().trim().replaceAll("[^\\d.]", ""));

			asrt.assertEquals(ExpectedTotal, ActualTotal,"Total(Without Tax) mismatch : Expected value is "+ExpectedTotal+"but got "+ActualTotal+ " in Billing screen");
		}

		//Displayed Subtotal
		Thread.sleep(3000);
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

		// 5. Search for OP
		// 6. Click on "Old Precia Own"
		// 7. Select configuration (purity: 22K)
		// 8. Click on "Add Item" button
		// Choose Exchange
		// 9. Select QC person
		// 10. Select Smith person
		// 11. Enter piece value
		// 12. Enter gross weight
		// 13. Enter Precious Stone Pieces, weight, rate
		// 14. click on calculate button
		// Expected: Check Exchange rate own Gold

		// Repeat step 5-14

		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.OPProduct);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldPreciaProduct));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_Purity);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_QcAndSmithPerson);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_QcAndSmithPerson); 
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossPieces);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossWeight);

		///Stone Details
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstonepcs"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstonepcs"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_StonePieces);
		List<WebElement> StoneErrorMessage = driver.findElements(NormalSaleGoldandSilverObj.Btn_AddtoCart("messageText h5"));
		if (!StoneErrorMessage.isEmpty())
		{
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20"));
		}	
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstonewt"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstonewt"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_StoneWeight);
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstonerate"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstonerate"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_StoneRate);

		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

		Map<String, Double> OpDataMap = new LinkedHashMap<>();

		String OpQty = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column3 textRight", "h4 ellipsis cell","1"));
		String OpRate = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column4 textRight", "h4 ellipsis cell","1"));
		String OpCvalue = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column5 textRight", "h4 ellipsis cell","1"));
		String OpQtyStone = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column3 textRight", "h4 ellipsis cell","2"));
		String OpRateStone = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column4 textRight", "h4 ellipsis cell","2"));
		String OpCvalueStone = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column5 textRight", "h4 ellipsis cell","2"));

		Double OpQtyIngredientTable =  Double.parseDouble(OpQty);
		Double OpRateIngredientTable = Double.parseDouble(OpRate);
		Double OpCValueIngredientTable = Double.parseDouble(OpCvalue);

		Double OpQtyIngredientTableStone =  Double.parseDouble(OpQtyStone);
		Double OpRateIngredientTableStone = Double.parseDouble(OpRateStone);
		Double OpCValueIngredientTableStone = Double.parseDouble(OpCvalueStone);

		Double OpCvalueTotal =OpCValueIngredientTable + OpCValueIngredientTableStone;//

		Double OpCalculatedCvalue = OpQtyIngredientTable * OpRateIngredientTable;
		System.out.println("The OP caluculated value: "+OpCalculatedCvalue);

		Double OpCalculatedCvalueStone = OpQtyIngredientTableStone * OpRateIngredientTableStone;
		System.out.println("The OP caluculated value: "+OpCalculatedCvalueStone);

		OpDataMap.put("OpQty", OpQtyIngredientTable);
		OpDataMap.put("OpCvalue",OpCValueIngredientTable);

		OpDataMap.put("OpQtyStone", OpQtyIngredientTableStone);
		OpDataMap.put("OpCvalueStone",OpCValueIngredientTableStone);

		asrt.assertEquals(OpCalculatedCvalueStone, OpCValueIngredientTableStone,1, "Mismatch in Stone Cvalue,  Expected " + OpCalculatedCvalueStone+" but got "+OpCValueIngredientTableStone+" in Old Precia Item details page");
		asrt.assertEquals(OpCalculatedCvalue, OpCValueIngredientTable,1, "Mismatch in Cvalue,  Expected " + OpCalculatedCvalue+" but got "+OpCValueIngredientTable+" in Old Precia Item details page");
		Double OpNetRateValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		asrt.assertEquals(OpRateIngredientTable, OpNetRateValue,1,"Mismatch in net rate, Expected " + OpRateIngredientTable+" but got "+OpNetRateValue+" in Old Precia Item details page");
		Double OpNetAmountValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(OpCalculatedCvalue, OpNetAmountValue,1," Mismatch in Net amount, Expected " + OpCalculatedCvalue+" but got "+OpNetAmountValue+" in Old Precia Item details page");
		Double OpTotalAmountValue =Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt")));
		asrt.assertEquals(OpCvalueTotal, OpTotalAmountValue,1, "Mismatch in Total amount value , Expected " + OpCvalueTotal+" but got "+OpTotalAmountValue+" in Old Precia Item details page");
		Thread.sleep(1000);

		//Step 15: Click on Add to cart button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		
		WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		Thread.sleep(1000);

		By CartChangedAlert = NormalSaleGoldandSilverObj.Ele_CustomerAdjustment("input string");
		By OkBtn = LoginPageObj.Btn_SingnIn("Button1Close");

		if (base.isElementPresent(driver, CartChangedAlert)) {		
			try {
				WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
				Thread.sleep(3000);
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}

		// 5. Search for OU
		// 6. Click on "Old Uncut Own"
		// 7. Select configuration (purity: 22K)
		// 8. Click on "Add Item" button
		// Choose Exchange
		// 9. Select QC person
		// 10. Select Smith person
		// 11. Enter piece value
		// 12. Enter gross weight
		// 13. Enter Precious Stone Pieces, weight, rate
		// 14. click on calculate button
		// Expected: Check Exchange rate own Gold

		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.OUProduct);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldandSilverObj.Edt_Name("Search"), "ENTER");
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldUncutOwnProduct));	
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_Purity);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NExchange"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_QcAndSmithPerson);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_QcAndSmithPerson); 
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("piecesogp"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossPieces);
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossWeight);

		///Stone Details
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonepcs"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonepcs"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_StonePieces);
		List<WebElement> StoneErrorMessage1 = driver.findElements(NormalSaleGoldandSilverObj.Btn_AddtoCart("messageText h5"));
		if (!StoneErrorMessage1.isEmpty()) 
		{
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart("buttonContainer col no-shrink blockWidth leftmostDialogButton rightmostDialogButton marginTop20"));
		}	
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonewt"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonewt"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_StoneWeight);
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonerate"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpstonerate"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC62_StoneRate);
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

		Map<String, Double> OuDataMap = new LinkedHashMap<>();

		String OuQty = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column3 textRight", "h4 ellipsis cell","1"));
		String OuRate = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column4 textRight", "h4 ellipsis cell","1"));
		String OuCvalue = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column5 textRight", "h4 ellipsis cell","1"));
		String OuQtyStone = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column3 textRight", "h4 ellipsis cell","2"));
		String OuRateStone = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column4 textRight", "h4 ellipsis cell","2"));
		String OuCvalueStone = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUNumber("column5 textRight", "h4 ellipsis cell","2"));

		Double OuQtyIngredientTable =  Double.parseDouble(OuQty);
		Double OuRateIngredientTable = Double.parseDouble(OuRate);
		Double OuCValueIngredientTable = Double.parseDouble(OuCvalue);

		Double OuQtyIngredientTableStone =  Double.parseDouble(OuQtyStone);
		Double OuRateIngredientTableStone = Double.parseDouble(OuRateStone);
		Double OuCValueIngredientTableStone = Double.parseDouble(OuCvalueStone);

		Double OuCvalueTotal=OuCValueIngredientTable+OuCValueIngredientTableStone;

		Double OuCalculatedCvalue = OuQtyIngredientTable * OuRateIngredientTable;
		System.out.println("The OU caluculated value: "+OuCalculatedCvalue);

		Double OuCalculatedCvalueStone = OuQtyIngredientTableStone * OuRateIngredientTableStone;
		System.out.println("The OU caluculated value Stone: "+OuCalculatedCvalueStone);

		OuDataMap.put("OuQty", OuQtyIngredientTable);
		OuDataMap.put("OuCvalue",OuCValueIngredientTable);

		OuDataMap.put("OuQtyStone", OuQtyIngredientTableStone);
		OuDataMap.put("OuCvalueStone",OuCValueIngredientTableStone);

		asrt.assertEquals(OuCalculatedCvalueStone, OuCValueIngredientTableStone,1, "Mismatch in Stone Cvalue,  Expected " + OuCalculatedCvalueStone+" but got "+OuCValueIngredientTableStone+" in Old Uncut Item details page");
		asrt.assertEquals(OuCalculatedCvalue, OuCValueIngredientTable,1, "Mismatch in Cvalue,  Expected " + OuCalculatedCvalue+" but got "+OuCValueIngredientTable+" in Old Uncut Item details page");
		Double OuNetRateValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		asrt.assertEquals(OuRateIngredientTable, OuNetRateValue,1,"Mismatch in net rate, Expected " + OuRateIngredientTable+" but got "+OuNetRateValue+" in Old Uncut Item details page");
		Double OuNetAmountValue = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt")));
		asrt.assertEquals(OuCalculatedCvalue, OuNetAmountValue,1," Mismatch in Net amount, Expected " + OuCalculatedCvalue+" but got "+OuNetAmountValue+" in Old Uncut Item details page");
		Double OuTotalAmountValue =Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt")));
		asrt.assertEquals(OuCvalueTotal, OuTotalAmountValue,1, "Mismatch in Total amount value , Expected " + OuCvalueTotal+" but got "+OuTotalAmountValue+" in Old Uncut Item details page");
		Thread.sleep(1000);

		//Step 15: Click on Add to cart button
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_AddtoCart1("SimpleProductDetailsView_AppBarView_appBarCommandCommand","win-commandicon"));		
		WebDriverWait Wait1 = new WebDriverWait(driver, Duration.ofSeconds(8));
		Thread.sleep(1000);

		By CartChangedAlert1 = NormalSaleGoldandSilverObj.Ele_CustomerAdjustment("cart has changed");
		By OkBtn1 = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");

		if (base.isElementPresent(driver, CartChangedAlert1)) {		
			try {
				WebElement OkButton = Wait1.until(ExpectedConditions.elementToBeClickable(OkBtn1));
				OkButton.click();
			} 
			catch (TimeoutException e) {
			} 
		}

		List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
		List<WebElement> ItemNamesBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));
		List<WebElement> ItemRateBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-TotalWithoutTaxField textRight"));
		List<WebElement> ItemQtyBilling = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String>     QuantityInBilling = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-QuantityField textRight"));
		List<String>     TextOPItemNamesBilling =new ArrayList<>();
		List<String>     TextItemNamesBilling =new ArrayList<>();
		List<String>     TextItemQtyBilling =new ArrayList<>();
		List<String>     TextOUItemNamesBilling =new ArrayList<>();

		TextItemQtyBilling.add(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossWeight);
		TextItemQtyBilling.add(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossWeight);

		Map<String, String> BillingScrnTableData = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);		
		String SubTotalAfterSkus 					= BillingScrnTableData.get("Subtotal");
		String TotalGrossWeightAfterSkus		 	= BillingScrnTableData.get("GrossWeight");
		String TotalNetWeightAfterSkus 				= BillingScrnTableData.get("NetWeight");
		String TaxAfterSkus 						= BillingScrnTableData.get("Tax");
		String TotalAmountAfterSkus 				= BillingScrnTableData.get("TotalAmount");
		String NetTotalAfterSkus 					= BillingScrnTableData.get("NetTotal");
		String LinesCountAfterSkus 					= BillingScrnTableData.get("LinesCount");

		double TotalAmountAfterSkusBillingScrn = Double.parseDouble(TotalAmountAfterSkus.trim().replaceAll("[^\\d.]", ""));
		String PdfTotalAmountAfterSkus = String.valueOf(TotalAmountAfterSkusBillingScrn);
		double TaxAfterSkusBillingScrn = Double.parseDouble(TaxAfterSkus.trim().replaceAll("[^\\d.]", ""));
		String PdfTaxAfterSkus = String.valueOf(TaxAfterSkusBillingScrn);
		double SubTotalAfterSkusBillingScrn = Double.parseDouble(SubTotalAfterSkus.trim().replaceAll("[^\\d.]", ""));
		String PdfSubTotalAfterSkus = String.valueOf(SubTotalAfterSkusBillingScrn);
		double NetTotalAfterSkusBillingScrn = Double.parseDouble(NetTotalAfterSkus.trim().replaceAll("[^\\d.]", ""));
		String PdfNetTotalAfterSkus = String.valueOf(NetTotalAfterSkusBillingScrn);
		double TotalGrossWeightAfterSkusBillingScrn = Double.parseDouble(TotalGrossWeightAfterSkus.trim().replaceAll("[^\\d.]", ""));
		String PdfTotalGrossWeightAfterSkus = String.valueOf(TotalGrossWeightAfterSkusBillingScrn);

		int LinesCountBilling = Integer.parseInt(LinesCountAfterSkus);
		double SubTotalBilling = Double.parseDouble(SubTotalAfterSkus.trim().replaceAll("[^\\d.\\-]", ""));
		double TaxBilling = Double.parseDouble(TaxAfterSkus.trim().replaceAll("[^\\d.\\-]", ""));
		double TotalAmountBilling = Double.parseDouble(TotalAmountAfterSkus.trim().replaceAll("[^\\d.\\-]", ""));

		int ExpectedLinesCountInBillingPge = AllProductRows.size();	
		asrt.assertEquals(ExpectedLinesCountInBillingPge,LinesCountBilling, "Mismatch in Lines count,expected "+ExpectedLinesCountInBillingPge+" but got "+LinesCountBilling+" in the billing screen");

		String FristSkuItemRate = ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.\\-]", "");
		String SecondSkuItemRate = ItemRateBilling.get(1).getText().trim().replaceAll("[^\\d.\\-]", "");

		String PdfFristSkuItemRate = 	ItemRateBilling.get(0).getText().trim().replaceAll("[^\\d.-]", "");
		String PdfSecondSkuItemRate = 	ItemRateBilling.get(1).getText().trim().replaceAll("[^\\d.-]", "");

		double FirstItemRate = Double.parseDouble(FristSkuItemRate);
		double SumSkuItemRate = FirstItemRate;

		System.out.println("Sum of two SKU: "+SumSkuItemRate);	
		String PdfSumSkuItemRate = 	String.valueOf(SumSkuItemRate);

		double ExpectedSubTotalBillingScrn = 0.0;
		ExpectedSubTotalBillingScrn = SumSkuItemRate-(OpTotalAmountValue+OuTotalAmountValue);
		System.out.println("Expected Subtotal : "+ExpectedSubTotalBillingScrn);	

		//tax cal
		double ExpectedTaxAmnt = SumSkuItemRate * 3 / 100;
		String StrEpectedTaxAmnt = String.valueOf(ExpectedTaxAmnt);
		double ExpectedTaxAmount = Double.parseDouble(StrEpectedTaxAmnt.trim().replaceAll("[^\\d.\\-]", ""));

		//total amount
		double ActualTotalAmountInBilling = ExpectedSubTotalBillingScrn + ExpectedTaxAmount;
		asrt.assertEquals(ExpectedTaxAmount,TaxBilling,1.0,"Mismatch in Tax value, expected "+ExpectedTaxAmount+" but got "+TaxBilling+" in the billing screen");

		for (int n=0; n<ItemNamesBilling.size();n++)
		{
			String ItemName=ItemNamesBilling.get(n).getText().trim();
			if(ItemName.contains("OP"))
			{TextOPItemNamesBilling.add(ItemName);}
			if(ItemName.contains("OU"))
			{TextOUItemNamesBilling.add(ItemName);}
			else {TextItemNamesBilling.add(ItemName);}
		}
		Thread.sleep(3000);

		//ITems Sales 
		for(int ItemName=0;ItemName<TextItemNamesBilling.size();ItemName++)
		{
			String ExpectedFull =TextItemNamesBilling.get(ItemName);
			String ActualItem = ItemNamesBilling.get(ItemName).getText().trim();
			asrt.assertEquals(ExpectedFull,ActualItem, "Mismatch in item name, expected "+ExpectedFull+"but got "+ActualItem+"  in the billing screen.");
		}


		for (int j=0; j<TextOPItemNamesBilling.size();j++)
		{
			String ActualItemOP = TextOPItemNamesBilling.get(j).trim();
			asrt.assertEquals(ActualItemOP, UtilityTestData.OPProduct,"Mismatch in Skus item name, expected "+UtilityTestData.OPProduct+" but got "+ActualItemOP+" in the billing screen.");

		}	

		for (int k=0; k<TextOUItemNamesBilling.size();k++)
		{
			String ActualItemOU = TextOUItemNamesBilling.get(k).trim();
			asrt.assertEquals( ActualItemOU, UtilityTestData.OUProduct,"Mismatch in Skus item name, expected "+UtilityTestData.OUProduct+" but got "+ActualItemOU+" in the billing screen.");

		}

		for (int Qty=0; Qty<TextItemQtyBilling.size();Qty++)
		{
			String ActualOty = QuantityInBilling.get(Qty+1).replace("-","").trim();
			String ExpectedQty = TextItemQtyBilling.get(Qty).trim();

			asrt.assertEquals( ActualOty, ExpectedQty,"Mismatch in Skus item name, expected "+ExpectedQty+" but got "+ActualOty+" in the billing screen.");
		}

		for (int l = 0; l <ItemNamesBilling.size(); l++)
		{
			String ItemName=ItemNamesBilling.get(l).getText().trim();
			if(ItemName.contains("OP") || ItemName.contains("OU") )
				break;    
			else {	
				String ItemRate= ItemRateBilling.get(l).getText();
				double ExpectedTotal = Double.parseDouble(ItemRate.replaceAll("[^\\d.]", ""));
				String ActualTotalBilling = ItemRateBilling.get(l).getText().trim();
				double ActualTotal = Double.parseDouble(ActualTotalBilling.replaceAll("[^\\d.]", ""));
				asrt.assertEquals(ExpectedTotal, ActualTotal,1.0,"Mismatch in total amounts (without tax),expected "+ExpectedTotal+" but got "+ActualTotal+" in the Billing screen.");		}
		}

		String FirstItemRateBilling= ItemRateBilling.get(0).getText().replace("-", "").trim();
		String OPItemRateBilling= ItemRateBilling.get(1).getText().replace("-", "").trim();
		String OUItemRateBilling= ItemRateBilling.get(2).getText().replace("-", "").trim();

		Double ActualRateOP22k = Double.parseDouble(OPItemRateBilling.replaceAll("[^\\d.]", "").replace(",", "").trim());
		Double ActualRateOU22k = Double.parseDouble(OUItemRateBilling.replaceAll("[^\\d.]", "").replace(",", "").trim());
		asrt.assertEquals(OpCvalueTotal, ActualRateOP22k,1.0, "Mismatch in total amount for 22K OP, expected "+OpCvalueTotal+" but got "+ActualRateOP22k+" in Billing screen");
		asrt.assertEquals(OuCvalueTotal, ActualRateOU22k,1.0, "Mismatch in total amount for 22K OU, expected "+OuCvalueTotal+" but got "+ActualRateOU22k+" in Billing screen");
		asrt.assertEquals(ExpectedSubTotalBillingScrn,SubTotalBilling,1.0,"Mismatch in Subtotal value, expected "+ExpectedSubTotalBillingScrn+" but got "+SubTotalBilling+"in the Billing screen");	
		asrt.assertEquals(TotalAmountBilling,ActualTotalAmountInBilling,1.0,"Mismatch in Total amount value, expected "+TotalAmountBilling+" but got "+ActualTotalAmountInBilling+" in the Billing screen");

		//Step 16: Click on Save to estimation button
		//Expected Result: Verify Invoice details
		base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_CustomerAdjustment("Save To Estimate"));
		String EstmnNumber = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("CartView_AppBarView_appBarCommandCommand"));
		Thread.sleep(6000);	
		String FirstInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC63_ProformaInvoice");
		Thread.sleep(6000);	
		String ProformaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FirstInvoice+"";
		BigDecimal Rate = new BigDecimal(PdfSumSkuItemRate);
		String InvoiceTotalAmount = PdfTotalAmountAfterSkus;
		String TaxableValue = String.format("%.2f", Rate);
		String GST = PdfTaxAfterSkus;
		String TotalAmnt =PdfSubTotalAfterSkus;
		pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
				ProformaInvoicePath,
				TaxableValue,
				GST,
				InvoiceTotalAmount,
				PdfNetTotalAfterSkus,
				TotalAmnt
				);

		//Step 17:Recall estimate from cashier
		//Expected Result:Verify whether the recalled item is the same as the item in the cart.
		//Cashier Screen after recall *No of Product lines, *Displayed Item Name, *Displayed Quantity, *Displayed TOTAL (without Tax), *Displayed Subtotal, *TAX, *Total Amount
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));		

		//Validation Steps : Verify whether the recalled item is the same as the item in the cart.
		String EstmnNumberRecallPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstmnNumberRecallPge,EstmnNumber,"Mismatch in Estimation No:, expected "+EstmnNumberRecallPge+" but got "+EstmnNumber+"in the Recall Page");	

		//Step 18: Select transaction type as Exchange
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_TransactionTypeExchnge);

		// Step 19: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept")); 
		base.setZoom(driver, 60);
		Thread.sleep(3000);

		// Step 20: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		// Step 21: Click on amount 0.00
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));	
		if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) {
			try { 
				base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} catch (Exception e) {
			}
		}

		// Step 22:  Post exchange invoice
		// Expected Result: Check Exchange invoice details
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Map<String, String> PurchaseBillData =pdfUtils.OldSkuBillPdfValidation(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_PaymentDetails);

		String GrossWt1 = PurchaseBillData.get("GrossWt1");
		double GrossWtDouble1 = Double.parseDouble(GrossWt1);
		String NetWt1 = PurchaseBillData.get("NetWt1");
		double NetWtDouble1 = Double.parseDouble(NetWt1);
		String Rate1 = PurchaseBillData.get("Rate1");
		String ItemTotal1 = PurchaseBillData.get("ItemTotal1");
		Map<String, String> StoneData = new HashMap<>();
		List<String> StoneNamesListFirst = new ArrayList<>();
		for (Map.Entry<String, String> entry : PurchaseBillData.entrySet()) {
			String Key = entry.getKey();

			if (Key.endsWith("Wt1") && !Key.equals("GrossWt1") && !Key.equals("NetWt1")) {
				String StoneName = Key.replace("Wt1", "");
				String StoneWeight = entry.getValue();

				String RateKey = StoneName + "Rate1";
				String StoneRate = PurchaseBillData.getOrDefault(RateKey, "");

				// Store in map
				StoneData.put(StoneName + "_Wt", StoneWeight);
				StoneData.put(StoneName + "_Rate", StoneRate);
				StoneNamesListFirst.add(StoneName);
			}
		}
		for (String stone : StoneNamesListFirst) {
			String StoneWeight1 = StoneData.get(stone + "_Wt");
			String StoneRate1 = StoneData.get(stone + "_Rate");
			System.out.println("Stone: " + stone + ", Weight: " + StoneWeight1 + ", Rate: " + StoneRate1);
			asrt.assertEquals(Double.parseDouble(StoneWeight1),OpQtyIngredientTableStone,1.0,"Mismatch in OP Weight, expected "+StoneWeight1+" but got "+OpQtyIngredientTableStone+" in the Billing screen");
			asrt.assertEquals(Double.parseDouble(StoneRate1),OpRateIngredientTableStone,1.0,"Mismatch in OP Rate, expected "+StoneRate1+" but got "+OpRateIngredientTableStone+" in the Billing screen");

		}

		String InvoiceNumberPdf = PurchaseBillData.get("InvoiceNumber");
		System.out.println("Invoice Number  " + InvoiceNumberPdf);
		asrt.assertEquals(GrossWtDouble1, Double.parseDouble(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossWeight),1.0, "Mismatch in OP Gross weight"+OpQtyIngredientTable+" but got "+GrossWt1+" in Billing screen");
		asrt.assertEquals(GrossWtDouble1, Double.parseDouble(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossWeight),1.0, "Mismatch in OU Gross weight "+OuQtyIngredientTable+" but got "+GrossWt1+" in Billing screen");

		String GrossWt2 = PurchaseBillData.get("GrossWt2");
		String NetWt2 = PurchaseBillData.get("NetWt2");
		String Rate2 = PurchaseBillData.get("Rate2");
		String TotalAmount2 = PurchaseBillData.get("TotalAmount2");
		String ItemTotal2 = PurchaseBillData.get("ItemTotal2");
		Map<String, String> StoneData2 = new HashMap<>();
		List<String> StoneNamesList = new ArrayList<>();
		for (Map.Entry<String, String> Entry : PurchaseBillData.entrySet()) {
			String Key = Entry.getKey();

			if (Key.endsWith("Wt1") && !Key.equals("GrossWt1") && !Key.equals("NetWt1")) {
				String StoneName = Key.replace("Wt1", "");
				String StoneWeight = Entry.getValue();

				String RateKey = StoneName + "Rate1";
				String StoneRate = PurchaseBillData.getOrDefault(RateKey, "");

				// Store in map
				StoneData2.put(StoneName + "_Wt", StoneWeight);
				StoneData2.put(StoneName + "_Rate", StoneRate);
				StoneNamesList.add(StoneName);
			}
		}
		for (String Stone : StoneNamesList) {
			String StoneWeight2 = StoneData2.get(Stone + "_Wt");
			String StoneRate2 = StoneData2.get(Stone + "_Rate");
			System.out.println("Stone: " + Stone + ", Weight: " + StoneWeight2 + ", Rate: " + StoneRate2);
			asrt.assertEquals(Double.parseDouble(StoneWeight2),OuQtyIngredientTableStone,1.0,"Mismatch in OU Weight, expected "+StoneWeight2+" but got "+OuQtyIngredientTableStone+" in the Purchase bill");
			asrt.assertEquals(Double.parseDouble(StoneRate2),OuRateIngredientTableStone,1.0,"Mismatch in OU Rate, expected "+StoneRate2+" but got "+OuRateIngredientTableStone+" in the Purchase bill");
		}

		asrt.assertEquals(Double.parseDouble(GrossWt1), Double.parseDouble(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossWeight),1.0, "Mismatch in OP Gross weight, expected "+GrossWt1+" but got "+OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossWeight+" in Purchase bill ");
		asrt.assertEquals(Double.parseDouble(NetWt1), OpQtyIngredientTable,1.0, "Mismatch in OP Net weight, expected "+NetWt1+" but got "+OpQtyIngredientTable+" in Purchase bill ");
		asrt.assertEquals(Double.parseDouble(Rate1), OpRateIngredientTable,1.0,"Mismatch in OP Rate, expected "+Rate1+" but got "+OpRateIngredientTable+"in the Purchase bill ");	
		asrt.assertEquals(Double.parseDouble(ItemTotal1),OpCvalueTotal,1.0,"Mismatch in OP Cvalue, expected "+ItemTotal1+" but got "+OpCvalueTotal+"in the Purchase bill ");	


		asrt.assertEquals(Double.parseDouble(GrossWt2), Double.parseDouble(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossWeight),1.0, "Mismatch in OU Gross weight, expected "+GrossWt2+" but got "+OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_GrossWeight+" in Purchase bill ");
		asrt.assertEquals(Double.parseDouble(NetWt2), OuQtyIngredientTable,1.0, "Mismatch in OU Net weight, expected "+NetWt2+" but got "+OuQtyIngredientTable+" in Purchase bill ");
		asrt.assertEquals(Double.parseDouble(Rate2), OuRateIngredientTable,1.0,"Mismatch in OU Rate, expected "+Rate2+" but got "+OuRateIngredientTable+"in the Purchase bill ");	
		asrt.assertEquals(Double.parseDouble(ItemTotal2),OuCvalueTotal,1.0,"Mismatch in OP Cvalue, expected "+ItemTotal2+" but got "+OuCvalueTotal+"in the Purchase bill ");	


		String ExchngeAdjustment =PurchaseBillData.get("PaymentDetails");

		//Step 23: Again recall estimate from cashier screen
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
		base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
		Thread.sleep(3000);
		base.setData(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

		// Step 24: Select the Transaction Type as Sales        
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_TransactionTypeSales);

		//Validation Steps : Verify whether the recalled item is the same as the item in the cart.
		String EstmnNumberRecallPgeSales = base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
		asrt.assertEquals(EstmnNumberRecallPgeSales,EstmnNumber,"Mismatch in Estimation No:, expected "+EstmnNumberRecallPge+" but got "+EstmnNumber+"in the Recall page");	

		// Step 25: Click on Process Estimation  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));  
		base.setZoom(driver, 60);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		// Step 26: Choose a sales representative
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));		

		String DiscountBilling = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("DiscountsField", "h4"));
		String PaymentAmountHDFC= base.GetText(NormalSaleGoldandSilverObj.Sel_AmountDue("Amount due"));
		double PaymentAmountHDFCTrimmedValue= Double.parseDouble(PaymentAmountHDFC.trim().replaceAll("[^\\d.]", ""));
		String PdfPaymentAmountHDFC = String.valueOf(PaymentAmountHDFCTrimmedValue);

		Map<String, String> SkuBillingScrnTableData = appUtils.TransactionSKUsLinesVerify(AllSkuList, SkuDetails);
		String SkuNetTotal = SkuBillingScrnTableData.get("TotalAmount");
		String SkuLinesCount = SkuBillingScrnTableData.get("LinesCount");
		String TotalNetWeightRecall 	 =   SkuBillingScrnTableData.get("NetWeight");

		double SkuNetTotalTrimmedValue= Double.parseDouble(SkuNetTotal.trim().replaceAll("[^\\d.]", ""));
		String PdfSkuNetTotal = String.valueOf(SkuNetTotalTrimmedValue);

		//Step 27: Click on amount
		//Step 28: Select payment method(cash/card)
		appUtils.PaymentAfterRecallEstimate(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC63_PaymentMethod);

		//Step 29: Post exchange invoice(sale)
		//Expected Result: Check final invoice details,* save receipt id for future reference
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		String FinalInvoice = pdfUtils.DownloadAndRenameLatestPDF("TC63_SaleInvoice");
		Thread.sleep(3000);		
		String SaleInvoicePath =System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";
		String GrossAmount = TaxableValue;
		String TotalDiscount = Discount;
		String TotalQtyPcs = SkuLinesCount;
		String Adjustment = ExchngeAdjustment;
		String PaymentHDFCCard = PdfPaymentAmountHDFC;
		String TotalDiamond ="";
		String SalesPdfPath = SaleInvoicePath;
		TotalGrossWeight= TotalGrossWeight;
		String NetValue = PdfSkuNetTotal;
		String TotalNetWt = TotalNetWeightRecall;	
		String PdfInvoiceNo = pdfUtils.SaleInvoicePdfAdjustmentValidation(
				SalesPdfPath,
				GrossAmount,
				TotalDiscount,
				TotalDiamond,
				TotalQtyPcs,
				TotalNetWt,
				TotalGrossWeight,
				TaxableValue,
				NetValue,
				PaymentHDFCCard,
				Adjustment,
				AllSkuList,
				SkuDetails);

		System.out.println("PDF INVOICE NO:-"+PdfInvoiceNo);
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

	}


	// <summary>
	// Test Case Title : multiple Own diamond purchase from old sku table
	// Automation ID : TC52
	// Author: Vishnu RR
	// </summary>
	public void TC52_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {



		//Step 1: Login to POS
		Thread.sleep(3000);
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);

		// Step 2: Navigate to the Transaction page
		Thread.sleep(8000);
		//base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
		appUtils.HamBurgerButtonClick("iconShop");

		// Step 3: Select customer and click on add to sale button
		Thread.sleep(1000);
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_CustomerID, UtilityTestData.MenuBarOptions[0]);

		//       4: Select OD
		//		 5.Click on OD own
		//		 6. Select configuration(purity 18K)
		//		 7. Click on Add item button 
		//		 Choose purchase
		//		 8. Enter sku
		//		 9. Click on fetch button
		//		 10. Select QC person
		//		 11. Select Smith person
		//		 12. click on calculate button
		//		 Note: Check displayed values
		//		 13. Click on add to cart button
		//		 Note: Continue step 5- 13
		List<String> Purities = Arrays.asList("18k","18k");
		List<String> ItemNamesInItemDetailsPge = new ArrayList<>();
		List<String> GrossWeight = new ArrayList<>();
		List<String> NetWeight = new ArrayList<>();
		List<String> GoldRate = new ArrayList<>();
		List<String> DiamondWeight = new ArrayList<>();
		List<String> DiamondRate = new ArrayList<>();
		List<String> TotalAmount = new ArrayList<>();
		String ActualTotalAmountInBillingPge ="";

		int j=0;//B

		for(String Purity : Purities) {
			//for(int j=0;j<Purities.size();j++) {
			//	String Purity = Purities.get(j);
			Thread.sleep(3000);
			base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_DesignCode);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldDiamondProduct));	

			base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),Purity);

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


			Thread.sleep(1000);
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_LineNo);
			String RandomSku = appUtils.getRandomSKU(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_SKUForMetalRate);
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"), RandomSku);
			System.out.println(j+"th Sku number fetch:"+RandomSku);
			Thread.sleep(2000);		
			base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));
			Thread.sleep(2000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_QcAndSmithPerson);
			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(4000);
			double CurrentGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
			WebDriverWait HamBurgerWait = new WebDriverWait(driver, Duration.ofSeconds(10));
			try {
				if (HamBurgerWait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"))) != null)
				{
					Thread.sleep(3000);
					base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
				}
			} catch (Exception e) {
				Thread.sleep(3000);
				base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc(
						"height48 centerY","Show or hide navigation bar"));
				Thread.sleep(3000);
				base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
			}

			base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_DesignCode);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldDiamondProduct));	

			base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),Purity);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));


			if (base.isElementPresent(driver, CartChangedAlert)) {		
				try {
					WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
					OkButton.click();
				}
				catch (TimeoutException e) {
				}
			}


			base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
			Thread.sleep(1000);
			base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_LineNo);
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_Setdata);
			Thread.sleep(2000);		
			base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));

			//To Handle the Unexpected Error when fetching SKU 
			WebDriverWait Waits = new WebDriverWait(driver, Duration.ofSeconds(8));
			By WarningAlert = LoginPageObj.Btn_SignInButton("No data found");
			By OKBtn = LoginPageObj.Btn_SignInButton("OK");

			if (base.isElementPresent(driver, WarningAlert)) {		
				try {
					WebElement OkButton = Waits.until(ExpectedConditions.elementToBeClickable(OKBtn));
					OkButton.click();
				}
				catch (TimeoutException e) {
				}
			}
			Thread.sleep(2000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC52_QcAndSmithPerson);
			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(3000);
			double ActualGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
			double ExpectedGoldRateInItemDetailsPge = (double) Math.round(CurrentGoldRateInItemDetailsPge*0.98);

			asrt.assertEquals(ActualGoldRateInItemDetailsPge, ExpectedGoldRateInItemDetailsPge,1.0,"ODPurchaseRate mismatch: Expected value is "+ActualGoldRateInItemDetailsPge+" but got "+ExpectedGoldRateInItemDetailsPge+" in item details page");

			String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));		
			double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
			double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
			String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
			double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);

			asrt.assertEquals(ActualGoldAmountInItemDetailsPge, ExpectedGoldAmountInItemDetailsPge,1.0,"GoldAmount mismatch: "+ActualGoldAmountInItemDetailsPge+" but got "+ ExpectedGoldAmountInItemDetailsPge +" in item details page");		

			String ExpectedDiamondWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"));
			System.out.print("Daimond Weight  " + ExpectedDiamondWeightInItemDetailsPge);
			String ExpectedDiamondRateInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"));
			double ExpectedDiamondAmountInItemDetailsPge = Math.round(Double.parseDouble(ExpectedDiamondWeightInItemDetailsPge)*Double.parseDouble(ExpectedDiamondRateInItemDetailsPge)*100.0)/100.0;
			String DiamondAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdamt"));
			double ActualDiamondAmountInItemDetailsPge = Double.parseDouble(DiamondAmountInItemDetailsPge);

			asrt.assertEquals(ActualDiamondAmountInItemDetailsPge, ExpectedDiamondAmountInItemDetailsPge,1.0, "Diamond amount mismatch: Expected is "+ActualDiamondAmountInItemDetailsPge+" but got "+ExpectedDiamondAmountInItemDetailsPge+" in item details page");

			String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
			double ActualTotalAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
			double ExpectedTotalAmountInItemDetailsPge = ActualGoldAmountInItemDetailsPge+ActualDiamondAmountInItemDetailsPge;

			asrt.assertEquals(ActualTotalAmountInItemDetailsPge, ExpectedTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ActualTotalAmountInItemDetailsPge+" but got "+ExpectedTotalAmountInItemDetailsPge+" in item details page");

			String ItemNameForOD = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
			String ExpectedItemNameInItemDetailsPge = ItemNameForOD.substring(ItemNameForOD.indexOf("-") + 2);

			ItemNamesInItemDetailsPge.add(ExpectedItemNameInItemDetailsPge);
			GrossWeight.add(ExpectedGrossWeightInItemDetailsPge);
			NetWeight.add(ExpectedNetWeightInItemDetailsPge);
			GoldRate.add(Double.toString(ActualGoldRateInItemDetailsPge));
			DiamondWeight.add(ExpectedDiamondWeightInItemDetailsPge);
			DiamondRate.add(ExpectedDiamondRateInItemDetailsPge);
			TotalAmount.add(TotalAmountInItemDetailsPge);

			if (base.isExists(NormalSaleGoldandSilverObj.Ele_Payement("Error"))) {
				base.buttonClick(LoginPageObj.Btn_SignInButton("OK"));
			};
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
			base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

			double SubTotalSum = 0.00;
			List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
			for(int i =0; i < AllProductRows.size() ;i++) {

				//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
				int ExpectedLinesCountInBillingPge = AllProductRows.size();
				int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));

				asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");

				List<WebElement> ItemName = base.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
				String ActualItemNameInBillingPge = ItemName.get(i).getText();
				String ExpectedItemNameInBillingPge = ItemNamesInItemDetailsPge.get(i);

				asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");

				List<WebElement> Quantity = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
				String QuantityInBillingPge = Quantity.get(i).getText().replace("-", "");
				String ActualQuantityInBillingPge = String.format("%.3f", Double.parseDouble(QuantityInBillingPge));
				String ExpectedQuantityInBillingPge = GrossWeight.get(i);

				asrt.assertEquals(ExpectedQuantityInBillingPge, ActualQuantityInBillingPge,"Quantity mismatch: Expected is "+ExpectedQuantityInBillingPge+" but got "+ActualQuantityInBillingPge+" in billing page");

				List<WebElement> TotalWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));
				String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replace("-", "").replace("₹", "").replace(",", "").trim();
				SubTotalSum = SubTotalSum+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
				Thread.sleep(3000);
				String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(TotalAmount.get(i)));

				asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge,"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
			}
			String ExpectedSubTotalInBillingPge = String.format("%.2f",SubTotalSum);
			String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
			asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

			String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
			String ExpectedTaxInBillingPge = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_ODTax;

			asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");

			String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
			ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();

			asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");
		}
		// Step 14: Click on the Amount
		String ActualAmountDueInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("₹", "").replace(",", "").trim();
		String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("₹", "").replace(",", "").trim();
		base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		// Step 15: Click on RTGS/NEFT
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("RTGS/NEFT"));
		base.ClickCondition(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment","iconReturnKey"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		// Step 16: Post invoice
		// Expected: Check final invoice details
		String PaymentDetails = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC61_PaymentDetails;
		Map<String, String> PdfDetails = pdfUtils.ValidateODPdf(
				PaymentDetails, 
				GrossWeight, 
				NetWeight, 
				GoldRate, 
				DiamondWeight, 
				DiamondRate, 
				TotalAmount, 
				ActualTotalAmountInBillingPge, 
				ActualAmountDueInBillingPge);
		String InvoiceNumber = PdfDetails.get("InvoiceNumber");
		String CustomerName = PdfDetails.get("CustomerName");



		// 17. search RTGS payment details
		// 28.verify RTGS entry line updated
		//Expected :In ERP, 1. search RTGS payment details and 2.verify RTGS entry line updated
		Thread.sleep(120000);
		erpUtils.RTGSPaymentDetails(CustomerName,ReceiveAmtFromInBillingPge);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

	}

	//<summary>
	// Test Case Title : own platinum purchase old sku table
	// Automation ID : TC58
	// Author: Sangeetha M S
	// </summary>
	public void TC58_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {
		//Step 1: Login to POS	
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 50);

		// Step 2: Click on Transaction button
		base.setZoom(driver, 60);
		appUtils.HamBurgerButtonClick("iconShop");

		//Prerequisite:  In ERP
		// 1. Search old SKU table
		// 2. Filter design code as OT
		// 3.select sku
		base.newWindow(1);
		driver.get(UtilityTestData.ERPURL);

		base.buttonClick(NormalSaleGoldandSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.SearchOldSKUtable);
		Thread.sleep(5000);
		base.pressKey(NormalSaleGoldandSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
		Thread.sleep(3000);
		base.buttonClick(LoginPageObj.Edt_AlertText("Design Code"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_Name("__FilterField_MGDOldSkuTable_ItemId_ItemId_Input_0_0_input"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("__FilterField_MGDOldSkuTable_ItemId_ItemId_Input_0_0_input"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.OTDetails);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_RunNowERP("__MGDOldSkuTable_ItemId_ApplyFilters_label"));
		Thread.sleep(3000);

		base.buttonClick(LoginPageObj.Edt_AlertText("SKU number"));
		Thread.sleep(3000);
		base.buttonClick(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsObj.Sel_SKUNuber("__FilterField_MGDOldSkuTable_SKUNumber_SKUNumber_0","button-label button-label-dropDown"));
		base.buttonClick(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsObj.Sel_BeginSKU("sysPopup flyoutButton-flyOut layout-root-scope","begins with","2"));
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("__FilterField_MGDOldSkuTable_SKUNumber_SKUNumber_Input_0_0_input"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("__FilterField_MGDOldSkuTable_SKUNumber_SKUNumber_Input_0_0_input"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.SKUNumbers);
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_RunNowERP("__MGDOldSkuTable_SKUNumber_ApplyFilters_label"));
		Thread.sleep(3000);

		String FetchedSkuNumber = base.GetValue(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsObj.Sel_SKU("SKU number", "3"));
		System.out.println("The SKU No: "+FetchedSkuNumber);
		String SkuNumber = FetchedSkuNumber.trim();
		Thread.sleep(10000);

		// Scroll right
		WebElement scrollbar = driver.findElement(By.cssSelector(".ScrollbarLayout_faceHorizontal"));
		Actions actions = new Actions(driver);
		actions.clickAndHold(scrollbar)
		.moveByOffset(1000, 0)
		.release()
		.perform();
		Thread.sleep(1000);

		String Purity = base.GetValue(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsObj.Sel_SKU("Purity", "3"));
		String LowerCasePurity = Purity.toLowerCase();
		System.out.println("The lowercase Purity: "+LowerCasePurity);
		String SkuPurity = LowerCasePurity.trim();
		Thread.sleep(2000);
		base.newWindow(0);
		Thread.sleep(5000);

		//Step 3.Select customer and click on add to sale button
		appUtils.HamBurgerButtonClick("iconShop");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_CustomerNo,UtilityTestData.MenuBarOptions[0]);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//Step 4.Select OT		
		//Step 5.Click on OT 
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
		base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),"OT");
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
		Thread.sleep(5000);
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_ProductType));	

		//Step 6.Select configuration(purity )
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_Purity);

		//Step 7.Click on Add item button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"), SkuNumber);
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_LineNo);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));	
		Thread.sleep(1000);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_QCPerson);
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_SmithPerson);
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(4000);
		double CurrentGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));

		//Step Choose Purchase 
		//Step 8. Enter SKU & click on fetch button
		Thread.sleep(2000);
		base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
		Thread.sleep(2000);
		base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"), SkuNumber);
		base.clearData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"));
		base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_LineNo);
		base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));	
		Thread.sleep(1000);

		//Step 9.Select QC person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_QCPerson);

		//Step 10.Select Smith person
		base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_SmithPerson);

		//Step 11.Click on calculate button
		//Note: Check displayed values
		//Check Purchase rate own Platinium
		//* Check calculation,Note:Billling Screen
		//No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax),Displayed Subtotal,TAX,Total Amount 
		base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
		Thread.sleep(4000);
		List<String> QuantitiesInItemDetailsPge = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("column3 textRight", "h4 ellipsis cell"));	
		List<String> RatesInItemDetailsPge = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("column4 textRight", "h4 ellipsis cell"));
		List<String> CvaluesInItemDetailsPge = base.GetElementTexts(NormalSaleGoldandSilverObj.Btn_CartMoney("column5 textRight", "h4 ellipsis cell"));
		Double TotalAmountInItemDetailsPge = 0.00;
		for (int i =0; i<RatesInItemDetailsPge.size();i++) 
		{			
			double ExpectedCvalueInItemDetailsPge = Double.parseDouble(QuantitiesInItemDetailsPge.get(i))*Double.parseDouble(RatesInItemDetailsPge.get(i));
			double ActualCvalueInItemDetailsPge = Double.parseDouble(CvaluesInItemDetailsPge.get(i));
			TotalAmountInItemDetailsPge = TotalAmountInItemDetailsPge+ActualCvalueInItemDetailsPge;
			asrt.assertEquals(ActualCvalueInItemDetailsPge, ExpectedCvalueInItemDetailsPge,1.0,"Cvalue mismatch: Expected is "+ExpectedCvalueInItemDetailsPge+" but got "+ActualCvalueInItemDetailsPge+" in OT item details page");	    
		}		
		String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
		String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
		String ActualTotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
		String ExpectedTotalAmountInItemDetailsPge = Double.toString(TotalAmountInItemDetailsPge);
		String ActualGrossAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpgrossamt"));

		asrt.assertEquals(ActualTotalAmountInItemDetailsPge, ActualGrossAmountInItemDetailsPge,"Total amount mismatch: "+ActualGrossAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in OT item details page");

		String OTItemName = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
		String ExpectedItemNameInItemDetailsPge = OTItemName.substring(OTItemName.indexOf("-") + 2);	
		double OTTotalAmount = Double.parseDouble(ActualTotalAmountInItemDetailsPge);
		double ActualOTPurchaseRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
		double ExpectedOTPurchaseRateInItemDetailsPge = Math.round((CurrentGoldRateInItemDetailsPge*0.98)*100.0)/100.0;

		asrt.assertEquals(ActualOTPurchaseRateInItemDetailsPge,ExpectedOTPurchaseRateInItemDetailsPge,1.0,"OTPurchaseRate mismatch: Expected value is "+ExpectedOTPurchaseRateInItemDetailsPge+" but got "+ActualOTPurchaseRateInItemDetailsPge+" in OT item details page");

		//Step 12.Click on cart button
		base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
		base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
		base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("bottomLeft grow pad12", "1"));

		//*No of Product lines,*Displayed Item Name,*Displayed Quantity,*Displayed TOTAL (without Tax),*Displayed Subtotal,*TAX,*Total Amount
		List<WebElement> ListOfItemNamesInBillingPge = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("row tillLayout-SelectedLinesFields", "tillLayout-ProductNameField wrapText"));		
		int ExpectedLinesCountInBillingPge = ListOfItemNamesInBillingPge.size();

		int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));
		asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");

		String ActualItemNameInBillingPge = base.GetText(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
		asrt.assertEquals(ExpectedItemNameInItemDetailsPge, ActualItemNameInBillingPge,"Item name mismatch: "+ExpectedItemNameInItemDetailsPge+" but found "+ActualItemNameInBillingPge+" in Billing page");

		String ActualGrossWeightInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5")).replace("-", "");
		double ActualGrossWeight = Double.parseDouble(ActualGrossWeightInBillingPge);
		double ExpectedQty = Double.parseDouble(ExpectedGrossWeightInItemDetailsPge);
		asrt.assertEquals(ExpectedQty, ActualGrossWeight,"Quantity mismatch: "+ExpectedQty+" but found "+ActualGrossWeight+" in Billing page");

		String ActualTotalWithoutTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		double ActualTotalWithoutTax = Double.parseDouble(ActualTotalWithoutTaxInBillingPge);		
		asrt.assertEquals(OTTotalAmount, ActualTotalWithoutTax,"Total without tax mismatch: "+OTTotalAmount+" but found "+ActualTotalWithoutTax+" in Billing page");

		String ExpectedSubTotalInBillingPge = String.valueOf(ActualTotalWithoutTax);
		String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualSubTotal = String.valueOf(Double.parseDouble(ActualSubTotalInBillingPge));	
		asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotal,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotal+" in Billing page");

		String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ExpectedTaxInBillingPge = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_OTTax;
		asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"OT Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in Billing page");

		String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;	
		String ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ActualTotalAmount = String.valueOf(Double.parseDouble(ActualTotalAmountInBillingPge));	
		asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmount,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmount+" in Billing page");

		//Step 13. Click on amount
		String ExpectedAmountDueFromBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("\u20B9", "").replace(",", "").trim();
		String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("\u20B9", "").replace(",", "").trim();
		base.ClickCondition(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

		//Step 14. Select payment method as RTGS/NEFT
		base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_PaymentDetails));
		base.buttonClick(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment","iconReturnKey"));
		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		//Step 15. Post the invoice
		String ExpectedGrossWeightFromBillingPge = ExpectedGrossWeightInItemDetailsPge;
		String ExpectedOTPurchaseRateFromItemDetailsPge = String.format("%.2f",ActualOTPurchaseRateInItemDetailsPge);
		String OTItemTotal =ActualTotalAmountInItemDetailsPge;
		String OTTotal =ActualTotalAmountInItemDetailsPge;
		String PaymentDetailsForOT =ActualTotalAmountInItemDetailsPge;	
		Map<String, String> OgPurchaseBill = pdfUtils.ValidateOTPdf(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC58_PaymentDetails,
				ExpectedGrossWeightFromBillingPge,
				QuantitiesInItemDetailsPge,
				RatesInItemDetailsPge,
				OTItemTotal,
				OTTotal,
				PaymentDetailsForOT);

		//Expected :In ERP, 1. search RTGS payment details and 2.verify RTGS entry line updated
		String CustomerName = pdfUtils.ExtractCustomerNameFromSaleInvoice();
		erpUtils.RTGSPaymentDetails(CustomerName,ReceiveAmtFromInBillingPge);
		pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
	}
	
	// <summary>
		// Test Case Title : Multiple Own diamond exchange from old SKU table
		// Automation ID : TC51
		// Author: Christy Reji
		// </summary>
		public void TC51_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {

			//Step 1: Login to POS
			Thread.sleep(3000);
			login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
			base.setZoom(driver, 70);

			// Step 2: Click on Transaction button
			appUtils.HamBurgerButtonClick("iconShop");


			// Step 3: Select customer and click on add to estimation button
			appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_CustomerID, UtilityTestData.MenuBarOptions[5]);

			//Scan a SKU and click on add to cart button
			Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
			List<Double> AllRates                 = new ArrayList<>();
			List<String> ExpectedItemNames        = new ArrayList<>();
			List<String> ItemQuantity             = new ArrayList<>();
			List<String> PurityOfItems            = new ArrayList();

			SKUResult  UncutSku = appUtils.GetOGSaleSku(
					OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_TransferType,
					OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_FromCounterUncut,
					OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_MetalTypeGold,
					OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_Weight,
					OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_SaleProductCount);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
			List<String> SkuList= UncutSku.PcsRateSKU;
			appUtils.SKUIngridentTableValues(SkuList.get(0), 1, ScannedSkuDataMap);

			for (int i = 0; i < SkuList.size(); i++)
			{
				String  CurrentSkuList = SkuList.get(i);
				int SkuCount           = i + 1;
				ExpectedItemNames.add(ScannedSkuDataMap.get("SKU_" + (i+1) + "_HeaderName"));
				ItemQuantity.add(ScannedSkuDataMap.get("SKU_" + (i+1) + "_GrossWeight"));
				PurityOfItems.add(ScannedSkuDataMap.get("SKU_" + (i+1) + "_Purity"));

				for (int ItemIndex = 1; ; ItemIndex++) 
				{
					String Rate = "SKU" + SkuCount + "_ITEM" + ItemIndex + "_RATE";
					if (ScannedSkuDataMap.containsKey(Rate)) 
					{
						try 
						{
							double SkuRate = Double.parseDouble(ScannedSkuDataMap.get(Rate));
							AllRates.add(SkuRate);
						}
						catch (NumberFormatException e) 
						{
							System.out.println("Rate not fetched");					
						}
					} 
					else{break; }
				}
				ScannedSkuDataMap.put("SKU_" + SkuCount + "_AllRates", AllRates.toString());
			}
			double ExpectedGoldRate22k   = 0;
			double ExpectedGoldRate18k   = 0;
			String Purity                = PurityOfItems.get(0);
			double ExchangeRate          =  AllRates.get(0);
			if (Purity.equalsIgnoreCase("18k"))
			{
				double ConvertedGoldRate = (ExchangeRate * 22) / 18;
				ExpectedGoldRate22k      = Math.round(ConvertedGoldRate);
				ExpectedGoldRate18k      =ExchangeRate;
			}
			else
			{
				ExpectedGoldRate22k      = Math.round(ExchangeRate);
				ExpectedGoldRate18k      = (ExchangeRate * 18) / 22;
			}

			Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
			String SubTotalSale         = TransactionDataLine.get("Subtotal").replace("\u20B9", "").replace(",", "").trim();
			String DiscountSale         = TransactionDataLine.get("Discount");
			String TotalGrossWeightSale = TransactionDataLine.get("GrossWeight");
			String TotalNetWeightSale   = TransactionDataLine.get("NetWeight");
			String TaxSale              = TransactionDataLine.get("Tax").replace("\u20B9", "").replace(",", "").trim();
			String TotalAmountSale      = TransactionDataLine.get("TotalAmount").replace("\u20B9", "").replace(",", "").trim();
			String NetTotalSale         = TransactionDataLine.get("NetTotal").replace("\u20B9", "").replace(",", "").trim();
			String LinesCountSale       = TransactionDataLine.get("LinesCount");

			mathUtils.ValidateTransactionLineCalculation(TransactionDataLine, ScannedSkuDataMap);

			// Step 4: Select OD
			Thread.sleep(5000);
			base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.ClickClearEnter(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.ODProduct);
			Thread.sleep(2000);			
			base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_CustomerSearch("Products"));

			// Step 5: Click on OD own
			Thread.sleep(5000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldDiamondProduct));	

			// Step 6: Select configuration(purity 18K)		
			base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_Purity);

			// Step 7: Click on Add item button
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

			// Step 9: Enter SKU and click on fetch button
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), "1");
			base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_OldSkuTable_SKU1); //1205
			base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));
			Thread.sleep(3000);

			//Step 10: Select QC person
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),UtilityTestData.QCAndSmithPerson);

			//Step 11: Select Smith person
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),UtilityTestData.QCAndSmithPerson); 

			//Step 12: click on calculate button
			//Expected Result:  Check Exchange rate(Board rate ) 
			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(3000);
			base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

			//Check Exchange rate own diamond(Validation)
			Thread.sleep(3000);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			double ActualODRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));	
			if (OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_Purity.equalsIgnoreCase("18k"))
			{
				asrt.assertEquals( Math.round(ActualODRateInItemDetailsPge),ExpectedGoldRate18k,1,"Exchange rate (Board rate)18k mismatch: Expected exchange rate is " + ExpectedGoldRate18k + " but got " + Math.round(ActualODRateInItemDetailsPge) +" in OD item details page");
			}
			else
			{
				asrt.assertEquals( Math.round(ActualODRateInItemDetailsPge),ExpectedGoldRate22k,1,"Exchange rate (Board rate)22k mismatch: Expected exchange rate is " +ExpectedGoldRate22k + " but got " + Math.round(ActualODRateInItemDetailsPge) +" in OD item details page");
			}
			Thread.sleep(3000);
			String ExpectedGrossWeightInItemDetailsPge1    = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			String ODtItemName                             = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
			String ExpectedItemNameInOgtItemDetailsPge1    = ODtItemName.substring(ODtItemName.indexOf("-") + 2);
			
			//cvalue
			String ExpectedNetWeightInItemDetailsPge       = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));
			double NetWeightInItemDetailsPge               = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
			double ExpectedGoldAmountInItemDetailsPge      = Math.round(NetWeightInItemDetailsPge * ActualODRateInItemDetailsPge * 100.0) / 100.0;
			String GoldAmountInItemDetailsPge              = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
			double ActualGoldAmountInItemDetailsPge        = Double.parseDouble(GoldAmountInItemDetailsPge);
			asrt.assertEquals(ExpectedGoldAmountInItemDetailsPge, ActualGoldAmountInItemDetailsPge,1.0,"GoldAmount mismatch: "+ExpectedGoldAmountInItemDetailsPge+" but got "+ActualGoldAmountInItemDetailsPge+" in item details page");		

			String ExpectedDiamondWeightInItemDetailsPge   = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdwt"));
			String ExpectedDiamondRateInItemDetailsPge     = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdrate"));
			double ExpectedDiamondAmountInItemDetailsPge   = Math.round(Double.parseDouble(ExpectedDiamondWeightInItemDetailsPge)*Double.parseDouble(ExpectedDiamondRateInItemDetailsPge)*100.0)/100.0;
			String DiamondAmountInItemDetailsPge           = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpdmdamt"));
			double ActualDiamondAmountInItemDetailsPge     = Double.parseDouble(DiamondAmountInItemDetailsPge);
			asrt.assertEquals(ExpectedDiamondAmountInItemDetailsPge, ActualDiamondAmountInItemDetailsPge,1.0, "Diamond amount mismatch: Expected is "+ExpectedDiamondAmountInItemDetailsPge+" but got "+ActualDiamondAmountInItemDetailsPge+" in item details page");

			String ExpectedGrossWeightInItemDetailsPge     = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			String TotalAmountInItemDetailsPge             = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt"));
			double ActualTotalAmountInItemDetailsPge       = Double.parseDouble(TotalAmountInItemDetailsPge);
			double ExpectedTotalAmountInItemDetailsPge     = ActualGoldAmountInItemDetailsPge+ActualDiamondAmountInItemDetailsPge;
			asrt.assertEquals(ExpectedTotalAmountInItemDetailsPge, ActualTotalAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedTotalAmountInItemDetailsPge+" but got "+ActualTotalAmountInItemDetailsPge+" in item details page");

			//Step 13: Click on add to cart button
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
			base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
			Thread.sleep(2000);

			//Repeat step 5 to 14
			//Select OD,Click on OD own,Select configuration(purity 18K),Click on Add item button
			//Enter SKU and click on fetch button,Select QC,smith person,click on calculate button
			Thread.sleep(5000);
			base.excuteJsClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.ClickClearEnter(NormalSaleGoldandSilverObj.Edt_Name("Search"),UtilityTestData.ODProduct);
			Thread.sleep(2000);			
			base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_CustomerSearch("Products")); 
			Thread.sleep(5000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldDiamondProduct));	
			base.excuteJsClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_Purity);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));
			Thread.sleep(1000);
			base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"), "1");
			base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_OldSkuTable_SKU2); //1028
			base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));
			Thread.sleep(3000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),UtilityTestData.QCAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),UtilityTestData.QCAndSmithPerson); 
			Thread.sleep(3000);
			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));

			Thread.sleep(3000);
			String ExpectedGrossWeightInItemDetailsPge2    = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			String ExpectedItemNameInOgtItemDetailsPge2    = ODtItemName.substring(ODtItemName.indexOf("-") + 2);
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));
			base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));

			//Cart has changed warning handling
			WebDriverWait Wait1  = new WebDriverWait(driver, Duration.ofSeconds(8));
			By CartChangedAlert1 = NormalSaleGoldandSilverObj.Ele_CustomerAdjustment("cart has changed");
			By OkBtn1            = LoginPageObj.Btn_SingnIn("DefaultMessageDialogButton");

			if (base.isElementPresent(driver, CartChangedAlert1)) {		
				try {
					WebElement OkButton = Wait1.until(ExpectedConditions.elementToBeClickable(OkBtn1));
					OkButton.click();
				} 
				catch (TimeoutException e) {
				} 
			}
			Thread.sleep(1000);
			//Repeated step ends

			Map<String, String> TransactionDataLineOD = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
			String SubTotalOD         = TransactionDataLineOD.get("Subtotal").replace("\u20B9", "").replace(",", "").trim();
			String DiscountOD         = TransactionDataLineOD.get("Discount");
			String TotalGrossWeightOD = TransactionDataLineOD.get("GrossWeight");
			String TotalNetWeightOD   = TransactionDataLineOD.get("NetWeight");
			String TaxOD              = TransactionDataLineOD.get("Tax").replace("\u20B9", "").replace(",", "").trim();
			String TotalAmountOD      = TransactionDataLineOD.get("TotalAmount").replace("\u20B9", "").replace(",", "").trim();
			String NetTotalOD         = TransactionDataLineOD.get("NetTotal").replace("\u20B9", "").replace(",", "").trim();
			String LinesCountOD       = TransactionDataLineOD.get("LinesCount");

			//Step 14: Save to estimate
			base.buttonClick(LoginPageObj.Edt_AlertText("Billing"));
			base.buttonClick(LoginPageObj.Edt_AlertText("Save To Estimate"));
			String EstmnNumber=base.GetValue(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Estimation no"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAdd"));

			//Verify the invoice details
			String TaxableValue        = SubTotalSale;
			String TotalAmnt           = SubTotalOD;
			String Gst                 = TaxSale;
			String InvoiceTotal        = TotalAmountOD;
			String NetTotal            = NetTotalOD;
			String ProformaPdfName     = pdfUtils.DownloadAndRenameLatestPDF("TC51_ProformaInvoice");
			String ProFormaInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+ProformaPdfName+"";
			pdfUtils.ProFormaInvoicePdfWithOldGoldNegativeValue(
					ProFormaInvoicePath,
					TaxableValue,
					Gst,
					InvoiceTotal,
					NetTotal,
					TotalAmnt
					);


			//Step 15:Recall estimate from cashier 
			//Expected : Verify whether the recalled item is the same as the item in the cart.
			//Note:Cashier Screen after recall,No of Product lines,Displayed Item Name,Displayed Quantity,Displayed TOTAL (without Tax)
			//Displayed Subtotal,TAX,Total Amount
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
			base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
			base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
			base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));

			// Validation 
			List<String> ExpectedOGItemNames = Arrays.asList(
					ExpectedItemNameInOgtItemDetailsPge1,
					ExpectedItemNameInOgtItemDetailsPge2
					);

			List<String> ExpectedGrossWeights = Arrays.asList(
					ExpectedGrossWeightInItemDetailsPge1,
					ExpectedGrossWeightInItemDetailsPge2
					);
			appUtils.RecallEstimateItemValidation(
					ExpectedItemNames,        
					ItemQuantity,             
					ExpectedOGItemNames,      
					ExpectedGrossWeights,    
					Double.parseDouble(SubTotalOD),
					Double.parseDouble(TaxOD),
					Double.parseDouble(TotalAmountOD),
					Integer.parseInt(LinesCountOD),
					"OD"                      
					);

			// Step 16:Select transaction type as Exchange
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_TransactionTypeExchange);

			//Step 17: Click on process estimation button
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

			//Step 18: Choose a sales representative
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

			//Step 19: Click on amount 0.00
			Map<String, String> TransactionDataLineODOnly = appUtils.TransactionSKUsLinesVerify(SkuList, ScannedSkuDataMap);
			String SubTotalODOnly         = TransactionDataLineODOnly.get("Subtotal").replace("\u20B9", "").replace(",", "").replace("-", "").trim();
			String TotalGrossWeightODOnly = TransactionDataLineODOnly.get("GrossWeight").replace("-", "");
			String TotalNetWeightODOnly   = TransactionDataLineODOnly.get("NetWeight");
			String TaxODOnly              = TransactionDataLineODOnly.get("Tax").replace("\u20B9", "").replace(",", "").trim();
			String TotalAmountODOnly      = TransactionDataLineODOnly.get("TotalAmount").replace("\u20B9", "").replace(",", "").replace("-", "").trim();

			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Amnt("h1"));
			if (base.isElementPresent(driver,NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"))) 
			{
				try 
				{ 
					base.excuteJsClick(NormalSaleGoldandSilverObj.Btn_Estimation("cancelButton primaryButton"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				} 
				catch (Exception e) {}
			}
			//Step 20: Post exchange invoice
			//Expected : Check the invoice details
			Map<String, String> PurchaseBillData = pdfUtils.OGPdfValidation("Adjustment");
			String ExchngeAdjustment             = PurchaseBillData.get("PaymentDetails").trim();
			String GrossWeighProd1               = PurchaseBillData.get("GrossWt1");
			String PaymentDetailsFromInvoice     = PurchaseBillData.get("PaymentDetails").trim();

			asrt.assertEquals(ExchngeAdjustment, SubTotalODOnly, "Mismatch in value ODPdf adjustment:"+ExchngeAdjustment+"but got" +SubTotalODOnly+"from billing page");
			asrt.assertEquals(GrossWeighProd1, ExpectedGrossWeightInItemDetailsPge1,"Mismatch in value ODPdf adjustment:"+GrossWeighProd1+"but got" +ExpectedGrossWeightInItemDetailsPge1+"rom billing page");
			asrt.assertEquals(PaymentDetailsFromInvoice, TotalAmountODOnly,"Mismatch in value ODPdf adjustment:"+PaymentDetailsFromInvoice+"but got" +TotalAmountODOnly+"from billing page");

			//Step 21:Again recall estimate from cashier screen
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Cash("tab-button-ButtonGrid2","text semilight primaryFontColor"));
			base.buttonClick(LoginPageObj.Edt_AlertText("Recall Estimate"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
			base.ClickClearEnter(NormalSaleGoldandSilverObj.Ele_EstmnNumber("Filter"),EstmnNumber);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell",EstmnNumber));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconGo"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

			//Step 22: Select transaction type as sales
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));	
			base.buttonClick(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_BankProofType("Transaction type"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC51_TransactionTypeSales);

			//Step 23: Click on process estimation button
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Estimation("win-disposable win-command AppBarCommand iconAccept"));

			//Step 24: Choose a sales representative
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));
			appUtils.HandleSpecifyRateModalInRecall();

			//Step 25 : click on amount
			//Step 26: Select payment method method (cash/card)
			String PaymentAmount = appUtils.PaymentAfterRecallEstimate("HDFC");
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));

			//Step 27 Post invoice
			//Expected : Check final invoice details
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("PRINT"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			String FinalInvoice    = pdfUtils.DownloadAndRenameLatestPDF("TC51_FinalSaleInvoice");
			Thread.sleep(3000);		
			String SaleInvoicePath = System.getProperty("user.dir") + "\\Invoices\\"+FinalInvoice+"";
			String GrossAmount     = SubTotalSale;
			String TotalDiscount   = DiscountSale;
			String TotalQtyPcs     = LinesCountSale;
			String Adjustment      = ExchngeAdjustment;
			String PaymentHDFCCard = PaymentAmount;
			String SalesPdfPath    = SaleInvoicePath;
			TotalNetWeightSale     = TotalNetWeightSale;
			TotalGrossWeightSale   = TotalGrossWeightSale;
			String NetValue        = NetTotalSale;
			String PdfInvoiceNo    = pdfUtils.SaleInvoicePdfAdjustmentValidation(
					SalesPdfPath,
					GrossAmount,
					TotalDiscount,
					"",
					TotalQtyPcs,
					TotalNetWeightSale,
					TotalGrossWeightSale,
					TaxableValue,
					NetValue,
					PaymentHDFCCard,
					Adjustment,
					SkuList,
					ScannedSkuDataMap
					);
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Btn_Close("primaryButton","Close"));
			pdfUtils.DeleteAllPDFFilesInInvoiceFolder();
		}
		
		// <summary>
		// Test Case Title : own uncut purchase from old SKU table
		// Automation ID : TC60
		// Author: Nandagopan
		// </summary>

		//Prerequsite
		//1. Old Exchange through (SKU table), take the receipt number or sku from ERP

		//	 In ERP
		//	 1. Search old SKU table
		//	 2. Filter design code as OU
		//	 3.select sku

		public void TC60_OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions() throws Exception {
			//Step 1: Login to POS
			Thread.sleep(3000);
			login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
			base.setZoom(driver, 60);

			// Step 2: Click on to the Transaction page
			Thread.sleep(8000);
			//base.excuteJsClick(LoginPageObj.Ele_ErrorMessage("width48 height48 buttonIcon center iconShop"));
			appUtils.HamBurgerButtonClick("iconShop");

			// Step 3: Select customer and click on add to sale button
			Thread.sleep(1000);
			appUtils.SearchByCustomerID(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_CustomerID, UtilityTestData.MenuBarOptions[0]);

			List<String> Purities = Arrays.asList("22k");
			List<String> ItemNamesInItemDetailsPge = new ArrayList<>();
			List<String> GrossWeight = new ArrayList<>();
			List<String> NetWeight = new ArrayList<>();
			List<String> GoldRate = new ArrayList<>();
			List<String> UncutWeight = new ArrayList<>();
			List<String> UncutRate = new ArrayList<>();
			List<String> TotalAmount = new ArrayList<>();
			String ActualTotalAmountInBillingPge ="";

			

			Thread.sleep(3000);
			base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));
			base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_DesignCode);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldUncutOwnProduct));	

			base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_OUPurity);

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
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_LineNo);
			String RandomSku = appUtils.getRandomSKU(OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_SKUForMetalRate);
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"), RandomSku);
			Thread.sleep(2000);		
			base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));
			Thread.sleep(2000);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_QcAndSmithPerson);
			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));


			Thread.sleep(4000);
			double CurrentGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
			WebDriverWait HamBurgerWait = new WebDriverWait(driver, Duration.ofSeconds(10));
			try {
				if (HamBurgerWait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"))) != null)
				{
					Thread.sleep(3000);
					base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
				}
			} catch (Exception e) {
				Thread.sleep(3000);
				base.buttonClick(NormalSaleGoldandSilverObj.Btn_abc(
						"height48 centerY","Show or hide navigation bar"));
				Thread.sleep(3000);
				base.excuteJsClick(LoginPageObj.Btn_SingnIn("CART_ITEM_ID"));
			}

			//Step 4.Select OU
			base.buttonClick(NormalSaleGoldandSilverObj.Edt_Name("Search"));

			//Step 5.Click on OU own
			base.setData(NormalSaleGoldandSilverObj.Edt_Name("Search"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_DesignCode);
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SearchList("Products"));
			Thread.sleep(2000);
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_OP("dataListLine",UtilityTestData.OldUncutOwnProduct));	
			base.buttonClick(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"));

			//Step 6. Select configuration(purity 22K)
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_Configuration("col grow"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_OUPurity);

			//	Step 7. Click on Add item button
			// Note: click on purchase button
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Add item"));

			if (base.isElementPresent(driver, CartChangedAlert)) {		
				try {
					WebElement OkButton = Wait.until(ExpectedConditions.elementToBeClickable(OkBtn));
					OkButton.click();
				}
				catch (TimeoutException e) {
				}
			}

			base.scrollToElement(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));
			Thread.sleep(1000);
			base.excuteJsClick(NormalSaleGoldandSilverObj.Ele_Name("NPurchase"));

			//	Step 8. Enter SKu
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogpreflineno"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_LineNo);
			base.setData(NormalSaleGoldandSilverObj.Ele_Name("ogprefskuno"), OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_Setdata);
			Thread.sleep(2000);		

			//	Step 9. Click on fetch button
			base.buttonClick(LoginPageObj.Btn_SignInButton("Fetch"));

			//To Handle the Unexpected Error when fetching SKU 
			WebDriverWait Waits = new WebDriverWait(driver, Duration.ofSeconds(8));
			By WarningAlert = LoginPageObj.Btn_SignInButton("No data found");
			By OKBtn = LoginPageObj.Btn_SignInButton("OK");

			if (base.isElementPresent(driver, WarningAlert)) {		
				try {
					WebElement OkButton = Waits.until(ExpectedConditions.elementToBeClickable(OKBtn));
					OkButton.click();
				}
				catch (TimeoutException e) {
				}
			}

			Thread.sleep(2000);
			//	 Step 10.Select QC person
			//	 Step 11.Select Smith person
			//Expected Condition: Check Purchase rate own Uncut

			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("qcInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_QcAndSmithPerson);
			base.selectorByVisibleText(NormalSaleGoldandSilverObj.Sel_DepositType("smInput"),OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_QcAndSmithPerson);

			//	Step 12. click on calculate button
			base.scrollToElementtoCenter(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			base.buttonClick(NormalSaleGoldandSilverObj.Btn_Deposit("Calculate"));
			Thread.sleep(3000);
			double ActualGoldRateInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetrate")));
			double ExpectedGoldRateInItemDetailsPge = (double) Math.round(CurrentGoldRateInItemDetailsPge*0.98);


			asrt.assertEquals(ActualGoldRateInItemDetailsPge, ExpectedGoldRateInItemDetailsPge,1.0,"OPurchaseRate mismatch: Expected value is "+ActualGoldRateInItemDetailsPge+" but got "+ExpectedGoldRateInItemDetailsPge+" in item details page");

			String ExpectedNetWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetwt"));		
			double NetWeightInItemDetailsPge = Double.parseDouble(ExpectedNetWeightInItemDetailsPge);
			double ExpectedGoldAmountInItemDetailsPge = Math.round(NetWeightInItemDetailsPge * ActualGoldRateInItemDetailsPge * 100.0) / 100.0;
			String GoldAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogpnetamt"));
			double ActualGoldAmountInItemDetailsPge = Double.parseDouble(GoldAmountInItemDetailsPge);

			asrt.assertEquals(ActualGoldAmountInItemDetailsPge, ExpectedGoldAmountInItemDetailsPge,1.0,"GoldAmount mismatch: "+ActualGoldAmountInItemDetailsPge+" but got "+ ExpectedGoldAmountInItemDetailsPge +" in item details page");		

			String ExpectedPreciousWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstonewt"));
			System.out.print(" Precious Stones Weight  " + ExpectedPreciousWeightInItemDetailsPge);
			String ExpectedPreciousRateInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstonerate"));
			//				
			String ExpectedGrossWeightInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("grossWeightogp"));
			String TotalAmountInItemDetailsPge = base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("PrcOgpstoneamt"));
			double ActualPreciousAmountInItemDetailsPge = Double.parseDouble(TotalAmountInItemDetailsPge);
			double ExpectedPreciousAmountInItemDetailsPge = Double.parseDouble(ExpectedPreciousWeightInItemDetailsPge)*Double.parseDouble(ExpectedPreciousRateInItemDetailsPge);

			asrt.assertEquals(ActualPreciousAmountInItemDetailsPge, ExpectedPreciousAmountInItemDetailsPge,1.0, "Total amount mismatch: Expected is "+ExpectedPreciousAmountInItemDetailsPge+" but got "+ActualPreciousAmountInItemDetailsPge+" in item details page");
			double ExpectedTotalAmountinItemDetailsPge =ActualGoldAmountInItemDetailsPge+ActualPreciousAmountInItemDetailsPge;
			double ActualTotalAmountInItemDetailsPge = Double.parseDouble(base.GetValue(NormalSaleGoldandSilverObj.Ele_Name("ogptotalamt")));
			asrt.assertEquals(ActualTotalAmountInItemDetailsPge, ExpectedTotalAmountinItemDetailsPge,1.0,"Total Amount Mismatch: "+ExpectedTotalAmountinItemDetailsPge+" but got "+ ActualTotalAmountInItemDetailsPge +" in item details page");

			String ItemNameForOU = base.GetText(NormalSaleGoldandSilverObj.Ele_SKUHeader("text: SectionHeader"));
			String ExpectedItemNameInItemDetailsPge = ItemNameForOU.substring(ItemNameForOU.indexOf("-") + 2);

			ItemNamesInItemDetailsPge.add(ExpectedItemNameInItemDetailsPge);
			GrossWeight.add(ExpectedGrossWeightInItemDetailsPge);
			NetWeight.add(ExpectedNetWeightInItemDetailsPge);
			GoldRate.add(Double.toString(ActualGoldRateInItemDetailsPge));
			UncutWeight.add(ExpectedPreciousWeightInItemDetailsPge);
			UncutRate.add(ExpectedPreciousRateInItemDetailsPge);
			TotalAmount.add(Double.toString(ActualTotalAmountInItemDetailsPge));		
			base.buttonClick(OldPurchaseExchangeOwnorOtherGoldorSilverSaleObj.Btn_ThreeDots("win-commandingsurface-actionarea win-appbar-actionarea"));

			//Step 13. Click on Add to cart button
			base.buttonClick(LoginPageObj.Edt_Alert("Add to Cart"));
			base.buttonClick(NormalSaleGoldandSilverObj.Ele_SKUNumber("margin0 h5 ellipsis maxWidth220", "1"));

			List<WebElement> AllProductRows = base.GetElement(LoginPageObj.Ele_ErrorMessage("listViewLine expandable"));
			for(int i =0; i < AllProductRows.size() ;i++) {

				//To validate LinesCount, Displayed ItemName, Displayed Quantity, Displayed TOTAL (without Tax), Displayed Subtotal,TAX, Total Amount
				int ExpectedLinesCountInBillingPge = AllProductRows.size();
				int ActualLinesCountInBillingPge = Integer.parseInt(base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("LinesField", "h4")));

				asrt.assertEquals(ExpectedLinesCountInBillingPge, ActualLinesCountInBillingPge,"Lines count mismatch: Expected is "+ExpectedLinesCountInBillingPge+" but got "+ActualLinesCountInBillingPge+" in Billing page");

				List<WebElement> ItemName = base.GetElement(LoginPageObj.Ele_ErrorMessage("tillLayout-ProductNameField wrapText"));
				String ActualItemNameInBillingPge = ItemName.get(i).getText();
				String ExpectedItemNameInBillingPge = ItemNamesInItemDetailsPge.get(i);

				asrt.assertEquals(ExpectedItemNameInBillingPge, ActualItemNameInBillingPge,"Item name mismatch: Expected is "+ExpectedItemNameInBillingPge+" but got "+ActualItemNameInBillingPge+" in billing page");

				List<WebElement> Quantity = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight","h5"));
				String QuantityInBillingPge = Quantity.get(i).getText().replace("-", "");
				String ActualQuantityInBillingPge = String.format("%.3f", Double.parseDouble(QuantityInBillingPge));
				String ExpectedQuantityInBillingPge = GrossWeight.get(i);

				asrt.assertEquals(ExpectedQuantityInBillingPge, ActualQuantityInBillingPge,"Quantity mismatch: Expected is "+ExpectedQuantityInBillingPge+" but got "+ActualQuantityInBillingPge+" in billing page");

				List<WebElement> TotalWithoutTax = base.GetElement(NormalSaleGoldandSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight","h5"));
				String ActualTotalWithoutTaxInBillingPge = TotalWithoutTax.get(i).getText().replace("-", "").replace("₹", "").replace(",", "").trim();
				//					SubTotalSum = SubTotalSum+Double.parseDouble(ActualTotalWithoutTaxInBillingPge);
				Thread.sleep(3000);
				String ExpectedTotalWithoutTaxInBillingPge = String.format("%.2f", Double.parseDouble(TotalAmount.get(i)));

				asrt.assertEquals(ExpectedTotalWithoutTaxInBillingPge, ActualTotalWithoutTaxInBillingPge,"TotalWithoutTax mismatch: Expected is "+ExpectedTotalWithoutTaxInBillingPge+" but got "+ActualTotalWithoutTaxInBillingPge+" in billing page");
			}

			String ExpectedSubTotalInBillingPge = String.format("%.2f",Double.parseDouble(TotalAmount.get(0)));
			String ActualSubTotalInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("SubtotalField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
			asrt.assertEquals(ExpectedSubTotalInBillingPge, ActualSubTotalInBillingPge,"Subtotal mismatch: "+ExpectedSubTotalInBillingPge+" but found "+ActualSubTotalInBillingPge+" in billing page");

			String ActualTaxInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TaxField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();
			String ExpectedTaxInBillingPge = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_OUTax;

			asrt.assertEquals(ExpectedTaxInBillingPge, ActualTaxInBillingPge,"Tax amount mismatch: "+ExpectedTaxInBillingPge+" but found "+ActualTaxInBillingPge+" in billing page");

			String ExpectedTotalAmountInBillingPge = ExpectedSubTotalInBillingPge;
			ActualTotalAmountInBillingPge = base.GetText(NormalSaleGoldandSilverObj.Ele_Lines("TotalAmountField", "h4")).replace("-", "").replace("₹", "").replace(",", "").trim();

			asrt.assertEquals(ExpectedTotalAmountInBillingPge, ActualTotalAmountInBillingPge,"Total Amount mismatch: "+ExpectedTotalAmountInBillingPge+" but found "+ActualTotalAmountInBillingPge+" in billing page");


			// Step 14: Click on the Amount
			String ActualAmountDueInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("-", "").replace("₹", "").replace(",", "").trim();
			String ReceiveAmtFromInBillingPge = base.GetText(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink")).replace("₹", "").replace(",", "").trim();
			base.buttonClick(NormalSalesReturnGoldSilverReturnSaleCounterObj.Ele_Amount("cartView_amountDueLink"));

			// Step 15: Choose Payment Method RTGS/NEFT
			base.buttonClick(NormalSaleGoldandSilverObj.Sel_PaymentMethod("RTGS/NEFT"));
			base.ClickCondition(NormalSaleGoldandSilverObj.Btn_IconReturnKey("paymentView fragment","iconReturnKey"));
			base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

			// Step 16: Post invoice
			//Expected Condition:Verify final Invoice details
			//save receipt id for future reference

			String PaymentDetails = OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactionsTestData.TC60_PaymentDetails;
			Map<String, String> PurchaseBillData = pdfUtils.OldSkuBillPdfValidationCustName("RTGS/NEFT");
			String GrossWt = PurchaseBillData.get("GrossWt1");
			double GrossWtDouble = Double.parseDouble(GrossWt);
			String NetWt = PurchaseBillData.get("NetWt1");
			double NetWtDouble = Double.parseDouble(NetWt);
			String Rate = PurchaseBillData.get("Rate1");
			String ItemTotal = PurchaseBillData.get("ItemTotal1");
			Map<String, String> StoneData = new HashMap<>();
			List<String> StoneNamesListFirst = new ArrayList<>();
			for (Map.Entry<String, String> entry : PurchaseBillData.entrySet()) {
				String Key = entry.getKey();

				if (Key.endsWith("Wt1") && !Key.equals("GrossWt1") && !Key.equals("NetWt1")) {
					String StoneName = Key.replace("Wt1", "");
					String StoneWeight = entry.getValue();
					String RateKey = StoneName + "Rate1";
					String StoneRate = PurchaseBillData.getOrDefault(RateKey, "");
					// Store in map
					StoneData.put(StoneName + "_Wt", StoneWeight);
					StoneData.put(StoneName + "_Rate", StoneRate);
					StoneNamesListFirst.add(StoneName);
				}
			}
			for (String Stone : StoneNamesListFirst) {
				String StoneWeight = StoneData.get(Stone + "_Wt");
				String StoneRate = StoneData.get(Stone + "_Rate");
				System.out.println("Stone: " + Stone + ", Weight: " + StoneWeight + ", Rate: " + StoneRate);

				asrt.assertEquals(Double.parseDouble(StoneWeight),Double.parseDouble(ExpectedPreciousWeightInItemDetailsPge),1.0,"Mismatch in Stone Weight , expected "+Double.parseDouble(ExpectedPreciousWeightInItemDetailsPge)+" but got "+Double.parseDouble(StoneWeight)+" in the Purchase Bill Pdf");
				asrt.assertEquals(Double.parseDouble(StoneRate),Double.parseDouble(ExpectedPreciousRateInItemDetailsPge),1.0,"Mismatch in Stone Rate value, expected "+Double.parseDouble(ExpectedPreciousRateInItemDetailsPge)+" but got "+Double.parseDouble(StoneRate)+" in the Purchase Bill Pdf");
			}

			asrt.assertEquals(GrossWtDouble, Double.parseDouble(ExpectedGrossWeightInItemDetailsPge),1.0, "Mismatch in Gross Weight of first Product, expected "+Double.parseDouble(ExpectedGrossWeightInItemDetailsPge)+" but got "+GrossWtDouble+" in the Purchase Bill Pdf");
			asrt.assertEquals(NetWtDouble, NetWeightInItemDetailsPge,1.0, "Mismatch in Net Weight of first Product, expected "+NetWeightInItemDetailsPge+" but got "+NetWtDouble+" in the Purchase Bill Pdf");
			asrt.assertEquals(Double.parseDouble(Rate), ActualGoldRateInItemDetailsPge,1.0,"Mismatch in Rate of first Product, expected "+ActualGoldRateInItemDetailsPge+" but got "+Double.parseDouble(Rate)+"in the Purchase Bill Pdf");	
			asrt.assertEquals(Double.parseDouble(ItemTotal),ActualTotalAmountInItemDetailsPge,1.0,"Mismatch in Item Total of first Product, expected "+ActualTotalAmountInItemDetailsPge+" but got "+Double.parseDouble(ItemTotal)+"in the Purchase Bill Pdf");

			String TotalAmountPdf = PurchaseBillData.get("TotalAmount");
			String PaymentDetailsPdf = PurchaseBillData.get("PaymentDetails");
			asrt.assertEquals(Double.parseDouble(TotalAmountPdf),ActualTotalAmountInItemDetailsPge,1.0,"Mismatch in Subtotal value, expected "+ActualTotalAmountInItemDetailsPge+" but got "+Double.parseDouble(TotalAmountPdf)+"in the Purchase Bill Pdf");	
			asrt.assertEquals(Double.parseDouble(PaymentDetailsPdf),ActualTotalAmountInItemDetailsPge,1.0,"Mismatch in Total amount value, expected "+ActualTotalAmountInItemDetailsPge+" but got "+Double.parseDouble(PaymentDetailsPdf)+" in the Purchase Bill Pdf");

//			String InvoiceNumber = PurchaseBillData.get("InvoiceNumber");
			String CustomerName = PurchaseBillData.get("BilledToName");

			// search RTGS payment details
			// .verify RTGS entry line updated
			//Expected :In ERP, 1. search RTGS payment details and 2.verify RTGS entry line updated
			Thread.sleep(120000);
			erpUtils.RTGSPaymentDetails(CustomerName,ReceiveAmtFromInBillingPge);
			pdfUtils.DeleteAllPDFFilesInInvoiceFolder();

		}
}
