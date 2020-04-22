package up.light.supports.pagefactory.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import up.light.Platforms;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(FindContainer.class)
public @interface Find {
	/**
	 * 对应的平台
	 * 
	 * @return
	 */
	Platforms platform();

	/**
	 * 要查找元素所在的上下文
	 * 
	 * @return
	 */
	String context();

	/**
	 * 定位信息
	 * 
	 * @return
	 */
	String locator();

	/**
	 * 是否缓存找到的元素，默认为false
	 * 
	 * @return
	 */
	boolean cache() default false;
}
