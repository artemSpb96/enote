package com.epam.enote.service;

import static com.epam.enote.service.utils.ServiceUtils.checkNotNull;
import static com.epam.enote.service.utils.ServiceUtils.noteBelongsToNotebook;
import static com.epam.enote.service.utils.ServiceUtils.notebookBelongsToUser;

import com.epam.enote.dao.impl.LabelDAO;
import com.epam.enote.dao.impl.NoteDAO;
import com.epam.enote.dao.impl.NotebookDAO;
import com.epam.enote.dao.impl.UserDAO;
import com.epam.enote.model.Label;
import com.epam.enote.model.Note;
import com.epam.enote.model.Notebook;
import com.epam.enote.model.User;
import com.epam.enote.service.exceptions.BadRequestException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NoteService {

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private NoteDAO noteDAO;

    @Autowired
    private LabelDAO labelDAO;

    public List<Note> getNotes(Long userId, Long notebookId) {
        Notebook notebook = notebookService.getNotebook(userId, notebookId);

        Hibernate.initialize(notebook.getNotes());

        return notebook.getNotes();
    }

    public Note getNote(Long userId, Long notebookId, Long noteId) {
        Notebook notebook = notebookService.getNotebook(userId, notebookId);

        Note note = noteDAO.findOne(noteId);
        checkNotNull(note);

        noteBelongsToNotebook(note, notebook);

        return note;
    }

    public void updateNote(Long userId, Long notebookId, Long noteId, Note updatedNote) {
        checkNotNull(updatedNote);

        Notebook notebook = notebookService.getNotebook(userId, notebookId);

        Note note = noteDAO.findOne(noteId);
        checkNotNull(note);

        noteBelongsToNotebook(note, notebook);

        note.setContent(updatedNote.getContent());
        note.setTimestamp(LocalDateTime.now());

        noteDAO.save(note);
    }

    public void updateContent(Long id, String content) {
        Note note = noteDAO.findOne(id);

        checkNotNull(note);

        note.setContent(content);
        note.setTimestamp(LocalDateTime.now());
        noteDAO.update(note);
    }

    public Long addLabel(Long noteId, Label label) {
        checkNotNull(label);

        Note note = noteDAO.findOne(noteId);

        checkNotNull(note);

        label.addNote(note);
        return labelDAO.save(label);
    }

    public void removeLabel(Long noteId, Long labelId) {
        Note note = noteDAO.findOne(noteId);
        checkNotNull(note);

        Set<Label> labelsToRemove = note.getLabels()
            .stream()
            .filter(label -> label.getId().equals(labelId))
            .collect(Collectors.toSet());

        if (labelsToRemove == null || labelsToRemove.isEmpty()) {
            throw new BadRequestException("no label with " + labelId + " in " + note);
        }

        note.getLabels().removeAll(labelsToRemove);

        noteDAO.update(note);
    }

    public void deleteNote(Long id) {
        Note note = noteDAO.findOne(id);
        checkNotNull(note);

        noteDAO.delete(note);
    }
}
