package up.light.testng.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import up.light.testng.data.IColumnFilter;
import up.light.testng.data.IRowFilter;

/**
 * <p>
 * 测试方法添加该注解可过滤行和列数据
 * </p>
 * 注意：
 * <ol>
 * <li>测试方法执行次数由行过滤器决定，当所有行均不满足行过滤器时，测试方法不会执行</li>
 * <li>列过滤器只决定取得的数据，当所有列都不满足列过滤器时，参数获取到的均为默认的0值，如int类型得到0，String类型得到null</li>
 * </ol>
 * 
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataFilter {

	/**
	 * 行过滤器
	 * 
	 * @return 行过滤器类型
	 */
	Class<? extends IRowFilter> row() default IRowFilter.class;

	/**
	 * 列过滤器
	 * 
	 * @return 列过滤器类型
	 */
	Class<? extends IColumnFilter> column() default IColumnFilter.class;

}
