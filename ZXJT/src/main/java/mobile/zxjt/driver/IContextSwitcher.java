package mobile.zxjt.driver;

/**
 * 上下文切换器，用于多个WEBVIEW时，根据自定义标签（如WEBVIEW[-1]）切换上下文
 */
public interface IContextSwitcher {

	/**
	 * 根据自定义标签切换上下文
	 * 
	 * @param context 自定义标签
	 * @param driver 驱动对象
	 */
	void switchContext(String context);

	/**
	 * 在窗口关闭前切到其他窗口防止ChromeDriver无响应
	 */
	void reset();

}
