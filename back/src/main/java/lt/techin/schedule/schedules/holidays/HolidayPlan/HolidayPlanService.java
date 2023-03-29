package lt.techin.schedule.schedules.holidays.HolidayPlan;

import jakarta.transaction.Transactional;
import lt.techin.schedule.exceptions.ValidationException;
import lt.techin.schedule.schedules.Schedule;
import lt.techin.schedule.schedules.ScheduleRepository;
import lt.techin.schedule.schedules.holidays.Holiday;
import lt.techin.schedule.schedules.holidays.HolidayRepository;
import lt.techin.schedule.schedules.holidays.SetupHolidaysSetForSchedule;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HolidayPlanService {
    private final HolidayRepository holidayRepository;
    private final HolidayPlanRepository holidayPlanRepository;
    private final ScheduleRepository scheduleRepository;

    public HolidayPlanService(HolidayRepository holidayRepository, HolidayPlanRepository holidayPlanRepository, ScheduleRepository scheduleRepository) {
        this.holidayRepository = holidayRepository;
        this.holidayPlanRepository = holidayPlanRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<HolidayPlan> getAll() {
        return holidayPlanRepository.findAll();
    }

    public List<HolidayPlan> getById(Long id) {
        return holidayPlanRepository.findByScheduleId(id);
    }

    public String create(HolidayPlan holidayPlan, Long id) {
        Schedule existingSchedule = scheduleRepository.findById(id).orElse(null);
        if (existingSchedule == null) {
            return "Toks tvarkaraštis neegzistuoja.";
        } else {
            boolean isBeforeOrEqual = (existingSchedule.getDateFrom().isEqual(holidayPlan.getDateFrom()) ||
                    (existingSchedule.getDateFrom().isBefore(holidayPlan.getDateFrom())
                            && existingSchedule.getDateUntil().isAfter(holidayPlan.getDateFrom())));
            boolean isAfterOrEqual = (existingSchedule.getDateUntil().isEqual(holidayPlan.getDateUntil()) ||
                    (existingSchedule.getDateUntil().isAfter(holidayPlan.getDateUntil())
                            && existingSchedule.getDateFrom().isBefore(holidayPlan.getDateUntil())));
            if (isBeforeOrEqual || isAfterOrEqual) {
                if (!(isBeforeOrEqual && isAfterOrEqual)) {
                    if (isBeforeOrEqual) {
                        holidayPlan.setDateUntil(existingSchedule.getDateUntil());
                    }
                    if (isAfterOrEqual) {
                        holidayPlan.setDateFrom(existingSchedule.getDateFrom());
                    }
                }
                try {
                    if (SetupHolidaysSetForSchedule.LookForHolidayDoublesByRange(holidayPlan.getDateFrom(),
                            holidayPlan.getDateUntil(), existingSchedule.getHolidays())) {
                        return "Tvarkaraštyje esančios atostogos sutampa su planuojamomis atostogomis.";
                    }
                    HolidayPlan holidayPlan1 = new HolidayPlan();
                    holidayPlan1.setHolidayName(holidayPlan.getHolidayName());
                    holidayPlan1.setDateFrom(holidayPlan.getDateFrom());
                    holidayPlan1.setDateUntil(holidayPlan.getDateUntil());
                    holidayPlan1.setSchedule(existingSchedule);
                    holidayPlanRepository.save(holidayPlan1);
                    Set<Holiday> createdHolidays =
                            SetupHolidaysSetForSchedule.AddHolidaysByPlan(holidayPlan1, existingSchedule);
                    if (createdHolidays.isEmpty())
                        return "Sukurta 0 atostogų dienų. Priežastį galite sužinoti debugindami.";
                    existingSchedule.addHolidays(createdHolidays);
                    scheduleRepository.save(existingSchedule);
                    return "";
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            } else {
                return "Atostogų datos nesutampa su tvarkaraščio datomis. Tvarkaraštis prasideda/pasibaigia: " +
                        existingSchedule.getDateFrom() + "/"
                        + existingSchedule.getDateUntil() + ". Atostogos prasideda/pasibaigia: " +
                        holidayPlan.getDateFrom() + "/" + holidayPlan.getDateUntil() + ".";
            }
            return "Atostogų sukūrimas nepavyko.";
        }
    }

    public HolidayPlan getHolidayById(Long holidayId) {
        return holidayPlanRepository.findById(holidayId).orElseThrow(() ->
                new ValidationException("Nurodytos atostogos neegzistuoja.",
                        "Holiday", "Does nor exist", holidayId.toString()));
    }

    public HolidayPlan update(Long holidayId, HolidayPlan holiday) {
        HolidayPlan existingHolidayPlan = holidayPlanRepository.findById(holidayId).orElseThrow(() ->
                new ValidationException("Nurodytos atostogos neegzistuoja.",
                        "Holiday", "Does nor exist", holidayId.toString()));
        existingHolidayPlan.setHolidayName(holiday.getHolidayName());
        existingHolidayPlan.setDateFrom(holiday.getDateFrom());
        existingHolidayPlan.setDateUntil(holiday.getDateUntil());
        return holidayPlanRepository.save(existingHolidayPlan);
    }

    @Transactional
    public Boolean deleteHoliday(Long holidayId) {
        try {
            holidayPlanRepository.deleteById(holidayId);
            return true;
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            return false;
        }
    }
}
