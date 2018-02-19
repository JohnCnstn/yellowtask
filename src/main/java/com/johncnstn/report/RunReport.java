package com.johncnstn.report;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class RunReport {
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
