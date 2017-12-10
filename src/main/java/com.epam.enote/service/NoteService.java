package com.epam.enote.service;

import static com.epam.enote.service.utils.ServiceUtils.checkNotNull;

import com.epam.enote.dao.impl.LabelDAO;
import com.epam.enote.dao.impl.NoteDAO;
import com.epam.enote.model.Label;
import com.epam.enote.model.Note;
import com.epam.enote.service.exceptions.BadRequestException;
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
        Note note = noteDAO.findOne(id);

        checkNotNull(note);

        return note;
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
