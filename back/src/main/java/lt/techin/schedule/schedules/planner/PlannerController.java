package lt.techin.schedule.schedules.planner;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lt.techin.schedule.schedules.planner.WorkDayMapper.toWorkDayDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/schedules")
public class PlannerController {

    Logger logger = Logger.getLogger(PlannerController.class.getName());

    private final PlannerService plannerService;

    public PlannerController(PlannerService plannerService) {
        this.plannerService = plannerService;
    }

    @PutMapping("/plan-schedule/{scheduleId}")
    public ResponseEntity<Boolean> planSchedule(@PathVariable Long scheduleId, @RequestParam Long subjectId, @RequestBody PlannerDto plannerDto) {
        Boolean createdDay = plannerService.addSubjectPlanToSchedule(scheduleId, subjectId, plannerDto);
        logger.log(Level.INFO, "The lessons for schedule {0} were created", scheduleId);
        return ok(createdDay);
    }

    @GetMapping("/{scheduleId}/lessons")
    public List<WorkDayDto> getWorkDays(@PathVariable Long scheduleId) {
        return plannerService.getWorkDays(scheduleId).stream().map(WorkDayMapper::toWorkDayDto).toList();
    }

    @GetMapping("/lesson/{workDayId}")
    public ResponseEntity<WorkDayDto> getWorkDay(@PathVariable Long workDayId) {
        Optional<WorkDay> workDay = plannerService.getWorkDay(workDayId);
        return workDay.map(day -> ok(toWorkDayDto(day))).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PatchMapping("/edit-lesson/{workDayId}")
    public ResponseEntity<WorkDayDto> updateWorkDay(@PathVariable Long workDayId, @RequestBody WorkDayDto workDayDto) {
       WorkDay updatedWorkDay = plannerService.updateWorkDay(workDayId, workDayDto);
       WorkDayDto updatedWorkDayDto = toWorkDayDto(updatedWorkDay);
       return ok(updatedWorkDayDto);
    }
}
