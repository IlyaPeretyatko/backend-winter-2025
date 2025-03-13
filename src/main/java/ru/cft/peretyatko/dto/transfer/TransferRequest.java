package ru.cft.peretyatko.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @NotNull(message = "Wallet id cannot be null.")
    private long walletId;

    @NotNull(message = "Amount id cannot be null.")
    @Min(value = 1, message = "Amount should be greater than 0.")
    public long amount;
}
