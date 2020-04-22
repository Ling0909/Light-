package mobile.zxjt.page.module.popup;

import java.util.List;

import io.appium.java_client.MobileElement;
import mobile.zxjt.page.base.PageManager;
import mobile.zxjt.page.base.PageUtil;
import mobile.zxjt.page.module.Loading;
import up.light.LightContext;
import up.light.Platforms;
import up.light.util.StringUtil;

class Confirm implements IConfirm {
	private List<MobileElement> oTextMsgs;
	private MobileElement oBtnOK;
	private MobileElement oBtnCancel;
	private Loading mLoading = PageManager.getPage(Loading.class);
	private MobileElement mCache;

	@Override
	public String doGetMsg() {
		int i = 1;
		String content;
		StringBuilder sb = new StringBuilder();
		for (MobileElement e : oTextMsgs) {
			if (i == 1) {
				mCache = e;
			}
			content = PageUtil.getText(e);
			if (StringUtil.hasLength(content)) {
				sb.append(content);
			}
			if (i++ % 2 == 0) {
				sb.append("\n");
			} else {
				sb.append(" ");
			}
		}
		if (sb.length() > 0) {
			for (int j = sb.length() - 1; Character.isWhitespace(sb.charAt(j)); --j) {
				sb.deleteCharAt(j);
			}
		} else {
			throw new RuntimeException("Can't get text of Confirm");
		}
		return sb.toString();
	}

	@Override
	public void doAccept() {
		oBtnOK.click();
		mLoading.waitForLoading();
	}

	@Override
	public void doAccept2() {
		if (LightContext.getPlatform() == Platforms.IOS) {
			// 破掉对话框女妖
			if (mCache != null) {
				mCache.click();
			} else {
				oBtnOK.click();
			}
		}
		oBtnOK.click();
		mLoading.waitForLoading();
	}

	@Override
	public void doCancel() {
		oBtnCancel.click();
	}

}
