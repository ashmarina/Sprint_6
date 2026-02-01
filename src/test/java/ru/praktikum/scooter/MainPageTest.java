package ru.praktikum.scooter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class MainPageTest {
    protected WebDriver driver;
    protected final boolean useChrome = false; // Chrome=true (проверка бага), Firefox=false (успешное поведение)

    @BeforeEach
    public void setUp() {
        if (useChrome) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            System.out.println("=== ЗАПУСК В CHROME ===");
        } else {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            System.out.println("=== ЗАПУСК В FIREFOX ===");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

