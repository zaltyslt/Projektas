package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.teachers.Teacher;

import java.util.Map;
import java.util.Optional;

public class WorkDayConflictSolver {

    public static void solveTeacherConflicts (WorkDay workdayBeingChanged, Teacher teacherToChangeTo, ScheduleRepository scheduleRepository, WorkDayRepository workDayRepository) {
        workdayBeingChanged.setTeacher(teacherToChangeTo);
        Map<Long, String> teacherConflicts = workdayBeingChanged.getScheduleIdWithTeacherNameConflict();
        for (Map.Entry<Long, String> longStringEntry : teacherConflicts.entrySet()) {
            Optional<Schedule> foundSchedule = scheduleRepository.findById(longStringEntry.getKey());
            if (foundSchedule.isPresent()) {
                Optional<WorkDay> foundWorkDay = foundSchedule.get().getWorkingDays().stream().filter(workDay -> workDay.getDate().equals(workdayBeingChanged.getDate())).findAny();
                if (foundWorkDay.isPresent()) {

                }
            }
        }

    }

//    public static void solveClassroomConflicts (WorkDay)
}
