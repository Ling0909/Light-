package mobile.zxjt.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageBase;
import mobile.zxjt.page.module.KeyboardLayout;
import mobile.zxjt.wait.WaitUtil;
import up.light.supports.pagefactory.DynamicLocator;

public class PageCodeSelect extends PageBase {
	private MobileElement oEditCode;
	private MobileElement oTextCode;
	private DynamicLocator<MobileElement> oTextItem;
	private MobileElement oTextCancel;

	@Override
	public void reset() {
		oTextCancel.click();
	}

	/**
	 * 根据名称选择相应代码
	 * 
	 * @param code 证券代码
	 * @param name 证券名称
	 */
	public void doSelectCode(String code, String name) {
		// 输入代码
		doInput(code);
		// 根据名称选择第一个
		oTextItem.findElement(name).click();
	}

	/**
	 * 选择无效代码（如000000）
	 * 
	 * @param code 证券代码
	 */
	public void doSelectInvalidCode(String code) {
		// 输入代码
		doInput(code);
		oTextCode.click();
	}

	/**
	 * 根据名称选择相应代码
	 * 
	 * @param code 证券代码
	 * @param name 证券名称
	 * @param index 被选目标索引
	 */
	public void doSelectCode(String code, String name, int index) {
		// 输入代码
		doInput(code);
		// 根据名称选择第一个
		oTextItem.findElements(name).get(index).click();
	}

	/**
	 * 根据坐标选择相应代码
	 * 
	 * @param code 证券代码
	 * @param name 证券名称
	 * @param locator 修正坐标回调接口
	 * 
	 * @author 大桥
	 */
	public void doSelectCode(String code, String name, IcoLocator locator) {
		// 输入代码
		doInput(code);
		// 回调
		int fix_y = locator.getY();
		// 根据修正Y坐标选择
		Point p;
		Dimension size;
		for (MobileElement e : oTextItem.findElements(name)) {
			p = e.getLocation();
			size = e.getSize();
			if (fix_y >= p.y && fix_y <= p.y + size.height) {
				e.click();
				return;
			}
		}
		throw new RuntimeException("Can't get element by fix_y: " + fix_y);
	}

	private void doInput(String code) {
		WebElement e = WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_MEDIUM);
		getKeyboard().doInput(code, e, KeyboardLayout.LAYOUT_5_4, false);
		WaitUtil.sleep(500, TimeUnit.MILLISECONDS);
	}

	/**
	 * 回调接口
	 */
	public interface IcoLocator {
		int getY();
	}

}
