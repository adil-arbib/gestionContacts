package com.example.gestion_contact.dto.contacts;

import com.example.gestion_contact.models.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {


    @Mapping(target = "groupes", ignore = true)
    Contact createContact(ContactDTO contactDTO);

    @Mapping(target = "groupes", ignore = true)
    ContactDTO toContactDTO(Contact contact);


    @Mapping(target = "groupes", ignore = true)
    List<ContactDTO> toContactDTOList(List<Contact> contacts);

    void updateContactFromDTO(ContactDTO contactDTO, @MappingTarget Contact contact);



}
