package com.github.nikiene.todo_list.action;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nikiene.todo_list.model.UserModel;
import com.github.nikiene.todo_list.repositories.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("")
    public ResponseEntity<Object> postUser(@RequestBody UserModel user) {
        var exists = this.userRepository.findByUsername(user.getUsername());

        if (exists != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        BCrypt.Hasher hasher = BCrypt.withDefaults();
        var hashedPassword = hasher.hashToString(12, user.getPassword().toCharArray());

        user.setPassword(hashedPassword);
        var createdUser = this.userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
