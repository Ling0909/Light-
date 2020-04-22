package up.light.supports.pagefactory;

import java.lang.reflect.Field;

import up.light.pojo.Locator;
import up.light.supports.JsonParser;

/**
 * 通过Json文件生成Locator对象
 */
public class JsonLocatorFactory implements ILocatorFactory {
	private JsonParser mParser;

	/**
	 * 构造方法
	 * 
	 * @param folder 存放Json文件的文件夹路径
	 */
	public JsonLocatorFactory(String folder) {
		mParser = new JsonParser(folder);
	}

	@Override
	public Locator getLocator(Field field) {
		return mParser.getLocator(field);
	}

}
