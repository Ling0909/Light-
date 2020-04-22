package up.light.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * DOM工具类
 * 
 * @since 1.0.0
 */
public abstract class DomUtil {

	/**
	 * 去除元素文本中的首尾空白字符
	 * 
	 * @param node 带处理元素
	 * @param defaultValue 默认值
	 * @return 去掉首尾空白的元素文本，当处理后文本为空时，返回defaultValue
	 */
	public static String getElementTrimedText(Element ele, String defaultValue) {
		String vStr = null;
		if (ele != null) {
			vStr = StringUtil.trimWhitespace(ele.getTextContent());
			if (vStr.length() == 0) {
				vStr = defaultValue;
			}
		}
		return vStr;
	}

	/**
	 * 去除元素文本中的首尾空白字符
	 * 
	 * @param node 带处理元素
	 * @return 去掉首尾空白的元素文本，当处理后文本为空时返回null
	 */
	public static String getElementTrimedText(Element element) {
		return getElementTrimedText(element, null);
	}

	/**
	 * 根据节点名获取元素
	 * 
	 * @param element 待查找元素的父元素
	 * @param name 节点名
	 * @param namespaceURI 命名空间URI
	 * @return 找到的元素，若找到多个返回第一个，若没找到返回null
	 * @see Element#getElementsByTagName(String)
	 * @see Element#getElementsByTagNameNS(String, String)
	 */
	public static Element getFirstElementByTag(Element element, String name, String namespaceURI) {
		Assert.notNull(name, "name must not be null");

		if (element != null) {
			NodeList vNodes;
			if (namespaceURI == null) {
				vNodes = element.getElementsByTagName(name);
			} else {
				vNodes = element.getElementsByTagNameNS(namespaceURI, name);
			}

			Node vNode;
			for (int i = 0; i < vNodes.getLength(); ++i) {
				vNode = vNodes.item(i);
				if (vNode instanceof Element) {
					return (Element) vNode;
				}
			}
		}
		return null;
	}

	/**
	 * 遍历所有子节点，仅对类型为Element的节点执行callback
	 * 
	 * @param root 要遍历的节点
	 * @param callback 回调对象
	 */
	public static void forEachElement(Node root, ElementCallBack callback) {
		if (root == null || callback == null)
			return;

		NodeList vNodes = root.getChildNodes();
		Node vNode;
		Element vElement;

		if (callback != null) {
			for (int i = 0; i < vNodes.getLength(); ++i) {
				vNode = vNodes.item(i);
				if (vNode instanceof Element) {
					vElement = (Element) vNode;
					callback.doWith(vElement);
				}
			}
		}
	}

	/**
	 * 遍历所有子节点，对所有节点执行callback
	 * 
	 * @param root 要遍历的节点
	 * @param callback 回调对象
	 */
	public static void forEachNode(Node root, NodeCallBack callback) {
		if (root == null || callback == null)
			return;

		NodeList vNodes = root.getChildNodes();
		Node vNode;

		if (callback != null) {
			for (int i = 0; i < vNodes.getLength(); ++i) {
				vNode = vNodes.item(i);
				callback.doWith(vNode);
			}
		}
	}

	/**
	 * 元素回调接口
	 */
	public interface ElementCallBack {
		/**
		 * 对元素进行处理
		 * 
		 * @param ele 待处理元素
		 * @see DomUtil#forEachElement(Node, ElementCallBack)
		 */
		void doWith(Element ele);
	}

	/**
	 * 节点回调接口
	 */
	public interface NodeCallBack {
		/**
		 * 对节点进行处理
		 * 
		 * @param ele 待处理节点
		 * @see DomUtil#forEachNode(Node, NodeCallBack)
		 */
		void doWith(Node node);
	}
}
