package mobile.zxjt.page;

import java.util.List;

import io.appium.java_client.MobileElement;

/**
 * 港股通回转资金、未交收明细
 */
public class PageHKHZZJ_WJSMX extends PageHKCXBase {
	// 预冻结资金
	private List<MobileElement> oPs1;
	// 二级资金
	private List<MobileElement> oPs2;

	public String doGetRow1() {
		return getString(oPs1);
	}

	public String doGetRow2() {
		return getString(oPs2);
	}

}
