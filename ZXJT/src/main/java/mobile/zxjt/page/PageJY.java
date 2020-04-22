package mobile.zxjt.page;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.ElementOf;
import mobile.zxjt.page.base.ElementOfs;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.wait.WaitUtil;
import up.light.supports.pagefactory.DynamicLocator;

/**
 * 股票买入、股票卖出、融资买入、融资卖出、担保品买入、担保品卖出
 */
public class PageJY extends PageTradeWithSelect {
	@ElementOf(ElementOfs.CODE)
	private MobileElement oInputCode;

	@ElementOf(ElementOfs.NAME)
	private MobileElement oPName;

	@ElementOf(ElementOfs.NUMBER)
	private MobileElement oInputNum;

	private MobileElement oInputPrice;
	private MobileElement oBtnWTFS;
	private DynamicLocator<MobileElement> oLiDropDwon;

	@ElementOf(ElementOfs.BUTTON_OK)
	private MobileElement oBtnTrade;

	public PageJY() {
		mType = PopupType.STYLE_1;
	}

	/**
	 * 重写输入数量，防止因可买过多引发element not clickable异常
	 */
	@Override
	public void doInputNumber(String num, KeyboardLayout layout) {
		clickOffset(oInputNum, -38, 0);
		getKeyboard().doInput(num, null, layout, false);
	}

	/**
	 * 输入错误代码（用于融资融券）
	 * 
	 * @param code 证券代码
	 * @param name 证券名称
	 */
	public void doInputErrorCode(String code, String name, boolean wait) {
		if (wait) {
			// 等待加载完毕
			waitAndCheck(PopupType.STYLE_1);
		}
		// 点击代码编辑框
		oInputCode.click();
		// 选择代码
		doSelectCode(code, name);
		// 等待加载完毕
		getLoading().waitForLoading();
	}

	/**
	 * 获取价格
	 * 
	 * @return
	 */
	public String doGetPrice() {
		return PageUtil.getValue(oInputPrice);
	}

	/**
	 * 编辑价格
	 * 
	 * @param price 要输入的价格（只支持整数）
	 */
	public void doEditPrice(String price) {
		// 调用 Javascript清空价格
		clearInputByJs(oInputPrice);
		// 输入新价格
		getKeyboard().doInput(price, oInputPrice, KeyboardLayout.LAYOUT_4_4);
	}

	/**
	 * 选择委托方式
	 * 
	 * @param WTFS 委托方式
	 */
	public void doChooseWTFS(String WTFS) {
		if (PageUtil.getText(oBtnWTFS).equals(WTFS))
			return;
		oBtnWTFS.click();
		WaitUtil.sleep(500, TimeUnit.MILLISECONDS);
		oLiDropDwon.findElement(WTFS).click();
	}

	@Override
	public void doTrade() {
		clickOffset(oBtnTrade, 0, 0);
	}

}
