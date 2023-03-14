package lt.techin.schedule.classrooms;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
public class ClassroomServiceTest {
    @Mock
    private ClassroomRepository classroomRepository;
    @InjectMocks
    private ClassroomService classroomService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetAll() {
        List<Classroom> expectedClassrooms = new ArrayList<>();
        expectedClassrooms.add(new Classroom(1L, "Classroom 1", "Description 1", BuildingType.AKADEMIJA, true));
        expectedClassrooms.add(new Classroom(2L, "Classroom 2", "Description 2", BuildingType.TECHIN, true));
        when(classroomRepository.findAll()).thenReturn(expectedClassrooms);
        List<Classroom> actualClassrooms = classroomService.getAll();
        assertEquals(expectedClassrooms, actualClassrooms);
    }
    @Test
    public void testGetActive() {
        List<Classroom> expectedClassrooms = new ArrayList<>();
        expectedClassrooms.add(new Classroom(1L, "Classroom 1", "Description 1", BuildingType.AKADEMIJA, true));
        expectedClassrooms.add(new Classroom(2L, "Classroom 2", "Description 2", BuildingType.TECHIN, true));
        when(classroomRepository.findAll()).thenReturn(expectedClassrooms);
        List<Classroom> actualClassrooms = classroomService.getActive();
        assertEquals(expectedClassrooms, actualClassrooms);
    }
    @Test
    public void testCreateSuccess() {
        ClassroomDto classroomDto = new ClassroomDto();
        String classroomName = RandomStringUtils.random(10);
        String description = RandomStringUtils.random(10);
        classroomDto.setClassroomName(classroomName);
        classroomDto.setDescription(description);
        classroomDto.setBuilding(BuildingType.AKADEMIJA);
        Classroom classroom = new Classroom();
        classroom.setClassroomName(classroomName);
        classroom.setDescription(description);
        classroom.setBuilding(BuildingType.AKADEMIJA);
        classroom.setActive(true);
        when(classroomRepository.save(classroom)).thenReturn(classroom);
        Classroom actualClassroom = classroomService.create(classroom);
        assertEquals(classroom, actualClassroom);
    }
    @Test
    public void testCreateFail() {
        ClassroomDto classroomDto = new ClassroomDto();
        String classroomName = RandomStringUtils.random(10);
        String description = RandomStringUtils.random(10);
        classroomDto.setClassroomName(classroomName);
        classroomDto.setDescription(description);
        classroomDto.setBuilding(BuildingType.AKADEMIJA);
        Classroom classroom = new Classroom();
        classroom.setClassroomName(classroomName);
        classroom.setDescription(description);
        classroom.setBuilding(BuildingType.AKADEMIJA);
        classroom.setActive(true);
        when(classroomRepository.findByClassroomNameAndBuilding(classroomName, BuildingType.AKADEMIJA))
                .thenReturn(Optional.of(classroom));
        Classroom actualClassroom = classroomService.create(classroom);
        assertNull(actualClassroom);
    }
    @Test
    void testUpdateExistingClassroom() {
        Long id = 1L;
        Classroom classroom = new Classroom();
        classroom.setClassroomName("Classroom 101");
        classroom.setBuilding(BuildingType.AKADEMIJA);
        classroom.setDescription("Description");
        classroom.setActive(true);
        when(classroomRepository.findById(id)).thenReturn(Optional.of(classroom));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);
        Classroom updatedClassroom = classroomService.update(id, classroom);
        assertNotNull(updatedClassroom);
        assertEquals(classroom, updatedClassroom);
    }
    @Test
    void testUpdateNonExistingClassroom() {
        Long id = 1L;
        Classroom classroom = new Classroom();
        classroom.setClassroomName("Classroom 101");
        classroom.setBuilding(BuildingType.AKADEMIJA);
        classroom.setDescription("Description");
        classroom.setActive(true);
        when(classroomRepository.findById(id)).thenReturn(Optional.empty());
        Classroom updatedClassroom = classroomService.update(id, classroom);
        assertNull(updatedClassroom);
    }
    @Test
    void testUpdateClassroomWithSameNameAndBuilding() {
        Long id = 1L;
        Classroom classroom = new Classroom();
        classroom.setClassroomName("Classroom 101");
        classroom.setBuilding(BuildingType.AKADEMIJA);
        classroom.setDescription("Description");
        classroom.setActive(true);
        Classroom existingClassroom = new Classroom();
        existingClassroom.setClassroomName("Classroom 101");
        existingClassroom.setBuilding(BuildingType.AKADEMIJA);
        existingClassroom.setDescription("Description");
        existingClassroom.setActive(true);
        when(classroomRepository.findById(id)).thenReturn(Optional.of(existingClassroom));
        Classroom updatedClassroom = classroomService.update(id, classroom);
//        assertNotNull(updatedClassroom);
//        assertEquals(existingClassroom, updatedClassroom);
    }

    @Test
    void testUpdateClassroomWithDifferentNameAndSameBuilding() {
        Long id = 1L;
        Classroom classroom = new Classroom();
        classroom.setClassroomName("New Classroom");
        classroom.setBuilding(BuildingType.AKADEMIJA);
        classroom.setDescription("Description");
        classroom.setActive(true);
        Classroom existingClassroom = new Classroom();
        existingClassroom.setClassroomName("Classroom 101");
        existingClassroom.setBuilding(BuildingType.AKADEMIJA);
        existingClassroom.setDescription("Description");
        existingClassroom.setActive(true);
        when(classroomRepository.findById(id)).thenReturn(Optional.of(existingClassroom));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);
        Classroom updatedClassroom = classroomService.update(id, classroom);
        assertNotNull(updatedClassroom);
        assertEquals(classroom, updatedClassroom);
    }

    @Test
    public void testFindById() {
        Classroom classroom = new Classroom();
        classroom.setId(1L);
        classroom.setClassroomName("Room A");
        classroom.setDescription("Classroom A");
        classroom.setBuilding(BuildingType.AKADEMIJA);
        classroom.setActive(true);
        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));
        Classroom foundClassroom = classroomService.finById(1L);
        assertEquals("Room A", foundClassroom.getClassroomName());
        assertEquals("Classroom A", foundClassroom.getDescription());
        assertEquals(BuildingType.AKADEMIJA, foundClassroom.getBuilding());
        assertEquals(true, foundClassroom.isActive());
    }

