package mobile.zxjt.driver;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.NoSuchElementException;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import up.light.IElementFinder;
import up.light.pojo.ByBean;
import up.light.pojo.Locator;
import up.light.util.Assert;

public class AppiumFinder implements IElementFinder<MobileElement> {
	private static final Log logger = LogFactory.getLog(AppiumFinder.class);
	private AppiumDriver<MobileElement> driver;
	private IContextSwitcher switcher;

	public AppiumFinder(AppiumDriver<MobileElement> driver, IContextSwitcher switcher) {
		this.driver = driver;
		this.switcher = switcher;
	}

	@Override
	public MobileElement findElement(Locator locator) {
		if (logger.isDebugEnabled()) {
			logger.debug("findElement by " + locator);
		}
		switcher.switchContext(locator.getContext());
		ByTypes type;
		for (ByBean bean : locator.getBys()) {
			type = ByTypes.fromString(bean.by);
			Assert.notNull(type, "Unsupported by: " + bean.by);
			try {
				return driver.findElement(type.getBy(bean.value));
			} catch (NoSuchElementException e) {
			}
		}
		throw new NoSuchElementException("Can't find element with locator: " + locator);
	}

	@Override
	public List<MobileElement> findElements(Locator locator) {
		if (logger.isDebugEnabled()) {
			logger.debug("findElements by " + locator);
		}
		switcher.switchContext(locator.getContext());
		ByTypes type;
		List<MobileElement> eles;
		for (ByBean bean : locator.getBys()) {
			type = ByTypes.fromString(bean.by);
			Assert.notNull(type, "Unsupported by: " + bean.by);
			eles = driver.findElements(type.getBy(bean.value));
			if (eles.size() > 0)
				return eles;
		}
		return new ArrayList<>(0);
	}

	@Override
	public Object getRealDriver() {
		return driver;
	}

}
