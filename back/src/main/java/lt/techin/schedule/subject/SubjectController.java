package lt.techin.schedule.subject;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lt.techin.schedule.subject.SubjectMapper.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<SubjectEntityDto> getSubjects() {
        return subjectService.getAll().stream().map(SubjectMapper::toSubjectEntityDto).collect(toList());
    }

    @GetMapping(value = "/{subjectId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SubjectEntityDto> getSubject(@PathVariable Long subjectId) {
        var subjectOptional = subjectService.getById(subjectId);

        var responseEntity = subjectOptional.map(subject -> ok(toSubjectEntityDto(subject))).orElseGet(()-> ResponseEntity.notFound().build());

        return responseEntity;
    }

    @GetMapping(value = "/deleted", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<SubjectEntityDto> getDeletedSubjects() {
        return subjectService.getAllDeleted().stream().map(SubjectMapper::toSubjectEntityDto).collect(toList());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto subjectDto) {
        var createdSubject = subjectService.create(toSubject(subjectDto));
        return ok(toSubjectDto(createdSubject));
    }

    @PatchMapping("/delete/{subjectId}")
    public ResponseEntity<SubjectDto> delete(@PathVariable Long subjectId) {
        var deletedSubject = subjectService.delete(subjectId);
        return ok(toSubjectDto(deletedSubject));
    }

    @PatchMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable Long subjectId, @RequestBody SubjectDto subjectDto) {
        var updatedSubject = subjectService.updateSubject(subjectId, toSubject(subjectDto));
        return ok(toSubjectDto(updatedSubject));
    }

    //Not used
    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long subjectId) {
        boolean deleted = subjectService.deleteById(subjectId);

        if(deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/restore/{subjectId}")
    public ResponseEntity<SubjectDto> restoreSubject(@PathVariable Long subjectId) {
        var restoredSubject = subjectService.restoreSubject(subjectId);
        return ok(toSubjectDto(restoredSubject));
    }

    @GetMapping("/module/{moduleId}")
    public List<SubjectEntityDto> findByModule(@PathVariable Long moduleId) {
        var subjects = subjectService.findAllByModuleId(moduleId);
        return subjects.stream().map(SubjectMapper::toSubjectEntityDto).collect(toList());
    }

//    Not used at demo v1/v2
    @GetMapping("/paged")
    @ResponseBody
    public ResponseEntity<List<SubjectEntityDto>> findSubjectsPaged(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        Page<Subject> pagedSubjects = subjectService.findAllPaged(page, pageSize, false);
        int totalPageCount = pagedSubjects.getTotalPages();
        List<SubjectEntityDto> subjects = pagedSubjects.stream()
                .map(SubjectMapper::toSubjectEntityDto)
                .collect(toList());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("totalCount", Integer.toString(totalPageCount));
        return ResponseEntity.ok().headers(httpHeaders).body(subjects);
    }

}
