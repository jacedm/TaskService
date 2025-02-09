package com.taskflow.taskservice.controller;

import com.taskflow.taskservice.dto.request.TaskRequest;
import com.taskflow.taskservice.dto.response.TaskResponse;
import com.taskflow.taskservice.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody @Valid TaskRequest taskRequest) {
        return taskService.createTask(taskRequest);
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable @Positive(message = "ID must be a positive number") Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse updateTask(@PathVariable @Positive(message = "ID must be a positive number") Long id,
                                   @RequestBody @Valid TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable @Positive(message = "ID must be a positive number") Long id) {
        taskService.deleteTask(id);
    }
}
