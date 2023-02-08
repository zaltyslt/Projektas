package lt.techin.schedule.classrooms.buildings;

public class BuildingMapper {
    public static BuildingDto toBuildingDto(Building building) {
        var buildingDto = new BuildingDto();

        buildingDto.setId(building.getId());
        buildingDto.setName(building.getName());
        return buildingDto;
    }

    public static Building toBuilding(BuildingDto buildingDto) {
        var building = new Building();
        building.setId(buildingDto.getId());
        building.setName(buildingDto.getName());
        return building;
    }
}
