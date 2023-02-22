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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Subject> getAll() {
        List<Subject> subjects =  subjectRepository.findAll().stream().filter(subject -> !subject.getDeleted()).collect(Collectors.toList());
        return subjects;
    }

    public List<Subject> getAllDeleted() {
        return subjectRepository.findAll().stream().filter(Subject::getDeleted).collect(Collectors.toList());
    }

    public Optional<Subject> getById(Long id) {
        return subjectRepository.findById(id);
    }

    public Subject create(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject delete(Long subjectId) {
        var existingSubject = subjectRepository.findById(subjectId).orElseThrow();
        existingSubject.setDeleted(true);
        return subjectRepository.save(existingSubject);
    }

    public Subject updateSubject(Long subjectId, Subject subject) {
        var existingSubject = subjectRepository.findById(subjectId).orElseThrow();
        var existingModule = moduleRepository.findById(subject.getModule().getId()).orElseThrow();

        existingSubject.setName(subject.getName());
        existingSubject.setDescription(subject.getDescription());
        existingSubject.setModule(existingModule);
        existingSubject.setClassRooms(subject.getClassRooms());

        return subjectRepository.save(existingSubject);
    }

    public Subject restoreSubject(Long id) {
        var subject = subjectRepository.findById(id).orElseThrow();
        subject.setDeleted(false);
        return subjectRepository.save(subject);
    }

    public List<Subject> findAllByModuleId(Long moduleId) {
        return subjectRepository.findSubjectsByModuleId(moduleId);
    }

    //Not used
    public boolean deleteById(Long id) {
        try {
            subjectRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    //Not used (pagination done only from frontend)
    private Pageable pageable(int page, int pageSize, String sortField, Sort.Direction sortDirection) {
        return PageRequest.of(page, pageSize, sortDirection, sortField);
    }

    //Not used
    public Page<Subject> findAllPaged(int page, int pageSize, boolean isDeleted) {

        Pageable pageable = PageRequest.of(page, pageSize);

        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedSubjectFilter");
        filter.setParameter("isDeleted", isDeleted);
        Page<Subject> subjects =  subjectRepository.findAll(pageable);
        session.disableFilter("deletedSubjectFilter");
        return subjects;
    }
}
