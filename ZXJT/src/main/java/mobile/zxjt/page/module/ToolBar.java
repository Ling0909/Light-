package mobile.zxjt.page.module;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;

import io.appium.java_client.MobileElement;
import mobile.zxjt.driver.DriverFactory;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.testng.StopException;
import mobile.zxjt.wait.WaitUtil;

public class ToolBar extends PageBase {
	private MobileElement oBtnBack;
	private MobileElement oImgInit;
	private MobileElement oBtnMsg;

	public void doBack() {
		DriverFactory.getSwitcher().reset();
		oBtnBack.click();
		WaitUtil.sleep(500, TimeUnit.MILLISECONDS);
	}

	/**
	 * 等待闪屏界面消失
	 */
	public void doWaitForStart() {
		try {
			WaitUtil.untilGone(driver, oImgInit, WaitUtil.WAIT_LONG);
		} catch (Exception e) {
			throw new StopException(e);
		}
	}

	/**
	 * 等待并关闭关闭首页对话框
	 */
	public void doWaitAndCloseMsgBox() {
		try {
			WaitUtil.waitFor(driver, oBtnMsg, WaitUtil.WAIT_LONG);
			while (WaitUtil.exists(driver, oBtnMsg, WaitUtil.WAIT_SHORT)) {
				oBtnMsg.click();
				Thread.sleep(300);
			}
		} catch (TimeoutException e) {
		} catch (Exception e) {
			throw new StopException(e);
		}
	}

}
