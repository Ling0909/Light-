package up.light.util;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import up.light.util.DomUtil.ElementCallBack;
import up.light.util.DomUtil.NodeCallBack;

public class DomUtilTest {

	@Test
	public void testGetElementTrimedTextElementString() {
		Element ele = mock(Element.class);
		when(ele.getTextContent()).thenReturn(" \ttest\n\r").thenReturn(" \t\n\r");

		String s1 = DomUtil.getElementTrimedText(ele);
		Assert.assertEquals("test", s1);

		String s2 = DomUtil.getElementTrimedText(ele);
		Assert.assertNull(s2);

		String s3 = DomUtil.getElementTrimedText(ele, "defaultvalue");
		Assert.assertEquals("defaultvalue", s3);
	}

	@Test
	public void testGetElementTrimedTextElement() {
		Element ele = mock(Element.class);
		when(ele.getTextContent()).thenReturn(" \ttest\n\r").thenReturn(" \t\n\r");

		String s1 = DomUtil.getElementTrimedText(ele);
		Assert.assertEquals("test", s1);

		String s2 = DomUtil.getElementTrimedText(ele);
		Assert.assertNull(s2);

		String s3 = DomUtil.getElementTrimedText(ele);
		Assert.assertNull(s3);
	}

	@Test
	public void testGetFirstElementByTagNullRoot() {
		// root is null
		Assert.assertNull(DomUtil.getFirstElementByTag(null, "test", null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFirstElementByTagNullName() {
		// name is null
		DomUtil.getFirstElementByTag(null, null, null);
	}

	@Test
	public void testGetFirstElementByTag2() {
		// root has no child
		Element root = mock(Element.class);
		NodeList nodes = mock(NodeList.class);
		when(nodes.getLength()).thenReturn(0);
		when(root.getElementsByTagName(anyString())).thenReturn(nodes);
		when(root.getElementsByTagNameNS(anyString(), anyString())).thenReturn(nodes);

		// no namespace
		Assert.assertNull(DomUtil.getFirstElementByTag(root, "test", null));

		// namespace
		Assert.assertNull(DomUtil.getFirstElementByTag(root, "test", "test"));

		verify(nodes, never()).item(anyInt());
	}

	@Test
	public void testGetFirstElementByTag3() {
		// normal
		Element root = mock(Element.class);
		NodeList nodes = mock(NodeList.class);
		Element ele = mock(Element.class);
		// root
		when(root.getElementsByTagName(anyString())).thenReturn(nodes);
		when(root.getElementsByTagNameNS(anyString(), anyString())).thenReturn(nodes);
		// child list
		when(nodes.getLength()).thenReturn(3);
		when(nodes.item(anyInt())).thenReturn(ele);
		// child element
		when(ele.getTextContent()).thenReturn("test");

		Element e1 = DomUtil.getFirstElementByTag(root, "test", null);
		Assert.assertNotNull(e1);
		Assert.assertEquals("test", e1.getTextContent());
		verify(nodes, times(1)).item(anyInt());

		Element e2 = DomUtil.getFirstElementByTag(root, "test", "test");
		Assert.assertNotNull(e2);
		Assert.assertEquals("test", e2.getTextContent());
		verify(nodes, times(2)).item(anyInt());
	}

	@Test
	public void testForEachElement() {
		int size = 3;
		Element root = mock(Element.class);
		NodeList nodes = mock(NodeList.class);
		Element ele = mock(Element.class);
		// root
		when(root.getChildNodes()).thenReturn(nodes);
		// child list
		when(nodes.getLength()).thenReturn(size);
		when(nodes.item(anyInt())).thenReturn(ele);
		// child element
		when(ele.getTextContent()).thenReturn("test");

		final List<String> texts = new ArrayList<>();
		ElementCallBack c = new ElementCallBack() {
			@Override
			public void doWith(Element ele) {
				String t = ele.getTextContent();
				Assert.assertEquals("test", t);
				texts.add(t);
			}
		};
		DomUtil.forEachElement(root, c);
		Assert.assertEquals(size, texts.size());
	}

	@Test
	public void testForEachNode() {
		int size = 3;
		Element root = mock(Element.class);
		NodeList nodes = mock(NodeList.class);
		Element ele = mock(Element.class);
		Text txt = mock(Text.class);
		// root
		when(root.getChildNodes()).thenReturn(nodes);
		// child list
		when(nodes.getLength()).thenReturn(size);
		when(nodes.item(anyInt())).thenReturn(txt).thenReturn(ele).thenReturn(txt);
		// text
		when(txt.getTextContent()).thenReturn("test");
		// element
		when(ele.getTextContent()).thenReturn("test");

		final StringBuilder s = new StringBuilder();

		NodeCallBack c = new NodeCallBack() {
			@Override
			public void doWith(Node node) {
				s.append(node.getTextContent());
			}
		};

		DomUtil.forEachNode(root, c);
		Assert.assertEquals("testtesttest", s.toString());
		verify(nodes, times(size)).item(anyInt());
	}

}
