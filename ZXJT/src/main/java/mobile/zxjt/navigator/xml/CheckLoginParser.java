package mobile.zxjt.navigator.xml;

import org.xml.sax.Attributes;

import up.light.supports.navigator.ICustomAttributeParser;

/**
 * checkLogin属性解析器
 */
public class CheckLoginParser implements ICustomAttributeParser {

	@Override
	public Object parse(Attributes attrs, int index) {
		return Boolean.valueOf(attrs.getValue(index));
	}

}
