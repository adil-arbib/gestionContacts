package com.example.gestion_contact.dto.groupes;

import com.example.gestion_contact.dto.contacts.ContactDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GroupeDTO {

    private Long id;

    private String nom;

    private List<ContactDTO> contacts;

    private int size;
}
