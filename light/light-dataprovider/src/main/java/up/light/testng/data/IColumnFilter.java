package up.light.testng.data;

/**
 * 列过滤器
 * 
 * @since 1.0.0
 */
public interface IColumnFilter {

	/**
	 * 过滤要读取的列
	 * 
	 * @param columnName 列名
	 * @param index 列索引
	 * @return 接受返回true， 否则返回false
	 */
	boolean accept(String columnName, int index);

}
