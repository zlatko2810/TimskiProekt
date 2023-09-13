package com.example.sudoswap.entity.transaction;

import com.example.sudoswap.entity.user.UserEntity;
import com.example.sudoswap.entity.wallet.WalletType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "amount")
    private float amount;

    @Enumerated(EnumType.STRING)
    private WalletType walletType;

    @ManyToOne
    private UserEntity user;

    public TransactionEntity(LocalDate date, float amount, WalletType walletType, UserEntity user) {
        this.date = date;
        this.amount = amount;
        this.walletType = walletType;
        this.user = user;
    }
}
