package mobile.zxjt.wait;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import up.light.LightContext;
import up.light.pojo.ImplicitlyWait;
import up.light.supports.pagefactory.IElementGetter;
import up.light.util.Assert;

public abstract class WaitUtil {
	public static final String KEY = "ImplicitlyWait";
	public static final int WAIT_SHORT = 1;
	public static final int WAIT_MEDIUM = 5;
	public static final int WAIT_LONG = 10;
	private static final Log logger = LogFactory.getLog(WaitUtil.class);

	public static boolean exists(final WebDriver driver, final WebElement e, int seconds) {
		if (logger.isInfoEnabled()) {
			logger.info("is exist: " + e + " in " + seconds + "s");
		}
		changeImplicitlyWait(driver);
		final IElementGetter getter = (IElementGetter) e;
		boolean result = true;
		try {
			new WebDriverWait(driver, seconds).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver input) {
					return getter.getElement() != null;
				}
			});
		} catch (TimeoutException e1) {
			result = false;
		}
		resetImplicitlyWait(driver);
		logger.info(result ? "element exist" : "element don't exist");
		return result;
	}

	public static WebElement waitFor(final WebDriver driver, final WebElement e, int seconds) {
		if (logger.isInfoEnabled()) {
			logger.info("wait for element: " + e + " in " + seconds + "s");
		}
		Assert.isInstanceOf(IElementGetter.class, e, null);
		changeImplicitlyWait(driver);
		final IElementGetter getter = (IElementGetter) e;

		WebElement element = new WebDriverWait(driver, seconds).until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver input) {
				return getter.getElement();
			}

			@Override
			public String toString() {
				return "visibilty of element " + e;
			}
		});

		resetImplicitlyWait(driver);

		if (logger.isInfoEnabled()) {
			logger.info("get element: " + element);
		}

		return element;
	}

	public static void untilGone(final WebDriver driver, final WebElement e, int seconds) {
		if (logger.isInfoEnabled()) {
			logger.info("until element gone: " + e + " in " + seconds + "s");
		}
		changeImplicitlyWait(driver);
		final IElementGetter getter = (IElementGetter) e;
		new WebDriverWait(driver, seconds, seconds).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				Boolean ret = Boolean.FALSE;
				try {
					return getter.getElement() == null;
				} catch (NoSuchElementException e) {
					ret = Boolean.TRUE;
				}
				return ret;
			}

			@Override
			public String toString() {
				return "invisibility of element " + e;
			}
		});
		resetImplicitlyWait(driver);
	}

	public static void waitForAttribute(final WebDriver driver, final WebElement e, int seconds, String attributeName,
			final String expectValue, ICondition condition) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format("wait for [%s] %s %s of %s in %ds", attributeName, condition, expectValue, e,
					seconds));
		}
		changeImplicitlyWait(driver);
		new WebDriverWait(driver, seconds).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				String actual = e.getAttribute(attributeName);
				return condition.isTrue(expectValue, actual);
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();
				sb.append("attribute[").append(attributeName).append("] ").append(condition);
				if (expectValue != null) {
					sb.append(" ").append(expectValue);
				}
				return sb.toString();
			}
		});
		resetImplicitlyWait(driver);
	}

	public static String waitForText(final WebDriver driver, final WebElement e, int seconds, final String expectValue,
			ICondition condition) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format("wait for text %s %s of %s in %ds", condition, expectValue, e, seconds));
		}
		changeImplicitlyWait(driver);
		String ret = new WebDriverWait(driver, seconds).until(new ExpectedCondition<String>() {
			@Override
			public String apply(WebDriver input) {
				String actual = e.getText();
				if (condition.isTrue(expectValue, actual))
					return actual;
				return null;
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();
				sb.append("text ").append(condition);
				if (expectValue != null) {
					sb.append(" ").append(expectValue);
				}

				return sb.toString();
			}
		});
		resetImplicitlyWait(driver);
		return ret;
	}

	public static void sleep(long time, TimeUnit unit) {
		try {
			unit.sleep(time);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sleep(int secondes) {
		sleep(secondes, TimeUnit.SECONDS);
	}

	private static void changeImplicitlyWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	private static void resetImplicitlyWait(WebDriver driver) {
		ImplicitlyWait wait = (ImplicitlyWait) LightContext.getAttribute(KEY);
		driver.manage().timeouts().implicitlyWait(wait.timeout, wait.unit);
	}

}
