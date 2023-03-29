package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import org.apache.poi.sl.draw.geom.GuideIf;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkDayConflictSolver {

    public static void solveTeacherConflicts (WorkDay workdayBeingChanged, ScheduleRepository scheduleRepository,
                                              WorkDayRepository workDayRepository, boolean shouldLookForNewConflicts) {
        Long originScheduleId = workdayBeingChanged.getSchedule().getId();
        Map<Long, String> teacherConflicts = workdayBeingChanged.getScheduleIdWithTeacherNameConflict();

        for (Map.Entry<Long, String> longStringEntry : teacherConflicts.entrySet()) {
            Long iteratedScheduleId = longStringEntry.getKey();
            Optional<Schedule> foundSchedule = scheduleRepository.findById(iteratedScheduleId);

            //Finds a workday by a conflicting schedule id - each schedule workdays has unique LocalDate
            if (foundSchedule.isPresent()) {
                Optional<WorkDay> foundWorkDay = foundSchedule.get().getWorkingDays().stream().filter(workDay ->
                        workDay.getDate().equals(workdayBeingChanged.getDate())).findAny();
                if (foundWorkDay.isPresent()) {
                    //Removing existing conflicts which were present with old teacher name, and are redundant with a new one
                    if (!foundWorkDay.get().getScheduleIdWithTeacherNameConflict().isEmpty()) {
                        foundWorkDay.get().removeTeacherConflictFromMap(originScheduleId);
                    }
                    if (!workdayBeingChanged.getScheduleIdWithTeacherNameConflict().isEmpty()) {
                        workdayBeingChanged.removeTeacherConflictFromMap(iteratedScheduleId);
                    }

                    //Sets booleans
                    if (foundWorkDay.get().getScheduleIdWithTeacherNameConflict().isEmpty()) {
                        foundWorkDay.get().setHasTeacherConflict(false);
                    }
                    if (workdayBeingChanged.getScheduleIdWithTeacherNameConflict().isEmpty()) {
                        workdayBeingChanged.setHasTeacherConflict(false);
                    }
                    //Checks whether all conflicts have been resolved
                    checkIfAllConflictsAreResolved(originScheduleId, scheduleRepository);
                    checkIfAllConflictsAreResolved(foundSchedule.get().getId(), scheduleRepository);

                    workDayRepository.saveAll(List.of(foundWorkDay.get(), workdayBeingChanged));

                    if (shouldLookForNewConflicts) {
                        //Solves new conflicts
                        setupNewTeacherConflicts(workdayBeingChanged, workDayRepository, originScheduleId);
                    }
                }
            }
        }
    }

    public static void solveClassroomConflicts (WorkDay workdayBeingChanged, ScheduleRepository scheduleRepository,
                                                WorkDayRepository workDayRepository, boolean shouldLookForConflicts) {
        Long originScheduleId = workdayBeingChanged.getSchedule().getId();
        Map<Long, String> classroomConflicts = workdayBeingChanged.getScheduleIdWithClassroomNameConflict();

        for (Map.Entry<Long, String> longStringEntry : classroomConflicts.entrySet()) {
            Long iteratedScheduleId = longStringEntry.getKey();
            Optional<Schedule> foundSchedule = scheduleRepository.findById(iteratedScheduleId);

            //Finds a workday by a conflicting schedule id - each schedule workdays has unique LocalDate
            if (foundSchedule.isPresent()) {
                Optional<WorkDay> foundWorkDay = foundSchedule.get().getWorkingDays().stream().filter(workDay ->
                        workDay.getDate().equals(workdayBeingChanged.getDate())).findAny();
                if (foundWorkDay.isPresent()) {
                    //Removing existing conflicts which were present with old teacher name, and are redundant with a new one
                    if (!foundWorkDay.get().getScheduleIdWithClassroomNameConflict().isEmpty()) {
                        foundWorkDay.get().removeClassroomConflictFromMap(originScheduleId);
                    }
                    if (!workdayBeingChanged.getScheduleIdWithClassroomNameConflict().isEmpty()) {
                        workdayBeingChanged.removeClassroomConflictFromMap(iteratedScheduleId);
                    }

                    //Sets booleans
                    if (foundWorkDay.get().getScheduleIdWithClassroomNameConflict().isEmpty()) {
                        foundWorkDay.get().setHasClassroomConflict(false);
                    }
                    if (workdayBeingChanged.getScheduleIdWithClassroomNameConflict().isEmpty()) {
                        workdayBeingChanged.setHasClassroomConflict(false);
                    }
                    //Checks whether all conflicts have been resolved
                    checkIfAllConflictsAreResolved(originScheduleId, scheduleRepository);
                    checkIfAllConflictsAreResolved(foundSchedule.get().getId(), scheduleRepository);

                    workDayRepository.saveAll(List.of(foundWorkDay.get(), workdayBeingChanged));

                    if (shouldLookForConflicts) {
                        //Solves new conflicts
                        setupNewClassroomConflicts(workdayBeingChanged, workDayRepository, originScheduleId);
                    }
                }
            }
        }
    }

    public static void setupNewTeacherConflicts (WorkDay changedWorkDay, WorkDayRepository workDayRepository, long originScheduleId) {
        //Finds all WorkDays which happen simultaneously (date and lesson times) and has the same teacher
        Set<WorkDay> foundedNewConflictingWorkDays = workDayRepository.findAll().stream().filter(workDay ->
                        SetupWorkDayViability.checkIfWorkdaysIntertwine(workDay, changedWorkDay.getDate(), changedWorkDay) &&
                                !workDay.getId().equals(changedWorkDay.getId()) &&
                                workDay.getTeacher() != null &&
                                workDay.getTeacher().equals(changedWorkDay.getTeacher()))
                .collect(Collectors.toSet());
        //Loops through conflicting days
        for (WorkDay conflictingWorkDay : foundedNewConflictingWorkDays) {
            //Sets booleans for conflicts
            if (!conflictingWorkDay.isHasTeacherConflict())
                conflictingWorkDay.setHasTeacherConflict(true);
            if (!changedWorkDay.isHasTeacherConflict())
                changedWorkDay.setHasTeacherConflict(true);

            conflictingWorkDay.addTeacherConflict(originScheduleId, changedWorkDay.getTeacher().getfName() + " " + changedWorkDay.getTeacher().getlName());
            changedWorkDay.addTeacherConflict(conflictingWorkDay.getSchedule().getId(), changedWorkDay.getTeacher().getfName() + " " + changedWorkDay.getTeacher().getlName());

            //Saves changes
            workDayRepository.save(conflictingWorkDay);
        }
    }

    public static void setupNewClassroomConflicts(WorkDay changedWorkDay, WorkDayRepository workDayRepository, long originScheduleId) {
        //Finds all WorkDays which happen simultaneously (date and lesson times) and has the same classroom
        Set<WorkDay> foundedNewConflictingWorkDays = workDayRepository.findAll().stream().filter(workDay ->
                        SetupWorkDayViability.checkIfWorkdaysIntertwine(workDay, changedWorkDay.getDate(), changedWorkDay) &&
                                !workDay.getId().equals(changedWorkDay.getId()) &&
                                workDay.getClassroom() != null &&
                                workDay.getClassroom().equals(changedWorkDay.getClassroom()))
                .collect(Collectors.toSet());
        //Loops through conflicting days
        for (WorkDay conflictingWorkDay : foundedNewConflictingWorkDays) {
            //Sets booleans for conflicts, if they had none
            if (!conflictingWorkDay.isHasClassroomConflict())
                conflictingWorkDay.setHasClassroomConflict(true);
            if (!changedWorkDay.isHasClassroomConflict())
                changedWorkDay.setHasClassroomConflict(true);

            conflictingWorkDay.addClassroomConflict(originScheduleId, changedWorkDay.getClassroom().getClassroomName());
            changedWorkDay.addClassroomConflict(conflictingWorkDay.getSchedule().getId(), changedWorkDay.getClassroom().getClassroomName());

            //Saves changes
            workDayRepository.save(conflictingWorkDay);
        }
    }

    public static void checkIfAllConflictsAreResolved (Long scheduleToCheck, ScheduleRepository scheduleRepository) {
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleToCheck);
        if (schedule.isPresent()) {
            boolean conflictsFound = schedule.get().getWorkingDays().stream().anyMatch(workDay ->
                    workDay.isHasTeacherConflict() || workDay.isHasClassroomConflict());
            schedule.get().setHasConflicts(conflictsFound);
            scheduleRepository.save(schedule.get());
        }
    }
}
