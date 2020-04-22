package up.light.supports.pagefactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.cglib.proxy.MethodInterceptor;
import up.light.Constants;
import up.light.IElementFinder;
import up.light.pojo.Locator;
import up.light.util.ReflectUtil;
import up.light.util.ReflectUtil.FieldCallback;

/**
 * 为字段设置动态代理
 */
public class FieldDecorator implements FieldCallback {
	private static final Log logger = LogFactory.getLog(FieldDecorator.class);
	private ILocatorFactory mFactory;
	private Object mInstance;
	private IElementFinder<?> mFinder;

	@Override
	public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
		Locator vLocator = mFactory.getLocator(field);
		if (vLocator == null)
			return;

		MethodInterceptor interceptor;
		Object value;

		ReflectUtil.makeAccessible(field);
		Class<?> vClazz = field.getType();

		if (List.class.isAssignableFrom(vClazz)) {
			// List
			interceptor = new ProxyForList(mFinder, vLocator);
			value = ProxyFactory.getProxy(ArrayList.class, interceptor);
		} else if (DynamicLocator.class == vClazz) {
			// DynamicLocator
			value = new DynamicLocator<>(mFinder, vLocator);
		} else {
			// Element
			interceptor = new ProxyForElement(mFinder, vLocator);
			value = ProxyFactory.getProxy(field.getType(), interceptor);
		}
		ReflectUtil.setField(field, mInstance, value);

		if (logger.isDebugEnabled()) {
			logger.debug(Constants.LOG_TAG + "field: " + field.getName() + ", locator: " + vLocator);
		}
	}

	public void setParameters(ILocatorFactory factory, Object instance, IElementFinder<?> finder) {
		this.mFactory = factory;
		this.mInstance = instance;
		this.mFinder = finder;
	}

}
