package up.light.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;

import up.light.util.ReflectUtil.FieldCallback;
import up.light.util.ReflectUtil.FieldFilter;

public class ReflectUtilTest {

	@Test
	public void testDoWithFieldsClassOfQFieldCallbackFieldFilter() {
		FieldCallbackImpl cb = new FieldCallbackImpl();
		FieldFilterImpl filter = new FieldFilterImpl();
		ReflectUtil.doWithFields(B.class, cb, filter);
		Assert.assertEquals(2, cb.i);
	}

	@Test
	public void testDoWithFieldsClassOfQFieldCallback() {
		FieldCallbackImpl cb = new FieldCallbackImpl();
		ReflectUtil.doWithFields(B.class, cb);
		Assert.assertEquals(8, cb.i);
	}

	@Test
	public void testDoWithLocalFieldsClassOfQFieldCallbackFieldFilter() {
		FieldCallbackImpl cb = new FieldCallbackImpl();
		FieldFilterImpl filter = new FieldFilterImpl();
		ReflectUtil.doWithLocalFields(B.class, cb, filter);
		Assert.assertEquals(1, cb.i);
	}

	@Test
	public void testDoWithLocalFieldsClassOfQFieldCallback() {
		FieldCallbackImpl cb = new FieldCallbackImpl();
		ReflectUtil.doWithLocalFields(B.class, cb);
		Assert.assertEquals(4, cb.i);
	}

	@Test
	public void testFindFieldClassOfQString() {
		Assert.assertNotNull(ReflectUtil.findField(B.class, "bi"));
		Assert.assertNotNull(ReflectUtil.findField(B.class, "ai"));
		Assert.assertNull(ReflectUtil.findField(B.class, "xxx"));
	}

	@Test
	public void testFindFieldClassOfQStringClassOfQ() {
		Assert.assertNotNull(ReflectUtil.findField(B.class, "bi", null));
		Assert.assertNotNull(ReflectUtil.findField(B.class, null, int.class));
		Assert.assertNotNull(ReflectUtil.findField(B.class, "bi", int.class));
		Assert.assertNull(ReflectUtil.findField(B.class, "bi", char.class));
		Assert.assertNull(ReflectUtil.findField(B.class, "xxx"));
		Assert.assertNull(ReflectUtil.findField(B.class, null, byte.class));
	}

	@Test
	public void testMakeAccessible() {
		Field f = ReflectUtil.findField(B.class, "bi", int.class);
		ReflectUtil.makeAccessible(f);
	}

	@Test
	public void testGetField() {
		B b = new B();
		Field f = ReflectUtil.findField(B.class, "bi", int.class);
		ReflectUtil.makeAccessible(f);
		Assert.assertEquals(10, ReflectUtil.getField(f, b));
	}

	@Test(expected = IllegalStateException.class)
	public void testGetFieldUnAccess() {
		B b = new B();
		Field f = ReflectUtil.findField(B.class, "bi", int.class);
		Assert.assertEquals(10, ReflectUtil.getField(f, b));
	}

	@Test
	public void testSetField() {
		B b = new B();
		Field f = ReflectUtil.findField(B.class, "ai", int.class);
		ReflectUtil.makeAccessible(f);
		ReflectUtil.setField(f, b, 20);
		Assert.assertEquals(20, b.getAi());
	}

}

class FieldCallbackImpl implements FieldCallback {
	public int i;

	@Override
	public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
		++i;
	}

}

class FieldFilterImpl implements FieldFilter {

	@Override
	public boolean matches(Field field) {
		return Modifier.isStatic(field.getModifiers());
	}

}

class A {
	private int ai;
	char ac;
	public String as;
	static int asi;

	public int getAi() {
		return ai;
	}

}

class B extends A {
	private int bi = 10;
	char bc;
	public String bs;
	static int bsi;

	public int getBi() {
		return bi;
	}
}
