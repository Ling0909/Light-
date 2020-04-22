package mobile.zxjt.test.rzrq;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageRRHK;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.Loading;
import mobile.zxjt.page.module.popup.IAlert;
import mobile.zxjt.page.module.popup.IConfirm;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import mobile.zxjt.test.base.Info;
import mobile.zxjt.test.base.InfoMapper;
import mobile.zxjt.test.base.TestBase;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.Key;
import up.light.testng.data.annotations.Mapper;

/**
 * 还款测试
 */
public class TestHK extends TestBase {
	private PageRRHK mPage = PageManager.getPage(PageRRHK.class);

	@BeforeClass
	public void enterHK() {
		Navigator.navigate("【两融】还款", mPage);
	}

	/*
	 * 正例
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testHK(@Mapper(InfoMapper.class) Info info, @Key("还款类型") String type, CustomRecord record) {
		String vKHZJ = null, vXHKE = null;

		// 切换类型
		mPage.doSwitchType(type);

		String vCheckPoint1 = info.getConfirmMsg();

		if ("卖券还款".equals(type)) {
			// 输入代码并校验名称
			String vName = mPage.doInputCode(info.getCode(), info.getName());
			Assertions.assertThat(vName).as("校验名称").isEqualTo(info.getName());
			// 输入数量
			mPage.doInputNumber(info.getNumber(), KeyboardLayout.LAYOUT_4_4);
			// 替换{PRICE}
			String vPrice = mPage.doGetPrice();
			vCheckPoint1 = vCheckPoint1.replace("{PRICE}", vPrice);
		} else {
			mPage.doInputMoney(info.getNumber());
			// 替换{KHZJ}、{XHKE}
			vKHZJ = mPage.doGetKHZJ();
			vXHKE = mPage.doGetXHKE();
			vCheckPoint1 = vCheckPoint1.replace("{KHZJ}", vKHZJ).replace("{XHKE}", vXHKE);
		}
		// 点击交易按钮
		mPage.doTrade();
		// 校验对话框1
		IConfirm vConfirm = Popups.getConfirm(PopupType.STYLE_1);
		String vActualCheckPoint1 = vConfirm.doGetMsg();
		Assertions.assertThat(vActualCheckPoint1).as("校验确认信息").isEqualTo(vCheckPoint1);
		vConfirm.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_CONFIRM, vActualCheckPoint1);
		// 校验对话框2
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_1);
		String vActualCheckPoint2 = vAlert.doGetMsg();
		Assertions.assertThat(vActualCheckPoint2).as("校验结果").contains(info.getResultMsg());
		vAlert.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActualCheckPoint2);
		// 校验金额
		if ("现金还款".equals(type)) {
			PageManager.getPage(Loading.class).waitForLoading();
			String vKHZJ_New = mPage.doGetKHZJ();
			String vXHKE_New = mPage.doGetXHKE();

			float vKH = add(vKHZJ_New, info.getNumber());
			float vKH_Old = toFloat(vKHZJ);
			Assertions.assertThat(vKH).as("校验资金").isEqualTo(vKH_Old);

			float vXH = add(vXHKE_New, info.getNumber());
			float vXH_Old = toFloat(vXHKE);
			Assertions.assertThat(vXH).as("校验资金").isEqualTo(vXH_Old);
		}
	}

	private float add(String str, String num) {
		String s = str.replace(",", "");
		Float n1 = Float.valueOf(s);
		Float n2 = Float.valueOf(num);
		return n1 + n2;
	}

	private float toFloat(String str) {
		return Float.valueOf(str.replace(",", ""));
	}

}
