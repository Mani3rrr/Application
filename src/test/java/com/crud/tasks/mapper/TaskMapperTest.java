package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {
    @InjectMocks
    TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");

        //When
        Task mappedTask = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals("Title", mappedTask.getTitle());
    }

    @Test
    public void mapToTaskDto() {
        //Given
        Task task = new Task(1L, "Title", "Content");

        //When
        TaskDto mappedTaskToDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals("Title", mappedTaskToDto.getTitle());
    }

    @Test
    public void mapToTaskDtoList() {
        //Given
        Task task = new Task(1L, "Title", "Content");
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(task);

        //When
        List<TaskDto> mappedTasksList = taskMapper.mapToTaskDtoList(tasksList);

        //Then
        assertEquals("Title", mappedTasksList.get(0).getTitle());
    }
}
