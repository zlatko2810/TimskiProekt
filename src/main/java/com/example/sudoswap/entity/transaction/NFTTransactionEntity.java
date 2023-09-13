package com.example.sudoswap.entity.transaction;

import com.example.sudoswap.entity.nft.CardEntity;
import com.example.sudoswap.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nft_transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NFTTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private float amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    private CardEntity card;

    @ManyToOne
    private UserEntity user;

    public NFTTransactionEntity(float amount, TransactionType transactionType, CardEntity card, UserEntity user) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.card = card;
        this.user = user;
    }
}
