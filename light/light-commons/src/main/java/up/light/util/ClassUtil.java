package up.light.util;

/**
 * Class工具类
 * 
 * @since 1.0.0
 */
public abstract class ClassUtil {
	private static final char PACKAGE_SEPARATOR = '.';
	private static final char INNER_CLASS_SEPARATOR = '$';
	private static final char PATH_SEPARATOR = '/';

	/**
	 * 根据类名获取Class对象，支持统一风格的内部类语法（例如：“a.B$C”可写成“a.B.C”）
	 * 
	 * @param name 类名
	 * @param classLoader 类加载器，使用默认加载器可为null
	 * @return 对应的Class对象
	 * @throws ClassNotFoundException 找不到类
	 * @throws LinkageError 类加载失败
	 */
	public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
		Assert.notNull(name, "Name must not be null");

		ClassLoader vClassLoader = classLoader;
		if (vClassLoader == null) {
			vClassLoader = getDefaultClassLoader();
		}
		try {
			return (vClassLoader != null ? vClassLoader.loadClass(name) : Class.forName(name));
		} catch (ClassNotFoundException ex) {
			int vLastDotIndex = name.lastIndexOf(PACKAGE_SEPARATOR);
			if (vLastDotIndex != -1) {
				String vInnerClassName = name.substring(0, vLastDotIndex) + INNER_CLASS_SEPARATOR
						+ name.substring(vLastDotIndex + 1);
				try {
					return (vClassLoader != null ? vClassLoader.loadClass(vInnerClassName)
							: Class.forName(vInnerClassName));
				} catch (ClassNotFoundException ex2) {
				}
			}
			throw ex;
		}
	}

	/**
	 * 等同于{@code forName}，只是加载出错时抛出的异常不同
	 * 
	 * @param className 类名
	 * @param classLoader 类加载器，使用默认加载器可为null
	 * @return 对应的Class对象
	 * @throws IllegalArgumentException 类加载失败
	 */
	public static Class<?> resolveClassName(String className, ClassLoader classLoader) throws IllegalArgumentException {
		try {
			return forName(className, classLoader);
		} catch (ClassNotFoundException ex) {
			throw new IllegalArgumentException("Cannot find class [" + className + "]", ex);
		} catch (LinkageError ex) {
			throw new IllegalArgumentException(
					"Error loading class [" + className + "]: problem with class file or dependent class", ex);
		}
	}

	/**
	 * 获取默认类加载器，优先使用当前线程的上下文类加载器，若没有则使用加载ClassUtil类的加载器
	 * 
	 * @return 默认类加载器，当系统类加载器不可访问时返回null
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
		}

		if (cl == null) {
			cl = ClassUtil.class.getClassLoader();
			if (cl == null) {
				try {
					cl = ClassLoader.getSystemClassLoader();
				} catch (Throwable ex) {
				}
			}
		}
		return cl;
	}

	/**
	 * 获取指定类的包名所代表的路径（例如"a.b.C" -> "a/b"），
	 * 
	 * @param clazz 类对象
	 * @return 包名所代表的路径，clazz为null或默认包下的类将返回""
	 */
	public static String classPackageAsResourcePath(Class<?> clazz) {
		if (clazz == null) {
			return "";
		}
		String vClassName = clazz.getName();
		int vPackageEndIndex = vClassName.lastIndexOf(PACKAGE_SEPARATOR);
		if (vPackageEndIndex == -1) {
			return "";
		}
		String vPackageName = vClassName.substring(0, vPackageEndIndex);
		return vPackageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
	}
}
