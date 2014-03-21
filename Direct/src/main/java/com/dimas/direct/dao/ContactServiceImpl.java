package com.dimas.direct.dao;

import com.dimas.direct.domain.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Copyright 2009-2014. NxSystems Inc.
 * PROPRIETARY/CONFIDENTIAL.
 * <p/>
 * Created: 08.02.14 20:12
 *
 * @author Dmitry Borovoy
 */
@Service("directContactService")
public class ContactServiceImpl implements ContactService {

    public final static String driverClassName = "org.postgresql.Driver";
    @Value("${jdbc.url}")
    public String url;
    @Value("${jdbc.username}")
    public String username;
    @Value("${jdbc.password}")
    public String password;

    private final static Logger l = LoggerFactory.getLogger(ContactServiceImpl.class);

    static {
        try {
            l.debug("driver: {}", driverClassName);
            Class.forName(driverClassName);
        } catch (ClassNotFoundException ex) {
            l.error(ex.getMessage());
        }
    }

    /**
     * Gets a connection
     *
     * @return Connection object
     * @throws java.sql.SQLException if the connection cannot be established
     */
    private Connection getConnection() throws SQLException {
        l.debug("url: {}", url);
        l.debug("username: {}", username);
        l.debug("password: {}", password);
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Safely closes connection
     *
     * @param connection Connection to close. Can be null.
     */
    private void closeConnection(Connection connection) {
        if (connection == null)
            return;
        try {
            connection.close();
        } catch (SQLException ex) {
            l.error(ex.getMessage());
        }
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> result = new ArrayList<Contact>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from contact");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getLong("id"));
                contact.setFirstName(resultSet.getString("first_name"));
                contact.setLastName(resultSet.getString("last_name"));
                contact.setBirthDate(resultSet.getDate("birth_date"));
                result.add(contact);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public Contact findById(Long id) {
        return null;
    }

    @Override
    public Contact save(Contact contact) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "insert into Contact (first_name, last_name, birth_date) values (?, ?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                contact.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return contact;
    }

    @Override
    public void delete(Contact contact) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from contact where id=?");
            statement.setLong(1, contact.getId());
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }
}