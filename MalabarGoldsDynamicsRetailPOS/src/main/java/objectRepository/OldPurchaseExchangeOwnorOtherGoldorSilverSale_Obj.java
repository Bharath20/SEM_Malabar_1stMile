package objectRepository;

import org.openqa.selenium.By;

public class OldPurchaseExchangeOwnorOtherGoldorSilverSale_Obj {
	
	//Ele
	public By Sel_OgOwn(String text) {return By.xpath("//*[text()='"+text+"')]");}
	//Btn
	public By Btn_ThreeDots(String ClassThreeDots) {return By.xpath("(//div[@class='"+ClassThreeDots+"']//button)[last()]");}
	
}
