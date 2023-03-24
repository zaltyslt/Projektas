package lt.techin.schedule.schedules.planner;

import java.util.Comparator;

public class WorkDayDtoComparator implements Comparator<WorkDay> {

    @Override
    public int compare(WorkDay o1, WorkDay o2) {
        int date = o1.getDate().compareTo(o2.getDate());
        if (date == 0) {
            if (o1.getLessonStartIntEnum() == (o2.getLessonStartIntEnum())) {
                return o1.getLessonStartIntEnum() - o2.getLessonStartIntEnum();
            }
            else{
                return o1.getLessonEndIntEnum() - o2.getLessonEndIntEnum();
            }
        }
        return date;
    }
}
