package com.dimas.springjdbc;

import com.dimas.springjdbc.dao.ContactService;
import com.dimas.springjdbc.domain.Contact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Copyright 2009-2014. NxSystems Inc.
 * PROPRIETARY/CONFIDENTIAL.
 * <p/>
 * Created: 08.02.14 19:16
 *
 * @author Dmitry Borovoy
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-config.xml"})
public class SpringJDBCFunctionalTest {

    protected final Logger l = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("springJDBCContactService")
    ContactService contactService;

    @Before
    public void setUp() throws Exception {
        l.info("Started");
        assertNotNull(contactService);
    }

    @Test
    public void testName() throws Exception {
        List<Contact> contactList = contactService.findAll();
        assertNotNull(contactList);
        l.info("size={}", contactList.size());

    }
}
