package mobile.zxjt.page;

import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.wait.Conditions;
import mobile.zxjt.wait.WaitUtil;
import up.light.supports.pagefactory.DynamicLocator;

/**
 * 资产查询
 */
public class PageZCCX extends PageBase {
	private DynamicLocator<MobileElement> oSpanKQ;
	private DynamicLocator<MobileElement> oSpanKY;
	private DynamicLocator<MobileElement> oSpanYE;
	private DynamicLocator<MobileElement> oSpanCurrency;
	private String index;

	public void doSwitchCurrency(String currency) {
		oSpanCurrency.findElement(currency).click();
		if (currency.charAt(0) == 'C') {
			index = "1";
		} else if (currency.charAt(0) == 'H') {
			index = "2";
		} else if (currency.charAt(0) == 'U') {
			index = "3";
		} else {
			throw new IllegalArgumentException("Unsupported type of currency: " + currency);
		}
	}

	public String doGetKQ() {
		WebElement e = oSpanKQ.findElement(index);
		String str = WaitUtil.waitForText(driver, e, WaitUtil.WAIT_LONG, null, Conditions.NOTBLANK);
		return PageUtil.stripe(str);
	}

	public String doGetKY() {
		return PageUtil.getText(oSpanKY.findElement(index));
	}

	public String doGetYE() {
		return PageUtil.getText(oSpanYE.findElement(index));
	}

	@Override
	public void reset() {
		// not used
	}

}
