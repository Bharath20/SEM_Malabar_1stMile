
package testData;

import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;

import utilPack.BasePge;

public class CustomerCreation_TestData {

	//TC132
	public static String TC132_FirstName = "Arun";
	public static String TC132_RegionCode = "Hindu";
	public static String CustomerSearch = "All customers";
	public static String TC132_PreferredLanguage = "Malayalam";
	public static String TC132_CustomerLanguageOptions = "English (India)";
	public static String TC132_PhoneNumber = "7"+BasePge.CreateRandomNumber(9);
	public static String TC132_LastName = BasePge.CreateRandomWithoutNumbers(2);
	public static List<String> TC132_ZipCode = Arrays.asList(
			"689126", "689122", "689121", "689121", "689511", "689573", "689124", "689574", "689506", "689626",
			"689510", "689123", "689595", "689572", "689109", "689104", "689590", "688012", "688001", "688011",
			"688001", "688011", "688005", "688011", "688007", "688561", "688535", "688534", "688530", "688006",
			"688505", "690505", "690105", "690507", "688524", "688524", "690104", "690106", "690532", "690534",
			"689624", "688537", "688529", "688501", "688522", "690110", "690535", "689504", "690572", "690516",
			"690517", "688506", "690502", "690502", "690508", "689508", "690509", "689521", "690533", "689512",
			"690548", "688533", "689623", "690558", "688538", "689622", "690101", "690101", "690101", "688539",
			"689627", "688502", "688503", "688525", "689505", "690506", "690511", "690513", "690571", "690504",
			"690510", "690529", "690503", "690512", "688541", "688521", "688531", "690531", "689520", "688570",
			"690559", "688526", "688504", "690537", "688004", "688014", "690527", "688003");

	//TC129
	public static String TC129_FirstName = "Aravind";
	public static String TC129_RegionCode = "Hindu";
	public static String TC129_PreferredLanguage = "Malayalam";
	public static String TC129_CustomerLanguageOptions = "English (India)";
	public static String TC129_LastName = BasePge.CreateRandomWithoutNumbers(2);
	public static String TC129_NewLastName = BasePge.CreateRandomWithoutNumbers(3);

	//TC_130 
	public static String TC130_CustomerType = "Person";
	public static String TC130_customerTitleOptions = "Mr.";
	public static String TC130_CustomerFirstName = "Test"+BasePge.CreateRandomWithoutNumbers(1).toUpperCase();
	public static String TC130_CustomerLastName = "User"+BasePge.CreateRandomWithoutNumbers(1).toUpperCase();
	public static String TC130_CustomerContact = "9"+BasePge.CreateRandomNumber(9);
	public static String TC130_customerReceiptPreferenceOptions = "Standard receipt";
	public static String TC130_customerCurrencyOptions = "INR";
	public static String customerLanguageOptions = "English (India)";
	public static String TC130_ZipCode= "688539";
	public static String TC130_CustomerAgeGroupCode= "(20-25)";
	public static String TC130_RegionCode= "Hindu";
	public static String TC130_CasteCode= "SURI";
	public static String TC130_ProfessionCode= "Engineer";
	public static String TC130_PreferredLanguage= "Malayalam";
	public static String TC130_Email= "test@gmail.com";
	public static String TC130_IDProofType= "PAN";
	public static String TC130_PanNumber= BasePge.CreateRandomWithoutNumbers(5) +BasePge.CreateRandomNumber(4)+ BasePge.CreateRandomWithoutNumbers(1).toUpperCase();
	public static String TC130_Preferrednumber = "9"+BasePge.CreateRandomNumber(9);
	public static String TC130_AddressProofType = "Aadhaar";
	public static String TC130_AadharNo = "9"+BasePge.CreateRandomNumber(11);
	public static String TC130_ReferredBy = "Self";
	public static String TC130_BankAccNo = "9"+BasePge.CreateRandomNumber(11);
	public static String TC130_IFSCCode = "SBIN0070028";
	public static String TC130_BranchName = "Kerala";  
	public static String TC130_BankName1 = "01::State Bank Of India";
	public static String TC130_DobDate= "4";
	public static String TC130_DobMonth= "August";
	public static String TC130_DobYear= "1995";
	public static String TC130_AnDate= "4";
	public static String TC130_AnMonth= "August";
	public static String TC130_AnYear= "2005";
	public static String TC130_CustomerSearch= "All Customers";
	//public static String TC130_ERPLink = "https://m365-perf.sandbox.operations.dynamics.com/?cmp=rjal&mi=retaildevices&cls=MGDCSVFileImport_UpdateSKULineItem";


