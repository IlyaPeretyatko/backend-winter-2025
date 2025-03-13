package ru.cft.peretyatko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.peretyatko.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
