package mobile.zxjt.page;

import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.wait.WaitUtil;
import up.light.supports.pagefactory.DynamicLocator;

/**
 * 基金分红设置
 */
public class PageJJFHSZ extends PageBase {
	private MobileElement oInputCode; // 代码
	private MobileElement oLabelName; // 名称
	private MobileElement oLabelJJJZ; // 净值
	private MobileElement oBtnFHFS; // 分红方式
	private DynamicLocator<MobileElement> oLiFHFS; // 分红方式菜单项
	private MobileElement oBtnSH; // 设置按钮

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
	 * 切换分红方式
	 * 
	 * @param type 红利转投、现金分红
	 */
	public void doSwitchType(String type) {
		oBtnFHFS.click();
		oLiFHFS.findElement(type).click();
	}

	/**
	 * 获取基金净值
	 * 
	 * @return 基金净值
	 */
	public String doGetJJJZ() {
		return PageUtil.getText(oLabelJJJZ);
	}

	/**
	 * 点击设置按钮
	 */
	public void doTrade() {
		oBtnSH.click();
	}

}
