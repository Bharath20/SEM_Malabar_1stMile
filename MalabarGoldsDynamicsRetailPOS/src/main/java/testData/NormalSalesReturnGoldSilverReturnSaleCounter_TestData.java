
package testData;

import java.util.Arrays;
import java.util.List;

import utilPack.BasePge;

public class NormalSalesReturnGoldSilverReturnSaleCounter_TestData 
{
	//TC13_MultipleSalesReturnSilver
	public static String TC13_CustID = "33CA00161606";
	public static List<String> TC13_ItemList = Arrays.asList("2", "Silver", "Silver");
	public static List<String> TC13_ReturnProducts = Arrays.asList("Silver","Silver");
	public static String TC13_CustomerName         = "ANAGHA S";
	public static String TC13_ToCounter="Silver";
	public static String TC13_MetalType="Silver";	
	public static String TC13_PaymentMode = "Convert to Advance";
	public static String TC13_SalePersonNumber = "3";
	public static String TC13_SalePersonName = "A S Kannan";
	public static String TC13_DueYear = "2026";
	public static String TC13_NomineeName = "qwerty";
	public static String TC13_NomineeRelation = "Brother";
	public static String TC13_NomineeAddress = "Tvm";
	public static String TC13_DepositType = "Normal Advance";
	//public static String TC13_ReceiptNumber = "5011A06IN0010020";
	//public static List<String> TC13_SkuValue       = Arrays.asList("100007906779","100003929989");

	//TC20
	public static String TC20_CustomerId = "33CA00161596";
	public static String TC20_ReceiptNumber = "5011T05IN0010039";
	public static String TC20_PaymentMode = "Convert to Advance";
	public static String TC20_SalePersonNumber = "3";
	public static String TC20_SalePersonName = "A S Kannan";
	public static String TC20_DueYear = "2026";
	public static String TC20_NomineeName = "Name";
	public static String TC20_NomineeRelation = "Sister";
	public static String TC20_DepositType = "Normal Advance";
	public static String TC20_ToCounter = "Gold";
	public static String TC20_MetalType = "Gold";
	public static List<String> TC20_ItemList = Arrays.asList("2", "Gold", "Gold");
	public static List<String> TC20_ProductsToReturn = Arrays.asList("Gold","Gold");


	//TC14_SalesReturnMultipleGoldandSilver
	public static String TC14_CustID = "7558852527";
	public static String TC14_Option = "CustomerSearch_addSelectedCustomerToCartCommand";
	public static String TC14_ReceiptNumber = "5011T04IN0010012";
	public static String TC14_PaymentMode = "Convert to Advance";
	public static String TC14_SalePersonNumber = "3";
	public static String TC14_SalePersonName = "A S Kannan";
	public static String TC14_DueYear = "2026";
	public static String TC14_NomineeName = "Manu";
	public static String TC14_NomineeRelation = "Brother";
	public static String TC14_DepositType = "Normal Advance";
	public static List<String> TC14_ReturnProducts=Arrays.asList("Gold","Gold","Silver","Silver");
	public static String TC14_CustomerName = "NEETHU MAHENDRAN";
	public static String TC14_FromGoldCounter = "Gold";
	public static String TC14_FromGoldMetalType = "Gold";
	public static String TC14_FromSilverCounter = "Silver";
	public static String TC14_FromSilverMetalType = "Silver";
	public static List<String> TC14_ItemList = Arrays.asList("2", "Gold", "Gold","2","Silver","Silver");

	//TC15_SalesReturnMultipleGoldandSilver
	public static String TC15_CustomerNo= "7907501134";
	public static String TC15_CustomerName="LAVANYA T";
	public static String TC15_Option = "CustomerSearch_addSelectedCustomerToCartCommand";
	public static String TC15_PaymentMode = "Convert to Advance";
	public static String TC15_SalePersonNumber = "3";
	public static String TC15_SalePersonName = "A S Kannan";
	public static String TC15_DueYear = "2026";
	public static String TC15_NomineeName = "Manu";
	public static String TC15_NomineeRelation = "Brother";
	public static String TC15_DepositType = "Normal Advance";
	public static int    TC15_SkuGoldCount = 1;
	public static String TC15_TransferType = "Inter";
	public static String TC15_FromCounter = "Gold";
	public static String TC15_MetalType = "Gold";
	public static int    TC15_SkuGoldCount2 = 1;
	public static String TC15_TransferType2 = "Inter";
	public static String TC15_FromCounter2 = "Uncut";
	public static String TC15_MetalType2 = "Gold";
	public static int    TC15_SkuGoldCount3 = 1;
	public static String TC15_TransferType3 = "Inter";
	public static String TC15_FromCounter3 = "Precia";
	public static String TC15_MetalType3 = "Gold";
	public static String TC15_ApprovalCode = "12345";
	public static String TC15_ApprovalCodeAgain = "12345";
	public static String TC15_TransationType = "Sales Return";
	public static String TC15_TransationType2 = "Sales";


