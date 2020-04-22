package mobile.zxjt.navigator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;

import mobile.zxjt.driver.DriverFactory;
import mobile.zxjt.navigator.xml.CheckLoginParser;
import mobile.zxjt.navigator.xml.XsdResolver;
import mobile.zxjt.page.base.PageBase;
import up.light.LightContext;
import up.light.folder.FolderTypes;
import up.light.supports.navigator.ICustomAttributeParser;
import up.light.supports.navigator.IUiTree;
import up.light.supports.navigator.UiTreeBuilder;

public abstract class Navigator {
	private static final Log logger = LogFactory.getLog(Navigator.class);
	private static final String CUSTOM_NS = "http://up.light/mobile/zxjt/custom/attribute";
	private static final String XML = "tree.xml";
	private static IUiTree tree;
	private static PageBase currentPage;

	public static void parseXml() {
		XsdResolver resolver = new XsdResolver();
		Map<String, EntityResolver> resolvers = new HashMap<>();
		resolvers.put(resolver.getXsdPath(), resolver);

		Map<String, ICustomAttributeParser> parsers = new HashMap<>();
		parsers.put(CUSTOM_NS, new CheckLoginParser());

		tree = UiTreeBuilder.build(LightContext.getFolderPath(FolderTypes.CONFIG) + XML,
				LightContext.getFolderPath(FolderTypes.REPOSITORY), DriverFactory.getFinder(), resolvers, parsers);
	}

	/**
	 * 导航到指定界面
	 * 
	 * @param name 界面名称
	 * @param targetPage 导航完成后的Page（用于场景恢复）
	 */
	public static void navigate(String name, PageBase targetPage) {
		if (logger.isInfoEnabled()) {
			logger.info("navigate to " + name);
		}
		tree.navigateTo(name);
		currentPage = targetPage;
	}

	/**
	 * 设置当前Page（用于场景恢复）
	 * 
	 * @param targetPage
	 */
	public static void setCurrentPage(PageBase targetPage) {
		currentPage = targetPage;
	}

	/**
	 * 场景恢复
	 */
	public static void resetPage() {
		if (currentPage != null) {
			currentPage.reset();
		} else {
			System.err.println("Current page is null");
		}
	}

}
