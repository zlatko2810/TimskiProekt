package com.example.sudoswap.repository;

import com.example.sudoswap.entity.transaction.NFTTransactionEntity;
import com.example.sudoswap.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NFTTransactionRepository extends JpaRepository<NFTTransactionEntity, Long> {

    List<NFTTransactionEntity> findAllByUser(UserEntity user);
}
