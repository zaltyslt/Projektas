package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.classrooms.ClassroomRepository;
import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.shift.LessonTime;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.teachers.Teacher;
import lt.techin.schedule.teachers.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        var existingSchedule = scheduleRepository.findById(scheduleId).orElseThrow(()-> new ValidationException("Tvarkaraštis neegzistuoja", "Schedule", "Does not exist", scheduleId.toString()));
        var existingSubject = subjectRepository.findById(subjectId).orElseThrow(()-> new ValidationException("Pasirinktas dalykas neegzistuoja", "Subject", "Does no exist", subjectId.toString()));
        var existingTeacher = new Teacher();
        var existingClassroom = new Classroom();

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

        LocalDate date = plannerDto.getDateFrom();
        int hours = plannerDto.getAssignedHours();
        int interval = plannerDto.getEndIntEnum() - plannerDto.getStartIntEnum() + 1;
        int days = hours/interval;
        int leftHours = hours % interval;
        int created = 0;

        if (leftHours == 0) {
            for (int i = 1; i <= days; i++) {
                WorkDay workDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, existingClassroom, getLessonStartString(plannerDto), getLessonEndString(plannerDto), plannerDto.getOnline());
                workDayRepository.save(workDay);
                date = date.plusDays(1);
                created++;
            }
        } else {
            for (int i = 1; i <= days; i++) {
                WorkDay workDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, existingClassroom, getLessonStartString(plannerDto), getLessonEndString(plannerDto), plannerDto.getOnline());
                workDayRepository.save(workDay);
                date = date.plusDays(1);
                created++;
            }
            var lastLesson = LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum() + leftHours - 1).getLessonEnd();
            WorkDay lastWorkDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, existingClassroom, getLessonEndString(plannerDto), lastLesson, plannerDto.getOnline());
            workDayRepository.save(lastWorkDay);
            created++;
        }
        if(created >=1) {
            return true;
        } else {
            return false;
        }
    }

    public List<WorkDay> getWorkDays(Long scheduleId) {
        return workDayRepository.findWorkDaysByScheduleId(scheduleId);
    }
}
