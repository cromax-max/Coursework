package ru.netology.helper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentCardDto {
    String number;
    String month;
    String year;
    String holder;
    String cvc;
}
