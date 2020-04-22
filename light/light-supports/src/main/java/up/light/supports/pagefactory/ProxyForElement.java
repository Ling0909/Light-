package up.light.supports.pagefactory;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import up.light.IElementFinder;
import up.light.pojo.Locator;

/**
 * 单个元素动态代理
 */
public class ProxyForElement implements MethodInterceptor {
	private IElementFinder<?> mFinder;
	private Locator mLocator;
	private Object mRealElement;

	public ProxyForElement(IElementFinder<?> finder, Locator locator) {
		this.mFinder = finder;
		this.mLocator = locator;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if ("toString".equals(method.getName())) {
			return "ProxyElement[" + mLocator + "]";
		}
		if (Object.class.equals(method.getDeclaringClass())) {
			return proxy.invokeSuper(obj, args);
		}

		Object object;
		if (mLocator.isCache() && mRealElement != null) {
			object = mRealElement;
		} else {
			// 查找元素
			object = mFinder.findElement(mLocator);
			if (mLocator.isCache()) {
				mRealElement = object;
			}
		}

		if (IElementGetter.class == method.getDeclaringClass()) {
			return object;
		}

		try {
			return method.invoke(object, args);
		} catch (Throwable t) {
			throw ThrowUtil.extractReadableException(t);
		}
	}

}
