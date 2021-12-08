package ru.netology.helper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardDto {
    private String number;
    private String month;
    private String year;
    private String holder;
    private String cvc;
}
