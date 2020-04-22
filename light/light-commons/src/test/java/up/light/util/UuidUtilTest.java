package up.light.util;

import org.junit.Assert;
import org.junit.Test;

public class UuidUtilTest {

	@Test
	public void testGetUuid() {
		String s1 = UuidUtil.getUuid();
		String s2 = UuidUtil.getUuid();
		Assert.assertEquals(36, s1.length());
		Assert.assertEquals(36, s2.length());
		Assert.assertNotEquals(s1, s2);
	}

	@Test
	public void testGetShortUuid() {
		String s1 = UuidUtil.getShortUuid();
		String s2 = UuidUtil.getShortUuid();
		Assert.assertEquals(8, s1.length());
		Assert.assertEquals(8, s2.length());
		Assert.assertNotEquals(s1, s2);
	}

}
