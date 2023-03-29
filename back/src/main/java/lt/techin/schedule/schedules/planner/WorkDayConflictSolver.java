package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.teachers.Teacher;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkDayConflictSolver {

    public static void solveTeacherConflicts (WorkDay workdayBeingChanged, Teacher teacherToChangeTo, ScheduleRepository scheduleRepository, WorkDayRepository workDayRepository) {
        workdayBeingChanged.setTeacher(teacherToChangeTo);
        Long originScheduleId = workdayBeingChanged.getSchedule().getId();

        Map<Long, String> teacherConflicts = workdayBeingChanged.getScheduleIdWithTeacherNameConflict();

        for (Map.Entry<Long, String> longStringEntry : teacherConflicts.entrySet()) {
            Long iteratedScheduleId = longStringEntry.getKey();
            Optional<Schedule> foundSchedule = scheduleRepository.findById(iteratedScheduleId);
            if (foundSchedule.isPresent()) {
                Optional<WorkDay> foundWorkDay = foundSchedule.get().getWorkingDays().stream().filter(workDay -> workDay.getDate().equals(workdayBeingChanged.getDate())).findAny();
                if (foundWorkDay.isPresent()) {
                    //Removing existing conflicts which were present with old teacher name, and are redundant with a new one
                    foundWorkDay.get().removeTeacherConflictFromMap(originScheduleId);
                    workdayBeingChanged.removeTeacherConflictFromMap(iteratedScheduleId);

                    //Solves new conflicts
                    setupNewTeacherConflicts(workdayBeingChanged, workDayRepository, originScheduleId);
                }
            }
        }

    }

//    public static void solveClassroomConflicts (WorkDay)

    private static void setupNewTeacherConflicts (WorkDay changedWorkDay, WorkDayRepository workDayRepository, long originScheduleId) {
        //Finds all WorkDays which happen simultaneously (date and lesson times) and has the same teacher
        Set<WorkDay> foundedNewConflictingWorkDays = workDayRepository.findAll().stream().filter(workDay -> SetupWorkDayViability.checkIfWorkdaysIntertwine(workDay, changedWorkDay.getDate(), changedWorkDay) &&
                workDay.getTeacher().equals(changedWorkDay.getTeacher())).collect(Collectors.toSet());
        for (WorkDay conflictingWorkDay : foundedNewConflictingWorkDays) {
               conflictingWorkDay.addTeacherConflict(originScheduleId, changedWorkDay.getTeacher().getfName() + " " + changedWorkDay.getTeacher().getlName());
               changedWorkDay.addTeacherConflict(conflictingWorkDay.getSchedule().getId(), changedWorkDay.getTeacher().getfName() + " " + changedWorkDay.getTeacher().getlName());

               //Saves changes
               workDayRepository.save(conflictingWorkDay);
        }
    }

    private static void setupNewClassroomConflicts(WorkDay changedWorkDay, WorkDayRepository workDayRepository, long originScheduleId) {
        //Finds all WorkDays which happen simultaneously (date and lesson times) and has the same classroom
        Set<WorkDay> foundedNewConflictingWorkDays = workDayRepository.findAll().stream().filter(workDay -> SetupWorkDayViability.checkIfWorkdaysIntertwine(workDay, changedWorkDay.getDate(), changedWorkDay) &&
                workDay.getClassroom().equals(changedWorkDay.getClassroom())).collect(Collectors.toSet());
        for (WorkDay conflictingWorkDay : foundedNewConflictingWorkDays) {
            conflictingWorkDay.addClassroomConflict(originScheduleId, changedWorkDay.getClassroom().getClassroomName());
            //Saves changes
            workDayRepository.save(conflictingWorkDay);
        }
    }
}
