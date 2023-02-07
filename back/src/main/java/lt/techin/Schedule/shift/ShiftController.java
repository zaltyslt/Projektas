package lt.techin.Schedule.shift;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shift")
public class ShiftController {

    private ShiftDTO shiftDatabase;

    public ShiftController(ShiftDTO shiftDatabase) {
        this.shiftDatabase = shiftDatabase;
    }

    @GetMapping
    public String getMapping () {
        shiftDatabase.save(new ShiftEntity("Morning", LessonTime.FIFTH, LessonTime.SEVENTH));
        return "Hello";
    }

    @GetMapping("/get")
    public String getShifts () {
        return shiftDatabase.findAll().get(0).toString();
    }
}
