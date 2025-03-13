package ru.cft.peretyatko.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cft.peretyatko.dto.user.*;
import ru.cft.peretyatko.service.UserService;
import ru.cft.peretyatko.validator.user.UserValidator;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/potato/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserValidator userValidator;

    @GetMapping("/{id}")
    public UserGetResponse getUserById(@PathVariable long id,
                               @RequestHeader("Authorization") UUID sessionId) {
        return userService.getUserById(id, sessionId);
    }

    @PostMapping
    public UserPostResponse createUser(@Valid @RequestBody UserPostRequest userPostRequest,
                              BindingResult bindingResult) {
        userValidator.validate(userPostRequest, bindingResult);
        return userService.createUser(userPostRequest);
    }

    @PatchMapping("/{id}")
    public void updateUser(@PathVariable long id,
                           @RequestHeader("Authorization") UUID sessionId,
                           @Valid @RequestBody UserPatchRequest userPatchRequest,
                           BindingResult bindingResult) {
        userValidator.validate(userPatchRequest, bindingResult);
        userService.updateUser(id, sessionId, userPatchRequest);
    }


}
