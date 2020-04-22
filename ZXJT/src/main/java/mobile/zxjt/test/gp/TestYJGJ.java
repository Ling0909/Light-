package mobile.zxjt.test.gp;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageZJDD;
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
 * 一键归集测试
 */
public class TestYJGJ extends TestBase {
	private PageZJDD mPage = PageManager.getPage(PageZJDD.class);

	@BeforeClass
	public void enterYJGJ() {
		Navigator.navigate("银证转账", mPage);
		mPage.doSwitchZJDD();
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testYJGJ(@Key("验证1") String yz1, CustomRecord record) {
		// 获取主辅资金
		float vSubMoney = mPage.doGetSubMoney();
		float vMainMoney = mPage.doGetMainMoney();
		// 点击一键归集
		mPage.doYJGJ();
		// 获取对话框中的金额
		IConfirm vConfirm = Popups.getConfirm(PopupType.STYLE_YJGJ);
		String vMsg = vConfirm.doGetMsg().replace(",", "");
		int vIndex = vMsg.indexOf('元');
		float vNum = Float.valueOf(vMsg.substring(3, vIndex));
		// 验证待归集金额
		Assertions.assertThat(vNum).as("验证待归集金额").isEqualTo(vSubMoney);
		vConfirm.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_CONFIRM, vMsg);
		// 验证结果
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_YJGJ);
		vMsg = vAlert.doGetMsg();
		Assertions.assertThat(vMsg).as("验证结果").contains(yz1);
		vAlert.doAccept();
		// 等待加载完成
		mPage.waitAndCheck(PopupType.STYLE_1);
		// 验证归集后金额
		float vNewMainMoney = vMainMoney + vSubMoney;
		vMainMoney = mPage.doGetMainMoney();
		Assertions.assertThat(vNewMainMoney).as("验证归集后金额").isEqualTo(vMainMoney);
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vMsg);
	}

}
