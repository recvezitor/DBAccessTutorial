package com.dimas.direct.dao;


import com.dimas.direct.domain.Contact;

import java.util.List;

/**
 * Copyright 2009-2014. NxSystems Inc.
 * PROPRIETARY/CONFIDENTIAL.
 * <p/>
 * Created: 08.02.14 17:14
 *
 * @author Dmitry Borovoy
 */
public interface ContactService {

    // Find all contacts
    public List<Contact> findAll();

    // Find a contact with details by id
    public Contact findById(Long id);

    // Insert or update a contact
    public Contact save(Contact contact);

    // Delete a contact
    public void delete(Contact contact);

}
