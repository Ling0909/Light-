package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.ElementOf;
import mobile.zxjt.page.base.ElementOfs;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.PopupType;
import up.light.supports.pagefactory.DynamicLocator;

/**
 * 还款
 */
public class PageRRHK extends PageTradeWithSelect {
	private DynamicLocator<MobileElement> oBtnTitle;

	@ElementOf(ElementOfs.CODE)
	private MobileElement oInputCode;

	@ElementOf(ElementOfs.NAME)
	private MobileElement oPName;

	@ElementOf(ElementOfs.NUMBER)
	private MobileElement oInputNum;

	@ElementOf(ElementOfs.BUTTON_OK)
	private MobileElement oBtnOK;

	private MobileElement oInputPrice;
	private MobileElement oInputHKJE;
	private MobileElement oTdKHZJ;
	private MobileElement oTdXHKE;

	public PageRRHK() {
		mType = PopupType.STYLE_1;
	}

	/**
	 * 切换类型
	 * 
	 * @param type 卖券还款、现金还款
	 */
	public void doSwitchType(String type) {
		oBtnTitle.findElement(type).click();
		waitAndCheck(PopupType.STYLE_1);
	}

	/**
	 * 获取价格
	 * 
	 * @return
	 */
	public String doGetPrice() {
		return PageUtil.getValue(oInputPrice);
	}

	/**
	 * 输入还款金额
	 * 
	 * @param money
	 */
	public void doInputMoney(String money) {
		getKeyboard().doInput(money, oInputHKJE, KeyboardLayout.LAYOUT_4_4);
	}

	/**
	 * 获取可还资金
	 * 
	 * @return
	 */
	public String doGetKHZJ() {
		return PageUtil.getText(oTdKHZJ);
	}

	/**
	 * 获取需还款额
	 * 
	 * @return
	 */
	public String doGetXHKE() {
		return PageUtil.getText(oTdXHKE);
	}

}
