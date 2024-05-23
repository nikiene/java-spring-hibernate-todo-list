package com.github.nikiene.todo_list.action;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nikiene.todo_list.model.UserModel;
import com.github.nikiene.todo_list.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/create")
    public UserModel createUser(@RequestBody UserModel user) {
        var createdUser = this.userRepository.save(user);

        return createdUser;
    }
}
