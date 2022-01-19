package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559000000000001");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559000000000002");
    }

    public static int checkTheTransferOfMoneyFromTheCard(int balance, int transferAmount) {
        int finalBalance = balance - transferAmount;
        return finalBalance;
    }

    public static int checkTheTransferOfMoneyToTheCard(int balance, int transferAmount) {
        int finalBalance = balance + transferAmount;
        return finalBalance;
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
    }


}