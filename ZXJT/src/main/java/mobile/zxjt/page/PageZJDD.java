package mobile.zxjt.page;

import java.util.List;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.popup.PopupType;
import up.light.supports.pagefactory.DynamicLocator;

/**
 * 资金调度界面
 */
public class PageZJDD extends PageBase {
	// 资金调度按钮
	private MobileElement oBtnZJDD;
	// 调拨按钮
	private DynamicLocator<MobileElement> oBtnDB;
	// 一键归集按钮
	private MobileElement oBtnYJGJ;
	// 主账户金额
	private MobileElement oDdZJE;
	// 辅账户金额
	private List<MobileElement> oDdFJE;

	/**
	 * 切换到资金调度标签
	 */
	public void doSwitchZJDD() {
		oBtnZJDD.click();
		waitAndCheck(PopupType.STYLE_1);
	}

	/**
	 * 点击一键归集按钮
	 */
	public void doYJGJ() {
		oBtnYJGJ.click();
	}

	/**
	 * 获取主账户资金
	 * 
	 * @return
	 */
	public Float doGetMainMoney() {
		// 去除多余的汉字、逗号和回车
		String vMoney = PageUtil.getText(oDdZJE).replaceAll("[,\n\r\u4e00-\u9fa5]", "");
		return Float.valueOf(vMoney);
	}

	/**
	 * 获取辅账户资金之和
	 * 
	 * @return
	 */
	public float doGetSubMoney() {
		String vMoney;
		float vSum = 0;
		for (MobileElement e : oDdFJE) {
			// 去除多余的汉字、逗号和回车
			vMoney = PageUtil.getText(e).replaceAll("[,\n\r\u4e00-\u9fa5]", "");
			vSum += Float.valueOf(vMoney);
		}
		return vSum;
	}

	/**
	 * 进入主辅划转界面
	 * 
	 * @param fzh 辅账户
	 */
	public void doEnterDB(String fzh) {
		oBtnDB.findElement(fzh).click();
	}

}
