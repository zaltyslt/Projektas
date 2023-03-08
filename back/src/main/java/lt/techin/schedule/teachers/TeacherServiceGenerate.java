package lt.techin.schedule.teachers;

import lt.techin.schedule.classrooms.BuildingType;
import lt.techin.schedule.classrooms.Classroom;
import lt.techin.schedule.classrooms.ClassroomRepository;
import lt.techin.schedule.module.Module;
import lt.techin.schedule.module.ModuleRepository;
import lt.techin.schedule.shift.LessonTime;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftRepository;
import lt.techin.schedule.shift.ShiftService;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectRepository;
import lt.techin.schedule.teachers.contacts.Contact;
import lt.techin.schedule.teachers.contacts.ContactType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TeacherServiceGenerate {
    private final static Logger logger = LoggerFactory.getLogger(TeacherMapper.class);
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final ShiftService shiftService;
    private final ShiftRepository shiftRepository;
    private final ModuleRepository moduleRepository;
    private final ClassroomRepository classroomRepository;


    public TeacherServiceGenerate(TeacherRepository teacherRepository, SubjectRepository subjectRepository, ShiftService shiftService, ShiftRepository shiftRepository, ModuleRepository moduleRepository, ClassroomRepository classroomRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.shiftService = shiftService;
        this.shiftRepository = shiftRepository;
        this.moduleRepository = moduleRepository;

        this.classroomRepository = classroomRepository;

    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        Module mod1 = new Module(null, "A01", "I modulis", LocalDateTime.now(), LocalDateTime.now(), false);
        Module mod2 = new Module(null, "A02", "II modulis", LocalDateTime.now(), LocalDateTime.now(), false);
        Module mod3 = new Module(null, "A03", "III modulis", LocalDateTime.now(), LocalDateTime.now(), false);
        var mod = moduleRepository.saveAll(Arrays.asList(mod1, mod2, mod3));
//Classroom(Long id, String classroomName, String description, boolean active) {
        Classroom cla1 = new Classroom(null, "101", "Klasė 101", true);
        cla1.setBuilding(BuildingType.TECHIN);
        Classroom cla2 = new Classroom(null, "202", "Klasė 202", true);
        cla2.setBuilding(BuildingType.AKADEMIJA);
        Classroom cla3 = new Classroom(null, "201", "Klasė 201", true);
        cla3.setBuilding(BuildingType.TECHIN);
        var cla = classroomRepository.saveAll(Arrays.asList(cla1, cla2, cla3));
        var classroomSet = new HashSet<Classroom>();
        classroomSet.addAll(Arrays.asList(cla1, cla2));
//        Subject(Long id, String name, String description, Module module, Set<Classroom> classRooms, Boolean deleted) {

        Subject sub1 = new Subject(null, "Dalykas1", "Dalyko 1 aprašymas", mod1, classroomSet, false);
        Subject sub2 = new Subject(null, "Dalykas2", "Dalyko 2 aprašymas", mod2, classroomSet, false);
        Subject sub3 = new Subject(null, "Dalykas3", "Dalyko 3 aprašymas", mod3, classroomSet, false);
        Set<Subject> subSet = new HashSet<>();
        subSet.addAll(Arrays.asList(sub1, sub2, sub3));
        var sub = subjectRepository.saveAll(subSet);

        Shift sh1 = new Shift("Pamaina1", LessonTime.FIRST.getLessonStart(), LessonTime.FIFTH.getLessonEnd(), true, 1, 5);
        Shift sh2 = new Shift("Pamaina2", LessonTime.FIRST.getLessonStart(), LessonTime.SEVENTH.getLessonEnd(), true, 1, 5);
        Shift sh3 = new Shift("Pamaina3", LessonTime.FIRST.getLessonStart(), LessonTime.EIGHTH.getLessonEnd(), true, 1, 5);
        var shift = shiftRepository.saveAll(Arrays.asList(sh1, sh2, sh3));


//         public Teacher(Long id, List< Contact > contacts, Set<Subject> subjects, Shift shift, String fName, String lName, String nickName,
//         Boolean isActive, LocalDateTime createdDateTime, LocalDateTime modifiedDateAndTime, Integer workHoursPerWeek, Integer hashCode) {
        Teacher t1 = new Teacher(null, null,subSet,sh1,"Vardas1", "Pavarde1", "", true, LocalDateTime.now(),LocalDateTime.now(), 10);
        Teacher t2 = new Teacher(null, null,subSet,sh2,"Vardas2", "Pavarde2", "", true, LocalDateTime.now(),LocalDateTime.now(), 20);
        Teacher t3 = new Teacher(null, null,subSet,sh3,"Vardas3", "Pavarde3", "", true, LocalDateTime.now(),LocalDateTime.now(), 30);
       var teach = teacherRepository.saveAll(Arrays.asList(t1,t2,t3));

        Contact con11 = new Contact(t1, ContactType.TEAMS_EMAIL, "Teams@1");
        Contact con12 = new Contact(t1, ContactType.TEAMS_NAME, "Teams1");
        Contact con13 = new Contact(t1, ContactType.DIRECT_EMAIL, "Mail@1");
        Contact con14 = new Contact(t1, ContactType.PHONE_NUMBER, "8 000 11111");
        List<Contact> t1con = new ArrayList<>(Arrays.asList(con11, con12,con13,con14));
        t1.setContacts(t1con);

        Contact con21 = new Contact(t2, ContactType.TEAMS_EMAIL, "Teams@2");
        Contact con22 = new Contact(t2, ContactType.TEAMS_NAME, "Teams2");
        Contact con23 = new Contact(t2, ContactType.DIRECT_EMAIL, "Mail@2");
        Contact con24 = new Contact(t2, ContactType.PHONE_NUMBER, "8 000 22222");
        List<Contact> t2con = new ArrayList<>(Arrays.asList(con21, con22,con23,con24));
        t2.setContacts(t2con);

        Contact con31 = new Contact(t3, ContactType.TEAMS_EMAIL, "Teams@3");
        Contact con32 = new Contact(t3, ContactType.TEAMS_NAME, "Teams3");
        Contact con33 = new Contact(t3, ContactType.DIRECT_EMAIL, "Mail@3");
        Contact con34 = new Contact(t3, ContactType.PHONE_NUMBER, "8 000 33333");
        List<Contact> t3con = new ArrayList<>(Arrays.asList(con31, con32,con33,con34));
        t3.setContacts(t3con);
        teach = teacherRepository.saveAll(Arrays.asList(t1,t2,t3));

//        Module(Long id, String number, String name, LocalDateTime createdDate, LocalDateTime modifiedDate, boolean deleted) {

    }
//        System.out.println("hello world, I have just started up");
//   if(shiftRepository.count() < 2){
//     shiftRepository.save(new Shift("Pirma", LessonTime.FIRST.toString(), LessonTime.SECOND.toString(), true,1,2 ));
//    }
//    if(subjectRepository.count() < 2){
//        var subject = new Subject();
////        subject.setId(1L);
//        subject.setName("SubJectName");
//        var teacher = new Teacher();
//        teacher.setId(1L);
//        var set = new HashSet<Teacher>();
//        set.add(teacher);
//        subject.setTeachers(set);
//        subjectRepository.save(subject);
//    }
//    }
    //    @EventListener(ApplicationReadyEvent.class)
//    public void loadInitialTeacherData() {
//        var optRecNumber = 2 - this.teacherRepository.count();
//
//        if (optRecNumber > 0) {
//            String suffix = "";
//            List<Teacher> teachers = new ArrayList<>();
//            for (int i = 0; i < optRecNumber; i++) {
//                suffix = Integer.toString(i);
////                var teacher = new Teacher("Name" + suffix, "Surname" + suffix);
//                var teacher = new Teacher(0L, "Name" + suffix, "Surname" + suffix);
//                teacher.setNickName("aaa");
//                teachers.add(teacher);
//
//            }
//            try {
//                var tt = teacherRepository.saveAll(teachers);
//            } catch (Exception e) {
//            //
//            }
//
//        }
//    }

}
