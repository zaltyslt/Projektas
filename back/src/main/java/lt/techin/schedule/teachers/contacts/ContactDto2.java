package lt.techin.schedule.teachers.contacts;

import java.io.Serializable;

/**
 * A DTO for the {@link Contact} entity
 */
public class ContactDto2 implements Serializable {
    private Long teacherId;
    private String phoneNumber;
    private String directEmail;
    private String teamsEmail;
    private String teamsName;

    public ContactDto2() {
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDirectEmail() {
        return directEmail;
    }

    public void setDirectEmail(String directEmail) {
        this.directEmail = directEmail;
    }

    public String getTeamsEmail() {
        return teamsEmail;
    }

    public void setTeamsEmail(String teamsEmail) {
        this.teamsEmail = teamsEmail;
    }

    public String getTeamsName() {
        return teamsName;
    }

    public void setTeamsName(String teamsName) {
        this.teamsName = teamsName;
    }

    @Override
    public String toString() {
        return "ContactDto2{" +
                "teacherId=" + teacherId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", directEmail='" + directEmail + '\'' +
                ", teamsEmail='" + teamsEmail + '\'' +
                ", teamsName='" + teamsName + '\'' +
                '}';
    }
}