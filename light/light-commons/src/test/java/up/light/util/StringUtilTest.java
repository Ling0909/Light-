package up.light.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testHasLengthCharSequence() {
		StringBuilder sb = new StringBuilder("123");
		Assert.assertTrue(StringUtil.hasLength(sb));

		sb = new StringBuilder(" ");
		Assert.assertTrue(StringUtil.hasLength(sb));

		sb = new StringBuilder();
		Assert.assertFalse(StringUtil.hasLength(sb));

		sb = new StringBuilder("");
		Assert.assertFalse(StringUtil.hasLength(sb));

		sb = null;
		Assert.assertFalse(StringUtil.hasLength(sb));
	}

	@Test
	public void testHasLengthString() {
		Assert.assertTrue(StringUtil.hasLength(" "));
		Assert.assertFalse(StringUtil.hasLength(""));
		Assert.assertFalse(StringUtil.hasLength(null));
	}

	@Test
	public void testHasTextCharSequence() {
		StringBuilder sb = new StringBuilder(" ");
		Assert.assertFalse(StringUtil.hasText(sb));

		sb = new StringBuilder("");
		Assert.assertFalse(StringUtil.hasText(sb));

		sb = null;
		Assert.assertFalse(StringUtil.hasText(sb));

		sb = new StringBuilder("123");
		Assert.assertTrue(StringUtil.hasText(sb));
	}

	@Test
	public void testHasTextString() {
		Assert.assertTrue(StringUtil.hasText("123"));
		Assert.assertFalse(StringUtil.hasText(""));
		Assert.assertFalse(StringUtil.hasText(null));
		Assert.assertFalse(StringUtil.hasText(" "));
		Assert.assertFalse(StringUtil.hasText(" 	"));
	}

	@Test
	public void testReplace() {
		String old = "1   2 ";
		String a = StringUtil.replace(old, " ", "");
		Assert.assertEquals("12", a);

		old = "this is {a} test string";
		a = StringUtil.replace(old, "{a}", "{b}");
		Assert.assertEquals("this is {b} test string", a);
	}

	@Test
	public void testGetFilename() {
		String a = StringUtil.getFilename("folder/1.txt");
		Assert.assertEquals("1.txt", a);

		a = StringUtil.getFilename("1.txt");
		Assert.assertEquals("1.txt", a);

		a = StringUtil.getFilename(null);
		Assert.assertEquals(null, a);
	}

	@Test
	public void testGetFilenameExtension() {
		String a = StringUtil.getFilenameExtension("1.txt");
		Assert.assertEquals("txt", a);

		a = StringUtil.getFilenameExtension("folder/1.txt");
		Assert.assertEquals("txt", a);

		a = StringUtil.getFilenameExtension("fo.lder/a");
		Assert.assertEquals(null, a);

		a = StringUtil.getFilenameExtension("folder");
		Assert.assertEquals(null, a);

		a = StringUtil.getFilenameExtension(null);
		Assert.assertEquals(null, a);
	}

	@Test
	public void testTrimWhitespace() {
		String s = StringUtil.trimWhitespace(" 	test\t\r\n");
		Assert.assertEquals("test", s);

		s = StringUtil.trimWhitespace(" 	\t\n\r");
		Assert.assertEquals("", s);
	}

	@Test
	public void testTrimWhitespaceString() {
		String striped = new String(new char[] { 32, 160, '\n', '\t', '\r' });
		String s = StringUtil.trimWhitespace(new String(new char[] { 160, 32, '\n', 't', 'e', 's', 't', '\r', 160 }),
				striped);
		Assert.assertEquals("test", s);

		s = StringUtil.trimWhitespace(" 	\t\n\r");
		Assert.assertEquals("", s);
	}

}
