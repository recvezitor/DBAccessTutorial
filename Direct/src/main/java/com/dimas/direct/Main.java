package com.dimas.direct;

import com.dimas.direct.dao.ContactService;
import com.dimas.direct.domain.Contact;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StopWatch;

import java.util.List;

public class Main {

    public static Logger l = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // initializing log4j
        DOMConfigurator.configureAndWatch("log4j.xml", 60000);

        // creating and initializing main context
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring-config.xml");
        ctx.registerShutdownHook();

        ContactService contactService = ctx.getBean("directContactService", ContactService.class);
        StopWatch watch = new StopWatch();

        watch.start();
        List<Contact> contactList1 = contactService.findAll();
        l.info("size3={}", contactList1.size());
        l.info("el[0]={}", contactList1.get(0).getFirstName());
        watch.stop();
        l.info("Total execution time {} ms", watch.getLastTaskTimeMillis());
    }
}