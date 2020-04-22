package mobile.zxjt.navigator;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import up.light.IElementFinder;
import up.light.pojo.Locator;
import up.light.supports.navigator.IAction;

public class ClickAction implements IAction {
	private AppiumDriver<WebElement> realDriver;

	@Override
	public void perform(IElementFinder<?> finder, Locator locator) {
		WebElement ele = (WebElement) finder.findElement(locator);
		if ("NATIVE_APP".equals(locator.getContext())) {
			ele.click();
		} else {
			getRealdriver(finder).executeScript("arguments[0].click();", ele);
		}
	}

	@SuppressWarnings("unchecked")
	private AppiumDriver<WebElement> getRealdriver(IElementFinder<?> finder) {
		if (realDriver == null) {
			realDriver = (AppiumDriver<WebElement>) finder.getRealDriver();
		}
		return realDriver;
	}

}
