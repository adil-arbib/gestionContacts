package com.example.gestion_contact.services;

import com.example.gestion_contact.dto.contacts.ContactDTO;
import com.example.gestion_contact.dto.contacts.ContactMapper;
import com.example.gestion_contact.dto.groupes.GroupeDTO;
import com.example.gestion_contact.dto.groupes.GroupeMapper;
import com.example.gestion_contact.models.Contact;
import com.example.gestion_contact.models.Groupe;
import com.example.gestion_contact.repositories.ContactRepository;
import com.example.gestion_contact.repositories.GroupeRepository;
import com.example.gestion_contact.exceptions.AlreadyExistsException;
import com.example.gestion_contact.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupeService {

    private final GroupeRepository groupeRepository;
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    private final GroupeMapper groupeMapper;


    public GroupeDTO getById(Long id) throws NotFoundException {
        Groupe groupe = groupeRepository.findById(id).orElseThrow(NotFoundException::new);
        GroupeDTO groupeDTO = groupeMapper.toGroupeDTO(groupe);
        groupeDTO.setContacts(groupe.getContacts().stream().map(contactMapper::toContactDTO).collect(Collectors.toList()));
        groupeDTO.setSize(groupe.getContacts().size());
        return groupeDTO;
    }


    public GroupeDTO create(GroupeDTO groupeDTO, List<Long> contactsIds) throws AlreadyExistsException, NotFoundException {
        if (groupeRepository.existsByNom(groupeDTO.getNom()))
            throw new AlreadyExistsException();

        Groupe groupe = groupeMapper.createGroupe(groupeDTO);
        if(contactsIds != null && !contactsIds.isEmpty()) {
            List<Contact> contacts = new ArrayList<>();
            for(Long contactId : contactsIds) {
                Contact contact = contactRepository.findById(contactId).orElseThrow(NotFoundException::new);
                contacts.add(contact);
            }
            groupe.setContacts(contacts);
        }
        return groupeMapper.toGroupeDTO(groupeRepository.save(groupe));
    }
    public void update(GroupeDTO groupeDTO) throws NotFoundException {
        Groupe groupe = groupeRepository.findById(groupeDTO.getId())
                .orElseThrow(NotFoundException::new);
        groupe.setNom(groupeDTO.getNom());
        groupeRepository.save(groupe);
    }

    public List<ContactDTO> getRestContacts(Long groupId) throws NotFoundException {
        Groupe groupe = groupeRepository.findById(groupId)
                .orElseThrow(NotFoundException::new);
        List<Contact> contacts = contactRepository.findByIdNotIn(groupe.getContacts()
                .stream().map(Contact::getId).collect(Collectors.toList()));
        return contacts.isEmpty()
                ? contactRepository.findAll().stream().map(contactMapper::toContactDTO).collect(Collectors.toList())
                : contacts.stream().map(contactMapper::toContactDTO).collect(Collectors.toList());
    }



    public void delete(Long id) throws NotFoundException {
        Groupe groupe = groupeRepository.findById(id).orElseThrow(NotFoundException::new);
        groupeRepository.delete(groupe);
    }

    public void removeContact(Long groupId, Long contactId) throws NotFoundException {
        Groupe groupe = groupeRepository.findById(groupId).orElseThrow(NotFoundException::new);
        Contact contact = contactRepository.findById(contactId).orElseThrow(NotFoundException::new);
        groupe.getContacts().remove(contact);
        groupeRepository.save(groupe);
    }


    public long count() {
        return groupeRepository.count();
    }

//    public List<GroupeDTO> getAll(int page, int size) {
//        return groupeRepository.findAll(PageRequest.of(page,size))
//                .stream()
//                .map(groupe -> {
//                    GroupeDTO groupeDTO = groupeMapper.toGroupeDTO(groupe);
//                    groupeDTO.setSize(groupe.getContacts().size());
//                    return groupeDTO;
//                }).collect(Collectors.toList());
//    }

    public Page<GroupeDTO> getAll(int page, int size) {
        return groupeRepository.findAll(PageRequest.of(page, size))
                .map(grp -> {
                    GroupeDTO groupeDTO = groupeMapper.toGroupeDTO(grp);
                    groupeDTO.setSize(grp.getContacts().size());
                    return groupeDTO;
                });
    }

    public Page<GroupeDTO> searchByNomContains(String keyword, int page, int size) {
        return groupeRepository.findByNomContains(keyword, PageRequest.of(page, size))
                .map(grp -> {
                    GroupeDTO groupeDTO = groupeMapper.toGroupeDTO(grp);
                    groupeDTO.setSize(grp.getContacts().size());
                    return groupeDTO;
                });
    }

    public Page<GroupeDTO> getGroupePage(int page, int size, String keyword) {
        return keyword.isEmpty()
                ? getAll(page, size) : searchByNomContains(keyword, page, size);
    }

    public void addContactsToGroup(Long groupId, List<Long> contactIds) throws NotFoundException {
        Groupe groupe = groupeRepository.findById(groupId).orElseThrow(NotFoundException::new);
        groupe.getContacts().addAll(contactRepository.findByIdIn(contactIds));
        groupeRepository.save(groupe);
    }

}
