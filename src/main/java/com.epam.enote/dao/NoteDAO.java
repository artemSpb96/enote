package com.epam.enote.dao;

import com.epam.enote.model.Note;

public class NoteDAO extends AbstractHibernateDAO<Note> {

    public NoteDAO() {
        setClazz(Note.class);
    }
}
