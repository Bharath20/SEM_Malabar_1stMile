package testScenario.NormalSaleUncutandPrecia;

import org.testng.annotations.Test;

import testPage.NormalSaleUncutandPrecia;
import testPage.NormalSalesReturnGoldSilverReturnSaleCounter;
import utilPack.BaseTest;

public class NormalSaleUncutandPrecia_Test extends BaseTest 

{	
	NormalSaleUncutandPrecia normalSaleUncutAndPrecia;

	@Test()
	public void TC30_NormalSaleUncutandPreciaTest() throws Exception 
	{	
		normalSaleUncutAndPrecia = new NormalSaleUncutandPrecia(getDriver());
		normalSaleUncutAndPrecia.TC30_NormalSaleUncutandPrecia();
	}

	@Test()
	public void TC33_NormalSaleUncutandPreciaTest() throws Exception 
	{	
		normalSaleUncutAndPrecia = new NormalSaleUncutandPrecia(getDriver());
		normalSaleUncutAndPrecia.TC33_NormalSaleUncutandPrecia();
	}

	@Test()
	public void TC31_NormalSaleUncutandPreciaTest() throws Exception 
	{	
		normalSaleUncutAndPrecia = new NormalSaleUncutandPrecia(getDriver());
		normalSaleUncutAndPrecia.TC31_NormalSaleUncutandPrecia();
	}
	
	@Test()
	public void TC32_NormalSaleUncutandPreciaTest() throws Exception 
	{	
		normalSaleUncutAndPrecia = new NormalSaleUncutandPrecia(getDriver());
		normalSaleUncutAndPrecia.TC32_NormalSaleUncutandPrecia();
	}

}
