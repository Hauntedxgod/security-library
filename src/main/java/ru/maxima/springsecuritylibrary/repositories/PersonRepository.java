package ru.maxima.springsecuritylibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.springsecuritylibrary.models.PersonRegistration;


@Repository
public interface PersonRepository extends JpaRepository<PersonRegistration, Long> {
    PersonRegistration findByName(String name);
}
