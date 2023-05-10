package com.example.gestion_contact.controllers;

import com.example.gestion_contact.dto.contacts.ContactDTO;
import com.example.gestion_contact.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    private final Logger log = Logger.getLogger("Contact controller");


    @GetMapping
    public String showContactPage(Model model) {
        model.addAttribute("contacts", contactService.getAllByOrder());
        return "contacts/all";
    }


    @GetMapping("/create")
    public String showContactForm(Model model) {
        model.addAttribute("contactDTO", new ContactDTO());
        return "contacts/create";
    }

    @PostMapping("/create")
    public String addContact(@ModelAttribute("contactDTO") ContactDTO contactDTO) {
        contactService.create(contactDTO);
        return "contacts/create";
    }


}
