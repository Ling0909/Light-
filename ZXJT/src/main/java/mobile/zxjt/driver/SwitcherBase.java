package mobile.zxjt.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;
import org.springframework.util.ConcurrentReferenceHashMap;

import io.appium.java_client.AppiumDriver;
import up.light.util.Assert;
import up.light.util.StringUtil;

public class SwitcherBase implements IContextSwitcher {
	private static final Log logger = LogFactory.getLog(SwitcherBase.class);
	private static final String PREFIX = "WEBVIEW[";
	private Pattern vPattern = Pattern.compile("^WEBVIEW\\[(-?[0-9]+|url\\.(start|end|contain|pattern)\\(.+\\))\\]$");
	protected AppiumDriver<? extends WebElement> driver;
	protected String currentContext = "NATIVE_APP";
	private ConcurrentReferenceHashMap<String, String> cache = new ConcurrentReferenceHashMap<>();

	public SwitcherBase(AppiumDriver<? extends WebElement> driver) {
		this.driver = driver;
	}

	@Override
	public void switchContext(String context) {
		if (!StringUtil.hasLength(context)) {
			System.err.println("The context to switch is blank");
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("switching to " + context);
		}
		Assert.notNull(context, "Context must not be null");
		Matcher vMatcher = vPattern.matcher(context);
		if (vMatcher.matches()) {
			switchWebview(context);
		} else {
			safeContext(context);
			if (logger.isDebugEnabled()) {
				logger.debug("switched to " + currentContext);
			}
		}
	}

	@Override
	public void reset() {
	}

	protected void switchWebview(String context) {
		String vMiddleStr = context.substring(PREFIX.length(), context.length() - 1);
		String vTarget;

		if (vMiddleStr.startsWith("url.")) {
			vTarget = getNameFromCache(vMiddleStr);
			if (vTarget == null) {
				// 根据url
				int vDotPos = vMiddleStr.indexOf('.');
				int vBracketPos = vMiddleStr.indexOf('(', vDotPos);
				String vMethod = vMiddleStr.substring(vDotPos + 1, vBracketPos);
				String vExpect = vMiddleStr.substring(vBracketPos + 1, vMiddleStr.length() - 1);
				vTarget = switchByUrl(vMethod, vExpect);
				addNameToCache(vMiddleStr, vTarget);
			} else {
				safeContextOrWindow(vTarget);
			}
		} else {
			// 根据索引
			int vIndex = Integer.parseInt(vMiddleStr, 10);
			switchByIndex(vIndex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("switched to " + currentContext);
			logger.debug("url: " + driver.getCurrentUrl());
		}
	}

	/**
	 * 根据WEBVIEW的URL切换上下文
	 * 
	 * @param method 匹配方法，可用start、end、contain、pattern
	 * @param expect 期望值
	 * @return 上下文名称
	 */
	protected String switchByUrl(String method, String expect) {
		logger.debug("switch context by url");
		List<String> cs = getWebviewContexts();
		for (int i = cs.size() - 1; i >= 0; --i) {
			safeContextOrWindow(cs.get(i));
			if (acceptUrl(method, driver.getCurrentUrl(), expect)) {
				return cs.get(i);
			}
		}
		throw new RuntimeException("Can't find webivew whose url matches " + method + "(" + expect + ")");
	}

	/**
	 * 根据WEBVIEW的index切换上下文
	 * 
	 * @param index WEBVIEW索引，从0开始
	 */
	protected void switchByIndex(int index) {
		logger.debug("switch context by index");
		List<String> vContexts = getWebviewContexts();
		index = parseAndCheckIndex(index, vContexts.size());
		String vTarget = vContexts.get(index);
		safeContextOrWindow(vTarget);
	}

	/**
	 * 修正并校验索引值
	 * 
	 * @param index 索引，可为负数，代表倒数第几个
	 * @param contextsNum 索引总数
	 * @return
	 */
	protected int parseAndCheckIndex(int index, int contextsNum) {
		if (index < 0) {
			index += contextsNum;
		}
		// check index
		if (index < 0 || index >= contextsNum) {
			throw new ArrayIndexOutOfBoundsException("Array index out of range: " + index + ", total: " + contextsNum);
		}
		return index;
	}

	protected void safeContextOrWindow(String target) {
		if (!target.equals(currentContext)) {
			driver.context(target);
			currentContext = target;
		}
	}

	private void safeContext(String target) {
		if (!target.equals(currentContext)) {
			driver.context(target);
			currentContext = target;
		}
	}

	protected String getNameFromCache(String urlPattern) {
		return cache.get(urlPattern);
	}

	protected void addNameToCache(String urlPattern, String context) {
		cache.put(urlPattern, context);
	}

	/*
	 * 判断url是否符合要求
	 */
	protected boolean acceptUrl(String method, String url, String expect) {
		if ("start".equals(method)) {
			return url.startsWith(expect);
		} else if ("end".equals(method)) {
			return url.endsWith(expect);
		} else if ("contain".equals(method)) {
			return url.indexOf(expect) >= 0;
		} else if ("pattern".equals(method)) {
			Pattern vPattern = Pattern.compile(expect);
			Matcher vMatcher = vPattern.matcher(url);
			return vMatcher.matches();
		}
		throw new IllegalArgumentException("should never be here");
	}

	/*
	 * 返回不包含NATIVE_APP的context列表
	 */
	private List<String> getWebviewContexts() {
		Set<String> vContexts = driver.getContextHandles();
		Assert.isTrue(vContexts.size() > 1, "There is no webview to switch");
		List<String> vList = new ArrayList<>(vContexts);
		return vList.subList(1, vList.size());
	}

}
