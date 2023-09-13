package com.example.sudoswap.controller;

import com.example.sudoswap.controller.base.BaseController;
import com.example.sudoswap.entity.wallet.WalletType;
import com.example.sudoswap.service.card.CardService;
import com.example.sudoswap.service.collection.CollectionService;
import com.example.sudoswap.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CardController extends BaseController {

    private final CardService cardService;
    private final CollectionService collectionService;

    public CardController(UserService userService, CardService cardService, CollectionService collectionService) {
        super(userService);
        this.cardService = cardService;
        this.collectionService = collectionService;
    }

    @GetMapping("/card")
    public String getCardPage(Model model) throws Exception {
        model.addAttribute("cards", cardService.findAllAdmin());
        return "card/list";
    }

    @GetMapping(path = {"/buy/card", "/user/card"})
    public String getCardPage(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "collection", required = false) Long collectionId,
                              Model model, HttpServletRequest httpServletRequest) throws Exception {
        if (httpServletRequest.getRequestURI().equals("/buy/card")) {
            model.addAttribute("cards", cardService.findAllPagination(page, 3, name, collectionId, false));
        } else {
            model.addAttribute("cards", cardService.findAllPagination(page, 3, name, collectionId, true));
            model.addAttribute("user", "user");
        }

        model.addAttribute("collections", collectionService.findAll());
        if (name != null) {
            model.addAttribute("name", name);
        }
        if (collectionId != null) {
            model.addAttribute("collection", collectionId);
        }
        return "card/buy";

    }

//    @GetMapping("/my/nft")
//    public String getUserNFTPage(@RequestParam(defaultValue = "0") int page,
//                                 @RequestParam(name = "name", required = false) String name,
//                                 @RequestParam(name = "collection", required = false) Long collectionId,
//                                 Model model) throws Exception {
//        model.addAttribute("cards", cardService.getUserNFT(page, 3, name, collectionId));
//        model.addAttribute("collections", collectionService.findAll());
//        if (name != null) {
//            model.addAttribute("name", name);
//        }
//        if (collectionId != null) {
//            model.addAttribute("collection", collectionId);
//        }
//        return "card/user_nft";
//
//    }

    @GetMapping("/card/add")
    public String getAddCardPage(Model model) {
        model.addAttribute("collections", collectionService.findAll());
        return "card/form";
    }

    @GetMapping("/card/edit/{id}")
    public String getEditCardPage(@PathVariable(name = "id") Long id, Model model) throws Exception {
        model.addAttribute("collections", collectionService.findAll());
        model.addAttribute("card", cardService.findById(id));
        return "card/form";
    }

    @PostMapping("/card/add")
    public String addCard(@RequestParam(name = "name") String name,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "price") float price,
                          @RequestParam(name = "image") MultipartFile multipartFile,
                          @RequestParam(name = "collection") Long collectionId,
                          Model model) throws Exception {
        try {
            cardService.addCard(name, description, price, multipartFile, collectionId);
            return "redirect:/card";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("cards", cardService.findAllAdmin());
            return "card/list";
        }

    }

    @PostMapping("/card/edit/{id}")
    public String editCard(@PathVariable(name = "id") Long id,
                           @RequestParam(name = "name") String name,
                           @RequestParam(name = "description") String description,
                           @RequestParam(name = "price") float price,
                           @RequestParam(name = "image") MultipartFile multipartFile,
                           @RequestParam(name = "collection") Long collectionId,
                           Model model) throws Exception {
        try {
            cardService.editCard(id, name, description, price, multipartFile, collectionId);
            return "redirect:/card";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("cards", cardService.findAllAdmin());
            return "card/list";
        }
    }

    @PostMapping("/card/delete/{id}")
    public String deleteCard(@PathVariable(name = "id") Long id) throws Exception {
        cardService.deleteCard(id);
        return "redirect:/card";
    }

    @PostMapping(path = {"/buy/card/*/{id}"})
    public String buyCard(@PathVariable(name = "id") Long id, HttpServletRequest request) throws Exception {
        if (request.getRequestURI().contains("/buy/card/btc")) {
            cardService.buyCard(WalletType.BTC, id);
        } else {
            cardService.buyCard(WalletType.ETH, id);
        }

        return "redirect:/buy/card";
    }
}
