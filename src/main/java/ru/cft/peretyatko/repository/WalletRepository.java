package ru.cft.peretyatko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.peretyatko.model.Wallet;


public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