	//TC128

	public static String TC128_FirstName = "KARAN"+BasePge.CreateRandomWithoutNumbers(2).toUpperCase();
	public static String TC128_LastName= "H"+BasePge.CreateRandomWithoutNumbers(2).toUpperCase();
	public static String TC128_PhoneNumber= "8"+BasePge.CreateRandomNumber(9);
	public static String TC128_CustomerReceiptlanguage= "English (India)";
	public static String TC128_CustomerReligion= "Hindu";
	public static String TC128_CustomerPrefLanguage= "Malayalam";
	public static String TC128_CustomerSearch = "All customers";
	//public static String TC128_ERPLink = "https://m365-perf.sandbox.operations.dynamics.com/?cmp=rjal&mi=retaildevices&cls=MGDCSVFileImport_UpdateSKULineItem";

	//TC138

	public static String TC138_CustomerType = "Organisation";
	public static String TC138_CustomerCompanyNameOG = "KFC"+BasePge.CreateRandomWithoutNumbers(2).toUpperCase();
	public static String TC138_CustomerCompanyNameNew = "BAS"+BasePge.CreateRandomWithoutNumbers(2).toUpperCase();
	public static String TC138_PhoneNumber= "8129101010";
	public static String TC138_CustomerReceiptlanguage= "English (India)";
	public static String TC138_CustomerReligion= "Hindu";
	public static String TC138_CustomerPrefLanguage= "Malayalam";


	//TC137
	public static String TC137_CustomerType = "Organisation";
	public static String TC137_ERPCustomerType = "Organization";
	public static String TC137_companyName = BasePge.CreateRandomWithoutNumbers(5) + " " + "Industries";
	public static String TC137_PhoneNumber = "9" + BasePge.CreateRandomNumber(9);
	public static List<String> TC137_CustomerLanguageOptions = Arrays.asList(
			"Arabic", "Arabic (Bahrain)", "Arabic (Egypt)", "Arabic (Kuwait)", "Arabic (Oman)", "Arabic (Qatar)", "Arabic (United Arab Emirates)",
			"Chinese (Simplified)", "Chinese (Traditional)", "Czech", "Danish", "Dutch", "Dutch (Belgium)", "English (Australia)",
			"English (Canada)", "English (Hong Kong SAR)", "English (India)", "English (Ireland)", "English (Malaysia)", "English (Malta)",
			"English (New Zealand)", "English (Pakistan)", "English (Philippines)", "English (Singapore)", "English (South Africa)",
			"English (United Kingdom)", "English (United States)", "Estonian", "Finnish", "French", "French (Belgium)", "French (Canada)",
			"French (Switzerland)", "German", "German (Austria)", "German (Switzerland)", "Greek", "Hebrew", "Hungarian", "Icelandic",
			"Indonesian", "Italian", "Italian (Switzerland)", "Japanese", "Korean", "Latvian", "Lithuanian", "Norwegian Bokmål (Norway)",
			"Polish", "Portuguese (Brazil)", "Portuguese (Portugal)", "Romanian", "Russian", "Spanish", "Spanish (Argentina)", "Spanish (Bolivia)",
			"Spanish (Chile)", "Spanish (Colombia)", "Spanish (Costa Rica)", "Spanish (Dominican Republic)", "Spanish (Ecuador)",
			"Spanish (Guatemala)", "Spanish (Mexico)", "Spanish (Nicaragua)", "Spanish (Panama)", "Spanish (Paraguay)", "Spanish (Peru)",
			"Spanish (Uruguay)", "Spanish (Venezuela)", "Swedish", "Thai", "Turkish", "Ukrainian", "Vietnamese");


