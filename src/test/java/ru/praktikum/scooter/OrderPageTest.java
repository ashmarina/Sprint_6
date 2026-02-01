package ru.praktikum.scooter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderPageTest extends MainPageTest {

    private static Stream<Arguments> dataTest() {
        return Stream.of(
                Arguments.of("Иван", "Иванов", "ул. Чертановская, 28",
                        "Пражская", "+79996595674",
                        "12.06.2026", "трое суток", "black",
                        "Квартира 111", true),

                Arguments.of("Елена", "Воробей", "Б. Черёмушкинская, 34",
                        "Академическая", "+79636657378",
                        "11.07.2026", "сутки", "grey",
                        "Нет лифта", false)
        );
    }

    @ParameterizedTest(name = "Заказ: {0} {1}, кнопка: {9}")
    @MethodSource("dataTest")
    public void orderScooterTest(
            String name, String surname, String address,
            String metroStation, String phone, String date,
            String rentalPeriod, String color, String comment,
            boolean useTopButton) {

        System.out.println("\n=== ТЕСТ ЗАКАЗА ===");
        System.out.println("Клиент: " + name + " " + surname);
        System.out.println("Кнопка: " + (useTopButton ? "ВЕРХНЯЯ" : "НИЖНЯЯ"));
        System.out.println("Браузер: " + (useChrome ? "Chrome" : "Firefox"));

        // Инициализируем Page Objects
        HomePage homePage = new HomePage(driver);
        OrderPage orderPage = new OrderPage(driver);

        // Закрываем куки и нажимаем кнопку заказа
        homePage.closeCookieBanner();

        if (useTopButton) {
            homePage.clickOrderButtonTop();
        } else {
            homePage.clickOrderButtonBottom();
        }

        // Заполняем первую страницу
        orderPage.fillFirstStep(name, surname, address, metroStation, phone);

        // Заполняем вторую страницу (включая нажатие кнопки "Заказать")
        orderPage.fillSecondStep(date, rentalPeriod, color, comment);

        // Подтверждаем заказ
        orderPage.confirmOrder();

        // В Firefox - должно работать
        boolean isSuccess = orderPage.isOrderSuccess();
        System.out.println("РЕЗУЛЬТАТ: " + (isSuccess ? "ЗАКАЗ УСПЕШНО СОЗДАН!" : "ЗАКАЗ НЕ СОЗДАН"));

        assertTrue(isSuccess, "Заказ не был создан успешно. Ожидалось сообщение 'Заказ оформлен'");
    }
}
