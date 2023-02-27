package lt.techin.schedule.shift;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1/shift")
@CrossOrigin("http://localhost:3000/")
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
        return shiftService.getShiftByID(shiftID);
    }

    @PostMapping("/add-shift")
    public @ResponseBody Map<Integer, String> addShift (@RequestBody @Valid Shift shiftToAdd,
                                                        BindingResult bindingResult) {
        Map<Integer, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int x = 0; x < bindingResult.getAllErrors().size(); x++) {
                response.put(x, errors.get(x).getDefaultMessage());
            }
        }
        else {
            String addRequest = shiftService.addUniqueShift(shiftToAdd);
            if(!addRequest.isEmpty()) {
                response.put(0, addRequest);
            }
        }
        return response;
    }

    @PatchMapping("/activate-shift/{shiftID}")
    public void activateShift(@PathVariable Long shiftID) {

        shiftService.changeActiveShiftStatusByID(shiftID, true);
    }

    @PatchMapping("/deactivate-shift/{shiftID}")
    public void deactivateShift(@PathVariable Long shiftID) {
        shiftService.changeActiveShiftStatusByID(shiftID, false);
    }

    @PutMapping("/modify-shift/{shiftID}")
    public @ResponseBody Map<Integer, String> modifyShift(@PathVariable Long shiftID,
                                                          @RequestBody @Valid Shift shiftToChange,
                                                          BindingResult bindingResult) {
        Map<Integer, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int x = 0; x < bindingResult.getAllErrors().size(); x++) {
                response.put(x, errors.get(x).getDefaultMessage());
            }
        }
        else {
            String modifyRequest = shiftService.modifyExistingShift(shiftID, shiftToChange);
            if (!modifyRequest.isEmpty()) {
                response.put(0, modifyRequest);
            }
        }
        return response;
    }
}
