package testScenario.NormalSaleGoldandSilver;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import testData.CommonData;
import testPage.Login;
import testPage.NormalSaleDiamondandPlatinum;
import testPage.NormalSaleGoldandSilver;
import utilPack.BaseTest;

public class NormalSaleGoldandSilver_Test extends BaseTest 
{	
	NormalSaleGoldandSilver normalSaleGoldAndSilver;
	
	@Test
	public void TC03_NormalSaleGoldandSilverTest() throws Exception
	{
		normalSaleGoldAndSilver=new NormalSaleGoldandSilver(driver);
		normalSaleGoldAndSilver.TC03_NormalSaleGoldandSilver();
	}

	@Test
	public void TC04_NormalSaleGoldandSilverTest() throws Exception
	{
		normalSaleGoldAndSilver=new NormalSaleGoldandSilver(driver);
		normalSaleGoldAndSilver.TC04_NormalSaleGoldandSilver();
	}	

	@Test()
	public void TC01_NormalSaleGoldandSilverTest() throws Exception 
	{	
		normalSaleGoldAndSilver=new NormalSaleGoldandSilver(driver);
		normalSaleGoldAndSilver.TC01_NormalSaleGoldandSilver();
	}	
	
	@Test
	public void TC05_NormalSaleGoldandSilverTest() throws Exception
	{
		normalSaleGoldAndSilver=new NormalSaleGoldandSilver(driver);
		normalSaleGoldAndSilver.TC05_NormalSaleGoldandSilver();
	}
	
	@Test()
	public void TC02_NormalSaleGoldandSilverTest() throws Exception 
	   {
		normalSaleGoldAndSilver=new NormalSaleGoldandSilver(driver);
		normalSaleGoldAndSilver.TC02_NormalSaleGoldandSilver();
	   }

	@Test
	public void TC06_NormalSaleGoldandSilverTest() throws Exception
	{
		normalSaleGoldAndSilver=new NormalSaleGoldandSilver(driver);
		normalSaleGoldAndSilver.TC06_NormalSaleGoldandSilver();
	}
	
}