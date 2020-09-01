package com.api.testing.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class BrowserUtils {
    private static Logger logger = LogManager.getLogger(BrowserUtils.class);
    /*
     * switches to new window by the exact title
     */
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    public static void switchToWindow(){
        ArrayList<String> tabs2 = new ArrayList<String> (Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(tabs2.get(1));
        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(tabs2.get(0));
    }


    public static void hover(WebElement element) {
        logger.info("Hovering over " + element.getText());
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    /**
     * return a list of string from a list of elements ignores any element with no
     * text
     *
     * @param list
     * @return
     */
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    public static List<String> getElementsText(By locator) {

        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();

        for (WebElement el : elems) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    public static void waitFor(int sec) {
        logger.info("Waiting for "+ sec +"sec");
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            logger.info("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
           logger.error("Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
        }
    }


    public static WebElement explicitWait(WebElement element, int timeOut) {

       Wait<WebDriver> wait = null;
        try {
            if(element.isDisplayed()){
                return element;
            }
        }catch(Exception e) {
            if(e.getMessage().toLowerCase().contains("stale")) {
                StaleElementUtils.refreshElement(element);
            }
        }finally {
            wait = new FluentWait<WebDriver>(Driver.getDriver())
                    .withTimeout(Duration.ofSeconds(timeOut))
                    .pollingEvery(Duration.ofMillis(30))
                    .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOf(element));
        }
        return element;
    }

    public static WebElement explictWaitAndClick(WebElement element, int timeOut){
        explicitWait(element,timeOut).click();
        return element;
    }

    public static WebElement explictWaitAndSendKeys(WebElement element, int timeOut, String keys){
        explicitWait(element,timeOut).sendKeys();
        return element;
    }

    public static String getAttribute(String xpath,String attribute){
       return Driver.getDriver().findElement(By.xpath(xpath)).getAttribute(attribute);
    }

    public static boolean checkChevronUp(String param){
       String chevronsState= Driver.getDriver().
                findElement(By.xpath("//*[@class='menu-toggle']/parent::div//header[.='"
                        +param+"']/following-sibling::div//*[local-name()='svg']")).getAttribute("data-icon");
       if(chevronsState.equals("chevron-up")){
           return true;
       }else{

       }

    return true;
    }

    public static void clickHiddenElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)Driver.getDriver();
        js.executeScript("arguments[0].click();", element);

    }

    public static void selectElementByText(WebElement element, String name){
        BrowserUtils.explicitWait(element,5);
        Select select=new Select(element);
        select.selectByVisibleText(name);
    }

    public static void selectElementByIndex(WebElement element, int index){
        BrowserUtils.explicitWait(element,5);
        Select select=new Select(element);
        select.selectByIndex(index);
    }

    public static void selectElementRandomly(WebElement element){
        Random random = new Random();
        Select select=new Select(element);
        List<WebElement> list = select.getOptions();
        int index = random.nextInt(list.size()+1);
        select.selectByIndex(index);
    }

    public static boolean waitForSuccessAlert(int timeinsec){
       List<WebElement> successAlert = Driver.getDriver().findElements(By.xpath("//*[@class='alert alert-success alert-dismissible fade show']"));
        logger.info("Waiting for success alert");
        try {
            for (int i=0; i<100*timeinsec; i++){
                if (successAlert.size()<1){
                    Thread.sleep(10);
                    continue;
                }else {
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
      return false;
    }

    public static boolean waitForDangerAlert(int timeinsec){
        List<WebElement> dangerAlert  = Driver.getDriver().findElements(By.xpath("//*[@class='alert alert-danger alert-dismissible fade show']"));
        logger.info("Waiting for danger alert");
        try {
            for (int i=0; i<10*timeinsec; i++){
                if (dangerAlert.size()<1){
                    Thread.sleep(100);
                    continue;
                }else {
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isClickable(WebElement element){
        if (element.isDisplayed() && element.isEnabled()) {
            return true;
        }else{
            logger.info("Element is not clickable" + element.getText());
            return false;
        }
    }

    public static void checkAlert(String param) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 2);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = Driver.getDriver().switchTo().alert();
            if (param.equals("accept")) {
               alert.accept();
            }else if(param.equalsIgnoreCase("dismiss")){
                alert.dismiss();
            }
        } catch (Exception e) {
            //exception handling
        }
    }

    public static void clearAndSend(WebElement element, String fieldValue)
    {
        BrowserUtils.explicitWait(element,5);
        if(!element.getAttribute("value").isEmpty()) {
            element.sendKeys(Keys.chord(Keys.END));
            int lenText = element.getAttribute("value").length();
            for (int i = 0; i < lenText; i++) {
                element.sendKeys(Keys.chord(Keys.LEFT + "" + Keys.DELETE));
            }
        }
        element.sendKeys(fieldValue);
    }

    public static void setAttribute(WebElement element, String attributeName, String attributeValue)
    {

        JavascriptExecutor executor = (JavascriptExecutor)Driver.getDriver();
        executor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
    }

    public static boolean checkDropDownMenu(WebElement param, String [] expectedList) {
        WebElement drpFilter = param;
        boolean match = false;
        Select select = new Select(drpFilter);
        List<WebElement> options = select.getOptions();
        for (WebElement we : options) {
            for (int i = 0; i < expectedList.length; i++) {
                if (we.getText().equals(expectedList[i])) {
                    match = true;
                }
            }
        }
        return match;
    }

    public static void selectFromDropDownMenu(WebElement param, String expectedList) {
        WebElement drpFilter = param;
        boolean match = false;
        Select select = new Select(drpFilter);
        List<WebElement> options = select.getOptions();

    }

    public static void scrollToViewElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static boolean isMenuElementVisible(WebElement element ){
        boolean displayed= false;
        try {
            scrollToViewElement(element);
            if (element.isDisplayed()) {
                displayed= true;
            }
        }catch (Error e){

        }finally {
            return displayed;
        }
    }

    public static boolean isElementPresent(By by) {
        try {
            return Driver.getDriver().findElement(by) != null;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public static void scrollUp() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollTo(0, 0);");
    }


    public static void waitForElement(WebElement webElem) {
        try {
            int trials = 0;
            do {
                Thread.sleep(200);
                trials++;
            } while (!webElem.isDisplayed() && trials < 50);
        }catch (Exception e) {
            logger.error(e);
        }

    }

    public static boolean elemIsPresent(WebElement element) {
        Boolean passFail = false;

        try {
            if (element.isDisplayed())
                passFail = true;
        } catch (NullPointerException | NoSuchElementException e) {
            System.err.println("Unable to locate element '" + element + "'");
        } catch (Exception e) {
            System.err.println("Unable to check display status of element '" + element + "'");
            //e.printStackTrace();
            logger.error(e);
        }

        return passFail;

    }

}
