package mobile.zxjt.test.filter;

import java.lang.reflect.Method;

import up.light.testng.data.IRow;
import up.light.testng.data.IRowFilter;

public class DebugFilter implements IRowFilter {

	@Override
	public boolean accept(IRow row, Method method) {
		return row.getIndex() <= 2;
	}

	@Override
	public Method getMethod() {
		return null;
	}

	@Override
	public void setMethod(Method method) {
	}

}
