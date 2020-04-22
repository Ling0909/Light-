package up.light.util;

import org.junit.Test;

public class AssertTest {

	@Test
	public void testStateBoolean() {
		Assert.state(true);
		try {
			Assert.state(false);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalStateException.class, e.getClass());
		}
	}

	@Test
	public void testStateBooleanString() {
		Assert.state(true, null);
		Assert.state(true, "test");
		try {
			Assert.state(false, null);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalStateException.class, e.getClass());
		}
		try {
			Assert.state(false, "test");
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalStateException.class, e.getClass());
		}
	}

	@Test
	public void testIsTrue() {
		Assert.isTrue(true, null);
		Assert.isTrue(true, "test");
		try {
			Assert.isTrue(false, null);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.isTrue(false, "test");
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	@Test
	public void testIsNull() {
		Object o = new Object();
		Assert.isNull(null, null);
		Assert.isNull(null, "test");
		try {
			Assert.isNull(o, null);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.isNull(o, "test");
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	@Test
	public void testNotNull() {
		Object o = new Object();
		Assert.notNull(o, null);
		Assert.notNull(o, "test");
		try {
			Assert.notNull(null, null);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.notNull(null, "test");
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	@Test
	public void testIsAssignable() {
		Assert.isAssignable(Object.class, String.class, null);
		Assert.isAssignable(Object.class, String.class, "test");
		try {
			Assert.isAssignable(Thread.class, String.class, null);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.isAssignable(Thread.class, String.class, "test");
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	@Test
	public void testHasLength() {
		Assert.hasLength("test", null);
		Assert.hasLength("test", "test");
		try {
			Assert.hasLength(null, null);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.hasLength("", null);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.hasLength(null, "test");
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.hasLength("", "test");
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	@Test
	public void testhasText() {
		Assert.hasText("test", null);
		Assert.hasText("test", "test");
		try {
			Assert.hasText(null, null);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.hasText(null, "test");
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.hasText("", null);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.hasText("", "test");
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.hasText(" 	", null);
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
		try {
			Assert.hasText(" 	", "test");
		} catch (Exception e) {
			org.junit.Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

}
