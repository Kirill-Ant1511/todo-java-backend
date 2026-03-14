package pal.comp.http.user;

import java.util.UUID;

public record ResponseUserDto(
        UUID id,
        String username,
        String passwordHash,
        String avatarUrl
) {
}
