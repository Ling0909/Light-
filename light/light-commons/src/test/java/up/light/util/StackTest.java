package up.light.util;

import java.util.EmptyStackException;

import org.junit.Assert;
import org.junit.Test;

public class StackTest {

	@Test
	public void testPush() {
		Stack<String> stack = new Stack<>();
		Assert.assertEquals("1", stack.push("1"));
		Assert.assertEquals("2", stack.push("2"));
		Assert.assertEquals("3", stack.push("3"));
	}

	@Test
	public void testPop() {
		Stack<String> stack = new Stack<>();
		stack.push("1");
		stack.push("2");
		Assert.assertEquals("2", stack.pop());
		Assert.assertEquals("1", stack.pop());
	}

	@Test(expected = EmptyStackException.class)
	public void testPopEmpty() {
		Stack<String> stack = new Stack<>();
		stack.pop();
	}

	@Test
	public void testPeek() {
		Stack<String> stack = new Stack<>();
		stack.push("2");
		Assert.assertEquals("2", stack.peek());
		Assert.assertEquals("2", stack.peek());
	}

	@Test(expected = EmptyStackException.class)
	public void testPeekEmpty() {
		Stack<String> stack = new Stack<>();
		stack.peek();
	}

	@Test
	public void testEmpty() {
		Stack<String> stack = new Stack<>();
		Assert.assertTrue(stack.empty());
		stack.push("2");
		Assert.assertFalse(stack.empty());
	}

	@Test
	public void testSize() {
		Stack<String> stack = new Stack<>();
		Assert.assertEquals(0, stack.size());

		stack.push("1");
		stack.push("2");
		stack.push("3");

		Assert.assertEquals(3, stack.size());

		stack.pop();
		stack.pop();

		Assert.assertEquals(1, stack.size());
	}

}
