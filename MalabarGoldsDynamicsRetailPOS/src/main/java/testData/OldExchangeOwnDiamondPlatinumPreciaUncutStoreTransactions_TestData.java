package testData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.sun.tools.javac.code.Attribute.Array;

import utilPack.BasePge;


public class OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_TestData {

	//TC59
	public static String TC59_CustomerID = "33CA00161603";

	public static int TC59_GoldSkuCount = 1;
	public static String TC59_GoldTransferType = "Inter";
	public static String TC59_GoldFromCounter = "Gold";
	public static String TC59_GoldMetalType = "Gold";

	public static int TC59_PreciaSkuCount = 1;
	public static String TC59_PreciaTransferType = "Inter";
	public static String TC59_PreciaFromCounter = "Precia";
	public static String TC59_PreciaMetalType = "Gold";

	public static String TC59_Purity ="22k";

	//public static String TC59_ReceiptNumber="5000T05IN0010431";
	public static String TC59_LineNumber="5";
	public static String TC59_QCPerson="AKASH T V";
	public static String TC59_SmithPerson="Amjeed Khan";
	public static String TC59_TransactionTypeOU="Exchange";

	public static String TC59_ApprCode = "12345";
	public static String TC59_CardNo = "12345";
	public static String TC59_TransactionType = "Sales";

	public static String TC59_PaymentDetails = "Adjustment";
	public static String TC59_BilledTo = "ROBIN ABRAHAM";

	//TC61
	public static String TC61_CustomerNo = "8086143322";
	public static String TC61_Product = "OD";
	public static String TC61_QcAndSmithPerson = "AKASH T V";
	public static String TC61_ODTax = "0.00";
	public static String TC61_PaymentDetails = "RTGS/NEFT";

	public static Map<String, String> TC61_PiecesByPurity = new HashMap<>();
	public static Map<String, String> TC61_GrossWeightByPurity = new HashMap<>();
	public static Map<String, String> TC61_DiamondPiecesByPurity = new HashMap<>();
	public static Map<String, String> TC61_DiamondWeightByPurity = new HashMap<>();
	public static Map<String, String> TC61_DiamondRateByPurity = new HashMap<>();

	static {
		for (String purity : List.of("22k", "18k")) {
			TC61_PiecesByPurity.put(purity, BasePge.CreateRandomNumberWithoutLeadingZero(1));
			TC61_GrossWeightByPurity.put(purity, BasePge.CreateRandomNumberWithoutLeadingZero(2));
			TC61_DiamondPiecesByPurity.put(purity, BasePge.CreateRandomNumberWithoutLeadingZero(1));
			TC61_DiamondWeightByPurity.put(purity, BasePge.CreateRandomNumberWithoutLeadingZero(1));
			TC61_DiamondRateByPurity.put(purity, BasePge.CreateRandomNumberWithoutLeadingZero(5));
		}
	}

	//TC54
	public static String TC54_CustomerID = "33CA00161601";
	public static String TC54_LineNumber = "2";
	public static String TC54_Purity = "22k";;
	public static String TC54_QcAndSmithPerson = "AKASH T V";
	public static String TC54_OGTax = "0.00";

	//TC62
	public static String TC62_CustomerNo = "33CA00161599";
	public static String TC62_Option="SearchView_AddCustomerForSalesEstimationCommand";

	public static int TC62_SkuCount=1;
	public static String TC62_GoldTransferType = "Inter";
	public static String TC62_GoldFromCounter = "Gold";
	public static String TC62_GoldMetalType = "Gold";
	public static double TC62_GoldSaleQuantity=4;

	public static String TC62_Product = "OD";
	public static String TC62_QcAndSmithPerson = "AKASH T V";
	public static String TC62_Pieces="1";
	public static String TC62_GrossWeight="1.5";
	public static String TC62_DiamondPieces="1";
	public static String TC62_DiamondGrossWeight="0.5";
	public static String TC62_DiamondRate="30000";
	public static String TC62_StonePieces="1";
	public static String TC62_StoneWeight="0.5";
	public static String TC62_StoneRate="2000";

	public static String TC62_TransactionTypeOD="Exchange";
	public static String TC62_TransactionType ="Sales";
	public static String TC62_ApprCode = "12345";
	public static String TC62_CardNo = "12345";
	public static String TC62_PaymentDetails = "Adjustment";

