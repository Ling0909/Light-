package up.light.supports.pagefactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import up.light.IElementFinder;
import up.light.pojo.Locator;

/**
 * List动态代理
 */
public class ProxyForList implements MethodInterceptor {
	private IElementFinder<?> mFinder;
	private Locator mLocator;
	private List<Object> mRealElements;

	public ProxyForList(IElementFinder<?> finder, Locator locator) {
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

		List<Object> objects;
		if (mLocator.isCache() && mRealElements != null) {
			objects = mRealElements;
		} else {
			// 查找元素
			List<?> es = mFinder.findElements(mLocator);
			objects = new ArrayList<>(es.size());
			objects.addAll(es);
			if (mLocator.isCache()) {
				mRealElements = objects;
			}
		}

		if (IElementGetter.class == method.getDeclaringClass()) {
			return objects;
		}

		try {
			return method.invoke(objects, args);
		} catch (Throwable t) {
			throw ThrowUtil.extractReadableException(t);
		}
	}

}
