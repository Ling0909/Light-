package up.light.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 资源工具类
 * 
 * @since 1.0.0
 */
public abstract class ResourceUtil {
	private static final String URL_PROTOCOL_FILE = "file";

	/**
	 * 将URL转成URI
	 * 
	 * @param url 要转换的URL
	 * @return 对应的URI
	 * @throws URISyntaxException 转换失败时抛出此异常
	 */
	public static URI toURI(URL url) throws URISyntaxException {
		return toURI(url.toString());
	}

	/**
	 * 将字符串转成URI
	 * 
	 * @param location 要转换的字符串
	 * @return 对应的URI
	 * @throws URISyntaxException 转换失败时抛出此异常
	 */
	public static URI toURI(String location) throws URISyntaxException {
		return new URI(StringUtil.replace(location, " ", "%20"));
	}

	/**
	 * 获取URL对应的文件（URL协议必须是file）
	 * 
	 * @param resourceUrl 文件URL
	 * @param description 文件描述
	 * @return File对象
	 * @throws FileNotFoundException URL协议不是file时抛出此异常
	 */
	public static File getFile(URL resourceUrl, String description) throws FileNotFoundException {
		Assert.notNull(resourceUrl, "Resource URL must not be null");
		if (!URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol())) {
			throw new FileNotFoundException(description + " cannot be resolved to absolute file path "
					+ "because it does not reside in the file system: " + resourceUrl);
		}
		try {
			return new File(toURI(resourceUrl).getSchemeSpecificPart());
		} catch (URISyntaxException ex) {
			return new File(resourceUrl.getFile());
		}
	}

	/**
	 * 判断URL是否为file协议
	 * 
	 * @param url 要判断的URL
	 * @return 是返回true，否则返回false
	 */
	public static boolean isFileURL(URL url) {
		String protocol = url.getProtocol();
		return URL_PROTOCOL_FILE.equals(protocol);
	}
}
