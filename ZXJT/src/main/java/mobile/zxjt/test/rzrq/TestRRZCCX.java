package mobile.zxjt.test.rzrq;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.page.PageRRZCCX;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.popup.PopupType;
import mobile.zxjt.test.base.TestBase;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderX;
import up.light.testng.data.annotations.Key;

/**
 * 融资融券资产查询测试
 */
public class TestRRZCCX extends TestBase {
	private PageRRZCCX mPage = PageManager.getPage(PageRRZCCX.class);

	@BeforeClass
	public void enterRRZCCX() {
		Navigator.navigate("【两融】资产查询", mPage);
	}

	/*
	 * 正例
	 */
	@Test(dataProvider = DataProviderX.NAME_LAZY, dataProviderClass = DataProviderX.class)
	public void testRRZCCX(@Key("可取") String kq, @Key("可用") String ky, @Key("余额") String ye, CustomRecord record) {
		// 等待加载
		mPage.waitAndCheck(PopupType.STYLE_1);
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
