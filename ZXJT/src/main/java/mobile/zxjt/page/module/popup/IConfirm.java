package mobile.zxjt.page.module.popup;

public interface IConfirm extends IPopup {

	/**
	 * 获取Confirm文本
	 * 
	 * @return
	 */
	String doGetMsg();

	/**
	 * 点击Confirm确定
	 */
	void doAccept();

	/**
	 * 点击Confirm确定（仅用于点击无效时）
	 */
	void doAccept2();

	/**
	 * 点击Confirm取消
	 */
	void doCancel();

}
