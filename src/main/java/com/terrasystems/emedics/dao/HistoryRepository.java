package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.History;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface HistoryRepository extends CrudRepository<History, String>{
    //List<History> findOrderByDate();
    //History findOrderByDate(Date date);
}
