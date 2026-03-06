package pal.comp.mappers;

import org.springframework.stereotype.Component;
import pal.comp.dto.ResponseCommentDto;
import pal.comp.entity.CommentEntity;

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

    public CommentEntity toEntity(ResponseCommentDto dto) {
        return null;
    }
}
