package pal.comp.dto;

public record LoginResponseDto(
        String token,
        Long expirationTime
) {
}
