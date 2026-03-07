package pal.comp.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pal.comp.dto.RequestTaskDto;
import pal.comp.dto.ResponseTaskDto;
import pal.comp.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;


    @GetMapping
    public ResponseEntity<List<ResponseTaskDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTaskDto> findOne(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findById(id));
    }

    @GetMapping("/users/{ownerId}")
    public ResponseEntity<List<ResponseTaskDto>> findByOwnerId(@PathVariable UUID ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findByOwnerId(ownerId));
    }

    @PostMapping
    public ResponseEntity<ResponseTaskDto> save(@RequestBody RequestTaskDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.create(request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseTaskDto> update(@PathVariable UUID id, @RequestBody RequestTaskDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
