package up.light.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 反射工具类
 * 
 * @since 1.0.0
 */
public abstract class ReflectUtil {

	/**
	 * 在指定类的所有字段（包含父类）上执行回调方法
	 * 
	 * @param clazz 要处理字段的类
	 * @param fc 回调方法
	 * @param ff 字段过滤器
	 */
	public static void doWithFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) {
		// Keep backing up the inheritance hierarchy.
		Class<?> targetClass = clazz;
		do {
			Field[] fields = getDeclaredFields(targetClass);
			for (Field field : fields) {
				if (ff != null && !ff.matches(field)) {
					continue;
				}
				try {
					fc.doWith(field);
				} catch (IllegalAccessException ex) {
					throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
				}
			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);
	}

	/**
	 * 在指定类的所有字段（包含父类）上执行回调方法，不过滤字段
	 * 
	 * @param clazz 要处理字段的类
	 * @param fc 回调方法
	 */
	public static void doWithFields(Class<?> clazz, FieldCallback fc) {
		doWithFields(clazz, fc, null);
	}

	/**
	 * 在指定类的所有字段（不包含父类）上执行回调方法
	 * 
	 * @param clazz 要处理字段的类
	 * @param fc 回调方法
	 * @param ff 字段过滤器
	 */
	public static void doWithLocalFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) {
		for (Field field : getDeclaredFields(clazz)) {
			if (ff != null && !ff.matches(field)) {
				continue;
			}
			try {
				fc.doWith(field);
			} catch (IllegalAccessException ex) {
				throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
			}
		}
	}

	/**
	 * 在指定类的所有字段（不包含父类）上执行回调方法，不过滤字段
	 * 
	 * @param clazz 要处理字段的类
	 * @param fc 回调方法
	 */
	public static void doWithLocalFields(Class<?> clazz, FieldCallback fc) {
		doWithLocalFields(clazz, fc, null);
	}

	/**
	 * 在类（包含其父类）中查找指定名称的字段
	 * 
	 * @param clazz 要查找的类
	 * @param name 字段名
	 * @return 找到返回字段对象，否则返回null
	 */
	public static Field findField(Class<?> clazz, String name) {
		return findField(clazz, name, null);
	}

	/**
	 * 在类（包含其父类）中查找指定名称或指定类型的字段
	 * 
	 * @param clazz 要查找的类
	 * @param name 字段名
	 * @param type 字段类型
	 * @return 找到返回字段对象，否则返回null
	 */
	public static Field findField(Class<?> clazz, String name, Class<?> type) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.isTrue(name != null || type != null, "Either name or type of the field must be specified");
		Class<?> searchType = clazz;
		while (Object.class != searchType && searchType != null) {
			Field[] fields = getDeclaredFields(searchType);
			for (Field field : fields) {
				if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * 使指定的字段可以访问
	 * 
	 * @param field 待修改的字段
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
				|| Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 获取字段的值
	 * 
	 * @param field 待处理字段
	 * @param target 字段所在的实例对象
	 * @return 字段的值
	 */
	public static Object getField(Field field, Object target) {
		try {
			return field.get(target);
		} catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException(
					"Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	/**
	 * 设置字段的值
	 * 
	 * @param field 待处理字段
	 * @param target 字段所在的实例对象
	 * @param value 要设置的值
	 */
	public static void setField(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		} catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException(
					"Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	private static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access field: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	private static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	private static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/*
	 * 获取指定类的所有字段
	 */
	private static Field[] getDeclaredFields(Class<?> clazz) {
		return clazz.getDeclaredFields();
	}

	/**
	 * 字段回调接口
	 */
	public interface FieldCallback {
		/**
		 * 回调方法
		 * 
		 * @param field 正在处理的字段
		 * @throws IllegalArgumentException
		 * @throws IllegalAccessException
		 */
		void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
	}

	/**
	 * 字段过滤接口
	 */
	public interface FieldFilter {
		/**
		 * 是否过滤字段
		 * 
		 * @param field 待检查的字段
		 * @return 要过滤返回false，否则返回true
		 */
		boolean matches(Field field);
	}
}
