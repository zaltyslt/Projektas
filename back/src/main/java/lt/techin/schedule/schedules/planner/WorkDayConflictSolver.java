package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.hibernate.jdbc.Work;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;

import java.util.*;
import java.util.stream.Collectors;

public class WorkDayConflictSolver {

    public static void solveTeacherConflicts (WorkDay workdayBeingChanged, ScheduleRepository scheduleRepository,
                                              WorkDayRepository workDayRepository, boolean shouldLookForNewConflicts) {
        Long originScheduleId = workdayBeingChanged.getSchedule().getId();
        //New object created for concurrent modification exception
        Map<Long, String> teacherConflicts = new HashMap<>(workdayBeingChanged.getScheduleIdWithTeacherNameConflict());

        if (teacherConflicts.isEmpty()) {
            setupNewTeacherConflicts(workdayBeingChanged, workDayRepository, scheduleRepository, originScheduleId);
            return;
        }

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
                        setupNewTeacherConflicts(workdayBeingChanged, workDayRepository, scheduleRepository, originScheduleId);
                    }
                }
            }
        }
    }

    public static void solveClassroomConflicts (WorkDay workdayBeingChanged, ScheduleRepository scheduleRepository,
                                                WorkDayRepository workDayRepository, boolean shouldLookForConflicts) {
        Long originScheduleId = workdayBeingChanged.getSchedule().getId();
        //New object created for concurrent modification exception
        Map<Long, String> classroomConflicts = new HashMap<>(workdayBeingChanged.getScheduleIdWithClassroomNameConflict());

        //If this is the first conflict found for this classroom
        if (classroomConflicts.isEmpty()) {
            setupNewClassroomConflicts(workdayBeingChanged, workDayRepository, scheduleRepository, originScheduleId);
            return;
        }

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
                        setupNewClassroomConflicts(workdayBeingChanged, workDayRepository, scheduleRepository, originScheduleId);
                    }
                }
            }
        }
    }

    public static void setupNewTeacherConflicts (WorkDay changedWorkDay, WorkDayRepository workDayRepository, ScheduleRepository scheduleRepository, long originScheduleId) {
        //Finds all WorkDays which happen simultaneously (date and lesson times) and has the same teacher
        Set<WorkDay> foundedNewConflictingWorkDays = workDayRepository.findAll().stream().filter(workDay ->
                        SetupWorkDayViability.checkIfWorkdaysIntertwine(workDay, workDay.getDate(), changedWorkDay) &&
                                !workDay.getId().equals(changedWorkDay.getId()) &&
                                workDay.getTeacher() != null &&
                                workDay.getTeacher().getfName().equals(changedWorkDay.getTeacher().getfName()) &&
                                workDay.getTeacher().getlName().equals(changedWorkDay.getTeacher().getlName())
                        )
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

            Optional<Schedule> originSchedule = scheduleRepository.findById(originScheduleId);
            Optional<Schedule> conflictingSchedule = scheduleRepository.findById(conflictingWorkDay.getSchedule().getId());

            if (originSchedule.isPresent() && conflictingSchedule.isPresent()) {
                originSchedule.get().setHasConflicts(true);
                conflictingSchedule.get().setHasConflicts(true);

                //Saves changes
                scheduleRepository.saveAll(List.of(originSchedule.get(), conflictingSchedule.get()));
            }
            //Saves changes
            workDayRepository.save(conflictingWorkDay);
            scheduleRepository.saveAll(List.of(originSchedule.get(), conflictingSchedule.get()));
        }
    }

    public static void setupNewClassroomConflicts(WorkDay changedWorkDay, WorkDayRepository workDayRepository, ScheduleRepository scheduleRepository, long originScheduleId) {
        //Finds all WorkDays which happen simultaneously (date and lesson times) and has the same classroom
        Set<WorkDay> foundedNewConflictingWorkDays = workDayRepository.findAll().stream().filter(workDay ->
                        SetupWorkDayViability.checkIfWorkdaysIntertwine(workDay, workDay.getDate(), changedWorkDay) &&
                                !workDay.getId().equals(changedWorkDay.getId()) &&
                                workDay.getClassroom() != null &&
                                workDay.getClassroom().getClassroomName().equals(changedWorkDay.getClassroom().getClassroomName()))
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

            Optional<Schedule> originSchedule = scheduleRepository.findById(originScheduleId);
            Optional<Schedule> conflictingSchedule = scheduleRepository.findById(conflictingWorkDay.getSchedule().getId());

            if (originSchedule.isPresent() && conflictingSchedule.isPresent()) {
                originSchedule.get().setHasConflicts(true);
                conflictingSchedule.get().setHasConflicts(true);

                //Saves changes
                scheduleRepository.saveAll(List.of(originSchedule.get(), conflictingSchedule.get()));
            }

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
            //Saves changes
            scheduleRepository.save(schedule.get());
        }
    }

    public static void checkIfAllConflictsAreResolved (Schedule schedule, ScheduleRepository scheduleRepository) {
        boolean conflictsFound = schedule.getWorkingDays().stream().anyMatch(workDay ->
                workDay.isHasTeacherConflict() || workDay.isHasClassroomConflict());
        schedule.setHasConflicts(conflictsFound);
        //Saves changes
        scheduleRepository.save(schedule);
    }

    public static void fixConflictsOnDeletion(WorkDay existingWorkDay, ScheduleRepository scheduleRepository, WorkDayRepository workDayRepository,
                                              long currentScheduleId) {
        if (existingWorkDay.isHasTeacherConflict()) {
            Map<Long, String> conflicts = existingWorkDay.getScheduleIdWithTeacherNameConflict();
            for (Map.Entry<Long, String> longStringEntry : conflicts.entrySet()) {
                Optional<Schedule> conflictingSchedule = scheduleRepository.findById(longStringEntry.getKey());
                if (conflictingSchedule.isPresent()) {
                    Optional<WorkDay> conflictingWorkDayOpt = conflictingSchedule.get().getWorkingDays().stream().filter(workDay ->
                            workDay.getDate().equals(existingWorkDay.getDate())).findFirst();
                    if (conflictingWorkDayOpt.isPresent()) {
                        WorkDay conflictingWorkDay = conflictingWorkDayOpt.get();
                        try {
                            conflictingWorkDay.removeTeacherConflictFromMap(currentScheduleId);
                        } catch (Exception ignored) {}
                        if (conflictingWorkDay.getScheduleIdWithTeacherNameConflict().isEmpty()) {
                            conflictingWorkDay.setHasTeacherConflict(false);
                        }
                        //Changes are saved in method
                        checkIfAllConflictsAreResolved(conflictingSchedule.get(), scheduleRepository);

                        //Saves changes
                        workDayRepository.save(conflictingWorkDay);
                    }
                }
            }
        }
        if (existingWorkDay.isHasClassroomConflict()) {
            Map<Long, String> conflicts = existingWorkDay.getScheduleIdWithClassroomNameConflict();
            for (Map.Entry<Long, String> longStringEntry : conflicts.entrySet()) {
                Optional<Schedule> conflictingSchedule = scheduleRepository.findById(longStringEntry.getKey());
                if (conflictingSchedule.isPresent()) {
                    Optional<WorkDay> conflictingWorkDayOpt = conflictingSchedule.get().getWorkingDays().stream().filter(workDay ->
                            workDay.getDate().equals(existingWorkDay.getDate())).findFirst();
                    if (conflictingWorkDayOpt.isPresent()) {
                        WorkDay conflictingWorkDay = conflictingWorkDayOpt.get();
                        try {
                            conflictingWorkDay.removeClassroomConflictFromMap(currentScheduleId);
                        }
                        catch (Exception ignored) {}
                        if (conflictingWorkDay.getScheduleIdWithClassroomNameConflict().isEmpty()) {
                            conflictingWorkDay.setHasClassroomConflict(false);
                        }
                        //Changes are saved in method
                        checkIfAllConflictsAreResolved(conflictingSchedule.get(), scheduleRepository);

                        //Saves changes
                        workDayRepository.save(conflictingWorkDay);
                    }
                }
            }
        }
    }
}
