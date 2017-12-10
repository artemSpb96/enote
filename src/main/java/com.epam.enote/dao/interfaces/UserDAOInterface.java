package com.epam.enote.dao.interfaces;

import com.epam.enote.model.User;
import java.util.List;

public interface UserDAOInterface {
    List<User> findAllByName(String name);
}
