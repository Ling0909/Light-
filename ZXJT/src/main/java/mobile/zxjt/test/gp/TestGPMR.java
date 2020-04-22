package mobile.zxjt.test.gp;

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
import up.light.testng.data.annotations.Key;
import up.light.testng.data.annotations.Mapper;

/**
 * 股票买入测试
 */
public class TestGPMR extends TestBase {
	private TestJYBase mTest = new TestJYBase();

	@BeforeClass
	public void enterGPMR() {
		Navigator.navigate("买入", mTest.getPage());
	}

	/*
	 * 正例，包含市价买入
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testGPMR(@Mapper(InfoMapper.class) Info info, @Key("委托方式") String wtfs, CustomRecord record) {
		// 输入交易信息
		String vCheckPoint1 = mTest.inputTradeInfo(info, wtfs, KeyboardLayout.LAYOUT_5_4, true);
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
	 * 反例，涨停、跌停价买入
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	@DataFilter(row = FLFilter.class)
	public void testGPMR_FL_Price(@Mapper(InfoMapper.class) Info info, CustomRecord record) {
		PageJY vPage = mTest.getPage();
		// 输入代码并校验股票名称
		String vActualName = vPage.doInputCode(info.getCode(), info.getName());
		Assertions.assertThat(vActualName).as("校验名称").isEqualTo(info.getName());
		// 修改价格
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
		Assertions.assertThat(info.getResultMsg()).as("校验错误信息").isSubstringOf(vActualCheckPoint);
		vAlert.doAccept2();
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActualCheckPoint);
	}

	/*
	 * 反例，错误数量
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	@DataFilter(row = FLFilter.class)
	public void testGPMR_FL_Number(@Mapper(InfoMapper.class) Info info, CustomRecord record) {
		// 输入交易信息
		String vCheckPoint1 = mTest.inputTradeInfo(info, null, KeyboardLayout.LAYOUT_5_4, true);
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
	 * 反例，无效代码
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	@DataFilter(row = FLFilter.class)
	public void testGPMR_FL_Code(@Mapper(InfoMapper.class) Info info, @Key("委托方式") String wtfs, CustomRecord record) {
		PageJY vPage = mTest.getPage();
		vPage.reset();
		// 输入代码
		vPage.doInputErrorCode(info.getCode(), info.getName(), true);
		// 选择交易方式
		vPage.doChooseWTFS(wtfs);
		// 输入价格
		String vPrice = info.getPrice();
		if (vPrice != null) {
			vPage.doEditPrice(vPrice);
		}
		// 输入数量
		vPage.doInputNumber(info.getNumber(), KeyboardLayout.LAYOUT_5_4);
		String vCheckPoint1 = info.getConfirmMsg();
		// 点击交易按钮
		vPage.doTrade();
		// 获取对话框1内容并校验
		mTest.checkConfirmMsg(vCheckPoint1);
		// 记录相关信息
		record.put(TestBase.KEY_CONFIRM, vCheckPoint1);
		// 获取对话框2内容并校验
		String vActualCheckPoint2 = mTest.checkResultMsg(info.getResultMsg());
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActualCheckPoint2);
	}

}
