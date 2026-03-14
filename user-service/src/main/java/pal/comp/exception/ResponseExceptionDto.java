package pal.comp.exception;

import java.time.LocalDateTime;

public record ResponseExceptionDto(
        String message,
        String detailMessage,
        LocalDateTime currentTime
) {
}
