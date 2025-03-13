package ru.cft.peretyatko.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cft.peretyatko.dto.session.*;
import ru.cft.peretyatko.service.SessionService;
import ru.cft.peretyatko.validator.session.SessionValidator;

import java.util.UUID;

@RestController
@RequestMapping("/potato/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    private final SessionValidator sessionValidator;

    @GetMapping("/{sessionId}")
    public SessionResponse getSessionById(@PathVariable UUID sessionId) {
        return sessionService.getSessionById(sessionId);
    }

    @PostMapping
    public SessionResponse createSession(@Valid @RequestBody SessionRequest sessionRequest,
                                         BindingResult bindingResult) {
        sessionValidator.validate(sessionRequest, bindingResult);
        return sessionService.createSession(sessionRequest);
    }

    @DeleteMapping("/{sessionId}")
    public void closeSession(@PathVariable UUID sessionId) {
        sessionService.closeSession(sessionId);
    }


}
