package up.light.supports.pagefactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import up.light.Constants;
import up.light.IElementFinder;
import up.light.util.Assert;
import up.light.util.ReflectUtil;

public abstract class PageFactory {
	private static final Log logger = LogFactory.getLog(PageFactory.class);
	private static FieldDecorator callback = new FieldDecorator();
	private static ILocatorFactory factory = new AnnotationLocatorFactory();
	private static ElementTypeFilter filter = new ElementTypeFilter();
	private static Class<?> typeCache;
	private static IElementFinder<?> finderCache;

	/**
	 * 初始化PageObject
	 * 
	 * @param finder 动态代理中使用的元素查找器
	 * @param page 要初始化的PageObject实例
	 * @param typeCache 要代理的类型
	 */
	public static void initElements(IElementFinder<?> finder, Object page, Class<?> proxyType) {
		if (logger.isInfoEnabled())
			logger.info(Constants.LOG_TAG + "initialize page object: " + page);

		Class<?> type = getProxyType(finder);
		Assert.isAssignable(type, proxyType, null);
		callback.setParameters(factory, page, finder);
		filter.setType(proxyType);
		// 不初始化父类成员变量
		ReflectUtil.doWithLocalFields(page.getClass(), callback, filter);
	}

	public static void initElements(IElementFinder<?> finder, Object page, Class<?> proxyType,
			ILocatorFactory factory2) {
		if (logger.isInfoEnabled())
			logger.info(Constants.LOG_TAG + "initialize page object: " + page);

		Class<?> type = getProxyType(finder);
		Assert.isAssignable(type, proxyType, null);
		callback.setParameters(factory2, page, finder);
		filter.setType(proxyType);
		// 不初始化父类成员变量
		ReflectUtil.doWithLocalFields(page.getClass(), callback, filter);
	}

	/**
	 * 设置如何生成Locator，默认根据annotation生成，可用Factory如下：
	 * <ul>
	 * <li>{@link up.light.supports.pagefactory.AnnotationLocatorFactory}</li>
	 * <li>{@link up.light.supports.pagefactory.JsonLocatorFactory}</li>
	 * </ul>
	 * 
	 * @param factory
	 */
	public static void setFactory(ILocatorFactory factory) {
		if (factory != null) {
			PageFactory.factory = factory;
		}
	}

	/*
	 * 获取IElementFinder泛型类型
	 */
	private static Class<?> getProxyType(IElementFinder<?> finder) {
		if (PageFactory.finderCache == finder && typeCache != null)
			return typeCache;

		Type[] types = finder.getClass().getGenericInterfaces();
		for (Type t : types) {
			if (t instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) t;
				if (pt.getRawType() == IElementFinder.class) {
					PageFactory.finderCache = finder;
					PageFactory.typeCache = (Class<?>) pt.getActualTypeArguments()[0];
					return typeCache;
				}
			}
		}
		throw new IllegalArgumentException("Can't get the type to proxy");
	}

}
