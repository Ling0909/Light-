package mobile.zxjt.testng;

import org.testng.IConfigurable;
import org.testng.IConfigureCallBack;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.SkipException;

/**
 * 测试执行控制器，等捕获到{@link StopException}时会跳过后续所有测试用例
 */
public class Controller implements IHookable, IConfigurable {
	private static boolean needStop;
	private static StopException ex;

	public static void setNeedStop(boolean flag, StopException ex) {
		needStop = flag;
		Controller.ex = ex;
	}

	@Override
	public void run(IConfigureCallBack callBack, ITestResult testResult) {
		if (!needStop) {
			callBack.runConfigurationMethod(testResult);
		} else {
			testResult.setStatus(ITestResult.SKIP);
		}
	}

	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
		if (!needStop) {
			callBack.runTestMethod(testResult);
		} else {
			testResult.setStatus(ITestResult.SKIP);
			testResult.setThrowable(new SkipException("Fatal error, test is skipped", ex));
		}
	}

}
