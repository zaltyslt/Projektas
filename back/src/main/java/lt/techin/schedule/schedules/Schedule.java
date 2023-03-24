package lt.techin.schedule.schedules;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lt.techin.schedule.group.Group;
import lt.techin.schedule.schedules.holidays.Holiday;
import lt.techin.schedule.schedules.planner.WorkDay;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groups;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<WorkDay> workingDays;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Holiday> holidays;
    private String schoolYear;
    private String semester;

    @Column(name = "date_from", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @Column(name = "date_until", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateUntil;

    @ElementCollection
    @CollectionTable(name = "subject_unassigned_time", joinColumns = @JoinColumn(name = "schedule_id"))
    @MapKeyColumn(name = "subject_id")
    @Column(name = "unassigned_time")
    private Map<Long, Integer> subjectIdWithUnassignedTime;

    private boolean active = true;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public Schedule() {
        subjectIdWithUnassignedTime = new HashMap<>();
        workingDays = new HashSet<>();
        holidays = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroups() {
        return groups;
    }

    public void setGroups(Group groups) {
        this.groups = groups;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(LocalDate dateUntil) {
        this.dateUntil = dateUntil;
    }

    public Set<WorkDay> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Set<WorkDay> workingDays) {
        this.workingDays = workingDays;
    }

    public Set<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(Set<Holiday> holidays) {
        this.holidays = holidays;
    }

    public void addWorkDay (WorkDay workDay) {
        workingDays.add(workDay);
    }

    public void addHoliday (Holiday holiday) {
        holidays.add(holiday);
    }



    public Map<Long, Integer> getSubjectIdWithUnassignedTime() {
        return subjectIdWithUnassignedTime;
    }

    public void setSubjectIdWithUnassignedTime(Map<Long, Integer> subjectIdWithUnassignedTime) {
        this.subjectIdWithUnassignedTime = subjectIdWithUnassignedTime;
    }

    public Integer getUnassignedTimeWithSubjectId(Long subjectID) {
        return subjectIdWithUnassignedTime.get(subjectID);
    }

    public Integer replaceUnassignedTime(Long subjectID, Integer newTime) {
        return subjectIdWithUnassignedTime.replace(subjectID, newTime);
    }

    public void deleteUnassignedTimeWithSubjectId(Long subjectID) {
        subjectIdWithUnassignedTime.remove(subjectID);
    }

    public void addUnassignedTimeWithSubjectId(Long subjectID, Integer time) {
        subjectIdWithUnassignedTime.put(subjectID, time);
    }
}