	//TC16
	public static String TC16_CustomerNo                = "33CA00161610";
	public static String TC16_CustomerName              = "CHRISTY REJI";	
	public static int TC16_SKUCount                     = 1;
	public static String TC16_TranferType               = "Inter";
	public static String TC16_FromCounterGold           = "Gold";
	public static String TC16_MetalTypeGold             = "Gold";
	public static String TC16_FromCounterDiamond        = "Diamond";
	public static String TC16_FromCounterUncut          = "Uncut";
	public static String TC16_FromCounterPrecia         = "Precia";
	public static List<String> TC16_ReturnProductSilver = Arrays.asList("Silver");
	public static List<String> TC16_ItemList            = Arrays.asList("1","Silver","Silver");

	//TC17_CounterInAndStockSummary
	public static String TC17_CustomerID="33CA00161604";
	public static List<String> CounterTransfer= Arrays.asList("1","Gold","Gold");
	public static List<String> TC17_ReturnProducts= Arrays.asList("Gold");
	public static String TC17_ToCounter="Gold";
	public static String TC17_MetalType="Gold";
	public static String TC17_Terminal="5011T02";

	//TC18
	public static String TC18_CustomerId = "33CA00161595";	
	public static String TC18_TransactionType = "Sales";
	public static String TC18_PaymentMethod = "HDFC (Credit Card)";
	public static String TC18_ApprCode = "12345";
	public static String TC18_CardNo = "12345";
	public static List<String> TC18_SkuItemList = Arrays.asList("1", "Gold", "Gold");
	public static List<String> ReturnProducts=Arrays.asList("Gold");

	//TC19
	public static String TC19_CustomerNo        = "33CA00161603";
	public static String TC19_CustomerName      ="ROBIN ABRAHAM";
	public static String TC19_Option 	        = "CustomerSearch_addSelectedCustomerToCartCommand";
	public static String TC19_PaymentMode       = "Convert to Advance";

	public static String TC19_SalePersonNumber  = "3";
	public static String TC19_SalePersonName    = "A S Kannan";
	public static String TC19_NomineeName       = "Grace";
	public static String TC19_DueYear           = "2026";
	public static String TC19_NomineeRelation   = "Sister";
	public static String TC19_DepositType       = "Normal Advance";

	public static int    TC19_SkuGoldCount      = 2;
	public static String TC19_TransferType      = "Inter";
	public static String TC19_FromCounter       = "Gold";
	public static String TC19_MetalType         = "Gold";

	public static List<String> TC19_ReturnProducts    = Arrays.asList("Gold");	 
	public static List<String> TC19_SkuList     = Arrays.asList("1","Gold","Gold");

	public static String TC19_ApprovalCode      = "12345";
	public static String TC19_ApprovalCodeAgain = "12345";
	public static String TC19_TransationType    = "Sales Return";
	public static String TC19_TransationType2   = "Sales";

	//TC12
	public static String TC12_CustomerNo = "33CA00161601";
	public static String TC12_CustomerName = "JHONCY JOSEPH";
	public static int    TC12_SkuGoldCount = 2;
	public static String TC12_TransferType = "Inter";
	public static String TC12_FromCounter = "Gold";
	public static String TC12_MetalType = "Gold";
	public static String TC12_ApprovalCode = "12345";
	public static String TC12_CardNo = "12345";
	public static String TC12_TransationType = "Sales Return";
	public static String TC12_TransationType2 = "Sales";
	public static List<String> TC12_ItemList           = Arrays.asList("1","Gold","Gold");
	public static List<String> TC12_ReturnProducts      = Arrays.asList("Gold");

}

