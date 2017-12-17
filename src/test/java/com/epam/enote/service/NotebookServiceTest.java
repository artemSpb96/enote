package com.epam.enote.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.enote.dao.impl.NoteDAO;
import com.epam.enote.dao.impl.NotebookDAO;
import com.epam.enote.model.Note;
import com.epam.enote.model.Notebook;
import com.epam.enote.model.User;
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
public class NotebookServiceTest {

    private static final Long USER_ID = 1L;
    private static final Long NOTEBOOK_ID_1 = 1L;
    private static final Long NOTEBOOK_ID_2 = 2L;
    private static final Long NOTEBOOK_ID_3 = 3L;
    private static final Long NOTE_ID = 1L;

    @Mock
    private UserService userService;

    @Mock
    private NotebookDAO notebookDAO;

    @Mock
    private NoteDAO noteDAO;

    @InjectMocks
    private NotebookService notebookService;

    private User user;

    private Notebook notebook1;

    private Notebook notebook2;

    private Notebook notebook3;

    private Note note;

    private List<Notebook> notebooks;

    @Before
    public void setUp() {

        user = new User();
        user.setId(USER_ID);

        notebook1 = new Notebook();
        notebook1.setId(NOTEBOOK_ID_1);

        notebook2 = new Notebook();
        notebook2.setId(NOTEBOOK_ID_2);

        notebook3 = new Notebook();
        notebook3.setId(NOTEBOOK_ID_3);

        note = new Note();
        note.setId(NOTE_ID);

        notebook1.addNote(note);

        notebooks = new ArrayList<>();
        notebooks.add(notebook1);
        notebooks.add(notebook2);

        user.setNotebooks(notebooks);

        when(userService.getUser(anyLong())).thenReturn(user);
    }

    @Test
    public void getNotebooks() {
        List<Notebook> notebooks = notebookService.getNotebooks(USER_ID);

        assertEquals(user.getNotebooks(), notebooks);

        verify(userService).getUser(USER_ID);
    }

    @Test
    public void getNotebook() {
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook1);

        Notebook notebook = notebookService.getNotebook(USER_ID, NOTEBOOK_ID_1);

        assertEquals(notebook1, notebook);

        verify(userService).getUser(USER_ID);
        verify(notebookDAO).findOne(NOTEBOOK_ID_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotebook_NotFoundNotebook() {
        when(notebookDAO.findOne(anyLong())).thenReturn(null);

        notebookService.getNotebook(USER_ID, NOTEBOOK_ID_1);
    }

    @Test(expected = BadRequestException.class)
    public void getNotebook_NotebookNotBelongsToUser() {
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook3);

        notebookService.getNotebook(USER_ID, NOTEBOOK_ID_3);
    }

    @Test
    public void addNote() {
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook1);
        when(noteDAO.save(any())).thenReturn(NOTE_ID);

        Long noteId = notebookService.addNote(USER_ID, NOTEBOOK_ID_1, note);

        assertEquals(NOTE_ID, noteId);
        assertEquals(notebook1, note.getNotebook());
        assertNotNull(note.getTimestamp());

        verify(userService).getUser(USER_ID);
        verify(notebookDAO).findOne(NOTEBOOK_ID_1);
        verify(noteDAO).save(any(Note.class));
    }

    @Test(expected = NotFoundException.class)
    public void addNote_NotFoundNotebook() {
        when(notebookDAO.findOne(anyLong())).thenReturn(null);

        notebookService.addNote(USER_ID, NOTEBOOK_ID_1, note);
    }


    @Test(expected = NotFoundException.class)
    public void addNote_NullNote() {
        notebookService.addNote(USER_ID, NOTEBOOK_ID_1, null);
    }

    @Test(expected = BadRequestException.class)
    public void addNote_NotebookNotBelongsToUser() {
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook3);

        notebookService.addNote(USER_ID, NOTEBOOK_ID_3, note);
    }

    @Test
    public void removeNote() {
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook1);
        when(noteDAO.findOne(anyLong())).thenReturn(note);

        notebookService.removeNote(USER_ID, NOTEBOOK_ID_1, NOTE_ID);

        verify(userService).getUser(USER_ID);
        verify(notebookDAO).findOne(NOTEBOOK_ID_1);
        verify(noteDAO).findOne(NOTE_ID);
        verify(noteDAO).delete(any(Note.class));
    }

    @Test(expected = NotFoundException.class)
    public void removeNote_NotFoundNotebook() {
        when(notebookDAO.findOne(anyLong())).thenReturn(null);

        notebookService.removeNote(USER_ID, NOTEBOOK_ID_1, NOTE_ID);
    }

    @Test(expected = NotFoundException.class)
    public void removeNote_NotFoundNote() {
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook1);
        when(noteDAO.findOne(anyLong())).thenReturn(null);

        notebookService.removeNote(USER_ID, NOTEBOOK_ID_1, NOTE_ID);
    }

    @Test(expected = BadRequestException.class)
    public void removeNote_NotebookNotBelongsToUser() {
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook3);

        notebookService.removeNote(USER_ID, NOTEBOOK_ID_1, NOTE_ID);
    }

    @Test(expected = BadRequestException.class)
    public void removeNote_NoteNotBelongsToNotebook() {
        when(notebookDAO.findOne(anyLong())).thenReturn(notebook2);
        when(noteDAO.findOne(anyLong())).thenReturn(note);

        notebookService.removeNote(USER_ID, NOTEBOOK_ID_2, NOTE_ID);
    }
}