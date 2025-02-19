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
        CurrencyDto dto = new CurrencyDto();
        dto.setId(currency.getId());
        dto.setCode(currency.getCode());
        dto.setFullName(currency.getFullName());
        dto.setSign(currency.getSign());
        return dto;
    }
}
