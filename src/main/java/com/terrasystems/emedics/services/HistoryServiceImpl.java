package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.HistoryRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.History;
import com.terrasystems.emedics.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.NEVER)
public class HistoryServiceImpl implements HistoryService {



    @Autowired
    HistoryRepository historyRepository;

    @Override
    @Transactional
    public List<History> getAllHistories(){
        List<History> history = (List<History>) historyRepository.findAll();
        return history;
    }

    @Override
    public History getHistoryById(String id) {
        History history = historyRepository.findOne(id);
        return history;
    }

    @Override
    public History getHistoryByDate(Date date) {
        History history = (History) historyRepository.findAll();
        return history;
    }


}
