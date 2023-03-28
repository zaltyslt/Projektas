package lt.techin.schedule.schedules.workdays;

import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.classrooms.ClassroomSmallDto;
import lt.techin.schedule.schedules.planner.*;
import lt.techin.schedule.teachers.Teacher;
import lt.techin.schedule.teachers.TeacherDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlannerServiceTest {

    @Autowired
    private PlannerService plannerService;


    @MockBean
    private WorkDayRepository workDayRepository;

    @Test
    void testGetLessonStartString() {
        PlannerDto plannerDto = new PlannerDto();
        plannerDto.setStartIntEnum(1);
        String expectedStartString = "08:00";
        String actualStartString = plannerService.getLessonStartString(plannerDto);
        assertEquals(expectedStartString, actualStartString);
    }

//    @Test
//    void testGetLessonEndString() {
//        // create a PlannerDto with an end time of 3
//        PlannerDto plannerDto = new PlannerDto();
//        plannerDto.setEndIntEnum(3);
//
//        // test that the end time string is correct
//        String expectedEndString = "09:30";
//        String actualEndString = plannerService.getLessonEndString(plannerDto);
//        assertEquals(expectedEndString, actualEndString);
//    }

    @Test
    public void testGetWorkDays() {
        Long scheduleId = 1L;
        List<WorkDay> expectedWorkDays = new ArrayList<>();
        expectedWorkDays.add(new WorkDay(1L));
        expectedWorkDays.add(new WorkDay(2L));
        when(workDayRepository.findWorkDaysByScheduleId(scheduleId))
                .thenReturn(expectedWorkDays);
        List<WorkDay> actualWorkDays = plannerService.getWorkDays(scheduleId);
        assertThat(actualWorkDays, equalTo(expectedWorkDays));
        Mockito.verify(workDayRepository).findWorkDaysByScheduleId(scheduleId);
    }

    @Test
    public void testGetWorkDay() {
        WorkDay workDay = new WorkDay();
        workDay.setId(1L);
        when(workDayRepository.findById(1L)).thenReturn(Optional.of(workDay));
        Optional<WorkDay> result = plannerService.getWorkDay(1L);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(result.get().getId(), 1L);
    }

    public void testUpdateWorkDay() {
        // Create test data
        Long workDayId = 1L;
        WorkDayDto workDayDto = new WorkDayDto();
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setfName("Vardenis");
        teacherDto.setlName("Pavardenis");
//        workDayDto.setTeacher(teacherDto);
        ClassroomSmallDto classroomSmallDto = new ClassroomSmallDto();
        classroomSmallDto.setClassroomName("101");
        workDayDto.setClassroom(classroomSmallDto);
        workDayDto.setOnline(false);

        WorkDay existingWorkDay = new WorkDay();
        existingWorkDay.setId(workDayId);
        existingWorkDay.setTeacher(new Teacher());
        existingWorkDay.setClassroom(new Classroom());
        existingWorkDay.setOnline(true);

        // Mock the behavior of the repository
        when(workDayRepository.findById(workDayId)).thenReturn(Optional.of(existingWorkDay));
//        when(workDayRepository.save(any(WorkDay.class))).thenReturn(existingWorkDay);

        // Call the service method
        WorkDay updatedWorkDay = plannerService.updateWorkDay(workDayId, workDayDto);

//        // Verify that the repository was called correctly
//        verify(workDayRepository, times(1)).findById(workDayId);
//        verify(workDayRepository, times(1)).save(existingWorkDay);

        // Verify that the returned value is correct
        assertEquals(existingWorkDay, updatedWorkDay);
        assertEquals("Vardenis", existingWorkDay.getTeacher().getfName());
        assertEquals("Pavardenis", existingWorkDay.getTeacher().getlName());
        assertEquals("101", existingWorkDay.getClassroom().getClassroomName());
        assertFalse(existingWorkDay.getOnline());
    }

}

