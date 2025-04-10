package com.app.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.todo.model.Task;
import com.app.todo.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> searchTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> optionalExistentTask = taskRepository.findById(id);
        if (optionalExistentTask.isPresent()) {
            Task existentTask = optionalExistentTask.get();
            existentTask.setName(updatedTask.getName());
            existentTask.setDescription(updatedTask.getDescription());
            existentTask.setStatus(updatedTask.getStatus());
            existentTask.setObservations(updatedTask.getObservations());
            return taskRepository.save(existentTask);
        }
        return null;
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
