package up.light.supports.pagefactory;

import up.light.pojo.ByBean;
import up.light.util.Assert;

public abstract class ByBeanFactory {
	private static final String SEPARATE_STR = "://";

	/**
	 * 通过字符串生成ByBean对象，字符串格式需为"定位方式://定位信息"，例如 id://kw
	 * 
	 * @param str 定位信息字符串
	 * @return ByBean对象
	 */
	public static ByBean getByBean(String str) {
		int index = str.indexOf(SEPARATE_STR);
		Assert.isTrue(index >= 0, "Invalid string of ByBean");
		return new ByBean(str.substring(0, index), str.substring(index + SEPARATE_STR.length()));
	}

}
