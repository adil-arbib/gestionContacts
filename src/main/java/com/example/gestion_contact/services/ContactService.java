package com.example.gestion_contact.services;

import com.example.gestion_contact.models.Contact;
import com.example.gestion_contact.repositories.ContactRepository;
import com.example.gestion_contact.dto.contacts.ContactDTO;
import com.example.gestion_contact.dto.contacts.ContactMapper;
import com.example.gestion_contact.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;


    public ContactDTO getById(Long id) throws NotFoundException {
        return contactMapper.toContactDTO(contactRepository.findById(id).orElseThrow(NotFoundException::new));
    }


    public ContactDTO create(ContactDTO contactDTO) {
        return contactMapper.toContactDTO(contactRepository.save(contactMapper.createContact(contactDTO)));
    }

    public List<ContactDTO> getAll() {
        return contactMapper.toContactDTOList(contactRepository.findAll());
    }

    public Page<ContactDTO> getAllByOrder(int page, int size) {
        Page<Contact> contactPage = contactRepository.findAllByOrderByNom(PageRequest.of(page, size));
        return contactPage.map(contactMapper::toContactDTO);
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

    public Page<ContactDTO> searchByNom(String nom, int page, int size) {
        Page<Contact> contactPage = contactRepository.findByNomContains(nom, PageRequest.of(page, size));
        System.out.println(contactPage.getTotalElements());
        return contactPage.map(contactMapper::toContactDTO);
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


    public Page<ContactDTO> getContactPage(int page, int size, String keyword) {
        return keyword.isEmpty() ? getAllByOrder(page, size) : searchByNom(keyword, page, size);
    }


}
