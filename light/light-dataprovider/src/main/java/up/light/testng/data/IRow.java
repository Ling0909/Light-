package up.light.testng.data;

import java.util.Date;

/**
 * 一行数据的封装对象
 * 
 * @since 1.0.0
 */
public interface IRow {

	/**
	 * 获取行索引，从1开始
	 * 
	 * @return 行索引
	 */
	int getIndex();

	/**
	 * 获取所有行标题
	 * 
	 * @return 所有行标题
	 */
	String[] getKeys();

	/**
	 * 获取值
	 * 
	 * @param key 键（行标题）
	 * @return 对应的值，未找到对应值返回false
	 */
	boolean getBoolean(String key);

	/**
	 * 获取值
	 * 
	 * @param key
	 * @return 对应的值，未找到对应值返回0
	 */
	byte getbyte(String key);

	/**
	 * 获取值
	 * 
	 * @param key 键（行标题）
	 * @return 对应的值，未找到对应值返回0
	 */
	char getChar(String key);

	/**
	 * 获取值
	 * 
	 * @param key 键（行标题）
	 * @return 对应的值，未找到对应值返回0
	 */
	short getShort(String key);

	/**
	 * 获取值
	 * 
	 * @param key 键（行标题）
	 * @return 对应的值，未找到对应值返回0
	 */
	int getInt(String key);

	/**
	 * 获取值
	 * 
	 * @param key 键（行标题）
	 * @return 对应的值，未找到对应值返回0
	 */
	long getLong(String key);

	/**
	 * 获取值
	 * 
	 * @param key 键（行标题）
	 * @return 对应的值，未找到对应值返回0
	 */
	float getFloat(String key);

	/**
	 * 获取值
	 * 
	 * @param key 键（行标题）
	 * @return 对应的值，未找到对应值返回0
	 */
	double getDouble(String key);

	/**
	 * 获取值
	 * 
	 * @param key 键（行标题）
	 * @return 对应的值，未找到对应值返回null
	 */
	String getString(String key);

	/**
	 * 获取值
	 * 
	 * @param key 键（行标题）
	 * @return 对应的值，未找到对应值返回null
	 */
	Date getDate(String key);

}
