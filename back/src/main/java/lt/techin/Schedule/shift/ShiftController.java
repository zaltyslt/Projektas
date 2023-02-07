package lt.techin.Schedule.shift;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/shift")
public class ShiftController {

    @GetMapping("/")
    public String getMapping () {
        return "Hello biatch";
    }
}
