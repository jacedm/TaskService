package com.taskflow.taskservice.mapper;

import com.taskflow.taskservice.dto.request.TaskRequest;
import com.taskflow.taskservice.dto.response.TaskResponse;
import com.taskflow.taskservice.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "status", source = "status")
    TaskResponse toResponse(Task task);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateTask(TaskRequest taskRequest, @MappingTarget Task task);

    @Mapping(target = "status", source = "status")
    Task requestToEntity(TaskRequest taskRequest);
}
