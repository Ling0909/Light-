package mobile.zxjt.test.lof;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageLOF;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.KeyboardLayout;
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
 * LOF申购测试
 */
public class TestLOFSG extends TestBase {
	private PageLOF mPage = PageManager.getPage(PageLOF.class);

	@BeforeClass
	public void enterLOFSG() {
		Navigator.navigate("LOF基金申购", mPage);
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testLOFSG(@Mapper(InfoMapper.class) Info info, CustomRecord record) {
		// 输入代码并校验名称
		String vName = mPage.doInputCode(info.getCode(), info.getName(), false);
		Assertions.assertThat(vName).as("校验名称").isEqualTo(info.getName());
		// 输入数量
		mPage.doInputNumber(info.getNumber(), KeyboardLayout.LAYOUT_4_4);
		// 替换{SX}
		String vSX = mPage.doGetSX();
		String vCheckPoint1 = info.getConfirmMsg().replace("{SX}", vSX);
		// 点击交易
		mPage.doTrade();
		// 验证对话框1
		IConfirm vConfirm = Popups.getConfirm(PopupType.STYLE_2);
		String vAcutalCheckPoint1 = vConfirm.doGetMsg();
		Assertions.assertThat(vAcutalCheckPoint1).as("校验确认信息").isEqualTo(vCheckPoint1);
		vConfirm.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_CONFIRM, vCheckPoint1);
		// 验证对话框2
		IAlert vAlert = Popups.getAlert(PopupType.STYLE_2);
		String vActualCheckPoint2 = vAlert.doGetMsg();
		Assertions.assertThat(vActualCheckPoint2).as("校验结果").contains(info.getResultMsg());
		vAlert.doAccept();
		// 记录相关信息
		record.put(TestBase.KEY_RESULT, vActualCheckPoint2);
	}

}
