package com.taskflow.taskservice.controller;

import com.taskflow.taskservice.dto.request.TaskRequest;
import com.taskflow.taskservice.dto.response.TaskResponse;
import com.taskflow.taskservice.enums.TaskStatus;
import com.taskflow.taskservice.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Validated
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
    public Page<TaskResponse> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return taskService.getFilteredTasks(status, from, to, pageable);
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
