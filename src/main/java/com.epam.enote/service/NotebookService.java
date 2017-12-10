package com.epam.enote.service;

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
        return notebookDAO.findOne(notebookId);
    }

    public Long addNote(Long notebookId, Note note) {
        Notebook notebook = notebookDAO.findOne(notebookId);

        note.setTimestamp(LocalDateTime.now());
        note.setNotebook(notebook);
        return noteDAO.save(note);
    }

    public void removeNote(Long notebookId, Note note) {
        noteDAO.delete(note);
    }

    public void deleteNotebook(Long id) {
        Notebook notebook = notebookDAO.findOne(id);
        notebookDAO.delete(notebook);
    }
}
