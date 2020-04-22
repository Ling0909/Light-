package mobile.zxjt.wait;

import up.light.util.StringUtil;

/**
 * {@link ICondition}的常规操作实现
 */
public enum Conditions implements ICondition {
	/**
	 * 值为空，若只有空白字符（如空格，回车等）也视为空
	 */
	BLANK {
		@Override
		public boolean isTrue(String expect, String actual) {
			return !StringUtil.hasText(actual);
		}
	},

	/**
	 * 值不为空
	 */
	NOTBLANK {
		@Override
		public boolean isTrue(String expect, String actual) {
			return StringUtil.hasText(actual);
		}
	},

	/**
	 * 实际值大于期望值（字符串比较）
	 */
	GREATER {
		@Override
		public boolean isTrue(String expect, String actual) {
			return actual.compareTo(expect) > 0;
		}
	},

	/**
	 * 实际值小于期望值（字符串比较）
	 */
	LESS {
		@Override
		public boolean isTrue(String expect, String actual) {
			return actual.compareTo(expect) < 0;
		}
	},

	/**
	 * 实际值等于期望值（字符串比较）
	 */
	EQUAL {
		@Override
		public boolean isTrue(String expect, String actual) {
			return actual.compareTo(expect) == 0;
		}
	},

	/**
	 * 实际值大于期望值（数值比较），若存在非数字则返回{@code false}
	 */
	NUM_GREATER {
		@Override
		public boolean isTrue(String expect, String actual) {
			boolean ret = false;
			try {
				ret = compareNum(actual, expect) > 0;
			} catch (NumberFormatException e) {
				ret = false;
			}
			return ret;
		}
	},

	/**
	 * 实际值小于期望值（数值比较），若存在非数字则返回{@code false}
	 */
	NUM_LESS {
		@Override
		public boolean isTrue(String expect, String actual) {
			boolean ret = false;
			try {
				ret = compareNum(actual, expect) < 0;
			} catch (NumberFormatException e) {
				ret = false;
			}
			return ret;
		}
	},

	/**
	 * 实际值等于期望值（数值比较），若存在非数字则返回{@code false}
	 */
	NUM_EQUAL {
		@Override
		public boolean isTrue(String expect, String actual) {
			boolean ret = false;
			try {
				ret = compareNum(actual, expect) == 0;
			} catch (NumberFormatException e) {
				ret = false;
			}
			return ret;
		}
	},

	/**
	 * 实际值包含期望值
	 */
	CONTAINS {
		@Override
		public boolean isTrue(String expect, String actual) {
			return actual.contains(expect);
		}
	},

	/**
	 * 期望值包含实际值
	 */
	CONTAINED {
		@Override
		public boolean isTrue(String expect, String actual) {
			return expect.contains(actual);
		}
	};

	public abstract boolean isTrue(String expect, String actual);

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}

	private static int compareNum(String actual, String expect) {
		Float act = Float.valueOf(actual);
		Float exp = Float.valueOf(expect);

		return Float.compare(act, exp);
	}
}
