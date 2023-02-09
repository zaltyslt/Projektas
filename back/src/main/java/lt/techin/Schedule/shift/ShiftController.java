package lt.techin.Schedule.shift;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shift")
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
    public String getShifts () {
        return shiftDatabase.findAll().get(0).toString();
    }

    @GetMapping("/add")
    public List<Shift> tryingToAdd() {
        shiftDatabase.save(new Shift("Rytas", LessonTime.getLessonTimeByInt(1).getLessonStart(), LessonTime.getLessonTimeByInt(3).getLessonEnd(), true));
        //shiftService.addShift(new Shift("Rytas", LessonTime.getLessonTimeByInt(1).getLessonStart(), LessonTime.getLessonTimeByInt(3).getLessonEnd(), true));
        System.out.println("Trying to add stuff");
        return shiftDatabase.findAll();
    }

    @PostMapping("/add")
    public void addShift () {
        System.out.println("Post mapping fired up!!");
    }
}
