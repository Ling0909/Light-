package mobile.zxjt.page.module;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.wait.WaitUtil;
import up.light.LightContext;

public class Loading extends PageBase {
	public static final String KEY = "FastMode";
	private MobileElement oDivLoading;
	private boolean mFastMode;

	public Loading() {
		// LightContext中FastMode默认不设置，即为null
		mFastMode = LightContext.getAttribute(KEY) == Boolean.TRUE;
	}

	public void waitForLoading() {
		WaitUtil.sleep(WaitUtil.WAIT_SHORT);
		if (!mFastMode) {
			while (WaitUtil.exists(driver, oDivLoading, WaitUtil.WAIT_SHORT))
				WaitUtil.sleep(500, TimeUnit.MILLISECONDS);
		} else {
			WaitUtil.untilGone(driver, oDivLoading, WaitUtil.WAIT_LONG);
		}
	}

}
