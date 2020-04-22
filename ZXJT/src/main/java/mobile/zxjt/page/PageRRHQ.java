package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.ElementOf;
import mobile.zxjt.page.base.ElementOfs;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.popup.PopupType;
import up.light.supports.pagefactory.DynamicLocator;

/**
 * 还券
 */
public class PageRRHQ extends PageTradeWithSelect {
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

	public PageRRHQ() {
		mType = PopupType.STYLE_1;
	}

	/**
	 * 切换类型
	 * 
	 * @param type 买券还券、现券还券
	 */
	public void doSwitchType(String type) {
		oBtnTitle.findElement(type).click();
	}

	/**
	 * 获取价格（仅用于买券还券）
	 * 
	 * @return
	 */
	public String doGetPrice() {
		return PageUtil.getValue(oInputPrice);
	}

}
