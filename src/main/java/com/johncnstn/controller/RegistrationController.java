package com.johncnstn.controller;

import com.johncnstn.data.dto.UserDto;
import com.johncnstn.data.entity.User;
import com.johncnstn.data.service.UserService;
import com.johncnstn.exception.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result) {

        if (!result.hasErrors()) {
            createUserAccount(userDto, result);
        }

        if (result.hasErrors()) {
            return "registration";
        } else {
            return "redirect:/login";
        }
    }

        private User createUserAccount(UserDto accountDto, BindingResult result) {
        User registered;
        try {
            registered = userService.registerNewUserAccount(accountDto);
        } catch (UsernameExistsException e) {
            return null;
        }
        return registered;
    }
}

