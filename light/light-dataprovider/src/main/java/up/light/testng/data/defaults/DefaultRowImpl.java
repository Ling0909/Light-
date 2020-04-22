package up.light.testng.data.defaults;

import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import up.light.testng.data.IRow;
import up.light.util.Assert;

public class DefaultRowImpl implements IRow {
	private int mIndex;
	private Map<String, Cell> mValues;

	public DefaultRowImpl(int index, Map<String, Cell> values) {
		mIndex = index;
		mValues = values;
	}

	@Override
	public int getIndex() {
		return mIndex;
	}

	@Override
	public String[] getKeys() {
		return mValues.keySet().toArray(new String[0]);
	}

	@Override
	public boolean getBoolean(String key) {
		Assert.notNull(key, "Key must not be null");
		Cell vCell = mValues.get(key);
		if (vCell == null) {
			return false;
		}
		// 获取单元格类型
		int vType = getCellType(vCell);
		switch (vType) {
		case Cell.CELL_TYPE_BLANK:
			return false;
		case Cell.CELL_TYPE_BOOLEAN:
			return vCell.getBooleanCellValue();
		case Cell.CELL_TYPE_STRING:
			return Boolean.valueOf(vCell.getStringCellValue());
		default:
			throw new IllegalArgumentException("Can't get boolean from type " + vType);
		}
	}

	@Override
	public byte getbyte(String key) {
		Assert.notNull(key, "Key must not be null");
		Cell vCell = mValues.get(key);
		if (vCell == null) {
			return 0;
		}
		// 获取单元格类型
		int vType = getCellType(vCell);
		switch (vType) {
		case Cell.CELL_TYPE_BLANK:
			return 0;
		case Cell.CELL_TYPE_NUMERIC:
			return (byte) vCell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			String vStr = vCell.getStringCellValue();
			return Byte.valueOf(vStr);
		default:
			throw new IllegalArgumentException("Can't get byte from type " + vType);
		}
	}

	@Override
	public char getChar(String key) {
		Assert.notNull(key, "Key must not be null");
		Cell vCell = mValues.get(key);
		if (vCell == null) {
			return 0;
		}
		// 获取单元格类型
		int vType = getCellType(vCell);
		switch (vType) {
		case Cell.CELL_TYPE_BLANK:
			return 0;
		case Cell.CELL_TYPE_NUMERIC:
			return (char) vCell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			String vStr = vCell.getStringCellValue();
			if (vStr.length() == 1)
				return vStr.charAt(0);
			else
				throw new IllegalArgumentException("Can't get char from String whose length > 1");
		default:
			throw new IllegalArgumentException("Can't get char from type " + vType);
		}
	}

	@Override
	public short getShort(String key) {
		Assert.notNull(key, "Key must not be null");
		Cell vCell = mValues.get(key);
		if (vCell == null) {
			return 0;
		}
		// 获取单元格类型
		int vType = getCellType(vCell);
		switch (vType) {
		case Cell.CELL_TYPE_BLANK:
			return 0;
		case Cell.CELL_TYPE_NUMERIC:
			return (short) vCell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			String vStr = vCell.getStringCellValue();
			return Short.valueOf(vStr);
		default:
			throw new IllegalArgumentException("Can't get short from type " + vType);
		}
	}

	@Override
	public int getInt(String key) {
		Assert.notNull(key, "Key must not be null");
		Cell vCell = mValues.get(key);
		if (vCell == null) {
			return 0;
		}
		// 获取单元格类型
		int vType = getCellType(vCell);
		switch (vType) {
		case Cell.CELL_TYPE_BLANK:
			return 0;
		case Cell.CELL_TYPE_NUMERIC:
			return (int) vCell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			String vStr = vCell.getStringCellValue();
			return Integer.valueOf(vStr);
		default:
			throw new IllegalArgumentException("Can't get int from type " + vType);
		}
	}

	@Override
	public long getLong(String key) {
		Assert.notNull(key, "Key must not be null");
		Cell vCell = mValues.get(key);
		if (vCell == null) {
			return 0;
		}
		// 获取单元格类型
		int vType = getCellType(vCell);
		switch (vType) {
		case Cell.CELL_TYPE_BLANK:
			return 0;
		case Cell.CELL_TYPE_NUMERIC:
			return (long) vCell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			String vStr = vCell.getStringCellValue();
			return Long.valueOf(vStr);
		default:
			throw new IllegalArgumentException("Can't get long from type " + vType);
		}
	}

	@Override
	public float getFloat(String key) {
		Assert.notNull(key, "Key must not be null");
		Cell vCell = mValues.get(key);
		if (vCell == null) {
			return 0;
		}
		// 获取单元格类型
		int vType = getCellType(vCell);
		switch (vType) {
		case Cell.CELL_TYPE_BLANK:
			return 0;
		case Cell.CELL_TYPE_NUMERIC:
			return (float) vCell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			String vStr = vCell.getStringCellValue();
			return Float.valueOf(vStr);
		default:
			throw new IllegalArgumentException("Can't get float from type " + vType);
		}
	}

	@Override
	public double getDouble(String key) {
		Assert.notNull(key, "Key must not be null");
		Cell vCell = mValues.get(key);
		if (vCell == null) {
			return 0;
		}
		// 获取单元格类型
		int vType = getCellType(vCell);
		switch (vType) {
		case Cell.CELL_TYPE_BLANK:
			return 0;
		case Cell.CELL_TYPE_NUMERIC:
			return vCell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			String vStr = vCell.getStringCellValue();
			return Double.valueOf(vStr);
		default:
			throw new IllegalArgumentException("Can't get double from type " + vType);
		}
	}

	@Override
	public String getString(String key) {
		Assert.notNull(key, "Key must not be null");
		Cell vCell = mValues.get(key);
		if (vCell == null) {
			return null;
		}
		// 获取单元格类型
		int vType = getCellType(vCell);
		switch (vType) {
		case Cell.CELL_TYPE_STRING:
			return vCell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(vCell.getBooleanCellValue());
		case Cell.CELL_TYPE_NUMERIC:
			return String.valueOf(vCell.getNumericCellValue());
		case Cell.CELL_TYPE_BLANK:
			return "";
		default:
			throw new IllegalArgumentException("Can't get String from type " + vType);
		}
	}

	@Override
	public Date getDate(String key) {
		Assert.notNull(key, "Key must not be null");
		Cell vCell = mValues.get(key);
		if (vCell == null) {
			return null;
		}
		if (HSSFDateUtil.isCellDateFormatted(vCell)) {
			return vCell.getDateCellValue();
		}
		throw new IllegalArgumentException("Can't get Date because type of cell is not Date");
	}

	private int getCellType(Cell cell) {
		int vType = cell.getCellType();
		if (vType == Cell.CELL_TYPE_FORMULA) {
			// 获取公式类型
			vType = cell.getCachedFormulaResultType();
		}
		return vType;
	}

}
