package org.mushroom.currencyexchanger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCurrencyPayload {
    private String code;
    private String name;
    private String sign;
}
