package com.example.sudoswap.entity.user;

import com.example.sudoswap.entity.nft.CardEntity;
import com.example.sudoswap.entity.wallet.WalletEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany
    private List<WalletEntity> wallets = new ArrayList<>();

    @OneToMany
    private List<CardEntity> cards = new ArrayList<>();

    public UserEntity(String firstName, String lastName, String username, String password, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
