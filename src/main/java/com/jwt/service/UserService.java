package com.jwt.service;

import com.jwt.model.User;
import com.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    List<User> userList = new ArrayList<>();

    public UserService() {
//        userList.add(new User("aakib", "123", "abc@gmail.com"));
//        userList.add(new User("raza", "231", "zxc@gmail.com"));
    }

    public List<User> getUserList() {

//        return userList;
        return repository.findAll();
    }

    public User getUser(String name) {
//        return userList.stream().filter(e -> e.getUserName().equalsIgnoreCase(name)).
//                findAny().orElse(null);
        User user = this.repository.findUserByUserName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + name));
        return user;
    }

    public User addUser(User user){
//        userList.add(user);
        User usr = repository.save(user);
        return usr;
    }

}
