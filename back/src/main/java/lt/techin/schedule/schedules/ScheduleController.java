package lt.techin.schedule.schedules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleDto;
import static lt.techin.schedule.schedules.ScheduleMapper.toScheduleFromCreateDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    Logger logger = LoggerFactory.getLogger(ScheduleController.class);
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
        logger.info("The schedule was disabled: {} ", scheduleId);
        return toScheduleDto(disableSchedule);
    }

    @PatchMapping("/enable-schedule/{scheduleId}")
    public ScheduleDto enableSchedule(@PathVariable Long scheduleId) {
        var enableSchedule = scheduleService.enable(scheduleId);
        logger.info("The schedule was enabled: {}", scheduleId);
        return toScheduleDto(enableSchedule);
    }

    @DeleteMapping("/delete-schedule/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        boolean result = scheduleService.deleteSchedule(scheduleId);
        logger.info("The schedule id {} was {} deleted: {}", scheduleId, result ? "" : "not", result);
        return result
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
}

