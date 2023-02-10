package lt.techin.Schedule.shift;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/api/v1/shift")
@CrossOrigin("http://localhost:3000")
public class ShiftController {

    private ShiftDTO shiftDatabase;
    private ShiftService shiftService;

    public ShiftController(ShiftDTO shiftDatabase, ShiftService shiftService) {
        this.shiftDatabase = shiftDatabase;
        this.shiftService = shiftService;
    }

    @GetMapping
    public String getMapping () {
        return "Hello";
    }

    @GetMapping("/get")
    public Shift getShift () {
        return shiftDatabase.findAll().get(0);
    }

    @GetMapping("get-active")
    public List<Shift> getActiveShifts() {
        return shiftService.getActiveShifts();
    }

    @GetMapping("get-inactive")
    public List<Shift> getInactiveShifts() {
        return shiftService.getInactiveShifts();
    }

    @GetMapping("/add")
    public List<Shift> tryingToAdd() {
        shiftDatabase.save(new Shift("Rytas", LessonTime.getLessonTimeByInt(1).getLessonStart(), LessonTime.getLessonTimeByInt(3).getLessonEnd(), true));
        shiftDatabase.save(new Shift("Rytas veliau", LessonTime.getLessonTimeByInt(4).getLessonStart(), LessonTime.getLessonTimeByInt(4).getLessonEnd(), true));
        shiftDatabase.save(new Shift("Vidurdienis", LessonTime.getLessonTimeByInt(5).getLessonStart(), LessonTime.getLessonTimeByInt(7).getLessonEnd(), true));
        shiftDatabase.save(new Shift("Vakaras anskciau", LessonTime.getLessonTimeByInt(8).getLessonStart(), LessonTime.getLessonTimeByInt(10).getLessonEnd(), true));
        shiftDatabase.save(new Shift("Vakaras", LessonTime.getLessonTimeByInt(11).getLessonStart(), LessonTime.getLessonTimeByInt(12).getLessonEnd(), true));

        return shiftDatabase.findAll();
    }

    @GetMapping("/add-inactive")
    public List<Shift> tryingToAddInactive() {
        shiftDatabase.save(new Shift("Naktis", LessonTime.getLessonTimeByInt(1).getLessonStart(), LessonTime.getLessonTimeByInt(3).getLessonEnd(), false));
        shiftDatabase.save(new Shift("Kazkokia Nesamone Cia", LessonTime.getLessonTimeByInt(4).getLessonStart(), LessonTime.getLessonTimeByInt(4).getLessonEnd(), false));

        return shiftDatabase.findAll();
    }

    @PostMapping("/add")
    public void addShift () {
        System.out.println("Post mapping fired up!!");
    }
}
