package lt.techin.schedule.schedules.toexcel;

import lt.techin.schedule.schedules.planner.PlannerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

//    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @GetMapping
    public ResponseEntity<byte[]> saveToExcel(@RequestParam Long id) throws IOException {
      String fileName = excelService.toExcel(id);

      File file = new File(fileName);
        // Check if the file exists
        if (file.exists()) {
            // Set the headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(file.length());
            headers.setContentDispositionFormData("attachment", fileName);

            // Convert the file to a byte array
            byte[] fileContent = Files.readAllBytes(file.toPath());

            // Return the response entity with the file as the body
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(fileContent);
        } else {
            // Return a 404 Not Found response if the file does not exist
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
