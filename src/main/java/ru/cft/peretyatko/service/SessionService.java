package ru.cft.peretyatko.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.peretyatko.dto.session.SessionResponse;
import ru.cft.peretyatko.dto.session.SessionRequest;
import ru.cft.peretyatko.error.exception.ServiceException;
import ru.cft.peretyatko.mapper.SessionMapper;
import ru.cft.peretyatko.model.Session;
import ru.cft.peretyatko.repository.SessionRepository;

import ru.cft.peretyatko.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    @Value("${session.expiration.duration}")
    private long sessionExpirationDuration;

    private final SessionRepository sessionRepository;

    private final UserService userService;

    private final SessionMapper sessionMapper;

    @Autowired
    public SessionService(SessionRepository sessionRepository, @Lazy UserService userService, SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
        this.sessionMapper = sessionMapper;
    }

    @Transactional
    public SessionResponse createSession(SessionRequest sessionRequest) {
        User user = userService.findById(sessionRequest.getUserId());
        if (!BCrypt.checkpw(sessionRequest.getPassword(), user.getPassword())) {
            throw new ServiceException(401, "Password is wrong.");
        }
        LocalDateTime now = LocalDateTime.now();
        Session session = new Session(UUID.randomUUID(), user, now, now.plusMinutes(sessionExpirationDuration), true);
        sessionRepository.save(session);
        return sessionMapper.toSessionResponse(session);
    }

    @Transactional(readOnly = true)
    public SessionResponse getSessionById(UUID sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new ServiceException(404, "Session wasn't found."));
        return sessionMapper.toSessionResponse(session);
    }

    @Transactional
    public void closeSession(UUID sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new ServiceException(404, "Session wasn't found."));
        session.setActive(false);
        sessionRepository.save(session);
    }

    @Transactional(readOnly = true)
    protected Session findSessionById(UUID sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new ServiceException(404, "Session wasn't found."));
        if (!session.isActive()) throw new ServiceException(408, "Session is not active.");
        if (session.getExpirationTime().isBefore(LocalDateTime.now())) throw new ServiceException(408, "Session timeout has expired.");
        return session;
    }
}
