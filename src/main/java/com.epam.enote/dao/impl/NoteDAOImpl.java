package com.epam.enote.dao.impl;

import com.epam.enote.dao.AbstractHibernateDAO;
import com.epam.enote.dao.interfaces.NoteDAO;
import com.epam.enote.model.Note;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NoteDAOImpl extends AbstractHibernateDAO<Note> implements NoteDAO {

    public NoteDAOImpl() {
        setClazz(Note.class);
    }
}
