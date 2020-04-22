package mobile.zxjt.navigator.xml;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XsdResolver implements EntityResolver {
	private static final String XSD = "attribute.xsd";

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		return new InputSource(XsdResolver.class.getResourceAsStream(XSD));
	}

	public String getXsdPath() {
		File f = new File(System.getProperty("user.dir") + '/' + XSD);
		String path = null;
		try {
			path = "file://" + f.toURI().toURL().getPath();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return path;
	}

}
