package com.johncnstn.controller;

import com.johncnstn.data.entity.Entry;
import com.johncnstn.report.CurrencyRate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/exchangeRates")
    public String handleForexRequest(Model model) {
        model.addAttribute("todayCurrencyRates", getTodayForexRates());
        return "forexView";
    }

    private List<CurrencyRate> getTodayForexRates() {
        //dummy rates
        List<CurrencyRate> currencyRates = new ArrayList<>();
        Date today = new Date();

        CurrencyRate cr = new CurrencyRate();
        cr.setDate(today);
        cr.setAvgSpeed(BigDecimal.valueOf(14.01));

        currencyRates.add(cr);

//        for (int i = 0; i < currencies.size(); i += 2) {
//            CurrencyRate cr = new CurrencyRate();
//            cr.setDate(today);
//
//            currencyRates.add(cr);
//        }
        return currencyRates;
    }

}
