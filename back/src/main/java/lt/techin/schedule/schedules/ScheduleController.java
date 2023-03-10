package lt.techin.schedule.schedules;

import jakarta.validation.Valid;
import lt.techin.schedule.shift.ShiftDto;
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
            logger.info("Get All Schedule List");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Get All Schedule List");
        return ok(toScheduleDto(scheduleService.findById(id)));
    }

    @PostMapping(value = "/create-schedule", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScheduleDto> create(@RequestParam Long groupId, @RequestBody ScheduleCreateDto scheduleCreateDto) {
        var createdSchedule = scheduleService.createSchedule(toScheduleFromCreateDto(scheduleCreateDto), groupId);
        logger.info("Get All Schedule List");
        return ok(toScheduleDto(createdSchedule));
    }

    @PatchMapping("/disable-schedule/{scheduleId}")
    public ScheduleDto disableSchedule(@PathVariable Long scheduleId) {
        var disableSchedule = scheduleService.disable(scheduleId);
        logger.log(Level.INFO, "The schedule was disable: {0} ", scheduleId);
        return toScheduleDto(disableSchedule);
    }

    @PatchMapping("/enable-schedule/{scheduleId}")
    public ScheduleDto enableSchedule(@PathVariable Long scheduleId) {
        var enableSchedule = scheduleService.enable(scheduleId);
        logger.log(Level.INFO, "The schedule was enabled: {0} ", scheduleId);
        return toScheduleDto(enableSchedule);
    }

    @PutMapping("/plan-schedule/{scheduleId}")
    public void planSchedule(@PathVariable Long scheduleId, @RequestParam Long subjectId, @RequestBody PlannerDto plannerDto) {
//        System.out.println(scheduleId + " " + plannerDto.getAssignedHours() + " " + plannerDto.getEndIntEnum() + " " + plannerDto.getStartIntEnum() + " " +
//                " " + plannerDto.getDateFrom() + " " +  plannerDto.getDateUntil() + " " + plannerDto.getClassroom() +
//                plannerDto.getTeacher() + " " + subjectId);
        scheduleService.addSubjectPlanToSchedule(scheduleId, subjectId, plannerDto);
    }
}

