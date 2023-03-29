package lt.techin.schedule.schedules;

public class ScheduleEntityDto {
    private Long id;

    public ScheduleEntityDto() {
    }

    public ScheduleEntityDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}