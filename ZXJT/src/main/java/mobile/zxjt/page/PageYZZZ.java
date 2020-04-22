package mobile.zxjt.page;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.wait.WaitUtil;
import up.light.supports.pagefactory.DynamicLocator;
import up.light.util.Assert;

public class PageYZZZ extends PageBase {
	// 银转证、证转银
	private DynamicLocator<MobileElement> oBtnTitle;
	// 转出银行
	private MobileElement oInputBank;
	// 银行下拉菜单
	private DynamicLocator<MobileElement> oLabelBank;
	// 转账金额
	private MobileElement oInputZZJE;
	// 银行密码
	private MobileElement oInputYHMM;
	// 资金密码
	private MobileElement oInputZJMM;
	// 转账按钮
	private MobileElement oBtnTrade;

	/**
	 * 切换类型：银转证、证转银
	 * 
	 * @param type 银转证、证转银
	 */
	public void doSwitchType(String type) {
		waitAndCheck(PopupType.STYLE_1);
		oBtnTitle.findElement(type).click();
		waitAndCheck(PopupType.STYLE_1);
	}

	/**
	 * 选择银行
	 * 
	 * @param bank 银行名称
	 * @param currency 币种
	 */
	public void doSelectBank(String bank, String currency) {
		oInputBank.click();
		WaitUtil.sleep(500, TimeUnit.MILLISECONDS);
		if ("中国银行".equals(bank)) {
			List<MobileElement> es = oLabelBank.findElements(bank);
			Assert.isTrue(es.size() >= 2, "Can't find bank [" + bank + "]");
			if ("港元".equals(currency)) {
				es.get(0).click();
			} else {
				es.get(1).click();
			}
		} else {
			oLabelBank.findElement(bank).click();
		}
	}

	/**
	 * 输入转账金额
	 * 
	 * @param num
	 */
	public void doInputNumber(String num) {
		getKeyboard().doInput(num, oInputZZJE, KeyboardLayout.LAYOUT_4_4);
	}

	/**
	 * 输入密码
	 * 
	 * @param YHMM 银行密码
	 * @param JZMM 资金密码
	 */
	public void doInputPassword(String YHMM, String JZMM) {
		if (YHMM != null && WaitUtil.exists(driver, oInputYHMM, 1)) {
			getKeyboard().doInput(YHMM, oInputYHMM, KeyboardLayout.LAYOUT_4_4);
		}
		if (JZMM != null && WaitUtil.exists(driver, oInputZJMM, 1)) {
			getKeyboard().doInput(JZMM, oInputZJMM, KeyboardLayout.LAYOUT_4_4);
		}
	}

	/**
	 * 点击转账
	 */
	public void doTrade() {
		oBtnTrade.click();
	}

}
