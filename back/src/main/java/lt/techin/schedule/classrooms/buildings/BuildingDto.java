package lt.techin.schedule.classrooms.buildings;

import java.util.Objects;

public class BuildingDto {
    private Long id;
//    private String name;
    private BuildingType building;
    public BuildingDto() {
    }

    public BuildingDto(Long id, BuildingType building) {
        this.id = id;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public BuildingType getBuilding() {
        return building;
    }

    public void setBuilding(BuildingType building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingDto that = (BuildingDto) o;
        return Objects.equals(id, that.id) && building == that.building;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, building);
    }

    @Override
    public String toString() {
        return "BuildingDto{" +
                "id=" + id +
                ", building=" + building +
                '}';
    }
}
