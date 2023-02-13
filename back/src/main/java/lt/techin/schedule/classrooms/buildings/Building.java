package lt.techin.schedule.classrooms.buildings;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Building {
    @Id
//    tau sito nereikia jeigu nori savo id deti
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private BuildingType building;

    public Building(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BuildingType getBuilding() {
        return building;
    }

    public void setBuilding(BuildingType building) {
        this.building = building;
    }

    //    nuosirdziai gali buti kad px sitie yra
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Building building = (Building) o;
//        return Objects.equals(id, building.id) && Objects.equals(name, building.name);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name);
//    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", building=" + building +
                '}';
    }
}
