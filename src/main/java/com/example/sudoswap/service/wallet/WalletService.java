package com.example.sudoswap.service.wallet;

import com.example.sudoswap.entity.wallet.WalletType;

import java.util.Map;

public interface WalletService {

    public void buyCrypto(WalletType walletType, float money) throws Exception;
}
