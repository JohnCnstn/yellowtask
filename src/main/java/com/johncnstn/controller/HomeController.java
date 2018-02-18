package com.johncnstn.controller;

import com.johncnstn.data.entity.Entry;
import com.johncnstn.data.entity.User;
import com.johncnstn.data.repository.EntryRepository;
import com.johncnstn.data.repository.UserRepository;
import com.johncnstn.data.service.EntryService;
import com.johncnstn.model.DataToCurrencyRate;
import com.johncnstn.report.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private DataToCurrencyRate dataToCurrencyRate;

    @Qualifier("userRepository")

    @Autowired
    private UserRepository userRepository;

    @Qualifier("entryRepository")
    @Autowired
    private EntryRepository entryRepository;

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

        List<Entry> entryList = entryRepository.findAllByUserId(getPrincipal().getId());

        return dataToCurrencyRate.da(entryList);
    }


    private User getPrincipal() {
        return userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
