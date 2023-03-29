package lt.techin.schedule.schedules.planner;

import java.time.LocalDate;
import java.util.Comparator;

public class LocalDateComparator implements Comparator<LocalDate> {
    @Override
    public int compare(LocalDate o1, LocalDate o2) {
        if (o1.getMonthValue() == o2.getMonthValue()) {
            return o1.getDayOfMonth() - o2.getDayOfMonth();
        } else {
            return o1.getMonthValue() - o2.getMonthValue();
        }
    }
}
