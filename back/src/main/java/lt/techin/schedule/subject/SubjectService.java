package lt.techin.schedule.subject;


import jakarta.persistence.EntityManager;
import lt.techin.schedule.classrooms.ClassroomRepository;
import lt.techin.schedule.module.ModuleRepository;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    private final ModuleRepository moduleRepository;

    private final ClassroomRepository classroomRepository;

    @Autowired
    private EntityManager entityManager;

    public SubjectService(SubjectRepository subjectRepository, ModuleRepository moduleRepository, ClassroomRepository classroomRepository) {
        this.subjectRepository = subjectRepository;
        this.moduleRepository = moduleRepository;
        this.classroomRepository = classroomRepository;
    }

    public List<Subject> getAll(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedSubjectFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<Subject> subjects =  subjectRepository.findAll();
        session.disableFilter("deletedSubjectFilter");
        return subjects;
//        return subjectRepository.findAll();
    }

    public Optional<Subject> getById(Long id) {
        return subjectRepository.findById(id);
    }

//    public Subject create(Subject subject) {
//        return subjectRepository.save(subject);
//    }

    public Subject create(Long moduleId, Long classRoomId, Subject subject) {
        var module = moduleRepository.findById(moduleId).orElseThrow();
        var classRoom = classroomRepository.findById(classRoomId).orElseThrow();

        subject.setModule(module);
        subject.addClassRoom(classRoom);
        return subjectRepository.save(subject);
    }

//    public Subject updateSubject(Long id, Subject subject) {
//        var existingSubject = subjectRepository.findById(id).orElseThrow();
//
//        existingSubject.setName(subject.getName());
//        existingSubject.setDescription(subject.getDescription());
//        existingSubject.setModule(subject.getModule());
////        existingSubject.setRooms(subject.getRooms());
//
//        return subjectRepository.save(existingSubject);
//    }

    public Subject updateSubject(Long subjectId, Long moduleId, Subject subject) {
        var existingSubject = subjectRepository.findById(subjectId).orElseThrow();
        var existingModule = moduleRepository.findById(moduleId).orElseThrow();

        existingSubject.setName(subject.getName());
        existingSubject.setDescription(subject.getDescription());
        existingSubject.setModule(existingModule);
//        existingSubject.setRooms(subject.getRooms());

        return subjectRepository.save(existingSubject);
    }

    public Subject restoreSubject(Long id) {
        var subject = subjectRepository.findById(id).orElseThrow();
        subject.setDeleted(false);
        return subjectRepository.save(subject);
    }

    public boolean deleteById(Long id) {
        try {
            subjectRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    private Pageable pageable(int page, int pageSize, String sortField, Sort.Direction sortDirection) {
        return PageRequest.of(page, pageSize, sortDirection, sortField);
    }

    public Page<Subject> findAllPaged(int page, int pageSize, boolean isDeleted) {

        Pageable pageable = PageRequest.of(page, pageSize);

        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedSubjectFilter");
        filter.setParameter("isDeleted", isDeleted);
        Page<Subject> subjects =  subjectRepository.findAll(pageable);
        session.disableFilter("deletedSubjectFilter");
        return subjects;
    }

//    @PostConstruct
//    //FIXME for dev purpose
//    public void loadInitialSubjects() {
//        var initialSubjectsToAdd = List.of(
//                new SubjectDto("Pirmas dalykas", "Duomenų bazės", null),
//                new SubjectDto("Antras dalykas", "Srping Boot", null),
//                new SubjectDto("Trečias dalykas", "React", null)
//        );

//        initialSubjectsToAdd.stream()
//                .map(SubjectMapper::toSubject)
//                .forEach(subjectRepository::save);
//
//        List<SubjectDto> subjects = new ArrayList<>();
//        subjects.addAll(initialSubjectsToAdd);
//        for (int i = 0; i < 100; i++) {
//            var subjectDto = new SubjectDto(String.format("Dalykas (%s)", i), "Aprašas", null, null);
//            subjects.add(subjectDto);
//        }
//
//        subjects.stream()
//                .map(SubjectMapper::toSubject)
//                .forEach(subjectRepository::save);
//    }
}
