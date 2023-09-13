package com.example.sudoswap.controller;

import com.example.sudoswap.controller.base.BaseController;
import com.example.sudoswap.service.card.CardService;
import com.example.sudoswap.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController extends BaseController {

    private final CardService cardService;

    public PageController(UserService userService, CardService cardService) {
        super(userService);
        this.cardService = cardService;
    }

    @GetMapping("/")
    public String getIndexPage(Model model) throws Exception {
        model.addAttribute("cards", cardService.getOneCardFromEachCollection());
        return "index";
    }

    @GetMapping("/login")
    public String getRegisterPage() {
        return "login";
    }


}
