package com.example.sudoswap.data;

import com.example.sudoswap.entity.user.UserEntity;
import com.example.sudoswap.entity.user.UserRole;
import com.example.sudoswap.entity.wallet.WalletEntity;
import com.example.sudoswap.entity.wallet.WalletType;
import com.example.sudoswap.repository.UserRepository;
import com.example.sudoswap.repository.WalletRepository;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializr {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializr(UserRepository userRepository, WalletRepository walletRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeData() {

        try {
            Optional<UserEntity> checkUser = userRepository.findByUsername("admin");
            Optional<UserEntity> checkUser2 = userRepository.findByUsername("zlatko");
            if (checkUser.isEmpty()) {
                UserEntity user = new UserEntity("admin", "admin", "admin", passwordEncoder.encode("admin"), UserRole.ADMIN);
                WalletEntity walletBTC = new WalletEntity(RandomStringUtils.randomAlphanumeric(42), WalletType.BTC, 0.0F);
                WalletEntity walletETH = new WalletEntity(RandomStringUtils.randomAlphanumeric(42), WalletType.ETH, 0.0F);
                walletRepository.save(walletBTC);
                walletRepository.save(walletETH);
                user.getWallets().add(walletBTC);
                user.getWallets().add(walletETH);
                userRepository.save(user);
            }

            if (checkUser2.isEmpty()) {
                UserEntity user = new UserEntity("zlatko","stojanovski","zlatko", passwordEncoder.encode("zlatko"), UserRole.USER);
                WalletEntity walletBTC = new WalletEntity(RandomStringUtils.randomAlphanumeric(42), WalletType.BTC, 0.0F);
                WalletEntity walletETH = new WalletEntity(RandomStringUtils.randomAlphanumeric(42), WalletType.ETH, 0.0F);
                walletRepository.save(walletBTC);
                walletRepository.save(walletETH);
                user.getWallets().add(walletBTC);
                user.getWallets().add(walletETH);
                userRepository.save(user);
            }
        } catch (Exception e) {

        }

    }
}
