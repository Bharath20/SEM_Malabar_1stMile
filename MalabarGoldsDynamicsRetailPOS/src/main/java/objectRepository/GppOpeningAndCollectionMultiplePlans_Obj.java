package objectRepository;

import org.openqa.selenium.By;

public class GppOpeningAndCollectionMultiplePlans_Obj {
	public By Sel_GPPNomineeName(String db) {return By.xpath("//input[contains(@data-bind,'"+db+"')]");}
	public By Btn_VoidTransaction(String id,String Position, String Btn){return By.xpath("//*[@id='"+id+"']/div[contains(@class,'"+Position+"')]/button[@aria-label='"+Btn+"']");}
	public By Ele_AmountPaid(String Amount){return By.xpath("//label[@for='"+Amount+"']/following-sibling::input");}
	public By Sel_IdType1(String IdType){return By.xpath("(//label[text()='"+IdType+"']/parent::div/select)[1]");}
	public By Ele_IdProofNo1(String IdProofValue,String Labeltext) {return By.xpath("(//div[label[@for='"+IdProofValue+"' and text()='"+Labeltext+"']]/input[@id='"+IdProofValue+"'])[1]");}

}
