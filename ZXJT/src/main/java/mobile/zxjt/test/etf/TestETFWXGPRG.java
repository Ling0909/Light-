package mobile.zxjt.test.etf;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageETF;
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
 * ETF网下股票认购测试
 */
public class TestETFWXGPRG extends TestBase {
	private PageETF mPage = PageManager.getPage(PageETF.class);

	@BeforeClass
	public void enterETFWXGPRG() {
		Navigator.navigate("网下股票认购", mPage);
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testETFWXGPRG(@Mapper(InfoMapper.class) Info info, @Key("ETF市场") String market,
			@Key("成分股代码") String cfgCode, @Key("成分股名称") String cfgName, CustomRecord record) {
		// 选择ETF市场
		mPage.doSwitchMarket(market);
		// 输入代码并校验名称
		// TODO 修改用例
//		String vName = mPage.doInputCode(info.getCode(), info.getName(), false);
		String vName = info.getName();
		mPage.doInputErrorCode(info.getCode(), null);
		Assertions.assertThat(vName).as("校验名称").isEqualTo(info.getName());
		// 输入成分股代码并校验名称
		vName = mPage.doInputCFGCode(cfgCode, cfgName);
		Assertions.assertThat(vName).as("校验成分股名称").isEqualTo(cfgName);
		// 输入数量
		mPage.doInputNumber(info.getNumber(), KeyboardLayout.LAYOUT_4_4);
		// 点击交易按钮
		mPage.doTrade();
		// 校验对话框1
		IConfirm vConfirm = Popups.getConfirm(PopupType.STYLE_3);
		String vActualCheckPoint1 = vConfirm.doGetMsg();
		Assertions.assertThat(vActualCheckPoint1).as("校验确认信息").isEqualTo(info.getConfirmMsg());
		vConfirm.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_CONFIRM, vActualCheckPoint1);
		// 校验对话框2
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_3);
		String vActualCheckPoint2 = vAlert.doGetMsg();
		Assertions.assertThat(vActualCheckPoint2).as("校验结果").contains(info.getResultMsg());
		vAlert.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActualCheckPoint2);
	}
}
