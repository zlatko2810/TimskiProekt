package com.example.sudoswap.entity.wallet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallet")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    private WalletType walletType;

    @Column(name = "money")
    private float money;

    public WalletEntity(String address, WalletType walletType, float money) {
        this.address = address;
        this.walletType = walletType;
        this.money = money;
    }
}
