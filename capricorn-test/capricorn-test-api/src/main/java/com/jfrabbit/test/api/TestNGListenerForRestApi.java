package com.jfrabbit.test.api;

import com.jfrabbit.common.util.DateUtil;
import com.jfrabbit.common.util.ExceptionUtil;
import com.jfrabbit.common.util.FunctionUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JasonZhang 2018/8/8 上午10:39
 */
@Slf4j
public class TestNGListenerForRestApi implements IInvokedMethodListener, ITestListener {

    public static void main(String[] args) {

    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // Do nothing
    }

    @Override
    /*处理BeforeClass BeforeMethod中的异常*/
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (testResult.getThrowable() != null) {
            log.error("Before throw exception: {}", testResult.getThrowable());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        saveResult(result);
    }

    @Override
    /*处理Test的异常*/
    public void onTestFailure(ITestResult result) {
        saveResult(result);
    }

    @Override
    /*处理DataProvider的异常*/
    public void onTestSkipped(ITestResult result) {
        if (result.getThrowable() != null) { // data provider exception
            log.error("DataProvider throw exception: {}", result.getThrowable());
        }
        saveResult(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onFinish(ITestContext context) {

        String startDate = DateUtil.dateFormat(context.getStartDate(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        String endDate = DateUtil.dateFormat(context.getEndDate(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        long spendTime = context.getEndDate().getTime() - context.getStartDate().getTime();
        int passCases = context.getPassedTests().size();
        int failCases = context.getFailedTests().size();
        int skipCases = context.getSkippedTests().size();

        // console debug
        log.info("\n===============================================\n");
        log.info("SuiteStart:{}", startDate);
        log.info("SuiteEnd:{}", endDate);
        log.info("SuiteSpendTime:{} ms", spendTime);
        log.info("PASS:{} | FAIL:{} | SKIP:{}", passCases, failCases, skipCases);
        log.info("\n===============================================\n");

        caseResults.forEach(arg -> arg.debug(log));
    }

    private static int index = 0;

    private static List<TestCaseResult> caseResults = new ArrayList<>();

    private void saveResult(ITestResult result) {
        String caseName = "";
        String throwable = "";

        for (Object obj : result.getParameters()) {
            if (obj instanceof RestRequestParam) {
                caseName = ((RestRequestParam) obj).getTestcase();
                break;
            }
        }

        if (FunctionUtil.EMPTY_STR.test(caseName)) {
            caseName = result.getName();
        }

        if (result.getStatus() == 2)
            throwable = ExceptionUtil.getErrorMsg(result.getThrowable());

        index++;

        switch (result.getStatus()) {
            case 1:
                caseResults.add(new TestCaseResult(index, caseName, ResultEnum.PASS, throwable));
                break;
            case 2:
                caseResults.add(new TestCaseResult(index, caseName, ResultEnum.FAIL, throwable));
                break;
            case 3:
                caseResults.add(new TestCaseResult(index, caseName, ResultEnum.SKIP, throwable));
                break;
        }
    }


}
