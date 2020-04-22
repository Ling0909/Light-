package mobile.zxjt.test.base;

import java.util.LinkedHashMap;
import java.util.Map;

import up.light.util.StringUtil;

public class Info {
	public static final String KEY_CODE = "代码";
	public static final String KEY_NAME = "名称";
	public static final String KEY_PRICE = "价格";
	public static final String KEY_NUMBER = "数量";
	public static final String KEY_CONFIRM = "验证1";
	public static final String KEY_RESULT = "验证2";

	private Map<String, String> values = new LinkedHashMap<>();

	public String getCode() {
		return values.get(KEY_CODE);
	}

	public void setCode(String code) {
		saveNotNull(KEY_CODE, code);
	}

	public String getName() {
		return values.get(KEY_NAME);
	}

	public void setName(String name) {
		saveNotNull(KEY_NAME, name);
	}

	public String getPrice() {
		return values.get(KEY_PRICE);
	}

	public void setPrice(String price) {
		saveNotNull(KEY_PRICE, price);
	}

	public String getNumber() {
		return values.get(KEY_NUMBER);
	}

	public void setNumber(String number) {
		saveNotNull(KEY_NUMBER, number);
	}

	public String getConfirmMsg() {
		return values.get(KEY_CONFIRM);
	}

	public void setConfirmMsg(String confirmMsg) {
		saveNotNull(KEY_CONFIRM, confirmMsg);
	}

	public String getResultMsg() {
		return values.get(KEY_RESULT);
	}

	public void setResultMsg(String resultMsg) {
		saveNotNull(KEY_RESULT, resultMsg);
	}

	private void saveNotNull(String key, String value) {
		if (StringUtil.hasLength(value))
			values.put(key, value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> e : values.entrySet()) {
			sb.append(e.getKey()).append(' ').append(e.getValue()).append('\n');
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

}
