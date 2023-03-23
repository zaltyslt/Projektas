package lt.techin.schedule.programs.subjectsHours;

import lt.techin.schedule.subject.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectHoursService {
    @Autowired
    private SubjectHoursRepository subjectHoursRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectHoursService(SubjectHoursRepository subjectHoursRepository, SubjectRepository subjectRepository) {
    }


    public List<SubjectHours> getAll() {
        return subjectHoursRepository.findAll();
    }

    public SubjectHours finById(Long id) {
        return subjectHoursRepository.findById(id).orElse(new SubjectHours());
    }

    public List<SubjectHours> create(List<SubjectHours> subjectHours) {
        return subjectHoursRepository.saveAll(subjectHours);
    }

    public boolean deleteById(Long id) {
        SubjectHours byId = subjectHoursRepository.findById(id).orElse(null);
        if (byId != null) {
            subjectHoursRepository.deleteById(byId.getId());
            return true;
        } else {
            return false;
        }
    }

    public List<SubjectHours> updateAll(List<SubjectHours> subjectHoursList) {
        List<SubjectHours> updatedList = new ArrayList<>();
        for (SubjectHours subjecthour : subjectHoursList) {
            if (subjecthour.getId() == null) {
                var createdHours = subjectHoursRepository.save(subjecthour);
                updatedList.add(createdHours);
            } else {
                var toNewSubject = subjectHoursRepository.findById(subjecthour.getId()).orElse(null);
                if (toNewSubject != null) {
                    toNewSubject.setHours(subjecthour.getHours());
                    toNewSubject.setSubject(subjecthour.getSubject());
                    toNewSubject.setSubjectName(subjecthour.getSubjectName());
                    updatedList.add(toNewSubject);
                }
            }
        }
        List<SubjectHours> newList = new ArrayList<>();
        if (!updatedList.isEmpty()) {
            newList = subjectHoursRepository.saveAll(updatedList);
        }
        return newList;
    }
}
