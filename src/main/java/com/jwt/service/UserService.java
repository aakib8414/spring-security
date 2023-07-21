package com.jwt.service;

import com.jwt.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    List<User> userList = new ArrayList<>();

    public UserService() {
        userList.add(new User("aakib", "123", "abc@gmail.com"));
        userList.add(new User("raza", "231", "zxc@gmail.com"));
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getUser(String name) {
        return userList.stream().filter(e -> e.getUserName().equalsIgnoreCase(name)).
                findAny().orElse(null);
    }

    public User addUser(User user){
        userList.add(user);
        return user;
    }

}
