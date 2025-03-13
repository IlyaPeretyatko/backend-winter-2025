package ru.cft.peretyatko.validator.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.cft.peretyatko.dto.session.SessionRequest;
import ru.cft.peretyatko.validator.DefaultValidator;

@Component
public class SessionValidatorImp extends DefaultValidator implements SessionValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SessionRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }

}
