package com.jwt.controller;

import com.jwt.model.User;
import com.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @GetMapping("/")
    public List<User> getAllUser() {
        System.out.println("Getting all users");
        return userService.getUserList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{name}")
    public User getUser(@PathVariable("name") String userName) {
        System.out.println("getting user by name: "+userName);
        return userService.getUser(userName);
    }

    @PostMapping("/")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping ("/{name}")
    public User updateUser(@PathVariable("name") String userName,@RequestBody User user) {
        System.out.println("getting user by name: "+userName);
        return userService.updateUser(user,userName);
    }
}
