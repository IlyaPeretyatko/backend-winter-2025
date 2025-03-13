package ru.cft.peretyatko.mapper;

import org.springframework.stereotype.Component;
import ru.cft.peretyatko.dto.session.SessionResponse;
import ru.cft.peretyatko.model.Session;

@Component
public class SessionMapper {
    public SessionResponse toSessionResponse(Session session) {
        SessionResponse sessionResponse = new SessionResponse();
        sessionResponse.setSessionId(session.getId());
        sessionResponse.setExpirationTime(session.getExpirationTime());
        sessionResponse.setActive(session.isActive());
        return sessionResponse;
    }
}
