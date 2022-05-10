package com.zooplus.page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Overview {
	private Logger logger = LoggerFactory.getLogger(Overview.class);
	private WebDriver driver;

	public Overview(WebDriver driver) {
		this.driver = driver;
	}

	public void add3Products() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (int i = 0; i < 3; i++) {
			waitUntilElementIsVisible(driver.findElement(By.cssSelector("div[id*='js-zootopiaRecosContainer']")));
			js.executeScript(
					"document.querySelector('div[id*=\"js-zootopiaRecosContainer\"] ul li:first-child button').click()");
			Thread.sleep(3000); // TODO: replace it with some Selenium feature
		}
	}

	public List<BigDecimal> getOriginalPrices() {
		waitUntilElementIsVisible(driver.findElement(By.cssSelector("div[class*='cart__table__row']")));
		List<WebElement> cartTableRows = driver.findElements(By.className("cart__table__row"));
		List<BigDecimal> originalPrices = new ArrayList<>();
		cartTableRows.forEach(e -> {
			WebElement priceWebElement = e.findElement(By.className("value"));
			logger.info("getOriginalPrices - price: {}", priceWebElement.getText());
			originalPrices.add(new BigDecimal(priceWebElement.getText().substring(1)));

		});
		return originalPrices;
	}

	public WebElement getLowerPriceProduct() {
		waitUntilElementIsVisible(driver.findElement(By.cssSelector("div[class*='cart__table__row']")));
		List<WebElement> cartTableRows = driver.findElements(By.className("cart__table__row"));

		WebElement lowestPriceElement = null;
		BigDecimal lowestPrice = new BigDecimal(Integer.MAX_VALUE);

		for (WebElement currentElement : cartTableRows) {
			WebElement priceWebElement = currentElement.findElement(By.className("value"));
			BigDecimal elementPrice = new BigDecimal(priceWebElement.getText().substring(1));

			if (elementPrice.compareTo(lowestPrice) <= 0) {
				lowestPrice = elementPrice;
				lowestPriceElement = currentElement;
			}
		}
		logger.info("getLowerPriceProduct - lowestPrice: {}", lowestPrice);
		return lowestPriceElement;
	}

	public WebElement getHighestPriceProduct() {

		waitUntilElementIsVisible(driver.findElement(By.cssSelector("div[class*='cart__table__row']")));
		List<WebElement> cartTableRows = driver.findElements(By.className("cart__table__row"));

		WebElement highestPriceElement = null;
		BigDecimal highestPrice = new BigDecimal(Integer.MIN_VALUE);

		for (WebElement currentElement : cartTableRows) {
			WebElement priceWebElement = currentElement.findElement(By.className("value"));
			BigDecimal elementPrice = new BigDecimal(priceWebElement.getText().substring(1));

			if (elementPrice.compareTo(highestPrice) > 0) {
				highestPrice = elementPrice;
				highestPriceElement = currentElement;
			}
		}

		logger.info("getHighestPriceProduct - highestPrice: {}", highestPrice);
		return highestPriceElement;
	}

	public void changeShippingAddress() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector('div[class*=\"js-shipping-cost\"]').click();");
		js.executeScript("document.querySelector('li[data-value=\"PT\"]').click();");
		js.executeScript("document.querySelector('input[name=\"zipCode\"]').value = \"5000\";");
		js.executeScript("document.querySelector('button[data-zta=\"shippingCostPopoverAction\"]').click();");
	}

	private WebElement waitUntilElementIsVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
}
