package lt.techin.schedule.group;


import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
//@CrossOrigin("http://localhost:3000/")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/get-active")
    public List<Group> getActiveGroups() {
        return groupService.getActiveGroups();
    }

    @GetMapping("/get-inactive")
    public List<Shift> getInactiveGroups() {
        return groupService.getInactiveGroups();
    }

    @GetMapping("/view-group/{groupID}")
    public Shift getShift(@PathVariable Long groupID) {
        return groupService.getGroupByID(groupID);
    }
}
