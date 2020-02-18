package com.crud.tasks.controller;


import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService service;

    @Test
    public void shouldGetEmptyTasks() throws Exception {

        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Title", "Content"));

        List<TaskDto> tasksDto = new ArrayList<>();

        tasksDto.add(new TaskDto(1L, "Title", "Content"));

        //When
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);
        when(service.getAllTasks()).thenReturn(tasks);

        //Then
        mockMvc.perform(get("/v1/tasks/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void shouldGetTasks() throws Exception {

        //Given
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(1L,"Task Title", "Task content"));
        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(tasks);

        //When $ Then
        mockMvc.perform(get("/v1/tasks/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Tasks Fields
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Task Title")))
                .andExpect(jsonPath("$[0].content", is("Task content")));
    }

    @Test
    public void shouldGetTask() throws Exception {

        //Given
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");
        Task task = new Task(1L, "Title", "Content");

        when(service.getTask(1L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(status().is(200));
    }


    @Test
    public void shouldUpdateTask() throws Exception {
        // Given
        Task task = new Task(1L, "Title", "Content");
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");

        when(service.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.content", is("Content")));
    }


    @Test
    public void shouldCreateTask() throws Exception {

        //Given
        Task task = new Task(1L, "Title", "Task");
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");

        when(service.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When $ Then
        mockMvc.perform(post("/v1/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));

    }

    @Test
    public void shouldDeleteTask() throws Exception {

        //Given
        Task task = new Task(1L, "Title", "Content");
        service.saveTask(task);
        // When
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/tasks/1")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
