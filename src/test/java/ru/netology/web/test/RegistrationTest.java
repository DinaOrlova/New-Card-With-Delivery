package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.data.RegistrationInfo;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationTest {

    public void deleteDate() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"A"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.DELETE));
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldTestWithoutRefreshPage() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateByInfo();
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        deleteDate();
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.selectDatePlusDaysFromCurrent(5));
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на "
                        +DataGenerator.Registration.selectDatePlusDaysFromCurrent(5)));
        deleteDate();
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.selectDatePlusDaysFromCurrent(15));
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldBe(visible);
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на "
                        +DataGenerator.Registration.selectDatePlusDaysFromCurrent(15)));
    }

    @Test
    void shouldTestWithRefreshPage() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateByInfo();
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        deleteDate();
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.selectDatePlusDaysFromCurrent(4));
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на "
                        +DataGenerator.Registration.selectDatePlusDaysFromCurrent(4)));
        refresh();
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        deleteDate();
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.selectDatePlusDaysFromCurrent(17));
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldBe(visible);
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на "
                        +DataGenerator.Registration.selectDatePlusDaysFromCurrent(17)));
    }

    @Test
    void shouldTestWithReopenPage() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateByInfo();
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        deleteDate();
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.selectDatePlusDaysFromCurrent(6));
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на "
                        +DataGenerator.Registration.selectDatePlusDaysFromCurrent(6)));
        closeWindow();
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        deleteDate();
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.selectDatePlusDaysFromCurrent(14));
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldBe(visible);
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на "
                        +DataGenerator.Registration.selectDatePlusDaysFromCurrent(14)));
    }

    @Test
    void shouldTestNameWithLetterEWithDots() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateByInfo();
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        deleteDate();
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.selectDatePlusDaysFromCurrent(5));
        $("[data-test-id=name] input").setValue("Артёмов Фёдор");
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на "
                        +DataGenerator.Registration.selectDatePlusDaysFromCurrent(5)));
    }

    @Test
    void shouldTestPhoneWithLessThan11Digits() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateByInfo();
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        deleteDate();
        $("[data-test-id=date] input").setValue(DataGenerator.Registration.selectDatePlusDaysFromCurrent(5));
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue("+700000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=phone] .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678. "));
    }
}
