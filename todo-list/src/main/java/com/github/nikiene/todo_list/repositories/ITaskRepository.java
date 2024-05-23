package com.github.nikiene.todo_list.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nikiene.todo_list.model.TaskModel;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByOwnerUserID(UUID ownerUserID);
}
