package up.light.supports.navigator;

import up.light.IElementFinder;
import up.light.pojo.Locator;

/**
 * 代表进入和退出的动作
 */
public interface IAction {

	/**
	 * 执行动作
	 * 
	 * @param finder 驱动
	 * @param locator 要操作元素的定位信息
	 */
	void perform(IElementFinder<?> finder, Locator locator);

}
