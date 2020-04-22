package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.ElementOf;
import mobile.zxjt.page.base.ElementOfs;
import mobile.zxjt.page.module.popup.PopupType;

/**
 * 担保品划转
 */
public class PageRRDBPHZ extends PageTradeWithSelect {
	private MobileElement oBtnPT2XY;
	private MobileElement oBtnXY2PT;

	@ElementOf(ElementOfs.CODE)
	private MobileElement oInputCode;

	@ElementOf(ElementOfs.NAME)
	private MobileElement oLabelName;

	@ElementOf(ElementOfs.NUMBER)
	private MobileElement oInputNum;

	@ElementOf(ElementOfs.BUTTON_OK)
	private MobileElement oBtnOK;

	public PageRRDBPHZ() {
		mType = PopupType.STYLE_1;
	}

	public void doSwitchType(String type) {
		waitAndCheck(PopupType.STYLE_1);
		if ("普通转信用".equals(type)) {
			oBtnPT2XY.click();
		} else if ("信用转普通".equals(type)) {
			oBtnXY2PT.click();
		} else {
			throw new IllegalArgumentException("Unsupported type: " + type);
		}
		waitAndCheck(PopupType.STYLE_1);
	}

}
