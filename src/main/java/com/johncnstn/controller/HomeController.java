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
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home() {
        return "/home";
    }

}
