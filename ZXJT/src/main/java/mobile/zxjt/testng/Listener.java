package mobile.zxjt.testng;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.IResultListener2;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import mobile.zxjt.driver.DriverFactory;
import mobile.zxjt.report.StepBean;
import mobile.zxjt.report.TestBean;
import mobile.zxjt.report.db.DBConnection;
import mobile.zxjt.report.db.dao.StepDao;
import mobile.zxjt.report.db.dao.TestDao;
import mobile.zxjt.test.base.JsonGroupSolver;
import mobile.zxjt.test.base.TestBase;
import mobile.zxjt.test.filter.GlobalFilter;
import up.light.LightContext;
import up.light.folder.FolderTypes;
import up.light.testng.data.CustomRecord;
import up.light.testng.data.DataProviderConfig;
import up.light.testng.data.annotations.Key;
import up.light.testng.data.defaults.DefaultDataReader;
import up.light.testng.data.reader.IDataReader;
import up.light.testng.data.reader.IGroupResolver;
import up.light.util.StringUtil;

public class Listener implements IResultListener2 {
	private static final Log logger = LogFactory.getLog(Listener.class);
	private static final String DATA_FILE = "data.xls";
	// ----------------------------------------------------
	private IGroupResolver resolver = new JsonGroupSolver();
	private IDataReader reader;
	// ----------------------------------------------------
	private TestDao mTestDao;
	private StepDao mStepDao;
	private TestBean mTest;
	private StepBean mStep;
	// ----------------------------------------------------
	private String currentName;
	// 失败截图编号
	private int picNum;

	@Override
	public void onStart(ITestContext context) {
		initDataProvider(context);
		initDao();
	}

