package mobile.zxjt.test.rzrq;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageJY;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.IAlert;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import mobile.zxjt.test.base.Info;
import mobile.zxjt.test.base.InfoMapper;
import mobile.zxjt.test.base.TestBase;
import mobile.zxjt.test.base.TestJYBase;
import mobile.zxjt.test.filter.FLFilter;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.DataFilter;
import up.light.testng.data.annotations.Mapper;

/**
 * 融券卖出测试
 */
public class TestRQMC extends TestBase {
	private TestJYBase mTest = new TestJYBase();

	@BeforeClass
	public void enterRQMC() {
		Navigator.navigate("【两融】融券卖出", mTest.getPage());
	}

	/*
	 * 正例
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testRQMC(@Mapper(InfoMapper.class) Info info, CustomRecord record) {
		// 输入交易信息
		String vCheckPoint1 = mTest.inputTradeInfo(info, null, KeyboardLayout.LAYOUT_4_4, false);
		// 获取对话框1内容并校验
		mTest.checkConfirmMsg(vCheckPoint1);
		// 记录相关信息
		record.put(TestBase.KEY_CONFIRM, vCheckPoint1);
		// 获取对话框2内容并校验
		String vActualCheckPoint2 = mTest.checkResultMsg(info.getResultMsg());
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActualCheckPoint2);
	}

	/*
	 * 反例，非担保品证券
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	@DataFilter(row = FLFilter.class)
	public void testRQMC_FL_Code(@Mapper(InfoMapper.class) Info info, CustomRecord record) {
		PageJY vPage = mTest.getPage();
		// 输入代码
		vPage.doInputErrorCode(info.getCode(), info.getName(), false);
		// 校验对话框信息
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_1);
		String vActual = vAlert.doGetMsg();
		Assertions.assertThat(info.getResultMsg()).as("校验结果").isSubstringOf(vActual);
		vAlert.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActual);
	}

	/*
	 * 反例，跌停价
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	@DataFilter(row = FLFilter.class)
	public void testRQMC_FL_Price(@Mapper(InfoMapper.class) Info info, CustomRecord record) {
		PageJY vPage = mTest.getPage();
		// 输入代码并校验股票名称
		String vActualName = vPage.doInputCode(info.getCode(), info.getName(), false);
		Assertions.assertThat(vActualName).as("校验名称").isEqualTo(info.getName());
		// 获取价格并替换验证点中的{PRICE}
		String vPrice = vPage.doGetPrice();
		vPrice = mTest.getPrice(vPrice, info.getPrice());
		vPage.doEditPrice(vPrice);
		// 点击交易按钮
		vPage.doTrade();
		// 记录相关信息
		StringBuilder sb = new StringBuilder();
		sb.append("证券代码 ").append(info.getCode());
		sb.append("\n证券名称 ").append(vActualName);
		sb.append("\n委托价格 ").append(vPrice);
		record.put(TestBase.KEY_CONFIRM, sb.toString());
		// 获取对话框内容并校验
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_1);
		String vActualCheckPoint = vAlert.doGetMsg();
		Assertions.assertThat(vActualCheckPoint).as("校验错误信息").contains(info.getResultMsg());
		vAlert.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActualCheckPoint);
	}

}
