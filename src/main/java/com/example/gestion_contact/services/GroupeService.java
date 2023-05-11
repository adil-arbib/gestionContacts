package com.example.gestion_contact.services;

import com.example.gestion_contact.dto.groupes.GroupeDTO;
import com.example.gestion_contact.dto.groupes.GroupeMapper;
import com.example.gestion_contact.models.Groupe;
import com.example.gestion_contact.repositories.GroupeRepository;
import com.example.gestion_contact.exceptions.AlreadyExistsException;
import com.example.gestion_contact.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupeService {

    private final GroupeRepository groupeRepository;

    private final GroupeMapper groupeMapper;


    public GroupeDTO create(GroupeDTO groupeDTO) throws AlreadyExistsException {
        if (groupeRepository.existsByNom(groupeDTO.getNom()))
            throw new AlreadyExistsException();

        return groupeMapper.toGroupeDTO(
                groupeRepository.save(groupeMapper.createGroupe(groupeDTO))
        );
    }

    public void delete(Long id) throws NotFoundException {
        Groupe groupe = groupeRepository.findById(id).orElseThrow(NotFoundException::new);
        groupeRepository.delete(groupe);
    }

    public GroupeDTO searchByNom(String nom) {
        return groupeMapper.toGroupeDTO(
                groupeRepository.findByNom(nom)
                        .orElse(null)
        );
    }


    public long count() {
        return groupeRepository.count();
    }

    public List<GroupeDTO> getAll() {
        return groupeRepository.findAll()
                .stream()
                .map(groupe -> {
                    GroupeDTO groupeDTO = groupeMapper.toGroupeDTO(groupe);
                    groupeDTO.setSize(groupe.getContacts().size());
                    return groupeDTO;
                }).collect(Collectors.toList());
    }

}
