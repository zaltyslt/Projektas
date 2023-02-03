package lt.techin.Schedule.hello;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
//@RequestMapping("/hello")
public class HelloController {
    public HelloController() {
    }

    @GetMapping(value = "/hello", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<HelloDTO> getAnswer() {
        var response = new HelloDTO();

        return ResponseEntity.ok(response);
    }
}
