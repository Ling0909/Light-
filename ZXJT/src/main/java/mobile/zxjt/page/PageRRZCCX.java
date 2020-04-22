package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;

/**
 * 融资融券资产查询
 */
public class PageRRZCCX extends PageBase {
	private MobileElement oSpanKY;
	private MobileElement oSpanKQ;
	private MobileElement oSpanYE;

	public String doGetKY() {
		return PageUtil.getText(oSpanKY);
	}

	public String doGetKQ() {
		return PageUtil.getText(oSpanKQ);
	}

	public String doGetYE() {
		return PageUtil.getText(oSpanYE);
	}

	@Override
	public void reset() {
	}

}
