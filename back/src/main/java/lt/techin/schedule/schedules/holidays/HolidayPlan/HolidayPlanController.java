package lt.techin.schedule.schedules.holidays.HolidayPlan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/schedules")
public class HolidayPlanController {
    Logger logger = LoggerFactory.getLogger(HolidayPlanController.class);

    private final HolidayPlanService holidayPlanService;

    public HolidayPlanController(HolidayPlanService holidayPlanService) {
        this.holidayPlanService = holidayPlanService;
    }

    @GetMapping(value = "/holidays", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<HolidayPlanDto> getHolidayList() {
        logger.info("Get All Holidays List");
        return holidayPlanService.getAll().stream().map(HolidayPlanMapper::toHolidayPlanDto).toList();
    }

    @GetMapping(value = "/holidays/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<HolidayPlanDto> getHolidayListById(@PathVariable Long id) {
        logger.info("Get Holiday by ID");
        return holidayPlanService.getById(id).stream().map(HolidayPlanMapper::toHolidayPlanDto).toList();
    }

    @PostMapping(value = "/holidays/create-holiday/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createHoliday(@RequestBody HolidayPlanDto holidayPlanDto, @PathVariable Long id) {
        String returnString = holidayPlanService.create(HolidayPlanMapper.toHolidayPlan(holidayPlanDto), id);
        if (!returnString.isEmpty()) {
            logger.info("The holiday was NOT created successfully");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", returnString));
        }
        logger.info("The holiday was created successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", returnString));
    }

    @GetMapping(value = "/holiday/{holidayId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HolidayPlanDto> getHolidayById(@PathVariable Long holidayId) {
        HolidayPlan existingHolidayPlan = holidayPlanService.getHolidayById(holidayId);
        return ok(HolidayPlanMapper.toHolidayPlanDto(existingHolidayPlan));
    }

    @PutMapping(value = "/holidays/update-holiday/{holidayId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HolidayPlanDto> updateHoliday(@PathVariable Long holidayId, @RequestBody HolidayPlanDto holidayPlanDto) {
        HolidayPlan updatedHolidayPlan = holidayPlanService.update(holidayId, HolidayPlanMapper.toHolidayPlan(holidayPlanDto));
        return ok(HolidayPlanMapper.toHolidayPlanDto(updatedHolidayPlan));
    }

    @DeleteMapping(value = "/holidays/delete-holiday/{holidayId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Boolean> deleteHoliday(@PathVariable Long holidayId) {
        Boolean result = holidayPlanService.deleteHoliday(holidayId);
        return ok(result);
    }
}
