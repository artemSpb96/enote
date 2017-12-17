package com.epam.enote.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.enote.dao.impl.NoteDAO;
import com.epam.enote.model.Note;
import com.epam.enote.model.Notebook;
import com.epam.enote.service.exceptions.BadRequestException;
import com.epam.enote.service.exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceTest {
    private static final Long USER_ID = 1L;
    private static final Long NOTEBOOK_ID = 1L;
    private static final Long NOTE_ID_1 = 1L;
    private static final Long NOTE_ID_2 = 2L;

    @Mock
    private NotebookService notebookService;

    @Mock
    private NoteDAO noteDAO;

    @InjectMocks
    private NoteService noteService;

    private Notebook notebook;

    private Note note1;

    private Note note2;

    private Note updatedNote;

    @Before
    public void setUp() {
        notebook = new Notebook();
        notebook.setId(NOTEBOOK_ID);

        note1 = new Note();
        note1.setId(NOTE_ID_1);

        note2 = new Note();
        note2.setId(NOTE_ID_2);

        updatedNote = new Note();
        updatedNote.setContent("updated");

        notebook.addNote(note1);

        when(notebookService.getNotebook(anyLong(), anyLong())).thenReturn(notebook);
    }

    @Test
    public void getNotes() {
        List<Note> notes = noteService.getNotes(USER_ID, NOTEBOOK_ID);

        assertEquals(notebook.getNotes(), notes);

        verify(notebookService).getNotebook(USER_ID, NOTEBOOK_ID);
    }

    @Test
    public void getNote() {
        when(noteDAO.findOne(anyLong())).thenReturn(note1);

        Note note = noteService.getNote(USER_ID, NOTEBOOK_ID, NOTE_ID_1);

        assertEquals(note1, note);

        verify(notebookService).getNotebook(USER_ID, NOTEBOOK_ID);
        verify(noteDAO).findOne(NOTE_ID_1);
    }


    @Test(expected = NotFoundException.class)
    public void getNote_NotFoundNote() {
        when(noteDAO.findOne(anyLong())).thenReturn(null);

        noteService.getNote(USER_ID, NOTEBOOK_ID, NOTE_ID_1);
    }

    @Test(expected = BadRequestException.class)
    public void getNote_NoteNotBelongsToNotebook() {
        when(noteDAO.findOne(anyLong())).thenReturn(note2);

        noteService.getNote(USER_ID, NOTEBOOK_ID, NOTE_ID_2);
    }

    @Test
    public void updateNote() {
        when(noteDAO.findOne(anyLong())).thenReturn(note1);

        noteService.updateNote(USER_ID, NOTEBOOK_ID, NOTE_ID_1, updatedNote);

        assertEquals(updatedNote.getContent(), note1.getContent());
        assertNotNull(note1.getTimestamp());

        verify(notebookService).getNotebook(USER_ID, NOTEBOOK_ID);
        verify(noteDAO).findOne(NOTE_ID_1);
        verify(noteDAO).save(any(Note.class));
    }

    @Test(expected = NotFoundException.class)
    public void updateNote_NotFoundNote() {
        when(noteDAO.findOne(anyLong())).thenReturn(null);

        noteService.updateNote(USER_ID, NOTEBOOK_ID, NOTE_ID_1, updatedNote);
    }

    @Test(expected = BadRequestException.class)
    public void updateNote_NoteNotBelongsToNotebook() {
        when(noteDAO.findOne(anyLong())).thenReturn(note2);

        noteService.updateNote(USER_ID, NOTEBOOK_ID, NOTE_ID_1, updatedNote);
    }

    @Test(expected = NotFoundException.class)
    public void updateNote_NullUpdatedNote() {
        noteService.updateNote(USER_ID, NOTEBOOK_ID, NOTE_ID_1, null);
    }

}