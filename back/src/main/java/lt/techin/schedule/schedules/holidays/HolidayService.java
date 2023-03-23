package lt.techin.schedule.schedules.holidays;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {
    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public List<Holiday> getAll() {
        return holidayRepository.findAll();
    }

    public Holiday create(Holiday holiday) {
        try {
            return holidayRepository.save(holiday);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }
}
