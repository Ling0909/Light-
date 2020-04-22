package mobile.zxjt.page;

import java.util.List;

import io.appium.java_client.MobileElement;

/**
 * 港股通负债查询、额度查询
 */
public class PageHKFZ_EDCX extends PageHKCXBase {
	private List<MobileElement> oLabelHGT;
	private List<MobileElement> oLabelSGT;

	public String doGetHGT() {
		return getString(oLabelHGT);
	}

	public String doGetSGT() {
		return getString(oLabelSGT);
	}

}
