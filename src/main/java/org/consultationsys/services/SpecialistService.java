package org.consultationsys.services;

import org.consultationsys.dtos.response.SpecialistResponseDTO;
import org.consultationsys.dtos.response.TimeSlotResponseDTO;
import org.consultationsys.mappers.SpecialistMapper;
import org.consultationsys.mappers.TimeSlotMapper;
import org.consultationsys.models.Specialist;
import org.consultationsys.models.TimeSlot;
import org.consultationsys.models.User;
import org.consultationsys.repositories.TimeSlotRepository;
import org.consultationsys.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpecialistService {

    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;

    public SpecialistService() {
        this.userRepository = new UserRepository();
        this.timeSlotRepository = new TimeSlotRepository();
    }

    public List<SpecialistResponseDTO> getSpecialistsBySpecialty(String specialty) {
        if (specialty == null || specialty.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialty cannot be empty");
        }

        return userRepository.findAll().stream()
            .filter(user -> user instanceof Specialist)
            .map(user -> (Specialist) user)
            .filter(specialist -> specialist.getSpecialty().equalsIgnoreCase(specialty.trim()))
            .filter(User::isActive)
            .sorted(Comparator.comparingDouble(Specialist::getConsultationFee))
            .map(SpecialistMapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<SpecialistResponseDTO> getAllSpecialists() {
        return userRepository.findAll().stream()
            .filter(user -> user instanceof Specialist)
            .map(user -> (Specialist) user)
            .filter(User::isActive)
            .map(SpecialistMapper::toDTO)
            .collect(Collectors.toList());
    }

    public Optional<Specialist> getSpecialistById(Long specialistId) {
        Optional<User> userOpt = userRepository.findById(specialistId);

        if (userOpt.isPresent() && userOpt.get() instanceof Specialist) {
            return Optional.of((Specialist) userOpt.get());
        }

        return Optional.empty();
    }

    public List<TimeSlotResponseDTO> getAvailableTimeSlots(Long specialistId) {
        LocalDateTime now = LocalDateTime.now();

        return timeSlotRepository.findBySpecialistId(specialistId).stream()
            .filter(slot -> slot.getDateTime().isAfter(now))
            .filter(TimeSlot::isAvailable)
            .filter(slot -> !slot.isReserved())
            .sorted(Comparator.comparing(TimeSlot::getDateTime))
            .map(TimeSlotMapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<TimeSlotResponseDTO> getAllTimeSlots(Long specialistId) {
        return timeSlotRepository.findBySpecialistId(specialistId).stream()
            .sorted(Comparator.comparing(TimeSlot::getDateTime))
            .map(TimeSlotMapper::toDTO)
            .collect(Collectors.toList());
    }

    public TimeSlot reserveTimeSlot(Long timeSlotId) {
        Optional<TimeSlot> slotOpt = timeSlotRepository.findById(timeSlotId);

        if (slotOpt.isEmpty()) {
            throw new IllegalArgumentException("Time slot not found with ID: " + timeSlotId);
        }

        TimeSlot slot = slotOpt.get();

        if (!slot.isAvailable() || slot.isReserved()) {
            throw new IllegalStateException("Time slot is not available");
        }

        if (slot.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot reserve past time slots");
        }

        slot.setReserved(true);
        slot.setAvailable(false);

        timeSlotRepository.save(slot);
        return slot;
    }

    public Specialist updateProfile(Long specialistId, String specialty, Double consultationFee) {
        Optional<Specialist> specialistOpt = getSpecialistById(specialistId);

        if (specialistOpt.isEmpty()) {
            throw new IllegalArgumentException("Specialist not found with ID: " + specialistId);
        }

        Specialist specialist = specialistOpt.get();

        if (specialty != null && !specialty.trim().isEmpty()) {
            specialist.setSpecialty(specialty.trim());
        }

        if (consultationFee != null && consultationFee > 0) {
            specialist.setConsultationFee(consultationFee);
        }

        return (Specialist) userRepository.update(specialist);
    }

    public List<TimeSlot> generateTimeSlots(Long specialistId, LocalDate date) {
        Optional<User> userOpt = userRepository.findById(specialistId);

        if (userOpt.isEmpty() || !(userOpt.get() instanceof Specialist)) {
            throw new IllegalArgumentException("Specialist not found with ID: " + specialistId);
        }

        User specialist = userOpt.get();

        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        int slotDuration = 30; // minutes

        List<TimeSlot> slots = new java.util.ArrayList<>();

        LocalTime currentTime = startTime;
        while (currentTime.isBefore(endTime)) {
            LocalDateTime slotDateTime = LocalDateTime.of(date, currentTime);

            TimeSlot slot = new TimeSlot(slotDateTime, slotDuration, true, false);
            slot.setSpecialist(specialist);

            timeSlotRepository.save(slot);
            slots.add(slot);

            currentTime = currentTime.plusMinutes(slotDuration);
        }

        return slots;
    }

    public List<String> getAllSpecialties() {
        return userRepository.findAll().stream()
            .filter(user -> user instanceof Specialist)
            .map(user -> (Specialist) user)
            .map(Specialist::getSpecialty)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
}
