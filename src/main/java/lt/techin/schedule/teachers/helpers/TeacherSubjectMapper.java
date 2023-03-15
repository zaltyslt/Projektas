package lt.techin.schedule.teachers.helpers;

import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectEntityDto;
import lt.techin.schedule.subject.SubjectMapper;
import lt.techin.schedule.teachers.Teacher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TeacherSubjectMapper {

    public static Set<TeacherSubjectsDto> subjectsToDtos(Set<Subject> subjects) {
        return subjects.stream()
                .map(s -> new TeacherSubjectsDto(s.getId(), s.getName(), s.getModule().getName()))
                .collect(Collectors.toSet());
    }

    public static Subject subjectFromDto(TeacherSubjectsDto subjectsDto) {
        Subject subject = new Subject();
        subject.setId(subjectsDto.getSubjectId());
        subject.setName(subjectsDto.getName());


        return subject;
    }

    public static Set<Subject> subjectsFromDtos(Set<TeacherSubjectsDto> subjectsDtos){
        Set<Subject> subjects = subjectsDtos.stream()
                .map(s->subjectFromDto(s))
                .collect(Collectors.toSet());
        return  subjects;

    }


}
