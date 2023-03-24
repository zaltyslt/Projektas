package lt.techin.schedule.programs.subjectHours;

import lt.techin.schedule.classrooms.BuildingType;
import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.classrooms.ClassroomDto;
import lt.techin.schedule.programs.subjectsHours.SubjectHours;
import lt.techin.schedule.programs.subjectsHours.SubjectHoursDto;
import lt.techin.schedule.programs.subjectsHours.SubjectHoursRepository;
import lt.techin.schedule.programs.subjectsHours.SubjectHoursService;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;


@SpringBootTest
public class SubjectHoursServiceTest {

    @Mock
    private SubjectHoursRepository subjectHoursRepository;

    @Mock
    private SubjectRepository subjectRepository;
    @InjectMocks
    private SubjectHoursService subjectHoursService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        List<SubjectHours> expectedSubjectHours = new ArrayList<>();
        Subject subject = new Subject();
        Subject subject2 = new Subject();
        expectedSubjectHours.add(new SubjectHours(1L, subject, 10));
        expectedSubjectHours.add(new SubjectHours(2L, subject2, 10));
        when(subjectHoursRepository.findAll()).thenReturn(expectedSubjectHours);
        List<SubjectHours> actualSubjectHours = subjectHoursService.getAll();
        Assertions.assertEquals(expectedSubjectHours, actualSubjectHours);
    }

//    @Test
//    public void testFindById() {
//    }

    @Test
    public void testCreateSuccess() {
        SubjectHoursDto subjectHoursDto = new SubjectHoursDto();
        List<SubjectHours> subjectHoursList = new ArrayList<>();
        subjectHoursDto.setSubject(1L);
        subjectHoursDto.setHours(10);
        SubjectHours subjectHours = new SubjectHours();
        subjectHours.setSubject(1L);
        subjectHours.setHours(10);
        when(subjectHoursRepository.save(subjectHours)).thenReturn(subjectHours);
        subjectHoursList.add(subjectHours);
        when(subjectHoursService.create(subjectHoursList)).thenReturn(subjectHoursList);
        List<SubjectHours> actualSubjectHours = subjectHoursService.create(subjectHoursList);
        Assertions.assertEquals(subjectHoursList, actualSubjectHours);
    }



}
