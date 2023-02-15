package lt.techin.schedule.hello;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @GetMapping(value = "/sup")
    @ResponseBody
    public String getAnswerString() {
        HelloDTO response = new HelloDTO();
        return response.getAnswer();
    }

    @GetMapping("/stuff")
    public String checkPersonInfo(@Valid HelloDTO helloDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/sup";
        }
        return "/hello";
    }
}
