package com.epam.enote.dao.impl;

import com.epam.enote.dao.AbstractHibernateDAO;
import com.epam.enote.dao.interfaces.NotebookDAO;
import com.epam.enote.model.Notebook;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NotebookDAOImpl extends AbstractHibernateDAO<Notebook> implements NotebookDAO {

    public NotebookDAOImpl() {
        setClazz(Notebook.class);
    }
}
