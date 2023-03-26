package lt.techin.schedule.schedules;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ScheduleControllerTest {
    @MockBean
    ScheduleService scheduleService;

    @InjectMocks
    ScheduleController scheduleController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetSchedulesController() throws Exception {
        mvc.perform(get("/api/v1/schedules")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetScheduleController() throws Exception {
        mvc.perform(get("/api/v1/schedules/schedule/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateScheduleController() throws Exception {
        String requestBody = "{\"schoolYear\":\"2022-2023\",\"semester\":\"spring\"," +
                "\"dateFrom\":\"2023-02-01\",\"dateUntil\":\"2023-06-30\"}";
        Long groupId = 1L;
        mvc.perform(post("/api/v1/schedules/create-schedule")
                        .param("groupId", groupId.toString())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDisableScheduleController() throws Exception {
        mvc.perform(patch("/api/v1/schedules/disable-schedule/{scheduleId}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testEnableScheduleController() throws Exception {
        mvc.perform(patch("/api/v1/schedules/enable-schedule/{scheduleId}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteSchedule() throws Exception {
        when(scheduleService.deleteSchedule(1L)).thenReturn(true);
        when(scheduleService.deleteSchedule(2L)).thenReturn(false);
        mockMvc.perform(delete("/api/v1/schedules/delete-schedule/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/api/v1/schedules/delete-schedule/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}