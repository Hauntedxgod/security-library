package ru.maxima.springsecuritylibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maxima.springsecuritylibrary.models.PersonRegistration;
import ru.maxima.springsecuritylibrary.service.AdminService;
import ru.maxima.springsecuritylibrary.service.PersonService;
import ru.maxima.springsecuritylibrary.service.RegistrationService;
import ru.maxima.springsecuritylibrary.validation.PersonValidator;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator validator;

    private final PersonService service;

    private final AdminService adminService;


    private final RegistrationService registrationService;

    @Autowired
    public AuthController(PersonValidator validator, PersonService service, AdminService adminService, RegistrationService registrationService) {
        this.validator = validator;
        this.service = service;
        this.adminService = adminService;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String giveRegistrationPage(@ModelAttribute("personFromPage") PersonRegistration person){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("personFromPage") PersonRegistration person , BindingResult result){

        validator.validate(person , result);

        if (result.hasErrors()){
            return "/auth/registration";
        }

        registrationService.save(person);

        return "redirect:/auth/login";
    }


    @GetMapping("/admin")
    private String admin(){
        adminService.adminRole();
        return "auth/admin";
    }

}
