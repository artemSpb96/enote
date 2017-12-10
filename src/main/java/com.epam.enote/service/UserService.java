package com.epam.enote.service;

import static com.epam.enote.service.utils.ServiceUtils.checkNotNull;
import static com.epam.enote.service.utils.ServiceUtils.notebookBelongsToUser;

import com.epam.enote.dao.impl.NotebookDAO;
import com.epam.enote.dao.impl.UserDAO;
import com.epam.enote.model.Notebook;
import com.epam.enote.model.User;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private NotebookDAO notebookDAO;


    public User getUser(Long userId) {
        User user = userDAO.findOne(userId);

        checkNotNull(user);

        return user;
    }

    public Long addNotebook(Long userId, Notebook notebook) {
        checkNotNull(notebook);

        User user = userDAO.findOne(userId);

        checkNotNull(user);

        notebook.setUser(user);
        notebook.setTimestamp(LocalDateTime.now());

        return notebookDAO.save(notebook);
    }

    public void removeNotebook(Long userId, Long notebookId) {
        User user = userDAO.findOne(userId);
        checkNotNull(user);

        Notebook notebook = notebookDAO.findOne(notebookId);
        checkNotNull(notebook);

        notebookBelongsToUser(notebook, user);

        notebookDAO.delete(notebook);
    }

    public void updateName(Long id, String name) {
        User user = userDAO.findOne(id);

        checkNotNull(user);

        user.setName(name);
        userDAO.update(user);
    }

    public void updateSurname(Long id, String surname) {
        User user = userDAO.findOne(id);

        checkNotNull(user);

        user.setSurname(surname);
        userDAO.update(user);
    }

    public void deleteUser(Long id) {
        User user = userDAO.findOne(id);

        checkNotNull(user);

        userDAO.delete(user);
    }
}
