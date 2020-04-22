package mobile.zxjt.page;

import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import mobile.zxjt.wait.WaitUtil;

/**
 * 基金认申赎
 */
public class PageJJ extends PageBase {
	private MobileElement oInputCode; // 基金代码
	private MobileElement oLabelName; // 基金名称
	private MobileElement oInputNum; // 申购、认购、赎回数量
	private MobileElement oBtnTrade; // 交易按钮
	private MobileElement oLabelJJJZ; // 基金净值
	private MobileElement oLabelKY; // 可用资金、可赎份额
	private MobileElement oMsgRisk; // 风险提示对话框文本

	/**
	 * 输入基金代码
	 * 
	 * @param code 基金代码
	 * @return 基金名称
	 */
	public String doInputCode(String code) {
		WebElement e = WaitUtil.waitFor(driver, oInputCode, WaitUtil.WAIT_MEDIUM);
		getKeyboard().doInput(code, e, KeyboardLayout.LAYOUT_4_4);
		waitAndCheck(PopupType.STYLE_1);
		return PageUtil.getText(oLabelName);
	}

	/**
	 * 输入数量
	 * 
	 * @param num 数量
	 */
	public void doInputNumber(String num) {
		getKeyboard().doInput(num, oInputNum, KeyboardLayout.LAYOUT_4_4);
	}

	/**
	 * 点击交易按钮
	 */
	public void doTrade() {
		oBtnTrade.click();
	}

	/**
	 * 获取基金净值
	 * 
	 * @return
	 */
	public String doGetJJJZ() {
		return PageUtil.getText(oLabelJJJZ);
	}

	/**
	 * 获取可用资金或可赎份额
	 * 
	 * @return
	 */
	public String doGetKY() {
		return PageUtil.getText(oLabelKY);
	}

	/**
	 * 处理风险提示框
	 */
	public void doHandleRiskMsg() {
		if (WaitUtil.exists(driver, oMsgRisk, WaitUtil.WAIT_SHORT)) {
			Popups.getConfirm(PopupType.STYLE_1).doAccept();
		}
	}

}
