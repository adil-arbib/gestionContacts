package com.example.gestion_contact.repositories;

import com.example.gestion_contact.models.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {


    Page<Contact> findAllByOrderByNom(PageRequest pageRequest);

    Page<Contact> findByNomContains(String keyword, Pageable pageable);

    Optional<Contact> findByTel1OrTel2(String tel1, String tel2);

    @Query("SELECT c FROM Contact c WHERE SOUNDEX(c.nom) = SOUNDEX(:nom)")
    Optional<List<Contact>> findByNomSoundex(@Param("nom") String nom);

    List<Contact> findByIdNotIn(List<Long> ids);

    List<Contact> findByIdIn(List<Long> ids);



}
