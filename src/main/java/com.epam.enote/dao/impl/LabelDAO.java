package com.epam.enote.dao.impl;

import com.epam.enote.dao.AbstractHibernateDAO;
import com.epam.enote.dao.interfaces.LabelDAOInterface;
import com.epam.enote.model.Label;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LabelDAO extends AbstractHibernateDAO<Label> implements LabelDAOInterface {

    public LabelDAO() {
        setClazz(Label.class);
    }
}
