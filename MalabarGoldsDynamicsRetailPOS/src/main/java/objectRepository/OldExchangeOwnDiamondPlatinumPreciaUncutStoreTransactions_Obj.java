package objectRepository;

import org.openqa.selenium.By;

public class OldExchangeOwnDiamondPlatinumPreciaUncutStoreTransactions_Obj {

	//Ele
	public By Sel_SKUNuber(String text, String Class) {return By.xpath("//div[@id='"+text+"']//span[@class='"+Class+"']");}
	public By Sel_BeginSKU(String Class, String text, String index) {return By.xpath("(//div[@class='"+Class+"']//span[text()='"+text+"'])["+index+"]");}
	public By Sel_SKU(String text, String index) {return By.xpath("(//input[@aria-label='"+text+"'])["+index+"]");}
	public static By Sel_ODCValue(String ele) { return By.xpath("//div[starts-with(@aria-label, '"+ele+"')]");

	}
}
