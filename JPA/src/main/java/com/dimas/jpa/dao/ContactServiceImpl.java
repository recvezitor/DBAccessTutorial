package com.dimas.jpa.dao;

import com.dimas.jpa.domain.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Copyright 2009-2014. NxSystems Inc.
 * PROPRIETARY/CONFIDENTIAL.
 * <p/>
 * Created: 08.02.14 20:12
 *
 * @author Dmitry Borovoy
 */

@Service("jpaContactService")
@Transactional
public class ContactServiceImpl implements ContactService {

    private final Logger l = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    public List<Contact> findAll() {
        List<Contact> contacts = em.createNamedQuery("Contact.findAll", Contact.class).getResultList();
        return contacts;
    }

    @Transactional(readOnly = true)
    public Contact findById(Long id) {
        TypedQuery<Contact> query = em.createNamedQuery("Contact.findById", Contact.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public Contact save(Contact contact) {
        if (contact.getId() == null) { // Insert Contact
            l.info("Inserting new contact");
            em.persist(contact);
        } else {                       // Update Contact
            em.merge(contact);
            l.info("Updating existing contact");
        }
        l.info("Contact saved with id: " + contact.getId());
        return contact;
    }

    public void delete(Contact contact) {
        Contact mergedContact = em.merge(contact);
        em.remove(mergedContact);
        l.info("Contact with id: " + contact.getId() + " deleted successfully");
    }

    final static String ALL_CONTACT_NATIVE_QUERY =
            "select * from contact";

    @Transactional(readOnly = true)
    public List<Contact> findAllByNativeQuery() {
        return em.createNativeQuery(ALL_CONTACT_NATIVE_QUERY, Contact.class).getResultList();
    }
}
