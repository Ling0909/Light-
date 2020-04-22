package mobile.zxjt.test.base;

import up.light.testng.data.IRow;
import up.light.testng.data.IRowMapper;

public class InfoMapper implements IRowMapper {

	@Override
	public Object map(IRow row) {
		Info info = new Info();
		info.setCode(row.getString(Info.KEY_CODE));
		info.setName(row.getString(Info.KEY_NAME));
		long number = row.getLong(Info.KEY_NUMBER);
		if (number > 0) {
			info.setNumber(String.valueOf(number));
		}
		info.setPrice(row.getString(Info.KEY_PRICE));
		info.setConfirmMsg(row.getString(Info.KEY_CONFIRM));
		info.setResultMsg(row.getString(Info.KEY_RESULT));
		return info;
	}

}
