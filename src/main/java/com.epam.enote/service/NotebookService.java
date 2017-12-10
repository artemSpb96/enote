package com.epam.enote.service;

import static com.epam.enote.service.utils.ServiceUtils.checkNotNull;
import static com.epam.enote.service.utils.ServiceUtils.noteBelongsToNotebook;

import com.epam.enote.dao.impl.NoteDAO;
import com.epam.enote.dao.impl.NotebookDAO;
import com.epam.enote.model.Note;
import com.epam.enote.model.Notebook;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NotebookService {

    @Autowired
    private NotebookDAO notebookDAO;

    @Autowired
    private NoteDAO noteDAO;

    public Notebook getNotebook(Long notebookId) {
        Notebook notebook = notebookDAO.findOne(notebookId);
        checkNotNull(notebook);

        return notebook;
    }

    public Long addNote(Long notebookId, Note note) {
        checkNotNull(note);

        Notebook notebook = notebookDAO.findOne(notebookId);
        checkNotNull(notebook);

        note.setTimestamp(LocalDateTime.now());
        note.setNotebook(notebook);
        return noteDAO.save(note);
    }

    public void removeNote(Long notebookId, Note note) {
        checkNotNull(note);

        Notebook notebook = notebookDAO.findOne(notebookId);
        checkNotNull(notebook);

        noteBelongsToNotebook(note, notebook);

        noteDAO.delete(note);
    }

    public void deleteNotebook(Long id) {
        Notebook notebook = notebookDAO.findOne(id);
        checkNotNull(notebook);

        notebookDAO.delete(notebook);
    }
}
