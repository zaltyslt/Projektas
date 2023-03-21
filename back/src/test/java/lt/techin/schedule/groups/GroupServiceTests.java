package lt.techin.schedule.groups;

import lt.techin.schedule.group.Group;
import lt.techin.schedule.group.GroupMapper;
import lt.techin.schedule.group.GroupRepository;
import lt.techin.schedule.group.GroupService;
import lt.techin.schedule.programs.Program;
import lt.techin.schedule.shift.Shift;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GroupServiceTests {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUniqueGroupWithUniqueName() {
        Program program = new Program();
        program.setProgramName("Program");
        program.setDescription("Description");
        program.setActive(true);

        Group group = new Group(1L, "Group", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
        , LocalDateTime.now(), LocalDateTime.now());
        String result = groupService.addUniqueGroup(GroupMapper.groupToDto(group));
        assertEquals("", result);
    }

    @Test
    public void testAddUniqueGroupWithDuplicateName() {
        Program program = new Program();
        program.setProgramName("Program");
        program.setDescription("Description");
        program.setActive(true);

        Group group1 = new Group(1L, "Group", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        Group group2 = new Group(2L, "Group", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        when(groupRepository.findAll()).thenReturn(List.of(group1));

        String result = groupService.addUniqueGroup(GroupMapper.groupToDto(group2));
        assertEquals("Grupės pavadinimas turi būti unikalus.", result);
    }

    @Test
    public void testGetActiveGroups() {
        Program program = new Program();
        program.setProgramName("Program");
        program.setDescription("Description");
        program.setActive(true);

        Group group1 = new Group(1L, "Group1", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        Group group2 = new Group(2L, "Group2", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        when(groupRepository.findAll()).thenReturn(Arrays.asList(group1, group2));

        List<Group> activeGroups = groupService.getActiveGroups();

        assertEquals(Arrays.asList(group1, group2), activeGroups);
    }

    @Test
    public void testGetInactiveGroups() {
        Program program = new Program();
        program.setProgramName("Program");
        program.setDescription("Description");
        program.setActive(true);

        Group group1 = new Group(1L, "Group1", "2018", 15, false, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        Group group2 = new Group(2L, "Group2", "2018", 15, false, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());


        when(groupRepository.findAll()).thenReturn(Arrays.asList(group1, group2));

        List<Group> inactiveGroups = groupService.getInactiveGroups();

        assertEquals(Arrays.asList(group1, group2), inactiveGroups);
    }

    @Test
    public void testGetGroupByID() {
        Program program = new Program();
        program.setProgramName("Program");
        program.setDescription("Description");
        program.setActive(true);

        Group group = new Group(1L, "Group1", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        group.setId(100L);

        when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));

        Group foundGroup = groupService.getGroupByID(100L);
        assertEquals(group, foundGroup);
    }

    @Test
    public void testModifyShiftWithUniqueName() {
        Program program = new Program();
        program.setProgramName("Program");
        program.setDescription("Description");
        program.setActive(true);

        Group group1 = new Group(1L, "Group1", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        Group group2 = new Group(2L, "Group2", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        Group group3 = new Group(2L, "Group3", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        group1.setId(200L);
        group2.setId(100L);
        group3.setId(100L);

        when(groupRepository.findById(group2.getId())).thenReturn(Optional.of(group2));
        when(groupRepository.findAll()).thenReturn(Arrays.asList(group1, group2));

        String result = groupService.modifyExistingGroup(group3.getId(), GroupMapper.groupToDto(group3));
        assertEquals("", result);
    }

    @Test
    public void testModifyShiftWithDuplicateName() {
        Program program = new Program();
        program.setProgramName("Program");
        program.setDescription("Description");
        program.setActive(true);

        Group group1 = new Group(1L, "Group", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        Group group2 = new Group(2L, "Group", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        Group group3 = new Group(2L, "Group", "2018", 15, true, program,
                new Shift("Shift", "8:00", "16:00", true, 1, 8)
                , LocalDateTime.now(), LocalDateTime.now());

        group1.setId(200L);
        group2.setId(100L);
        group3.setId(100L);

        when(groupRepository.findById(group2.getId())).thenReturn(Optional.of(group2));
        when(groupRepository.findAll()).thenReturn(Arrays.asList(group1, group2));

        String result = groupService.modifyExistingGroup(group3.getId(), GroupMapper.groupToDto(group3));
        assertEquals("Grupės pavadinimas turi būti unikalus.", result);
    }
}
