package mobile.zxjt.page;

import java.lang.reflect.Field;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.ElementOf;
import mobile.zxjt.page.base.ElementOfs;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.page.module.popup.PopupType;
import up.light.util.ReflectUtil;

public class PageTradeWithSelect extends PageBase {
	private MobileElement mElementOfCode;
	private MobileElement mElementOfName;
	private MobileElement mElementOfNumber;
	private MobileElement mElementOfButtonOK;

	protected PageCodeSelect mPageCodeSelect = PageManager.getPage(PageCodeSelect.class);
	protected boolean isInSelectView;

	/**
	 * 对话框类型，子类需在调用方法前设置其值
	 */
	protected PopupType mType;

	public PageTradeWithSelect() {
		Field[] fs = this.getClass().getDeclaredFields();

		for (Field f : fs) {
			if (MobileElement.class != f.getType())
				continue;

			ElementOf ef = f.getAnnotation(ElementOf.class);

			if (ef != null) {
				ElementOfs eType = ef.value();
				ReflectUtil.makeAccessible(f);
				MobileElement eValue = null;
				try {
					eValue = (MobileElement) f.get(this);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}

				switch (eType) {
				case CODE:
					mElementOfCode = eValue;
					break;
				case NAME:
					mElementOfName = eValue;
					break;
				case NUMBER:
					mElementOfNumber = eValue;
					break;
				case BUTTON_OK:
					mElementOfButtonOK = eValue;
					break;
				}
			}
		}
	}

	@Override
	public void reset() {
		if (isInSelectView) {
			mPageCodeSelect.reset();
			isInSelectView = false;
		}
		super.reset();
	}

	/**
	 * 输入证券代码
	 * 
	 * @param code 证券代码
	 * @param name 证券名称
	 * @param wait 页面刚打开时是否存在加载框
	 * @return 实际显示的名称
	 */
	public String doInputCode(String code, String name, boolean wait) {
		if (wait) {
			// 等待加载完毕
			waitAndCheck(mType);
		}
		// 点击代码编辑框
		mElementOfCode.click();
		// 选择代码
		doSelectCode(code, name);
		waitAndCheck(mType);
		return PageUtil.getText(mElementOfName);
	}

	/**
	 * 输入证券代码
	 * 
	 * @param code 证券代码
	 * @param name 证券名称
	 * @return 实际显示的名称
	 */
	public String doInputCode(String code, String name) {
		return doInputCode(code, name, true);
	}

	/**
	 * 输入交易数量
	 * 
	 * @param num 数量
	 * @param layout 键盘布局
	 */
	public void doInputNumber(String num, KeyboardLayout layout) {
		getKeyboard().doInput(num, mElementOfNumber, layout);
	}

	/**
	 * 点击交易按钮
	 */
	public void doTrade() {
		mElementOfButtonOK.click();
		getLoading().waitForLoading();
	}

	/**
	 * 选择证券代码
	 * 
	 * @param code 证券代码
	 * @param name 证券名称
	 */
	protected void doSelectCode(String code, String name) {
		isInSelectView = true;
		if (name == null) {
			mPageCodeSelect.doSelectInvalidCode(code);
		} else {
			mPageCodeSelect.doSelectCode(code, name);
		}
		isInSelectView = false;
	}

}
