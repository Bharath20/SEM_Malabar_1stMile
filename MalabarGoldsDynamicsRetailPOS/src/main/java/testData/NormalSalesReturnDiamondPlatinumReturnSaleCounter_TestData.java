package testData;

import java.util.Arrays;
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

}