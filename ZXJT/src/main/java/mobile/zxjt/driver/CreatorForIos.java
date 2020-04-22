package mobile.zxjt.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class CreatorForIos extends AbstractCreator {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends WebDriver> T createDriver() {
		IOSDriver<IOSElement> driver = null;
		try {
			driver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), getDc());
			setWait(driver);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return (T) driver;
	}

	@Override
	public IContextSwitcher createSwithcer(AppiumDriver<? extends WebElement> driver) {
		return new SwitcherBase(driver);
	}

}
