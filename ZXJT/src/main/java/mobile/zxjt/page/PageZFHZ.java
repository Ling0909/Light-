package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.wait.WaitUtil;

/**
 * 主辅划转
 */
public class PageZFHZ extends PageBase {
	private MobileElement oImgJH;
	private MobileElement oInputNum;
	private MobileElement oBtnOK;
	private MobileElement oInputPwd;

	/**
	 * 交换主辅账户
	 */
	public void doSwitchAccount(String type) {
		if ("主转辅".equals(type)) {
			oImgJH.click();
		}
	}

	/**
	 * 输入划转数量
	 * 
	 * @param num
	 */
	public void doInputNumber(String num) {
		String vNum = PageUtil.getValue(oInputNum);
		if (!num.equals(vNum)) {
			if (vNum.length() > 0) {
				clearInputByJs(oInputNum);
			}
			getKeyboard().doInput(num, oInputNum, KeyboardLayout.LAYOUT_4_4);
		}
	}

	/**
	 * 点击确定按钮
	 */
	public void doTrade() {
		oBtnOK.click();
	}

	/**
	 * 输入资金密码
	 * 
	 * @param pwd
	 */
	public void doInputPwd(String pwd) {
		getKeyboard().doInput(pwd, oInputPwd, KeyboardLayout.LAYOUT_4_4);
	}

	/**
	 * 判断是否在主辅划转界面
	 * 
	 * @return
	 */
	public boolean isInZFHZ() {
		return WaitUtil.exists(driver, oInputNum, WaitUtil.WAIT_SHORT);
	}

}
