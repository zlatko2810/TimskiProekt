package com.example.sudoswap.repository;

import com.example.sudoswap.entity.transaction.TransactionEntity;
import com.example.sudoswap.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByUser(UserEntity user);
}
