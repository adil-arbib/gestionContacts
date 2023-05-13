package com.example.gestion_contact.controllers;

import com.example.gestion_contact.dto.contacts.ContactDTO;
import com.example.gestion_contact.dto.groupes.GroupeDTO;
import com.example.gestion_contact.exceptions.AlreadyExistsException;
import com.example.gestion_contact.exceptions.NotFoundException;
import com.example.gestion_contact.services.ContactService;
import com.example.gestion_contact.services.GroupeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groupes")
public class GroupeController {

    private final GroupeService groupeService;
    private final ContactService contactService;

    private final Logger log = Logger.getLogger("Groupe controller");


    @GetMapping
    public String showGroupePage(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        Page<GroupeDTO> groupePage = groupeService.getGroupePage(page, size, keyword);
        model.addAttribute("groupesPage", groupePage);
        model.addAttribute("pages", new int[groupePage.getTotalPages()]);
        model.addAttribute("currentPage" ,page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("max", groupePage.getTotalPages() -1 );
        model.addAttribute("size" ,size);
        return "groupes/all";
    }

    @GetMapping("/create")
    public String showAddGroupeForm(Model model) {
        model.addAttribute("groupeDTO", new GroupeDTO());
        model.addAttribute("contacts", contactService.getAll());
        return "groupes/create";
    }

    @PostMapping("/create")
    public String addGroupe(@ModelAttribute("groupeDTO") GroupeDTO groupeDTO, @RequestParam("selectedContacts") List<Long> selectedContactIds) throws AlreadyExistsException, NotFoundException {
        groupeService.create(groupeDTO, selectedContactIds);
        return "redirect:/groupes/create";
    }
//
//    @GetMapping("/{id}")
//    public String showGroupePage(@PathVariable Long id, Model model) throws NotFoundException {
//        GroupeDTO groupeDTO = groupeService.getById(id);
//        model.addAttribute("groupe", groupeDTO);
//        return "groupes/groupe";
//    }
//
//    @PostMapping("/{id}")
//    public String updateContact(@ModelAttribute("groupe") GroupeDTO groupeDTO) throws NotFoundException {
//        groupeService.update(groupeDTO);
//        return "redirect:/groupes";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String delete(
//            @PathVariable Long id,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "10") int size,
//            @RequestParam(name = "keyword", defaultValue = "") String keyword
//    ) throws NotFoundException {
//        groupeService.delete(id);
//        return "redirect:/groupes?page="+page+"&size="+size+"&keyword="+keyword;
//    }




}
