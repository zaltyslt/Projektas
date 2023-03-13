package lt.techin.schedule.group;


import jakarta.validation.Valid;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftDto;
import lt.techin.schedule.shift.ShiftMapper;
import lt.techin.schedule.shift.ShiftService;
import lt.techin.schedule.teachers.TeacherDto;
import lt.techin.schedule.validators.ValidationDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/get-active")
    public List<Group> getActiveGroups() {
        return groupService.getActiveGroups();
    }

    @GetMapping("/get-active/small")
    public List<GroupEntityDto> getActiveSmallGroups() {
        return groupService.getActiveGroups().stream().map(GroupMapper::toGroupEntityDto).collect(Collectors.toList());
    }

    @GetMapping("/get-inactive")
    public List<Group> getInactiveGroups() {
        return groupService.getInactiveGroups();
    }

    @GetMapping("/view-group/{groupID}")
    public Group getGroup(@PathVariable Long groupID) {
        return groupService.getGroupByID(groupID);
    }

    @PatchMapping("/activate-group/{groupID}")
    public void activateShift(@PathVariable Long groupID) {
        groupService.changeActiveGroupStatusByID(groupID, true);
    }

    @PatchMapping("/deactivate-group/{groupID}")
    public void deactivateShift(@PathVariable Long groupID) {
        groupService.changeActiveGroupStatusByID(groupID, false);
    }
    @PostMapping(value = "/add-group-test", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> addGroupTest (@RequestBody @Valid String groupToAddDto, BindingResult bindingResult) {
        var aa = groupToAddDto;
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "/add-group", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ValidationDto addGroup (@RequestBody @Valid GroupDto groupToAddDto, BindingResult bindingResult) {
       ValidationDto validationDto = new ValidationDto();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int x = 0; x < bindingResult.getAllErrors().size(); x++) {
                validationDto.addValidationError(
                        "\"" + Objects.requireNonNull(bindingResult.getFieldError()).getField() + "\"",
                        errors.get(x).getDefaultMessage());
            }
            validationDto.setPassedValidation(false);
            validationDto.setValid(false);
        }
        else {
            String addResponse = groupService.addUniqueGroup(groupToAddDto);
            validationDto.setPassedValidation(true);
            if(addResponse.isEmpty()) {
                validationDto.setValid(true);
            } else {
                validationDto.setValid(false);
                validationDto.addDatabaseError(addResponse);
            }
        }
        return validationDto;
    }

    @PutMapping(value = "/modify-group/{groupID}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ValidationDto modifyShift(@PathVariable Long groupID, @RequestBody @Valid GroupDto groupToChangeDto, BindingResult bindingResult) {
        GroupDto groupDto = groupToChangeDto;
        ValidationDto validationDto = new ValidationDto();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int x = 0; x < bindingResult.getAllErrors().size(); x++) {
                validationDto.addValidationError(
                        "\"" + Objects.requireNonNull(bindingResult.getFieldError()).getField() + "\"",
                        errors.get(x).getDefaultMessage());
            }
            validationDto.setPassedValidation(false);
            validationDto.setValid(false);
        } else {
            String modifyResponse = groupService.modifyExistingGroup(groupID, groupToChangeDto);
            validationDto.setPassedValidation(true);
            if (modifyResponse.isEmpty()) {
                validationDto.setValid(true);
            } else {
                validationDto.setValid(false);
                validationDto.addDatabaseError(modifyResponse);
            }
        }
        return validationDto;
    }

}
