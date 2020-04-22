package mobile.zxjt.test.jj;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageJJ;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.popup.IAlert;
import mobile.zxjt.page.module.popup.IConfirm;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import mobile.zxjt.test.base.Info;
import mobile.zxjt.test.base.InfoMapper;
import mobile.zxjt.test.base.TestBase;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.Mapper;

/**
 * 基金认购测试
 */
public class TestJJRG extends TestBase {
	private PageJJ mPage = PageManager.getPage(PageJJ.class);

	@BeforeClass
	public void enterRG() {
		Navigator.navigate("认购", mPage);
	}

	/*
	 * 正例、反例
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testRG(@Mapper(InfoMapper.class) Info info, CustomRecord record) {
		// 输入代码并校验名称
		String vName = mPage.doInputCode(info.getCode());
		Assertions.assertThat(vName).as("校验名称").isEqualTo(info.getName());
		// 输入数量
		mPage.doInputNumber(info.getNumber());
		// 获取净值、可用资金
		String vJZ = mPage.doGetJJJZ();
		String vKY = mPage.doGetKY();
		// 替换{JZ}、{KY}
		String vCheckPoint1 = info.getConfirmMsg().replace("{JZ}", vJZ).replace("{KY}", vKY);
		// 点击交易
		mPage.doTrade();
		// 验证对话框1
		IConfirm vConfirm = Popups.getConfirm(PopupType.STYLE_1);
		String vAcutalCheckPoint1 = vConfirm.doGetMsg();
		Assertions.assertThat(vAcutalCheckPoint1).as("校验确认信息").isEqualTo(vCheckPoint1);
		vConfirm.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_CONFIRM, vCheckPoint1);
		// 处理风险提示框
		mPage.doHandleRiskMsg();
		// 验证对话框2
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_1);
		String vActualCheckPoint2 = vAlert.doGetMsg();
		Assertions.assertThat(vActualCheckPoint2).as("校验结果").contains(info.getResultMsg());
		vAlert.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActualCheckPoint2);
	}

}
