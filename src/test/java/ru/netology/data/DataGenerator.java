package ru.netology.data;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Data
@RequiredArgsConstructor

public class DataGenerator {

    public static class Generate {
        private Generate() {
        }

        public static AppRegistration generateNewApp(String locale) {
            Faker faker = new Faker(new Locale("ru"));
            return new AppRegistration(generateCity(),faker.name().lastName() + " " + faker.name().firstName(), faker.numerify("+7##########"));
        }

        public static String generateCity() {
            Random random = new Random();
            List<String> validCity = Arrays.asList("Москва", "Сыктывкар", "Архангельск", "Анадырь");
            String city = validCity.get(random.nextInt(validCity.size()));
            return city;
        }

        public static String generateSpecialName() {
            Random random = new Random();
            List<String> specialNameWithSpecialLetter = Arrays.asList("Артём Булатов", "Алёна Апина", "Саша Ёшка");
            String specialName = specialNameWithSpecialLetter.get(random.nextInt(specialNameWithSpecialLetter.size()));
            return specialName;
        }

        public static String generateInvalidName() {
            String invalidName = "Marty McFly";
            return invalidName;
        }

        public static String generateInvalidCity() {
            String invalidCity = "New York";
            return invalidCity;
        }

        public static String generateCardDeliveryDate() {
            LocalDate firstDate = LocalDate.now().plusDays(3);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return firstDate.format(formatter);
        }

        public static String generateNewCardDeliveryDate() {
            LocalDate secondDate = LocalDate.now().plusDays(30);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return secondDate.format(formatter);
        }

        public static String generateSpecialCardDeliveryDate() {
            LocalDate specialDate = LocalDate.now().plusYears(400);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return specialDate.format(formatter);
        }

        public static String generateInvalidDate() {
            String invalidDate = "16.16.2200";
            return invalidDate;
        }

    }
}
