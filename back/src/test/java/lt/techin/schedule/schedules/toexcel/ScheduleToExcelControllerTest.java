//package lt.techin.schedule.schedules.toexcel;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//class ScheduleToExcelControllerTest {
//    @Mock
//    private ScheduleToExcelService excelService;
//
//    @BeforeEach
//    public void removeFile() throws IOException {
//        String fileName = "test.xlsx";
//        Path path = Paths.get(fileName);
//        if (Files.exists(path)) {
//            Files.delete(path);
//        }
//    }
//
//    @Test
//    void saveToExcel_shouldReturnExcelFile() throws IOException {
//        long id = 123L;
//        boolean paged = true;
//        String fileName = "test.xlsx";
//        Path path = Paths.get(fileName);
//        byte[] data = "Some test data".getBytes();
//        try (FileOutputStream fileOutputStream = new FileOutputStream(path.toFile())) {
//            fileOutputStream.write(data);
//        }
//        byte[] fileContent = Files.readAllBytes(new File(fileName).toPath());
//        when(excelService.toExcel(id, paged)).thenReturn(fileName);
//        ScheduleToExcelController scheduleToExcelController = new ScheduleToExcelController(excelService);
//        ResponseEntity<byte[]> response = scheduleToExcelController.saveToExcel(id, paged);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), response.getHeaders().getContentType());
//        assertEquals(fileContent.length, response.getHeaders().getContentLength());
//        assertTrue(response.getHeaders().getCacheControl().contains("must-revalidate"));
//        assertTrue(response.getHeaders().getContentDisposition().toString().contains("attachment"));
//        assertArrayEquals(fileContent, response.getBody());
//    }
//
//    @Test
//    void saveToExcel_shouldReturnNotFound() throws IOException {
//        long id = 123L;
//        boolean paged = true;
//        String fileName = "test.xlsx";
//        when(excelService.toExcel(id, paged)).thenReturn(fileName);
//        ScheduleToExcelController scheduleToExcelController = new ScheduleToExcelController(excelService);
//        ResponseEntity<byte[]> response = scheduleToExcelController.saveToExcel(id, paged);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//}