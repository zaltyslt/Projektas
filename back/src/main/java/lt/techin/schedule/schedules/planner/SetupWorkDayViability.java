package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.schedules.holidays.Holiday;
import lt.techin.schedule.schedules.holidays.LithuanianHolidaySetup;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetupWorkDayViability {
    public static WorkDay setupWorkDay (Long scheduleID, WorkDay workDay, ScheduleRepository scheduleRepository,
                                        WorkDayRepository workDayRepository, Schedule primarySchedule) {
        //Assigned so workDay.getDate() method wouldn't be called unnecessarily
        LocalDate workDayDate = workDay.getDate();

        //This is used to avoid ConcurrentModificationException
        Set<Schedule> schedulesWithConflicts = new HashSet<>();

        scheduleRepository.findAll().forEach(schedule -> {
            if (!schedule.getId().equals(scheduleID)) {
                Set<WorkDay> workingDays = schedule.getWorkingDays();
                for (WorkDay loopingWorkDay : workingDays) {
                    if (checkIfWorkdaysIntertwine(workDay, workDayDate, loopingWorkDay)) {
                        //Checks whether classrooms are the ones having conflict, sending values to original WorkDay
                        if (workDay.getClassroom() != null && workDay.getClassroom().equals(loopingWorkDay.getClassroom())) {
                            //Setting current WorkDay
                            workDay.setHasClassroomConflict(true);
                            workDay.addClassroomConflict(schedule.getId(), workDay.getClassroom().getClassroomName());
                            //Setting WorkDay which has conflicts
                            loopingWorkDay.setHasClassroomConflict(true);
                            loopingWorkDay.addClassroomConflict(scheduleID, workDay.getClassroom().getClassroomName());
                            //Need to rewrite WorkDay which has conflicts with current WorkDay
                            workDayRepository.save(loopingWorkDay);

                            schedulesWithConflicts.addAll(List.of(schedule, primarySchedule));
                        }
                        //Checks whether teachers are the ones having conflict, sending values to original WorkDay
                        if (workDay.getTeacher() != null && workDay.getTeacher().equals(loopingWorkDay.getTeacher())) {
                            //Setting current WorkDay
                            workDay.setHasTeacherConflict(true);
                            workDay.addTeacherConflict(schedule.getId(), workDay.getTeacher().getfName() + " " + workDay.getTeacher().getlName());
                            //Setting WorkDay which has conflicts
                            loopingWorkDay.setHasTeacherConflict(true);
                            loopingWorkDay.addTeacherConflict(scheduleID, workDay.getTeacher().getfName() + " " + workDay.getTeacher().getlName());
                            //Need to rewrite WorkDay which has conflicts with current WorkDay
                            workDayRepository.save(loopingWorkDay);

                            schedulesWithConflicts.addAll(List.of(schedule, primarySchedule));
                        }
                    }
                }
            }
        });
        //This is needed to avoid ConcurrentModificationException
        for (Schedule schedule : schedulesWithConflicts) {
            schedule.setHasConflicts(true);
            scheduleRepository.save(schedule);
        }
        return workDay;
    }

    public static boolean checkIfLocalDateIsWorkable (LocalDate localDate, Set<Holiday> holidays, Set<WorkDay> workDays) {
        //Returns false if a localDate checked is a weekend
        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }
        //Checks whether it's a Lithuanian holiday date
        if (LithuanianHolidaySetup.isItNotAnLithuanianHolidayDate(localDate)) {
            return false;
        }
        //Checks whether localDate passed is a holiday, returns false if it is
        if (holidays.stream().anyMatch(h -> (h.getDate().equals(localDate)))) {
            return false;
        }
        //Checks whether localDate passed is already tagged as a workDay
        if (!workDays.isEmpty()) {
            return workDays.stream().noneMatch(h -> (h.getDate().equals(localDate)));
        }
        return true;
    }


    public static boolean checkIfWorkdaysIntertwine (WorkDay currentWorkDay, LocalDate currentDate, WorkDay loopingWorkDay) {
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


