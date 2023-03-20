package lt.techin.schedule.schedules;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleDto;
import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleFromCreateDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    Logger logger = Logger.getLogger(ScheduleController.class.getName());
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ScheduleDto> getScheduleList() {
        logger.info("Get All Schedule List");
        return scheduleService.getAll().stream().map(ScheduleMapper::toScheduleDto).toList();
    }

    @GetMapping(value = "/schedule/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable Long id) {
        var schedule = scheduleService.findById(id);
        if (schedule == null) {
            logger.info("Get Schedule By Id not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Get Schedule By Id Success");
        return ok(toScheduleDto(scheduleService.findById(id)));
    }

    @PostMapping(value = "/create-schedule", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScheduleDto> create(@RequestParam Long groupId, @RequestBody ScheduleCreateDto scheduleCreateDto) {
        var createdSchedule = scheduleService.createSchedule(toScheduleFromCreateDto(scheduleCreateDto), groupId);
        logger.info("The schedule was created, successfully");
        return ok(toScheduleDto(createdSchedule));
    }

    @PatchMapping("/disable-schedule/{scheduleId}")
    public ScheduleDto disableSchedule(@PathVariable Long scheduleId) {
        var disableSchedule = scheduleService.disable(scheduleId);
        logger.log(Level.INFO, "The schedule was disabled: {0} ", scheduleId);
        return toScheduleDto(disableSchedule);
    }

    @PatchMapping("/enable-schedule/{scheduleId}")
    public ScheduleDto enableSchedule(@PathVariable Long scheduleId) {
        var enableSchedule = scheduleService.enable(scheduleId);
        logger.log(Level.INFO, "The schedule was enabled: {0} ", scheduleId);
        return toScheduleDto(enableSchedule);
    }

    @DeleteMapping("/delete-schedule/{scheduleId}")
    public ResponseEntity<Boolean> deleteSchedule(@PathVariable Long scheduleId) {
//        var enableSchedule = scheduleService.enable(scheduleId);
        var idd = scheduleId;
        logger.log(Level.INFO, "The schedule was deleted: {0} ", scheduleId);
        return ResponseEntity.ok(true);
    }
}

