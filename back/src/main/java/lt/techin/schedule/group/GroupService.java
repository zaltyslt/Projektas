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

    public List<Group> getActiveGroups() {
        return groupRepository.findAll().stream().filter(Group::getIsActive).sorted(compareGroupByName).collect(Collectors.toList());
    }

    public List<Group> getInactiveGroups() {
        return groupRepository.findAll().stream().filter(s -> !s.getIsActive()).sorted(compareGroupByName).collect(Collectors.toList());
    }

    public Group getGroupByID(Long groupID) {
        return groupRepository.findById(groupID).orElse(null);
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

    public void changeActiveGroupStatusByID(Long shiftID, boolean activeStatus) {
        Optional<Group> groupToActivate = groupRepository.findById(shiftID);
        if (groupToActivate.isPresent()) {
            groupToActivate.get().setIsActive(activeStatus);
            groupRepository.save(groupToActivate.get());
        }
    }

    //Explained in shifts
    public String modifyExistingGroup(Long groupID, GroupDto groupDto) {
        if (groupRepository.findById(groupID).isPresent()) {
            Optional<Group> foundGroup = findGroupByName(groupDto.getName());
            if(foundGroup.isPresent() && !foundGroup.get().getId().equals(groupID)) {
                return "Grupės pavadinimas turi būti unikalus.";
            }
            if (groupDto.getId() == null) {
                groupDto.setId(groupID);
            }
            if (groupDto.getCreatedDate() == null) {
                groupDto.setCreatedDate(groupRepository.findById(groupID).get().getCreatedDate());
            }
            Group groupToSave = GroupMapper.dtoToGroup(groupDto);
            groupRepository.save(groupToSave);
            return "";
        }
        return "Grupės pakeisti nepavyko.";
    }
}

