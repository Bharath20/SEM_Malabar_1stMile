
package testData;
import java.util.Arrays;
import java.util.List;

import utilPack.BasePge;

public class Utility_TestData {

	//Reusable method for Payment Selection
	public static String Remarks= "test";

	//Reusable method datas
	//MenubarOptions DetailOrder -1)AddToSale 2)CreateCustomer 3)AdvancedSearch 4)ChangeView 5)Select 6)Add To Estimation 7)Add to GPP acc open
	public static String[] MenuBarOptions   = {"CustomerToCartCommand","addNewCustomerCommand","CustomerSearchCriteriaCommand","switchDisplayModeCommand","customerToggleItemSelectionCommand","AddCustomerForSalesEstimationCommand","AddCustomerToOrExtensionViewCommandCommand"};
	public static String  ApprCode          = "11111";
	public static String  CardNumber        = "12345";
	public static String  ChangedueAmount   = "₹ 0.00";
	public static String[] ReceiptSelection = {"Duplicate For Transporter","Original For Recipient","Triplicate For Supplier","INSURANCE CERTIFICATE"};

	//Reusable method to Purchase Old Gold Own
	public static String Product= "Old Gold Own";
	public static String OldUncutOwnProduct= "Old Uncut Own";
	public static String OldDiamondProduct= "Old Diamond Own";
	public static String OGProduct= "OG";
	public static String SearchRTGSPaymentDetails = "RTGS Payment details";
	public static String ERPURL = "https://m365-perf.sandbox.operations.dynamics.com/?cmp=rjal&mi=retaildevices&cls=MGDCSVFileImport_UpdateSKULineItem";



	//AppUtils
	//Nominee Details
	public static String PaymentMode = "Convert to Advance";
	public static String SalePersonNumber = "3";
	public static String SalePersonName = "A S Kannan";
	public static String DueYear = "2026";
	public static String NomineeName = "TC_Malabar";
	public static String NomineeRelation = "Brother";
	public static String NomineeAddress = "Tvm";
	public static String DepositType = "Normal Advance";

	//AppUtils
	//Counter Flow Terminal
	public static String Terminal = "5011D01";


	//ERPUtilities
	public static String SearchMetalRate = "Metal rate";
	public static String WarehouseId = "5011";
	public static String Purity24 = "24k";
	public static String Purity22 = "22k";
	public static String Purity18 = "18k";
	public static String Rate = "8,836";
	public static String SearchDistributionSchedule = "Distribution schedule";
	public static String SearchMyBatchJobs = "My batch jobs";
	public static String NmetalRate = "N-METALRATE";



	//Menubar option in the customer profile page
	// MenuBarOptions DetailOrder:1) Add to GPP acc open  2) Add to Estimation  3) Add to Client Book  4) Edit  5) Add to Sale
	public static String[] MenuBarOptionsCustomerProfile = {"ToGpsacopnExtension","SalesEstimation","AddCustomerToClientBook","EditCustomer","AddCustomerToSale"};

	//Reusable Piece Rate SKU's
	public static List<String> GoldPieceRateSkus = Arrays.asList("100014985866", "100013322344", "100015213154", "100015214759", "100014987054",
			"100014988058", "100014988074", "100006532167", "100006391373", "100006393664");

	public static List<String> DiamondPieceRateSkus = Arrays.asList("330000014067","330000015448","110001522336","330000014095","330000015056",
			"330000015167","330000015262","330000015454","110000871315","570000029014","570000081896","110001585042","110001484662","110001255678",
			"110000579936","110000543812","110000451818","110001596211","110001191432","110000824472","110001556805","110001599854","110001172335",
			"110001191371","110001374751","110001040705","110001377248","110000865340");

	public static List<String> SilverPieceRateSkus = Arrays.asList("KERAAAAHSOFM","KERAAAAHTHAO","KERAAAAIAZQY","KERAAAAHSNZQ","100014142554",
			"100014142553","100014206377","100014230665","100014193726","100008166289","100008167015","100008167537","100008193571","100008193819",
			"100008194986","100008195284","100008203866","100008196119","100008215897","100008216754","100008218840");

	public static List<String> PlatinumPieceRateSkus=Arrays.asList();
	
	public static List<String> RestrictedSkus = Arrays.asList("100009571286","100007803924","100011854588","100007803924","100013170841","100014033098", "100012765411","100013473595","100015581591","100014799435","100011537704","100015090708","110000697619","110000540777","110001213301","110001592022","110001595957","100012942251","100011977668","100015484739","100015583990");

	public static List<String> UncutVirazSkus=Arrays.asList("100015583990","100015147559","100015199583","100013368148","100015146081","100014549101",
			"100015337687","100015584287","100014757112","100011813079","100013370098","100015336361","100011064225","100011664505","100013370102",
			"100014255823","100015090706","100007132688","330000011819","100011924513","100006949501","100012186695","100012276726","100013086401",
			"100008855316","100015199584","100015339445","100015146077","100015145982","100014339649","100014746545","100014746437","100015582968",
			"100011923724","100011812980","100011064693","100007383951","100007258765","100010139194","100014021879","100012183497","100012158074",
			"100012183473","100010818141","100009014145","100012405384","100013793451","100014107325","100014951727","100011537704","100015090708");

//Existing Customer Mobile Numbers
	//Organization
	public static String TC138_PhoneNumber= "8129101010";
	
	//Retail
	public static String TC129_PhoneNumber= "8129144884";
	
}

