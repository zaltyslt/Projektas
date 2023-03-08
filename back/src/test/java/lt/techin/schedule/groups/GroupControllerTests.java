package lt.techin.schedule.groups;

import lt.techin.schedule.group.Group;
import lt.techin.schedule.group.GroupController;
import lt.techin.schedule.group.GroupService;
import lt.techin.schedule.programs.Program;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftController;
import lt.techin.schedule.shift.ShiftService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerTests {

    @MockBean
    GroupService groupService;

    @InjectMocks
    public GroupController groupController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetActiveShifts() throws Exception {
        Program program = new Program();
        program.setProgramName("Program");
        program.setDescription("Description");
        program.setActive(true);

        Group group1 = new Group(1L, "Group1", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        Group group2 = new Group(2L, "Group2", "2020", 10, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        List<Group> expectedGroups = List.of(group1, group2);
        when(groupService.getActiveGroups()).thenReturn(expectedGroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/group/get-active"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{\"id\":1,\"name\":\"Group1\",\"schoolYear\":\"2018\",\"studentAmount\":15,\"isActive\":true}," +
                        "{\"id\":2,\"name\":\"Group2\",\"schoolYear\":\"2020\",\"studentAmount\":10,\"isActive\":true}" +
                        "]"));
    }

    @Test
    public void testGetInactiveShifts() throws Exception {
        Program program = new Program();
        program.setProgramName("Program");
        program.setDescription("Description");
        program.setActive(true);

        Group group1 = new Group(1L, "Group1", "2018", 15, false, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        Group group2 = new Group(2L, "Group2", "2020", 10, false, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        List<Group> expectedGroups = List.of(group1, group2);
        when(groupService.getInactiveGroups()).thenReturn(expectedGroups);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/group/get-inactive")).andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/group/get-inactive"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{\"id\":1,\"name\":\"Group1\",\"schoolYear\":\"2018\",\"studentAmount\":15,\"isActive\":false}," +
                        "{\"id\":2,\"name\":\"Group2\",\"schoolYear\":\"2020\",\"studentAmount\":10,\"isActive\":false}" +
                        "]"));
    }
}
