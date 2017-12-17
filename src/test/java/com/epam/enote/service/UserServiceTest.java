package com.epam.enote.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.enote.dao.impl.NotebookDAO;
import com.epam.enote.dao.impl.UserDAO;
import com.epam.enote.model.Notebook;
import com.epam.enote.model.User;
import com.epam.enote.service.exceptions.BadRequestException;
import com.epam.enote.service.exceptions.NotFoundException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final Long USER_TEST_ID = 1L;
    private static final Long NOTEBOOK_TEST_ID = 1L;

    @Mock
    private UserDAO userDAO;

    @Mock
    private NotebookDAO notebookDAO;

    @InjectMocks
    private UserService userService;

    private User user;

    private Notebook notebook;

    @Before
    public void setUp() {
        user = new User();
        user.setId(USER_TEST_ID);
        user.setName("name");
        user.setSurname("surname");

        notebook = new Notebook();
        notebook.setId(NOTEBOOK_TEST_ID);
    }

    @Test
    public void getUser() {
        when(userDAO.findOne(anyLong())).thenReturn(user);

        User actualUser = userService.getUser(USER_TEST_ID);

        assertEquals(user, actualUser);

        verify(userDAO).findOne(USER_TEST_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getUser_NotFoundUser() {
        when(userDAO.findOne(anyLong())).thenReturn(null);

        userService.getUser(USER_TEST_ID);
    }

    @Test
    public void addNotebook() {
        when(userDAO.findOne(anyLong())).thenReturn(user);
        when(notebookDAO.save(any())).thenReturn(NOTEBOOK_TEST_ID);

        Long actualNotebookId = userService.addNotebook(USER_TEST_ID, notebook);

        assertEquals(NOTEBOOK_TEST_ID, actualNotebookId);
        assertEquals(user, notebook.getUser());
        assertNotNull(notebook.getTimestamp());

        verify(userDAO).findOne(USER_TEST_ID);
        verify(notebookDAO).save(any(Notebook.class));
    }

    @Test(expected = NotFoundException.class)
    public void addNotebook_NullNotebook() {
        userService.addNotebook(USER_TEST_ID, null);
    }

    @Test(expected = NotFoundException.class)
    public void addNotebook_NotFoundUser() {
        when(userDAO.findOne(anyLong())).thenReturn(null);

        userService.addNotebook(USER_TEST_ID, notebook);
    }

    @Test
    public void removeNotebook() {
        user.addNotebook(notebook);

        when(userDAO.findOne(anyLong())).thenReturn(user);
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook);

        userService.removeNotebook(USER_TEST_ID, NOTEBOOK_TEST_ID);

        verify(userDAO).findOne(USER_TEST_ID);
        verify(notebookDAO).findOne(NOTEBOOK_TEST_ID);
        verify(notebookDAO).delete(any(Notebook.class));
    }

    @Test(expected = NotFoundException.class)
    public void removeNotebook_NotFoundUser() {
        when(userDAO.findOne(anyLong())).thenReturn(null);
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook);

        userService.removeNotebook(USER_TEST_ID, NOTEBOOK_TEST_ID);
    }

    @Test(expected = NotFoundException.class)
    public void removeNotebook_NotFoundNotebook() {
        when(userDAO.findOne(anyLong())).thenReturn(user);
        when(notebookDAO.findOne(anyLong())).thenReturn(null);

        userService.removeNotebook(USER_TEST_ID, NOTEBOOK_TEST_ID);
    }

    @Test(expected = BadRequestException.class)
    public void removeNotebook_NotebookDoesnotBelongToUser() {
        when(userDAO.findOne(anyLong())).thenReturn(user);
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook);

        userService.removeNotebook(USER_TEST_ID, NOTEBOOK_TEST_ID);
    }

    @Test
    public void updateName() {
        String newName = "newName";
        when(userDAO.findOne(anyLong())).thenReturn(user);

        userService.updateName(USER_TEST_ID, newName);

        assertEquals(user.getName(), newName);

        verify(userDAO).findOne(USER_TEST_ID);
        verify(userDAO).update(any(User.class));
    }

    @Test(expected = NotFoundException.class)
    public void updateName_NotFoundUser() {
        when(userDAO.findOne(anyLong())).thenReturn(null);

        userService.updateName(USER_TEST_ID, "newName");
    }

    @Test
    public void updateSurname() {
        String newSurname = "newSurname";
        when(userDAO.findOne(anyLong())).thenReturn(user);

        userService.updateSurname(USER_TEST_ID, newSurname);

        assertEquals(user.getSurname(), newSurname);

        verify(userDAO).findOne(USER_TEST_ID);
        verify(userDAO).update(any(User.class));
    }

    @Test(expected = NotFoundException.class)
    public void updateSurname_NotFoundUser() {
        when(userDAO.findOne(anyLong())).thenReturn(null);

        userService.updateSurname(USER_TEST_ID, "newSurname");
    }

    @Test
    public void deleteUser() {
        when(userDAO.findOne(anyLong())).thenReturn(user);

        userService.deleteUser(USER_TEST_ID);

        verify(userDAO).delete(any(User.class));
        verify(userDAO).findOne(USER_TEST_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteUser_NotFound() {
        when(userDAO.findOne(anyLong())).thenReturn(null);

        userService.deleteUser(USER_TEST_ID);
    }
}