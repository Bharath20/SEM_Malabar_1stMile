package testData;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.sun.tools.javac.code.Attribute.Array;
import utilPack.BasePge;
public class OldPurchaseExchangeOwnorOtherGoldorSilverSale_TestData {

	//TC40
	public static String TC40_CustomerId               = "33CA00161596";
	public static int TC40_SkuCount                    = 1;
	public static String TC40_OgItemName               = "OG";
	public static String TC40_TransferType             = "Inter";
	public static String TC40_FromCounter              = "Gold";
	public static String TC40_MetalType                = "Gold";
	public static double TC40_SaleQty                  = 9.00;
	public static String TC40_Configuration            = "22k";
	public static String TC40_Configuration18k         = "18k";
	public static String TC40_PurchaseOrExchange       = "NExchange";
	public static String TC40_QCPerson                 = "AKASH T V";
	public static String TC40_SmithPerson              = "AKASH T V";
	public static String TC40_GrossPieces              = "1";
	public static String TC40_GrossWeight              = "1";
	public static String TC40_TotalDiamond             ="0";
	public static String TC40_TransactionType1         = "Exchange";
	public static String TC40_TransactionType2         = "Sales";
	public static String TC40_PaymentMethod            = "HDFC";
	public static List<String> TC40_ReturnProducts     = Arrays.asList("Gold");
	public static String TC40_ToCounter                = "Gold";
	public static String TC40_PaymentDetails           = "Adjustment";

	//TC41
	public static String TC41_CustomerNo               = "8086143322";
	public static String TC41_Purity                   = "22k";
	public static String TC41_LineNo                   = "1";
	public static String TC41_QcAndSmithPerson         = "AKASH T V";
	public static String TC41_OGTax                    = "0.00";
	public static String TC41_PaymentDetails           = "RTGS/NEFT";

	//TC47
	public static String TC47_CustomerNo               = "33CA00161601";
	public static String TC47_Purity                   = "22k";
	public static String TC47_QcAndSmithPerson         = "AKASH T V";
	public static String TC47_PaymentDetails           = "RTGS/NEFT";
	public static String TC47_GrossPieces              = BasePge.CreateRandomNumberWithoutLeadingZero(1);
	public static String TC47_GrossWeight              = BasePge.CreateRandomNumberWithoutLeadingZero(2);
	public static String TC47_OGTax                    = "0.00";
	public static String TC47_CustomerName             = "JHONCY JOSEPH";

	//TC42
	public static String TC42_CustomerId               ="33CA00161599";
	public static String TC42_Option                   = "SearchView_AddCustomerForSalesEstimationCommand";
	public static String TC42_ToCounter                = "Gold";
	public static String TC42_TransactionType          ="Sales";
	public static String TC42_TransferType             ="Inter";
	public static String TC42_GoldFromCounter          ="Gold";
	public static String TC42_DiamondFromCounter       ="Diamond";
	public static String TC42_UncutFromCounter         ="Uncut";
	public static String TC42_PreciaFromCounter        ="Precia";
	public static String TC42_MetalType                ="Gold";
	public static String TC42_LineNumber               ="3";
	public static String TC42_QCPerson                 ="AKASH T V";
	public static String TC42_SmithPerson              ="Amjeed Khan";
	public static String TC42_TransactionTypeOG        ="Exchange";
	public static String TC42_CardCode                 = "24354";
	public static String TC42_ApprCode                 ="90876";
	public static String TC42_Purity                   = "22k";	
	public static double TC42_GoldSaleQuantity         =5;
	public static double TC42_DiamondSaleQuantity      =2;
	public static double TC42_SaleQuantity             =2.50;
	public static int TC42_SkuCount                    =1;
	public static String TC42_TotalDiamond             ="1";

	//TC39
	public static String TC39_CustomerNo               = "33CA00161595";
	public static String TC39_Purity                   = "22k";
	public static String TC39_Purity18k                = "18k";
	public static String TC39_QcAndSmithPerson         = "AKASH T V";
	public static String TC39_PaymentDetails           = "RTGS/NEFT";
	public static String TC39_GrossPieces              = "1";
	public static String TC39_GrossWeight              = "10";
	public static String TC39_OGTax                    = "0.00";
	public static String TC39_CustomerName             = "GOKUL JI";

	//TC44 
	public static String TC44_CustomerID               = "33CA00161610";
	public static int TC44_SKUCount                    = 1;
	public static String TC44_TranferType              = "Inter";
	public static String TC44_FromCounterGold          = "Gold";
	public static String TC44_MetalTypeGold            = "Gold";
	public static String TC44_OgtProduct               = "OGT";
	public static String TC44_QCAndSmithPerson         ="AKASH T V";
	public static String TC44_GrossPieces              = "1";
	public static String TC44_GrossWeight              = "10";
	public static double TC44_SaleQuantity             =11;
	public static String TC44_XRFPurity                = "0.919";
	
