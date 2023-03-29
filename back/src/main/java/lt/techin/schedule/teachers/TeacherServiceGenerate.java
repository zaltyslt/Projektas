package lt.techin.schedule.teachers;

import lt.techin.schedule.classrooms.ClassroomRepository;
import lt.techin.schedule.module.ModuleRepository;
import lt.techin.schedule.shift.ShiftRepository;
import lt.techin.schedule.shift.ShiftService;
import lt.techin.schedule.subject.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceGenerate {
    private final static Logger logger = LoggerFactory.getLogger(TeacherMapper.class);
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final ShiftService shiftService;
    private final ShiftRepository shiftRepository;
    private final ModuleRepository moduleRepository;
    private final ClassroomRepository classroomRepository;

    public TeacherServiceGenerate(TeacherRepository teacherRepository,
                                  SubjectRepository subjectRepository, ShiftService shiftService,
                                  ShiftRepository shiftRepository, ModuleRepository moduleRepository,
                                  ClassroomRepository classroomRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.shiftService = shiftService;
        this.shiftRepository = shiftRepository;
        this.moduleRepository = moduleRepository;
        this.classroomRepository = classroomRepository;
    }
}
