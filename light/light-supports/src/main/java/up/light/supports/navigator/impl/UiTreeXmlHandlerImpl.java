package up.light.supports.navigator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import up.light.IElementFinder;
import up.light.pojo.Locator;
import up.light.supports.navigator.IAction;
import up.light.supports.navigator.ICustomAttributeParser;
import up.light.supports.navigator.IUiNode;
import up.light.supports.navigator.IUiTree;
import up.light.supports.navigator.UiTreeXmlHandler;
import up.light.util.Assert;
import up.light.util.Stack;
import up.light.util.StringUtil;

public class UiTreeXmlHandlerImpl extends UiTreeXmlHandler {
	protected UiTreeImpl mTree;
	protected Locator mDefaultExitLocator;
	protected IAction mDefaultEnterAction;
	protected IAction mDefaultExitAction;
	protected Map<String, ICustomAttributeParser> parsers = new HashMap<>();
	protected String mCurrentNodeName;
	protected Stack<IUiNode> mNodes = new Stack<>();
	protected List<IUiNode> mNodes2 = new ArrayList<>();
	protected List<String> mNames = new ArrayList<>();

	public UiTreeXmlHandlerImpl(String folder, IElementFinder<?> finder) {
		super(folder);
		mTree = new UiTreeImpl(finder);
	}

	@Override
	public IUiTree getTree() {
		return mTree;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		mCurrentNodeName = qName;

		if ("node".equals(qName)) {
			IUiNode parent = mNodes.empty() ? null : mNodes.peek();
			IUiNode node = new UiNodeImpl(parent);
			// 先设置默认值，若之后属性中有设置会自动覆盖
			node.setEnterAction(mDefaultEnterAction);
			node.setExitAction(mDefaultExitAction);
			node.setExitLocator(mDefaultExitLocator);
			// 添加到列表
			mNodes2.add(node);
			// 遍历属性
			for (int i = 0; i < attributes.getLength(); ++i) {
				String attrValue = attributes.getValue(i);
				// 属性值为空则跳过
				if (!StringUtil.hasText(attrValue))
					continue;

				String name = attributes.getQName(i);
				String attrUri = attributes.getURI(i);
				if (isDefaultNamespace(attrUri)) {
					setDefaultAttribute(node, name, attrValue);
				} else {
					Object value = parseCustomAttribute(attrUri, attributes, i);
					if (value != null) {
						node.setAttribute(name, value);
					}
				}
			}
			// 检查节点必要属性
			checkNodeAttribute(node);
			// 压到栈顶
			mNodes.push(node);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		mCurrentNodeName = null;
		if ("node".equals(qName)) {
			mNodes.pop();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = new String(ch, start, length);

		if (!StringUtil.hasText(value))
			return;

		if ("listener".equals(mCurrentNodeName)) {
			mTree.addListener(getListener(value));
			return;
		}
		if ("enter".equals(mCurrentNodeName)) {
			mDefaultEnterAction = getAction(value);
			mTree.setDefaultEnterAction(mDefaultEnterAction);
			return;
		}
		if ("exit".equals(mCurrentNodeName)) {
			mDefaultExitAction = getAction(value);
			mTree.setDefaultExitAction(mDefaultExitAction);
			return;
		}
		if ("exitLocator".equals(mCurrentNodeName)) {
			mDefaultExitLocator = getLocator(value);
			return;
		}
	}

	@Override
	public void endDocument() throws SAXException {
		mTree.setNodes(mNodes2);
	}

	protected void setDefaultAttribute(IUiNode node, String qName, String value) {
		if ("name".equals(qName)) {
			node.setName(value);
			return;
		}
		if ("enterLocator".equals(qName)) {
			node.setEnterLocator(getLocator(value));
			return;
		}
		if ("exitLocator".equals(qName)) {
			Locator locator = getLocator(value);
			if (locator != null) {
				node.setExitLocator(locator);
			}
			return;
		}
		if ("enterAction".equals(qName)) {
			IAction action = getAction(value);
			if (action != null) {
				node.setEnterAction(action);
			}
			return;
		}
		if ("exitAction".equals(qName)) {
			IAction action = getAction(value);
			if (action != null) {
				node.setExitAction(action);
			}
			return;
		}
		if ("noBack".equals(qName)) {
			node.setBackDoNothing(Boolean.valueOf(value));
			return;
		}
	}

	private void checkNodeAttribute(IUiNode node) {
		String name = node.getName();
		Assert.isTrue(!mNames.contains(name), "Duplicate node name: " + name);
		mNames.add(name);
		Assert.notNull(node.getEnterAction(), "Can't get enterAction for node: " + name);
		Assert.notNull(node.getExitAction(), "Can't get exitAction for node: " + name);
		Assert.notNull(node.getEnterLocator(), "Can't get enterLocator for node: " + name);
		Assert.notNull(node.getExitLocator(), "Can't get exitLocator for node: " + name);
	}
}
