
package testScenario.NormalSalesReturnUncutorPrecia;

import org.testng.annotations.Test;

import testPage.NormalSaleUncutandPrecia;
import testPage.NormalSalesReturnGoldSilverReturnSaleCounter;
import testPage.NormalSalesReturnUncutorPrecia;
import utilPack.BaseTest;

public class NormalSalesReturnUncutorPrecia_Test extends BaseTest 

{
	NormalSalesReturnUncutorPrecia normalSalesReturnUncutOrPrecia;  


@Test()
public void TC36_NormalSalesReturnUncutorPreciaTest() throws Exception 
{	
	normalSalesReturnUncutOrPrecia  = new NormalSalesReturnUncutorPrecia(getDriver());
	normalSalesReturnUncutOrPrecia.TC36_NormalSalesReturnUncutorPrecia();
}


@Test()
public void TC35_NormalSalesReturnUncutorPreciaTest() throws Exception 
{	normalSalesReturnUncutOrPrecia  = new NormalSalesReturnUncutorPrecia(getDriver());	
	normalSalesReturnUncutOrPrecia.TC35_NormalSalesReturnUncutorPrecia();		
}

@Test()
public void TC37_NormalSalesReturnUncutorPreciaTest() throws Exception 
{	normalSalesReturnUncutOrPrecia  = new NormalSalesReturnUncutorPrecia(getDriver());	
	normalSalesReturnUncutOrPrecia.TC37_NormalSalesReturnUncutorPrecia();		
}
@Test()
public void TC34_NormalSalesReturnUncutorPreciaTest() throws Exception 
{	normalSalesReturnUncutOrPrecia  = new NormalSalesReturnUncutorPrecia(getDriver());	
	normalSalesReturnUncutOrPrecia.TC34_NormalSalesReturnUncutorPrecia();		
}

@Test()
public void TC38_NormalSalesReturnUncutorPreciaTest() throws Exception 
{	
normalSalesReturnUncutOrPrecia = new NormalSalesReturnUncutorPrecia(getDriver());
normalSalesReturnUncutOrPrecia.TC38_NormalSalesReturnUncutorPrecia();		
}

}
