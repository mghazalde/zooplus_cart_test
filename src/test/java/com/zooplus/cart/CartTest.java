package com.zooplus.cart;

import com.zooplus.config.TestConfigReader;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartTest {
    private Logger logger = LoggerFactory.getLogger(CartTest.class);
    private WebDriver driver;

    @BeforeClass
    public void setup() throws InterruptedException, IOException {
        initDriver();
        driver.manage().window().maximize();
        driver.navigate().to(TestConfigReader.get("URL"));
        // FIXME: why I have to wait first 5 seconds?
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
    }

    @Test(priority = 1)
    public void testAddProductFromRecommendedProducts() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String recommendedProductsDivId = "js-zootopiaRecosContainerEmpty";
        waitUntilElementIsVisible(driver.findElement(By.id(recommendedProductsDivId)));
        js.executeScript("document.querySelector('#" + recommendedProductsDivId + "').scrollIntoView();");
        js.executeScript("document.querySelector('#" + recommendedProductsDivId + " ul li:first-child button').click();");

        List<WebElement> cartTableRows = driver.findElements(By.className("cart__table__row"));
        logger.info("testAddProductFromRecommendedProducts - cartTableRows size: {}",  cartTableRows.size());
        Assert.assertEquals(cartTableRows.size(), 1);
    }

    @Test(priority = 2)
    public void testValidatePageUrlContainsOverview() throws InterruptedException {
        String currentUrl = driver.getCurrentUrl();
        logger.info("testValidatePageUrlContainsOverview - currentUrl: {}", currentUrl);
        Assert.assertTrue(currentUrl.contains("overview"));
    }

    @Test(priority = 3)
    public void testAdd3ProductsFromRecommendedProducts() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 3; i++) {
            waitUntilElementIsVisible(driver.findElement(By.cssSelector("div[id*='js-zootopiaRecosContainer']")));
            js.executeScript("document.querySelector('div[id*=\"js-zootopiaRecosContainer\"] ul li:first-child button').click()");
            Thread.sleep(3000); // TODO: replace it with some Seleinium feature
        }

        List<WebElement> cartTableRows = driver.findElements(By.className("cart__table__row"));
        logger.info("testAdd3ProductsFromRecommendedProducts - cartTableRows size: {}", cartTableRows.size());
        Assert.assertEquals(cartTableRows.size(), 4);
    }

    @Test(priority = 4)
    public void testGetAndSortPrices() {
        waitUntilElementIsVisible(driver.findElement(By.cssSelector("div[class*='cart__table__row']")));

        List<BigDecimal> originalPrices = getOriginalPrices();
        logger.info("testGetAndSortPrices - Before Sort: {}", originalPrices);

        List<BigDecimal> sortedPrices = originalPrices.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        logger.info("testGetAndSortPrices - After Sort: {}", sortedPrices);

        Assert.assertTrue(originalPrices.containsAll(sortedPrices));
    }

    @Test(priority = 5)
    public void testIncrementLowerPriceProduct() {
        waitUntilElementIsVisible(driver.findElement(By.cssSelector("div[class*='cart__table__row']")));
        List<WebElement> cartTableRows = driver.findElements(By.className("cart__table__row"));

        WebElement lowestPriceElement = null;
        BigDecimal lowestPrice = new BigDecimal(Integer.MAX_VALUE);

        for (WebElement currentElement: cartTableRows) {
            WebElement priceWebElement = currentElement.findElement(By.className("value"));
            BigDecimal elementPrice = new BigDecimal(priceWebElement.getText().substring(1));

            if (elementPrice.compareTo(lowestPrice) <= 0) {
                lowestPrice = elementPrice;
                lowestPriceElement = currentElement;
            }
        }

        logger.info("testIncrementLowerPriceProduct - lowestPrice: {}", lowestPrice);
        Assert.assertNotNull(lowestPriceElement);
        lowestPriceElement.findElement(By.className("js-inc-amount")).click();
    }

    @Test(priority = 6)
    public void testDeleteTheProductWithHighestPrice() throws InterruptedException {
        waitUntilElementIsVisible(driver.findElement(By.cssSelector("div[class*='cart__table__row']")));
        List<WebElement> cartTableRows = driver.findElements(By.className("cart__table__row"));

        WebElement highestPriceElement = null;
        BigDecimal highestPrice = new BigDecimal(Integer.MIN_VALUE);

        for (WebElement currentElement: cartTableRows) {
            WebElement priceWebElement = currentElement.findElement(By.className("value"));
            BigDecimal elementPrice = new BigDecimal(priceWebElement.getText().substring(1));

            if (elementPrice.compareTo(highestPrice) > 0) {
                highestPrice = elementPrice;
                highestPriceElement = currentElement;
            }
        }

        logger.info("testDeleteTheProductWithHighestPrice - highestPrice: {}", highestPrice);
        Assert.assertNotNull(highestPriceElement);
        highestPriceElement.findElement(By.className("js-remove__btn")).click();
        Thread.sleep(3000); // TODO: replace it with some selenuim feature
    }

    @Test(priority = 7)
    public void testSubtotalAndTotalAreCorrect() {
        waitUntilElementIsVisible(driver.findElement(By.cssSelector("div[class*='cart__table__row']")));
        List<WebElement> cartTableRows = driver.findElements(By.className("cart__table__row"));

        BigDecimal subTotalPrice = new BigDecimal(0);

        for (WebElement currentElement: cartTableRows) {
            WebElement priceWebElement = currentElement.findElement(By.className("value"));
            BigDecimal elementPrice = new BigDecimal(priceWebElement.getText().substring(1));
            subTotalPrice = subTotalPrice.add(elementPrice);
        }

        WebElement subTotalElement = driver.findElement(By.cssSelector("div[class='price__info__sub-total'] span"));
        BigDecimal subTotalElementPrice = new BigDecimal(subTotalElement.getText().substring(1));

        logger.info("testSubtotalAndTotalAreCorrect - subTotalPrice: {}", subTotalPrice);
        logger.info("testSubtotalAndTotalAreCorrect - subTotalElementPrice: {}", subTotalElementPrice);

        WebElement shippingFeesElement = driver.findElement(By.cssSelector("span[class='shipping__cost__value']"));
        BigDecimal shippingFees = new BigDecimal(shippingFeesElement.getText().substring(1));
        logger.info("testSubtotalAndTotalAreCorrect - shippingFees: {}", shippingFees);

        BigDecimal total = subTotalElementPrice.add(shippingFees);
        logger.info("testSubtotalAndTotalAreCorrect - total: {}", total);

        WebElement totalElement = driver.findElement(By.cssSelector("span[class*='total__price']"));
        BigDecimal totalElementPrice = new BigDecimal(totalElement.getText().substring(1));
        logger.info("testSubtotalAndTotalAreCorrect - totalElementPrice: {}", totalElementPrice);

        Assert.assertTrue(subTotalPrice.compareTo(subTotalElementPrice) == 0);
        Assert.assertTrue(total.compareTo(totalElementPrice) == 0);
    }

    @Test(priority = 8)
    public void testChangeShippingAddress() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('div[class*=\"js-shipping-cost\"]').click();");
        js.executeScript("document.querySelector('li[data-value=\"PT\"]').click();");
        js.executeScript("document.querySelector('input[name=\"zipCode\"]').value = \"5000\";");
        js.executeScript("document.querySelector('button[data-zta=\"shippingCostPopoverAction\"]').click();");

        WebElement shippingCountryNameElement = driver.findElement(By.cssSelector("span[data-zta=\"shippingCountryName\"]"));
        logger.info("testChangeShippingAddress - shippingCountryNameElement: {}", shippingCountryNameElement.getText());
        Assert.assertEquals(shippingCountryNameElement.getText().trim(), "Portugal (5000)");
    }

    @Test(priority = 9)
    public void testSubtotalAndTotalAreCorrectIncludingShippingFees() {
        testSubtotalAndTotalAreCorrect();
    }

    private WebElement waitUntilElementIsVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void initDriver() {
        try {
            String browserName = TestConfigReader.get("BrowserName");
            logger.info("initDriver - browserName: {}", browserName);

            switch (browserName.toUpperCase()) {
                case "CHROME":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;

                case "FIREFOX":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;

                case "EDGE":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;

                default:
                    throw new RuntimeException("Invalid Browser Name");
            }
        } catch (Exception e) {
            logger.error("Error while executing initDriver", e);
        }
    }

    private List<BigDecimal> getOriginalPrices() {
        List<WebElement> cartTableRows = driver.findElements(By.className("cart__table__row"));
        List<BigDecimal> originalPrices = new ArrayList<>();

        cartTableRows.forEach(e -> {
            WebElement priceWebElement = e.findElement(By.className("value"));
            logger.info("testGetAndSortPrices - price: {}", priceWebElement.getText());
            originalPrices.add(new BigDecimal(priceWebElement.getText().substring(1)));

        });

        return originalPrices;
    }
}