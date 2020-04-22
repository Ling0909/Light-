package mobile.zxjt.test.rzrq;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageRRYZZZ;
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
 * 融资融券银证转账测试
 */
public class TestRRYZZZ extends TestBase {
	private PageRRYZZZ mPage = PageManager.getPage(PageRRYZZZ.class);

	@BeforeClass
	public void enterRRYZZZ() {
		Navigator.navigate("【两融】银证转账", mPage);
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testRRYZZZ(@Key("转账方式") String type, @Key("转账金额") String num, @Key("银行密码") String yhmm,
			@Key("资金密码") String jzmm, @Key("验证1") String yz1, @Key("验证2") String yz2, CustomRecord record) {
		// 切换转账方式
		mPage.doSwitchType(type);
		// 输入金额
		mPage.doInputNumber(num);
		// 输入密码，点击转账
		mPage.doTrade();
		mPage.doInputPassword(yhmm, jzmm);
		// 验证对话框1
		IConfirm vConfirm = Popups.getConfirm(PopupType.STYLE_1);
		String vActualCheckPoint1 = vConfirm.doGetMsg();
		String vReplaced = vActualCheckPoint1.replace(",", "");
		Assertions.assertThat(vReplaced).as("校验确认信息").isEqualTo(yz1);
		vConfirm.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_CONFIRM, vActualCheckPoint1);
		// 验证对话框2
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_1);
		String vActualCheckPoint2 = vAlert.doGetMsg();
		Assertions.assertThat(vActualCheckPoint2).as("校验结果").contains(yz2);
		vAlert.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActualCheckPoint2);
	}

}
