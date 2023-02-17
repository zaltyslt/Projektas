package lt.techin.schedule.module;

public class ModuleServiceTest {

//    import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Sort;
//
//import lt.techin.schedule.module.Module;
//import lt.techin.schedule.module.ModuleRepository;

//        private ModuleRepository moduleRepository = Mockito.mock(ModuleRepository.class);
//
//        private ModuleService moduleService;
//
//        @BeforeEach
//        void setUp() {
//            moduleService = new ModuleService(moduleRepository);
//        }
//
//        @Test
//        void testGetAll() {
//            Mockito.when(moduleRepository.findAll()).thenReturn(List.of(new Module(1L, "Module 1", "Module", true), new Module("Module 2"));
//            List<Module> modules = moduleService.getAll(false);
//            assertEquals(2, modules.size());
//        }
//
//        @Test
//        void testGetById() {
//            Mockito.when(moduleRepository.findById(1L)).thenReturn(Optional.of(new Module("Module 1")));
//            Optional<Module> module = moduleService.getById(1L);
//            assertTrue(module.isPresent());
//            assertEquals("Module 1", module.get().getName());
//        }
//
//        @Test
//        void testCreate() {
//            Module module = new Module(null, "Modulekod 1", "Modulepav", LocalDateTime.now(), LocalDateTime.now(), true);
//            Mockito.when(moduleRepository.save(module)).thenReturn( new Module(1L, "Modulekod 1", "Modulepav", LocalDateTime.now(), LocalDateTime.now(), true));
//            Module savedModule = moduleService.create(module);
//            assertNotNull(savedModule);
//            assertEquals("Module 1", savedModule.getName());
//        }
//
//        @Test
//        void testUpdateModule() {
//            Module existingModule = new Module("Module 1");
//            existingModule.setId(1L);
//            Module updatedModule = new Module("Module 2");
//            updatedModule.setId(1L);
//
//            Mockito.when(moduleRepository.findById(1L)).thenReturn(Optional.of(existingModule));
//            Mockito.when(moduleRepository.save(existingModule)).thenReturn(updatedModule);
//
//            Module savedModule = moduleService.updateModule(1L, updatedModule);
//            assertNotNull(savedModule);
//            assertEquals("Module 2", savedModule.getName());
//        }
//
//        @Test
//        void testRestoreModule() {
//            Module module = new Module("Module 1");
//            module.setId(1L);
//            module.setDeleted(true);
//
//            Mockito.when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));
//            Mockito.when(moduleRepository.save(module)).thenReturn(module);
//
//            Module restoredModule = moduleService.restoreModule(1L);
//            assertNotNull(restoredModule);
//            assertFalse(restoredModule.isDeleted());
//        }
//
//        @Test
//        void testDeleteById() {
//            Mockito.doNothing().when(moduleRepository).deleteById(1L);
//            boolean deleted = moduleService.deleteById(1L);
//            assertTrue(deleted);
//
//            Mockito.doThrow(EmptyResultDataAccessException.class).when(moduleRepository).deleteById(2L);
//            boolean notDeleted = moduleService.deleteById(2L);
//            assertFalse(notDeleted);
//        }
//
//        @Test
//        void testFindAllPaged() {
//            Mockito.when(moduleRepository.findAll(PageRequest.of(0, 2, Sort.Direction.DESC, "name"))).thenReturn(Page.empty());
//            Page<Module> modules = moduleService.findAllPaged(0, 2, false);
//            assertNotNull(modules);
//        }

    }



