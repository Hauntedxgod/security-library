package ru.maxima.springsecuritylibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.springsecuritylibrary.models.Person;


@Repository
public interface PeopleRepositories extends JpaRepository<Person, Long> {

}
