package mobile.zxjt.page.module.popup;

import java.lang.reflect.Field;

import up.light.pojo.Locator;
import up.light.supports.JsonParser;
import up.light.supports.pagefactory.ILocatorFactory;

class JsonLocatorFactory2 implements ILocatorFactory {
	private JsonParser mParser;
	private String file;

	public JsonLocatorFactory2(String folder, String file) {
		mParser = new JsonParser(folder);
		this.file = file;
	}

	@Override
	public Locator getLocator(Field field) {
		return mParser.getLocator(file, field.getName());
	}

}
