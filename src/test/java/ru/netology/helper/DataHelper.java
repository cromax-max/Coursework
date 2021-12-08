package ru.netology.helper;

public class DataHelper {

    public static CardDto createApprovedCard() {
        return new CardDto("1111 2222 3333 4444", "05", "23", "CHUCK NORRIS", "951");
    }

    public static CardDto createDeclinedCard() {
        return new CardDto("5555 6666 7777 8888", "11", "22", "CHUCK NORRIS", "159");
    }

    public static CardDto createInvalidCardWithoutNumber() {
        return new CardDto("", "11", "22", "CHUCK NORRIS", "159");
    }

    public static CardDto createInvalidCard() {
        return new CardDto("0000 0000 0000 0000", "00", "24", "0", "000");
    }
}
