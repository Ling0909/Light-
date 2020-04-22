package up.light.testng.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 在测试方法参数列表中添加该类型参数以记录自定义信息
 * 
 * @since 1.0.0
 */
public class CustomRecord {
	private Map<String, String> mContainer = new HashMap<>();

	/**
	 * 添加记录
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void put(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException("Key must not be null");
		}
		mContainer.put(key, value);
	}

	/**
	 * 获取记录
	 * 
	 * @param key 键
	 * @return 对应的值，未找到对应的值返回null
	 */
	public String get(String key) {
		return mContainer.get(key);
	}

	@Override
	public String toString() {
		return "CustomRecord [" + mContainer + "]";
	}

}
