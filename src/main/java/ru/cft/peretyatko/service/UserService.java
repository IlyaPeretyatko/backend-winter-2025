package ru.cft.peretyatko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.peretyatko.dto.user.*;
import ru.cft.peretyatko.error.exception.ServiceException;
import ru.cft.peretyatko.mapper.UserMapper;
import ru.cft.peretyatko.model.Session;
import ru.cft.peretyatko.model.User;
import ru.cft.peretyatko.repository.UserRepository;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final SessionService sessionService;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy SessionService sessionService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserPostResponse createUser(UserPostRequest userPostRequest) {
        User user = userMapper.toUser(userPostRequest);
        User createdUser = userRepository.save(user);
        return userMapper.toUserPostResponse(createdUser);
    }

    @Transactional(readOnly = true)
    public UserGetResponse getUserById(long id, UUID sessionId) {
        sessionService.findSessionById(sessionId);
        User user = userRepository.findById(id).orElseThrow(() -> new ServiceException(404, "User wasn't found."));
        return userMapper.toUserGetResponse(user);
    }

    @Transactional
    public void updateUser(long id, UUID sessionId, UserPatchRequest userPatchRequest) {
        Session session = sessionService.findSessionById(sessionId);
        User user = userRepository.findById(id).orElseThrow(() -> new ServiceException(404, "User wasn't found."));
        if (user.getId() != session.getUser().getId()) {
            throw new ServiceException(403, "The session owner ID does not match the user ID whose data is being requested.");
        }
        userMapper.updateUser(userPatchRequest, user);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    protected User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ServiceException(404, "User wasn't found."));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }


}
