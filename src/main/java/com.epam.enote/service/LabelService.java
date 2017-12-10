package com.epam.enote.service;

import static com.epam.enote.service.utils.ServiceUtils.checkNotNull;

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
        Label label = labelDAO.findOne(labelId);
        checkNotNull(label);

        return label;
    }

    public void delete(Long labelId) {
        Label label = labelDAO.findOne(labelId);
        checkNotNull(label);

        labelDAO.delete(label);
    }

}
