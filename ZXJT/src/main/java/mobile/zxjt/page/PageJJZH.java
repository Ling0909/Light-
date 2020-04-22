package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import mobile.zxjt.wait.WaitUtil;

/**
 * 基金转换
 */
public class PageJJZH extends PageBase {
	// 代码、名称
	private MobileElement oInputZCDM;
	private MobileElement oLabelZCMC;
	private MobileElement oInputZRDM;
	private MobileElement oLabelZRMC;
	// 数量
	private MobileElement oInputNum;
	// 最大可转
	private MobileElement oLabelZDKZ;
	// 转换按钮
	private MobileElement oBtnZH;
	private MobileElement oMsgRisk;

	/**
	 * 输入转出代码
	 * 
	 * @param code 转出代码
	 * @return 转出名称
	 */
	public String doInputZCDM(String code) {
		return inputCode(code, oInputZCDM, oLabelZCMC);
	}

	/**
	 * 输入转入代码
	 * 
	 * @param code 转入代码
	 * @return 转入名称
	 */
	public String doInputZRDM(String code) {
		return inputCode(code, oInputZRDM, oLabelZRMC);
	}

	/**
	 * 输入数量
	 * 
	 * @param num
	 */
	public void doInputNum(String num) {
		getKeyboard().doInput(num, oInputNum, KeyboardLayout.LAYOUT_4_4);
	}

	/**
	 * 获取最大可转
	 * 
	 * @return
	 */
	public String doGetZDKZ() {
		return PageUtil.getText(oLabelZDKZ);
	}

	/**
	 * 点击转换按钮
	 */
	public void doTrade() {
		oBtnZH.click();
	}

	/**
	 * 输入基金代码
	 * 
	 * @param code 代码
	 * @param codeEle 输入代码的控件
	 * @param nameEle 显示名称的控件
	 * @return 基金名称
	 */
	private String inputCode(String code, MobileElement codeEle, MobileElement nameEle) {
		getKeyboard().doInput(code, codeEle, KeyboardLayout.LAYOUT_4_4);
		waitAndCheck(PopupType.STYLE_1);
		return PageUtil.getText(nameEle);
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
