package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.ElementOf;
import mobile.zxjt.page.base.ElementOfs;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.popup.PopupType;

/**
 * 股转定价、做市、限价
 */
public class PageGFZR extends PageTradeWithSelect {
	@ElementOf(ElementOfs.CODE)
	private MobileElement oInputCode;

	@ElementOf(ElementOfs.NAME)
	private MobileElement oPName;

	@ElementOf(ElementOfs.NUMBER)
	private MobileElement oInputNum;

	@ElementOf(ElementOfs.BUTTON_OK)
	private MobileElement oBtnOK;

	private MobileElement oInputPrice;

	public PageGFZR() {
		mType = PopupType.STYLE_2;
	}

	/**
	 * 获取价格
	 * 
	 * @return
	 */
	public String doGetPrice() {
		return PageUtil.getValue(oInputPrice);
	}

}
