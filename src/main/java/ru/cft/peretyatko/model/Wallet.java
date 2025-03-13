package ru.cft.peretyatko.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.cft.peretyatko.model.transfer.Transfer;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number")
    private long number;

    @Column(name = "balance", nullable = false)
    private long balance;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "senderWallet", cascade = CascadeType.ALL)
    List<Transfer> sentTransfers;

    @OneToMany(mappedBy = "receiverWallet", cascade = CascadeType.ALL)
    List<Transfer> receivedTransfers;

    public void addAmountOnBalance(long diff) {
        this.balance += diff;
    }

    public void subAmountFromBalance(long diff) {
        this.balance -= diff;
    }

}

