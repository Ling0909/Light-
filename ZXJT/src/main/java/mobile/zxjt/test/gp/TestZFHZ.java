package mobile.zxjt.test.gp;

import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageZFHZ;
import mobile.zxjt.page.PageZJDD;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.ToolBar;
import mobile.zxjt.page.module.popup.IAlert;
import mobile.zxjt.page.module.popup.IConfirm;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.page.module.popup.Popups;
import mobile.zxjt.test.base.TestBase;
import mobile.zxjt.wait.WaitUtil;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.Key;

public class TestZFHZ extends TestBase {
	private PageZJDD mPageZJDD = PageManager.getPage(PageZJDD.class);
	private PageZFHZ mPage = PageManager.getPage(PageZFHZ.class);

	@BeforeClass
	public void enterZFHZ() {
		Navigator.navigate("银证转账", mPageZJDD);
		mPageZJDD.doSwitchZJDD();
	}

	@AfterClass
	public void leaveZFHZ() {
		if (mPage.isInZFHZ()) {
			PageManager.getPage(ToolBar.class).doBack();
		}
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testZFHZ(@Key("调拨类型") String type, @Key("金额") String je, @Key("辅账户") String fzh,
			@Key("资金密码") String zjmm, @Key("验证1") String yz1, @Key("验证2") String yz2, CustomRecord record) {
		if (!mPage.isInZFHZ()) {
			// 点击调拨按钮
			mPageZJDD.doEnterDB(fzh);
			Navigator.setCurrentPage(mPage);
		}
		// 切换转入转出账户
		mPage.doSwitchAccount(type);
		// 输入划转数量
		mPage.doInputNumber(je);
		// 点击确定
		mPage.doTrade();
		// 输入密码
		mPage.doInputPwd(zjmm);
		// 校验对话框1
		IConfirm vConfirm = Popups.getConfirm(PopupType.STYLE_1);
		String vActualCheckPoint1 = vConfirm.doGetMsg();
		String replaced = vActualCheckPoint1.replace(",", "");
		Assertions.assertThat(replaced).as("校验确认信息").isEqualTo(yz1);
		vConfirm.doAccept();
		// 记录相关结果
		record.put(TestBase.KEY_CONFIRM, vActualCheckPoint1);
		// 校验对话框2
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_1);
		String vActualCheckPoint2 = vAlert.doGetMsg();
		Assertions.assertThat(vActualCheckPoint2).as("校验结果").contains(yz2);
		vAlert.doAccept();
		// 记录相关结果
		record.put(TestBase.KEY_RESULT, vActualCheckPoint2);

		WaitUtil.sleep(1);
		if (mPage.isInZFHZ()) {
			mPage.reset();
		} else {
			Navigator.setCurrentPage(mPageZJDD);
		}
	}

}
