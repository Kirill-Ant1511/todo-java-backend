package pal.comp.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ResponseTaskDto(
        UUID id,
        LocalDateTime createdAt,
        String title,
        String description,
        UUID ownerId,
        String ownerName,
        UUID performBy,
        Boolean isDone,
        List<ResponseCommentDto> comments

) {
}
