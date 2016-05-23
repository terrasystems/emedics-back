package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.History;

import java.util.Date;
import java.util.List;

public interface HistoryService {
    List<History> getAllHistories();
    History getHistoryById(String id);
    History getHistoryByDate(Date date);
}
