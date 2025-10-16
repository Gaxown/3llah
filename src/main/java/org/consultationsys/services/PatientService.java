package org.consultationsys.services;

import org.consultationsys.dtos.response.PatientResponseDTO;
import org.consultationsys.mappers.PatientMapper;
import org.consultationsys.models.Patient;
import org.consultationsys.models.VitalSign;
import org.consultationsys.models.WaitingQueue;
import org.consultationsys.repositories.PatientRepository;
import org.consultationsys.repositories.VitalSignRepository;
import org.consultationsys.repositories.WaitingQueueRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientService {

    private final PatientRepository patientRepository;
    private final VitalSignRepository vitalSignRepository;
    private final WaitingQueueRepository queueRepository;

    public PatientService() {
        this.patientRepository = new PatientRepository();
        this.vitalSignRepository = new VitalSignRepository();
        this.queueRepository = new WaitingQueueRepository();
    }

    public Optional<Patient> searchPatientBySSN(String ssn) {
        if (ssn == null || ssn.trim().isEmpty()) {
            return Optional.empty();
        }
        return patientRepository.findBySocialSecurityNumber(ssn.trim());
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient createPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }

        if (patient.getSocialSecurityNumber() == null || patient.getSocialSecurityNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Social Security Number is required");
        }

        Optional<Patient> existing = patientRepository.findBySocialSecurityNumber(patient.getSocialSecurityNumber());
        if (existing.isPresent()) {
            throw new IllegalStateException("Patient with this SSN already exists");
        }

        return patientRepository.save(patient);
    }

    public VitalSign addVitalSigns(Long patientId, VitalSign vitalSign) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);

        if (patientOpt.isEmpty()) {
            throw new IllegalArgumentException("Patient not found with ID: " + patientId);
        }

        Patient patient = patientOpt.get();
        vitalSign.setPatient(patient);
        vitalSign.setMeasurementDate(LocalDateTime.now());

        return vitalSignRepository.save(vitalSign);
    }

    public WaitingQueue addToQueue(Long patientId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);

        if (patientOpt.isEmpty()) {
            throw new IllegalArgumentException("Patient not found with ID: " + patientId);
        }

        Patient patient = patientOpt.get();

        Optional<WaitingQueue> existingQueue = queueRepository.findByPatientId(patientId);
        if (existingQueue.isPresent()) {
            return existingQueue.get();
        }

        WaitingQueue queueEntry = new WaitingQueue(patient, LocalDateTime.now());
        return queueRepository.save(queueEntry);
    }

    public List<PatientResponseDTO> getPatientsByDate(LocalDate date) {
        return queueRepository.findByDate(date).stream()
            .map(wq -> {
                PatientResponseDTO dto = PatientMapper.toDTO(wq.getPatient());
                dto.setArrivalTime(wq.getArrivalTime());
                return dto;
            })
            .sorted(Comparator.comparing(PatientResponseDTO::getArrivalTime))
            .collect(Collectors.toList());
    }

    public List<PatientResponseDTO> getTodayPatients() {
        return getPatientsByDate(LocalDate.now());
    }

    public Optional<VitalSign> getLatestVitalSigns(Long patientId) {
        List<VitalSign> vitalSigns = vitalSignRepository.findByPatientId(patientId);

        return vitalSigns.stream()
            .max(Comparator.comparing(VitalSign::getMeasurementDate));
    }

    public Patient updatePatient(Patient patient) {
        if (patient == null || patient.getId() == null) {
            throw new IllegalArgumentException("Patient and ID cannot be null");
        }

        Optional<Patient> existingOpt = patientRepository.findById(patient.getId());
        if (existingOpt.isEmpty()) {
            throw new IllegalArgumentException("Patient not found with ID: " + patient.getId());
        }

        return patientRepository.update(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}
