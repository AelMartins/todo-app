package com.app.todo.service;

import java.util.List;
import java.util.Optional;

import com.app.todo.model.Task;

public interface TaskService {

    Task createTask(Task task);

    Optional<Task> searchTaskById(Long id);

    List<Task> listAllTasks();

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);
}
