package ru.maxima.springsecuritylibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.springsecuritylibrary.models.Book;

import java.util.List;

@Repository
public interface BookRepositories extends JpaRepository<Book, Long> {


    List<Book> findByOwner_Id(Long id);

}
