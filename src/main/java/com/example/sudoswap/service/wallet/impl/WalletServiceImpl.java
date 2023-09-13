package com.example.sudoswap.service.wallet.impl;

import com.example.sudoswap.entity.transaction.TransactionEntity;
import com.example.sudoswap.entity.wallet.WalletEntity;
import com.example.sudoswap.entity.wallet.WalletType;
import com.example.sudoswap.repository.TransactionRepository;
import com.example.sudoswap.repository.WalletRepository;
import com.example.sudoswap.service.common.CommonService;
import com.example.sudoswap.service.wallet.WalletService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final CommonService commonService;

    public WalletServiceImpl(WalletRepository walletRepository, TransactionRepository transactionRepository, CommonService commonService) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.commonService = commonService;
    }

    @Override
    public void buyCrypto(WalletType walletType, float money) throws Exception {
        List<WalletEntity> wallets = commonService.getLoggedInUser().getWallets();
        for (WalletEntity w : wallets) {
            if (w.getWalletType().equals(walletType)) {
                w.setMoney(w.getMoney() + money);
                walletRepository.save(w);
                break;
            }
        }
        transactionRepository.save(new TransactionEntity(LocalDate.now(), money, walletType, commonService.getLoggedInUser()));
    }
}
