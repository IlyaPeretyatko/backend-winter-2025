package ru.cft.peretyatko.validator.transfer;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.cft.peretyatko.dto.transfer.TransferRequest;
import ru.cft.peretyatko.validator.DefaultValidator;

@Component
public class TransferValidatorImp extends DefaultValidator implements TransferValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(TransferRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        createAndThrowException(errors);
    }
}
