package com.johncnstn.report;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Component
public class CurrencyRate {
    @Setter
    @Getter
    private String avgSpeed;

    @Setter
    @Getter
    private String avgTime;

    @Setter
    @Getter
    private int totalDistance;

    @Setter
    @Getter
    private String week;
}
