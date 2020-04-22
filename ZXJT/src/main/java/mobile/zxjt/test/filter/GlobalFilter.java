package mobile.zxjt.test.filter;

import java.lang.reflect.Method;

import up.light.testng.data.IRow;
import up.light.testng.data.IRowFilter;

/**
 * 全局过滤器，根据"执行"列判断是否执行
 */
public class GlobalFilter implements IRowFilter {

	@Override
	public boolean accept(IRow row, Method method) {
		return row.getBoolean("执行");
	}

	@Override
	public Method getMethod() {
		return null;
	}

	@Override
	public void setMethod(Method method) {
	}

}
