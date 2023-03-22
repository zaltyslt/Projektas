package lt.techin.schedule.schedules.toexcel;

import lt.techin.schedule.schedules.planner.PlannerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/schedules/excel", consumes = {MediaType.APPLICATION_JSON_VALUE})
public class ScheduleToExcelController {

    private final PlannerService plannerService;
    private final ScheduleToExcelService excelService;
    private final static Logger logger = LoggerFactory.getLogger(ScheduleToExcelController.class);


    public ScheduleToExcelController(PlannerService plannerService, ScheduleToExcelService excelService) {
        this.plannerService = plannerService;
        this.excelService = excelService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> saveToExcel(@RequestParam Long id) {
       excelService.toExcel(id);

        return ResponseEntity.ok().build();
    }

}
