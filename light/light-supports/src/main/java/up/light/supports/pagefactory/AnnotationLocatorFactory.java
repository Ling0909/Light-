package up.light.supports.pagefactory;

import java.lang.reflect.Field;
import java.util.Arrays;

import up.light.LightContext;
import up.light.Platforms;
import up.light.pojo.Locator;
import up.light.supports.pagefactory.annotations.Find;

/**
 * 根据注解生成Locator对象
 */
public class AnnotationLocatorFactory implements ILocatorFactory {
	private Platforms mPlatform;

	@Override
	public Locator getLocator(Field field) {
		Find[] findArr = field.getAnnotationsByType(Find.class);
		for (Find f : findArr) {
			if (f.platform() == getPlatform()) {
				return new Locator(f.context(), Arrays.asList(ByBeanFactory.getByBean(f.locator())), f.cache());
			}
		}
		return null;
	}

	/*
	 * 获取当前平台
	 */
	private Platforms getPlatform() {
		if (mPlatform == null) {
			mPlatform = LightContext.getPlatform();
		}
		return mPlatform;
	}

}
