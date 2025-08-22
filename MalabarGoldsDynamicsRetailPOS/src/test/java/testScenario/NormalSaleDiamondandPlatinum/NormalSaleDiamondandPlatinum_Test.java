package testScenario.NormalSaleDiamondandPlatinum;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import testData.CommonData;
import testPage.Login;
import testPage.NormalSaleDiamondandPlatinum;
import testPage.POC;
import utilPack.BaseTest;

public class NormalSaleDiamondandPlatinum_Test extends BaseTest 

{	
	NormalSaleDiamondandPlatinum normalSaleDiamondAndPlatinum ;
	
	@Test()
	public void TC07_NormalSaleDiamondandPlatinumTest() throws Exception 
	{	
		normalSaleDiamondAndPlatinum = new NormalSaleDiamondandPlatinum(getDriver());
		normalSaleDiamondAndPlatinum.TC07_NormalSaleDiamondandPlatinum();
	}	
	@Test()
	public void TC08_NormalSaleDiamondandPlatinumTest() throws Exception
	{	
	normalSaleDiamondAndPlatinum = new NormalSaleDiamondandPlatinum(getDriver());	
	normalSaleDiamondAndPlatinum.TC08_NormalSaleDiamondandPlatinum();
	}
	@Test()
	public void TC09_NormalSaleDiamondandPlatinumTest() throws Exception 
	{	
		normalSaleDiamondAndPlatinum = new NormalSaleDiamondandPlatinum(getDriver());
		normalSaleDiamondAndPlatinum.TC09_NormalSaleDiamondandPlatinum();
	}
	@Test()
	public void TC11_NormalSaleDiamondandPlatinumTest() throws Exception
	{	
		normalSaleDiamondAndPlatinum = new NormalSaleDiamondandPlatinum(getDriver());
		normalSaleDiamondAndPlatinum.TC11_NormalSaleDiamondandPlatinum();
	}
	@Test()
	public void TC10_NormalSaleDiamondandPlatinumTest() throws Exception 
	{	
		normalSaleDiamondAndPlatinum = new NormalSaleDiamondandPlatinum(getDriver());
		normalSaleDiamondAndPlatinum.TC10_NormalSaleDiamondandPlatinum();			
	}
}