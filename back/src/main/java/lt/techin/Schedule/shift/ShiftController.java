package lt.techin.Schedule.shift;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shift")
public class ShiftController {

    @GetMapping
    public String getMapping () {
        return "Hello";
    }
}
