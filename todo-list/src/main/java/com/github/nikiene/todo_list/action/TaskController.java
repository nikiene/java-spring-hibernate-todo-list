package com.github.nikiene.todo_list.action;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nikiene.todo_list.enums.PriorityEnum;
import com.github.nikiene.todo_list.model.TaskModel;
import com.github.nikiene.todo_list.repositories.ITaskRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @GetMapping("")
    public ResponseEntity<Object> getTasks() {

        var tasks = this.taskRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PostMapping("")
    public ResponseEntity<Object> postTask(@RequestBody TaskModel task, HttpServletRequest request) {

        var ownerUserID = (UUID) request.getAttribute("ownerUserID");
        task.setOwnerUserID(ownerUserID);

        var currentDateTime = LocalDateTime.now();

        if (task.getCreatedAt() == null) {
            task.setCreatedAt(currentDateTime);
        }

        if (currentDateTime.isBefore(task.getCreatedAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date");
        }

        if (task.getUpdatedAt() == null) {
            task.setUpdatedAt(currentDateTime);
        }

        if (currentDateTime.isBefore(task.getUpdatedAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date");
        }

        if (task.getCreatedAt().isAfter(task.getUpdatedAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date");
        }

        if (task.getPriority() == null) {
            task.setPriority(PriorityEnum.MEDIUM);
        }

        if (task.isDone()) {
            if (task.getDoneAt() == null) {
                task.setDoneAt(currentDateTime);
            }
        }

        if (task.getDoneAt() != null) {
            task.setDone(true);
        }

        if (task.getDoneAt() != null && task.getDoneAt().isBefore(task.getCreatedAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date");
        }
        var createdTask = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

}
