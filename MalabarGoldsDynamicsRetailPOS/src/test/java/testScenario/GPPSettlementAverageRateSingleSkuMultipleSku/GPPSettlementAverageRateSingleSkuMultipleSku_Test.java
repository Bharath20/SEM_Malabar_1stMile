package testScenario.GPPSettlementAverageRateSingleSkuMultipleSku;

import utilPack.BaseTest;

import org.testng.annotations.Test;
import testPage.GPPSettlementAverageRateSingleSkuMultipleSku;
public class GPPSettlementAverageRateSingleSkuMultipleSku_Test extends BaseTest{
	
	GPPSettlementAverageRateSingleSkuMultipleSku GPPSettlementAverageRateSingleSkuMultipleSku;
	
	@Test()
	public void TC110_GPPSettlementAverageRateSingleSkuMultipleSkuTest() throws Exception 
	{	
		GPPSettlementAverageRateSingleSkuMultipleSku = new GPPSettlementAverageRateSingleSkuMultipleSku(getDriver());
		GPPSettlementAverageRateSingleSkuMultipleSku.TC110_GPPSettlementAverageRateSingleSkuMultipleSku();
	}
	
	@Test()
	public void TC111_GPPSettlementAverageRateSingleSkuMultipleSkuTest() throws Exception 
	{	
		GPPSettlementAverageRateSingleSkuMultipleSku = new GPPSettlementAverageRateSingleSkuMultipleSku(getDriver());
		GPPSettlementAverageRateSingleSkuMultipleSku.TC111_GPPSettlementAverageRateSingleSkuMultipleSku();
	}
}
