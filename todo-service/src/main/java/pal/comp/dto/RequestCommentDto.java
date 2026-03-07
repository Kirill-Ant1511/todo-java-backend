package pal.comp.dto;


import java.util.UUID;

public record RequestCommentDto(
        UUID taskId,
        UUID ownerId,
        String content
) {
}
