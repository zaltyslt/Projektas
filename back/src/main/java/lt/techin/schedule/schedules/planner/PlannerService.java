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
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    public Boolean addSubjectPlanToSchedule(Long scheduleId, Long subjectId, PlannerDto plannerDto) {
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

        int hours;
        Integer unassignedHours = existingSchedule.getUnassignedTimeWithSubjectId(subjectId);
        Program program = existingSchedule.getGroups().getProgram();
        SubjectHours subjectHour = program.getSubjectHoursList().stream().filter(sh -> sh.getSubject().equals(subjectId)).findAny().orElseThrow(() -> new ValidationException("Pasirinktas dalykas neegzistuoja programoje", "Program", "Does not exist", program.toString()));

        int plannerAssignedHours = plannerDto.getAssignedHours();

        //In a case where Map already has a value of that particular subjectId
        if (unassignedHours != null) {
            if (unassignedHours == 0) {
                throw new ValidationException("Dalykas pasirinktoje programoje nebeturi nepanaudotų valandų", "Program", "Unassigned hours value is 0", existingSchedule.getSubjectIdWithUnassignedTime().toString());
            }
            //Checks so that passed value wouldn't exceed maximum hours subject has
            if (plannerAssignedHours > unassignedHours) {
                hours = unassignedHours;
                existingSchedule.replaceUnassignedTime(subjectId, 0);
            }
            //Places new assigned hours amount
            else {
                hours = plannerAssignedHours;
                existingSchedule.replaceUnassignedTime(subjectId, unassignedHours - hours);
            }
        }
        //In a case where Map doesn't have a value of that particular subjectId
        else {
            //Checks so that passed value wouldn't exceed maximum hours subject has
            if (plannerAssignedHours > subjectHour.getHours()) {
                hours = subjectHour.getHours();
                existingSchedule.addUnassignedTimeWithSubjectId(subjectId, 0);
            }
            //Places new assigned hours amount
            else {
                hours = plannerAssignedHours;
                existingSchedule.addUnassignedTimeWithSubjectId(subjectId, subjectHour.getHours() - hours);
            }
        }

        LocalDate date = plannerDto.getDateFrom();
        int interval = plannerDto.getEndIntEnum() - plannerDto.getStartIntEnum() + 1;
        int days = hours/interval;
        int leftHours = hours % interval;
        boolean created = false;
        int looper = 0;

        while(looper < days) {

            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY) {
                date = date.plusDays(2);
            }
            else if (dayOfWeek == DayOfWeek.SUNDAY) {
                date = date.plusDays(1);
            }
            else {
                WorkDay workDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, existingClassroom, getLessonStartString(plannerDto), getLessonEndString(plannerDto), plannerDto.getOnline());
                workDayRepository.save(workDay);
                date = date.plusDays(1);
                created = true;
                existingSchedule.addWorkDay(workDay);
                looper++;
            }
        }

        if (leftHours != 0) {
            while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                date = date.plusDays(1);
            }
            String lastLesson = LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum() + leftHours - 1).getLessonEnd();
            WorkDay lastWorkDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, existingClassroom, getLessonStartString(plannerDto), lastLesson, plannerDto.getOnline());
            workDayRepository.save(lastWorkDay);
            created = true;
            existingSchedule.addWorkDay(lastWorkDay);
        }
        return created;
    }

    public List<WorkDay> getWorkDays(Long scheduleId) {
        return workDayRepository.findWorkDaysByScheduleId(scheduleId);
    }
}
