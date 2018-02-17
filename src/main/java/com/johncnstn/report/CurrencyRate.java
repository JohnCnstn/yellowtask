package com.johncnstn.report;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

public class CurrencyRate {
    @Setter
    @Getter
    private Date date;

    @Setter
    @Getter
    private BigDecimal avgSpeed;

    @Setter
    @Getter
    private long avgDistance;
}
