package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class FormCardTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldPositiveResultTest1() {
        $("[data-test-id=city] .input__control").setValue("Тюмень");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петров-Сидоров Петр");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".checkbox").click();
        $(".button").click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    void shouldPositiveResultTest2() {
        $("[data-test-id=city] .input__control").setValue("Тюмень");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петр Петров");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".checkbox").click();
        $(".button").click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    void shouldFailValidationForDateWithErrorMessage1() {
        $("[data-test-id=city] .input__control").setValue("Тюмень");
        String currentDate = generateDate(0, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петр Петров");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldFailValidationForDateWithErrorMessage2() {
        $("[data-test-id=city] .input__control").setValue("Тюмень");
        String currentDate = generateDate(1, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петр Петров");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldFailValidationForDateWithErrorMessage3() {
        $("[data-test-id=city] .input__control").setValue("Тюмень");
        String currentDate = generateDate(2, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петров Сидоров Петр");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldFailValidationForCityWithErrorMessage1() {
        $("[data-test-id=city] .input__control").setValue("Лангепас");
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петров Сидоров Петр");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=city] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldFailValidationForCityWithErrorMessage2() {
        $("[data-test-id=city] .input__control").setValue("");
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петров Сидоров Петр");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=city] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFailValidationForNameWithErrorMessage1() {
        $("[data-test-id=city] .input__control").setValue("Нарьян-Мар");
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Petrov Oleg");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=name] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldFailValidationForNameWithErrorMessage2() {
        $("[data-test-id=city] .input__control").setValue("Нарьян-Мар");
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("57657665/*/-*?*?::?&&^^*%$%#");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=name] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldFailValidationForNameWithErrorMessage3() {
        $("[data-test-id=city] .input__control").setValue("Нарьян-Мар");
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=name] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFailValidationForPhoneWithErrorMessage1() {
        $("[data-test-id=city] .input__control").setValue("Нарьян-Мар");
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петров Сидоров Петр");
        $("[data-test-id=phone] .input__control").setValue("+7901234567");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldFailValidationForPhoneWithErrorMessage2() {
        $("[data-test-id=city] .input__control").setValue("Нарьян-Мар");
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петров Сидоров Петр");
        $("[data-test-id=phone] .input__control").setValue("");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFailValidationForPhoneWithErrorMessage3() {
        $("[data-test-id=city] .input__control").setValue("Нарьян-Мар");
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петров Сидоров Петр");
        $("[data-test-id=phone] .input__control").setValue("ytcucutcut");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldFailValidationForPhoneWithErrorMessage4() {
        $("[data-test-id=city] .input__control").setValue("Нарьян-Мар");
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петров Сидоров Петр");
        $("[data-test-id=phone] .input__control").setValue("+888888888888888888888888888");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldFailValidationForAllDataWithErrorMessage() {
        $("[data-test-id=city] .input__control").setValue("Tokio");
        String currentDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Grigory Petrov");
        $("[data-test-id=phone] .input__control").setValue("+444444444444444444444444444444");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=city] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldFailValidationForClickCheckboxWithErrorMessage() {
        $("[data-test-id=city] .input__control").setValue("Тюмень");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(currentDate);
        $("[data-test-id=name] .input__control").setValue("Петр Петров");
        $("[data-test-id=phone] .input__control").setValue("+79012345678");
        $(".button").click();
        $(".checkbox.input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
