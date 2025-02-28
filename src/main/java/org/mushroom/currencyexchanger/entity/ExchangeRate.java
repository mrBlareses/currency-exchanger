package org.mushroom.currencyexchanger.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRate {
    private long id;
    private String baseCurrencyId;
    private String targetCurrencyId;
    private BigDecimal rate;
}