	public static String TC44_TransactionTypeOGT       ="Exchange";
	public static String TC44_TransactionTypeSales     ="Sales";

	//TC49
	public static String TC49_CustomerId = "7558852527";
	public static int TC49_SkuGoldCount=1;	
	public static String TC49_TransferType="Inter";
	public static String TC49_GoldFromCounter="Gold";
	public static String TC49_MetalType="Gold";	
	public static int TC49_SkuUncutCount=1;	
	public static String TC49_UncutFromCounter="Uncut";
	public static String TC49_Configuration = "22k";
	public static String TC49_PurchaseOrExchange= "NExchange";
	public static String TC49_QCPerson = "AKASH T V";
	public static String TC49_SmithPerson = "AKASH T V";
	public static String TC49_GrossPieces = "1";
	public static String TC49_GrossWeight = "1";
	public static String TC49_OgItemName = "OG";
	public static String TC49_SilverConfiguration = "925";
	public static String TC49_OSItemName = "OS";
	public static String TC49_TransactionTypeExchnge = "Exchange";
	public static String TC49_PaymentDetails = "Adjustment";
	public static String TC49_TransactionTypeSales = "Sales";
	public static String TC49_PaymentMethod = "HDFC";
	
	//TC48
		public static String TC48_CustomerId = "33CA00162200";  // 33CA00162199
		public static String TC48_Configuration = "22k";
		public static String TC48_PurchaseOrExchange = "NPurchase";
		public static String TC48_GrossPieces = "5";
		public static String TC48_GrossWeight = "10";
		public static String TC48_QCPerson = "AKASH T V";
		public static String TC48_SmithPerson = "AKASH T V";
		public static String TC48_AdvanceAmt = "-50000";
		public static String TC48_SalesPerson = "AKASH T V";
		public static String TC48_DueYear = "2026";
		public static String TC48_Remark = "Remarks for the Amount convert to advance";
		public static String TC48_Nomineename = "TestAJAY";
		public static String TC48_NomineRelation = "Father";
		public static String TC48_PartialAmount = "500-";

		//TC45
		public static String TC45_CustomerNo          = "33CA00161604";
		public static List<String> TC45_OSPurity      = Arrays.asList("925","800");
		public static List<String> TC45_OSProductName = Arrays.asList("OS","Old Silver");
		public static String TC45_PaymentDetails      = "Adjustment";
		public static String TC45_PaymentMethods      = "HDFC";
		public static String TC45_QcAndSmithPerson    = "AKASH T V";	
		public static String TC45_GrossPieces         = "1";
		public static String TC45_GrossWeight         = "10";	
		public static String TC45_CustomerName        = "GOKUL P";
		public static String TC45_TransferType        = "Inter";
		public static String TC45_MetalType           = "Gold";
		public static String TC45_FromCounter         = "Gold";
		public static String TC45_ExchangeProduct     = "OS";
		public static int  TC45_SkuCountToAdd         = 1;
		
		//TC43
		public static String TC43_CustomerNo               = "33CA00161601";
		public static String TC43_Purity                   = "22k";
		public static String TC43_QcAndSmithPerson         = "AKASH T V";
		public static String TC43_PaymentDetails           = "RTGS/NEFT";
		public static String TC43_GrossPieces              = BasePge.CreateRandomNumberWithoutLeadingZero(1);
		public static String TC43_GrossWeight              = BasePge.CreateRandomNumberWithoutLeadingZero(2);;
		public static String TC43_OGTax                    = "0.00";
		public static String TC43_CustomerName             = "JHONCY JOSEPH";
		public static String TC43_XRFPurity                = "0.916";
		public static String TC43_XRFPurity1               = "0.917";
		public static String TC43_XRFPurity2               = "0.918";
		
		//TC46
		public static String TC46_CustomerNo          = "33CA00162187";
		public static List<String> TC46_OSPurity      = Arrays.asList("925","800");
		public static List<String> TC46_OSProductName = Arrays.asList("OS","Old Silver");
		public static String TC46_InvoiceNumber       = "5111T05IN0010001";
		public static String TC46_LineNumber925          = "8";
		public static String TC46_LineNumber800          = "7";
		public static String TC46_PaymentDetails      = "Adjustment";
		public static String TC46_PaymentMethods      = "HDFC";
		public static String TC46_QcAndSmithPerson    = "AKASH T V";	
		public static String TC46_CustomerName        = "GOKUL P";
		public static String TC46_TransferType        = "Inter";
		public static String TC46_MetalType           = "Gold";
		public static String TC46_FromCounter         = "Gold";
		public static String TC46_ExchangeProduct     = "OS";
		public static int  TC46_SkuCountToAdd         = 1;
		public static String TC46_TransactionTypeExchnge = "Exchange";
		public static String TC46_TransactionTypeSales = "Sales";
}
