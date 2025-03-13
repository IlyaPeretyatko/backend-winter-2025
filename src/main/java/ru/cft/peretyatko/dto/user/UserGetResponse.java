package ru.cft.peretyatko.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGetResponse {

    private String lastName;

    private String firstName;

    private String middleName;

    private LocalDate birthdate;

}
