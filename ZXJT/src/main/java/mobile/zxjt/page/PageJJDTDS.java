package mobile.zxjt.page;

import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import mobile.zxjt.wait.WaitUtil;
import up.light.supports.pagefactory.DynamicLocator;

public class PageJJDTDS extends PageBase {
	// 基金定投、定赎
	private MobileElement oLiJJDT;
	private MobileElement oLiJJDS;
	// 添加
	private MobileElement oImgAdd;
	// 交易界面元素
	private MobileElement oInputCode;
	private MobileElement oLabelName;
	private MobileElement oInputNum;
	// 扣款周期按钮
	private MobileElement oBtnKKZQ;
	// 月
	private MobileElement oDivMonth;
	// 周
	private MobileElement oDivWeek;
	// 扣款周期菜单项
	private DynamicLocator<MobileElement> oLiKKZQ;
	// 巨额标志
	private MobileElement oBtnJEBZ;
	private DynamicLocator<MobileElement> oLiJEBZ;
	// 开始、终止日期
	private MobileElement oInputKSRQ;
	private MobileElement oInputZZRQ;
	private MobileElement oBtnTrade;
	private MobileElement oMsgRisk;

	/**
	 * 切换类型
	 * 
	 * @param type 基金定投、基金定赎
	 */
	public void doSwitchType(String type) {
		MobileElement vToClick = null;

		switch (type) {
		case "基金定投":
			vToClick = oLiJJDT;
			break;
		case "基金定赎":
			vToClick = oLiJJDS;
			break;
		default:
			throw new RuntimeException(type + " is unsupported");
		}

		waitAndCheck(PopupType.STYLE_1);
		vToClick.click();
		waitAndCheck(PopupType.STYLE_1);
	}

	/**
	 * 点击添加按钮
	 */
	public void doAdd() {
		oImgAdd.click();
	}

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
	 * 输入交易数量
	 * 
	 * @param number 数量
	 */
	public void doInputNumber(String number) {
		if (!PageUtil.getValue(oInputNum).equals(number)) {
			clearInputByJs(oInputNum);
			getKeyboard().doInput(number, oInputNum, KeyboardLayout.LAYOUT_4_4);
		}
	}

	/**
	 * 选择扣款周期、赎回周期
	 * 
	 * @param type 月、周
	 * @param time 日、周几
	 */
	public void doChooseTime(String type, String time) {
		oBtnKKZQ.click();
		WaitUtil.sleep(1);

		String index = null;

		switch (type) {
		case "每月":
			oDivMonth.click();
			index = time.substring(0, time.length() - 1);
			break;
		case "每周":
			oDivWeek.click();
			Week wk = Week.fromString(time);
			index = String.valueOf(wk.index);
			break;
		default:
			throw new RuntimeException(type + " is unsupported");
		}

		oLiKKZQ.findElement(index).click();
	}

	/**
	 * 选择巨额标志
	 * 
	 * @param jebz 取消、顺延
	 */
	public void doChooseJEBZ(String jebz) {
		oBtnJEBZ.click();
		WaitUtil.sleep(1);
		oLiJEBZ.findElement(jebz).click();
	}

	/**
	 * 获取开始时间
	 * 
	 * @return
	 */
	public String doGetBeginTime() {
		return PageUtil.getValue(oInputKSRQ);
	}

	/**
	 * 获取结束时间
	 * 
	 * @return
	 */
	public String doGetEndTime() {
		return PageUtil.getValue(oInputZZRQ);
	}

	/**
	 * 点击交易按钮
	 */
	public void doTrade() {
		oBtnTrade.click();
	}

	/**
	 * 处理风险提示框
	 */
	public void doHandleRiskMsg() {
		if (WaitUtil.exists(driver, oMsgRisk, WaitUtil.WAIT_SHORT)) {
			Popups.getConfirm(PopupType.STYLE_1).doAccept();
		}
	}

	private static enum Week {
		MONDAY("星期一", 1), TUESDAY("星期二", 2), WEDNESDAY("星期三", 3), THURSDAY("星期四", 4), FRIDAY("星期五", 5);

		final String name;
		final int index;

		Week(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static Week fromString(String str) {
			for (Week w : Week.values()) {
				if (w.name.equals(str)) {
					return w;
				}
			}
			throw new RuntimeException(str + " is unsupported");
		}
	}

}
