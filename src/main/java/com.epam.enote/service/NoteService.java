package com.epam.enote.service;

import com.epam.enote.dao.impl.LabelDAO;
import com.epam.enote.dao.impl.NoteDAO;
import com.epam.enote.model.Label;
import com.epam.enote.model.Note;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NoteService {

    @Autowired
    private NoteDAO noteDAO;

    @Autowired
    private LabelDAO labelDAO;

    public Note getNote(Long id) {
        return noteDAO.findOne(id);
    }

    public void updateContent(Long id, String content) {
        Note note = noteDAO.findOne(id);
        note.setContent(content);
        note.setTimestamp(LocalDateTime.now());
        noteDAO.update(note);
    }

    public Long addLabel(Long noteId, Label label) {
        Note note = noteDAO.findOne(noteId);
        label.addNote(note);
        return labelDAO.save(label);
    }

    public void removeLabel(Long noteId, Long labelId) {
        Note note = noteDAO.findOne(noteId);
        Set<Label> labelsToRemove = note.getLabels()
            .stream()
            .filter(label -> label.getId().equals(labelId))
            .collect(Collectors.toSet());

        note.getLabels().removeAll(labelsToRemove);

        noteDAO.update(note);
    }

    public void deleteNote(Long id) {
        Note note = noteDAO.findOne(id);
        noteDAO.delete(note);
    }
}
