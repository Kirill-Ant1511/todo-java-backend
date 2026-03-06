package pal.comp.dto;


import java.util.UUID;

public record RequestTaskDto(
        String title,
        String description,
        UUID ownerId
) {
}
