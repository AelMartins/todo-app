package com.app.todo.service;

import com.app.todo.model.Task;
import com.app.todo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl TaskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task1 = new Task("Tarefa 1", "Descrição 1", "Pendente", "Observações 1");
        task1.setId(1L);
        task2 = new Task("Tarefa 2", "Descrição 2", "Em Andamento", "Observações 2");
        task2.setId(2L);
    }

    @Test
    void createTask_needToSaveTeskAndReturnSavedTesk() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);
        Task newTask = TaskService.createTask(new Task("Nova Tarefa", "Nova Descrição", "Pendente", ""));
        assertNotNull(newTask.getId());
        assertEquals("Nova Tarefa", newTask.getName());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void searchTaskById_needToReturnExistentTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        Optional<Task> findedTask = TaskService.searchTaskById(1L);
        assertTrue(findedTask.isPresent());
        assertEquals("Tarefa 1", findedTask.get().getName());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void searchTaskById_needToReturnEmptyOptionalIfTaskDontExist() {
        when(taskRepository.findById(3L)).thenReturn(Optional.empty());
        Optional<Task> findedTask = TaskService.searchTaskById(3L);
        assertTrue(findedTask.isEmpty());
        verify(taskRepository, times(1)).findById(3L);
    }

    @Test
    void listAllTasks_needToReturnTasksList() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));
        List<Task> tasksList = TaskService.listAllTasks();
        assertEquals(2, tasksList.size());
        assertEquals("Tarefa 1", tasksList.get(0).getName());
        assertEquals("Tarefa 2", tasksList.get(1).getName());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void updateTask_needUpdateExistentTaskAndReturnUpdatedTask() {
        Task updatedTask = new Task("Tarefa 1 Atualizada", "Nova Descrição", "Concluída", "Novas Observações");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = TaskService.updateTask(1L, updatedTask);

        assertNotNull(result);
        assertEquals("Tarefa 1 Atualizada", result.getName());
        assertEquals("Concluída", result.getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTask_needReturnNullIfTaskDontExist() {
        Task updatedTask = new Task("Tarefa Atualizada", "Descrição", "Status", "");
        when(taskRepository.findById(3L)).thenReturn(Optional.empty());
        Task result = TaskService.updateTask(3L, updatedTask);
        assertNull(result);
        verify(taskRepository, times(1)).findById(3L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_needToCallDeleteByIdMethodFromRepository() {
        doNothing().when(taskRepository).deleteById(1L);
        TaskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
