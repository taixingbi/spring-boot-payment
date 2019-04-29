package com.example.demo.controller;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository usersRepository) {
        this.userRepository = usersRepository;
    }

    @GetMapping("/users")
    public List<Users> all() {
        return userRepository.findAll();
    }

    @GetMapping("/users/id/{id}")
    public Users findById(@PathVariable final int id ) {

        return userRepository.findById(id);
    }

    @PostMapping("/users/post")
    public Users addUser( Users post) {

        userRepository.save(post);
        return post;

    }

}



