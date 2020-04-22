package up.light.util;

import org.junit.Assert;
import org.junit.Test;

public class ClassUtilTest {

	@Test
	public void testForName() {
		ClassLoader loader = ClassUtilTest.class.getClassLoader();
		Class<?> clazz = null, clazz1 = null;

		// should success
		try {
			clazz = ClassUtil.forName("java.lang.Object", null);
			clazz1 = ClassUtil.forName("java.lang.Object", loader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Assert.assertEquals(Object.class, clazz);
		Assert.assertEquals(Object.class, clazz1);

		// nested class, should success
		try {
			clazz = ClassUtil.forName("java.lang.Thread.State", null);
			clazz1 = ClassUtil.forName("java.lang.Thread.State", loader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Assert.assertEquals(Thread.State.class, clazz);
		Assert.assertEquals(Thread.State.class, clazz1);

		// should fail
		try {
			ClassUtil.forName("a.b.C", null);
		} catch (Exception e) {
			Assert.assertEquals(ClassNotFoundException.class, e.getClass());
		}

		// should fail
		try {
			ClassUtil.forName("a.b.C", loader);
		} catch (Exception e) {
			Assert.assertEquals(ClassNotFoundException.class, e.getClass());
		}
	}

	@Test
	public void testResolveClassName() {
		ClassLoader loader = ClassUtilTest.class.getClassLoader();
		Class<?> clazz = null, clazz1 = null;

		// should success
		clazz = ClassUtil.resolveClassName("java.lang.Object", null);
		clazz1 = ClassUtil.resolveClassName("java.lang.Object", loader);
		Assert.assertEquals(Object.class, clazz);
		Assert.assertEquals(Object.class, clazz1);

		// nested class, should success
		clazz = ClassUtil.resolveClassName("java.lang.Thread.State", null);
		clazz1 = ClassUtil.resolveClassName("java.lang.Thread.State", loader);
		Assert.assertEquals(Thread.State.class, clazz);
		Assert.assertEquals(Thread.State.class, clazz1);

		// should fail
		try {
			ClassUtil.resolveClassName("a.b.C", null);
		} catch (Exception e) {
			Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}

		// should fail
		try {
			ClassUtil.resolveClassName("a.b.C", loader);
		} catch (Exception e) {
			Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	@Test
	public void testGetDefaultClassLoader() {
		ClassLoader loader = ClassUtil.getDefaultClassLoader();
		Assert.assertNotNull(loader);
		Assert.assertTrue(ClassLoader.class.isAssignableFrom(loader.getClass()));
	}

	@Test
	public void testClassPackageAsResourcePath() {
		String a = ClassUtil.classPackageAsResourcePath(Object.class);
		Assert.assertEquals("java/lang", a);

		a = ClassUtil.classPackageAsResourcePath(Thread.State.class);
		Assert.assertEquals("java/lang", a);

		a = ClassUtil.classPackageAsResourcePath(null);
		Assert.assertEquals("", a);
	}

}
