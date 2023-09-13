package com.example.sudoswap.service.transaction.impl;

import com.example.sudoswap.entity.transaction.TransactionEntity;
import com.example.sudoswap.repository.TransactionRepository;
import com.example.sudoswap.service.common.CommonService;
import com.example.sudoswap.service.transaction.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CommonService commonService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CommonService commonService) {
        this.transactionRepository = transactionRepository;
        this.commonService = commonService;
    }

    @Override
    public List<TransactionEntity> findAll() throws Exception {
        return transactionRepository.findAllByUser(commonService.getLoggedInUser());
    }
}
