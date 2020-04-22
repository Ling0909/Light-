package mobile.zxjt.navigator;

import mobile.zxjt.driver.DriverFactory;
import mobile.zxjt.page.PageLogin;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.module.Loading;
import mobile.zxjt.page.module.ToolBar;
import mobile.zxjt.testng.StopException;
import up.light.supports.navigator.INavListener;
import up.light.supports.navigator.IUiNode;

public class NavListenerImpl implements INavListener {
	private static final String PASSWORD = "123321";
	private static final String PREFIX = "【两融】";
	private static final String ATTR_NAME = "custom:checkLogin";
	private boolean hasStarted;
	private boolean reset;
	private boolean PTJY_Logined;
	private boolean RZRQ_Logined;
	private Loading mLoading = PageManager.getPage(Loading.class);

	@Override
	public void beforeNavigate(String target) {
		if (!hasStarted) {
			ToolBar bar = PageManager.getPage(ToolBar.class);
			bar.doWaitForStart();
			bar.doWaitAndCloseMsgBox();
			hasStarted = true;
		}
		// 每次导航前设置reset，以保证仅当在该次导航中存在退出动作时才重置window，且只重置一次
		reset = false;
	}

	@Override
	public void beforeAction(boolean enter, IUiNode node) {
		// 如果没有重置window且为退出Action则重置
		if (!(reset || enter || node.isBackDoNothing())) {
			DriverFactory.getSwitcher().reset();
			reset = true;
		}
	}

	@Override
	public void afterAction(boolean enter, IUiNode node) {
		// 登录检查
		if (enter) {
			Boolean check = (Boolean) node.getAttribute(ATTR_NAME);
			boolean rzrq = node.getName().startsWith(PREFIX);

			if (Boolean.TRUE == check && Boolean.FALSE == isLogined(rzrq)) {
				PageLogin page = PageManager.getPage(PageLogin.class);
				try {
					page.doLogin(getUsername(rzrq), PASSWORD, !rzrq);
				} catch (Exception e) {
					throw new StopException(e);
				}
				if (rzrq) {
					RZRQ_Logined = true;
				} else {
					PTJY_Logined = true;
				}
			}
		}
		if ((enter && "港股通".equals(node.getName())) || (!enter && "港股通".equals(node.getParent().getName()))) {
			mLoading.waitForLoading();
		}
	}

	@Override
	public void afterNavigate(String target) {
	}

	private boolean isLogined(boolean rzrq) {
		if (rzrq)
			return RZRQ_Logined;
		return PTJY_Logined;
	}

	private String getUsername(boolean rzrq) {
		if (rzrq)
			return "99000002";
		return "80316041";
	}

}
