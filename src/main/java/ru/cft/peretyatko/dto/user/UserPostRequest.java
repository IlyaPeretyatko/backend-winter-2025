package ru.cft.peretyatko.dto.user;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequest {
    @NotNull(message = "Last name cannot be null.")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters long.")
    @Pattern(regexp = "^[А-Я][а-я]*$", message = "First letter must be uppercase, only Russian letters are allowed.")
    private String lastName;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long.")
    @Pattern(regexp = "^[А-Я][а-я]*$", message = "First letter must be uppercase, only Russian letters are allowed.")
    private String firstName;

    @Size(min = 2, max = 50, message = "Middle name must be between 2 and 50 characters long.")
    @Pattern(regexp = "^[А-Я][а-я]*$", message = "First letter must be uppercase, only Russian letters are allowed.")
    private String middleName;

    @NotNull(message = "Birthdate cannot be null.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate birthdate;

    @NotNull(message = "Phone cannot be null.")
    @Pattern(regexp = "^[7-8]\\d{10}$", message = "Phone bad input.")
    private String phone;


    @NotNull(message = "Email cannot be null.")
    @Email(message = "Bad input email.")
    private String email;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "The input must include at least one lowercase letter, one uppercase letter, one digit, " +
                    "and at least one special character.")
    private String password;
}
