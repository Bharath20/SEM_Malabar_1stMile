package utilPack;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import testData.CommonData;
import testData.Utility_TestData;
import testPage.Login;

public class ErpUtilities {

	protected WebDriver driver;
	protected BasePge base;
	protected PdfUtilities pdfUtils;
	protected AppUtilities appUtils;
	protected Login login;
	Assert asrt;
	JavascriptExecutor js;

	LoginPage_Obj LoginPageObj= new LoginPage_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();

	public ErpUtilities(BasePge base) {
		this.base   = base;
		this.driver = base.driver;
		this.login  = new Login(this.driver);
		this.pdfUtils = new PdfUtilities(this.base);
		this.appUtils = new AppUtilities(this.base);
	}

	/// <summary>
	/// To Set metal rate in ERP for Gold 24k,22k,18k
	/// Author : Jhoncy,Vishnu
	/// </summary>
	public String SetMetalRateInERP(String SkuGoldRateReturn, String SkuPurityReturn) throws Exception
	{
		double OriginalRate = Double.parseDouble(SkuGoldRateReturn);
		double Purity = 0;

		if (SkuPurityReturn.contains("24")) {
			Purity = 24;
		} else if (SkuPurityReturn.contains("22")) {
			Purity = 22;
		} else if (SkuPurityReturn.contains("18")) {
			Purity = 18;
		} else {
			System.out.println("Unsupported purity: " + SkuPurityReturn);
			Purity = 24; 
		}

		// Convert to 24K equivalent rate
//		double GoldRateAs24K = (OriginalRate / Purity) * 24;
//		String ConvertedMetalRate = String.format("%.2f", GoldRateAs24K + 50);  // Add 50 buffer if needed
//
//		System.out.println("Converted 24K Rate used in ERP: " + ConvertedMetalRate);
		double GoldRateAs24K = (OriginalRate / Purity) * 24;
		long RoundedMetalRate = Math.round(GoldRateAs24K + 50);
		String ConvertedMetalRate = String.valueOf(RoundedMetalRate);
		System.out.println("Converted Metal Rate: " + ConvertedMetalRate);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

		//Step 1.Go to ERP of that specific Legal Entity
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> Tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(Tabs.get(1));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
		driver.navigate().to("https://m365-perf.sandbox.operations.dynamics.com/?cmp=rjal&mi=retaildevices&cls=MGDCSVFileImport_UpdateSKULineItem");

		driver.navigate().refresh();
		//Step 2.Search Metal rate
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), UtilityTestData.SearchMetalRate);
		base.setZoom(driver, 85);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");

		//Step 3. Click on New
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_NewInMetalRate("mgdmetalrate_2_SystemDefinedNewButton"));

		//Step 4.Accounted under as 'Gold'
		//Step 5.Store/Non-store as 'Store'
		//Step 6.Select the required warehouse
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("InventLocationId"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("InventLocationId"), UtilityTestData.WarehouseId);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseCode(UtilityTestData.WarehouseId,"Warehouse"));

		//Step 7.Select the Configuration as "24k"
		//Step 8.Enter the 24k Board Rate in Sales, click Enter
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("EcoResConfiguraionName"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("EcoResConfiguraionName"), UtilityTestData.Purity24);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseCode(UtilityTestData.Purity24,"Configuration"));
		Thread.sleep(500);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("SalesRate"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("SalesRate"), ConvertedMetalRate);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_WarehouseName("SalesRate"), "ENTER");

		//Step 9.Click on OK
		base.buttonClick(LoginPageObj.Edt_Alert("OK"));

		//Step 10. Click on New
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_NewInMetalRate("mgdmetalrate_2_SystemDefinedNewButton"));

		//Step 11.Accounted under as 'Gold'
		//Step 12.Store/Non-store as 'Store'
		//Step 13.Select the required warehouse
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("InventLocationId"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("InventLocationId"), UtilityTestData.WarehouseId);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseCode(UtilityTestData.WarehouseId,"Warehouse"));

		//Step 14.Select the Configuration as "22k"
		//Step 15.Enter the 24k Board Rate in Sales, click Enter
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("EcoResConfiguraionName"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("EcoResConfiguraionName"), UtilityTestData.Purity22);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseCode(UtilityTestData.Purity22,"Configuration"));
		Thread.sleep(500);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("SalesRate"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("SalesRate"), ConvertedMetalRate);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_WarehouseName("SalesRate"), "ENTER");

		//Step 16.Click on OK
		base.buttonClick(LoginPageObj.Edt_Alert("OK"));

		//Step 17. Click on New
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_NewInMetalRate("mgdmetalrate_2_SystemDefinedNewButton"));

		//Step 18.Accounted under as 'Gold'
		//Step 19.Store/Non-store as 'Store'
		//Step 20.Select the required warehouse
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("InventLocationId"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("InventLocationId"), UtilityTestData.WarehouseId);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseCode(UtilityTestData.WarehouseId,"Warehouse"));

		//Step 21.Select the Configuration as "18k"
		//Step 22.Enter the 24k Board Rate in Sales, click Enter
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("EcoResConfiguraionName"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("EcoResConfiguraionName"), UtilityTestData.Purity18);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseCode(UtilityTestData.Purity18,"Configuration"));
		Thread.sleep(500);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("SalesRate"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("SalesRate"), ConvertedMetalRate);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_WarehouseName("SalesRate"), "ENTER");

		//Step 23.Click on OK
		Thread.sleep(500);
		base.buttonClick(LoginPageObj.Edt_Alert("OK"));

		((JavascriptExecutor) driver).executeScript("window.open()");
		driver.switchTo().window(Tabs.get(1));
		driver.navigate().to("https://m365-perf.sandbox.operations.dynamics.com/?cmp=rjal&mi=retaildevices&cls=MGDCSVFileImport_UpdateSKULineItem");

		//step 24.In ERP, Go to Distribution Schedule
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), UtilityTestData.SearchDistributionSchedule);
		Thread.sleep(3000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");

		//Step 25.Filter the job ""N-METALRATE"" and click on ""Run now""
		Thread.sleep(1000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), UtilityTestData.NmetalRate);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), "ENTER");
		base.excuteJsClick(LoginPageObj.Edt_Alert("Run now"));
		base.excuteJsClick(LoginPageObj.Edt_Alert("OK"));

		((JavascriptExecutor) driver).executeScript("window.open()");
		driver.switchTo().window(Tabs.get(1));
		driver.navigate().to("https://m365-perf.sandbox.operations.dynamics.com/?cmp=rjal&mi=retaildevices&cls=MGDCSVFileImport_UpdateSKULineItem");

		boolean jobEnded = false;
		int maxAttempts = 8; // 1 normal + 1 retry

		for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			try {
				// Step 26: Search My Batch Job
				base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
				base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), UtilityTestData.SearchMyBatchJobs);
				Thread.sleep(1000);
				base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");

				// Step 27: Sort by "Scheduled start date/time"
				Thread.sleep(2000);
				By sortColumn = NormalSaleGoldAndSilverObj.Ele_Transaction("public_fixedDataTableCell_cellContent", "Scheduled start date/time");
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(sortColumn)).click();
				base.buttonClick(LoginPageObj.Edt_Alert("Sort newest to oldest"));

				// Step 28: Wait for "Ended" job
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						NormalSaleGoldAndSilverObj.Ele_StatusERP("2", "Ended")));

				jobEnded = true;
				break; // âœ… success, break the loop

			} catch (StaleElementReferenceException stale) {
				System.out.println("Attempt " + attempt + ": Stale element. Retrying after refresh...");
				if (attempt < maxAttempts) {
					driver.navigate().refresh();
					Thread.sleep(3000);
				} else {
					throw new RuntimeException("Stale element not resolved after retries", stale);
				}
			} catch (TimeoutException timeout) {
				System.out.println("Attempt " + attempt + ": Timeout while waiting for 'Ended' job status.");
				if (attempt < maxAttempts) {
					driver.navigate().refresh();
					Thread.sleep(3000);
				} else {
					throw new RuntimeException("Job status 'Ended' not found even after retrying.", timeout);
				}
			}
		}


		driver.switchTo().window(Tabs.get(0));
		return ConvertedMetalRate;

	}

	///<summary>
	/// Method To get invoice no, Total Netweight, Line Count, Net Total... after normal sale
	/// Author : Vishnu Manoj K
	///</summary>
	public Map<String, Object> GetInvoiceDetailsAfterNormalSale(String CustomerNo, List<String> CounterTransfer) throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		Thread.sleep(5000);

		// Step 2: Navigate to the Transaction page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));

		// Step 3: Select customer and click in add to sale button
		appUtils.SearchByCustomerID(CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4: Scan Multiple SKUs
		List<String> AllSkuList = new ArrayList<>();
		for (int J = 0; J + 2 < CounterTransfer.size(); J += 3) {
			List<String> RequiredSkuList = appUtils.SelectMultipleSKUs(
					Integer.parseInt(CounterTransfer.get(J)), "Inter", CounterTransfer.get(J + 1), CounterTransfer.get(J + 2));
			AllSkuList.addAll(RequiredSkuList);
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));
		}

		// Step 5: Extract SKU table data
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		for (int I = 0; I < AllSkuList.size(); I++) {
			appUtils.SKUIngridentTableValues(AllSkuList.get(I), I + 1, ScannedSkuDataMap);
		}

		// Step 6: Extract required values
		double TotalNetWeight = 0.0;
		String FirstSkuPurity = "N/A";
		String FirstSkuGoldRate = "Not Found";
		boolean FoundRg = false;

		double TotalRgWeightInFirstPurity = 0.0;
		double FirstPurity = 0.0;

		Map<String, String> SkuWiseData = new LinkedHashMap<>();

		for (int Index = 0; Index < AllSkuList.size(); Index++) {
			int SkuNumber = Index + 1;

			String NetWeightStr = ScannedSkuDataMap.get("SKU_" + SkuNumber + "_NetWeight");
			try {
				double NetWeight = Double.parseDouble(NetWeightStr);
				TotalNetWeight += NetWeight;
			} catch (Exception Ex) {
				System.out.println(" - Error parsing Net Weight for SKU " + SkuNumber + ": " + NetWeightStr);
			}
			SkuWiseData.put("SKU_" + SkuNumber + "_NetWeight", NetWeightStr);

			String GrossWeight = ScannedSkuDataMap.getOrDefault("SKU_" + SkuNumber + "_GrossWeight", "0.0");
			SkuWiseData.put("SKU_" + SkuNumber + "_GrossWeight", GrossWeight);

			String StoneWeight = ScannedSkuDataMap.getOrDefault("SKU_" + SkuNumber + "_StoneWeight", "0.0");
			SkuWiseData.put("SKU_" + SkuNumber + "_StoneWeight", StoneWeight);

			String SkuTotal = ScannedSkuDataMap.getOrDefault("SKU_" + SkuNumber + "_Total", "0.0");
			SkuWiseData.put("SKU_" + SkuNumber + "_Total", SkuTotal);

			String RgWeightStr = ScannedSkuDataMap.getOrDefault("SKU_" + SkuNumber + "_TotalRGWeight", "0.0");
			double RgWeight = 0.0;
			try {
				RgWeight = Double.parseDouble(RgWeightStr);
			} catch (Exception Ex) {
				System.out.println(" Invalid RG Weight for SKU_" + SkuNumber + ": " + RgWeightStr);
				continue;
			}

			if (RgWeight == 0.0) continue;

			String ThisPurityStr = ScannedSkuDataMap.getOrDefault("SKU_" + SkuNumber + "_Purity", "").replaceAll("(?i)k", "").trim();
			double ThisPurity = 0.0;
			try {
				ThisPurity = Double.parseDouble(ThisPurityStr);
			} catch (Exception Ex) {
				System.out.println("Invalid Purity for SKU_" + SkuNumber + ": " + ThisPurityStr);
				continue;
			}

			if (!FoundRg) {
				FirstPurity = ThisPurity;
				FirstSkuPurity = ThisPurityStr;

				for (String Key : ScannedSkuDataMap.keySet()) {
					if (Key.startsWith("SKU" + SkuNumber + "_ITEM") && Key.endsWith("_ITEMID")) {
						String ItemKeyPrefix = Key.replace("_ITEMID", "");
						String ItemId = ScannedSkuDataMap.get(Key);
						if ("RG".equalsIgnoreCase(ItemId)) {
							FirstSkuGoldRate = ScannedSkuDataMap.getOrDefault(ItemKeyPrefix + "_RATE", "Not Found");
							break;
						}
					}
				}
				FoundRg = true;
			}

			double ThisSkuGoldRate = 0.0;
			for (String Key : ScannedSkuDataMap.keySet()) {
				if (Key.startsWith("SKU" + SkuNumber + "_ITEM") && Key.endsWith("_ITEMID")) {
					String ItemKeyPrefix = Key.replace("_ITEMID", "");
					String ItemId = ScannedSkuDataMap.get(Key);
					if ("RG".equalsIgnoreCase(ItemId)) {
						try {
							ThisSkuGoldRate = Double.parseDouble(
									ScannedSkuDataMap.getOrDefault(ItemKeyPrefix + "_RATE", "0.0")
									.replaceAll("[^0-9.]", ""));
						} catch (Exception Ex) {
							System.out.println(" Invalid RATE for SKU_" + SkuNumber + ": " + ScannedSkuDataMap.get(ItemKeyPrefix + "_RATE"));
						}
						break;
					}
				}
			}

			double FirstGoldRate = 0.0;
			try {
				FirstGoldRate = Double.parseDouble(FirstSkuGoldRate.replaceAll("[^0-9.]", ""));
			} catch (Exception Ex) {
				System.out.println(" Invalid First RG Rate: " + FirstSkuGoldRate);
				continue;
			}

			double ConvertedRgWeight = 0.0;
			if (FirstPurity == ThisPurity && FirstGoldRate == ThisSkuGoldRate) {
				ConvertedRgWeight = RgWeight;
			} else {
				ConvertedRgWeight = (RgWeight * ThisSkuGoldRate) / (FirstGoldRate);
				ConvertedRgWeight = Math.round(ConvertedRgWeight * 1000.0) / 1000.0;
			}

			TotalRgWeightInFirstPurity += ConvertedRgWeight;

			System.out.println("SKU_" + SkuNumber + " RG Weight = " + RgWeight +
					", Purity = " + ThisPurity + ", RG Rate = " + ThisSkuGoldRate +
					" => Converted to First Purity (" + FirstPurity + "K @ " + FirstGoldRate +
					") = " + ConvertedRgWeight);
		}

		System.out.println(" Total RG Weight Converted to First Purity (" + FirstPurity + "K): " + TotalRgWeightInFirstPurity);

		// Step 7: Finalize transaction
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(AllSkuList, ScannedSkuDataMap);
		String SubTotal = TransactionDataLine.get("Subtotal");
		String Discount = TransactionDataLine.get("Discount");
		String TotalGrossWeight = TransactionDataLine.get("GrossWeight");
		String TotalNewNetWeight = TransactionDataLine.get("NetWeight");
		String Tax = TransactionDataLine.get("Tax");
		String TotalAmount = TransactionDataLine.get("TotalAmount");
		String NetTotal = TransactionDataLine.get("NetTotal");
		String LinesCount = TransactionDataLine.get("LinesCount");

		appUtils.TransactionSKUsLinesVerify(AllSkuList, ScannedSkuDataMap);

		String PaymentAmount = appUtils.PaymentAfterRecallEstimate("HDFC");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

		String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("Method_TaxInvoice");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		String TaxInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + TaxInvoiceName;
		String InvoiceNo = pdfUtils.ExtractReceiptNoFrmSaleInvoice(TaxInvoicePath);

		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		// Step 8: Collect returned product names
		List<String> ReturnedProducts = new ArrayList<>();
		for (String Key : ScannedSkuDataMap.keySet()) {
			if (Key.matches("SKU\\d+_ITEM\\d+_ITEMNAME")) {
				ReturnedProducts.add(ScannedSkuDataMap.get(Key));
			}
		}

		// Step 9: Return all collected data
		Map<String, Object> ResultMap = new LinkedHashMap<>();
		ResultMap.put("InvoiceNo", InvoiceNo);
		ResultMap.put("TotalNetWeight", String.format("%.3f", TotalNetWeight));
		ResultMap.put("TotalRgWeight", String.format("%.3f", TotalRgWeightInFirstPurity));
		ResultMap.put("FirstSkuPurity", FirstSkuPurity);
		ResultMap.put("FirstSkuGoldRate", FirstSkuGoldRate);
		ResultMap.put("SKUWiseDetails", SkuWiseData);
		ResultMap.put("SubTotal", SubTotal);
		ResultMap.put("Discount", Discount);
		ResultMap.put("TotalGrossWeight", TotalGrossWeight);
		ResultMap.put("Tax", Tax);
		ResultMap.put("TotalAmount", TotalAmount);
		ResultMap.put("NetTotal", NetTotal);
		ResultMap.put("LinesCount", LinesCount);
		ResultMap.put("ScannedSKUDataMap", ScannedSkuDataMap);
		ResultMap.put("TransactionDataLine", TransactionDataLine);

		return ResultMap;
	}

	/// <summary>
	/// Validates SKU-level financial metrics:
	/// 1. Recalculates ingredient CValue (Qty ï¿½ Rate) and matches with expected.
	/// 2. Verifies GrossWeight by summing ingredient quantities.
	/// 3. Validates MakingValue from NetWeight and MakingRate.
	/// 4. Calculates WastageAmount from NetWeight ï¿½ Rate ï¿½ WastageQty%.
	/// 5. Ensures Total = TotalCValue + Making or Wastage.
	/// 6. Asserts RG item gold rate matches expected based on RemainingReturnWeight logic.
	/// Author: VISHNU MANOJ K
	/// </summary>
	public void ResetRemainingReturnWeight() {
		RemainingReturnWeight = -1;
		FirstPurity = -1;
		FirstRate = -1;
	}
	private static double RemainingReturnWeight = -1;
	private static double FirstPurity = -1; 
	private static double FirstRate = -1; 
	public void ErpSkuIngredientItemCalculation(Map<String, String> SkuIngredientData, int SkuNumber,
			String TotalRgWeightReturn, String FirstSkuPurityReturn,
			String FirstSkuGoldRateReturn, String CurrentGoldRate24K) {

		System.out.println("====== Validating SKU_" + SkuNumber + " ======");

		double CurrentPurity = ParsePurity(SkuIngredientData.getOrDefault("SKU_" + SkuNumber + "_Purity", ""));

		if (FirstPurity < 0) {
			FirstPurity = ParsePurity(FirstSkuPurityReturn);
			FirstRate = ParseDouble(FirstSkuGoldRateReturn);
		}

		double CurrentFirstRate = (CurrentPurity != FirstPurity)
				? Round((FirstRate * CurrentPurity) / FirstPurity)
						: FirstRate;

		double Rate24K = ParseDouble(CurrentGoldRate24K);

		System.out.println("CurrentFirstRate: " + CurrentFirstRate + " FirstRate: " + FirstRate);
		System.out.println("CurrentPurity: " + CurrentPurity + " FirstPurity: " + FirstPurity + " Rate24K: " + Rate24K);

		double AdjustedGoldRate = (CurrentPurity != 24.0)
				? (Rate24K * CurrentPurity / 24.0)
						: Rate24K;

		System.out.printf("Adjusted Gold Rate for %.1fK: %.2f\n", CurrentPurity, AdjustedGoldRate);

		if (RemainingReturnWeight < 0) {
			double RgWeight = ParseDouble(TotalRgWeightReturn);
			RemainingReturnWeight = (CurrentPurity != FirstPurity)
					? Round((RgWeight * FirstRate) / ((FirstRate * CurrentPurity) / FirstPurity))
							: RgWeight;

			System.out.println("Total RG Weight Converted = " + RemainingReturnWeight);
		} else {
			if (CurrentPurity != FirstPurity) {
				RemainingReturnWeight = (RemainingReturnWeight * FirstRate) / ((FirstRate * CurrentPurity) / FirstPurity);
				RemainingReturnWeight = Round(RemainingReturnWeight);
			}
			System.out.println("RemainingReturnWeight already used or calculated: " + RemainingReturnWeight);
		}

		double SumOfCValues = 0.0;
		double SumOfRgQty = 0.0;
		double Rate = 0.0;

		for (String Key : SkuIngredientData.keySet()) {
			if (Key.endsWith("_QTY")) {
				String Base = Key.replace("_QTY", "");
				double Qty = ParseDouble(SkuIngredientData.get(Base + "_QTY"));
				Rate = ParseDouble(SkuIngredientData.get(Base + "_RATE"));
				double ExpectedCValue = ParseDouble(SkuIngredientData.get(Base + "_CVALUE"));
				double CalcCValue = Round(Qty * Rate);

				System.out.printf("CValue for %s: Qty=%.3f, Rate=%.2f, CalcValue=%.2f, ExpValue=%.2f\n",
						Base, Qty, Rate, CalcCValue, ExpectedCValue);

				asrt.assertEquals(CalcCValue, ExpectedCValue, 1.0, "Mismatch in CValue for " + Base);

				String ItemId = SkuIngredientData.getOrDefault(Base + "_ITEMID", "").trim().toUpperCase();
				if ("RG".equals(ItemId)) {
					SumOfCValues += Qty * Rate;
					SumOfRgQty += Qty;
				}
			}
		}

		System.out.println("Total RG CValue Sum: " + SumOfCValues);
		System.out.println("Total RG Qty Sum: " + SumOfRgQty);

		ValidateWeightsAndCharges(SkuIngredientData, SkuNumber, AdjustedGoldRate);

		double ExpectedGoldRate;

		if (RemainingReturnWeight >= SumOfRgQty) {
			ExpectedGoldRate = CurrentFirstRate;
			RemainingReturnWeight = RemainingReturnWeight - SumOfRgQty;
		} else if (RemainingReturnWeight > 0.0) {
			double ExcessQty = SumOfRgQty - RemainingReturnWeight;
			ExpectedGoldRate = ((ExcessQty * AdjustedGoldRate) + (RemainingReturnWeight * CurrentFirstRate)) / SumOfRgQty;
			RemainingReturnWeight = 0.0;
		} else {
			ExpectedGoldRate = AdjustedGoldRate;
		}

		ExpectedGoldRate = Round(ExpectedGoldRate);

		double RgRateInThisSku = GetRgRate(SkuIngredientData, SkuNumber);
		System.out.println("Assertion: RG Rate in SKU UI = " + RgRateInThisSku + " vs Expected = " + ExpectedGoldRate);

		asrt.assertEquals(RgRateInThisSku, ExpectedGoldRate, 1.0, "Mismatch in RG gold rate for SKU_" + SkuNumber);

		FirstRate = CurrentFirstRate;
		FirstPurity = CurrentPurity;

		System.out.println("====== Validation Completed for SKU_" + SkuNumber + " ======\n");
	}


	// --- Utility Methods ---
	private double ParsePurity(String PurityStr) {
		return ParseDouble(PurityStr.replaceAll("(?i)k", "").trim());
	}

	private double ParseDouble(String Str) {
		try {
			return Double.parseDouble(Str.replaceAll("[^\\d.]", ""));
		} catch (Exception e) {
			return 0.0;
		}
	}

	private double Round(double Value) {
		return Math.round(Value * 100.0) / 100.0;
	}

	private double GetRgRate(Map<String, String> Data, int SkuNum) {
		for (String Key : Data.keySet()) {
			if (Key.startsWith("SKU" + SkuNum + "_ITEM") && Key.endsWith("_ITEMID")
					&& "RG".equals(Data.get(Key).trim().toUpperCase())) {
				return ParseDouble(Data.getOrDefault(Key.replace("_ITEMID", "_RATE"), "0"));
			}
		}
		return -1.0;
	}

	private void ValidateWeightsAndCharges(Map<String, String> Data, int SkuNum, double AdjustedRate) {
		double Gross = 0.0;
		String QtyPattern  = "^SKU" + SkuNum + "_ITEM(\\d+)_QTY$";

		for (String Key : Data.keySet()) {

			Matcher M = Pattern.compile(QtyPattern).matcher(Key);
			if (M.find()) {
				int ItemIndex = Integer.parseInt(M.group(1));
				double Qty = ParseDouble(Data.get(Key));
				String Unit = Data.getOrDefault("SKU" + SkuNum + "_ITEM" + ItemIndex + "_UNIT", "").toLowerCase();
				Gross += Unit.contains("ct") ? Qty * 0.2 : Qty;
			}
		}

		double ActualGross = Round(Gross);
		double ExpectedGross = ParseDouble(Data.get("SKU_" + SkuNum + "_GrossWeight"));
		asrt.assertEquals(ActualGross, ExpectedGross, 1.0, "Mismatch in Gross Weight for SKU_" + SkuNum);

		double NetWeight = ParseDouble(Data.get("SKU_" + SkuNum + "_NetWeight"));
		double MakingRate = ParseDouble(Data.getOrDefault("SKU_" + SkuNum + "_MakingRate", "0"));
		double ExpectedMaking = ParseDouble(Data.get("SKU_" + SkuNum + "_MakingValue"));
		String MakingType = Data.getOrDefault("SKU_" + SkuNum + "_MakingType", "");
		double CalcMaking = NetWeight * MakingRate;

		if (MakingType.equals("Total") || MakingType.equals("Pcs")) {
			asrt.assertEquals(MakingRate, ExpectedMaking, 1, "Mismatch in MakingRate for SKU_" + SkuNum);
		} else {
			asrt.assertEquals(CalcMaking, ExpectedMaking, 1, "Mismatch in MakingValue for SKU_" + SkuNum);
		}

		double WastagePercent = ParseDouble(Data.getOrDefault("SKU_" + SkuNum + "_WastageQty", "0"));
		double ExpectedWastage = ParseDouble(Data.get("SKU_" + SkuNum + "_WastageAmount"));
		double CalcWastage = NetWeight * AdjustedRate * WastagePercent / 100.0;

		asrt.assertEquals(CalcWastage, ExpectedWastage, 1.0 , "Mismatch in Wastage Amount for SKU_" + SkuNum);

		double CValue = ParseDouble(Data.get("SKU_" + SkuNum + "_TotalCValue"));
		double ExpectedTotal = ParseDouble(Data.get("SKU_" + SkuNum + "_Total"));
		double ActualTotal = Round(CValue + (ExpectedMaking == 0.0 ? ExpectedWastage : ExpectedMaking));

		asrt.assertEquals(ActualTotal, ExpectedTotal, 1.0, "Mismatch in Total Amount for SKU_" + SkuNum);
	}


	/// <summary>
	/// Validate SKU Count- Compares the number of SKUs shown on the transaction page with the LinesCount from TransactionDataLine.
	/// Validate SKU Names- Ensures each displayed SKU name matches the expected name from ScannedSkuDataMap, ignoring prefix codes.
	/// Validate Gross Weight (Qty)-Confirms the displayed quantity (gross weight) for each SKU matches the expected gross weight.
	/// Validate Total Without Tax (Per SKU)-Asserts that each SKU's displayed total (without tax) matches the expected total value.
	/// Validate Subtotal (Sum of All SKU Totals)-Adds all displayed SKU totals and compares the sum to the Subtotal value from TransactionDataLine.
	/// Validate Tax (3% of Subtotal) -Calculates 3% of the subtotal and verifies it matches the displayed Tax value.
	/// Validate Total Amount (Subtotal + Tax)-Adds subtotal and tax and confirms it equals the displayed TotalAmount.
	/// Author: VISHNU MANOJ K
	/// </summary>
	public void ErpValidateTransactionLineCalculation(Map<String, String> TransactionDataLine, Map<String, String> ScannedSkuDataMap) {
		System.out.println("====== Validation Started for Transaction Page Line ======");

		// Step 1: SKU Count validation
		List<String> SkuNameElements = base.GetElementTexts(
				NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5")
				);
		int ExpectedLinesCount = Integer.parseInt(TransactionDataLine.get("LinesCount").replaceAll("[^\\d]", ""));
		Assert.assertEquals(SkuNameElements.size(), ExpectedLinesCount,
				"After Calculation Value Mismatch in number of SKUs. Actual: " + SkuNameElements.size() + ", Expected: " + ExpectedLinesCount);

		// Step 2: SKU Name Validation
		List<String> ActualSkuNamesOnScreen = SkuNameElements.stream().map(String::trim).collect(Collectors.toList());
		List<String> ExpectedProductNames = new ArrayList<>();
		int TotalSkuCount = (int) ScannedSkuDataMap.keySet().stream()
				.filter(k -> k.endsWith("_SKUName")).count();

		for (int Index = 1; Index <= TotalSkuCount; Index++) {
			String ExpectedSkuName = ScannedSkuDataMap.get("SKU_" + Index + "_SKUName");
			Assert.assertNotNull(ExpectedSkuName, "Expected SKU name is null for SKU_" + Index);
			String[] Split = ExpectedSkuName.trim().split(" - ", 2);
			String ExpectedSkuNameOnly = (Split.length > 1) ? Split[1].trim() : ExpectedSkuName.trim();
			ExpectedProductNames.add(ExpectedSkuNameOnly);
		}

		for (String ExpectedProductName : ExpectedProductNames) {
			System.out.println("Validating presence of expected SKU Name: " + ExpectedProductName);
			Assert.assertTrue(ActualSkuNamesOnScreen.contains(ExpectedProductName),
					"SKU name not found in UI: " + ExpectedProductName);
		}

		// Step 3: Gross Weight Validation
		List<String> ActualSkuNamesGrossWeight = base.GetElementTexts(
				NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5")
				);
		List<String> ActualQuantities = base.GetElementTexts(
				NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5")
				);

		Map<String, Double> ActualSkuNameToQtyMap = new HashMap<>();
		for (int Index = 0; Index < ActualSkuNamesGrossWeight.size(); Index++) {
			String SkuName = ActualSkuNamesGrossWeight.get(Index).trim();
			String QtyStr = ActualQuantities.get(Index).trim().replaceAll("[^\\d.]", "");
			double Qty = Double.parseDouble(QtyStr);
			ActualSkuNameToQtyMap.put(SkuName + "_SKU_" + (Index + 1), Qty);
		}

		for (int Index = 1; Index <= TotalSkuCount; Index++) {
			String ExpectedSkuNameFull = ScannedSkuDataMap.get("SKU_" + Index + "_SKUName");
			Assert.assertNotNull(ExpectedSkuNameFull, "Expected SKU name is null for SKU_" + Index);
			String[] Split = ExpectedSkuNameFull.trim().split(" - ", 2);
			String ExpectedSkuNameOnly = (Split.length > 1) ? Split[1].trim() : ExpectedSkuNameFull.trim();
			String ExpectedQtyStr = ScannedSkuDataMap.get("SKU_" + Index + "_GrossWeight");
			Assert.assertNotNull(ExpectedQtyStr, "Expected GrossWeight is null for SKU_" + Index);
			double ExpectedQty = Double.parseDouble(ExpectedQtyStr.trim().replaceAll("[^\\d.]", ""));
			double ActualQty = ActualSkuNameToQtyMap.get(ExpectedSkuNameOnly + "_SKU_" + Index);

			System.out.println("Asserting GrossWeight for SKU_" + Index + ": Expected = " + ExpectedQty + ", Actual = " + ActualQty);
			Assert.assertEquals(Math.abs(ActualQty), ExpectedQty, 1.00,
					"Mismatch in GrossWeight. Actual: " + ActualQty + ", Expected: " + ExpectedQty + " for SKU_" + Index);
		}

		// Step 4 & 5: Total Without Tax and Subtotal
		List<String> ActualSkuNamesTotal = base.GetElementTexts(
				NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5")
				);
		List<String> ActualTotalElements = base.GetElementTexts(
				NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5")
				);

		double ListSubTotalNoTax = 0.0;
		Map<String, Double> ActualSkuToTotalMap = new HashMap<>();

		for (int Index = 0; Index < ActualSkuNamesTotal.size(); Index++) {
			String SkuName = ActualSkuNamesTotal.get(Index).trim();
			String RawTotal = ActualTotalElements.get(Index).trim();
			String TotalStr = RawTotal.replaceAll("[^\\d.-]", "");
			if (TotalStr.isEmpty()) {
				throw new IllegalArgumentException("Invalid total value for SKU: " + SkuName + " Raw: " + RawTotal);
			}
			double Total = Double.parseDouble(TotalStr);
			ListSubTotalNoTax += Total;
			ActualSkuToTotalMap.put(SkuName + "_SKU_" + (Index + 1), Total);
		}

		for (int Index = 1; Index <= TotalSkuCount; Index++) {
			String ExpectedSkuNameFull = ScannedSkuDataMap.get("SKU_" + Index + "_SKUName");
			Assert.assertNotNull(ExpectedSkuNameFull, "Expected SKU name is null for SKU_" + Index);
			String[] Split = ExpectedSkuNameFull.trim().split(" - ", 2);
			String ExpectedSkuNameOnly = (Split.length > 1) ? Split[1].trim() : ExpectedSkuNameFull.trim();
			String ExpectedTotalStr = ScannedSkuDataMap.get("SKU_" + Index + "_Total");
			Assert.assertNotNull(ExpectedTotalStr, "Expected Total is null for SKU_" + Index);
			double ExpectedTotal = Double.parseDouble(ExpectedTotalStr.trim().replaceAll("[^\\d.]", ""));
			double ActualTotal = ActualSkuToTotalMap.get(ExpectedSkuNameOnly + "_SKU_" + Index);

			System.out.println("Asserting TOTAL (Without Tax) for SKU_" + Index + ": Expected = " + ExpectedTotal + ", Actual = " + ActualTotal);
			Assert.assertEquals(Math.abs(ActualTotal), ExpectedTotal, 1,
					"Mismatch in TOTAL (Without Tax). Actual: " + ActualTotal + ", Expected: " + ExpectedTotal + " for SKU_" + Index);
		}

		// Step 6: Subtotal
		double ExpectedSubtotal = Double.parseDouble(TransactionDataLine.get("Subtotal").replaceAll("[^\\d.]", ""));
		System.out.println("Asserting TOTAL (Without Tax) SUM vs Subtotal:");
		System.out.println("Expected Subtotal = " + ExpectedSubtotal + ", Actual Sum = " + ListSubTotalNoTax);
		Assert.assertEquals(Math.abs(ListSubTotalNoTax), ExpectedSubtotal, 1,
				"Mismatch in total sum. Actual: " + Math.abs(ListSubTotalNoTax) + ", Expected: " + ExpectedSubtotal);

		// Step 7: Tax
		double CalculatedTax = Math.round((ExpectedSubtotal * 0.03) * 100.0) / 100.0;
		double ExpectedTax = Double.parseDouble(TransactionDataLine.get("Tax").replaceAll("[^\\d.]", ""));
		System.out.println("Asserting calculated 3% TAX vs displayed Tax:");
		System.out.println("Expected Tax = " + ExpectedTax + ", Calculated Tax = " + CalculatedTax);
		Assert.assertEquals(Math.abs(CalculatedTax), ExpectedTax, 1.00,
				"Mismatch in calculated Tax. Calculated: " + CalculatedTax + ", Expected: " + ExpectedTax);

		// Step 8: Total Amount
		double CalculatedTotalAmount = Math.round((ExpectedSubtotal + ExpectedTax) * 100.0) / 100.0;
		double ExpectedTotalAmount = Double.parseDouble(TransactionDataLine.get("TotalAmount").replaceAll("[^\\d.]", ""));
		System.out.println("Asserting Total Amount (Subtotal + Tax) with displayed TotalAmount:");
		System.out.println("Expected TotalAmount = " + ExpectedTotalAmount + ", Calculated TotalAmount = " + CalculatedTotalAmount);
		Assert.assertEquals(Math.abs(CalculatedTotalAmount), ExpectedTotalAmount, 1.00,
				"Mismatch in Total Amount. Calculated: " + CalculatedTotalAmount + ", Expected: " + ExpectedTotalAmount);

		System.out.println("====== Validation Completed for Transaction Page Line ======");
	}

	/// <summary>
	/// To verify RTGS PaymentDetails
	/// Author: Jhoncy and Chandana Babu
	/// <summary>
	public void RTGSPaymentDetails(String ActualCustName, String ReceiveAmt) throws Exception
	{
		base.newWindow(1);
		driver.get(UtilityTestData.ERPURL);

		//Step 1:search RTGS payment details
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), UtilityTestData.SearchRTGSPaymentDetails);
		Thread.sleep(5000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");

		base.newWindow(1);
		driver.get(UtilityTestData.ERPURL);

		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), UtilityTestData.SearchRTGSPaymentDetails);
		Thread.sleep(5000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");

		//Step 2:verify RTGS entry line updated
		Thread.sleep(3000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Transaction("public_fixedDataTableCell_cellContent","Cust name"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_ERPCustAccntisExactly("FilterField_MGDRtgsPaymentDetails_CustName_CustName"),ActualCustName);
		base.buttonClick(LoginPageObj.Edt_Alert("Apply"));
		int maxAttempts = 10;
		boolean isFound = false;
		for (int i = 0; i < maxAttempts; i++) {
			driver.navigate().refresh();
			Thread.sleep(10000);
			// Scroll right
			WebElement scrollbar = driver.findElement(By.cssSelector(".ScrollbarLayout_faceHorizontal"));
			Actions actions = new Actions(driver);
			actions.clickAndHold(scrollbar)
			.moveByOffset(400, 0)
			.release()
			.perform();
			Thread.sleep(1000);
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Transaction("public_fixedDataTableCell_cellContent", "Receive amt"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_ERPCustAccntisExactly("FilterField_MGDRtgsPaymentDetails_ReceiveAmt_ReceiveAmt"), ReceiveAmt);
			base.buttonClick(LoginPageObj.Edt_Alert("Apply"));
			Thread.sleep(1000);
			if (!base.isExists(NormalSaleGoldAndSilverObj.Ele_ERPReceiveAmt("Receive amt"))) {
				isFound = false;
			}
			else {
				isFound = true;
				break;
			}
		}
		WebElement scrollbar = driver.findElement(By.cssSelector(".ScrollbarLayout_faceHorizontal"));
		Actions action = new Actions(driver);
		Thread.sleep(1000);
		action.clickAndHold(scrollbar)
		.moveByOffset(-300, 0)
		.release()
		.perform();
		Thread.sleep(1000);	
		String CustomerNameFromERP = base.GetValue(NormalSaleGoldAndSilverObj.Ele_ERPCustName(ActualCustName));
		asrt.assertEquals(CustomerNameFromERP,ActualCustName ," RTGS entry line not updated: Customer mismatch: " +"Expected: " + CustomerNameFromERP + ", but found: " + ActualCustName);
	}

	///<summary>
	/// Performs a normal sale of Dual Tone Platinum SKU
	///
	/// Logs in to the POS system
	/// Selects customer and scans Dual Tone Platinum SKUs
	/// Extracts and processes SKU details (net weight, RG weight, purity, rate)
	/// Converts all RG weights to a common purity and rate
	/// Finalizes transaction, makes payment, downloads invoice
	/// Returns invoice number, totals, SKU-level data, and scanned values
	///
	/// Author : Robin T Abraham
	///</summary>
	public Map<String, Object> GetInvoiceDetailsAfterNormalSaleDualTonePlatinum(String CustomerNo) throws Exception {

		// Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
		base.setZoom(driver, 60);
		Thread.sleep(5000);

		// Step 2: Navigate to the Transaction page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));

		// Step 3: Select customer and click in add to sale button
		appUtils.SearchByCustomerID(CustomerNo, UtilityTestData.MenuBarOptions[0]);

		// Step 4: Scan Multiple SKUs
		List<String> AllSkuList = new ArrayList<>();
		List<String>ScannedSkus = appUtils.ScanMultiplePlatinumSkus("Inter","Platinum", "Platinum");
		List<String> PlatinumSkus = appUtils.FetchDualTonePlatinumSku(ScannedSkus, 1);

		AllSkuList.addAll(PlatinumSkus);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Estimation("headerSplitViewToggleButton expandedNavButton iconGlobalNavButton win-splitviewpanetoggle win-disposable"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h5 textLeft centerY", "Transaction"));

		// Step 5: Extract SKU table data
		Map<String, String> ScannedSkuDataMap = new LinkedHashMap<>();
		for (int I = 0; I < AllSkuList.size(); I++) {
			appUtils.SKUIngridentTableValues(AllSkuList.get(I), I + 1, ScannedSkuDataMap);
		}

		// Step 6: Extract required values
		double TotalNetWeight = 0.0;
		String FirstSkuPurity = "N/A";
		String FirstSkuGoldRate = "Not Found";
		boolean FoundRg = false;

		double TotalRgWeightInFirstPurity = 0.0;
		double FirstPurity = 0.0;

		Map<String, String> SkuWiseData = new LinkedHashMap<>();

		for (int Index = 0; Index < AllSkuList.size(); Index++) {
			int SkuNumber = Index + 1;

			String NetWeightStr = ScannedSkuDataMap.get("SKU_" + SkuNumber + "_NetWeight");
			try {
				double NetWeight = Double.parseDouble(NetWeightStr);
				TotalNetWeight += NetWeight;
			} catch (Exception Ex) {
				System.out.println(" - Error parsing Net Weight for SKU " + SkuNumber + ": " + NetWeightStr);
			}
			SkuWiseData.put("SKU_" + SkuNumber + "_NetWeight", NetWeightStr);

			String GrossWeight = ScannedSkuDataMap.getOrDefault("SKU_" + SkuNumber + "_GrossWeight", "0.0");
			SkuWiseData.put("SKU_" + SkuNumber + "_GrossWeight", GrossWeight);

			String StoneWeight = ScannedSkuDataMap.getOrDefault("SKU_" + SkuNumber + "_StoneWeight", "0.0");
			SkuWiseData.put("SKU_" + SkuNumber + "_StoneWeight", StoneWeight);

			String SkuTotal = ScannedSkuDataMap.getOrDefault("SKU_" + SkuNumber + "_Total", "0.0");
			SkuWiseData.put("SKU_" + SkuNumber + "_Total", SkuTotal);

			String RgWeightStr = ScannedSkuDataMap.getOrDefault("SKU_" + SkuNumber + "_TotalRGWeight", "0.0");
			double RgWeight = 0.0;
			try {
				RgWeight = Double.parseDouble(RgWeightStr);
			} catch (Exception Ex) {
				System.out.println("â�Œ Invalid RG Weight for SKU_" + SkuNumber + ": " + RgWeightStr);
				continue;
			}

			if (RgWeight == 0.0) continue;

			String ThisPurityStr = ScannedSkuDataMap.getOrDefault("SKU_" + SkuNumber + "_Purity", "").replaceAll("(?i)k", "").trim();
			double ThisPurity = 0.0;
			try {
				ThisPurity = Double.parseDouble(ThisPurityStr);
			} catch (Exception Ex) {
				System.out.println("â�Œ Invalid Purity for SKU_" + SkuNumber + ": " + ThisPurityStr);
				continue;
			}

			if (!FoundRg) {
				FirstPurity = ThisPurity;
				FirstSkuPurity = ThisPurityStr;

				for (String Key : ScannedSkuDataMap.keySet()) {
					if (Key.startsWith("SKU" + SkuNumber + "_ITEM") && Key.endsWith("_ITEMID")) {
						String ItemKeyPrefix = Key.replace("_ITEMID", "");
						String ItemId = ScannedSkuDataMap.get(Key);
						if ("RG".equalsIgnoreCase(ItemId)) {
							FirstSkuGoldRate = ScannedSkuDataMap.getOrDefault(ItemKeyPrefix + "_RATE", "Not Found");
							break;
						}
					}
				}
				FoundRg = true;
			}

			double ThisSkuGoldRate = 0.0;
			for (String Key : ScannedSkuDataMap.keySet()) {
				if (Key.startsWith("SKU" + SkuNumber + "_ITEM") && Key.endsWith("_ITEMID")) {
					String ItemKeyPrefix = Key.replace("_ITEMID", "");
					String ItemId = ScannedSkuDataMap.get(Key);
					if ("RG".equalsIgnoreCase(ItemId)) {
						try {
							ThisSkuGoldRate = Double.parseDouble(
									ScannedSkuDataMap.getOrDefault(ItemKeyPrefix + "_RATE", "0.0")
									.replaceAll("[^0-9.]", ""));
						} catch (Exception Ex) {
							System.out.println("â�Œ Invalid RATE for SKU_" + SkuNumber + ": " + ScannedSkuDataMap.get(ItemKeyPrefix + "_RATE"));
						}
						break;
					}
				}
			}

			double FirstGoldRate = 0.0;
			try {
				FirstGoldRate = Double.parseDouble(FirstSkuGoldRate.replaceAll("[^0-9.]", ""));
			} catch (Exception Ex) {
				System.out.println("â�Œ Invalid First RG Rate: " + FirstSkuGoldRate);
				continue;
			}

			double ConvertedRgWeight = 0.0;
			if (FirstPurity == 18.0 && FirstGoldRate == ThisSkuGoldRate) {
				ConvertedRgWeight = RgWeight;
			} else {
				ConvertedRgWeight = (RgWeight * ThisSkuGoldRate) / (FirstGoldRate);
				ConvertedRgWeight = Math.round(ConvertedRgWeight * 1000.0) / 1000.0;
			}

			TotalRgWeightInFirstPurity += ConvertedRgWeight;

			System.out.println("SKU_" + SkuNumber + " RG Weight = " + RgWeight +
					", Purity = " + ThisPurity + ", RG Rate = " + ThisSkuGoldRate +
					" => Converted to First Purity (" + FirstPurity + "K @ " + FirstGoldRate +
					") = " + ConvertedRgWeight);
		}

		System.out.println("âœ… Total RG Weight Converted to First Purity (" + FirstPurity + "K): " + TotalRgWeightInFirstPurity);

		// Step 7: Finalize transaction
		Map<String, String> TransactionDataLine = appUtils.TransactionSKUsLinesVerify(AllSkuList, ScannedSkuDataMap);
		String SubTotal          = TransactionDataLine.get("Subtotal");
		String Discount          = TransactionDataLine.get("Discount");
		String TotalGrossWeight  = TransactionDataLine.get("GrossWeight");
		String TotalNewNetWeight = TransactionDataLine.get("NetWeight");
		String Tax               = TransactionDataLine.get("Tax");
		String TotalAmount       = TransactionDataLine.get("TotalAmount");
		String NetTotal          = TransactionDataLine.get("NetTotal");
		String LinesCount        = TransactionDataLine.get("LinesCount");

		appUtils.TransactionSKUsLinesVerify(AllSkuList, ScannedSkuDataMap);

		String PaymentAmount = appUtils.PaymentAfterRecallEstimate("HDFC");
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Transaction("h4 ellipsis cell", "Original For Recipient"));
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_Deposit("PRINT"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

		String TaxInvoiceName = pdfUtils.DownloadAndRenameLatestPDF("Method_TaxInvoice");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		String TaxInvoicePath = System.getProperty("user.dir") + "\\Invoices\\" + TaxInvoiceName;
		String InvoiceNo = pdfUtils.ExtractReceiptNoFrmSaleInvoice(TaxInvoicePath);
		String Purity = pdfUtils.ExtractPurityFrmSaleInvoice(TaxInvoicePath);

		base.buttonClick(LoginPageObj.Btn_SignInButton("Close"));

		// Step 8: Collect returned product names
		List<String> ReturnedProducts = new ArrayList<>();
		for (String Key : ScannedSkuDataMap.keySet()) {
			if (Key.matches("SKU\\d+_ITEM\\d+_ITEMNAME")) {
				ReturnedProducts.add(ScannedSkuDataMap.get(Key));
			}
		}

		// Step 9: Return all collected data
		Map<String, Object> ResultMap = new LinkedHashMap<>();
		ResultMap.put("InvoiceNo", InvoiceNo);
		ResultMap.put("TotalNetWeight", String.format("%.3f", TotalNetWeight));
		ResultMap.put("TotalRgWeight", String.format("%.3f", TotalRgWeightInFirstPurity));
		ResultMap.put("Purity",	Purity);
		ResultMap.put("FirstSkuPurity", FirstSkuPurity);
		ResultMap.put("FirstSkuGoldRate", FirstSkuGoldRate);
		ResultMap.put("SKUWiseDetails", SkuWiseData);
		ResultMap.put("SubTotal", SubTotal);
		ResultMap.put("Discount", Discount);
		ResultMap.put("TotalGrossWeight", TotalGrossWeight);
		ResultMap.put("Tax", Tax);
		ResultMap.put("TotalAmount", TotalAmount);
		ResultMap.put("NetTotal", NetTotal);
		ResultMap.put("LinesCount", LinesCount);
		ResultMap.put("ScannedSKUDataMap", ScannedSkuDataMap);

		return ResultMap;
	}
	
	///<summary>
	/// Merges return and sale SKU maps and re-indexes keys as SKU_1, SKU_2, ...
	/// Author: Vishnu M K
	///</summary>
	public Map<String, String> MergeScannedSkuDataMaps(Map<String, String> ReturnSkuMap, Map<String, String> NewSkuMap) {
	    Map<String, String> MergedMap = new LinkedHashMap<>();
	    int SkuIndex = 1;
	 
	    // Step 1: Merge return SKUs first
	    if (ReturnSkuMap != null) {
	        Map<Integer, Map<String, String>> GroupedReturnMap = GroupByIndex(ReturnSkuMap);
	        for (Map<String, String> SkuGroup : GroupedReturnMap.values()) {
	            for (Map.Entry<String, String> Entry : SkuGroup.entrySet()) {
	                String Suffix = Entry.getKey().substring(Entry.getKey().indexOf("_", 4)); // from SKU_1_GrossWeight → _GrossWeight
	                MergedMap.put("SKU_" + SkuIndex + Suffix, Entry.getValue());
	            }
	            SkuIndex++;
	        }
	    }
	 
	    // Step 2: Merge new scanned SKUs next
	    if (NewSkuMap != null) {
	        Map<Integer, Map<String, String>> GroupedNewMap = GroupByIndex(NewSkuMap);
	        for (Map<String, String> SkuGroup : GroupedNewMap.values()) {
	            for (Map.Entry<String, String> Entry : SkuGroup.entrySet()) {
	                String Suffix = Entry.getKey().substring(Entry.getKey().indexOf("_", 4));
	                MergedMap.put("SKU_" + SkuIndex + Suffix, Entry.getValue());
	            }
	            SkuIndex++;
	        }
	    }
	 
	    return MergedMap;
	}
	 
	// Helper method to group SKU fields by index
	private Map<Integer, Map<String, String>> GroupByIndex(Map<String, String> SkuMap) {
	    Map<Integer, Map<String, String>> GroupedMap = new TreeMap<>();
	    for (Map.Entry<String, String> Entry : SkuMap.entrySet()) {
	        String Key = Entry.getKey();
	        if (Key.startsWith("SKU_")) {
	            String[] Parts = Key.split("_", 3);
	            if (Parts.length >= 3) {
	                try {
	                    int Index = Integer.parseInt(Parts[1]);
	                    GroupedMap.computeIfAbsent(Index, k -> new LinkedHashMap<>())
	                              .put("SKU_" + Index + "_" + Parts[2], Entry.getValue());
	                } catch (NumberFormatException e) {
	                    // Skip malformed keys like SKU_X
	                }
	            }
	        }
	    }
	    return GroupedMap;
	}
	/// <summary>
			/// Validate SKU Count- Compares the number of SKUs shown on the transaction page with the LinesCount from TransactionDataLine.
			/// Validate SKU Names- Ensures each displayed SKU name matches the expected name from ScannedSkuDataMap, ignoring prefix codes.
			/// Validate Gross Weight (Qty)-Confirms the displayed quantity (gross weight) for each SKU matches the expected gross weight.
			/// Validate Total Without Tax (Per SKU)-Asserts that each SKU's displayed total (without tax) matches the expected total value.
			/// Validate Subtotal (Sum of All SKU Totals)-Adds all displayed SKU totals and compares the sum to the Subtotal value from TransactionDataLine.
			/// Validate Tax (3% of Subtotal) -Calculates 3% of the subtotal and verifies it matches the displayed Tax value.
			/// Validate Total Amount (Subtotal + Tax)-Adds subtotal and tax and confirms it equals the displayed TotalAmount.
			/// Author: VISHNU MANOJ K
			/// <summary>
			public void ErpValidateTransactionLineCalculation(Map<String, String> TransactionDataLine,Map<String, String> ScannedSkuDataMap,String LinesCountSale)
			{
				System.out.println("====== Validation Started for Transaction Page Line ======  ");
	 
				// === Step 1: Assert that number of SKUs matches LinesCount ===
				List<String> SkuNameElements = base.GetElementTexts(
						NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-ProductNameField wrapText", "h5")
						);
	 
				int ExpectedLinesCount = Integer.parseInt(TransactionDataLine.get("LinesCount").replaceAll("[^\\d]", ""));
				Assert.assertEquals(SkuNameElements.size(), ExpectedLinesCount, "After Calculation Value Mismatch in number of SKUs Actual:"+SkuNameElements.size()+" and number of SKUs Expected:"+ExpectedLinesCount+" LinesCount in Transaction Line Page");
	 
				// === Step 2: Assert each SKU Name ===
				for (int i = 0; i < SkuNameElements.size(); i++) {
					String ActualSkuName = SkuNameElements.get(i).trim();
					String ExpectedSkuName = ScannedSkuDataMap.get("SKU_" + (i + 1) + "_SKUName");
					Assert.assertNotNull(ExpectedSkuName, "Expected SKU name is null for SKU_" + (i + 1));
	 
					String[] Split = ExpectedSkuName.trim().split(" - ", 2);
					String ExpectedProductNameOnly = (Split.length > 1) ? Split[1].trim() : ExpectedSkuName.trim();
	 
					System.out.println("Asserting SKU_" + (i + 1) + " Name: Expected = " + ExpectedProductNameOnly + ", Actual = " + ActualSkuName);
					Assert.assertEquals(ActualSkuName, ExpectedProductNameOnly, "After Calculation Mismatch in SKU name Actual:"+ActualSkuName+" and SKU name Expected:"+ExpectedProductNameOnly+"  for SKU_" + (i + 1));
				}
	 
				// === Step 3: Assert GrossWeight (Qty) ===
				List<String> SkuQuantityElements = base.GetElementTexts(
						NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-QuantityField textRight", "h5")
						);
	 
				for (int i = 0; i < SkuQuantityElements.size(); i++) {
					String ActualQtyStr = SkuQuantityElements.get(i).trim().replaceAll("[^\\d.]", "");
					String ExpectedGrossWeightStr = ScannedSkuDataMap.get("SKU_" + (i + 1) + "_GrossWeight");
	 
					Assert.assertNotNull(ExpectedGrossWeightStr, "Expected GrossWeight is null for SKU_" + (i + 1));
	 
					double ActualQty = Double.parseDouble(ActualQtyStr);
					double ExpectedGrossWeight = Double.parseDouble(ExpectedGrossWeightStr.trim().replaceAll("[^\\d.]", ""));
	 
					System.out.println("Asserting GrossWeight for SKU_" + (i + 1) + ": Expected = " + ExpectedGrossWeight + ", Actual = " + ActualQty);
					Assert.assertEquals(ActualQty, ExpectedGrossWeight,1, "After Calculation Mismatch in GrossWeight Actual:"+ActualQty+" and Expected:"+ExpectedGrossWeight+" for SKU_" + (i + 1));
				}
	 
				// === Step 4 & 5: Assert Total Without Tax and Subtotal ===
				List<String> TotalWithoutTaxElements = base.GetElementTexts(
					    NormalSaleGoldAndSilverObj.Btn_CartMoney("tillLayout-TotalWithoutTaxField textRight", "h5")
					);
				
				    int LinesCountSaleInt = Integer.parseInt(LinesCountSale);
	 
					double FinalAdjustedSum = 0.0;
	 
					for (int i = 0; i < TotalWithoutTaxElements.size(); i++) {
					    int SkuNumber = i + 1;
					    String ActualTotalStr = TotalWithoutTaxElements.get(i).trim().replaceAll("[^\\d.]", "");
					    String ExpectedTotalStr = ScannedSkuDataMap.get("SKU_" + SkuNumber + "_Total");
	 
					    double ActualTotal = Double.parseDouble(ActualTotalStr);
					    double ExpectedTotal = Double.parseDouble(ExpectedTotalStr.trim().replaceAll("[^\\d.]", ""));
	 
					    System.out.println("Asserting TOTAL (Without Tax) for SKU_" + SkuNumber + ": Expected = " + ExpectedTotal + ", Actual = " + ActualTotal);
					    Assert.assertEquals(ActualTotal, ExpectedTotal, 1, "After Calculation Mismatch in TOTAL (Without Tax) Actual:" + ActualTotal + " and Expected:" + ExpectedTotal + " for SKU_" + SkuNumber);
	 
					    // Add or Subtract from FinalAdjustedSum based on LinesCount
					    if (SkuNumber <= LinesCountSaleInt) {
					        FinalAdjustedSum += ActualTotal; // Add first N (return) SKUs
					    } else {
					        FinalAdjustedSum -= ActualTotal; // Subtract newly scanned SKUs
					    }
					}
	 
					double ExpectedSubtotal = Double.parseDouble(TransactionDataLine.get("Subtotal").replaceAll("[^\\d.]", ""));
					System.out.println("Asserting Final Adjusted TOTAL (Without Tax) vs Subtotal:");
					System.out.println("Expected Subtotal = " + ExpectedSubtotal + ", Final Adjusted Sum = " + FinalAdjustedSum);
					Assert.assertEquals(Math.abs(FinalAdjustedSum), ExpectedSubtotal, 1, "After Calculation Mismatch in Adjusted Total (Without Tax) Actual:" + FinalAdjustedSum + " and Expected Subtotal:" + ExpectedSubtotal);
	 
	 
				// === Step 6: TAX = 3% of Subtotal ===
				double CalculatedTax = Math.round((ExpectedSubtotal * 0.03) * 100.0) / 100.0;
				double ExpectedTax = Double.parseDouble(TransactionDataLine.get("Tax").replaceAll("[^\\d.]", ""));
	 
				System.out.println("Asserting calculated 3% TAX vs displayed Tax:");
				System.out.println("Expected Tax = " + ExpectedTax + ", Calculated Tax = " + CalculatedTax);
				Assert.assertEquals(CalculatedTax, ExpectedTax,1, "After Calculation Mismatch in calculated Tax "+CalculatedTax+" and Expected Tax value is"+ExpectedTax+"");
	 
				// === Step 7: TotalAmount = Subtotal + Tax ===
				double CalculatedTotalAmount = Math.round((ExpectedSubtotal + ExpectedTax) * 100.0) / 100.0;
				double ExpectedTotalAmount = Double.parseDouble(TransactionDataLine.get("TotalAmount").replaceAll("[^\\d.]", ""));
	 
				System.out.println("Asserting Total Amount (Subtotal + Tax) with displayed TotalAmount:");
				System.out.println("Expected TotalAmount = " + ExpectedTotalAmount + ", Calculated TotalAmount = " + CalculatedTotalAmount);
				Assert.assertEquals(CalculatedTotalAmount, ExpectedTotalAmount,1, "After Calculation Mismatch in Total Amount Calculated "+CalculatedTotalAmount+" and Expected Total Amount "+ExpectedTotalAmount+"");
	 
				System.out.println("====== Validation Completed for Transaction Page Line ======  ");
	 
			}
}
