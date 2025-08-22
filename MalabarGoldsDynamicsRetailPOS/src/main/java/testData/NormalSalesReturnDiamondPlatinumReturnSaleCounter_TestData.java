package testData;

import java.util.Arrays;
import utilPack.BasePge;
import java.util.List;

public class NormalSalesReturnDiamondPlatinumReturnSaleCounter_TestData {

	//TC23
	public static String TC23_CustomerNo           = "33CA00161610";
	public static String TC23_CustomerName         = "CHRISTY REJI";
	public static List<String> TC23_ItemList = Arrays.asList("2","Diamond","Gold");
	public static List<String> TC23_ReturnProducts = Arrays.asList("Diamond","Diamond");

	//TC24
	public static String TC24_CustomerNo           = "33CA00161604";
	public static String TC24_CustomerName         = "GOKUL P";
	public static List<String> TC24_SkuValue       = Arrays.asList("2","Platinum","Platinum");;
	public static List<String> TC24_ReturnProducts = Arrays.asList("Platinum","Platinum");

	//TC25
	public static String TC25_CustomerNo = "33CA00161595";
	public static String TC25_CustomerName         = "GOKUL JI";
	//	public static List<String> SkuValueDiamond=Arrays.asList("110001591767","570000025371");
	//	public static List<String> SkuValuePlatinum=Arrays.asList("110001566633","110001566608");
	public static List<String> ReturnProducts=Arrays.asList("Diamond","Diamond","Platinum","Platinum");
	public static List<String> SkuList=Arrays.asList("2","Diamond","Gold","2","Platinum","Platinum");

	//TC26
	public static String TC26_CustomerNo = "33CA00161601";
	public static String TC26_CustomerName = "JHONCY JOSEPH";
	public static int    TC26_SkuGoldCount = 1;
	public static String TC26_TransferType = "Inter";
	public static String TC26_FromCounter = "Gold";
	public static String TC26_MetalType = "Gold";
	public static String TC26_FromCounter2 = "Uncut";
	public static String TC26_FromCounter3 = "Precia";
	public static String TC26_FromCounter4 = "Diamond";
	public static String TC26_ApprovalCode = "12345";
	public static String TC26_CardNo = "12345";
	public static String TC26_TransationType = "Sales Return";
	public static String TC26_TransationType2 = "Sales";
	public static List<String> TC26_ItemList           = Arrays.asList("2","Diamond","Gold");
	public static List<String> TC26_ReturnProducts      = Arrays.asList("Diamond","Diamond");

	//TC27
	public static String TC27_CustomerNo        = "33CA00161603";
	public static String TC27_CustomerName      = "ROBIN ABRAHAM";
	public static String TC27_Option            = "CustomerSearch_addSelectedCustomerToCartCommand";
	public static String TC27_PaymentMode       = "Convert to Advance";
	public static String TC27_SalePersonNumber  = "3";
	public static String TC27_SalePersonName    = "A S Kannan";
	public static String TC27_DueYear           = "2026";
	public static String TC27_NomineeName       = "Grace";
	public static String TC27_NomineeRelation   = "Sister";
	public static String TC27_DepositType       = "Normal Advance";
	public static int    TC27_SkuCount          = 2;
	public static String TC27_TransferType      = "Inter";
	public static String TC27_FromCounter       = "Precia";
	public static String TC27_MetalType         = "Gold";
	public static String TC27_ApprovalCode      = "12345";
	public static String TC27_ApprovalCodeAgain = "12345";
	public static String TC27_TransationType    = "Sales Return";
	public static String TC27_TransationType2   = "Sales";

	//TC28
	public static String TC28_CustomerNo = "7558852527";
	public static List<String> TC_28ItemList=Arrays.asList("1", "Diamond", "Gold");
	public static int    TC28_SkuDiamondCount = 1;
	public static String TC28_TransferType = "Inter";
	public static String TC28_FromCounter = "Diamond";
	public static String TC28_ToCounter = "Diamond";
	public static String TC28_MetalType = "Gold";
	public static String TC28_Configuration = "22k";
	public static String TC28_PurchaseOrExchange= "NExchange";
	public static String TC28_QCPerson = "AKASH T V";
	public static String TC28_SmithPerson = "AKASH T V";
	public static String TC28_GrossPieces = "1";
	public static String TC28_GrossWeight = "1";
	public static String TC28_OgItemName = "OG";
	public static String TC28_TransactionTypeSalesReturn = "Sales Return";
	public static String TC28_TransactionTypeExchnge = "Exchange";
	public static String TC28_PaymentMode = "Convert to Advance";
	public static String TC28_SalePersonNumber = "3";
	public static String TC28_SalePersonName = "A S Kannan";
	public static String TC28_DueYear = "2026";
	public static String TC28_NomineeName = "Name";
	public static String TC28_NomineeRelation = "Sister";
	public static String TC28_DepositType = "Normal Advance";
	public static String TC28_PaymentDetails = "Adjustment";
	public static List<String> TC28_ReturnProducts = Arrays.asList("Diamond");
	public static String TC28_Option = "SearchView_AddCustomerForSalesEstimationCommand";
	public static String TC28_TransactionTypeSales = "Sales";
	public static String TC28_PaymentMethod = "HDFC";
	public static String TC28_TotalDiamond="0";

	//TC29_SalesReturnMultipleGoldandSilver
	public static String TC29_CustomerNo= "7907501134";
	public static String TC29_CustomerName="LAVANYA T";
	public static int    TC29_SkuGoldCount = 1;
	public static String TC29_TransferType = "Inter";
	public static String TC29_FromCounter = "Gold";
	public static String TC29_MetalType = "Gold";
	public static String TC29_ApprovalCode = "12345";
	public static String TC29_ApprovalCodeAgain = "12345";
	public static String TC29_TransationType = "Sales Return";
	public static String TC29_TransationType2 = "Sales";
	public static String TC29_TransationType3 = "Exchange";
	public static List<String> TC29_ItemList= Arrays.asList("1", "Diamond", "Gold","1", "Platinum", "Platinum");
	public static List<String> TC29_ReturnProducts = Arrays.asList("Diamond","Platinum");
	public static String TC29_Product = "OD";
	public static String TC29_QcAndSmithPerson = "AKASH T V";
	public static String TC29_PiecesByPurity= BasePge.CreateRandomNumberWithoutLeadingZero(2);
	public static String TC29_GrossWeightByPurity= "90";

}