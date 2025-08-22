package objectRepository;

import org.openqa.selenium.By;

public class AdvanceCollectionNormalAdvanceOfferAdvanceConvertToAdvance_Obj {
	
	//Ele
	public By Ele_CheckBox(String Type, String CheckId){return By.xpath("//input[@type='"+Type+"' and @id='"+CheckId+"']");}
	public By Ele_TotalAmount(String AriaLabel, String Class){return By.xpath("//div[contains(@aria-label,'"+AriaLabel+"') and @class='"+Class+"']");}
	public By Ele_ScanSku(String DataBind){return By.xpath("(//input[contains(@data-bind,'"+DataBind+"')])[1]");}
	
	//Edt
	public By Ele_IsOrder(String Class, String DataBind){return By.xpath("//div[@class='"+Class+"']//select[contains(@data-bind,'"+DataBind+"')]");}
	public By Ele_Amount(String DataBind, String Type){return By.xpath("//input[contains(@data-bind,'"+DataBind+"') and @type='"+Type+"']");}	
	public By Ele_Date(String DivClass, String SelectClass){return By.xpath("//div[contains(@class,'"+DivClass+"')]//select[contains(@class,'"+SelectClass+"')]");}
	public By Ele_Remarks(String DataBind){return By.xpath("//textarea[contains(@data-bind,'"+DataBind+"')]");}
}

