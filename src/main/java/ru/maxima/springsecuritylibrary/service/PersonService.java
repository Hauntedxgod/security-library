package ru.maxima.springsecuritylibrary.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxima.springsecuritylibrary.models.PersonRegistration;
import ru.maxima.springsecuritylibrary.repositories.PersonRepository;


@Service
public class PersonService {
    private final PersonRepository peopleRepository;

    @Autowired
    public PersonService(PersonRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public PersonRegistration findByName(String name){
        return peopleRepository.findByName(name);
    }

}
