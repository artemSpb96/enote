package com.epam.enote.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.epam.enote.config.TestDatabaseConfig;
import com.epam.enote.dao.impl.UserDAOImpl;
import com.epam.enote.model.User;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDatabaseConfig.class, UserDAOImpl.class})
@Transactional
public class ITUserDAOTest {

    private static final String TEST_NAME_1 = "test1";
    private static final String TEST_NAME_2 = "test2";

    @Autowired
    UserDAOImpl userDAO;

    @Before
    public void setUp() {
        User user1 = new User(TEST_NAME_1, TEST_NAME_1);
        User user2 = new User(TEST_NAME_2, TEST_NAME_2);

        userDAO.save(user1);
        userDAO.save(user2);

    }

    @After
    public void clear() {
        List<User> users = userDAO.findAll();

        for (User user : users) {
            userDAO.delete(user);
        }
    }

    @Test
    public void testSave() {
        User user = new User("testName", "testSurname");

        Long id = userDAO.save(user);

        assertEquals(user, userDAO.findOne(id));
    }

    @Test
    public void testFindAllByName() {
        List<User> users = userDAO.findAllByName(TEST_NAME_1);

        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    public void testUpdate() {
        List<User> users = userDAO.findAllByName(TEST_NAME_2);
        User user = users.get(0);

        String updatedName = "newName";
        user.setName(updatedName);

        userDAO.update(user);

        assertEquals(updatedName, userDAO.findOne(user.getId()).getName());
    }

    @Test
    public void testDelete() {
        List<User> users = userDAO.findAllByName(TEST_NAME_1);
        User user = users.get(0);

        userDAO.delete(user);

        assertNull(userDAO.findOne(user.getId()));
    }

    @Test
    public void testFindAll() {
        List<User> users = userDAO.findAll();

        assertEquals(2, users.size());
    }

}