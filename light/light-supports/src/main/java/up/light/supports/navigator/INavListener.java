package up.light.supports.navigator;

/**
 * 界面导航监听器
 * 
 * @since 1.0.0
 */
public interface INavListener {

	/**
	 * 在一次导航开始之前调用
	 * 
	 * @param target 目标节点名称
	 */
	void beforeNavigate(String target);

	/**
	 * 在每一次执行动作前调用
	 * 
	 * @param enter 进入节点时为true， 退出时为false
	 * @param node 正在处理的节点
	 */
	void beforeAction(boolean enter, IUiNode node);

	/**
	 * 在每一次执行动作后调用
	 * 
	 * @param enter 进入节点时为true， 退出时为false
	 * @param node 正在处理的节点
	 */
	void afterAction(boolean enter, IUiNode node);

	/**
	 * 在一次导航完成之后调用
	 * 
	 * @param target 目标节点名称
	 */
	void afterNavigate(String target);

}
