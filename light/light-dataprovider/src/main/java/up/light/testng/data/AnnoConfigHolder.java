package up.light.testng.data;

import java.lang.reflect.Method;

import org.testng.ITestContext;

import up.light.exception.LightInstantiationException;
import up.light.testng.data.annotations.DataFilter;
import up.light.testng.data.reader.IGroupResolver;
import up.light.util.InstantiateUtil;

/**
 * 存储测试方法注解信息
 */
class AnnoConfigHolder {
	private Method method;
	private String group;
	private IRowFilter rowFilter;
	private IColumnFilter columnFilter;

	public void update(ITestContext context, Method method, IGroupResolver resolver) {
		// 当设置invocationCount时DataProvider会被多次调用
		// 方法相同，不需更新
		if (this.method == method)
			return;

		// 清空之前结果，防止干扰
		rowFilter = null;
		columnFilter = null;

		// 更新group
		group = resolver.getGroup(context, method);
		// 获取注解filter
		DataFilter vAnno = method.getAnnotation(DataFilter.class);
		if (vAnno != null) {
			// row
			Class<? extends IRowFilter> vRowClazz = vAnno.row();
			if (vRowClazz != IRowFilter.class) {
				try {
					rowFilter = InstantiateUtil.instantiate(vRowClazz);
				} catch (LightInstantiationException e) {
					throw new RuntimeException(e.getCause());
				}
			}
			// column
			Class<? extends IColumnFilter> vColumnClazz = vAnno.column();
			if (vColumnClazz != IColumnFilter.class) {
				try {
					columnFilter = InstantiateUtil.instantiate(vColumnClazz);
				} catch (LightInstantiationException e) {
					throw new RuntimeException(e.getCause());
				}
			}
		}
		this.method = method;
	}

	public String getGroup() {
		return group;
	}

	public IRowFilter getRowFilter() {
		return rowFilter;
	}

	public IColumnFilter getColumnFilter() {
		return columnFilter;
	}

}
