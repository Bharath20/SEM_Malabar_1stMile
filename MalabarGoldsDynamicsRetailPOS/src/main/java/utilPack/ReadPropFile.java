package utilPack;




import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;



public class ReadPropFile {
	
	Properties properties;
    FileInputStream fStream;
	public Properties  getPropData() throws IOException {
		String propFilePath = System.getProperty("user.dir");
		fStream = new FileInputStream(propFilePath+"\\Utils\\data.properties");
		properties = new Properties();
		properties.load(fStream);
		return properties;
	}
	
	public boolean  getStatus() throws IOException {
		String propFilePath = System.getProperty("user.dir");
		fStream = new FileInputStream(propFilePath+"\\Utils\\data.properties");
		properties = new Properties();
		properties.load(fStream);
		String status = properties.getProperty("Stat");
		
		if(status.equals(true)) {
			return true;
		}
			
		else
			return false;
	}
	
}
