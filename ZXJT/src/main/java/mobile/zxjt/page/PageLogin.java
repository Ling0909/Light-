package mobile.zxjt.page;

import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.Keyboard;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.IAlert;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import mobile.zxjt.wait.WaitUtil;

public class PageLogin extends PageBase {
	private MobileElement oInputUsername;
	private MobileElement oInputPassword;
	private MobileElement oInputVertifyCode;
	private MobileElement oLabelVertifyCode;
	private MobileElement oBtnLogin;

	public void doLogin(String username, String password, boolean checkConfirm) {
		// 等待用户名编辑框出现
		WebElement eleUsername = WaitUtil.waitFor(driver, oInputUsername, WaitUtil.WAIT_LONG);
		Keyboard kb = getKeyboard();

		// 点击用户名编辑框并输入
		if (!PageUtil.getValue(eleUsername).equals(username)) {
			kb.doInput(username, eleUsername, KeyboardLayout.LAYOUT_4_4);
		}

		// 点击密码编辑框并输入
		kb.doInput(password, oInputPassword, KeyboardLayout.LAYOUT_4_4);

		// 输入验证码
		String code = oLabelVertifyCode.getText();
		kb.doInput(code, oInputVertifyCode, KeyboardLayout.LAYOUT_4_4);

		// 点击登录
		oBtnLogin.click();

		// 等待加载框消失
		getLoading().waitForLoading();

		// 融资融券不检查对话框
		IAlert alert = Popups.getAlert(PopupType.STYLE_1);
		String msg = null;
		if (alert.exists()) {
			if (checkConfirm && (msg = alert.doGetMsg()).contains("尊敬的客户")) {
				alert.doAccept();
			} else {
				throw new RuntimeException(msg);
			}
		}

		// 等待加载框消失
		getLoading().waitForLoading();

		// 等待登录按钮消失
		WaitUtil.untilGone(driver, oBtnLogin, WaitUtil.WAIT_MEDIUM);
	}

}
