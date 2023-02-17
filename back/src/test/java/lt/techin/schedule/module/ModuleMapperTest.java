package lt.techin.schedule.module;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuleMapperTest {

    @Test
    public void testToModuleDto() {
        // Create a sample Module object
        Module module = new Module();
        module.setNumber("M001");
        module.setName("Module 1");

        // Convert the Module object to a ModuleDto object
        ModuleDto moduleDto = ModuleMapper.toModuleDto(module);

        // Verify that the ModuleDto object has the correct values
        assertEquals("M001", moduleDto.getNumber());
        assertEquals("Module 1", moduleDto.getName());
    }

    @Test
    public void testToModuleEntityDto() {
        // Create a sample Module object
        Module module = new Module();
        module.setId(1L);
        module.setNumber("M001");
        module.setName("Module 1");

        // Convert the Module object to a ModuleEntityDto object
        ModuleEntityDto moduleEntityDto = ModuleMapper.toModuleEntityDto(module);

        // Verify that the ModuleEntityDto object has the correct values
        assertEquals(1L, moduleEntityDto.getId());
        assertEquals("M001", moduleEntityDto.getNumber());
        assertEquals("Module 1", moduleEntityDto.getName());
    }

    @Test
    public void testToModule() {
        // Create a sample ModuleDto object
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setNumber("M001");
        moduleDto.setName("Module 1");

        // Convert the ModuleDto object to a Module object
        Module module = ModuleMapper.toModule(moduleDto);

        // Verify that the Module object has the correct values
        assertEquals("M001", module.getNumber());
        assertEquals("Module 1", module.getName());
    }

}