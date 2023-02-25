package lt.techin.schedule.teachers;

import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectEntityDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ITeacher<Teacher>{

    Set<TeacherDto> getAllTeachers();
    TeacherDto getTeacherById(Long id);
    Set<TeacherDto> getTeachersByName(String name);
    Set<TeacherDto> getTeachersBySubject(Subject subject);
//    Set<TeacherDto> getTeachersByShift(Shift shift);
    Set<TeacherDto> getTeachersByActiveStatus(boolean status);
    TeacherDto createTeacher(TeacherDto teacherDto);



    //to be implemented
//    List<Teacher> getTeachersByMaxHoursPerWeek(int hours);

//    TeacherDto createTeacher(TeacherDto teacherDto);
//    String switchTeacherisActive(Long teacherId, boolean status);


}
