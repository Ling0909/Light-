package mobile.zxjt.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class CreatorForAndroid extends AbstractCreator {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends WebDriver> T createDriver() {
		AndroidDriver<AndroidElement> driver = null;
		try {
			driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), getDc());
			setWait(driver);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return (T) driver;
	}

	@Override
	public IContextSwitcher createSwithcer(AppiumDriver<? extends WebElement> driver) {
		return new SwitcherForAndroid(driver);
	}

}
