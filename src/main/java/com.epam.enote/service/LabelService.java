package com.epam.enote.service;

import com.epam.enote.dao.impl.LabelDAO;
import com.epam.enote.model.Label;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDAO labelDAO;

    public Label getLabel(Long labelId) {
        return labelDAO.findOne(labelId);
    }

    public void delete(Long labelId) {
        Label label = labelDAO.findOne(labelId);
        labelDAO.delete(label);
    }

}
