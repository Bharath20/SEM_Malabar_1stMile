
package utilPack;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.openqa.selenium.PageLoadStrategy;
import io.github.bonigarcia.wdm.WebDriverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class BaseTest {
	public WebDriver driver;
	ScreenShotCapture sc;
	@BeforeSuite
	public void ClearScreenshotsDirectory() {
	    // Define the screenshots folder path
	    String ScreenshotDirPath = System.getProperty("user.dir") + "\\OutputScreenshots";
	    File ScreenshotDir = new File(ScreenshotDirPath);

	    if (ScreenshotDir.exists()) {
	        File[] Files = ScreenshotDir.listFiles();
	        if (Files != null) {
	            for (File File : Files) {
	                if (File.isFile() && File.getName().endsWith(".png")) {
	                    boolean deleted = File.delete();
	                    if (!deleted) {
	                        System.out.println("Failed to delete file: " + File.getAbsolutePath());
	                    }
	                }
	            }
	        }}
	    }
	@BeforeMethod
	@Parameters("Browser")
	public void InitializeDriver(@Optional("Chrome") String browser) throws InterruptedException {
	    if (browser == null || browser.trim().isEmpty()) {
	        browser = "Chrome"; // fallback
	    }

	    if (browser.equalsIgnoreCase("Chrome")) {
	        WebDriverManager.chromedriver().setup();

	        String DownloadFilepath = System.getProperty("user.dir") + "\\Invoices";

	        Map<String, Object> prefs = new HashMap<>();
	        prefs.put("plugins.always_open_pdf_externally", true);
	        prefs.put("download.prompt_for_download", false);
	        prefs.put("download.default_directory", DownloadFilepath);
	        prefs.put("profile.default_content_setting_values.automatic_download", 1);
	        prefs.put("savefile.default_directory", DownloadFilepath);
	        prefs.put("printing.print_preview_sticky_settings.appState",
	                  "{\"recentDestinations\": [{\"id\": \"Save as PDF\", \"origin\": \"local\"}]," +
	                  "\"selectedDestinationId\": \"Save as PDF\",\"version\": 2}");

	        ChromeOptions Options = new ChromeOptions();
	        Options.setExperimentalOption("prefs", prefs);
	        Options.setAcceptInsecureCerts(true);
	        Options.addArguments("--kiosk-printing"); 
	        Options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
	        driver = new ChromeDriver(Options);

	    } else if (browser.equalsIgnoreCase("Firefox")) {
	        WebDriverManager.firefoxdriver().setup();
	        driver = new FirefoxDriver();

	    } else {
	        throw new IllegalArgumentException("Invalid browser type: " + browser);
	    }

	    driver.manage().window().maximize();
	    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(180));

	    // Application URL to launch
	    driver.get("https://scuwkj98nz556938096-cpos.su.retail.dynamics.com/#DeviceId=5011D01&TerminalId=5011D01");
	}


	@AfterMethod(alwaysRun = true)
	public void CloseDriver(ITestResult iTestResult) throws IOException {
		if (iTestResult.getStatus() == ITestResult.FAILURE) {
			sc = new ScreenShotCapture();
			sc.captureFailureScreenShot(driver, iTestResult.getName());
		}
		//driver.quit();
		if (driver!=null) {
			driver.quit();
		}
	}
	public WebDriver getDriver() {
		return driver;
	}

}