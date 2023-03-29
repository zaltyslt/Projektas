//package lt.techin.schedule.schedules.toexcel;
//
//import lt.techin.schedule.schedules.planner.PlannerService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//
//@RestController
//@RequestMapping(value = "/api/v1/schedules/excel", consumes = {MediaType.APPLICATION_JSON_VALUE})
//public class ScheduleToExcelController {
//    private final PlannerService plannerService;
//    private final ScheduleToExcelService excelService;
//    private final static Logger logger = LoggerFactory.getLogger(ScheduleToExcelController.class);
//
//    public ScheduleToExcelController(PlannerService plannerService, ScheduleToExcelService excelService) {
//        this.plannerService = plannerService;
//        this.excelService = excelService;
//    }
//
//    @GetMapping
//    public ResponseEntity<byte[]> saveToExcel(@RequestParam("id")
//                                              Long id, @RequestParam("p") boolean paged) throws IOException {
//        excelService.cleaner();
//        String fileName = excelService.toExcel(id, paged);
//        File file = new File(fileName);
//        if (file.exists()) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType(
//                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//            headers.setContentLength(file.length());
//            headers.setContentDispositionFormData("attachment", fileName);
//            byte[] fileContent = Files.readAllBytes(file.toPath());
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .headers(headers)
//                    .body(fileContent);
//        } else {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .build();
//        }
//    }
//}
