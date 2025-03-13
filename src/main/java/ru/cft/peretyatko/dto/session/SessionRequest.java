package ru.cft.peretyatko.dto.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequest {
    @NotNull(message = "User id cannot be null.")
    private long userId;

    @NotNull(message = "Password cannot be null.")
    private String password;
}
