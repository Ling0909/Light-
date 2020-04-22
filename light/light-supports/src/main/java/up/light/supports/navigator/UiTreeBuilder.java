package up.light.supports.navigator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;

import up.light.Constants;
import up.light.IElementFinder;
import up.light.supports.navigator.impl.UiTreeXmlHandlerImpl;

/**
 * IUiTree的Xml解析器，默认采用Json存储Locator
 */
public abstract class UiTreeBuilder {
	private static final Log logger = LogFactory.getLog(UiTreeBuilder.class);

	/**
	 * 解析xml文件得到IUiTree
	 * 
	 * @param xml xml文件路径
	 * @param folder 存放json的文件夹路径
	 * @param finder 元素查找器
	 * @return
	 */
	public static IUiTree build(String xml, String folder, IElementFinder<?> finder) {
		return build(xml, new UiTreeXmlHandlerImpl(folder, finder));
	}

	/**
	 * 解析xml文件得到IUiTree
	 * 
	 * @param xml xml文件路径
	 * @param folder 存放json的文件夹路径
	 * @param finder 元素查找器
	 * @param customEntityResolvers 自定义实体解析器
	 * @param customAttributeParsers 自定义属性解析器
	 * @return
	 */
	public static IUiTree build(String xml, String folder, IElementFinder<?> finder,
			Map<String, EntityResolver> customEntityResolvers,
			Map<String, ICustomAttributeParser> customAttributeParsers) {
		UiTreeXmlHandler handler = new UiTreeXmlHandlerImpl(folder, finder);
		handler.setCustomEntityResolvers(customEntityResolvers);
		handler.setCustomAttributeParsers(customAttributeParsers);
		return build(xml, handler);
	}

	/**
	 * 解析xml文件得到IUiTree
	 * 
	 * @param xml xml文件路径
	 * @param handler 自定义handler
	 * @return
	 */
	public static IUiTree build(String xml, UiTreeXmlHandler handler) {
		if (logger.isInfoEnabled())
			logger.info(Constants.LOG_TAG + "parse xml of IUiTree: " + xml);

		SAXParserFactory vFactory = SAXParserFactory.newInstance();
		// 开启命名空间支持
		vFactory.setNamespaceAware(true);
		vFactory.setValidating(true);

		try (InputStream vIns = new FileInputStream(xml)) {
			// 开启Schema验证
			vFactory.setFeature("http://apache.org/xml/features/validation/schema", true);
			// 解析XML
			SAXParser vParser = vFactory.newSAXParser();
			vParser.parse(vIns, handler);
			// 获取解析结果
			return handler.getTree();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
