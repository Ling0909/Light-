package mobile.zxjt.test.jj;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageJJZH;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.popup.IAlert;
import mobile.zxjt.page.module.popup.IConfirm;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import mobile.zxjt.test.base.TestBase;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.Key;

/**
 * 基金转换测试
 */
public class TestJJZH extends TestBase {
	private PageJJZH mPage = PageManager.getPage(PageJJZH.class);

	@BeforeClass
	public void enterJJZH() {
		Navigator.navigate("基金转换", mPage);
	}

	/*
	 * 正例
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testJJZH(@Key("转出代码") String zcdm, @Key("转出名称") String zcmc, @Key("转入代码") String zrdm,
			@Key("转入名称") String zrmc, @Key("数量") int num, @Key("验证1") String yz1, @Key("验证2") String yz2,
			CustomRecord record) {
		// 输入代码并校验名称
		String vZCMC = mPage.doInputZCDM(zcdm);
		Assertions.assertThat(vZCMC).as("校验转出名称").isEqualTo(zcmc);
		String vZRMC = mPage.doInputZRDM(zrdm);
		Assertions.assertThat(vZRMC).as("校验转入名称").isEqualTo(zrmc);
		// 输入数量
		mPage.doInputNum(String.valueOf(num));
		// 替换{ZDKZ}
		String vZDKZ = mPage.doGetZDKZ();
		String vCheck1 = yz1.replace("{ZDKZ}", vZDKZ);
		// 点击转换
		mPage.doTrade();
		// 验证对话框1
		IConfirm vConfirm = Popups.getConfirm(PopupType.STYLE_1);
		String vAcutal1 = vConfirm.doGetMsg();
		Assertions.assertThat(vAcutal1).as("校验确认信息").isEqualTo(vCheck1);
		vConfirm.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_CONFIRM, vCheck1);
		// 处理风险提示框
		mPage.doHandleRiskMsg();
		// 验证对话框2
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_1);
		String vActual2 = vAlert.doGetMsg();
		Assertions.assertThat(vActual2).as("校验结果").contains(yz2);
		vAlert.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActual2);
	}

}
