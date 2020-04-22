package up.light.testng.data.reader;

import java.lang.reflect.Method;

import org.testng.ITestContext;

/**
 * 数据分组解析
 * 
 * @since 1.0.0
 */
public interface IGroupResolver {

	/**
	 * 根据测试上下文、测试方法解析数据分组
	 * 
	 * @param context 测试上下文
	 * @param method 测试方法
	 * @return 测试方法对应的数据分组名
	 */
	String getGroup(ITestContext context, Method method);

}
