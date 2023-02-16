package lt.techin.schedule.shifts;

import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftController;
import lt.techin.schedule.shift.ShiftService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ShiftControllerTests {
    @MockBean
    ShiftService shiftService;

    @InjectMocks
    public ShiftController shiftController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetActiveShifts() throws Exception {
        Shift shift1 = new Shift("Shift 1", "8:00", "16:00", true, 1, 8);
        Shift shift2 = new Shift("Shift 2", "9:00", "17:00", true, 2, 9);
        shift1.setId(1L);
        shift2.setId(2L);

        List<Shift> expectedShifts = List.of(shift1, shift2);
        when(shiftService.getActiveShifts()).thenReturn(expectedShifts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shift/get-active")).andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shift/get-active"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\": 1,\"name\": \"Shift 1\",\"shiftStartingTime\": \"8:00\",\"shiftEndingTime\": \"16:00\",\"startIntEnum\": 1, \"endIntEnum\": 8, \"isActive\": true}," +
                        "{\"id\": 2,\"name\": \"Shift 2\",\"shiftStartingTime\": \"9:00\",\"shiftEndingTime\": \"17:00\",\"startIntEnum\": 2,\"endIntEnum\": 9,\"isActive\": true}" +
                        "]"));
    }

    @Test
    public void testGetInactiveShifts() throws Exception {
        Shift shift1 = new Shift("Shift 1", "8:00", "16:00", false, 1, 8);
        Shift shift2 = new Shift("Shift 2", "9:00", "17:00", false, 2, 9);
        shift1.setId(1L);
        shift2.setId(2L);

        List<Shift> expectedShifts = List.of(shift1, shift2);
        when(shiftService.getInactiveShifts()).thenReturn(expectedShifts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shift/get-inactive")).andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shift/get-inactive"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\": 1,\"name\": \"Shift 1\",\"shiftStartingTime\": \"8:00\",\"shiftEndingTime\": \"16:00\",\"startIntEnum\": 1, \"endIntEnum\": 8, \"isActive\": false}," +
                        "{\"id\": 2,\"name\": \"Shift 2\",\"shiftStartingTime\": \"9:00\",\"shiftEndingTime\": \"17:00\",\"startIntEnum\": 2,\"endIntEnum\": 9,\"isActive\": false}" +
                        "]"));
    }

    @Test
    public void testActivateShift() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/shift/activate-shift/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeactivateShift() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/shift/deactivate-shift/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddShift() throws Exception {
        Shift shiftToAdd = new Shift("New Shift", "8:00", "16:00", true, 1, 8);
        shiftToAdd.setId(1L);
        when(shiftService.addUniqueShift(shiftToAdd)).thenReturn("");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shift/add-shift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"New Shift\",\"shiftStartingTime\": \"8:00\",\"shiftEndingTime\": \"16:00\",\"startIntEnum\": 1, \"endIntEnum\": 8,\"isActive\": true}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @Test
    public void testModifyShift() throws Exception {
        Shift shiftToChange = new Shift("Updated Shift", "8:00", "16:00", true, 1, 8);
        shiftToChange.setId(1L);
        when(shiftService.modifyExistingShift(1L, shiftToChange)).thenReturn("");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/shift/modify-shift/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Updated Shift\",\"shiftStartingTime\": \"8:00\",\"shiftEndingTime\": \"16:00\",\"startIntEnum\": 1, \"endIntEnum\": 8,\"isActive\": true}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }
}
