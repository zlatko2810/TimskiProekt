package com.example.sudoswap.controller;

import com.example.sudoswap.controller.base.BaseController;
import com.example.sudoswap.entity.wallet.WalletType;
import com.example.sudoswap.service.user.UserService;
import com.example.sudoswap.service.wallet.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class WalletController extends BaseController {

    private final WalletService walletService;

    public WalletController(UserService userService, WalletService walletService) {
        super(userService);
        this.walletService = walletService;
    }

    @GetMapping("/buy")
    public String getBuyCryptoPage(Model model) {
        model.addAttribute("cryptos", WalletType.values());
        return "buy_crypto";
    }

    @PostMapping("/buy")
    public String buyCrypto(@RequestParam(name = "type") String type, @RequestParam(name = "money") float money, Model model) {
        model.addAttribute("type", type);
        model.addAttribute("money", money);
        return "payment";
    }

    @PostMapping("/payment")
    public String paymentSuccessful(@RequestParam Map<String, String> allParams) throws Exception {
        System.out.println(allParams.get("type"));
        System.out.println(allParams.get("money"));
        walletService.buyCrypto(WalletType.valueOf(allParams.get("type")), Float.parseFloat(allParams.get("money")));
        return "payment_successful";
    }
}
