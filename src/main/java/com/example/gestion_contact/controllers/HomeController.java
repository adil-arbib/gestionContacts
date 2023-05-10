package com.example.gestion_contact.controllers;

import com.example.gestion_contact.services.ContactService;
import com.example.gestion_contact.services.GroupeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ContactService contactService;
    private final GroupeService groupeService;

    @GetMapping( "/index")
    public String home(Model model) {
        model.addAttribute("contactsCount", contactService.count());
        model.addAttribute("groupesCount", groupeService.count());
        return "index";
    }

    @GetMapping({"", "/"})
    public String redirectToHome() {
        return "redirect:/index";
    }

}
