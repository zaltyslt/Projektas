package lt.techin.schedule.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.schedule.module.Module;
import lt.techin.schedule.module.ModuleDto;
import lt.techin.schedule.module.ModuleMapper;
import lt.techin.schedule.module.ModuleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ModuleControllerTests {

    @MockBean
    ModuleService moduleService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetActiveModules() throws Exception {
        Module module1 = new Module(1L, "10AS", "Module 1",false);
        Module module2 = new Module(2L, "20AS", "Module 2",false);
        List<Module> expectedModules = List.of(module1, module2);
        when(moduleService.getAll()).thenReturn(expectedModules);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/modules"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{\"id\": 1,\"number\": \"10AS\",\"name\": \"Module 1\"}," +
                        "{\"id\": 2,\"number\": \"20AS\",\"name\": \"Module 2\"}" +
                        "]"));
    }

    @Test
    public void testGetInactiveModules() throws Exception {
        Module module1 = new Module(1L, "10AS", "Module 1",true);
        Module module2 = new Module(2L, "20AS", "Module 2",true);
        List<Module> expectedModules = List.of(module1, module2);
        when(moduleService.getAllDeleted()).thenReturn(expectedModules);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/modules/deleted"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{\"id\": 1,\"number\": \"10AS\",\"name\": \"Module 1\"}," +
                        "{\"id\": 2,\"number\": \"20AS\",\"name\": \"Module 2\"}" +
                        "]"));
    }

    @Test
    public void testActivateModule() throws Exception {
        Module module = new Module(1L, "10AS", "Module 1",true);
        when(moduleService.restoreModule(1L)).thenReturn(module);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/modules/restore/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"number\": \"10AS\",\"name\": \"Module 1\"}"));
    }

    @Test
    public void testDeactivateModule() throws Exception {
        Module module = new Module(1L, "10AS", "Module 1",false);
        when(moduleService.delete(1L)).thenReturn(module);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/modules/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"number\": \"10AS\",\"name\": \"Module 1\"}"));;
    }

    @Test
    void testAddModule() throws Exception {
        Module moduleToAdd = new Module(1L, "10AS", "Module 1",false);
        ModuleDto moduleDto = ModuleMapper.toModuleDto(moduleToAdd);
        String response = "";
        when(moduleService.create(moduleToAdd)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/modules/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(moduleDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void testModifyModule() throws Exception {
        Module moduleToModify = new Module(1L, "10AS", "Module 1",false);
        ModuleDto moduleDto = ModuleMapper.toModuleDto(moduleToModify);
        String response = "";
        when(moduleService.updateModule(1L, moduleToModify)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/modules/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(moduleDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}
