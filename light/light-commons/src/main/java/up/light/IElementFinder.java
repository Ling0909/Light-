package up.light;

import java.util.List;

import up.light.pojo.Locator;

public interface IElementFinder<T> {

	/**
	 * 根据定位信息查询元素
	 * 
	 * @param locator 定位信息
	 * @return 找到的元素
	 */
	T findElement(Locator locator);

	/**
	 * 根据定位信息查询元素
	 * 
	 * @param locator 定位信息
	 * @return 找到的元素列表
	 */
	List<T> findElements(Locator locator);

	/**
	 * 切换上下文
	 * 
	 * @param name 上下文名称
	 */
	// void context(String name);

	/**
	 * 获取真实驱动
	 * 
	 * @return 真实驱动
	 */
	Object getRealDriver();

}
