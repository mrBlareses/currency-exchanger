package org.mushroom.currencyexchanger.dto;

import lombok.Data;
import org.mushroom.currencyexchanger.entity.Currency;

@Data
public class CurrencyDto {
    private long id;
    private String code;
    private String fullName;
    private String sign;

    public static CurrencyDto fromCurrency(Currency currency) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setId(currency.getId());
        currencyDto.setCode(currency.getCode());
        currencyDto.setFullName(currency.getFullName());
        currencyDto.setSign(currency.getSign());
        return currencyDto;
    }
}
