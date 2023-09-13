package com.example.sudoswap.controller.base;

import com.example.sudoswap.entity.wallet.WalletEntity;
import com.example.sudoswap.entity.wallet.WalletType;
import com.example.sudoswap.service.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Component
public class BaseController {

    private final UserService userService;

    public BaseController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("btc")
    public float addBtcToModel() throws Exception {
        try {
            List<WalletEntity> userWallets = userService.getUserWallets();
            return (float) userWallets.stream()
                    .filter(w -> w.getWalletType() == WalletType.BTC)
                    .mapToDouble(WalletEntity::getMoney)
                    .sum();
        } catch (Exception e) {

        }
        return 0.0F;
    }

    @ModelAttribute("eth")
    public float addEthToModel() throws Exception {
        try {
            List<WalletEntity> userWallets = userService.getUserWallets();
            return (float) userWallets.stream()
                    .filter(w -> w.getWalletType() == WalletType.ETH)
                    .mapToDouble(WalletEntity::getMoney)
                    .sum();
        } catch (Exception e) {

        }
        return 0.0F;
    }
}
