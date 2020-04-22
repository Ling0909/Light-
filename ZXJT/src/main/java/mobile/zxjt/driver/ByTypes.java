package mobile.zxjt.driver;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;

public enum ByTypes {
	ID {
		@Override
		public By getBy(String value) {
			return By.id(value);
		}
	},
	LINKTEXT {
		@Override
		public By getBy(String value) {
			return By.linkText(value);
		}
	},
	PARTIALLINKTEXT {
		@Override
		public By getBy(String value) {
			return By.partialLinkText(value);
		}
	},
	NAME {
		@Override
		public By getBy(String value) {
			return By.name(value);
		}
	},
	TAGNAME {
		@Override
		public By getBy(String value) {
			return By.tagName(value);
		}
	},
	XPATH {
		@Override
		public By getBy(String value) {
			return By.xpath(value);
		}
	},
	CLASSNAME {
		@Override
		public By getBy(String value) {
			return By.className(value);
		}
	},
	CSSSELECTOR {
		@Override
		public By getBy(String value) {
			return By.cssSelector(value);
		}
	},
	ACCESSIBILITYID {
		@Override
		public By getBy(String value) {
			return MobileBy.AccessibilityId(value);
		}
	},
	UIAUTOMATOR {
		@Override
		public By getBy(String value) {
			return MobileBy.AndroidUIAutomator(value);
		}
	},
	UIAUTOMATION {
		@Override
		public By getBy(String value) {
			return MobileBy.IosUIAutomation(value);
		}
	};

	public abstract By getBy(String value);

	public static ByTypes fromString(String name) {
		try {
			return ByTypes.valueOf(name.toUpperCase());
		} catch (Exception e) {
		}
		return null;
	}
}