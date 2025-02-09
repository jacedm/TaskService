package com.taskflow.taskservice.service;

import com.taskflow.taskservice.dto.request.TaskRequest;
import com.taskflow.taskservice.dto.response.TaskResponse;
import com.taskflow.taskservice.entity.Task;
import com.taskflow.taskservice.exception.TaskNotFoundException;
import com.taskflow.taskservice.mapper.TaskMapper;
import com.taskflow.taskservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = taskMapper.requestToEntity(taskRequest);

        return taskMapper.toResponse(
                taskRepository.save(task)
        );
    }

    public TaskResponse getTaskById(Long id) {
        return taskMapper.toResponse(
                taskRepository.findById(id)
                        .orElseThrow(() -> new TaskNotFoundException("Task not found"))
        );
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        taskMapper.updateTask(taskRequest, task);

        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found");
        }

        taskRepository.deleteById(id);
    }
}
