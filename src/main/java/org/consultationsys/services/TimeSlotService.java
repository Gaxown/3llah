package org.consultationsys.services;

import org.consultationsys.models.TimeSlot;
import org.consultationsys.repositories.TimeSlotRepository;

import java.util.List;
import java.util.Optional;

public class TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotService() {
        this.timeSlotRepository = new TimeSlotRepository();
    }

    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    public void createTimeSlot(TimeSlot timeSlot) {
        timeSlotRepository.save(timeSlot);
    }

    public Optional<TimeSlot> findById(Long id) {
        return timeSlotRepository.findById(id);
    }

    public List<TimeSlot> findAllForSpecialist(Long specialistId) {
        return timeSlotRepository.findAllForSpecialist(specialistId);
    }

    public TimeSlot updateTimeSlot(TimeSlot timeSlot) {
        return timeSlotRepository.update(timeSlot);
    }

    public void deleteTimeSlot(Long id) {
        timeSlotRepository.delete(id);
    }
}
