package com.epam.enote.dao;

import com.epam.enote.model.Label;

public class LabelDAO extends AbstractHibernateDAO<Label> {

    public LabelDAO() {
        setClazz(Label.class);
    }
}
