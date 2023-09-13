package com.example.sudoswap.service.transaction;

import com.example.sudoswap.entity.transaction.TransactionEntity;

import java.util.List;

public interface TransactionService {

    public List<TransactionEntity> findAll() throws Exception;
}
