package up.light.util;

import org.junit.Assert;
import org.junit.Test;

import up.light.exception.LightInstantiationException;

public class InstantiateUtilTest {

	@Test(expected = IllegalArgumentException.class)
	public void testInstantiateClassOfTNullClass() throws LightInstantiationException {
		InstantiateUtil.instantiate(null);
	}

	@Test
	public void testInstantiateClassOfT() throws LightInstantiationException {
		String s = InstantiateUtil.instantiate(String.class);
		Assert.assertNotNull(s);
	}

	@Test
	public void testInstantiateClassOfQClassOfT() throws LightInstantiationException {
		Object s = InstantiateUtil.instantiate(String.class, Object.class);
		Assert.assertNotNull(s);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInstantiateClassOfQClassOfT2() throws LightInstantiationException {
		InstantiateUtil.instantiate(String.class, Thread.class);
	}

}
