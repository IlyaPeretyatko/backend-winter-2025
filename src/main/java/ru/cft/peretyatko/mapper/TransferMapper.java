package ru.cft.peretyatko.mapper;

import org.springframework.stereotype.Component;
import ru.cft.peretyatko.dto.transfer.TransferResponse;
import ru.cft.peretyatko.model.transfer.Transfer;

@Component
public class TransferMapper {
    public TransferResponse toTransferResponse(Transfer transfer) {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setTransferId(transfer.getId());
        transferResponse.setAmount(transfer.getAmount());
        transferResponse.setTransferType(transfer.getTransferType());
        transferResponse.setStatus(transfer.getTransferStatus());
        transferResponse.setCreationTime(transfer.getCreatedAt());
        return transferResponse;
    }
}
