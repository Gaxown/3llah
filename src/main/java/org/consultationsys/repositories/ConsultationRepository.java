package org.consultationsys.repositories;

import org.consultationsys.models.enums.ConsultationStatus;
import org.consultationsys.utils.JPAUtil;


import java.util.List;
import java.util.Optional;

import org.consultationsys.models.Consultation;

public class ConsultationRepository {

    public ConsultationRepository() {
    }

    public Consultation save(Consultation consultation) {
       JPAUtil.executeInTransaction(em -> em.persist(consultation));
       return consultation;
    }

    public Optional<Consultation> findById(Long id) {
        return Optional.ofNullable(JPAUtil.getEntityManager().find(Consultation.class, id));
    }

    public List<Consultation> findAll() {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT c FROM Consultation c", Consultation.class)
                .getResultList();
    }

    public Consultation update(Consultation consultation) {
        final Consultation[] updated = new Consultation[1];
        JPAUtil.executeInTransaction(em -> {
            updated[0] = em.merge(consultation);
        });
        return updated[0];
    }

    public void deleteById(Long id) {
        // JPAUtil.executeInTransaction(em -> {
        //     Consultation consultation = em.find(Consultation.class, id);
        //     if (consultation != null) {
        //         em.remove(consultation);
        //     }
        // });

        findById(id).ifPresent(consultation -> {
            JPAUtil.executeInTransaction(em -> em.remove(em.contains(consultation) ? consultation : em.merge(consultation)));
        });
    }

    public List<Consultation> findByPatientId(Long patientId) {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT c FROM Consultation c WHERE c.patient.id = :patientId", Consultation.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }

    public List<Consultation> findByGeneralistId(Long generalistId) {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT c from Consultation from c WHERE c.generalistId = :generalistId", Consultation.class)
                .setParameter("generalistId", generalistId)
                .getResultList();
    }

    public List<Consultation> findByStatus(ConsultationStatus status) {
        return JPAUtil.getEntityManager()
                .createQuery("SELECT c FROM Consultation c WHERE c.status = :status", Consultation.class)
                .setParameter("status", status.toString())
                .getResultList();
    }
}
