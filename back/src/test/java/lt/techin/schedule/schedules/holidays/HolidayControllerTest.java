package lt.techin.schedule.schedules.holidays;

import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.shift.ShiftService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//@SqlGroup({
//        @Sql(value = "classpath:init/schedule.sql", executionPhase = BEFORE_TEST_METHOD)
//})
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@SpringBootTest
@AutoConfigureMockMvc
public class HolidayControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @BeforeEach
//    public void setUp() {
//        Schedule testSchedule = new Schedule();
//        testSchedule.setId(1L);
//    }

    @MockBean
    HolidayService holidayService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetHolidaysController() throws Exception {

    }

    @Test
    void testGetHolidayController() throws Exception {

    }

    @Test
    void testCreateHolidayController() throws Exception {

    }
}
