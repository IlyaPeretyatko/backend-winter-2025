package ru.cft.peretyatko.dto.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {
    private LocalDateTime expirationTime;

    private UUID sessionId;

    private boolean active;
}