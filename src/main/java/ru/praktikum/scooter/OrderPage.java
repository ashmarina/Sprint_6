package ru.praktikum.scooter;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderPage extends MainPage {

    // Локаторы для первой страницы
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.className("select-search__input");
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");

    // Локаторы для второй страницы
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodField = By.className("Dropdown-placeholder");
    private final By colorBlack = By.id("black");
    private final By colorGrey = By.id("grey");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[text()='Назад']/following-sibling::button[text()='Заказать']");

    // Локаторы для окна
    private final By confirmButton = By.xpath("//button[text()='Да']");
    private final By successMessage = By.className("Order_ModalHeader__3FDaJ");

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    // Метод для заполнения первой страницы
    public void fillFirstStep(String name, String surname, String address,
                              String metroStation, String phone) {
        // Ждем загрузки формы
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));

        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);

        // Выбор метро
        driver.findElement(metroField).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[@class='select-search__options']")
        ));

        WebElement station = driver.findElement(
                By.xpath("//div[text()='" + metroStation + "' and @class='Order_Text__2broi']")
        );
        station.click();

        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();

        // Ждем вторую страницу
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
    }

    // Метод для заполнения второй страницы
    public void fillSecondStep(String date, String rentalPeriod,
                               String color, String comment) {
        // Дата
        WebElement dateInput = driver.findElement(dateField);
        dateInput.click();
        dateInput.clear();
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);

        // Срок аренды
        driver.findElement(rentalPeriodField).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='Dropdown-menu']")
        ));
        driver.findElement(By.xpath("//div[text()='" + rentalPeriod + "']")).click();

        // Цвет
        if ("black".equals(color)) {
            driver.findElement(colorBlack).click();
        } else if ("grey".equals(color)) {
            driver.findElement(colorGrey).click();
        }

        // Комментарий
        if (comment != null && !comment.isEmpty()) {
            driver.findElement(commentField).sendKeys(comment);
        }

        // Нажимаем "Заказать"
        WebElement orderBtn = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        orderBtn.click();
    }

    // Метод подтверждения заказа
    public void confirmOrder() {
        System.out.println("Ожидаем появления диалогового окна подтверждения...");

        // Ждем появления кнопки "Да"
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
        System.out.println("Кнопка 'Да' найдена, кликаем...");
        confirmBtn.click();
    }

    // Метод проверки успешности заказа
    public boolean isOrderSuccess() {
        try {
            WebElement successElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(successMessage)
            );

            String actualText = successElement.getText().trim();
            System.out.println("Текст в модальном окне: '" + actualText + "'");

            return actualText.contains("Заказ оформлен");
        } catch (TimeoutException e) {
            System.out.println("Модальное окно с подтверждением заказа не появилось");
            return false;
        }
    }
}