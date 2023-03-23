package lt.techin.schedule.schedules;

import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.subject.SubjectController;
import lt.techin.schedule.subject.SubjectService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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