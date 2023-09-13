package com.example.sudoswap.service.transaction.impl;

import com.example.sudoswap.entity.transaction.NFTTransactionEntity;
import com.example.sudoswap.repository.NFTTransactionRepository;
import com.example.sudoswap.service.common.CommonService;
import com.example.sudoswap.service.transaction.NFTTransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NFTTransactionServiceImpl implements NFTTransactionService {

    private final NFTTransactionRepository nftTransactionRepository;
    private final CommonService commonService;

    public NFTTransactionServiceImpl(NFTTransactionRepository nftTransactionRepository, CommonService commonService) {
        this.nftTransactionRepository = nftTransactionRepository;
        this.commonService = commonService;
    }

    @Override
    public List<NFTTransactionEntity> findAll() throws Exception {
        return nftTransactionRepository.findAllByUser(commonService.getLoggedInUser());
    }
}
