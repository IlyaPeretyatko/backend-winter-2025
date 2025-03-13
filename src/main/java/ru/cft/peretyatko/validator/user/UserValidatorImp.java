package ru.cft.peretyatko.validator.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.cft.peretyatko.dto.user.UserPatchRequest;
import ru.cft.peretyatko.dto.user.UserPostRequest;
import ru.cft.peretyatko.error.exception.ValidationException;
import ru.cft.peretyatko.service.UserService;
import ru.cft.peretyatko.validator.DefaultValidator;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidatorImp extends DefaultValidator implements UserValidator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserPostRequest.class) || clazz.equals(UserPatchRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
        if (target instanceof UserPostRequest userPostRequest) {
            if (userService.existsByEmail(userPostRequest.getEmail())) {
                throw new ValidationException("User with this email already exists", List.of("email"));
            } else if (userService.existsByPhone(userPostRequest.getPhone())) {
                throw new ValidationException("User with this phone already exists", List.of("phone"));
            }
        }
    }
}
