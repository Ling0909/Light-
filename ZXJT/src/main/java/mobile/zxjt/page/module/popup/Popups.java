package mobile.zxjt.page.module.popup;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import io.appium.java_client.MobileElement;
import mobile.zxjt.driver.DriverFactory;
import up.light.LightContext;
import up.light.folder.FolderTypes;
import up.light.supports.pagefactory.ILocatorFactory;
import up.light.supports.pagefactory.PageFactory;
import up.light.util.Assert;

public abstract class Popups {
	private static final Map<String, IPopup> cache = new HashMap<>();

	public static IAlert getAlert(PopupType type) {
		Assert.notNull(type, "PopupType must not be null");
		PopupBean<IAlert> bean = type.getAlert();
		String repo = bean.repo;
		IPopup alert = cache.get(repo);
		if (alert == null) {
			alert = getInstance(bean.clazz);
			initElements(alert, repo);
			cache.put(repo, alert);
		}
		return (IAlert) alert;
	}

	public static IConfirm getConfirm(PopupType type) {
		Assert.notNull(type, "PopupType must not be null");
		PopupBean<IConfirm> bean = type.getConfirm();
		String repo = bean.repo;
		IPopup confirm = cache.get(repo);
		if (confirm == null) {
			confirm = getInstance(bean.clazz);
			initElements(confirm, repo);
			cache.put(repo, confirm);
		}
		return (IConfirm) confirm;
	}

	/*
	 * 手动指定对象库初始化成员变量
	 */
	private static void initElements(Object obj, String repo) {
		String folder = LightContext.getFolderPath(FolderTypes.REPOSITORY);
		ILocatorFactory factory = new JsonLocatorFactory2(folder, repo);
		PageFactory.initElements(DriverFactory.getFinder(), obj, MobileElement.class, factory);
	}

	public static IPopup getInstance(Class<? extends IPopup> clazz) {
		try {
			Constructor<? extends IPopup> c = clazz.getDeclaredConstructor();
			c.setAccessible(true);
			return c.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

}
