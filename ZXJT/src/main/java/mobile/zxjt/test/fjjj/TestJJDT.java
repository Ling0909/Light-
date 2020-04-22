package mobile.zxjt.test.fjjj;

import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageJJDTDS;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.ToolBar;
import mobile.zxjt.page.module.popup.IAlert;
import mobile.zxjt.page.module.popup.IConfirm;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import mobile.zxjt.test.base.Info;
import mobile.zxjt.test.base.InfoMapper;
import mobile.zxjt.test.base.TestBase;
import mobile.zxjt.wait.WaitUtil;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.Key;
import up.light.testng.data.annotations.Mapper;

/**
 * 基金定投测试
 */
public class TestJJDT extends TestBase {
	private PageJJDTDS mPage = PageManager.getPage(PageJJDTDS.class);

	@BeforeClass
	public void enterJJDT() {
		Navigator.navigate("基金定投", mPage);
		// 切换类型并点击添加
		mPage.doSwitchType("基金定投");
		mPage.doAdd();
	}

	@AfterClass
	public void leaveJJDT() {
		PageManager.getPage(ToolBar.class).doBack();
	}

	/*
	 * 正例、反例
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testJJDT(@Mapper(InfoMapper.class) Info info, @Key("扣款周期") String kkzq, @Key("扣款日") String kkr,
			CustomRecord record) {
		// 输入代码、校验名称
		String vName = mPage.doInputCode(info.getCode());
		Assertions.assertThat(vName).as("校验名称").isEqualTo(info.getName());
		// 输入数量
		mPage.doInputNumber(info.getNumber());
		// 选择扣款周期
		mPage.doChooseTime(kkzq, kkr);
		// 获取开始日期、终止日期
		String vKKRQ = mPage.doGetBeginTime();
		String vZZRQ = mPage.doGetEndTime();
		String vCheckPoint1 = info.getConfirmMsg().replace("{BeginTime}", vKKRQ).replace("{EndTime}", vZZRQ);
		WaitUtil.sleep(1);
		// 点击交易
		mPage.doTrade();
		// 处理风险提示框
		mPage.doHandleRiskMsg();
		// 验证对话框1
		IConfirm vConfirm = Popups.getConfirm(PopupType.STYLE_JJDT);
		String vActualCheckPoint1 = vConfirm.doGetMsg();
		Assertions.assertThat(vActualCheckPoint1).as("校验确认信息").isEqualTo(vCheckPoint1);
		vConfirm.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_CONFIRM, vCheckPoint1);
		// 验证对话框2
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_1);
		String vActualCheckPoint2 = vAlert.doGetMsg();
		Assertions.assertThat(vActualCheckPoint2).as("校验结果").contains(info.getResultMsg());
		vAlert.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActualCheckPoint2);
	}

}
