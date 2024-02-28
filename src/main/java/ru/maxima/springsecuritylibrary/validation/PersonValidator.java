package ru.maxima.springsecuritylibrary.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maxima.springsecuritylibrary.models.PersonRegistration;
import ru.maxima.springsecuritylibrary.security.PersonDetailsService;


@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService service;

    @Autowired
    public PersonValidator(PersonDetailsService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PersonRegistration.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonRegistration p = (PersonRegistration) target;

        try {
            service.loadUserByUsername(p.getName());
        } catch (UsernameNotFoundException e) {
            return;
        }

        errors.rejectValue("username" , "100" , "User with this nickname existed");

    }
}
