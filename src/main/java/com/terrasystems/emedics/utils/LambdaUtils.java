package com.terrasystems.emedics.utils;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.TaskSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

final public class LambdaUtils {
    public static TaskSearchCriteria criteria;
    public static Specification<Event> datePredicate = (r, q, b) -> {
        if (criteria.getPeriod() == 1) {
            LocalDateTime now = LocalDateTime.now();
            LocalTime timeNow = now.toLocalTime();
            int hours = timeNow.getHour();
            LocalDateTime before = now.minusHours(hours);
            return b.between(r.get("date"), Date.from(before.atZone(ZoneId.systemDefault()).toInstant()), Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        }
        if (criteria.getPeriod() == 2) {
            LocalDateTime now = LocalDateTime.now();
            LocalTime timeNow = now.toLocalTime();
            int hours = timeNow.getHour();
            LocalDateTime to = now.minusHours(hours);
            LocalDateTime from = to.minusDays(1);
            return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
        }
        if (criteria.getPeriod() == 3) {
            LocalDateTime now = LocalDateTime.now();
            LocalTime timeNow = now.toLocalTime();
            int hours = timeNow.getHour();
            LocalDateTime to = now.minusHours(hours);
            LocalDateTime from = to.minusDays(7);
            return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
        }
        if (criteria.getPeriod() == 4) {
            LocalDateTime now = LocalDateTime.now();
            LocalTime timeNow = now.toLocalTime();
            int hours = timeNow.getHour();
            LocalDateTime to = now.minusHours(hours);
            LocalDateTime from = to.minusMonths(1);
            return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));

        }
        return null;
    };
}
