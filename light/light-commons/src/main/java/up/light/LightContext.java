package up.light;

import java.util.HashMap;
import java.util.Map;

import up.light.folder.FolderTypes;
import up.light.util.Assert;

/**
 * 核心配置类
 * 
 * @since 1.0.0
 */
public abstract class LightContext {
	private static Platforms platform;
	private static boolean multiple = true;
	private static Map<String, String> folderPaths = new HashMap<>();
	private static Map<String, Object> attributes = new HashMap<>();
	private static String rootPath;

	static {
		for (FolderTypes vType : FolderTypes.values()) {
			folderPaths.put(vType.getFolderType(), vType.getDefault());
		}
	}

	/**
	 * 获取平台名称
	 * 
	 * @return 平台名称
	 */
	public static Platforms getPlatform() {
		return platform;
	}

	/**
	 * 设置平台名称
	 * 
	 * @param platform 平台名称
	 */
	public static void setPlatform(String platform) {
		Platforms p = Platforms.fromString(platform);
		Assert.notNull(p, "Unsupported platform: " + platform);
		LightContext.platform = p;
	}

	/**
	 * 是否多重定位
	 * 
	 * @return
	 */
	public static boolean isMultiple() {
		return multiple;
	}

	/**
	 * 设置多重定位
	 * 
	 * @param multiple 是否多重定位
	 */
	public static void setMultiple(boolean multiple) {
		LightContext.multiple = multiple;
	}

	/**
	 * 获取根目录路径，默认为"当前目录/平台名/"，例如 C:/test/android/
	 * 
	 * @return 根目录路径
	 */
	public static String getRootPath() {
		if (rootPath == null) {
			rootPath = LightContext.class.getResource("/").getPath() + getPlatform().name().toLowerCase() + "/";
		}
		return rootPath;
	}

	/**
	 * 获取文件夹路径
	 * 
	 * @param folderType 文件夹类型
	 * @return 文件夹路径，以"/"结束
	 */
	public static String getFolderPath(String folderType) {
		Assert.notNull(folderType, "FolderType must not be null");
		String vPath = folderPaths.get(folderType);
		Assert.notNull(vPath, "Can't get folder's path with type [" + folderType + "]");
		return getRootPath() + vPath + "/";
	}

	/**
	 * 获取文件夹路径
	 * 
	 * @param folderType 文件夹类型
	 * @return 文件夹路径，以"/"结束
	 */
	public static String getFolderPath(FolderTypes folderType) {
		Assert.notNull(folderType, "FolderType must not be null");
		return getFolderPath(folderType.getFolderType());
	}

	/**
	 * 设置文件夹路径
	 * 
	 * @param type 文件夹类型
	 * @param path 文件夹路径
	 */
	public static void setFolderPath(String type, String path) {
		folderPaths.put(type, path);
	}

	/**
	 * 设置自定义属性
	 * 
	 * @param name 属性名
	 * @param value 属性值
	 */
	public static void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	/**
	 * 获取自定义属性
	 * 
	 * @param name 属性名
	 * @return 属性值，未找到对应属性返回null
	 */
	public static Object getAttribute(String name) {
		return attributes.get(name);
	}

}
