package lt.techin.schedule.classrooms;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lt.techin.schedule.classrooms.ClassroomMapper.toClassroom;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClassroomServiceTest {
    @Mock
    private ClassroomRepository classroomRepository;

    @InjectMocks
    private ClassroomService classroomService;

    @Test
    public void testGetAll() {
        List<Classroom> classroomList = new ArrayList<>();
        classroomList.add(new Classroom(1L, "Classroom 1", "Description 1", true));
        classroomList.add(new Classroom(2L, "Classroom 2", "Description 2", true));
        when(classroomRepository.findAll()).thenReturn(classroomList);

        List<Classroom> result = classroomService.getAll();

        assertEquals(2, result.size());
        assertEquals("Classroom 1", result.get(0).getClassroomName());
        assertEquals("Classroom 2", result.get(1).getClassroomName());
    }

    @Test
    public void testCreate() {
        Classroom classroom = new Classroom(
                null, "New Classroom", "New Description", true);
        when(classroomRepository.save(classroom))
                .thenReturn(new Classroom(
                        1L, "New Classroom", "New Description", true));

        classroom.setBuilding(BuildingType.AKADEMIJA);
        Classroom result = classroomService.create(classroom);

        assertEquals(1L, result.getId());
        assertEquals("New Classroom", result.getClassroomName());
        assertEquals("New Description", result.getDescription());
        assertTrue(result.isActive());
        assertNull(result.getBuilding());
    }

    @Test
    void create_shouldCreateNewClassroom() {

        Classroom classroom = new Classroom(
                null, "New Classroom", "New Description", true);
        when(classroomRepository.save(classroom))
                .thenReturn(new Classroom(
                        1L, "New Classroom", "New Description", true));
        classroom.setBuilding(BuildingType.AKADEMIJA);

        Classroom savedClassroom = classroomService.create(classroom);

        assertNotNull(savedClassroom);
        assertNotNull(savedClassroom.getId());
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        Classroom existingClassroom = new Classroom(id, "Classroom 1",
                "Description 1", true);
        existingClassroom.setBuilding(BuildingType.AKADEMIJA);
        Classroom updatedClassroom = new Classroom(id, "Updated Classroom",
                "Updated Description", true);
        when(classroomRepository.findById(id)).thenReturn(Optional.of(existingClassroom));
        when(classroomRepository.save(existingClassroom)).thenReturn(updatedClassroom);

        Classroom result = classroomService.update(id, updatedClassroom);

        assertEquals("Updated Classroom", result.getClassroomName());
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Classroom classroom = new Classroom(id, "Classroom 1", "Description 1", true);
        when(classroomRepository.findById(id)).thenReturn(Optional.of(classroom));
        Classroom result = classroomService.finById(id);
        assertEquals("Classroom 1", result.getClassroomName());
        assertEquals("Description 1", result.getDescription());
    }

    @Test
    public void testDisable() {
        Long id = 1L;
        Classroom existingClassroom = new Classroom(id, "Classroom 1", "Description 1", true);
        when(classroomRepository.findById(id)).thenReturn(Optional.of(existingClassroom));
        when(classroomRepository.save(existingClassroom)).thenReturn(existingClassroom);
        Classroom result = classroomService.disable(id);
        assertFalse(result.isActive());
    }

    @Test
    public void testEnable() {
        Long id = 1L;
        Classroom existingClassroom = new Classroom(id, "Classroom 1",
                "Description 1", false);
        when(classroomRepository.findById(id)).thenReturn(Optional.of(existingClassroom));
        when(classroomRepository.save(existingClassroom)).thenReturn(existingClassroom);
        Classroom result = classroomService.enable(id);
        assertTrue(result.isActive());
    }

//    @Test
//    void testFailCreate() {
//        String generatedName = RandomStringUtils.random(5, false, true);
//        String generatedDescription = RandomStringUtils.random(5, true, false);
//
//        ClassroomDto badClassRoom = new ClassroomDto();
//        badClassRoom.setClassroomName("!@#$%^%");
//        badClassRoom.setDescription("generatedDescription");
//        badClassRoom.setBuilding(BuildingType.AKADEMIJA);
//
//        System.out.println(badClassRoom);
//        when(classroomRepository.save(toClassroom(badClassRoom)))
//                .thenReturn(toClassroom(badClassRoom));
//
//
//        var createClassroom = classroomService.create(toClassroom(badClassRoom));
//        assertNull(createClassroom);
//    }

}
