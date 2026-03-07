package pal.comp.mappers;

import org.springframework.stereotype.Component;
import pal.comp.dto.RequestCommentDto;
import pal.comp.dto.ResponseCommentDto;
import pal.comp.entity.CommentEntity;
import pal.comp.entity.TaskEntity;

@Component
public class CommentMapper {
    public ResponseCommentDto toDto(CommentEntity entity) {
        return new ResponseCommentDto(
                entity.getId(),
                entity.getOwnerId(),
                null,
                entity.getContent()
        );
    }

    public CommentEntity toEntity(RequestCommentDto dto) {
        return new CommentEntity(
                null,
                dto.ownerId(),
                dto.content(),
                new TaskEntity(dto.taskId(), null, null, null, null, null, null, null)
        );
    }
}
