package com.app.todo.controller;

import com.app.todo.model.Task;
import com.app.todo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    void createTask_needToReturnCreatedTaskAndCreatedStatus() throws Exception {
        Task newTask = new Task("Nova Tarefa", "Nova Descrição", "Pendente", "");
        newTask.setId(1L);
        when(taskService.createTask(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task("Nova Tarefa", "Nova Descrição", "Pendente", ""))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("Nova Tarefa"));
    }

    @Test
    void searchTaskById_needToReturnExistentTaskAndOkStatus() throws Exception {
        Task task = new Task("Tarefa Teste", "Descrição Teste", "Em Andamento", "Obs");
        task.setId(1L);
        when(taskService.searchTaskById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("Tarefa Teste"));
    }

    @Test
    void searchTaskById_needToReturnNotFoundStatusIfTaskDontExist() throws Exception {
        when(taskService.searchTaskById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void listAllTasks_needToReturnTheTasksListAndOkStatus() throws Exception {
        Task task1 = new Task("Tarefa 1", "Desc 1", "Pendente", "");
        Task task2 = new Task("Tarefa 2", "Desc 2", "Concluída", "");
        when(taskService.listAllTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Tarefa 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("[1].name").value("Tarefa 2"));
    }

    @Test
    void updateTask_needToReturnTheUpdatedTaskAndOkStatus() throws Exception {
        Task existentTask = new Task("Tarefa Antiga", "Desc Antiga", "Pendente", "");
        existentTask.setId(1L);
        Task updatedTask = new Task("Tarefa Nova", "Desc Nova", "Concluída", "Atualizado");
        updatedTask.setId(1L);
        when(taskService.updateTask(1L, any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task("Tarefa Nova", "Desc Nova", "Concluída", "Atualizado"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("Tarefa Nova"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Concluída"));
    }

    @Test
    void updateTask_needToReturnNotFoundStatusIfTaskDontExist() throws Exception {
        when(taskService.updateTask(1L, any(Task.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task("Tarefa Nova", "Desc Nova", "Concluída", "Atualizado"))))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteTask_needToReturnNoContentStatus() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
