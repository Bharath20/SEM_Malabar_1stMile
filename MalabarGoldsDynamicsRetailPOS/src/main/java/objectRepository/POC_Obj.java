package objectRepository;

import org.openqa.selenium.By;

public class POC_Obj {
	//Edt
		public By Edt_Name(String text) {return By.xpath("//input[@placeholder='"+text+"']");}
		public By Edt_CalString(String text) {return By.xpath("//div[text()='"+text+"']");}
		public By Edt_ApprCode(String text) {return By.xpath("//input[@class='"+text+"']");}

		//Ele
		public By Ele_Transaction(String text) {return By.xpath("//div[@class='h5 marginLeft08 padRight28']//div[text()='"+text+"']");}
		public By Ele_Name(String text) {return By.xpath("//input[@id='"+text+"']");}
		public By Ele_DropDownType(String Path) { return By.xpath("//select[@id='"+Path+"']");}
		public By Ele_Address(String text){return By.xpath("//h2[contains(text(),'"+text+"')]");}
		public By Ele_Address() {return By.xpath("/html/body/div[5]/div/div/div/div[2]/div/div[2]/form/div[1]/div[2]/div[1]/div/div[1]/div[1]/div/div/div/div[2]/div/div");}
		public By Ele_SearchList(String text) {return By.xpath("//h5[text()='"+text+"']");}
		public By Ele_Payement(String text) {return By.xpath("//h3[text()='"+text+"']");}
		public By Ele_DepositAmnt(String id) {return By.xpath("//input[@id='"+id+"']");}
		public By Ele_SalesPerson(String text) {return By.xpath("//select[@tabindex='"+text+"']");}
		public By Ele_SKUNumber(String cls, String index) {return By.xpath("(//div[@class='"+cls+"'])["+index+"]");}
		public By Ele_SKUNumber(String text,String cls, String index) {return By.xpath("(//div[@class='"+text+"']//following-sibling::div[@class='"+cls+"'])["+index+"]");}
		public By Ele_CustomerAdjustment(String text){return By.xpath("//div[contains(text(),'"+text+"')]");}
		public By Ele_SeeAll(String text) {return By.xpath("//a[contains(text(),'"+text+"')]");}
		public By Ele_Purchase(String id) {return By.xpath("//div[@id='"+id+"']");}
		public By Ele_abc(String cls, String text) {return By.xpath("//div[@class='"+cls+"']//input[contains(@class,'"+text+"')]");}
		public By Ele_Return(String text) {return By.xpath("//button[contains(text(),'"+text+"')]");}
		public By Ele_DropDownConfig(String Path) { return By.xpath("//select[@aria-label='"+Path+"']");}
		public By Ele_OGProduct(String Path) { return By.xpath("//div[@aria-label='"+Path+"']");}
		public By Ele_EstimationNo(String cls, String text, String index) {return By.xpath("(//div[@class='"+cls+"']//input[@type='"+text+"')["+index+"]");}
		public By Ele_ESNo(String cls, String index) {return By.xpath("(//input[@type='"+cls+"'])["+index+"]");}
		public By Ele_FilterESNo(String text) {return By.xpath("//label[text()='"+text+"']//following-sibling::input");}
		public By Ele_ConvtAdv(String text, String cls) {return By.xpath("//div[@class='"+text+"']//following-sibling::div[text()='"+cls+"']");}
		public By Ele_DrpdwnYear(String Path) { return By.xpath("//select[@class='"+Path+"']");}
		public By Ele_Remarks(String Path) { return By.xpath("//textarea[@tabindex='"+Path+"']");}
		public By Ele_DrpdwnNominee(String text) {return By.xpath("//label[text()='"+text+"']//following-sibling::select");}
		public By Ele_EstmnNumber(String text) {return By.xpath("//label[text()='"+text+"']//following-sibling::input");}
		
		//Btn
		public By Btn_Transaction(String cls, String txt) {return By.xpath("//div[@class='"+cls+"' and text()='"+txt+"']");}
		public By Btn_Estimation(String btn) {return By.xpath("//button[@class='"+btn+"']");}
		public By Btn_AddtoCart(String btn) {return By.xpath(" //div[@class='"+btn+"']");}
		public By Btn_Deposit(String btn) {return By.xpath("//button[normalize-space(text())='"+btn+"']");}
		public By Btn_Amnt(String btn) {return By.xpath("//a[@class='"+btn+"']");}
		public By Btn_CartMoney(String cls, String cls2) {return By.xpath("//div[@class='"+cls+"']//div[@class='"+cls2+"']");}
		public By Btn_ReturnCartMoney(String cls, String cls2) {return By.xpath("//button[@class='"+cls+"']//div[@class='"+cls2+"']");}
		public By Btn_Returnproduct(String cls, String index) {return By.xpath("(//button[@class='"+cls+"'])["+index+"]");}
		public By Btn_OP(String cls,String text) {return By.xpath("//div[@class='"+cls+"']//div[normalize-space(text())='"+text+"']");}
		public By Btn_Cash(String cls1,String cls2) {return By.xpath("//button[@id='"+cls1+"']//div[@class='"+cls2+"']");}	
		public By Btn_AddtoCart1(String btn,String cls1) {return By.xpath("//button[@id='"+btn+"']//span[@class='"+cls1+"']");}
		public By Btn_back(String id,String cls1) {return By.xpath("//div[@id='"+id+"']//button[@class='"+cls1+"']");}
		
		//sel
		public By Sel_DepositType(String id) {return By.xpath("//select[@id='"+id+"']");}
		public By Sel_BankProofType(String text) {return By.xpath("//label[text()='"+text+"']//following-sibling::select");}
		public By Sel_Configuration(String text) {return By.xpath("//div[@class='"+text+"']//select");}

}
