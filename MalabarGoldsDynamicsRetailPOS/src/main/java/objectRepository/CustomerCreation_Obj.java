
package objectRepository;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CustomerCreation_Obj {
  
	
	public By Ele_StreetName(String name) {return By.xpath("//textarea[@id='"+name+"']");}
	public By Ele_CustomerIDLabel(String id) {return By.xpath("//div[@data-bind='"+id+"']");}
	public By Ele_ERPCustid(String Custid) {return By.xpath("//input[@aria-label='"+Custid+"']");}
	public By Ele_ERPCustomerAgeGroupCode(String text ) {return By.xpath("//label[text()='"+text+"']");}
	public By Ele_ERPaddressField(String value ) {return By.xpath("//textarea[@aria-label='"+value+"']");}
	public By Ele_ERPAccField(String list,String acc ) {return By.xpath("//ul[@id='"+list+"']//span[text()='"+acc+"']");}
	public By Ele_Customer(String customer,String data) {return By.xpath("//div[text()='"+customer+"']/parent::div/parent::div[@class='"+data+"']");}
	public By Ele_PrimaryCustomAddr(String name) {return By.xpath("(//div[contains(@class,'customerPanelPrimaryAddress')]/child::div/child::div/child::a[text()='"+name+"'])[1]");}
  
    public By Ele_Dob(String Text, String cls) { return By.xpath("//label[text()='"+Text+"']/..//select[@class='"+cls+"']");}
	public By Ele_Field(String text) {return By.xpath("//textarea[@id='"+text+"']");} 
	public By Ele_Nambtn(String Text, String cls) { return By.xpath ("//div[@class= '"+Text+"']//a[@class='"+cls+"']");}
	public By Ele_Customer(String customer) {return By.xpath("//div[text()='"+customer+"']/parent::div/parent::div[@class='dataListLine']");} 
	public By Ele_CustomerAddress(String id, String cls) {return By.xpath("//div[@id='"+id+"']//a[@class='"+cls+"']");}
	public By Ele_PrimaryEmail(String text ) {return By.xpath("//a[text()='"+text+"']/../..//div//div[@class='h5']");}
	public By Ele_ErpEmailContact(String value ) {return By.xpath("//input[@value='"+value+"']/ancestor::div[contains(@id,'ContactInfo_Type')]/following-sibling::div[contains(@id,'ContactInfo_Locator')]//input[@aria-label='Contact number/address']");} 
	public By Btn_AddtoCart(String btn) {return By.xpath(" //div[@class='"+btn+"']");}
  
  
  
	public By Ele_Name(String text) {return By.xpath("//input[@id='"+text+"']");}
	public By Ele_ERPAccount(String text) {return By.xpath("//span[@data-bind='"+text+"']");}
	public By Ele_Phone() {return By.xpath("//div[@class='h5']");}
	public By Ele_RecieptLang(String Option,String text) {return By.xpath("//select[@id='"+Option+"']/option[@value='"+text+"']");}
	public By Ele_ERPOptionSel(String Option) {return By.xpath("//span[text()='"+Option+"']/parent::li");}

  
}


