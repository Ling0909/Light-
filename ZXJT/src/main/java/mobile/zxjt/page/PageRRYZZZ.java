package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.wait.WaitUtil;

public class PageRRYZZZ extends PageBase {
	private MobileElement oBtnYZZ;
	private MobileElement oBtnZZY;
	private MobileElement oInputNum;
	private MobileElement oInputYHMM;
	private MobileElement oInputZJMM;
	private MobileElement oBtnOK;

	public void doSwitchType(String type) {
		waitAndCheck(PopupType.STYLE_1);
		if ("银行转证券".equals(type)) {
			oBtnYZZ.click();
		} else if ("证券转银行".equals(type)) {
			oBtnZZY.click();
		} else {
			throw new IllegalArgumentException("Unsupported type: " + type);
		}
		waitAndCheck(PopupType.STYLE_1);
	}

	public void doInputNumber(String num) {
		getKeyboard().doInput(num, oInputNum, KeyboardLayout.LAYOUT_4_4);
	}

	public void doTrade() {
		oBtnOK.click();
	}

	public void doInputPassword(String yhmm, String zjmm) {
		if (yhmm != null && WaitUtil.exists(driver, oInputYHMM, 1)) {
			getKeyboard().doInput(yhmm, oInputYHMM, KeyboardLayout.LAYOUT_4_4);
		} /*else {
			System.err.println("银行密码为空");
		}*/
		if (zjmm != null && WaitUtil.exists(driver, oInputYHMM, 1)) {
			getKeyboard().doInput(zjmm, oInputZJMM, KeyboardLayout.LAYOUT_4_4);
		} /*else {
			System.err.println("资金密码为空");
		}*/
	}

}
