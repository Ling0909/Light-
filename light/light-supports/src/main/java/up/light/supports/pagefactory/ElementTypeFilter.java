package up.light.supports.pagefactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import up.light.util.ReflectUtil.FieldFilter;

/**
 * 字段过滤器，过滤指定类型的字段
 */
class ElementTypeFilter implements FieldFilter {
	private Class<?> mType;

	@Override
	public boolean matches(Field field) {
		if (mType.isAssignableFrom(field.getType()) || isDecoratableGenericType(field)) {
			return true;
		}
		return false;
	}

	private boolean isDecoratableGenericType(Field field) {
		Class<?> clazz = field.getType();
		if (List.class != clazz && DynamicLocator.class != clazz) {
			return false;
		}

		Type genericType = field.getGenericType();
		if (!(genericType instanceof ParameterizedType)) {
			// 不是泛型
			return false;
		}

		Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
		if (mType.equals(listType)) {
			// 泛型类型符合
			return true;
		}

		// 泛型类型不符
		return false;
	}

	public void setType(Class<?> type) {
		this.mType = type;
	}

}
