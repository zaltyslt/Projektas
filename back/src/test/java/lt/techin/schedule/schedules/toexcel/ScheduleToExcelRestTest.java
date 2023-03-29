//package lt.techin.schedule.schedules.toexcel;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ScheduleToExcelRestTest {
//    @InjectMocks
//    ScheduleToExcelController employeeController;
//
//    @Mock
//    ScheduleToExcelService excelService;
//
//    @Test
//    public void acceptGetRequestTest() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//        when(excelService.toExcel(1L, true)).thenReturn(".xlsx");
//    }
//}
