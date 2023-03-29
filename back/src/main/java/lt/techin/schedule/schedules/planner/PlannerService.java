package lt.techin.schedule.schedules.planner;

import jakarta.transaction.Transactional;
import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.classrooms.ClassroomRepository;
import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.programs.Program;
import lt.techin.schedule.programs.subjectsHours.SubjectHours;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.schedules.holidays.Holiday;
import lt.techin.schedule.shift.LessonTime;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.teachers.Teacher;
import lt.techin.schedule.teachers.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static lt.techin.schedule.classrooms.ClassroomMapper.toClassroomFromSmallDto;
import static lt.techin.schedule.teachers.TeacherMapper.toTeacherFromEntityDto;

@Service
public class PlannerService {

    private final ScheduleRepository scheduleRepository;

    private final SubjectRepository subjectRepository;

    private final TeacherRepository teacherRepository;

    private final WorkDayRepository workDayRepository;
    private final ClassroomRepository classroomRepository;

    private final int INTERVAL_CONSTANT = 1;

    public PlannerService(ScheduleRepository scheduleRepository, SubjectRepository subjectRepository, TeacherRepository teacherRepository, WorkDayRepository workDayRepository,
                          ClassroomRepository classroomRepository) {
        this.scheduleRepository = scheduleRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.workDayRepository = workDayRepository;
        this.classroomRepository = classroomRepository;
    }

