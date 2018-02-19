package com.johncnstn.controller;

import com.johncnstn.data.entity.Entry;
import com.johncnstn.data.entity.User;
import com.johncnstn.data.repository.EntryRepository;
import com.johncnstn.data.repository.UserRepository;
import com.johncnstn.report.ParseDataToRunReport;
import com.johncnstn.report.RunReportList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private ParseDataToRunReport parseDataToRunReport;

    @Qualifier("entryRepository")
    @Autowired
    private EntryRepository entryRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(Model model) {
        model.addAttribute("runReportList", getRunReportList());
        return "";
    }

    public RunReportList getRunReportList() {

        List<Entry> entryList = entryRepository.findAllByUserId(getPrincipal().getId());

        return parseDataToRunReport.setRunReport(entryList);
    }

    private User getPrincipal() {
        return userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
