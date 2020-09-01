package com.api.testing.utils;

import com.api.testing.UITestCase;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;


import java.net.MalformedURLException;
import java.net.URL;

public class Driver {
    public static Logger logger = LogManager.getLogger(Driver.class);
    private static final String SELENIUM_GRID_URL = System.getProperty("selenium.grid.url", "http://10.150.1.106:4444/wd/hub");
    private static final String browser = System.getProperty("testbrowser");
    private static final String cwd = System.getProperty("user.dir");
    private static final String macdriversDirectory=cwd+"/src/main/resources/macdrivers/";


    private Driver() {
    }

    private static WebDriver driver;
    public static WebDriver getDriver() {
        if (driver == null) {

            switch (browser) {
                case "chrome":
                    ChromeOptions chromeOptions= new ChromeOptions();
                    chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "phantomjs":
                    WebDriverManager.phantomjs().setup();
//                    driver = new PhantomJSDriver();
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "firefox-headless":
                    System.setProperty("webdriver.chrome.driver", "cucumber-bdd-framework/src/test/resources/drivers/geckodriver");
                    driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));
                    break;

                case "ie":
                    if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                        throw new WebDriverException("Your operating system does not support the requested browser");
                    }
                    WebDriverManager.iedriver().setup();
//                    driver = new InternetExplorerDriver();
                    break;

                case "edge":
                    if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                        System.setProperty("webdriver.edge.driver", macdriversDirectory+"msedgedriver");
                        driver = new EdgeDriver();
                    }else {
                        WebDriverManager.edgedriver().setup();
                        driver = new EdgeDriver();
                    }
                    break;

                case "safari":
                    String os = System.getProperty("os.name");
                    if (os.toLowerCase().contains("windows")) {
                        throw new WebDriverException("Your operating system does not support the requested browser");
                    }
                    SafariOptions options= new SafariOptions();

                    options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//                    WebDriverManager.chromedriver().setup();
//                    driver = new SafariDriver(options);
                    System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
                    driver = new SafariDriver(options);
                    if ( os != null && os.contains("Mac")) {
                        Point targetPosition = new Point(0, 0);
                        driver.manage().window().setPosition(targetPosition);
                        Dimension targetSize = new Dimension(1280, 800); //your screen resolution here
                        driver.manage().window().setSize(targetSize);
                        UITestCase.isWindowsSizeSet=true;
                    }
                    break;

                case "remote_firefox":
                    driver = getRemoteFirefoxDriver();
                    break;
                case "remote_chrome":
                    driver = getRemoteChromeDriver();
                    break;
                case "remote_edge":
                    driver = getRemoteEdgeDriver();
                    break;
                case "remote_safari":
                    driver = getRemoteSafariDriver();
                    break;
            }
        }
        return driver;

    }


    public static WebDriver getRemoteChromeDriver() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-extensions");
//        options.addArguments("disable-infobars");
        RemoteWebDriver webDriver = null;
//        try {
//            webDriver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), desiredCapabilities);
//            String os = System.getProperty("os.name");
//            if ( os != null && os.contains("Mac")) {
//                Point targetPosition = new Point(0, 0);
//                webDriver.manage().window().setPosition(targetPosition);
//                Dimension targetSize = new Dimension(1280, 1024); //your screen resolution here
//                webDriver.manage().window().setSize(targetSize);
//            }
//            webDriver.manage().window().maximize();
//            webDriver.setFileDetector(new LocalFileDetector());
//            System.out.println("RemoteWebDriver Info" + webDriver.getCapabilities()) ;
//        } catch (MalformedURLException e) {
//           e.printStackTrace();
//        }
        return webDriver;
    }


    public static RemoteWebDriver getRemoteFirefoxDriver() {
//        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
//        desiredCapabilities.setAcceptInsecureCerts(true);
        RemoteWebDriver webDriver = null;
//        try {
//            webDriver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), desiredCapabilities);
//            webDriver.manage().window().maximize();
//            webDriver.setFileDetector(new LocalFileDetector());
////            logger.info("Test Video: " + getSeleniumGridVideoURL(webDriver.getSessionId().toString()));
//        } catch (MalformedURLException e) {
//            logger.error(e);
//        }
        return webDriver;
    }

    //grid url is hard coded
    public static RemoteWebDriver getRemoteEdgeDriver() {
//        DesiredCapabilities desiredCapabilities = DesiredCapabilities.edge();
//        desiredCapabilities.setAcceptInsecureCerts(true);
//		System.setProperty("webdriver.ie.driver.silent", "true");

        RemoteWebDriver webDriver = null;
//        try {
//            webDriver = new RemoteWebDriver(new URL("http://10.150.1.238:4444/wd/hub"), desiredCapabilities);
//            webDriver.manage().window().maximize();
//            webDriver.setFileDetector(new LocalFileDetector());
//            System.out.println("RemoteWebDriver Info" + webDriver.getCapabilities());
////            logger.info("Test Video: " + getSeleniumGridVideoURL(webDriver.getSessionId().toString()));
//        } catch (MalformedURLException e) {
//            logger.error(e);
//        }
        return webDriver;
    }

    public static WebDriver getRemoteSafariDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName("safari");

        RemoteWebDriver webDriver = null;
//        try {
//            webDriver = new RemoteWebDriver(new URL(SELENIUM_GRID_URL), desiredCapabilities);
//            webDriver.manage().window().maximize();
//            webDriver.setFileDetector(new LocalFileDetector());
////            logger.info("Test Video: " + getSeleniumGridVideoURL(webDriver.getSessionId().toString()));
//        } catch (MalformedURLException e) {
//            logger.error(e);
//        }
        return webDriver;
    }

    public static String getSeleniumGridConsoleURL() {
        return SELENIUM_GRID_URL.replace("/wd/hub", "/grid/console");
    }

    public static String getSeleniumGridVideoURL(String sessionID) {
        return SELENIUM_GRID_URL.replace("4444", "3000").replace("/wd/hub", "/download_video/") + sessionID + ".mp4";
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
