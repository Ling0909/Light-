package mobile.zxjt.test.gp;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageJY;
import mobile.zxjt.page.module.KeyboardLayout;
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
 * 股票卖出测试
 */
public class TestGPMC extends TestBase {
	private TestJYBase mTest = new TestJYBase();

	@BeforeClass
	public void enterGPMC() {
		Navigator.navigate("卖出", mTest.getPage());
	}

	/*
	 * 正例，包含市价买入
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testGPMC(@Mapper(InfoMapper.class) Info info, @Key("委托方式") String wtfs, CustomRecord record) {
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
	 * 反例，错误数量、持仓不足
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	@DataFilter(row = FLFilter.class)
	public void testGPMC_FL_Number(@Mapper(InfoMapper.class) Info info, CustomRecord record) {
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
	 * 反例，无效代码，因选择代码000000时无法选择委托方式，暂不开放
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	@DataFilter(row = FLFilter.class)
	public void testGPMC_FL_Code(@Mapper(InfoMapper.class) Info info, @Key("委托方式") String wtfs, CustomRecord record) {
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
