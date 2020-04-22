package up.light.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

import up.light.io.IResource;

/**
 * Properties工具类
 * 
 * @since 1.0.0
 */
public abstract class PropertiesUtil {
	private static final String XML_FILE_EXTENSION = ".xml";

	/**
	 * 使用默认类加载器加载所有properties，对于多个同名资源将进行合并处理
	 * 
	 * @param resourceName 相对于class path的资源名
	 * @return Properties对象
	 * @throws IOException 加载失败
	 */
	public static Properties loadAllProperties(String resourceName) throws IOException {
		return loadAllProperties(resourceName, null);
	}

	/**
	 * 使用指定类加载器加载所有properties，对于多个同名资源将进行合并处理
	 * 
	 * @param resourceName 相对于class path的资源名
	 * @param classLoader 要使用的类加载器
	 * @return Properties对象
	 * @throws IOException 加载失败
	 */
	public static Properties loadAllProperties(String resourceName, ClassLoader classLoader) throws IOException {
		Assert.notNull(resourceName, "Resource name must not be null");
		ClassLoader vClassLoader = classLoader;
		if (vClassLoader == null) {
			vClassLoader = ClassUtil.getDefaultClassLoader();
		}

		Enumeration<URL> vUrls = (vClassLoader != null ? vClassLoader.getResources(resourceName)
				: ClassLoader.getSystemResources(resourceName));
		Properties vProps = new Properties();

		while (vUrls.hasMoreElements()) {
			URL vUrl = vUrls.nextElement();
			URLConnection vCon = vUrl.openConnection();
			vCon.setUseCaches(false);
			InputStream vIns = vCon.getInputStream();
			try {
				if (resourceName.endsWith(XML_FILE_EXTENSION)) {
					vProps.loadFromXML(vIns);
				} else {
					vProps.load(vIns);
				}
			} finally {
				vIns.close();
			}
		}
		return vProps;
	}

	/**
	 * 从指定的资源加载Properties
	 * 
	 * @param resource 资源名
	 * @return Properties对象
	 * @throws IOException 加载失败
	 */
	public static Properties loadProperties(IResource resource) throws IOException {
		Properties props = new Properties();
		fillProperties(props, resource);
		return props;
	}

	/**
	 * 将指定资源的内容读入Properties
	 * 
	 * @param props 存放读入内容的Properties
	 * @param resource 资源名
	 * @throws IOException 加载失败
	 */
	public static void fillProperties(Properties props, IResource resource) throws IOException {
		InputStream vIn = resource.getInputStream();
		try {
			String vFilename = resource.getFilename();
			if (vFilename != null && vFilename.endsWith(XML_FILE_EXTENSION)) {
				props.loadFromXML(vIn);
			} else {
				props.load(vIn);
			}
		} finally {
			vIn.close();
		}
	}
}
