package up.light.supports.navigator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import up.light.exception.LightInstantiationException;
import up.light.pojo.Locator;
import up.light.supports.JsonParser;
import up.light.util.Assert;
import up.light.util.ClassUtil;
import up.light.util.InstantiateUtil;

public abstract class UiTreeXmlHandler extends DefaultHandler {
	protected static final String DEFAULT_XSD_URI = "https://xinufo.github.io/xsd/light/navigator/navigator.xsd";
	protected static final String DEFAULT_XSD_NAME = "navigator.xsd";

	protected Map<String, EntityResolver> customEntityResolvers = new HashMap<>();
	protected Map<String, ICustomAttributeParser> customAttributeParsers = new HashMap<>();
	protected JsonParser jsonParser;

	private Map<String, Object> instanceCache = new HashMap<>();

	public UiTreeXmlHandler(String folder) {
		jsonParser = new JsonParser(folder);
	}

	/**
	 * 注册自定义实体解析器
	 * 
	 * @param systemId
	 * @param resolver
	 */
	public void registerEntityResolver(String systemId, EntityResolver resolver) {
		if (resolver != null)
			customEntityResolvers.put(systemId, resolver);
	}

	/**
	 * 注册自定义属性解析器
	 * 
	 * @param uri 命名空间URI
	 * @param parser 解析器
	 */
	public void registerParser(String uri, ICustomAttributeParser parser) {
		if (parser != null)
			customAttributeParsers.put(uri, parser);
	}

	public void setCustomEntityResolvers(Map<String, EntityResolver> customEntityResolvers) {
		this.customEntityResolvers = customEntityResolvers;
	}

	public void setCustomAttributeParsers(Map<String, ICustomAttributeParser> customAttributeParsers) {
		this.customAttributeParsers = customAttributeParsers;
	}

	/**
	 * 获取解析好的树
	 * 
	 * @return
	 */
	public abstract IUiTree getTree();

	@Override
	public abstract void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException;

	@Override
	public abstract void endElement(String uri, String localName, String qName) throws SAXException;

	@Override
	public abstract void characters(char[] ch, int start, int length) throws SAXException;

	@Override
	public void error(SAXParseException e) throws SAXException {
		throw e;
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
		if (DEFAULT_XSD_URI.equals(systemId)) {
			return new InputSource(UiTreeXmlHandler.class.getResourceAsStream(DEFAULT_XSD_NAME));
		}
		EntityResolver resolver = customEntityResolvers.get(systemId);
		if (resolver != null) {
			InputSource ins = resolver.resolveEntity(publicId, systemId);
			if (ins != null) {
				return ins;
			}
		}
		throw new SAXException("Can't resolve entity: " + systemId);
	}

	protected INavListener getListener(String name) {
		return getInstance(name, INavListener.class);
	}

	protected IAction getAction(String name) {
		return getInstance(name, IAction.class);
	}

	protected Locator getLocator(String str) {
		int dotIndex = str.indexOf('.');
		Assert.isTrue(dotIndex >= 0, "Invaild format of locator");
		return jsonParser.getLocator(str.substring(0, dotIndex), str.substring(dotIndex + 1));
	}

	@SuppressWarnings("unchecked")
	protected <T> T getInstance(String className, Class<T> superClazz) {
		Object ins = getInstanceFromCache(className);
		if (ins == null) {
			Class<?> clazz = ClassUtil.resolveClassName(className, null);
			try {
				ins = InstantiateUtil.instantiate(clazz, superClazz);
				addInstanceToCache(className, ins);
			} catch (LightInstantiationException e) {
				throw new RuntimeException(e.getCause());
			}
		}
		return (T) ins;
	}

	protected boolean isDefaultNamespace(String uri) {
		return "".equals(uri);
	}

	protected Object parseCustomAttribute(String uri, Attributes attrs, int index) {
		ICustomAttributeParser parser = customAttributeParsers.get(uri);
		if (parser != null) {
			return parser.parse(attrs, index);
		} else {
			System.err.println("WARN: unhandled attribute: " + uri);
			return null;
		}
	}

	private Object getInstanceFromCache(String className) {
		return instanceCache.get(className);
	}

	private void addInstanceToCache(String className, Object ins) {
		instanceCache.put(className, ins);
	}

}
