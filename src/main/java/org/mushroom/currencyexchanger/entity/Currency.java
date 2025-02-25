package org.mushroom.currencyexchanger.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    private long id;
    private String code;
    private String fullName;
    private String sign;
}
