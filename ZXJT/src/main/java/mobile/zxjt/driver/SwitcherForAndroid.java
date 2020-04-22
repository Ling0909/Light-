package mobile.zxjt.driver;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class SwitcherForAndroid extends SwitcherBase {
	private static final Log logger = LogFactory.getLog(SwitcherForAndroid.class);
	private static final String URL_PATTERN = "url.pattern(.+ptjy_(header|index).html)";
	private String webview;

	public SwitcherForAndroid(AppiumDriver<? extends WebElement> driver) {
		super(driver);
	}

	@Override
	public void reset() {
		switchToWebview();

		String vTarget = getNameFromCache(URL_PATTERN);
		if (vTarget == null) {
			int vDotPos = URL_PATTERN.indexOf('.');
			int vBracketPos = URL_PATTERN.indexOf('(', vDotPos);
			String vMethod = URL_PATTERN.substring(vDotPos + 1, vBracketPos);
			String vExpect = URL_PATTERN.substring(vBracketPos + 1, URL_PATTERN.length() - 1);
			vTarget = switchByUrl(vMethod, vExpect);
			addNameToCache(URL_PATTERN, vTarget);

			if (logger.isDebugEnabled()) {
				logger.debug("get unclosed window: " + vTarget);
			}
		} else {
			safeContextOrWindow(vTarget);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("reset window to " + vTarget);
		}
	}

	@Override
	protected String switchByUrl(String method, String expect) {
		logger.info("switch window by url");
		switchToWebview();
		for (String w : driver.getWindowHandles()) {
			safeContextOrWindow(w);
			if (acceptUrl(method, driver.getCurrentUrl(), expect)) {
				return w;
			}
		}
		throw new RuntimeException("Can't find window whose url matches " + method + "(" + expect + ")");
	}

	@Override
	protected void switchByIndex(int index) {
		logger.debug("switch window by index");
		switchToWebview();
		Set<String> vWins = driver.getWindowHandles();
		index = parseAndCheckIndex(index, vWins.size());
		String vTarget = vWins.toArray(new String[0])[index];
		safeContextOrWindow(vTarget);
	}

	@Override
	protected void safeContextOrWindow(String target) {
		if (!target.equals(currentContext)) {
			switchToWebview();
			driver.switchTo().window(target);
			currentContext = target;
		}
	}

	private String getWebview() {
		if (webview == null) {
			for (String c : driver.getContextHandles()) {
				if (c.startsWith("WEBVIEW_")) {
					webview = c;
					return webview;
				}
			}
			throw new IllegalStateException("There is no webview found");
		}
		return webview;
	}

	private void switchToWebview() {
		if ("NATIVE_APP".equals(currentContext)) {
			super.safeContextOrWindow(getWebview());
		}
	}

}
