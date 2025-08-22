package objectRepository;

import org.openqa.selenium.By;

public class AdvanceSettlementNormalAdvanceOfferAdvanceConvertToAdvanceAddOnAdvanceLegacyPos_Obj {

	public By Ele_BalanceAmountInput(String labelText) {
		return By.xpath("//label[text()='" + labelText + "']/following-sibling::input");
	}
	public By Ele_AdvanceAdjustTrnsNo(String cls,String aria) {
		return By.xpath("//div[@class='"+cls+"' and contains(@aria-label,'"+aria+"')]");
	}
	public By Ele_AdvanceAdjustBalanceAmt(String role,String aria,String role1,int i) {
		return By.xpath("(//div[@role='"+role+"']/descendant::div[contains(@aria-label,'"+aria+"') and @role='"+role1+"'])["+(i+1)+"]");
	}

	public By Ele_AdvanceAdjustMetalRate(String role,String aria,String role1,int i) {
		return By.xpath("(//div[@role='"+role+"']/descendant::div[contains(@aria-label,'"+aria+"') and @role='"+role1+"'])["+(i+1)+"]");
	}
	//Sel
	public By Sel_OrderNumber(String DataBind){return By.xpath("//select[contains(@data-bind,'"+DataBind+"')]");}
	//Ele
	public By Ele_BalanceAndAdjustmentAmnt(String DataBind){return By.xpath("//input[contains(@data-bind,'"+DataBind+"')]");}
	public By Ele_MetalRateBlankMsg(){return By.xpath("//span[contains(text(),'Metal rate should not be blank')]");}
	public By Ele_OrderBalanceAmt(String cls,String aria) { return By.xpath("//div[@class='"+cls+"']//div[contains(@aria-label,'"+aria+"')]");}
	public By Ele_MetalRate(String cls, String cell, String ariaStart) {return By.xpath("//div[@class='" + cls + "']//div[@class='" + cell + "' and starts-with(@aria-label,'" + ariaStart + "')]");}
}
