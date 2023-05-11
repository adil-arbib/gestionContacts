package com.example.gestion_contact.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String tel1;

    private String tel2;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String emailPer;

    @Column(nullable = false)
    private String emailPro;

    @Column(nullable = false)
    private String genre;

    @ManyToMany(mappedBy = "contacts", fetch = FetchType.LAZY)
    private Collection<Groupe> groupes;

}
