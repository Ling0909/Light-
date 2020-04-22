package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.ElementOf;
import mobile.zxjt.page.base.ElementOfs;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.popup.PopupType;

/**
 * LOF申购、赎回、认购
 */
public class PageLOF extends PageTradeWithSelect {
	@ElementOf(ElementOfs.CODE)
	private MobileElement oInputCode;
	@ElementOf(ElementOfs.NAME)
	private MobileElement oPName;
	@ElementOf(ElementOfs.NUMBER)
	private MobileElement oInputNum;
	@ElementOf(ElementOfs.BUTTON_OK)
	private MobileElement oBtnOK;
	private MobileElement oLabelSX;

	public PageLOF() {
		mType = PopupType.STYLE_2;
	}

	/**
	 * 获取申购、赎回、认购上限
	 * 
	 * @return
	 */
	public String doGetSX() {
		return PageUtil.getText(oLabelSX);
	}

}
