package testScenario.POC;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import testData.CommonData;
import testPage.Login;
import testPage.POC;
import utilPack.BaseTest;

public class POC_Test extends BaseTest 

{	
	@Test()
	public void TC01_POCTest() throws Exception 
	{	
		POC poc = new POC(getDriver());	
		poc.TC01_POC();			
	}
	
	@Test()
	public void TC02_POCTest() throws Exception 
	{	
		POC poc = new POC(getDriver());	
		poc.TC02_POC();			
	}
	
	@Test()
	public void TC03_POCTest() throws Exception 
	{	
		POC poc = new POC(getDriver());	
		poc.TC03_POC();			
	}
	
	@Test()
	public void TC04_POCTest() throws Exception 
	{	
		POC poc = new POC(getDriver());	
		poc.TC04_POC();			
	}
	
	@Test()
	public void TC05_POCTest() throws Exception 
	{	
		POC poc = new POC(getDriver());	
		poc.TC05_POC();			
	}
	@Test()
	public void TC06_POCTest() throws Exception 
	{	
		POC poc = new POC(getDriver());	
		poc.TC06_POC();			
	}
	@Test()
	public void TC07_POCTest() throws Exception 
	{	
		POC poc = new POC(getDriver());	
		poc.TC07_POC();			
	}
	
}