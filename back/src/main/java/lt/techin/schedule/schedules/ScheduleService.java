package lt.techin.schedule.schedules;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.group.GroupRepository;
import lt.techin.schedule.schedules.planner.WorkDayRepository;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.teachers.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        var schedule = scheduleRepository.findAll();
        for (Schedule schedule1 : schedule) {
            System.out.println(schedule1.getDateFrom().getDayOfWeek());

        }
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

    public boolean deleteSchedule(Long id) {
        Optional<Schedule> scheduleToDelete = scheduleRepository.findById(id);
        if (scheduleToDelete.isPresent()) {
            try {
             scheduleRepository.delete(scheduleToDelete.get());
                return true;
            } catch (Exception e) {
              return false;
            }
        }
        throw new ValidationException("Toks tvarkaraštis neegzistuoja","id","not found",id.toString());

    }

}
