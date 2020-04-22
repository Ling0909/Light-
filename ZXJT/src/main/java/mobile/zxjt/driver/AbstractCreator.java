package mobile.zxjt.driver;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import mobile.zxjt.wait.WaitUtil;
import up.light.LightContext;
import up.light.folder.FolderTypes;
import up.light.io.FileSystemResource;
import up.light.pojo.ImplicitlyWait;
import up.light.util.PropertiesUtil;

public abstract class AbstractCreator implements IDriverCreator {

	protected DesiredCapabilities getDc() {
		DesiredCapabilities dc = new DesiredCapabilities();
		Properties props = readCapabilities();
		String key;
		for (Enumeration<?> e = props.keys(); e.hasMoreElements();) {
			key = (String) e.nextElement();
			dc.setCapability(key, props.getProperty(key));
		}
		return dc;
	}

	protected void setWait(WebDriver driver) {
		ImplicitlyWait wait = (ImplicitlyWait) LightContext.getAttribute(WaitUtil.KEY);
		driver.manage().timeouts().implicitlyWait(wait.timeout, wait.unit);
	}

	private Properties readCapabilities() {
		try {
			String file = LightContext.getFolderPath(FolderTypes.CONFIG) + "dc.properties";
			return PropertiesUtil.loadProperties(new FileSystemResource(file));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
