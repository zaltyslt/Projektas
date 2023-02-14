package lt.techin.schedule.shift;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1/shift")
@CrossOrigin("http://localhost:3000")
public class ShiftController {

    private final ShiftDTO shiftDatabase;
    private final ShiftService shiftService;

    public ShiftController(ShiftDTO shiftDatabase, ShiftService shiftService) {
        this.shiftDatabase = shiftDatabase;
        this.shiftService = shiftService;
    }

    @GetMapping("/get")
    public Shift getShift () {
        return shiftDatabase.findAll().get(0);
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

    @GetMapping("/add")
    public List<Shift> tryingToAdd() {
        shiftDatabase.save(new Shift("Rytas", LessonTime.getLessonTimeByInt(1).getLessonStart(), LessonTime.getLessonTimeByInt(3).getLessonEnd(), true, 1, 3));
        shiftDatabase.save(new Shift("Rytas veliau", LessonTime.getLessonTimeByInt(4).getLessonStart(), LessonTime.getLessonTimeByInt(4).getLessonEnd(), true,4 ,4));
        shiftDatabase.save(new Shift("Vidurdienis", LessonTime.getLessonTimeByInt(5).getLessonStart(), LessonTime.getLessonTimeByInt(7).getLessonEnd(), true,5, 7));
        shiftDatabase.save(new Shift("Vakaras anskciau", LessonTime.getLessonTimeByInt(8).getLessonStart(), LessonTime.getLessonTimeByInt(10).getLessonEnd(), true,8 ,10));
        shiftDatabase.save(new Shift("Vakaras", LessonTime.getLessonTimeByInt(11).getLessonStart(), LessonTime.getLessonTimeByInt(12).getLessonEnd(), true,11,12));
        return shiftDatabase.findAll();
    }

    @GetMapping("/add-inactive")
    public List<Shift> tryingToAddInactive() {
        shiftDatabase.save(new Shift("Naktis", LessonTime.getLessonTimeByInt(1).getLessonStart(), LessonTime.getLessonTimeByInt(3).getLessonEnd(), false,1,3));
        shiftDatabase.save(new Shift("Kazkokia Nesamone Cia", LessonTime.getLessonTimeByInt(4).getLessonStart(), LessonTime.getLessonTimeByInt(4).getLessonEnd(), false, 4,4));
        return shiftDatabase.findAll();
    }

    @PostMapping("/add-shift")
    public @ResponseBody Map<String, String> addShift (@RequestBody @Valid Shift shiftToAdd, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int x = 0; x < bindingResult.getAllErrors().size(); x++) {
                response.put("message" + x, errors.get(x).getDefaultMessage());
            }
        }
        else {
            String addRequest = shiftService.addUniqueShift(shiftToAdd);
            if(!addRequest.isEmpty()) {
                response.put("message" + 0, addRequest);
            }
        }
        return response;
    }

    @PutMapping("/activate-shift/{shiftID}")
    public void activateShift(@PathVariable Long shiftID) {
        shiftService.changeActiveShiftStatusByID(shiftID, true);
    }

    @PutMapping("/deactivate-shift/{shiftID}")
    public void deactivateShift(@PathVariable Long shiftID) {
        shiftService.changeActiveShiftStatusByID(shiftID, false);
    }

    @PutMapping("/modify-shift/{shiftID}")
    public @ResponseBody Map<String, String> modifyShift(@PathVariable Long shiftID, @RequestBody @Valid Shift shiftToChange, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int x = 0; x < bindingResult.getAllErrors().size(); x++) {
                response.put("message" + x, errors.get(x).getDefaultMessage());
            }
        }
        else {
            String modifyRequest = shiftService.modifyExistingShift(shiftID, shiftToChange);
            if (!modifyRequest.isEmpty()) {
                System.out.println("ERRORAS HERE");
                response.put("message" + 0, modifyRequest);
            }
        }
        return response;
    }
}
