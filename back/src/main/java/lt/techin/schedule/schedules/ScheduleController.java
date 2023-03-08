package lt.techin.schedule.schedules;

import lt.techin.schedule.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static lt.techin.schedule.schedules.ScheduleMapper.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    Logger logger = Logger.getLogger(ScheduleController.class.getName());
    private final ScheduleService scheduleService;
    @Autowired
    private GroupService groupService;

    public ScheduleController(ScheduleService scheduleService, GroupService groupService) {
        this.scheduleService = scheduleService;
        this.groupService = groupService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ScheduleDto> getScheduleList() {
        logger.info("Get All Schedule List");

//        var schedule = new Schedule();
//        var groups = new Group();
//        groupService.addGroup(groups);
//        schedule.setGroups(groups);
//        var createdschedule = scheduleService.create(schedule);
//
//        List<ScheduleDto> scheduleDtos = new ArrayList<>();
//        scheduleDtos.add(toScheduleDto(createdschedule));
//        return scheduleDtos;

        return scheduleService.getAll().stream().map(ScheduleMapper::toScheduleDto).toList();
    }

    @GetMapping(value = "/schedule/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable Long id) {
        var schedule = scheduleService.findById(id);
        if (schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ok(toScheduleDto(scheduleService.findById(id)));
    }

    @PostMapping(value = "/create-schedule", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, String>> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        var createSchedule = scheduleService.create(toSchedule(scheduleDto));
        logger.info("The schedule was created, successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", (toScheduleDto(createSchedule).toString())));
    }

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScheduleDto> create(@RequestParam Long groupId, @RequestBody ScheduleCreateDto scheduleCreateDto) {
        var createdSchedule = scheduleService.createSchedule(toScheduleFromCreateDto(scheduleCreateDto), groupId);
        return ok(toScheduleDto(createdSchedule));
    }
}

