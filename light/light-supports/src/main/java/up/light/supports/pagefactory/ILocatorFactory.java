package up.light.supports.pagefactory;

import java.lang.reflect.Field;

import up.light.pojo.Locator;

public interface ILocatorFactory {

	/**
	 * 根据字段获取对应的Locator
	 * 
	 * @param field 正在处理的字段
	 * @return 字段对应的Locator，用于动态代理查找元素
	 */
	Locator getLocator(Field field);

}
