package mobile.zxjt.test.base;

import org.assertj.core.api.Assertions;

import mobile.zxjt.page.PageJY;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.IAlert;
import mobile.zxjt.page.module.popup.IConfirm;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;

public class TestJYBase {
	private PageJY mPage = PageManager.getPage(PageJY.class);

	public PageJY getPage() {
		return mPage;
	}

	/**
	 * 输入交易信息
	 * 
	 * @param info 交易信息
	 * @param wtfs 委托方式
	 * @param layout 键盘布局
	 * @param wait 页面刚打开时是否存在加载框
	 * @return 实际确认信息（对话框1）内容
	 */
	public String inputTradeInfo(Info info, String wtfs, KeyboardLayout layout, boolean wait) {
		// 输入代码并校验股票名称
		String vActualName = mPage.doInputCode(info.getCode(), info.getName(), wait);
		Assertions.assertThat(vActualName).as("校验名称").isEqualTo(info.getName());
		// 选择交易方式
		if (wtfs != null) {
			mPage.doChooseWTFS(wtfs);
		}
		// 输入数量
		mPage.doInputNumber(info.getNumber(), layout);
		// 获取价格并替换验证点中的{PRICE}
		String vPrice = mPage.doGetPrice();
		String vCheckPoint1 = info.getConfirmMsg().replace("{PRICE}", vPrice);
		// 点击交易按钮
		mPage.doTrade();
		return vCheckPoint1;
	}

	/**
	 * 校验对话框1内容
	 * 
	 * @param expected 预期内容
	 */
	public void checkConfirmMsg(String expected) {
		// 获取对话框1内容并校验
		IConfirm vConfirm = Popups.getConfirm(PopupType.STYLE_1);
		String vActualCheckPoint1 = vConfirm.doGetMsg();
		Assertions.assertThat(vActualCheckPoint1).as("校验确认信息").isEqualTo(expected);
		vConfirm.doAccept2();
	}

	/**
	 * 校验对话框1内容
	 * 
	 * @param expected 预期内容
	 * @return 实际内容
	 */
	public String checkResultMsg(String expected) {
		// 获取对话框2内容并校验
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_1);
		String vActualCheckPoint2 = vAlert.doGetMsg();
		Assertions.assertThat(vActualCheckPoint2).as("校验结果").contains(expected);
		vAlert.doAccept();
		return vActualCheckPoint2;
	}

	/**
	 * 根据当前价格计算出一个超出涨停或跌停的价格
	 * 
	 * @param price 现价
	 * @param type 类型：{LOW}、{HIGH}
	 * @return 计算出的价格（为方便输入已四舍五入取整）
	 */
	public String getPrice(String price, String type) {
		Float num = Float.valueOf(price);
		if ("{LOW}".equals(type)) {
			num /= 2;
		} else if ("{HIGH}".equals(type)) {
			num *= 2;
		} else {
			return price;
		}
		return String.valueOf(Math.round(num));
	}

}
