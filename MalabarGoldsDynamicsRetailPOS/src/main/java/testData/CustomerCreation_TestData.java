
package testData;

import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;

import utilPack.BasePge;

public class CustomerCreation_TestData {
	
	
	//TC129
	public static String TC129_FirstName = "Aravind";
	public static String TC129_RegionCode = "Hindu";
	public static String TC129_PreferredLanguage = "Malayalam";
	public static String TC129_CustomerLanguageOptions = "English (India)";
	public static String TC129_LastName = BasePge.CreateRandomWithoutNumbers(2);
	public static String TC129_NewLastName = BasePge.CreateRandomWithoutNumbers(3);
  
  
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
	
		
		//TC138

		public static String TC138_CustomerType = "Organisation";
		public static String TC138_CustomerCompanyNameOG = "KFC"+BasePge.CreateRandomWithoutNumbers(2).toUpperCase();
		public static String TC138_CustomerCompanyNameNew = "BAS"+BasePge.CreateRandomWithoutNumbers(2).toUpperCase();
		public static String TC138_CustomerReceiptlanguage= "English (India)";
		public static String TC138_CustomerReligion= "Hindu";
		public static String TC138_CustomerPrefLanguage= "Malayalam";

}
