
package testScenario.CustomerCreation;

import org.testng.annotations.Test;

import testPage.CustomerCreation;
import utilPack.BaseTest;



public class CustomerCreation_Test  extends BaseTest
{
     CustomerCreation customerCreation;

	@Test()
	public void TC129_CustomerCreationTest() throws Exception 
	{	
		customerCreation = new CustomerCreation(getDriver());
		customerCreation.TC129_CustomerCreation();
	}
	
	@Test()
	public void TC131_CustomerCreationTest() throws Exception 
	{	
		customerCreation = new CustomerCreation(getDriver());
		customerCreation.TC131_CustomerCreation();
	}
  
	@Test()
	public void TC138_CustomerCreationTest() throws Exception 
	{	
		customerCreation = new CustomerCreation(getDriver());
		customerCreation.TC131_CustomerCreation();
	}
}

