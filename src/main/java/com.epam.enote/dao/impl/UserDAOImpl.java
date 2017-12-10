package com.epam.enote.dao.impl;

import com.epam.enote.dao.AbstractHibernateDAO;
import com.epam.enote.dao.interfaces.UserDAO;
import com.epam.enote.model.User;
import java.util.List;
import org.hibernate.query.Query;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserDAOImpl extends AbstractHibernateDAO<User> implements UserDAO {
    public UserDAOImpl() {
        setClazz(User.class);
    }

    @Override
    public List<User> findAllByName(String name) {
        Query<User> query = getCurrentSession()
            .createQuery("from " + User.class.getName() + " where name = '" + name + "'",
                User.class);
        return query.list();
    }
}
