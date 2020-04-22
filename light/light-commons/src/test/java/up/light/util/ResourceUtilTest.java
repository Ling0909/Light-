package up.light.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class ResourceUtilTest {

	@Test
	public void testToURIURL() throws Exception {
		URL u = new URL("http://www.baidu.com/1 2");
		URI uri = ResourceUtil.toURI(u);
		Assert.assertEquals("http://www.baidu.com/1%202", uri.toString());
	}

	@Test
	public void testToURIString() throws Exception {
		URI uri = ResourceUtil.toURI("http://www.baidu.com/1 2");
		Assert.assertEquals("http://www.baidu.com/1%202", uri.toString());
	}

	@Test
	public void testGetFile() throws FileNotFoundException {
		URL u = this.getClass().getClassLoader().getResource("test.properties");
		File f = ResourceUtil.getFile(u, "test");
		Assert.assertNotNull(f);
		Assert.assertEquals("test.properties", f.getName());
	}

	@Test(expected = FileNotFoundException.class)
	public void testGetFile2() throws Exception {
		URL u = new URL("http://www.baidu.com");
		ResourceUtil.getFile(u, "test");
	}

	@Test
	public void testIsFileURL() throws Exception {
		URL u1 = this.getClass().getClassLoader().getResource("test.properties");
		Assert.assertTrue(ResourceUtil.isFileURL(u1));

		URL u2 = new URL("http://www.baidu.com");
		Assert.assertFalse(ResourceUtil.isFileURL(u2));
	}

}
