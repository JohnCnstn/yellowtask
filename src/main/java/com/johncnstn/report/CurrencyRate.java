package com.johncnstn.report;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class CurrencyRate {
    @Setter
    @Getter
    private Date date;

    @Setter
    @Getter
    private BigDecimal avgSpeed;

    @Setter
    @Getter
    private int totalDistance;

    @Setter
    @Getter
    private int week;
}
