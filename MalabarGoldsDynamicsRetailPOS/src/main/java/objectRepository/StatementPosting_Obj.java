package objectRepository;

import org.openqa.selenium.By;

public class StatementPosting_Obj {

	//Btn
	public By Btn_ReceiptNo(String Class, String ControlName){return By.xpath("//div[contains(@class,'"+Class+"') and @data-dyn-controlname='"+ControlName+"']");}
	public By Btn_Add(String Name){return By.xpath("//button[@name='"+Name+"']");}
	public By Btn_Calculate(String IdCalculate, String ForCalculate){return By.xpath("//span[contains(@id,'"+IdCalculate+"') and contains(@for,'"+ForCalculate+"')]");}	

	//Edt
	public By Edt_StoreNo(String StoreNo){return By.xpath("//input[@aria-haspopup='"+StoreNo+"']");}	
}

