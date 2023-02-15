package lt.techin.schedule.classrooms.buildings;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@EnableJpaRepositories(basePackageClasses = BuildingRepository.class)
class BuildingServiceTest {

    @Autowired
    BuildingRepository buildingRepository;

//    BuildingDto buildintTotest = new BuildingDto(219L, "TECHINLT");


//    @BeforeEach
//    void setUp() {
//        buildingRepository.save(
//                BuildingMapper.toBuilding(buildintTotest));
//    }

//    @Test
//    void create() {
//        assertEquals(
//                buildintTotest,
//                BuildingMapper.toBuildingDto(
//                        buildingRepository.save(
//                                BuildingMapper.toBuilding(buildintTotest))));
//    }
//
//    @Test
//    void getBuildingById() {
//
//        assertEquals(buildintTotest,
//                BuildingMapper.toBuildingDto(buildingRepository.findById(buildintTotest.getId()).orElse(null)));
//    }
//
//    @Test
//    void getAll() {
//        assertEquals(List.of(buildintTotest),
//                buildingRepository.findAll().stream()
//                        .map(BuildingMapper::toBuildingDto)
//                        .toList());
//    }
}