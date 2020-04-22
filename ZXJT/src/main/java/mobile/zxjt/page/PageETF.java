package mobile.zxjt.page;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.ElementOf;
import mobile.zxjt.page.base.ElementOfs;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.popup.PopupType;
import up.light.supports.pagefactory.DynamicLocator;

/**
 * ETF网下现金认购、网下股票认购、申购、赎回
 */
public class PageETF extends PageTradeWithSelect {
	@ElementOf(ElementOfs.CODE)
	private MobileElement oInputETFCode;
	@ElementOf(ElementOfs.NAME)
	private MobileElement oPETFName;
	@ElementOf(ElementOfs.NUMBER)
	private MobileElement oInputNum;
	@ElementOf(ElementOfs.BUTTON_OK)
	private MobileElement oBtnOK;
	// 成分股代码
	private MobileElement oInputCFGCode;
	// 成分股名称
	private MobileElement oPCFGName;
	// ETF市场
	private MobileElement oDivETFSC;
	// ETF市场菜单
	private DynamicLocator<MobileElement> oLiETFSC;

	public PageETF() {
		mType = PopupType.STYLE_3;
	}

	/**
	 * 切换ETF市场
	 * 
	 * @param market
	 */
	public void doSwitchMarket(String market) {
		oDivETFSC.click();
		oLiETFSC.findElement(market).click();
	}

	/**
	 * 输入成分股代码
	 * 
	 * @param code 成分股代码
	 * @param name 成分股名称
	 * @return 显示的成分股名称
	 */
	public String doInputCFGCode(String code, String name) {
		oInputCFGCode.click();
		doSelectCode(code, name);
		waitAndCheck(mType);
		return PageUtil.getText(oPCFGName);
	}

	/**
	 * 输入错误代码
	 * 
	 * @param code
	 * @param name
	 */
	public void doInputErrorCode(String code, String name) {
		// 点击代码编辑框
		oInputETFCode.click();
		// 选择代码
		doSelectCode(code, name);
		// 等待加载完毕
		getLoading().waitForLoading();
	}

}