	@Override
	public void onFinish(ITestContext context) {
		if (reader != null) {
			reader.close();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		createOrUseExistTest(result);

		logger.info("===================");
		logger.info(currentName);
		logger.info("===================");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String paramStr, resultStr = null;
		CustomRecord record = getCustomRecord(result.getParameters());
		// 优先使用CustomRecord中记录的confirm和result，若没有则根据参数生成confirm，result留空
		if (record != null) {
			paramStr = record.get(TestBase.KEY_CONFIRM);
			if (paramStr == null) {
				paramStr = getParamsDescription(result);
			}
			resultStr = record.get(TestBase.KEY_RESULT);
		} else {
			paramStr = getParamsDescription(result);
		}
		// 写入数据库
		addStepAndUpdateTest(result, paramStr, resultStr, null, null);
		// 控制台打印结果
		printResult(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String paramStr, resultStr;
		CustomRecord record = getCustomRecord(result.getParameters());
		if (record != null) {
			paramStr = record.get(TestBase.KEY_CONFIRM);
			if (paramStr == null) {
				paramStr = getParamsDescription(result);
			}
		} else {
			paramStr = getParamsDescription(result);
		}
		// 生成错误信息
		Throwable th = result.getThrowable();
		resultStr = getThrowableDescription(th);
		String stackTrace = getStackTrace(th);
		// 创建截图
		String pic = takeScreenshot();
		// 写入数据库
		addStepAndUpdateTest(result, paramStr, resultStr, stackTrace, pic);

		// 场景恢复
		handleFail(result);
		// 控制台打印结果
		printResult(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Throwable th = result.getThrowable();
		String resultStr = null, stackTrace = null;
		if (th != null) {
			// 因DataProvider异常而被跳过时不执行onTestStart
			// 所以需要在此调用createOrUseExistTest方法
			createOrUseExistTest(result);

			// 生成错误信息
			resultStr = getThrowableDescription(th);
			stackTrace = getStackTrace(th);
		}
		String paramStr = getParamsDescription(result);
		addStepAndUpdateTest(result, paramStr, resultStr, stackTrace, null);
		// 控制台打印结果
		printResult(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// not used
	}

	@Override
	public void beforeConfiguration(ITestResult result) {
		createOrUseExistTest(result);

		logger.info("===================");
		logger.info(currentName);
		logger.info("===================");
	}

	@Override
	public void onConfigurationSuccess(ITestResult result) {
		addStepAndUpdateTest(result, currentName, null, null, null);
		// 控制台打印结果
		printResult(result);
	}

	@Override
	public void onConfigurationFailure(ITestResult result) {
		String paramStr = currentName;
		Throwable th = result.getThrowable();
		String resultStr = getThrowableDescription(th);
		String stackTrace = getStackTrace(th);
		String screenshot = takeScreenshot();
		addStepAndUpdateTest(result, paramStr, resultStr, stackTrace, screenshot);
		// 控制台打印结果
		printResult(result);
	}

	@Override
	public void onConfigurationSkip(ITestResult result) {
		addStepAndUpdateTest(result, currentName, null, null, null);
		// 控制台打印结果
		printResult(result);
	}

	/*
	 * 初始化DataProvider
	 */
	private void initDataProvider(ITestContext context) {
		DataProviderConfig vConfig = new DataProviderConfig();
		String data = LightContext.getFolderPath(FolderTypes.DATA) + DATA_FILE;
		reader = new DefaultDataReader(data);
		vConfig.setReader(reader);
		vConfig.setGroupResovler(resolver);
		vConfig.setRowFilter(new GlobalFilter());
		context.setAttribute(DataProviderConfig.KEY_CONTEXT_CONFIG, vConfig);
	}

	/*
	 * 初始化DAO
	 */
	private void initDao() {
		try {
			Connection vConn = DBConnection.getConnection();
			mTestDao = new TestDao(vConn);
			mStepDao = new StepDao(vConn);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void createOrUseExistTest(ITestResult result) {
		Method method = result.getMethod().getConstructorOrMethod().getMethod();
		String name = resolver.getGroup(result.getTestContext(), method);
		if (!name.equals(currentName)) {
			// 创建新的Test
			mTest = new TestBean();
			mTest.setName(name);
			mTest.setStartTime(new Date());
			mTestDao.add(mTest);

			currentName = name;
			picNum = 0;
		}
		createStepInstance();
	}

	/*
	 * 创建Step实例
	 */
	private void createStepInstance() {
		mStep = new StepBean();
		mStep.setTestId(mTest.getId());
		mStep.setTimeStamp(new Date());
	}

	/*
	 * 将Step写入数据库并更新Test
	 */
	private void addStepAndUpdateTest(ITestResult result, String paramStr, String resultStr, String stack,
			String screen) {
		mTest.setEndTime(new Date());
		mTestDao.update(mTest);

		mStep.setStatus(result.getStatus());
		mStep.setParam(paramStr);
		mStep.setResult(resultStr);
		mStep.setStackTrace(stack);
		mStep.setScreenshot(screen);
		mStepDao.add(mStep);
	}

	/*
	 * 截图并返回文件名
	 */
	private String takeScreenshot() {
		String fileName = "screenshot/" + currentName + (++picNum) + ".png";
		AppiumDriver<MobileElement> driver = DriverFactory.getDriver();
		File f = driver.getScreenshotAs(OutputType.FILE);
		try {
			String folder = LightContext.getFolderPath(FolderTypes.REPORT);
			FileUtil.copyFile(f, new File(folder + fileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return fileName;
	}

	/*
	 * 处理失败
	 */
	private void handleFail(ITestResult result) {
		if (!checkNeedStop(result)) {
			if (result.getMethod().isTest()) {
				doRecovery(result.getInstance());
			}
		}
	}

	/*
	 * 判断是否需要停止后续执行
	 */
	private boolean checkNeedStop(ITestResult result) {
		Throwable tr = result.getThrowable();
		if (tr instanceof StopException) {
			Controller.setNeedStop(true, (StopException) tr);
			return true;
		}
		return false;
	}

	/*
	 * 执行场景恢复
	 */
	private void doRecovery(Object ins) {
		if (ins instanceof TestBase) {
			((TestBase) ins).recovery();
		}
	}

	/*
	 * 控制台打印结果
	 */
	private void printResult(ITestResult result) {
		String resultStr;
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			resultStr = "PASS";
			break;
		case ITestResult.FAILURE:
			System.err.println(currentName + ", FAIL\n" + getThrowableDescription(result.getThrowable()));
			return;
		case ITestResult.SKIP:
			resultStr = "SKIP";
			break;
		default:
			resultStr = "unknown status: " + result.getStatus();
			break;
		}
		System.out.println(currentName + ", " + resultStr);
	}

	/*
	 * 生成异常简要描述信息
	 */
	private String getThrowableDescription(Throwable tr) {
		String msg = tr.getMessage();
		if (StringUtil.hasText(msg)) {
			return StringEscapeUtils.escapeHtml4(msg);
		}
		return tr.getClass().getName();
	}

	/*
	 * 生成异常堆栈信息
	 */
	private String getStackTrace(Throwable tr) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		pw.close();
		String ex = sw.toString();
		return StringEscapeUtils.escapeHtml4(ex);
	}

	/*
	 * 根据参数生成参数信息
	 */
	private String getParamsDescription(ITestResult result) {
		StringBuilder ret = new StringBuilder();
		Method method = result.getMethod().getConstructorOrMethod().getMethod();
		Parameter[] params = method.getParameters();
		Object[] values = result.getParameters();

		if (values.length == 0)
			return method.toString();

		Key anno;
		for (int i = 0; i < params.length; ++i) {
			if (CustomRecord.class == params[i].getType()) {
				continue;
			}
			anno = params[i].getAnnotation(Key.class);
			if (anno != null) {
				ret.append(anno.value()).append(' ');
			}
			ret.append(values[i]).append('\n');
		}
		if (ret.length() > 0) {
			// 删除最后一个换行
			ret.deleteCharAt(ret.length() - 1);
		}
		return ret.toString();
	}

	/*
	 * 从参数中获取CustomRecord
	 */
	private CustomRecord getCustomRecord(Object[] params) {
		for (Object o : params) {
			if (o != null && o.getClass() == CustomRecord.class)
				return (CustomRecord) o;
		}
		return null;
	}

}
