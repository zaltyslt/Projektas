package lt.techin.schedule.schedules.planner;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String createdString = plannerService.addSubjectPlanToSchedule(scheduleId, subjectId, plannerDto);
        if (createdString.isEmpty()) {
            logger.log(Level.INFO, "Creating lessons plan for a schedule " + scheduleId + " was successful", scheduleId);
            return ok(true);
        }
        else {
            logger.log(Level.INFO, "Creating lessons plan for a schedule " + scheduleId + " was unsuccessful", scheduleId);
            return ok(false);
        }

    }

    @GetMapping("/{scheduleId}/lessons")
    public List<WorkDayDto> getWorkDays(@PathVariable Long scheduleId) {
        return plannerService.getWorkDays(scheduleId).stream().map(WorkDayMapper::toWorkDayDto).toList();
    }
}
