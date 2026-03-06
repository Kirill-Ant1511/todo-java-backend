package pal.comp.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pal.comp.dto.RequestTaskDto;
import pal.comp.dto.ResponseCommentDto;
import pal.comp.dto.ResponseTaskDto;
import pal.comp.entity.TaskEntity;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final CommentMapper commentMapper;

    public ResponseTaskDto toDto(TaskEntity entity) {
        List<ResponseCommentDto> dtoComments = null;
        if (entity.getComments() != null) {
            dtoComments = entity.getComments().stream().map(commentMapper::toDto).toList();
        }

        return new ResponseTaskDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getOwnerId(),
                null,
                entity.getPerformBy(),
                entity.getIsDone(),
                dtoComments
        );
    }

    public TaskEntity toEntity(RequestTaskDto dto) {
        return new TaskEntity(
                null,
                LocalDateTime.now(),
                dto.title(),
                dto.description(),
                dto.ownerId(),
                null,
                false,
                null
        );
    }
}
