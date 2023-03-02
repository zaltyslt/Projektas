package lt.techin.schedule.teachers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.techin.schedule.shift.ShiftDto;
import lt.techin.schedule.teachers.contacts.ContactDto2;
import lt.techin.schedule.teachers.helpers.TeacherShiftDto;
import lt.techin.schedule.teachers.helpers.TeacherSubjectsDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * A DTO for the {@link lt.techin.schedule.teachers.Teacher} entity
 */
public class TeacherDto implements Serializable {
    private Long id;
    private String fName;
    private String lName;
    private String nickName;
    private Set<TeacherSubjectsDto> subjectsDtoList;
//    private List<ContactDto> contacts;
    private ContactDto2 contacts;
    private TeacherShiftDto teacherShiftDto;
    //    private String savedSubjectsList;
//    private ShiftDto selectedShift;
    private Integer workHoursPerWeek;
    private Boolean isActive;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime dateCreated;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime dateMotified;

    private String message;


    public TeacherDto() {
    }

/////////////////////////


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Set<TeacherSubjectsDto> getSubjectsDtoList() {
        return subjectsDtoList;
    }

    public void setSubjectsDtoList(Set<TeacherSubjectsDto> subjectsDtoList) {
        this.subjectsDtoList = subjectsDtoList;
    }

    public ContactDto2 getContacts() {
        return contacts;
    }

    public void setContacts(ContactDto2 contacts) {
        this.contacts = contacts;
    }

//    public ShiftDto getSelectedShift() {
//        return selectedShift;
//    }
//
//    public void setSelectedShift(ShiftDto selectedShift) {
//        this.selectedShift = selectedShift;
//    }

    public TeacherShiftDto getTeacherShiftDto() {
        return teacherShiftDto;
    }

    public void setTeacherShiftDto(TeacherShiftDto teacherShiftDto) {
        this.teacherShiftDto = teacherShiftDto;
    }

    public Integer getWorkHoursPerWeek() {
        return workHoursPerWeek;
    }

    public void setWorkHoursPerWeek(Integer workHoursPerWeek) {
        this.workHoursPerWeek = workHoursPerWeek;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateMotified() {
        return dateMotified;
    }

    public void setDateMotified(LocalDateTime dateMotified) {
        this.dateMotified = dateMotified;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TeacherDto{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", workHoursPerWeek=" + workHoursPerWeek +
                ", isActive=" + isActive +
                ", contacts=" + contacts +
                ", subjectsDtoList=" + subjectsDtoList +
                ", teacherShiftDto=" + teacherShiftDto +

                ", dateCreated=" + dateCreated +
                ", dateMotified=" + dateMotified +
                ", message='" + message + '\'' +
                '}';
    }
}

