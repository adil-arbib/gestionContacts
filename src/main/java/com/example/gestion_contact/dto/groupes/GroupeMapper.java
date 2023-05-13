package com.example.gestion_contact.dto.groupes;

import com.example.gestion_contact.dto.contacts.ContactDTO;
import com.example.gestion_contact.models.Contact;
import com.example.gestion_contact.models.Groupe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupeMapper {


    @Mapping(target = "contacts", ignore = true)
    Groupe createGroupe(GroupeDTO groupeDTO);

    @Mapping(target = "contacts", ignore = true)
    GroupeDTO toGroupeDTO(Groupe groupe);


    @Mapping(target = "contacts", ignore = true)
    List<GroupeDTO> toGroupeDTOList(List<Groupe> groupes);

    void updateGroupeFromDTO(GroupeDTO groupeDTO, @MappingTarget Groupe groupe);



}