    public String getLessonStartString (PlannerDto plannerDto) {
        return LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum()).getLessonStart();
    }

    public String getLessonEndString (PlannerDto plannerDto) {
        return LessonTime.getLessonTimeByInt(plannerDto.getEndIntEnum()).getLessonEnd();
    }

    /*
    Checks whether it's possible to put lessons plan into schedule without exceeding schedule date limits
    */
    public String isValidDates(LocalDate startDatePlanner, LocalDate endDatePlanner, LocalDate startDateSchedule, LocalDate endDateSchedule) {
        if (startDatePlanner.isBefore(startDateSchedule) || startDatePlanner.isAfter(endDateSchedule)) {
            return "Pateikta plano data neįeina į tvarkaraščio laikotarpį.";
        }
        if (endDatePlanner.isAfter(endDateSchedule)) {
            return "Viršijamas nustatytas tvarkaraščio laikotarpis. Paskutinė plano diena yra " + endDatePlanner +
                    ", vėliausia leidžiama tvarkaraščio diena yra " + endDateSchedule + ".";
        }
        return "";
    }

    public int setupAndValidateUnassignedHoursMap (Schedule currentSchedule, Integer unassignedHours, int plannerAssignedHours, Long subjectId, SubjectHours subjectHour) {
        //In a case where Map already has a value of that particular subjectId
        if (unassignedHours != null) {
            if (unassignedHours == 0) {
                throw new ValidationException("Dalykas pasirinktoje programoje nebeturi nepanaudotų valandų", "Program", "Unassigned hours value is 0", currentSchedule.getSubjectIdWithUnassignedTime().toString());
            }
            //Checks so that passed value wouldn't exceed maximum hours subject has
            if (plannerAssignedHours >= unassignedHours) {
                currentSchedule.replaceUnassignedTime(subjectId, 0);
                return unassignedHours;
            }
            //Places new assigned hours amount
            else {
                currentSchedule.replaceUnassignedTime(subjectId, unassignedHours - plannerAssignedHours);
                return plannerAssignedHours;
            }
        }
        //In a case where Map doesn't have a value of that particular subjectId
        else {
            //Checks so that passed value wouldn't exceed maximum hours subject has
            if (plannerAssignedHours >= subjectHour.getHours()) {
                currentSchedule.addUnassignedTimeWithSubjectId(subjectId, 0);
                return subjectHour.getHours();
            }
            //Places new assigned hours amount
            else {
                currentSchedule.addUnassignedTimeWithSubjectId(subjectId, subjectHour.getHours() - plannerAssignedHours);
                return plannerAssignedHours;
            }
        }
    }


    public String addSubjectPlanToSchedule(Long scheduleId, Long subjectId, PlannerDto plannerDto) {
        Schedule existingSchedule = scheduleRepository.findById(scheduleId).orElseThrow(()->
                new ValidationException("Tvarkaraštis neegzistuoja", "Schedule", "Does not exist", scheduleId.toString()));
        Subject existingSubject = subjectRepository.findById(subjectId).orElseThrow(()->
                new ValidationException("Pasirinktas dalykas neegzistuoja", "Subject", "Does no exist", subjectId.toString()));
        Teacher existingTeacher;
        Classroom existingClassroom;

        if (plannerDto.getTeacher() == null) {
            existingTeacher = null;
        } else {
           existingTeacher = teacherRepository.findById(plannerDto.getTeacher().getId()).orElseThrow(() ->
                   new ValidationException("Pasirinktas mokytojas neegzistuoja", "Teacher", "Does not exist", plannerDto.getTeacher().getId().toString()));
        }

        if(plannerDto.getClassroom() == null) {
            existingClassroom = null;
        } else {
            existingClassroom = classroomRepository.findById(plannerDto.getClassroom().getId()).orElseThrow(()->
                    new ValidationException("Pasirinkta klasė neegzistuoja", "Classroom", "Does not exist", plannerDto.getClassroom().getId().toString()));
        }

        Program program = existingSchedule.getGroups().getProgram();
        SubjectHours subjectHour = program.getSubjectHoursList().stream().filter(sh -> sh.getSubject().equals(subjectId)).findAny().orElseThrow(() ->
                new ValidationException("Pasirinktas dalykas neegzistuoja programoje", "Program", "Does not exist", program.toString()));

        Integer unassignedHours = existingSchedule.getUnassignedTimeWithSubjectId(subjectId);
        int plannerAssignedHours = plannerDto.getAssignedHours();

        //Setting values of unassigned hours in scheduler entity
        int hours = setupAndValidateUnassignedHoursMap(existingSchedule, unassignedHours, plannerAssignedHours, subjectId, subjectHour);

        int interval = plannerDto.getEndIntEnum() - plannerDto.getStartIntEnum() + 1;
        int workDaysRequired;
        int lastDayHours;

        if (hours % interval != 0) {
            workDaysRequired = hours / interval + 1;
            lastDayHours = hours % interval;
        }
        else {
            workDaysRequired = hours / interval;
            lastDayHours = 0;
        }

        List<LocalDate> workableDates = new ArrayList<>();
        LocalDate date = plannerDto.getDateFrom();

        //In case of the bug
        int counter = 0;
        //Finding every possible workday
        for (int i = 0; i < workDaysRequired; i++) {
            //If the date found is not workable, iterating it through until viable date is found
            while (!SetupWorkDayViability.checkIfLocalDateIsWorkable(date, existingSchedule.getHolidays(), existingSchedule.getWorkingDays())) {
                date = date.plusDays(1);
                /*
                If it loops for 100 times, something went wrong (most likely)
                Yes, this is a stupid check, bite me
                */
                counter++;
                if (counter == 100) {
                    return "Plano sukurti nepavyko, nerasta galimų darbo dienų.";
                }
            }
            workableDates.add(date);
            date = date.plusDays(1);
        }

        //Getting last lesson date of the planner
        LocalDate lastDate = workableDates.get(workableDates.size() - 1);

        //Validating whether planned lessons are viable for this schedule
        String isDateValid = isValidDates(plannerDto.getDateFrom(), lastDate, existingSchedule.getDateFrom(), existingSchedule.getDateUntil());
        if (!isDateValid.isEmpty()) {
            return isDateValid;
        }

        Set<WorkDay> workDaySet = existingSchedule.getWorkingDays();
        //Iterating through workdays if all workdays has same amount of lesson hours
        if (lastDayHours == 0) {
            for (LocalDate workableDate : workableDates) {
                if (workDaySet.stream().noneMatch(wd -> wd.getDate().isEqual(workableDate))) {
                    WorkDay workDay = SetupWorkDayViability.setupWorkDay(scheduleId,
                        new WorkDay
                                (
                                    workableDate, existingSubject, existingTeacher, existingSchedule, existingClassroom, getLessonStartString(plannerDto),
                                    getLessonEndString(plannerDto),
                                    plannerDto.getStartIntEnum(), plannerDto.getEndIntEnum(),
                                    plannerDto.getOnline()
                                ),
                        scheduleRepository, workDayRepository, existingSchedule);
                    workDayRepository.save(workDay);
                    existingSchedule.addWorkDay(workDay);
                }
            }
        }
        //In a case where there is an uneven amount of hours and the last day has a different amount
        else {
            for (int i = 0; i < workableDates.size() - 1; i++) {
                LocalDate workableDate = workableDates.get(i);
                if (workDaySet.stream().noneMatch(wd -> wd.getDate().isEqual(workableDate))) {
                    WorkDay workDay = SetupWorkDayViability.setupWorkDay(scheduleId,
                        new WorkDay
                                (
                                    workableDate, existingSubject, existingTeacher, existingSchedule, existingClassroom, getLessonStartString(plannerDto),
                                    getLessonEndString(plannerDto),
                                    plannerDto.getStartIntEnum(), plannerDto.getEndIntEnum(),
                                    plannerDto.getOnline()
                                ),
                        scheduleRepository, workDayRepository, existingSchedule);
                    workDayRepository.save(workDay);
                    existingSchedule.addWorkDay(workDay);
                }
            }
            LocalDate lastWorkableDate = workableDates.get(workableDates.size() - 1);
            if (workDaySet.stream().noneMatch(wd -> wd.getDate().isEqual(lastWorkableDate))) {
                WorkDay workDay = SetupWorkDayViability.setupWorkDay(scheduleId,
                    new WorkDay
                            (
                                lastWorkableDate, existingSubject, existingTeacher, existingSchedule, existingClassroom, getLessonStartString(plannerDto),
                                    //Gets end of lesson by the amount of hours left
                                    LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum() + lastDayHours - 1).getLessonEnd(),
                                    plannerDto.getStartIntEnum(), plannerDto.getStartIntEnum() + lastDayHours - 1,
                                plannerDto.getOnline()
                            ),
                    scheduleRepository, workDayRepository, existingSchedule);
                workDayRepository.save(workDay);
                existingSchedule.addWorkDay(workDay);
            }
        }
        return "";
    }

    public List<WorkDay> getWorkDays(Long scheduleId) {
        return workDayRepository.findWorkDaysByScheduleId(scheduleId);
    }

    public List<Holiday> getHolidays(Long scheduleId) {
        Schedule foundSchedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new ValidationException("Tvarkaraštis nerastas", "Schedule", "Does not exist", scheduleId.toString()));
        return new ArrayList<>(foundSchedule.getHolidays());
    }
    public Optional<WorkDay> getWorkDay(Long workDayId) {
        return workDayRepository.findById(workDayId);
    }

    public WorkDay updateWorkDay(Long workDayId, WorkDayDto workDayDto) {
        WorkDay existingWorkDay = workDayRepository.findById(workDayId).orElseThrow(() ->
                new ValidationException("Nurodyta darbo diena neegzistuoja", "WorkDay", "Does not exist", workDayId.toString()));

        Teacher teacherToChangeTo = toTeacherFromEntityDto(workDayDto.getTeacher());

        //Teacher is changed, need to update conflicts if any are found
        if (teacherToChangeTo != null && !teacherToChangeTo.equals(existingWorkDay.getTeacher())) {
            //Sets teacher before conflicts logic
            existingWorkDay.setTeacher(teacherToChangeTo);
            WorkDayConflictSolver.solveTeacherConflicts(existingWorkDay, scheduleRepository, workDayRepository, true);
        }
        else if (teacherToChangeTo == null) {
            existingWorkDay.setTeacher(null);
            WorkDayConflictSolver.solveTeacherConflicts(existingWorkDay, scheduleRepository, workDayRepository, false);
        }

        Classroom classroomToChangeTo = toClassroomFromSmallDto(workDayDto.getClassroom());

        //Classroom is changed, need to update conflicts if any are found
        if (classroomToChangeTo != null && !classroomToChangeTo.equals(existingWorkDay.getClassroom())) {
            //Sets classroom before conflicts logic
            existingWorkDay.setClassroom(classroomToChangeTo);
            WorkDayConflictSolver.solveClassroomConflicts(existingWorkDay, scheduleRepository, workDayRepository, true);
        }
        else if (classroomToChangeTo == null) {
            existingWorkDay.setClassroom(null);
            WorkDayConflictSolver.solveClassroomConflicts(existingWorkDay, scheduleRepository, workDayRepository, false);
        }

        WorkDayConflictSolver.checkIfAllConflictsAreResolved(existingWorkDay.getSchedule().getId(), scheduleRepository);

        existingWorkDay.setOnline(workDayDto.getOnline());

        return workDayRepository.save(existingWorkDay);
    }

    @Transactional
    public boolean deleteWorkDay(Long workDayId, WorkDayDto workDayDto) {
        WorkDay existingWorkDay = workDayRepository.findById(workDayId).orElseThrow(() ->
                new ValidationException("Nurodyta darbo diena neegzistuoja", "WorkDay", "Does not exist", workDayId.toString()));
        Schedule schedule = scheduleRepository.findById(existingWorkDay.getSchedule().getId()).orElseThrow(() ->
                new ValidationException("Nurodytas tvarkaraštis neegzistuoja", "Schedule", "Does not exist", existingWorkDay.getSchedule().getId().toString()));
        Integer unassignedHours = schedule.getUnassignedTimeWithSubjectId(existingWorkDay.getSubject().getId());
        var start = workDayDto.getStartIntEnum();
        var end = workDayDto.getEndIntEnum();
        int interval = end - start + INTERVAL_CONSTANT;

        workDayRepository.deleteById(workDayId);
        schedule.replaceUnassignedTime(existingWorkDay.getSubject().getId(), unassignedHours + interval);
        scheduleRepository.save(schedule);
        return true;
    }

    @Transactional
    public boolean deleteLessonsBySubjectId(Long scheduleId, Long subjectId, int hours) {
        Schedule existingSchedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new ValidationException("Nurodytas tvarkaraštis neegzistuoja", "Schedule", "Does not exist", scheduleId.toString()));
        long deleted = workDayRepository.deleteBySubjectId(subjectId);

        if(deleted > 0 ){
            existingSchedule.replaceUnassignedTime(subjectId, hours);
            return true;
        } else {
            throw new ValidationException("Dalykas neturi suplanuotų valandų.", "Subject", "Subject does not have planned hours in schedule", subjectId.toString());
        }
    }
}
