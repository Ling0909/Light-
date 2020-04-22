package up.light.util;

import java.util.EmptyStackException;
import java.util.LinkedList;

/**
 * LinkedList实现的栈
 *
 * @param <T>
 * @since 1.0.0
 */
public class Stack<T> {
	private LinkedList<T> list = new LinkedList<>();

	/**
	 * 压栈
	 * 
	 * @param item 被压栈元素
	 * @return 被压栈元素
	 */
	public T push(T item) {
		list.push(item);
		return item;
	}

	/**
	 * 弹栈
	 * 
	 * @return 栈顶元素
	 */
	public T pop() {
		if (list.isEmpty()) {
			throw new EmptyStackException();
		}
		return list.pop();
	}

	/**
	 * 获取栈顶元素（不弹栈）
	 * 
	 * @return 栈顶元素
	 */
	public T peek() {
		if (list.isEmpty()) {
			throw new EmptyStackException();
		}
		return list.peek();
	}

	/**
	 * 判断栈是否为空
	 * 
	 * @return 空返回true，否则返回false
	 */
	public boolean empty() {
		return list.isEmpty();
	}

	/**
	 * 获取栈内元素个数
	 * 
	 * @return 栈内元素个数
	 */
	public int size() {
		return list.size();
	}

	@Override
	public String toString() {
		return list.toString();
	}

}
