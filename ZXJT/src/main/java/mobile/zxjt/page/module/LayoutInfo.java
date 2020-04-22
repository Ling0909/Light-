package mobile.zxjt.page.module;

/**
 * 键盘布局信息
 */
public class LayoutInfo {
	/**
	 * 横向元素数
	 */
	public final int width;
	/**
	 * 纵向元素数
	 */
	public final int height;
	/**
	 * 横向偏移数
	 */
	public final int offsetX;
	/**
	 * 纵向偏移数
	 */
	public final int offsetY;

	public LayoutInfo(int width, int height, int offsetX, int offsetY) {
		this.width = width;
		this.height = height;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

}
