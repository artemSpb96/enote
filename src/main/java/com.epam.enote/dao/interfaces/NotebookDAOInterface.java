package com.epam.enote.dao.interfaces;

import com.epam.enote.model.Notebook;
import java.util.List;

public interface NotebookDAOInterface {

    List<Notebook> findAllByUserId(Long userId);

}
