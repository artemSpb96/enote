package com.epam.enote.dao;

import com.epam.enote.model.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserDAO extends AbstractHibernateDAO<User> {
    public UserDAO() {
        setClazz(User.class);
    }
}
