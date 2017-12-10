package com.epam.enote.dao.impl;

import com.epam.enote.dao.AbstractHibernateDAO;
import com.epam.enote.dao.interfaces.NotebookDAOInterface;
import com.epam.enote.model.Notebook;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NotebookDAO extends AbstractHibernateDAO<Notebook> implements
    NotebookDAOInterface {

    public NotebookDAO() {
        setClazz(Notebook.class);
    }
}
