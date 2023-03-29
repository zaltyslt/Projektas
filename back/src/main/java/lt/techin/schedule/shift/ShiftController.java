package lt.techin.schedule.shift;

import jakarta.validation.Valid;
import lt.techin.schedule.validators.ValidationDto;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1/shift")
//@CrossOrigin("http://localhost:3000/")
public class ShiftController {

    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping("/get-active")
    public List<Shift> getActiveShifts() {
        return shiftService.getActiveShifts();
    }

    @GetMapping("/get-inactive")
    public List<Shift> getInactiveShifts() {
        return shiftService.getInactiveShifts();
    }

    @GetMapping("/view-shift/{shiftID}")
    public Shift getShift(@PathVariable Long shiftID) {
        var result = shiftService.getShiftByID(shiftID);
        return result;
    }

    @PostMapping(value = "/add-shift", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ValidationDto addShift (@RequestBody @Valid ShiftDto shiftToAddDto, BindingResult bindingResult) {
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
            String addResponse = shiftService.addUniqueShift(shiftToAddDto);
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

    @PatchMapping("/activate-shift/{shiftID}")
    public void activateShift(@PathVariable Long shiftID) {
        shiftService.changeActiveShiftStatusByID(shiftID, true);
    }

    @PatchMapping("/deactivate-shift/{shiftID}")
    public void deactivateShift(@PathVariable Long shiftID) {
        shiftService.changeActiveShiftStatusByID(shiftID, false);
    }

    @PutMapping(value = "/modify-shift/{shiftID}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ValidationDto modifyShift(@PathVariable Long shiftID, @RequestBody @Valid ShiftDto shiftToChangeDto, BindingResult bindingResult) {
        ShiftDto shiftDto = shiftToChangeDto;
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
            String modifyResponse = shiftService.modifyExistingShift(shiftID, shiftToChangeDto);
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
