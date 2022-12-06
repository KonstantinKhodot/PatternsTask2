package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=\"login\"]").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"]").setValue(registeredUser.getPassword());
        $("[text()='Продолжить']").click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id=\"login\"]").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=\"password\"]").setValue(notRegisteredUser.getPassword());
        $("[text()='Продолжить']").click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id=\"login\"]").setValue(blockedUser.getLogin());
        $("[data-test-id=\"password\"]").setValue(blockedUser.getPassword());
        $("[text()='Продолжить']").click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id=\"login\"]").setValue(wrongLogin.getLogin());
        $("[data-test-id=\"password\"]").setValue(registeredUser.getPassword());
        $("[text()='Продолжить']").click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id=\"login\"]").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"]").setValue(wrongPassword.getPassword());
        $("[text()='Продолжить']").click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.visible);
    }
}
