package com.example.gestion_contact.services;

import com.example.gestion_contact.dto.contacts.ContactDTO;
import com.example.gestion_contact.dto.groupes.GroupeDTO;
import com.example.gestion_contact.dto.groupes.GroupeMapper;
import com.example.gestion_contact.models.Contact;
import com.example.gestion_contact.models.Groupe;
import com.example.gestion_contact.repositories.GroupeRepository;
import com.example.gestion_contact.exceptions.AlreadyExistsException;
import com.example.gestion_contact.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupeService {

    private final GroupeRepository groupeRepository;

    private final GroupeMapper groupeMapper;


    public GroupeDTO getById(Long id) throws NotFoundException {
        return groupeMapper.toGroupeDTO(groupeRepository.findById(id).orElseThrow(NotFoundException::new));
    }


    public GroupeDTO create(GroupeDTO groupeDTO) throws AlreadyExistsException {
        if (groupeRepository.existsByNom(groupeDTO.getNom()))
            throw new AlreadyExistsException();

        return groupeMapper.toGroupeDTO(
                groupeRepository.save(groupeMapper.createGroupe(groupeDTO))
        );
    }
    public void update(GroupeDTO groupeDTO) throws NotFoundException {
        Groupe groupe = groupeRepository.findById(groupeDTO.getId())
                .orElseThrow(NotFoundException::new);

        groupeMapper.updateGroupeFromDTO(groupeDTO, groupe);
        groupeRepository.save(groupe);
    }

    public void delete(Long id) throws NotFoundException {
        Groupe groupe = groupeRepository.findById(id).orElseThrow(NotFoundException::new);
        groupeRepository.delete(groupe);
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

}
