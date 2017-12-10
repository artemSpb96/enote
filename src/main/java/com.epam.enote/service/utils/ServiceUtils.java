package com.epam.enote.service.utils;

import com.epam.enote.model.Label;
import com.epam.enote.model.Note;
import com.epam.enote.model.Notebook;
import com.epam.enote.model.User;
import com.epam.enote.service.exceptions.BadRequestException;
import com.epam.enote.service.exceptions.NotFoundException;
import java.util.List;

public class ServiceUtils {

    public static void checkNotNull(User user) {
        if (user == null) {
            throw new NotFoundException("user was not found");
        }
    }

    public static void checkNotNull(Notebook notebook) {
        if (notebook == null) {
            throw new NotFoundException("notebook was not found");
        }
    }

    public static void checkNotNull(Note note) {
        if (note == null) {
            throw new NotFoundException("note was not found");
        }
    }

    public static void checkNotNull(Label label) {
        if (label == null) {
            throw new NotFoundException("label was not found");
        }
    }

    public static void notebookBelongsToUser(Notebook notebook, User user) {
        List<Notebook> userNotebooks = user.getNotebooks();

        if (userNotebooks == null
            || userNotebooks
            .stream()
            .noneMatch(userNotebook -> userNotebook.getId().equals(notebook.getId()))) {
            throw new BadRequestException(notebook + " does not belong to " + user);
        }
    }

    public static void noteBelongsToNotebook(Note note, Notebook notebook) {
        List<Note> notebookNotes = notebook.getNotes();

        if (notebookNotes == null
            || notebookNotes
            .stream()
            .noneMatch(notebookNote -> notebookNote.getId().equals(note.getId()))) {
            throw new BadRequestException(note + " does not belong to " + notebook);
        }
    }
}
