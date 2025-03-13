package ru.cft.peretyatko.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cft.peretyatko.dto.transfer.TransferRequest;
import ru.cft.peretyatko.dto.transfer.TransferResponse;
import ru.cft.peretyatko.model.transfer.TransferDebitCredit;
import ru.cft.peretyatko.model.transfer.TransferStatus;
import ru.cft.peretyatko.service.TransferService;
import ru.cft.peretyatko.validator.transfer.TransferValidator;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/potato/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    private final TransferValidator transferValidator;

    @GetMapping
    public List<TransferResponse> getTransfers(@RequestHeader("Authorization") UUID sessionId,
                                               @RequestParam("transferType") TransferDebitCredit debitCredit,
                                               @RequestParam("active") TransferStatus transferStatus,
                                               @RequestParam("userId") Long userId) {
        return transferService.getTransfers(sessionId, debitCredit, transferStatus, userId);
    }

    @GetMapping("/{transferId}")
    public TransferResponse getTransferById(@RequestHeader("Authorization") UUID sessionId,
                                            @PathVariable UUID transferId) {
        return transferService.getTransferById(sessionId, transferId);
    }

    @PostMapping
    public TransferResponse createTransfer(@RequestHeader("Authorization") UUID sessionId,
                                           @Valid @RequestBody TransferRequest transferRequest,
                                           BindingResult bindingResult) {
        transferValidator.validate(transferRequest, bindingResult);
        return transferService.createTransfer(sessionId, transferRequest);
    }

}
