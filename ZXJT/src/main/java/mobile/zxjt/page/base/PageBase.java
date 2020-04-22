package mobile.zxjt.page.base;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import mobile.zxjt.driver.DriverFactory;
import mobile.zxjt.page.module.Keyboard;
import mobile.zxjt.page.module.Loading;
import mobile.zxjt.page.module.popup.IAlert;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import up.light.LightContext;
import up.light.folder.FolderTypes;
import up.light.supports.pagefactory.ILocatorFactory;
import up.light.supports.pagefactory.JsonLocatorFactory;
import up.light.supports.pagefactory.PageFactory;

public class PageBase {
	protected static AppiumDriver<MobileElement> driver = DriverFactory.getDriver();
	private static final ILocatorFactory FACTORY = new JsonLocatorFactory(
			LightContext.getFolderPath(FolderTypes.REPOSITORY));
	private Loading mLoading;

	public PageBase() {
		PageFactory.setFactory(FACTORY);
		PageFactory.initElements(DriverFactory.getFinder(), this, MobileElement.class);
	}

	public void reset() {
		String context = driver.getContext();
		if (!context.equals("NATIVE_APP")) {
			driver.navigate().refresh();
		}
	}

	/**
	 * 获取键盘
	 * 
	 * @return
	 */
	protected Keyboard getKeyboard() {
		return PageManager.getPage(Keyboard.class);
	}

	/**
	 * 获取Loading
	 * 
	 * @return
	 */
	public Loading getLoading() {
		if (mLoading == null) {
			mLoading = PageManager.getPage(Loading.class);
		}
		return mLoading;
	}

	/**
	 * 根据相对于中心的偏移量点击元素
	 * 
	 * @param e 要点击的元素
	 * @param offsetX X方向的偏移，正数向右，负数向左
	 * @param offsetY Y方向的偏移，正数向下，负数向上
	 */
	protected void clickOffset(WebElement e, int offsetX, int offsetY) {
		Dimension size = e.getSize();
		Actions action = new Actions(driver);
		action.moveToElement(e, size.width / 2 + offsetX, size.height / 2 + offsetY).click();
		action.build().perform();
	}

	/**
	 * 调用JS清空编辑框内容
	 * 
	 * @param e 要清空的编辑框
	 */
	protected void clearInputByJs(WebElement e) {
		String script = "arguments[0].value = ''";
		driver.executeScript(script, e);
	}

	/**
	 * 等待加载完毕并检查对话框，如果type为null则不检查对话框
	 * 
	 * @param type 对话框类型
	 */
	public void waitAndCheck(PopupType type) {
		getLoading().waitForLoading();
		if (type != null) {
			IAlert vAlert = Popups.getAlert(type);
			if (vAlert.exists()) {
				throw new RuntimeException(vAlert.doGetMsg());
			}
		}
	}

}
