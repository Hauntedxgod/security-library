package ru.maxima.springsecuritylibrary.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {


    /*Необходимо добавить Spring Security в
    библиотеку и настроить роли возможностью обычным пользователям лишь просматривать книги,
    админы же могут добавлять/удалять/редактировать пользователей/книги
    * */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void adminRole(){
        System.out.println("Yes we can do admin stuff");
    }

}
