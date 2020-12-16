package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.AppRegistration;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class AppCardDeliveryTest {
    private SelenideElement cityField = $("[data-test-id=city] input");
    private SelenideElement dateField = $("[data-test-id=date] input");
    private SelenideElement personName = $("[data-test-id=name] input");
    private SelenideElement phoneNumber = $("[data-test-id=phone] input");
    private SelenideElement agreementField = $("[data-test-id=agreement]");
    private SelenideElement planButton = $$("button").find(exactText("Запланировать"));
    private SelenideElement replanButton = $$("button").find(exactText("Перепланировать"));
    private SelenideElement successMessage = $(withText("Успешно!"));
    private SelenideElement successNotificationContent = $("[data-test-id=success-notification] .notification__content");
    AppRegistration userData = DataGenerator.Generate.generateNewApp("ru");

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void shouldOpenBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldConfirmReplanRequest() {
        cityField.setValue(userData.getCity());
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.Generate.generateCardDeliveryDate());
        personName.setValue(userData.getName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Generate.generateCardDeliveryDate()));
        dateField.doubleClick().sendKeys(BACK_SPACE);
        dateField.setValue(DataGenerator.Generate.generateNewCardDeliveryDate());
        planButton.click();
        replanButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Generate.generateNewCardDeliveryDate()));
    }

    @Test
    void shouldConfirmSpecialNameRequest () {
        cityField.setValue(userData.getCity());
        dateField.click();
        dateField.setValue(DataGenerator.Generate.generateCardDeliveryDate());
        personName.setValue(DataGenerator.Generate.generateSpecialName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        successMessage.waitUntil(visible, 5000);
        successNotificationContent.shouldHave(text("Встреча успешно запланирована на " + DataGenerator.Generate.generateCardDeliveryDate()));
    }

    @Test
    void shouldNotConfirmWrongNameRequest () {
        cityField.setValue(userData.getCity());
        personName.setValue(DataGenerator.Generate.generateInvalidName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmRequest () {
        personName.setValue(userData.getName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        $(withText("Поле обязательно для заполнения")).waitUntil(visible, 5000);
    }

    @Test
    void shouldNotConfirmWrongDataRequest () {
        cityField.setValue(DataGenerator.Generate.generateInvalidCity());
        personName.setValue(userData.getName());
        phoneNumber.setValue(userData.getPhone());
        agreementField.click();
        planButton.click();
        $(byText("Доставка в выбранный город недоступна")).waitUntil(visible, 5000);
    }

        @Test
        void shouldNotConfirmWrongDateRequest () {
            cityField.setValue(userData.getCity());
            dateField.doubleClick().sendKeys(BACK_SPACE);
            dateField.setValue(DataGenerator.Generate.generateSpecialCardDeliveryDate());
            personName.setValue(userData.getName());
            phoneNumber.setValue(userData.getPhone());
            agreementField.click();
            planButton.click();
            $(withText("Неверно введена дата")).waitUntil(visible, 5000);
        }

        @Test
        void shouldNotConfirmWrongDateRequestV2 () {
            cityField.setValue(userData.getCity());
            dateField.doubleClick().sendKeys(BACK_SPACE);
            dateField.setValue(DataGenerator.Generate.generateInvalidDate());
            personName.setValue(userData.getName());
            phoneNumber.setValue(userData.getPhone());
            agreementField.click();
            planButton.click();
            $(withText("Неверно введена дата")).waitUntil(visible, 5000);
        }

}
