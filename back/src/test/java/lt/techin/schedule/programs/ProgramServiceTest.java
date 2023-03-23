package lt.techin.schedule.programs;

import lt.techin.schedule.group.Group;
import lt.techin.schedule.programs.subjectsHours.SubjectHours;
import lt.techin.schedule.programs.subjectsHours.SubjectHoursService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProgramServiceTest {
    @Mock
    private ProgramRepository programRepository;
    @Mock
    private SubjectHoursService subjectHoursService;
    @InjectMocks
    private ProgramService programService;
    private ProgramRepository verify(ProgramRepository programRepository, VerificationMode times) {
        return programRepository;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        programService = new ProgramService(programRepository, subjectHoursService);
    }

    @Test
    public void testGetAll() {
        // Arrange
        List<Program> expectedPrograms = new ArrayList<>();
        Set<Group> group = new HashSet<>();
        List<SubjectHours> subjectHours = new ArrayList<>();
        expectedPrograms.add(new Program(1L, "program1", "desc1", group,
                true, subjectHours));
        expectedPrograms.add(new Program(2L, "program2", "desc2", group,
                true, subjectHours));
        when(programRepository.findAll()).thenReturn(expectedPrograms);
        List<Program> actualPrograms = programService.getAll();
        assertEquals(expectedPrograms.size(), actualPrograms.size());
        assertEquals(expectedPrograms.get(0).getProgramName(), actualPrograms.get(0).getProgramName());
        assertEquals(expectedPrograms.get(1).getProgramName(), actualPrograms.get(1).getProgramName());
    }

    @Test
    public void testCreateSuccess() {
        Set<Group> group = new HashSet<>();
        List<SubjectHours> subjectHours = new ArrayList<>();
        Program program = new Program(1L, "program1", "desc1", group,
                true, subjectHours);
        when(programRepository.save(program)).thenReturn(program);
        Program createdProgram = programService.create(program);
        assertEquals(program.getProgramName(), createdProgram.getProgramName());
    }

    @Test
    public void testCreateFail() {
        Program program = new Program();
        Program createdProgram = programService.create(program);
        assertEquals(null, createdProgram);
        program = new Program();
        when(programRepository.findByProgramName(program.getProgramName())).thenReturn(program);
        createdProgram = programService.create(program);
        assertEquals(null, createdProgram);
    }

    @Test
    void testCreateWithSubjectList() {
        Program program = new Program();
        program.setProgramName("Test Program");
        program.setSubjectHoursList(List.of());
        when(programRepository.findByProgramName(program.getProgramName())).thenReturn(null);
        when(subjectHoursService.create(program.getSubjectHoursList())).thenReturn(List.of());
        when(programRepository.save(program)).thenReturn(program);
        Program result = programService.createWithSubjectList(program);
        assertEquals(program, result);
        verify(programRepository, times(1)).findByProgramName(program.getProgramName());
//        verify(subjectHoursService, times(1)).create(program.getSubjectHoursList());
        verify(programRepository, times(1)).save(program);
    }

    @Test
    void testCreateWithSubjectListNullName() {
        Program program = new Program();
        program.setSubjectHoursList(List.of());
        Program result = programService.createWithSubjectList(program);
        assertNull(result);
    }

    @Test
    void testCreateWithSubjectListExistingProgramName() {
        Program program = new Program();
        program.setProgramName("Test Program");
        program.setSubjectHoursList(List.of());
        when(programRepository.findByProgramName(program.getProgramName())).thenReturn(new Program());
        Program result = programService.createWithSubjectList(program);
        assertNull(result);
        verify(programRepository, times(1)).findByProgramName(program.getProgramName());
//        verify(subjectHoursService, never()).create(program.getSubjectHoursList());
        verify(programRepository, never()).save(program);
    }

    @Test
    void testFindByProgramName() {
        Set<Group> group = new HashSet<>();
        List<SubjectHours> subjectHours = new ArrayList<>();
        List<Program> programs = new ArrayList<>(Arrays.asList(
                new Program(1L, "program1", "desc1", group,
                        true, subjectHours),
                new Program(2L, "program2", "desc2", group,
                        true, subjectHours),
                new Program(3L, "program3", "desc3", group,
                        true, subjectHours)
        ));
        when(programRepository.findAll()).thenReturn(programs);
        assertTrue(programService.findByProgramName("program2"));
        assertFalse(programService.findByProgramName("Program4"));
    }

    @Test
    void testFindByProgramNameNotSelf() {
        Set<Group> group = new HashSet<>();
        List<SubjectHours> subjectHours = new ArrayList<>();
        List<Program> programs = new ArrayList<>(Arrays.asList(
                new Program(1L, "program1", "desc1", group,
                        true, subjectHours),
                new Program(2L, "program2", "desc2", group,
                        true, subjectHours),
                new Program(3L, "program3", "desc3", group,
                        true, subjectHours)
        ));
        when(programRepository.findAll()).thenReturn(programs);
        assertTrue(programService.findByProgramNameNotSelf("program2", "program3"));
        assertFalse(programService.findByProgramNameNotSelf("Program2", "Program2"));
        assertFalse(programService.findByProgramNameNotSelf("Program2", "Program4"));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        Program existingProgram = new Program();
        existingProgram.setId(id);
        existingProgram.setProgramName("Existing Program");
        existingProgram.setDescription("Existing Description");
        existingProgram.setActive(true);
        existingProgram.setSubjectHoursList(List.of(new SubjectHours()));
        Program newProgram = new Program();
        newProgram.setId(id);
        newProgram.setProgramName("New Program");
        newProgram.setDescription("New Description");
        newProgram.setActive(false);
        newProgram.setSubjectHoursList(List.of(new SubjectHours()));
        when(programRepository.findById(id)).thenReturn(java.util.Optional.of(existingProgram));
        when(programRepository.save(existingProgram)).thenReturn(existingProgram);
        Program result = programService.update(id, newProgram);
        assertNotNull(result);
        assertEquals("New Program", result.getProgramName());
        assertEquals("New Description", result.getDescription());
        assertFalse(result.isActive());
        assertEquals(1, result.getSubjectHoursList().size());
        verify(programRepository, times(1)).findById(id);
        verify(programRepository, times(1)).save(existingProgram);
    }

    @Test
    void testUpdateProgramNotFound() {
        Long id = 1L;
        Program newProgram = new Program();
        newProgram.setId(id);
        when(programRepository.findById(id)).thenReturn(java.util.Optional.empty());
        Program result = programService.update(id, newProgram);
        assertNull(result);
        verify(programRepository, times(1)).findById(id);
        verify(programRepository, never()).save(any());
    }
    @Test
    public void testFinById() {
        Long id = 1L;
        Program program = new Program();
        program.setId(id);
        when(programRepository.findById(id)).thenReturn(Optional.of(program));
        Program result = programService.finById(id);
        assertEquals(id, result.getId());
    }

    @Test
    public void testDisable() {
        Long id = 1L;
        Program program = new Program();
        program.setId(id);
        program.setActive(true);
        when(programRepository.findById(id)).thenReturn(Optional.of(program));
        when(programRepository.save(program)).thenReturn(program);
        Program result = programService.disable(id);
        assertEquals(false, result.isActive());
    }

    @Test
    public void testEnable() {
        Long id = 1L;
        Program program = new Program();
        program.setId(id);
        program.setActive(false);
        when(programRepository.findById(id)).thenReturn(Optional.of(program));
        when(programRepository.save(program)).thenReturn(program);
        Program result = programService.enable(id);
        assertEquals(true, result.isActive());
    }
}
