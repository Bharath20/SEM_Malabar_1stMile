package utilPack;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.JavascriptExecutor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.WebDriver;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.Assert;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class PdfUtilities {

	protected WebDriver driver;
	static Assert asrt;
	JavascriptExecutor js;
	BasePge base;

	public PdfUtilities(BasePge base) {
		this.base   = base;
		this.driver = base.driver; 

	}


	/// <summary>
	/// Extracts and returns the full text content from a PDF file.
	/// Author - Vishnu Manoj
	/// </summary>
	public static String IsGetText(String PdfFilePath) {
		String PdfText = "";
		try {
			File File = new File(PdfFilePath);
			PDDocument Document = PDDocument.load(File);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			PdfText = pdfStripper.getText(Document);
			Document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PdfText;
	}

	/// <summary>
	/// Verifies that specific label-value pairs exist in the extracted PDF text.
	/// Author - Vishnu Manoj
	/// </summary>              
//	public void VerifyMultipleFieldsInPdf(String PdfText, Map<String, String> ExpectedFields) {
//		// Step 1: Normalize PDF content — remove \u20B9, commas, colons, equal signs, and extra spaces
//		String NormalizedPdfContent = PdfText.replaceAll("[\u20B9,=:]", "")  // remove \u20B9, commas, colons, equal signs
//				.replaceAll("\\s+", "")    // remove all whitespace
//				.trim();
//
//		for (Map.Entry<String, String> Entry : ExpectedFields.entrySet()) {
//			// Step 2: Normalize label and expected value in the same way
//			String Label = Entry.getKey().trim().replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "");
//			String ExpectedValue = Entry.getValue().trim().replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "");
//
//			boolean IsFound = false;
//
//			// Step 3: Combine for exact match like "GrossAmount22484.50"
//			String ExpectedTextCombined = (Label + ExpectedValue).trim();
//			if (NormalizedPdfContent.contains(ExpectedTextCombined)) {
//				IsFound = true;
//			} else {
//				// Step 4: Loose match — check if label exists and value follows
//				int LabelIndex = NormalizedPdfContent.indexOf(Label);
//				if (LabelIndex != -1) {
//					String AfterLabel = NormalizedPdfContent.substring(LabelIndex + Label.length());
//					if (AfterLabel.contains(ExpectedValue)) {
//						IsFound = true;
//					}
//				}
//			}
//
//			if (IsFound) {
//				System.out.println("Found: " + Label + " = " + ExpectedValue);
//			} else {
//				System.out.println("NOT Found: " + Label + " = " + ExpectedValue);
//			}
//
//			Assert.assertTrue(IsFound, "Expected PDF content not found: [" + Label + " = " + ExpectedValue + "]");
//		}
//	}
	public void VerifyMultipleFieldsInPdf(String PdfText, Map<String, String> ExpectedFields) {
	    // Step 1: Normalize PDF content
	    String NormalizedPdfContent = PdfText.replaceAll("[\u20B9,=:]", "")  // remove symbols
	            .replaceAll("\\s+", "") // remove whitespace
	            .trim();

	    for (Map.Entry<String, String> Entry : ExpectedFields.entrySet()) {
	        String Label = Entry.getKey().trim().replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "");
	        String ExpectedValue = Entry.getValue().trim().replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "");

	        boolean IsFound = false;

	        // Step 2: Try string-based check first
	        String ExpectedTextCombined = (Label + ExpectedValue).trim();
	        if (NormalizedPdfContent.contains(ExpectedTextCombined)) {
	            IsFound = true;
	        } else {
	            int LabelIndex = NormalizedPdfContent.indexOf(Label);
	            if (LabelIndex != -1) {
	                String AfterLabel = NormalizedPdfContent.substring(LabelIndex + Label.length());

	                if (AfterLabel.contains(ExpectedValue)) {
	                    IsFound = true;
	                } else {
	                    // Step 3: Fallback to numeric tolerance check
	                    try {
	                        double ExpectedNum = Double.parseDouble(ExpectedValue);
	                        String ExtractedDigits = AfterLabel.replaceAll("[^0-9.]", ""); 
	                        if (!ExtractedDigits.isEmpty()) {
	                            double ActualNum = Double.parseDouble(ExtractedDigits);
	                            if (Math.abs(ExpectedNum - ActualNum) <= 1.0) {
	                                IsFound = true;
	                            }
	                        }
	                    } catch (NumberFormatException e) {
	                        // ignore if parsing fails
	                    }
	                }
	            }
	        }

	        if (IsFound) {
	            System.out.println(" Found: " + Label + " = " + ExpectedValue);
	        } else {
	            System.out.println(" NOT Found: " + Label + " = " + ExpectedValue);
	        }

	        Assert.assertTrue(IsFound, "Expected PDF content not found (±1 tolerance): [" + Label + " = " + ExpectedValue + "]");
	    }
	}
	/// <summary>
	/// Validates that each expected row (e.g., invoice line items) is present in the given PDF text.
	/// Author - Vishnu Manoj
	/// </summary> 
	public void VerifyTableRowInPdf(String PdfText, List<String> ExpectedRows) {
		String NormalizedPdf = PdfText.replaceAll("\\s+", " ").trim();
		for (String Row : ExpectedRows) {
			String NormalizedRow = Row.replaceAll("\\s+", " ").trim();
			boolean Found = NormalizedPdf.contains(NormalizedRow);
			if (Found) {
				System.out.println("ITEM Found: " + Row);
			} else {
				System.out.println("ITEM NOT Found: " + Row);
			}
			Assert.assertTrue(Found, "Expected PDF content not found in PDF: " + Row);
		}
	}

	/// <summary>
	/// To verify the accuracy of values in a Pro Forma Invoice PDF (Estimation PDF) by
	/// Validating both individual fields and a summary table row using extracted PDF text.
	/// Author - Vishnu Manoj
	/// </summary>	
	public void ProFormaInvoicePDFVerifyNormal(String PdfPath,String Subtotal,String Tax,String TotalAmount,String NetTotal,String LinesCount,double TotalGrossWeight,double TotalNetWeight) {
		try {
			// Step 1: Read and normalize PDF content
			//Thread.sleep(5000); // wait for PDF to be downloaded
			String PdfContent = this.IsGetText(PdfPath);
			String NormalizedPdfContent = PdfContent.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("\\s+", " ").trim();

			// Step 2: Field-level verifications
			Map<String, String> ExpectedFields = new HashMap<>();
			ExpectedFields.put("Taxable Value :", Subtotal.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[\u20B9]", "").replaceAll("\\?", "").trim());
			ExpectedFields.put("GST :", Tax.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[\u20B9]", "").replaceAll("\\?", "").trim());
			ExpectedFields.put("Invoice Total :", TotalAmount.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[\u20B9]", "").replaceAll("\\?", "").trim());
			ExpectedFields.put("Net Total :", NetTotal.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[\u20B9]", "").replaceAll("\\?", "").trim());
			this.VerifyMultipleFieldsInPdf(NormalizedPdfContent, ExpectedFields);

			// Step 3: Construct total summary row
			List<String> ExpectedRows = new ArrayList<>();

			String CleanLinesCount = LinesCount.replaceAll("[^\\d]", "");
			String CleanSubtotal = Subtotal.replaceAll("[^\\d.]", "");
			String FormattedGrossWeight = String.format("%.3f", TotalGrossWeight);
			String FormattedNetWeight = String.format("%.3f", TotalNetWeight);
			String FormattedSubtotal = String.format("%.2f", Double.parseDouble(CleanSubtotal));

			String TotalRow = String.format("Total %s %s %s %s",
					CleanLinesCount,
					FormattedGrossWeight,
					FormattedNetWeight,
					FormattedSubtotal);

			ExpectedRows.add(TotalRow);

			// Step 4: Row-level verification
			this.VerifyTableRowInPdf(NormalizedPdfContent, ExpectedRows);

		} catch (Exception e) {
			System.out.println("Error in EstimatePDFVerify: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/// <summary>
	/// Downloads and renames the latest PDF in the "Invoices" folder using a custom name prefix and timestamp.
	/// Author - Vishnu Manoj
	/// </summary>
	public static  String DownloadAndRenameLatestPDF(String PdfName) throws IOException, InterruptedException {
		String DownloadDir = System.getProperty("user.dir") + "\\Invoices";

		// Wait for download to complete
		Thread.sleep(5000);

		// Get all PDF files
		File Dir = new File(DownloadDir);
		File[] Files = Dir.listFiles((d, name) -> name.toLowerCase().endsWith(".pdf"));

		if (Files == null || Files.length == 0) {
			throw new FileNotFoundException("No PDF found in directory: " + DownloadDir);
		}

		// Sort files by last modified (most recent first)
		Arrays.sort(Files, Comparator.comparingLong(File::lastModified).reversed());
		File LatestFile = Files[0];

		// Generate new file name
		String Timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String NewPdfName = PdfName + "_" + Timestamp + ".pdf";
		File RenamedFile = new File(DownloadDir + "\\" + NewPdfName);

		// Rename file
		boolean Success = LatestFile.renameTo(RenamedFile);
		if (!Success) {
			throw new IOException("Failed to rename file: " + LatestFile.getName());
		}

		System.out.println("File renamed to: " + RenamedFile.getName());
		return NewPdfName;
	}

	/// <summary>
	/// To remove all PDF files in the Invoices
	/// Author - Vishnu Manoj
	/// <summary>
	public static void DeleteAllPDFFilesInInvoiceFolder() throws IOException {
		String DownloadDir = System.getProperty("user.dir") + "\\Invoices";

		File Dir = new File(DownloadDir);
		File[] Files = Dir.listFiles((d, name) -> name.toLowerCase().endsWith(".pdf"));

		if (Files == null || Files.length == 0) {
			System.out.println("No PDF files found in directory: " + DownloadDir);
			return;
		}

		for (File File : Files) {
			if (File.delete()) {
				System.out.println("Deleted: " + File.getName());
			} else {
				System.out.println("Failed to delete: " + File.getName());
			}
		}
	}

	/// <summary>
	/// Performs text extraction from the GPP PDF to locate and display the Application ID
	/// Author - Nivya Ramesan
	/// </summary> 
	public void GPPpdfValidation() {
		String PdfPath = "C:\\Users\\HP\\Downloads\\GPP.pdf";
		String PdfContent = IsGetText(PdfPath); 

		if (PdfContent == null || PdfContent.trim().isEmpty()) {
			System.err.println("Failed to extract text from PDF.");
			return;
		}

		PdfContent = PdfContent.replaceAll("\\r?\\n", "\n"); 

		String[] lines = PdfContent.split("\n");

		for (String line : lines) {
			if (line.trim().toLowerCase().startsWith("application id")) {
				System.out.println("" + line.trim());
				return;
			}
		}

		System.err.println("Application ID not found in the PDF.");
	}

	/// <summary>
	/// Method to validate OG(Old Gold) Pdf(PDF Name -Purchase Bill) for multiple products
	/// Method includes fetching Invoice Number, GrossWt, NetWt, Rate, Item Total, Convert to Advance
	/// Validates that Gross Wt and Net Wt are equal per product, and TotalAmount = PaymentDetails
	/// Author - Christy Reji
	/// </summary>
	public Map<String, String> OGPdfValidation(String paymentKeyword) throws IOException, InterruptedException {

		// Rename the downloaded PDF to OG
		String NewPdfName    = DownloadAndRenameLatestPDF("OG");
		String PdfPath       = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);

		Map<String, String> OGPdfData = new HashMap<>();
		OGPdfData.put("OGPdfName", ExtractedText.contains("PURCHASE BILL") ? "PURCHASE BILL" : "");

		// Invoice Number
		Matcher InvoiceMatcher = Pattern.compile("Invoice No\\s*:?\\s*(\\S+)").matcher(ExtractedText);
		if (InvoiceMatcher.find()) {
			OGPdfData.put("InvoiceNumber", InvoiceMatcher.group(1));
		}

		// Product details: supports 1 or more products
		Pattern ProductPattern = Pattern.compile(
				"(\\d+\\.\\d{3})\\s+(\\d+\\.\\d{3})\\s+(\\d{4,6}\\.\\d{2})\\s+(\\d{4,6}\\.\\d{2})\\s*Item Total\\s*(\\d{4,6}[\\.,]\\d{2})");
		Matcher ProductMatcher = ProductPattern.matcher(ExtractedText);

		int ProductIndex = 1;
		while (ProductMatcher.find()) {
			OGPdfData.put("GrossWt"     + ProductIndex, ProductMatcher.group(1));
			OGPdfData.put("NetWt"       + ProductIndex, ProductMatcher.group(2));
			OGPdfData.put("Rate"        + ProductIndex, ProductMatcher.group(3));
			OGPdfData.put("TotalAmount" + ProductIndex, ProductMatcher.group(4));
			OGPdfData.put("ItemTotal"   + ProductIndex, ProductMatcher.group(5).replace(",", ".")); // Replace comma
			ProductIndex++;
		}
		System.out.println("OGPdfData "+OGPdfData);

		// Fallback for single product if main pattern not matched
		if (ProductIndex == 1) {
			Pattern SingleProductPattern = Pattern.compile(
					"Raw Gold.*?(\\d+\\.\\d{3})\\s+(\\d+\\.\\d{3})\\s+(\\d{4,6}\\.\\d{2})\\s+(\\d{4,6}\\.\\d{2})");
			Matcher FallbackMatcher = SingleProductPattern.matcher(ExtractedText);
			if (FallbackMatcher.find()) {
				OGPdfData.put("GrossWt1",     FallbackMatcher.group(1));
				OGPdfData.put("NetWt1",       FallbackMatcher.group(2));
				OGPdfData.put("Rate1",        FallbackMatcher.group(3));
				OGPdfData.put("TotalAmount1", FallbackMatcher.group(4));
			}
			System.out.println("OGPdfData1 "+OGPdfData);
			// Fallback for Item Total
			Matcher ItemTotalMatcher = Pattern.compile("Item Total\\s*(\\d{4,6}[\\.,]\\d{2})").matcher(ExtractedText);
			if (ItemTotalMatcher.find()) {
				OGPdfData.put("ItemTotal1", ItemTotalMatcher.group(1).replace(",", "."));
			}
		}

		OGPdfData.put("TotalQuantity", String.valueOf(ProductIndex - 1));

		System.out.println("OGPdfData after item total and Tot qty "+OGPdfData);

		// Extract Total Amount (numeric, after "Total", followed by currency in words or number line)
		Matcher TotalAmountMatcher = Pattern.compile("Total\\s*(\\d{4,6}[\\.,]\\d{2})\\s*Rupees").matcher(ExtractedText);
		if (TotalAmountMatcher.find()) {
			OGPdfData.put("TotalAmount", TotalAmountMatcher.group(1).replace(",", "."));
		}
		System.out.println("OGPdfData after tot amnt "+OGPdfData);

		// Convert to Advance (payment details)
		String PaymentKeyword = Pattern.quote(paymentKeyword); // To handle special characters
		//Matcher PaymentMatcher = Pattern.compile("Adjustment\\s*:?\\s*(\\d{4,6}[\\.,]\\d{2})").matcher(ExtractedText);
		Matcher PaymentMatcher = Pattern.compile(PaymentKeyword + "\\s*:?\\s*(\\d{4,6}[\\.,]\\d{2})").matcher(ExtractedText);
		if (PaymentMatcher.find()) {
			OGPdfData.put("PaymentDetails", PaymentMatcher.group(1).replace(",", "."));
		} else {
			OGPdfData.put("PaymentDetails", "Not Found");
		}

		// Assertion: TotalAmount == PaymentDetails
		String Total = OGPdfData.get("TotalAmount");
		String Payment = OGPdfData.get("PaymentDetails");

		if (Total != null && Payment != null && !Payment.equals("Not Found")) {
			asrt.assertEquals(Total, Payment, "Total Amount and Payment Details mismatch.");
		} else {
			throw new AssertionError("Missing Total or Payment data for validation.");
		}

		System.out.println("Validation successful for OG PDF");

		//		System.out.println("OGPdfData "+OGPdfData);

		System.out.println("Final OGPdfData "+OGPdfData);

		return OGPdfData;
		//how to call in your class
		//Map<String, String> result = PdfUtils.OGPdfValidation();
		//System.out.println("Invoice Number: " + result.get("InvoiceNumber"));
	}
	/// <summary>
	/// To verify that each expected SKU line (Gross, Stone, Net, Amount) is present in the PDF text â€” allowing for minor rounding differences (within Â±0.05)
	/// Author - Vishnu Manoj
	/// <summary>
	public void VerifyMultipleItemTotalLines(String PdfText, List<double[]> ExpectedLines) {
		String[] Lines = PdfText.split("\\r?\\n");
		int MatchCount = 0;

		for (int Index = 0; Index < ExpectedLines.size(); Index++) {
			double[] Expected = ExpectedLines.get(Index);
			double ExpectedGross = Expected[0];
			double ExpectedStone = Expected[1];
			double ExpectedNet = Expected[2];
			double ExpectedAmount = Expected[3];

			//System.out.println("===== Comparing Line " + (Index + 1) + " =====");
			System.out.println("Gross Wt  - Expected: " + ExpectedGross);
			System.out.println("Stone Wt  - Expected: " + ExpectedStone);
			System.out.println("Net Wt    - Expected: " + ExpectedNet);
			System.out.println("Amount    - Expected: " + ExpectedAmount);

			boolean Matched = false;

			for (String Line : Lines) {
				List<Double> NumbersInLine = ExtractDoublesFromLine(Line);

				for (int I = 0; I <= NumbersInLine.size() - 4; I++) {
					double Gross = NumbersInLine.get(I);
					double Stone = NumbersInLine.get(I + 1);
					double Net = NumbersInLine.get(I + 2);
					double Amount = NumbersInLine.get(I + 3);

					System.out.printf(" Checking: Gross=%.3f, Stone=%.3f, Net=%.3f, Amount=%.2f\n",
							Gross, Stone, Net, Amount);

					if (IsApproxEqual(Gross, ExpectedGross) &&
							IsApproxEqual(Stone, ExpectedStone) &&
							IsApproxEqual(Net, ExpectedNet) &&
							IsApproxEqual(Amount, ExpectedAmount)) {
						Matched = true;
						MatchCount++;
						break;
					}
				}

			}
			Assert.assertTrue(Matched, String.format(
					" Expected line not found for SKU %d: Gross=%.3f, Stone=%.3f, Net=%.3f, Amount=%.2f",
					Index + 1, ExpectedGross, ExpectedStone, ExpectedNet, ExpectedAmount
					));
		}

		System.out.println(" Matched " + MatchCount + " expected item line(s) successfully.");
	}
	private List<Double> ExtractDoublesFromLine(String Line) {
		List<Double> Values = new ArrayList<>();
		Matcher Matcher = Pattern.compile("\\d+\\.\\d+").matcher(Line);
		while (Matcher.find()) {
			Values.add(Double.parseDouble(Matcher.group()));
		}
		return Values;
	}

	private boolean IsApproxEqual(double A, double B) {
		return Math.abs(A - B) < 0.05; // tolerance of Â±0.05
	}

	/// <summary>
	/// Validates Sale Invoice PDF Validation
	/// Author - Vishnu Manoj
	/// </summary>
	public String SaleInvoicePdfValidation(		    
			String PdfPath,
			String GrossAmount,
			String TotalDiscount,
			String TotalQtyPcs,
			String TotalNetWeight,
			String TotalGrossWeight,
			String TaxableValue,
			String NetValue,
			String PaymentAmountHDFC,
			List<String> SkuList,
			Map<String, String> ScannedSKUDataMap
			) throws IOException, InterruptedException {
		String ExtractedInvoiceNo = "";
		String PdfContent = this.IsGetText(PdfPath);

		try {
			Pattern Vpattern = Pattern.compile("Invoice No\\s*:?[\\s]*([\\w\\d]+)");
			Matcher Matcher = Vpattern.matcher(PdfContent);
			if (Matcher.find()) {
				ExtractedInvoiceNo = Matcher.group(1).trim();
				System.out.println("Extracted Invoice No: " + ExtractedInvoiceNo);
			} else {
				System.out.println("Invoice No not found in PDF.");
			}
		} catch (Exception e) {
			System.out.println("Error extracting Invoice No: " + e.getMessage());
		}

		String NormalizedPdfContent = PdfContent.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("\\s+", " ").replaceAll("[\u20B9,=:]", "").trim();

		Map<String, String> ExpectedFields = new HashMap<>();
		ExpectedFields.put("Gross Amount", GrossAmount.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").replaceAll("\\?", "").trim());
		ExpectedFields.put("Total Discount",  (TotalDiscount == null || TotalDiscount.trim().isEmpty() ? "0.00" : TotalDiscount)
				.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").replaceAll("\\?", "").trim());
		ExpectedFields.put("Total Qty(Pcs)", TotalQtyPcs.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").replaceAll("\\?", "").trim());
		ExpectedFields.put("Total Net Wt.", TotalNetWeight.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").replaceAll("\\?", "").trim());
		ExpectedFields.put("Total Gross Wt.", TotalGrossWeight.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").replaceAll("\\?", "").trim());
		ExpectedFields.put("Taxable Value", TaxableValue.replaceAll("[\u20B9,=:]", "").replaceAll("\\?", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("HDFC (Credit Card)", PaymentAmountHDFC.replaceAll("[\u20B9,]", "").replaceAll("\\s+", "").replaceAll("\\?", "").trim());
		ExpectedFields.put("Net Value", NetValue);//due to Decimal issue, Calculation issue

		List<double[]> ExpectedLines = new ArrayList<>();
		for (int SkuIndex = 1; SkuIndex <= SkuList.size(); SkuIndex++) {
			double GrossWeight = Math.round(Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_GrossWeight", "0.0")) * 1000.0) / 1000.0;
			double StoneWeight = Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_StoneWeight", "0.0"));
			double NetWeight = Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_NetWeight", "0.0"));
			double TotalAmount = Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_Total", "0.0"));

			System.out.println("SKU" + SkuIndex + " → Gross: " + GrossWeight + ", Stone: " + StoneWeight + ", Net: " + NetWeight + ", Total: " + TotalAmount);

			ExpectedLines.add(new double[]{GrossWeight, StoneWeight, NetWeight, TotalAmount});
		}

		this.VerifyMultipleFieldsInPdf(NormalizedPdfContent, ExpectedFields);
		this.VerifyMultipleItemTotalLines(NormalizedPdfContent, ExpectedLines);

		return ExtractedInvoiceNo;
	}

	///<Summary>
	///Method to validate the Schemes(GG,GS,GB) pdf
	///@Author: Gokul.P
	///</Summary>
	public static void SchemePdfValidation( Map<String, String> ExpectedFields,String SchemeName) throws IOException, InterruptedException
	{
		String NewPdfName = DownloadAndRenameLatestPDF(SchemeName.toUpperCase());
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);	
		Map<String, String> Extracted = ExtractFields(ExtractedText);		
		if(ExtractedText.contains(SchemeName))
		{
			Extracted.put("SchemePdfName", ExtractedText.contains(SchemeName) ? SchemeName : "");
			Assert.assertEquals(Extracted.get("SchemePdfName"), SchemeName,"The "+SchemeName+" is displayed in the form is incorrect");
		}

		for (Map.Entry<String, String> entry : Extracted.entrySet()) 
		{
			String Key = entry.getKey();
			String Value = entry.getValue();			
		}
		//Application ID Validation
		if (ExtractedText.contains("Application ID"))
		{   
			String ActualApplicationId = Extracted.get("Application ID");		    
			String ExpectedApplicationId = ExtractedText.contains(ActualApplicationId) ? ActualApplicationId : " ";
			Assert.assertEquals(ActualApplicationId,ExpectedApplicationId, "The " + ActualApplicationId + " and the " + ExpectedApplicationId + " are mismatched" );
		}

		// Validate the values fetched from the application
		for (Map.Entry<String, String> Expected : ExpectedFields.entrySet())
		{
			String Key = Expected.getKey();
			String ExpectedValue = Expected.getValue();
			String ActualValue = Extracted.get(Key);			
			Assert.assertEquals(ExpectedValue, ActualValue, "The "+ExpectedValue+" and the "+ActualValue+" are mismatched");
		}
	}

	///<Summary>
	/// Method to return the value of the corresponding field
	///@Author: Gokul.P
	///</Summary>
	public static String Match(String Text, String Regex) {
		Pattern pattern = Pattern.compile(Regex);
		Matcher Matcher = pattern.matcher(Text);
		if (Matcher.find()) {
			String Value = Matcher.group(1);
			if (Value != null && !Value.trim().isEmpty()) {
				return Value.trim();
			} else {
				return null;
			}
		}
		return null;
	}

	///<Summary>
	/// Fetch the values of the Fields using pattern
	///@Author: Gokul.P
	///</Summary>
	public static Map<String, String> ExtractFields(String PdfText) 
	{
		Map<String, String> Result = new LinkedHashMap<>();
		Result.put("Application ID", Match(PdfText, "Application ID\"?\\s*[:\"]\\s*\"?(\\w+)"));
		Result.put("Plan",Match(PdfText,"Plan\\s*:\\s*(\\w+::.*)"));
		Result.put("Customer",Match(PdfText,"Customer\\s*:\\s*(\\w+\\s*::\\s*.*)"));
		Result.put("Customer Address", Match(PdfText, "Address\\s*:\\s*(.*?)\\s*Phone"));
		Result.put("Phone", Match(PdfText, "Phone[\"\\s]*[:\"]\\s*\"?(\\d{10})"));
		Result.put("Mobile", Match(PdfText, "Mobile[\"\\s]*[:\"]\\s*\"?(\\d{10})"));
		Result.put("Email", Match(PdfText, "Email\\s*[:\"]\\s*\"?(\\S+@\\S+)?"));
		Result.put("Date Of Birth", Match(PdfText, "Date Of Birth\\s*[:\"]\\s*\"?(\\d{2}/\\d{2}/\\d{4})"));
		Result.put("Identity Type", Match(PdfText, "Identity Type\\s*[:\"]?\\s*(.*?)\\s*Identity Detail"));
		Result.put("Identity Detail", Match(PdfText, "Identity Detail\"?\\s*[:\"]\\s*\"?(\\d{12})"));		
		Result.put("Monthly Advance", Match(PdfText, "Monthly Advance\\s*[:\"]?\\s*(.*?)\\s*Advance Due Date"));	
		Result.put("Remind mode", Match(PdfText, "Remind mode\\s*[:\"]?\\s*(.*?)\\s*Bank A/C Holder Name"));
		Result.put("Bank A/C Holder Name", Match(PdfText, "Bank A/C Holder Name\"?\\s*[:\"]\\s*\"?(.*?)\\s*Bank Name"));
		Result.put("Bank Name", Match(PdfText, "Bank Name\\s*[:\"]?\\s*(.*?)\\s*Bank Account No"));
		Result.put("Bank Account No", Match(PdfText, "Bank Account No\\s*[:\"]\\s*\"?(\\d{9,})"));
		Result.put("IFSC Code", Match(PdfText, "IFSC Code\\s*[:\"]\\s*\"?([A-Z]{4}0\\d{6})"));
		Result.put("Branch", Match(PdfText, "Branch\\s*[:\"]\\s*\"?(.*?)\\s*Proof of Bank Account"));
		Result.put("Proof of Bank Account", Match(PdfText, "Proof of Bank Account\\s*[:\"]\\s*\"?(.*?)\\s*Name"));
		Result.put("Nominee Name", Match(PdfText, "Nominee Name\\s*[:\"]\\s*\"?(.*?)\\s*Nominee Address"));
		Result.put("Nominee Address", Match(PdfText, "Nominee Address\\s*[:\"]\\s*\"?(CPT.*?)\\s*Nominee Phone"));
		Result.put("Nominee Phone", Match(PdfText, "Nominee Phone\\s*[:\"]\\s*\"?(\\d{10})?"));
		Result.put("Relation with nominee", Match(PdfText, "Relation with nominee\\s*[:\"]\\s*\"?(.*?)\\s*Guardian Name"));
		return Result;
	}

	/// <summary>
	/// Extracts and validates key financial fields from the Normal Advance PDF,
	/// including Receipt Id, Advance Received, Net Advance, Weight, Rate,
	/// Deposit Type, Payment Mode, and Amount.
	/// Ensures Advance = Net Advance = Payment.
	/// Author - Nivya Ramesan
	/// </summary>
	public Map<String, String> NormalAdvancePdfValidation() throws IOException, InterruptedException {

		String NewPdfName = DownloadAndRenameLatestPDF("NormalAdvance");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);

		String[] Lines = ExtractedText.split("\\r?\\n");

		String ReceiptId = "";
		String AdvanceReceived = "";
		String NetAdvance = "";
		String ApproxWeight = "";
		String FixedGoldRate = "";
		String DepositType = "";
		String PaymentMode = "";
		String PaymentAmount = "";
		String MaxGoldRate = "";

		boolean FoundAdvanceLine = false;

		BiFunction<String, String, String> ExtractValueByPrefix = (Line, Prefix) -> {
			if (Line.startsWith(Prefix)) {
				String[] Parts = Line.split(":");
				if (Parts.length > 1) {
					return Parts[1].trim();
				}
			}
			return "";
		};

		for (String LineRaw : Lines) {
			String Line = LineRaw.trim();

			// Receipt ID
			if (Line.startsWith("Receipt Id")) {
				ReceiptId = ExtractValueByPrefix.apply(Line, "Receipt Id");
			}

			//Max Gold Rate
			if (Line.startsWith("Max Gold Rate")) {
				MaxGoldRate = ExtractValueByPrefix.apply(Line, "Max Gold Rate");
			}

			// Advance Received and Net Advance
			if (Line.contains("Advance Received") && Line.contains("Net Advance")) {
				FoundAdvanceLine = true;
			} else if (FoundAdvanceLine) {
				String[] Values = Line.trim().split("\\s+");
				if (Values.length >= 2) {
					AdvanceReceived = Values[0];
					NetAdvance = Values[1];
				}
				FoundAdvanceLine = false;
			}

			// Approx Weight
			if (Line.startsWith("Towards the advance against purchase of approximate")) {
				int Idx = Line.lastIndexOf("approximate");
				if (Idx != -1) {
					ApproxWeight = Line.substring(Idx + 11).replace("GM", "").trim();
				}
			}

			if (Line.startsWith("Fixed gold rate per gram")) {
				FixedGoldRate = ExtractValueByPrefix.apply(Line, "Fixed gold rate per gram");
			}

			if (Line.startsWith("Max gold rate per gram")) {
				FixedGoldRate = ExtractValueByPrefix.apply(Line, "Max gold rate per gram");
			}
			if (Line.startsWith("Deposit Type")) {
				DepositType = ExtractValueByPrefix.apply(Line, "Deposit Type");
			}

			if (Line.startsWith("Convert to Advance")) {
				PaymentMode = "Convert to Advance";
				PaymentAmount = ExtractValueByPrefix.apply(Line, "Convert to Advance");
			}
			if (Line.startsWith("SBI (Credit Card)")) {
				PaymentMode = "SBI (Credit Card)";
				PaymentAmount = ExtractValueByPrefix.apply(Line, "SBI (Credit Card)");
			}
		}
		Thread.sleep(2000);
		// Assertions
		Assert.assertEquals(AdvanceReceived, NetAdvance, "Advance Received and Net Advance do not match");
		Assert.assertEquals(PaymentAmount, AdvanceReceived, "Advance Received and Payment Amount do not match");

		Map<String, String> ResultMap = new HashMap<>();
		ResultMap.put("ReceiptId", ReceiptId);
		ResultMap.put("MaxGoldRate", MaxGoldRate);
		ResultMap.put("AdvanceReceived", AdvanceReceived);
		ResultMap.put("NetAdvance", NetAdvance);
		ResultMap.put("ApproxWeight", ApproxWeight);
		ResultMap.put("FixedGoldRate", FixedGoldRate);
		ResultMap.put("DepositType", DepositType);
		ResultMap.put("PaymentMode", PaymentMode);
		ResultMap.put("PaymentAmount", PaymentAmount);

		return ResultMap;
	}




	/// <summary>
	/// To verify the accuracy of values in a Pro Forma Invoice PDF for negative values(Estimation PDF) by
	/// Validating both individual fields and a summary table row using extracted PDF text.
	/// Author - Anagha
	/// </summary>	
	public void ProFormaInvoicePDFVerifyNegativeValue(String PdfPath,String Subtotal,String Tax,String TotalAmount,String NetTotal,String LinesCount,double TotalGrossWeight,double TotalNetWeight) {
		try {
			// Step 1: Read and normalize PDF content
			String PdfContent = this.IsGetText(PdfPath);
			String NormalizedPdfContent = PdfContent.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").replaceAll("\\s+", " ").trim();

			// Step 2: Field-level verifications
			Map<String, String> ExpectedFields = new HashMap<>();
			ExpectedFields.put("Taxable Value :", Subtotal.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").trim());
			ExpectedFields.put("GST :", Tax.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").trim());
			ExpectedFields.put("Invoice Total :", TotalAmount.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").trim());
			ExpectedFields.put("Net Total :", NetTotal.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").trim());
			this.VerifyMultipleFieldsInPdf(NormalizedPdfContent, ExpectedFields);

			// Step 3: Construct total summary row
			List<String> ExpectedRows = new ArrayList<>();

			String CleanLinesCount = LinesCount.replaceAll("[^\\d]", "");
			String CleanSubtotal = Subtotal.replaceAll("[^\\d.]", "").replaceAll("[()]", "");
			String FormattedGrossWeight = String.format("%.3f", TotalGrossWeight);
			String FormattedNetWeight = String.format("%.3f", TotalNetWeight);
			String FormattedSubtotal = String.format("%.2f", Double.parseDouble(CleanSubtotal));

			String TotalRow = String.format("Total %s %s %s %s",
					CleanLinesCount,
					FormattedGrossWeight,
					FormattedNetWeight,
					FormattedSubtotal);

			ExpectedRows.add(TotalRow);

			// Step 4: Row-level verification
			this.VerifyTableRowInPdf(NormalizedPdfContent, ExpectedRows);

		} catch (Exception e) {
			System.out.println("Error in EstimatePDFVerify: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/// <summary>
	/// Method to validate SalesReturn Pdf(PDF Name -Credit Note) for multiple products
	/// Method includes fetching SerialNo,Item Total,Product,GrossWt,StoneWt,NetWt,Amount,TotalProducts,PaymentDetails,TotalNetValue
	/// GST,TotalDiscount,TotalQty,TotalNetValue,TaxableValue 
	/// Author - Christy Reji
	/// </summary>
	public Map<String, String> SalesReturnPDFCredit() throws IOException, InterruptedException {

		// Step 1: Rename the downloaded PDF
		String NewPdfName    = DownloadAndRenameLatestPDF("SalesReturnCreditNote");
		String PdfPath       = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);
		System.out.println(ExtractedText);

		Map<String, String> CreditNoteData = new HashMap<>();

		// Step 2: Extract Serial Number
		Matcher serialMatcher = Pattern.compile("Serial No\\s*:\\s*(\\S+)").matcher(ExtractedText);
		if (serialMatcher.find()) {
			CreditNoteData.put("SerialNo", serialMatcher.group(1));
		}

		// Step 3: Extract Product details dynamically for any number of products and calculate totals
		Pattern ProductPattern = Pattern.compile("Item Total in Grams\\s*(\\d+\\.\\d{3})\\s*(\\d+\\.\\d{3})\\s*(\\d+\\.\\d{3})\\s*(\\d+\\.\\d{2})");
		Matcher ProductMatcher = ProductPattern.matcher(ExtractedText);
		int ProductIndex    = 1;
		double TotalGrossWt = 0.0;
		double TotalStoneWt = 0.0;
		double TotalNetWt   = 0.0;
		double TotalAmount  = 0.0;
		//Fetching above values of each individual product
		while (ProductMatcher.find()) {
			String GrossWt = ProductMatcher.group(1);
			String StoneWt = ProductMatcher.group(2);
			String NetWt   = ProductMatcher.group(3);
			String Amount  = ProductMatcher.group(4);

			CreditNoteData.put("Product" + ProductIndex + ".GrossWt", GrossWt);
			CreditNoteData.put("Product" + ProductIndex + ".StoneWt", StoneWt);
			CreditNoteData.put("Product" + ProductIndex + ".NetWt", NetWt);
			CreditNoteData.put("Product" + ProductIndex + ".Amount", Amount);

			TotalGrossWt += Double.parseDouble(GrossWt);
			TotalStoneWt += Double.parseDouble(StoneWt);
			TotalNetWt   += Double.parseDouble(NetWt);
			TotalAmount  += Double.parseDouble(Amount);

			ProductIndex++;
		}
		int TotalProducts = ProductIndex - 1;
		CreditNoteData.put("TotalProducts", String.valueOf(TotalProducts));

		// Set calculated values
		CreditNoteData.put("GrossAmount",  String.format("%.2f", TotalAmount));
		CreditNoteData.put("TaxableValue", String.format("%.2f", TotalAmount));
		CreditNoteData.put("TotalNetWt",   String.format("%.3f", TotalNetWt));
		CreditNoteData.put("TotalGrossWt", String.format("%.3f", TotalGrossWt));

		// Step 4: Extract Payment Details
		Matcher ConvertAdvance = Pattern.compile("Convert to Advance\\s*:\\s*(\\d+\\.\\d{2})").matcher(ExtractedText);
		Matcher Adjustment     = Pattern.compile("Adjustment\\s*:\\s*(\\d+\\.\\d{2})").matcher(ExtractedText);
		if (ConvertAdvance.find()) {
			CreditNoteData.put("PaymentDetails", ConvertAdvance.group(1));
		} else if (Adjustment.find()) {
			CreditNoteData.put("PaymentDetails", Adjustment.group(1));
		}

		// Step 5: Extract only TotalNetValue
		Matcher NetValueMatcher = Pattern.compile("Net Value\\s*(\\d+\\.\\d{2})").matcher(ExtractedText);
		if (NetValueMatcher.find()) {
			CreditNoteData.put("TotalNetValue", NetValueMatcher.group(1));
			CreditNoteData.put("TotalValue", NetValueMatcher.group(1));
		}

		// Step 5b: Extract Total Discount 
		Matcher DiscountMatcher = Pattern.compile("Total Gross Wt\\.\\s*(\\d+\\.\\d{2})\\s*(\\d+\\.\\d{2})").matcher(ExtractedText);
		if (DiscountMatcher.find()) {
			CreditNoteData.put("TotalDiscount", DiscountMatcher.group(2));
		}

		// Step 5c: Extract GST value
		Matcher GstMatcher = Pattern.compile("(\\d+\\.\\d{2})\\s*\\n?(\\d+\\.\\d{2})\\s*\\n?Rupees").matcher(ExtractedText);
		if (GstMatcher.find()) {
			CreditNoteData.put("GST", GstMatcher.group(1));
		}

		// Step 6: Total Qty 
		Matcher QtyMatcherAlt = Pattern.compile("Total Qty\\(Pcs\\)[\\s\\n]*(\\d+)").matcher(ExtractedText);
		if (QtyMatcherAlt.find()) {
			String TotalQty = QtyMatcherAlt.group(1);
			if (!TotalQty.equals(String.valueOf(TotalProducts))) {
				CreditNoteData.put("TotalQty", String.valueOf(TotalProducts));
			} else {
				CreditNoteData.put("TotalQty", TotalQty);
			}
		} else {
			CreditNoteData.put("TotalQty", String.valueOf(TotalProducts));
		}
		return CreditNoteData;
		//How to call in your class
		//		    Map<String, String> results = pdfUtils.SalesReturnPDFCredit();
		//		    String SerialNo       = result.get("SerialNo");
		//		    String TotalNetValue  = result.get("TotalNetValue");
		//		    String TaxableValue   = result.get("TaxableValue");
		//		    String PaymentDetails = result.get("PaymentDetails");
		//		    
		//		    //i indicates each individual product
		//		    int totalProducts = Integer.parseInt(result.get("TotalProducts"));
		//		    for (int i = 1; i <= totalProducts; i++) {
		//		        String grossWt = result.get("Product" + i + ".GrossWt");
		//		        String stoneWt = result.get("Product" + i + ".StoneWt");
		//		        String netWt = result.get("Product" + i + ".NetWt");
		//		        String amount = result.get("Product" + i + ".Amount");
		//		    }
	}

	/// <summary>
	/// To verify the accuracy of values including Adjustment value in a Sales Invoice PDF for negative values(Sales invoice PDF)
	/// Validating both individual fields and a summary table row using extracted PDF text.
	/// Author - Hasna 
	/// </summary>
	public String SaleInvoicePdfAdjustmentValidation(
			String SalesPdfPath,
			String GrossAmount,
			String TotalDiscount,
			String TotalDiamond,
			String TotalQtyPcs,
			String TotalNetWeight,
			String TotalGrossWeight,
			String TaxableValue,
			String NetValue,
			String PaymentAmountHDFC,
			String Adjustment,
			List<String> SkuList,
			Map<String, String> ScannedSKUDataMap
			) throws IOException, InterruptedException {
		String ExtractedInvoiceNo = "";
		String PdfContent = this.IsGetText(SalesPdfPath);

		try {
			Pattern Vpattern = Pattern.compile("Invoice No\\s*:?[\\s]*([\\w\\d]+)");
			Matcher Matcher = Vpattern.matcher(PdfContent);
			if (Matcher.find()) {
				ExtractedInvoiceNo = Matcher.group(1).trim();
				System.out.println("Extracted Invoice No: " + ExtractedInvoiceNo);
			} else {
				System.out.println("Invoice No not found in PDF.");
			}
		} catch (Exception e) {
			System.out.println("Error extracting Invoice No: " + e.getMessage());
		}

		String NormalizedPdfContent = PdfContent.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("\\s+", " ").replaceAll("[\u20B9,=:]", "").trim();

		Map<String, String> ExpectedFields = new HashMap<>();
		ExpectedFields.put("Gross Amount", GrossAmount.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Total Discount",  (TotalDiscount == null || TotalDiscount.trim().isEmpty() ? "0.00" : TotalDiscount)
				.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Total Qty(Pcs)", TotalQtyPcs.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Total Net Wt.", TotalNetWeight.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Total Gross Wt.", TotalGrossWeight.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Taxable Value", TaxableValue.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		//ExpectedFields.put("HDFC (Credit Card)", PaymentAmountHDFC.replaceAll("[\u20B9,]", "").replaceAll("\\s+", "").trim());
		//Jhoncy for TC34
		if (PaymentAmountHDFC != null && !PaymentAmountHDFC.trim().isEmpty()) {
			ExpectedFields.put("HDFC (Credit Card)", PaymentAmountHDFC.replaceAll("[\u20B9,]", "").replaceAll("\\s+", "").trim());
		}
		ExpectedFields.put("Net Value", NetValue);//due to Decimal issue, Calculation issue
		ExpectedFields.put("Adjustment :", Adjustment.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());

		if (TotalDiamond != null && !TotalDiamond.trim().isEmpty() && !TotalDiamond.trim().equals("0")) {
			ExpectedFields.put("Total Diamond amount is :", TotalDiamond.replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		}
		List<double[]> ExpectedLines = new ArrayList<>();
		for (int SkuIndex = 1; SkuIndex <= SkuList.size(); SkuIndex++) {
			double GrossWeight = Math.round(Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_GrossWeight", "0.0")) * 1000.0) / 1000.0;
			double StoneWeight = Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_StoneWeight", "0.0"));
			double NetWeight = Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_NetWeight", "0.0"));
			double TotalAmount = Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_Total", "0.0"));

			System.out.println("SKU" + SkuIndex + " → Gross: " + GrossWeight + ", Stone: " + StoneWeight + ", Net: " + NetWeight + ", Total: " + TotalAmount);

			ExpectedLines.add(new double[]{GrossWeight, StoneWeight, NetWeight, TotalAmount});
		}

		this.VerifyMultipleFieldsInPdf(NormalizedPdfContent, ExpectedFields);
		this.VerifyMultipleItemTotalLines(NormalizedPdfContent, ExpectedLines);

		return ExtractedInvoiceNo;
	}
	/// <summary>
	/// To verify the accuracy of values in a Pro Forma Invoice PDF for negative values(Estimation PDF) for Old Gold
	/// Validating both individual fields and a summary table row using extracted PDF text.
	/// Author - Hasna EK
	/// </summary>	
	public void ProFormaInvoicePdfWithOldGoldNegativeValue(String PdfPath,String TaxableValue,String Gst,String InvoiceTotal,String NetTotal,String TotalAmnt) {
		//,double TotalGrossWeight,double TotalNetWeight
		try {
			// Step 1: Read and normalize PDF content
			//Thread.sleep(5000); // wait for PDF to be downloaded
			String PdfContent = this.IsGetText(PdfPath);
			String NormalizedPdfContent = PdfContent.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").replaceAll("\\s+", " ").trim();
			System.out.println(" Normalized PDF Content = " + NormalizedPdfContent);

			// Step 2: Field-level verifications
			//			ExpectedFields.put("Taxable Value : ", TaxableValue.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()\\-]", "").trim());
			//			ExpectedFields.put("GST : ", Gst.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()\\-]", "").trim());
			//			ExpectedFields.put("Invoice Total : ", InvoiceTotal.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()\\-]", "").trim());
			//			ExpectedFields.put("Net Total : ", NetTotal.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()\\-]", "").trim());
			Map<String, String> ExpectedFields = new HashMap<>();
			ExpectedFields.put("Taxable Value : ", String.format("%.2f",Double.parseDouble(TaxableValue.replaceAll("[^\\d.]", ""))));
			ExpectedFields.put("GST : ", String.format("%.2f", Double.parseDouble(Gst.replaceAll("[^\\d.]", ""))));
			ExpectedFields.put("Invoice Total : ", String.format("%.2f",Double.parseDouble(InvoiceTotal.replaceAll("[^\\d.]", ""))));
			ExpectedFields.put("Net Total : ", String.format("%.2f",Double.parseDouble(NetTotal.replaceAll("[^\\d.]", ""))));

			//			this.VerifyMultipleFieldsInPdf(NormalizedPdfContent, ExpectedFields);
			this.VerifyPdfFieldsWithTolerance(NormalizedPdfContent, ExpectedFields);
			// Step 3: Construct total summary row
			//List<String> ExpectedRows = new ArrayList<>();

			//String CleanNetSubtotal  = TotalAmnt.replaceAll("[^\\d.]", "").replaceAll("[()]", "");
			//String FormattedGrossWeight = String.format("%.3f", TotalGrossWeight);
			//String FormattedNetWeight = String.format("%.3f", TotalNetWeight);
			//String FormattedSubtotal = String.format("%.2f", Double.parseDouble(CleanNetSubtotal ));

			//String TotalRow = String.format("Total %s %s %s",
			//		FormattedGrossWeight,
			//		FormattedNetWeight,
			//		FormattedSubtotal);

			//ExpectedRows.add(TotalRow);

			// Step 4: Row-level verification
			//this.VerifyTableRowInOgPdf(NormalizedPdfContent, ExpectedRows);

		} catch (Exception e) {
			System.out.println("Error in EstimatePDFVerify: " + e.getMessage());
			e.printStackTrace();
		}
	}
	/// <summary>
	/// Validates that each expected row (e.g., invoice line items) is present in the given PDF text.
	/// Author - Hasna
	/// </summary> 
	public void VerifyTableRowInOgPdf(String PdfText, List<String> ExpectedRows) {
		String NormalizedPdf = PdfText.replaceAll("\\s+", " ").trim();

		for (String Row : ExpectedRows) {
			String NormalizedRow = Row.replaceAll("\\s+", " ").trim();
			String[] parts = NormalizedRow.split(" ");

			boolean allPartsFound = true;

			for (String part : parts) {
				// Check if part is a number (allow decimals)
				if (part.matches("-?\\d+(\\.\\d+)?")) {
					double expectedValue = Double.parseDouble(part);
					boolean matched = false;

					// Search for all numbers in the PDF and compare with tolerance
					Matcher m = Pattern.compile("-?\\d+(\\.\\d+)?").matcher(NormalizedPdf);
					while (m.find()) {
						double actualValue = Double.parseDouble(m.group());
						if (Math.abs(actualValue - expectedValue) <= 1.0) {
							matched = true;
							break;
						}
					}

					if (!matched) {
						allPartsFound = false;
						break;
					}
				} else {
					// Non-numeric part: do a direct string check
					if (!NormalizedPdf.contains(part)) {
						allPartsFound = false;
						break;
					}
				}
			}

			if (allPartsFound) {
				System.out.println("ITEM Found: " + Row);
			} else {
				System.out.println("ITEM NOT Found: " + Row);
			}

			asrt.assertTrue(allPartsFound, "Expected parts of PDF row not found (±1 tolerance for numbers): " + Row);
		}
	}
	/// <summary>Add commentMore actions
	/// Extract Receipt Number from Sale Invoice PDF
	/// Author - Hasna EK
	/// </summary>
	public String ExtractReceiptNoFrmSaleInvoice(String PdfPath)throws IOException, InterruptedException {

		String ExtractedInvoiceNo = "";
		String PdfContent = this.IsGetText(PdfPath);

		try {
			Pattern Vpattern = Pattern.compile("Invoice No\\s*:?[\\s]*([\\w\\d]+)");
			Matcher Matcher = Vpattern.matcher(PdfContent);
			if (Matcher.find()) {
				ExtractedInvoiceNo = Matcher.group(1).trim();
				System.out.println("Extracted Invoice No: " + ExtractedInvoiceNo);
			} else {
				System.out.println("Invoice No not found in PDF.");
			}
		} catch (Exception e) {
			System.out.println("Error extracting Invoice No: " + e.getMessage());
		}
		return  ExtractedInvoiceNo;
	}

	/// <summary>Add commentMore actions
	/// Extract Customer Name from Sale Invoice PDF
	/// Author - Christy Reji
	public static String ExtractCustomerNameFromSaleInvoice() throws IOException {

		String ExtractedCustomerName = "";
		String PdfContent=FetchingLatestPdfReadContent();

		try {
			// Extract 'Billed to : CUSTOMER NAME'
			Pattern Patterns = Pattern.compile("Billed to\\s*:\\s*([A-Za-z ]+)", Pattern.CASE_INSENSITIVE);
			Matcher Match = Patterns.matcher(PdfContent);
			if (Match.find()) {
				ExtractedCustomerName = Match.group(1).trim();
				System.out.println("Extracted Customer Name: " + ExtractedCustomerName);
			} else {
				System.out.println("Customer Name not found in PDF.");
			}
		} catch (Exception e) {
			System.out.println("Error extracting Customer Name: " + e.getMessage());
		}
		return ExtractedCustomerName;
	}

	/// <summary>
	/// Fetching latest downloaded PDF from Invoice folder,then read and return the PDF contents
	/// Author - Christy Reji
	public static String FetchingLatestPdfReadContent() throws IOException {

		// Get folder path
		String FolderPath = System.getProperty("user.dir") + "\\Invoices";
		File   Folder     = new File(FolderPath);

		// Get all PDF files in the folder
		File[] PdfFiles   = Folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

		if (PdfFiles == null || PdfFiles.length == 0) {
			System.out.println("No PDF files found in Invoices folder.");
			return "";
		}
		// Sort by last modified and get the latest one
		File LatestPdf = Arrays.stream(PdfFiles)
				.max(Comparator.comparingLong(File::lastModified))
				.orElse(null);
		if (LatestPdf == null) {
			System.out.println("Unable to find the latest PDF.");
			return "";
		}
		// Read content from latest PDF using PDFBox
		PDDocument Document      = PDDocument.load(LatestPdf);
		PDFTextStripper Stripper = new PDFTextStripper();
		String PdfContent        = Stripper.getText(Document);
		Document.close();
		return PdfContent; 
	}

	/// <summary>
	/// To verify the accuracy of values in a Pro Forma Invoice PDF for negative values(Estimation PDF) by
	/// Validating both individual fields and a summary table row using extracted PDF text.
	//Return Sale Pdf validations
	/// Author - Christy
	/// </summary>	
	public void ProFormaInvoicePDFVerifyReturnSale(String PdfPath,String Subtotal,String Tax,String TotalAmount,String NetTotal,String LinesCount,double TotalGrossWeight,double TotalNetWeight) {
		try {
			// Step 1: Read and normalize PDF content
			String PdfContent = this.IsGetText(PdfPath);
			String NormalizedPdfContent = PdfContent.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").replaceAll("\\s+", " ").trim();

			// Step 2: Field-level verifications
			Map<String, String> ExpectedFields = new HashMap<>();
			ExpectedFields.put("Taxable Value :", Subtotal.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").trim());
			ExpectedFields.put("GST :", Tax.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").trim());
			ExpectedFields.put("Invoice Total :", TotalAmount.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").trim());
			ExpectedFields.put("Net Total :", NetTotal.replaceAll("\u20B9", "").replaceAll(",", "").replaceAll("[()]", "").trim());
			this.VerifyMultipleFieldsInPdf(NormalizedPdfContent, ExpectedFields);

			// Step 3: Construct total summary row
			List<String> ExpectedRows = new ArrayList<>();

			String CleanLinesCount = LinesCount.replaceAll("[^\\d]", "");
			String CleanSubtotal = Subtotal.replaceAll("[^\\d.]", "").replaceAll("[()]", "");
			String FormattedGrossWeight = String.format("%.3f", TotalGrossWeight);
			String FormattedNetWeight = String.format("%.3f", TotalNetWeight);
			String FormattedSubtotal = String.format("%.2f", Double.parseDouble(CleanSubtotal));

			// Step 4: Row-level verification
			this.VerifyTableRowInPdf(NormalizedPdfContent, ExpectedRows);

		} catch (Exception e) {
			System.out.println("Error in EstimatePDFVerify: " + e.getMessage());
			e.printStackTrace();
		}
	}

	///<Summary>
	/// Method to get the total pieces from the stock summary
	/// Author: Gokul.P
	///</Summary>

	public static String StockSummary(String filePath, String sectionName,String Counter, String product, String purity ) {
		try {
			String text = IsGetText(filePath);
			Matcher m = Pattern.compile(Pattern.quote(sectionName) + "(.*?)Sub Total", Pattern.DOTALL).matcher(text);
			if (!m.find()) return "Section not found";
			String sectionText = m.group(1);
			String[] lines = sectionText.split("\\r?\\n");
			int total = 0;
			if (sectionName.equalsIgnoreCase("Sales Return SKU")) {
				int start = -1, end = lines.length;

				// Find start of counter block (e.g., "Sales Return")
				for (int i = 0; i < lines.length; i++)
				{
					if (lines[i].trim().toLowerCase().startsWith(Counter.toLowerCase()))
					{
						start = i + 1;
						break;
					}
				}
				if (start == -1) return "Counter not found";

				// Find end of counter block (next counter)
				for (int i = start; i < lines.length; i++) 
				{
					String line = lines[i].trim();
					if ((line.toLowerCase().startsWith("sales return") || line.toLowerCase().startsWith("transit")) &&!line.toLowerCase().startsWith(Counter.toLowerCase())) 
					{
						end = i;
						break;
					}
				}
				for (int i = start; i < end; i++) 
				{
					String line = lines[i].trim();
					if (!line.isEmpty() && line.toLowerCase().contains(product.toLowerCase()) && line.toLowerCase().contains(purity.toLowerCase()))
					{
						String[] parts = line.split("\\s+");
						for (int j = 0; j < parts.length - 1; j++)
						{
							if (parts[j].equalsIgnoreCase(purity))
							{
								try { return String.valueOf(Integer.parseInt(parts[j + 1])); } catch (Exception ignored) {}
							}
						}
					}
				}
			} else 
			{
				//  Handle On-hand SKU logic
				for (String line : lines) 
				{
					line = line.trim();
					if (!line.isEmpty() && line.toLowerCase().contains(product.toLowerCase()) && line.toLowerCase().contains(purity.toLowerCase()) &&(Counter.equalsIgnoreCase("Gold") && line.startsWith("GLD")
							|| Counter.equalsIgnoreCase("Precia") && line.startsWith("PRC")
							|| Counter.equalsIgnoreCase("Uncut") && line.startsWith("UNC")
							|| line.toLowerCase().startsWith(Counter.toLowerCase())))
					{

						String[] parts = line.split("\\s+");
						for (int i = 0; i < parts.length - 1; i++) 
						{
							if (parts[i].equalsIgnoreCase(purity)) 
							{
								try { total += Integer.parseInt(parts[i + 1]); } catch (Exception ignored) {}
								break;
							}
						}
					}
				}
			}
			return String.valueOf(total);
		} catch (Exception e)
		{
			e.printStackTrace();
			return "Error";
		}
	}
	/// <summary>
	/// PDF Validation-Validates OD Purchase Bill 
	/// Author - Chandana Babu
	/// </summary>
	public static Map<String, String> ValidateODPdf(
			String PaymentKeyword,
			List<String> ExpectedGrossWt,
			List<String> ExpectedNetWt,
			List<String> ExpectedGoldRate,
			List<String> ExpectedDiamondWt,
			List<String> ExpectedDiamondRate,
			List<String> ExpectedItemTotal,
			String ExpectedTotal,
			String ExpectedPaymentDetails
			) throws IOException, InterruptedException {

		String NewPdfName = DownloadAndRenameLatestPDF("OD");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);

		Map<String, String> Data = new HashMap<>();
		//System.out.println("Extracted Text:\n" + ExtractedText);
		// Customer Name
		Matcher CustomerMatcher = Pattern.compile("Billed to\\s*:?\\s*(.+)").matcher(ExtractedText);
		if (CustomerMatcher.find()) {
			Data.put("CustomerName", CustomerMatcher.group(1).trim());
		}
		// Invoice Number
		Matcher InvoiceMatcher = Pattern.compile("Invoice No\\s*:?\\s*(\\S+)").matcher(ExtractedText);
		if (InvoiceMatcher.find()) {
			Data.put("InvoiceNumber", InvoiceMatcher.group(1));
		}
		// Gold Products
		Pattern GoldPattern = Pattern.compile(
				"Raw Gold.*?\\s+(\\d+\\.\\d{3})\\s+(\\d+\\.\\d{3})\\s+(\\d{4,9}\\.\\d{2})\\s+(\\d{4,9}\\.\\d{2})"
				);
		Matcher GoldMatcher = GoldPattern.matcher(ExtractedText);
		int GoldIndex = 1;
		while (GoldMatcher.find()) {
			Data.put("GrossWt" + GoldIndex, GoldMatcher.group(1));
			Data.put("NetWt" + GoldIndex, GoldMatcher.group(2));
			Data.put("GoldRate" + GoldIndex, GoldMatcher.group(3));
			GoldIndex++;
		}
		// Diamond Products
		Pattern DiamondPattern = Pattern.compile(
				"Diamond\\s*\\d+\\s*Ct\\s*(\\d+\\.\\d{3})\\s+(\\d{5,9}\\.\\d{2})\\s+(\\d{5,9}\\.\\d{2})"
				);
		Matcher DiamondMatcher = DiamondPattern.matcher(ExtractedText);

		int DiamondIndex = 1;
		while (DiamondMatcher.find()) {
			Data.put("DiamondWt" + DiamondIndex, DiamondMatcher.group(1));
			Data.put("DiamondRate" + DiamondIndex, DiamondMatcher.group(2));
			DiamondIndex++;
		}
		// Item Totals
		Matcher ItemTotalMatcher = Pattern.compile("Item Total\\s*(\\d{5,9}[\\.,]\\d{2})").matcher(ExtractedText);
		int ItemIndex = 1;
		while (ItemTotalMatcher.find()) {
			Data.put("ItemTotal" + ItemIndex, ItemTotalMatcher.group(1).replace(",", "."));
			ItemIndex++;
		}
		// Grand Total
		Matcher TotalMatcher = Pattern.compile("Total\\s*(\\d{5,9}[\\.,]\\d{2})\\s*Rupees").matcher(ExtractedText);
		if (TotalMatcher.find()) {
			Data.put("Total", TotalMatcher.group(1).replace(",", "."));
		}
		// Payment Details
		String KeywordPattern = Pattern.quote(PaymentKeyword);
		Matcher PaymentMatcher = Pattern.compile(KeywordPattern + "\\s*:?\\s*(\\d{5,9}[\\.,]\\d{2})").matcher(ExtractedText);
		if (PaymentMatcher.find()) {
			Data.put("PaymentDetails", PaymentMatcher.group(1).replace(",", "."));
		} else {
			Data.put("PaymentDetails", "Not Found");
		}
		// Validate Numeric Values using Double.parseDouble
		for (int i = 0; i < ExpectedGrossWt.size(); i++) {
			String GrossWt = "GrossWt" + (i + 1);
			Double ActualGrossWeight = Double.parseDouble(Data.get(GrossWt));
			Double ExpectedGrossWeight = Double.parseDouble(ExpectedGrossWt.get(i));
			asrt.assertEquals(ActualGrossWeight, ExpectedGrossWeight, 1.0,"Gross weight mismatch: Expected value is " + ExpectedGrossWeight + " but got " + ActualGrossWeight + " in OD purchase bill");
		}

		for (int i = 0; i < ExpectedNetWt.size(); i++) {
			String NetWt = "NetWt" + (i + 1);
			Double ActualNetWeight = Double.parseDouble(Data.get(NetWt));
			Double ExpectedNetWeight = Double.parseDouble(ExpectedNetWt.get(i));
			asrt.assertEquals(ActualNetWeight, ExpectedNetWeight, 1.0,"Net weight mismatch: Expected value is " + ExpectedNetWeight + " but got " + ActualNetWeight + " in OD purchase bill");
		}

		for (int i = 0; i < ExpectedGoldRate.size(); i++) {
			String GoldRate = "GoldRate" + (i + 1);
			Double ActualRawGoldRate = Double.parseDouble(Data.get(GoldRate));
			Double ExpectedRawGoldRate = Double.parseDouble(ExpectedGoldRate.get(i));
			asrt.assertEquals(ActualRawGoldRate, ExpectedRawGoldRate,1.0,"Gold rate mismatch: Expected value is " + ExpectedRawGoldRate + " but got " + ActualRawGoldRate + " in OD purchase bill");
		}

		for (int i = 0; i < ExpectedDiamondWt.size(); i++) {
			String DiamondWt = "DiamondWt" + (i + 1);
			Double ActualDiamondWeight = Double.parseDouble(Data.get(DiamondWt));
			System.out.print("PDF Daimond Weight " +ActualDiamondWeight);

			Double ExpectedDiamondWeight = Double.parseDouble(ExpectedDiamondWt.get(i));
			asrt.assertEquals(ActualDiamondWeight, ExpectedDiamondWeight,1.0, "Diamond weight mismatch: Expected value is " + ExpectedDiamondWeight + " but got " + ActualDiamondWeight + " in OD purchase bill");
		}

		for (int i = 0; i < ExpectedDiamondRate.size(); i++) {
			String DiamondRate = "DiamondRate" + (i + 1);
			Double ActualDiamondRateValue = Double.parseDouble(Data.get(DiamondRate));
			Double ExpectedDiamondRateValue = Double.parseDouble(ExpectedDiamondRate.get(i));
			asrt.assertEquals(ActualDiamondRateValue, ExpectedDiamondRateValue,1.0, "Diamond rate mismatch: Expected value is " + ExpectedDiamondRateValue + " but got " + ActualDiamondRateValue + " in OD purchase bill");
		}

		for (int i = 0; i < ExpectedItemTotal.size(); i++) {
			String ItemTotal = "ItemTotal" + (i + 1);
			Double ActualItemTotalValue = Double.parseDouble(Data.get(ItemTotal));
			Double ExpectedItemTotalValue = Double.parseDouble(ExpectedItemTotal.get(i));
			asrt.assertEquals(ActualItemTotalValue, ExpectedItemTotalValue, 1.0,"Item total mismatch: Expected value is " + ExpectedItemTotalValue + " but got " + ActualItemTotalValue + " in OD purchase bill");
		}
		Double ActualTotal = Double.parseDouble(Data.get("Total"));
		Double ExpectedTotalVal = Double.parseDouble(ExpectedTotal);
		asrt.assertEquals(ActualTotal, ExpectedTotalVal, 1.0,"Total mismatch: Expected value is " + ExpectedTotalVal + " but got " + ActualTotal + " in OD purchase bill");

		Double ActualPayment = Double.parseDouble(Data.get("PaymentDetails"));
		Double ExpectedPaymentVal = Double.parseDouble(ExpectedPaymentDetails);
		asrt.assertEquals(ActualPayment, ExpectedPaymentVal,1.0, "Payment Details mismatch: Expected value is " + ExpectedPaymentVal + " but got " + ActualPayment + " in OD purchase bill");

		System.out.println("OD PDF validation successful.");
		//System.out.println("Extracted Data: " + Data);
		return Data;
	}


	/// <summary>
	/// Extract Purity from Sale Invoice PDF
	/// Author - Robin
	/// </summary>
	public String ExtractPurityFrmSaleInvoice(String PdfPath)throws IOException, InterruptedException {

		String ExtractedPurity = "";
		String PdfContent = this.IsGetText(PdfPath);

		try {

			Pattern PurityPattern = Pattern.compile("(18K)\\s*:\\s*INR\\s*([\\d,]+\\.\\d{2})");
			Matcher PurityMatcher = PurityPattern.matcher(PdfContent);
			if (PurityMatcher.find()) {
				ExtractedPurity = PurityMatcher.group(1).trim();
				System.out.println("Extracted Purity: " + ExtractedPurity );
			} else {
				System.out.println("18K Purity not found in PDF.");
			}

		} catch (Exception e) {
			System.out.println("Error extracting Invoice No: " + e.getMessage());
		}

		return ExtractedPurity;
	}
	/// <summary>
	/// Extracting Application ID from GOLDEN BLOOM PURCHASE ADVANCE PLAN - Application Form PDF
	/// Author - Aiswarya
	/// </summary>

	public static String ExtractApplicationIdFromPdf() throws IOException, InterruptedException {
		String ApplicationId = "";
		String NewPdfName = DownloadAndRenameLatestPDF("GB Application Form");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);	

		try {

			// Regex to match
			Pattern pattern = Pattern.compile("Application ID\\s*[:：]\\s*(\\w+)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(ExtractedText);
			if (matcher.find()) {
				ApplicationId = matcher.group(1).trim();
				System.out.println("Extracted Application ID: " + ApplicationId);
			} else {
				System.out.println("Application ID not found in PDF.");
			}
		} catch (Exception e) {
			System.out.println("Error extracting Application ID: " + e.getMessage());
		}
		return ApplicationId;
	}
	/// <summary>
	/// Extracts Amount, Cumulative Amount, and Payment Mode from the GPP RECEIPT PDF
	/// Author - Aiswarya
	/// </summary>
	public static Map<String, String> ExtractPaymentDetailsFromPdf() throws IOException, InterruptedException {
		Map<String, String> PaymentDetails = new HashMap<>();

		String NewPdfName = DownloadAndRenameLatestPDF("GPP RECEIPT");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);	

		try {

			String[] lines = ExtractedText.split("\\r?\\n");
			for (int i = 0; i < lines.length - 1; i++) {
				if (lines[i].contains("Amt(Rs.)") && lines[i].contains("Cum. Amt(Rs.)")) {
					// Likely table header found; get next line
					String[] NextLineValues = lines[i + 1].trim().split("\\s+");

					// Example: [11476.00, 0.000, 22k, 91.6, 1000.00, 3000.00, 0.000]
					if (NextLineValues.length >= 6) {
						PaymentDetails.put("Amt", NextLineValues[4].trim());
						PaymentDetails.put("CumAmt", NextLineValues[5].trim());
					}
					break;
				}
			}

			// Extract Payment Mode (same as before)
			Pattern PaymentModePattern = Pattern.compile("Payment Mode\\s*:\\s*(.+)");
			Matcher PaymentModeMatcher = PaymentModePattern.matcher(ExtractedText);
			if (PaymentModeMatcher.find()) {
				PaymentDetails.put("PaymentMode", PaymentModeMatcher.group(1).trim());
			} else {
				System.out.println("Payment Mode not found.");
			}
			for (int i = 0; i < lines.length; i++) {

				// 1. Application ID
				if (lines[i].toLowerCase().contains("application id")) {
					String[] parts = lines[i].split(":");
					if (parts.length >= 2) {
						PaymentDetails.put("ApplicationID", parts[1].trim());
					}
				}

				// 2. Being advance against
				if (lines[i].toLowerCase().contains("being advance against")) {
					String[] parts = lines[i].split(":");
					if (parts.length >= 2) {
						PaymentDetails.put("AdvanceAgainst", parts[1].trim());
					}
				}

				// 3. Customer Name
				if (lines[i].toLowerCase().startsWith("billed to")) {
					String[] parts = lines[i].split(":");
					if (parts.length >= 2) {
						PaymentDetails.put("CustomerName", parts[1].trim());
					}
				}

				// 4. Mobile
				if (lines[i].toLowerCase().startsWith("mobile")) {
					String[] parts = lines[i].split(":");
					if (parts.length >= 2) {
						PaymentDetails.put("Phone", parts[1].trim());
					}
				}

				// 5. Board Rate, Purity, Amt
				if (lines[i].contains("Amt(Rs.)") && lines[i].contains("Cum. Amt(Rs.)")) {
					if (i + 1 < lines.length) {
						String[] NextLineValues = lines[i + 1].trim().split("\\s+");
						// Example: [14088.00, 0.923, 22k, 91.6, 13000.00, 13000.00, 0.923]
						if (NextLineValues.length >= 5) {
							PaymentDetails.put("BoardRate", NextLineValues[0].trim());
							PaymentDetails.put("Purity", NextLineValues[2].trim());
							PaymentDetails.put("Amt", NextLineValues[4].trim());
						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Error extracting payment details: " + e.getMessage());
		}

		return PaymentDetails;
	}


	/// <summary>
	/// Verifies Advance Received,Approx Weight,Fixed Gold Rate,Deposit Type in normalized PDF text.
	/// Author - Hasna EK
	/// </summary>
	public void VerifyFieldsInNormalAdvancePdf(double ExpectedAdvanceReceived, String DepositType) throws  InterruptedException, IOException{
		Map<String, String> pdfData = this.NormalAdvancePdfValidation();

		// Log relevant PDF fields
		System.out.println("Receipt Id: " + pdfData.get("ReceiptId"));
		System.out.println("Advance Received: " + pdfData.get("AdvanceReceived"));
		System.out.println("Approx Weight: " + pdfData.get("ApproxWeight"));
		System.out.println("Fixed Gold Rate: " + pdfData.get("FixedGoldRate"));
		System.out.println("Deposit Type: " + pdfData.get("DepositType"));

		double AdvanceReceived = Double.parseDouble(pdfData.get("AdvanceReceived").replaceAll("[^\\d.-]", "").trim());
		double FixedGoldRate = Double.parseDouble(pdfData.get("FixedGoldRate").replaceAll("[^\\d.-]", "").trim());
		double MaxGoldRate = Double.parseDouble(pdfData.get("MaxGoldRate").replaceAll("[^\\d.-]", "").trim());
		double approxWeightPdf = Double.parseDouble(pdfData.get("ApproxWeight").replaceAll("[^\\d.-]", "").trim());
		String PdfDepositType = pdfData.get("DepositType");
		System.out.println("FixedGoldRate= "+FixedGoldRate);
		// Validations
		asrt.assertEquals(ExpectedAdvanceReceived, AdvanceReceived, 1, 
				"Total amount mismatch: Expected " + ExpectedAdvanceReceived + " but got " + AdvanceReceived + " in the Normal Advance Pdf");
		System.out.println("MaxGoldRate= "+MaxGoldRate);
		asrt.assertEquals(MaxGoldRate, FixedGoldRate, 1, 
				"Fixed Gold Rate mismatch: Expected " + FixedGoldRate + " but got " + MaxGoldRate + " in the Normal Advance Pdf");

		asrt.assertEquals(DepositType, PdfDepositType, 
				"Deposit Type mismatch: Expected " + DepositType + " but got " + PdfDepositType + " in the Normal Advance Pdf");

		// Approx Weight Calculation and Validation
		if (pdfData.get("ApproxWeight") != null && !pdfData.get("ApproxWeight").isEmpty()) {
			double approxWeightExpected = AdvanceReceived / MaxGoldRate;
			double roundedApproxWeightExpected = Math.round(approxWeightExpected * 1000.0) / 1000.0;

			System.out.println("Rounded ApproxWeight Expected: " + roundedApproxWeightExpected);
			System.out.println("ApproxWeight PDF Value: " + approxWeightPdf);

			asrt.assertEquals(approxWeightPdf, roundedApproxWeightExpected, 1, 
					"Mismatch of ApproxWeightValue: Expected: " + approxWeightPdf + " but got " + roundedApproxWeightExpected + " in the Normal Advance Pdf");
		}
	}

	/// <summary>
	/// Verifies Taxable Value,Gross amount, Gross weight, Net weight, Net total,stone weight, lines count etc in credit note PDF text.
	/// Author - Hasna EK
	/// </summary>
	public void VerifyFieldsInCreditNotePdf(Map<String, String> SkuWiseData, Map<String, Object> InvoiceDetails, String LinesCountReturn, double ExpectedAdvanceReceived) throws  InterruptedException, IOException{

		Map<String, String> Result = this.SalesReturnPDFCredit();
		String PdfSerialNoReturn         = Result.get("SerialNo");
		String PdfTotalProductsReturn    = Result.get("TotalProducts");
		String PdfTotalQtyReturn         = Result.get("TotalQty");
		String PdfGrossAmountReturn      = Result.get("GrossAmount");
		String PdfTotalNetWtReturn       = Result.get("TotalNetWt");
		String PdfTotalGrossWtReturn     = Result.get("TotalGrossWt");
		String PdfTotalNetValueReturn    = Result.get("TotalNetValue");
		String PdfTaxableValueReturn     = Result.get("TaxableValue");
		String PdfAdjustmentReturn       = Result.get("PaymentDetails");
		String PdfTotalValueReturn       = Result.get("TotalValue");
		int TotalProductsSale = Integer.parseInt(Result.get("TotalProducts"));

		// Loop through each SKU/Product
		for (int i = 1; i <= TotalProductsSale; i++) {
			String ExpectedGrossWt   		= Result.get("Product" + i + ".GrossWt");
			String ExpectedStoneWt		    = Result.get("Product" + i + ".StoneWt");
			String ExpectedNetWt 			= Result.get("Product" + i + ".NetWt");
			String ExpectedAmount 			= Result.get("Product" + i + ".Amount");
			String ActualGrossWt 			= SkuWiseData.getOrDefault("SKU_" + i + "_GrossWeight", "N/A");
			String ActualStoneWt 			= SkuWiseData.getOrDefault("SKU_" + i + "_StoneWeight", "N/A");
			String ActualNetWt 				= SkuWiseData.getOrDefault("SKU_" + i + "_NetWeight", "N/A");
			String ActualAmount 			= SkuWiseData.getOrDefault("SKU_" + i + "_Total", "N/A");

			Assert.assertEquals(ActualGrossWt, ExpectedGrossWt, "Mismatch in Gross Weight for SKU_" + i +"in PDF Sale Return Invoice details");
			Assert.assertEquals(ActualStoneWt, ExpectedStoneWt, "Mismatch in Stone Weight for SKU_" + i+"in PDF Sale Return Invoice details");
			Assert.assertEquals(ActualNetWt, ExpectedNetWt, "Mismatch in Net Weight for SKU_" + i+"in PDF Sale Return Invoice details");
			Assert.assertEquals(ActualAmount, ExpectedAmount, "Mismatch in Total Amount for SKU_" + i+"in PDF Sale Return Invoice details");
		}
		double ActualTaxableValue 		= Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedTaxableValue  	= Double.parseDouble(PdfTaxableValueReturn);

		double ActualGrossAmount 		= Math.abs(Double.parseDouble(((String) InvoiceDetails.get("SubTotal")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedGrossAmount 		= Double.parseDouble(PdfGrossAmountReturn);

		double ActualTotalGrossWeight   = Math.abs(Double.parseDouble(((String) InvoiceDetails.get("TotalGrossWeight")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim()));
		double ExpectedTotalGrossWeight = Double.parseDouble(PdfTotalGrossWtReturn);

		double ActualNetWeight			= Double.parseDouble(((String) InvoiceDetails.get("TotalNetWeight")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		double ExpectedNetWeight 		= Double.parseDouble(PdfTotalNetWtReturn);

		double ActualNetTotal 			= Double.parseDouble(((String) InvoiceDetails.get("NetTotal")).replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "").trim());
		double ExpectedNetTotal 		= Double.parseDouble(PdfTotalNetValueReturn);

		//		Assert.assertEquals(ExpectedAdvanceReceived, PdfAdjustmentReturn,1, "Mismatch in Taxable Value Actual "+ActualTaxableValue+" and Expected Taxable Value "+ExpectedTaxableValue+"in PDF Sale Return Invoice details");
		Assert.assertEquals(ActualTaxableValue, ExpectedTaxableValue,1, "Mismatch in Taxable Value Actual "+ActualTaxableValue+" and Expected Taxable Value "+ExpectedTaxableValue+"in PDF Sale Return Invoice details");
		Assert.assertEquals(ActualGrossAmount, ExpectedGrossAmount,1, "Mismatch in Gross Amount Actual "+ActualGrossAmount+" and Expected Gross Amount "+ExpectedGrossAmount+"in PDF Sale Return Invoice details");
		Assert.assertEquals(LinesCountReturn, PdfTotalQtyReturn, "Mismatch in No of Product Lines Actual "+LinesCountReturn+" and Expected No of Product Lines "+PdfTotalProductsReturn+"in PDF Sale Return Invoice details");
		Assert.assertEquals(ActualNetWeight, ExpectedNetWeight,1, "Mismatch in Total Net Weight Actual "+ActualNetWeight+" and Expected Net Weight "+ExpectedNetWeight+"in PDF Sale Return Invoice details");
		Assert.assertEquals(ActualTotalGrossWeight, ExpectedTotalGrossWeight,1, "Mismatch in Total Gross Weight Actual "+ActualTotalGrossWeight+" and Expected Total Gross Weight"+ExpectedTotalGrossWeight+"in PDF Sale Return Invoice details");
		Assert.assertEquals(ActualNetTotal, ExpectedNetTotal,1, "Mismatch in Net Total Actual "+ActualNetTotal+" and Expected Net Total "+ExpectedNetTotal+"in PDF Sale Return Invoice details");

	}
	/// <summary>
	/// Verifies expected label-value pairs in normalized PDF text.
	/// Backward-compatible with older test cases.
	/// Tolerates currency signs, brackets, spacing, and float precision up to 1. 
	/// Author - Hasna EK
	/// </summary>
	public void VerifyPdfFieldsWithTolerance(String PdfText, Map<String, String> ExpectedFields) {
		// Step 1: Normalize the entire PDF text by removing currency, symbols, brackets, percent signs, and spaces
		String NormalizedPdfContent = PdfText.replaceAll("[\u20B9,=:]", "")  // remove \u20B9, commas, colons, equal signs
				.replaceAll("\\s+", "")    // remove all whitespace
				.trim();

		for (Map.Entry<String, String> Entry : ExpectedFields.entrySet()) {
			// Step 2: Normalize label and expected value in the same way
			//			String Label = Entry.getKey().trim().replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "");
			String Label = Entry.getKey()
					.replaceAll("[\u20B9,=:]", "")
					.replaceAll("\\d+(\\.\\d+)?%?", "") // removes numeric values like "3.00%" from labels
					.replaceAll("\\s+", "")
					.trim();
			System.out.println("Label = "+Label);

			String ExpectedValue = Entry.getValue().trim().replaceAll("[\u20B9,=:]", "").replaceAll("\\s+", "");

			System.out.println("ExpectedValue = "+ExpectedValue);

			boolean IsFound = false;

			// Step 3: Combine for exact match like "GrossAmount22484.50"
			String ExpectedTextCombined = (Label + ExpectedValue).trim();

			if (NormalizedPdfContent.contains(ExpectedTextCombined)) {
				IsFound = true;
			} else {
				// Step 4: Loose match — check if label exists and value follows
				int LabelIndex = NormalizedPdfContent.indexOf(Label);
				if (LabelIndex != -1) {
					String AfterLabel = NormalizedPdfContent.substring(LabelIndex + Label.length());

					// Step 1: Remove percentage patterns like "3.00%"
					String cleanedAfterLabel = AfterLabel.replaceFirst("\\d+(\\.\\d+)?%", "");

					// Step 2: Match the first number (InvoiceTotal1123.44 or GST11924.74)
					Matcher matcher = Pattern.compile("(\\d+(\\.\\d+)?)").matcher(cleanedAfterLabel);
					if (matcher.find()) {
						String actualStr = matcher.group(1);
						double actual = Double.parseDouble(actualStr);
						double expected = Double.parseDouble(ExpectedValue);

						System.out.println("actual = " + actual);

						if (Math.abs(actual - expected) <= 1) {
							IsFound = true;
						}
					}
				}
			}
			if (IsFound) {
				System.out.println("Found: " + Label + " = " + ExpectedValue);
			} else {
				System.out.println("NOT Found: " + Label + " = " + ExpectedValue);
			}

			Assert.assertTrue(IsFound, "Expected PDF content not found: [" + Label + " = " + ExpectedValue + "]");
		}
	}

	///<Summary>
	///Method validate OS Pdf
	///</Summary>
	public Map<String, String> OSPdfValidation(String paymentKeyword) throws IOException, InterruptedException {

		String NewPdfName = DownloadAndRenameLatestPDF("OS");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);

		Map<String, String> pdfData = new HashMap<>();

		// Match lines with: Gross Wt, Net Wt, Rate, Amount
		Pattern pattern = Pattern.compile("(\\d{1,3}\\.\\d{3})\\s+(\\d{1,3}\\.\\d{3})\\s+(\\d{1,3}\\.\\d{2})\\s+(\\d{3,4}\\.\\d{2})");
		Matcher matcher = pattern.matcher(ExtractedText);

		int index = 1;
		while (matcher.find()) {
			pdfData.put("GrossWt" + index, matcher.group(1));
			pdfData.put("NetWt"   + index, matcher.group(2));
			pdfData.put("Rate"    + index, matcher.group(3));
			index++;
		}

		pdfData.put("TotalItems", String.valueOf(index - 1));

		// Extract adjustment/payment
		Pattern adjustmentPattern = Pattern.compile(Pattern.quote(paymentKeyword) + "\\s*:?\\s*(\\d{3,4}\\.\\d{2})");
		Matcher adjustmentMatcher = adjustmentPattern.matcher(ExtractedText);
		if (adjustmentMatcher.find()) {
			pdfData.put("PaymentDetails", adjustmentMatcher.group(1));
		} else {
			pdfData.put("PaymentDetails", "Not Found");
		}

		// Debug print
		System.out.println("PDF Extracted Data:");
		for (Map.Entry<String, String> entry : pdfData.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		return pdfData;
	}

	/// <summary>
	/// PDF Validation - Validates OU Purchase Bill (Old Uncut)
	/// Author - Robin T Abraham
	/// </summary>
	public static Map<String, Object> ValidateOUPdf(
			String PaymentKeyword,
			String ExpectedCustomer,
			String ExpectedGrossWtStr,
			List<String> ExpectedNetWtOU,
			List<String> ExpectedGoldRate,
			String ExpectedItemTotal,
			String ExpectedTotal,
			String ExpectedPaymentDetails
			) throws IOException, InterruptedException {

		// Convert comma-separated strings to trimmed List<String>
		List<String> ExpectedGrossWtOU = Arrays.stream(ExpectedGrossWtStr.split(","))
				.map(String::trim)
				.collect(Collectors.toList());

		String NewPdfName = DownloadAndRenameLatestPDF("OU");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);

		Map<String, Object> Data = new HashMap<>();

		// Customer Name
		Matcher CustomerMatcher = Pattern.compile("(?i)Billed to\\s*:?\\s*(.+)").matcher(ExtractedText);
		if (CustomerMatcher.find()) {
			String CustomerName = CustomerMatcher.group(1).trim();
			Data.put("CustomerName", CustomerName);
			asrt.assertEquals(CustomerName, ExpectedCustomer, "Customer name mismatch in OU purchase bill");
		}

		// Gold Products
		Pattern GoldPattern = Pattern.compile("Raw Gold.*?(\\d+\\.\\d{3})\\s+(\\d+\\.\\d{3})\\s+(\\d{4,9}\\.\\d{2})");
		Matcher GoldMatcher = GoldPattern.matcher(ExtractedText);
		int GoldIndex = 1;
		while (GoldMatcher.find()) {

			Data.put("GrossWt" + GoldIndex, GoldMatcher.group(1));
			Data.put("NetWt" + GoldIndex, GoldMatcher.group(2));
			Data.put("GoldRate" + GoldIndex, GoldMatcher.group(3));
			GoldIndex++;
		}

		// Stone Products
		List<Map<String, String>> ExtractedStones = new ArrayList<>();
		Pattern StonePattern = Pattern.compile("(?i)([A-Za-z]+)\\s+Ct\\s+(\\d+\\.\\d{3})\\s+(\\d+\\.\\d{2})\\s+(\\d+\\.\\d{2})");
		Matcher StoneMatcher = StonePattern.matcher(ExtractedText);
		while (StoneMatcher.find()) {
			Map<String, String> Stone = new HashMap<>();
			Stone.put("StoneName", StoneMatcher.group(1).trim());
			Stone.put("StoneWt", StoneMatcher.group(2));
			Stone.put("Rate", StoneMatcher.group(3));
			Stone.put("Amount", StoneMatcher.group(4));
			ExtractedStones.add(Stone);
		}
		Data.put("Stones", ExtractedStones);

		// Item Total
		Matcher ItemTotalMatcher = Pattern.compile("Item Total\\s*(\\d{5,9}[\\.,]\\d{2})").matcher(ExtractedText);
		if (ItemTotalMatcher.find()) {
			String ItemTotal = ItemTotalMatcher.group(1).replace(",", ".");
			Data.put("ItemTotal", ItemTotal);
			if (!ExpectedItemTotal.isEmpty()) {
				asrt.assertEquals(Double.parseDouble(ItemTotal), Double.parseDouble(ExpectedItemTotal), "Item total mismatch in OU purchase bill");
			}
		}

		// Grand Total
		Matcher TotalMatcher = Pattern.compile("Total\\s*(\\d{5,9}[\\.,]\\d{2})").matcher(ExtractedText);
		if (TotalMatcher.find()) {
			String Total = TotalMatcher.group(1).replace(",", ".");
			Data.put("Total", Total);

			if (!ExpectedTotal.isEmpty())
				asrt.assertEquals(Double.parseDouble(Total), Double.parseDouble(ExpectedTotal), "Total mismatch in OU purchase bill");
		}

		// Payment Details
		String KeywordPattern = Pattern.quote(PaymentKeyword);
		Matcher PaymentMatcher = Pattern.compile(KeywordPattern + "\\s*:?\\s*(\\d{5,9}[\\.,]\\d{2})").matcher(ExtractedText);
		if (PaymentMatcher.find()) {
			String PaymentVal = PaymentMatcher.group(1).replace(",", ".");
			Data.put("PaymentDetails", PaymentVal);

			if (!ExpectedPaymentDetails.isEmpty())
				asrt.assertEquals(Double.parseDouble(PaymentVal), Double.parseDouble(ExpectedPaymentDetails), "Payment details mismatch in OU purchase bill");
		}

		// Validate Gold values
		try {
			asrt.assertEquals(
					Double.parseDouble(Data.get("GrossWt1").toString()),
					Double.parseDouble(ExpectedGrossWtOU.get(0)),
					"Gross weight mismatch for RG"
					);

			asrt.assertEquals(
					Double.parseDouble(Data.get("NetWt1").toString()),
					Double.parseDouble(ExpectedNetWtOU.get(0)),
					"Net weight mismatch for RG"
					);

			asrt.assertEquals(
					Double.parseDouble(Data.get("GoldRate1").toString()),
					Double.parseDouble(ExpectedGoldRate.get(0)),
					"Gold rate mismatch for RG"
					);
		} catch (NullPointerException e) {
			throw new AssertionError("Missing Raw Gold data in PDF", e);
		}

		System.out.println("OU PDF validation successful.");
		return Data;
	}

	/// <summary>
	/// PDF Validation - Validates Purchase Bill (Old Precia, Old Gold etc.)
	/// Author - Hasna EK
	/// </summary>
	public static Map<String, String> PurchaseBillPdfValidation(String paymentKeyword,double TotalAmountInBillingPge, double TotalGrossWt,double AmountDueInBillingPge, double TotalNetWt) throws IOException, InterruptedException {
		String PdfName = DownloadAndRenameLatestPDF("PurchaseBill");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + PdfName;
		String ExtractedText = IsGetText(PdfPath);

		System.out.println("ExtractedText:\n" + ExtractedText);

		Map<String, String> PdfData = new HashMap<>();

		// Invoice Number
		Matcher InvoiceMatcher = Pattern.compile("Invoice No\\s*:?\\s*(\\S+)").matcher(ExtractedText);
		if (InvoiceMatcher.find()) {
			PdfData.put("InvoiceNumber", InvoiceMatcher.group(1));
		}

		// 1. Extract Item Total
		Matcher ItemTotalMatcher = Pattern.compile("Item Total\\s*(\\d+\\.\\d{2})").matcher(ExtractedText);
		if (ItemTotalMatcher.find()) {
			PdfData.put("ItemTotal", ItemTotalMatcher.group(1));
		}

		// 2. Extract GrossWt and NetWt from line like "2 6.740 6.472"
		Matcher WtMatcher = Pattern.compile("\\d+\\s+(\\d+\\.\\d{3})\\s+(\\d+\\.\\d{3})").matcher(ExtractedText);
		if (WtMatcher.find()) {
			PdfData.put("GrossWt", WtMatcher.group(1));
			PdfData.put("NetWt", WtMatcher.group(2));
		}

		// 3.Extract Total Amount (numeric, after "Total", followed by currency in words or number line)
		Matcher TotalAmountMatcher = Pattern.compile("Total\\s*(\\d{4,6}[\\.,]\\d{2})\\s*Rupees").matcher(ExtractedText);
		if (TotalAmountMatcher.find()) {
			PdfData.put("TotalAmount", TotalAmountMatcher.group(1).replace(",", "."));
		}

		// 4. Extract Payment Details using dynamic keyword like RTGS/NEFT
		String keywordPattern = Pattern.quote(paymentKeyword);
		Matcher PaymentMatcher = Pattern.compile(keywordPattern + "\\s*:?\\s*(\\d+\\.\\d{2})").matcher(ExtractedText);
		if (PaymentMatcher.find()) {
			PdfData.put("PaymentDetails", PaymentMatcher.group(1));
		} else {
			PdfData.put("PaymentDetails", "Not Found");
		}

		// 5. Compare TotalAmount and PaymentDetails
		String TotalAmount = PdfData.get("TotalAmount");
		String Payment = PdfData.get("PaymentDetails");
		if (TotalAmount != null && Payment != null && !Payment.equals("Not Found")) {
			asrt.assertEquals(TotalAmount, Payment, "Mismatch between total"+TotalAmount+" and payment"+Payment+" in Purchase bill");
		} else {
			throw new AssertionError("Missing Total or Payment for validation.");
		}
		double TotalAmountInPdf = Double.parseDouble(TotalAmount);
		asrt.assertEquals(TotalAmountInBillingPge, TotalAmountInPdf,1,"Mismatch in Total amount, Expected "+TotalAmountInBillingPge+"but got"+TotalAmountInPdf+"in Purchase bill");

		String GrossWt = PdfData.get("GrossWt");

		double TotalGrossWtInPdf = Double.parseDouble(GrossWt);
		asrt.assertEquals(TotalGrossWt, TotalGrossWtInPdf,1,"Mismatch in Total amount, Expected "+TotalGrossWt+"but got"+TotalGrossWtInPdf+"in Purchase bill");

		double PaymentAmount = Double.parseDouble(Payment);

		asrt.assertEquals(AmountDueInBillingPge, PaymentAmount,1,"Mismatch in Total amount, Expected "+AmountDueInBillingPge+"but got"+PaymentAmount+"in Purchase bill");

		String NetWtInPdf = PdfData.get("NetWt");
		double TotalNetWtInPdf = Double.parseDouble(NetWtInPdf);
		asrt.assertEquals(TotalNetWt, TotalNetWtInPdf,1,"Mismatch in Total amount, Expected "+TotalNetWt+"but got"+TotalNetWtInPdf+"in Purchase bill");


		System.out.println("Purchase Bill validation successful.");
		System.out.println("Extracted Data: " + PdfData);
		return PdfData;
	}
	/// <summary>
	/// Extracts and validates key financial fields from the Offer Advance PDF,
	/// including Receipt Id, Advance Received, Net Advance, Weight, Rate,
	/// Deposit Type, Payment Mode, Validity, Offer Expiry Date, and Item Details.
	/// Author - Aiswarya
	/// </summary>
	public Map<String, String> OfferAdvancePdfValidation() throws IOException, InterruptedException {

		// Step 1: Download and read PDF
		Thread.sleep(5000);
		String NewPdfName = PdfUtilities.DownloadAndRenameLatestPDF("OfferAdvance");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = PdfUtilities.IsGetText(PdfPath);
		String[] Lines = ExtractedText.split("\\r?\\n");
		String ReceiptId = "";
		String AdvanceReceived = "";
		String ApproxWeight = "";
		String FixedGoldRate = "";
		String DepositType = "";
		String Validity = "";
		String ValidityDays = "";
		String OfferExpiryDate = "";
		boolean nextLineHasAdvance = false;
		BiFunction<String, String, String> ExtractValueByPrefix = (Line, Prefix) -> {
			if (Line.startsWith(Prefix)) {
				String[] Parts = Line.split(":");
				if (Parts.length > 1) {
					return Parts[1].trim();
				}
			}
			return "";
		};
		Pattern amountPattern = Pattern.compile("Amount\\s*:\\s*(\\d+[.]\\d+)");
		Pattern daysPattern = Pattern.compile("\\((\\d+)\\)\\s*Days");
		for (String LineRaw : Lines) {
			String Line = LineRaw.trim();
			// Receipt ID
			if (Line.startsWith("Receipt Id")) {
				ReceiptId = ExtractValueByPrefix.apply(Line, "Receipt Id");
			}
			// Advance Received using regex
			if (Line.contains("Advance Received") && Line.contains("Net Advance")) {
				nextLineHasAdvance = true;
				continue;
			}

			if (nextLineHasAdvance) {
				String[] parts = Line.split("\\s+");
				if (parts.length >= 2) {
					AdvanceReceived = parts[0]; // First value is Advance Received
					// Optional: String NetAdvance = parts[1];
				}
				nextLineHasAdvance = false;
			}
			// Approx Weight
			if (Line.startsWith("Line: 1") && Line.contains("G.Wt.")) {
				Pattern wtPattern = Pattern.compile("G\\.Wt\\.\\s*:\\s*(\\d+\\.\\d+)");
				Matcher wtMatcher = wtPattern.matcher(Line);
				if (wtMatcher.find()) {
					ApproxWeight = wtMatcher.group(1);
				}
			}
			// Validity, Days, Expiry Date
			if (Line.contains("Validity") && Line.contains("Offer Expire Date")) {
				String[] parts = Line.split("\\s+");
				for (int i = 0; i < parts.length; i++) {
					if (parts[i].equalsIgnoreCase("Validity")) {
						Validity = parts[i + 1] + " " + parts[i + 2]; // e.g., 3.00 months
					} else if (parts[i].equalsIgnoreCase("Date")) {
						OfferExpiryDate = parts[i + 1].replace("(", "").replace(")", "").trim();
					}
				}
				// Extract days using regex
				Matcher daysMatcher = daysPattern.matcher(Line);
				if (daysMatcher.find()) {

					ValidityDays = daysMatcher.group(1); // e.g., "30"
				}
			}
			// Gold Rate
			if (Line.startsWith("Max gold rate per gram") || Line.startsWith("Fixed gold rate per gram")) {
				String prefix = Line.startsWith("Max") ? "Max gold rate per gram" : "Fixed gold rate per gram";
				FixedGoldRate = ExtractValueByPrefix.apply(Line, prefix);
			}
			// Deposit Type

			if (Line.startsWith("Deposit Type")) {

				DepositType = ExtractValueByPrefix.apply(Line, "Deposit Type");
			}
		}
		// Prepare final result map
		Map<String, String> ResultMap = new HashMap<>();

		ResultMap.put("ReceiptId", ReceiptId);
		ResultMap.put("AdvanceReceived", AdvanceReceived);
		ResultMap.put("ApproxWeight", ApproxWeight);
		ResultMap.put("FixedGoldRate", FixedGoldRate);
		ResultMap.put("DepositType", DepositType);
		ResultMap.put("Validity", Validity);
		ResultMap.put("ValidityDays", ValidityDays);
		ResultMap.put("OfferExpiryDate", OfferExpiryDate);
		return ResultMap;
	}
	/// <summary>
	/// To verify the accuracy of values including Adjustment value and advance amount in a Sales Invoice PDF for negative values(Sales invoice PDF)
	/// Validating both individual fields and a summary table row using extracted PDF text.
	/// Author - Nandagopan
	/// </summary>
	public String SaleInvoicePdfAdjustmentValidationAdvance(
			String SalesPdfPath,
			String GrossAmount,
			String TotalDiscount,
			String TotalDiamond,
			String TotalQtyPcs,
			String TotalNetWeight,
			String TotalGrossWeight,
			String TaxableValue,
			String NetValue,
			String PaymentAmountHDFC,
			String Adjustment,
			String AdvanceAmount,
			List<String> SkuList,
			Map<String, String> ScannedSKUDataMap
			) throws IOException, InterruptedException {
		String ExtractedInvoiceNo = "";
		String PdfContent = this.IsGetText(SalesPdfPath);

		try {
			Pattern Vpattern = Pattern.compile("Invoice No\\s*:?[\\s]*([\\w\\d]+)");
			Matcher Matcher = Vpattern.matcher(PdfContent);
			if (Matcher.find()) {
				ExtractedInvoiceNo = Matcher.group(1).trim();
				System.out.println("Extracted Invoice No: " + ExtractedInvoiceNo);
			} else {
				System.out.println("Invoice No not found in PDF.");
			}
		} catch (Exception e) {
			System.out.println("Error extracting Invoice No: " + e.getMessage());
		}
		String NormalizedPdfContent = PdfContent.replaceAll("₹", "")
				.replaceAll(",", "")
				.replaceAll("\\s+", " ")
				.replaceAll("[₹,=:]", "")
				.trim();

		Map<String, String> ExpectedFields = new HashMap<>();
		ExpectedFields.put("Gross Amount", GrossAmount.replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Total Discount", (TotalDiscount == null || TotalDiscount.trim().isEmpty() ? "0.00" : TotalDiscount)
				.replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Total Qty(Pcs)", TotalQtyPcs.replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Total Net Wt.", TotalNetWeight.replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Total Gross Wt.", TotalGrossWeight.replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Taxable Value", TaxableValue.replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("HDFC (Credit Card)", PaymentAmountHDFC.replaceAll("[₹,]", "").replaceAll("\\s+", "").trim());
		ExpectedFields.put("Net Value", NetValue); // Keep as-is if using tolerance elsewhere
		if (Adjustment != null && !Adjustment.trim().isEmpty()) {
			ExpectedFields.put("Adjustment :", Adjustment.replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		}

		try {
			double ExpectedAdvance = Math.round(Double.parseDouble(AdvanceAmount.replaceAll("[^\\d.]", "")) * 100.0) / 100.0;

			Pattern pattern = Pattern.compile("Advance Amount\\s*:?[\\s]*([\\d.]+)");
			Matcher matcher = pattern.matcher(PdfContent);

			if (matcher.find()) {
				double ActualAdvance = Math.round(Double.parseDouble(matcher.group(1).trim()) * 100.0) / 100.0;

				if (ExpectedAdvance != ActualAdvance) {
					System.out.printf("Advance Amount mismatch. Expected: %.2f, Found: %.2f%n", ExpectedAdvance, ActualAdvance);
					throw new AssertionError("Advance Amount does not match.");
				} else {
					System.out.printf(" Advance Amount matched: %.2f%n", ActualAdvance);
				}

			} else {
				System.out.println("'Advance Amount' not found in PDF.");
				throw new AssertionError("Advance Amount missing in PDF.");
			}

		} catch (Exception e) {
			System.out.println("Error validating Advance Amount: " + e.getMessage());
		}

		if (TotalDiamond != null && !TotalDiamond.trim().isEmpty() && !TotalDiamond.trim().equals("0")) {
			ExpectedFields.put("Total Diamond amount is :", TotalDiamond.replaceAll("[₹,=:]", "").replaceAll("\\s+", "").trim());
		}
		List<double[]> ExpectedLines = new ArrayList<>();
		for (int SkuIndex = 1; SkuIndex <= SkuList.size(); SkuIndex++) {
			double GrossWeight = Math.round(Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_GrossWeight", "0.0")) * 1000.0) / 1000.0;
			double StoneWeight = Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_StoneWeight", "0.0"));
			double NetWeight = Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_NetWeight", "0.0"));
			double TotalAmount = Double.parseDouble(ScannedSKUDataMap.getOrDefault("SKU_" + SkuIndex + "_Total", "0.0"));

			System.out.println("SKU" + SkuIndex + " → Gross: " + GrossWeight + ", Stone: " + StoneWeight + ", Net: " + NetWeight + ", Total: " + TotalAmount);

			ExpectedLines.add(new double[]{GrossWeight, StoneWeight, NetWeight, TotalAmount});
		}

		this.VerifyMultipleFieldsInPdf(NormalizedPdfContent, ExpectedFields);
		this.VerifyMultipleItemTotalLines(NormalizedPdfContent, ExpectedLines);
		return ExtractedInvoiceNo;
	}
	/// <summary>
	/// To verify the accuracy of values in a Pro Forma Invoice PDF for negative values(Estimation PDF) for Old Gold
	/// Validating both individual fields and a summary table row using extracted PDF text[Adjustment Value]
	/// Author - Nandagopan
	/// </summary>	
	public void ProFormaInvoicePdfWithOldGoldNegativeValueAdjustmentAmount(String PdfPath,String TaxableValue,String Gst,String InvoiceTotal,String TotalAdjustment,String NetTotal,String TotalAmnt) {
		//,double TotalGrossWeight,double TotalNetWeight
		try {
			// Step 1: Read and normalize PDF content
			//Thread.sleep(5000); // wait for PDF to be downloaded
			String PdfContent = this.IsGetText(PdfPath);
			String NormalizedPdfContent = PdfContent.replaceAll("₹", "").replaceAll(",", "").replaceAll("[()]", "").replaceAll("\\s+", " ").trim();
			System.out.println(" Normalized PDF Content = " + NormalizedPdfContent);

			// Step 2: Field-level verifications
			Map<String, String> ExpectedFields = new HashMap<>();
			ExpectedFields.put("Taxable Value : ", String.format("%.2f",Double.parseDouble(TaxableValue.replaceAll("[^\\d.]", ""))));
			ExpectedFields.put("GST : ", String.format("%.2f", Double.parseDouble(Gst.replaceAll("[^\\d.]", ""))));
			ExpectedFields.put("Invoice Total : ", String.format("%.2f",Double.parseDouble(InvoiceTotal.replaceAll("[^\\d.]", ""))));
			ExpectedFields.put("Total Adjustment : ", String.format("%.2f",Double.parseDouble(TotalAdjustment.replaceAll("[^\\d.]", ""))));
			ExpectedFields.put("Net Total : ", String.format("%.2f",Double.parseDouble(NetTotal.replaceAll("[^\\d.]", ""))));
			this.VerifyPdfFieldsWithTolerance(NormalizedPdfContent, ExpectedFields);

		} catch (Exception e) {
			System.out.println("Error in EstimatePDFVerify: " + e.getMessage());
			e.printStackTrace();
		}
	}
	/// <summary>
	/// PDF Validation - Validates OT (Exchange) Purchase Bill with multiple net and stone weights, and rates
	/// Author - Chandana Babu
	/// </summary>
	public static Map<String, String> ValidateOTPdf(
			String PaymentKeyword,
			String ExpectedGrossWt,
			List<String> ExpectedWeights,  // NetWt and StoneWt in order
			List<String> ExpectedRates,     // Metal and Stone Rates in order
			String ExpectedItemTotal,
			String ExpectedTotal,
			String ExpectedPaymentDetails
			) throws IOException, InterruptedException {

		String NewPdfName = DownloadAndRenameLatestPDF("OT");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);

		Map<String, String> Data = new HashMap<>();
		List<String> ActualWeights = new ArrayList<>();
		//Customer Name
		Matcher CustomerMatcher = Pattern.compile("Billed to\\s*:?\\s*(.+)").matcher(ExtractedText);
		if (CustomerMatcher.find()) {
			Data.put("CustomerName", CustomerMatcher.group(1).trim());
		}
		//Invoice Number
		Matcher InvoiceMatcher = Pattern.compile("Invoice No\\s*:?\\s*(\\S+)").matcher(ExtractedText);
		if (InvoiceMatcher.find()) {
			Data.put("InvoiceNumber", InvoiceMatcher.group(1));
		}
		//Gross, Net, Rate from Raw Gold / Platinum lines
		Pattern MetalPattern = Pattern.compile("(Raw Gold|Raw Platinum).*?(\\d+\\.\\d{3})\\s+(\\d+\\.\\d{3})\\s+(\\d{4,9}\\.\\d{2})");
		Matcher MetalMatcher = MetalPattern.matcher(ExtractedText);
		int RateIndex = 1;
		boolean IsGrossWtCaptured = false;

		while (MetalMatcher.find()) {
			if (!IsGrossWtCaptured) {
				Data.put("GrossWt", MetalMatcher.group(2)); // Only first gross weight
				IsGrossWtCaptured = true;
			}
			ActualWeights.add(MetalMatcher.group(3)); // Net weight
			Data.put("Rate" + RateIndex, MetalMatcher.group(4)); // Rate
			RateIndex++;
		}
		//Stone Weights & Rates
		Pattern StonePattern = Pattern.compile("(Diamond|Ruby|Emerald).*?(\\d+\\.\\d{3})\\s+(\\d{4,9}\\.\\d{2})");
		Matcher StoneMatcher = StonePattern.matcher(ExtractedText);

		while (StoneMatcher.find()) {
			ActualWeights.add(StoneMatcher.group(2)); // Stone weight
			Data.put("Rate" + RateIndex, StoneMatcher.group(3)); // Stone rate
			RateIndex++;
		}
		//Item Total
		Matcher ItemTotalMatcher = Pattern.compile("Item Total\\s*(\\d{5,9}[\\.,]\\d{2})").matcher(ExtractedText);
		if (ItemTotalMatcher.find()) {
			Data.put("ItemTotal", ItemTotalMatcher.group(1).replace(",", "."));
		}
		//Grand Total
		Matcher TotalMatcher = Pattern.compile("Total\\s*(\\d{5,9}[\\.,]\\d{2})\\s*Rupees").matcher(ExtractedText);
		if (TotalMatcher.find()) {
			Data.put("Total", TotalMatcher.group(1).replace(",", "."));
		}
		//Payment Details
		String KeywordPattern = Pattern.quote(PaymentKeyword);
		Matcher PaymentMatcher = Pattern.compile(KeywordPattern + "\\s*:?\\s*(\\d{5,9}[\\.,]\\d{2})").matcher(ExtractedText);
		if (PaymentMatcher.find()) {
			Data.put("PaymentDetails", PaymentMatcher.group(1).replace(",", "."));
		} else {
			Data.put("PaymentDetails", "Not Found");
		}

		//VALIDATIONS
		// Gross Weight
		asrt.assertEquals(Double.parseDouble(Data.get("GrossWt")), Double.parseDouble(ExpectedGrossWt), "Gross weight mismatch: Expected is "+ExpectedGrossWt+" but got "+Data.get("GrossWt")+" in purchase bill");

		// Net + Stone Weights
		for (int i = 0; i < ExpectedWeights.size(); i++) {
			Double ActualWeight = Double.parseDouble(ActualWeights.get(i));
			Double ExpectedWeight = Double.parseDouble(ExpectedWeights.get(i));
			asrt.assertEquals(ActualWeight, ExpectedWeight, "Weight mismatch: Expected is "+ExpectedWeight+" but got "+ActualWeight+" in purchase bill");
		}

		// Rates
		for (int i = 0; i < ExpectedRates.size(); i++) {
			String Key = "Rate" + (i + 1);
			Double ActualRate = Double.parseDouble(Data.get(Key));
			Double ExpectedRate = Double.parseDouble(ExpectedRates.get(i));
			asrt.assertEquals(ActualRate, ExpectedRate, "Rate mismatch: Expected is "+ExpectedRate+" but got "+ActualRate+" in purchase bill");
		}

		// Item Total, Total, Payment
		asrt.assertEquals(Double.parseDouble(Data.get("ItemTotal")), Double.parseDouble(ExpectedItemTotal), "Item total mismatchmismatch: Expected is "+ExpectedItemTotal+" but got "+Data.get("ItemTotal")+" in purchase bill");
		asrt.assertEquals(Double.parseDouble(Data.get("Total")), Double.parseDouble(ExpectedTotal),"Total mismatch: Expected is "+ExpectedTotal+" but got "+Data.get("Total")+" in purchase bill");
		asrt.assertEquals(Double.parseDouble(Data.get("PaymentDetails")), Double.parseDouble(ExpectedPaymentDetails), "Payment mismatch: Expected is "+ExpectedPaymentDetails+" but got "+Data.get("PaymentDetails")+" in purchase bill");

		System.out.println("OT PDF validation successful.");
		return Data;
	}

	/// <summary>
	/// PDF Validation - Validates of Purchase Bill Old Skus
	/// Author - Vishnu Manoj K
	/// </summary>
	public Map<String, String> OldSkuBillPdfValidation(String PaymentKeyword) throws IOException, InterruptedException {

		String NewPdfName = DownloadAndRenameLatestPDF("PurchaseBill");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);
		System.out.println("Extracted Text:\n" + ExtractedText);

		Map<String, String> PurchaseBillPdfData = new HashMap<>();

		PurchaseBillPdfData.put("PurchaseBillPdfName", ExtractedText.contains("PURCHASE BILL") ? "PURCHASE BILL" : "");

		Matcher InvoiceMatcher = Pattern.compile("Invoice No\\s*:?\\s*(\\S+)").matcher(ExtractedText);
		if (InvoiceMatcher.find()) {
			PurchaseBillPdfData.put("InvoiceNumber", InvoiceMatcher.group(1));
		}

		Pattern productBlockPattern = Pattern.compile(
				"Raw Gold.*?(\\d+\\.\\d{3})\\s+" + // Gross Wt
						"(\\d+\\.\\d{3})\\s+" +           // Net Wt
						"(\\d{4,6}\\.\\d{2})\\s+" +       // Rate
						"(\\d{4,6}\\.\\d{2}).*?" +        // Total Amount
						"Item Total\\s*(\\d{4,6}[\\.,]\\d{2})",
						Pattern.DOTALL);

		Matcher productBlockMatcher = productBlockPattern.matcher(ExtractedText);
		int productIndex = 1;

		// Stone details pattern (handles Ruby, Emerald etc.)
		Pattern stoneDetailsPattern = Pattern.compile(
				"(?i)([A-Z][a-zA-Z]+)\\s+Grams\\s+(\\d+\\.\\d{3})\\s+(\\d+\\.\\d{2})\\s+(\\d+\\.\\d{2})");

		Matcher stoneMatcher = stoneDetailsPattern.matcher(ExtractedText);
		int stoneMatchIndex = 0;

		while (productBlockMatcher.find()) {
			PurchaseBillPdfData.put("GrossWt"     + productIndex, productBlockMatcher.group(1));
			PurchaseBillPdfData.put("NetWt"       + productIndex, productBlockMatcher.group(2));
			PurchaseBillPdfData.put("Rate"        + productIndex, productBlockMatcher.group(3));
			PurchaseBillPdfData.put("TotalAmount" + productIndex, productBlockMatcher.group(4));
			PurchaseBillPdfData.put("ItemTotal"   + productIndex, productBlockMatcher.group(5).replace(",", "."));

			// Try matching a stone detail for this product
			while (stoneMatcher.find()) {
				stoneMatchIndex++;
				if (stoneMatchIndex == productIndex) {
					String stoneName = stoneMatcher.group(1).trim(); // e.g., Ruby, Emerald
					String stoneWt   = stoneMatcher.group(2).trim();
					String stoneRate = stoneMatcher.group(3).trim();
					PurchaseBillPdfData.put(stoneName + "Wt" + productIndex, stoneWt);
					PurchaseBillPdfData.put(stoneName + "Rate" + productIndex, stoneRate);
					break;
				}
			}

			productIndex++;
		}

		// Fallback if no product block found
		if (productIndex == 1) {
			Pattern fallbackPattern = Pattern.compile(
					"Raw Gold.*?(\\d+\\.\\d{3})\\s+" +
							"(\\d+\\.\\d{3})\\s+" +
							"(\\d{4,6}\\.\\d{2})\\s+" +
					"(\\d{4,6}\\.\\d{2})");

			Matcher fallbackMatcher = fallbackPattern.matcher(ExtractedText);
			if (fallbackMatcher.find()) {
				PurchaseBillPdfData.put("GrossWt1",     fallbackMatcher.group(1));
				PurchaseBillPdfData.put("NetWt1",       fallbackMatcher.group(2));
				PurchaseBillPdfData.put("Rate1",        fallbackMatcher.group(3));
				PurchaseBillPdfData.put("TotalAmount1", fallbackMatcher.group(4));
			}

			Matcher itemTotalMatcher = Pattern.compile("Item Total\\s*(\\d{4,6}[\\.,]\\d{2})").matcher(ExtractedText);
			if (itemTotalMatcher.find()) {
				PurchaseBillPdfData.put("ItemTotal1", itemTotalMatcher.group(1).replace(",", "."));
			}
		}

		// Backup: If "Stone Wt" specifically exists elsewhere (less reliable, optional)
		Matcher backupStoneMatcher = Pattern.compile("Stone\\s*Wt\\s*:?\\s*(\\d+\\.\\d{3})").matcher(ExtractedText);
		int backupStoneIndex = 1;
		while (backupStoneMatcher.find()) {
			PurchaseBillPdfData.put("StoneWt" + backupStoneIndex, backupStoneMatcher.group(1));
			backupStoneIndex++;
		}

		PurchaseBillPdfData.put("TotalQuantity", String.valueOf(productIndex - 1));
		System.out.println("OGPdfData after items: " + PurchaseBillPdfData);

		Matcher totalAmountMatcher = Pattern.compile("Total\\s*(\\d{4,6}[\\.,]\\d{2})\\s*Rupees").matcher(ExtractedText);
		if (totalAmountMatcher.find()) {
			PurchaseBillPdfData.put("TotalAmount", totalAmountMatcher.group(1).replace(",", "."));
		}

		String PaymentKeywordOld = Pattern.quote(PaymentKeyword);
		Matcher paymentMatcher = Pattern.compile(PaymentKeywordOld + "\\s*:?\\s*(\\d{4,6}[\\.,]\\d{2})").matcher(ExtractedText);
		if (paymentMatcher.find()) {
			PurchaseBillPdfData.put("PaymentDetails", paymentMatcher.group(1).replace(",", "."));
		} else {
			PurchaseBillPdfData.put("PaymentDetails", "Not Found");
		}

		// Final assertion
		String Total = PurchaseBillPdfData.get("TotalAmount");
		String Payment = PurchaseBillPdfData.get("PaymentDetails");
		Matcher InvoiceMatcherInvoice = Pattern.compile("Invoice No\\s*:?\\s*(\\S+)").matcher(ExtractedText);
		if (InvoiceMatcherInvoice.find()) {
			PurchaseBillPdfData.put("InvoiceNumber", InvoiceMatcher.group(1));
		}

		System.out.println("Final OGPdfData: " + PurchaseBillPdfData);
		return PurchaseBillPdfData;
	}
	/// <summary>
	/// PDF Validation - To Extract GPP Plan PDF Application ID,PLan,Customer name,Phone no,Nominee name,Bank account no ,IFSC details
	/// Author - Sangeetha M S
	/// </summary>
	public static Map<String, String> ExtractDetailsFromGPPPdf(String SchemeCode) throws IOException, InterruptedException {
		Map<String, String> details = new HashMap<>();
		String NewPdfName = DownloadAndRenameLatestPDF(SchemeCode+" Application Form");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);

		try {
			// Application ID
			Matcher AppId = Pattern.compile("Application ID\\s*[:：]\\s*(\\w+)", Pattern.CASE_INSENSITIVE)
					.matcher(ExtractedText);
			if (AppId.find()) details.put("ApplicationID", AppId.group(1).trim());

			// Plan
			Matcher Plans = Pattern.compile("Plan\\s*[:：]\\s*(.*)").matcher(ExtractedText);
			if (Plans.find()) details.put("Plan", Plans.group(1).trim());

			// Customer Name
			Matcher Customer = Pattern.compile("Customer\\s*[:：]\\s*(.*)", Pattern.CASE_INSENSITIVE).matcher(ExtractedText);
			if (Customer.find()) {
				String FullValue = Customer.group(1).trim(); 

				// Split by "::" and take the last part
				String[] parts = FullValue.split("::");
				String CustomerName = parts[parts.length - 1].trim();  

				details.put("CustomerName", CustomerName);
			}

			// Phone number
			Matcher PhoneNum = Pattern.compile("Phone\\s*[:：]\\s*(\\d+)").matcher(ExtractedText);
			if (PhoneNum.find()) details.put("Phone", PhoneNum.group(1).trim());

			// Nominee Name
			Matcher Nominee = Pattern.compile(
					"Nominee Name\\s*[:：]\\s*(.*?)(?=\\s+Nominee Address|\\r|\\n|$)",
					Pattern.CASE_INSENSITIVE
					).matcher(ExtractedText);

			if (Nominee.find()) {
				details.put("NomineeName", Nominee.group(1).trim());
			}
			// Bank Account No
			Matcher Acc = Pattern.compile("Bank Account No\\s*[:：]\\s*(\\d+)").matcher(ExtractedText);
			if (Acc.find()) details.put("BankAccountNo", Acc.group(1).trim());

			// IFSC Code
			Matcher Ifsc = Pattern.compile("IFSC Code\\s*[:：]\\s*([A-Z0-9]+)").matcher(ExtractedText);
			if (Ifsc.find()) details.put("IFSCCode", Ifsc.group(1).trim());

		} catch (Exception e) {
			System.out.println("Error extracting details: " + e.getMessage());
		}

		// Print results for verification
		details.forEach((k,v) -> System.out.println(k + " : " + v));

		return details;
	}

	/// <summary>
	/// PDF Validation - Validates of Purchase Bill Old Sku(Single)
	/// Author - Nandagopan
	/// </summary>
	public Map<String, String> OldSkuBillPdfValidationCustName(String PaymentKeyword) throws IOException, InterruptedException {

		String NewPdfName = DownloadAndRenameLatestPDF("PurchaseBill");
		String PdfPath = System.getProperty("user.dir") + "\\Invoices\\" + NewPdfName;
		String ExtractedText = IsGetText(PdfPath);
		System.out.println("Extracted Text:\n" + ExtractedText);

		Map<String, String> PurchaseBillPdfData = new HashMap<>();
		// Get "Billed to" name
		Matcher billedToMatcher = Pattern.compile("Billed to\\s*:?\\s*(.+)").matcher(ExtractedText);
		if (billedToMatcher.find()) {
			PurchaseBillPdfData.put("BilledToName", billedToMatcher.group(1).trim());
		} else {
			PurchaseBillPdfData.put("BilledToName", "Not Found");
		}

		PurchaseBillPdfData.put("PurchaseBillPdfName", ExtractedText.contains("PURCHASE BILL") ? "PURCHASE BILL" : "");

		Matcher InvoiceMatcher = Pattern.compile("Invoice No\\s*:?\\s*(\\S+)").matcher(ExtractedText);
		if (InvoiceMatcher.find()) {
			PurchaseBillPdfData.put("InvoiceNumber", InvoiceMatcher.group(1));
		}

		Pattern productBlockPattern = Pattern.compile(
				"Raw Gold.*?(\\d+\\.\\d{3})\\s+" + // Gross Wt
						"(\\d+\\.\\d{3})\\s+" +           // Net Wt
						"(\\d{4,6}\\.\\d{2})\\s+" +       // Rate
						"(\\d{4,6}\\.\\d{2}).*?" +        // Total Amount
						"Item Total\\s*(\\d{4,6}[\\.,]\\d{2})",
						Pattern.DOTALL);

		Matcher productBlockMatcher = productBlockPattern.matcher(ExtractedText);
		int productIndex = 1;

		// Stone details pattern (handles Ruby, Emerald etc.)
		Pattern stoneDetailsPattern = Pattern.compile(
				"(?i)([A-Z][a-zA-Z]+)\\s+Grams\\s+(\\d+\\.\\d{3})\\s+(\\d+\\.\\d{2})\\s+(\\d+\\.\\d{2})");

		Matcher stoneMatcher = stoneDetailsPattern.matcher(ExtractedText);
		int stoneMatchIndex = 0;

		while (productBlockMatcher.find()) {
			PurchaseBillPdfData.put("GrossWt"     + productIndex, productBlockMatcher.group(1));
			PurchaseBillPdfData.put("NetWt"       + productIndex, productBlockMatcher.group(2));
			PurchaseBillPdfData.put("Rate"        + productIndex, productBlockMatcher.group(3));
			PurchaseBillPdfData.put("TotalAmount" + productIndex, productBlockMatcher.group(4));
			PurchaseBillPdfData.put("ItemTotal"   + productIndex, productBlockMatcher.group(5).replace(",", "."));

			// Try matching a stone detail for this product
			while (stoneMatcher.find()) {
				stoneMatchIndex++;
				if (stoneMatchIndex == productIndex) {
					String stoneName = stoneMatcher.group(1).trim(); // e.g., Ruby, Emerald
					String stoneWt   = stoneMatcher.group(2).trim();
					String stoneRate = stoneMatcher.group(3).trim();
					PurchaseBillPdfData.put(stoneName + "Wt" + productIndex, stoneWt);
					PurchaseBillPdfData.put(stoneName + "Rate" + productIndex, stoneRate);
					break;
				}
			}

			productIndex++;
		}

		// Fallback if no product block found
		if (productIndex == 1) {
			Pattern fallbackPattern = Pattern.compile(
					"Raw Gold.*?(\\d+\\.\\d{3})\\s+" +
							"(\\d+\\.\\d{3})\\s+" +
							"(\\d{4,6}\\.\\d{2})\\s+" +
					"(\\d{4,6}\\.\\d{2})");

			Matcher fallbackMatcher = fallbackPattern.matcher(ExtractedText);
			if (fallbackMatcher.find()) {
				PurchaseBillPdfData.put("GrossWt1",     fallbackMatcher.group(1));
				PurchaseBillPdfData.put("NetWt1",       fallbackMatcher.group(2));
				PurchaseBillPdfData.put("Rate1",        fallbackMatcher.group(3));
				PurchaseBillPdfData.put("TotalAmount1", fallbackMatcher.group(4));
			}

			Matcher itemTotalMatcher = Pattern.compile("Item Total\\s*(\\d{4,6}[\\.,]\\d{2})").matcher(ExtractedText);
			if (itemTotalMatcher.find()) {
				PurchaseBillPdfData.put("ItemTotal1", itemTotalMatcher.group(1).replace(",", "."));
			}
		}

		// Backup: If "Stone Wt" specifically exists elsewhere (less reliable, optional)
		Matcher backupStoneMatcher = Pattern.compile("Stone\\s*Wt\\s*:?\\s*(\\d+\\.\\d{3})").matcher(ExtractedText);
		int backupStoneIndex = 1;
		while (backupStoneMatcher.find()) {
			PurchaseBillPdfData.put("StoneWt" + backupStoneIndex, backupStoneMatcher.group(1));
			backupStoneIndex++;
		}

		PurchaseBillPdfData.put("TotalQuantity", String.valueOf(productIndex - 1));
		System.out.println("OGPdfData after items: " + PurchaseBillPdfData);

		Matcher totalAmountMatcher = Pattern.compile("Total\\s*(\\d{4,6}[\\.,]\\d{2})\\s*Rupees").matcher(ExtractedText);
		if (totalAmountMatcher.find()) {
			PurchaseBillPdfData.put("TotalAmount", totalAmountMatcher.group(1).replace(",", "."));
		}

		String PaymentKeywordOld = Pattern.quote(PaymentKeyword);
		Matcher paymentMatcher = Pattern.compile(PaymentKeywordOld + "\\s*:?\\s*(\\d{4,6}[\\.,]\\d{2})").matcher(ExtractedText);
		if (paymentMatcher.find()) {
			PurchaseBillPdfData.put("PaymentDetails", paymentMatcher.group(1).replace(",", "."));
		} else {
			PurchaseBillPdfData.put("PaymentDetails", "Not Found");
		}

		// Final assertion
		String Total = PurchaseBillPdfData.get("TotalAmount");
		String Payment = PurchaseBillPdfData.get("PaymentDetails");
		Matcher InvoiceMatcherInvoice = Pattern.compile("Invoice No\\s*:?\\s*(\\S+)").matcher(ExtractedText);
		if (InvoiceMatcherInvoice.find()) {
			PurchaseBillPdfData.put("InvoiceNumber", InvoiceMatcher.group(1));
		}

		System.out.println("Final OGPdfData: " + PurchaseBillPdfData);
		return PurchaseBillPdfData;
	}
}