	//TC53
	public static String TC53_CustomerID = "33CA00162187";
	public static int    TC53_GoldSkuCount = 1;
	public static String TC53_GoldTransferType = "Inter";
	public static String TC53_GoldFromCounter = "Gold";
	public static String TC53_GoldMetalType = "Gold";
	public static int    TC53_DiamondSkuCount = 1;
	public static String TC53_DiamondTransferType = "Inter";
	public static String TC53_DiamondFromCounter = "Diamond";
	public static String TC53_DiamondMetalType = "Gold";
	public static int    TC53_UncutSkuCount = 1;
	public static String TC53_UncutTransferType = "Inter";
	public static String TC53_UncutFromCounter = "Uncut";
	public static String TC53_UncutMetalType = "Gold";
	public static int    TC53_PreciaSkuCount = 1;
	public static String TC53_PreciaTransferType = "Inter";
	public static String TC53_PreciaFromCounter = "Precia";
	public static String TC53_PreciaMetalType = "Gold";
	public static String TC53_Purity ="22k";
	public static String TC53_ReceiptNumber="5111T05IN0010001";
	public static String TC53_LineNumber="9";
	public static String TC53_QCPerson="AKASH T V";
	public static String TC53_SmithPerson="Amjeed Khan";
	public static String TC53_TransactionTypeOG="Exchange";
	public static String TC53_DiamondPieces="0";
	public static String TC53_ApprCode = "12345";
	public static String TC53_CardNo = "12345";
	public static String TC53_TransactionType = "Sales";
	public static String TC53_PaymentDetails = "Adjustment";
	public static String TC53_PaymentMode = "Convert to Advance";

	//TC55
	public static String TC55_CustomerID = "33CA00161596";
	//public static String TC55_ReceiptNo = "5000T05IN0010431";
	public static String TC55_OPProduct ="OP";
	public static String TC55_OldPreciaOwnProduct = "Old Precious Own";
	public static String TC55_Purity = "22k";
	public static String TC55_LineNumber = "7";
	public static String TC55_QcAndSmithPerson = "AKASH T V";
	public static String TC55_OpTax = "0.00";
	public static String TC55_PaymentDetails = "RTGS/NEFT";

	//TC57
	public static String TC57_CustomerNo = "8848123534";
	public static int TC57_SaleProductCount = 1;
	public static String TC57_TransferType = "Inter";
	public static String TC57_FromCounterPlatinum = "Platinum";
	public static String TC57_MetalTypePlatinum = "Platinum";
	public static String TC57_FromCounterGold = "Gold";
	public static String TC57_MetalTypeGold = "Gold";
	public static double TC57_Weight = 5.000;
	public static String TC57_OldPatinum = "OT";
	public static String TC57_OldPatinumOwn = "Old Platinum Own";
	public static String TC57_Purity = "950";
	//public static String TC57_InvoiceNo = "5000T05IN0010431";
	public static String TC57_LineNo = "1";
	public static String TC57_QcAndSmithPerson = "AKASH T V";
	public static String TC57_TransactionTypeExchange = "Exchange";
	public static String TC57_TransactionTypeSale = "Sales";
	public static double TC57_TaxAmountForOT = 0.00;
	public static String TC57_PaymentDetails = "Adjustment";

	//TC56
	public static String SearchOldSKUtable = "Old sku table";
	public static String OPDetails = "OP";
	public static String SKUNumber = "1";
	public static String TC56_CustomerID = "7558852527";
	public static int TC56_SkuGoldCount=1;	
	public static String TC56_TransferType="Inter";
	public static String TC56_GoldFromCounter="Gold";
	public static String TC56_MetalType="Gold";	
	public static String TC56_OPProdcut="OP";
	public static String TC56_OldPreciaProduct=  "Old Precious Own";
	public static String TC56_LineNo=  "1";
	public static String TC56_QcAndSmithPerson = "AKASH T V";
	public static String TC56_OPItemName = "OP";
	public static String TC56_TransactionTypeExchnge = "Exchange";
	public static String TC56_DepositType = "Normal Advance";
	public static String TC56_TransactionTypeSales = "Sales";
	public static String TC56_PaymentMethod = "HDFC";

	//TC50
	public static String TC50_CustomerId = "7558852527";
	public static int    TC50_SkuGoldCount=1;	
	public static String TC50_TransferType="Inter";
	public static String TC50_GoldFromCounter="Gold";
	public static String TC50_MetalType="Gold";	
	public static int    TC50_SkuUncutCount=1;	
	public static String TC50_UncutFromCounter="Uncut";
	public static String TC50_Configuration = "22k";
	public static String TC50_OgItemName = "OU";
	public static String TC50_TransactionTypeExchnge = "Exchange";
	public static String TC50_PaymentDetails = "Adjustment";
	public static String TC50_TransactionTypeSales = "Sales";
	public static String TC50_PaymentMethod = "HDFC";
	public static String TC50_Purity                   = "22k";
	public static String TC50_QcAndSmithPerson         = "AKASH T V";
	public static String TC50_GrossPieces1              = "1";
	public static String TC50_GrossWeight1              = "2";
	public static String TC50_GrossPieces2              = "1";
	public static String TC50_GrossWeight2              = "2";
	public static String TC50_StonePieces1="1";
	public static String TC50_StoneWeight1="0.5";
	public static String TC50_StonePieces2="1";
	public static String TC50_StoneWeight2="0.5";
	public static String TC50_StoneRate1="2000";
	public static String TC50_StoneRate2="2000";

