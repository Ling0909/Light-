package up.light.supports.navigator;

import java.util.NoSuchElementException;

/**
 * UI树
 * 
 * @since 1.0.0
 */
public interface IUiTree {

	/**
	 * 获取默认进入动作，若IUiNode未单独设置动作则使用该动作
	 * 
	 * @return 进入动作
	 */
	IAction getDefaultEnterAction();

	/**
	 * 设置默认进入动作
	 * 
	 * @param action 进入动作
	 */
	void setDefaultEnterAction(IAction action);

	/**
	 * 获取默认退出动作，若IUiNode未单独设置动作则使用该动作
	 * 
	 * @return 退出动作
	 */
	IAction getDefaultExitAction();

	/**
	 * 设置默认退出动作
	 * 
	 * @param action 退出动作
	 */
	void setDefaultExitAction(IAction action);

	/**
	 * 获取当前节点
	 * 
	 * @return 当前节点
	 */
	IUiNode getCurrentNode();

	/**
	 * 设置当前节点
	 * 
	 * @param node 当前节点
	 */
	void setCurrentNode(IUiNode node);

	/**
	 * 根据名字查找节点
	 * 
	 * @param name 节点名
	 * @return 找到的节点
	 * @throws NoSuchElementException 未找到对应节点抛出此异常
	 */
	IUiNode getNodeByName(String name) throws NoSuchElementException;

	/**
	 * 将界面导航到指定界面
	 * 
	 * @param name 要导航到节点的名字
	 */
	void navigateTo(String name);

	/**
	 * 添加监听器
	 * 
	 * @param listener 监听器
	 */
	void addListener(INavListener listener);

}
