package com.api.testing.listeners;


import com.api.testing.utils.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomListener extends TestListenerAdapter {
    private static Logger logger = LogManager.getLogger(CustomListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        saveScreenshot(result);
        savePageSource(result);
        logger.info("Test failied, driver is quit");
        Driver.closeDriver();
    }

    private void saveScreenshot(ITestResult result) {
            logger.info(String.format("Screenshot of %s failure was not saved", result.getName()));
    }

    @SuppressWarnings("deprecation")
    private void savePageSource(ITestResult result) {
            logger.info(String.format("Pagedump of %s failure was not saved", result.getName()));
    }
}
