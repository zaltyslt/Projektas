package lt.techin.schedule.schedules.holidays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static lt.techin.schedule.schedules.holidays.HolidayMapper.toHoliday;
import static lt.techin.schedule.schedules.holidays.HolidayMapper.toHolidayDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/schedules")
public class HolidayController {
    Logger logger = LoggerFactory.getLogger(HolidayController.class);

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping(value = "/holidays", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<HolidayDto> getHolidayList() {
        logger.info("Get All Holidays List");
        return holidayService.getAll().stream().map(HolidayMapper::toHolidayDto).toList();
    }

    @GetMapping(value = "/holidays/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<HolidayDto> getHolidayListById(@PathVariable Long id) {
        logger.info("Get Holiday by ID");
        return holidayService.getById(id).stream().map(HolidayMapper::toHolidayDto).toList();
    }

    @PostMapping(value = "/holidays/create-holiday/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createHoliday(@RequestBody HolidayDto holidayDto, @PathVariable Long id) {
        var createHoliday = holidayService.create(toHoliday(holidayDto), id);
        if (createHoliday == null) {
            logger.info("The holiday was NOT created successfully");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
        logger.info("The holiday was created successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", (toHolidayDto(createHoliday).toString())));
    }

    @GetMapping(value = "/holiday/{holidayId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HolidayDto> getHolidayById(@PathVariable Long holidayId) {
        Holiday existingHoliday = holidayService.getHolidayById(holidayId);
        return ok(toHolidayDto(existingHoliday));
    }

    @PutMapping(value = "/holidays/update-holiday/{holidayId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HolidayDto> updateHoliday(@PathVariable Long holidayId, @RequestBody HolidayDto holidayDto) {
        Holiday updatedHoliday = holidayService.update(holidayId, toHoliday(holidayDto));
        return ok(toHolidayDto(updatedHoliday));
    }

    @DeleteMapping(value = "/holidays/delete-holiday/{holidayId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Boolean> deleteHoliday(@PathVariable Long holidayId) {
        Boolean result = holidayService.deleteHoliday(holidayId);
        return ok(result);
    }
}
