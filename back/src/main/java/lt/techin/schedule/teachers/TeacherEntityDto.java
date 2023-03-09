package lt.techin.schedule.teachers;

import java.util.Objects;

public class TeacherEntityDto {

    private Long id;

    private String fName;

    private String lName;

    public TeacherEntityDto() {
    }

    public TeacherEntityDto(Long id, String fName, String lName) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherEntityDto that = (TeacherEntityDto) o;
        return Objects.equals(id, that.id) && Objects.equals(fName, that.fName) && Objects.equals(lName, that.lName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fName, lName);
    }

    @Override
    public String toString() {
        return "TeacherEntityDto{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                '}';
    }
}
