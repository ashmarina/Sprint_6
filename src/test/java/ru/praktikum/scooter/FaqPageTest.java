package ru.praktikum.scooter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FaqPageTest extends MainPageTest {

    //Тексты вопросов и ожидаемые тексты ответов
    private static Stream<Arguments> questionAnswerData() {
        return Stream.of(
                Arguments.of(
                        "Сколько это стоит? И как оплатить?",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."
                ),
                Arguments.of(
                        "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."
                ),
                Arguments.of(
                        "Как рассчитывается время аренды?",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."
                ),
                Arguments.of(
                        "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."
                ),
                Arguments.of(
                        "Можно ли продлить заказ или вернуть самокат раньше?",
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."
                ),
                Arguments.of(
                        "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."
                ),
                Arguments.of(
                        "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."
                ),
                Arguments.of(
                        "Я жизу за МКАДом, привезёте?", //Опечатка на сайте: должно быть "живу"
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."
                )
        );
    }

    @ParameterizedTest(name = "Вопрос: {0}")
    @MethodSource("questionAnswerData")
    public void FAQTest(String questionText, String expectedAnswer) {
        System.out.println("\n=== Тест вопроса: " + questionText + " ===");

        FaqPage faqPage = new FaqPage(driver);

        // Прокручиваем к FAQ секции
        faqPage.scrollToFAQSection();

        // Кликаем по вопросу с привязкой по тексту и получаем ответ
        String actualAnswer = faqPage.getAnswerTextForQuestion(questionText);

        System.out.println("Ожидаемый ответ: " + expectedAnswer);
        System.out.println("Фактический ответ: " + actualAnswer);

        // Проверяем что ответ соответствует ожидаемому
        assertTrue(actualAnswer.equals(expectedAnswer),
                "Ответ на вопрос не соответствует ожидаемому.\n" +
                        "Вопрос: " + questionText + "\n" +
                        "Ожидалось: " + expectedAnswer + "\n" +
                        "Получено: " + actualAnswer);
    }
}
