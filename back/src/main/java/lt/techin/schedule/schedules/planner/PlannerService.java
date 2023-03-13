package lt.techin.schedule.schedules.planner;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.shift.LessonTime;
import lt.techin.schedule.subject.SubjectRepository;
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

    public PlannerService(ScheduleRepository scheduleRepository, SubjectRepository subjectRepository, TeacherRepository teacherRepository, WorkDayRepository workDayRepository) {
        this.scheduleRepository = scheduleRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.workDayRepository = workDayRepository;
    }


    public Boolean addSubjectPlanToSchedule(Long scheduleId, Long subjectId, PlannerDto plannerDto) {
        var existingSchedule = scheduleRepository.findById(scheduleId).orElseThrow(()-> new ValidationException("Tvarkaraštis neegzistuoja", "Schedule", "Does not exist", scheduleId.toString()));
        var existingSubject = subjectRepository.findById(subjectId).orElseThrow(()-> new ValidationException("Pasirinktas dalykas neegzistuoja", "Subject", "Does no exist", subjectId.toString()));
        var existingTeacher = teacherRepository.findById(plannerDto.getTeacher().getId()).orElseThrow(()-> new ValidationException("Pasirinktas mokytojas neegzistuoja", "Teacher", "Does not exist", plannerDto.getTeacher().getId().toString()));

        LocalDate date = plannerDto.getDateFrom();
        int hours = plannerDto.getAssignedHours();
        int interval = plannerDto.getEndIntEnum() - plannerDto.getStartIntEnum() + 1;
        int days = hours/interval;
        int leftHours = hours % interval;
        int created = 0;

        if (leftHours == 0) {
            for (int i = 1; i <= days; i++) {
                WorkDay workDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum()).getLessonStartFloat(), LessonTime.getLessonTimeByInt(plannerDto.getEndIntEnum()).getLessonEndFloat());
                workDayRepository.save(workDay);
                date = date.plusDays(1);
                created++;
            }
        } else {
            for (int i = 1; i <= days; i++) {
                WorkDay workDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum()).getLessonStartFloat(), LessonTime.getLessonTimeByInt(plannerDto.getEndIntEnum()).getLessonEndFloat());
                workDayRepository.save(workDay);
                date = date.plusDays(1);
                created++;
            }
            WorkDay lastWorkDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum()).getLessonStartFloat(), LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum() + leftHours - 1).getLessonEndFloat());
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
