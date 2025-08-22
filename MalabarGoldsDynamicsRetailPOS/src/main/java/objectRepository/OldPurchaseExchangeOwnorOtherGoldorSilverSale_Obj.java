package objectRepository;

import org.openqa.selenium.By;

public class OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj {

	//Ele
	public By Sel_OgOwn(String text) {return By.xpath("//*[text()='"+text+"')]");}
	public By Ele_OSOwn(String ExchangeProduct) {return By.xpath("//div[@role='option']//div[@class='dataListLine']//div[normalize-space(text())='"+ExchangeProduct+"']");}
	public By Ele_MenuBar(String Value) {return By.xpath("//div[@id='sharedAppBarHost']//button[@aria-label='"+Value+"']");}

	//Btn
	public By Btn_ThreeDots(String ClassThreeDots) {return By.xpath("(//div[@class='"+ClassThreeDots+"']//button)[last()]");}

	//Clear
	public By Btn_ClearButton(String ariaLabel) {return By.xpath("//input[@aria-label='Payment amount']/following-sibling::button[@aria-label='" + ariaLabel + "']");}


}
