package lt.techin.schedule.teachers.contacts;

import lt.techin.schedule.teachers.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping(value = "/api/v1/contacts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ContactController {

    private final ContactService contactService;
    public static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    @ResponseBody
    public List<ContactDto> getAllContacts() {
        var contacts = contactService.getAllContacts()
                .stream()
                .map(c -> ContactMapper.contactToDto(c)).toList();

        return contacts;
    }


    @GetMapping(value = "/teacher")
    @ResponseBody
    public ResponseEntity<List<ContactDto>> getContact(@RequestParam ("tid") String tid) {
        var contacts = contactService.getContactsByTeacherId(Long.parseLong(tid));

        if (contacts.size() > 0) {
            return ok(contacts.stream()
                    .map(c -> ContactMapper.contactToDto(c)).toList());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/t/{teacherId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Contact>> getContactByTeacherId(@PathVariable Long teacherId) {

        var contactList = contactService.getContactsByTeacherId(teacherId);

        return contactList.size() > 0 ?
                ResponseEntity.ok(contactList) :
                ResponseEntity.notFound().build();

    }

    @PatchMapping("/update")
    public ResponseEntity<ContactDto> updateAnimal(@RequestParam ("tid") Long tId, @RequestBody ContactDto contactDto) {
        Contact contact = ContactMapper.contactFromDto(contactDto);
        List<Contact> contacts = List.of(contact);
        Teacher dummyTeacher = new Teacher();
        dummyTeacher.setId(tId);

        var contactUpdate = contactService.createContacts(dummyTeacher, contacts);
        var updatedContact = contacts.get(0);
        return ok(ContactMapper.contactToDto(updatedContact));
    }
}
