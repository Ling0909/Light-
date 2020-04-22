package mobile.zxjt.page.module;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import up.light.LightContext;
import up.light.Platforms;

/**
 * 键盘布局
 */
public enum KeyboardLayout {
	LAYOUT_4_4 {
		@Override
		public LayoutInfo getInfo() {
			return new LayoutInfo(4, 4, 0, 0);
		}
	},
	LAYOUT_5_4 {
		@Override
		public LayoutInfo getInfo() {
			return new LayoutInfo(5, 4, 1, 0);
		}
	};

	private Point[] mPoints;

	public abstract LayoutInfo getInfo();

	private static final Log logger = LogFactory.getLog(KeyboardLayout.class);

	public Point[] getCache(WebDriver driver, WebElement ele) {
		if (mPoints == null) {
			if (logger.isInfoEnabled()) {
				logger.info(this + " initializing");
			}
			mPoints = new Point[10];
			LayoutInfo vInfo = this.getInfo();
			Dimension vDim = ele.getSize();
			int vY = ele.getLocation().y;
			int vWidth, vHeight, vIndexX, vIndexY;

			if (Platforms.ANDROID == LightContext.getPlatform()) {
				// android识别整块键盘
				vWidth = Math.round(vDim.width / vInfo.width);
				vHeight = Math.round(vDim.height / vInfo.height);
			} else {
				// ios识别每个小块
				vWidth = vDim.width;
				vHeight = vDim.height;
			}

			// 1 - 9
			for (int i = 1; i < mPoints.length; ++i) {
				vIndexX = (i - 1) % 3;
				vIndexY = (i - 1) / 3;
				mPoints[i] = new Point(vWidth / 2 + vWidth * (vIndexX + vInfo.offsetX),
						vY + vHeight / 2 + vHeight * (vIndexY + vInfo.offsetY));
			}
			// 0
			mPoints[0] = new Point(vWidth / 2 + vWidth * (1 + vInfo.offsetX),
					vY + vHeight / 2 + vHeight * (3 + vInfo.offsetY));
		}
		return mPoints;
	}
}
