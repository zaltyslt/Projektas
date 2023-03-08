package lt.techin.schedule.schedules;

import lt.techin.schedule.group.GroupRepository;
import lt.techin.schedule.group.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
//    private GroupService groupService;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           GroupRepository groupRepository,
                           GroupService groupService) {
        this.scheduleRepository = scheduleRepository;
        this.groupRepository = groupRepository;
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    public Schedule create(Schedule schedule) {
//        if (schedule.getGroups() == null) return null;
//        {
            try {
                return scheduleRepository.save(schedule);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
            return null;
        }
//    }
}
