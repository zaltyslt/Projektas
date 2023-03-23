package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.schedules.ScheduleRepository;

import java.time.LocalDate;
import java.util.Set;

public class SetupWorkDayConflicts {
    public static WorkDay SetupWorkDay(Long scheduleID, WorkDay workDay, ScheduleRepository scheduleRepository) {
        //Assigned so workDay.getDate() method wouldn't be called unnecessarily
        LocalDate workDayDate = workDay.getDate();

        scheduleRepository.findAll().forEach(schedule -> {
            Set<WorkDay> workingDays = schedule.getWorkingDays();
            for (WorkDay loopingWorkDay : workingDays) {
                if (CheckIfWorkdaysIntertwine(workDay, workDayDate, loopingWorkDay)) {
                    //Checks whether classrooms are the ones having conflict, sending values to original WorkDay
                    if (workDay.getClassroom().equals(loopingWorkDay.getClassroom())) {
                        workDay.setHasClassroomConflict(true);
                        workDay.addClassroomConflict(schedule.getId(), workDay.getClassroom().getClassroomName());
                    }
                    //Checks whether teachers are the ones having conflict, sending values to original WorkDay
                    if (workDay.getTeacher().equals(loopingWorkDay.getTeacher())) {
                        workDay.setHasTeacherConflict(true);
                        workDay.addTeacherConflict(schedule.getId(), workDay.getTeacher().getfName() + " " + workDay.getTeacher().getlName());
                    }
                }
            }
        });
        return workDay;
    }

    //SORTING NEEDED IN A CASE OF MORE EFFICIENT LOOPS
    //schedule.getWorkingDays().stream().sorted(new WorkDayDtoComparator());
    //PROBABLY?


    private static boolean CheckIfWorkdaysIntertwine (WorkDay currentWorkDay, LocalDate currentDate, WorkDay loopingWorkDay) {
        //Checks whether WorkDays are on the same date
        if (currentDate.isEqual(loopingWorkDay.getDate())) {
            //Returns true if a lesson happens at the same time
            return currentWorkDay.getLessonStartIntEnum() < loopingWorkDay.getLessonEndIntEnum() || currentWorkDay.getLessonEndIntEnum() > loopingWorkDay.getLessonStartIntEnum();
        }
        return false;
    }
    
    
}


