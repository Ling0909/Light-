package mobile.zxjt.page.base;

import java.util.HashMap;
import java.util.Map;

import up.light.exception.LightInstantiationException;
import up.light.util.Assert;
import up.light.util.InstantiateUtil;

/**
 * Page管理器，获取Page实例，为防止Page多次初始化，所有Page均使用单例模式
 */
public class PageManager {
	private static Map<Class<? extends PageBase>, PageBase> pages = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static <T extends PageBase> T getPage(Class<T> clazz) {
		Assert.notNull(clazz, "class must not be null");
		PageBase p = pages.get(clazz);

		if (p == null) {
			try {
				p = InstantiateUtil.instantiate(clazz);
			} catch (LightInstantiationException e) {
				throw new RuntimeException(e.getCause());
			}
			pages.put(clazz, p);
		}
		return (T) p;
	}

}
