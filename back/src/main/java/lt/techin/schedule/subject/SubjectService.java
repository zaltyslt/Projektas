package lt.techin.schedule.subject;

import jakarta.persistence.EntityManager;
import lt.techin.schedule.classrooms.ClassroomRepository;
import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.module.ModuleRepository;
import lt.techin.schedule.programs.subjectsHours.SubjectHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static lt.techin.schedule.subject.SubjectMapper.toSubjectDto;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final ModuleRepository moduleRepository;
    private final ClassroomRepository classroomRepository;
    @Autowired
    private SubjectHoursRepository subjectHours;
    @Autowired
    private EntityManager entityManager;

    public SubjectService(SubjectRepository subjectRepository,
                          ModuleRepository moduleRepository,
                          ClassroomRepository classroomRepository) {
        this.subjectRepository = subjectRepository;
        this.moduleRepository = moduleRepository;
        this.classroomRepository = classroomRepository;
    }


    public List<Subject> getAll() {
        return subjectRepository.findAll().stream().filter(subject ->
                !subject.getDeleted()).collect(Collectors.toList());
    }

    public List<Subject> getAllDeleted() {
        return subjectRepository.findAll().stream().filter(Subject::getDeleted).collect(Collectors.toList());
    }

    public Optional<Subject> getById(Long id) {
        return subjectRepository.findById(id);
    }

    public Subject create(Subject subject) {
        var existing = subjectRepository.findAll();
        existing = existing.stream().filter(s -> s.getName().equalsIgnoreCase(subject.getName()))
                .filter(s -> s.getDescription().equalsIgnoreCase(subject.getDescription()))
                .filter(s -> s.getModule().getId().equals(subject.getModule().getId()))
                .collect(Collectors.toList());
        if (existing.size() > 0) {
            var subjectDto = toSubjectDto(subject);
            throw new ValidationException("Dalykas su tokiu pavadinimu, aprašu ir moduliu jau sukurtas.",
                    "Subject", "Not unique", subjectDto.toString());
        } else {
            return subjectRepository.save(subject);
        }
    }

    public Subject delete(Long subjectId) {
        var existingSubject = subjectRepository.findById(subjectId).orElseThrow();
        existingSubject.setDeleted(true);
        if (subjectHours != null) {
            var existingsubjecthours = subjectHours.findBySubject(subjectId).orElse(null);
            if (existingsubjecthours != null) {
                existingsubjecthours.setDeleted(true);
                subjectHours.save(existingsubjecthours);
            }
        }
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
        if (subjectHours != null) {
            var existingsubjecthours = subjectHours.findBySubject(id).orElse(null);
            if (existingsubjecthours != null) {
                existingsubjecthours.setDeleted(false);
                subjectHours.save(existingsubjecthours);
            }
        }
        return subjectRepository.save(subject);
    }

    public List<Subject> findAllByModuleId(Long moduleId) {
        return subjectRepository.findSubjectsByModuleId(moduleId).stream().filter(subject ->
                !subject.getDeleted()).collect(Collectors.toList());
    }
}
