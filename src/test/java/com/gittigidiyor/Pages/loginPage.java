package com.gittigidiyor.Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class loginPage {

    public static void main(String[] args) throws IOException {

        //OPEN BROWSER AND GO RELATED WEBPAGE
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //IMPLICIT WAIT
        driver.navigate().to("https://www.gittigidiyor.com");
        //driver.get("https://www.gittigidiyor.com");

        //SEARCH "BILGISAYAR" WORD FROM SEARCH ENGINE!..
        WebElement closeCookie = driver.findElement(By.cssSelector(".tyj39b-5.lfsBU"));
        closeCookie.click();
        WebElement findSearchPlace = driver.findElement(By.xpath("//div/input"));
        findSearchPlace.sendKeys("Bilgisayar");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        //SECOND PAGE IS OPENING
        Actions act = new Actions(driver);
        WebElement secondPage = driver.findElement(By.xpath("//a[span='2']"));
        act.moveToElement(secondPage).perform();
        secondPage.click();

        //GET A RANDOM WEBELEMENT FROM SECONDPAGE
        WebDriverWait wait = new WebDriverWait(driver,20); //EXPLICIT WAIT
        driver.navigate().refresh();
        List<WebElement> chooseRandom = driver.findElements(By.xpath("//li/article/div[2]/a"));
        System.out.println("SIZE = " + chooseRandom.size());
        int sizeNum = chooseRandom.size();
        Random rd = new Random();
        int num = rd.nextInt(sizeNum);
        System.out.println(num);
        WebElement elm = chooseRandom.get(num);  //WebElement elm = chooseRandom.get(num-1);
        System.out.println(elm);
        wait.until(ExpectedConditions.elementToBeClickable(elm)) ;
        elm.click();
        //JavascriptExecutor jst =(JavascriptExecutor) driver;
        //jst.executeAsyncScript("Block");

        //GET THE PRICE OF CHOSEN PRODUCT WRITE THE TEXT FILE

        //Alert alert = driver.switchTo().alert();
        //alert.dismiss();

        FileWriter fileWriter = new FileWriter("priceName.txt");
        BufferedWriter read = new BufferedWriter(fileWriter);

        WebElement productInfo = driver.findElement(By.xpath("//div/h1"));
        String namePro = productInfo.getAttribute("innerHTML");
        String text = productInfo.getText();

        System.out.println("text = " + text);
        System.out.println("innerHTML =" + namePro);

        read.write(text);
        read.close();

        //ADD PRODUCT TO BASKET

        //driver.navigate().refresh();
        // Actions actBasket = new Actions(driver);
        WebElement basket = driver.findElement(By.cssSelector("form button.control-button.gg-ui-button.plr10.gg-ui-btn-default"));
        wait.until(ExpectedConditions.elementToBeClickable(basket));
        //actBasket.moveToElement(basket).click().perform();
        basket.click();

        //CHECK THE PRICE OF BASKET
        WebElement pagePrice = driver.findElement(By.cssSelector("div#sp-price-discountPrice"));
        String pricePage = pagePrice.getText();
        System.out.println("PAGE =" + pricePage);
        WebElement basketPrice = driver.findElement(By.cssSelector("p span.product-new-price"));
        String priceBasket = pagePrice.getText();
        System.out.println("BASKET =" + priceBasket);

        Assert.assertEquals(pricePage,priceBasket, "PRICE IS NOT TRUE!..");

        //INCREASE THE NUMBER OF PRODUCT AND CHECK IF IT IS 2
        driver.navigate().refresh();
        WebElement increment = driver.findElement(By.cssSelector("a.IncNumber.gg-icon.gg-icon-plus"));
        wait.until(ExpectedConditions.elementToBeClickable(increment)); //EXPLICIT WAIT
        increment.click();

        //VERIFY IF IT IS 2 OR NOT
        WebElement expectedNum = driver.findElement(By.cssSelector("input#buyitnow_adet"));

        String a = expectedNum.getAttribute("value"); //INCREASING VALUE IN DOME
        System.out.println("a =" + a);
        //driver.navigate().refresh(); //IT IS RESETTING THE NUMBER I HAVE INCREASED.
        String ActualNum = "2";
        Assert.assertEquals(ActualNum,a, "IT IS NOT CORRECT!..");

        //ERASE THE VALUE FROM BASKET AND CHECK IF IT IS ERASED
        //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        //driver.navigate().refresh();
        try {
            basket = driver.findElement(By.cssSelector("form button.control-button.gg-ui-button.plr10.gg-ui-btn-default"));
            //wait.until(ExpectedConditions.elementToBeClickable(basket));
            basket.click();
            WebElement goToBasket = driver.findElement(By.cssSelector("a.gg-ui-btn-default.padding-none"));
            goToBasket.click();
            WebElement erase = driver.findElement(By.xpath("//a/i[@class='gg-icon gg-icon-bin-medium'][1]"));
            //driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
            //wait.until(ExpectedConditions.elementToBeClickable(erase));
            erase.click();
            WebElement noProductInTheBasket = driver.findElement(By.xpath("//div/h2[1]"));
            String actual = noProductInTheBasket.getAttribute("innerHTML");
            System.out.println("actual = " + actual);
            String expected = "Sepetinizde ürün bulunmamaktadır.";
            System.out.println("expected = " + expected);
            Assert.assertEquals(actual,expected, "IT IS NOT CORRECT!..");
        }catch (Exception e){
            System.out.println("AAANoSuchElementException");
        }

        //driver.close();
    }
}











