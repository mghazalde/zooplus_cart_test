package com.zooplus.page;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartEmpty {
	WebDriver driver;

	public CartEmpty(WebDriver driver) {
		this.driver = driver;
	}

	public void addProduct() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		String recommendedProductsDivId = "js-zootopiaRecosContainerEmpty";
		waitUntilElementIsVisible(driver.findElement(By.id(recommendedProductsDivId)));
		js.executeScript("document.querySelector('#" + recommendedProductsDivId + "').scrollIntoView();");
		js.executeScript(
				"document.querySelector('#" + recommendedProductsDivId + " ul li:first-child button').click();");

	}

	private WebElement waitUntilElementIsVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void navigate() throws IOException {
	}

	public void agree() {
	}

	public String getTitle() {
		return driver.getCurrentUrl();
	}
}
