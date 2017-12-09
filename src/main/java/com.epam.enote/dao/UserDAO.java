package com.epam.enote.dao;

import com.epam.enote.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    @Autowired
    SessionFactory sessionFactory;

    public User getById(Long id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    public void save(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

}
