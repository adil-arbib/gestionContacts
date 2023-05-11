package com.example.gestion_contact.controllers;

import com.example.gestion_contact.services.GroupeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groupes")
public class GroupeController {

    private final GroupeService groupeService;


    @GetMapping
    public String showContactPage(Model model) {
        model.addAttribute("groupes", groupeService.getAll());
        return "groupes/all";
    }


}
