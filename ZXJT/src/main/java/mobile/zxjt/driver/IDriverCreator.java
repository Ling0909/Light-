package mobile.zxjt.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public interface IDriverCreator {

	<T extends WebDriver> T createDriver();

	IContextSwitcher createSwithcer(AppiumDriver<? extends WebElement> driver);

}
