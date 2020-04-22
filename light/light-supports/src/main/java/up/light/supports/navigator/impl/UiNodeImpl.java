package up.light.supports.navigator.impl;

import java.util.HashMap;
import java.util.Map;

import up.light.pojo.Locator;
import up.light.supports.navigator.IAction;
import up.light.supports.navigator.IUiNode;

public class UiNodeImpl implements IUiNode {
	private Locator mEnterLocator;
	private Locator mExitLocator;
	private String mName;
	private IUiNode mParent;
	private IAction mEnterAction;
	private IAction mExitAction;
	private boolean mNoBack;
	private Map<String, Object> mAttrs;

	UiNodeImpl(IUiNode parent) {
		mParent = parent;
	}

	@Override
	public Locator getEnterLocator() {
		return mEnterLocator;
	}

	@Override
	public void setEnterLocator(Locator locator) {
		mEnterLocator = locator;
	}

	@Override
	public Locator getExitLocator() {
		return mExitLocator;
	}

	@Override
	public void setExitLocator(Locator locator) {
		mExitLocator = locator;
	}

	@Override
	public String getName() {
		return mName;
	}

	@Override
	public void setName(String name) {
		mName = name;
	}

	@Override
	public IAction getEnterAction() {
		return mEnterAction;
	}

	@Override
	public void setEnterAction(IAction action) {
		mEnterAction = action;
	}

	@Override
	public IAction getExitAction() {
		return mExitAction;
	}

	@Override
	public void setExitAction(IAction action) {
		mExitAction = action;
	}

	@Override
	public IUiNode getParent() {
		return mParent;
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (mAttrs == null) {
			mAttrs = new HashMap<String, Object>();
		}
		mAttrs.put(name, value);
	}

	@Override
	public Object getAttribute(String name) {
		if (mAttrs == null)
			return null;
		return mAttrs.get(name);
	}

	@Override
	public boolean isBackDoNothing() {
		return mNoBack;
	}

	@Override
	public void setBackDoNothing(boolean flag) {
		mNoBack = flag;
	}

	@Override
	public int hashCode() {
		return mName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UiNodeImpl) {
			UiNodeImpl other = (UiNodeImpl) obj;
			return other.mName.equals(this.mName);
		}
		return false;
	}

	@Override
	public String toString() {
		return "UiNode [" + mName + "]";
	}

}
