package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;

/**
 * 港股通资产查询
 */
public class PageHKZCCX extends PageBase {
	private MobileElement oLabelKQ;
	private MobileElement oLabelKY;
	private MobileElement oLabelYE;

	public String doGetKQ() {
		return PageUtil.getText(oLabelKQ);
	}

	public String doGetKY() {
		return PageUtil.getText(oLabelKY);
	}

	public String doGetYE() {
		return PageUtil.getText(oLabelYE);
	}

	@Override
	public void reset() {
	}

}