//    @Test
//    public void testDisable() {
//        Classroom classroom = new Classroom();
//        classroom.setId(1L);
//        classroom.setClassroomName("Room A");
//        classroom.setDescription("Classroom A");
//        classroom.setBuilding(BuildingType.AKADEMIJA);
//        classroom.setActive(true);
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));
//        Classroom disabledClassroom = classroomService.disable(1L);
//        Assertions.assertFalse(disabledClassroom.isActive());
//    }
//
//
//    @Test
//    public void testEnable() {
//        Classroom classroom = new Classroom();
//        classroom.setId(1L);
//        classroom.setClassroomName("Room A");
//        classroom.setDescription("Classroom A");
//        classroom.setBuilding(BuildingType.AKADEMIJA);
//        classroom.setActive(false);
//        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));
//        Classroom enabledClassroom = classroomService.enable(1L);
//        assertEquals(true, enabledClassroom.isActive());
//    }

//    @Test
//    void testFindByClassroomNameAndBuilding() {
//        List<Classroom> classrooms = new ArrayList<>();
//        classrooms.add(new Classroom(1L, "Classroom 1", "Description 1", BuildingType.AKADEMIJA, true));
//        classrooms.add(new Classroom(2L, "Classroom 2", "Description 2", BuildingType.AKADEMIJA, true));
//        classrooms.add(new Classroom(3L, "Classroom 3", "Description 3", BuildingType.TECHIN, true));
//        when(classroomRepository.findAll()).thenReturn(classrooms);
//        boolean result = classroomService.findByClassroomNameAndBuilding("Classroom 2", BuildingType.TECHIN);
//        Assertions.assertTrue(result);
//        result = classroomService.findByClassroomNameAndBuilding(null, BuildingType.TECHIN);
//        Assertions.assertFalse(result);
//    }

}
