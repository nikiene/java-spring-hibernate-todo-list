package com.github.nikiene.todo_list.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nikiene.todo_list.model.UserModel;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {

}