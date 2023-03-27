package lt.techin.schedule.config;

import lt.techin.schedule.schedules.holidays.LithuanianHolidayDto;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class LithuanianHolidays {

    public static final LithuanianHolidayDto NEW_YEAR = new LithuanianHolidayDto("Naujieji metai", LocalDate.of(LocalDate.now().getYear(), 1, 1));

    public static final LithuanianHolidayDto DAY_OF_RE_ESTABLISHMENT_OF_THE_THE_STATE_OF_LITHUANIA = new LithuanianHolidayDto
            ("Lietuvos valstybės atkūrimo diena", LocalDate.of(LocalDate.now().getYear(), 2, 16));

    public static final LithuanianHolidayDto DAY_OF_RESTITUTION_OF_INDEPENDENCE_OF_LITHUANIA = new LithuanianHolidayDto
            ("Lietuvos nepriklausomybės atkūrimo diena", LocalDate.of(LocalDate.now().getYear(), 3, 11));

    public static final LithuanianHolidayDto INTERNATIONAL_LABOUR_DAY = new LithuanianHolidayDto("Tarptautinė darbo diena", LocalDate.of(LocalDate.now().getYear(), 5, 1));

    public static final LithuanianHolidayDto DAY_OF_DEW = new LithuanianHolidayDto("Rasos ir Joninių diena", LocalDate.of(LocalDate.now().getYear(), 6, 24));

    public static final LithuanianHolidayDto STATEHOOD_DAY = new LithuanianHolidayDto("Valstybės diena", LocalDate.of(LocalDate.now().getYear(), 7, 6));

    public static final LithuanianHolidayDto ASSUMPTION_DAY = new LithuanianHolidayDto("Žolinės", LocalDate.of(LocalDate.now().getYear(), 8, 15));

    public static final LithuanianHolidayDto ALL_SAINTS_DAY = new LithuanianHolidayDto("Visų šventųjų diena", LocalDate.of(LocalDate.now().getYear(), 10, 1));

    public static final LithuanianHolidayDto ALL_SOULS_DAY = new LithuanianHolidayDto("Vėlinių diena", LocalDate.of(LocalDate.now().getYear(), 10, 2));

    public static final LithuanianHolidayDto CHRISTMAS_EVE = new LithuanianHolidayDto("Kūčios", LocalDate.of(LocalDate.now().getYear(), 12, 24));

    public static final LithuanianHolidayDto CHRISTMAS_FIRST_DAY = new LithuanianHolidayDto("Kalėdos", LocalDate.of(LocalDate.now().getYear(), 12, 25));

    public static final LithuanianHolidayDto CHRISTMAS_SECOND_DAY = new LithuanianHolidayDto("Kalėdos", LocalDate.of(LocalDate.now().getYear(), 12, 26));


    public static final LinkedHashSet<LithuanianHolidayDto> LITHUANIAN_HOLIDAYS = new LinkedHashSet<>(Arrays.asList(
            NEW_YEAR, DAY_OF_RE_ESTABLISHMENT_OF_THE_THE_STATE_OF_LITHUANIA, DAY_OF_RESTITUTION_OF_INDEPENDENCE_OF_LITHUANIA,
            INTERNATIONAL_LABOUR_DAY, DAY_OF_DEW, STATEHOOD_DAY, ASSUMPTION_DAY, ALL_SAINTS_DAY, ALL_SOULS_DAY, CHRISTMAS_EVE,
            CHRISTMAS_FIRST_DAY, CHRISTMAS_SECOND_DAY));





}
