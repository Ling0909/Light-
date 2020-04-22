package mobile.zxjt.test.rzrq;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageRRHQ;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.KeyboardLayout;
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
 * 还券测试
 */
public class TestHQ extends TestBase {
	private PageRRHQ mPage = PageManager.getPage(PageRRHQ.class);

	@BeforeClass
	public void enterHQ() {
		Navigator.navigate("【两融】还券", mPage);
	}

	/*
	 * 正例
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testHQ(@Mapper(InfoMapper.class) Info info, @Key("还券类型") String type, CustomRecord record) {
		// 切换类型
		mPage.doSwitchType(type);
		// 输入代码并校验名称
		String vName = mPage.doInputCode(info.getCode(), info.getName(), false);
		Assertions.assertThat(vName).as("校验名称").isEqualTo(info.getName());
		// 输入数量
		mPage.doInputNumber(info.getNumber(), KeyboardLayout.LAYOUT_4_4);
		String vCheckPoint1 = info.getConfirmMsg();
		if ("买券还券".equals(type)) {
			// 替换{PRICE}
			String vPrice = mPage.doGetPrice();
			vCheckPoint1 = vCheckPoint1.replace("{PRICE}", vPrice);
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
	}

}
