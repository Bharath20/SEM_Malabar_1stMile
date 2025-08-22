

package testData;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class NormalSaleDiamondandPlatinum_TestData {


	//TC07
	public static String TC07_CustomerID = "7558852527";
	public static String TC07_Option = "SearchView_AddCustomerForSalesEstimationCommand";
	public static int TC07_SKUCount = 1;
	public static String TC07_TransferType = "Inter";
	public static String TC07_ToCounterDiamond = "Diamond";
	public static String TC07_MetalTypeDiamond = "Gold";
	public static String TC07_ToCounterPlatinum = "Platinum";
	public static String TC07_MetalTypePlatinum = "Platinum";
	public static String TC07_TransactionType = "Sales";
	public static String TC07_PaymentType = "HDFC (Credit Card)";
	public static String TC07_AprCode = "12345";
	public static String TC07_CardNumber = "12345";
	public static List<String> TC07_ReturnProducts=Arrays.asList("Diamond","Platinum");


	//TC08
		public static String TC08_CustomerId = "33CA00161596";
		public static String TC08_Option = "AddCustomerForSalesEstimationCommand";
//		public static List<String> TC08_SkuList = Arrays.asList("110001557106","110000779349","110001609377");
		public static String TC08_TransactionType = "Sales";
		public static String TC08_PaymentMethod = "HDFC";
		public static List<String> TC08_ReturnProducts = Arrays.asList("Platinum","Platinum","Platinum");
		public static String TC08_ToCounter = "Platinum";
		public static String TC08_MetalType = "Platinum";
		public static String TC08_TransferType = "Inter";
		public static String TC08_FromCounter = "Platinum";
		public static int TC08_skuCountPerCategory = 1;


	//TC09
	public static String TC09_CustomerNo = "8848123534";
	public static String TC09_TransactionType = "Sales";
	public static String TC09_PaymentMethod = "HDFC (Credit Card)";
	public static String TC09_ApprCode = "12345";
	public static String TC09_CardNo = "12345";	
	public static List<String> TC09_RequiredReturnPcsRateSKUs = Arrays.asList("Diamond","Diamond");
	public static String TC09_ToCounter = "Diamond";	
	public static String TC09_MetalType = "Gold";
    
	//TC11
	public static String TC11_CustomerNo = "33CA00161603";
	//public static List<String> TC11_DiamondSkuList = Arrays.asList("110001322824","110001322881","110001326174","570000072397");
	public static int TC11_MakingRateDiamondSKUCount = 1;
	public static String TC11_MakingRateDiamondTransferType = "Inter";
	public static String TC11_MakingRateDiamondFromCounter = "Diamond";
	public static String TC11_MakingRateDiamondMetalType = "Gold";
	public static int TC11_MakingRatePlatinumSKUCount = 1;
	public static String TC11_MakingRatePlatinumTransferType = "Inter";
	public static String TC11_MakingRatePlatinumFromCounter = "Platinum";
	public static String TC11_MakingRatePlatinumMetalType = "Platinum";
	public static String TC11_TransactionType = "Sales";
	public static String TC11_PaymentMethod = "HDFC (Credit Card)";
	public static String TC11_ApprCode = "12345";
	public static String TC11_CardNo = "12345";
	public static String TC11_FromCounter = "Diamond";
	public static String TC11_MetalType = "Gold";
	public static List<String> TC11_ReturnProducts = Arrays.asList("Diamond","Platinum");

	//TC10
	public static String TC10_CustomerId = "33CA00161599";
	public static String TC10_TransferTypeDiamond = "Inter";
	public static String TC10_FromCounter = "Diamond";
	public static String TC10_MetalType = "Gold";
	public static int TC10_SkuCount=1;
	public static String TC10_TransactionType = "Sales";
	public static String TC10_ApprCode = "98765";
	public static String TC10_CardNo = "50252";
	public static List<String> TC10_ReturnProducts=Arrays.asList("Diamond","Diamond");
	//public static List<String> TC10_DiamondSkuList = Arrays.asList("330000014067","110001562490");

}


