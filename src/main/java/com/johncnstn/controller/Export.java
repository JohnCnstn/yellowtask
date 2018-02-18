package com.johncnstn.controller;

import com.johncnstn.data.entity.Entry;
import com.johncnstn.data.entity.User;
import com.johncnstn.data.repository.EntryRepository;
import com.johncnstn.data.repository.UserRepository;
import com.johncnstn.model.DataToCurrencyRate;
import com.johncnstn.report.CurrencyRate;
import com.johncnstn.view.PdfView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class Export {

    @Autowired
    private DataToCurrencyRate dataToCurrencyRate;

    @Qualifier("entryRepository")
    @Autowired
    private EntryRepository entryRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(Model model) {
        model.addAttribute("todayCurrencyRates", getTodayForexRates());
        return "";
    }

    @GetMapping("/exchangeRates")
    public String handleForexRequest(Model model) {
        model.addAttribute("todayCurrencyRates", getTodayForexRates());
        return "forexView";
    }

    public List<CurrencyRate> getTodayForexRates() {
        //dummy rates

        List<Entry> entryList = entryRepository.findAllByUserId(getPrincipal().getId());

        return dataToCurrencyRate.da(entryList);
    }

    private User getPrincipal() {
        return userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
