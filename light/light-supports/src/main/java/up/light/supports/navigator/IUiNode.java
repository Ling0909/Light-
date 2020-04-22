package up.light.supports.navigator;

import up.light.pojo.Locator;

/**
 * UI树节点，一个节点对应着一个界面
 * 
 * @since 1.0.0
 */
public interface IUiNode {

	/**
	 * 获取进入节点的UI元素的定位信息
	 * 
	 * @return 定位信息
	 */
	Locator getEnterLocator();

	/**
	 * 设置进入元素的定位信息
	 * 
	 * @param locator 定位信息
	 */
	void setEnterLocator(Locator locator);

	/**
	 * 获取退出节点的UI元素的定位信息
	 * 
	 * @return 定位信息
	 */
	Locator getExitLocator();

	/**
	 * 设置退出元素的定位信息
	 * 
	 * @param locator 定位信息
	 */
	void setExitLocator(Locator locator);

	/**
	 * 获取节点名称
	 * 
	 * @return 节点名称
	 */
	String getName();

	/**
	 * 设置节点名称
	 * 
	 * @param name 节点名称
	 */
	void setName(String name);

	/**
	 * 获取节点进入动作
	 * 
	 * @return 进入动作
	 */
	IAction getEnterAction();

	/**
	 * 设置节点进入动作，会覆盖IUiTree中的默认动作
	 * 
	 * @param action 进入动作
	 * @see {@link IUiTree#getDefaultEnterAction()}
	 */
	void setEnterAction(IAction action);

	/**
	 * 获取节点退出动作
	 * 
	 * @return 退出动作
	 */
	IAction getExitAction();

	/**
	 * 设置节点退出动作，会覆盖IUiTree中的默认动作
	 * 
	 * @param action 退出动作
	 * @see {@link IUiTree#getDefaultEnterAction()}
	 */
	void setExitAction(IAction action);

	/**
	 * 获取父节点
	 * 
	 * @return 父节点
	 */
	IUiNode getParent();

	/**
	 * 设置自定义属性
	 * 
	 * @param name 属性名
	 * @param value 属性值
	 */
	void setAttribute(String name, Object value);

	/**
	 * 获取指定属性值
	 * 
	 * @param name 属性名
	 * @return 属性值
	 */
	Object getAttribute(String name);

	/**
	 * 退出该节点是否不需要执行任何动作
	 * 
	 * @return 是返回true，否则返回false
	 */
	boolean isBackDoNothing();

	/**
	 * 设置退出该节点是否不需要执行任何动作
	 * 
	 * @param flag
	 */
	void setBackDoNothing(boolean flag);

}
