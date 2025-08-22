package objectRepository;

import org.openqa.selenium.By;

public class NormalSaleGoldandSilver_Obj {


	//Edt
	public By Edt_Name(String text) {return By.xpath("//input[@placeholder='"+text+"']");}
	public By Edt_ApprCode(String text) {return By.xpath("//input[@class='"+text+"']");}
	public By Edt_TextField(String divclass , String inputtype) {return By.xpath("//div[@class='"+divclass+"']//input[@type='"+inputtype+"']");}

	//Ele
	public By Ele_Transaction(String text) {return By.xpath("//div[@class='h5 marginLeft08 padRight28']//div[text()='"+text+"']");}
	public By Ele_Name(String text) {return By.xpath("//input[@id='"+text+"']");}
	public By Ele_Address(String text){return By.xpath("//h2[contains(text(),'"+text+"')]");}
	public By Ele_Address() {return By.xpath("/html/body/div[5]/div/div/div/div[2]/div/div[2]/form/div[1]/div[2]/div[1]/div/div[1]/div[1]/div/div/div/div[2]/div/div");}
	public By Ele_SearchList(String text) {return By.xpath("//h5[text()='"+text+"']");}
	public By Ele_CustomerSearch(String text) {return By.xpath("//h5[text()='"+text+"' and @class='nowrap whitespaceNoWrap']");}
	public By Ele_Payement(String text) {return By.xpath("//h3[text()='"+text+"']");}
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
	public By Ele_ConvtAdv(String text, String cls) {return By.xpath("//div[@class='"+text+"']//following-sibling::div[text()='"+cls+"']");}
	public By Ele_DrpdwnYear(String Path) { return By.xpath("//select[@class='"+Path+"']");}
	public By Ele_Remarks(String Path) { return By.xpath("//textarea[@tabindex='"+Path+"']");}
	public By Ele_EstmnNumber(String text) {return By.xpath("//label[text()='"+text+"']//following-sibling::input");}
	public By Ele_ChangeDueAmount(String amount) {return By.xpath("//div[contains(text(),'"+amount+"')]/parent::div//div[contains(@data-bind,'changeAmount')]");}
	public By Ele_Lines(String LinesFieldid, String LinesClass) {return By.xpath("//div[@id='"+LinesFieldid+"']//div[@class='"+LinesClass+"']");}
	public By Ele_SKUSalesRep(String partialClass, String textValue) {return By.xpath("//div[contains(@class,'" + partialClass + "') and contains(text(),'" + textValue + "')]");}
	public By Ele_TotWithoutTax(String databind){return By.xpath("//label[@data-bind='"+databind+"']");}
	public By Ele_ItemRecallEst(String role,String Item) {return By.xpath("//div[@role='"+role+"' and contains(@aria-label,'"+Item+"')]");}
	public By Ele_SKUHeader(String header) { return By.xpath("//h3[@data-bind='"+header+"']");}
	public By Ele_WarehouseCode(String value,String label) { return By.xpath("//input[@value='"+value+"' and @aria-label='"+label+"']");}
	public By Ele_WarehouseName(String name) {return By.xpath("//input[@name='"+name+"']");}
	public By Ele_RunNowERP(String id) {return By.xpath("//span[@id='"+id+"']");}
	public By Ele_DistrSched(String id) {return By.xpath("//li[@id='"+id+"']");}
	public By Ele_Transaction(String cls,String text) {return By.xpath("//div[@class='"+cls+"']//div[text()='"+text+"']");}
	public By Ele_StatusERP(String row,String title) {return By.xpath("//div[@aria-rowindex='"+row+"']//input[@title='"+title+"']");}
	public By Ele_OGRate(String cls,String role) {return By.xpath("//div[@class='"+cls+"']//div[@role='"+role+"']");}
	public By Ele_ERPCustAccnt(String cls,String text) {return By.xpath("//span[@class='"+cls+"'and text()='"+text+"']");}
	public By Ele_ERPCustAccntisExactly(String text) {return By.xpath("//input[contains(@name,'"+text+"')]");}
	public By Ele_ERPInvoice(String invoiceNo) {return By.xpath("//input[@value='"+invoiceNo+"']");}
	public By Ele_ERPCustName(String value) {return By.xpath("(//input[@value='"+value+"'])[1]");}
	public By Ele_ERPReceiveAmt(String value) {return By.xpath("//input[@aria-label='"+value+"']");}
	
	//Btn
	public By Btn_NewInMetalRate(String fr) {return By.xpath("//span[@for='"+fr+"']");}
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
	public By Btn_ViewMore(String text) {return By.xpath("//span[@class='"+text+"']");}	
	public By Btn_MenuBarOptions(String options) {return By.xpath("//button[contains(@id,'"+options+"')]");}
	public By Btn_IconReturnKey(String Class,String keyname) {return By.xpath("//div[@class='"+Class+"']//div[contains(@class,'"+keyname+"')]");}
	public By Btn_CustomerAdjustment(String title) {return By.xpath("//button[@title='"+title+"']");}
	public By Btn_abc(String abcclass , String arialabel) {return By.xpath("//div[@class='"+abcclass+"']//button[@aria-label='"+arialabel+"']");}
	public By Btn_return(String btnarialabel) {return By.xpath("//button[@aria-label='"+btnarialabel+"']");}
	public By Ele_SameAsAbove(String text) {return By.xpath("//input[contains(@data-bind,'"+text+"')]");}
	

	//sel
	public By Sel_DepositType(String id) {return By.xpath("//select[@id='"+id+"']");}
	public By Sel_BankProofType(String text) {return By.xpath("//label[text()='"+text+"']//following-sibling::select");}
	public By Sel_Configuration(String text) {return By.xpath("//div[@class='"+text+"']//select");}
	public By Sel_AmountDue(String count) {return By.xpath("//a[contains(@aria-label,'"+count+"')]");}
	public By Sel_Receipt(String options) {return By.xpath("//div[text()='"+options+"']/ancestor::div[@role='listitem']");}
	public By Sel_PaymentMethod(String paymentMethod) {return By.xpath("//h3[text()='Payment method']/ancestor::div/form//div[@id='optionsButtonGrid']//div[contains(text(),'"+paymentMethod+"')]");}
	
	public By Btn_VoidMethod(String id, String Action) {return By.xpath("//div[@id='"+id+"']//button//div/child::div[text()='"+Action+"']");}	
	public By Txt_ProductValidation() {return By.xpath("//div[@role='option']//div[@class='listViewLine expandable']//div[contains(@class,'QuantityField')]//div");}
	public By Txt_TotalProduct() {return By.xpath("//div[@id='LinesField']//div[@data-bind='text: value']");}
	public By Fld_Product(String quantity) {return By.xpath("//div[@aria-label='QUANTITY "+quantity+"']/ancestor::div[contains(@class,'SelectedLinesFields')]");}
	public By Txt_Quantity(String index) {return By.xpath("(//div[contains(@class,'listViewLine')]/following-sibling::div//div[contains(@class,'lineItemBarcode')]//div)["+index+"]");}
	
	
}

