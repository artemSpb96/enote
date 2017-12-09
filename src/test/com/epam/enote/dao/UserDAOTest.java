package com.epam.enote.dao;

import static org.junit.Assert.assertEquals;

import com.epam.enote.config.TestDatabaseConfig;
import com.epam.enote.model.User;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDatabaseConfig.class, UserDAO.class})
@Transactional
public class UserDAOTest {

    @Autowired
    UserDAO userDAO;

    @Test
    public void testSave() {
        User user = new User(1L, "testName", "testSurname");

        userDAO.save(user);

        assertEquals(user, userDAO.getById(1L));
    }

}