package ru.maxima.springsecuritylibrary.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.springsecuritylibrary.models.Book;
import ru.maxima.springsecuritylibrary.models.OwnerDTO;
import ru.maxima.springsecuritylibrary.service.BookService;
import ru.maxima.springsecuritylibrary.service.PeopleService;


@Controller
@RequestMapping("/book")
public class BookController {

    private final PeopleService service;
    private final BookService bookService;

    @Autowired
    public BookController(PeopleService service, BookService bookService) {
        this.service = service;
        this.bookService = bookService;
    }

    @GetMapping()
    public String allBook(Model model){
        model.addAttribute("allBook" , bookService.getAllBook());
        return "view-with-all-book";
    }

    @GetMapping("/{id}")
    public String idOfBook(@PathVariable("id")Long id , Model model){
        Book book = bookService.getIdBook(id);
        if (book.getOwner() != null) {
            book.setOwner(service.getPersonId(book.getOwner().getId()));
        }
        model.addAttribute("idBook", book);
        model.addAttribute("getAllPerson", service.getAllPeople());
        model.addAttribute("idPerson" , service.getPersonId(id));
        model.addAttribute("ownerDto", new OwnerDTO());
        return "view-with-book-id";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String getNewBook(Model model){
        model.addAttribute("newBook" , new Book());
        return "view-create-new-book";
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public String createBook(@ModelAttribute("newBook") @Valid Book libraryBook , BindingResult binding){
        if(binding.hasErrors()){
            return "view-create-new-book";
        }
        bookService.saveBook(libraryBook);
        return "redirect:/book";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addowner/{id}")
    public String orderBook(@PathVariable("id") Long id , @ModelAttribute(name = "ownerDto") OwnerDTO ownerDTO
            , BindingResult binding ){
        if (ownerDTO.getOwnerId() != null){
            bookService.addOwner(id , Long.valueOf(ownerDTO.getOwnerId()));
        }
        return "redirect:/book";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/edit")
    public String editedBook(@PathVariable("id" )  Long id , Model model ){
        model.addAttribute("editedBook" , bookService.getIdBook(id));
        return "view-to-edit-book";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}")
    public String updateEditedBook(@PathVariable("id") Long id , @ModelAttribute("editedBook")
    @Valid Book editedBook , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "view-to-edited-book";
        }
        bookService.updateBook(id , editedBook);
        return "redirect:/book";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id ) {
        bookService.deleteBook(id);
        return "redirect:/book";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/deletes/addowner/{id}")
    public String deletePersonBook(@PathVariable("id") Long id){
        bookService.deleteOfPersonBook(id);
        return "redirect:/book";
    }
}
