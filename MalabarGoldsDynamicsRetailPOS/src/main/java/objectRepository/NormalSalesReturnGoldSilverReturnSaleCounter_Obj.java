package objectRepository;

import org.openqa.selenium.By;

public class NormalSalesReturnGoldSilverReturnSaleCounter_Obj 
{
	//Txt
	public By Txt_Sku(String SKU) {return By.xpath("//div[contains(@class,'"+SKU+"')]");}

	//Ele
	public By Ele_Amount(String text) {return By.xpath("//a[@data-ax-bubble='"+text+"']");}
	public By Ele_SubTot(String id, String class1, String class2) {return By.xpath("//div[@id='"+id+"']//div[@class='"+class1+"']//div[@class='"+class2+"']");}
	public By Ele_NormalAdvInvoice(String class1, String class2, String value) {return By.xpath("//div[contains(@class,'"+class1+"')]//div[@class='"+class2+"' and @aria-label='"+value+"']");}
	public By Ele_SpecifyPrice(String text) {return By.xpath("//h3[contains(text(),'"+text+"')]");}
	public By Ele_RecipetNumber() {return By.xpath("//div[@id='dataListSrcTransfer']//div[@class='win-itemsblock']//div[contains(@class,'win-container win-container')]");}
	public By Ele_AmountDue(String clas,String data) {return By.xpath("//a[@class='"+clas+"' and @data-ax-bubble='"+data+"']");};
	
	//Btn
	public By Btn_Close(String class1, String text) {return By.xpath("//button[@class='"+class1+"' and text()='"+text+"']");}
	public By Btn_ReciptClose() {return By.xpath("(//div[@class='marginTop0'])[3]/following-sibling::div//button");}
	public By Btn_Receive(String CounterBtn) {return By.xpath("//span[text()='"+CounterBtn+"']/ancestor::button");}
	public By Btn_BillingBack(String BackBtn) {return By.xpath("//div[text()='"+BackBtn+"']/ancestor::button[contains(@class,'buttonGridButton')]");}
	public By Btn_KeyOne(String class1,String Vlaue, String text) {return By.xpath("//div[@class='"+class1+"']//button[@value='"+Vlaue+"']//div[text()='"+text+"']");}

	//Checkbox
	public By Cb_MetalType(String MetalType) {return By.xpath("//div[@aria-label='METAL TYPE NAME "+MetalType+"' and @role='option']/parent::div");}
}
