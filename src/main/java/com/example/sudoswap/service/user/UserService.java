package com.example.sudoswap.service.user;

import com.example.sudoswap.entity.wallet.WalletEntity;

import java.util.List;

public interface UserService {

    public void registerNewUser(String firstName, String lastName, String username, String password) throws Exception;

    public List<WalletEntity> getUserWallets() throws Exception;
}
