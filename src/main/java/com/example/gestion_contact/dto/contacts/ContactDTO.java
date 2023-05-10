package com.example.gestion_contact.dto.contacts;


import com.example.gestion_contact.dto.groupes.GroupeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class ContactDTO {

    private Long id;

    private String nom;

    private String prenom;

    private String tel1;

    private String tel2;

    private String adresse;

    private String emailPer;

    private String emailPro;

    private String genre;

   private List<GroupeDTO> groupes;


}
