package mobile.zxjt.page.module;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.wait.WaitUtil;
import up.light.util.Assert;

/**
 * 键盘，仅可用于数字输入
 */
public class Keyboard extends PageBase {
	private static final Log logger = LogFactory.getLog(Keyboard.class);
	private static final int TAP_DURATION = 100;
	private MobileElement oViewKeyboard;

	/**
	 * 输入指定字符串
	 * 
	 * @param str 要输入的字符串
	 * @param ele 要输入的控件
	 * @param click 是否点击控件
	 * @param layout 键盘布局
	 */
	public void doInput(String str, WebElement ele, KeyboardLayout layout, boolean click) {
		if (logger.isInfoEnabled()) {
			logger.info("input: " + str + ", layout: " + layout);
		}
		if (str == null || str.length() == 0) {
			System.err.println("The string to input is blank");
			return;
		}

		Assert.notNull(layout, "layout must not be null");

		if (click) {
			// 点击元素弹出键盘
			ele.click();
		}
		WebElement realElement = WaitUtil.waitFor(driver, oViewKeyboard, WaitUtil.WAIT_SHORT);
		WaitUtil.sleep(1);

		int k;
		Point[] vPoints = layout.getCache(driver, realElement);

		for (int i = 0; i < str.length(); ++i) {
			k = getNumber(str.charAt(i));
			Assert.isTrue(k >= 0 && k < vPoints.length, "Unsupported number: " + k);
			driver.tap(1, vPoints[k].x, vPoints[k].y, TAP_DURATION);
		}

		WaitUtil.sleep(500, TimeUnit.MILLISECONDS);
		// 关闭键盘
		if (realElement.isDisplayed()) {
			int x = 2 * vPoints[3].x - vPoints[2].x;
			driver.tap(1, x, vPoints[3].y, TAP_DURATION);
			WaitUtil.sleep(500, TimeUnit.MILLISECONDS);
		}
	}

	/**
	 * 输入指定字符串（默认点击控件以弹出键盘）
	 * 
	 * @param str 要输入的字符串
	 * @param ele 要输入的控件
	 * @param layout 键盘布局
	 */
	public void doInput(String str, WebElement ele, KeyboardLayout layout) {
		doInput(str, ele, layout, true);
	}

	private int getNumber(char chr) {
		return chr - 48;
	}

}
