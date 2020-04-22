package mobile.zxjt.driver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import up.light.IElementFinder;
import up.light.LightContext;
import up.light.folder.FolderTypes;
import up.light.util.ClassUtil;
import up.light.util.InstantiateUtil;

public abstract class DriverFactory {
	private static final String FILE_NAME = "driver.creator";
	private static AppiumDriver<MobileElement> driver;
	private static IElementFinder<? extends WebElement> finder;
	private static IContextSwitcher switcher;
	private static boolean init;

	public static AppiumDriver<MobileElement> getDriver() {
		initialize();
		return driver;
	}

	@SuppressWarnings("unchecked")
	public static <T extends WebElement> IElementFinder<T> getFinder() {
		initialize();
		return (IElementFinder<T>) finder;
	}

	public static IContextSwitcher getSwitcher() {
		initialize();
		return switcher;
	}

	public static void close() {
		if (driver != null) {
			driver.quit();
			init = false;
		}
	}

	private static void initialize() {
		if (!init) {
			IDriverCreator creator = buildCreator();
			driver = creator.createDriver();
			switcher = creator.createSwithcer(driver);
			finder = new AppiumFinder(driver, switcher);
			init = true;
		}
	}

	private static IDriverCreator buildCreator() {
		String file = LightContext.getFolderPath(FolderTypes.CONFIG) + FILE_NAME;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String className = reader.readLine();
			Class<?> clazz = ClassUtil.forName(className, null);
			return InstantiateUtil.instantiate(clazz, IDriverCreator.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
