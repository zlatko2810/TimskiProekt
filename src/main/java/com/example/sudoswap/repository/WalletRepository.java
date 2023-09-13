package com.example.sudoswap.repository;

import com.example.sudoswap.entity.wallet.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity,Long> {
}
