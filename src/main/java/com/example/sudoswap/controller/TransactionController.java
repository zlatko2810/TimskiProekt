package com.example.sudoswap.controller;

import com.example.sudoswap.controller.base.BaseController;
import com.example.sudoswap.entity.wallet.WalletType;
import com.example.sudoswap.service.transaction.NFTTransactionService;
import com.example.sudoswap.service.transaction.TransactionService;
import com.example.sudoswap.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController extends BaseController {

    private final TransactionService transactionService;
    private final NFTTransactionService nftTransactionService;

    public TransactionController(UserService userService, TransactionService transactionService, NFTTransactionService nftTransactionService) {
        super(userService);
        this.transactionService = transactionService;
        this.nftTransactionService = nftTransactionService;
    }

    @GetMapping("/transaction")
    public String getTransactionPage(Model model) throws Exception {
        model.addAttribute("transactions", transactionService.findAll());
        model.addAttribute("btcPrice", 25753.0F);
        model.addAttribute("ethPrice", 1637.58F);
        model.addAttribute("bitcoin", WalletType.BTC);
        model.addAttribute("ethereum", WalletType.ETH);
        return "transaction";
    }

    @GetMapping("/transaction/nft")
    public String getNFTTransactionPage(Model model) throws Exception {
        model.addAttribute("transactions", nftTransactionService.findAll());
        return "transaction_nft";
    }
}