	//TC140
	public static String TC140_CustomerType = "Organisation";
	public static String TC140_CompanyName = "Test"+BasePge.CreateRandomWithoutNumbers(2).toUpperCase();
	public static String TC140_PhoneNumber= "7"+BasePge.CreateRandomNumber(9);
	public static String TC140_CustomerReceiptlanguage= "English (India)";
	public static String TC140_CustomerReligion= "Hindu";
	public static String TC140_CustomerPrefLanguage= "Malayalam";
	public static String TC140_ZipCode= "688539";
	public static String TC140_EditedZipCode= "695036";
	public static String TC140_Editedstreetdetails= "This is an edited details";
	//public static String TC140_ERPLink = "https://m365-perf.sandbox.operations.dynamics.com/?cmp=rjal&mi=retaildevices&cls=MGDCSVFileImport_UpdateSKULineItem";
	public static String TC140_CustomerSearch= "All Customers";

	//TC139
	public static String TC139_CustomerType = "Organisation";
	public static String TC139_ERPCustomerType = "Organization";
	public static String TC139_CompanyName = BasePge.CreateRandomWithoutNumbers(5) + " " + "Industries Ltd";
	public static String TC139_Email= "test@gmail.com";
	public static List<String> TC139_CustomerLanguageOptions = Arrays.asList(
			"Arabic", "Arabic (Bahrain)", "Arabic (Egypt)", "Arabic (Kuwait)", "Arabic (Oman)", "Arabic (Qatar)", "Arabic (United Arab Emirates)",
			"Chinese (Simplified)", "Chinese (Traditional)", "Czech", "Danish", "Dutch", "Dutch (Belgium)", "English (Australia)",
			"English (Canada)", "English (Hong Kong SAR)", "English (India)", "English (Ireland)", "English (Malaysia)", "English (Malta)",
			"English (New Zealand)", "English (Pakistan)", "English (Philippines)", "English (Singapore)", "English (South Africa)",
			"English (United Kingdom)", "English (United States)", "Estonian", "Finnish", "French", "French (Belgium)", "French (Canada)",
			"French (Switzerland)", "German", "German (Austria)", "German (Switzerland)", "Greek", "Hebrew", "Hungarian", "Icelandic",
			"Indonesian", "Italian", "Italian (Switzerland)", "Japanese", "Korean", "Latvian", "Lithuanian", "Norwegian Bokmål (Norway)",
			"Polish", "Portuguese (Brazil)", "Portuguese (Portugal)", "Romanian", "Russian", "Spanish", "Spanish (Argentina)", "Spanish (Bolivia)",
			"Spanish (Chile)", "Spanish (Colombia)", "Spanish (Costa Rica)", "Spanish (Dominican Republic)", "Spanish (Ecuador)",
			"Spanish (Guatemala)", "Spanish (Mexico)", "Spanish (Nicaragua)", "Spanish (Panama)", "Spanish (Paraguay)", "Spanish (Peru)",
			"Spanish (Uruguay)", "Spanish (Venezuela)", "Swedish", "Thai", "Turkish", "Ukrainian", "Vietnamese");
	public static String TC139_PhoneNumber = "7"+ BasePge.CreateRandomNumber(9);
	public static String TC139_BankAccNo = "9"+BasePge.CreateRandomNumber(11);
	public static String TC139_IFSCCode = "SBIN0" + BasePge.CreateRandomNumber(6);;
	public static String TC139_BranchName = "Kerala";  
	public static String TC139_BankName = "01::State Bank Of India";
  
	 //TC_131 
		public static String TC131_CustomerFirstName = BasePge.CreateRandomWithoutNumbers(4);
		public static String TC131_CustomerLastName = BasePge.CreateRandomWithoutNumbers(3);
		public static String TC131_ReligionCode= "Hindu";
		public static String TC131_PreferredLanguage= "Malayalam";
		public static String TC131_Selectoption= "--SELECT--";
		public static String TC131_SelectLanguageOption= "Select a language";
		public static String TC131_CustomerContact = "9"+BasePge.CreateRandomNumber(9);
		public static String TC131_CustomerLanguage = "English (India)";
		public  List <String> TC131_SpecialCharacters = Arrays.asList(
			    "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/",
			    ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~"
			);

}
