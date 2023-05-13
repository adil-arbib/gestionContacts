package com.example.gestion_contact;

import com.example.gestion_contact.models.Contact;
import com.example.gestion_contact.repositories.ContactRepository;
import com.mysql.cj.log.Log;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.logging.Logger;

@SpringBootTest
class GestionContactApplicationTests {

    @Autowired
    private ContactRepository contactRepository;
    private final Logger log = Logger.getLogger("TEST");

    @Test
    void contextLoads() {
    }



}
