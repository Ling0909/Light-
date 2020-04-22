package mobile.zxjt.page.base;

import org.openqa.selenium.WebElement;

import up.light.util.StringUtil;

public abstract class PageUtil {
	// 网页空格ASCII码是160，但trim只能处理ASCII为32的空格
	private static final String STRIP_CHARS = new String(new char[] { 32, 160 });

	/**
	 * 对于编辑框等输入控件获取其内容
	 * 
	 * @param e
	 * @return
	 */
	public static String getValue(WebElement e) {
		return StringUtil.trimWhitespace(e.getAttribute("value"), STRIP_CHARS);
	}

	/**
	 * 获取控件文本，对于输入类控件请使用{@link #getValue(WebElement)}
	 * 
	 * @param e
	 * @return
	 */
	public static String getText(WebElement e) {
		return StringUtil.trimWhitespace(e.getText(), STRIP_CHARS);
	}

	/**
	 * 去除字符串中的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String stripe(String str) {
		return StringUtil.trimWhitespace(str, STRIP_CHARS);
	}

}
