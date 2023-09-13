package com.example.sudoswap.service.transaction;

import com.example.sudoswap.entity.transaction.NFTTransactionEntity;

import java.util.List;

public interface NFTTransactionService {

    public List<NFTTransactionEntity> findAll() throws Exception;
}
