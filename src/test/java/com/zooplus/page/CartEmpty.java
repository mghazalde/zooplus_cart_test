package com.zooplus.page;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class CartEmpty {
    WebDriver driver;

    public CartEmpty(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate() throws IOException {
    }

    public void agree() {
    }

    public String getTitle() {
        return driver.getCurrentUrl();
    }
}

