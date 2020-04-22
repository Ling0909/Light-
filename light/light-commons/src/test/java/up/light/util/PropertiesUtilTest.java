package up.light.util;

import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import up.light.io.ClassPathResource;
import up.light.io.IResource;

public class PropertiesUtilTest {
	private IResource res = new ClassPathResource("test.properties");

	@Test
	public void testLoadAllPropertiesString() throws IOException {
		Properties p = PropertiesUtil.loadAllProperties("test.properties");
		Assert.assertEquals("123", p.getProperty("a"));
		Assert.assertEquals("456", p.getProperty("b"));
	}

	@Test
	public void testLoadAllPropertiesStringClassLoader() throws IOException {
		Properties p1 = PropertiesUtil.loadAllProperties("test.properties", null);
		Assert.assertEquals("123", p1.getProperty("a"));
		Assert.assertEquals("456", p1.getProperty("b"));

		ClassLoader loader = this.getClass().getClassLoader();
		Properties p2 = PropertiesUtil.loadAllProperties("test.properties", loader);
		Assert.assertEquals("123", p2.getProperty("a"));
		Assert.assertEquals("456", p2.getProperty("b"));
	}

	@Test
	public void testLoadProperties() throws IOException {
		Properties p = PropertiesUtil.loadProperties(res);
		Assert.assertEquals("123", p.getProperty("a"));
		Assert.assertEquals("456", p.getProperty("b"));
	}

	@Test
	public void testFillProperties() throws IOException {
		Properties p = new Properties();
		PropertiesUtil.fillProperties(p, res);
		Assert.assertEquals("123", p.getProperty("a"));
		Assert.assertEquals("456", p.getProperty("b"));
	}

}
