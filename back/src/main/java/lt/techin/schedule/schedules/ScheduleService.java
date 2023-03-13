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

    public void addSubjectPlanToSchedule(Long scheduleId, Long subjectId, PlannerDto plannerDto) {

//        Schedule scheduleee = new Schedule();
//        scheduleee.setId(1L);
//        scheduleee.setActive(true);
//        scheduleee.setSemester("semes");
//        scheduleee.setDateFrom(LocalDate.now());
//        scheduleee.setDateUntil(LocalDate.now());
//        scheduleee.setSchoolYear("100");
//        scheduleee.setCreatedDate(LocalDateTime.now());
//        scheduleee.setCreatedDate(LocalDateTime.now());
//        scheduleee.setGroups(null);
//
//        Classroom classroom = new Classroom();
//        classroom.setId(1L);
//        classroom.setClassroomName("name");
//        classroom.setBuilding(BuildingType.AKADEMIJA);
//        classroom.setActive(true);
//        classroom.setDescription("descr");
//        classroom.setCreatedDate(LocalDateTime.now());
//        classroom.setModifiedDate(LocalDateTime.now());
//
//        Subject ssu = new Subject();
//        ssu.setId(subjectId);
//        ssu.setName("Subjektas");
//        ssu.setDescription("Desc");
//        ssu.setClassRooms(Set.of(classroom));
//
//        ssu.setModule(new Module(1L, "10", "NameModule", LocalDateTime.now(), LocalDateTime.now(), false));
//        subjectRepository.save(ssu);


//        Schedule currentSchedule = scheduleee; //findById(scheduleId);
//        Subject subject = subjectRepository.findById(subjectId).orElse(null);

        var existingSchedule = scheduleRepository.findById(scheduleId).orElseThrow();
        var existingSubject = subjectRepository.findById(subjectId).orElseThrow();
        var existingTeacher = teacherRepository.findById(plannerDto.getTeacher().getId()).orElseThrow();


//        if (existingSchedule != null && existingSubject != null) {
//            LocalDate startDate = plannerDto.getDateFrom();
//            LocalDate endDate = plannerDto.getDateUntil();
//
//            //Used so stream would include last day also
//            startDate.datesUntil(endDate.plusDays(1)).forEach(date -> {
//                existingSchedule.addWorkDay(new WorkDay(date, existingSubject, existingTeacher,
//                            LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum()).getLessonStartFloat(),
//                            LessonTime.getLessonTimeByInt(plannerDto.getEndIntEnum()).getLessonEndFloat()));
//            });
//        }

        LocalDate date = plannerDto.getDateFrom();
        int hours = plannerDto.getAssignedHours();
        int interval = plannerDto.getEndIntEnum() - plannerDto.getStartIntEnum() + 1;
        int days = hours/interval;
        int leftHours = hours % interval;
        System.out.println(days);
        System.out.println(leftHours);

        if (leftHours == 0) {
            for (int i = 1; i <= days; i++) {
                WorkDay workDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum()).getLessonStartFloat(), LessonTime.getLessonTimeByInt(plannerDto.getEndIntEnum()).getLessonEndFloat());
                workDayRepository.save(workDay);
                existingSchedule.addWorkDay(workDay);
                scheduleRepository.save(existingSchedule);
                date = date.plusDays(1);
            }
        } else {
            for (int i = 1; i <= days; i++) {
                WorkDay workDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum()).getLessonStartFloat(), LessonTime.getLessonTimeByInt(plannerDto.getEndIntEnum()).getLessonEndFloat());
                workDayRepository.save(workDay);
                existingSchedule.addWorkDay(workDay);
                scheduleRepository.save(existingSchedule);
                date = date.plusDays(1);
            }
            WorkDay lastWorkDay = new WorkDay(date, existingSubject, existingTeacher, existingSchedule, LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum()).getLessonStartFloat(), LessonTime.getLessonTimeByInt(plannerDto.getStartIntEnum() + leftHours - 1).getLessonEndFloat());
            workDayRepository.save(lastWorkDay);
            existingSchedule.addWorkDay(lastWorkDay);
            scheduleRepository.save(existingSchedule);
            date = date.plusDays(1);
        }



    }

}
