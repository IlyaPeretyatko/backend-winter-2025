package ru.cft.peretyatko.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.cft.peretyatko.model.transfer.TransferStatus;
import ru.cft.peretyatko.model.transfer.TransferType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private UUID transferId;
    private LocalDateTime creationTime;
    private long amount;
    private TransferType transferType;
    private TransferStatus status;
}
