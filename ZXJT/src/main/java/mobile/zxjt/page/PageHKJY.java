package mobile.zxjt.page;

import java.io.File;
import java.io.IOException;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;

import io.appium.java_client.MobileElement;
import mobile.zxjt.opencv.ImgLocator;
import mobile.zxjt.page.base.ElementOf;
import mobile.zxjt.page.base.ElementOfs;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.popup.PopupType;
import up.light.LightContext;
import up.light.Platforms;

public class PageHKJY extends PageTradeWithSelect {
	@ElementOf(ElementOfs.CODE)
	private MobileElement oInputCode;

	@ElementOf(ElementOfs.NAME)
	private MobileElement oPName;

	private MobileElement oInputPrice;

	@ElementOf(ElementOfs.NUMBER)
	private MobileElement oInputNum;

	@ElementOf(ElementOfs.BUTTON_OK)
	private MobileElement oBtnOK;

	private boolean needOpenCV;
	private String mFileName;

	public PageHKJY() {
		mType = PopupType.STYLE_2;
		needOpenCV = LightContext.getPlatform() == Platforms.IOS;
		if (needOpenCV) {
			mFileName = LightContext.getRootPath() + "temp/temp.png";
			String os = System.getProperty("os.name");
			if (os.toLowerCase().startsWith("win")) {
				mFileName = mFileName.substring(1);
			}
		}
	}

	@Override
	protected void doSelectCode(String code, String name) {
		isInSelectView = true;
		if (needOpenCV) {
			mPageCodeSelect.doSelectCode(code, name, () -> {
				File f = driver.getScreenshotAs(OutputType.FILE);
				try {
					FileUtil.copyFile(f, new File(mFileName));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return ImgLocator.getCoordinates(mFileName);
			});
		} else {
			mPageCodeSelect.doSelectCode(code, name, 1);
		}
		isInSelectView = false;
	}

	public String doGetPrice() {
		return PageUtil.getValue(oInputPrice);
	}

}
