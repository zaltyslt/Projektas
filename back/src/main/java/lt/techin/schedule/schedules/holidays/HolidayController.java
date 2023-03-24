package lt.techin.schedule.schedules.holidays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        var createHoliday = holidayService.create(HolidayMapper.toHoliday(holidayDto), id);
        if (createHoliday == null) {
            logger.info("The holiday was NOT created successfully");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
        logger.info("The holiday was created successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", (HolidayMapper.toHolidayDto(createHoliday).toString())));
    }
}
