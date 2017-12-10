package com.epam.enote.service;

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
    UserDAO userDAO;

    @Autowired
    NotebookDAO notebookDAO;

    //TODO add exceptions
    public User getUser(Long userId) {
        return userDAO.findOne(userId);
    }

    public Long addNotebook(Long userId, Notebook notebook) {
        User user = userDAO.findOne(userId);

        notebook.setId(user.getId());
        notebook.setTimestamp(LocalDateTime.now());

        return notebookDAO.save(notebook);
    }

    public void removeNotebook(Long userId, Long notebookId) {
        Notebook notebook = notebookDAO.findOne(notebookId);

        notebookDAO.delete(notebook);
    }

    public void updateName(Long id, String name) {
        User user = userDAO.findOne(id);
        user.setName(name);
        userDAO.update(user);
    }

    public void updateSurname(Long id, String surname) {
        User user = userDAO.findOne(id);
        user.setSurname(surname);
        userDAO.update(user);
    }

    public void deleteUser(Long id) {
        User user = userDAO.findOne(id);
        userDAO.delete(user);
    }
}
