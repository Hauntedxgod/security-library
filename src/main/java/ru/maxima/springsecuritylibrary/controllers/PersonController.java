package ru.maxima.springsecuritylibrary.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.springsecuritylibrary.models.OwnerDTO;
import ru.maxima.springsecuritylibrary.models.Person;
import ru.maxima.springsecuritylibrary.service.BookService;
import ru.maxima.springsecuritylibrary.service.PeopleService;


@Controller
@RequestMapping("/people")
public class PersonController {

   private final PeopleService service;
   private final BookService bookService;

   @Autowired
    public PersonController(PeopleService service, BookService bookService) {
        this.service = service;
       this.bookService = bookService;
   }

    @GetMapping()
    public String allPeople(Model model){
       model.addAttribute("allPeople" , service.getAllPeople());
        return "view-with-alll-people";
    }

    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") Long id , Model model){
        Person person = service.getPersonId(id);
        person.setBooks(bookService.getOwnerId(id));
        model.addAttribute("allBook" , bookService.getAllBook());
        model.addAttribute("personById", person);
        model.addAttribute("getBookId" , bookService.getIdBook(id));
        model.addAttribute("ownerDto", new OwnerDTO());
        return "view-with-person-by-id";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String giveToUserPageToCreateNewPerson(Model model) {
       model.addAttribute("newPerson" , new Person());
        return "view-to-create-new-person";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public String createNewPerson(@ModelAttribute("newPerson") @Valid Person person , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "view-to-create-new-person";
        }
        service.save(person);
        return "redirect:/people";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addowner/{id}")
    public String orderPerson(@PathVariable("id") Long id , @ModelAttribute(name = "ownerDto") OwnerDTO ownerDTO
            , BindingResult binding ){
        if (ownerDTO.getOwnerId() != null){
            service.addOwner(id , Long.valueOf(ownerDTO.getOwnerId()));
        }
        return "redirect:/people";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/deletePerson/addowner/{id}")
    public String deletePersonBook(@PathVariable("id") Long id) {
        service.deleteBookFromPerson(id);
        return "redirect:/people";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/edit")
    public String giveToUserPageToEditPerson(@PathVariable("id") Long id, Model model){
        model.addAttribute("editedPerson" , service.getPersonId(id));
        return "view-to-edit-person";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}")
    public String updateEditedPerson(@PathVariable("id") Long id ,
                                     @ModelAttribute("editedPerson") @Valid Person editedPerson , BindingResult binding){
        if (binding.hasErrors()){
            return "view-to-edit-person";
        }
        service.updatePerson(editedPerson , id);
        return "redirect:/people";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") Long id ) {
        service.deletePerson(id);
        return "redirect:/people";


    }
}
