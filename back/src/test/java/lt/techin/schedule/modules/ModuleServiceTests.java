package lt.techin.schedule.modules;

import lt.techin.schedule.module.Module;
import lt.techin.schedule.module.ModuleRepository;
import lt.techin.schedule.module.ModuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ModuleServiceTests {

    @Mock
    private ModuleRepository moduleDatabase;

    @InjectMocks
    private ModuleService moduleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUniqueModuleWithUniqueName() {
        Module module = new Module(1L, "10", "Module", LocalDateTime.now(), LocalDateTime.now(), false);
        String result = moduleService.create(module);
        assertEquals("", result);
    }

    @Test
    public void testAddUniqueModuleWithDuplicateName() {
        Module module1 = new Module(1L, "100", "Module1", LocalDateTime.now(), LocalDateTime.now(), false);
        Module module2 = new Module(2L, "100", "Module2", LocalDateTime.now(), LocalDateTime.now(), true);

        when(moduleDatabase.findAll()).thenReturn(List.of(module1));

        String result = moduleService.create(module2);
        assertEquals("Modulio numeris turi būti unikalus.", result);
    }

    @Test
    public void testGetActiveShifts() {
        Module module1 = new Module(1L, "100", "Module1", LocalDateTime.now(), LocalDateTime.now(), false);
        Module module2 = new Module(2L, "100", "Module2", LocalDateTime.now(), LocalDateTime.now(), true);

        when(moduleDatabase.findAll()).thenReturn(Arrays.asList(module1, module2));

        List<Module> activeModules = moduleService.getAll();

        assertEquals(List.of(module1), activeModules);
    }

    @Test
    public void testGetInactiveShifts() {
        Module module1 = new Module(1L, "100", "Module1", LocalDateTime.now(), LocalDateTime.now(), false);
        Module module2 = new Module(2L, "100", "Module2", LocalDateTime.now(), LocalDateTime.now(), true);

        when(moduleDatabase.findAll()).thenReturn(Arrays.asList(module1, module2));

        List<Module> inactiveModules = moduleService.getAllDeleted();

        assertEquals(List.of(module2), inactiveModules);
    }

    @Test
    public void testGetShiftByID() {
        Module module = new Module(1L, "100", "Module1", LocalDateTime.now(), LocalDateTime.now(), false);

        when(moduleDatabase.findById(1L)).thenReturn(Optional.of(module));

        Optional<Module> foundModule = moduleService.getById(1L);

        foundModule.ifPresent(value -> assertEquals(module, value));
    }

    @Test
    public void testModifyModuleWithUniqueNumber() {
        Module module1 = new Module(1L, "100", "Module1", LocalDateTime.now(), LocalDateTime.now(), false);
        Module module2 = new Module(2L, "1000", "Module2", LocalDateTime.now(), LocalDateTime.now(), true);
        Module module3 = new Module(2L, "1000", "Module3", LocalDateTime.now(), LocalDateTime.now(), true);

        when(moduleDatabase.findById(2L)).thenReturn(Optional.of(module2));
        when(moduleDatabase.findAll()).thenReturn(Arrays.asList(module1, module2));

        String result = moduleService.updateModule(2L, module3);
        assertEquals("", result);
    }

    @Test
    public void testModifyModuleWithDuplicateNumber() {
        Module module1 = new Module(1L, "1000", "Module1", LocalDateTime.now(), LocalDateTime.now(), false);
        Module module2 = new Module(2L, "100", "Module2", LocalDateTime.now(), LocalDateTime.now(), true);
        Module module3 = new Module(2L, "1000", "Module3", LocalDateTime.now(), LocalDateTime.now(), true);

        when(moduleDatabase.findById(2L)).thenReturn(Optional.of(module2));
        when(moduleDatabase.findAll()).thenReturn(Arrays.asList(module1, module2));

        String result = moduleService.updateModule(2L, module3);
        assertEquals("Modulio numeris turi būti unikalus.", result);
    }
}
