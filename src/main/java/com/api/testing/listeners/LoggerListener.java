package com.api.testing.listeners;

import com.api.testing.utils.Driver;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class LoggerListener implements ITestListener {
	
	public int numberOfTestsRun; 
	public int totalNumberOfTests;

	private static Logger logger = LogManager.getLogger(LoggerListener.class);

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	// Text attachments for Allure
	@Attachment(value = "Page screenshot", type = "image/png")
	public byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	// Text attachments for Allure
	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}

	// HTML attachments for Allure
	@Attachment(value = "{0}", type = "text/html")
	public static String attachHtml(String html) {
		return html;
	}


	@Override
	public void onStart(ITestContext testContext) {
		logger.info("Test run started.");
		numberOfTestsRun = 0;
		ITestNGMethod[] allTestMethods = testContext.getAllTestMethods();
		totalNumberOfTests = allTestMethods.length;
		logger.info("Going to run these tests:");
		for (int i=0; i < allTestMethods.length; i++) {
			logger.info(String.format("%s. %s.%s", i+1, allTestMethods[i].getTestClass().getName(), allTestMethods[i].getMethodName()));
		}
	}

	@Override
	public void onTestStart(ITestResult testResult) {
		logger.info("--------------------------------------------------");
		logger.info(String.format("%s STARTED.", testResult.getName()));
		logger.info("Now Control goes back to the testng class name");
	}

	@Override
	public void onTestSuccess(ITestResult testResult) {
		logger.info(String.format("%s SUCCEEDED.", testResult.getName()));
		numberOfTestsRun += 1;
		logger.info(String.format("Progress: %s / %s tests ran so far.", numberOfTestsRun, totalNumberOfTests));
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
	}


	@Override
	public void onTestFailure(ITestResult testResult) {
		if (testResult.getThrowable() != null) {
			logger.error("Exception thrown during test run: ", testResult.getThrowable());
		}
		logger.info(String.format("%s FAILED.", testResult.getName()));
		numberOfTestsRun += 1;
		logger.info(String.format("Progress: %s / %s tests ran so far.", numberOfTestsRun, totalNumberOfTests));

		logger.info("I am in onTestFailure method " + getTestMethodName(testResult) + " failed");
		Object testClass = testResult.getInstance();
		WebDriver driver = Driver.getDriver();
		// Allure ScreenShotRobot and SaveTestLog
		if (driver instanceof WebDriver) {
			logger.info("Screenshot captured for test case:" + getTestMethodName(testResult));
			saveScreenshotPNG(driver);
		}
		// Save a log on allure.
		saveTextLog(getTestMethodName(testResult) + " failed and screenshot taken!");

	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		logger.info(String.format("%s SKIPPED.", testResult.getName()));
		numberOfTestsRun += 1;
		logger.info(String.format("Progress: %s / %s tests ran so far.", numberOfTestsRun, totalNumberOfTests));
	}

	@Override
	public void onFinish(ITestContext testContext) {
		logger.info("--------------------------------------------------");
		logger.info("Test run finished.");
	}
}
