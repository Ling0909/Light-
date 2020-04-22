package up.light.supports.navigator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import up.light.IElementFinder;
import up.light.pojo.Locator;
import up.light.supports.navigator.IAction;
import up.light.supports.navigator.INavListener;
import up.light.supports.navigator.IUiNode;
import up.light.supports.navigator.IUiTree;
import up.light.util.Assert;

public class UiTreeImpl implements IUiTree {
	private static final int DELAY = 800;
	private List<IUiNode> mNodes;
	private IAction mEnterAction;
	private IAction mExitAction;
	private IUiNode mCurrentNode;
	private List<INavListener> mListeners = new ArrayList<>();
	private IElementFinder<?> mFinder;

	UiTreeImpl(IElementFinder<?> finder) {
		mFinder = finder;
	}

	@Override
	public IAction getDefaultEnterAction() {
		return mEnterAction;
	}

	@Override
	public void setDefaultEnterAction(IAction action) {
		mEnterAction = action;
	}

	@Override
	public IAction getDefaultExitAction() {
		return mExitAction;
	}

	@Override
	public void setDefaultExitAction(IAction action) {
		mExitAction = action;
	}

	@Override
	public IUiNode getCurrentNode() {
		return mCurrentNode;
	}

	@Override
	public void setCurrentNode(IUiNode node) {
		mCurrentNode = node;
	}

	@Override
	public IUiNode getNodeByName(String name) throws NoSuchElementException {
		for (IUiNode node : mNodes) {
			if (node.getName().equals(name)) {
				return node;
			}
		}
		throw new NoSuchElementException("Can't find node with name: " + name);
	}

	@Override
	public void navigateTo(String name) {
		// 目标节点与当前节点相同直接返回
		if (mCurrentNode.getName().equals(name)) {
			return;
		}

		// 调用监听器
		for (INavListener lis : mListeners) {
			lis.beforeNavigate(name);
		}
		// 获取当前节点到目标节点的最短路径
		IUiNode vTarget = getNodeByName(name);
		IUiNode[] vTo = getPathToRoot(vTarget);
		IUiNode[] vFrom = getPathToRoot(mCurrentNode);
		int vSame = getLastSame(vFrom, vTo);

		IUiNode vNode;
		IAction vAction;
		Locator vLocator;
		// 按路径依次退出
		for (int i = 0; i < vFrom.length - vSame; ++i) {
			vNode = vFrom[i];
			vAction = vNode.getExitAction();
			vLocator = vNode.getExitLocator();

			// 调用监听器
			for (INavListener lis : mListeners) {
				lis.beforeAction(false, vNode);
			}
			// 退出时需要执行动作
			if (!vNode.isBackDoNothing()) {
				// 执行退出操作
				if (vAction != null) {
					// Node自定义Action
					vAction.perform(mFinder, vLocator);
				} else {
					// 默认Action
					vAction = getDefaultExitAction();
					Assert.notNull(vAction, "There is no action for node: " + vNode.getName());
					vAction.perform(mFinder, vLocator);
				}
				// 设置current
				mCurrentNode = i < vFrom.length - 1 ? vFrom[i + 1] : vTo[vTo.length - vSame - 1];
				// 调用监听器
				for (INavListener lis : mListeners) {
					lis.afterAction(false, vNode);
				}
				// 延时
				sleep(DELAY);
			}
		}
		// 按路径依次进入
		for (int j = vTo.length - vSame - 1; j >= 0; --j) {
			vNode = vTo[j];
			vAction = vNode.getEnterAction();
			vLocator = vNode.getEnterLocator();

			// 调用监听器
			for (INavListener lis : mListeners) {
				lis.beforeAction(true, vNode);
			}
			// 执行退出操作
			if (vAction != null) {
				// Node自定义Action
				vAction.perform(mFinder, vLocator);
			} else {
				// 默认Action
				vAction = getDefaultEnterAction();
				Assert.notNull(vAction, "There is no action for node: " + vNode.getName());
				vAction.perform(mFinder, vLocator);
			}
			// 设置current
			mCurrentNode = vNode;
			// 调用监听器
			for (INavListener lis : mListeners) {
				lis.afterAction(true, vNode);
			}
			// 延时
			sleep(DELAY);
		}
		// 调用监听器
		for (INavListener lis : mListeners) {
			lis.afterNavigate(name);
		}
		// 修改当前节点
		// mCurrentNode = vTarget;
	}

	@Override
	public void addListener(INavListener listener) {
		if (listener != null)
			mListeners.add(listener);
	}

	public void setNodes(List<IUiNode> nodes) {
		Assert.isTrue(nodes.size() > 0, "Can't initialize empty tree");
		mNodes = nodes;
		// 设置树的根节点为当前节点
		mCurrentNode = mNodes.get(0);
	}

	/*
	 * 得到指定节点到根节点的路径，顺序为节点->Root
	 */
	private IUiNode[] getPathToRoot(IUiNode node) {
		List<IUiNode> vNodes = new ArrayList<>();

		while (node != null) {
			vNodes.add(node);
			node = node.getParent();
		}

		return vNodes.toArray(new IUiNode[0]);
	}

	/*
	 * 返回交叉点位于数组倒数第几个元素
	 */
	private int getLastSame(IUiNode[] from, IUiNode[] to) {
		int i = from.length - 1, j = to.length - 1;
		int k = 0;
		for (; i >= 0 && j >= 0; --i, --j) {
			if (!from[i].equals(to[j])) {
				break;
			}
			++k;
		}
		return k;
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
