package lt.techin.schedule.groups;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.schedule.group.Group;
import lt.techin.schedule.group.GroupDto;
import lt.techin.schedule.group.GroupMapper;
import lt.techin.schedule.group.GroupService;
import lt.techin.schedule.programs.Program;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.subject.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerTests {
    @MockBean
    GroupService groupService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SubjectRepository subjectRepository;

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
        verify(groupService, times(1)).getActiveGroups();
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

    @Test
    public void testActivateGroup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/group/activate-group/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeactivateGroup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/group/deactivate-group/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddSGroup() throws Exception {
        Group groupToAdd = new Group(1L, "Group1", "2018", 15, false, new Program(),
                new Shift());
        GroupDto groupDto = GroupMapper.groupToDto(groupToAdd);
        String response = "";
        when(groupService.addUniqueGroup(groupDto)).thenReturn(response);
        mockMvc.perform(post("/api/v1/group/add-group")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(groupDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void testModifyGroup() throws Exception {
        Group groupToAdd = new Group(1L, "Group1", "2018", 15, false, new Program(),
                new Shift());
        GroupDto groupDto = GroupMapper.groupToDto(groupToAdd);
        when(groupService.modifyExistingGroup(1L, groupDto)).thenReturn("");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/group/modify-group/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(groupDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}
