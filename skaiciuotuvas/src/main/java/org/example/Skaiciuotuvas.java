package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Skaiciuotuvas {
    public static WebDriver browser;
    public static Wait<WebDriver> wait;

    public static final String PASSWORD = "Password123?";
    public static final String HOME_URL = "http://localhost:8080/";

    public static void setup() {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--ignore-certificate-errors");

        browser = new ChromeDriver(chromeOptions);
        browser.get(HOME_URL);

        wait = new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(2))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(ElementNotInteractableException.class);

    }

    public static void main(String[] args) {

        setup();
        String userName = generateUniqueUserName();
        createNewUser(userName, true);
        clickLogoutButton();
        fillLoginForm(userName);

        fillCalculatorForm("2","5");

        searchForOperation("", "", "");


//        closeBrowser();
    }

    public static void createNewUser(String userName, boolean confirmPassword){
        clickOnElement(By.xpath("/html/body/div[1]/form/div/h4/a"));

        WebElement userNameInput = browser.findElement(By.id("username"));
        userNameInput.sendKeys(userName);

        WebElement passwordInput = browser.findElement(By.id("password"));
        passwordInput.sendKeys(PASSWORD);

        if (confirmPassword){
            WebElement passwordConfirmInput = browser.findElement(By.id("passwordConfirm"));
            passwordConfirmInput.sendKeys(PASSWORD);
        }

        clickOnElement(By.xpath("//*[@id=\"userForm\"]/button"));

    }
    public static void clickLogoutButton(){clickOnElement(By.xpath("/html/body/nav/div/ul[2]/a"));}

    public static void fillLoginForm(String userName){
        WebElement userNameInput = browser.findElement(By.name("username"));
        userNameInput.sendKeys(userName);

        WebElement passwordInput = browser.findElement(By.name("password"));
        passwordInput.sendKeys(PASSWORD);

        clickOnElement(By.xpath("/html/body/div[1]/form/div/button"));
    }

    public static void fillCalculatorForm(String firstNumber, String secondNumber){
        WebElement firstNumberInput = browser.findElement(By.id("sk1"));
        firstNumberInput.clear();
        firstNumberInput.sendKeys(firstNumber);

        WebElement secondNumberInput = browser.findElement(By.id("sk2"));
        secondNumberInput.clear();
        secondNumberInput.sendKeys(secondNumber);

        clickOnElement(By.xpath("//*[@id=\"number\"]/input[3]"));
    }

    public static boolean searchForOperation(String firstNumber, String secondNumber, String result){
        clickOnElement(By.xpath("/html/body/nav/div/ul[1]/li/a"));
        WebElement resultTable = browser.findElement(By.xpath("/html/body/div/table/tbody"));

        for(WebElement row : resultTable.findElements(By.tagName("tr"))){
            List<WebElement> columns = row.findElements(By.tagName("td"));

            if (columns.toArray().length > 0){

                if(columns.get(0).getText().equals(firstNumber) && columns.get(2).getText().equals(secondNumber) && columns.get(3).getText().equals(result)){
                    return true;
                }
            }
        }
        return false;
    }



    public static int generateRandomNumber(){
        Random rand = new Random();
        return rand.nextInt(100000) ;
    }

    public static String generateUniqueUserName() {
        Random rand = new Random();
        return "test" + rand.nextInt(100000);
    }

    public static void clickOnElement(By locator){browser.findElement(locator).click();}

    public static void closeBrowser(){
        browser.quit();
    }


}
