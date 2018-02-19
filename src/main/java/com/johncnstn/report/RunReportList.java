package com.johncnstn.report;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RunReportList {
    @Getter
    private List<RunReport> runReportList = new ArrayList<>();
}