	//TC63
	public static String TC63_CustomerID = "33CA00161603";

	public static int    TC63_SkuGoldCount=1;	
	public static String TC63_TransferType="Inter";
	public static String TC63_FromCounter="Gold";
	public static String TC63_MetalType="Gold";	
	public static double TC63_SaleQuantity=6.000;

	public static int    TC63_SkuUncutCount=1;	
	public static String TC63_UncutFromCounter="Uncut";
	public static String TC63_Configuration = "22k";
	public static String TC63_OuItemName = "OU";
	public static String TC63_TransactionTypeExchnge = "Exchange";
	public static String TC63_PaymentDetails = "Adjustment";
	public static String TC63_TransactionTypeSales = "Sales";
	public static String TC63_PaymentMethod = "HDFC";
	public static String TC63_Purity = "22k";
	public static String TC63_QcAndSmithPerson = "AKASH T V";
	public static String TC43_PaymentDetails = "RTGS/NEFT";
	public static String TC63_GrossPieces = "1";
	public static String TC63_GrossWeight = "2";
	public static String TC63_StonePieces="1";
	public static String TC63_StoneWeight="0.5";
	public static String TC63_StoneRate="2000";

	//52
	public static String TC52_CustomerID = "33CA00162208 ";
	public static String TC52_Purity = "18k";
	public static String TC52_QcAndSmithPerson = "AKASH T V";
	public static String TC52_Purchase = "NPurchase"; 
	public static String TC52_Exchange = "NExchange";
	public static String TC52_SearchOldSku = "Old sku table";
	public static String TC52_DesignCode = "OD";
	public static String TC52_LineNo = "1";

	public static List<String> TC52_SKUForMetalRate = new ArrayList<>(
			//  Arrays.asList("1100013338510", "110000567494", "110001282357")
			Arrays.asList("JNG1DOAAPTF", "MBI2DEEQROB", "CMR1DRAABHS")
			);
	public static String TC52_Setdata = "110000193614A";

	//TC58
	public static String TC58_CustomerNo     = "33CA00161599";
	public static String TC58_ProductType    ="OT";
	public static String TC58_Purity         ="950";
	public static String TC58_QCPerson       ="AKASH T V";
	public static String TC58_SmithPerson    ="Amjeed Khan";
	public static String TC58_LineNo         = "1";
	public static String OTDetails           = "OT";
	public static String SKUNumbers          = "1";
	public static String TC58_OTTax          ="0.00";
	public static String TC58_PaymentDetails = "RTGS/NEFT";

	//TC51
	public static String TC51_CustomerID              = "33CA00161610";
	public static String TC51_Purity                  = "18k";
	public static String TC51_TransferType            = "Inter";
	public static String TC51_FromCounterUncut        = "Uncut";
	public static String TC51_MetalTypeGold           = "Gold";
	public static double TC51_Weight                  =20;
	public static int TC51_SaleProductCount           = 1;
	public static String TC51_TransactionTypeExchange ="Exchange";
	public static String TC51_TransactionTypeSales    ="Sales";
	public static String TC51_OldSkuTable_SKU1        ="1205";
	public static String TC51_OldSkuTable_SKU2        ="1028";
	
	//TC60
	
	public static String TC60_CustomerID = "33CA00161615 ";
	public static String TC60_Purity = "22k";
	public static String TC60_QcAndSmithPerson = "AKASH T V";
	public static String TC60_Purchase = "NPurchase"; 
	public static String TC60_Exchange = "NExchange";
	public static String TC60_SearchOldSku = "Old sku table";
	public static String TC60_DesignCode = "OU";
	public static String TC60_LineNo = "1";
	public static List<String> TC60_SKUForMetalRate = new ArrayList<>(
			  //  Arrays.asList("1100013338510", "110000567494", "110001282357")
				Arrays.asList("000000117", "000000135", "0BAHU0796")
			);
	public static String TC60_Setdata = "1000143631001";
	public static String TC60_OUTax = "0.00";
	public static String TC60_PaymentDetails = "RTGS/NEFT";
	public static String TC60_OUPurity = "22k"; 
}
