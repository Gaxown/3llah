package org.consultationsys.repositories;

import java.util.List;
import java.util.Optional;

import org.consultationsys.models.Patient;
import org.consultationsys.utils.JPAUtil;

public class PatientRepository {

    public PatientRepository() {
    }


    public List<Patient> findAll() {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT p FROM Patient p", Patient.class)
                .getResultList();
    }

    public Optional<Patient> findById(Long id) {
        return Optional.ofNullable(JPAUtil.getEntityManager().find(Patient.class, id));
    }

    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            JPAUtil.executeInTransaction(em -> em.persist(patient));
            return patient;
        }
        final Patient[] updated = new Patient[1];
        JPAUtil.executeInTransaction(em -> updated[0] = em.merge(patient));
        return updated[0];
    }

    public Optional<Patient> findBySocialSecurityNumber(String ssn) {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT p FROM Patient p WHERE p.socialSecurityNumber = :ssn", Patient.class)
                .setParameter("ssn", ssn)
                .getResultStream()
                .findFirst();
    }

    public Patient update(Patient patient) {
        final Patient[] updated = new Patient[1];
        JPAUtil.executeInTransaction(em -> {
            updated[0] = em.merge(patient);
        });
        return updated[0];
    }

    public void delete(Long id) {
        findById(id).ifPresent(patient ->
            JPAUtil.executeInTransaction(em -> em.remove(em.contains(patient) ? patient : em.merge(patient))
            ));
    }

}
