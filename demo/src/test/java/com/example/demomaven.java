package com.example;

import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class demomaven {
    private static String mmtUrl = "https://www.makemytrip.com/";
    private static String _pathFlightList = "//div[@id='premEcon']/div/div[contains(@id,'flight_list_item_')]";
    private static String _userName = "robinsingh21@aol.com";
    private static String _password = "Welcome@123";
    private static String _aolUrl = "https://mail.aol.com/";
    private static String _mailSubjectText = "Damco";
    private static String _msgLineOne = "Line One";
    private static String _msgLineTwo = "Line two";
    private static String _msgLineThree = "Line three";
    private static String _filePath = "D:\\Robin\\google.com.png";
    private static String _imgSize = "13.7kB";

    private static By flightList = By.xpath(_pathFlightList);
    private static By lastFlight = By.xpath(_pathFlightList + "[last()]");
    private static By flightPrice = By.xpath(".//div[@class='priceSection']//p/parent::div");
    private static By fromLabel = By.xpath(".//span[text()='From']");
    private static By toLabel = By.xpath(".//span[text()='To']");
    private static By mumbaiListOption = By.xpath("//ul[@role='listbox']//li[1]//p[contains(text(),'Mumbai')]");
    private static By delhiListOption = By.xpath("//ul[@role='listbox']//li[1]//p[contains(text(),'Delhi')]");
    private static By dtPicker = By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected']");
    private static By departureColumn = By.xpath("//div[contains(@class,'sortbySection')]//span[text()='Departure']");

    private static By login = By.xpath("//span[text()='Login']");
    private static By userName = By.xpath("//input[@id='login-username']");
    private static By password = By.xpath("//input[@id='login-passwd']");
    private static By compose = By.xpath("//a[@data-test-id='compose-button']");
    private static By next = By.xpath("//input[@id='login-signin']");
    private static By mailTo = By.xpath("//input[@id='message-to-field']");
    private static By subject = By.xpath("//input[@data-test-id='compose-subject']");
    private static By msgBody = By.xpath("//div[@data-test-id='rte']");
    private static By submit = By.xpath("//button[@id='login-signin']");
    private static By bulletList = By.xpath("//button[@title='Bulleted List']");
    private static By bulletOption = By.xpath("//li[@data-test-id='bullet']");
    private static By send = By.xpath("//button[@data-test-id='compose-send-button']");
    private static By sender = By.xpath("//span[@data-test-id='email-pill']/span/span");
    private static By attachFiles = By.xpath("//input[@title='Attach files']");
    private static By progressBar = By.xpath("//div[@id='loadProgIn']");    

    private static By attachmentList = By.xpath("//li[@data-test-id='attachment-tray-list-item']");

    private static By inbox = By.xpath("//a[@data-test-folder-name='Inbox']");
    private static By unread = By.xpath("//a[@data-test-smartview-type='UNREAD']");
    private static By newMsgCount = By
            .xpath("//a[@data-test-folder-name='Inbox']//span[@data-test-id='displayed-count']");
    private static By closeSentnotification = By.xpath("//button[@title='Close notification']");
    private static By latestMail = By.xpath("//li/a[@data-test-id='message-list-item']");
    private static By attachmentProgressBar = By.xpath("//div[@data-test-id='attachment-progress-bar']");
    private static By subjectText = By.xpath("//span[@data-test-id='message-group-subject-text']");
    private static By msgText = By.xpath("//div[@data-test-id='message-view-body-content']//ul/li");
    private static By attchmentDownloadIcon = By.xpath("//a[@data-test-id='attachment-download']/span");
    private static By imgSize = By.xpath("//a[@data-test-id='attachment-download']//div[last()]");

    static WebDriver driver = new ChromeDriver();

    @Test
    public static void main(String[] args) throws InterruptedException {
        //GetSecondCheapestFlight();
        SendMail_aol();
    }

    // Task 2 - aol send mail and verify
    public static void SendMail_aol() {
        OpenChrome(_aolUrl);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        DoLogin(_userName, _password);

        // click Compose Button
        WebDriverWait _wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        _wait.until(ExpectedConditions.elementToBeClickable(compose));
        find(compose, 60);
        click(compose);

        // set To: field
        WebElement elmTo = find(mailTo, 60);
        elmTo.click();
        elmTo.sendKeys(_userName);

        // Enter subject
        WebElement elmSubject = find(subject);
        elmSubject.click();
        elmSubject.sendKeys(_mailSubjectText);

        // Message body
        WebElement elmBody = find(msgBody);
        elmBody.click();
        elmBody.sendKeys(_msgLineOne + System.lineSeparator() + _msgLineTwo + System.lineSeparator() + _msgLineThree);
        new Actions(driver).keyDown(Keys.LEFT_CONTROL).sendKeys("a").perform();
        click(bulletList);
        click(bulletOption);

        // Add attchement
        WebElement elmAttachFile = find(attachFiles, 10);
        elmAttachFile.sendKeys(_filePath);
        elmBody.click();
        waitForVisibilityOf(attachmentList, 60);
        waitForInVisibilityOf(attachmentProgressBar);

        // Send
        click(send);
        click(closeSentnotification);

        // goto inbox
        click(inbox);

        WebElement elmDisplayCount = null;
        while (true) {
            try {
                click(inbox);
                click(unread);
                elmDisplayCount = find(newMsgCount, 15);
                if (elmDisplayCount != null)
                    break;
            } catch (Exception e) {
            }
        }

        String mailcount = elmDisplayCount.getText();
        String one = "1";
        if (mailcount.toString().equals(one)) {
            click(inbox);
            click(latestMail);
        }

        System.out.println("Verification");
        SoftAssert softAssert = new SoftAssert();

        String senderMail = find(sender).getText();
        softAssert.assertEquals(senderMail, _userName, "Sender doesn't match");

        String actualsubjectText = find(subjectText).getText();
        softAssert.assertEquals(actualsubjectText, _mailSubjectText, "Subject text doesn't match");

        waitForVisibilityOf(msgText);
        List<WebElement> msgTextList = driver.findElements(msgText);
        softAssert.assertEquals(msgTextList.get(0).getText(), _msgLineOne, "Message line one doesn't match");
        softAssert.assertEquals(msgTextList.get(1).getText(), _msgLineTwo, "Message line two doesn't match");
        softAssert.assertEquals(msgTextList.get(2).getText(), _msgLineThree, "Message line three doesn't match");

        System.out.println("Download attachment");
        click(attchmentDownloadIcon);
        softAssert.assertEquals(find(imgSize).getText(), _imgSize, "Image size doesn't match");
    }

    //Task 1 - MMT Flight Booking
    public static void GetSecondCheapestFlight() throws InterruptedException {
        OpenChrome(mmtUrl);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        driver.navigate().refresh();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        WebElement frmCity = find(fromLabel);
        frmCity.click();

        // Select from : Delhi
        WebElement fromTextBox = driver.findElement(By.xpath("//input[@placeholder='From']"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        fromTextBox.sendKeys("Delhi");
        fromTextBox.sendKeys(Keys.RETURN);
        waitForVisibilityOf(delhiListOption);
        fromTextBox.sendKeys((Keys.ARROW_DOWN));
        fromTextBox.sendKeys(Keys.RETURN);

        // Select To : Mumbai
        WebElement tocity = driver.findElement(toLabel);
        tocity.click();
        WebElement toTextBox = driver.findElement(By.xpath("//input[@placeholder='To']"));
        toTextBox.sendKeys("Mumbai");
        waitForVisibilityOf(mumbaiListOption);
        toTextBox.sendKeys((Keys.ARROW_DOWN));
        toTextBox.sendKeys((Keys.RETURN));

        WebElement elmDate = driver.findElement(dtPicker);
        elmDate.click();

        driver.findElement(By.xpath("//a[text()='Search']")).click();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

        WebElement elmPopUp = find(By.xpath("//div[@class='commonOverlay ']/span"));
        if (elmPopUp != null)
            elmPopUp.click();

        // Sort by departure time
        click(departureColumn);

        //get all flight list
        ScrollToEnd(driver);
        List<WebElement> elmFlightList = driver.findElements(flightList);

        //create a hash map of all flight price and index
        HashMap<Integer, Integer> PriceMap = new HashMap<Integer, Integer>();
        Integer index = 0;
        if (elmFlightList != null && elmFlightList.size() > 0) {
            for (WebElement flight : elmFlightList) {

                int intPrice = getPrice(flight);
                PriceMap.put(index, intPrice);
                index++;
            }
        }

        //sort flight list by price
        Map<Integer, Integer> mp = SortByValue(PriceMap);

        //get index of flight with second least price
        Object secondLeast = mp.keySet().toArray()[1];
        Integer flightIndex = Integer.parseInt(secondLeast.toString()) + 1;
        Object secondLeastPrice = mp.get(secondLeast);
        
        // get name of flight with second least price
        String xpth = _pathFlightList + "[" + flightIndex.toString()
                + "]//p[contains(@class,'airlineName')]";
        WebElement elmAirline = driver.findElement(By.xpath(xpth));
        String secondLeastAirName = elmAirline.getText();
        System.out.println(secondLeastAirName + " has second least price of " + secondLeastPrice);
    }

    private static HashMap<Integer, Integer> SortByValue(HashMap<Integer, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1,
                    Map.Entry<Integer, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private static Integer getPrice(WebElement element) {
        int price = 0;
        WebElement elmPrice = element.findElement(flightPrice);
        if (elmPrice != null) {
            String[] priceText = elmPrice.getText().split("\\R");
            price = Integer.parseInt(priceText[0].replaceAll("[^0-9]+", ""));
        }

        return price;

    }

    private static void ScrollToEnd(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        long intialLength = (long) js.executeScript("return document.body.scrollHeight");
        while (true) {
            js.executeScript("window.scrollTo(0,document.body.scrollHeight)");

            waitForVisibilityOf(lastFlight);
            long currentLength = (long) js.executeScript("return document.body.scrollHeight");
            if (intialLength == currentLength) {
                break;
            }
            intialLength = currentLength;
        }
    }

    private static void OpenChrome(String url) {
        WebDriverManager.chromedriver().setup();
        driver.manage().window().maximize();
        driver.get(url);
    }

    private static void waitForVisibilityOf(By locator, Integer... timeOut) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebDriverWait wait = new WebDriverWait(driver,
                        timeOut.length > 0 ? Duration.ofSeconds(timeOut[0]) : Duration.ofSeconds(30));
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            } catch (Exception e) {

            }
            attempts++;
        }
    }

    private static void waitForInVisibilityOf(By locator, Integer... timeOut) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebDriverWait wait = new WebDriverWait(driver,
                        timeOut.length > 0 ? Duration.ofSeconds(timeOut[0]) : Duration.ofSeconds(30));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            } catch (Exception e) {

            }
            attempts++;
        }
    }

    private static WebElement find(By locator) {
        waitForVisibilityOf(locator);
        return driver.findElement(locator);
    }

    private static WebElement find(By locator, Integer timeout) {
        waitForVisibilityOf(locator, timeout);
        return driver.findElement(locator);
    }

    private static void click(By locator) {
        WebElement elm = find(locator);
        int attempts = 0;
        while (attempts < 5) {
            try {
                elm.click();
                break;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    private static void DoLogin(String _userName, String _password) {
        driver.findElement(login).click();
        waitForVisibilityOf(userName);
        WebElement elmUserName = find(userName);
        elmUserName.sendKeys(_userName);
        click(next);

        waitForVisibilityOf(password);
        WebElement elmPassword = find(password);
        elmPassword.sendKeys(_password);
        click(submit);
        waitForInVisibilityOf(progressBar, 60);
    }

}
