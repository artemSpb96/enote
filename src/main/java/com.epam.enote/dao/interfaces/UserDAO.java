package com.epam.enote.dao.interfaces;

import com.epam.enote.model.User;
import java.util.List;

public interface UserDAO {
    List<User> findAllByName(String name);
}
