package com.example.sudoswap.controller;

import com.example.sudoswap.controller.base.BaseController;
import com.example.sudoswap.service.collection.CollectionService;
import com.example.sudoswap.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CollectionController extends BaseController {

    private final CollectionService collectionService;

    public CollectionController(UserService userService, CollectionService collectionService) {
        super(userService);
        this.collectionService = collectionService;
    }

    @GetMapping("/collection")
    public String getCollectionPage(Model model) {
        model.addAttribute("collections", collectionService.findAll());
        return "collection/list";
    }

    @GetMapping("/collection/add")
    public String getAddCollectionPage() {
        return "collection/form";
    }

    @GetMapping("/collection/edit/{id}")
    public String getEditCollectionPage(@PathVariable(name = "id") Long id, Model model) throws Exception {
        model.addAttribute("collection", collectionService.findById(id));
        return "collection/form";
    }

    @PostMapping("/collection/add")
    public String addCollection(@RequestParam(name = "name") String name, @RequestParam(name = "numberOfCards") int numberOfCards) {
        collectionService.createCollection(name, numberOfCards);
        return "redirect:/collection";
    }

    @PostMapping("/collection/edit/{id}")
    public String editCollection(@PathVariable(name = "id") Long id, @RequestParam(name = "name") String name, @RequestParam(name = "numberOfCards") int numberOfCards) throws Exception {
        collectionService.editCollection(id, name, numberOfCards);
        return "redirect:/collection";
    }

    @PostMapping("/collection/delete/{id}")
    public String deleteCollection(@PathVariable(name = "id") Long id, Model model) throws Exception {
        try {
            collectionService.deleteCollection(id);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("collections", collectionService.findAll());
            return "collection/list";
        }

        return "redirect:/collection";
    }


}
