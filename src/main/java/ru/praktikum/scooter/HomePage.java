package ru.praktikum.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends MainPage {

    // Локаторы объявляем как константы
    private final By orderButtonTop = By.className("Button_Button__ra12g");
    private final By orderButtonBottom = By.xpath("//div[contains(@class, 'Home_FinishButton')]//button");
    private final By cookieButton = By.xpath("//button[text()='да все привыкли']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void closeCookieBanner() {
        try {
            driver.findElement(cookieButton).click();
        } catch (Exception e) {
            // Баннер уже закрыт или не найден
        }
    }

    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }

    public void clickOrderButtonBottom() {
        WebElement button = driver.findElement(orderButtonBottom);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", button
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", button
        );
    }

    public boolean isHeaderDisplayed() {
        try {
            return driver.findElement(By.className("Home_Header__iJKdX")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
