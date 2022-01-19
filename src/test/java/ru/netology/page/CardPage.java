package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CardPage {
    private SelenideElement form = $(".form");
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public CardPage() {
        form.shouldBe(visible);
    }

    public void moneyTransfer(DataHelper.CardInfo fromCardInfo, int amountToTransfer) {
        String amount = Integer.toString(amountToTransfer);
        amountField.setValue(amount);
        fromField.setValue(fromCardInfo.getCardNumber());
        transferButton.click();
    }
}
