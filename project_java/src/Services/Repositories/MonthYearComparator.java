package Services.Repositories;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Comparator para ordenar por ano e mês
 */

public class MonthYearComparator implements Comparator<LocalDateTime>, Serializable{

    @Override
    public int compare(LocalDateTime o1, LocalDateTime o2) {

    if(o1.getYear() != o2.getYear()) {
        return o1.getYear() - o2.getYear();
    }

    return o1.getMonthValue() - o2.getMonthValue();
    }
    
}
