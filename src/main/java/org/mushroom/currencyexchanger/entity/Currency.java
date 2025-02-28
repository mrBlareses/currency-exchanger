package org.mushroom.currencyexchanger.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Currency {
    private Long id;
    private String code;
    private String fullName;
    private String sign;

    public Currency(String code, String fullName, String sign) {
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }
}
