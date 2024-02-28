package ru.maxima.springsecuritylibrary.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;

    @NotEmpty(message = "The line with the name of the book must be filled in")
    @Size(min = 1 , max = 30,  message = "Name should be between 1 and 30 characters")
    @Column(name = "nameOfBook")
    private String nameOfBook;

    @NotEmpty(message = "Name should not to be empty")
    @Size(min = 1 , max = 30,  message = "Name should be between 1 and 30 characters")
    @Column(name = "authorName")
    private String authorName;

    @Min(value = 0 , message = "Age should be more than 0")
    @Column(name = "yearOfCreation")
    private int yearOfCreation;


    @ManyToOne
    @JoinColumn(name = "owner" , referencedColumnName = "id")
    private Person owner;
}
