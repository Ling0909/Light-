package mobile.zxjt.page.module.popup;

public interface IAlert extends IPopup {

	/**
	 * 获取Alert文本
	 * 
	 * @return
	 */
	String doGetMsg();

	/**
	 * 关闭Alert
	 */
	void doAccept();

	/**
	 * 关闭Alert（仅用于点击无效时）
	 */
	void doAccept2();

	/**
	 * Alert是否存在
	 * 
	 * @return
	 */
	boolean exists();

}
