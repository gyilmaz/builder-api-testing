package com.api.testing;


import com.api.testing.listeners.LoggerListener;
import com.api.testing.listeners.PriorityInterceptor;
import com.api.testing.listeners.ScreenshotListener;
import com.api.testing.utils.BrowserUtils;
import com.api.testing.utils.Driver;
import org.apache.commons.io.FileUtils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Listeners({ LoggerListener.class, ScreenshotListener.class , PriorityInterceptor.class })

public class UITestCase extends BaseAPITest{
    public static Logger logger = LogManager.getLogger(UITestCase.class);
    public static boolean isWindowsSizeSet=false;


    @BeforeSuite(alwaysRun = true)
    public static void printRemoteGridConsoleURL() {

        try {
            InetAddress ip = InetAddress.getLocalHost();
            Reporter.log("Running on IP Address: " + ip,true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        BrowserUtils.waitFor(5);
    }

    @BeforeSuite(alwaysRun = true)
    public static void webDriverSetUp() {

        logger.info("Browser is opening");
        Driver.getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        if (!isWindowsSizeSet){
            Driver.getDriver().manage().window().fullscreen();
        }
        Driver.getDriver().get(TEST_MACHINE);

    }


    @AfterMethod(alwaysRun = true)
    public void classTearDown() {
        File scrFile = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String fileName = String.format("%s.png", timeStamp);
            FileUtils.copyFile(scrFile, new File("./target/screenshots/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTearDown() {

        Driver.closeDriver();
        Driver.getDriver().quit();
    }

}

