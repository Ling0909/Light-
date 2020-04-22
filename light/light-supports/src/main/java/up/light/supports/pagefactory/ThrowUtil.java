package up.light.supports.pagefactory;

import java.lang.reflect.InvocationTargetException;

abstract class ThrowUtil {

	/**
	 * 从反射调用的异常中获取真实的异常
	 * 
	 * @param e
	 * @return
	 */
	static Throwable extractReadableException(Throwable e) {
		if (!RuntimeException.class.equals(e.getClass()) && !InvocationTargetException.class.equals(e.getClass())) {
			return e;
		}
		return extractReadableException(e.getCause());
	}

}
