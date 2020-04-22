package up.light.supports.navigator;

import org.xml.sax.Attributes;

/**
 * XML自定义属性解析器
 */
public interface ICustomAttributeParser {

	/**
	 * 解析自定义属性
	 * 
	 * @param attrs 属性对象
	 * @param index 属性索引
	 * @return 属性值
	 */
	Object parse(Attributes attrs, int index);

}
