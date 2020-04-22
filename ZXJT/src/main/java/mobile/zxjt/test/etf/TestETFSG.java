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
import mobile.zxjt.test.filter.FLFilter;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.DataFilter;
import up.light.testng.data.annotations.Key;
import up.light.testng.data.annotations.Mapper;

/**
 * ETF申购测试
 */
public class TestETFSG extends TestBase {
	private PageETF mPage = PageManager.getPage(PageETF.class);

	@BeforeClass
	public void enterETFSG() {
		Navigator.navigate("ETF申购", mPage);
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testETFSG(@Mapper(InfoMapper.class) Info info, @Key("ETF市场") String market, CustomRecord record) {
		doTrade(info, market, record);
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	@DataFilter(row = FLFilter.class)
	public void testETFSG_FL_Number(@Mapper(InfoMapper.class) Info info, @Key("ETF市场") String market,
			CustomRecord record) {
		doTrade(info, market, record);
		// 清除页面错误信息，防止影响下一条测试用例执行
		mPage.reset();
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	@DataFilter(row = FLFilter.class)
	public void testETFSG_FL_Code(@Mapper(InfoMapper.class) Info info, @Key("ETF市场") String market,
			CustomRecord record) {
		// 选择ETF市场
		mPage.doSwitchMarket(market);
		// 输入代码并校验名称
		mPage.doInputErrorCode(info.getCode(), info.getName());
		// 校验对话框
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_3);
		String vActualCheckPoint1 = vAlert.doGetMsg();
		Assertions.assertThat(vActualCheckPoint1).as("校验结果").contains(info.getResultMsg());
		vAlert.doAccept();
		// 清除页面错误信息，防止影响下一条测试用例执行
		mPage.reset();
		// 记录相关信息
		StringBuilder sb = new StringBuilder();
		sb.append("证券代码 ").append(info.getCode());
		sb.append("\n证券名称 ").append(info.getName());
		record.put(TestBase.KEY_CONFIRM, sb.toString());
		record.put(TestBase.KEY_RESULT, vActualCheckPoint1);
	}

	private void doTrade(Info info, String market, CustomRecord record) {
		String vNameToSelect = info.getName();
		if ("50申 赎".equals(vNameToSelect)) {
			// FUCKING SPACE ヽ(ˋДˊ)ノ !!!!!!!!!!!!!!!!!!!!!!
			vNameToSelect = "50申  赎";
		}
		// 选择ETF市场
		mPage.doSwitchMarket(market);
		// 输入代码并校验名称
		String vName = mPage.doInputCode(info.getCode(), vNameToSelect, false);
		Assertions.assertThat(vName).as("校验名称").isEqualTo(info.getName());
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
