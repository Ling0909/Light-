package up.light.testng.data;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import up.light.exception.LightInstantiationException;
import up.light.testng.data.annotations.Key;
import up.light.testng.data.annotations.Mapper;
import up.light.testng.data.reader.IDataReader;
import up.light.testng.data.reader.IGroupResolver;
import up.light.util.Assert;
import up.light.util.InstantiateUtil;

/**
 * <p>
 * TestNG数据驱动
 * </p>
 * 测试方法可使用以下类型的参数，DataProviderX会根据参数类型自动生成对应的数据：
 * <ol>
 * <li>添加了{@link Key}注解的基本数据类型、String类型、Date类型</li>
 * <li>添加了{@link Mapper}注解的引用数据类型</li>
 * <li>IRow类型</li>
 * <li>CustomRecord类型</li>
 * </ol>
 * 
 * 示例：
 * <ul>
 * <li>
 * <code>public void login(@Key("username") username, @Key("password") password)</code>
 * </li>
 * <li>
 * <code>public void login(@Mapper(UserInfoMapper.class) UserInfo, CustomRecord record)</code>
 * </li>
 * <li><code>public void login(IRow row)</code></li>
 * </ul>
 * 
 * @since 1.0.0
 * 
 * @see Key
 * @see Mapper
 * @see IRow
 * @see CustomRecord
 */
public abstract class DataProviderX {
	public static final String NAME = "light_dp";
	public static final String NAME_LAZY = "light_lazy_dp";
	private static DataProviderConfig mConfig;
	private static IGroupResolver mResovler;
	private static IRowMapper mRowMapper;
	private static AnnoConfigHolder mConfigHolder = new AnnoConfigHolder();

	/**
	 * TestNG数据驱动，一次读取全部数据
	 * 
	 * @param context 测试上下文，由TestNG自动注入
	 * @param method 使用此驱动的方法，由TestNG自动注入
	 * @return 全部数据
	 */
	@DataProvider(name = "light_dp")
	public static Object[][] provider(ITestContext context, Method method) {
		Assert.isTrue(method.getParameters().length > 0, "Method with no parameter can't use data provider");

		// 获取Reader
		IDataReader vReader = getConfig(context).getReader();

		// 更新DP信息
		IGroupResolver vResolver = getResolver(context, method);
		mConfigHolder.update(context, method, vResolver);

		// 切换分组
		vReader.changeGroup(mConfigHolder.getGroup());

		// 读取所有行
		IRowFilter vRowFilter = getRowFilter(context);
		if (vRowFilter != null) {
			vRowFilter.setMethod(method);
		}
		IColumnFilter vColumnFilter = getColumnFilter(context);
		IRow[] vRows = vReader.readAll(vRowFilter, vColumnFilter);

		Object[][] vRet = new Object[vRows.length][];
		for (int i = 0; i < vRows.length; ++i) {
			vRet[i] = row2Array(vRows[i], method);
		}

		return vRet;
	}

	/**
	 * TestNG数据驱动，一次读取一行部数据
	 * 
	 * @param context 测试上下文，由TestNG自动注入
	 * @param method 使用此驱动的方法，由TestNG自动注入
	 * @return 行迭代器
	 */
	@DataProvider(name = "light_lazy_dp")
	public static Iterator<Object[]> lazyProvider(ITestContext context, Method method) {
		Assert.isTrue(method.getParameters().length > 0, "Method with no parameter can't use data provider");

		// 获取Reader
		IDataReader vReader = getConfig(context).getReader();

		// 更新DP信息
		IGroupResolver vResolver = getResolver(context, method);
		mConfigHolder.update(context, method, vResolver);

		// 切换分组
		vReader.changeGroup(mConfigHolder.getGroup());

		// 获取过滤器
		IRowFilter vRowFilter = getRowFilter(context);
		if (vRowFilter != null) {
			vRowFilter.setMethod(method);
		}
		IColumnFilter vColumnFilter = getColumnFilter(context);
		return new LightIterator(vReader, method, vRowFilter, vColumnFilter);
	}

	/**
	 * 将行数据（Row）按照测试方法参数列表转换为数组
	 * 
	 * @param row 行数据
	 * @param method 测试方法
	 * @return 数组
	 */
	public static Object[] row2Array(IRow row, Method method) {
		Class<?>[] vParamTypes = method.getParameterTypes();
		Parameter[] vParams = method.getParameters();
		Object[] vRet = new Object[vParamTypes.length];

		for (int i = 0; i < vParamTypes.length; ++i) {
			if (CustomRecord.class == vParamTypes[i]) {
				// CustomRecord
				vRet[i] = new CustomRecord();
			} else if (IRow.class == vParamTypes[i]) {
				// IRow
				vRet[i] = row;
			} else if (vParamTypes[i].isPrimitive() || String.class == vParamTypes[i] || Date.class == vParamTypes[i]) {
				// 基本数据类型、String类型、Date类型
				Key vKey = vParams[i].getAnnotation(Key.class);
				Assert.notNull(vKey, "String, Date or Primitive type must have annotation of @Key");
				String vKeyName = vKey.value();
				Class<?> vClazz = vParams[i].getType();
				vRet[i] = getValue(row, vKeyName, vClazz);
			} else {
				Mapper vAnnoMapper = vParams[i].getAnnotation(Mapper.class);
				Assert.notNull(vAnnoMapper, "Object type must have annotation of @Mapper");
				Class<? extends IRowMapper> vMapperClazz = vAnnoMapper.value();
				if (mRowMapper == null || mRowMapper.getClass() != vMapperClazz) {
					try {
						mRowMapper = InstantiateUtil.instantiate(vMapperClazz);
					} catch (LightInstantiationException e) {
						throw new RuntimeException(e.getCause());
					}
				}
				vRet[i] = mRowMapper.map(row);
			}
		}

		return vRet;
	}

	private static DataProviderConfig getConfig(ITestContext context) {
		if (mConfig == null) {
			mConfig = (DataProviderConfig) context.getAttribute(DataProviderConfig.KEY_CONTEXT_CONFIG);
		}
		return mConfig;
	}

	private static IGroupResolver getResolver(ITestContext context, Method method) {
		if (mResovler == null) {
			mResovler = getConfig(context).getGroupResovler();
		}
		return mResovler;
	}

	private static IRowFilter getRowFilter(ITestContext context) {
		IRowFilter vFilter = mConfigHolder.getRowFilter();
		if (vFilter == null) {
			vFilter = getConfig(context).getRowFilter();
		}
		return vFilter;
	}

	private static IColumnFilter getColumnFilter(ITestContext context) {
		IColumnFilter vFilter = mConfigHolder.getColumnFilter();
		if (vFilter == null) {
			vFilter = getConfig(context).getColumnFilter();
		}
		return vFilter;
	}

	private static Object getValue(IRow row, String key, Class<?> clazz) {
		if (clazz == boolean.class) {
			return row.getBoolean(key);
		}
		if (clazz == byte.class) {
			return row.getbyte(key);
		}
		if (clazz == char.class) {
			return row.getChar(key);
		}
		if (clazz == short.class) {
			return row.getShort(key);
		}
		if (clazz == int.class) {
			return row.getInt(key);
		}
		if (clazz == long.class) {
			return row.getLong(key);
		}
		if (clazz == float.class) {
			return row.getFloat(key);
		}
		if (clazz == double.class) {
			return row.getDouble(key);
		}
		if (clazz == String.class) {
			return row.getString(key);
		}
		if (clazz == Date.class) {
			return row.getDate(key);
		}
		// should never get here
		throw new IllegalArgumentException("unsupported type: " + clazz.getName());
	}

}
