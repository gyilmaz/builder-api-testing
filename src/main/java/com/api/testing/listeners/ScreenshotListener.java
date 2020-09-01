package com.api.testing.listeners;

import com.api.testing.utils.Driver;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ScreenshotListener extends TestListenerAdapter {
	private static Logger logger = LogManager.getLogger(ScreenshotListener.class);

	@Override
	public void onTestFailure(ITestResult result) {
		saveScreenshot(result);
		savePageSource(result);
	}

	private void saveScreenshot(ITestResult result) {
		WebDriver webDriver = Driver.getDriver();
		
		if (webDriver != null && ((RemoteWebDriver)webDriver).getSessionId() != null) {
			File destination = new File(String.format("%s/screenshots/%s.png", System.getProperty("user.dir"), result.getName())); 
			logger.info(destination.toString());
			File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

			try {
				FileUtils.copyFile(screenshot, destination);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			
			String fileURL = "";
			try {
				fileURL = destination.toURI().toURL().toString();
			} catch (MalformedURLException e) {
				logger.error(e);
			}
			
			logger.info(String.format("Screenshot of %s failure saved to %s", result.getName(), fileURL));
		}
	}
	
	@SuppressWarnings("deprecation")
	private void savePageSource(ITestResult result) {
		WebDriver webDriver = Driver.getDriver();
		if (webDriver != null && ((RemoteWebDriver)webDriver).getSessionId() != null) {
			File destination = new File(String.format("%s/pagedumps/%s.html", System.getProperty("user.dir"), result.getName()));
			String pageSource = webDriver.getPageSource();

			try {
				FileUtils.writeStringToFile(destination, pageSource);
			} catch (IOException e) {
				logger.error(e);
			}
			
			String fileURL = "";
			try {
				fileURL = destination.toURI().toURL().toString();
			} catch (MalformedURLException e) {
				logger.error(e);
			}
			
			logger.info(String.format("Pagedump of %s failure saved to %s", result.getName(), fileURL));
		}
	}
}
