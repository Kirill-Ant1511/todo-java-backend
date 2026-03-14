package pal.comp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pal.comp.dto.RequestCommentDto;
import pal.comp.dto.ResponseCommentDto;
import pal.comp.entity.CommentEntity;
import pal.comp.mappers.CommentMapper;
import pal.comp.repository.CommentRepository;

import java.util.UUID;

@Service
//@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final CommentMapper mapper;

    public CommentService(CommentRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ResponseCommentDto create(RequestCommentDto request) {
        var commentEntity = mapper.toEntity(request);
        var createdComment = repository.save(commentEntity);
        return mapper.toDto(createdComment);
    }

    public void delete(UUID id) {
        var foundedComment = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Such comment does not exist")
        );
        repository.delete(foundedComment);
    }
}
