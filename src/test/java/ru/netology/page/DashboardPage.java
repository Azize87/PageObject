package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement firstCardButton = $$("[data-test-id=action-deposit]").first();
    private SelenideElement secondCardButton = $$("[data-test-id=action-deposit]").last();
    private SelenideElement balance0001 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement balance0002 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    private SelenideElement notification = $("[data-test-id=error-notification]").$(withText("Ошибка"));

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public CardPage FirstCard() {
        firstCardButton.click();
        return new CardPage();
    }

    public CardPage SecondCard() {
        secondCardButton.click();
        return new CardPage();
    }

    public int getFirstCardBalance() {
        val text = balance0001.text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        val text = balance0002.text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public SelenideElement getNotificationVisible() {
        return notification.shouldBe(visible);

    }
}
