package mobile.zxjt.test.ggt;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageHKZCCX;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.test.base.TestBase;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.Key;

/**
 * 港股通资产查询
 */
public class TestHKZCCX extends TestBase {
	private PageHKZCCX mPage = PageManager.getPage(PageHKZCCX.class);

	@BeforeClass
	public void enterHKZCCX() {
		Navigator.navigate("港股通资产查询", mPage);
	}

	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testHKZCCX(@Key("可取") String kq, @Key("可用") String ky, @Key("余额") String ye, CustomRecord record) {
		// 等待加载
		mPage.waitAndCheck(PopupType.STYLE_2);
		// 获取数值
		String vActualKQ = mPage.doGetKQ();
		String vActualKY = mPage.doGetKY();
		String vActualYE = mPage.doGetYE();
		// 校验
		Assertions.assertThat(vActualKQ).as("校验可取").isEqualTo(kq);
		Assertions.assertThat(vActualKY).as("校验可用").isEqualTo(ky);
		Assertions.assertThat(vActualYE).as("校验余额").isEqualTo(ye);
		// 记录信息
		StringBuilder sb = new StringBuilder();
		sb.append("可取 ").append(vActualKQ).append('\n');
		sb.append("可用 ").append(vActualKY).append('\n');
		sb.append("余额 ").append(vActualYE).append('\n');
		record.put(TestBase.KEY_RESULT, sb.toString());
	}

}
