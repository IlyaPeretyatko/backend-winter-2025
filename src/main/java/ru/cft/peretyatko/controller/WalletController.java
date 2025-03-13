package ru.cft.peretyatko.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.peretyatko.dto.wallet.WalletResponse;
import ru.cft.peretyatko.service.WalletService;

import java.util.UUID;

@RestController
@RequestMapping("/potato/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/{id}")
    public WalletResponse getWalletByUserId(@PathVariable long id,
                                            @RequestHeader("Authorization") UUID sessionId) {
        return walletService.getWalletByUserId(id, sessionId);
    }

    @PostMapping("/{userId}/HESOYAM")
    public void hesoyam(@PathVariable long userId,
                        @RequestHeader("Authorization") UUID sessionId) {
        walletService.hesoyam(userId, sessionId);
    }
}
