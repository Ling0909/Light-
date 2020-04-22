package mobile.zxjt;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;

import mobile.zxjt.driver.DriverFactory;
import mobile.zxjt.navigator.Navigator;
import mobile.zxjt.report.db.DBConnection;
import mobile.zxjt.testng.HTMLReporter;
import mobile.zxjt.testng.Runner;
import mobile.zxjt.wait.WaitUtil;
import up.light.LightContext;
import up.light.folder.FolderTypes;
import up.light.pojo.ImplicitlyWait;

public class Main {

	public static void main(String[] args) {
		//生成报告
		//Html.a();
		
		try {
			init("ios");
			Runner.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HTMLReporter.generate();
			DBConnection.close();
			DriverFactory.close();
		}
	}

	private static void init(String platform) {
		// 设置平台
		LightContext.setPlatform(platform);
		// 初始化Log4J
		String file = LightContext.getFolderPath(FolderTypes.CONFIG) + "log4j.properties";
		PropertyConfigurator.configure(file);
		// 设置默认等待时间
		ImplicitlyWait wait = new ImplicitlyWait(5, TimeUnit.SECONDS);
		LightContext.setAttribute(WaitUtil.KEY, wait);
		// 初始化Navigator
		Navigator.parseXml();
	}

}
