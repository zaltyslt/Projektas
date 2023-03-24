package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.schedules.ScheduleRepository;

import java.time.LocalDate;
import java.util.Set;

public class SetupWorkDayConflicts {
    public static WorkDay SetupWorkDay(Long scheduleID, WorkDay workDay, ScheduleRepository scheduleRepository, WorkDayRepository workDayRepository) {
        //Assigned so workDay.getDate() method wouldn't be called unnecessarily
        LocalDate workDayDate = workDay.getDate();

        int something = scheduleRepository.findAll().size();
        scheduleRepository.findAll().forEach(schedule -> {
            if (!schedule.getId().equals(scheduleID)) {
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


