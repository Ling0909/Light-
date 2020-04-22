package mobile.zxjt.test.ggt;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageHKFZ_EDCX;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.test.base.TestBase;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.Key;

/**
 * 港股通负债查询测试
 */
public class TestHKFZCX extends TestBase {
	private PageHKFZ_EDCX mPage = PageManager.getPage(PageHKFZ_EDCX.class);

	@BeforeClass
	public void enterHKFZCX() {
		Navigator.navigate("负债查询", mPage);
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testHKFZCX(@Key("沪港通") String hgt, @Key("深港通") String sgt, CustomRecord record) {
		StringBuilder sb = new StringBuilder();
		// 等待加载
		mPage.waitAndCheck(PopupType.STYLE_2);
		String vActualHGT = mPage.doGetHGT();
		Assertions.assertThat(vActualHGT).as("校验沪港通").isEqualTo(hgt);
		// 记录相关信息
		sb.append("沪港通\n").append(vActualHGT);
		record.put(TestBase.KEY_RESULT, sb.toString());
		String vActualSGT = mPage.doGetSGT();
		Assertions.assertThat(vActualSGT).as("校验深港通").isEqualTo(sgt);
		// 记录相关信息
		sb.append("\n深港通\n").append(vActualHGT);
		record.put(TestBase.KEY_RESULT, sb.toString());
	}

}
