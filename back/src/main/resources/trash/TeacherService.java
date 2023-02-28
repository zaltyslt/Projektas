package trash;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lt.techin.schedule.subject.*;
import lt.techin.schedule.teachers.contacts.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TeacherService implements ITeacher<Teacher> {
    private final static Logger logger = LoggerFactory.getLogger(TeacherMapper.class);
    protected final TeacherRepository teacherRepository;
    private final ContactService contactService;
    private final ContactRepository contactRepository;
    private final SubjectService subjectService;
    private final SubjectRepository subjectRepository;
    private final TeacherServiceFind finder;
    private final TeacherServiceGenerate generator;


@Autowired
    public TeacherService(TeacherRepository teacherRepository, ContactService contactService, ContactRepository contactRepository, SubjectService subjectService, SubjectRepository subjectRepository, TeacherServiceFind finder, TeacherServiceGenerate generator) {
        this.teacherRepository = teacherRepository;
        this.contactService = contactService;
        this.contactRepository = contactRepository;
        this.subjectService = subjectService;
        this.subjectRepository = subjectRepository;
        this.finder = finder;
    this.generator = generator;
}

    @Override
    public Set<TeacherDto> getAllTeachers() {
        return finder.getAllTeachers();
    }

    @Override
    public TeacherDto getTeacherById(Long id) {
        return finder.getTeacherById(id);
    }

    @Override
    public Set<TeacherDto> getTeachersByName(String name) {
        return finder.getTeachersByName(name);
    }

    @Override
    public Set<TeacherDto> getTeachersBySubject(Subject subject) {
        return finder.getTeachersBySubjects(subject);
    }
//    @Override
//    public Set<TeacherDto> getTeachersByShift(Shift shift) {
//        return notImplemented;
//    }
    @Override
    public Set<TeacherDto> getTeachersByActiveStatus(boolean status) {
        return finder.getTeachersByActiveStatus(status);
    }
//    public TeacherDto createTeacher(TeacherDto teacherDto) {
//
//        return null;
//    }


//    @PostConstruct
    //FIXME for dev purpose
    public void generateTeachers(Long count){
        generator.loadInitialTeacherData();
    }



    /////////////////////////


//    public TeacherDto updateTeacher(Long teacherId, TeacherDto teacherDto) {
//        var presentTeacher = teacherRepository.findById(teacherId)
//                .orElseThrow(() -> new IllegalArgumentException("Not found " + teacherId.toString()));
//        var updatedTeacher = TeacherMapper.teacherFromDto(teacherDto);
//        var contactsDtoList = teacherDto.getContacts();
//
//        if (!contactsDtoList.isEmpty()) {
//
//            List<Contact> contactsList = contactsDtoList.stream().map(c -> ContactMapper.contactFromDto(c)).toList();
//
//            contactRepository.deleteAllByTeacher(presentTeacher);
//            var contacts = contactService.createContacts(presentTeacher, contactsList);
//
//            presentTeacher.setContacts(contacts);
//        }

//        List<String> fieldNames =
//                Arrays.asList("fName", "lName", "NickName", "Active", "WorkHoursPerWeek");
//        try {
//            for (String fieldName : fieldNames) {
//                Method getterMethod = updatedTeacher.getClass().getMethod("get" + fieldName);
//                Object value = getterMethod.invoke(updatedTeacher);
//                Method setterMethod = presentTeacher.getClass().getMethod("set" + fieldName, value.getClass());
//
//                setterMethod.invoke(presentTeacher, value);
//            }
//            presentTeacher.setActive(updatedTeacher.getActive());
//            // presentTeacher.setContacts(updatedTeacher.getContacts());
//        } catch (Exception e) {
//            logger.error("Mokytojo atnaujinti nepavyko.");
//            //ToDo messages to front
/////* NoSuchMethodException, InvocationTargetException, IllegalAccessException
//        }
//        teacherRepository.save(presentTeacher);
//        // presentTeacher.setContacts(updatedTeacher.getContacts()); //techer and contacts has ids
//        return TeacherMapper.teacherToDto(presentTeacher);
//    }

    public Boolean updateTeacherSubjects(Long teacherId, List<SubjectEntityDto> subjectsDto) {
        var teacher = teacherRepository.findById(teacherId);
        if (teacher.isPresent()) {
            var presentTeacher = teacher.get();
            //Fixme to be removed
            Set<Subject> presentSubjects = presentTeacher.getSubjects();

            var aaa = subjectsDto.stream()
                    .map(s -> s.getId())
                    .map(id -> subjectService.getById(id))
                    .filter(optionalSubject -> optionalSubject.isPresent())
                    .map(o -> o.get())
//                    .peek(p->p.addTeacher(presentTeacher))// .filter(Optional::isPresent)
                    .collect(Collectors.toSet());

            presentTeacher.setSubjects(aaa);
//            try{
//            teacherRepository.save(presentTeacher);
//            }catch (Exception e) {//
//            }
            teacherRepository.save(presentTeacher);

            return true;
        }
        return false;
    }






    public String switchTeacherisActive(Long teacherId, boolean status) {
        Optional<Teacher> result = teacherRepository.findById(teacherId);
        if (result.isPresent()) {
            if (result.get().getActive() != status) {
                Teacher teacher = result.get();
                teacher.setActive(status);
                teacherRepository.save(teacher);
                return status == true ? "Mokytojas aktyvus." : "Mokytojas ištrintas.";
            }
            return "Mokytojo būsena nepakeista.";
        }
        return "Mokytojas nerastas.";
    }

    public static String saveSubjectsList(List<Subject> subjects) {
        if (subjects != null) {
            return subjects.stream().map(x -> x.getId().toString())
                    .collect(Collectors.joining(";"));
        }
        return "*";
    }

    public static List<Subject> loadSubjectsList(String savedSubjectsList) {
        ObjectMapper objMapper = new ObjectMapper();
        var savedSubjects = savedSubjectsList.split(";");
        var restoredList = new ArrayList<Subject>();

        for (String id : savedSubjects) {
            Subject subject = new Subject();
            subject.setId(Long.parseLong(id));
            restoredList.add(subject);
        }

        return restoredList;
    }
//    public static List<Subject> fillSubjects(List<Subject> subjects){
//        var result = new ArrayList<Subject>();
//        for(Subject subject: subjects){
//            result.add(tec subjectService.getById(subject.getId()));
//        }
//    }
//    public static Optional<TeacherDto>
}
