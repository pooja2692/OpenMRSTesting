package Listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import Base.Generic;


public class TestListener extends Generic implements ITestListener {

	
	@Override
	public void onTestStart(ITestResult result) {
		log.info(result.getName()+ " TestCase Execution Started at "+result.getStartMillis());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		log.info("TestCase Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		log.info("TestCase Failed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		log.info("TestCase Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
	}

}
