package mobile.zxjt.opencv;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import up.light.LightContext;

/**
 * 使用OpenCV识别沪港通、深港通图标位置，仅用于iOS
 */
public abstract class ImgLocator {
	private static Mat _templ;

	static {
		System.load(System.getProperty("user.dir") + "/opencv/opencv_java320.dylib");
	}

	public static int getCoordinates(String src) {
		Mat templ = getTemplate();
		Mat source = Imgcodecs.imread(src);
		int cols = source.cols() - templ.cols() + 1;
		int rows = source.rows() - templ.rows() + 1;
		Mat result = new Mat(rows, cols, CvType.CV_32FC2);
		Imgproc.matchTemplate(source, templ, result, Imgproc.TM_SQDIFF);
		Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
		MinMaxLocResult r = Core.minMaxLoc(result, new Mat());
		// iOS修正系数为0.5
		return (int) (r.minLoc.y * 0.5) + templ.rows() / 2;
	}

	private static Mat getTemplate() {
		if (_templ == null) {
			String path = LightContext.getRootPath() + "img/hgt.png";
			String os = System.getProperty("os.name");

			if (os.toLowerCase().startsWith("win")) {
				path = path.substring(1);
			}
			_templ = Imgcodecs.imread(path);
		}
		return _templ;
	}

}
