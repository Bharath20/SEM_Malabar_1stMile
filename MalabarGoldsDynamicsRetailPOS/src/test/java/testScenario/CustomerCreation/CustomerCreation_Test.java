
package testScenario.CustomerCreation;

import org.testng.annotations.Test;

import testPage.CustomerCreation;
import utilPack.BaseTest;



public class CustomerCreation_Test  extends BaseTest
{
	CustomerCreation customerCreation;
	
	@Test()
	public void TC131_CustomerCreationTest() throws Exception 
	{	
		customerCreation= new CustomerCreation(getDriver());
		customerCreation.TC131_CustomerCreation();
	}

	@Test()
	public void TC132_CustomerCreationTest() throws Exception 
	{	
		customerCreation= new CustomerCreation(getDriver());
		customerCreation.TC132_CustomerCreation();
	}
	@Test()
	public void TC129_CustomerCreationTest() throws Exception 
	{	
		customerCreation = new CustomerCreation(getDriver());
		customerCreation.TC129_CustomerCreation();
	}


	@Test()
	public void TC130_CustomerCreationTest() throws Exception 
	{	
		customerCreation = new CustomerCreation(getDriver());
		customerCreation.TC130_CustomerCreation();
	}	

	@Test()
	public void TC128_CustomerCreationTest() throws Exception
	{
		customerCreation = new CustomerCreation(getDriver());
		customerCreation.TC128_CustomerCreation();
	}
	@Test()
	public void TC137_CustomerCreationTest() throws Exception
	{
		customerCreation = new CustomerCreation(getDriver());
		customerCreation.TC137_CustomerCreation();
	}


	@Test()
	public void TC140_CustomerCreationTest() throws Exception
	{
		customerCreation = new CustomerCreation(getDriver());
		customerCreation.TC140_CustomerCreation();
	}
  
	@Test()
	public void TC139_CustomerCreationTest() throws Exception
	{
		customerCreation = new CustomerCreation(getDriver());
		customerCreation.TC139_CustomerCreation();
	}
	@Test()
	public void TC138_CustomerCreationTest() throws Exception
	{	
		customerCreation= new CustomerCreation(getDriver());
		customerCreation.TC138_CustomerCreation();
	}
}
