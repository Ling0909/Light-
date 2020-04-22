package mobile.zxjt.testng;

import java.io.File;
import java.io.InputStream;

public class CssHelper {

	public static String getCSS() {
		try (InputStream ins = CssHelper.class.getResourceAsStream("custom.css")) {
			File f = new File(CssHelper.class.getResource("custom.css").toURI());
			long len = f.length();
			byte[] buff = new byte[(int) len];
			ins.read(buff);
			return new String(buff);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
