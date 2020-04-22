package up.light.testng.data.reader;

import up.light.testng.data.IColumnFilter;
import up.light.testng.data.IRow;
import up.light.testng.data.IRowFilter;

/**
 * 数据读取
 * 
 * @since 1.0.0
 */
public interface IDataReader {

	/**
	 * 设置数据源
	 * 
	 * @param dataSource 数据源
	 */
	void setDataSource(String dataSource);

	/**
	 * 是否有未读数据
	 * 
	 * @return 是返回true，否返回false
	 */
	boolean hasNext();

	/**
	 * 切换数据分组，例如Excel切换sheet，数据库切换表
	 * 
	 * @param group 分组名
	 */
	void changeGroup(String group);

	/**
	 * 读取一行数据
	 * 
	 * @param filter 列过滤器
	 * @return 已过滤列的一行数据（filter为null表示不过滤）
	 */
	IRow readRow(IColumnFilter filter);

	/**
	 * 读取全部数据
	 * 
	 * @param rowFilter 行过滤器
	 * @param columnFilter 列过滤器
	 * @return 已过滤列的所有行数据（filter为null表示不过滤）
	 */
	IRow[] readAll(IRowFilter rowFilter, IColumnFilter columnFilter);

	/**
	 * 关闭
	 */
	void close();

}
