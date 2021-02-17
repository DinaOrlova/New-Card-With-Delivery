package ru.netology.web.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {}

    public static class Registration {
        private Registration() {}

        public static RegistrationInfo generateByInfo() {
            Faker faker = new Faker(new Locale("ru"));
            return new RegistrationInfo(
                    faker.address().cityName(),
                    faker.name().fullName(),
                    faker.phoneNumber().cellPhone()
            );
        }

        public static String selectDatePlusDaysFromCurrent(int days) {
            LocalDate deliveryDate = LocalDate.now().plusDays(days);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formatDate = deliveryDate.format(formatter);
            return formatDate;
        }
    }
}
