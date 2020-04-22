package up.light.testng.data;

import java.lang.reflect.Method;

/**
 * 行过滤器
 * 
 * @since 1.0.0
 */
public interface IRowFilter {

	/**
	 * 过滤行
	 * 
	 * @param Row 行对象
	 * @param method 当前测试方法
	 * @return 接受返回true， 否则返回false
	 */
	boolean accept(IRow row, Method method);

	/**
	 * 获取当前测试方法，若在accept方法中不使用method参数可直接返回null
	 * 
	 * @return 测试方法
	 */
	Method getMethod();

	/**
	 * 设置当前测试方法，若在accept方法中不使用method参数方法体可为空
	 * 
	 * @param method 测试方法
	 */
	void setMethod(Method method);

}
