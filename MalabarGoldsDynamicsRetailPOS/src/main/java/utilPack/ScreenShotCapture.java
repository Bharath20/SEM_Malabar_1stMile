package utilPack;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;//temporary file
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenShotCapture {
	public void captureFailureScreenShot(WebDriver driver, String name) throws IOException {
		TakesScreenshot scrShot = (TakesScreenshot) driver;
		File screenShot = scrShot.getScreenshotAs(OutputType.FILE);
		File f1 = new File(System.getProperty("user.dir") + "\\OutputScreenshots");
		if (!f1.exists()) {
			f1.mkdirs();
		}
		String timeStamp = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());
		File finalDestination = new File(
				System.getProperty("user.dir") + "\\OutputScreenshots\\" + name + "_" + timeStamp + ".png");
		FileHandler.copy(screenShot, finalDestination);

	}
}
