package mobile.zxjt.test.filter;

import java.lang.reflect.Method;

import up.light.testng.data.IRow;
import up.light.testng.data.IRowFilter;

/**
 * 反例过滤
 */
public class FLFilter implements IRowFilter {
	private Method method;

	@Override
	public boolean accept(IRow row, Method method) {
		/*boolean use = row.getBoolean("执行");
		if (use) {
			String type = row.getString("类型");
			if (type != null) {
				return type.equalsIgnoreCase(getMethodSuffix(method));
			}
		}
		return use;*/
		return false;
	}

	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public void setMethod(Method method) {
		this.method = method;
	}

	private String getMethodSuffix(Method method) {
		String name = method.getName();
		int pos = name.lastIndexOf('_');
		if (pos >= 0) {
			return name.substring(pos + 1);
		} else {
			return null;
		}
	}

}
