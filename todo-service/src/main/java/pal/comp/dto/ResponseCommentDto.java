package pal.comp.dto;

import java.util.UUID;

public record ResponseCommentDto(
        UUID id,
        UUID ownerId,
        String ownerName,
        String content
) {
}
