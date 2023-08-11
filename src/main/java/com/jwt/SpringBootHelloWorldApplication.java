package com.jwt;

import com.jwt.model.User;
import com.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringBootHelloWorldApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHelloWorldApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        User u1 = new User("aakib", encoder.encode("aakib"), "abc@gmail.com", "ROLE_USER",null);
        User u2 = new User("admin", encoder.encode("admin"), "zxc@gmail.com", "ROLE_ADMIN",null);
        this.userRepository.save(u1);
        this.userRepository.save(u2);
    }
}