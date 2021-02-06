package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.data.RegistrationInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationTest {
    private LocalDate deliveryDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void selectDatePlusDaysFromCurrent(int days) {
        deliveryDate = LocalDate.now().plusDays(days);
        String formatDate = deliveryDate.format(formatter);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"A"));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.DELETE));
        $("[data-test-id=date] input").setValue(formatDate);
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldTestWithoutRefreshPage() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateByInfo();
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        selectDatePlusDaysFromCurrent(5);
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(visible, 15000);
        selectDatePlusDaysFromCurrent(15);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").waitUntil(visible, 15000);
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(visible, 15000);
    }

    @Test
    void shouldTestWithRefreshPage() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateByInfo();
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        selectDatePlusDaysFromCurrent(4);
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(visible, 15000);
        refresh();
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        selectDatePlusDaysFromCurrent(17);
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").waitUntil(visible, 15000);
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(visible, 15000);
    }

    @Test
    void shouldTestWithReopenPage() {
        RegistrationInfo registrationInfo = DataGenerator.Registration.generateByInfo();
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        selectDatePlusDaysFromCurrent(6);
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(visible, 15000);
        closeWindow();
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(registrationInfo.getCity());
        selectDatePlusDaysFromCurrent(14);
        $("[data-test-id=name] input").setValue(registrationInfo.getName());
        $("[data-test-id=phone] input").setValue(registrationInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").waitUntil(visible, 15000);
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(visible, 15000);
    }
}
