package up.light.testng.data.defaults;

import java.lang.reflect.Method;

import org.testng.ITestContext;

import up.light.testng.data.annotations.GroupName;
import up.light.testng.data.reader.IGroupResolver;

/**
 * 默认GroupResolver，按照以下顺序获取group
 * <ol>
 * <li>读取方法<code>GroupName</code>注解的值作为group名</li>
 * <li>直接使用<code>类名 + "_" + 方法名 </code>作为group名</li>
 * </ol>
 */
public class DefaultGroupResolver implements IGroupResolver {

	@Override
	public String getGroup(ITestContext context, Method method) {
		String vGroup = getGroupFromAnnotation(method);
		if (vGroup == null) {
			String vClassName = method.getDeclaringClass().getSimpleName();
			String vMethodName = method.getName();
			vGroup = vClassName + "_" + vMethodName;
		}
		return vGroup;
	}

	private String getGroupFromAnnotation(Method method) {
		GroupName vAnno = method.getAnnotation(GroupName.class);
		if (vAnno == null)
			return null;
		return vAnno.value();
	}

}
