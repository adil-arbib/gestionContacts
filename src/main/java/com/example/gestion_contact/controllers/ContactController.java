package com.example.gestion_contact.controllers;

import com.example.gestion_contact.dto.contacts.ContactDTO;
import com.example.gestion_contact.exceptions.NotFoundException;
import com.example.gestion_contact.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    private final Logger log = Logger.getLogger("Contact controller");


    @GetMapping
    public String showContactAllPage(Model model) {
        model.addAttribute("contacts", contactService.getAllByOrder());
        return "contacts/all";
    }


    @GetMapping("/create")
    public String showAddContactForm(Model model) {
        model.addAttribute("contactDTO", new ContactDTO());
        return "contacts/create";
    }

    @PostMapping("/create")
    public String addContact(@ModelAttribute("contactDTO") ContactDTO contactDTO) {
        contactService.create(contactDTO);
        return "contacts/create";
    }


    @GetMapping("/{id}")
    public String showContactPage(@PathVariable Long id, Model model) throws NotFoundException {
        ContactDTO contactDTO = contactService.getById(id);
        model.addAttribute("contact", contactDTO);
        log.info(contactDTO.toString());
        return "contacts/contact";
    }

    @PostMapping("/{id}")
    public String updateContact(@ModelAttribute("contact") ContactDTO contactDTO) throws NotFoundException {
        contactService.update(contactDTO);
        return "redirect:/contacts";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws NotFoundException {
        contactService.delete(id);
        return "redirect:/contacts";
    }


}
