package com.github.nikiene.todo_list.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.github.nikiene.todo_list.enums.PriorityEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @Column(unique = true)
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    private UUID ownerUserID;

    @Column(length = 50)
    private String title;

    @Column
    private String description;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private boolean isDone;

    @Column
    private LocalDateTime doneAt;

    @Column
    private PriorityEnum priority;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("Title must be less than 50 characters");
        }
        this.title = title;
    }
}
