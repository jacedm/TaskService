package com.taskflow.taskservice.repository.spec;

import com.taskflow.taskservice.entity.Task;
import com.taskflow.taskservice.enums.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TaskSpecification {

    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> createdAfter(LocalDateTime from) {
        return (root, query, criteriaBuilder) ->
                from == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), from);
    }

    public static Specification<Task> createdBefore(LocalDateTime to) {
        return (root, query, criteriaBuilder) ->
                to == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), to);
    }
}
