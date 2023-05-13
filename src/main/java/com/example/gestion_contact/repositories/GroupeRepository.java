package com.example.gestion_contact.repositories;

import com.example.gestion_contact.models.Groupe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {

    boolean existsByNom(String nom);

    Page<Groupe> findByNomContains(String nom, Pageable pageable);


}
