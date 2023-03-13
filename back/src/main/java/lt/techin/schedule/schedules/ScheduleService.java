package lt.techin.schedule.schedules;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.group.GroupRepository;
import lt.techin.schedule.shift.LessonTime;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.teachers.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleCreateDto;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    private final WorkDayRepository workDayRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           GroupRepository groupRepository,
                           SubjectRepository subjectRepository,
                           TeacherRepository teacherRepository, WorkDayRepository workDayRepository) {
        this.scheduleRepository = scheduleRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.workDayRepository = workDayRepository;
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    public Schedule createSchedule(Schedule schedule, Long groupId) {
        var existingGroup = groupRepository.findById(groupId).orElseThrow(() ->
                new ValidationException("Nurodyta grupė nerasta", "Group", "Does not exist", groupId.toString()));
        schedule.setGroups(existingGroup);

        var existing = scheduleRepository.findAll();
        existing = existing.stream().filter(s -> s.getGroups().getName().equalsIgnoreCase(existingGroup.getName()))
                .filter(s -> s.getDateFrom().equals(schedule.getDateFrom()))
                .filter(s -> s.getDateUntil().equals(schedule.getDateUntil()))
                .collect(Collectors.toList());
        if (existing.size() > 0) {
            var scheduleDto = toScheduleCreateDto(schedule);

            throw new ValidationException("Tvarkaraštis šiai grupei ir šiam " +
                    "laikotarpiui jau yra sukurtas", "Schedule", "Not unique", scheduleDto.toString());
        } else {
            return scheduleRepository.save(schedule);
        }
    }

    public Schedule disable(Long id) {
        var existingSchedule = scheduleRepository.findById(id).orElse(null);
        if (existingSchedule != null) {
            existingSchedule.setActive(false);
            return scheduleRepository.save(existingSchedule);
        }
        return null;
    }

    public Schedule enable(Long scheduleId) {
        var existingSchedule = scheduleRepository.findById(scheduleId).orElse(null);
        if (existingSchedule != null) {
            existingSchedule.setActive(true);
            return scheduleRepository.save(existingSchedule);
        }
        return null;
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

}
