package com.app.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.todo.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Métodos adicionais de consulta devem ser definidos aqui
}
