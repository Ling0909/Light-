package up.light.testng.data;

import up.light.testng.data.defaults.DefaultDataReader;
import up.light.testng.data.defaults.DefaultGroupResolver;
import up.light.testng.data.reader.IDataReader;
import up.light.testng.data.reader.IGroupResolver;

/**
 * 设置DataProviderX所需的IDataReader、IGroupResolver等，需调用ITestContext#setAttribute方法加入到TestNG上下文中
 */
public class DataProviderConfig {
	public static final String KEY_CONTEXT_CONFIG = "TestNG_Config";
	private IDataReader mReader;
	private IGroupResolver mGroupResovler;
	private IRowFilter mRowFilter;
	private IColumnFilter mColumnFilter;

	public static DataProviderConfig buildDefault(String excel) {
		DataProviderConfig vConfig = new DataProviderConfig();
		vConfig.mReader = new DefaultDataReader(excel);
		vConfig.mGroupResovler = new DefaultGroupResolver();
		return vConfig;
	}

	public IDataReader getReader() {
		return mReader;
	}

	public void setReader(IDataReader reader) {
		mReader = reader;
	}

	public IGroupResolver getGroupResovler() {
		return mGroupResovler;
	}

	public void setGroupResovler(IGroupResolver groupResovler) {
		mGroupResovler = groupResovler;
	}

	public IRowFilter getRowFilter() {
		return mRowFilter;
	}

	public void setRowFilter(IRowFilter rowFilter) {
		mRowFilter = rowFilter;
	}

	public IColumnFilter getColumnFilter() {
		return mColumnFilter;
	}

	public void setColumnFilter(IColumnFilter columnFilter) {
		mColumnFilter = columnFilter;
	}

}
