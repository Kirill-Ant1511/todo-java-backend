package pal.comp.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pal.comp.dto.RequestTaskDto;
import pal.comp.dto.ResponseTaskDto;
import pal.comp.mappers.TaskMapper;
import pal.comp.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<ResponseTaskDto> findAll() {
        return taskRepository.findAll().stream().map(taskMapper::toDto).toList();
    }

    public ResponseTaskDto findById(UUID id) {
        var foundedTask = taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Task with id: " + id + " not found")
        );

        return taskMapper.toDto(foundedTask);
    }

    public List<ResponseTaskDto> findByOwnerId(UUID ownerId) {
        var foundedTasks = taskRepository.findByOwnerId(ownerId);
        return foundedTasks.stream().map(taskMapper::toDto).toList();
    }


    public ResponseTaskDto create(RequestTaskDto request) {
        var entityTask = taskMapper.toEntity(request);
        var createdTask = taskRepository.save(entityTask);
        return taskMapper.toDto(createdTask);
    }

    public ResponseTaskDto update(UUID id, RequestTaskDto request) {
        var foundedTask = taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Task with id: " + id + " not found")
        );
        foundedTask.setTitle(request.title());
        foundedTask.setDescription(request.description());
        var updatedTask = taskRepository.save(foundedTask);
        return taskMapper.toDto(updatedTask);
    }

    public void deleteById(UUID id) {
        var foundedTask = taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Task with id: " + id + " not found")
        );
        taskRepository.delete(foundedTask);
    }
}
