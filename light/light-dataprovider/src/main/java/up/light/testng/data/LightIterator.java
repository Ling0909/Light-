package up.light.testng.data;

import java.lang.reflect.Method;
import java.util.Iterator;

import up.light.testng.data.reader.IDataReader;

/**
 * 用于DataProvider延迟加载
 * 
 * @since 1.0.0
 * @see DataProviderX
 */
public class LightIterator implements Iterator<Object[]> {
	private IDataReader mReader;
	private Method mMethod;
	private IRowFilter mRowFilter;
	private IColumnFilter mColumnFilter;
	private IRow mCache;

	public LightIterator(IDataReader reader, Method method, IRowFilter rowFilter, IColumnFilter columnFilter) {
		mReader = reader;
		mMethod = method;
		mRowFilter = rowFilter;
		mColumnFilter = columnFilter;
	}

	@Override
	public boolean hasNext() {
		IRow vRow = null;
		while (mReader.hasNext()) {
			vRow = mReader.readRow(mColumnFilter);
			if (mRowFilter != null && !mRowFilter.accept(vRow, mRowFilter.getMethod())) {
				continue;
			}
			mCache = vRow;
			return true;
		}
		return false;
	}

	@Override
	public Object[] next() {
		if (mCache == null)
			throw new IllegalStateException("There is no more data to read");
		return DataProviderX.row2Array(mCache, mMethod);
	}

}
