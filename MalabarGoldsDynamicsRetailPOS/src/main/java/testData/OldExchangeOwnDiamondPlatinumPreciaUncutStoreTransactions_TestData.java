package testData;
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

	public static int TC59_DiamondSkuCount = 1;
	public static String TC59_DiamondTransferType = "Inter";
	public static String TC59_DiamondFromCounter = "Diamond";
	public static String TC59_DiamondMetalType = "Gold";

	public static int TC59_UncutSkuCount = 1;
	public static String TC59_UncutTransferType = "Inter";
	public static String TC59_UncutFromCounter = "Uncut";
	public static String TC59_UncutMetalType = "Gold";

	public static int TC59_PreciaSkuCount = 1;
	public static String TC59_PreciaTransferType = "Inter";
	public static String TC59_PreciaFromCounter = "Precia";
	public static String TC59_PreciaMetalType = "Gold";

	public static String TC59_Purity ="22k";

	public static String TC59_ReceiptNumber="5000T05IN0010431";
	public static String TC59_LineNumber="5";
	public static String TC59_QCPerson="AKASH T V";
	public static String TC59_SmithPerson="Amjeed Khan";
	public static String TC59_TransactionTypeOG="Exchange";

	public static String TC59_ApprCode = "12345";
	public static String TC59_CardNo = "12345";
	public static String TC59_TransactionType = "Sales";

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
}
