package testData;

import java.util.Arrays;
import java.util.List;

public class NormalSaleUncutandPrecia_TestData
{
	//TC33
	public static String TC33_CustomerID = "33CA00161601";
	//public static List<String> TC33_SkuList = Arrays.asList("100008885993","100007531053");
	public static List<String> TC33_ReturnProducts=Arrays.asList("Precia","Precia");
	public static String TC33_TransferType = "Inter";
	public static String TC33_FromCounter = "Precia";
	public static String TC33_MetalType = "Gold";
	public static String TC33_ApprCode = "12345";
	public static String TC33_CardNo = "12345";
	public static String TC33_TransactionType = "Sales";
	public static int TC33_SKUCount = 2;

	//TC30  
		public static String TC30_CustomerId="33CA00161599";
		public static String TC30_Option = "SearchView_AddCustomerForSalesEstimationCommand";
		public static int TC30_SkuUncutCount=1;	
		public static int TC30_SkuPreciaCount=1;	
		public static String TC30_TransferType="Inter";
		public static String TC30_UncutFromCounter="Uncut";
		public static String TC30_PreciaFromCounter="Precia";
		public static String TC30_MetalType="Gold";	
		public static String TC30_TransactionType = "Sales";
		public static String TC30_ApprCode = "54321";
		public static String TC30_CardCode = "12345";
		//public static List<String> TC30_SkuUncutList = Arrays.asList("100015317993");
		//public static List<String> TC30_SkuPreciaList= Arrays.asList("100011201515");
		//public static List<String> TC30_ReturnProducts=Arrays.asList("Uncut","Precia");

	//TC31
		public static String TC31_CustomerID = "33CA00161603";
		public static int TC31_SKUCount = 2;
		public static String TC31_TransferType = "Inter";
		public static String TC31_FromCounter = "Uncut";
		public static String TC31_MetalType = "Gold";
		public static String TC31_ApprCode = "12345";
		public static String TC31_CardNo = "12345";
		public static String TC31_TransactionType = "Sales";
	
	//TC32
	public static String TC32_CustomerID = "33CA00161596";
	public static String TC32_Option = "AddCustomerForSalesEstimationCommand";
//	public static List<String> TC32_SkuList = Arrays.asList("100009014145");
	public static String TC32_TransactionType = "Sales";
	public static String TC32_PaymentMethod = "HDFC";
	public static List<String> TC32_ReturnProducts = Arrays.asList("Uncut");
	public static String TC32_ToCounter = "Uncut";
	public static String TC32_MetalType = "Gold";
}
