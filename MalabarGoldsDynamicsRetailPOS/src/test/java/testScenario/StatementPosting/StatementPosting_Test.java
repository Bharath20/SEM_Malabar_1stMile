package testScenario.StatementPosting;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import testData.CommonData;
import testPage.Login;
import testPage.StatementPosting;
import testPage.POC;
import utilPack.BaseTest;

public class StatementPosting_Test extends BaseTest 

{	
	StatementPosting StatementPosting ;
	
	@Test()
	public void TC310_StatementPostingTest() throws Exception 
	{	
		StatementPosting = new StatementPosting(getDriver());
		StatementPosting.TC310_StatementPosting();
	}
}