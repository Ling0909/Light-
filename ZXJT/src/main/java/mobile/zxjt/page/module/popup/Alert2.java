package mobile.zxjt.page.module.popup;

import io.appium.java_client.MobileElement;
import mobile.zxjt.driver.DriverFactory;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.wait.WaitUtil;

/**
 * 在港股通、LOF等界面成功、失败对话框不是同一个
 */
class Alert2 implements IAlert {
	private MobileElement oTextMsg;
	private MobileElement oBtnOK;
	private MobileElement oTextFailMsg;
	private MobileElement oBtnFailOK;
	private boolean isFail;

	@Override
	public String doGetMsg() {
		// 先判断获取失败信息再获取成功信息
		if (WaitUtil.exists(DriverFactory.getDriver(), oTextFailMsg, WaitUtil.WAIT_SHORT)) {
			isFail = true;
			return PageUtil.getText(oTextFailMsg);
		}
		isFail = false;
		return PageUtil.getText(oTextMsg);
	}

	@Override
	public void doAccept() {
		if (isFail) {
			oBtnFailOK.click();
		} else {
			oBtnOK.click();
		}
	}

	@Override
	public void doAccept2() {
	}

	@Override
	public boolean exists() {
		// 因此方法仅用于判断是否出现错误
		// 所以只判断oTextFailMsg是否存在即可
		return WaitUtil.exists(DriverFactory.getDriver(), oTextFailMsg, WaitUtil.WAIT_SHORT);
	}

}
