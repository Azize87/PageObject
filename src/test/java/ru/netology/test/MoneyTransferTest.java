package ru.netology.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    int amountToTransfer = 1000;
    int newAmountToTransfer = 20000;

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    void asserting() {
        val dashboardPage = new DashboardPage();
        val balance1 = dashboardPage.getFirstCardBalance();
        val balance2 = dashboardPage.getSecondCardBalance();

        if (balance1 - balance2 > 0) {
            val transferAmount = (balance1 - balance2) / 2;
            val cardInfo = DataHelper.getFirstCardInfo();
            val cardPage = dashboardPage.SecondCard();
            cardPage.moneyTransfer(cardInfo, amountToTransfer);
        }
        if (balance2 - balance1 > 0) {
            val transferAmount = (balance2 - balance1) / 2;
            val cardInfo = DataHelper.getSecondCardInfo();
            val cardPage = dashboardPage.FirstCard();
            cardPage.moneyTransfer(cardInfo, amountToTransfer);
        }
    }

    @Test
    void shouldTransferFromFirstCardToSecond() {
        val dashboardPage = new DashboardPage();
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val cardPage = dashboardPage.SecondCard();
        val cardInfo = DataHelper.getFirstCardInfo();
        cardPage.moneyTransfer(cardInfo, 1000);
        val balanceAfterTransactionOnRecharged = DataHelper.checkTheTransferOfMoneyToTheCard(balanceSecondCardBefore, amountToTransfer);
        val balanceAfterTransaction = DataHelper.checkTheTransferOfMoneyFromTheCard(balanceFirstCardBefore, amountToTransfer);
        val balanceFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfter = dashboardPage.getSecondCardBalance();
        assertEquals(balanceAfterTransactionOnRecharged, balanceSecondCardAfter);
        assertEquals(balanceAfterTransaction, balanceFirstCardAfter);

    }

    @Test
    void shouldTransferFromSecondCardToFirst() {
        val dashboardPage = new DashboardPage();
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val cardPage = dashboardPage.FirstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        cardPage.moneyTransfer(cardInfo, 1000);
        val balanceAfterTransactionOnRecharged = DataHelper.checkTheTransferOfMoneyToTheCard(balanceFirstCardBefore, amountToTransfer);
        val balanceAfterTransaction = DataHelper.checkTheTransferOfMoneyFromTheCard(balanceSecondCardBefore, amountToTransfer);
        val balanceFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfter = dashboardPage.getSecondCardBalance();
        assertEquals(balanceAfterTransactionOnRecharged, balanceFirstCardAfter);
        assertEquals(balanceAfterTransaction, balanceSecondCardAfter);

    }

    @Test
    void shouldTransferFromSecondCardToFirstIfAmountTransferIsOverBalance() {
        val dashboardPage = new DashboardPage();
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val cardPage = dashboardPage.FirstCard();
        val cardInfo = DataHelper.getSecondCardInfo();
        cardPage.moneyTransfer(cardInfo, newAmountToTransfer);
        val dashboardPageWithError = new DashboardPage();
        dashboardPageWithError.getNotificationVisible();


    }
}
