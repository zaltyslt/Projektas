package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.classrooms.ClassroomRepository;
import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.programs.Program;
import lt.techin.schedule.programs.subjectsHours.SubjectHours;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.shift.LessonTime;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.teachers.Teacher;
import lt.techin.schedule.teachers.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlannerService {

    private final ScheduleRepository scheduleRepository;

    private final SubjectRepository subjectRepository;

    private final TeacherRepository teacherRepository;

    private final WorkDayRepository workDayRepository;
    private final ClassroomRepository classroomRepository;

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
            return "Pateikta plano data neįeina į tvarkaraščio datos rėžį.";
        }
        if (endDatePlanner.isAfter(endDateSchedule)) {
            return "Paskutinė suplanuota pamokų plano diena viršija tvarkaraščio trukmę. Paskutinė plano diena yra " + endDatePlanner +
                    ", vėliausia leidžiama tvarkaraščio diena yra " + endDateSchedule + ".";
        }
        return "";
    }

    public int SetupAndValidateUnassignedHoursMap(Schedule currentSchedule, Integer unassignedHours, int plannerAssignedHours, Long subjectId, SubjectHours subjectHour) {
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
        Schedule existingSchedule = scheduleRepository.findById(scheduleId).orElseThrow(()-> new ValidationException("Tvarkaraštis neegzistuoja", "Schedule", "Does not exist", scheduleId.toString()));
        Subject existingSubject = subjectRepository.findById(subjectId).orElseThrow(()-> new ValidationException("Pasirinktas dalykas neegzistuoja", "Subject", "Does no exist", subjectId.toString()));
        Teacher existingTeacher;
        Classroom existingClassroom;

        if (plannerDto.getTeacher() == null) {
            existingTeacher = null;
        } else {
           existingTeacher = teacherRepository.findById(plannerDto.getTeacher().getId()).orElseThrow(() -> new ValidationException("Pasirinktas mokytojas neegzistuoja", "Teacher", "Does not exist", plannerDto.getTeacher().getId().toString()));
        }

        if(plannerDto.getClassroom() == null) {
            existingClassroom = null;
        } else {
            existingClassroom = classroomRepository.findById(plannerDto.getClassroom().getId()).orElseThrow(()-> new ValidationException("Pasirinkta klasė neegzistuoja", "Classroom", "Does not exist", plannerDto.getClassroom().getId().toString()));
        }

        Integer unassignedHours = existingSchedule.getUnassignedTimeWithSubjectId(subjectId);
        Program program = existingSchedule.getGroups().getProgram();
        SubjectHours subjectHour = program.getSubjectHoursList().stream().filter(sh -> sh.getSubject().equals(subjectId)).findAny().orElseThrow(() ->
                new ValidationException("Pasirinktas dalykas neegzistuoja programoje", "Program", "Does not exist", program.toString()));
        int plannerAssignedHours = plannerDto.getAssignedHours();

        int hours = SetupAndValidateUnassignedHoursMap(existingSchedule, unassignedHours, plannerAssignedHours, subjectId, subjectHour);

        LocalDate date = plannerDto.getDateFrom();
        int interval = plannerDto.getEndIntEnum() - plannerDto.getStartIntEnum() + 1;
        int days = hours/interval;

        LocalDate lastDate = date.plusDays(days);

        int leftHours = hours % interval;
        int looper = 0;

        String isDateValid = isValidDates(plannerDto.getDateFrom(), lastDate, existingSchedule.getDateFrom(), existingSchedule.getDateUntil());
        if (!isDateValid.isEmpty()) {
            return isDateValid;
        }

        while(looper < days) {

            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY) {
                date = date.plusDays(2);
            }
            else if (dayOfWeek == DayOfWeek.SUNDAY) {
                date = date.plusDays(1);
            }
            else {
                WorkDay workDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, existingClassroom, getLessonStartString(plannerDto), getLessonEndString(plannerDto),
                        LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum()).getLessonStartFloat(), plannerDto.getOnline());
                workDayRepository.save(workDay);
                date = date.plusDays(1);
                existingSchedule.addWorkDay(workDay);
                looper++;
            }
        }

        LinkedHashSet<WorkDay> workDays = existingSchedule.getWorkingDays().stream().sorted(new WorkDayDtoComparator()).collect(Collectors.toCollection(LinkedHashSet::new));

        if (leftHours != 0) {
            while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                date = date.plusDays(1);
            }
            String lastLesson = LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum() + leftHours - 1).getLessonEnd();
            WorkDay lastWorkDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, existingClassroom, getLessonStartString(plannerDto), lastLesson,
                    LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum()).getLessonStartFloat(), plannerDto.getOnline());
            workDayRepository.save(lastWorkDay);
            existingSchedule.addWorkDay(lastWorkDay);
        }
        return "";
    }

    public List<WorkDay> getWorkDays(Long scheduleId) {
        return workDayRepository.findWorkDaysByScheduleId(scheduleId);
    }
}
