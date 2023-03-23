package lt.techin.schedule.schedules.planner;

import java.util.Comparator;

public class WorkDayDtoComparator implements Comparator<WorkDay> {

    @Override
    public int compare(WorkDay o1, WorkDay o2) {
        int date = o1.getDate().compareTo(o2.getDate());
        if (date == 0) {
            return o1.getLessonStartFloat().compareTo(o2.getLessonStartFloat());
        }
        return date;
    }
}
