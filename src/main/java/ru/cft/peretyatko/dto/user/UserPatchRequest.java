package ru.cft.peretyatko.dto.user;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPatchRequest {

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters long.")
    @Pattern(regexp = "^[А-Я][а-я]*$", message = "First letter must be uppercase, only Russian letters are allowed.")
    private String lastName;

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long.")
    @Pattern(regexp = "^[А-Я][а-я]*$", message = "First letter must be uppercase, only Russian letters are allowed.")
    private String firstName;

    @Size(min = 2, max = 50, message = "Middle name must be between 2 and 50 characters long.")
    @Pattern(regexp = "^[А-Я][а-я]*$", message = "First letter must be uppercase, only Russian letters are allowed.")
    private String middleName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate birthdate;

}
