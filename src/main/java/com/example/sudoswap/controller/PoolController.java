package com.example.sudoswap.controller;

import com.example.sudoswap.controller.base.BaseController;
import com.example.sudoswap.service.pool.PoolService;
import com.example.sudoswap.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PoolController extends BaseController {

    private final PoolService poolService;

    public PoolController(UserService userService, PoolService poolService) {
        super(userService);
        this.poolService = poolService;
    }

    @GetMapping("/sell/card/{id}")
    public String addCardToSale(@PathVariable(name = "id") Long id, @RequestParam(name = "price") float price) throws Exception {
        poolService.addCardToSale(id, price);
        return "redirect:/user/card";
    }

    @GetMapping("/remove/card/{id}")
    public String removeCardFromSale(@PathVariable(name = "id") Long id) throws Exception {
        poolService.removeCardFromSale(id);
        return "redirect:/user/card";
    }
}
