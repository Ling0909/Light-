package up.light.testng.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import up.light.testng.data.IRowMapper;

/**
 * <p>
 * 测试方法中引用数据类型的参数需添加该注解，用于将行数据（Row）自动映射成参数所对应的类型
 * </p>
 * 例如：
 * <code>public void method(@Mapper(MyMapper.class) MyObject a, CustomRecord record)</code>
 * 
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Mapper {

	/**
	 * 行映射器
	 * 
	 * @return 行映射器类型
	 */
	Class<? extends IRowMapper> value();
}
