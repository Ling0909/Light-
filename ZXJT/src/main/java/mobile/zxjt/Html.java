package mobile.zxjt;

import mobile.zxjt.testng.HTMLReporter;
import up.light.LightContext;
import up.light.folder.FolderTypes;

public class Html {

	public static void a() {
		LightContext.setPlatform("ios");
		LightContext.setFolderPath(FolderTypes.REPORT.getFolderType(), "123");
		HTMLReporter.generate();
	}

}
