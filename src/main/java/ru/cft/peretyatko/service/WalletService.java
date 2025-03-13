package ru.cft.peretyatko.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.peretyatko.dto.wallet.WalletResponse;
import ru.cft.peretyatko.error.exception.ServiceException;
import ru.cft.peretyatko.model.Session;
import ru.cft.peretyatko.model.User;
import ru.cft.peretyatko.model.Wallet;
import ru.cft.peretyatko.repository.WalletRepository;

import java.util.Random;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    private final SessionService sessionService;

    private final UserService userService;

    @Autowired
    public WalletService(WalletRepository walletRepository, @Lazy SessionService sessionService, @Lazy UserService userService) {
        this.walletRepository = walletRepository;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public WalletResponse getWalletByUserId(long id, UUID sessionId) {
        Session session = sessionService.findSessionById(sessionId);
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new ServiceException(404, "Wallet wasn't found."));
        if (wallet.getUser().getId() != session.getUser().getId()) {
            throw new ServiceException(403, "The session owner ID does not match the user ID whose data is being requested.");
        }
        return new WalletResponse(wallet.getNumber(), wallet.getBalance());
    }

    @Transactional
    public void hesoyam(long id, UUID sessionId) {
        sessionService.findSessionById(sessionId);
        User user = userService.findById(id);
        Wallet wallet = user.getWallet();
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        if (randomNumber < 25) {
            wallet.addAmountOnBalance(10);
        }
        walletRepository.save(wallet);
    }

    @Transactional(readOnly = true)
    protected Wallet findWalletById(long id) {
        return walletRepository.findById(id).orElseThrow(() -> new ServiceException(404, "Wallet wasn't found."));
    }

    @Transactional
    protected void saveWallet(Wallet wallet) {
        walletRepository.save(wallet);
    }
}
