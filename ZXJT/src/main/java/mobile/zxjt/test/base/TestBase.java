package mobile.zxjt.test.base;

import mobile.zxjt.navigator.Navigator;

/**
 * Test基类，提供默认的recovery方法
 */
public class TestBase {
	public static final String KEY_CONFIRM = "confirm";
	public static final String KEY_RESULT = "result";

	public void recovery() {
		Navigator.resetPage();
	}

}
