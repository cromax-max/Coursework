package ru.netology.helper;

public class DataHelper {

    public static PaymentCardDto createApprovedCard() {
        return new PaymentCardDto("1111 2222 3333 4444", "05", "23", "CHUCK NORRIS", "951");
    }

    public static PaymentCardDto createDeclinedCard() {
        return new PaymentCardDto("5555 6666 7777 8888", "11", "22", "CHUCK NORRIS", "159");
    }

    public static PaymentCardDto createInvalidCard() {
        return new PaymentCardDto("", "11", "22", "CHUCK NORRIS", "159");
    }
}
