package lt.techin.schedule.schedules.holidays;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/schedules")
public class HolidayController {
    Logger logger = LoggerFactory.getLogger(HolidayController.class);

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping(value = "/holidays/create-holiday/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createHoliday(@PathVariable Long id, @Valid @RequestBody HolidayPlanDto holidayPlanDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("The holiday was NOT created successfully");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", (bindingResult.getAllErrors().get(0).getDefaultMessage() + " \"" +
                                                        Objects.requireNonNull(bindingResult.getFieldError()).getField() + "\" " + " laukelyje."
                            )));
        } else {
            //If validation is passed trying to create holidays
            String returnString = holidayService.create(holidayPlanDto, id);
            if (!returnString.isEmpty()) {
                logger.info("The holiday was NOT created successfully");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", returnString));
            }
            logger.info("The holiday was created successfully");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", returnString));
        }
    }

    @GetMapping(value = "/holiday/{holidayId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HolidayDto> getHolidayById(@PathVariable Long holidayId) {
        Holiday existingHoliday = holidayService.getHolidayById(holidayId);
        return ok(HolidayMapper.toHolidayDto(existingHoliday));
    }

    @PutMapping(value = "/holidays/update-holiday/{holidayId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HolidayDto> updateHoliday(@PathVariable Long holidayId, @RequestBody HolidayDto holidayDto) {
        Holiday updatedHoliday = holidayService.update(holidayId, HolidayMapper.toHoliday(holidayDto));
        return ok(HolidayMapper.toHolidayDto(updatedHoliday));
    }

    @DeleteMapping(value = "/holidays/delete-holiday/{holidayId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Boolean> deleteHoliday(@PathVariable Long holidayId) {
        Boolean result = holidayService.deleteHoliday(holidayId);
        return ok(result);
    }
}
