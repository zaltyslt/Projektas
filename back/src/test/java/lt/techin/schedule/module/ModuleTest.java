package lt.techin.schedule.module;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ModuleTest {

    private Module module;

    @BeforeEach
    void setUp() {
        module = new Module();
        module.setNumber("M1");
        module.setName("Module 1");
    }

    @Nested
    @DisplayName("equals and hashCode")
    class EqualsAndHashCodeTests {

        @Test
        void equalObjects() {
            Module module2 = new Module();
            module2.setId(module.getId());
            module2.setNumber(module.getNumber());
            module2.setName(module.getName());
            module2.setCreatedDate(module.getCreatedDate());
            module2.setModifiedDate(module.getModifiedDate());
            module2.setDeleted(module.isDeleted());
            assertTrue(module.equals(module2));
            assertEquals(module.hashCode(), module2.hashCode());
        }

        @Test
        void differentObjects() {
            Module module2 = new Module();
            module2.setId(2L);
            module2.setNumber("M2");
            module2.setName("Module 2");
            assertFalse(module.equals(module2));
            assertNotEquals(module.hashCode(), module2.hashCode());
        }
    }

    @Test
    void prePersistSetsCreatedDateAndModifiedDate() {
        module.prePersist();
        assertNotNull(module.getCreatedDate());
        assertNotNull(module.getModifiedDate());
    }

    @Test
    void preUpdateSetsModifiedDate() {
        module.prePersist();
        LocalDateTime previousModifiedDate = module.getModifiedDate();
        try{Thread.sleep(2000);}
        catch (Exception e){}

        module.preUpdate();
        LocalDateTime newModifiedDate = module.getModifiedDate();
        assertNotEquals(previousModifiedDate, newModifiedDate);
    }

    @Test
    void gettersAndSetters() {
        assertEquals("M1", module.getNumber());
        assertEquals("Module 1", module.getName());
        module.setNumber("M2");
        module.setName("Module 2");
        assertEquals("M2", module.getNumber());
        assertEquals("Module 2", module.getName());
    }

    @Test
    void isDeleted() {
        assertFalse(module.isDeleted());
        module.setDeleted(true);
        assertTrue(module.isDeleted());
    }
}