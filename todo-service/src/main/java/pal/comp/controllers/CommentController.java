package pal.comp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pal.comp.dto.RequestCommentDto;
import pal.comp.dto.ResponseCommentDto;
import pal.comp.service.CommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService service;

    @PostMapping
    public ResponseEntity<ResponseCommentDto> createComment(@RequestBody RequestCommentDto request) {
        return ResponseEntity.ok(service.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
