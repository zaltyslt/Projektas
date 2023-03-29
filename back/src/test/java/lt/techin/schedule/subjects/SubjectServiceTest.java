package lt.techin.schedule.subjects;

import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.module.Module;
import lt.techin.schedule.module.ModuleRepository;
import lt.techin.schedule.programs.subjectsHours.SubjectHoursRepository;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.subject.SubjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {
    @Mock
    SubjectRepository subjectRepository;

    @Mock
    ModuleRepository moduleRepository;

    @InjectMocks
    SubjectService subjectService;

    @Test
    public void testGetAllNotDeleted() {
        List<Subject> subjects = new ArrayList<>();
        Set<Classroom> classroomSet = new HashSet<>();
        subjects.add(new Subject(1L, "Subject 1", "Subject 1 description", new Module(), classroomSet, false));
        subjects.add(new Subject(2L, "Subject 2", "Subject 2 description", new Module(), classroomSet, false));
        subjects.add(new Subject(3L, "Subject 3", "Subject 3 description", new Module(), classroomSet, true));
        when(subjectRepository.findAll()).thenReturn(subjects);
        List<Subject> results = subjectService.getAll();
        assertEquals(2, results.size());
        assertEquals("Subject 1", results.get(0).getName());
        assertEquals("Subject 2", results.get(1).getName());
    }

    @Test
    public void testGetAllDeleted() {
        List<Subject> subjects = new ArrayList<>();
        Set<Classroom> classroomSet = new HashSet<>();
        subjects.add(new Subject(1L, "Subject 1", "Subject 1 description", new Module(), classroomSet, false));
        subjects.add(new Subject(2L, "Subject 2", "Subject 2 description", new Module(), classroomSet, false));
        subjects.add(new Subject(3L, "Subject 3", "Subject 3 description", new Module(), classroomSet, true));
        when(subjectRepository.findAll()).thenReturn(subjects);
        List<Subject> results = subjectService.getAllDeleted();
        assertEquals(1, results.size());
        assertEquals("Subject 3", results.get(0).getName());
    }

    @Test
    public void testGetById() {
        Set<Classroom> classroomSet = new HashSet<>();
        Subject subject = new Subject(1L, "Subject 1", "Subject 1 description", new Module(), classroomSet, false);
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        Subject result = subjectService.getById(1L).get();
        assertEquals("Subject 1", result.getName());
        assertEquals("Subject 1 description", result.getDescription());
    }

    @Test
    public void testCreateSubject() {
        Set<Classroom> classroomSet = new HashSet<>();
        Subject subject = new Subject(1L, "Subject 1", "Subject 1 description", new Module(), classroomSet, false);
        when(subjectRepository.save(subject)).thenReturn(subject);
        Subject result = subjectService.create(subject);
        assertEquals("Subject 1", result.getName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testCreateSubjectThrowsException() {
        List<Subject> subjects = new ArrayList<>();
        Set<Classroom> classroomSet = new HashSet<>();
        Subject subject = new Subject(1L, "Subject 1", "Subject 1 description", new Module(1L, "Number1", "Name1"), classroomSet, false);
        subjects.add(new Subject(1L, "Subject 1", "Subject 1 description", new Module(1L, "Number1", "Name1"), classroomSet, false));
        subjects.add(new Subject(2L, "Subject 2", "Subject 2 description", new Module(1L, "Number1", "Name1"), classroomSet, false));
        subjects.add(new Subject(3L, "Subject 3", "Subject 3 description", new Module(1L, "Number1", "Name1"), classroomSet, true));
        when(subjectRepository.findAll()).thenReturn(subjects);
        assertThrows(ValidationException.class, () -> {
            subjectService.create(subject);
        });
    }

    @Test
    public void testDelete() {
        Set<Classroom> classroomSet = new HashSet<>();
        Subject subject = new Subject(1L, "Subject 1", "Subject 1 description", new Module(), classroomSet, false);
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(subjectRepository.save(subject)).thenReturn(subject);
        Subject result = subjectService.delete(1L);
        assertEquals("Subject 1", result.getName());
        assertEquals(true, result.getDeleted());
    }

    @Test
    public void testRestoreSubject() {
        Set<Classroom> classroomSet = new HashSet<>();
        Subject subject = new Subject(1L, "Subject 1", "Subject 1 description", new Module(), classroomSet, true);
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(subjectRepository.save(subject)).thenReturn(subject);
        Subject result = subjectService.restoreSubject(1L);
        assertEquals("Subject 1", result.getName());
        assertEquals(false, result.getDeleted());
    }

    @Test
    public void testUpdateSubject() {
        Set<Classroom> classroomSet = new HashSet<>();
        Long id = 1L;
        Module existingModule = new Module(1L, "Number1", "Name1");
        Subject existingSubject = new Subject(id, "Subject 1", "Subject 1 description", existingModule, classroomSet, true);
        Subject updatedSubject = new Subject(id, "Updated subject 1", "Updated subject 1 description", existingModule, classroomSet, false);
        when(subjectRepository.findById(id)).thenReturn(Optional.of(existingSubject));
        when(moduleRepository.findById(existingSubject.getModule().getId())).thenReturn(Optional.of(existingModule));
        when(subjectRepository.save(existingSubject)).thenReturn(updatedSubject);
        Subject result = subjectService.updateSubject(id, updatedSubject);
        assertEquals("Updated subject 1", result.getName());
        assertEquals("Updated subject 1 description", result.getDescription());
        assertEquals(existingModule, result.getModule());
        assertEquals(false, result.getDeleted());
        assertEquals(classroomSet, result.getClassRooms());
    }
}
