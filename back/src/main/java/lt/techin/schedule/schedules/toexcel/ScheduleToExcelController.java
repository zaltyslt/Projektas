package lt.techin.schedule.schedules.toexcel;

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

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1/schedules/excel", consumes = {MediaType.APPLICATION_JSON_VALUE})
public class ScheduleToExcelController {
    private final ScheduleToExcelService excelService;
    private final static Logger logger = LoggerFactory.getLogger(ScheduleToExcelController.class);

    public ScheduleToExcelController(ScheduleToExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping
    public ResponseEntity<byte[]> saveToExcel(@RequestParam("id") Long id, @RequestParam("p") boolean paged) throws IOException {
        byte[] workBook = excelService.toExcel(id, paged);

        if (workBook.length > 0) {
            // Set the headers for the response
            logger.info("Workbook data created succesfully.");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            headers.setContentLength(workBook.length);
            headers.setContentDispositionFormData("attachment", "workBook.xlsx");
            // Return a 200 OK
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(workBook);
        } else {
            logger.info("Workbook data creation fail.");
            // Return a 404 Not Found response if the file does not exist
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
