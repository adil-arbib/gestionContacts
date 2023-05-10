package com.example.gestion_contact.services;

import com.example.gestion_contact.dto.contacts.ContactDTO;
import com.example.gestion_contact.dto.contacts.ContactMapper;
import com.example.gestion_contact.exceptions.NotFoundException;
import com.example.gestion_contact.models.Contact;
import com.example.gestion_contact.models.Genre;
import com.example.gestion_contact.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;


    public ContactDTO create(ContactDTO contactDTO) {
        Contact contact = contactMapper.createContact(contactDTO);
        contact.setGenre(
                contactDTO.getGenre().equals("male")
                ? Genre.MALE : Genre.FEMALE
        );
        return contactMapper.toContactDTO(contactRepository.save(contact));
    }

    public List<ContactDTO> getAllByOrder() {
        return contactMapper.toContactDTOList(
                contactRepository.findAllByOrderByNom()
                        .orElse(null)
        );
    }

    public void delete(Long id) throws NotFoundException {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        contactRepository.delete(contact);
    }

    public void update(ContactDTO contactDTO) throws NotFoundException {
        Contact contact = contactRepository.findById(contactDTO.getId())
                .orElseThrow(NotFoundException::new);

        contactMapper.updateContactFromDTO(contactDTO, contact);
        contactRepository.save(contact);
    }

    public ContactDTO searchByNom(String nom) {
        return contactMapper.toContactDTO(contactRepository.findByNom(nom).orElse(null));
    }

    public ContactDTO searchByTel(String tel) {
        return contactMapper.toContactDTO(contactRepository.findByTel1OrTel2(tel, tel).orElse(null));
    }

    public List<ContactDTO> searchSoundex(String nom) {
        return contactMapper.toContactDTOList(contactRepository.findByNomSoundex(nom).orElse(null));
    }

    public long count() {
        return contactRepository.count();
    }





}
