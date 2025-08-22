package testPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.LoginPage_Obj;
import testData.CommonData;
import utilPack.BasePge;

//import basePackage.BasePge;

public class Login extends BasePge {

	BasePge base;
	Assert asrt;
	 
	LoginPage_Obj LoginPageObj = new LoginPage_Obj();
	
	public Login(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
	}
	
	public  void EnterCredential(String UserName, String Password)
	{
		base.setData(LoginPageObj.Edt_LoginEmail("email"), UserName );
		base.buttonClick(LoginPageObj.Edt_LoginEmail("submit"));
		base.setData(LoginPageObj.Edt_LoginEmail("password"), Password );
		base.buttonClick(LoginPageObj.Edt_LoginEmail("submit"));
		base.buttonClick(LoginPageObj.Edt_LoginEmail("submit"));

	}
	
	
	public void loginToApplication(String UserName, String Password) throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		base.setZoom(driver, 65);
		wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("nextButton")));
		base.buttonClick(LoginPageObj.Btn_SingnIn("nextButton"));
		base.setZoom(driver, 65);
		wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("activateButton")));
		base.buttonClick(LoginPageObj.Btn_SingnIn("activateButton"));
		base.setZoom(driver, 65);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		EnterCredential(UserName, Password);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Edt_Alert("DA1016")));
		base.setZoom(driver, 65);
		wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("retryActivationButton")));
		base.buttonClick(LoginPageObj.Btn_SingnIn("retryActivationButton"));
		base.setZoom(driver, 65);
		//Da2009
		wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Ele_ErrorMessage("table-cell text-left content")));
		base.buttonClick(LoginPageObj.Ele_ErrorMessage("table-cell text-left content"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPageObj.Btn_SingnIn("signInButton")));
		base.buttonClick(LoginPageObj.Btn_SingnIn("signInButton"));
 
		EnterCredential(UserName, Password);
 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(35));
 
	}
}
