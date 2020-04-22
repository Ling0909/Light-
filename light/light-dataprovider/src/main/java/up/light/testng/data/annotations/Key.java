package up.light.testng.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试方法基本数据类型需添加该注解，用于从行数据（Row）中自动获取对应类型的值
 * 
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Key {

	/**
	 * 参数所对应的键（行标题）
	 * 
	 * @return 键（行标题）
	 */
	String value();
}
