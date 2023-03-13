package lt.techin.schedule.teachers;

import lt.techin.schedule.subject.*;
import lt.techin.schedule.teachers.contacts.ContactRepository;
import lt.techin.schedule.teachers.contacts.ContactService;
import lt.techin.schedule.teachers.helpers.TeacherSubjectMapper;
import lt.techin.schedule.teachers.helpers.TeacherSubjectsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherServiceFind {
    private final static Logger logger = LoggerFactory.getLogger(TeacherMapper.class);
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public TeacherServiceFind(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }
    public List<TeacherDto> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return TeacherMapper.teachersToDtos(teachers);
    }

    public TeacherDto getTeacherById(Long id) {

      var  result = teacherRepository.findById(id);
        return result.isPresent()
                ? TeacherMapper.teacherToDto(result)
                : new TeacherDto();
    }

    public List<TeacherDto> getTeachersByName(String name) {
        name = "%" + name.toLowerCase() + "%";
        var result = teacherRepository.getTeachersByNameFragment(name);
        return TeacherMapper.teachersToDtos(result);
    }

    public Set<TeacherDto> getTeachersBySubjects(Subject subject) {
        var result = teacherRepository.getTeacherBySubject(subject);
        //Comparator<Teacher> byLastName = Comparator.comparing(Teacher::getlName);
        return !result.isEmpty()
                ? result.stream()
                .sorted(Comparator.comparing(Teacher::getlName))
                .filter(t -> t.getSubjects().contains(subject)) //just to be sure :)
                .map(TeacherMapper::teacherToDto)
                .collect(Collectors.toSet())
                : new HashSet<TeacherDto>();
    }

    public List<TeacherDto> getTeachersByActiveStatus(boolean active) {
        var result = teacherRepository.findByisActive(active);
        return !result.isEmpty()
                ? result.stream()
                    .sorted(Comparator.comparing(Teacher::getModifiedDateAndTime).reversed())
                    .filter(t -> t.getActive() == active) //just to be sure :)
                    .map(TeacherMapper::teacherToDto)
                    .toList()
                : new ArrayList<>();


    }

    public Set<TeacherSubjectsDto> getMiniSubjects(){
        var result = subjectRepository.findAll().stream()
                .filter(subject -> !subject.getDeleted()).collect(Collectors.toSet());

        return TeacherSubjectMapper.subjectsToDtos(result);
    }

    public List<Teacher> findTeachersBySubjectsId(Long subjectId, Long shiftId) {
        return teacherRepository.findTeachersBySubjectsId(subjectId).stream().filter(t -> t.getShift().getId().equals(shiftId)).collect(Collectors.toList());
    }
}
