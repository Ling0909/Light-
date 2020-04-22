package mobile.zxjt.testng;

import java.util.Arrays;

import org.testng.TestNG;

import up.light.LightContext;
import up.light.folder.FolderTypes;

public class Runner {

	public static void run() {
		TestNG testng = new TestNG(false);
		String xml = LightContext.getFolderPath(FolderTypes.CONFIG) + "test.xml";
		testng.setTestSuites(Arrays.asList(xml));
		testng.run();
	}

}
