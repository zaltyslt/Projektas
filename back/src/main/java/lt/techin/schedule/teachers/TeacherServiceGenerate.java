package lt.techin.schedule.teachers;

import lt.techin.schedule.shift.LessonTime;
import lt.techin.schedule.shift.Shift;
import lt.techin.schedule.shift.ShiftRepository;
import lt.techin.schedule.shift.ShiftService;
import lt.techin.schedule.subject.Subject;
import lt.techin.schedule.subject.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class TeacherServiceGenerate {
    private final static Logger logger = LoggerFactory.getLogger(TeacherMapper.class);
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final ShiftService shiftService;
    private final ShiftRepository shiftRepository;

    public TeacherServiceGenerate(TeacherRepository teacherRepository, SubjectRepository subjectRepository, ShiftService shiftService, ShiftRepository shiftRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.shiftService = shiftService;
        this.shiftRepository = shiftRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("hello world, I have just started up");
   if(shiftRepository.count() < 2){
     shiftRepository.save(new Shift("Pirma", LessonTime.FIRST.toString(), LessonTime.SECOND.toString(), true,1,2 ));
    }
    if(subjectRepository.count() < 2){
        var subject = new Subject();
//        subject.setId(1L);
        subject.setName("SubJectName");
        var teacher = new Teacher();
        teacher.setId(1L);
        var set = new HashSet<Teacher>();
        set.add(teacher);
        subject.setTeachers(set);
        subjectRepository.save(subject);
    }
    }
    //    @EventListener(ApplicationReadyEvent.class)
    public void loadInitialTeacherData() {
        var optRecNumber = 2 - this.teacherRepository.count();

        if (optRecNumber > 0) {
            String suffix = "";
            List<Teacher> teachers = new ArrayList<>();
            for (int i = 0; i < optRecNumber; i++) {
                suffix = Integer.toString(i);
//                var teacher = new Teacher("Name" + suffix, "Surname" + suffix);
                var teacher = new Teacher(0L, "Name" + suffix, "Surname" + suffix);
                teacher.setNickName("aaa");
                teachers.add(teacher);

            }
            try {
                var tt = teacherRepository.saveAll(teachers);
            } catch (Exception e) {
            //
            }

        }
    }

}
