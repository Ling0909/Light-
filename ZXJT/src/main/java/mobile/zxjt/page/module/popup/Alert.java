package mobile.zxjt.page.module.popup;

import io.appium.java_client.MobileElement;
import mobile.zxjt.driver.DriverFactory;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.wait.WaitUtil;
import up.light.LightContext;
import up.light.Platforms;
import up.light.util.StringUtil;

class Alert implements IAlert {
	private MobileElement oTextMsg;
	private MobileElement oBtnOK;

	@Override
	public String doGetMsg() {
		String content = PageUtil.getText(oTextMsg);
		if (!StringUtil.hasLength(content)) {
			throw new RuntimeException("Can't get text of Alert");
		}
		return content;
	}

	@Override
	public void doAccept() {
		oBtnOK.click();
	}

	@Override
	public void doAccept2() {
		if (LightContext.getPlatform() == Platforms.IOS) {
			// 破掉对话框女妖
			oTextMsg.click();
		}
		oBtnOK.click();
	}

	@Override
	public boolean exists() {
		return WaitUtil.exists(DriverFactory.getDriver(), oTextMsg, WaitUtil.WAIT_SHORT);
	}

}
