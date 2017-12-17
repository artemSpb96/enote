package com.epam.enote.service;

import static com.epam.enote.service.utils.ServiceUtils.checkNotNull;
import static com.epam.enote.service.utils.ServiceUtils.noteBelongsToNotebook;
import static com.epam.enote.service.utils.ServiceUtils.notebookBelongsToUser;

import com.epam.enote.dao.impl.NoteDAO;
import com.epam.enote.dao.impl.NotebookDAO;
import com.epam.enote.dao.impl.UserDAO;
import com.epam.enote.model.Note;
import com.epam.enote.model.Notebook;
import com.epam.enote.model.User;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NotebookService {

    @Autowired
    private NotebookDAO notebookDAO;

    @Autowired
    private NoteDAO noteDAO;

    @Autowired
    private UserService userService;

    public List<Notebook> getNotebooks(Long userId) {
        User user = userService.getUser(userId);

        Hibernate.initialize(user.getNotebooks());

        return user.getNotebooks();
    }

    public Notebook getNotebook(Long userId, Long notebookId) {
        User user = userService.getUser(userId);

        Notebook notebook = notebookDAO.findOne(notebookId);
        checkNotNull(notebook);

        notebookBelongsToUser(notebook, user);

        return notebook;
    }

    public Long addNote(Long userId, Long notebookId, Note note) {
        checkNotNull(note);

        User user = userService.getUser(userId);

        Notebook notebook = notebookDAO.findOne(notebookId);
        checkNotNull(notebook);

        notebookBelongsToUser(notebook, user);

        note.setTimestamp(LocalDateTime.now());
        note.setNotebook(notebook);

        return noteDAO.save(note);
    }

    public void removeNote(Long userId, Long notebookId, Long noteId) {
        User user = userService.getUser(userId);

        Notebook notebook = notebookDAO.findOne(notebookId);
        checkNotNull(notebook);

        notebookBelongsToUser(notebook, user);

        Note note = noteDAO.findOne(noteId);
        checkNotNull(note);

        noteBelongsToNotebook(note, notebook);

        noteDAO.delete(note);
    }

    public void deleteNotebook(Long id) {
        Notebook notebook = notebookDAO.findOne(id);
        checkNotNull(notebook);

        notebookDAO.delete(notebook);
    }
}
