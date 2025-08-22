
package objectRepository;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GPPSettlementAverageRateSingleSkuMultipleSku_Obj {
  
	//Ele
	public By Ele_GPPCustAccnt(String id) {return By.xpath("(//input[contains(@id,'"+id+"')])[1]");}
	public By Ele_GPPApply(String id,String txt) {return By.xpath("//span[@id='"+id+"' and text()='"+txt+"']");}
	public By Ele_GPPMature(String clas,String txt) {return By.xpath("//button[@class='"+clas+"' and text()='"+txt+"']");}
	public By Ele_Matured(String id,String cls,String ttle) {return By.xpath("//div[contains(@id,'"+id+"')]//div[@class='"+cls+"' and @title='"+ttle+"']");}

	//Sel
	public By Sel_GPPAccountNo(String db) {return By.xpath("//select[contains(@data-bind,'"+db+"')]");}
	
}


