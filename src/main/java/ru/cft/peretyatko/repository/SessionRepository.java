package ru.cft.peretyatko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.peretyatko.model.Session;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
}
