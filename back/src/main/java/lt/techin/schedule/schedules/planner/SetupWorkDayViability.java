package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.schedules.holidays.Holiday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public class SetupWorkDayViability {
    public static WorkDay SetupWorkDay(Long scheduleID, WorkDay workDay, ScheduleRepository scheduleRepository, WorkDayRepository workDayRepository, Schedule primarySchedule) {
        //Assigned so workDay.getDate() method wouldn't be called unnecessarily
        LocalDate workDayDate = workDay.getDate();

        scheduleRepository.findAll().forEach(schedule -> {
            if (!schedule.getId().equals(scheduleID)) {
                Set<WorkDay> workingDays = schedule.getWorkingDays();
                for (WorkDay loopingWorkDay : workingDays) {
                    if (CheckIfWorkdaysIntertwine(workDay, workDayDate, loopingWorkDay)) {
                        //Checks whether classrooms are the ones having conflict, sending values to original WorkDay
                        if (workDay.getClassroom().equals(loopingWorkDay.getClassroom())) {
                            //Setting current WorkDay
                            workDay.setHasClassroomConflict(true);
                            workDay.addClassroomConflict(schedule.getId(), workDay.getClassroom().getClassroomName());
                            //Setting WorkDay which has conflicts
                            loopingWorkDay.setHasTeacherConflict(true);
                            loopingWorkDay.addClassroomConflict(scheduleID, workDay.getClassroom().getClassroomName());
                            //Need to rewrite WorkDay which has conflicts with current WorkDay
                            workDayRepository.save(loopingWorkDay);
                            //Saves schedule status as the one having at least one conflict
                            schedule.setHasConflicts(true);
                            scheduleRepository.save(schedule);
                            //Sets the same to a schedule being checked
                            primarySchedule.setHasConflicts(true);
                            scheduleRepository.save(primarySchedule);
                        }
                        //Checks whether teachers are the ones having conflict, sending values to original WorkDay
                        if (workDay.getTeacher().equals(loopingWorkDay.getTeacher())) {
                            //Setting current WorkDay
                            workDay.setHasTeacherConflict(true);
                            workDay.addTeacherConflict(schedule.getId(), workDay.getTeacher().getfName() + " " + workDay.getTeacher().getlName());
                            //Setting WorkDay which has conflicts
                            loopingWorkDay.setHasTeacherConflict(true);
                            loopingWorkDay.addTeacherConflict(scheduleID, workDay.getTeacher().getfName() + " " + workDay.getTeacher().getlName());
                            //Need to rewrite WorkDay which has conflicts with current WorkDay
                            workDayRepository.save(loopingWorkDay);
                            //Saves schedule status as the one having at least one conflict
                            schedule.setHasConflicts(true);
                            scheduleRepository.save(schedule);
                            //Sets the same to a schedule being checked
                            primarySchedule.setHasConflicts(true);
                            scheduleRepository.save(primarySchedule);
                        }
                    }
                }
            }
        });
        return workDay;
    }

    public static boolean CheckIfLocalDateIsWorkable (LocalDate localDate, Set<Holiday> holidays) {
        //Returns false if a localDate checked is a weekend
        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }
        //Checks whether localDate passed is a holiday, returns false if it is
        return holidays.stream().noneMatch(h -> (h.getDateFrom().isBefore(localDate) && h.getDateUntil().isAfter(localDate)
                && (h.getDateFrom().isEqual(localDate) || h.getDateFrom().isEqual(localDate)))
        );
    }


    private static boolean CheckIfWorkdaysIntertwine (WorkDay currentWorkDay, LocalDate currentDate, WorkDay loopingWorkDay) {
        //Checks whether WorkDays are on the same date
        if (currentDate.isEqual(loopingWorkDay.getDate())) {
            //Returns true if a lesson happens at the same time
            int[] currentLessonInt = new int[]{currentWorkDay.getLessonStartIntEnum(), currentWorkDay.getLessonEndIntEnum()};
            int[] toCheckLessonInt =  new int[]{loopingWorkDay.getLessonStartIntEnum(), loopingWorkDay.getLessonEndIntEnum()};

            //Checks if both lessons start or end at the same time
            if (currentLessonInt[0] == toCheckLessonInt[0] || currentLessonInt[1] == toCheckLessonInt[1]) {
                return true;
            }
            //Checks if a current lesson has a lesson to check inside
            if (currentLessonInt[0] < toCheckLessonInt[0] && currentLessonInt[1] > toCheckLessonInt[1]) {
                return true;
            }
            //Checks if a lesson to check has a current lesson inside
            return toCheckLessonInt[0] < currentLessonInt[0] && toCheckLessonInt[1] > currentLessonInt[1];
        }
        return false;
    }
}


