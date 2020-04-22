package up.light.testng.data.defaults;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import up.light.testng.data.IColumnFilter;
import up.light.testng.data.IRow;
import up.light.testng.data.IRowFilter;
import up.light.testng.data.reader.IDataReader;
import up.light.util.Assert;

/**
 * 默认Excel Reader
 */
public class DefaultDataReader implements IDataReader {
	private String mDataSource;
	private Workbook mWorkBook;
	private Sheet mSheet;
	private String mCurrentGroup;
	private int mCurrentIndex;
	private int mRowCount;
	private String[] mTitles;

	public DefaultDataReader(String excel) {
		this.mDataSource = excel;
	}

	@Override
	public void setDataSource(String dataSource) {
		this.mDataSource = dataSource;
	}

	@Override
	public boolean hasNext() {
		return mCurrentIndex < mRowCount;
	}

	@Override
	public void changeGroup(String group) {
		Assert.notNull(group, "Group name must not be null");
		mCurrentIndex = 1;

		if (group.equals(mCurrentGroup)) {
			return;
		}

		// get sheet
		mSheet = getWorkBook().getSheet(group);
		Assert.notNull(mSheet, "Can't get sheet with name: " + group);

		// get row number
		mRowCount = mSheet.getLastRowNum() + 1;

		// get first row
		Row vRow = mSheet.getRow(0);
		Assert.notNull(vRow, "Invalid format: first row must be title");

		// get column number
		int vColumnCount = vRow.getLastCellNum();
		mTitles = new String[vColumnCount];

		// read titles
		for (int i = 0; i < vColumnCount; ++i) {
			Cell vCell = vRow.getCell(i);
			mTitles[i] = vCell.getStringCellValue();
		}
	}

	@Override
	public IRow readRow(IColumnFilter filter) {
		return createRow(mCurrentIndex, filter);
	}

	@Override
	public IRow[] readAll(IRowFilter rowFilter, IColumnFilter columnFilter) {
		List<IRow> vRowList = new ArrayList<>();
		for (int i = 1; i < mRowCount; ++i) {
			IRow vRow = createRow(i, columnFilter);
			if (rowFilter != null && !rowFilter.accept(vRow, rowFilter.getMethod())) {
				continue;
			}
			vRowList.add(vRow);
		}
		IRow[] vArr = new IRow[vRowList.size()];
		return vRowList.toArray(vArr);
	}

	@Override
	public void close() {
		if (mWorkBook != null) {
			try {
				mWorkBook.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private Workbook getWorkBook() {
		if (mWorkBook == null) {
			try {
				mWorkBook = WorkbookFactory.create(new File(mDataSource), null, true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return mWorkBook;
	}

	private IRow createRow(int rowIndex, IColumnFilter filter) {
		Map<String, Cell> vValues = new HashMap<>();
		Row vRow = mSheet.getRow(rowIndex);
		for (int i = 0; i < mTitles.length; ++i) {
			if (filter != null && !filter.accept(mTitles[i], i)) {
				continue;
			}
			Cell vCell = vRow.getCell(i);
			if (vCell != null)
				vValues.put(mTitles[i], vCell);
		}
		++mCurrentIndex;
		return new DefaultRowImpl(rowIndex, vValues);
	}

}
