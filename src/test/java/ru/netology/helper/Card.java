package ru.netology.helper;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Card {
    CARD_DTO,
    APPROVED("1111 2222 3333 4444", "05", "23", "CHUCK NORRIS", "951"),
    DECLINED("5555 6666 7777 8888", "11", "22", "CHUCK NORRIS", "159"),
    OUT_SCOPE("3434 7878 5555 1010", "12", "24", "ADRIANO CELENTANO", "111"),
    INVALID("0000 0000 0000 0000", "00", "24", "0", "000"),
    INVALID_WITHOUT_NUMBER("", "11", "22", "CHUCK NORRIS", "159");

    private String number;
    private String month;
    private String year;
    private String holder;
    private String cvc;
}

