package mobile.zxjt.test.ggt;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageHKHZZJ_WJSMX;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.test.base.TestBase;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.Key;

/**
 * 港股通回转资金测试
 */
public class TestHKHZZJ extends TestBase {
	private PageHKHZZJ_WJSMX mPage = PageManager.getPage(PageHKHZZJ_WJSMX.class);

	@BeforeClass
	public void enterHKHZZJ() {
		Navigator.navigate("回转资金", mPage);
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testHKHZZJ(@Key("验证1") String yz1, @Key("验证2") String yz2, CustomRecord record) {
		StringBuilder sb = new StringBuilder();
		// 等待加载
		mPage.waitAndCheck(PopupType.STYLE_2);
		String vActual1 = mPage.doGetRow1();
		Assertions.assertThat(vActual1).as("校验预冻结资金").isEqualTo(yz1);
		// 记录相关信息
		sb.append("预冻结资金\n").append(vActual1);
		record.put(TestBase.KEY_RESULT, sb.toString());
		String vActual2 = mPage.doGetRow2();
		Assertions.assertThat(vActual2).as("校验二级资金").isEqualTo(yz2);
		// 记录相关信息
		sb.append("\n二级资金\n").append(vActual1);
		record.put(TestBase.KEY_RESULT, sb.toString());
	}

}
