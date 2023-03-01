package lt.techin.schedule.group;

import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftDto;
import lt.techin.schedule.shift.ShiftMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void addGroup(Group group) {
        groupRepository.save(group);
    }


    private final Comparator<Group> compareGroupByName = Comparator.comparing(o -> o.getName().toLowerCase());

    public List<Shift> getActiveShifts() {
        return groupRepository.findAll().stream().filter(Group::getIsActive).sorted(compareGroupByName).collect(Collectors.toList());
    }

    public List<Shift> getInactiveShifts() {
        return groupRepository.findAll().stream().filter(s -> !s.getIsActive()).sorted(compareGroupByName).collect(Collectors.toList());
    }

    public Shift getShiftByID(Long shiftID) {
        return groupRepository.findAll().stream().filter(s -> Objects.equals(s.getId(), shiftID)).findAny().orElse(null);
    }

    private Optional<Group> findGroupByName(String name) {
        return groupRepository.findAll().stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst();
    }

    public String addUniqueGroup(GroupDto groupDto) {
        if(findGroupByName(groupDto.getName()).isPresent()) {
            return "Grupės pavadinimas turi būti unikalus.";
        }
        else {
            groupRepository.save(GroupMapper.dtoToGroup(groupDto));
            return "";
        }
    }



}

