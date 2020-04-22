package up.light.testng.data;

import up.light.testng.data.annotations.Mapper;

/**
 * 行映射器
 * 
 * @since 1.0.0
 * @see Mapper
 */
public interface IRowMapper {

	/**
	 * 将一行数据(Row)映射成指定对象
	 * 
	 * @param row 行数据
	 * @return 映射的对象
	 */
	Object map(IRow row);

}
