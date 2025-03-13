package ru.cft.peretyatko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.peretyatko.model.transfer.Transfer;

import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
}
