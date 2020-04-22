package up.light.supports.pagefactory;

import java.util.ArrayList;
import java.util.List;

import up.light.IElementFinder;
import up.light.pojo.ByBean;
import up.light.pojo.Locator;
import up.light.util.Assert;

public class DynamicLocator<T> {
	private static final char ARG_CHAR = '$';
	private IElementFinder<T> mFinder;
	private Locator mLocator;
	private List<int[]> mIndexs;

	public DynamicLocator(IElementFinder<T> finder, Locator locator) {
		mFinder = finder;
		mLocator = locator;
		initIndexs();
	}

	public T findElement(String... args) {
		return mFinder.findElement(setLocatorArgs(args));
	}

	public List<T> findElements(String... args) {
		return mFinder.findElements(setLocatorArgs(args));
	}

	private Locator setLocatorArgs(String... args) {
		Locator loc = new Locator(mLocator.getContext(), null, mLocator.isCache());
		List<ByBean> orginal = mLocator.getBys();
		List<ByBean> bys = new ArrayList<>(orginal.size());
		ByBean by;
		StringBuilder sb;
		int i = 0;
		int fix; // 用于修正替换后字符串后的索引位置

		for (int[] indexs : mIndexs) {
			fix = 0;
			Assert.isTrue(args.length >= indexs.length, "Invalid number of arguments, required " + indexs.length);
			sb = new StringBuilder(orginal.get(i).value);
			for (int j = 0; j < indexs.length; ++j) {
				sb.replace(indexs[j] + fix, indexs[j] + fix + 1, args[j]);
				fix += args[j].length() - 1;
			}
			by = new ByBean(orginal.get(i).by, sb.toString());
			bys.add(by);
			++i;
		}
		loc.setBys(bys);
		return loc;
	}

	private void initIndexs() {
		List<ByBean> bys = mLocator.getBys();
		mIndexs = new ArrayList<>(bys.size());
		String sb;
		ArrayList<Integer> ls;
		int i;
		for (int j = 0; j < bys.size(); ++j) {
			i = 0;
			sb = bys.get(j).value;
			ls = new ArrayList<>();
			while (i != -1) {
				i = sb.indexOf(ARG_CHAR, i);
				if (i != -1) {
					ls.add(i);
					++i;
				}
			}
			mIndexs.add(ls.stream().mapToInt(Integer::intValue).toArray());
		}
	}

}
