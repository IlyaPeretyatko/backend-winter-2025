package ru.cft.peretyatko.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.peretyatko.dto.transfer.TransferRequest;
import ru.cft.peretyatko.dto.transfer.TransferResponse;
import ru.cft.peretyatko.error.exception.ServiceException;
import ru.cft.peretyatko.mapper.TransferMapper;
import ru.cft.peretyatko.model.Session;
import ru.cft.peretyatko.model.Wallet;
import ru.cft.peretyatko.model.transfer.Transfer;
import ru.cft.peretyatko.model.transfer.TransferDebitCredit;
import ru.cft.peretyatko.model.transfer.TransferStatus;
import ru.cft.peretyatko.model.transfer.TransferType;
import ru.cft.peretyatko.repository.TransferRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

    private final SessionService sessionService;

    private final WalletService walletService;

    private final TransferMapper transferMapper;

    @Transactional(readOnly = true)
    public List<TransferResponse> getTransfers(UUID sessionId, TransferDebitCredit debitCredit, TransferStatus transferStatus, Long userId) {
        Session session = sessionService.findSessionById(sessionId);
        Wallet wallet = session.getUser().getWallet();
        List<Transfer> listTransfer;
        if (debitCredit != null) {
            listTransfer = (debitCredit == TransferDebitCredit.IN) ? wallet.getReceivedTransfers() : wallet.getSentTransfers();
        } else {
            listTransfer = new LinkedList<>();
            listTransfer.addAll(wallet.getReceivedTransfers());
            listTransfer.addAll(wallet.getSentTransfers());
        }
        List<TransferResponse> listTransferResponse = new LinkedList<>();
        for (Transfer transfer : listTransfer) {
            if (transferStatus != null) {
                if (transferStatus != transfer.getTransferStatus()) {
                    continue;
                }
            }
            if (userId != null) {
                long secondUser = (session.getUser().getId() == transfer.getSenderWallet().getUser().getId())
                        ? transfer.getReceiverWallet().getUser().getId()
                        : transfer.getSenderWallet().getUser().getId();
                if (userId != secondUser) {
                    continue;
                }
            }
            listTransferResponse.add(transferMapper.toTransferResponse(transfer));
        }
        return listTransferResponse;
    }

    @Transactional(readOnly = true)
    public TransferResponse getTransferById(UUID sessionId, UUID transferId) {
        Session session = sessionService.findSessionById(sessionId);
        Transfer transfer = transferRepository.findById(transferId).orElseThrow(() -> new ServiceException(404, "Transfer wasn't found."));
        Long userId = session.getUser().getId();
        if (!(transfer.getSenderWallet().getUser().getId() == userId) && !(transfer.getReceiverWallet().getUser().getId() == userId)) {
            throw new ServiceException(404, "The session owner ID does not match the user ID whose data is being requested.");
        }
        return transferMapper.toTransferResponse(transfer);
    }

    @Transactional
    public TransferResponse createTransfer(UUID sessionId, TransferRequest transferRequest) {
        Session session = sessionService.findSessionById(sessionId);
        long receiverWalletId = transferRequest.getWalletId();
        long amount = transferRequest.getAmount();
        Wallet receiverWallet = walletService.findWalletById(receiverWalletId);
        Wallet senderWallet = session.getUser().getWallet();
        if (senderWallet.getBalance() < amount) throw new ServiceException(400, "Not enough money.");
        Transfer transfer = new Transfer(UUID.randomUUID(), LocalDateTime.now(), senderWallet, receiverWallet, amount,
                TransferType.TRANSFER, TransferStatus.PAID);
        transferRepository.save(transfer);
        senderWallet.subAmountFromBalance(amount);
        receiverWallet.addAmountOnBalance(amount);
        walletService.saveWallet(receiverWallet);
        walletService.saveWallet(senderWallet);
        return transferMapper.toTransferResponse(transfer);
    }
}
