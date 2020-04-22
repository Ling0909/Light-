package mobile.zxjt.page;

import java.util.List;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;

/**
 * 港股通回转资金、负债查询、额度查询父类
 */
public class PageHKCXBase extends PageBase {

	protected String getString(List<MobileElement> es) {
		StringBuilder sb = new StringBuilder();
		for (MobileElement e : es) {
			sb.append(PageUtil.getText(e)).append('\n');
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	@Override
	public void reset() {
	}

}
