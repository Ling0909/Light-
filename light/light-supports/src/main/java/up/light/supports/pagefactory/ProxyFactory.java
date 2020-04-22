package up.light.supports.pagefactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public abstract class ProxyFactory {

	/**
	 * 生成CGLIB动态代理
	 * 
	 * @param clazz 要代理的类（要有无参构造方法）
	 * @param interceptor 方法拦截器
	 * @return 动态代理对象
	 */
	public static Object getProxy(Class<?> clazz, MethodInterceptor interceptor) {
		Enhancer vEnhancer = new Enhancer();
		vEnhancer.setSuperclass(clazz);
		vEnhancer.setInterfaces(new Class[] { IElementGetter.class });
		vEnhancer.setCallback(interceptor);
		return vEnhancer.create();
	}

}
