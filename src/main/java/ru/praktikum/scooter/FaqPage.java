package ru.praktikum.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.praktikum.scooter.MainPage;

import java.util.List;

public class FaqPage extends MainPage {

    private final By faqSection = By.className("Home_FAQ__3uVm4");
    private final By cookieButton = By.xpath("//button[text()='да все привыкли']");
    private final By allQuestions = By.cssSelector("[data-accordion-component='AccordionItemButton']");

    public FaqPage(WebDriver driver) {
        super(driver);
    }

    public void closeCookieBanner() {
        try {
            WebElement cookieBtn = driver.findElement(cookieButton);
            if (cookieBtn.isDisplayed()) {
                cookieBtn.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieButton));
            }
        } catch (Exception e) {
            // Баннер уже закрыт
        }
    }

    public void scrollToFAQSection() {
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(faqSection));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                section
        );
    }

    // Основной метод: клик по вопросу по тексту и получение ответа
    public String getAnswerTextForQuestion(String questionText) {
        // Закрываем cookie-баннер
        closeCookieBanner();

        // Находим и кликаем по вопросу
        WebElement questionElement = findQuestionByText(questionText);
        clickQuestion(questionElement);

        // Находим и возвращаем текст ответа
        return getAnswerTextForClickedQuestion();
    }

    // Найти вопрос по тексту
    private WebElement findQuestionByText(String questionText) {
        // Сначала пробуем найти по точному тексту
        List<WebElement> questions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allQuestions));

        for (WebElement question : questions) {
            String actualText = question.getText().trim();
            if (actualText.equals(questionText)) {
                return question;
            }
        }

        // Если не нашли точное совпадение, ищем по части текста
        for (WebElement question : questions) {
            String actualText = question.getText().trim();
            if (actualText.contains(questionText) || questionText.contains(actualText)) {
                return question;
            }
        }

        // Если вопрос не найден, выбрасываем исключение
        throw new RuntimeException("Вопрос с текстом '" + questionText + "' не найден на странице");
    }

    // Кликнуть по вопросу
    private void clickQuestion(WebElement question) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                question
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-accordion-component='AccordionItemPanel']:not([hidden])")
        ));
    }

    // Получить текст ответа для кликнутого вопроса
    private String getAnswerTextForClickedQuestion() {
        // Ждем, пока появится хотя бы один не скрытый ответ
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-accordion-component='AccordionItemPanel']:not([hidden])")
        ));

        // Находим все видимые (не скрытые) ответы
        List<WebElement> answers = driver.findElements(
                By.cssSelector("[data-accordion-component='AccordionItemPanel']:not([hidden])")
        );

        if (answers.isEmpty()) {
            throw new RuntimeException("Не найден открытый ответ на вопрос");
        }

        // Берем первый видимый ответ (должен быть только один открытый)
        return answers.get(0).getText().trim();
    }
}