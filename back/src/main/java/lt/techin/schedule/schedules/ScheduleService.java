package lt.techin.schedule.schedules;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.group.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleCreateDto;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           GroupRepository groupRepository) {
        this.scheduleRepository = scheduleRepository;
        this.groupRepository = groupRepository;
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
}
