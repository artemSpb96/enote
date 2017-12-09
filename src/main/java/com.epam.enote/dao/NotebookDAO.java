package com.epam.enote.dao;

import com.epam.enote.model.Notebook;

public class NotebookDAO extends AbstractHibernateDAO<Notebook> {

    public NotebookDAO() {
        setClazz(Notebook.class);
    }
}
