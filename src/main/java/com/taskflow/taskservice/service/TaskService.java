package com.taskflow.taskservice.service;

import com.taskflow.taskservice.dto.request.TaskRequest;
import com.taskflow.taskservice.dto.response.TaskResponse;
import com.taskflow.taskservice.entity.Task;
import com.taskflow.taskservice.enums.TaskStatus;
import com.taskflow.taskservice.exception.TaskNotFoundException;
import com.taskflow.taskservice.mapper.TaskMapper;
import com.taskflow.taskservice.repository.TaskRepository;
import com.taskflow.taskservice.repository.spec.TaskSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    public Page<TaskResponse> getFilteredTasks(TaskStatus status,
                                               LocalDateTime from,
                                               LocalDateTime to,
                                               Pageable pageable) {

        Specification<Task> spec = Specification
                .where(TaskSpecification.hasStatus(status))
                .and(TaskSpecification.createdAfter(from))
                .and(TaskSpecification.createdBefore(to));

        return taskRepository.findAll(spec, pageable).map(taskMapper::toResponse);
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        taskMapper.updateTask(taskRequest, task);

        return taskMapper.toResponse(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
